package com.kbs.jpademo.repository;

import com.kbs.jpademo.domain.Item;
import com.kbs.jpademo.domain.ItemSellStatus;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Test
    public void insertItem(){
        Item item = Item.builder()
                .itemNm("볼펜")
                .itemDetail("수성 볼펜")
                .itemSellStatus(ItemSellStatus.판매중)
                .price(1000)
                .stockNumber(10)
                .build();
        itemRepository.save(item);
    }
    @Test
    public void findAll(){
        List<Item> items = itemRepository.findAll();
        for(Item item:items){
            log.info(item.toString());
        }
    }
    @Test
    public void findById(){
        Optional<Item> item = itemRepository.findById(1L);
        Optional<Item> item1 = itemRepository.findById(2L);
        log.info(item.toString());
        log.info(item1.toString());
    }
    @Test
    public void updateItem(){
        Item item = itemRepository.findById(1L).get();
        item.setItemNm("지우개");
        item.setItemDetail("고무");
        itemRepository.save(item);
    }
    @Test
    public void deleteItem(){
        itemRepository.deleteById(2L);
    }
}
