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

    public static Delivery createDelivery(String city, String street, String zipcode){
        Delivery delivery = new Delivery();
        delivery.setCity(city);
        delivery.setStreet(street);
        delivery.setZipcode(zipcode);
        delivery.setStatus(DeliveryStatus.READY);
        return delivery;
    }
}
