package com.oxymedical.component.workflowComponent.command.tool;


public interface IObservable
{
	public void addObserver(IObserver o);
	public void deleteObserver(IObserver o);
	public void notifyObservers();
	public void notifyObservers(Object obj);
	public void deleteObservers();
	/*
	 * public void setChanged(); 
	 * public void clearChanged();
	 */
	public boolean hasChanged();
	public int countObservers();
}
