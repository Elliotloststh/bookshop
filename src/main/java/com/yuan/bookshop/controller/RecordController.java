package com.yuan.bookshop.controller;

import com.yuan.bookshop.Utils.ApiResponse;
import com.yuan.bookshop.service.RecordService;
import com.yuan.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @GetMapping("/allUnreadNumber")
    public ApiResponse getAllUnreadNumber() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        ApiResponse response = ApiResponse.ok();
        response.putDataValue("number", recordService.countAllUnread(id));
        return response;
    }

}
