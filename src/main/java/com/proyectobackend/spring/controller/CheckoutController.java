package com.proyectobackend.spring.controller;

import com.proyectobackend.spring.dto.Purchase;
import com.proyectobackend.spring.dto.PurchaseResponse;
import com.proyectobackend.spring.service.CheckoutService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService){

        this.checkoutService = checkoutService;

    }

    @Secured({"ROLE_USER"})
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;

    }

}
