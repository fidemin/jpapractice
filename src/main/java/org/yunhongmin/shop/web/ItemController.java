package org.yunhongmin.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.dto.CreateItemDto;
import org.yunhongmin.shop.dto.DetailItemDto;
import org.yunhongmin.shop.dto.ListItemDto;
import org.yunhongmin.shop.dto.UpdateItemDto;
import org.yunhongmin.shop.mapper.ItemMapper;
import org.yunhongmin.shop.service.ItemService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        List<ListItemDto> listItemDtoList = items.stream()
                .map(ItemMapper.INSTANCE::toListItemDto)
                .collect(Collectors.toList());
        model.addAttribute("items", listItemDtoList);
        return "items/list";
    }

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

    @RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.GET)
    public String updateForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findItem(itemId);
        DetailItemDto detailItemDto = ItemMapper.INSTANCE.toDetailItemDto(item);
        model.addAttribute("item", detailItemDto);
        return "items/updateForm";
    }

    @RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.POST)
    public String update(@PathVariable("itemId") Long itemId, UpdateItemDto updateItemDto) {
        Item item = itemService.findItem(itemId);
        item.setCreatedAt(new Date());
        ItemMapper.INSTANCE.updateItemFromUpdateItemDto(updateItemDto, item);
        itemService.updateItem(item);
        return "redirect:/items";
    }
}
