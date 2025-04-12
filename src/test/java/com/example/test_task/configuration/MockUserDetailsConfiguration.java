package com.example.test_task.configuration;

import com.example.test_task.security.CustomUserDetails;
import com.example.test_task.security.CustomUserDetailsService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockUserDetailsConfiguration {
    public static final Long KNOWN_USER_ID = 1L;
    public static final String KNOWN_USER_EMAIL = "ivan12@fgh.com";

    @Bean
    @Primary
    public CustomUserDetailsService customUserDetailsService() {
        CustomUserDetailsService mockService = Mockito.mock(CustomUserDetailsService.class);

        // Возвращаем пользователя с нужным userId
        CustomUserDetails userDetails = new CustomUserDetails(KNOWN_USER_EMAIL, "password", KNOWN_USER_ID);

        Mockito.when(mockService.loadUserByUsername(KNOWN_USER_EMAIL))
            .thenReturn(userDetails);

        Mockito.when(mockService.loadByUserId(KNOWN_USER_ID)).thenReturn(userDetails);

        return mockService;
    }
}
