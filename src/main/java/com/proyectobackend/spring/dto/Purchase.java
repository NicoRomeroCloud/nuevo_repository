package com.proyectobackend.spring.dto;

import com.proyectobackend.spring.entity.Address;
import com.proyectobackend.spring.entity.Customer;
import com.proyectobackend.spring.entity.Order;
import com.proyectobackend.spring.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
