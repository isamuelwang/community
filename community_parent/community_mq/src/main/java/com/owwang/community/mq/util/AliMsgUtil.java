package com.owwang.community.mq.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author samuel
 * @create 2019-04-078 22:14
 **/
@Component
public class AliMsgUtil {

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private static final String PRODUCT = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * todo 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
     */
    private static String ACCESSKEYID;
    @Value("${alimsg.ACCESSKEYID}")
    public void setACCESSKEYID(String accessKeyId) {
        ACCESSKEYID = accessKeyId;
    }
    //private static String ACCESSKEYID="LTAI4Fs683H4kksvpz3ndLsc";

    /**
     * todo 此处需要替换成开发者自己的accessKeySecret(在阿里云访问控制台寻找)
     */
    private static String ACCESSKEYSECRET;
    @Value("${alimsg.ACCESSKEYSECRET}")
    public void setACCESSKEYSECRET(String accessKeyId) {
        ACCESSKEYSECRET = accessKeyId;
    }
    //private static final String ACCESSKEYSECRET="3By9alltBI4kNRNES94zGk8yL1M4xo";

    /**
     * todo 必填:短信签名-可在短信控制台中找到
     */
    private static String SIGNNAME;
    @Value("${alimsg.SIGNNAME}")
    public void setSIGNNAME(String accessKeyId) {
        SIGNNAME = accessKeyId;
    }
    //private static final String SIGNNAME="欧文网";

    /**
     * todo 必填:短信模板-可在短信控制台中找到
     */
    private static String TEMPLATECODE;
    @Value("${alimsg.TEMPLATECODE}")
    public void setTEMPLATECODE(String accessKeyId) {
        TEMPLATECODE = accessKeyId;
    }
    //private static final String TEMPLATECODE="SMS_182535740";

    /**
     * 发送方法
     *
     * @param code 发送参数
     * @param phone 电话号码
     * @return 返回值
     * @throws ClientException 异常
     */
    public static SendSmsResponse sendSms(String code, String phone) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //todo 必填:短信签名-可在短信控制台中找到
        request.setSignName(SIGNNAME);
        //todo 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TEMPLATECODE);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println(sendSmsResponse.getMessage());
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return sendSmsResponse;
    }

    public static void main(String[] args) throws ClientException, InterruptedException {
        //发短信
        SendSmsResponse response = sendSms("随机数", "电话号码");
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
    }

    /**
     * 生成六位随机数
     *
     * @return
     */
    public static String createRandomVcode() {
        //验证码
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            vcode.append((int) (Math.random() * 9));
        }
        return vcode.toString();
    }
}