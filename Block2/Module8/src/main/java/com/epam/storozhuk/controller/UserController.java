package com.epam.storozhuk.controller;

import com.epam.storozhuk.dto.UserDto;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUserById(@PathVariable long id, Model model) {
        model.addAttribute("returnUser", userService.getUserById(id));
        return "/user/user";
    }

    @PutMapping(value = "/create")
    public ModelAndView createUser(@RequestBody UserDto userDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        User toCreateUser = User.builder()
                .username(userDto.getUsername())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .createDate(currentTime)
                .updateDate(currentTime)
                .build();
        User returnUser = userService.createUser(toCreateUser);
        return new ModelAndView("redirect:/user/" + returnUser.getId());
    }
}
