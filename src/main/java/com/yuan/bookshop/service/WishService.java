package com.yuan.bookshop.service;

import com.yuan.bookshop.dao.WishMapper;
import com.yuan.bookshop.model.Account;
import com.yuan.bookshop.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    @Autowired
    WishMapper wishMapper;

    @Autowired
    UserService userService;

    public Wish seletcByPrimaryKey(Long id) {
        return wishMapper.selectByPrimaryKey(id);
    }

    public int insert(Wish wish) {
        return wishMapper.insertSelective(wish);
    }

    public List<Wish> selectRecentPage(int pageIndex, int pageSize) {
        return wishMapper.selectRecentPage((pageIndex-1)*pageSize, pageSize);
    }

    public int countAll(int pageSize) {
        int total = wishMapper.countAll();
        return total%pageSize==0 ? total/pageSize :total/pageSize + 1;
    }

    public String getWishListData(List<Wish> wishes) {
        StringBuilder res = new StringBuilder();
        for (Wish wish : wishes) {
            long id = wish.getId();
            long userId = wish.getUserId();
            Account account = userService.selectByPrimaryKey(userId);
            String template = "<tr class=\"cart-header\">\n" +
                    "\n" +
                    "\t\t\t<td class=\"ring-in\">\n" +
                    "\t\t\t\t<h5>" + wish.getTitle() + "</h5>\n" +
                    "\t\t\t<div class=\"clearfix\"> </div>\n" +
                    "\t\t\t</td>\n" +
                    "\t\t\t<td class=\"item_price\">" + account.getUsername() + "</td>\n" +
                    "\t\t\t<td class=\"add-check\"><a data-toggle=\"modal\" data-target=\"#myModal2\" data-id=\"" + id + "\" class=\"item_add hvr-skew-backward\" >详情</a></td>\n" +
                    "\t\t\t<td ><a class=\"item_add hvr-skew-backward\" href=\"#\" >联系Ta</a></td>\n" +
                    "\t\t  </tr>";
            res.append(template);
        }
        return res.toString();
    }
}
