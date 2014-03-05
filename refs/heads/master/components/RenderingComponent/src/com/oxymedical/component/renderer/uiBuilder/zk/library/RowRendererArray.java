package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Column;
import org.zkoss.zul.api.Columns;

import com.oxymedical.component.renderer.data.ColumnDef;
import com.oxymedical.component.renderer.data.GridDef;
import com.oxymedical.component.renderer.data.GridDefConstants;

public class RowRendererArray implements RowRenderer
{
	private org.zkoss.zk.ui.event.EventListener onItemChangeListener = null;
	private org.zkoss.zk.ui.event.EventListener onSelectChangeListener = null;
	private GridDef gridDef = null;
	
	public RowRendererArray(){
		this.onItemChangeListener = new org.zkoss.zk.ui.event.EventListener(){  
		    public void onEvent(Event event){
		    	onItemChange(event);
		    };
		};
		this.onSelectChangeListener = new org.zkoss.zk.ui.event.EventListener(){  
		    public void onEvent(Event event){
		    	onSelectChange(event);
		    };
		};
	}
	
	public void setGridDef(GridDef gridDef){
		this.gridDef = gridDef;
	}
	public GridDef getGridDef() {
		return gridDef;
	}

	private void onSelectChange(Event event){
		Component ctrl = event.getTarget();
		Row row = null;
		if(ctrl.getParent() instanceof Cell){
			row = (Row)ctrl.getParent().getParent();
		}else{
			row = (Row)ctrl.getParent();
		}
		Checkbox chkBx = (Checkbox)ctrl;
		row.setAttribute(GridDefConstants.Attr_RowSelected, Boolean.toString(chkBx.isChecked()));
	}
	
	private void onItemChange(Event event){
		Component ctrl = event.getTarget();
		Row row = null;
		if(ctrl.getParent() instanceof Cell){
			row = (Row)ctrl.getParent().getParent();
		}else{
			row = (Row)ctrl.getParent();
		}
		row.setAttribute(GridDefConstants.Attr_RowEdited, "true");
	}
	
	private boolean checkColType(int colIndex, String colType){
		boolean match = false;
		if(this.gridDef != null){
			if(this.gridDef.getColumns() != null){
				if(colIndex < this.gridDef.getColumns().length){
					String colDataType = this.gridDef.getColumns()[colIndex].getType();
					if(colDataType.contains("_")){
						colDataType = colDataType.split("_")[0];
					}
					if(colDataType.equalsIgnoreCase(colType)){
						match = true;
					}
				}
			}
		}
		return match;
	}
	
	@Override
	public void render(Row row, java.lang.Object data) throws Exception{
		if(data != null){
			String[] _data = (String[])data;
			Component itemParent = null;
			for(int i = 0; i < _data.length; i++){
				itemParent = row;
				if(i == 0){
					if(this.gridDef != null && this.gridDef.getSelectable() == true){
						Cell cell = new Cell();
						cell.setWidth(GridDefConstants.Cell_Width);
						itemParent = cell;
						Checkbox ctrl = new Checkbox();
						ctrl.addEventListener(Events.ON_CHECK, this.onSelectChangeListener);
						ctrl.setAttribute("OnlyForSelect", "true");
						ctrl.setWidth(GridDefConstants.Select_CkhBx_Width);
						ctrl.setParent(itemParent);
					}
				}
				if(this.gridDef == null || (checkColType(i, GridDefConstants.ColType_Label))){
					Label ctrl = new Label(_data[i]);
					ctrl.setWidth(GridDefConstants.Inline_Ctrl_Width);
					ctrl.setParent(itemParent);
				}else if(checkColType(i, GridDefConstants.ColType_Textbox)){
				    Textbox ctrl = new Textbox();
					ctrl.addEventListener(Events.ON_CHANGE, this.onItemChangeListener);
					ctrl.setText(_data[i]);
					ctrl.setInplace(true);
					ctrl.setWidth(GridDefConstants.Inline_Ctrl_Width);
					ctrl.setParent(itemParent);
				}else if(checkColType(i, GridDefConstants.ColType_Datebox)){
					Datebox ctrl = new Datebox();
					ctrl.addEventListener(Events.ON_CHANGE, this.onItemChangeListener);
					ctrl.setFormat(this.gridDef.getColumns()[i].getFormat());
					ctrl.setText(_data[i]);
					ctrl.setInplace(true);
					ctrl.setWidth(GridDefConstants.Inline_Ctrl_Width);
					ctrl.setParent(itemParent);
				}
				if(i == 0){
					if(itemParent instanceof Cell){
						itemParent.setParent(row);
					}
				}
			}
		}
	}
}

