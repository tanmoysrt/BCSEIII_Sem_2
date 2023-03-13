package com.example.fly_high.dao;

import com.example.fly_high.entity.Flight;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FlightDaoImpl implements FlightDao{
    private final EntityManager entityManager;

    @Autowired
    public FlightDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void save(Flight flight) {
        Session currentSession = entityManager.unwrap(Session.class);
        Flight dbFlight = currentSession.merge(flight);
        flight.setId(dbFlight.getId());
    }

    @Override
    public void delete(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Flight dbFlight = currentSession.get(Flight.class, id);
        currentSession.remove(dbFlight);
    }

    @Override
    public void update(Flight flight) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.merge(flight);
    }

    @Override
    public Flight find(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Flight.class, id);
    }

    @Override
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Flight> query = currentSession.createQuery("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and departureDate=:departureDate", Flight.class);
        query.setParameter("departureCity", departureCity);
        query.setParameter("arrivalCity", arrivalCity);
        query.setParameter("departureDate", departureDate);
        return query.getResultList();
    }

    @Override
    public List<Flight> find(String departureCity, String arrivalCity, String departureDate, Integer maxCost) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Flight> query = currentSession.createQuery("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and departureDate=:departureDate and cost<=:maxCost", Flight.class);
        query.setParameter("departureCity", departureCity);
        query.setParameter("arrivalCity", arrivalCity);
        query.setParameter("departureDate", departureDate);
        query.setParameter("maxCost", maxCost);
        return query.getResultList();
    }
}
