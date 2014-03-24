package com.oxymedical.component.rulesComponent.rete.productionMemory;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.rulesComponent.IFactHandle;


public class FactHandle implements IFactHandle {
	
	List<Object> factList = new ArrayList<Object>();
	
	public IFactHandle getFactHandle(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IFactHandle> getFactHandleList() {		
		return null;
	}

	public Object getObject() {
		
		return null;
	}

	public void setObject(Object object) {
		this.factList.add(object);
	}

}
