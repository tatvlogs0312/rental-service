package com.example.rentalservice.redis;

import com.example.rentalservice.model.key_cloak.KeyCloakTokenResDTO;
import com.example.rentalservice.proxy.KeyCloakProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakCacheService {

    private final String PREDIX = "keycloak_superuser_token";

    private final RedisService redisService;

    private final KeyCloakProxy keyCloakProxy;

    public String getToken() {
        String tokenFromRedis = redisService.getValue(PREDIX);
        if (StringUtils.isNotBlank(tokenFromRedis)) {
            return tokenFromRedis;
        } else {
            KeyCloakTokenResDTO keyCloakTokenResDTO = keyCloakProxy.loginKeyCloak();
            String token = keyCloakTokenResDTO.getAccessToken();
            redisService.setValue(PREDIX, token, keyCloakTokenResDTO.getExpiresIn());

            return token;
        }
    }
}
