package com.sudheer.paymentservice.services;


import com.sudheer.paymentservice.PaymentGateway.PaymentGatewayChooserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    public String getPaymentLink(Long amount,String orderId,String phoneNumber,String name){
        return paymentGatewayChooserStrategy.getOptimalPaymentGateway().getPaymentLink(amount,orderId,phoneNumber,name);
    }
}
