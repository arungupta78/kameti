package com.demo.advice;

import com.demo.model.AuthenticationRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SignInAspect {
  @Around("@annotation(com.demo.annotation.LogSignIn)")
  public Object logSignIn(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("User logged in at : " + LocalDateTime.now());

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    /*
        System.out.println("full method description: " + signature.getMethod());
        System.out.println("method name: " + signature.getMethod().getName());
        System.out.println("declaring type: " + signature.getDeclaringType());
    */

    //    System.out.println("Method args names:");
    //    Arrays.stream(signature.getParameterNames()).forEach(s -> System.out.println("arg name: "
    // + s));
    //
    //    System.out.println("Method args types:");
    //    Arrays.stream(signature.getParameterTypes()).forEach(s -> System.out.println("arg type: "
    // + s));

    System.out.println("Method args values:");
    Arrays.stream(joinPoint.getArgs())
        .forEach(o -> System.out.println("arg value: " + o.toString()));

    var req = (AuthenticationRequest) joinPoint.getArgs()[0];
    System.out.println("Argument received: " + req);

    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;

    System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
    return proceed;
  }
}
