package org.activiti;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class Pusher {

    private String appKey;

    private String masterSecret;

    private JPushClient pushClient;

    public Pusher(String appKey, String masterSecret) {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        this.pushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
    }

    public String getAppKey() {

        return appKey;
    }

    public String getMasterSecret() {

        return masterSecret;
    }

    public JPushClient getPushClient() {

        return pushClient;
    }

    public PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String tag, String head, String body) {

        return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder().setAlert(head).setBadge(5)
                                .setSound("happy").addExtra("from", "JPush").build())
                        .build())
                .setMessage(Message.content(body)).setOptions(Options.newBuilder().setApnsProduction(false).build())
                .build();
    }
}
