package com.java4all.service;

import com.java4all.base.PolicyFileEso;

/**
 * Author: momo
 * Date: 2018/5/12
 * Description:
 */
public interface PolicyFileService {

    /**根据id查找*/
    PolicyFileEso getById(String id) throws Exception;

    /**新增*/
    void save(PolicyFileEso policyFileEso) throws Exception;

    /**局部更新*/
    void updateOrAddSomeField(PolicyFileEso policyFileEso) throws Exception;
    
    /**条件删除*/
    String deleteByParams(PolicyFileEso policyFileEso) throws Exception;
    
    
}
