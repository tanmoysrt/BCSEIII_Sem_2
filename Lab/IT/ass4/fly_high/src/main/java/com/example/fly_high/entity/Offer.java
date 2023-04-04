package com.example.fly_high.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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


    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private int cost;

    @Column(name = "date")
    private Date date;

    @Column(name = "is_limited_time_offer")
    private boolean isLimitedTimeOffer;

    @Column(name = "valid_until")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time validUntil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @JsonManagedReference
    private Flight flight;

    public Offer() {
    }

    public Offer(int cost, Date date, boolean isLimitedTimeOffer, Time validUntil, Flight flight, String description){
        this.cost = cost;
        this.date = date;
        this.isLimitedTimeOffer = isLimitedTimeOffer;
        this.validUntil = validUntil;
        this.flight = flight;
        this.description = description;
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

    public boolean getIsLimitedTimeOffer() {
        return isLimitedTimeOffer;
    }

    public void setIsLimitedTimeOffer(boolean isLimitedTimeOffer) {
        this.isLimitedTimeOffer = isLimitedTimeOffer;
    }

    public Time getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Time validUntil) {
        this.validUntil = validUntil;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
