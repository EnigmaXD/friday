package com.friday.pay.wxpay.dto;

import java.util.Map;

/**
 * 微信支付回调参数
 * 
 * @author xuyinghao
 *
 */
public class WxpayNotifyParams {

    /**
     * 返回状态码 SUCCESS/FAIL <br/>
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String returnCode;

    /**
     * 返回信息<br/>
     * 返回信息，如非空，为错误原因
     */
    private String returnMsg;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 业务结果 SUCCESS/FAIL
     */
    private String resultCode;

    /**
     * 错误代码 错误返回的信息描述
     */
    private String errCode;

    /**
     * 错误代码描述 错误返回的信息描述
     */
    private String errCodeDes;

    /**
     * 用户标识 用户在商户appid下的唯一标识
     */
    private String openid;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 总金额 订单总金额，单位为分
     */
    private int totalFee;

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 商户订单号 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String outTradeNo;

    /**
     * 商家数据包 商家数据包，原样返回
     */
    private String attach;

    /**
     * 支付完成时间 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     */
    private String timeEnd;

    public WxpayNotifyParams(Map<String, String> params) {
        this.returnCode = params.get("return_code");
        this.returnMsg = params.get("return_msg");
        this.appid = params.get("appid");
        this.mchId = params.get("mch_id");
        this.deviceInfo = params.get("device_info");
        this.resultCode = params.get("result_code");
        this.errCode = params.get("err_code");
        this.errCodeDes = params.get("err_code_des");
        this.openid = params.get("openid");
        this.tradeType = params.get("trade_type");
        this.totalFee = params.containsKey("total_fee") ? 0 : Integer.valueOf(params.get("total_fee"));
        this.transactionId = params.get("transaction_id");
        this.outTradeNo = params.get("out_trade_no");
        this.attach = params.get("attach");
        this.timeEnd = params.get("time_end");

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
