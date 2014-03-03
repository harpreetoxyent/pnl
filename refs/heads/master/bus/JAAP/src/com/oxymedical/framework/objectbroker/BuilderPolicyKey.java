package com.oxymedical.framework.objectbroker;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class BuilderPolicyKey 
{
	private Class policyType;
	private Class buildType;
	private String buildID;

	public BuilderPolicyKey(Class policyInterface, Class typePolicyAppliesTo, String idPolicyAppliesTo)
	{

		this.buildType = typePolicyAppliesTo;
		this.buildID = idPolicyAppliesTo;
		this.policyType = policyInterface;
	}
	public boolean equals(Object obj)
	{
		BuilderPolicyKey other = (BuilderPolicyKey)obj ;
		if (other == null)
			return false;
		//object are same if they are of same class type and
		//have same id
		boolean classNameFlg = false; 
		if(this.policyType == null && other.policyType == null)
			classNameFlg = true;
		else if((this.policyType != null && other.policyType != null))
			classNameFlg = (this.policyType.getName() == other.policyType.getName());
		else 
			classNameFlg = false;
		boolean buildTypeFlg = false;
		
		if(this.buildType.getName()==null && other.buildType.getName()==null)
			buildTypeFlg = true;
		else if((this.buildType != null && other.buildType != null))
			buildTypeFlg = (this.buildType.getName() == other.buildType.getName());
		else 
			buildTypeFlg = false;
		
		
		boolean buildIdFlg = (this.buildID == other.buildID);
		return (classNameFlg && buildTypeFlg && buildIdFlg);
	}
	//Not Implemented in Prototype
	public int hashCode()
	{
		return System.identityHashCode(1L);
	}	
	
}
