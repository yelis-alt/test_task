package com.example.test_task.service;

import com.example.test_task.api.dto.business.UserDto;
import com.example.test_task.api.dto.request.RegistrationRequest;
import com.example.test_task.persistence.entity.AccountEntity;
import com.example.test_task.persistence.entity.EmailDataEntity;
import com.example.test_task.persistence.entity.PhoneDataEntity;
import com.example.test_task.persistence.entity.UserEntity;
import com.example.test_task.persistence.mapper.AccountMapper;
import com.example.test_task.persistence.mapper.EmailDataMapper;
import com.example.test_task.persistence.mapper.PhoneDataMapper;
import com.example.test_task.persistence.mapper.UserMapper;
import com.example.test_task.persistence.repo.AccountRepository;
import com.example.test_task.persistence.repo.EmailDataRepository;
import com.example.test_task.persistence.repo.PhoneDataRepository;
import com.example.test_task.persistence.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final AccountRepository accountRepository;

    private final UserMapper userMapper;
    private final PhoneDataMapper phoneDataMapper;
    private final EmailDataMapper emailDataMapper;
    private final AccountMapper accountMapper;

    @Transactional
    public UserDto register(RegistrationRequest request) {
        log.debug("Attempting to register a new user with name: {}", request.getName());

        try {
            UserEntity userEntity = new UserEntity(
                    null,
                    request.getName(),
                    request.getDateOfBirth(),
                    passwordEncoder.encode(request.getPassword()));
            UserEntity savedUserEntity = userRepository.save(userEntity);

            EmailDataEntity emailDataEntity = new EmailDataEntity(
                    null, savedUserEntity.getId(), request.getEmail());
            EmailDataEntity savedEmailDataEntity = emailDataRepository.save(emailDataEntity);
            PhoneDataEntity phoneDataEntity = new PhoneDataEntity(
                    null, savedUserEntity.getId(), request.getPhone());
            PhoneDataEntity savedPhoneDataEntity = phoneDataRepository.save(phoneDataEntity);

            AccountEntity accountEntity = new AccountEntity(
                    null, savedUserEntity.getId(), new BigDecimal("0.00"), new BigDecimal("0.00"));
            AccountEntity savedAccountEntity = accountRepository.save(accountEntity);

            UserDto userDto = userMapper.toDto(savedUserEntity);
            userDto.setPhoneDataDtoList(List.of(phoneDataMapper.toDto(savedPhoneDataEntity)));
            userDto.setEmailDataDtoList(List.of(emailDataMapper.toDto(savedEmailDataEntity)));
            userDto.setAccountDto(accountMapper.toDto(savedAccountEntity));

            return userDto;
        } catch (Exception e) {
            log.debug("Failed to register a new user with name: {} due to {}", request.getName(), e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
