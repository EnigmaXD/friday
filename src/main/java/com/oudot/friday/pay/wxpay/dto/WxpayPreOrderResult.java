package com.oudot.friday.pay.wxpay.dto;

import java.util.Map;

/**
 * 预订单接口返回参数
 * 
 * @author xuyinghao
 *
 */
public class WxpayPreOrderResult {

	/**
	 * 返回状态码 SUCCESS/FAIL <br/>
	 * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	private String returnCode;

	/**
	 * 返回信息</br>
	 * 返回信息，如非空，为错误原因
	 */
	private String returnMsg;

	/**
	 * 调用接口提交的应用ID
	 */
	private String appid;

	/**
	 * 调用接口提交的商户号
	 */
	private String mchId;

	/**
	 * 调用接口提交的终端设备号
	 */
	private String deviceInfo;

	/**
	 * 微信返回的随机字符串
	 */
	private String nonceStr;

	/**
	 * 微信返回的签名
	 */
	private String sign;

	/**
	 * 业务结果 SUCCESS/FAIL
	 */
	private String resultCode;

	/**
	 * 错误代码
	 */
	private String errCode;

	/**
	 * 错误代码描述
	 */
	private String errCodeDes;

	/**
	 * 交易类型<br/>
	 * return_code 和result_code都为SUCCESS的时候有返回
	 */
	private String tradeType;

	/**
	 * 预支付交易会话标识<br/>
	 * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时<br/>
	 * return_code 和result_code都为SUCCESS的时候有返回
	 */
	private String prepayId;

	public WxpayPreOrderResult(Map<String, String> params) {
		this.returnCode = params.get("return_code");
		this.returnMsg = params.get("return_msg");
		this.appid = params.get("appid");
		this.mchId = params.get("mch_id");
		this.deviceInfo = params.get("device_info");
		this.nonceStr = params.get("nonce_str");
		this.sign = params.get("sign");
		this.resultCode = params.get("result_code");
		this.errCode = params.get("err_code");
		this.errCodeDes = params.get("err_code_des");
		this.tradeType = params.get("trade_type");
		this.prepayId = params.get("prepay_id");
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

}
