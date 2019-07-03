package com.yuan.bookshop.controller;

import com.yuan.bookshop.Utils.AliyunUtils;
import com.yuan.bookshop.Utils.ApiResponse;
import com.yuan.bookshop.model.Catalog;
import com.yuan.bookshop.model.Item;
import com.yuan.bookshop.service.CatalogService;
import com.yuan.bookshop.service.ItemService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
public class BookController {
    @Autowired
    ItemService itemService;

    @Autowired
    CatalogService catalogService;



    @PostMapping("/shelve")
    public ApiResponse shelveBook(shelveInput input) {
        ApiResponse response = null;
        Item item = new Item();
        try {
            String name = AliyunUtils.uploadImg(input.getCover());
            String url = AliyunUtils.getImgUrl(name,"");
            item.setCover(url);
        } catch (Exception e) {
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "封面上传失败，请重试~");
            return response;
        }


        item.setSellerId(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()));
        item.setIsbn(input.getISBN());
        item.setTitle(input.getTitle());
        item.setAuthor(input.getAuthor());
        item.setCatalogId(input.getSecondCatalog());
        item.setFatherCatalogId(input.getFirstCatalog());
        item.setPress(input.getPress());
        item.setAddress(input.getAddress());
        item.setOriginPrice(input.getOriginPrice());
        item.setPrice(input.getPrice());
        item.setDescription(input.getDescription());
        item.setLink(input.getLink());
        item.setDeliveryType(input.getDeliveryType());
        item.setDictionary(input.getDictionary());
        item.setState(1);//上架状态
        item.setBrief(input.getBrief());
        item.setCreateTime(System.currentTimeMillis()/1000);
        item.setUpdateTime(System.currentTimeMillis()/1000);

        if(itemService.insert(item) > 0){
            response = ApiResponse.ok();
            return response;
        } else {
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
    }

    @GetMapping("/catalog/all")
    public ApiResponse getAllCatalog (){
        ApiResponse response = ApiResponse.ok();
        List<Catalog> catalogs = catalogService.selectByLevel(1);
        List<CatalogInfo> catalogInfos = catalogs.stream().map(CatalogInfo::of).collect(Collectors.toList());
        for(CatalogInfo one: catalogInfos) {
            List<Catalog> children = catalogService.selectChildCatalog(one.getId());
            one.setChildren(children);
        }
        response.putDataValue("catalogs", catalogInfos);
        return response;
    }

    @GetMapping("/catalog/first")
    public ApiResponse getFirstCatalog (){
        ApiResponse response = ApiResponse.ok();
        List<Catalog> catalogs = catalogService.selectByLevel(1);
        response.putDataValue("catalogs", catalogs);
        return response;
    }

    @GetMapping("/catalog/second/{father}")
    public ApiResponse getSecondCatalog (@PathVariable("father") Long father ){
        ApiResponse response = ApiResponse.ok();
        List<Catalog> catalogs = catalogService.selectChildCatalog(father);
        response.putDataValue("catalogs", catalogs);
        return response;
    }

    @PostMapping("/setState")
    public ApiResponse setState(@RequestBody setStateInput input) {
        ApiResponse response;
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Item item = itemService.selectByPrimaryKey(input.getItemId());
        if(!item.getSellerId().equals(id)) {
            response = ApiResponse.forbidden();
            response.putDataValue("msg","你没有权限");
            return response;
        }

        Item record = new Item();
        record.setId(input.getItemId());
        record.setState(input.getState());

        if(itemService.updateByPrimaryKeySelective(record) == 1) {
            return ApiResponse.ok();
        } else {
            response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "服务器开小差了，请重试~");
            return response;
        }
    }

    @Data
    public static class setStateInput {
        private Long itemId;
        private Integer state;
    }

    @Data
    public static class CatalogInfo {
        private Long id;

        private String name;

        private Long fatherId;

        private Integer level;

        private List<Catalog> children;

        public static CatalogInfo of(Catalog catalog) {
            CatalogInfo info = new CatalogInfo();
            info.setId(catalog.getId());
            info.setName(catalog.getName());
            info.setFatherId(catalog.getFatherId());
            info.setLevel(catalog.getLevel());
            return info;
        }
    }


    @Data
    public static class shelveInput {
        private String ISBN;

        private String title;

        private String author;

        private String press;

        private MultipartFile cover;

        private Double originPrice;

        private Double price;

        private String brief;

        private String description;

        private Long secondCatalog;

        private Long firstCatalog;

        private String link;

        private String dictionary;

        private Integer deliveryType;

        private String address;

    }
}
