package org.yunhongmin.shop.event;

import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

public class UserJoinEvent extends ApplicationEvent {
    private final String username;

    public UserJoinEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUserName() {
        return username;
    }
}
