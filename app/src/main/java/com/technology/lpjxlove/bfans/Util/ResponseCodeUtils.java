package com.technology.lpjxlove.bfans.Util;

/**
 * Created by LPJXLOVE on 2016/9/5.
 */
public class ResponseCodeUtils {

    public static String TransForm(int responseCode) {
        String text ;
        switch (responseCode) {
            case 9001:
                text = "ApplicationId 为空,请初始化";
                break;
            case 9002:
                text = "解析返回数据出错";
                break;
            case 9003:
                text = "上传文件出错";
                break;
            case 9004:
                text = "上传文件失败";
                break;
            case 9005:
                text = "批量操作只支持最多50条";
                break;
            case 9006:
                text = "objectId为空";
                break;
            case 9007:
                text = "文件大小超过10M";
                break;
            case 9008:
                text = "上传文件不存在";
                break;
            case 9009:
                text = "没有缓存数据";
                break;
            case 9010:
                text = "网络超时";
                break;
            case 9011:
                text = "BmobUser类不支持批量操作";
                break;
            case 9012:
                text = "上下文为空";
                break;
            case 9013:
                text = "BmobObject格式不正确";
                break;
            case 9014:
                text = "第三方帐号授权失败";
                break;
            case 9015:
                text = "其他错误均返回此code";
                break;
            case 9016:
                text = "无网络连接，请检查您的手机网络";
                break;
            case 9017:
                text = "与第三方登录有关的错误，具体请看对应的错误描述";
                break;
            case 9018:
                text = "参数不能为空";
                break;
            case 9019:
                text = "格式不正确：手机号码、邮箱地址、验证码";
                break;
            default:
                text="未知错误";

        }

        return text;
    }
}
