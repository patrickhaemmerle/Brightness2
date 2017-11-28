package io.haemmi.brightness2.hue.bridgeDiscovery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

public class NupnpBridgeDiscoveryImplTest {

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

        NupnpBridgeDiscoveryImpl bd = new NupnpBridgeDiscoveryImpl();
        bd.setNupnpUrl("http://localhost:8080");

        List<DiscoveredBridge> bridges = bd.discover().blockingGet();

        assertEquals(1, bridges.size());
        assertEquals("001788fffe100491", bridges.get(0).getId());
        assertEquals("192.168.2.23", bridges.get(0).getIternalipaddress());
    }

    @Test
    public void multipleResultsReturned() {

        mockWebServer.enqueue(new MockResponse().setBody("[{\n" +
                "     \"id\":\"001788fffe100491\",\n" +
                "     \"internalipaddress\":\"192.168.2.23\",\n" +
                "     \"macaddress\":\"00:17:88:10:04:91\",\n" +
                "     \"name\":\"Philips Hue\"\n" +
                "}, {\"id\":\"001788fffe100492\",\n" +
                "     \"internalipaddress\":\"192.168.2.24\",\n" +
                "     \"macaddress\":\"00:17:88:10:04:92\",\n" +
                "     \"name\":\"Philips Hue 2\"\n" +
                "}]"));

        NupnpBridgeDiscoveryImpl bd = new NupnpBridgeDiscoveryImpl();
        bd.setNupnpUrl("http://localhost:8080");

        List<DiscoveredBridge> bridges = bd.discover().blockingGet();

        assertEquals(2, bridges.size());
        assertEquals("001788fffe100491", bridges.get(0).getId());
        assertEquals("192.168.2.23", bridges.get(0).getIternalipaddress());
    }

    @Test
    public void emptyResultsReturned() {

        mockWebServer.enqueue(new MockResponse().setBody("[]"));

        NupnpBridgeDiscoveryImpl bd = new NupnpBridgeDiscoveryImpl();
        bd.setNupnpUrl("http://localhost:8080");

        List<DiscoveredBridge> bridges = bd.discover().blockingGet();

        assertEquals(0, bridges.size());
    }
}
