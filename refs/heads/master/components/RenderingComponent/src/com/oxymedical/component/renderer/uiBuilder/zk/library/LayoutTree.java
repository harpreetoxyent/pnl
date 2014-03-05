package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.util.List;
//import com.oxymedical.component.renderer.uiBuilder.zk.controlLayout.Controller;

import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

public class LayoutTree extends Tree {

	public LayoutTree() {
	}

	public void onSelect() {
		Treeitem item = getSelectedItem();

		Treechildren children = item.getTreechildren();

		if (item != null) {
             /*Controller controller = new Controller();
             List list = controller.getList(item.getValue());
             for(int i=0; i<list.size(); i++) {
	             Treeitem newItem = new Treeitem();
	             String str = (String)list.get(i);
				 newItem.setLabel(str);
				 newItem.setValue(str);
	             newItem.setParent(children);
	         }*/
		}
	}

	public void onClick() {
		Treeitem item = getSelectedItem();

		Treechildren children = item.getTreechildren();

		if (item != null) {
			System.out.println(item.getValue());
			 Treeitem newItem = new Treeitem();
			 newItem.setLabel("TestClick");
			 newItem.setValue("TestClick");
             newItem.setParent(children);
		}
	}
}