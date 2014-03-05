using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Event;
using EIBFormDesigner.Properties;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Designer
{
    public partial class PropertyWindow : ToolWindow
    {
        static bool isPropsListInitialised = false;
        public PropertyWindow()
        {
            InitializeComponent();
            if (!isPropsListInitialised)
            {
                fillInvalidProperties();
            }
        }
        public static void fillInvalidProperties()
        {
            List<string> InvalidPropListButton = new List<string>();
            List<string> InvalidPropListLabel = new List<string>();
            List<string> InvalidPropListTextBox = new List<string>();
            List<string> InvalidPropListComboBox = new List<string>();
            List<string> InvalidPropListDatepicker = new List<string>();
            List<string> InvalidPropListTime = new List<string>();
            List<string> InvalidPropListGrid = new List<string>();
            List<string> InvalidPropListLattice = new List<string>(); 
            List<string> InvalidPropListList = new List<string>();
            List<string> InvalidPropListMultiTab = new List<string>();
            InvalidPropListButton.AddRange(new string[] { "FlatAppearance", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseWaitCursor", "AllowDrop", "AutoEllipses", "ContextMenuStrip", "DialogResult", "TabStop" });
            InvalidPropListLabel.AddRange(new string[] { "FlatStyle", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseWaitCursor", "AllowDrop", "AutoEllipses", "ContextMenuStrip", "DialogResult", "TabStop" });
            InvalidPropListTextBox.AddRange(new string[] { "BackColor", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic","UseVisualStyle", "UseWaitCursor", "AllowDrop", "AutoEllipses", "ContextMenuStrip", "DialogResult", "TabStop" });
            InvalidPropListComboBox.AddRange(new string[] { "BackColor", "DrawMode", "DropDownWidth", "DropDownHeight", "ImeMode" });
            InvalidPropListDatepicker.AddRange(new string[] { "FlatAppearance", "BackgroundImage", "BackgroundImageLayout", "BackColor", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseVisualStyle", "UseWaitCursor", "AllowDrop", "AutoEllipses", "ContextMenuStrip", "DialogResult", "TabStop" });
            InvalidPropListTime.AddRange(new string[] { "BackgroundImage", "BackgroundImageLayout", "BackColor", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseVisualStyle", "UseWaitCursor", "AllowDrop", "FlatAppearance", "ContextMenuStrip", "DialogResult", "TabStop", "AutoValidate", "ImeMode" });
            InvalidPropListGrid.AddRange(new string[] { "BackgroundImage", "BackgroundImageLayout", "BackColor", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseVisualStyle", "UseWaitCursor", "AllowDrop", "FlatAppearance", "ContextMenuStrip", "DialogResult", "TabStop", "AutoValidate", "ImeMode" });
            InvalidPropListLattice.AddRange(new string[] { "BackgroundImage", "BackgroundImageLayout", "BackColor", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseVisualStyle", "UseWaitCursor", "AllowDrop", "FlatAppearance", "ContextMenuStrip", "DialogResult", "TabStop", "AutoValidate", "ImeMode" });
            InvalidPropListList.AddRange(new string[] { "BackgroundImage", "BackgroundImageLayout", "BackColor", "ImageAlign", "ImageKey", "ImageList", "ImageIndex", "RightToLeft", "TextImageRelation", "UseMnemonic", "UseVisualStyle", "UseWaitCursor", "AllowDrop", "FlatAppearance", "ContextMenuStrip", "DialogResult", "TabStop" ,"AutoValidate","ImeMode"});
            InvalidPropListMultiTab.AddRange(new string[] { "TabPages" });
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBButton), InvalidPropListButton);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBLabel), InvalidPropListLabel);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBTextBox), InvalidPropListTextBox);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBCombobox), InvalidPropListComboBox);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBDatePicker), InvalidPropListDatepicker);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBTime), InvalidPropListTime);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBGrid), InvalidPropListGrid);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBLattice), InvalidPropListLattice);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBListbox), InvalidPropListList);
            FilteredPropertyGrid.InvalidProps.Add(typeof(EIBTabControl), InvalidPropListMultiTab);
            isPropsListInitialised = true;
        }
        private void controlList_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void propertyGrid_Click(object sender, EventArgs e)
        {

        }

    }
}