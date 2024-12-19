package com.example.rentalservice.common;

import com.example.rentalservice.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.SecureRandom;
import java.util.UUID;

public class Utils {

    private Utils() {}

    public static String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        EnglishCharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        EnglishCharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        EnglishCharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, lowerCaseRule, upperCaseRule, digitRule);
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String generateOTP(int length) {
        if (length <= 0) {
            throw new ApplicationException("Độ dài OTP phải lớn hơn 0");
        }

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = secureRandom.nextInt(10);
            otp.append(digit);
        }

        return otp.toString();
    }

    public static String handleProvince(String province) {
        if (StringUtils.isNotBlank(province)) {
            String provinceReplace = province.replace("Thành phố", "");
            return provinceReplace.trim();
        }

        return province;
    }

    public static String handleDistrict(String district) {
        if (StringUtils.isNotBlank(district)) {
            String districtReplace = district.replaceAll("\\b(Huyện|Thành phố|Quận|Thị xã)\\b", "");
            return districtReplace.trim();
        }

        return district;
    }

    public static String handleWard(String ward) {
        if (StringUtils.isNotBlank(ward)) {
            String wardReplace = ward.replaceAll("\\b(Xã|Phường|Thị trấn)\\b", "");
            return wardReplace.trim();
        }

        return ward;
    }
}
