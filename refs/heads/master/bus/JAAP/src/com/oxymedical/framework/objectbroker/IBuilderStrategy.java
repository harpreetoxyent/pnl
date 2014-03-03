package com.oxymedical.framework.objectbroker;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IBuilderStrategy 
{
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild);
	public Object TearDown(IBuilderContext context, Object item);
}
