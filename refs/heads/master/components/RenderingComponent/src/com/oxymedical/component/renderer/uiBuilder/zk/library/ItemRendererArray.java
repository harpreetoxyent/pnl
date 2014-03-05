package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;


import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.component.renderer.data.GridDef;
import com.oxymedical.component.renderer.data.GridDefConstants;

public class ItemRendererArray implements ListitemRenderer{
	
	private org.zkoss.zk.ui.event.EventListener onItemChangeListener = null;
	private GridDef gridDef = null;
	private boolean deleteColHeaderPresent = false;
	private org.zkoss.zk.ui.event.EventListener onDeleteBtnClick = null;
	
	public ItemRendererArray(){
		this.onItemChangeListener = new org.zkoss.zk.ui.event.EventListener(){  
		    public void onEvent(Event event){
		    	onItemChange(event);
		    };
		};
		this.onDeleteBtnClick = new org.zkoss.zk.ui.event.EventListener(){  
		    public void onEvent(Event event){
		    	try {
					onDeleteClick(event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    };
		};
	}
	
	public void setGridDef(GridDef gridDef){
		this.gridDef = gridDef;
	}
	public GridDef getGridDef() {
		return gridDef;
	}
	
	private void onItemChange(Event event){
		Component ctrl = event.getTarget();
		Listitem listItem = (Listitem)ctrl.getParent().getParent();
		listItem.setAttribute(GridDefConstants.Attr_RowEdited, "true");
	}
	private void onDeleteClick(Event event) throws InterruptedException{
		Component ctrl = event.getTarget();
		Listitem listItem_delete = (Listitem)ctrl.getParent().getParent();
		List list_Items = listItem_delete.getListbox().getItems();
		if (Messagebox.YES == Messagebox.show( "Are you sure you want to delete this row?", "Question", Messagebox.CANCEL | Messagebox.YES, Messagebox.QUESTION)) {
			list_Items.remove(listItem_delete);
			return;
		}else {
			return;
		}
	}
	
	private boolean checkColType(int colIndex, String colType){
		boolean match = false;
		if(this.gridDef != null){
			if(this.gridDef.getColumns() != null){
				if(colIndex < this.gridDef.getColumns().length){
					if(this.gridDef.getColumns()[colIndex].getType().equalsIgnoreCase(colType)){
						match = true;
					}
				}
			}
		}
		return match;
	}
	
	private boolean evaluateExpression(String evalExp, String[] _data) throws Exception{
		CharSequence cs_find = null, cs_replace = null;
	    String colIndexStr = null;
	    int colIndex = 0;
	    
	    System.out.println("evalExp before: " + evalExp);
	    while(evalExp.indexOf(GridDefConstants.ColStartPlaceHolder) != -1){
	    	colIndexStr = evalExp.substring((evalExp.indexOf(GridDefConstants.ColStartPlaceHolder) + GridDefConstants.ColStartPlaceHolder.length()), evalExp.indexOf(GridDefConstants.ColEndPlaceHolder));
	    	colIndex = Integer.parseInt(colIndexStr);
	    	cs_find = new String(GridDefConstants.ColStartPlaceHolder + colIndexStr + GridDefConstants.ColEndPlaceHolder);
	    	cs_replace = new String(_data[colIndex]);
	    	evalExp = evalExp.replace(cs_find, cs_replace);
	    }
	    System.out.println("evalExp after: " + evalExp);
	    
	    ScriptEngineManager manager = new ScriptEngineManager(); 
	    ScriptEngine engine = manager.getEngineByName("js");
	    boolean evalResult = Boolean.parseBoolean(engine.eval(evalExp).toString());
	    return evalResult;
	}
	
	public void render(Listitem listItem, java.lang.Object data) throws Exception
	{
		if(data != null){
			String[] _data = (String[])data;
			Component itemParent = null;
			
			//checking highlight information in GridDef (Example: '[column.0]'.equalsIgnoreCase('d1') || [column.3] > 6 )
			if(this.gridDef != null && this.gridDef.getHighlightExpression().length() > 0 && this.gridDef.getHighlightColor().length() > 0){
				String evalExp = this.gridDef.getHighlightExpression();
			    
				boolean evalResult = evaluateExpression(evalExp, _data);
				System.out.println("evalResult Highlight: " + evalResult);
			    
			    if(evalResult == true){
			    	listItem.setStyle("background:" + this.gridDef.getHighlightColor());
			    }
			}
			
			//checking select information in GridDef
			if(this.gridDef != null && this.gridDef.getSelectExpression().length() > 0){
				String evalExp = this.gridDef.getSelectExpression();
			    
				boolean evalResult = evaluateExpression(evalExp, _data);
				System.out.println("evalResult Select: " + evalResult);
			    
				if(evalResult == true){
			    	if(listItem.isCheckable() == true){
			    		listItem.setSelected(true);
			    	}
				}
			}
			
			if(this.gridDef != null && gridDef.getShowDeleteCol() == true && deleteColHeaderPresent == false){
				    Listhead listhead = listItem.getListbox().getListhead();
					Listheader listheader_del =new Listheader();
					listheader_del.setLabel("Delete");
					listheader_del.setWidth("50px");
					listheader_del.setStyle("text-align:center");
					listhead.appendChild(listheader_del);
					deleteColHeaderPresent = true;
			 }
			for(int i = 0; i < _data.length; i++){
				Listcell listCell = new Listcell();
				itemParent = listCell;
				if(this.gridDef == null){
					//Listcell listcell=new Listcell(_data[i]);
					listCell.setValue(_data[i]);
					listCell.setLabel(_data[i]);
					//listcell.setParent(li);
				}else if(checkColType(i, GridDefConstants.ColType_Label)){
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
				itemParent.setParent(listItem);
			}
			if(this.gridDef != null && gridDef.getShowDeleteCol() == true){
				Listcell deletelistCell = new Listcell();
				deletelistCell.setStyle("text-align:center");
				Button delBtn = new Button();
				delBtn.setWidth("25px");
				delBtn.setLabel("X");
				delBtn.setStyle("text-align:center");
				delBtn.addEventListener(Events.ON_CLICK, this.onDeleteBtnClick);
				delBtn.setParent(deletelistCell);
				deletelistCell.setParent(listItem);
			}
		}
	}
}