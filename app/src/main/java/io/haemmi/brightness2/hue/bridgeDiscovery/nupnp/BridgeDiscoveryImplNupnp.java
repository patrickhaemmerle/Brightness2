package io.haemmi.brightness2.hue.bridgeDiscovery.nupnp;

import android.support.annotation.NonNull;

import java.util.List;

import io.haemmi.brightness2.hue.bridgeDiscovery.BridgeDiscovery;
import io.haemmi.brightness2.hue.bridgeDiscovery.DiscoveredBridge;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class BridgeDiscoveryImplNupnp implements BridgeDiscovery {

    private String nupnpUrl = "https://www.meethue.com/api/";

    void setNupnpUrl(String nupnpUrl) {
        this.nupnpUrl = nupnpUrl;
    }

    @Override
    public Single<List<DiscoveredBridge>> discover() {
        return discoverBridges();
    }

    @NonNull
    private Single<List<DiscoveredBridge>> discoverBridges() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(nupnpUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BridgeDiscoveryApi api = retrofit.create(BridgeDiscoveryApi.class);
        return api.discoverBridges();
    }

}