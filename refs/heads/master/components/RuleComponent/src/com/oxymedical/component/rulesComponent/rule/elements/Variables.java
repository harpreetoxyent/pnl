package com.oxymedical.component.rulesComponent.rule.elements;

import com.oxymedical.component.rulesComponent.IVariables;

/**
 * Logical view of Variables.
 * A variable consists of name, id and its type.
 * 
 * @author Oxyent Medical
 *
 */
public class Variables implements IVariables {
	
	private String name;
	private String type;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
