package com.example.test_task.service;

import com.example.test_task.dto.business.ContactDto;
import com.example.test_task.dto.enums.ContactTypeEnum;
import com.example.test_task.dto.request.ContactChangeRequest;
import com.example.test_task.dto.request.ContactUpdateRequest;
import com.example.test_task.exception.CountConstraintException;
import com.example.test_task.exception.UniqueConstraintException;
import com.example.test_task.persistence.entity.EmailDataEntity;
import com.example.test_task.persistence.entity.PhoneDataEntity;
import com.example.test_task.persistence.mapper.EmailDataMapper;
import com.example.test_task.persistence.mapper.PhoneDataMapper;
import com.example.test_task.persistence.repo.EmailDataRepository;
import com.example.test_task.persistence.repo.PhoneDataRepository;
import com.example.test_task.util.ContactValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    private static final String UNIQUE_EXCEPTION_TEMPLATE = 
            "These %s: %s have already been added by another user";
    private static final String COUNT_EXCEPTION_TEMPLATE =
            "The user must have at least one %s";
    
    private final PhoneDataRepository phoneDataRepository;
    private final PhoneDataMapper phoneDataMapper;

    private final EmailDataRepository emailDataRepository;
    private final EmailDataMapper emailDataMapper;

    @Transactional
    public List<ContactDto> addContacts(List<ContactChangeRequest> requestList, Long userId) {
        final List<ContactDto> resultList = new ArrayList<>();

        getChangeRequestByEnumMap(requestList).forEach((contactType, requests) -> {
            List<String> requestValueList = requests.stream()
                    .map(ContactChangeRequest::getValue)
                    .toList();

            switch (contactType) {
                case PHONE -> {
                    validateContactList(ContactTypeEnum.PHONE, requestValueList);

                    List<PhoneDataEntity> entityForSaveList = requests.stream()
                            .map(r -> new PhoneDataEntity(userId, r.getValue()))
                            .toList();

                    try {
                        List<PhoneDataEntity> savedEntityList = phoneDataRepository.saveAll(entityForSaveList);
                        log.info("Saved {} phone data for user_id: {}", savedEntityList.size(), userId);

                        resultList.addAll(savedEntityList.stream()
                                .map(phoneDataMapper::toDto)
                                .toList());
                    } catch (Exception e) {
                        log.error("Failed to save {} phone data for user_id: {} due to {}",
                                entityForSaveList.size(),
                                userId,
                                e.getMessage());
                    }
                }
                case EMAIL -> {
                    validateContactList(ContactTypeEnum.EMAIL, requestValueList);

                    List<EmailDataEntity> entityForSaveList = requests.stream()
                            .map(r -> new EmailDataEntity(userId, r.getValue()))
                            .toList();

                    try {
                        List<EmailDataEntity> savedEntityList = emailDataRepository.saveAll(entityForSaveList);
                        log.info("Saved {} email data for user_id: {}", savedEntityList.size(), userId);

                        resultList.addAll(savedEntityList.stream()
                                .map(emailDataMapper::toDto)
                                .toList());
                    } catch (Exception e) {
                        log.error("Failed to save {} email data for user_id: {} due to {}",
                                entityForSaveList.size(),
                                userId,
                                e.getMessage());
                    }
                }
            }
        });

        return resultList;
    }

    @Transactional
    public void deleteContacts(List<ContactChangeRequest> requestList, Long userId) {
        getChangeRequestByEnumMap(requestList)
                .forEach((contactType, requests) -> {
                    List<String> requestValueList = requests.stream()
                            .map(ContactChangeRequest::getValue)
                            .toList();

                    switch (contactType) {
                        case PHONE -> {
                            if (phoneDataRepository.countAllByUserId(userId) > requestList.size()) {
                                try {
                                    phoneDataRepository.deleteByUserIdAndPhoneIn(userId, requestValueList);
                                    log.info("Deleted {} phone data for user_id: {}", requestValueList.size(), userId);
                                } catch (Exception e) {
                                    log.error("Failed to delete {} phone data for user_id: {} due to {}",
                                            requestValueList.size(),
                                            userId,
                                            e.getMessage());
                                }
                            } else {
                                throw new CountConstraintException(COUNT_EXCEPTION_TEMPLATE.formatted("phone"));
                            }
                        }
                        case EMAIL -> {
                            if (emailDataRepository.countAllByUserId(userId) > requestList.size()) {
                                try {
                                    emailDataRepository.deleteByUserIdAndEmailIn(userId, requestValueList);
                                    log.info("Deleted {} email data for user_id: {}", requestValueList.size(), userId);
                                } catch (Exception e) {
                                    log.error("Failed to delete {} email data for user_id: {} due to {}",
                                            requestValueList.size(),
                                            userId,
                                            e.getMessage());
                                }
                            } else {
                                throw new CountConstraintException(COUNT_EXCEPTION_TEMPLATE.formatted("email"));
                            }
                        }
                    }
                });
    }

    @Transactional
    public List<ContactDto> updateContacts(List<ContactUpdateRequest> requestList, Long userId) {
        final List<ContactDto> resultList = new ArrayList<>();

        getUpdateRequestByEnumMap(requestList).forEach((contactType, requests) -> {
            List<String> requestOldValueList = requests.stream()
                    .map(ContactUpdateRequest::getOldValue)
                    .toList();
            List<String> requestNewValueList = requests.stream()
                    .map(ContactUpdateRequest::getNewValue)
                    .toList();

            switch (contactType) {
                case PHONE -> {
                    validateContactList(ContactTypeEnum.PHONE, requestNewValueList);
                    
                    Map<String, List<PhoneDataEntity>> entityByOldValueMap =
                            phoneDataRepository.findAllByUserIdAndPhoneIn(userId, requestOldValueList)
                                    .stream()
                                    .collect(Collectors.groupingBy(PhoneDataEntity::getPhone));

                    List<PhoneDataEntity> entityForSaveList = requests.stream()
                            .map(r -> {
                                PhoneDataEntity entity = entityByOldValueMap.get(r.getOldValue()).get(0);
                                entity.setPhone(r.getNewValue());
                                return entity;
                            })
                            .toList();
                    try {
                        List<PhoneDataEntity> savedEntityList = phoneDataRepository.saveAll(entityForSaveList);
                        log.info("Updated {} email data for user_id: {}",
                                savedEntityList.size(),
                                userId);

                        resultList.addAll(savedEntityList.stream()
                                .map(phoneDataMapper::toDto)
                                .toList());
                    } catch (Exception e) {
                        log.error("Failed to update {} email data for user_id: {} due to {}",
                                entityForSaveList.size(),
                                userId,
                                e.getMessage());
                    }
                }
                case EMAIL -> {
                    validateContactList(ContactTypeEnum.EMAIL, requestNewValueList);

                    Map<String, List<EmailDataEntity>> entityByOldValueMap =
                            emailDataRepository.findAllByUserIdAndEmailIn(userId, requestOldValueList)
                                    .stream()
                                    .collect(Collectors.groupingBy(EmailDataEntity::getEmail));

                    List<EmailDataEntity> entityForSaveList = requests.stream()
                            .map(r -> {
                                EmailDataEntity entity = entityByOldValueMap.get(r.getOldValue()).get(0);
                                entity.setEmail(r.getNewValue());
                                return entity;
                            })
                            .toList();
                    try {
                        List<EmailDataEntity> savedEntityList = emailDataRepository.saveAll(entityForSaveList);
                        log.info("Updated {} phone data for user_id: {}",
                                savedEntityList.size(),
                                userId);

                        resultList.addAll(savedEntityList.stream()
                                .map(emailDataMapper::toDto)
                                .toList());
                    } catch (Exception e) {
                        log.error("Failed to update {} phone data for user_id: {} due to {}",
                                entityForSaveList.size(),
                                userId,
                                e.getMessage());
                    }
                }
            }
        });

        return resultList;
    }

    public void checkPhonesExistence(List<String> phones) {
        List<String> existingPhoneList = phoneDataRepository.findExistingPhones(phones);
        if (!existingPhoneList.isEmpty()) {
            throw new UniqueConstraintException(UNIQUE_EXCEPTION_TEMPLATE
                    .formatted("phones", existingPhoneList));
        }
    }

    public void checkEmailsExistence(List<String> emails) {
        List<String> existingEmailList = emailDataRepository.findExistingEmails(emails);
        if (!existingEmailList.isEmpty()) {
            throw new UniqueConstraintException(UNIQUE_EXCEPTION_TEMPLATE
                    .formatted("emails", existingEmailList));
        }
    }

    private Map<ContactTypeEnum, List<ContactChangeRequest>> getChangeRequestByEnumMap
            (List<ContactChangeRequest> requestList) {
        return requestList.stream()
                .collect(Collectors.groupingBy(ContactChangeRequest::getContact));
    }

    private Map<ContactTypeEnum, List<ContactUpdateRequest>> getUpdateRequestByEnumMap
            (List<ContactUpdateRequest> requestList) {
        return requestList.stream()
                .collect(Collectors.groupingBy(ContactUpdateRequest::getContact));
    }

    private void validateContactList(ContactTypeEnum contactType, List<String> contactList) {
        switch (contactType) {
            case PHONE: {
                ContactValidator.validateContactList(ContactTypeEnum.PHONE, contactList);
                checkPhonesExistence(contactList);
            }

            case EMAIL: {
                ContactValidator.validateContactList(ContactTypeEnum.EMAIL, contactList);
                checkEmailsExistence(contactList);
            }
        }
    }
}
