package com.example.service;

import java.util.List;

import com.example.model.Network;

public interface NetworkService {

    /**
     * Store the new network
     *
     * @param network
     */
    public void addNetwork(Network network);

    /**
     * Query and return all the registered networks
     *
     * @return all the registered networks
     */
    public List<Network> listNetworks();

}
