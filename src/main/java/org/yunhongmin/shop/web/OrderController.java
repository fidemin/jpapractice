package org.yunhongmin.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yunhongmin.shop.domain.Item;
import org.yunhongmin.shop.domain.Order;
import org.yunhongmin.shop.domain.OrderSearch;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.dto.ItemIdCountDto;
import org.yunhongmin.shop.dto.ListOrderDto;
import org.yunhongmin.shop.dto.OrderSearchDto;
import org.yunhongmin.shop.mapper.OrderMapper;
import org.yunhongmin.shop.mapper.OrderSearchMapper;
import org.yunhongmin.shop.service.ItemService;
import org.yunhongmin.shop.service.OrderService;
import org.yunhongmin.shop.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String list(OrderSearchDto orderSearchDto, Model model) {
        OrderSearch orderSearch = OrderSearchMapper.INSTANCE.toOrderSearch(orderSearchDto);
        List<Order> orders = orderService.search(orderSearch);

        List<ListOrderDto> listOrderDtos = orders.stream()
                .map(OrderMapper.INSTANCE::toListOrderDto).collect(Collectors.toList());

        model.addAttribute("orders", listOrderDtos);

        return "orders/list";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String orderForm(Model model) {
        List<User> users = userService.findUsers();
        List<Item> items = itemService.findItems();

        model.addAttribute("users", users);
        model.addAttribute("items", items);

        return "orders/order";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(@RequestParam("userId") Long userId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        List<ItemIdCountDto> itemIdCountDtos = new ArrayList<>();
        itemIdCountDtos.add(new ItemIdCountDto(itemId, count));

        orderService.order(userId, itemIdCountDtos);
        return "redirect:/orders";
    }
}
