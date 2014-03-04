package com.oxymedical.component.useradmin.impl;

import java.util.List;

import com.oxymedical.component.useradmin.ILayout;
import com.oxymedical.component.useradmin.model.Layout;

public class LayoutImpl implements ILayout
{

	public Layout addLayout(String groupId, String userId, boolean privateLayout, String parentLayoutId, String name, String title, String type, boolean hidden, String friendlyURL) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteLayout(String layoutId, String ownerId) {
		// TODO Auto-generated method stub
		
	}

	public void deleteLayout(Layout layout, boolean updateLayoutSet) {
		// TODO Auto-generated method stub
		
	}

	public void deleteLayouts(String ownerId) {
		// TODO Auto-generated method stub
		
	}

	public Layout getLayout(String plid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Layout getLayout(String layoutId, String ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getLayouts(String ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getLayouts(String ownerId, String parentLayoutId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLayouts(String ownerId, String parentLayoutId, String[] layoutIds) {
		// TODO Auto-generated method stub
		
	}

	public Layout updateLayout(String layoutId, String ownerId, String parentLayoutId, String name, String title, String languageId, String type, boolean hidden, String friendlyURL) {
		// TODO Auto-generated method stub
		return null;
	}

	public Layout updateLayout(String layoutId, String ownerId, String typeSettings) {
		// TODO Auto-generated method stub
		return null;
	}

	public Layout updateLookAndFeel(String layoutId, String ownerId, String themeId, String colorSchemeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
