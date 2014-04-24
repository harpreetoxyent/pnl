/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeBuilder;

/**
 * @author vka
 *
 */
public class DataPatternAttributes implements IDataPatternAttributes {
	private String dataPatternId = null;

	/**
	 * @return the dataPatternId
	 */
	public String getDataPatternId() {
		return dataPatternId;
	}

	/**
	 * @param dataPatternId the dataPatternId to set
	 */
	public void setDataPatternId(String dataPatternId) {
		this.dataPatternId = dataPatternId;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		else if (obj == null) return false;
		else if (this.getClass() != obj.getClass()) return false;
		
		DataPatternAttributes dpa = (DataPatternAttributes)obj;
		return (dataPatternId == dpa.dataPatternId || (dataPatternId != null && dataPatternId.equals(dpa.dataPatternId)));
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + (null == dataPatternId ? 0 : dataPatternId.hashCode());
		return hash;
	}
}
