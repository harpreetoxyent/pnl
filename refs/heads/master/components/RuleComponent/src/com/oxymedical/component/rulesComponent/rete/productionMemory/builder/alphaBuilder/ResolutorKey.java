package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder;

/**
 * This class uniquely identifies a condition with the two keys namely join1 and join2
 * 
 * @author Oxyent Medical
 *
 */
public class ResolutorKey {
	
	private String join1;
	
	private String join2;
	
	public ResolutorKey()	
	{
		this(null, null);
	}
	public ResolutorKey(String join1, String join2)
	{
		this.join1 = join1;
		this.join2 = join2;
	}
	
	public String getJoin1() {
		return join1;
	}
	
	public String getJoin2() {
		return join2;
	}
	
	public boolean equals(Object obj)
	{
		ResolutorKey other = (ResolutorKey)obj ;
		if (other == null)
			return false;
		//object are same if they are of same conditions
		
		String conditionJoin1 = this.join1;
		String conditionJoin2 = this.join2;
		
		String conditionA = other.join1;
		String conditionB = other.join2;	
		boolean matched1 = false, matched2 = false;
		matched1 = match(conditionJoin1, conditionA) || match(conditionJoin1, conditionB);
		if (matched1)
			matched2 = match(conditionJoin2, conditionA) || match(conditionJoin2, conditionB);
			
		return (matched1 && matched2);
	}
	
	public int hashCode()
	{
		return 0;
	}

	private boolean match(String st1, String st2) {		
		return (st1.trim()).equals(st2.trim());
	}
}
