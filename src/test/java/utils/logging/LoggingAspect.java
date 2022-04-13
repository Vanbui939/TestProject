package utils.logging;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

  private LocalDateTime beforeActionTime;

  @Before("execution(* selenium.core.BasePage+.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    StringBuilder builder = new StringBuilder();
    for (Object value : args) {
      builder.append("\"").append(value.toString()).append("\", ");
    }
    if (builder.toString().endsWith(", ")) {
      builder.delete(builder.length() - 2, builder.length());
    }
    Signature signature = joinPoint.getSignature();
    if (builder.length() == 0) {
      Log.action("Starting action: " + signature.getDeclaringTypeName() + "." + signature.getName()
          + "()");
    } else {
      Log.action("Starting action: " + signature.getDeclaringTypeName() + "." + signature.getName()
          + "(" + builder.toString() + ")");
    }
    beforeActionTime = LocalDateTime.now();
  }

  @AfterThrowing(pointcut = "execution(* selenium.core.BasePage+.*(..))", throwing = "error")
  public void afterFailureAction(JoinPoint joinPoint, Throwable error) throws IOException {
    String actionDuration = elapseDuration(beforeActionTime);
    Log.action("Action failed: " + error + ". (" + actionDuration + " sec).");

  }

  @AfterReturning(pointcut = "execution(* selenium.core.BasePage+.*(..))", returning = "result")
  public void afterAction(JoinPoint joinPoint, Object result) throws IOException {
    String actionDuration = elapseDuration(beforeActionTime);
    Log.action("Action Done! (" + actionDuration + " sec).");
  }

  @Before("execution(* appium.android.core.AndroidScreen+.*(..))")
  public void logBeforeAndroid(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    StringBuilder builder = new StringBuilder();
    for (Object value : args) {
      builder.append("\"" + value.toString() + "\", ");
    }
    if (builder.toString().endsWith(", ")) {
      builder.delete(builder.length() - 2, builder.length());
    }
    Signature signature = joinPoint.getSignature();
    if (builder.length() == 0) {
      Log.action("Starting action: " + signature.getDeclaringTypeName() + "." + signature.getName()
          + "()");
    } else {
      Log.action("Starting action: " + signature.getDeclaringTypeName() + "." + signature.getName()
          + "(" + builder.toString() + ")");
    }
    beforeActionTime = LocalDateTime.now();
  }

  @AfterThrowing(pointcut = "execution(* appium.android.core.AndroidScreen+.*(..))",
      throwing = "error")
  public void afterFailureActionAndroid(JoinPoint joinPoint, Throwable error) throws IOException {
    String actionDuration = elapseDuration(beforeActionTime);
    Log.action("Action failed: " + error + ". (" + actionDuration + " sec).");

  }

  @AfterReturning(pointcut = "execution(* appium.android.core.AndroidScreen+.*(..))",
      returning = "result")
  public void afterActionAndroid(JoinPoint joinPoint, Object result) throws IOException {
    String actionDuration = elapseDuration(beforeActionTime);
    Log.action("Action Done! (" + actionDuration + " sec).");
  }

  // @Around("@annotation(cucumber.api.java.en.Given)")
  // public Object logsGivenStep(ProceedingJoinPoint thisJoinPoint) throws Throwable {
  // MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
  // Method method = thisJoinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
  // signature.getMethod().getParameterTypes());
  // Given monitored = method.getAnnotation(Given.class);
  // Log.step("Given: " + monitored.value().toString());
  // Object result = thisJoinPoint.proceed();
  // return result;
  // }
  //
  // @Around("@annotation(cucumber.api.java.en.When)")
  // public Object logsWhenStep(ProceedingJoinPoint thisJoinPoint) throws Throwable {
  // MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
  // Method method = thisJoinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
  // signature.getMethod().getParameterTypes());
  // When monitored = method.getAnnotation(When.class);
  // Log.step("When: " + monitored.value().toString());
  // Object result = thisJoinPoint.proceed();
  // return result;
  // }
  //
  // @Around("@annotation(cucumber.api.java.en.Then)")
  // public Object logsThenStep(ProceedingJoinPoint thisJoinPoint) throws Throwable {
  // MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
  // Method method = thisJoinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
  // signature.getMethod().getParameterTypes());
  // Then monitored = method.getAnnotation(Then.class);
  // Log.step("Then: " + monitored.value().toString());
  // Object result = thisJoinPoint.proceed();
  // return result;
  // }
  //
  // @Around("@annotation(cucumber.api.java.en.And)")
  // public Object logsAndStep(ProceedingJoinPoint thisJoinPoint) throws Throwable {
  // MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
  // Method method = thisJoinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
  // signature.getMethod().getParameterTypes());
  // And monitored = method.getAnnotation(And.class);
  // Log.step("And: " + monitored.value().toString());
  // Object result = thisJoinPoint.proceed();
  // return result;
  // }

  public String elapseDuration(LocalDateTime before) {
    LocalDateTime after = LocalDateTime.now();
    Duration d = Duration.between(before, after);
    return String.valueOf((float) d.toMillis() / 1000);
  }
}
