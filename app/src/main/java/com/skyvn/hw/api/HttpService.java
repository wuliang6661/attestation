package com.skyvn.hw.api;

import com.skyvn.hw.bean.BaseResult;
import com.skyvn.hw.bean.CodeImgBO;
import com.skyvn.hw.bean.LoginSuressBO;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    //    String URL = "http://47.98.108.34:8080/";   //正式服
    String URL = "http://47.96.126.117:9989/";   //测试服
//    String URL = "http://mapi.platform.yinghezhong.com/";  //测试服2
//    String URL = "http://api.open.yinghezhong.com/";  //正式环境
//    String URL = "http://mapi.open.yinghezhong.com/";  //正式环境2


    /**
     * 获取图片验证码
     */
    @POST("/clientUser/getCodeImg")
    Observable<BaseResult<CodeImgBO>> getCodeImg();

    /**
     * 获取短信验证码
     */
    @POST("/clientUser/getVerificationCode")
    Observable<BaseResult<String>> getVerificationCode(@Body Map<String, Object> params);

    /**
     * 登录接口
     */
    @POST("/clientUser/login")
    Observable<BaseResult<LoginSuressBO>> loginUser(@Body Map<String, Object> params);

    /**
     * 提交反馈
     */
    @POST("/operateApplicationFeedback/addOperateApplicationFeedback")
    Observable<BaseResult<String>> addOperateApplicationFeedback(@Body Map<String, Object> params);

    /**
     * 绑定银行卡
     */
    @POST("/clientUserBankCard/addClientSmsRecordAuth")
    Observable<BaseResult<String>> bindBankCard(@Body Map<String, Object> params);

    /**
     * 获取银行卡
     */
    @POST("/clientUserBankCard/getBankCard")
    Observable<BaseResult<String>> getBankCard();

    /**
     * 上传文件
     */
    @Multipart
    @POST("/upload/uploadFile")
    Observable<BaseResult<String>> updateFile(@Part MultipartBody.Part file);

}
