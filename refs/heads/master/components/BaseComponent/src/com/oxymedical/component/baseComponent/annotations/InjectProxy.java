package com.oxymedical.component.baseComponent.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medicalís prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectProxy 
{
	String implementationClass();
	String idComponent();
}
