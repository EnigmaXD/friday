# Friday-pay支付组件
本项目集成支付相关功能，目前支持支付宝、微信支付（app支付）



```xml  
<dependency>
    <groupId>com.oudot.friday</groupId>
    <artifactId>friday-pay</artifactId>
    <version>latest version</version>
</dependency>
```

### 项目结构
```
└─com
    └─oudot
        └─friday
            └─pay
                ├─alipay    支付宝
                │  ├─constant
                │  ├─dto
                │  ├─exception
                │  ├─handler
                │  └─util
                └─wxpay     微信支付
                    ├─constant
                    ├─dto
                    ├─exception
                    ├─handler
                    └─util
```

### 支付宝 Alipay
基于支付宝手机网站支付功能，相关文档：https://docs.open.alipay.com/203/105288/

#### 配置参数

|     配置方法    |                 描述                 |
|-----------------|--------------------------------------|
| env             | 环境（默认生产环境）                 |
| appId           | appId（必填）                      |
| privateKey      | 应用私钥（必填）                     |
| alipayPublicKey | 支付宝公钥（必填）                   |
| returnUrl       | 页面返回地址（必填，HTTP/HTTPS开头） |
| notifyUrl       | 回调地址（必填，HTTP/HTTPS开头）     |
| signType        | 加密方式（默认SHA2）                 |

```java
AlipayUtils alipayUtils = new AlipayUtils.Builder()
                                        .appId(PayConsts.ALIPAY_APP_ID)
                                        .privateKey(PayConsts.ALIPAY_PRIVATE_KEY)
                                        .alipayPublicKey(PayConsts.ALIPAY_PUBLIC_KEY)
                                        .returnUrl(PayConsts.ALIPAY_RETURN_URL)
                                        .notifyUrl(PayConsts.ALIPAY_NOTIFY_URL)
                                        .builder();
```

#### 发起支付
支付宝手机H5支付分为页面跳转类、系统调用类两种模式，详细流程请见 **[官方文档](https://docs.open.alipay.com/203/105285/)**

##### 页面跳转类
返回支付链接，可传递至前端页面直接跳转。  
AlipayUtils.getUrl(String outTradeNo, String totalAmount, String subject);

|     字段    |  类型  |    描述    |
|-------------|--------|------------|
| outTradeNo  | String | 外部订单号 |
| totalAmount | String | 交易金额   |
| subject     | String | 标题       |

```java
public String alipayTrade(String outTradeNo, String totalAmout,String subject) {
    String url;
    try {
        url = PayUtils.Alipay().getUrl(outTradeNo, totalAmout, subject);
    } catch (Exception e) {
        logger.error(e.getMessage(),e);
        url="";
    }
    return url;
}
```

##### 系统调用类
将http返回对象传入，直接将接口返回html输出至页面。  
doPost(String outTradeNo, String totalAmount, String subject, HttpServletResponse httpResponse)

|     字段     |                  类型                  |     描述     |
|--------------|----------------------------------------|--------------|
| outTradeNo   | String                                 | 外部订单号   |
| totalAmount  | String                                 | 交易金额     |
| subject      | String                                 | 标题         |
| httpResponse | javax.servlet.http.HttpServletResponse | http响应对象 |

```java
public void alipayTrade(String outTradeNo, String totalAmout, String subject, HttpServletResponse httpResponse) {
    try {
        PayUtils.Alipay().doPost(outTradeNo, totalAmout, subject, httpResponse);
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    }
}
```

#### 回调接口
AbstractAlipayNotifyHandler 支付宝回调接口模板
tradeSuccess、tradeFinished、tradeClose对应支付成功、交易完成、交易关闭状态，关于交易状态，详见 **[官方文档](https://docs.open.alipay.com/203/105286/)**

AlipayNotifyParams 支付宝回调参数

|      字段      |      描述      |
|----------------|----------------|
| tradeNo        | 支付宝交易号   |
| outTradeNo     | 商户订单号     |
| buyerLogonId   | 买家支付宝账号 |
| tradeStatus    | 交易状态       |
| totalAmount    | 订单金额       |
| receiptAmount  | 实收金额       |
| invoiceAmount  | 开票金额       |
| buyerPayAmount | 付款金额       |
| pointAmount    | 集分宝金额     |
| refundFee      | 总退款金额     |
| subject        | 订单标题       |
| body           | 商品描述       |
| gmtCreate      | 交易创建时间   |
| gmtPayment     | 交易付款时间   |
| gmtRefund      | 交易退款时间   |
| gmtClose       | 交易结束时间   |
| fundBillList   | 支付金额信息   |
| passbackParams | 回传参数       |

```java
@Controller
public class AlipayController extends AbstractAlipayNotifyHandler {

    @ResponseBody
    @RequestMapping("/alipay/notifyUrl")
    @Override
    public String receiver(HttpServletRequest httpRequest) {
       return super.receiver(httpRequest);
    }

    @Override
    public AlipayUtils getUtil() {
        return PayUtils.Alipay();
    }

    @Override
    public void tradeSuccess(AlipayNotifyParams params) {
        //交易成功
    }

    @Override
    public void tradeFinished(AlipayNotifyParams params) {
        //交易完成
    }

    @Override
    public void tradeClose(AlipayNotifyParams params) {
        //交易关闭       
    }
}
```

#### 前台回跳
完成支付操作后，将根据sdk中配置的returnUrl以get请求形式返回页面，可从链接中获取参数，如：外部订单号。
具体前台回调参数，可见官方文档中 **[前台回跳参数](https://docs.open.alipay.com/203/107090/#s2)** 部分。




### 微信 Wxpay
微信端目前使用微信app支付流程，详见微信 **[APP支付](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_3)**。

#### 配置参数

|     配置方法    |                    描述                    |
|-----------------|--------------------------------------------|
| env             | 环境（默认生产环境）                       |
| appId           | appId（必填）                              |
| mchId           | 商户id（必填）                             |
| key             | Api秘钥（必填,需登录**商户平台**进行设置） |
| notifyUrl       | 回调地址（必填，HTTP/HTTPS开头）           |

**回跳链接** 回跳链接需在APP调起控件时传递，故不在此处配置

```java
 WxpayUtils wxpayUtils = new WxpayUtils.Builder()
                                    .appId(PayConsts.WXPAY_APP_ID)
                                    .mchId(PayConsts.WXPAY_MCH_ID)
                                    .key(PayConsts.WXPAY_KEY)
                                    .notifyUrl(PayConsts.WXPAY_NOTIFY_URL)
                                    .env(Env.PRO)
                                    .builder();
```

#### 发起支付
通过调用统一下单接口生成预订单，将预订单参数传递给app，通过app端SDK调起支付控件。
WxpayPreOrderResult preOrder(String outTradeNo, int totalAmount, String body, String ip);

|     字段    |  类型  |                 描述                |
|-------------|--------|-------------------------------------|
| outTradeNo  | String | 外部订单号                          |
| totalAmount | int    | 交易金额(最小货币单位，人民币为 分) |
| body        | String | 标题                                |
| ip          | String | 用户端ip（可填127.0.0.1）           |

WxpayPreOrderResult 统一下单接口返回内容

|    字段    |  类型  |               描述               |
|------------|--------|----------------------------------|
| returnCode | String | 通信标识SUCCESS/FAIL             |
| returnMsg  | String | 返回信息，如非空，为错误原因     |
| appid      | String | 调用接口提交的应用ID             |
| mchId      | String | 调用接口提交的商户号             |
| deviceInfo | String | 调用接口提交的终端设备号         |
| nonceStr   | String | 微信返回的随机字符串             |
| sign       | String | 微信返回的签名                   |
| resultCode | String | 业务结果 SUCCESS/FAIL            |
| errCode    | String | 错误代码                         |
| errCodeDes | String | 错误代码描述                     |
| tradeType  | String | 交易类型                         |
| prepayId   | String | 预支付交易会话标识,有效期为2小时 |

WxpaySDKParams convertSDKParams(WxpayPreOrderResult result) 根据预订单结果生成APP数据包

WxpaySDKParams SDK参数

|    字段   |  类型  |             描述             |
|-----------|--------|------------------------------|
| appid     | String | 应用ID                       |
| partnerid | String | 商户号                       |
| prepayid  | String | 预支付交易会话ID             |
| pack      | String | 扩展字段（固定值Sign=WXPay） |
| noncestr  | String | 随机字符串                   |
| timestamp | String | 时间戳                       |
| sign      | String | 签名                         |

```java
public ResponseDto wxpayTrade(String outTradeNo, String totalAmout) {
    try {
        WxpayPreOrderResult result = PayUtils.Wxpay().preOrder(outTradeNo,
                Integer.valueOf(totalAmout.replaceAll("\\.", "")), PayConsts.WXPAY_BODY, "127.0.0.1");
        if (ReturnCode.SUCCESS.getValue().equals(result.getReturnCode())
                && ResultCode.SUCCESS.getValue().equals(result.getResultCode())
                && !StringUtils.isEmpty(result.getPrepayId())) {
            RechargeInitWxpayRes res = new RechargeInitWxpayRes();
            res.setSdkParams(PayUtils.Wxpay().convertSDKParams(result));
            res.setReturnUrl(PayConsts.WXPAY_RETURN_URL + "?out_trade_no=" + outTradeNo);
            return ResponseDto.newSuccess(res);
        } else {
            return ResponseDto.newErr(ResponseConst.PAY_FAIL);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseDto.newErr(ResponseConst.SYSTEM_ERROR);
    }
}
```

#### 回调接口
AbstractWxpayNotifyHanlder 支付宝回调接口模板
tradeSuccess、tradeFail对应ResultCode SUCCESS/FAIL，关于交易状态，详见 **[官方文档](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3)**

WxpayNotifyParams 微信支付回调参数

|      字段     |                 描述                 |
|---------------|--------------------------------------|
| returnCode    | 返回状态码 SUCCESS/FAIL              |
| returnMsg     | 返回信息，如非空，为错误原因         |
| appid         | 应用ID                               |
| mchId         | 商户号                               |
| deviceInfo    | 设备号                               |
| resultCode    | 业务结果 SUCCESS/FAIL                |
| errCode       | 错误代码 错误返回的信息描述          |
| errCodeDes    | 错误代码描述 错误返回的信息描述      |
| openid        | 用户标识 用户在商户appid下的唯一标识 |
| tradeType     | 交易类型                             |
| totalFee      | 总金额 订单总金额，单位为分          |
| transactionId | 微信支付订单号                       |
| outTradeNo    | 商户订单号                           |
| attach        | 商家数据包，原样返回                          |
| timeEnd       | 支付完成时间，格式为yyyyMMddHHmmss   |

```java
@Controller
public class WxpayController extends AbstractWxpayNotifyHanlder {

    @ResponseBody
    @RequestMapping("/wxpay/notifyUrl")
    @Override
    public String receiver(@RequestBody String reqBody) {
        return super.receiver(reqBody);
    }

    @Override
    public WxpayUtils getUtil() {
        return PayUtils.Wxpay();
    }

    @Override
    public void tradeSuccess(WxpayNotifyParams params) {
    }

    @Override
    public void tradeFail(WxpayNotifyParams params) {
    }

}
```