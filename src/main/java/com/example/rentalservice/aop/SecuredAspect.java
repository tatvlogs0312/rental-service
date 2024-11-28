package com.example.rentalservice.aop;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.exception.AuthorizationException;
import com.example.rentalservice.exception.ExceptionEnums;
import com.example.rentalservice.exception.ForbiddenException;
import com.example.rentalservice.redis.RedisService;

import java.lang.reflect.Method;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecuredAspect {

    private final RedisService redisService;

    @Before(value = "@annotation(com.example.rentalservice.aop.Secured)")
    public void before(JoinPoint j) {
        String username = JwtUtils.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new AuthorizationException(ExceptionEnums.EX_NOT_AUTHORIZATION);
        }

        //Get role for use api
        MethodSignature signature = (MethodSignature) j.getSignature();
        Method method = signature.getMethod();
        var s = method.getAnnotation(Secured.class);
        RoleEnum[] roleEnums = s.roles();
        if (roleEnums.length > 0) {
            String roleOfApi = roleEnums[0].name();
            //Verify role
            if (StringUtils.isNotBlank(roleOfApi)) {
                String roleOfUser = redisService.getValue(username + "_role");
                if (!Objects.equals(roleOfApi, roleOfUser)) {
                    throw new ForbiddenException(ExceptionEnums.EX_FORBIDDEN);
                }
            }
        }


    }
}
