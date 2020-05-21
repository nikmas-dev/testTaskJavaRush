package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ShipController {
    private ShipService shipService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "rest/ships")
    public ResponseEntity<List<Ship>> getShipsList(@RequestParam(name = "name") String name, @RequestParam(name = "planet") String planet,
                                                   @RequestParam(name = "ShipType") ShipType shipType, @RequestParam(name = "after") Long after,
                                                   @RequestParam(name = "before") Long before, @RequestParam(name = "isUsed") Boolean isUsed,
                                                   @RequestParam(name = "minSpeed") Double minSpeed, @RequestParam(name = "maxSpeed") Double maxSpeed,
                                                   @RequestParam(name = "minCrewSize") Integer minCrewSize, @RequestParam(name = "maxCrewSize") Integer maxCrewSize,
                                                   @RequestParam(name = "minRating") Double minRating, @RequestParam(name = "maxRating") Double maxRating,
                                                   @RequestParam(name = "order") ShipOrder order, @RequestParam(name = "pageNumber") Integer pageNumber,
                                                   @RequestParam(name = "pageSize") Integer pageSize
    ){
        if (pageNumber == null)
            pageNumber = 0;

        if (pageSize == null)
            pageSize = 3;

        if (isUsed == null)
            isUsed = false;

        List<Ship> shipList = shipService.getShipsList(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating,
                maxRating, order, pageNumber, pageSize);

        return shipList != null && !shipList.isEmpty()
                ? new ResponseEntity<>(shipList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "rest/ships/count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam(name = "name") String name, @RequestParam(name = "planet") String planet,
                                                 @RequestParam(name = "ShipType") ShipType shipType, @RequestParam(name = "after") Long after,
                                                 @RequestParam(name = "before") Long before, @RequestParam(name = "isUsed") Boolean isUsed,
                                                 @RequestParam(name = "minSpeed") Double minSpeed, @RequestParam(name = "maxSpeed") Double maxSpeed,
                                                 @RequestParam(name = "minCrewSize") Integer minCrewSize, @RequestParam(name = "maxCrewSize") Integer maxCrewSize,
                                                 @RequestParam(name = "minRating") Double minRating, @RequestParam(name = "maxRating") Double maxRating
    ){
        if (isUsed == null)
            isUsed = false;

        Integer shipsCount = shipService.getShipsCount(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating,
                maxRating);


        return new ResponseEntity<>(shipsCount, HttpStatus.OK);
    }

    @PostMapping(value = "rest/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) throws ParseException {
        if (ship.getName() == null || ship.getPlanet() == null || ship.getShipType() == null || ship.getProdDate() == null || ship.getSpeed() == null
                || ship.getCrewSize() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (ship.getName().isEmpty() || ship.getPlanet().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (ship.getName().length() > 50 || ship.getPlanet().length() > 50)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Calendar startRangeCalendar = new GregorianCalendar(2800, Calendar.JANUARY, 1);

        Date d = new SimpleDateFormat("MM/dd/yyyy").parse("01/12/3019");
        Calendar finishRangeCalendar = Calendar.getInstance();
        finishRangeCalendar.setTime(d);
        finishRangeCalendar.set(Calendar.DATE, finishRangeCalendar.getActualMaximum(Calendar.DATE));
        finishRangeCalendar.set(Calendar.HOUR, 23);
        finishRangeCalendar.set(Calendar.MINUTE, 59);
        finishRangeCalendar.set(Calendar.SECOND, 59);

        if (ship.getProdDate().before(startRangeCalendar.getTime()) || ship.getProdDate().after(finishRangeCalendar.getTime()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (ship.getSpeed() < 0.01 || ship.getSpeed() > 0.99)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship newShip = shipService.createShip(ship);
        return new ResponseEntity<>(newShip, HttpStatus.OK);
    }

    @GetMapping(value = "rest/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id){
        if (id < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship = shipService.getShip(id);

        return ship != null ? new ResponseEntity<>(ship, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "rest/ships/{id}")
    public ResponseEntity<?> updateShip(@PathVariable Long id, @RequestBody Ship ship){
        if (id < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship updatedShip = shipService.updateShip(id, ship);
        return updatedShip != null ? new ResponseEntity<>(updatedShip, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "rest/ships/{id}")
    public ResponseEntity<?> deleteShip(@PathVariable Long id){
        if (id < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Boolean isDeleted = shipService.deleteShip(id);

        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
