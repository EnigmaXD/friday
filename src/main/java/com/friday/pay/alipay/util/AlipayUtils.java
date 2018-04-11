package com.friday.pay.alipay.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.friday.pay.alipay.constant.AlipayConsts;
import com.friday.pay.alipay.dto.AlipayNotifyParams;
import com.friday.pay.alipay.exception.AlipayUtilsException;

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

	private String alipayPublicKey;
	private String signType;
	private String appId;

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
		this.alipayPublicKey = builder.alipayPublicKey;
		this.signType = builder.signType;
		this.appId = builder.appId;

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
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		alipayRequest.setReturnUrl(returnUrl);
		alipayRequest.setNotifyUrl(notifyUrl);
		JSONObject content = new JSONObject();
		content.put(AlipayConsts.OUT_TRADE_NO_KEY, outTradeNo);
		content.put(AlipayConsts.TOTAL_AMOUNT_KEY, totalAmount);
		content.put(AlipayConsts.SUBJECT_KEY, subject);
		content.put(AlipayConsts.PRODUCT_CODE_KEY, AlipayConsts.PRODUCT_CODE_QUICK_WAP_WAY);
		// 填充业务参数
		alipayRequest.setBizContent(JSONObject.toJSONString(content));
		// 调用SDK生成表单
		String form = alipayClient.pageExecute(alipayRequest).getBody();
		httpResponse.setContentType("text/html;charset=" + "UTF8");
		// 直接将完整的表单html输出到页面
		httpResponse.getWriter().write(form);
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

		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		alipayRequest.setReturnUrl(returnUrl);
		alipayRequest.setNotifyUrl(notifyUrl);
		JSONObject content = new JSONObject();
		content.put(AlipayConsts.OUT_TRADE_NO_KEY, outTradeNo);
		content.put(AlipayConsts.TOTAL_AMOUNT_KEY, totalAmount);
		content.put(AlipayConsts.SUBJECT_KEY, subject);
		content.put(AlipayConsts.PRODUCT_CODE_KEY, AlipayConsts.PRODUCT_CODE_QUICK_WAP_WAY);
		// 填充业务参数
		alipayRequest.setBizContent(JSONObject.toJSONString(content));
		String form = alipayClient.pageExecute(alipayRequest, "GET").getBody();

		return form;
	}

	/**
	 * 验证签名
	 * 
	 * @param params
	 * @return
	 * @throws AlipayApiException
	 */
	public Boolean signCheck(Map<String, String> params) throws AlipayApiException {
		boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", signType);
		return signVerified;
	}

	/**
	 * 验证返回参数
	 * 
	 * @param httpRequest
	 * @return
	 */
	public AlipayNotifyParams resultCheck(HttpServletRequest httpRequest) {
		Map<String, String> paramMap = getParams(httpRequest);

		try {
			boolean signVerified = AlipaySignature.rsaCheckV1(paramMap, alipayPublicKey, "UTF-8", signType);
			if (!signVerified) {
				throw new AlipayUtilsException("sign check fail");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			throw new AlipayUtilsException(e);
		}
		if (!appId.equals(paramMap.get(AlipayConsts.APP_ID_KEY))) {
			throw new AlipayUtilsException("appId wrong");
		}

		AlipayNotifyParams params = new AlipayNotifyParams(paramMap);

		if (StringUtils.isEmpty(params.getOutTradeNo())) {
			throw new AlipayUtilsException("out trade number blank");
		}

		return params;
	}

	/**
	 * 获取参数列表
	 * 
	 * @param httpRequest
	 * @return
	 */
	private Map<String, String> getParams(HttpServletRequest httpRequest) {
		Map<String, String> params = new HashMap<>(32);
		Map<String, String[]> map = httpRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String ok = entry.getKey();
			Object ov = entry.getValue();
			String[] value = new String[1];
			if (ov instanceof String[]) {
				value = (String[]) ov;
			} else {
				value[0] = ov.toString();
			}
			for (int k = 0; k < value.length; k++) {
				params.put(ok, value[k]);
			}
		}
		return params;
	}

}
