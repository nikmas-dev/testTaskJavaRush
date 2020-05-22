package com.space.repository;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
//    List<Ship> getShipsList(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpead,
//                            Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
//                            Integer pageNumber, Integer pageSize);
//    Integer getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpead,
//                          Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);
//    Ship createShip(Ship ship);
//    Ship getShip(Long id);
//    Ship updateShip(Long id, Ship ship);
//    Boolean deleteShip(Long id);

    @Query("select b from Ship b where b.id = :id")
    Ship getShip(@Param("id") Long id);

//    @Query("select b from Ship b where b.name like %?1%")
//    Page<Ship> findByName(Pageable pageable);
@Query("SELECT c FROM Ship c WHERE (:name is null or c.name = :name) and (:planet is null or c.planet = :planet) and (:shipType is null or c.shipType = :shipType) and (:after is null or c.prodDate = :?)")
    List<Ship> findAllByNameContainingIgnoreCaseAndPlanetContainingIgnoreCaseAndShipTypeEqualsAndProdDateAfterAndProdDateBeforeAndIsUsedEqualsAndSpeedGreaterThanEqualAndSpeedIsLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeIsLessThanEqualAndRatingGreaterThanEqualAndRatingIsLessThanEqualAndC(@Param("name") String name, @Param("planet") String planet, @Param("shipType") ShipType shipType, @Param("after") Long after, Long before, @Param("isUsed") Boolean isUsed, @Param("minSpeed") Double minSpeed, @Param("maxSpeed") Double maxSpeed, @Param("minCrewSize") Integer minCrewSize, @Param("maxCrewSize") Integer maxCrewSize, @Param("minRating") Double minRating, @Param("maxRating") Double maxRating);
}
