package com.oxymedical.component.renderer.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Column;
import org.zkoss.zul.api.Columns;
import org.zkoss.zul.api.Row;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import com.oxymedical.component.renderer.uiBuilder.zk.library.CustomComparator;

public class CustomListModel extends ListModelList implements ListModel, ListModelExt { 
	
	private static final long serialVersionUID = 3962989704798956028L;
	private String[][] _data;
	private boolean _asc = false;
	private Component dGridComp;
	
	public CustomListModel(String[][] data, Component dGridComp) {
		super(data);
		this.dGridComp = dGridComp;
		this._data = data; 
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	private String[][] getDataFromGrid() {
		
		String[][] sortData = null;
		int cellCount = 0;
		int rowCount = 0;
		if(dGridComp != null && dGridComp instanceof Grid){		
			Grid dGrid = (Grid)dGridComp;
			rowCount = dGrid.getRows().getChildren().size();
			Row row = (Row) dGrid.getRows().getChildren().get(0);
			cellCount = row.getChildren().size();
			sortData = new String[rowCount][cellCount];
			for(int i = 0; i < dGrid.getRows().getChildren().size(); i++){
				Row dRow = (Row)dGrid.getRows().getChildren().get(i);
				for(int j = 0; j < dRow.getChildren().size(); j++){
					Component rowItem = (Component)dRow.getChildren().get(j);
					sortData[i][j] = getRowItemData(rowItem);	
				}
			}
		}
		return sortData;
	}
		
	private String getRowItemData(Component rowItem){
		String itemData = "";
		if(rowItem instanceof Label){
			itemData = ((Label)rowItem).getValue();
		}else if(rowItem instanceof Textbox){
			itemData = ((Textbox)rowItem).getValue();
		}else if(rowItem instanceof Datebox){
			itemData = ((Datebox)rowItem).getText();
		}
		return itemData;
	}
	
	@SuppressWarnings("unchecked")
	public void sort(Comparator cmpr, boolean ascending)  {
		String[][] newData = getDataFromGrid();
		_data = newData;
		Arrays.sort(_data, cmpr );
		invalidate();
	}
	
	public void invalidate() {
		 fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
		 System.out.println("CONTENTS_CHANGED: ");
	}
	 
	@Override
	public void addListDataListener(ListDataListener arg0) {
		super.addListDataListener(arg0);
	}

	@Override
	public Object getElementAt(int arg0) {
		return _data[arg0];
	}

	@Override
	public int getSize() {
		int size = 0;
		size = super.getSize();
		return size;
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		super.removeListDataListener(arg0);
	}
}

