package com.example.fly_high.dao;


import com.example.fly_high.entity.Offer;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class OfferDaoImpl implements OfferDao {
    private final EntityManager entityManager;

    @Autowired
    public OfferDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Offer offer) {
        Session currentSession = entityManager.unwrap(Session.class);
        Offer dbOffer = currentSession.merge(offer);
        offer.setId(dbOffer.getId());
    }

    @Override
    @Transactional
    public void delete(Offer offer) {
        Session currentSession = entityManager.unwrap(Session.class);
        Offer dbOffer = currentSession.get(Offer.class, offer.getId());
        currentSession.remove(dbOffer);
    }

    @Override
    @Transactional
    public void update(Offer offer) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.merge(offer);
    }

    @Override
    @Transactional
    public Offer find(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Offer.class, id);
    }

    @Override
    @Transactional
    public List<Offer> find(Date date) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Offer where date=:date", Offer.class).setParameter("date", date).getResultList();
    }

    @Override
    @Transactional
    public List<Offer> find() {
        Date currentDate = new Date();
        return find(currentDate);
    }
}
