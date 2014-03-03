package com.oxymedical.component.baseComponent.annotations;
import java.lang.annotation.*;
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
public @interface EventSubscriber 
{
   String topic();
}