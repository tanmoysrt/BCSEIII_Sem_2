package com.example.fly_high.dao;

import com.example.fly_high.entity.Flight;
import com.example.fly_high.entity.Offer;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.descriptor.java.JdbcDateJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class FlightDaoImpl implements FlightDao{
    private final EntityManager entityManager;

    @Autowired
    public FlightDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void save(Flight flight) {
        Session currentSession = entityManager.unwrap(Session.class);
        Flight dbFlight = currentSession.merge(flight);
        flight.setId(dbFlight.getId());
    }

    @Override
    @Transactional
    public void delete(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Flight dbFlight = currentSession.get(Flight.class, id);
        Offer offer = dbFlight.getOffer();
        if (offer != null) {
            offer.setFlight(null);
            currentSession.merge(offer);
            currentSession.remove(offer);
        }
        currentSession.remove(dbFlight);
        currentSession.flush();

    }

    @Override
    @Transactional
    public void update(Flight flight) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.merge(flight);
    }

    @Override
    @Transactional
    public Flight find(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Flight.class, id);
    }

    @Override
    @Transactional
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Flight> query = currentSession.createQuery("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and departureDate=:departureDate", Flight.class);
        query.setParameter("departureCity", departureCity);
        query.setParameter("arrivalCity", arrivalCity);
        LocalDate date = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        query.setParameter("departureDate", date);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate, Integer maxCost) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Flight> query = currentSession.createQuery("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and departureDate=:departureDate and cost<=:maxCost", Flight.class);
        query.setParameter("departureCity", departureCity);
        query.setParameter("arrivalCity", arrivalCity);
        LocalDate date = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        query.setParameter("departureDate", date);
        query.setParameter("maxCost", maxCost);
        return query.getResultList();
    }

    @Override
    public List<Flight> findAll() {
        // sort by departure date desc
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Flight order by departureDate desc", Flight.class).getResultList();
    }
}
