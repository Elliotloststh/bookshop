package com.yuan.bookshop.controller;

import com.yuan.bookshop.Constant.ValidationCode;
import com.yuan.bookshop.Utils.AliyunUtils;
import com.yuan.bookshop.Utils.ApiResponse;
import com.yuan.bookshop.Utils.CommonUtils;
import com.yuan.bookshop.Utils.SendEmailUtils;
import com.yuan.bookshop.model.Account;
import com.yuan.bookshop.model.Code;
import com.yuan.bookshop.service.CodeService;
import com.yuan.bookshop.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    CodeService codeService;

    @Autowired
    UserService userService;

    @PostMapping("/public/registerCode")
    public ApiResponse registerCode(@RequestBody codeInput input) {
        String code = CommonUtils.randomCode();

        if(codeService.insertOrUpdate(input.getEmail(), code, ValidationCode.REGISTER.getValue()) > 0) {
            SendEmailUtils sendEmailUtils = new SendEmailUtils();
            sendEmailUtils.sendRegisterCode(input.getEmail(), code);
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
    }

    @PostMapping("/public/register")
    public ApiResponse register(@RequestBody registerInput input) {
        ApiResponse response = null;

        if(userService.selectByName(input.getUsername()) != null) {
            response = ApiResponse.customerError();
            response.putDataValue("msg", "用户名已存在");
            return response;
        }

        if(userService.selectByEmail(input.getEmail()) != null) {
            response = ApiResponse.customerError();
            response.putDataValue("msg", "邮箱已被注册");
            return response;
        }

        Code code = codeService.selectByEmailAndType(input.getEmail(), ValidationCode.REGISTER.getValue());
        if(code == null || !code.getCode().equals(input.getCode())) {
            response = ApiResponse.customerError();
            response.putDataValue("msg", "验证码错误");
            return response;
        }

        Account account = new Account();
        account.setUsername(input.getUsername());
        account.setPhoneNumber(input.getPhone());
        account.setEmail(input.getEmail());
        account.setAvatar("https://jhcdn.oss-cn-hangzhou.aliyuncs.com/BS/default-avatar.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_500/quality,q_90/circle,r_250");
        try {
            account.setPassword(CommonUtils.md5(input.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
        int result = userService.insertSelective(account);
        if(result == 0) {
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        } else {
            return ApiResponse.ok();
        }
    }

    @PostMapping("/public/retrieveCode")
    public ApiResponse retrieveCode(@RequestBody codeInput input) {
        String code = CommonUtils.randomCode();

        if(userService.selectByEmail(input.getEmail()) == null) {
            ApiResponse response = ApiResponse.customerError();
            response.putDataValue("msg", "邮箱未注册");
            return response;
        }

        if(codeService.insertOrUpdate(input.getEmail(), code, ValidationCode.RETRIEVE.getValue()) > 0) {
            SendEmailUtils sendEmailUtils = new SendEmailUtils();
            sendEmailUtils.sendRetrieveCode(input.getEmail(), code);
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
    }

    @PostMapping("/public/retrieve")
    public ApiResponse retrieve(@RequestBody retrieveInput input) {
        ApiResponse response = null;

        Code code = codeService.selectByEmailAndType(input.getEmail(), ValidationCode.RETRIEVE.getValue());
        if(code == null || !code.getCode().equals(input.getCode())) {
            response = ApiResponse.customerError();
            response.putDataValue("msg", "验证码错误");
            return response;
        }

        Account origin = userService.selectByEmail(input.getEmail());
        Account account = new Account();
        account.setId(origin.getId());
        try {
            account.setPassword(CommonUtils.md5(input.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
        int result = userService.updateByPrimaryKeySelective(account);
        if(result == 0) {
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        } else {
            return ApiResponse.ok();
        }
    }

    @GetMapping("/private/getUsername")
    public ApiResponse getUsername() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = userService.selectByPrimaryKey(Long.valueOf(id));
        ApiResponse response = ApiResponse.ok();
        response.putDataValue("username", account.getUsername());
        return response;
    }

    @GetMapping("/private/getAvatar")
    public ApiResponse getAvatar() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = userService.selectByPrimaryKey(Long.valueOf(id));
        ApiResponse response = ApiResponse.ok();
        response.putDataValue("avatar", account.getAvatar());
        return response;
    }

    @GetMapping("/private/getAddress")
    public ApiResponse getAddress() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = userService.selectByPrimaryKey(Long.valueOf(id));
        ApiResponse response = ApiResponse.ok();
        response.putDataValue("address", account.getAddress());
        return response;
    }

    @PostMapping("/private/changePassword")
    public ApiResponse changePassword(@RequestBody changerPwdInput input ) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = userService.selectByPrimaryKey(Long.valueOf(id));
        ApiResponse response;
        String origin;
        String password;
        try {
            origin = CommonUtils.md5(input.getOrigin());
            password = CommonUtils.md5(input.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
        if(!origin.equals(account.getPassword())) {
            response = ApiResponse.customerError();
            response.putDataValue("msg", "原密码错误");
            return response;
        }

        account.setPassword(password);
        userService.updateByPrimaryKeySelective(account);
        response = ApiResponse.ok();
        return response;
    }
    
    @PostMapping("/private/changeAvatar")
    public ApiResponse changeAvatar (@RequestParam("avatar") MultipartFile avatar) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = userService.selectByPrimaryKey(Long.valueOf(id));
        ApiResponse response = null;
        try {
            String name = AliyunUtils.uploadImg(avatar);
            String url = AliyunUtils.getImgUrl(name, AliyunUtils.styleAvatar);
            account.setAvatar(url);
            userService.updateByPrimaryKeySelective(account);
            response = ApiResponse.ok();
            return response;
        } catch (Exception e) {
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
    }

    @PostMapping("/private/update")
    public ApiResponse update(@RequestBody updateInput input) {
        long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Account account = new Account();
        account.setId(id);
        account.setGender(input.getGender());
        account.setPhoneNumber(input.getPhone());
        account.setDescription(input.getDescription());
        account.setAddress(input.getAddress());

        if(userService.updateByPrimaryKeySelective(account) == 1) {
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
    }

    @Data
    public static class codeInput {
        private String email;
    }

    @Data
    public static class updateInput {
        private Integer gender;
        private String phone;
        private String description;
        private String address;
    }

    @Data
    public static class registerInput {
        private String username;
        private String phone;
        private String email;
        private String password;
        private String code;
    }

    @Data
    public static class retrieveInput {
        private String email;
        private String password;
        private String code;
    }

    @Data
    public static class changerPwdInput {
        private String origin;
        private String password;
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(89));
//        SendEmailUtils sendEmailUtils = new SendEmailUtils();
//        sendEmailUtils.sendRegisterCode("3160102443@zju.edu.cn", "test");
    }
}


