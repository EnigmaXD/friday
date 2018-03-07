package com.friday.pay.alipay.util;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.friday.pay.alipay.constant.AlipayConsts;

/**
 * 支付宝H5支付工具类 <br>
 * Created on 2018-3-7
 * 
 * @see <a href="https://docs.open.alipay.com/203/105288/">官方文档</a>
 * @author xuyinghao
 *
 */
public class AlipayUtils {

    private AlipayClient alipayClient;
    private String returnUrl;
    private String notifyUrl;

    public static class Builder {

        private String serverUrl = AlipayConsts.ServerUrl.PRO.getValue();
        private String appId;
        private String privateKey;
        private String alipayPublicKey;
        private String returnUrl;
        private String notifyUrl;
        private String signType = AlipayConsts.SignType.RSA2.getValue();

        /**
         * 设置应用网关，默认生产环境
         * 
         * @see com.shishang.plugin.alipay.constant.AlipayConsts.ServerUrl
         */
        public Builder serverUrl(AlipayConsts.ServerUrl serverUrlEnum) {
            this.serverUrl = serverUrlEnum.getValue();
            return this;
        }

        /**
         * 设置APPID，必填
         */
        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        /**
         * 设置应用私钥，必填
         */
        public Builder privateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        /**
         * 设置支付宝，必填
         */
        public Builder alipayPublicKey(String alipayPublicKey) {
            this.alipayPublicKey = alipayPublicKey;
            return this;
        }

        /**
         * 设置页面返回地址，必填
         */
        public Builder returnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
            return this;
        }

        /**
         * 设置回调地址，必填
         */
        public Builder notifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
            return this;
        }

        /**
         * 设置加密方式，默认RSA2
         * 
         * @see com.shishang.plugin.alipay.constant.AlipayConsts.SignType
         */

        public Builder signType(AlipayConsts.SignType signTypeEnum) {
            this.signType = signTypeEnum.getValue();
            return this;
        }

        public AlipayUtils builder() {
            return new AlipayUtils(this);
        }

    }

    private AlipayUtils(Builder builder) {

        this.returnUrl = builder.returnUrl;
        this.notifyUrl = builder.notifyUrl;
        this.alipayClient = new DefaultAlipayClient(builder.serverUrl, builder.appId, builder.privateKey, "json",
                "UTF-8", builder.alipayPublicKey, builder.signType);
    }

    /**
     * 系统调用方式
     * 
     * @param outTradeNo
     *            业务订单号
     * @param totalAmount
     *            订单总金额
     * @param subject
     *            交易标题
     * @param httpResponse
     * @throws Exception
     */
    public void doPost(String outTradeNo, String totalAmount, String subject, HttpServletResponse httpResponse)
            throws Exception {
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);// 在公共参数中设置回跳和通知地址
        JSONObject content = new JSONObject();
        content.put(AlipayConsts.OUT_TRADE_NO_KEY, outTradeNo);
        content.put(AlipayConsts.TOTAL_AMOUNT_KEY, totalAmount);
        content.put(AlipayConsts.SUBJECT_KEY, subject);
        content.put(AlipayConsts.PRODUCT_CODE_KEY, AlipayConsts.PRODUCT_CODE_QUICK_WAP_WAY);
        alipayRequest.setBizContent(JSONObject.toJSONString(content));// 填充业务参数

        String form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
        httpResponse.setContentType("text/html;charset=" + "UTF8");
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
    }

    /**
     * 页面跳转方式
     * 
     * @param outTradeNo
     *            业务订单号
     * @param totalAmount
     *            订单总金额
     * @param subject
     *            交易标题
     * @return 支付链接，前端页面直接跳转
     * @throws Exception
     */
    public String getUrl(String outTradeNo, String totalAmount, String subject) throws Exception {

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);// 在公共参数中设置回跳和通知地址
        JSONObject content = new JSONObject();
        content.put(AlipayConsts.OUT_TRADE_NO_KEY, outTradeNo);
        content.put(AlipayConsts.TOTAL_AMOUNT_KEY, totalAmount);
        content.put(AlipayConsts.SUBJECT_KEY, subject);
        content.put(AlipayConsts.PRODUCT_CODE_KEY, AlipayConsts.PRODUCT_CODE_QUICK_WAP_WAY);
        alipayRequest.setBizContent(JSONObject.toJSONString(content));// 填充业务参数
        String form = alipayClient.pageExecute(alipayRequest, "GET").getBody();

        return form;
    }

}
