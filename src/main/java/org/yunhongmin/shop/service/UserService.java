package org.yunhongmin.shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.yunhongmin.shop.annotation.PerformanceLogging;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.event.UserJoinEvent;
import org.yunhongmin.shop.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private ApplicationEventPublisher applicationEventPublisher;


    /**
     * @param user
     * @return userId
     */
    @PerformanceLogging
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        UserJoinEvent userJoinEvent = new UserJoinEvent(this, user.getName());
        applicationEventPublisher.publishEvent(userJoinEvent);
        return user.getId();
    }

    /**
     *
     * @return all users
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * @param userId
     * @return user with userId
     */
    public User findUser(Long userId) {
        return userRepository.findOne(userId);
    }

    private void validateDuplicateUser(User user) {
        List<User> dupUsers = userRepository.findByName(user.getName());
        if (!dupUsers.isEmpty()) {
            throw new IllegalArgumentException("The user with same name exists already.");
        }
    }
}
