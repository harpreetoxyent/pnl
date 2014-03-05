using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
using System.Drawing;
using System.Xml;
using EIBFormDesigner.Search;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Event;
using EIBXMLServices;


namespace EIBFormDesigner.XML
{
    class EIBDeserializeServices
    {
        internal static IEIBControl currentControl = null;
        internal static IEIBControl currentTreeControl = null;


        //Derserialize the project manifest file
        public static void DeserializeProjectFile(TreeView treeView, string fileName, XmlNode parentXMLNode)
        {
            BaseWindow.formCounter = 0;
            BaseWindow.dataCounter = 0;
            BaseWindow.workCounter = 0;
            FormDesignerUtilities.formWindowNames.Clear();
            FormDesignerUtilities.dataWindowNames.Clear();
            FormDesignerUtilities.workWindowNames.Clear();

            UIEventManager.form.Controlpool.Clear();
            UIEventManager.form.History.Clear();
            try
            {

                // disabling re-drawing of treeview till all nodes are added
                treeView.BeginUpdate();
                XmlDocument doc = new XmlDocument();
                XmlNode currentXmlNode = null;
                if (parentXMLNode == null)
                {
                    doc.Load(fileName);
                    currentXmlNode = doc.FirstChild;
                }
                else
                {
                    currentXmlNode = parentXMLNode;
                }
                foreach (XmlNode xmlNode in currentXmlNode.ChildNodes)
                {
                    if (xmlNode.NodeType == XmlNodeType.Element)
                    {
                        if (xmlNode.Name == FormDesignerConstants.FormPattern)
                        {
                            EIBControlCollection.ClearCollection();
                            TreeNode newNode = new TreeNode();
                            newNode.Text = xmlNode.Name;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode, FormDesignerConstants.FormPattern);
                        }
                        if (xmlNode.Name == FormDesignerConstants.DataPattern)
                        {
                            TreeNode newNode = new TreeNode();
                            newNode.Text = xmlNode.Name;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode, FormDesignerConstants.DataPattern);
                        }
                        if (xmlNode.Name == FormDesignerConstants.WorkflowPattern)
                        {
                            TreeNode newNode = new TreeNode();
                            newNode.Text = xmlNode.Name;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode, FormDesignerConstants.WorkflowPattern);
                        }
                    }
                }
                foreach (BaseWindow baseWindow in FormDesigner.listFormBaseWindow.Values)
                {
                    //baseWindow.baseFrame.ResumeLayout();
                    // enabling redrawing of treeview after all nodes are added
                    //baseWindow.baseFrame.Invalidate();
                }
            }
            catch (FileNotFoundException)
            {
                MessageBox.Show("Project Manifest.xml File not Found");
            }
            finally
            {
                //foreach (BaseWindow basewindow in FormDesigner.listFormBaseWindow.Values)
                //{
                //    basewindow.baseFrame.ResumeLayout();
                //    // enabling redrawing of treeview after all nodes are added
                //    basewindow.baseFrame.Invalidate();
                //}
                // enabling redrawing of treeview after all nodes are added
                treeView.EndUpdate();
            }
        }

        private static void HandleChildNodes(TreeNode treeNode, XmlNode xmlNode, string windowName)
        {
            foreach (XmlNode xmlChildNode in xmlNode.ChildNodes)
            {
                if (xmlChildNode.NodeType == XmlNodeType.Element)
                {
                    TreeNode newChildNode = new TreeNode();
                    newChildNode.Text = xmlChildNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value;
                    newChildNode.ImageIndex = 6;
                    treeNode.Nodes.Add(newChildNode);
                    if (windowName.Equals(FormDesignerConstants.WorkflowPattern))
                    {
                        FormDesignerUtilities.workWindowNames.Add(newChildNode.Text, newChildNode.Text);
                        BaseWindow.workCounter++;
                    }
                    else if (windowName.Equals(FormDesignerConstants.FormPattern))
                    {
                        if (!FormDesignerUtilities.formWindowNames.Contains(newChildNode.Text))
                        {
                            FormDesignerUtilities.formWindowNames.Add(newChildNode.Text, newChildNode.Text);
                        }
                        BaseWindow baseWindow =  ToolBoxWindow.form.initBaseWindow(FormDesignerConstants.FormPattern, newChildNode.Text, true);
                        try
                        {
                            EIBDeserializeServices.DeserializeWindowXML(EIBXMLUtilities.formFolderName + newChildNode.Text, ToolBoxWindow.form.currentBaseWindow.baseFrame, null);
                            FormDesignerUtilities.LoadUserControl(newChildNode.Text);
                        }
                        catch (Exception)
                        {
                            MessageBox.Show("Error Occured.\n Xml File Was Modified.");
                        }

                    }
                    else if (windowName.Equals(FormDesignerConstants.DataPattern))
                    {
                        FormDesignerUtilities.dataWindowNames.Add(newChildNode.Text, newChildNode.Text);
                        BaseWindow.dataCounter++;
                    }
                }
            }
        }

        //Deserialize individual base windows and there child control
        public static void DeserializeWindowXML(string fileName, Control baseFrame, XmlNode parentXMLNode)
        {
            XmlDocument doc = new XmlDocument();
            XmlNode currentXmlNode = null;
            EIBGrid grid = null;
            EIBLattice lattice = null;
            try
            {

                // disabling re-drawing of treeview till all nodes are added
                fileName = fileName + ".xml";
                if (parentXMLNode == null)
                {
                    doc.Load(fileName);
                    currentXmlNode = doc.FirstChild;
                    baseFrame.Controls.Clear();
                }
                else
                {
                    currentXmlNode = parentXMLNode;
                }
                // baseFrame.Controls.Clear();
                if (currentXmlNode.Name == FormDesignerConstants.BaseWindow)
                {
                    // loading node attributes
                    UpdateControlProperties(baseFrame, currentXmlNode);
                    ((EIBPanel)baseFrame).AutoScroll = true;
                }
                foreach (XmlNode xmlNode in currentXmlNode.ChildNodes)
                {
                    #region Desearilization for each control
                    if (xmlNode.NodeType == XmlNodeType.Element)
                    {

                        if (xmlNode.Name == FormDesignerConstants.TextBoxControl)
                        {
                            EIBTextBox newNode = new EIBTextBox();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBTextBox.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.SchedularControl)
                        {
                            EIBSchedular newNode = new EIBSchedular();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBSchedular.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.AppletControl)
                        {
                            EIBApplet newNode = new EIBApplet();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBApplet.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.ButtonControl)
                        {
                            EIBButton newNode = new EIBButton();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBButton.counter++;
                            //UIEventManager.form.Controlpool.Add(newNode,);
                        }
                        if (xmlNode.Name == FormDesignerConstants.JasperControl)
                        {
                            EIBJasper newNode = new EIBJasper();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBJasper.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.DatePickerControl)
                        {
                            EIBDatePicker newNode = new EIBDatePicker();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBDatePicker.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.TimeControl)
                        {
                            EIBTime newNode = new EIBTime();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            newNode.ShowTimeSplit();
                            EIBTime.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.CalenderControl)
                        {
                            EIBCalender newNode = new EIBCalender();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBCalender.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.LabelControl)
                        {
                            EIBLabel newNode = new EIBLabel();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBLabel.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.RadioControl)
                        {
                            EIBRadioButton newNode = new EIBRadioButton();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBRadioButton.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.CheckboxControl)
                        {
                            EIBCheckbox newNode = new EIBCheckbox();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBCheckbox.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.ComboControl)
                        {
                            EIBCombobox newNode = new EIBCombobox();
                            UpdateControlProperties(newNode, xmlNode);
                            UpdateComboBoxValues(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBCombobox.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.PagingControl)
                        {
                            EIBPaging newNode = new EIBPaging();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBPaging.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.ImageBrowseControl)
                        {
                            EIBImageBrowse newNode = new EIBImageBrowse();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBImageBrowse.counter++;
                        }
                        /*if (xmlNode.Name == FormDesignerConstants.MenuBarControl)
                        {
                            EIBMenuBar.counter++;
                            EIBMenuBar newNode = new EIBMenuBar();
                            UpdateControlProperties(newNode, xmlNode);
                            UpdateMenuValues(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            newNode.Height = 25;
                            newNode.Width = newNode.Parent.Width;
                            newNode.CurrentToolStrip.Dock = DockStyle.Fill;
                            DragDropHandler.RegisterControl(newNode, true, true);
                            newNode.Click += new EventHandler(FormDesigner.eventManager.handleMenuClick);
                            currentControl = newNode;
                        }*/
                        if (xmlNode.Name == FormDesignerConstants.MenuBarControl)
                        {
                            EIBVMenuBar newNode = new EIBVMenuBar();
                            UpdateControlProperties(newNode, xmlNode);
                            UpdateMenuValues(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            newNode.Dock = DockStyle.None;
                            baseFrame.Controls.Add(newNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            newNode.PerformLayout();
                            //newNode.Size = new Size(30, 30);
                            //newNode.Click += new EventHandler(FormDesigner.eventManager.handleMenuClick);
                            currentControl = newNode;
                            EIBVMenuBar.counter++;

                        }
                        if (xmlNode.Name == FormDesignerConstants.FrameControl)
                        {
                            EIBPanel newNode = new EIBPanel();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            Console.Write("Done with Frame");
                            EIBPanel.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.PlaceHolderControl)
                        {
                            EIBPlaceHolder newNode = new EIBPlaceHolder();
                            
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            Console.Write("Done with PlaceHolder");
                            EIBPlaceHolder.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.UserControl)
                        {
                            EIBItem newNode = new EIBItem();
                            
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            //EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            //DragDropHandler.RegisterControl(newNode, true, true);
                            Console.Write("Done with Item");
                            EIBItem.counter++;
                        }

                        if (xmlNode.Name == FormDesignerConstants.RadioGroupControl)
                        {
                            EIBRadioGroup newNode = new EIBRadioGroup();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            //Console.Write("Done with EIBradioGroup");
                            EIBRadioGroup.counter++;
                        }

                        if (xmlNode.Name == FormDesignerConstants.PictureControl)
                        {
                            EIBPicture newNode = new EIBPicture();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBPicture.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.GridControl)
                        {
                            EIBGrid newNode = new EIBGrid();
                            UpdateControlProperties(newNode, xmlNode);
                            currentControl = newNode;
                            baseFrame.Controls.Add(newNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            EIBGrid.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.LatticeControl)
                        {
                            EIBLattice newNode = new EIBLattice();
                            UpdateControlProperties(newNode, xmlNode);
                            currentControl = newNode;
                            baseFrame.Controls.Add(newNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            EIBLattice.counter++;
                        }
                        if (xmlNode.Name.Trim().Equals(FormDesignerConstants.RowControl, StringComparison.CurrentCultureIgnoreCase))
                        {
                            EIBRow newNode = new EIBRow();
                            if (currentControl is EIBGrid)
                            {
                                if (currentControl is EIBGrid)
                                {
                                    grid = (EIBGrid)currentControl;
                                    UpdateControlProperties(newNode, xmlNode);
                                    DragDropHandler.RegisterControl(newNode, true, true);
                                    grid.Rows.Add(newNode);
                                    currentControl = newNode;
                                    EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                                    EIBRow.counter++;
                                }
                            }

                        }
                        //added for LROW
                        if (xmlNode.Name.Trim().Equals(FormDesignerConstants.LRowControl, StringComparison.CurrentCultureIgnoreCase))
                        {
                            EIBRow newNode = new EIBRow();
                            if (currentControl is EIBLattice)
                            {
                                if (currentControl is EIBLattice)
                                {
                                    lattice = (EIBLattice)currentControl;
                                    UpdateControlProperties(newNode, xmlNode);
                                    DragDropHandler.RegisterControl(newNode, true, true);
                                    lattice.Rows.Add(newNode);
                                    IEIBControl parent = currentControl;
                                    currentControl = newNode;
                                    EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                                    currentControl = parent;
                                    EIBRow.counter++;
                                }
                            }

                        }
                        if (xmlNode.Name.Trim().Equals(FormDesignerConstants.ColumnControl, StringComparison.CurrentCultureIgnoreCase))
                        {
                            EIBColumn newNode = new EIBColumn();
                            if (currentControl is EIBRow)
                            {
                                EIBRow row = (EIBRow)currentControl;
                                row.Columns.Add(newNode);
                                DragDropHandler.RegisterControl(newNode, true, true);
                                UpdateControlProperties(newNode, xmlNode);
                            }
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            EIBColumn.counter++;
                        }
                        //added for LCOLUMN
                        if (xmlNode.Name.Trim().Equals(FormDesignerConstants.LColumnControl, StringComparison.CurrentCultureIgnoreCase))
                        {
                            EIBColumn newNode = new EIBColumn();
                            if (currentControl is EIBRow)
                            {
                                EIBRow row = (EIBRow)currentControl;
                                row.Columns.Add(newNode);
                                DragDropHandler.RegisterControl(newNode, true, true);
                                UpdateControlProperties(newNode, xmlNode);
                            }
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            EIBColumn.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.ListControl)
                        {
                            EIBListbox newNode = new EIBListbox();
                            UpdateControlProperties(newNode, xmlNode);
                            UpdateListBoxValues(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBListbox.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.MultiTabControl)
                        {
                            EIBTabControl newNode = new EIBTabControl();
                            UpdateControlProperties(newNode, xmlNode);
                            //newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            EIBTabControl.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.TreeNodeControl)
                        {
                            EIBTreeNode newNode = new EIBTreeNode();
                            currentTreeControl = null;
                            UpdateTreeNodeProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            if ((currentControl != null) && (currentControl is EIBTreeView))
                            {
                                if (!(((EIBTreeView)currentControl).Nodes.Contains(newNode)))
                                {
                                    ((EIBTreeView)currentControl).Nodes.Add(newNode);
                                }
                            }
                            //baseFrame.Controls.Add(newNode);
                            EIBTreeNode.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.TabPage)
                        {
                            EIBTabPage newNode = new EIBTabPage();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            EIBTabPage.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.TreeControl)
                        {
                            EIBTreeView newNode = new EIBTreeView();
                            UpdateControlProperties(newNode, xmlNode);
                            newNode.InitiateSettings(null);
                            baseFrame.Controls.Add(newNode);
                            currentControl = newNode;
                            EIBDeserializeServices.DeserializeWindowXML(null, newNode, xmlNode);
                            DragDropHandler.RegisterControl(newNode, true, true);
                            EIBTreeView.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.SearchControl)
                        {
                            EIBSearch eibSearch = new EIBSearch();
                            UpdateControlProperties(eibSearch, xmlNode);
                            eibSearch.SearchDesign = new EIBFormDesigner.Search.SearchDesigner(baseFrame);
                            eibSearch.SearchDesign.initSearchFrame(baseFrame);
                            UpdateSearchProperties(eibSearch.SearchDesign, xmlNode);
                            baseFrame.Controls.Add(eibSearch);
                            EIBSearch.counter++;
                        }
                        if (xmlNode.Name == FormDesignerConstants.BrowseControl)
                        {
                            EIBBrowse eibBrowse = new EIBBrowse();
                            UpdateControlProperties(eibBrowse, xmlNode);
                            eibBrowse.InitiateSettings(null);
                            baseFrame.Controls.Add(eibBrowse);
                            EIBBrowse.counter++;
                        }
                    }
                    #endregion
                    // moving up to in TreeView if end tag is encountered
                }
            }
            catch (FileNotFoundException ex)
            {
                MessageBox.Show("The File " + ex.FileName + " not found.");
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error Occured.\n Xml File Was Modified."+ex.Message);
            }
            finally
            {

            }
        }
        private static void UpdateMenuValues(IEIBControl menuBar, XmlNode menubarNode)
        {
            //DragDropHandler.RegisterControl(menuBar, true, true);

            if (menubarNode.ChildNodes.Count > 0)
            {
                foreach (XmlNode menuNode in menubarNode.ChildNodes)
                {
                    if (menuNode.Name != "Events" && menuNode.Name != "DataMappings")
                    {
                        EIBMenu menu = new EIBMenu();

                        SetMenuValue(menu, menuNode);
                        menu.InitiateSettings(null);
                        //menu.ControlName = menu.Name;
                        menu.Click += new EventHandler(FormDesigner.eventManager.handleMenuClick);
                        if (menuBar is EIBMenuBar)
                        {
                            ((EIBMenuBar)menuBar).CurrentToolStrip.Items.Add(menu);
                            ((EIBMenuBar)menuBar).CurrentMenu = menu;
                        }
                        else
                        {
                            ((EIBVMenuBar)menuBar).Items.Add(menu);
                            ((EIBVMenuBar)menuBar).CurrentMenu = menu;
                        }
                        if (menuNode.HasChildNodes)
                        {
                            UpdateSubMenuValues(menuBar, menuNode);
                        }
                        else
                        {

                        }
                        EIBMenu.counter++;
                    }
                    //control.Items.Add(childXMLNode.Attributes[XMLServicesConstants.XmlNodeTextAtt].Value.ToString());
                    //foreach (XmlNode Node in menuNode.ChildNodes)
                    //{
                    //    if (Node.Name == FormDesignerConstants.MenuControl)
                    //    {
                    //        EIBMenu submenu = new EIBMenu();
                    //        //submenu.InitiateSettings(null);
                    //        SetMenuValue(submenu, Node);
                    //        submenu.Click += new EventHandler(FormDesigner.eventManager.handleMenuItemClick);
                    //        //menuItem.ControlName = menuItem.Name;
                    //        menuBar.CurrentMenu.DropDownItems.Add(submenu);
                    //        menuBar.CurrentMenu.CurrentMenuItem = menuItem;
                    //        //to deserialise the evnts related to menu items
                    //        SetEIBEventsValue(menuItem, menuItemNode);
                    //    }
                    //    EIBMenuItem menuItem = new EIBMenuItem();
                    //    menuItem.InitiateSettings(null);
                    //    SetMenuValue(menuItem, menuItemNode);
                    //    menuItem.Click += new EventHandler(FormDesigner.eventManager.handleMenuItemClick);
                    //    //menuItem.ControlName = menuItem.Name;
                    //    menuBar.CurrentMenu.DropDownItems.Add(menuItem);
                    //    menuBar.CurrentMenu.CurrentMenuItem = menuItem;
                    //    //to deserialise the evnts related to menu items
                    //    SetEIBEventsValue(menuItem, menuItemNode);
                    //}

                }

            }
            else
            {

            }

        }

        private static void UpdateSubMenuValues(IEIBControl menuBar, XmlNode menuNode)
        {
            //DragDropHandler.RegisterControl(menuBar, true, true);
            if (menuNode.ChildNodes.Count > 0)
            {
                foreach (XmlNode Node in menuNode.ChildNodes)
                {
                    if (Node.Name == FormDesignerConstants.MenuControl)
                    {
                        EIBMenu submenu = new EIBMenu();
                        SetMenuValue(submenu, Node);
                        submenu.InitiateSettings(null);
                        submenu.Click += new EventHandler(FormDesigner.eventManager.handleMenuClick);
                        //menuItem.ControlName = menuItem.Name;
                        if (menuBar is EIBMenuBar)
                        {
                            ((EIBMenuBar)menuBar).CurrentMenu.DropDownItems.Add(submenu);
                        }
                        else
                        {
                            ((EIBVMenuBar)menuBar).CurrentMenu.DropDownItems.Add(submenu);
                        }
                        

                        //to deserialise the evnts related to menu items
                        SetEIBEventsValue(submenu, Node);
                        if (Node.HasChildNodes)
                        {
                            if (menuBar is EIBMenuBar)
                            {
                                ((EIBMenuBar)menuBar).CurrentMenu = submenu; ;
                            }
                            else
                            {
                                ((EIBVMenuBar)menuBar).CurrentMenu = submenu;
                            }
                            
                            UpdateSubMenuValues(menuBar, Node);
                        }
                        //else
                        //{
                        //    menuBar.CurrentMenu = (EIBMenu)menuBar.CurrentMenu.OwnerItem;
                        //}

                        EIBMenu.counter++;
                    }
                    else if (Node.Name == FormDesignerConstants.MenuItemControl)
                    {
                        EIBMenuItem menuItem = new EIBMenuItem();
                        SetMenuValue(menuItem, Node);
                        menuItem.InitiateSettings(null);
                        menuItem.Click += new EventHandler(FormDesigner.eventManager.handleMenuItemClick);
                        //menuItem.ControlName = menuItem.Name;
                        if (menuBar is EIBMenuBar)
                        {
                            ((EIBMenuBar)menuBar).CurrentMenu.DropDownItems.Add(menuItem);
                            ((EIBMenuBar)menuBar).CurrentMenu.CurrentMenuItem = menuItem;
                        }
                        else
                        {
                            ((EIBVMenuBar)menuBar).CurrentMenu.DropDownItems.Add(menuItem);
                            ((EIBVMenuBar)menuBar).CurrentMenu.CurrentMenuItem = menuItem;
                        }
                        //to deserialise the evnts related to menu items
                        SetEIBEventsValue(menuItem, Node);
                        EIBMenuItem.counter++;
                    }
                }
                if (menuBar is EIBMenuBar)
                {
                    ((EIBMenuBar)menuBar).CurrentMenu = (EIBMenu)((EIBMenuBar)menuBar).CurrentMenu.OwnerItem;
                }
                else
                {
                    ((EIBVMenuBar)menuBar).CurrentMenu = (EIBMenu)((EIBVMenuBar)menuBar).CurrentMenu.OwnerItem;
                }
                
            }
            else
            {
                //menuBar.CurrentMenu = (EIBMenu)menuBar.CurrentMenu.OwnerItem;
            }

            //if (menubarNode.ChildNodes.Count > 0)
            //{
            //    foreach (XmlNode menuNode in menubarNode.ChildNodes)
            //    {
            //        EIBMenu menu = new EIBMenu();
            //        // menu.InitiateSettings(null);
            //        SetMenuValue(menu, menuNode);
            //        //menu.ControlName = menu.Name;
            //        menu.Click += new EventHandler(FormDesigner.eventManager.handleMenuClick);
            //        menuBar.CurrentToolStrip.Items.Add(menu);
            //        menuBar.CurrentMenu = menu;
            //        //control.Items.Add(childXMLNode.Attributes[XMLServicesConstants.XmlNodeTextAtt].Value.ToString());
            //        foreach (XmlNode menuItemNode in menuNode.ChildNodes)
            //        {
            //            EIBMenuItem menuItem = new EIBMenuItem();
            //            menuItem.InitiateSettings(null);
            //            SetMenuValue(menuItem, menuItemNode);
            //            menuItem.Click += new EventHandler(FormDesigner.eventManager.handleMenuItemClick);
            //            //menuItem.ControlName = menuItem.Name;
            //            menuBar.CurrentMenu.DropDownItems.Add(menuItem);
            //            menuBar.CurrentMenu.CurrentMenuItem = menuItem;
            //            //to deserialise the evnts related to menu items
            //            SetEIBEventsValue(menuItem, menuItemNode);
            //        }

            //    }

            //}

        }

        private static void SetMenuValue(ToolStripItem control, XmlNode xmlNode)
        {
            int attributeCount = xmlNode.Attributes.Count;
            if (attributeCount > 0)
            {
                for (int counter = 0; counter < attributeCount; counter++)
                {
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeTextAtt)
                    {
                        control.Text = xmlNode.Attributes[counter].Value;
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeMenuIconAtt)
                    {
                        string fileNameValue = Path.GetFileNameWithoutExtension(xmlNode.Attributes[counter].Value);
                        fileNameValue = fileNameValue.Insert(fileNameValue.Length, DateTime.Now.Ticks.ToString());
                        fileNameValue = Path.GetTempPath() + @"\" + fileNameValue + ".jpg";
                        File.Copy(EIBXMLUtilities.folderName + @"\" + xmlNode.Attributes[counter].Value, fileNameValue, true);
                        Bitmap imgInFile = new Bitmap(fileNameValue);
                        if(control is EIBMenu) 
                            ((EIBMenu)control).MenuIcon = imgInFile;
                        if (control is EIBMenuItem)
                            ((EIBMenuItem)control).MenuIcon = imgInFile;
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeWidthAtt)
                    {
                        control.Width = Int32.Parse(xmlNode.Attributes[counter].Value);
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeHeightAtt)
                    {
                        control.Height = Int32.Parse(xmlNode.Attributes[counter].Value);
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeIdAtt)
                    {
                        control.Name = xmlNode.Attributes[counter].Value;
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeNetBackColorAtt)
                    {
                        control.BackColor = Color.FromName(xmlNode.Attributes[counter].Value);
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeNameAtt)
                    {
                        control.Name = xmlNode.Attributes[counter].Value;
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeFontSizeAtt)
                    {
                        control.Font = new Font(control.Font.FontFamily, float.Parse(xmlNode.Attributes[counter].Value), control.Font.Style);
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeFontStyleAtt)
                    {
                        FontStyle fs = GetFontStyle(xmlNode.Attributes[counter].Value);
                        control.Font = new Font(control.Font, fs);
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeFontAtt)
                    {

                        FontFamily f = new FontFamily(xmlNode.Attributes[counter].Value);
                        if (f.IsStyleAvailable(control.Font.Style))
                        {
                            control.Font = new Font(xmlNode.Attributes[counter].Value, control.Font.Size, control.Font.Style);
                        }
                        else
                        {
                            MessageBox.Show("Font style not supported for  " + (xmlNode.Attributes[counter].Value.ToString().Trim()) + "  for control  " + (control.Text.ToString()));
                        }
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeNetBackColorAtt)
                    {
                        if (!xmlNode.Attributes[counter].Value.Contains("#"))
                        {
                            control.BackColor = Color.FromName(xmlNode.Attributes[counter].Value);
                        }
                        else
                        {
                            control.BackColor = ColorTranslator.FromHtml(xmlNode.Attributes[counter].Value);
                        }
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeNetForeColorAtt)
                    {
                        if (!xmlNode.Attributes[counter].Value.Contains("#"))
                        {
                            control.ForeColor = Color.FromName(xmlNode.Attributes[counter].Value);
                        }
                        else
                        {
                            control.ForeColor = ColorTranslator.FromHtml(xmlNode.Attributes[counter].Value);
                        }
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeTextAlignAtt)
                    {
                        control.TextAlign = getTextAlign(xmlNode.Attributes[counter].Value);
                    }
                    if (xmlNode.Attributes[counter].Name == XMLServicesConstants.XmlNodeVisibleToAtt)
                    {
                        string[] visibleToValues = xmlNode.Attributes[counter].Value.Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);
                        List<string> visibleToList = new List<string>(visibleToValues);
                        ((IEIBControl)control).VisibleTo = visibleToList;
                    }
                    
                }
            }
        }

        private static ContentAlignment getTextAlign(string value)
        {
            ContentAlignment contentAlignment = ContentAlignment.MiddleCenter;
            if (value == ContentAlignment.BottomCenter.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.BottomCenter;
            }
            else if (value == ContentAlignment.BottomLeft.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.BottomLeft;
            }
            else if (value == ContentAlignment.BottomRight.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.BottomRight;
            }
            else if (value == ContentAlignment.MiddleLeft.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.MiddleLeft;
            }
            else if (value == ContentAlignment.MiddleRight.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.MiddleRight;
            }
            else if (value == ContentAlignment.TopCenter.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.TopCenter;
            }
            else if (value == ContentAlignment.TopLeft.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.TopLeft;
            }
            else if (value == ContentAlignment.TopRight.ToString().ToLower())
            {
                contentAlignment = ContentAlignment.TopRight;
            }
            return contentAlignment;
        }

        private static void UpdateComboBoxValues(EIBCombobox control, XmlNode xmlNode)
        {
            if (xmlNode.ChildNodes.Count > 0)
            {
                foreach (XmlNode childXMLNode in xmlNode.ChildNodes)
                {
                    if (childXMLNode.Attributes[XMLServicesConstants.XmlNodeTextAtt] != null)
                    {
                        string childXMLStr = childXMLNode.Attributes[XMLServicesConstants.XmlNodeTextAtt].Value.ToString();
                        control.Items.Add(childXMLStr);
                    }
                }
            }
        }
        private static void UpdateListBoxValues(EIBListbox control, XmlNode xmlNode)
        {
            if (xmlNode.ChildNodes.Count > 0)
            {
                foreach (XmlNode childXMLNode in xmlNode.ChildNodes)
                {
                    if (childXMLNode.Attributes[XMLServicesConstants.XmlNodeListItemNameAtt] != null)
                    {
                        string childXMLStr = childXMLNode.Attributes[XMLServicesConstants.XmlNodeListItemNameAtt].Value.ToString();
                        control.Items.Add(childXMLStr);
                    }
                }
            }
        }

        private static void UpdateControlProperties(Control control, XmlNode xmlNode)
        {
            int attributeCount = xmlNode.Attributes.Count;
            if (attributeCount > 0)
            {
                for (int counter = 0; counter < attributeCount; counter++)
                {
                    SetAttributeValue(control, xmlNode.Attributes[counter].Name, xmlNode.Attributes[counter].Value);
                    string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
                    if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
                    {
                        SetEIBAttributeValue((IEIBControl)control, xmlNode.Attributes[counter].Name, xmlNode.Attributes[counter].Value);
                    }
                }
            }
            if (!(control is BaseWindow) && !(control.Parent is BaseWindow))
            {
                FormDesigner.eventManager.addPropertiesControl(control);
            }
            string eibInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibInterfaceName) != null)
            {
                SetEIBEventsValue((IEIBControl)control, xmlNode);
                SetDataMappingValue((IEIBControl)control, xmlNode);
            }

        }
        private static void UpdateSearchProperties(SearchDesigner control, XmlNode xmlNode)
        {
            if (xmlNode.ChildNodes.Count > 0)
            {
                foreach (XmlNode childXMLNode in xmlNode.ChildNodes)
                {
                    if (childXMLNode.Name.Equals("search"))
                    {
                        foreach (XmlNode queryXMLNode in childXMLNode.ChildNodes)
                        {
                            control.addButton_Click(null, null);
                            if (queryXMLNode.Attributes != null)
                            {
                                int attributeCount = queryXMLNode.Attributes.Count;
                                if (attributeCount > 0)
                                {
                                    for (int counter = 0; counter < attributeCount; counter++)
                                    {
                                        if (queryXMLNode.Attributes[counter].Name.Equals("FieldName"))
                                        {
                                            control.searchControl.selectedFieldList = queryXMLNode.Attributes[counter].Value;
                                            control.searchControl.fieldList.SelectedItem = queryXMLNode.Attributes[counter].Value;
                                        }
                                        if (queryXMLNode.Attributes[counter].Name.Equals("Operator"))
                                        {
                                            control.searchControl.selectedOperatorComboBox = queryXMLNode.Attributes[counter].Value;
                                            control.searchControl.operatorComboBox.SelectedItem = queryXMLNode.Attributes[counter].Value;
                                        }
                                        if (queryXMLNode.Attributes[counter].Name.Equals("Condition"))
                                        {
                                            control.searchControl.selectedConditionList = queryXMLNode.Attributes[counter].Value;
                                            control.searchControl.conditionList.SelectedItem = queryXMLNode.Attributes[counter].Value;
                                        }
                                        if (queryXMLNode.Attributes[counter].Name.Equals("FieldValue"))
                                        {
                                            control.searchControl.selectedFieldValue = queryXMLNode.Attributes[counter].Value;
                                            control.searchControl.fieldValue.Text = queryXMLNode.Attributes[counter].Value;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        private static void UpdateTreeNodeProperties(EIBTreeNode control, XmlNode xmlNode)
        {
            if (xmlNode.Attributes != null)
            {
                #region Test TreeNode
                int attributeCount = xmlNode.Attributes.Count;
                if (attributeCount > 0)
                {
                    for (int counter = 0; counter < attributeCount; counter++)
                    {
                        SetTreeNodeAttributeValue(control, xmlNode.Attributes[counter].Name, xmlNode.Attributes[counter].Value);
                        string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
                        if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
                        {
                            SetEIBAttributeValue((IEIBControl)control, xmlNode.Attributes[counter].Name, xmlNode.Attributes[counter].Value);
                        }
                    }
                }
                string eibInterfaceName = (typeof(IEIBControl)).Name;
                if (control.GetType().GetInterface(eibInterfaceName) != null)
                {
                    SetEIBEventsValue((IEIBControl)control, xmlNode);
                }
                #endregion

                foreach (XmlNode childXMLNode in xmlNode.ChildNodes)
                {
                    if (childXMLNode.Name.Equals(FormDesignerConstants.TreeNodeControl))
                    {
                        EIBTreeNode childNode = new EIBTreeNode();
                        UpdateTreeNodeProperties(childNode, childXMLNode);
                        control.Nodes.Add(childNode);
                    }
                }
            }


        }


        // This method deserialize the EIB events associated with the control from 
        // XML document. Look into all event nodes and check for associated event 
        // name
        private static void SetEIBEventsValue(IEIBControl control, XmlNode xmlNode)
        {
            foreach (XmlNode childXmlNode in xmlNode.ChildNodes)
            {
                if (childXmlNode.NodeType == XmlNodeType.Element)
                {
                    if (childXmlNode.Name == XMLServicesConstants.XmlNodeEventsElt)
                    {
                        foreach (XmlNode childEventXmlNode in childXmlNode.ChildNodes)
                        {
                            int attributeCount = childEventXmlNode.Attributes.Count;
                            if (attributeCount > 0 && childEventXmlNode.Attributes["internal"] == null)
                            {
                                for (int counter = 0; counter < attributeCount; counter++)
                                {
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnClickElt)
                                    {
                                        control.OnClickValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnDoubleClickElt)
                                    {
                                        control.OnDoubleClick = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeEnteringElt)
                                    {
                                        control.EnteringValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeExitingElt)
                                    {
                                        control.ExitingValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeDefaultValueElt)
                                    {
                                        control.DefaultValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnDropElt)
                                    {
                                        control.OnDrop = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnFocusElt)
                                    {
                                        control.OnFocus = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnBlurElt)
                                    {
                                        control.OnBlur = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnEventCreateElt)
                                    {
                                        control.OnEventCreateValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnEventEditElt)
                                    {
                                        control.OnEventEditValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnEventUpdateElt)
                                    {
                                        control.OnEventUpdateValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnChangeElt)
                                    {
                                        control.OnChange = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == XMLServicesConstants.XmlNodeOnOKElt)
                                    {
                                        control.OnOK = childEventXmlNode.InnerText;
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }


        private static void SetEIBAttributeValue(IEIBControl node, string propertyName, string value)
        {
            if (propertyName == XMLServicesConstants.XmlNodeNameAtt)
            {
                node.ControlName = value;
            }
            /*if (propertyName == XMLServicesConstants.XmlNodeDataFieldAtt)
            {
                node.DataFieldName = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeDataPatternAtt)
            {
                node.DataPatternName = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeDataTableAtt)
            {
                node.DataTableName = value;
            }*/
            if (propertyName == XMLServicesConstants.XmlNodeIsMandatoryAtt)
            {
                node.IsMandatory = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeDraggableAtt)
            {
                node.Draggable = value.ToLower().Equals("true");
            }
            if (propertyName == XMLServicesConstants.XmlNodeDroppableAtt)
            {
                node.Droppable = value.ToLower().Equals("true");
            }

        }

        private static void SetDataMappingValue(IEIBControl control, XmlNode xmlNode)
        {
            XmlNode xmlChildNode = xmlNode.SelectSingleNode(XMLServicesConstants.xmlDataMappings);
            if (xmlChildNode != null)
            {
                foreach (XmlNode childXmlNode in xmlChildNode.ChildNodes)
                {
                    if (childXmlNode.NodeType == XmlNodeType.Element)
                    {
                        if (childXmlNode.Name == XMLServicesConstants.xmlMappings)
                        {
                            int attributeCount = childXmlNode.Attributes.Count;
                            if (attributeCount > 0)
                            {
                                string dataFieldName="", dataTableName="", dataPatternName="";
                                XmlAttribute dataFieldAtt = childXmlNode.Attributes[XMLServicesConstants.XmlNodeDataFieldAtt];
                                if (dataFieldAtt != null)
                                {
                                    dataFieldName = dataFieldAtt.Value;
                                }
                                XmlAttribute dataPatternAtt = childXmlNode.Attributes[XMLServicesConstants.XmlNodeDataPatternAtt];
                                if (dataPatternAtt != null)
                                {
                                    dataPatternName = dataPatternAtt.Value;
                                }

                                XmlAttribute dataTableAtt = childXmlNode.Attributes[XMLServicesConstants.XmlNodeDataTableAtt];
                                if (dataTableAtt != null)
                                {
                                    dataTableName = dataTableAtt.Value;
                                }
                                DataMapping dataMapping = new DataMapping(dataPatternName, dataTableName, dataFieldName);
                                if (control.DataMappings == null)
                                {
                                    control.DataMappings = new List<DataMapping>();
                                }
                                control.DataMappings.Add(dataMapping);

                            }
                        }
                    }
                }
            }

        }
        private static void SetAttributeValue(Control control, string propertyName, string value)
        {
            if (control is EIBColumn)
            {
                if (propertyName == XMLServicesConstants.XmlNodeSortableAtt)
                {
                    if (value.Equals("auto"))
                    {
                        ((EIBColumn) control).Sortable = true;
                    }
                    else { ((EIBColumn)control).Sortable = false; }
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeTextAtt)
            {
                control.Text = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeTabIndexAtt)
            {
                control.TabIndex = int.Parse(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodeIdAtt)
            {
                control.Name = value;
            }
            // Check for the layout of the basewindow going negative.
            if (control.Parent is BaseWindow)
            {
                control.Location = new System.Drawing.Point(0,0);
            }
            else
            {
                if (propertyName == XMLServicesConstants.XmlNodeXAtt)
                {
                    control.Location = new System.Drawing.Point(Int32.Parse(value), control.Location.Y);
                }
                if (propertyName == XMLServicesConstants.XmlNodeYAtt)
                {
                    control.Location = new System.Drawing.Point(control.Location.X, Int32.Parse(value));
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeWidthTypeAtt)
            {
                if (control is ICustomSizableControl)
                {
                    if (value == WidthMeasurement.Auto.ToString())
                    {
                        ((ICustomSizableControl)control).WidthType = WidthMeasurement.Auto;
                    }
                    else if (value == WidthMeasurement.Pixel.ToString())
                    {
                        ((ICustomSizableControl)control).WidthType = WidthMeasurement.Pixel;
                    }
                    else if (value == WidthMeasurement.Percent.ToString())
                    {
                        ((ICustomSizableControl)control).WidthType = WidthMeasurement.Percent;
                    }
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeWidthAtt)
            {
                if (control is ICustomSizableControl)
                {
                    ((ICustomSizableControl)control)._Width = Int32.Parse(value);
                    /*if (((ICustomSizableControl)control).WidthType == WidthMeasurement.Percent)
                    {
                        ((ICustomSizableControl)control).WidthType = WidthMeasurement.Percent;
                        ((ICustomSizableControl)control)._Width = Int32.Parse(value.Replace("%", ""));
                    }
                    else if (value.Contains("px"))
                    {
                        ((ICustomSizableControl)control).WidthType = WidthMeasurement.Pixel;
                        ((ICustomSizableControl)control)._Width = Int32.Parse(value.Replace("px", ""));
                    }
                    else
                    {
                        int wdth = 0;
                        if (Int32.TryParse(value, out wdth))
                        {
                            ((ICustomSizableControl)control).WidthType = WidthMeasurement.Auto;
                            ((ICustomSizableControl)control)._Width = Int32.Parse(value);
                        }
                    }*/
                }
                else
                {
                    control.Width = Int32.Parse(value);
                }
                if (control is EIBColumn)
                {
                    EIBColumn column = (EIBColumn)control;
                    if (column.WidthType == WidthMeasurement.Pixel)
                    {
                        column.WidthPixel = Int32.Parse(value);
                        control.Width = Int32.Parse(value);
                    }
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeHeightAtt)
            {
                control.Height = Int32.Parse(value);
                if (control is EIBRow)
                {
                    EIBRow row = (EIBRow)control;
                    if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                    {
                        row.HeightPixel = Int32.Parse(value);
                    }
                }
            }
            #region Cursor propreties
            if (propertyName == XMLServicesConstants.XmlNodeCursorAtt)
            {
                control.Cursor = getCursorFromValue(value);
            }
            #endregion

            #region Padding and Margin Properties
            if (propertyName == XMLServicesConstants.XmlNodePaddingLeftAtt)
            {
                control.Padding = new Padding(int.Parse(value), control.Padding.Top, control.Padding.Right, control.Padding.Bottom) ;
            }
            if (propertyName == XMLServicesConstants.XmlNodePaddingRightAtt)
            {
                control.Padding = new Padding(control.Padding.Left, control.Padding.Top, int.Parse(value), control.Padding.Bottom) ;
            }
            if (propertyName == XMLServicesConstants.XmlNodePaddingBottomAtt)
            {
                control.Padding = new Padding(control.Padding.Left, control.Padding.Top, control.Padding.Right, int.Parse(value)) ;
            }
            if (propertyName == XMLServicesConstants.XmlNodePaddingTopAtt)
            {
                control.Padding = new Padding(control.Padding.Left, int.Parse(value), control.Padding.Right,control.Padding.Bottom ) ;
            }

            if (!(control is IEIBControl))
            {
                if (propertyName == XMLServicesConstants.XmlNodeMarginLeftAtt)
                {
                    control.Margin = new Padding(int.Parse(value), control.Margin.Top, control.Margin.Right, control.Margin.Bottom);
                }
                if (propertyName == XMLServicesConstants.XmlNodeMarginRightAtt)
                {
                    control.Margin = new Padding(control.Margin.Left, control.Margin.Top, int.Parse(value), control.Margin.Bottom);
                }
                if (propertyName == XMLServicesConstants.XmlNodeMarginBottomAtt)
                {
                    control.Margin = new Padding(control.Margin.Left, control.Margin.Top, control.Margin.Right, int.Parse(value));
                }
                if (propertyName == XMLServicesConstants.XmlNodeMarginTopAtt)
                {
                    control.Margin = new Padding(control.Margin.Left, int.Parse(value), control.Margin.Right, control.Margin.Bottom);
                }
            }
            else
            {
                IEIBControl control1 = (IEIBControl)control;
                if (propertyName == XMLServicesConstants.XmlNodeMarginLeftAtt)
                {
                    control1.ControlMargin = new Padding(int.Parse(value), control1.ControlMargin.Top, control1.ControlMargin.Right, control1.ControlMargin.Bottom);
                }
                if (propertyName == XMLServicesConstants.XmlNodeMarginRightAtt)
                {
                    control1.ControlMargin = new Padding(control1.ControlMargin.Left, control1.ControlMargin.Top, int.Parse(value), control1.ControlMargin.Bottom);
                }
                if (propertyName == XMLServicesConstants.XmlNodeMarginBottomAtt)
                {
                    control1.ControlMargin = new Padding(control1.ControlMargin.Left, control1.ControlMargin.Top, control1.ControlMargin.Right, int.Parse(value));
                }
                if (propertyName == XMLServicesConstants.XmlNodeMarginTopAtt)
                {
                    control1.ControlMargin = new Padding(control1.ControlMargin.Left, int.Parse(value), control1.ControlMargin.Right, control1.ControlMargin.Bottom);
                }
            }
            #endregion
            if (propertyName == XMLServicesConstants.XmlNodeBoxAlignmentAtt)
            {

                if (value == "horizontal")
                {
                    ((EIBPanel)control).BoxAlignment = BoxAlignment.Horizontal;
                }
                else if (value == "vertical")
                {
                    ((EIBPanel)control).BoxAlignment = BoxAlignment.Vertical;
                }
                else
                {
                    ((EIBPanel)control).BoxAlignment = BoxAlignment.None;
                }
            }

            if (propertyName == XMLServicesConstants.XmlNodeDateFormatAtt)
            {
                if (control is EIBDatePicker)
                {
                    EIBDatePicker datetimepicker = (EIBDatePicker)control;
                    datetimepicker.CustomFormatType = value;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeVisibleAtt)
            {
                if (control is ICustomVisible)
                {
                    ICustomVisible ctrlVisible = (ICustomVisible)control;
                    if ((value.Equals(true.ToString().ToLower())))
                    {
                        ctrlVisible.Visible = true;
                    }
                    else
                    {
                        ctrlVisible.Visible = false;
                    }

                }
                else
                {
                    if ((value.Equals(true.ToString().ToLower())))
                        control.Visible = true;
                    else
                        control.Visible = false;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeEnableAtt)
            {
                if ((value.Equals(true.ToString().ToLower())))
                    control.Enabled = true;
                else
                    control.Enabled = false;
            }
            if (propertyName == XMLServicesConstants.XmlNodeAlignAtt)
            {
                if (control is IContainerControl)
                {
                    ((IContainerControl)control).Align = getContainerAlignment(value); //(value == ContainerAlignment.Center.ToString().ToLower())?ContainerAlignment.Center:(value == ContainerAlignment.Left.ToString().ToLower())?ContainerAlignment.Left:ContainerAlignment.Right;
                }
            }

            if (propertyName == XMLServicesConstants.XmlNodePositionAtt)
            {
                ((IEIBControl)control).Position = (value == ControlPosition.Absolute.ToString().ToLower()) ? ControlPosition.Absolute : ControlPosition.Relative;
            }

            if (propertyName == XMLServicesConstants.XmlNodeFontSizeAtt)
            {
                control.Font = new Font(control.Font.FontFamily, float.Parse(value),control.Font.Style);
            }
            if (propertyName == XMLServicesConstants.XmlNodeFontStyleAtt)
            {
                FontStyle fs=GetFontStyle(value);
                control.Font = new Font(control.Font,fs );
            }
            if (propertyName == XMLServicesConstants.XmlNodeFontAtt)
            {
                
                     FontFamily f = new FontFamily(value);
                     if (f.IsStyleAvailable(control.Font.Style))
                     {
                         control.Font = new Font(value, control.Font.Size, control.Font.Style);
                     }
                     else
                     {
                         MessageBox.Show("Font style not supported for  " +(value.ToString().Trim()) + "  for control  " +(control.Text.ToString())  );
                     }               
            }
            if (propertyName == XMLServicesConstants.XmlNodeNetBackColorAtt)
            {
                if (!value.Contains("#"))
                {
                    control.BackColor = Color.FromName(value);
                }
                else
                {
                    control.BackColor = ColorTranslator.FromHtml(value);
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeNetForeColorAtt)
            {
                if (!value.Contains("#"))
                {
                    control.ForeColor = Color.FromName(value);
                }
                else
                {
                    control.ForeColor = ColorTranslator.FromHtml(value);
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeTimeAtt)
            {
                ((EIBTime)control).setTime(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodeFirstDayOfWeekAtt)
            {
                if(control is EIBSchedular)
                    ((EIBSchedular)control).FirstDayOfWeek = value;
            }

            //Applet
            if (propertyName == XMLServicesConstants.XmlNodeArchiveAtt)
            {
                if (control is EIBApplet)
                    ((EIBApplet)control).Archive = value.Split('/')[1];
            }
            if (propertyName == XMLServicesConstants.XmlNodeCodeAtt)
            {
                if (control is EIBApplet)
                    ((EIBApplet)control).Code = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeAppNameAtt)
            {
                if (control is EIBApplet)
                    ((EIBApplet)control).AppName = value;
            }

            if (propertyName == XMLServicesConstants.XmlNodeMultiSelectAtt)
            {
                bool val;
                if (value.Equals(true.ToString(),StringComparison.OrdinalIgnoreCase))
                    val = true;
                else
                    val = false;
                if(control is EIBGrid)
                    ((EIBGrid)control).MultiSelect = val;
                if(control is EIBLattice)
                    ((EIBLattice)control).MultiSelect = val;
                if (control is EIBListbox)
                    ((EIBListbox)control).MultiSelect = val;
            }

            if (propertyName == XMLServicesConstants.XmlNodeMenuIconAtt)
            {
                string fileNameValue = Path.GetFileNameWithoutExtension(value);
                fileNameValue = fileNameValue.Insert(fileNameValue.Length, DateTime.Now.Ticks.ToString());
                fileNameValue = Path.GetTempPath() + @"\" + fileNameValue + ".jpg";
                File.Copy(EIBXMLUtilities.folderName + @"\" + value, fileNameValue, true);
                Bitmap imgInFile = new Bitmap(fileNameValue);
                if(control is EIBMenuBar)
                    ((EIBMenuBar) control).MenuIcon = imgInFile;
                if (control is EIBVMenuBar)
                    ((EIBVMenuBar)control).MenuIcon = imgInFile;
            } 

            if (propertyName == XMLServicesConstants.XmlNodeMoldAtt)
            {
                if (control is EIBSchedular)
                    ((EIBSchedular)control).Mold = value;
            }

            if (propertyName == XMLServicesConstants.XmlNodeTimeZoneAtt)
            {
                if (control is EIBSchedular)
                    ((EIBSchedular)control).TimeZone = value;
            }
            
            
            if (propertyName == XMLServicesConstants.XmlNodePasswordCharAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    if (value.Length == 1)
                    {
                        textBox.PasswordChar = value[0];
                    }
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeReadOnlyAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    textBox.ReadOnly = (value.Equals(true.ToString().ToLower()));
                }
                if (control is EIBCombobox)
                {
                    EIBCombobox comboBox = (EIBCombobox)control;
                    comboBox.ReadOnly = (value.Equals(true.ToString().ToLower()));
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeMaxLengthAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    textBox.MaxLength = Int32.Parse(value); ;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeValueTypeAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    textBox.ValueType = value;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodePageSizeAtt)
            {
                if (control is EIBPaging)
                {
                    ((EIBPaging)control).PageSize = Int32.Parse(value);
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodePaginalAtt)
            {
                if (control is EIBListbox)
                {
                    ((EIBListbox)control).paginal = value;
                }
                if (control is EIBGrid)
                {
                    ((EIBGrid)control).paginal = value;
                }
                if (control is EIBLattice)
                {
                    ((EIBLattice)control).paginal = value;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeSourceAtt)
            {
                if (control is EIBPicture)
                {
                    EIBPicture picture = (EIBPicture)control;
                    MemoryStream ms = new MemoryStream(File.ReadAllBytes(EIBXMLUtilities.folderName + "/" + value));
                    Image imgInFile = Image.FromStream(ms);
                    //Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
                    picture.Image = imgInFile;
                    ms.Close();
                    ms.Dispose();
                }
                if (control is EIBImageBrowse)
                {
                    EIBImageBrowse imageBrowse = (EIBImageBrowse)control;
                    MemoryStream ms = new MemoryStream(File.ReadAllBytes(EIBXMLUtilities.folderName + "/" + value));
                    Image imgInFile = Image.FromStream(ms);
                    //Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
                    imageBrowse.Image = imgInFile;
                    ms.Close();
                    ms.Dispose();
                }
                if (control is EIBButton)
                {
                    EIBButton button = (EIBButton)control;
                    string fileNameValue = Path.GetFileNameWithoutExtension(value);
                    fileNameValue = fileNameValue.Insert(fileNameValue.Length, DateTime.Now.Ticks.ToString());
                    fileNameValue = Path.GetTempPath() + @"\" + fileNameValue + ".jpg";
                    File.Copy(EIBXMLUtilities.folderName + @"\" + value, fileNameValue, true);
                    Bitmap imgInFile = new Bitmap(fileNameValue);
                    button.Image = imgInFile;
                    //MemoryStream ms = new MemoryStream(File.ReadAllBytes(EIBXMLUtilities.folderName + "/" + value));
                    //Image imgInFile = Image.FromStream(ms);
                    //button.Image = imgInFile;
                    //ms.Close();
                    //ms.Dispose();
                }
                GC.Collect();
                GC.WaitForPendingFinalizers();
            }

            if (propertyName == XMLServicesConstants.XmlNodeBackgroundImageAtt)
            {   
                string fileNameValue = Path.GetFileNameWithoutExtension(value);
                fileNameValue = fileNameValue.Insert(fileNameValue.Length, DateTime.Now.Ticks.ToString());
                fileNameValue = Path.GetTempPath() + @"\" + fileNameValue + ".jpg";
                File.Copy(EIBXMLUtilities.folderName + @"\" + value, fileNameValue , true);
                Bitmap imgInFile = new Bitmap(fileNameValue);
                control.BackgroundImage = imgInFile;
            }

            if (propertyName == XMLServicesConstants.XmlNodeBackgroundImageLayoutAtt)
            {
                control.BackgroundImageLayout = getBackGroundImageLayout(value);
                //if (control is EIBPicture)
                //{
                //    EIBPicture picture = (EIBPicture)control;
                //    Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
                //    picture.Image = imgInFile;
                //}
                //if (control is EIBButton)
                //{
                //    EIBButton button = (EIBButton)control;
                //    Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
                //    button.Image = imgInFile;
                //}
            }


            if (propertyName == XMLServicesConstants.XmlNodeMultiLineAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    textBox.Multiline = (value == true.ToString().ToLower());
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeSizeTypeAtt)
            {
                if (control is EIBRow)
                {
                    EIBRow row = (EIBRow)control;
                    if (value.Trim().Equals("Pixel"))
                    {
                        row.HeightTyp = EIBRow.HeightMeasurement.Pixel;
                        row.HeightPixel = row.Height;
                    }
                    if (value.Trim().Equals("Percent"))
                    {
                        row.HeightTyp = EIBRow.HeightMeasurement.Percent;
                    }
                    if (value.Trim().Equals("Auto"))
                    {
                        row.HeightTyp = EIBRow.HeightMeasurement.Auto;
                    }
                }
                if (control is EIBColumn)
                {
                    EIBColumn column = (EIBColumn)control;
                    if (value.Trim().Equals("Pixel"))
                    {
                        column.WidthType = WidthMeasurement.Pixel;
                        column.WidthPixel = column.Width;
                    }
                    if (value.Trim().Equals("Percent"))
                    {
                        column.WidthType = WidthMeasurement.Percent;
                    }
                    if (value.Trim().Equals("Auto"))
                    {
                        column.WidthType = WidthMeasurement.Auto;
                    }
                }

            }


            if (propertyName == XMLServicesConstants.XmlNodeDefaultAtt)
            {
                if (control is EIBPanel)
                {
                    EIBPanel panel = (EIBPanel)control;
                    panel.DefaultScreen = (value.Equals(true.ToString().ToLower()));

                }
                //if (control is EIBPlaceHolder)
                //{
                //    EIBPlaceHolder placeholder = (EIBPlaceHolder)control;
                //    placeholder.DefaultScreen = (value.Equals(true.ToString().ToLower()));

                //}
            }
            //if (propertyName == XMLServicesConstants.XmlNodeBackgroundImageAtt)
            //{
            //    if (control is EIBPanel)
            //    {

            //        EIBPanel panel = (EIBPanel)control;
            //            Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
            //            panel.BackgroundImage = imgInFile;
            //    }

            //}

            if (propertyName == XMLServicesConstants.XmlNodeItemreferenceAtt)
            {
                if (control is EIBItem)
                {
                    control.Text = value;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeTextAlignAtt)
            {
                EIBLabel label = (EIBLabel)control;
                if (control is EIBLabel)
                    label.TextAlign = TextAlignment(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodeBorderAtt)
            {
                if (control is EIBPanel)
                {
                    EIBPanel panel = (EIBPanel)control;
                    panel.BorderStyle = BorderStyleValue(value);
                    //if (value == System.Windows.Forms.BorderStyle.FixedSingle.ToString().ToLower())
                    //{
                    //    panel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
                    //}
                    //if (value == System.Windows.Forms.BorderStyle.None.ToString().ToLower())
                    //{
                    //    panel.BorderStyle = System.Windows.Forms.BorderStyle.None;
                    //}
                    //if (value == System.Windows.Forms.BorderStyle.Fixed3D.ToString().ToLower())
                    //{
                    //    panel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
                    //}
                }
                if (control is EIBPlaceHolder)
                {
                    EIBPlaceHolder placeholder = (EIBPlaceHolder)control;
                    placeholder.BorderStyle = BorderStyleValue(value);
                }
                if (control is EIBLabel)
                {
                    EIBLabel label = (EIBLabel)control;
                    if(propertyName == XMLServicesConstants.XmlNodeBorderAtt)
                        label.BorderStyle = BorderStyleValue(value);
                    
                }
                if (control is EIBRadioGroup)
                {
                    EIBRadioGroup radiogroup = (EIBRadioGroup)control;
                    radiogroup.BorderStyle = BorderStyleValue(value);
                }
                if (control is EIBGrid)
                {
                    EIBGrid gridcontrol = (EIBGrid)control;
                    gridcontrol.BorderStyle = BorderStyleValue(value);
                }
                if (control is EIBLattice)
                {
                    EIBLattice latticecontrol = (EIBLattice)control;
                    latticecontrol.BorderStyle = BorderStyleValue(value);
                }
                if (control is EIBTextBox)
                {
                    EIBTextBox textBoxControl = (EIBTextBox)control;
                    textBoxControl.BorderStyle = BorderStyleValue(value);
                }
                if (control is EIBRow)
                {
                    EIBRow row = (EIBRow)control;
                    row.BorderStyle = BorderStyleValue(value);
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeFlatStyleAtt)
            {
                ((EIBButton)control).FlatStyle = getFlatStyle(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodeBorderSizeAtt)
            {
                ((EIBButton)control).FlatAppearance.BorderSize = int.Parse(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodeOrientAtt)
            {
                ((EIBVMenuBar)control).Orient = (value == Controls.Common.MenuOrient.horizontal.ToString().ToLower()) ? Controls.Common.MenuOrient.horizontal : Controls.Common.MenuOrient.vertical;
            }
            if (propertyName == XMLServicesConstants.XmlNodeUseAtt)
            {
                ((IEIBControl)control).Use = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeTitleAtt)
            {
                if ((control is EIBPanel) && control.Parent is BaseWindow)
                {
                    ((EIBPanel)control).Title = value;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodePopupAtt)
            {
                if ((control is EIBPanel) && control.Parent is BaseWindow)
                {
                    ((EIBPanel)control).Popup = (value == "true") ? true : false;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeSearchFieldAtt)
            {
                if (control is EIBTextBox)
                {
                    ((EIBTextBox)control).SearchField = (value == "true") ? true : false;
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeVisibleToAtt)
            {
                string[] visibleToValues = value.Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries);
                List<string> visibleToList = new List<string>(visibleToValues);
                ((IEIBControl)control).VisibleTo = visibleToList;
            }
            if (propertyName == XMLServicesConstants.XmlNodeAutoSizeAtt)
            {
                if (control is EIBVMenuBar)
                {
                    ((EIBVMenuBar)control).AutoSize = value.Equals(true.ToString().ToLower());
                }
            }
        }

        private static Cursor getCursorFromValue(string value)
        {
            Cursor cur = Cursors.Default;
            if (value == Cursors.AppStarting.ToString())
            {
                cur = Cursors.AppStarting;
            }
            else if (value == Cursors.Arrow.ToString())
            {
                cur = Cursors.Arrow;
            }
            else if (value == Cursors.Cross.ToString())
            {
                cur = Cursors.Cross;
            }
            else if (value == Cursors.Default.ToString())
            {
                cur = Cursors.Default;
            }
            else if (value == Cursors.Hand.ToString())
            {
                cur = Cursors.Hand;
            }
            else if (value == Cursors.Help.ToString())
            {
                cur = Cursors.Help;
            }
            else if (value == Cursors.HSplit.ToString())
            {
                cur = Cursors.HSplit;
            }
            else if (value == Cursors.IBeam.ToString())
            {
                cur = Cursors.IBeam;
            }
            else if (value == Cursors.No.ToString())
            {
                cur = Cursors.No;
            }
            else if (value == Cursors.NoMove2D.ToString())
            {
                cur = Cursors.NoMove2D;
            }
            else if (value == Cursors.NoMoveHoriz.ToString())
            {
                cur = Cursors.NoMoveHoriz;
            }
            else if (value == Cursors.NoMoveVert.ToString())
            {
                cur = Cursors.NoMoveVert;
            }
            else if (value == Cursors.PanEast.ToString())
            {
                cur = Cursors.PanEast;
            }
            else if (value == Cursors.PanNE.ToString())
            {
                cur = Cursors.PanNE;
            }
            else if (value == Cursors.PanNorth.ToString())
            {
                cur = Cursors.PanNorth;
            }
            else if (value == Cursors.PanNW.ToString())
            {
                cur = Cursors.PanNW;
            }
            else if (value == Cursors.PanSE.ToString())
            {
                cur = Cursors.PanSE;
            }
            else if (value == Cursors.PanSouth.ToString())
            {
                cur = Cursors.PanSouth;
            }
            else if (value == Cursors.PanSW.ToString())
            {
                cur = Cursors.PanSW;
            }
            else if (value == Cursors.PanWest.ToString())
            {
                cur = Cursors.PanWest;
            }
            else if (value == Cursors.SizeAll.ToString())
            {
                cur = Cursors.SizeAll;
            }
            else if (value == Cursors.SizeNESW.ToString())
            {
                cur = Cursors.SizeNESW;
            }
            else if (value == Cursors.SizeNS.ToString())
            {
                cur = Cursors.SizeNS;
            }
            else if (value == Cursors.SizeNWSE.ToString())
            {
                cur = Cursors.SizeNWSE;
            }
            else if (value == Cursors.SizeWE.ToString())
            {
                cur = Cursors.SizeWE;
            }
            else if (value == Cursors.UpArrow.ToString())
            {
                cur = Cursors.UpArrow;
            }
            else if (value == Cursors.VSplit.ToString())
            {
                cur = Cursors.VSplit;
            }
            else if (value == Cursors.WaitCursor.ToString())
            {
                cur = Cursors.WaitCursor;
            }
            return cur;
        }

        private static ImageLayout getBackGroundImageLayout(string value)
        {
            ImageLayout imageLayout = ImageLayout.None;
            if (value == ImageLayout.Center.ToString().ToLower())
            {
                imageLayout = ImageLayout.Center;
            }
            else if (value == ImageLayout.Stretch.ToString().ToLower())
            {
                imageLayout = ImageLayout.Stretch;
            }
            else if (value == ImageLayout.Tile.ToString().ToLower())
            {
                imageLayout = ImageLayout.Tile;
            }
            else if (value == ImageLayout.Zoom.ToString().ToLower())
            {
                imageLayout = ImageLayout.Zoom;
            }
            return imageLayout;
        }

        private static ContainerAlignment getContainerAlignment(string value)
        {
            ContainerAlignment containerAlign = ContainerAlignment.None;
            if (value == ContainerAlignment.Left.ToString().ToLower())
            {
                containerAlign = ContainerAlignment.Left;
            }
            else if (value == ContainerAlignment.Center.ToString().ToLower())
            {
                containerAlign = ContainerAlignment.Center;
            }
            else if (value == ContainerAlignment.Right.ToString().ToLower())
            {
                containerAlign = ContainerAlignment.Right;
            }
            return containerAlign;
        }

        private static FlatStyle getFlatStyle(string value)
        {
            FlatStyle flatStyle = FlatStyle.Standard;
            if (value == FlatStyle.Flat.ToString().ToLower())
            {
                flatStyle = FlatStyle.Flat;
            }
            else if (value == FlatStyle.Popup.ToString().ToLower())
            {
                flatStyle = FlatStyle.Popup;
            }
            else if (value == FlatStyle.System.ToString().ToLower())
            {
                flatStyle = FlatStyle.System;
            }
            return flatStyle;
        }

        private static FontStyle GetFontStyle(string value)
        {
            FontStyle fontStyle = FontStyle.Regular;
            if (value == System.Drawing.FontStyle.Bold.ToString())
            {
                fontStyle = System.Drawing.FontStyle.Bold;
            }
            if (value == System.Drawing.FontStyle.Italic.ToString())
            {
                fontStyle = System.Drawing.FontStyle.Italic;
            }
            if (value == System.Drawing.FontStyle.Underline.ToString())
            {
                fontStyle = System.Drawing.FontStyle.Underline;
            }
            if (value == System.Drawing.FontStyle.Strikeout.ToString())
            {
                fontStyle = System.Drawing.FontStyle.Strikeout;
            }
            if (value == "Bold, Italic")
            {
                fontStyle = System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic;
            }
            return fontStyle;
        }

        private static BorderStyle BorderStyleValue(string value)
        {
            BorderStyle borderStyle=BorderStyle.None;
            if (value == System.Windows.Forms.BorderStyle.FixedSingle.ToString().ToLower())
            {
                borderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            }
            if (value == System.Windows.Forms.BorderStyle.None.ToString().ToLower())
            {
                borderStyle = System.Windows.Forms.BorderStyle.None;
            }
            if (value == System.Windows.Forms.BorderStyle.Fixed3D.ToString().ToLower())
            {
                borderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            }
            return borderStyle;
        }

        private static ContentAlignment TextAlignment(string value)
        {
            ContentAlignment textAlign = ContentAlignment.TopLeft;
            if (value == ContentAlignment.BottomCenter.ToString().ToLower())
            {
                textAlign = ContentAlignment.BottomCenter;
            }
            if (value == ContentAlignment.BottomLeft.ToString().ToLower())
            {
                textAlign = ContentAlignment.BottomLeft;
            }
            if (value == ContentAlignment.BottomRight.ToString().ToLower())
            {
                textAlign = ContentAlignment.BottomRight;
            }
            if (value == ContentAlignment.MiddleCenter.ToString().ToLower())
            {
                textAlign = ContentAlignment.MiddleCenter;
            }
            if (value == ContentAlignment.MiddleLeft.ToString().ToLower())
            {
                textAlign = ContentAlignment.MiddleLeft;
            }
            if (value == ContentAlignment.MiddleRight.ToString().ToLower())
            {
                textAlign = ContentAlignment.MiddleRight;
            }
            if (value == ContentAlignment.TopCenter.ToString().ToLower())
            {
                textAlign = ContentAlignment.TopCenter;
            }
            if (value == ContentAlignment.TopLeft.ToString().ToLower())
            {
                textAlign = ContentAlignment.TopLeft;
            }
            if (value == ContentAlignment.TopRight.ToString().ToLower())
            {
                textAlign = ContentAlignment.TopRight;
            }
            return textAlign;
        }

        private static void SetTreeNodeAttributeValue(TreeNode control, string propertyName, string value)
        {
            if (propertyName == XMLServicesConstants.XmlNodeTextAtt)
            {
                control.Text = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeIdAtt)
            {
                control.Name = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeNameAtt)
            {
                control.Name = value;
            }
        }

    }
}
