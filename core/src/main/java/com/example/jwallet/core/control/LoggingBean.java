package com.example.jwallet.core.control;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundConstruct;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
@Logged
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingBean {

    @Inject
    Logger logger;

    @AroundInvoke
    //@AroundConstruct
    public Object logMethod(final InvocationContext invocationContext) throws Exception {
        logger.log(Level.FINE,
            () -> String.format("Method %s invoked at %s", invocationContext.getMethod().getName(),
                LocalDateTime.now(ZoneOffset.UTC)));
        return invocationContext.proceed();
    }
}
