package org.yunhongmin.shop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yunhongmin.shop.domain.User;

import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class UserServiceTest {
    @Autowired UserService userService;

    @Test
    public void joinSuccess() {
        // Given
        User user = new User();
        String name = "yunhong";
        user.setName(name);

        // When
        Long userId = userService.join(user);
        assertEquals(name, user.getName());
    }

}