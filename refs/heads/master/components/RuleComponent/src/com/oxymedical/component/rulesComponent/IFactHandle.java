package com.oxymedical.component.rulesComponent;

import java.util.List;

public interface IFactHandle {
	
	public List<IFactHandle> getFactHandleList();
	
	public Object getObject();
	
	public void setObject(Object object);
	
	public IFactHandle getFactHandle(Object object);

}
