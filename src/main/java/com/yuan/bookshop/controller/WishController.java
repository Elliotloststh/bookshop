package com.yuan.bookshop.controller;

import com.yuan.bookshop.Utils.ApiResponse;
import com.yuan.bookshop.model.Account;
import com.yuan.bookshop.model.Wish;
import com.yuan.bookshop.service.UserService;
import com.yuan.bookshop.service.WishService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wish")
public class WishController {
    @Autowired
    UserService userService;

    @Autowired
    WishService wishService;

    @GetMapping("/wishInfo/{id}")
    public ApiResponse wishInfo(@PathVariable("id") Long id) {
        Wish wish = wishService.seletcByPrimaryKey(id);
        Account account = userService.selectByPrimaryKey(wish.getUserId());
        ApiResponse response = ApiResponse.ok();
        response.putDataValue("username", account.getUsername());
        response.putDataValue("title", wish.getTitle());
        response.putDataValue("message", wish.getMessage());
        return response;
    }

    @PostMapping("/createWish")
    public ApiResponse createWish(@RequestBody createWishInput input) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Wish wish = new Wish();
        wish.setUserId(id);
        wish.setTitle(input.getTitle());
        wish.setMessage(input.getMessage());
        wish.setCreateTime(System.currentTimeMillis()/1000);
        if(wishService.insert(wish) == 1) {
            return ApiResponse.ok();
        } {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }

    }

    @Data
    public static class createWishInput {
        String title;
        String message;
    }
}
