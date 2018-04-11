package com.friday.pay.wxpay.dto;

import java.util.HashMap;
import java.util.Map;

import com.github.wxpay.sdk.WXPayUtil;

/**
 * APP调起SDK参数
 * 
 * @author xuyinghao
 *
 */
public class WxpaySDKParams {

	private String appid;
	private String partnerid;
	private String prepayid;
	private String pack;
	private String noncestr;
	private String timestamp;
	private String sign;

	public WxpaySDKParams() {

	}

	public WxpaySDKParams(WxpayPreOrderResult result, String key) throws Exception {
		this.appid = result.getAppid();
		this.partnerid = result.getMchId();
		this.prepayid = result.getPrepayId();
		this.pack = "Sign=WXPay";
		this.noncestr = WXPayUtil.generateNonceStr();

		// 生成10位时间戳
		Long currentTimeMillis = System.currentTimeMillis();
		long second = currentTimeMillis / 1000L;
		String timestamp = String.valueOf(second).substring(0, 10);
		this.timestamp = timestamp;

		Map<String, String> data = new HashMap<>();
		data.put("appid", this.appid);
		data.put("partnerid", this.partnerid);
		data.put("prepayid", this.prepayid);
		data.put("package", this.pack);
		data.put("noncestr", this.noncestr);
		data.put("timestamp", this.timestamp);
		this.sign = WXPayUtil.generateSignature(data, key);
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
