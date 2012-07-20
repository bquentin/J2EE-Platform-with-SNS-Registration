package com.example.service;

import com.example.model.Network;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Service
public class NetworkServiceImpl implements NetworkService {

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public void addNetwork(Network network) {
        em.persist(network);
    }

    @Transactional
    public List<Network> listNetworks() {
        CriteriaQuery<Network> c = em.getCriteriaBuilder().createQuery(Network.class);
        c.from(Network.class);
        return em.createQuery(c).getResultList();
    }
}
