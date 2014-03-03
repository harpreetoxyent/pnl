package com.oxymedical.framework.objectbroker.Helper;

import java.util.Hashtable;
import java.util.Enumeration;

import com.oxymedical.framework.objectbroker.*;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class PolicyList 
{
	private Hashtable <BuilderPolicyKey,IBuilderPolicy> policies= new Hashtable<BuilderPolicyKey, IBuilderPolicy>();
	public PolicyList()
	{
		
	}
	public PolicyList(PolicyList[] policiesToCopy)
	{
		if (policiesToCopy != null)
		{
			for(PolicyList policyList : policiesToCopy)
			{
				AddPolicies(policyList);
			}		
		}
	}
	public PolicyList(PolicyList policyToCopy)
	{
			AddPolicies(policyToCopy);
	}
	public int getCount()
	{
		return policies.size();
	}
	public void AddPolicies(PolicyList policiesToCopy)
	{
		if (policiesToCopy != null)
		{
			BuilderPolicyKey key;
			Enumeration ePolicy = policiesToCopy.policies.keys();
			while(ePolicy.hasMoreElements())
			{
				key = (BuilderPolicyKey)ePolicy.nextElement();
				policies.put(key ,(IBuilderPolicy)policiesToCopy.policies.get(key));
			}
		}
	}
	public void Clear(Class policyInterface, Class typePolicyAppliesTo, String idPolicyAppliesTo)
	{
		policies.remove(new BuilderPolicyKey(policyInterface, typePolicyAppliesTo, idPolicyAppliesTo));
	}
	public void ClearAll()
	{
		policies.clear();
	}
/*	public <TPolicyInterface> void ClearDefault(TPolicyInterface T)
	{
		ClearDefault(T.getClass());
	}*/

	/// Removes a default policy.
	public void ClearDefault(Class policyInterface)
	{
		Clear(policyInterface, null, null);
	}
	@SuppressWarnings("unchecked")
	public <TPolicyInterface extends IBuilderPolicy> TPolicyInterface get(Class policyInterface, Class typePolicyAppliesTo, String idPolicyAppliesTo)
	{
		BuilderPolicyKey key = new BuilderPolicyKey(policyInterface, typePolicyAppliesTo, idPolicyAppliesTo);
		TPolicyInterface policy;

		if ((policy = (TPolicyInterface)policies.get(key)) !=null)
		{
			return policy;
		}

		BuilderPolicyKey defaultKey = new BuilderPolicyKey(policyInterface, null, null);
		if ((policy = (TPolicyInterface)policies.get(defaultKey)) !=null)
			return policy;

		return null;
	}
	
	public <TPolicyInterface extends IBuilderPolicy> void set(TPolicyInterface policy, Class typePolicyAppliesTo, String idPolicyAppliesTo)
	{
		set(policy.getClass(), policy, typePolicyAppliesTo, idPolicyAppliesTo);
	}

	public void set(Class policyInterface, IBuilderPolicy policy, Class typePolicyAppliesTo, String idPolicyAppliesTo)
	{
		BuilderPolicyKey key = new BuilderPolicyKey(policyInterface, typePolicyAppliesTo, idPolicyAppliesTo);
		policies.put(key, policy);
	}
}

	
