using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Controls
{
    public static class EIBControlCollection
    {
        public static Dictionary<string, string> Buttonlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> CheckBoxlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> ComboBoxlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Labellist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Browselist = new Dictionary<string, string>();//
        public static Dictionary<string, string> Linelist = new Dictionary<string, string>();//
        public static Dictionary<string, string> Panellist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Jasperlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> RadioGrouplist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Picturelist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> RadioButtonlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> TabControllist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> TabPagelist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> TextBoxlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> TreeViewlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> TreeNodelist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> MenuBarlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Menulist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> MenuItemlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> ListBoxlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Gridlist = new Dictionary<string, string>();
        public static Dictionary<string, string> DatePickerlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> PlaceHolderlist = new Dictionary<string, string>();
        public static Dictionary<string, string> Itemlist = new Dictionary<string, string>();
        public static Dictionary<string, string> Searchlist = new Dictionary<string, string>();
        public static Dictionary<string, string> Timelist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Paginglist = new Dictionary<string, string>();
        public static Dictionary<string, string> HMenuBarlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Calenderlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> ImageBrowselist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Schedularlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Appletlist = new Dictionary<string, string>();//.
        public static Dictionary<string, string> Latticelist = new Dictionary<string, string>();//.
        static string name;

        public static string CheckControlForUniqueness<T>(string ControlName)
        {
            
            //string name="";
            ControlName = ControlName.ToLower();
            //name = ControlName;
            switch (typeof(T).Name)
            {
                #region control
                case "EIBButton":
                    if (Buttonlist.ContainsKey(ControlName))
                    {
                        EIBButton.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.ButtonControl + EIBButton.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBCheckbox":
                    if (CheckBoxlist.ContainsKey(ControlName))
                    {
                        EIBCheckbox.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.CheckboxControl + EIBCheckbox.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBCombobox":
                    if (ComboBoxlist.ContainsKey(ControlName))
                    {
                        EIBCombobox.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.ComboControl + EIBCombobox.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                
                case "EIBLabel":
                    if (Labellist.ContainsKey(ControlName))
                    {
                        EIBLabel.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.LabelControl + EIBLabel.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBPanel":
                    if (Panellist.ContainsKey(ControlName))
                    {
                        EIBPanel.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.FrameControl + EIBPanel.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBJasper":
                    if (Jasperlist.ContainsKey(ControlName))
                    {
                        EIBJasper.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.JasperControl + EIBJasper.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                
                case "EIBRadioGroup":
                    if (RadioGrouplist.ContainsKey(ControlName))
                    {
                        EIBRadioGroup.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.RadioGroupControl + EIBRadioGroup.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBRadioButton":
                    if (RadioButtonlist.ContainsKey(ControlName))
                    {
                        EIBRadioButton.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.RadioControl + EIBRadioButton.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBPicture":
                    if (Picturelist.ContainsKey(ControlName))
                    {
                        EIBPicture.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.PictureControl + EIBPicture.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                
                case "EIBTabControl":
                    if (TabControllist.ContainsKey(ControlName))
                    {
                        EIBTabControl.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.MultiTabControl.ToLower() + EIBTabControl.counter);

                    }
                   else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBTabPage":
                    if (TabPagelist.ContainsKey(ControlName))
                    {
                        EIBTabPage.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.TabPage + EIBTabPage.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBTextBox":
                    if (TextBoxlist.ContainsKey(ControlName))
                    {
                        EIBTextBox.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.TextBoxControl + EIBTextBox.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                
                case "EIBTreeView":
                    if (TreeViewlist.ContainsKey(ControlName))
                    {
                        EIBTreeView.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.TreeControl + EIBTreeView.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBTreeNode":
                    if (TreeNodelist.ContainsKey(ControlName))
                    {
                        EIBTreeNode.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.TreeNodeControl + EIBTreeNode.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBMenuBar":
                    if (MenuBarlist.ContainsKey(ControlName))
                    {
                        EIBMenuBar.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.MenuBarControl + EIBMenuBar.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBVMenuBar":
                    if (HMenuBarlist.ContainsKey(ControlName))
                    {
                        EIBVMenuBar.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.MenuBarControl.ToLower() + EIBVMenuBar.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBMenuItem":
                    if (MenuItemlist.ContainsKey(ControlName))
                    {
                        EIBMenuItem.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.MenuItemControl + EIBMenuItem.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBMenu":
                    if (Menulist.ContainsKey(ControlName))
                    {
                        EIBMenu.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.MenuControl + EIBMenu.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBListbox":
                    if (ListBoxlist.ContainsKey(ControlName))
                    {
                        EIBListbox.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.ListControl + EIBListbox.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBGrid":
                    if (Gridlist.ContainsKey(ControlName))
                    {
                        EIBGrid.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.GridControl + EIBGrid.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBDatePicker":
                    if (DatePickerlist.ContainsKey(ControlName))
                    {
                        EIBDatePicker.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.DatePickerControl + EIBDatePicker.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBTime":
                    if (Timelist.ContainsKey(ControlName))
                    {
                        EIBTime.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.TimeControl + EIBTime.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBPlaceHolder":
                    if (PlaceHolderlist.ContainsKey(ControlName))
                    {
                        EIBPlaceHolder.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.PlaceHolderControl + EIBPlaceHolder.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBBrowse":
                    if (Browselist.ContainsKey(ControlName))
                    {
                        EIBBrowse.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.BrowseControl + EIBBrowse.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBPaging":
                    if (Paginglist.ContainsKey(ControlName))
                    {
                        EIBPaging.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.PagingControl + EIBPaging.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBCalender":
                    if (Calenderlist.ContainsKey(ControlName))
                    {
                        EIBCalender.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.CalenderControl + EIBCalender.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBSchedular":
                    if (Schedularlist.ContainsKey(ControlName))
                    {
                        EIBSchedular.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.SchedularControl + EIBSchedular.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBApplet":
                    if (Appletlist.ContainsKey(ControlName))
                    {
                        EIBApplet.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.AppletControl + EIBApplet.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBLattice":
                    if (Latticelist.ContainsKey(ControlName))
                    {
                        EIBLattice.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.LatticeControl + EIBLattice.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                case "EIBImageBrowse":
                    if (ImageBrowselist.ContainsKey(ControlName))
                    {
                        EIBImageBrowse.counter++;
                        CheckControlForUniqueness<T>(FormDesignerConstants.ImageBrowseControl + EIBImageBrowse.counter);

                    }
                    else
                    {
                        name = ControlName;
                    }
                    break;
                #endregion
            }
            return name;
                
        }
        public static void RemoveControlFromCollection(string ControlType,string Controlname)
        {
            switch (ControlType)
            {
                case "label":
                    if(Labellist.ContainsKey(Controlname))
                        Labellist.Remove(Controlname);
                    break;
                case "button":
                    if (Buttonlist.ContainsKey(Controlname))
                        Buttonlist.Remove(Controlname);
                    break;
                case "panel":
                    if (Panellist.ContainsKey(Controlname))
                        Panellist.Remove(Controlname);    
                    break;
                case "jasper":
                    if (Jasperlist.ContainsKey(Controlname))
                        Jasperlist.Remove(Controlname);
                    break;
                case "checkbox":
                    if (CheckBoxlist.ContainsKey(Controlname))
                        CheckBoxlist.Remove(Controlname);
                    break;
                case "combobox":
                    if (ComboBoxlist.ContainsKey(Controlname))
                        ComboBoxlist.Remove(Controlname);
                    break;
                case "radiogroup":
                    if (RadioGrouplist.ContainsKey(Controlname))
                        RadioGrouplist.Remove(Controlname);
                    break;
                case "picture":
                    if (Picturelist.ContainsKey(Controlname))
                        Picturelist.Remove(Controlname);
                    break;
                case "radiobutton":
                    if (RadioButtonlist.ContainsKey(Controlname))
                        RadioButtonlist.Remove(Controlname);
                    break;
                case "tabcontrol":
                    if (TabControllist.ContainsKey(Controlname))
                        TabControllist.Remove(Controlname);
                    break;
                case "tabpage":
                    if (TabPagelist.ContainsKey(Controlname))
                        TabPagelist.Remove(Controlname);
                    break;

                case "textbox":
                    if (TextBoxlist.ContainsKey(Controlname))
                        TextBoxlist.Remove(Controlname);
                    break;
                case "datepicker":
                    if (DatePickerlist.ContainsKey(Controlname))
                        DatePickerlist.Remove(Controlname);
                    break;
                case "menubar":
                    if (MenuBarlist.ContainsKey(Controlname))
                        MenuBarlist.Remove(Controlname);
                    break;
                case "menu":
                    if (Menulist.ContainsKey(Controlname))
                        Menulist.Remove(Controlname);
                    break;
                case "menuitem":
                    if (MenuItemlist.ContainsKey(Controlname))
                        MenuItemlist.Remove(Controlname);
                    break;
                case "treeview":
                    if (TreeViewlist.ContainsKey(Controlname))
                        TreeViewlist.Remove(Controlname);
                    break;
                case "treenode":
                    if (TreeNodelist.ContainsKey(Controlname))
                        TreeNodelist.Remove(Controlname);
                    break;
                case "listbox":
                    if (ListBoxlist.ContainsKey(Controlname))
                        ListBoxlist.Remove(Controlname);
                    break;
                case "hmenubar":
                    if (HMenuBarlist.ContainsKey(Controlname))
                        HMenuBarlist.Remove(Controlname);
                    break;
                case "calender":
                    if (Calenderlist.ContainsKey(Controlname))
                        Calenderlist.Remove(Controlname);
                    break;
                case "schedular":
                    if (Schedularlist.ContainsKey(Controlname))
                        Schedularlist.Remove(Controlname);
                    break;
                case "applet":
                    if (Appletlist.ContainsKey(Controlname))
                        Appletlist.Remove(Controlname);
                    break;
                case "imagebrowse":
                    if (ImageBrowselist.ContainsKey(Controlname))
                        ImageBrowselist.Remove(Controlname);
                    break;

            }
        }

        public static void ClearCollection()
        {
            Buttonlist.Clear();
            CheckBoxlist.Clear();
            ComboBoxlist.Clear();
            Labellist.Clear();
            Browselist.Clear(); //    
            Linelist.Clear(); //
            Panellist.Clear(); 
            RadioGrouplist.Clear(); 
            Picturelist.Clear(); 
            RadioButtonlist.Clear(); 
            TabControllist.Clear(); 
            TabPagelist.Clear(); 
            TextBoxlist.Clear(); 
            TreeViewlist.Clear(); 
            TreeNodelist.Clear(); 
            MenuBarlist.Clear(); 
            Menulist.Clear(); 
            MenuItemlist.Clear();
            ListBoxlist.Clear();
            Gridlist.Clear(); 
            DatePickerlist.Clear(); 
            PlaceHolderlist.Clear(); 
            Itemlist.Clear(); 
            Searchlist.Clear();
            Jasperlist.Clear();
            Timelist.Clear();
            Paginglist.Clear();
            HMenuBarlist.Clear();
            Calenderlist.Clear();
            ImageBrowselist.Clear();
            Schedularlist.Clear();
            Appletlist.Clear();
            Latticelist.Clear();
            ClearCounter();

        }
        public static void ClearCounter()
        {
            EIBButton.counter=0;
            EIBCheckbox.counter=0;
            EIBCombobox.counter = 0;
            EIBLabel.counter = 0;
            EIBBrowse.counter = 0; //    
            //Line.counter=0; //
            EIBPanel.counter = 0;
            EIBRadioGroup.counter = 0;
            EIBPicture.counter = 0;
            EIBTime.counter = 0;
            EIBRadioButton.counter = 0;
            EIBTabControl.counter = 0;
            EIBTabPage.counter = 0;
            EIBTextBox.counter = 0;
            EIBTreeView.counter = 0;
            EIBTreeNode.counter = 0;
            EIBMenuBar.counter = 0;
            EIBMenu.counter = 0;
            EIBMenuItem.counter = 0;
            EIBListbox.counter = 0;
            EIBGrid.counter=0;
            EIBDatePicker.counter = 0;
            EIBPlaceHolder.counter = 0;
            EIBItem.counter = 0;
            EIBSearch.counter = 0;
            EIBJasper.counter = 0;
            EIBTime.counter = 0;
            EIBPaging.counter = 0;
            EIBVMenuBar.counter = 0;
            EIBCalender.counter = 0;
            EIBSchedular.counter = 0;
            EIBApplet.counter = 0;
            EIBImageBrowse.counter = 0;
            EIBLattice.counter = 0;
        }
    }
}
