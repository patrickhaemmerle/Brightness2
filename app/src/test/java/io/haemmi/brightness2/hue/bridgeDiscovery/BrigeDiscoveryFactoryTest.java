package io.haemmi.brightness2.hue.bridgeDiscovery;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class BrigeDiscoveryFactoryTest {

    @Test
    public void createBridgeDiscovery() {
        assertTrue(BridgeDiscoveryFactory.createBridgeDiscovery() instanceof BridgeDiscovery);
    }
}
