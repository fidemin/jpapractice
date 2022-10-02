package org.yunhongmin.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.dto.CreateItemDto;
import org.yunhongmin.shop.mapper.ItemMapper;
import org.yunhongmin.shop.service.ItemService;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/items/new", method = RequestMethod.GET)
    public String createForm() {
        return "items/createForm";
    }

    @RequestMapping(value = "/items/new", method = RequestMethod.POST)
    public String create(CreateItemDto createItemDto) {
        Item item = ItemMapper.INSTANCE.toEntity(createItemDto);
        itemService.saveItem(item);
        return "redirect:/items";
    }
}
