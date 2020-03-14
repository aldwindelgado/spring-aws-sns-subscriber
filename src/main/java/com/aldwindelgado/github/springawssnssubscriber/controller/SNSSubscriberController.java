package com.aldwindelgado.github.springawssnssubscriber.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aldwin Delgado on Mar 14, 2020
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/subscribe")
public class SNSSubscriberController {

    /*
        Will be triggered once you create a subscription to AWS SNS dashboard
        {@link https://ap-southeast-1.console.aws.amazon.com/sns/v3/home?region=ap-southeast-1#/create-subscription}
        Just make sure that the "Enable raw message delivery" is DISABLED, otherwise the subscriber
        will have a hard time receiving the message.

        I don't know if that is a bug or yet to be fixed by Spring AWS team or I'm just too dumb to
        properly configure the message handler resolver :)
     */
    @NotificationSubscriptionMapping
    public void handleSubscriptionMessage(NotificationStatus notificationStatus) {
        log.info("Handle Subscription Message...");
        notificationStatus.confirmSubscription();
    }

    @NotificationMessageMapping
    public void handleNotificationMessage(@NotificationSubject String subject,
        @NotificationMessage String notificationMessage) {
        log.info("Handling Notification Message...");
        log.info("Subject: {}", subject);
        log.info("Message: {}", notificationMessage);
    }

    // Not really needed this one...
    @NotificationUnsubscribeConfirmationMapping
    public void handleUnsubscribeMessage(NotificationStatus notificationStatus) {
        log.info("Handling Unsubscribe Message...");
        notificationStatus.confirmSubscription();
    }
}
