package com.skyvn.hw.config;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/913:45
 * desc   :  配置参数类
 * version: 1.0
 */
public interface IConstant {

    //语言类型
    String LANGUAGE_TYPE = "language_type";

    /**
     * 活体检测的key
     */
    String LIVE_KEY = "7cec70f1679a5c3c";

    /**
     * 活体检测的
     */
    String Secret_Key = "728e65227bf25507";

    /**
     * 界面风格  1 :风格1  2：风格2
     */
    int STYLE = 1;

    /**
     * AppId
     */
    String appId = "1237328537956216833";

    /**
     * 服务器接口地址
     */
    String URL = "http://47.96.126.117:9989/";

//    app打包：
//    百倍云：
//    saas-app：currentApplicationId：1237328537956216833
//    接口地址：http://47.96.126.117:9989
//    租户app：currentApplicationId：1237613520948129793 ，currentTenantId：1234763145872871425
//    接口地址：http://47.96.126.117:9986
//    客户：
//    saas-app：currentApplicationId：1242415496784642049
//    接口地址：http://tsa.fengyunv40.com
//    租户app：currentApplicationId：1242353191250165761 ，currentTenantId：1242352620035608577
//    接口地址：http://tta.fengyunv40.com
}
