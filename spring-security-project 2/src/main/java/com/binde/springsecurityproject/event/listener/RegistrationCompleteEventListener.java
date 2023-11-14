package com.binde.springsecurityproject.event.listener;

import com.binde.springsecurityproject.entity.User;
import com.binde.springsecurityproject.event.RegistrationCompleteEvent;
import com.binde.springsecurityproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create the verification Token for the User with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        //send mail to user
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;
        //send verification Email()

        log.info("click the link to verify your account registration : {} ",
                url);
    }
}
