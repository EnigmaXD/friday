package com.friday.pay.alipay.handler;

import javax.servlet.http.HttpServletRequest;

import com.friday.pay.alipay.constant.AlipayConsts;
import com.friday.pay.alipay.dto.NotifyParams;
import com.friday.pay.alipay.exception.AlipayUtilsException;
import com.friday.pay.alipay.util.AlipayUtils;

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
     * 业务入口
     * 
     * @return
     */
    public void receiver(HttpServletRequest httpRequest) {

        try {
            NotifyParams params = getUtil().resultCheck(httpRequest);
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
                break;
            }

        } catch (AlipayUtilsException e) {
            e.printStackTrace();
            exceptionCaught(e);
        }
    }

    /**
     * 交易支付成功，可退款
     */
    public abstract void tradeSuccess(NotifyParams params);

    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    public abstract void tradeClose(NotifyParams params);

    /**
     * 交易结束，不可退款
     */
    public abstract void tradeFinished(NotifyParams params);

    public void exceptionCaught(Throwable cause) {
        cause.printStackTrace();
    }

}
