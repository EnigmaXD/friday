package com.friday.pay.alipay.constant;

public class AlipayConsts {

	/**
	 * {@value}
	 */
    public static final String OUT_TRADE_NO_KEY = "out_trade_no";
    
    /**
	 * {@value}
	 */
    public static final String TOTAL_AMOUNT_KEY = "total_amount";
    
    /**
	 * {@value}
	 */
    public static final String SUBJECT_KEY = "subject";
    
    /**
	 * {@value}
	 */
    public static final String PRODUCT_CODE_KEY = "product_code";
    
    /**
     * 产品码：手机网站支付 值为 {@value}
     */
    public static final String PRODUCT_CODE_QUICK_WAP_WAY = "QUICK_WAP_WAY";

    public enum ServerUrl {
        /**
         * 正式网关
         */
        PRO("https://openapi.alipay.com/gateway.do"),

        /**
         * 沙盒网关
         */
        DEV("http://openapi.alipaydev.com/gateway.do");
        private String value;

        ServerUrl(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public enum SignType {
        RSA("RSA"),
        RSA2("RSA");

        private String value;

        SignType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}
