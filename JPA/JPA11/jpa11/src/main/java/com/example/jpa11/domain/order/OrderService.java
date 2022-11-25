package com.example.jpa11.domain.order;


import com.example.jpa11.domain.delivery.Delivery;
import com.example.jpa11.domain.item.Item;
import com.example.jpa11.domain.item.ItemService;
import com.example.jpa11.domain.member.Member;
import com.example.jpa11.domain.member.MemberRepository;
import com.example.jpa11.domain.order.search.OrderSearch;
import com.example.jpa11.domain.orderItem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public Long order(Long memberId, Long itemId, int count) {

        Member member  = memberRepository.findMemberById(memberId);
        Item item = itemService.findOne(itemId);

        Delivery delivery = new Delivery(member.getAddress());
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllOrders(orderSearch.getOrderStatus(), orderSearch.getMemberName());
    }
}
