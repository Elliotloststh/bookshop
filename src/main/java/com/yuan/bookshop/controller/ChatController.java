package com.yuan.bookshop.controller;

import com.yuan.bookshop.Utils.ApiResponse;
import com.yuan.bookshop.model.Account;
import com.yuan.bookshop.model.Record;
import com.yuan.bookshop.service.RecordService;
import com.yuan.bookshop.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @GetMapping("/getHistory")
    private ApiResponse getHistory() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Long> oppositeIds = recordService.selectAllReceiverId(id);
        List<Long> senderIds = recordService.selectAllSenderId(id);
        oppositeIds.removeAll(senderIds);
        oppositeIds.addAll(senderIds);
        List<People> people = new ArrayList<>();
        oppositeIds.stream().forEach(oid -> {
            Account account = userService.selectByPrimaryKey(oid);
            People people1 = new People();
            people1.setId(oid);
            people1.setName(account.getUsername());
            people1.setAvatar(account.getAvatar());
            people1.setUnReadNumber(recordService.countUnread(id, oid));
            List<Record> records = recordService.selectAllMsg(id, oid);

            if(records.size() == 1 && records.get(0).getReceiverId() == id) {
                return;
            }

            List<Line> lines = new ArrayList<>();
            records.stream().forEach(record -> {
                Line line = new Line();
                if(record.getSenderId()==id) {
                    line.setType(0);
                } else {
                    line.setType(1);
                }
                line.setMsg(record.getContent());
                lines.add(line);
            });
            people1.setPreview(lines.get(lines.size()-1).getMsg());
            people1.setLines(lines);
            people.add(people1);
        });
        ApiResponse response = ApiResponse.ok();
        response.putDataValue("history", people);
        return response;
    }

    @PostMapping("/send")
    public ApiResponse sendMsg(@RequestBody sendInput input) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Record record = new Record();
        record.setSenderId(id);
        record.setReceiverId(input.getOppositeId());
        record.setContent(input.getMsg());
        record.setIsRead(0);
        record.setTime(System.currentTimeMillis()/1000);
        if(recordService.insertSelective(record)==1){
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了");
            return response;
        }

    }

    @PostMapping("/haveRead")
    public ApiResponse haveRead(@RequestBody haveReadInput input) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        recordService.setRead(id, input.getOppositeId());
        return ApiResponse.ok();
    }

    @PostMapping("/connect")
    public ApiResponse connect(@RequestBody connectInput input) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        if(id.equals(input.getOppositeId())) {
            ApiResponse response = ApiResponse.customerError();
            response.putDataValue("msg","不能联系自己哦");
            return response;
        }
        if(recordService.selectAllMsg(id, input.getOppositeId()).size() == 0) {
            Record record = new Record();
            record.setSenderId(id);
            record.setReceiverId(input.getOppositeId());
            record.setTime(System.currentTimeMillis()/1000);
            record.setIsRead(1);
            recordService.insertSelective(record);
        }
        return ApiResponse.ok();
    }


    @Data
    public static class People {
        Long id;
        String avatar;
        String name;
        String preview;
        Integer unReadNumber;
        List<Line> lines;
    }

    @Data
    public static class Line {
        Integer type; //0为自己发，1为对方发
        String msg;
    }


    @Data
    public static class sendInput {
        Long oppositeId;
        String msg;
    }

    @Data
    public static class haveReadInput {
        Long oppositeId;
    }

    @Data
    public static class connectInput {
        Long oppositeId;
    }

}
