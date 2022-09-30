package org.yunhongmin.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findItem(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
