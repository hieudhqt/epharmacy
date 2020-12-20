package com.hieu.swd.epharmacy.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PushNotificationRequest {

    private String title;
    private String message;
    private String topic;
    private String token;

    public PushNotificationRequest(String title, String message, String topic) {
        this.title = title;
        this.message = message;
        this.topic = topic;
    }
}
