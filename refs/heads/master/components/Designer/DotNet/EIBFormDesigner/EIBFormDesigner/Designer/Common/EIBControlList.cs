using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;

namespace EIBFormDesigner.Designer
{
    class EIBControlList
    {
        public static Hashtable controlCollectionList = new Hashtable();
        static EIBControlList()
        {
            controlCollectionList.Add("EIBButton", FormDesignerConstants.ButtonControl);
            controlCollectionList.Add("EIBCheckbox", FormDesignerConstants.CheckboxControl);
            controlCollectionList.Add("EIBCombobox", FormDesignerConstants.ComboControl);
            controlCollectionList.Add("EIBLabel", FormDesignerConstants.LabelControl);
            controlCollectionList.Add("EIBBrowse", FormDesignerConstants.BrowseControl);
            controlCollectionList.Add("EIBLine", FormDesignerConstants.LineControl);
            controlCollectionList.Add("EIBPanel", FormDesignerConstants.FrameControl);
            controlCollectionList.Add("EIBJasper", FormDesignerConstants.JasperControl);
            controlCollectionList.Add("EIBRadioGroup", FormDesignerConstants.RadioGroupControl);
            controlCollectionList.Add("EIBPicture", FormDesignerConstants.PictureControl);
            controlCollectionList.Add("EIBRadioButton", FormDesignerConstants.RadioControl);
            controlCollectionList.Add("EIBTabControl", FormDesignerConstants.MultiTabControl);
            controlCollectionList.Add("EIBTabPage", FormDesignerConstants.TabPage);
            controlCollectionList.Add("EIBTextBox", FormDesignerConstants.TextBoxControl);
            controlCollectionList.Add("EIBTreeView", FormDesignerConstants.TreeControl);
            controlCollectionList.Add("EIBTreeNode", FormDesignerConstants.TreeNodeControl);
            //controlCollectionList.Add("EIBMenuBar", FormDesignerConstants.MenuBarControl);
            controlCollectionList.Add("EIBMenu", FormDesignerConstants.MenuControl);
            controlCollectionList.Add("EIBMenuItem", FormDesignerConstants.MenuItemControl);
            controlCollectionList.Add("EIBTable", FormDesignerConstants.TableControl);
            controlCollectionList.Add("EIBRelation", FormDesignerConstants.RelationControl);
            controlCollectionList.Add("EIBNode", FormDesignerConstants.NodeControl);
            controlCollectionList.Add("EIBNodeRelation", FormDesignerConstants.NodeConnectorControl);
            controlCollectionList.Add("EIBSearch", FormDesignerConstants.SearchControl);
            controlCollectionList.Add("EIBListbox", FormDesignerConstants.ListControl);
            controlCollectionList.Add("EIBGrid", FormDesignerConstants.GridControl);
            controlCollectionList.Add("EIBDatePicker", FormDesignerConstants.DatePickerControl);
            controlCollectionList.Add("EIBTime", FormDesignerConstants.TimeControl);
            controlCollectionList.Add("EIBPlaceHolder", FormDesignerConstants.PlaceHolderControl);
            controlCollectionList.Add("EIBItem", FormDesignerConstants.UserControl);
            controlCollectionList.Add("EIBPaging", FormDesignerConstants.PagingControl);
            controlCollectionList.Add("EIBVMenuBar", FormDesignerConstants.MenuBarControl);
            controlCollectionList.Add("EIBCalender", FormDesignerConstants.CalenderControl);
            controlCollectionList.Add("EIBImageBrowse", FormDesignerConstants.ImageBrowseControl);
            controlCollectionList.Add("EIBSchedular", FormDesignerConstants.SchedularControl);
            controlCollectionList.Add("EIBApplet", FormDesignerConstants.AppletControl);
            controlCollectionList.Add("EIBLattice", FormDesignerConstants.LatticeControl);
            controlCollectionList.Add("EIBScrollableRow", FormDesignerConstants.ScrollableRowControl);
        }
        public static string GetRenderingElement(string controlType)
        {
            if (controlCollectionList.ContainsKey(controlType))
            {
                return (string)controlCollectionList[controlType];
            }
            return controlType;
        }
    }
}
