package com.example.fly_high.dao;

import com.example.fly_high.entity.Offer;

import java.util.Date;
import java.util.List;

public interface OfferDao {
    public void save(Offer offer);
    public void delete(int offerId);
    public void update(Offer offer);

    public Offer find(int id);
    public List<Offer> find(Date date);
    public List<Offer> find();
    public List<Offer> findGenericOffers();
}
