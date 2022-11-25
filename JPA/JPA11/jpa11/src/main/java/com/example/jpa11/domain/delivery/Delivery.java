package com.example.jpa11.domain.delivery;

import com.example.jpa11.domain.address.Address;
import com.example.jpa11.domain.delivery.status.DeliveryStatus;
import com.example.jpa11.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery() {

    }
    public Delivery(Address address) {
        this.address = address;
    }

}

