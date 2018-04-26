package com.oudot.friday.pay.wxpay.constant;

/**
 * 微信支付常量
 * 
 * @author xuyinghao
 *
 */
public class WxpayConsts {

	/**
	 * {@value}
	 */
	public static final String NOTIFY_CONFIRM_SUCCESS_XML = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><xml><return_msg>SUCCESS</return_msg><return_code>SUCCESS</return_code></xml>";

	/**
	 * {@value}
	 */
	public static final String NOTIFY_CONFIRM_FAIL_XML = "<?xml version='1.0' encoding='UTF-8' standalone='no'?><xml><return_msg>FAIL</return_msg><return_code>FAIL</return_code></xml>";

	public enum Env {
		/**
		 * 生产环境
		 */
		PRO,
		/**
		 * 沙盒环境
		 */
		SANDBOX;
	}

	public enum ReturnCode {
		/**
		 * 成功
		 */
		SUCCESS("SUCCESS"),
		/**
		 * 失败
		 */
		FAIL("FAIL");

		private String value;

		private ReturnCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum ResultCode {
		/**
		 * 成功
		 */
		SUCCESS("SUCCESS"),
		/**
		 * 失败
		 */
		FAIL("FAIL");

		private String value;

		private ResultCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum ReturnResult {
		/**
		 * 成功
		 */
		SUCCESS("SUCCESS"),
		/**
		 * 失败
		 */
		FAIL("FAIL");

		private String value;

		private ReturnResult(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum TradeType {
		/**
		 * APP支付
		 */
		APP("APP");

		private String value;

		private TradeType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
