package com.ldz.biz.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProInfoLuckNumBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//商品ID
	private String proId;
	//商品名称
	private String proName;
	//中奖号码
	private String luckNum;
}
