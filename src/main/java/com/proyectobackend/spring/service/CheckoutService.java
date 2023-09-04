package com.proyectobackend.spring.service;

import com.proyectobackend.spring.dao.OrderRepository;
import com.proyectobackend.spring.dto.Purchase;
import com.proyectobackend.spring.dto.PurchaseResponse;
import com.proyectobackend.spring.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
