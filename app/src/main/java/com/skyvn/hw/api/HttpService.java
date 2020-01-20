package com.skyvn.hw.api;

import com.skyvn.hw.bean.AttentionSourrssBO;
import com.skyvn.hw.bean.AuthTypeBO;
import com.skyvn.hw.bean.BankBO;
import com.skyvn.hw.bean.BankCardBO;
import com.skyvn.hw.bean.BaseResult;
import com.skyvn.hw.bean.CodeImgBO;
import com.skyvn.hw.bean.KeFuBO;
import com.skyvn.hw.bean.LablesBO;
import com.skyvn.hw.bean.LoginSuressBO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
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
     * 退出登录
     */
    @POST("/clientUser/exit")
    Observable<BaseResult<String>> exitLogin(@Body Map<String, Object> params);

    /**
     * 提交反馈
     */
    @POST("/operateApplicationFeedback/addOperateApplicationFeedback")
    Observable<BaseResult<String>> addOperateApplicationFeedback(@Body Map<String, Object> params);

    /**
     * 绑定银行卡
     */
    @POST("/clientUserBankCard/addClientSmsRecordAuth")
    Observable<BaseResult<AttentionSourrssBO>> bindBankCard(@Body Map<String, Object> params);

    /**
     * 获取银行卡
     */
    @GET("/clientUserBankCard/getBankCard")
    Observable<BaseResult<BankCardBO>> getBankCard();

    /**
     * 获取认证项的认证状态
     */
    @GET("/clientAuthStatus/getAuthList")
    Observable<BaseResult<List<AuthTypeBO>>> getAuthList();

    /**
     * 提交个人资料
     */
    @POST("/clientUserInfo/addClientInfoAuthTwo")
    Observable<BaseResult<AttentionSourrssBO>> commitClientInfo(@Body Map<String, Object> params);

    /**
     * 实名认证资料
     */
    @POST("/clientUserIdcard/addClientIdcardAuthTwo")
    Observable<BaseResult<AttentionSourrssBO>> commitIdCard(@Body Map<String, Object> params);

    /**
     * 提交公司信息
     */
    @POST("/clientUserInfo/addCompanyInfoAuth")
    Observable<BaseResult<AttentionSourrssBO>> commitCompanyInfo(@Body Map<String, Object> params);

    /**
     * 提交驾照信息
     */
    @POST("/clientDrivingLicense/addDrivingLicenseAuth")
    Observable<BaseResult<AttentionSourrssBO>> commitJiaZhaoInfo(@Body Map<String, Object> params);

    /**
     * 提交短信1414信息
     */
    @POST("/clientSms1414/addClientSms1414Auth")
    Observable<BaseResult<AttentionSourrssBO>> addClientSms1414(@Body Map<String, Object> params);

    /**
     * 提交小视频认证
     */
    @POST("/clientIdcardVideo/addIdcardVideoAuth")
    Observable<BaseResult<AttentionSourrssBO>> addVideoInfo(@Body Map<String, Object> params);

    /**
     * 提交紧急联系人认证
     */
    @POST("/clientUserContact/addClientContactAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientContactInfo(@Body Map<String, Object> params);

    /**
     * 提交通讯录认证
     */
    @POST("/clientAddressList/addClientAddressListAuth")
    Observable<BaseResult<AttentionSourrssBO>> addContactListInfo(@Body Map<String, Object> params);

    /**
     * 提交活体检测认证
     */
    @POST("/clientActive/addClientActiveAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientActiveAuth(@Body Map<String, Object> params);

    /**
     * 获取返回的文案信息
     */
    @GET("/operateApplicationAuthProcess/getCopyWriter")
    Observable<BaseResult<String>> getCopyWriter(@Query("code") String code);

    /**
     * 跳过
     */
    @GET("/clientAuthStatus/getJumpAuth")
    Observable<BaseResult<AttentionSourrssBO>> JumpAuth(@Query("code") String code);

    /**
     * 获取枚举数据
     */
    @GET("/sysLabel/getSysLabels")
    Observable<BaseResult<List<LablesBO>>> getSysLabels(@Query("parentId") String parentId);

    /**
     * 获取所有银行
     */
    @GET("/sysBank/getSysBanks")
    Observable<BaseResult<List<BankBO>>> getSysBanks();

    /**
     * 获取全部客服
     */
    @GET("/operateApplicationCustomerService/getCustomerServicesByApplicationId")
    Observable<BaseResult<List<KeFuBO>>> getCustomerServicesByApplicationId();

    /**
     * 上传文件
     */
    @Multipart
    @POST("/upload/uploadFile")
    Observable<BaseResult<String>> updateFile(@Part MultipartBody.Part file);

}
