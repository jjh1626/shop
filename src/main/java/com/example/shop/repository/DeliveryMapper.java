package com.example.shop.repository;

import com.example.shop.model.Delivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {
    void save(Delivery delivery);
    Delivery findOne(Long deliveryId);
    void modifyStatus(Delivery delivery);
}
