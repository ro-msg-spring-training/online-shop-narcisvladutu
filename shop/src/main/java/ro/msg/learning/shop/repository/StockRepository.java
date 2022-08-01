package ro.msg.learning.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query(value = "select * from stock where location_id = ?1 and product_id = ?2 ",
            nativeQuery = true)
    Stock findByLocationAndProduct(Integer locationId, Integer productId);
}