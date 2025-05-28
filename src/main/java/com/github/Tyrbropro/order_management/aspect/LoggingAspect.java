package com.github.Tyrbropro.order_management.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public final class LoggingAspect {

    @Around("execution( * com.github.Tyrbropro.order_management.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = getCurrentHttpRequest();

        String httpMethod = request != null ? request.getMethod() : "N/A";
        String uri = request != null ? request.getRequestURI() : "N/A";

        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        Long before = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long after = System.currentTimeMillis();

        System.out.printf(
                "[LOG] HTTP %s %s%n-> Method: %s%n-> Args: %s%n-> Result: %s%n-> Lead time: %d mc%n%n",
                httpMethod,
                uri,
                methodName,
                Arrays.toString(args),
                result,
                (after - before)
        );

        return result;
    }

    @Around("execution(* com.github.Tyrbropro.order_management.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable{
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Long before = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long after = System.currentTimeMillis();

        System.out.printf("[LOG] Method: %s%n-> Args: %s%n-> Result: %s%n-> Lead time: %d mc%n%n",
                methodName,
                Arrays.toString(args),
                result,
                (after-before));

        return result;
    }

    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }
}
