package org.yunhongmin.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.dto.JoinUserDto;
import org.yunhongmin.shop.mapper.UserMapper;
import org.yunhongmin.shop.service.UserService;

@Controller
public class UserController {
    @Autowired
    UserService userService;

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
