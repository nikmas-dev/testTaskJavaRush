package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.repository.ShipRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

//    @Autowired
//    public void setShipRepository(ShipRepository shipRepository) {
//        this.shipRepository = shipRepository;
//    }
    @Transactional
    @Override
    public List<Ship> getShipsList(String name, String planet, ShipType shipType, Long after, Long before,
                                   Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                   Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                                   Integer pageNumber, Integer pageSize)
    {

//        return shipRepository.getShipsList(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating,
//                maxRating, order, pageNumber, pageSize);
        return shipRepository.findAll();
    }

    @Transactional
    @Override
    public Long getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpead, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        return shipRepository.count();
    }


    @Transactional
    @Override
    public Ship createShip(Ship ship) {
//        return shipRepository.createShip(ship);
        Ship createdShip = shipRepository.saveAndFlush(ship);
        return createdShip;
    }

    @Transactional
    @Override
    public Ship getShip(Long id) {
        return shipRepository.getShip(id);
    }

    @Transactional
    @Override
    public Ship updateShip(Long id, Ship ship) {
        ship.setId(id);
        return shipRepository.saveAndFlush(ship);
    }

    @Transactional
    @Override
    public Boolean deleteShip(Long id) {
        shipRepository.delete(shipRepository.getShip(id));
        return true;
    }


}
