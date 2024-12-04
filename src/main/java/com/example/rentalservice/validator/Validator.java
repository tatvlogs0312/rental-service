package com.example.rentalservice.validator;

import com.example.rentalservice.exception.ApplicationException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String PHONE_REGEX = "^(\\+84|0)[3-9][0-9]{8}$";
    private static final String CMND_CCCD_REGEX = "^[0-9]{9}$|^[0-9]{12}$";

    public static void emailValidator(String email) {
        if (StringUtils.isNotBlank(email)) {
            Boolean isValid = Pattern.matches(EMAIL_REGEX, email);
            if (BooleanUtils.isFalse(isValid)) {
                throw new ApplicationException("Email không đúng định dạng");
            }
        }
    }

    public static void phoneNumberValidator(String phoneNumber) {
        if (StringUtils.isNotBlank(phoneNumber)) {
            Boolean isValid = Pattern.matches(PHONE_REGEX, phoneNumber);
            if (BooleanUtils.isFalse(isValid)) {
                throw new ApplicationException("Số điện thoại không đúng định dạng");
            }
        }
    }

    public static void identityNumberValidator(String identityNumber) {
        if (StringUtils.isNotBlank(identityNumber)) {
            Boolean isValid = Pattern.matches(CMND_CCCD_REGEX, identityNumber);
            if (BooleanUtils.isFalse(isValid)) {
                throw new ApplicationException("Số điện thoại không đúng định dạng");
            }
        }
    }

    public static void genderValidator(String gender) {
        if (StringUtils.isNotBlank(gender)) {
            if (!StringUtils.equalsIgnoreCase(gender, "Nam") && !StringUtils.equalsIgnoreCase(gender, "Nữ")) {
                throw new ApplicationException("Giới tính không đúng định dạng");
            }
        }
    }
}
