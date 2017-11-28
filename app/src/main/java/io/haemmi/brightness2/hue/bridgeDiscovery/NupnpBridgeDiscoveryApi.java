package io.haemmi.brightness2.hue.bridgeDiscovery;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

interface NupnpBridgeDiscoveryApi {
    @GET("nupnp")
    public Single<List<DiscoveredBridge>> discoverBridges();

}
