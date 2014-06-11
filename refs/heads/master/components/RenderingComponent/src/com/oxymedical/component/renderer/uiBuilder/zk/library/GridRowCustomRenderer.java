package com.oxymedical.component.renderer.uiBuilder.zk.library;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.ext.*;
import org.zkoss.zk.au.*;
import org.zkoss.zk.au.out.*;
import org.zkoss.zul.*;


public class GridRowCustomRenderer implements RowRenderer 
{
	public static Window window = null;
	public static Combobox methodCombobox;
	ComboEventListener comboListener= new ComboEventListener();
    public void render(final Row row, final java.lang.Object data) 
    {
    	window = (Window)row.getParent().getRoot();
        System.out.println("Inside GridRowCustomRenderer ");
    	String[] ary = (String[]) data;
        new Label(ary[0]).setParent(row);
        methodCombobox =  new Combobox(ary[1]);
        comboListener.onEvent(methodCombobox);
        methodCombobox.setSelectedIndex(0);
        methodCombobox.setParent(row);
        new  Combobox(ary[2]).setParent(row);
        new Textbox(ary[3]).setParent(row);
        new Textbox(ary[4]).setParent(row);
        new Combobox(ary[5]).setParent(row);
        new Textbox(ary[6]).setParent(row);
        new Textbox(ary[7]).setParent(row);
        new Textbox(ary[8]).setParent(row);
        new Textbox(ary[9]).setParent(row);
        new Textbox(ary[10]).setParent(row);
        new Textbox(ary[11]).setParent(row);
        new Combobox(ary[12]).setParent(row);
        new Textbox(ary[13]).setParent(row);
        new Textbox(ary[14]).setParent(row);
        new Textbox(ary[15]).setParent(row);
        new Textbox(ary[16]).setParent(row);
        new Combobox(ary[17]).setParent(row);
        new Textbox(ary[18]).setParent(row);
        new Combobox(ary[19]).setParent(row);
        new Textbox(ary[20]).setParent(row);
        System.out.println("GridRowCustomRenderer ary String" + ary[0] + ary[1] + ary[2]);
    }
}
