package com.example.fly_high.service;

import com.example.fly_high.dao.FlightDao;
import com.example.fly_high.entity.Flight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService{
    private FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public boolean save(Flight flight) {
        try{
            flightDao.save(flight);
            return true;
        }catch(Exception e) {
            System.out.println("Error while saving flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            flightDao.delete(id);
            return true;
        }catch (Exception e) {
            System.out.println("Error while deleting flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Flight flight) {
        try {
            flightDao.update(flight);
            return true;
        }catch (Exception e) {
            System.out.println("Error while updating flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Flight find(int id) {
        return flightDao.find(id);
    }

    @Override
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate) {
        try {
            return flightDao.find(departureCity, arrivalCity, departureDate);
        }catch (Exception e){
            System.out.println("Error while finding flight: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate, Integer maxCost) {
        try {
            return flightDao.find(departureCity, arrivalCity, departureDate, maxCost);
        }catch (Exception e){
            System.out.println("Error while finding flight: " + e.getMessage());
            return new ArrayList<Flight>();
        }
    }
}
