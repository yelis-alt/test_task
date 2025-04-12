package com.example.test_task.api.contoller;

import com.example.test_task.api.dto.business.AccountDto;
import com.example.test_task.api.dto.request.TransferRequest;
import com.example.test_task.config.SecurityConfig;
import com.example.test_task.config.WebMvcConfiguration;
import com.example.test_task.configuration.MockUserDetailsConfiguration;
import com.example.test_task.security.JwtService;
import com.example.test_task.service.FinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static com.example.test_task.configuration.MockUserDetailsConfiguration.KNOWN_USER_ID;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({
    SecurityConfig.class,
    WebMvcConfiguration.class,
    MockUserDetailsConfiguration.class
})
@ExtendWith(MockitoExtension.class)
class FinanceControllerTest {
    @Mock
    private FinanceService financeService;
    @Autowired
    @InjectMocks
    private FinanceController financeController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        financeController.setFinanceService(financeService);
    }

    @Test
    public void runTransfer_withAuthorizedUser() throws Exception {
        String token = jwtService.generateToken(KNOWN_USER_ID);

        when(financeService.transfer(any(TransferRequest.class), anyLong()))
            .thenReturn(new AccountDto(1L, 2L, BigDecimal.valueOf(5d), BigDecimal.valueOf(10d)));

        mockMvc.perform(MockMvcRequestBuilders.post("/finance/transfer")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"toUserId\": 100, \"value\": 10.50}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.userId").value(2L))
            .andExpect(jsonPath("$.initialBalance").value(5d))
            .andExpect(jsonPath("$.balance").value(10d));
    }
}