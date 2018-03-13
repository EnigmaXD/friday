package com.friday.pay.alipay.dto;

import java.util.Map;

public class NotifyParams {

    /**
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 买家支付宝账号
     */
    private String buyerLogonId;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 订单金额
     */
    private String totalAmount;

    /**
     * 实收金额
     */
    private String receiptAmount;

    /**
     * 开票金额
     */
    private String invoiceAmount;

    /**
     * 付款金额
     */
    private String buyerPayAmount;

    /**
     * 集分宝金额
     */
    private String pointAmount;

    /**
     * 总退款金额
     */
    private String refundFee;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 交易创建时间
     */
    private String gmtCreate;

    /**
     * 交易付款时间
     */
    private String gmtPayment;

    /**
     * 交易退款时间
     */
    private String gmtRefund;

    /**
     * 交易结束时间
     */
    private String gmtClose;

    /**
     * 支付金额信息
     */
    private String fundBillList;

    /**
     * 回传参数
     */
    private String passbackParams;

    public NotifyParams(Map<String, String> params) {
        this.tradeNo = params.get("trade_no");
        this.outTradeNo = params.get("out_trade_no");
        this.buyerLogonId = params.get("buyer_logon_id");
        this.tradeStatus = params.get("trade_status");
        this.totalAmount = params.get("total_amount");
        this.receiptAmount = params.get("receipt_amount");
        this.invoiceAmount = params.get("invoice_amount");
        this.buyerPayAmount = params.get("buyer_pay_amount");
        this.pointAmount = params.get("point_amount");
        this.refundFee = params.get("refund_fee");
        this.subject = params.get("subject");
        this.body = params.get("body");
        this.gmtCreate = params.get("gmt_create");
        this.gmtPayment = params.get("gmt_payment");
        this.gmtRefund = params.get("gmt_refund");
        this.gmtClose = params.get("gmt_close");
        this.fundBillList = params.get("fund_bill_list");
        this.passbackParams = params.get("passback_params");
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(String buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(String pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(String gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public String getGmtRefund() {
        return gmtRefund;
    }

    public void setGmtRefund(String gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    public String getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(String gmtClose) {
        this.gmtClose = gmtClose;
    }

    public String getFundBillList() {
        return fundBillList;
    }

    public void setFundBillList(String fundBillList) {
        this.fundBillList = fundBillList;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }

}
