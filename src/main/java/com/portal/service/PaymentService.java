// src/main/java/com/portal/service/PaymentService.java
package com.portal.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private RazorpayClient client;

    public PaymentService() throws Exception {
        this.client = new RazorpayClient("rzp_test_u2ZdKyUjNGIIw0", "TTgIu8n6qo637CE2OAD0Qizz");
    }

    public String createOrder(int amount) throws Exception {
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", true);

        Order order = client.orders.create(options);
        return order.toString();
    }
}
