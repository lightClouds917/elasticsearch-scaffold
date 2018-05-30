package com.java4all.base;

import java.util.List;
import java.util.Map;

/**
 * Author: momo
 * Date: 2018/5/12
 * Description:
 */
public class PolicyFileEso {

    private String id;

    /**姓名*/
    private String name;

    /**年龄*/
    private Integer age;

    /**地址*/
    private String address;

    /**省份信息*/
    private List<Map<String,Object>> provinceNumInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Map<String, Object>> getProvinceNumInfo() {
        return provinceNumInfo;
    }

    public void setProvinceNumInfo(List<Map<String, Object>> provinceNumInfo) {
        this.provinceNumInfo = provinceNumInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
