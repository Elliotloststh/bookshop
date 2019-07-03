package com.yuan.bookshop.service;

import com.yuan.bookshop.dao.CatalogMapper;
import com.yuan.bookshop.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    CatalogMapper catalogMapper;

    public List<Catalog> selectByLevel(int level) {
        return catalogMapper.selectByLevel(level);
    }

    public List<Catalog> selectChildCatalog(long father) {
        return catalogMapper.selectChildCatalog(father);
    }

    public Catalog selectbyId(long id) {
        return catalogMapper.selectByPrimaryKey(id);
    }
}
