package com.oxymedical.framework.objectbroker;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public enum BuilderStage 
{
	/// <summary>
	/// Strategies in this stage run before creation. Typical work done in this stage might
	/// include strategies that use reflection to set policies into the context that other
	/// strategies would later use.
	/// </summary>
	PreCreation,

	/// <summary>
	/// Strategies in this stage create objects. Typically you will only have a single policy-driven
	/// creation strategy in this stage.
	/// </summary>
	Creation,

	/// <summary>
	/// Strategies in this stage work on created objects. Typical work done in this stage might
	/// include setter injection and method calls.
	/// </summary>
	Initialization,

	/// <summary>
	/// Strategies in this stage work on objects that are already initialized. Typical work done in
	/// this stage might include looking to see if the object implements some notification interface
	/// to discover when its initialization stage has been completed.
	/// </summary>
	PostInitialization

}
