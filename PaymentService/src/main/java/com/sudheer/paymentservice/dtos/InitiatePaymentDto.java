package com.sudheer.paymentservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {
    private String orderId;

    private String phoneNumber;

    private String name;

    private Long amount;
}
