package com.sekarre.helpcenternotification.factories;


import com.sekarre.helpcenternotification.domain.Role;
import com.sekarre.helpcenternotification.domain.User;

import java.util.Set;

public class UserMockFactory {

    public static User getDefaultUserMock() {
        return User.builder()
                .id(1L)
                .username("Default User")
                .password("password")
                .build();
    }

    public static User getCurrentUserMock() {
        return User.builder()
                .id(2L)
                .username("Current User")
                .password("password")
                .build();
    }

    public static User getUserWithGivenIdMock(Long userId) {
        return User.builder()
                .id(userId)
                .username("Current User")
                .password("password")
                .build();
    }

    public static User getUserWithRolesMock(Set<Role> roles) {
        return User.builder()
                .id(3L)
                .roles(roles)
                .username("Current User")
                .password("password")
                .build();
    }
}