package com.microserviceshop.PaymentService.service;

import com.microserviceshop.PaymentService.model.PaymentRequest;
import com.microserviceshop.PaymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
