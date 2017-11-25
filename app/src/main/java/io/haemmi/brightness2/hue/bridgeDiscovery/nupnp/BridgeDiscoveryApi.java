package io.haemmi.brightness2.hue.bridgeDiscovery.nupnp;

import java.util.List;

import io.haemmi.brightness2.hue.bridgeDiscovery.DiscoveredBridge;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface BridgeDiscoveryApi {

    @GET("nupnp")
    public Single<List<DiscoveredBridge>> discoverBridges();

}
