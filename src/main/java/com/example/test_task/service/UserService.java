package com.example.test_task.service;

import com.example.test_task.dto.business.UserDto;
import com.example.test_task.dto.projection.UserProjection;
import com.example.test_task.dto.request.UserFilterRequest;
import com.example.test_task.persistence.entity.EmailDataEntity;
import com.example.test_task.persistence.entity.PhoneDataEntity;
import com.example.test_task.persistence.mapper.EmailDataMapper;
import com.example.test_task.persistence.mapper.PhoneDataMapper;
import com.example.test_task.persistence.mapper.UserMapper;
import com.example.test_task.persistence.repo.EmailDataRepository;
import com.example.test_task.persistence.repo.PhoneDataRepository;
import com.example.test_task.persistence.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final EmailDataRepository emailDataRepository;

    private final UserMapper userMapper;
    private final PhoneDataMapper phoneDataMapper;
    private final EmailDataMapper emailDataMapper;

    @Cacheable("users")
    public Page<UserDto> getWithFilters(UserFilterRequest request, Integer page, Integer size) {
        log.debug("Attempting to fetch users with filters: {}", request);

        try {
            Pageable pageable = PageRequest.of(page, size);

            List<UserProjection> projectionList = userRepository.findWithFilters(
                    request.getDateOfBirth(), request.getPhone(), request.getName(), request.getEmail());

            Set<Long> userIdSet = projectionList
                    .stream()
                    .map(UserProjection::getId)
                    .collect(Collectors.toSet());

            Map<Long, List<PhoneDataEntity>> phoneEntityListByUserIdMap = phoneDataRepository
                    .findAllByUserIdIn(userIdSet)
                    .stream()
                    .collect(Collectors.groupingBy(PhoneDataEntity::getUserId));
            Map<Long, List<EmailDataEntity>> emailEntityListByUserIdMap = emailDataRepository
                    .findAllByUserIdIn(userIdSet)
                    .stream()
                    .collect(Collectors.groupingBy(EmailDataEntity::getUserId));

            List<UserDto> userDtoList = projectionList
                    .stream()
                    .map(p -> {
                        UserDto dto  = userMapper.toDto(p);

                        dto.setPhoneDataDtoList(
                                phoneEntityListByUserIdMap.get(dto.getId())
                                        .stream()
                                        .map(phoneDataMapper::toDto)
                                        .collect(Collectors.toList()));
                        dto.setEmailDataDtoList(
                                emailEntityListByUserIdMap.get(dto.getId())
                                        .stream()
                                        .map(emailDataMapper::toDto)
                                        .collect(Collectors.toList()));

                        return dto;
                    })
                    .collect(Collectors.toList());

            return new PageImpl<>(userDtoList, pageable, userDtoList.size());
        } catch (Exception e) {
            log.error("Failed to get users with filters", e);
            throw new ServiceException("Failed to retrieve users", e);
        }
    }
}
