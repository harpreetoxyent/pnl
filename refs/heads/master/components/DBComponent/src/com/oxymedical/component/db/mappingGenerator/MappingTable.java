/**
 * 
 */
package com.oxymedical.component.db.mappingGenerator;

/**
 * @author vka
 *
 */
public class MappingTable {
	private String firstTable= null;
	private String secondTable= null;
	private String jointTable =null;
	/**
	 * @return the firstTable
	 */
	public String getFirstTable() {
		return firstTable;
	}
	/**
	 * @param firstTable the firstTable to set
	 */
	public void setFirstTable(String firstTable) {
		this.firstTable = firstTable;
	}
	/**
	 * @return the secondTable
	 */
	public String getSecondTable() {
		return secondTable;
	}
	/**
	 * @param secondTable the secondTable to set
	 */
	public void setSecondTable(String secondTable) {
		this.secondTable = secondTable;
	}
	/**
	 * @return the jointTable
	 */
	public String getJointTable() {
		return jointTable;
	}
	/**
	 * @param jointTable the jointTable to set
	 */
	public void setJointTable(String jointTable) {
		this.jointTable = jointTable;
	}
	

}
