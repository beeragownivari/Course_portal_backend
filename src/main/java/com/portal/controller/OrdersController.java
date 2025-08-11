package com.portal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.razorpay.RazorpayException;
import com.portal.model.Orders;
import com.portal.service.OrderService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) throws RazorpayException {
        System.out.println("Creating Razorpay order for: " + orders.getEmail());
        Orders razorpayOrder = orderService.createOrder(orders);
        return ResponseEntity.ok(razorpayOrder);
    }

    @PostMapping("/paymentCallback")
    public ResponseEntity<String> paymentCallback(@RequestBody Map<String, String> response) {
        System.out.println("Received payment callback: " + response);
        orderService.updateStatus(response);
        return ResponseEntity.ok("Payment status updated");
    }

}
