package com.example.fly_high.rest;

import com.example.fly_high.entity.Flight;
import com.example.fly_high.entity.Offer;
import com.example.fly_high.service.FlightService;
import com.example.fly_high.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offer")
public class OfferRestController {
    private OfferService offerService;
    private FlightService flightService;

    @Autowired
    public OfferRestController(OfferService offerService, FlightService flightService) {
        this.offerService = offerService;
        this.flightService = flightService;
    }

    @GetMapping("")
    public List<Offer> getOffers() {
        return offerService.find();
    }

    @GetMapping("/generic")
    public List<Offer> getGenericOffers() {
        return offerService.findGenericOffers();
    }

    @GetMapping("/{offerId}")
    public Offer getOffer(@PathVariable int offerId){
        Offer offer = offerService.find(offerId);
        return offer;
    }

    @PostMapping("")
    public Offer addOffer(@RequestBody Offer offer){
        offer.setId(0);
        if (offer.getFlight() != null) {
            Flight flight = flightService.find(offer.getFlight().getId());
            if(flight.getOffer() != null){
                return null;
            }
            offer.setFlight(flight);
        }
        offerService.save(offer);
        return offer;
    }

    @PutMapping("/{offerId}")
    public Offer updateOffer(@PathVariable int offerId, @RequestBody Offer offer){
        offer.setId(offerId);
        offerService.update(offer);
        return offer;
    }

    @DeleteMapping("/{offerId}")
    public Map<String, Object> deleteOffer(@PathVariable int offerId){
        Boolean status = offerService.delete(offerId);
        Map<String, Object> response = Map.of("status", status);
        return response;
    }
}
