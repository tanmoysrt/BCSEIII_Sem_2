package com.example.fly_high.rest;

import com.example.fly_high.entity.Flight;
import com.example.fly_high.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flight")
public class FlightRestController {
    private FlightService flightService;

    @Autowired
    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/search")
    public List<Flight> getAllFlights(@RequestBody Map<String, Object> payload) {
        String departure = (String) payload.get("departure");
        String destination = (String) payload.get("destination");
        String departureDate = (String) payload.get("departureDate");
        Integer maxCost = (Integer) payload.get("maxCost");
        if (maxCost != null) {
            return flightService.find(departure, destination, departureDate, maxCost);
        }

        return flightService.find(departure, destination, departureDate);
    }

    @PostMapping("")
    public Flight addFlight(@RequestBody Flight flight){
        flightService.save(flight);
        return flight;
    }

    @GetMapping("/{flightId}")
    public Flight getFlight(@PathVariable int flightId){
        return flightService.find(flightId);
    }

    @PutMapping("/{flightId}")
    public Flight updateFlight(@PathVariable int flightId, @RequestBody Flight flight){
        flight.setId(flightId);
        flightService.update(flight);
        return flight;
    }

    @DeleteMapping("/{flightId}")
    public Map<String, Object> deleteFlight(@PathVariable int flightId){
        Boolean status = flightService.delete(flightId);
        Map<String, Object> response = Map.of("status", status);
        return response;
    }
}
