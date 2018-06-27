package com.oudot.friday.pay.alipay.handler;

import javax.servlet.http.HttpServletRequest;

import com.oudot.friday.pay.alipay.constant.AlipayConsts;
import com.oudot.friday.pay.alipay.dto.AlipayNotifyParams;
import com.oudot.friday.pay.alipay.exception.AlipayUtilsException;
import com.oudot.friday.pay.alipay.util.AlipayUtils;

/**
 * 异步通知接口模板
 * 
 * @author xuyinghao
 *
 */
public abstract class AbstractAlipayNotifyHandler {

	/**
	 * 获取AlipayUtils对象
	 * 
	 * @return
	 */
	public abstract AlipayUtils getUtil();

	/**
	 * 业务入口 </br>
	 * 确认接收成功后需要返回success字符串，否则会进行重发，间隔频率：4m,10m,10m,1h,2h,6h,15h
	 * 
	 * @return
	 */
	public String receiver(HttpServletRequest httpRequest) {

		try {
			AlipayNotifyParams params = getUtil().resultCheck(httpRequest);
			AlipayConsts.TradeStatus tradeStatus = AlipayConsts.TradeStatus.getByValue(params.getTradeStatus());
			if (null == tradeStatus) {
				throw new AlipayUtilsException("trade status wrong");
			}
			switch (tradeStatus) {
			case TRADE_SUCCESS:
				tradeSuccess(params);
				break;

			case TRADE_CLOSED:
				tradeClose(params);
				break;

			case TRADE_FINISHED:
				tradeFinished(params);
				break;

			default:
				throw new AlipayUtilsException("invalid trade status");
			}

		} catch (AlipayUtilsException e) {
			e.printStackTrace();
			exceptionCaught(e);

			return AlipayConsts.NOTIFY_CONFIRM_FAIL;
		}

		return AlipayConsts.NOTIFY_CONFIRM_SUCCESS;
	}

	/**
	 * 交易支付成功，可退款
	 * 
	 * @param params
	 */
	public abstract void tradeSuccess(AlipayNotifyParams params);

	/**
	 * 未付款交易超时关闭，或支付完成后全额退款
	 * 
	 * @param params
	 */
	public abstract void tradeClose(AlipayNotifyParams params);

	/**
	 * 交易结束，不可退款
	 * 
	 * @param params
	 */
	public abstract void tradeFinished(AlipayNotifyParams params);

	public void exceptionCaught(Throwable cause) {
		cause.printStackTrace();
	}

}
