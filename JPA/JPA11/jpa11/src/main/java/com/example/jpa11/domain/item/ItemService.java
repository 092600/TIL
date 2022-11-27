package com.example.jpa11.domain.item;

import com.example.jpa11.domain.item.book.Book;
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

    public void saveItem(Item tmp) {
        if (tmp.getId() == null) {
            itemRepository.save(tmp);
        } else {
            Item item = itemRepository.findItemById(tmp.getId());

            item.setPrice(tmp.getPrice());
            item.setStockQuantity(tmp.getStockQuantity());
        }
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Book findOne(Long id) {
        return (Book) itemRepository.findItemById(id);
    }

}
