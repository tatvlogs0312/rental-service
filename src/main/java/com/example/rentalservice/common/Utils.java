package com.example.rentalservice.common;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

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
}
