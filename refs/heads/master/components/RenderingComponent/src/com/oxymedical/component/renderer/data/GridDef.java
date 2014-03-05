package com.oxymedical.component.renderer.data;

public class GridDef {

	private ColumnDef [] columns = null;
	private boolean selectable = false;
	private String highlightExpression = "";
	private String selectExpression = "";
	private String highlightColor = "";
	public boolean showDeleteCol = false;
	public boolean isSortable = false;
	
	public GridDef(String [] colTypes){
		ColumnDef [] colsDef = new ColumnDef[colTypes.length];
		ColumnDef colDef = null;
		String [] colTypeParts;
		for(int i = 0; i < colTypes.length; i++){
			colTypeParts = colTypes[i].split("[|]");
			
			colDef = new ColumnDef(colTypeParts[GridDefConstants.ColType_Index]);
			if(colTypeParts[GridDefConstants.ColType_Index].equalsIgnoreCase(GridDefConstants.ColType_Datebox)){
				if(colTypeParts.length > 1){
					colDef.setFormat(colTypeParts[GridDefConstants.ColFormat_Index]);
				}else{
					colDef.setFormat(GridDefConstants.Default_Date_Format);
				}
			}
			
			colsDef[i] = colDef;
		}
		this.setColumns(colsDef);
	}

	public String getHighlightExpression() {
		return highlightExpression;
	}

	private void setHighlightExpression(String highlightExpression) {
		this.highlightExpression = highlightExpression;
	}

	public String getHighlightColor() {
		return highlightColor;
	}

	private void setHighlightColor(String highlightColor) {
		this.highlightColor = highlightColor;
	}

	public ColumnDef[] getColumns() {
		return columns;
	}
	public boolean getShowDeleteCol() {
		return showDeleteCol;
	}

	public void setShowDeleteCol(boolean showDeleteCol) {
		this.showDeleteCol = showDeleteCol;
	}
	
	public boolean getIsSortable() {
		return isSortable;
	}

	public void setIsSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}
	
	public void setColumns(ColumnDef[] columns) {
		this.columns = columns;
	}

	public boolean getSelectable() {
		return selectable;
	}
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
	public void setHighlightInfo(String exp, String color){
		setHighlightExpression(exp);
		setHighlightColor(color);
	}
	public String getSelectExpression() {
		return selectExpression;
	}

	public void setSelectExpression(String selectExpression) {
		this.selectExpression = selectExpression;
	}
}
