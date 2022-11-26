package com.example.jpa11.domain.item;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.DirtyCheckEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        if (item.getId() == null) {
            itemRepository.save(item);
        } else {
            Item findItem = itemRepository.findItemById(item.getId());

            findItem.setPrice(item.getPrice());
            findItem.setStockQuantity(item.getStockQuantity());
        }
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findItemById(id);
    }

}
