package com.proyectobackend.spring.service;

import com.proyectobackend.spring.dao.CustomerRepository;
import com.proyectobackend.spring.dao.OrderRepository;
import com.proyectobackend.spring.dto.Purchase;
import com.proyectobackend.spring.dto.PurchaseResponse;
import com.proyectobackend.spring.entity.Customer;
import com.proyectobackend.spring.entity.Order;
import com.proyectobackend.spring.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImplementation implements CheckoutService{

    private CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CheckoutServiceImplementation(CustomerRepository customerRepository,
                                         OrderRepository orderRepository){
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase){

        //recuperar orden de dto
        Order order = purchase.getOrder();

        //generar numero de seguimiento
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //poblar orden con orderItem
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //poblar order con billingAddress y shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //poblar customer con order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        //guardar en la base de datos
        customerRepository.save(customer);

        //retornar respuesta
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber(){

        //generar un UUID random
        return UUID.randomUUID().toString();
    }

    public List<Order> list(){
        return orderRepository.findAll();
    }


}
