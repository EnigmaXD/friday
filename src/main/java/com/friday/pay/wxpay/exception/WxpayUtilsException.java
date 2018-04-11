package com.friday.pay.wxpay.exception;

/**
 * 微信支付异常
 * 
 * @author xuyinghao
 *
 */
public class WxpayUtilsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WxpayUtilsException(String message) {
		super(message);
	}

	public WxpayUtilsException(Throwable cause) {
		super(cause);
	}

	public WxpayUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
