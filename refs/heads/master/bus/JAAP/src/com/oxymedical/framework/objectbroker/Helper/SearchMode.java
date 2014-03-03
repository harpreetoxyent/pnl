package com.oxymedical.framework.objectbroker.Helper;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public enum SearchMode 
{
	/// <summary>
	/// Search local first and then up the containment heirarchy as needed
	/// </summary>
	Up,

	/// <summary>
	/// Search local only
	/// </summary>
	Local
}
