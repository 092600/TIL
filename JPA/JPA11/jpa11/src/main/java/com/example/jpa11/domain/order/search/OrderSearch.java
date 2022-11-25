package com.example.jpa11.domain.order.search;

import com.example.jpa11.domain.order.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
