package com.oxymedical.component.baseComponent.annotations;
import java.lang.annotation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
public @interface EventPublisher
{
	String topic();
   String scope() default "[global]"; 
}

