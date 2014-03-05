package com.oxymedical.component.renderer.uiBuilder.zk.library;

import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


//in uilibrary this call was rowRenderder
public class RowRendererItem implements RowRenderer
{
	public void render(Row row, java.lang.Object data) throws Exception
	{
		if(data!=null)
		{
			new Label((String)data).setParent(row);
		}
	}

	
}

 class ItemRenderer implements ListitemRenderer
{
	public void render(Listitem li, java.lang.Object data) throws Exception
	{
		if(data!=null)
		{
			//new Listcell(data).setParent(li);
			Listcell listcell=new Listcell((String)data);
			listcell.setValue(data);
			listcell.setParent(li);
		}
	}
}
