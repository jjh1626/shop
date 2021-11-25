package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Delivery {
    private Long deliveryId;
    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;
}
