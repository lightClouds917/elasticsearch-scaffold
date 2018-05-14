package com.java4all.base;

import java.io.Serializable;


public class ResponseWrapper implements Serializable{

	private static final long serialVersionUID = 1L;


	/**
	 * 成功为true ,其他都为失败编号
	 */
	private Boolean success;
	private Boolean openLoginForm;
	private String message;

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	private String resUrl;
	private Object data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ResponseWrapper markError(String message) {
		ResponseWrapper responseVo = new ResponseWrapper();
		responseVo.success = false;
		responseVo.message = message;
		return responseVo;
	}
	
	public static ResponseWrapper makeOpenLoginForm(String message) {
		ResponseWrapper responseVo = new ResponseWrapper();
		responseVo.openLoginForm = true;
		responseVo.message = message;
		return responseVo;
	}

	public Boolean getOpenLoginForm() {
		return openLoginForm;
	}

	public void setOpenLoginForm(Boolean openLoginForm) {
		this.openLoginForm = openLoginForm;
	}

	public static ResponseWrapper markSuccess(Object data) {
		ResponseWrapper responseVo = new ResponseWrapper();
		responseVo.success = true;
		responseVo.data = data;
		return responseVo;
	}

	public static ResponseWrapper markSuccess(Object data,String resUrl) {
		ResponseWrapper responseVo = new ResponseWrapper();
		responseVo.success = true;
		responseVo.data = data;
		responseVo.resUrl=resUrl;
		return responseVo;
	}

	public static ResponseWrapper markSuccessAndMsg(Object data,String msg) {
		ResponseWrapper responseVo = new ResponseWrapper();
		responseVo.success = true;
		responseVo.data = data;
		responseVo.message = msg;
		return responseVo;
	}


}

