package com.kientran.order_service.repository;

import com.kientran.order_service.entity.Order;
import com.kientran.order_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByAccountId(Integer accountId);

    @Query(value = "SELECT SUM(b.totalprice) FROM orders a, bill b, order_status c WHERE a.order_status_id=c.id and a.bill_id=b.id and c.status != 'Đã hủy';", nativeQuery = true)
    Double calculateRevenue();

    @Query(value = "select count(*) from orders;", nativeQuery = true)
    Integer getTotalOrder();
}
