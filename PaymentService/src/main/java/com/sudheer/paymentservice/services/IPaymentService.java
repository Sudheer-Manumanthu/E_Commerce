package com.sudheer.paymentservice.services;

public interface IPaymentService {
    public String getPaymentLink(Long amount,String orderId,String phoneNumber,String name);
}
