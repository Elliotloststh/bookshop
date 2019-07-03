package com.yuan.bookshop.service;

import com.yuan.bookshop.Constant.ItemConstant;
import com.yuan.bookshop.dao.ItemMapper;
import com.yuan.bookshop.model.Catalog;
import com.yuan.bookshop.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemMapper itemMapper;

    private static final DecimalFormat df = new DecimalFormat("#.00");

    public int insert(Item record) {
        return itemMapper.insertSelective(record);
    }

    public List<Item> selectRecentPage(int pageIndex, int pageSize) {
        return itemMapper.selectRecentPage((pageIndex-1)*pageSize, pageSize);
    }

    public Item selectByPrimaryKey(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }


    public List<Item> selectByCatalogPage(Catalog catalog, int pageIndex, int pageSize) {
        if(catalog.getLevel() == 2) {
            return itemMapper.selectByCatalogPage(catalog.getId(),(pageIndex-1)*pageSize, pageSize);
        } else {
            return itemMapper.selectByFatherCatalogPage(catalog.getId(),(pageIndex-1)*pageSize, pageSize);
        }

    }

    public List<Item> searchByItemPage(String key, int pageIndex, int pageSize) {
        return itemMapper.searchByItemPage(key, (pageIndex-1)*pageSize, pageSize);
    }

    public List<Item> selectBySellerPage(long sellerId, int pageIndex, int pageSize) {
        return itemMapper.selectBySellerPage(sellerId, (pageIndex-1)*pageSize, pageSize);
    }

    public int countSeller(long sellerId, int pageSize) {
        int total = itemMapper.countSeller(sellerId);
        return total%pageSize==0 ? total/pageSize : total/pageSize+1;
    }

    public int countSearch(String key, int pageSize) {
        int total = itemMapper.countSearch(key);
        return total%pageSize==0 ? total/pageSize : total/pageSize+1;
    }

    public int countPage(int pageSize) {
        int total = itemMapper.countAll();
        return total%pageSize==0 ? total/pageSize : total/pageSize+1;
    }

    public int countCatalog(Catalog catalog, int pageSize) {
        if(catalog.getLevel() == 2) {
            int total = itemMapper.countCatalog(catalog.getId());
            return total%pageSize==0 ? total/pageSize : total/pageSize+1;
        } else {
            int total = itemMapper.countFatherCatalog(catalog.getId());
            return total%pageSize==0 ? total/pageSize : total/pageSize+1;
        }

    }

    public int updateByPrimaryKeySelective(Item record) {
        return itemMapper.updateByPrimaryKeySelective(record);
    }

    public String getProductData(List<Item> items) {
        StringBuilder res = new StringBuilder();
        int count = items.size()%9==0?items.size()/9:items.size()/9+1;
        for(int i=0; i<count; i++) {
            List<Item> itemList = items.subList(i*9,(i+1)*9<=items.size()?(i+1)*9:items.size());
            res.append("<div class=\"mid-popular\">\n");
            for(Item item:itemList) {
                long id = item.getId();
                String idUrl = "?id="+id;
                String cover = item.getCover();
                String title = item.getTitle();
                String originPrice = df.format(item.getOriginPrice());
                String price = df.format(item.getPrice());
                String author = item.getAuthor();
                String template = "<div class=\"col-md-4 item-grid1 simpleCart_shelfItem\">\n" +
                        "\t\t\t\t\t<div class=\" mid-pop\">\n" +
                        "\t\t\t\t\t<div class=\"pro-img\">\n" +
                        "\t\t\t\t\t\t<img src=\""+cover+"\" class=\"img-responsive\" alt=\"\">\n" +
                        "\t\t\t\t\t\t<div class=\"zoom-icon \">\n" +
                        "\t\t\t\t\t\t<a class=\"picture\" href=\""+cover+"\" rel=\"title\" class=\"b-link-stripe b-animate-go  thickbox\"><i class=\"glyphicon glyphicon-search icon \"></i></a>\n" +
                        "\t\t\t\t\t\t<a href=\"single"+idUrl+"\"><i class=\"glyphicon glyphicon-menu-right icon\"></i></a>\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t<div class=\"mid-1\">\n" +
                        "\t\t\t\t\t\t<div class=\"women\">\n" +
                        "\t\t\t\t\t\t<div>\n" +
                        "\t\t\t\t\t\t\t<span>"+author+"</span>\n" +
                        "\t\t\t\t\t\t\t<h6 class=\"h6_hidden\" title=\""+title+"\"><a href=\"single\" >"+title+"</a></h6>\n" +
                        "\t\t\t\t\t\t\t</div>\n" +
                        "\n" +
                        "\t\t\t\t\t\t\t<div class=\"clearfix\"></div>\n" +
                        "\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t<div class=\"mid-2\">\n" +
                        "\t\t\t\t\t\t\t\t<p ><label>￥"+originPrice+"</label><em class=\"item_price\">￥"+price+"</em></p>\n" +
                        "\n" +
                        "\t\t\t\t\t\t\t\t\n" +
                        "\t\t\t\t\t\t\t\t<div class=\"clearfix\"></div>\n" +
                        "\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t</div>";
                res.append(template);
            }
            res.append("<div class=\"clearfix\"></div>\n" +
                    "\t\t\t\t</div>");
        }

        return res.toString();
    }

    public String getIndexData(List<Item> items) {
        StringBuilder res = new StringBuilder();
        int count = items.size()%4==0?items.size()/4:items.size()/4+1;
        for(int i=0; i<count; i++) {
            List<Item> itemList = items.subList(i*4,(i+1)*4<=items.size()?(i+1)*4:items.size());
            res.append("<div class=\"mid-popular\">\n");
            for(Item item:itemList) {
                long id = item.getId();
                String idUrl = "?id="+id;
                String cover = item.getCover();
                String title = item.getTitle();
                String originPrice = df.format(item.getOriginPrice());
                String price = df.format(item.getPrice());
                String author = item.getAuthor();
                String template = "<div class=\"col-md-3 item-grid simpleCart_shelfItem\">\n" +
                        "\t\t\t\t\t<div class=\" mid-pop\">\n" +
                        "\t\t\t\t\t<div class=\"pro-img\">\n" +
                        "\t\t\t\t\t\t<img src=\""+cover+"\" class=\"img-responsive\" alt=\"\">\n" +
                        "\t\t\t\t\t\t<div class=\"zoom-icon \">\n" +
                        "\t\t\t\t\t\t<a class=\"picture\" href=\""+cover+"\" rel=\"title\" class=\"b-link-stripe b-animate-go  thickbox\"><i class=\"glyphicon glyphicon-search icon \"></i></a>\n" +
                        "\t\t\t\t\t\t<a href=\"single"+idUrl+"\"><i class=\"glyphicon glyphicon-menu-right icon\"></i></a>\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t<div class=\"mid-1\">\n" +
                        "\t\t\t\t\t\t<div class=\"women\">\n" +
                        "\t\t\t\t\t\t<div >\n" +
                        "\t\t\t\t\t\t\t<span>"+author+"</span>\n" +
                        "\t\t\t\t\t\t\t<h6 class=\"h6_hidden\" title=\""+title+"\"><a href=\"single"+idUrl+"\">"+title+"</a></h6>\n" +
                        "\t\t\t\t\t\t\t</div>\n" +
                        "\n" +
                        "\t\t\t\t\t\t\t<div class=\"clearfix\"></div>\n" +
                        "\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t<div class=\"mid-2\">\n" +
                        "\t\t\t\t\t\t\t\t<p ><label>￥"+originPrice+"</label><em class=\"item_price\">￥"+price+"</em></p>\n" +
                        "\n" +
                        "\t\t\t\t\t\t\t\t\n" +
                        "\t\t\t\t\t\t\t\t<div class=\"clearfix\"></div>\n" +
                        "\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\n" +
                        "\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t</div>\n";
                res.append(template);
            }
            res.append("<div class=\"clearfix\"></div>\n" +
                    "\t\t\t\t</div>");
        }

        return res.toString();
    }

    public String getShelveData(List<Item> items) {
        StringBuilder res = new StringBuilder();
        for(Item item: items) {
            long item_id = item.getId();
            String href;
            String text;
            if(item.getState()==1) {
                text = "下架";
                href = "putShelves(this,2)";
            } else if(item.getState()==2) {
                text = "上架";
                href = "putShelves(this,1)";
            } else {
                text = "";
                href = "#";
            }
            String template = "<tr class=\"cart-header\">\n" +
                    "\n" +
                    "\t\t\t<td class=\"ring-in\"><a href=\"single?id="+item_id+"\" class=\"at-in\"><img src=\""+item.getCover()+"\" class=\"img-responsive\" alt=\"\"></a>\n" +
                    "\t\t\t<div class=\"sed\">\n" +
                    "\t\t\t\t<h5><a href=\"single?id="+item_id+"\">"+item.getTitle()+"</a></h5>\n" +
                    "\t\t\t\t<p>"+item.getAuthor()+"  著</p>\n" +
                    "\t\t\t\t<p>"+item.getPress()+"</p>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div class=\"clearfix\"> </div>\n" +
                    "\t\t\t<div class=\"close1\" hidden=\"hidden\"> </div>\n" +
                    "\t\t\t</td>\n" +
                    "\t\t\t<td>"+item.getPrice()+"</td>\n" +
                    "\t\t\t<td>"+ ItemConstant.deliveryTypeMap.get(item.getDeliveryType()) +"</td>\n" +
                    "\t\t\t<td class=\"item_price\">"+ItemConstant.stateTypeMap.get(item.getState())+"</td>\n" +
                    "\t\t\t<td ><a class=\"item_add hvr-skew-backward\" id=\"putButton\" onclick=\""+href+"\" data-itemId=\""+item_id+"\">"+text+"</a></td>\n" +
                    "\t\t\t<td ><a class=\"item_add hvr-skew-backward\" id=\"deleteButton\" onclick=\"putShelves(this,3)\" data-itemId=\""+item_id+"\">删除</a></td>\n" +
                    "\t\t  </tr>";
            res.append(template);


        }

        return res.toString();
    }

}
