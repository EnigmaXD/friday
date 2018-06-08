package com.oudot.friday.pay.wxpay.handler;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oudot.friday.pay.wxpay.constant.WxpayConsts;
import com.oudot.friday.pay.wxpay.dto.WxpayNotifyParams;
import com.oudot.friday.pay.wxpay.exception.WxpayUtilsException;
import com.oudot.friday.pay.wxpay.util.WxpayUtils;

/**
 * 微信支付结果通知接口模板
 * 
 * @author xuyinghao
 *
 */
public abstract class AbstractWxpayNotifyHanlder {

	/**
	 * 获取WxpayUtils对象
	 * 
	 * @return
	 */
	public abstract WxpayUtils getUtil();

	/**
	 * 业务入口
	 * 
	 * @return
	 */
	public String receiver(HttpServletRequest httpRequest) {
		try {
			BufferedReader br = httpRequest.getReader();
			String str, notifyData = "";
			while ((str = br.readLine()) != null) {
				notifyData += str;
			}
			return receiver(notifyData);
		} catch (IOException e) {
			e.printStackTrace();
			exceptionCaught(e);
		}
		return WxpayConsts.NOTIFY_CONFIRM_FAIL_XML;
	}

	public String receiver(String notifyData) {

		try {
			WxpayNotifyParams params = getUtil().resultCheck(notifyData);
			if (WxpayConsts.ReturnCode.SUCCESS.getValue().equals(params.getReturnCode())) {

				if (WxpayConsts.ResultCode.SUCCESS.getValue().equals(params.getResultCode())) {
					tradeSuccess(params);
					return WxpayConsts.NOTIFY_CONFIRM_SUCCESS_XML;

				} else {
					tradeFail(params);
					return WxpayConsts.NOTIFY_CONFIRM_FAIL_XML;
				}
			} else {
				throw new WxpayUtilsException("return_code_fail," + params.getReturnMsg());
			}

		} catch (Exception e) {
			e.printStackTrace();
			exceptionCaught(e);
		}
		return WxpayConsts.NOTIFY_CONFIRM_FAIL_XML;
	}

	/**
	 * 交易支付成功 result_code为SUCCESS
	 * 
	 * @param params
	 */
	public abstract void tradeSuccess(WxpayNotifyParams params);

	/**
	 * 交易支付失败 result_code为FAIL
	 * 
	 * @param params
	 */
	public abstract void tradeFail(WxpayNotifyParams params);

	public void exceptionCaught(Throwable cause) {
		cause.printStackTrace();
	}

}
