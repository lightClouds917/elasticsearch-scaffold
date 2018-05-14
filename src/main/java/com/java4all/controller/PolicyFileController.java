package com.java4all.controller;

import com.java4all.base.PolicyFileEso;
import com.java4all.base.ResponseWrapper;
import com.java4all.service.PolicyFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: momo
 * Date: 2018/5/12
 * Description:
 */
@RestController
@RequestMapping("policyFile")
public class PolicyFileController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PolicyFileService policyFileService;

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.GET)
    public ResponseWrapper getById(String id){
        try {
            PolicyFileEso policyFileEso = policyFileService.getById(id);
            return ResponseWrapper.markSuccess(policyFileEso);
        }catch (Exception ex){
            logger.info("=======>查询失败",ex);
            return ResponseWrapper.markError("查询失败");
        }
    }

    /**
     * 保存
     * @param policyFileEso
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.GET)
    public ResponseWrapper save(PolicyFileEso policyFileEso){
        try {
            policyFileService.save(policyFileEso);
            return ResponseWrapper.markSuccess("保存成功");
        }catch (Exception ex){
            logger.info("=======>保存失败",ex);
            return ResponseWrapper.markError("保存失败");
        }
    }

    /**
     * 修改
     * @param policyFileEso
     * @return
     */
    @RequestMapping(value = "updateOrAddSomeField",method = RequestMethod.GET)
    public ResponseWrapper updateOrAddSomeField(PolicyFileEso policyFileEso){
        try {
            policyFileService.updateOrAddSomeField(policyFileEso);
            return ResponseWrapper.markSuccess("修改成功");
        }catch (Exception ex){
            logger.info("=======>修改失败",ex);
            return ResponseWrapper.markError("修改失败");
        }
    }

    /**
     * 删除
     * @param policyFileEso
     * @return
     */
    @RequestMapping(value = "deleteByParams",method = RequestMethod.GET)
    public ResponseWrapper deleteByParams(PolicyFileEso policyFileEso){
        try {
            String info = policyFileService.deleteByParams(policyFileEso);
            return ResponseWrapper.markSuccess(info);
        }catch (Exception ex){
            logger.info("=======>删除失败",ex);
            return ResponseWrapper.markError("删除失败");
        }
    }

}
