package com.yuan.bookshop.Utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//邮件发送工具
@Component
public class SendEmailUtils {

    private JavaMailSender javaMailSender = getJavaMailSender();

    /**
     * 发送邮件
     * @param title   标题
     * @param titleWithName  是否在标题后添加 名称
     * @param content        内容
     * @param contentWithName   是否在内容后添加 名称
     * @param email     接收者的邮箱【注册人】
     */
    private void sendNormalEmail(
            String title,boolean titleWithName,
            String content, boolean contentWithName, String email ){
        String dName="BookPan";
        MimeMessage mimeMessage=null;
        try {
            if(contentWithName) {
                content += "<p style='text-align:right'>" + dName+ "</p>";
                content += "<p style='text-align:right'>" + CommonUtils.curDate("yyyy-MM-dd HH:mm:ss")+ "</p>";
            }
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"utf-8");
            helper.setFrom("17367078496@163.com"); //设置 谁发送的
            helper.setTo(email);//发给谁 【接收者的邮箱】
            title= titleWithName?title +"-"+dName:title; //标题内容
            helper.setSubject(title);//发送邮件的辩题
            helper.setText(content,true);//发送的内容 是否为html
        }catch (Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }


    /**
     * 发送 注册时的验证码
     * @param email   接收者的邮箱【注册人】
     * @param code    验证码
     */
    public void sendRegisterCode(final String email, String code){
        final StringBuffer sb= new StringBuffer();//实例化一个StringBuffer
        sb.append("<h2>").append(email).append(",您好！<h2>").append("<p style='color:red'>此次注册的验证码是：").append(code).append("</p>");
        new Thread(() -> sendNormalEmail("验证码", false, sb.toString(), false, email)).start();
    }

    /**
     * 发送 找回密码时的验证码
     * @param email   接收者的邮箱【注册人】
     * @param code    验证码
     */
    public void sendRetrieveCode(final String email, String code){
        final StringBuffer sb= new StringBuffer();//实例化一个StringBuffer
        sb.append("<h2>").append(email).append(",您好！<h2>").append("<p style='color:red'>找回密码的验证码是：").append(code).append("</p>");
        new Thread(() -> sendNormalEmail("验证码", false, sb.toString(), false, email)).start();
    }


    /**
     * 注册成功时的提示邮件
     * @param email 接收的邮箱地址 【注册人】
     * @param pwd 初始密码
     * @param url 登陆地址
     */
    public void sendRegisterSuc(final String email, String pwd, String url) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<h3>恭喜您，注册成功！</h3>")
                .append("<h2>初始化密码是：<b style='color:#F00'>").append(pwd).append("</b>，请不要告诉任何人！</h2>")
                .append("请及时<a href='").append(url).append("'>登陆网站</a>修改密码。");
        new Thread(() -> sendNormalEmail("注册成功", false, sb.toString(), false, email)).start();
    }

    /**
     * 密码找回时的提示邮件
     * @param email 接收的邮箱地址  【注册人】
     * @param pwd 初始密码
     * @param url 登陆地址
     */
    public void sendFindPwdSuc(final String email, String pwd, String url) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<h3>恭喜您，密码找回成功！</h3>")
                .append("<h2>系统随机密码是：<b style='color:#F00'>").append(pwd).append("</b>，请不要告诉任何人！</h2>")
                .append("请及时<a href='").append(url).append("'>登陆网站</a>修改密码。");

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendNormalEmail("成功找回密码", false, sb.toString(), false, email);
            }
        }).start();
    }

    /**
     * 新用户注册通过
     * @param email 接收邮箱地址（管理员的）
     * @param nickname 注册人姓名
     * @param regEmail 注册人邮箱
     * @param url 地址
     */
    public void sendOnRegister(final String email, String nickname, String regEmail, String url) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<a href='").append(url).append("'><h1>姓名：").append(nickname).append("</h1></a>");
        sb.append("<h3>注册邮箱：").append(regEmail).append("</h3>");
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendNormalEmail("新用户注册通知", true, sb.toString(), true, email);
            }
        }).start();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.163.com");
        mailSender.setPort(25);

        mailSender.setUsername("17367078496@163.com");
        mailSender.setPassword("123456BS");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}

