package io.haemmi.brightness2.hue.bridgeDiscovery;

import java.util.List;

import io.reactivex.Single;


public interface BridgeDiscovery {
    public Single<List<DiscoveredBridge>> discover();
}
