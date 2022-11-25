package com.example.jpa11.domain.order;

import com.example.jpa11.domain.order.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderById(Long id);

    List<Order> findAll();

    @Query("SELECT o FROM Order o WHERE o.status = :orderSearchOrderStatus AND o.member.name = :orderSearchMemberName")
    List<Order> findAllOrders(OrderStatus orderSearchOrderStatus, String orderSearchMemberName);

}
