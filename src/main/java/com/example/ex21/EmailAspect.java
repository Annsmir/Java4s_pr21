package com.example.ex21;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@org.aspectj.lang.annotation.Aspect
public class EmailAspect {

    @Autowired
    SpringEmailService sender;
    
    @Before("databaseWriteAccessMethods()")
    public void logMethodAcceptionEntityAnnotatedBean(JoinPoint jp) {

        StringBuilder sb = new StringBuilder();

        for( Object o : jp.getArgs() ) {
            sb.append(o.toString());
            sb.append(' ');
        }

        sender.sendMail("Database write notificaton", jp.toLongString() + " with args " + sb.toString());
    }

    @Pointcut("execution(* com.example.ex21.*Service*.create*(..))")
    public void create() {}
    @Pointcut("execution(* com.example.ex21.*Service*.update*(..))")
    public void update() {}
    @Pointcut("execution(* com.example.ex21.*Service*.delete*(..))")
    public void delete() {}

    @Pointcut("update() || delete() || create()")
    public void databaseWriteAccessMethods() {}


}
