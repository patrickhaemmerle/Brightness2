package io.haemmi.brightness2.hue.bridgeDiscovery.nupnp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.haemmi.brightness2.hue.bridgeDiscovery.DiscoveredBridge;
import io.haemmi.brightness2.hue.bridgeDiscovery.nupnp.BridgeDiscoveryImplNupnp;
import io.reactivex.observers.BaseTestConsumer;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class BridgeDiscoveryImplNupnpTest {

    private MockWebServer mockWebServer;

    @Before
    public void startMockServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8080);
    }

    @After
    public void stopMockServer() throws IOException {
        if (mockWebServer != null) {
            mockWebServer.shutdown();
        }
    }

    @Test
    public void singleResultReturned() throws Throwable {

        mockWebServer.enqueue(new MockResponse().setBody("[{\n" +
                "     \"id\":\"001788fffe100491\",\n" +
                "     \"internalipaddress\":\"192.168.2.23\",\n" +
                "     \"macaddress\":\"00:17:88:10:04:91\",\n" +
                "     \"name\":\"Philips Hue\"\n" +
                "}]"));

        BridgeDiscoveryImplNupnp bd = new BridgeDiscoveryImplNupnp();
        bd.setNupnpUrl("http://localhost:8080");

        List<DiscoveredBridge> bridges = new ArrayList<DiscoveredBridge>();
        bd.discover().subscribe(bridgeList -> {
            bridges.addAll(bridgeList);
        });

        assertEquals(1, bridges.size());
        assertEquals("001788fffe100491", bridges.get(0).getUid());
        assertEquals("192.168.2.23", bridges.get(0).getIp());
    }

    @Test
    public void multipleResultsReturned() {
        fail("Implement me!");
    }

    @Test
    public void emptyResultsReturned() {
        fail("Implement me!");
    }

}
