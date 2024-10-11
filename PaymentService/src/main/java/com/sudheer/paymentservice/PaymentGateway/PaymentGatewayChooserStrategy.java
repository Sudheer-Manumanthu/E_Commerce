package com.sudheer.paymentservice.PaymentGateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class PaymentGatewayChooserStrategy {
    @Autowired
    private RazorpayPaymentGateway razorpayPaymentGateway;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    public IPaymentGateway getOptimalPaymentGateway() {
        //Add logic for random num generation and call stripe if num is even else call razorpay
        //return razorpayPaymentGateway;
        return stripePaymentGateway;
    }
}
