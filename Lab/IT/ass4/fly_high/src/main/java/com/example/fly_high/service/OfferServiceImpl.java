package com.example.fly_high.service;

import com.example.fly_high.dao.OfferDao;
import com.example.fly_high.entity.Offer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService{
    private OfferDao offerDao;

    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Override
    public boolean save(Offer offer) {
        try {
            offerDao.save(offer);
            return true;
        }catch (Exception e) {
            System.out.println("Error while saving offer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int offerId) {
        try {
            offerDao.delete(offerId);
            return true;
        }catch (Exception e) {
            System.out.println("Error while deleting offer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Offer offer) {
        try {
            offerDao.update(offer);
            return true;
        }catch (Exception e) {
            System.out.println("Error while updating offer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Offer find(int id) {
        try{
            return offerDao.find(id);
        }catch (Exception e) {
            System.out.println("Error while finding offer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Offer> find() {
        try {
            // Current date
            Date date = new Date();
            return offerDao.find(date);
        }catch (Exception e) {
            System.out.println("Error while finding offer: " + e.getMessage());
            return new ArrayList<Offer>();
        }
    }
}
