package org.yunhongmin.shop.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.yunhongmin.shop.event.UserJoinEvent;

@Component
public class UserJoinEventListener implements ApplicationListener<UserJoinEvent> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    @Async
    public void onApplicationEvent(UserJoinEvent event){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread sleep interrupted");
        }

        logger.info("Joined user: " + event.getUserName());
    }
}
