package com.yuan.bookshop.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    private static final char[] CHARS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'
    };

    public static String getFileType(String fileName) {
        if (fileName != null && fileName.indexOf(".") >= 0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

    /**
     * 判断文件是否为图片文件
     *
     * @param fileName
     * @return
     */
    public static Boolean isImageFile(String fileName) {
        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    public static String curDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /** 随机6位数 */
    public static String randomCode() {
        Integer res = (int)(Math.random()*900000+100000);
        return res+"";
    }

    public static String md5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        return new BigInteger(1,md.digest()).toString(16);
    }

    public static String md5(String username,String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(username.getBytes());
        md.update(password.getBytes());
        return new BigInteger(1,md.digest()).toString(16);
    }

    public static String randomGenerate(int length) {

        List<String> list = new ArrayList<String>(CHARS.length);
        for (int i = 0; i < CHARS.length; i++) {
            list.add(String.valueOf(CHARS[i]));
        }
        Collections.shuffle(list);

        int count = 0;
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.nanoTime());
        while (count < length) {
            int i = random.nextInt(list.size());
            sb.append(list.get(i));
            count++;
        }
        return sb.toString();
    }
}
