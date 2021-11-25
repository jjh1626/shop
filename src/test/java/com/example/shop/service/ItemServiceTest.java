package com.example.shop.service;

import com.example.shop.model.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(MemberServiceTest.class);

    @Autowired ItemService itemService;

    @Test
    public void 상품등록() throws Exception {
        Item item = new Item();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        item.setDtype("B");

        Long itemId = itemService.saveItem(item);
        Item findItem = itemService.findOne(itemId);

        log.info("findItemName : " + findItem.getName());
        Assertions.assertThat(item.getName()).isEqualTo(findItem.getName());
    }
}