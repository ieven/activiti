package org.activiti;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushTest {

    protected static final String APP_KEY = "0520041b34fefe45a504640f";
    protected static final String MASTER_SECRET = "aaafb275e9451dd0f12fdefd";

    public static void main(String[] args) {

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage();
        // PushPayload payload = build();

        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result);

        }
        catch (APIConnectionException e) {
            // Connection error, should retry later
            e.printStackTrace();
            System.out.println("Connection error, should retry later");

        }
        catch (APIRequestException e) {
            // Should review the error, and fix the request
            e.printStackTrace();
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
        }
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {

        return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag("1122"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder().setAlert("测试程序头").setBadge(5)
                                .setSound("happy").addExtra("from", "JPush").build())
                        .build())
                .setMessage(Message.content("测试程序消息体"))
                .setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
    }

}
