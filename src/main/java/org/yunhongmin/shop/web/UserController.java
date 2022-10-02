package org.yunhongmin.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.dto.JoinUserDto;
import org.yunhongmin.shop.dto.ListUserDto;
import org.yunhongmin.shop.mapper.UserMapper;
import org.yunhongmin.shop.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model) {
        List<ListUserDto> listUserDtos = userService.findUsers().stream()
                .map(UserMapper.INSTANCE::toListUserDto).collect(Collectors.toList());
        model.addAttribute("users", listUserDtos);
        return "users/list";
    }

    @RequestMapping(value = "users/join", method = RequestMethod.GET)
    public String joinForm() {
        return "users/joinForm";
    }

    @RequestMapping(value = "users/join", method = RequestMethod.POST)
    public String join(JoinUserDto joinUserDto) {
        User user = UserMapper.INSTANCE.toUser(joinUserDto);
        userService.join(user);
        return "redirect:/users";
    }
}
