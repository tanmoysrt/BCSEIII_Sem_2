package com.example.fly_high.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;



@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cost")
    private int cost;

    @Column(name = "date")
    private Date date;

    @Column(name = "is_limited_time_offer")
    private boolean isLimitedTimeOffer;

    @Column(name = "valid_until")
    private Time validUntil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @JsonManagedReference
    private Flight flight;

    public Offer() {
    }

    public Offer(int cost, Date date, boolean isLimitedTimeOffer, Time validUntil, Flight flight){
        this.cost = cost;
        this.date = date;
        this.isLimitedTimeOffer = isLimitedTimeOffer;
        this.validUntil = validUntil;
        this.flight = flight;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isLimitedTimeOffer() {
        return isLimitedTimeOffer;
    }

    public void setLimitedTimeOffer(boolean limitedTimeOffer) {
        isLimitedTimeOffer = limitedTimeOffer;
    }

    public Time getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Time validUntil) {
        this.validUntil = validUntil;
    }


}
