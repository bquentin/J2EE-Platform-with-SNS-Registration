package com.example.model;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.service.NetworkService;
import com.example.service.NetworkServiceImpl;
import com.example.model.Network;

public class NetworkTest {

    NetworkService networkService = null;

    @Before
    public void setUp() {
        networkService = new NetworkServiceImpl();
    }

    @After
    public void tearDown() {
        networkService = null;
    }

    @Test
    public void testNetworkServiceAddingNetwork() {
        Network nw1 = new Network();
        Network nw2 = new Network();
        Network nw3 = new Network();

        networkService.addNetwork(nw1);
        networkService.addNetwork(nw2);
        networkService.addNetwork(nw3);

        List<Network> netws = networkService.listNetworks();

        assertEquals(netws.size(), 3);
    }

    @Test
    public void testNetworkServiceNetworkData() {
        Network nw1 = new Network();
        nw1.setId(0);
        nw1.setProvider("QWE");
        nw1.setUid("ASD");
        nw1.setName("NAME1");
        nw1.setToken("ZXC");
        nw1.setTokenSecret("VBN");
        nw1.setImage("IMG1");

        networkService.addNetwork(nw1);

        List<Network> netws = networkService.listNetworks();

        try {
            Network nw2 = netws.get(0);
            StringBuilder sb = new StringBuilder();

            sb.append(nw2.getId());
            sb.append(nw2.getProvider());
            sb.append(nw2.getUid());
            sb.append(nw2.getName());
            sb.append(nw2.getToken());
            sb.append(nw2.getTokenSecret());
            sb.append(nw2.getImage());

            assertEquals(sb.toString(), "0QWEASDNAME1ZXCVBNIMG1");
        } catch (IndexOutOfBoundsException ex) {
        }
    }
}