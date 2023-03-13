package com.example.fly_high.dao;

import com.example.fly_high.entity.Flight;

import java.util.List;

public interface FlightDao {
    public void save(Flight flight);
    public void delete(int id);
    public void update(Flight flight);

    public Flight find(int id);
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate);
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate, Integer maxCost);
}
