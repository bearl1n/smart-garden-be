package com.gmv.smartgardenbe.repository;

import com.gmv.smartgardenbe.entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Orders, Long>  {

    @Transactional
    @Query(value = "SELECT * " +
            "FROM Orders ord " +
            "WHERE ord.order_status = :orderStatus " +
            "AND ord.star_date < :currenDate LIMIT 10", nativeQuery = true)
    List<Orders> findNewOrders(@Param("orderStatus") String orderStatus, @Param("currenDate")  LocalDateTime currentDate);


    @Query(value = "select orders from Orders  orders where orders.id in :id")
    Orders getById(@Param("id") Long orderId);
    @Query(value = "SELECT * " +
            "FROM Orders ord " +
            "WHERE ord.order_status in ('IN_ORDER', 'IN_PROCESS') " +
            "AND ord.device_id = :deviceId " +
            "AND ord.relay_id =:relayId " +
            "AND ord.end_date is null", nativeQuery = true)
    List<Orders> findActiveOrder(@Param("deviceId") Long deviceId, @Param("relayId")  Long relayId);
}
