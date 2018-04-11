package com.friday.pay.wxpay.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.internal.util.StringUtils;
import com.friday.pay.wxpay.constant.WxpayConsts;
import com.friday.pay.wxpay.constant.WxpayConsts.TradeType;
import com.friday.pay.wxpay.dto.WxpayNotifyParams;
import com.friday.pay.wxpay.dto.WxpayPreOrderResult;
import com.friday.pay.wxpay.dto.WxpaySDKParams;
import com.friday.pay.wxpay.exception.WxpayUtilsException;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;

/**
 * 微信支付工具类 <br>
 * Created on 2018-4-11
 * 
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/index.html/">官方文档</a>
 * @author xuyinghao
 *
 */
public class WxpayUtils {

	private WXPay wxpayClient;
	private String key;
	private String notifyUrl;

	public static class Builder implements WXPayConfig {

		private String appId;
		private String mchId;
		private String key;
		private InputStream certStream;
		private int httpConnectTimeoutMs;
		private int httpReadTimeoutMs;
		private String notifyUrl;
		private WxpayConsts.Env env = WxpayConsts.Env.PRO;

		/**
		 * 设置APPID，必填
		 */
		public Builder appId(String appId) {
			this.appId = appId;
			return this;
		}

		/**
		 * 设置商户ID，必填
		 */
		public Builder mchId(String mchId) {
			this.mchId = mchId;
			return this;
		}

		/**
		 * 设置Api秘钥，必填<br/>
		 * 该秘钥需登录商户平台进行设置
		 */
		public Builder key(String key) {
			this.key = key;
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
		 * 设置环境，选填，默认生产
		 */
		public Builder env(WxpayConsts.Env env) {
			this.env = env;
			return this;
		}

		/**
		 * 设置HTTP(S) 连接超时时间，单位毫秒，选填
		 */
		public Builder httpConnectTimeoutMs(int httpConnectTimeoutMs) {
			this.httpConnectTimeoutMs = httpConnectTimeoutMs;
			return this;
		}

		/**
		 * 设置HTTP(S) 读数据超时时间，单位毫秒，选填
		 */
		public Builder httpReadTimeoutMs(int httpReadTimeoutMs) {
			this.httpReadTimeoutMs = httpReadTimeoutMs;
			return this;
		}

		public WxpayUtils builder() {
			if (StringUtils.isEmpty(this.appId)) {
				throw new WxpayUtilsException("APPID must not be empty");
			}
			if (StringUtils.isEmpty(this.mchId)) {
				throw new WxpayUtilsException("mchId must not be empty");
			}
			if (StringUtils.isEmpty(this.key)) {
				throw new WxpayUtilsException("key must not be empty");
			}
			if (StringUtils.isEmpty(this.notifyUrl)) {
				throw new WxpayUtilsException("notifyUrl must not be empty");
			}
			return new WxpayUtils(this);
		}

		@Override
		public String getAppID() {
			return this.appId;
		}

		@Override
		public String getMchID() {
			return this.mchId;
		}

		@Override
		public String getKey() {
			return this.key;
		}

		@Override
		public InputStream getCertStream() {
			return this.certStream;
		}

		@Override
		public int getHttpConnectTimeoutMs() {
			return this.httpConnectTimeoutMs;
		}

		@Override
		public int getHttpReadTimeoutMs() {
			return this.httpReadTimeoutMs;
		}
	}

	private WxpayUtils(Builder builder) {
		if (WxpayConsts.Env.SANDBOX.equals(builder.env)) {
			this.wxpayClient = new WXPay(builder, SignType.MD5, true);
		} else {
			this.wxpayClient = new WXPay(builder);
		}
		this.notifyUrl = builder.notifyUrl;
		this.key = builder.key;
	}

	/**
	 * 创建预订单
	 * 
	 * @param outTradeNo
	 * @param totalAmount
	 * @param body
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public WxpayPreOrderResult preOrder(String outTradeNo, int totalAmount, String body) throws Exception {
		return preOrder(outTradeNo, totalAmount, body, null);
	}

	public WxpayPreOrderResult preOrder(String outTradeNo, int totalAmount, String body, String ip) throws Exception {

		Map<String, String> data = new HashMap<String, String>(8);
		data.put("body", body);
		data.put("out_trade_no", outTradeNo);
		data.put("total_fee", String.valueOf(totalAmount));
		data.put("spbill_create_ip", ip);
		data.put("notify_url", notifyUrl);
		data.put("trade_type", TradeType.APP.getValue());

		Map<String, String> resp = wxpayClient.unifiedOrder(data);

		if (wxpayClient.isPayResultNotifySignatureValid(resp)) {
			return new WxpayPreOrderResult(resp);
		} else {
			throw new WxpayUtilsException("sign check fail");
		}
	}

	/**
	 * 校验支付结果参数
	 * 
	 * @param notifyData
	 *            异步重置报文
	 * @return
	 * @throws Exception
	 */
	public WxpayNotifyParams resultCheck(String notifyData) throws Exception {

		Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);

		if (wxpayClient.isPayResultNotifySignatureValid(notifyMap)) {
			return new WxpayNotifyParams(notifyMap);
		} else {
			throw new WxpayUtilsException("sign check fail");
		}
	}

	/**
	 * 根据预订单结果生成APP数据包<br/>
	 * APP使用数据包中的内容调起SDK
	 * 
	 * @param result
	 *            创建预定单结果
	 * @return
	 * @throws Exception
	 */
	public WxpaySDKParams convertSDKParams(WxpayPreOrderResult result) throws Exception {
		return new WxpaySDKParams(result, key);
	}

}
