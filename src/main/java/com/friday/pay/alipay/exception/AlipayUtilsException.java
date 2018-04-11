package com.friday.pay.alipay.exception;

/**
 * 支付宝异常
 * 
 * @author xuyinghao
 *
 */
public class AlipayUtilsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlipayUtilsException(String message) {
		super(message);
	}

	public AlipayUtilsException(Throwable cause) {
		super(cause);
	}

	public AlipayUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
