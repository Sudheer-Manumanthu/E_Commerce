package com.sudheer.paymentservice.PaymentGateway;

public interface IPaymentGateway {
    public String getPaymentLink(Long amount,String orderId,String phoneNumber,String name);
}
