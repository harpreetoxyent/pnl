package com.oxymedical.component.baseComponent.maintenance.annotations;
import java.lang.annotation.*;
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
public @interface MaintenanceSubscriber 
{
  
}