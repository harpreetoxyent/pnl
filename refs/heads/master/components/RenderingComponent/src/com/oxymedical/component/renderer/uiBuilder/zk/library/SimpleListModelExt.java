package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;

public class SimpleListModelExt extends SimpleListModel {
	
	private static final long serialVersionUID = 1L;

	public SimpleListModelExt(List<String> data) {
        super(data);
    }
	
	public SimpleListModelExt(String[] data) {
        super(data);
    }
	
	@Override
	public ListModel getSubModel(Object value, int nRows){   
		final String idx = value == null ? "" : value.toString();
		final LinkedList<String> data = new LinkedList<String>();
		int _dataSize = getSize();
		for(int i = 0; i < _dataSize; i++){
			if (idx.equals("") || getElementAt(i).toString().toLowerCase().startsWith(idx.toLowerCase())){
				data.add(getElementAt(i).toString());
			}
		}
		return new SimpleListModel(data);
	}
}
