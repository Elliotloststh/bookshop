package com.yuan.bookshop.controller;

import com.yuan.bookshop.Constant.ItemConstant;
import com.yuan.bookshop.Constant.PageConstant;
import com.yuan.bookshop.model.*;
import com.yuan.bookshop.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class WebController {

    private Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    OrderService orderService;

    @Autowired
    WishService wishService;

//    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest request) {
        if(request.getParameter("login")==null) {
            map.put("flag", "false");
        } else {
            map.put("flag", "true");
        }

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));

        List<Item> items = itemService.selectRecentPage(pageIndex, PageConstant.indexPageSize);
        String data = itemService.getIndexData(items);
        map.put("data", data);

        int totalPage = itemService.countPage(PageConstant.indexPageSize);
        if(totalPage==0) totalPage=1;
        map.put("lastIndex", pageIndex-1<1? 1 : pageIndex-1);
        map.put("nextIndex", pageIndex+1>totalPage ? totalPage : pageIndex+1);
        map.put("endIndex", totalPage);
        return "index";
    }

    @RequestMapping("/login")
    public String login(ModelMap map) {
        map.put("flag", "false");
        map.put("flag2", "false");
        map.put("msg", "");
        return "login";
    }

    @RequestMapping("/login/error")
    public String loginError(ModelMap map) {
        map.put("flag", "true");
        map.put("flag2", "false");
        map.put("msg", "账号或密码错误");
        return "login";
    }

    @RequestMapping("/login/success")
    public String loginSuccess(ModelMap map) {
        map.put("flag", "false");
        map.put("flag2", "true");
        map.put("msg", "");
        return "login";
    }

    @RequestMapping("/login/timeout")
    public String loginTimeout(ModelMap map) {
        map.put("flag", "true");
        map.put("flag2", "false");
        map.put("msg", "session失效，请重新登录");
        return "index";
    }

    @RequestMapping("/register")
    public String register(ModelMap map) {
        return "register";
    }

    @RequestMapping("/404")
    public String error404(ModelMap map) {
        return "404";
    }

    @RequestMapping("/checkout")
    public String checkout(ModelMap map,HttpServletRequest request) {
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Order> orders = orderService.selectByBuyerPage(id, pageIndex, PageConstant.checkoutPageSize);
        int totalPage = orderService.countBuyer(id, PageConstant.checkoutPageSize);
        if(totalPage==0) totalPage=1;
        String data = orderService.getCheckoutData(orders);
        map.put("data", data);
        map.put("lastIndex", pageIndex-1<1? 1 : pageIndex-1);
        map.put("nextIndex", pageIndex+1>totalPage ? totalPage : pageIndex+1);
        map.put("endIndex", totalPage);
        return "checkout";
    }

    @RequestMapping("/contact")
    public String contact(ModelMap map) {
        return "contact";
    }

    @RequestMapping("/product")
    public String product(ModelMap map, HttpServletRequest request) {
        long catalogId = request.getParameter("catalog_id")==null?0:Integer.parseInt(request.getParameter("catalog_id"));
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));
        List<Item> items = new ArrayList<>();
        String catalogUrl;
        int totalPage = 0;
        String catalogName = "";
        if(catalogId == 0) {
            items = itemService.selectRecentPage(pageIndex, PageConstant.productPageSize);
            totalPage = itemService.countPage(PageConstant.productPageSize);
            catalogUrl = "";
        } else {
            Catalog catalog = catalogService.selectbyId(catalogId);
            if(catalog != null) {
                items = itemService.selectByCatalogPage(catalog, pageIndex, PageConstant.productPageSize);
                totalPage = itemService.countCatalog(catalog, PageConstant.productPageSize);
                catalogName = catalog.getName();
            }
            catalogUrl = "&catalog_id="+catalogId;

        }

        if(totalPage==0) totalPage=1;

        String data = itemService.getProductData(items);
        map.put("data", data);
        map.put("catalogName",catalogName!=""?catalogName:"书城");

        map.put("firstUrl","/product?pageIndex=1" + catalogUrl);
        map.put("lastUrl", "/product?pageIndex="+ (pageIndex-1<1? 1 : pageIndex-1)+catalogUrl);
        map.put("nextUrl", "/product?pageIndex="+ (pageIndex+1>totalPage ? totalPage : pageIndex+1)+catalogUrl);
        map.put("endUrl", "/product?pageIndex="+ totalPage + catalogUrl);

        return "product";
    }

    @RequestMapping("/single")
    public String single(ModelMap map, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        Item item = itemService.selectByPrimaryKey(id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Account seller = userService.selectByPrimaryKey(item.getSellerId());
        map.put("cover", item.getCover());
        map.put("title",item.getTitle());
        map.put("authorAndPress",item.getAuthor()+" 著        " + item.getPress());
        map.put("brief", item.getBrief());
        map.put("sellerId", seller.getId());
        map.put("sellerName", seller.getUsername());
        String introduction = "书名：《"+item.getTitle()+"》"+
                "\n作者："+item.getAuthor()+ "        ISBN："+item.getIsbn() +
                "\n出版社："+item.getPress()+ "        类别："+catalogService.selectbyId(item.getFatherCatalogId()).getName()+" » "+catalogService.selectbyId(item.getCatalogId()).getName() +
                "\n原价："+item.getOriginPrice()+"        现价："+item.getPrice()+"        交易方式：" + ItemConstant.deliveryTypeMap.get(item.getDeliveryType()) +
                "\n卖家："+seller.getUsername()+"        上架时间："+simpleDateFormat.format(new Date(item.getCreateTime()*1000))+
                "\n";
        if(item.getAddress()!=null) {
            introduction = introduction+"发布地点："+item.getAddress();
        }
        map.put("deliveryType",ItemConstant.deliveryTypeMap.get(item.getDeliveryType()));
        map.put("price", item.getPrice());
        map.put("introduction", introduction);
        map.put("detail", item.getDescription());
        map.put("dictionary", item.getDictionary());
        map.put("itemId", item.getId());
        map.put("deleted", item.getState()==1?"false":"true");
        return "single";
    }

    @RequestMapping("/typo")
    public String typo(ModelMap map) {
        return "typo";
    }

    @RequestMapping("/wishlist")
    public String wishlist(ModelMap map, HttpServletRequest request) {
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Wish> wishes = wishService.selectRecentPage(pageIndex, 10);
        int totalPage = wishService.countAll(10);
        if(totalPage==0) totalPage=1;
        String data = wishService.getWishListData(wishes);
        map.put("data", data);
        map.put("lastIndex", pageIndex-1<1? 1 : pageIndex-1);
        map.put("nextIndex", pageIndex+1>totalPage ? totalPage : pageIndex+1);
        map.put("endIndex", totalPage);
        return "wishlist";
    }

    @RequestMapping("/retrieve")
    public String retrieve(ModelMap map) {
        return "retrieve";
    }

    @RequestMapping("/changePassword")
    public String changePassword(ModelMap map) {
        return "changePassword";
    }

    @RequestMapping("/profile")
    public String profile(ModelMap map) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Account account = userService.selectByPrimaryKey(id);

        map.put("username", account.getUsername());
        map.put("email", account.getEmail());
        Integer gender = account.getGender();
        String genderMsg;
        if(gender==null) {
            genderMsg = "<option value=\"1\">男</option>\n" +
                    "                            <option value=\"2\">女</option>";
            map.put("gender", genderMsg);
        } else if(gender==2) {
            genderMsg = "<option value=\"1\">男</option>\n" +
                    "                            <option value=\"2\" selected=\"selected\">女</option>";
            map.put("gender", genderMsg);
        } else {
            genderMsg = "<option value=\"1\" selected=\"selected\">男</option>\n" +
                    "                            <option value=\"2\">女</option>";
            map.put("gender", genderMsg);
        }

        map.put("phone", account.getPhoneNumber());
        map.put("description", account.getDescription()==null?"":account.getDescription());
        map.put("address", account.getAddress()==null?"":account.getAddress());
        return "profile";
    }

    @RequestMapping("/shelveBook")
    public String shelveBook(ModelMap map, HttpServletRequest request) {
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Item> items = itemService.selectBySellerPage(id, pageIndex, PageConstant.shelvePageSize);
        int totalPage = itemService.countSeller(id, PageConstant.shelvePageSize);
        if(totalPage==0) totalPage=1;
        String data = itemService.getShelveData(items);

        map.put("data", data);
        map.put("lastIndex", pageIndex-1<1? 1 : pageIndex-1);
        map.put("nextIndex", pageIndex+1>totalPage ? totalPage : pageIndex+1);
        map.put("endIndex", totalPage);
        return "shelveBook";
    }

    @RequestMapping("/store")
    public String store(ModelMap map, HttpServletRequest request) {
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Order> orders = orderService.selectBySellerPage(id, pageIndex, PageConstant.checkoutPageSize);
        int totalPage = orderService.countSeller(id, PageConstant.checkoutPageSize);
        if(totalPage==0) totalPage=1;
        String data = orderService.getStoreData(orders);
        map.put("data", data);
        map.put("lastIndex", pageIndex-1<1? 1 : pageIndex-1);
        map.put("nextIndex", pageIndex+1>totalPage ? totalPage : pageIndex+1);
        map.put("endIndex", totalPage);
        return "store";
    }

    @RequestMapping("/chat")
    public String chat(ModelMap map , HttpServletRequest request) {


        return "chat";
    }

    @RequestMapping("/search")
    public String search(ModelMap map, HttpServletRequest request) {
        String key = request.getParameter("key");
        if(key == null) {
            return "404";
        }
        int pageIndex = request.getParameter("pageIndex")==null?1:Integer.parseInt(request.getParameter("pageIndex"));
        List<Item> items = itemService.searchByItemPage(key, pageIndex, PageConstant.searchPageSize);
        String data = itemService.getIndexData(items);
        map.put("data", data);

        int totalPage = itemService.countSearch(key, PageConstant.indexPageSize);
        if(totalPage==0) totalPage=1;
        map.put("firstUrl", "/search?key="+key);
        map.put("lastUrl", "/search?key="+key+"&pageIndex=" + (pageIndex-1<1? 1 : pageIndex-1));
        map.put("nextUrl", "/search?key="+key+"&pageIndex=" + (pageIndex+1>totalPage ? totalPage : pageIndex+1));
        map.put("endUrl", "/search?key="+key+"&pageIndex=" + totalPage);

        return "search";
    }
}
