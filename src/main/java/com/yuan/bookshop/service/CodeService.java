package com.yuan.bookshop.service;

import com.yuan.bookshop.dao.CodeMapper;
import com.yuan.bookshop.model.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeService {
    @Autowired
    private CodeMapper codeMapper;

    public Integer insertOrUpdate(String email, String random, int type) {
        Code code = codeMapper.selectByEmailAndType(email, type);
        if(code == null) {
            return insert(email, random, type);
        } else {
            return update(code.getId(),email, random, type);
        }
    }

    public Integer insert(String email, String random, int type) {
        Code code = new Code();
        code.setEmail(email);
        code.setCode(random);
        code.setType(type);
        code.setCreateTime(System.currentTimeMillis()/1000);
        code.setUpdateTime(System.currentTimeMillis()/1000);
        return codeMapper.insertSelective(code);
    }

    public Integer update(Long id, String email, String random, int type) {
        Code code = new Code();
        code.setId(id);
        code.setCode(random);
        code.setUpdateTime(System.currentTimeMillis()/1000);
        return codeMapper.updateByPrimaryKeySelective(code);
    }

    public Code selectByEmailAndType(String email, int type) {
        return codeMapper.selectByEmailAndType(email, type);
    }

}
