package com.oxymedical.component.useradmin;

public interface ILayout 
{
	public com.oxymedical.component.useradmin.model.Layout addLayout(java.lang.String groupId,
			java.lang.String userId, boolean privateLayout,
			java.lang.String parentLayoutId, java.lang.String name,
			java.lang.String title, java.lang.String type, boolean hidden,
			java.lang.String friendlyURL);

	public void deleteLayout(java.lang.String layoutId, java.lang.String ownerId);

	public void deleteLayout(com.oxymedical.component.useradmin.model.Layout layout,
			boolean updateLayoutSet);
		
	public void deleteLayouts(java.lang.String ownerId);
		
	public com.oxymedical.component.useradmin.model.Layout getLayout(java.lang.String plid);

	public com.oxymedical.component.useradmin.model.Layout getLayout(
		java.lang.String layoutId, java.lang.String ownerId);
	
	public java.util.List getLayouts(java.lang.String ownerId);

	public java.util.List getLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId);

	public void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds);

	public com.oxymedical.component.useradmin.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String languageId,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL);

	public com.oxymedical.component.useradmin.model.Layout updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String typeSettings);

	public com.oxymedical.component.useradmin.model.Layout updateLookAndFeel(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String themeId, java.lang.String colorSchemeId);
	
}
