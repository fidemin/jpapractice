package org.yunhongmin.shop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yunhongmin.shop.domain.User;
import org.yunhongmin.shop.repository.UserRepository;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void joinSuccess() {
        // Given
        User user = new User();
        String name = "yunhong";
        user.setName(name);

        // When
        userService.join(user);

        // Then
        assertEquals(name, user.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void joinFailure() {
        // Given
        User user = new User();
        String name = "yunhong";
        user.setName(name);
        userRepository.save(user);

        // When
        User dupUser = new User();
        user.setName(name);
        userService.join(user);

        // Then
        fail("IllegalArgumentException required");
    }
}