package com.oxymedical.component.renderer.data;

public class ColumnDef {

	private String type;
	private String format;
	private String datatype;
	
	public ColumnDef(String colType){
		this.setType(colType);
		if (colType.contains("_")){
		this.setDataType(colType.split("_")[1]);	
		}
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDataType() {
		return datatype;
	}
	
	public void setDataType(String datatype) {
		this.datatype = datatype;
	}

	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
}
