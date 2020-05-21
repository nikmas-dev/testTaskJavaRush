package com.space.repository;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public class ShipRepositoryImpl{
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public List<Ship> getShipsList(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order, Integer pageNumber, Integer pageSize) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("from Ship").list();
//    }
//
//    @Override
//    public Integer getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("select count(*) from Ship", Number.class).getSingleResult().intValue();
//    }
//
//    @Override
//    public Ship createShip(Ship ship) {
//        Session session = sessionFactory.getCurrentSession();
//        session.persist(ship);
//        return ship;
//    }
//
//    @Override
//    public Ship getShip(Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(Ship.class, id);
//    }
//
//    @Override
//    public Ship updateShip(Long id, Ship ship) {
//        ship.setId(id);
//        Session session = sessionFactory.getCurrentSession();
//        session.update(ship);
//        return ship;
//    }
//
//    @Override
//    public Boolean deleteShip(Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        Ship ship = session.get(Ship.class, id);
//        session.delete(ship);
//        return true;
//    }
}
