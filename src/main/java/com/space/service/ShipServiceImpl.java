package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.repository.ShipRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

//    @Autowired
//    public void setShipRepository(ShipRepository shipRepository) {
//        this.shipRepository = shipRepository;
//    }

    @Override
    public Page<Ship> getShipsList(String name, String planet, ShipType shipType, Long after, Long before,
                                   Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                   Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                                   Optional<Integer> pageNumber, Optional<Integer> pageSize)
    {

//        return shipRepository.getShipsList(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating,
//                maxRating, order, pageNumber, pageSize);
//        Page<Ship> allShips = shipRepository.findByName(PageRequest.of(0, 3, Sort.by(order.getFieldName()).ascending()));
//        return allShips;
//        Page<Ship> allShips = shipRepository.findAll(PageRequest.of(0, 5, Sort.by("id").ascending()));
//        return allShips;
//        Page<Ship> page = new PageImpl<>(allShips, PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()).ascending()), allShips.size());
//        return page;
        List<Ship> allShips = shipRepository.findAll();
        allShips = allShips.stream()
                .filter(s -> name != null ? s.getName().contains(name) : true)
                .filter(s -> planet != null ? s.getPlanet().contains(planet) : true)
                .filter(s -> shipType != null ? s.getShipType().equals(shipType) : true)
                .filter(s -> after != null ? s.getProdDate().after(new Date(after)) : true)
                .filter(s -> before != null ? s.getProdDate().before(new Date(before)) : true)
                .filter(s -> s.getUsed())
                .filter(s -> minSpeed != null ? s.getSpeed() >= minSpeed : true)
                .filter(s -> maxSpeed != null ? s.getSpeed() <= maxSpeed : true)
                .filter(s -> minCrewSize != null ? s.getCrewSize() >= minCrewSize : true)
                .filter(s -> maxCrewSize != null ? s.getCrewSize() <= maxCrewSize : true)
                .filter(s -> minRating != null ? s.getRating() >= minRating : true)
                .filter(s -> maxRating != null ? s.getRating() <= maxRating : true)
                .collect(Collectors.toCollection(ArrayList::new));

        Page<Ship> page = new PageImpl<>(allShips, PageRequest.of(pageNumber.orElse(0), pageSize.orElse(3), Sort.by(order.getFieldName()).ascending()), allShips.size());

//        shipRepository.findAllBySpeedGreaterThanEqualAndProdDateAfter(minSpeed, new Date(after));
        return page;
    }


    @Override
    public Long getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpead, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        return shipRepository.count();
    }



    @Override
    public Ship createShip(Ship ship) {
        try {
            ship.setRating(getRating(ship));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Ship createdShip = shipRepository.saveAndFlush(ship);
        return createdShip;
    }


    @Override
    public Ship getShip(Long id) {
        return shipRepository.getShip(id);
    }


    @Override
    public Ship updateShip(Long id, Ship ship) {
        ship.setId(id);
        try {
            ship.setRating(getRating(ship));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shipRepository.saveAndFlush(ship);
    }


    @Override
    public Boolean deleteShip(Long id) {
        shipRepository.delete(shipRepository.getShip(id));
        return true;
    }

    private static Double getRating(Ship ship) throws ParseException {
        Double k = ship.getUsed() ? 0.5 : 1;

        Calendar shipProd = Calendar.getInstance();
        shipProd.setTime(ship.getProdDate());
        int shipProdYear = shipProd.get(Calendar.YEAR);

        Double rating = 80 * ship.getSpeed() * k / (3019 - shipProdYear + 1);
        return rating;
    }
}
