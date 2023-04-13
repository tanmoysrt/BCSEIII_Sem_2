package com.example.fly_high.service;

import com.example.fly_high.entity.Flight;
import com.example.fly_high.entity.Offer;

import java.util.List;

public interface FlightService {
    public boolean save(Flight flight);
    public boolean delete(int id);
    public boolean update(Flight flight);

    public Flight find(int id);
    public List<Flight> findAll();
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate);
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate, Integer maxCost);
}
