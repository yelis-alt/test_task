package com.example.test_task.util;

import com.example.test_task.dto.enums.ContactTypeEnum;
import com.example.test_task.exception.RegexException;

import java.util.List;

public class ContactValidator {
    private final static String PHONE_REGEX = "^7\\d{10}$";
    private final static String PHONE_EXCEPTION_TEMPLATE = "Phone number %s is not of valid format";

    private final static String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final static String EMAIL_EXCEPTION_TEMPLATE = "Email %s is not of valid format";

    public static void validateContactList(ContactTypeEnum contactType,
                                              List<String> contactList) {
        switch (contactType) {
            case PHONE: {
                contactList.forEach(phone -> {
                    if (!phone.matches(PHONE_REGEX)) {
                        throw new RegexException(PHONE_EXCEPTION_TEMPLATE.formatted(phone));
                    }
                });
            }
            case EMAIL: {
                contactList.forEach(email -> {
                    if (!email.matches(EMAIL_REGEX)) {
                        throw new RegexException(EMAIL_EXCEPTION_TEMPLATE.formatted(email));
                    }
                });
            }
        }
    }
}

