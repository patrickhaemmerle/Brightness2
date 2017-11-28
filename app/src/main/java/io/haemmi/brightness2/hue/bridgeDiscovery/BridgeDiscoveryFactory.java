package io.haemmi.brightness2.hue.bridgeDiscovery;

public abstract class BridgeDiscoveryFactory {

    public static BridgeDiscovery createBridgeDiscovery() {
        return new NupnpBridgeDiscoveryImpl();
    }

}
