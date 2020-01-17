package com.skyvn.hw.api;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.Utils;
import com.skyvn.hw.bean.BankBO;
import com.skyvn.hw.bean.BankCardBO;
import com.skyvn.hw.bean.CodeImgBO;
import com.skyvn.hw.bean.LablesBO;
import com.skyvn.hw.bean.LoginSuressBO;
import com.skyvn.hw.util.EquipmentUtil;
import com.skyvn.hw.util.rx.RxResultHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.URL);
        return service;
    }


    /**
     * 获取图片验证码
     */
    public static Observable<CodeImgBO> getCodeImg() {
        return getService().getCodeImg().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取短信验证码
     */
    public static Observable<String> getVerificationCode(String key, String phone, String imageCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", key);
        params.put("phone", phone);
        params.put("verificationCode", imageCode);
        return getService().getVerificationCode(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 登录
     */
    public static Observable<LoginSuressBO> loginUser(double localGpsLat, double loginLongitude, String phone, String verificationCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("deviceId", DeviceUtils.getAndroidID());
        params.put("deviceName", EquipmentUtil.getSystemDevice());
        params.put("localGpsLat", localGpsLat + "");
        params.put("localGpsLong", loginLongitude + "");
        params.put("phone", phone);
        params.put("verificationCode", verificationCode);
        return getService().loginUser(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 退出登录
     */
    public static Observable<String> exitLogin() {
        Map<String, Object> params = new HashMap<>();
        return getService().exitLogin(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交反馈
     */
    public static Observable<String> addOperateApplicationFeedback(String content, String contact, String images) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        params.put("contact", contact);
        params.put("imageOssUrl", images);
        return getService().addOperateApplicationFeedback(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取银行卡
     */
    public static Observable<BankCardBO> getBankCard() {
        return getService().getBankCard().compose(RxResultHelper.httpRusult());
    }

    /**
     * 绑定银行卡
     */
    public static Observable<String> bindBankCard(String bankName, String cardNo, String name, String subbranch) {
        Map<String, Object> params = new HashMap<>();
        params.put("bank", bankName);
        params.put("cardNo", cardNo);
        params.put("name", name);
        params.put("subbranch", subbranch);
        return getService().bindBankCard(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交个人资料
     */
    public static Observable<String> commitPersonMsg(String xueli, String hunyin, String zinvNum, String juzhuTime,
                                                     String juzhuAddress, String zalo, String facebook) {
        Map<String, Object> params = new HashMap<>();
        params.put("education", xueli);
        params.put("marriage", hunyin);
        params.put("child", zinvNum);
        params.put("liveMonth", juzhuTime);
        params.put("liveAddress", juzhuAddress);
        params.put("zalo", zalo);
        params.put("facebook", facebook);
        return getService().commitClientInfo(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交公司资料
     */
    public static Observable<String> commitComplanyInfo(String company, String companyAddress, String companyTel,
                                                        String pretaxIncome, String profession, String workMonth,
                                                        String workPicOssUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("company", company);
        params.put("companyAddress", companyAddress);
        params.put("companyTel", companyTel);
        params.put("pretaxIncome", pretaxIncome);
        params.put("profession", profession);
        params.put("workMonth", workMonth);
        params.put("workPicOssUrl", workPicOssUrl);
        return getService().commitCompanyInfo(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取枚举数据
     */
    public static Observable<List<LablesBO>> getSysLables(int parentId) {
        return getService().getSysLabels(parentId + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取所有银行
     */
    public static Observable<List<BankBO>> getSysBanks() {
        return getService().getSysBanks().compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交图片
     */
    public static Observable<String> updateFile(File file) {
        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = file;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return getService().updateFile(body).compose(RxResultHelper.httpRusult());
    }
}
