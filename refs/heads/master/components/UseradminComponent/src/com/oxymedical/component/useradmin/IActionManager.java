package com.oxymedical.component.useradmin;

public interface IActionManager 
{
	void addAction(IAction action);
	IAction getAction(Integer actionId);
}
