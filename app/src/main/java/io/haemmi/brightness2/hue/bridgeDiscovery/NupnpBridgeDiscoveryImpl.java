package io.haemmi.brightness2.hue.bridgeDiscovery;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class NupnpBridgeDiscoveryImpl implements BridgeDiscovery {

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
        NupnpBridgeDiscoveryApi api = retrofit.create(NupnpBridgeDiscoveryApi.class);
        return api.discoverBridges().subscribeOn(Schedulers.io());
    }

}