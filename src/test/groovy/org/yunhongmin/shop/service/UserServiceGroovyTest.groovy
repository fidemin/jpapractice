package org.yunhongmin.shop.service

import org.yunhongmin.shop.domain.User
import org.yunhongmin.shop.repository.UserRepository
import spock.lang.Specification

class UserServiceGroovyTest extends Specification {
    UserRepository userRepository = Mock(UserRepository)
    UserService userService

    def "join Success"() {
        given:
        userService = new UserService(
                userRepository: userRepository
        )

        def name = "Yunhong"
        def user = new User()
        user.setName(name)
        userRepository.findByName(name) >> []

        when:
        userService.join(user)

        then:
        noExceptionThrown()
    }

    def "join Fail"() {
        given:
        userService = new UserService(
                userRepository: userRepository
        )

        def name = "Yunhong"
        def user = new User()
        user.setName(name)
        userRepository.findByName(name) >> [new User()]

        when:
        userService.join(user)

        then:
        thrown(IllegalArgumentException.class)
    }

}
