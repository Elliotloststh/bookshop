package com.yuan.bookshop.service;

import com.yuan.bookshop.Constant.OrderConstant;
import com.yuan.bookshop.dao.OrderMapper;
import com.yuan.bookshop.model.Item;
import com.yuan.bookshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ItemService itemService;

    public int insert(Order record) {
        return orderMapper.insertSelective(record);
    }

    public String getCheckoutData(List<Order> orders) {
        StringBuilder res = new StringBuilder();
        for(Order order: orders) {
            long order_id = order.getId();
            long item_id = order.getItemId();
            Item item = itemService.selectByPrimaryKey(item_id);
            String hiddenStr = order.getState()!=1?"style=\"display: none\"":"";
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
            "\t\t\t<td>"+order.getPayment()+"</td>\n" +
            "\t\t\t<td>"+ OrderConstant.paymentTypeMap.get(order.getPaymentType()) +"</td>\n" +
            "\t\t\t<td class=\"item_price\">"+OrderConstant.stateTypeMap.get(order.getState())+"</td>\n" +
            "\t\t\t<td class=\"add-check\"><a data-toggle=\"modal\" data-target=\"#myModal\" data-orderId=\""+order_id+"\" class=\"item_add hvr-skew-backward\" >详情</a></td>\n" +
            "\t\t\t<td ><a class=\"item_add hvr-skew-backward\" "+hiddenStr+" onclick=\"confirmReceive(this)\" data-orderId=\""+order_id+"\">确认收货</a></td>\n" +
            "\t\t  </tr>";
            res.append(template);


        }

        return res.toString();
    }

    public String getStoreData(List<Order> orders) {
        StringBuilder res = new StringBuilder();
        for(Order order: orders) {
            long order_id = order.getId();
            long item_id = order.getItemId();
            Item item = itemService.selectByPrimaryKey(item_id);
            String hiddenStr = order.getState()!=0?"style=\"display: none\"":"";
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
                    "\t\t\t<td>"+order.getPayment()+"</td>\n" +
                    "\t\t\t<td>"+ OrderConstant.paymentTypeMap.get(order.getPaymentType()) +"</td>\n" +
                    "\t\t\t<td class=\"item_price\">"+OrderConstant.stateTypeMap.get(order.getState())+"</td>\n" +
                    "\t\t\t<td class=\"add-check\"><a data-toggle=\"modal\" data-target=\"#myModal\" data-orderId=\""+order_id+"\" class=\"item_add hvr-skew-backward\" >详情</a></td>\n" +
                    "\t\t\t<td ><a class=\"item_add hvr-skew-backward\" "+hiddenStr+" data-toggle=\"modal\" data-target=\"#myModal2\" data-orderId=\""+order_id+"\">发货</a></td>\n" +
                    "\t\t  </tr>";
            res.append(template);


        }

        return res.toString();
    }

    public List<Order> selectByBuyerPage(Long buyerId, int pageIndex, int pageSize) {
        return orderMapper.selectByBuyerPage(buyerId, (pageIndex-1)*pageSize, pageSize);
    }

    public int countBuyer(Long buyerId, int pageSize) {
        int total = orderMapper.countBuyer(buyerId);
        return total%pageSize==0 ? total/pageSize : total/pageSize+1;
    }

    public List<Order> selectBySellerPage(Long sellerId, int pageIndex, int pageSize) {
        return orderMapper.selectBySellerPage(sellerId, (pageIndex-1)*pageSize, pageSize);
    }

    public int countSeller(Long sellerId, int pageSize) {
        int total = orderMapper.countSeller(sellerId);
        return total%pageSize==0 ? total/pageSize : total/pageSize+1;
    }

    public Order selectByPrimaryKey(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }
}
