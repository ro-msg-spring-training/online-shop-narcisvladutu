package ro.msg.learning.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query(value = "select LO.* from LOCATION AS LO " +
            "INNER JOIN STOCK AS ST ON (LO.ID = ST.LOCATION_ID)" +
            "INNER JOIN PRODUCT AS PR ON (ST.PRODUCT_ID = PR.ID)" +
            "WHERE PR.ID = ?1 AND ST.quantity >= ?2 ", nativeQuery = true)
    List<Location> findAllAvailableShippingLocations(final Integer productId, final Integer quantity);

    @Query(value = "SELECT TOP 1 LO.* FROM LOCATION AS LO " +
            "INNER JOIN STOCK AS ST ON (LO.ID = ST.LOCATION_ID)" +
            "INNER JOIN PRODUCT AS PR ON (ST.PRODUCT_ID = PR.ID)" +
            "WHERE PR.ID = ?1 AND ST.quantity >= ?2 " +
            "AND ST.QUANTITY = (SELECT max(QUANTITY) FROM STOCK WHERE PRODUCT_ID = ?1) ",
            nativeQuery = true)
    Location findMostAbundantShippingLocation(final Integer productId, final Integer quantity);
}