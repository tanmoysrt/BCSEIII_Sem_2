package com.example.fly_high.service;

import com.example.fly_high.entity.Offer;

import java.util.Date;
import java.util.List;

public interface OfferService {
    public boolean save(Offer offer);
    public boolean delete(Offer offer);
    public boolean update(Offer offer);

    public Offer find(int id);
    public List<Offer> find(Date date);
    public List<Offer> find();
}
