package com.demo.advice;

import java.time.LocalDateTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SignInAspect {
  @Around("@annotation(com.demo.annotation.LogSignIn)")
  public Object logSignIn(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("User logged in at : " + LocalDateTime.now());
    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;

    System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
    return proceed;
  }
}
