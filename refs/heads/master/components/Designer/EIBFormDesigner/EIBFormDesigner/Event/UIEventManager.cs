using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using System.Drawing;
using System.Windows.Forms;
using EIBFormDesigner.Tools;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Database.Table;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Event;
using System.Collections;
using System.ComponentModel;
using System.Drawing.Drawing2D;
using System.Xml;
using System.Diagnostics;
using System.Reflection;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Properties;
using EIBFormDesigner.XML;
using EIBFormDesigner.Search;
using EIBFormDesigner.Workflow.XML;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Database;
using EIBFormDesigner.UndoRedo;
using GenericUndoRedo;

namespace EIBFormDesigner
{
    public class UIEventManager : IUIEventManager
    {
        private static System.Windows.Forms.ContextMenu contextMenu;

        
        private static PropertiesManager propertiesManager = null;
        private static FormulaManager formulaManager = new FormulaManager();
        private static ToolBox toolBox = null;
        internal static EIBFormDesigner.Designer.FormDesigner form = null;
        private static List<IEIBControl> currentControl = new List<IEIBControl>();
        private static Control parentPanel = null;
        //private static EIBGrid parentTable = null;
        internal static EIBMenuBar parentMenuBar = null;
        private static Boolean menuItemSelected = false;
        //
        // Create an instance of the PickBox class
        //
        //internal static PickBox pickBox = new PickBox();
        internal static Dictionary<IEIBControl, PickBox> pickBox = new Dictionary<IEIBControl, PickBox>();

        public static System.Windows.Forms.ContextMenu ContextMenu
        {
            get { return UIEventManager.contextMenu; }
            set { UIEventManager.contextMenu = value; }
        }
        public PropertiesManager getPropertiesManagerInstance()
        {
            if (propertiesManager == null)
            {
                propertiesManager = new PropertiesManager();
            }
            return propertiesManager;
        }
        public static ToolBox getToolBox
        {
            get { return UIEventManager.toolBox; }
        }
        private void controlList_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {
                Control eibControl = null;
                ComboBox selectedListControl = (ComboBox)sender;
                if (form.currentBaseWindow.baseFrame != null && pickBox != null)
                {
                    if (form.currentBaseWindow.CurrentControl != null && form.currentBaseWindow.CurrentControl.Count == 1)
                    {
                        if (form.currentBaseWindow.CurrentControl[0] is EIBPanel)
                        {
                            form.hideAllPickbox();
                            eibControl = form.currentBaseWindow.baseFrame.Controls.Find((string)selectedListControl.SelectedItem, true)[0];
                            //Control parentControl = (Control)form.currentBaseWindow.CurrentControl[0];

                            //eibControl = parentControl.Controls[(string)selectedListControl.SelectedItem];
                            if (eibControl != null)
                            {
                                if (eibControl is IEIBControl)
                                {
                                    form.currentBaseWindow.CurrentControl[0] = (IEIBControl)eibControl;
                                    currentControl[0] = ((IEIBControl)eibControl);
                                }
                            }
                        }
                        else
                        {
                            form.hideAllPickbox();
                            eibControl = form.currentBaseWindow.baseFrame.Controls.Find((string)selectedListControl.SelectedItem, true)[0];
                            //eibControl = form.currentBaseWindow.baseFrame.Controls[(string)selectedListControl.SelectedItem];
                            if (eibControl != null)
                            {
                                if (eibControl is IEIBControl)
                                {
                                    form.currentBaseWindow.CurrentControl[0] = (IEIBControl)eibControl;
                                    currentControl[0] = ((IEIBControl)eibControl);
                                }
                            }
                        }
                    }
                    if (eibControl != null)
                    {
                        MouseEventArgs ex = new MouseEventArgs(MouseButtons.Left, 0, 0, 0, 0);
                        pickBox[(IEIBControl)eibControl].SelectControl(eibControl, ex);
                        List<IEIBControl> li = new List<IEIBControl>();
                        li.Add((IEIBControl)eibControl);
                        propertiesManager.displayProperties(form.propertyWindow, li);
                        formulaManager.displayFormulaProperties(form.formulaEditor, (IEIBControl)eibControl);
                        form.propertyWindow.controlList.Items.Clear();
                        foreach (Control ctrl in eibControl.Controls)
                        {
                            if (!(eibControl is EIBTable))
                            form.propertyWindow.controlList.Items.Add(ctrl.Name);
                        }

                    }
                }
            }
            catch
            {

            }
        }
        public UIEventManager(EIBFormDesigner.Designer.FormDesigner designer)
        {
            form = designer;
            propertiesManager = getPropertiesManagerInstance();
            if (toolBox == null)
            {
                toolBox = new ToolBox(form);
            }
            initContextMenu();
            form.propertyWindow.controlList.SelectedIndexChanged += new System.EventHandler(this.controlList_SelectedIndexChanged);


        }
        public void cutMenuItem_Click(object sender, System.EventArgs e)
        {
            //handle Cut
            if (currentControl != null && currentControl.Count==1)
            {
                copyMenuItem_Click(null, null);
                deleteMenuItem_Click(null, null);
            }
        }
        public void pasteMenuItem_Click(object sender, System.EventArgs e)
        {
            try
            {
                //handle Paste
                Control ctrl = ToolBox.GetCtrlFromClipBoard() as Control;
                if (ctrl != null)
                {
                    /*if (ctrl is EIBGrid)
                    {
                        DragDropHandler.RegisterControl(ctrl, true, true);
                    }*/
                    //Rectangle rcObject = ctrl.Bounds;
                    //rcObject.Offset(10, 10);
                    //ctrl.SetBounds(rcObject.X, rcObject.Y, rcObject.Width, rcObject.Height);
                    Control container = null;
                    //if (parentPanel != null)
                    //{
                        if (currentControl.Count == 1 && currentControl[0] != null)
                        {
                            if (currentControl[0] is Control)
                            {
                                container = (Control)currentControl[0];
                            }
                        }
                        if (container != null)
                        {
                            if (container.AllowDrop == false)
                            {
                                container = container.Parent;
                            }
                            int centerx, centery;
                            centerx = container.Size.Width / 2;
                            centery = container.Size.Height / 2;
                            ctrl.SetBounds(centerx, centery, ctrl.Width, ctrl.Height);
                            string iToolInterfaceName = (typeof(ITool)).Name;
                            if (ctrl.GetType().GetInterface(iToolInterfaceName) != null)
                            {
                                ITool iTool = (ITool)ctrl;
                                if ((ctrl is EIBTabControl))
                                {
                                    ((EIBTabControl)ctrl).InitiateSettingsWithNoChild();
                                }
                                else
                                {
                                    iTool.InitiateSettings(form);
                                }
                                if (ctrl is IEIBControl)
                                {
                                    ((IEIBControl)ctrl).ControlName = ctrl.Name;
                                }
                            }
                            if (container.AllowDrop == true)
                            {
                                container.Controls.Add(ctrl);
                            }

                        }
                        //parentPanel.Controls.Add(ctrl);
                        ctrl.BringToFront();

                        setPropertiesForControl(ctrl);
                        addPropertiesControl(ctrl);
                        form.History.Do(new AddControlMemento());
                        form.Controlpool.Add((IEIBControl)ctrl, form.CurrentBaseWindow);
                        handleControlClick(ctrl, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
                        form.Invalidate();
                    //}


                }
            }
            catch
            {
                
            }
            

        }
        public void deleteFromControlCollections(IEIBControl ctrlToDelete)
        {
            if (ctrlToDelete is Control)
            {
                foreach (object ctrl in ((Control)ctrlToDelete).Controls)
                {
                    if (ctrl is IEIBControl)
                    {
                        deleteFromControlCollections((IEIBControl)ctrl);
                    }
                }
            }
            string Name = "";

            if (ctrlToDelete.GetType().GetInterface(typeof(IEIBControl).Name) != null)
            {
                PropertyInfo propertyInfo = ctrlToDelete.GetType().GetProperty("Name");
                if (propertyInfo != null)
                {
                    Name = (string)propertyInfo.GetGetMethod().Invoke(ctrlToDelete, null);
                }

            }
            if (!string.IsNullOrEmpty(Name))
            {
                if (ctrlToDelete is EIBTextBox)
                {

                    if (EIBControlCollection.TextBoxlist.ContainsKey(Name))
                    {
                        EIBControlCollection.TextBoxlist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBTreeNode)
                {
                    if (EIBControlCollection.TreeNodelist.ContainsKey(Name))
                    {
                        EIBControlCollection.TreeNodelist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBTreeView)
                {
                    if (EIBControlCollection.TreeViewlist.ContainsKey(Name))
                    {
                        foreach (TreeNode ctrl in ((TreeView)ctrlToDelete).Nodes)
                        {
                            if (ctrl is IEIBControl)
                            {
                                deleteFromControlCollections((IEIBControl)ctrl);
                            }
                        }
                        EIBControlCollection.TreeViewlist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBMenu)
                {
                    if (EIBControlCollection.Menulist.ContainsKey(Name))
                    {
                        EIBControlCollection.Menulist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBMenuItem)
                {
                    if (EIBControlCollection.MenuItemlist.ContainsKey(Name))
                    {
                        EIBControlCollection.MenuItemlist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBMenuBar)
                {
                    if (EIBControlCollection.MenuBarlist.ContainsKey(Name))
                    {
                        EIBControlCollection.MenuBarlist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBTabPage)
                {
                    if (EIBControlCollection.TabPagelist.ContainsKey(Name))
                    {
                        EIBControlCollection.TabPagelist.Remove(Name);
                    }
                }
                else if (ctrlToDelete is EIBPaging)
                {

                    if (EIBControlCollection.Paginglist.ContainsKey(Name))
                    {
                        foreach (IEIBControl control in loadGridAndListBoxList().Values)
                        {
                            if (control is EIBGrid)
                            {
                                if (((EIBGrid)control).Paginal == Name)
                                {
                                    ((EIBGrid)control).Paginal = null;
                                }
                            }
                            if (control is EIBLattice)
                            {
                                if (((EIBLattice)control).Paginal == Name)
                                {
                                    ((EIBLattice)control).Paginal = null;
                                }
                            }
                            else if (control is EIBListbox)
                            {
                                if (((EIBListbox)control).Paginal == Name)
                                {
                                    ((EIBListbox)control).Paginal = null;
                                }
                            }
                        }
                        EIBControlCollection.Paginglist.Remove(Name);
                        
                    }
                }
            }
        }
        private static Dictionary<string, Control> loadGridAndListBoxList()
        {
            Dictionary<string, Control> GridAndListBoxDictionary = new Dictionary<string, Control>();
            int counter = 0;
            if (EIBFormDesigner.Program.currentForm != null)
            {
                BaseWindow baseWindow = EIBFormDesigner.Program.currentForm.currentBaseWindow;
                FindGridAndListBoxControls(GridAndListBoxDictionary, counter, baseWindow);
            }
            return GridAndListBoxDictionary;
        }
        private static void FindGridAndListBoxControls(Dictionary<string, Control> GridAndListBoxDictionary, int counter, Control baseWindow)
        {
            foreach (Control control in baseWindow.Controls)
            {
                if (((control is EIBLattice) || (control is EIBGrid) || (control is EIBListbox)) && !(control.Name.Trim().Equals("")))
                {
                    IEIBControl eibControl = (IEIBControl)control;
                    //this.GridAndListBoxList.Items.Add(eibControl.ControlName);
                    GridAndListBoxDictionary.Add(control.Name, control);
                    counter++;
                }
                if (control is EIBPanel || control is EIBTabControl || control is EIBTabPage || control is EIBRadioGroup)
                {
                    FindGridAndListBoxControls(GridAndListBoxDictionary, counter, control);
                }
            }
        }
        public void setPropertiesForControl(object ctrl)
        {
            if (ctrl is ToolStrip)
            {
                ToolStrip toolStrip = (ToolStrip)ctrl;
                foreach (ToolStripItem ctl in toolStrip.Items)
                {
                    setPropertiesForControl(ctl);
                    /*if (ctl is IEIBControl)
                    {
                        EIBFormDesigner.Designer.FormDesigner.eventManager.addPropertiesControl(ctl);
                    }*/
                    if (ctl is ITool)
                    {
                        ITool tool = (ITool)ctl;
                        tool.InitiateSettings(form);
                    }
                }
            }
            else if (ctrl is EIBMenu)
            {
                EIBMenu menu = (EIBMenu)ctrl;
                foreach (ToolStripItem ctl in menu.DropDownItems)
                {
                    setPropertiesForControl(ctl);
                    /*if (ctl is IEIBControl)
                    {
                        EIBFormDesigner.Designer.FormDesigner.eventManager.addPropertiesControl(ctl);
                    }*/
                    if (ctl is ITool)
                    {
                        ITool tool = (ITool)ctl;
                        tool.InitiateSettings(form);
                    }
                }
                menu.Click += new EventHandler(handleMenuClick);
            }
            else if (ctrl is EIBMenuItem)
            {
                EIBMenuItem menuItem = (EIBMenuItem)ctrl;
                menuItem.Click += new EventHandler(handleMenuItemClick);
            }
            else
            {
                foreach (Control ctl in ((Control)ctrl).Controls)
                {
                    setPropertiesForControl(ctl);
                    if (ctl is IEIBControl)
                    {
                        EIBFormDesigner.Designer.FormDesigner.eventManager.addPropertiesControl(ctl);
                    }
                    if (ctl is ITool)
                    {
                        ITool tool = (ITool)ctl;
                        if (ctl is EIBTabControl)
                        {
                            ((EIBTabControl)ctl).InitiateSettingsWithNoChild();
                        }
                        else
                        {
                            tool.InitiateSettings(form);
                        }
                    }

                }
            }
        }
        public void copyMenuItem_Click(object sender, System.EventArgs e)
        {
            if (currentControl != null && currentControl.Count==1)
            {
                if (currentControl[0] is Control)
                {
                    ToolBox.CopyCtrl2ClipBoard((Control)currentControl[0]);
                }
            }
            //handle Copy
        }
        public void deleteMenuItem_Click(object sender, System.EventArgs e)
        {
            try
            {
                //handle Delete
                if (currentControl != null)
                {
                    if (currentControl.Count == 1)
                    {
                        if (currentControl[0] is Control)
                        {
                            Control DispControl = (Control)currentControl[0];
                            if (DispControl.GetType() == typeof(EIBTextBox) || DispControl.GetType() == typeof(EIBDatePicker)
                                || DispControl.GetType() == typeof(EIBTime) || DispControl.GetType() == typeof(EIBListbox)
                                || DispControl.GetType() == typeof(EIBCombobox))
                            {
                                if (form.displayNameExplorer.MapList != null)
                                {
                                    if (form.displayNameExplorer.MapList.ContainsKey(DispControl.Name))
                                    {
                                        form.displayNameExplorer.MapList.Remove(DispControl.Name);
                                    }
                                    else
                                    {
                                        form.displayNameExplorer.comboBox1.SelectedIndex = -1;
                                        form.displayNameExplorer.comboBox1.SelectedText = "";
                                    }
                                }
                                else
                                {
                                    form.displayNameExplorer.comboBox1.SelectedIndex = -1;
                                    form.displayNameExplorer.comboBox1.SelectedText = "";
                                }
                            }
                            else
                            {
                                form.displayNameExplorer.comboBox1.SelectedIndex = -1;
                                form.displayNameExplorer.comboBox1.SelectedText = "";
                            }
                        }
                    }
                    ArrayList treeNOdes = new ArrayList();
                    foreach (IEIBControl control in currentControl)
                    {
                        this.deleteFromControlCollections(control);
                        if (control is EIBTreeNode)
                        {
                            EIBTreeNode childNode = (EIBTreeNode)control;
                            EIBTreeView parentView = (EIBTreeView)(childNode.TreeView);
                            if (parentView != null)
                            {
                                parentView.Nodes.Remove(childNode);
                                if (parentView.Nodes.Count == 0)
                                {
                                    parentView.Parent.Controls.Remove(parentView);   
                                }
                                pickBox[parentView].HideHandles();
                                treeNOdes.Add(childNode);
                                List<IEIBControl> Pcontrols = new List<IEIBControl>();
                                Pcontrols.Add(form.currentBaseWindow.baseFrame);
                                propertiesManager.displayProperties(form.propertyWindow, Pcontrols);
                            }
                        }
                       
                        else if (control is EIBTreeView)
                        {
                            EIBTreeView parentView = (EIBTreeView)control;
                            if (parentView != null)
                            {
                                Control parentControl = ((Control)parentView).Parent;
                                if (parentControl != null && !(parentControl is BaseWindow))
                                {
                                    parentControl.Controls.Remove(((Control)control));
                                    pickBox[control].HideHandles();
                                }
                            }
                        }
                        else if (control is EIBMenu)
                        {
                            EIBMenu menuControl = ((EIBMenu)control);
                            if (menuControl.GetCurrentParent() != null)
                            {
                                if (menuControl.GetCurrentParent().Items.Contains(menuControl))
                                {
                                    menuControl.GetCurrentParent().Items.Remove(menuControl);
                                }
                            }

                        }
                        else if (control is EIBMenuItem)
                        {
                            EIBMenuItem menuControl = ((EIBMenuItem)control);
                            if (menuControl.GetCurrentParent() != null)
                            {
                                if (menuControl.GetCurrentParent().Items.Contains(menuControl))
                                {
                                    menuControl.GetCurrentParent().Items.Remove(menuControl);
                                }
                            }

                        }
                        else if (control is EIBMenuBar)
                        {
                            EIBMenuBar menubarControl = ((EIBMenuBar)control);
                            Control parentControl = ((Control)menubarControl).Parent;
                            if (parentControl != null && !(parentControl is BaseWindow))
                            {
                                if (form.Controlpool.IndexOf((IEIBControl)control) >= 0)
                                {
                                    form.Controlpool.Remove(control);
                                }
                                form.History.Do(new DeleteControlMemento(control, form.currentBaseWindow));

                                parentControl.Controls.Remove(((Control)control));
                                pickBox[control].HideHandles();
                            }

                        }
                        else if (control is EIBTable)
                        {
                            EIBTable eibTable = (EIBTable)control;
                            if (eibTable.TableData != null)
                            {
                                if (eibTable.TableData.ParentRelations.Count > 0)
                                {
                                    MessageBox.Show("Cannot remove a table that has existing parent relations.  Remove relations first.");
                                    return;
                                }
                                if (eibTable.TableData.ChildRelations.Count > 0)
                                {
                                    MessageBox.Show("Cannot remove a table that has existing child relations.  Remove relations first.");
                                    return;
                                }
                                if (TableUserControl.editTableNameList.ContainsKey(eibTable.Name))
                                {
                                    MessageBox.Show("Cannot remove a table in edit mode.");
                                    return;
                                }
                            }
                            else
                            {
                                eibTable.tableName.Enabled = false;
                            }
                            Control parentControl = ((Control)eibTable).Parent;
                            if (parentControl != null && !(parentControl is BaseWindow))
                            {
                                parentControl.Controls.Remove(eibTable);
                                pickBox[control].HideHandles();
                                if (parentControl.Parent is BaseWindow)
                                {
                                    BaseWindow baseWindow = (BaseWindow)parentControl.Parent;
                                    if (baseWindow.TypeOfWindow == FormDesignerConstants.DataPattern)
                                    {
                                        if (eibTable.tableName.Text.Trim() != "")
                                        {
                                            if (baseWindow.DatabaseDataSet.Tables.Contains(eibTable.tableName.Text))
                                            {
                                                //baseWindow.DatabaseDataSet.Relations
                                                baseWindow.DatabaseDataSet.Tables.Remove(eibTable.tableName.Text);
                                            }
                                        }
                                    }
                                }
                            }

                            foreach (EIBTableConnectorBase connectorBase in eibTable.tableConnectorList)
                            {
                                Control connectorControl = parentControl.Controls[connectorBase.ControlName];
                                EIBTableConnector tableConnector = (EIBTableConnector)connectorControl;
                                if (tableConnector != null)
                                {
                                    //tableConnector.Redraw(-1   );
                                    EIBFormDesigner.Database.Table.Line line = tableConnector.line;
                                    line.isDeleted = true;

                                    tableConnector.EraseLine(line);
                                }
                                parentControl.Controls.Remove(connectorControl);
                            }

                        }

                        else if (control is EIBNode)
                        {
                            EIBNode eibNode = (EIBNode)control;
                            Control parentControl = ((Control)eibNode).Parent;
                            if (parentControl != null && !(parentControl is BaseWindow))
                            {
                                parentControl.Controls.Remove(eibNode);
                                pickBox[control].HideHandles();
                                if (parentControl.Parent is BaseWindow)
                                {
                                    BaseWindow baseWindow = (BaseWindow)parentControl.Parent;

                                }
                            }

                            foreach (EIBNodeConnectorBase connectorBase in eibNode.nodeConnectorList)
                            {
                                Control connectorControl = parentControl.Controls[connectorBase.ControlName];
                                EIBNodeConnector nodeConnector = (EIBNodeConnector)connectorControl;
                                if (nodeConnector != null)
                                {
                                    //tableConnector.Redraw(-1 );
                                    EIBFormDesigner.Workflow.Controls.Line line = nodeConnector.line;
                                    nodeConnector.EraseLine(line);
                                    line.isDeleted = true;
                                }
                                parentControl.Controls.Remove(connectorControl);
                            }
                        }
                        else if (control is EIBTabPage)
                        {
                            Control parentControl = ((Control)control).Parent;
                            if (parentControl != null && !(parentControl is BaseWindow))
                            {
                                int count = 0;
                                count = parentControl.Controls.Count;
                                if (count < 2)
                                {
                                    Control currentControlNew = ((Control)control).Parent;
                                    Control parentControlNew = ((Control)currentControlNew).Parent;
                                    #region For Redo Undo
                                    if (form.Controlpool.IndexOf((IEIBControl)currentControlNew) >= 0)
                                    {
                                        form.Controlpool.Remove((IEIBControl)currentControlNew);
                                    }
                                    form.History.Do(new DeleteControlMemento((IEIBControl)currentControlNew, form.currentBaseWindow));
                                    #endregion
                                    parentControlNew.Controls.Remove(((Control)currentControlNew));
                                    if (EIBControlCollection.TabPagelist.ContainsKey(((Control)control).Name.Trim()))
                                    {
                                        EIBControlCollection.TabPagelist.Remove(((Control)control).Name.Trim());
                                    }
                                    if (EIBControlCollection.TabControllist.ContainsKey(((Control)currentControlNew).Name.Trim()))
                                    {
                                        EIBControlCollection.TabControllist.Remove(((Control)currentControlNew).Name.Trim());
                                    }
                                    pickBox[control].HideHandles();
                                    form.CurrentBaseWindow.baseFrame.Focus();
                                }
                                else
                                {
                                    #region For Redo Undo
                                    if (form.Controlpool.IndexOf((IEIBControl)control) >= 0)
                                    {
                                        form.Controlpool.Remove(control);
                                    }
                                    form.History.Do(new DeleteControlMemento(control, form.currentBaseWindow));
                                    #endregion
                                    parentControl.Controls.Remove(((Control)control));
                                    if (EIBControlCollection.TabPagelist.ContainsKey(((Control)control).Name.Trim()))
                                    {
                                        EIBControlCollection.TabPagelist.Remove(((Control)control).Name.Trim());
                                    }
                                    pickBox[control].HideHandles();
                                }
                            }
                        }
                        else if (control is EIBFormDesigner.Workflow.Controls.MarkControl)
                        {
                            EIBFormDesigner.Workflow.Controls.MarkControl markControl = (EIBFormDesigner.Workflow.Controls.MarkControl)control;
                            ((BaseWindow)markControl.parentConnector.baseFrame.Parent).Lines.Remove(markControl.parentConnector.line);
                            Control parentControl = ((Control)control).Parent;
                            if (parentControl != null && !(parentControl is BaseWindow))
                            {
                                int count = 0;
                                count = parentControl.Controls.Count;
                                if (count < 2)
                                {
                                    Control currentControlNew = ((Control)control).Parent;
                                    Control parentControlNew = ((Control)currentControlNew).Parent;
                                    parentControlNew.Controls.Remove(((Control)currentControlNew));
                                    pickBox[control].HideHandles();
                                }
                                else
                                {
                                    parentControl.Controls.Remove(((Control)control));
                                    pickBox[control].HideHandles();
                                }
                            }
                            markControl.parentConnector.baseFrame.Invalidate();
                            markControl.parentConnector.baseFrame.Update();
                            markControl.parentConnector.Redraw();
                        }
                        else
                        {
                            Control parentControl = ((Control)control).Parent;
                            if (parentControl != null && !(parentControl is BaseWindow))
                            {
                                Control myControl = (Control)control;
                                removeControl(myControl);
                                if (form.Controlpool.IndexOf((IEIBControl)control) >= 0)
                                {
                                    form.Controlpool.Remove(control);
                                }
                                form.History.Do(new DeleteControlMemento(control,form.currentBaseWindow));

                                parentControl.Controls.Remove(((Control)control));
                                pickBox[control].HideHandles();
                                
                                

                                //EIBControlCollection.RemoveControlFromCollection(currentControl[0].ControlType,((Control)currentControl[0]).Name);
                            }
                            List<IEIBControl> Pcontrols = new List<IEIBControl>();
                            Pcontrols.Add(form.currentBaseWindow.baseFrame);
                            propertiesManager.displayProperties(form.propertyWindow,Pcontrols);
                        }
                        
                    }
                    foreach (TreeNode ctl in treeNOdes)
                        currentControl.Remove((IEIBControl)ctl);

                }
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        public void removeControl(Control ctrl)
        {
            try
            {
                foreach (Control ctrlremove in ctrl.Controls)
                {
                    if (ctrlremove.Controls.Count > 0)
                    {
                        removeControl(ctrlremove);
                    }
                    EIBControlCollection.RemoveControlFromCollection(((IEIBControl)ctrlremove).ControlType, ctrlremove.Name);
                }
                EIBControlCollection.RemoveControlFromCollection(((IEIBControl)ctrl).ControlType, ctrl.Name);
            }
            catch
            {

            }
        }
       
        public void initContextMenu()
        {
            contextMenu = new System.Windows.Forms.ContextMenu();
            System.Windows.Forms.MenuItem cutMenuItem;
            cutMenuItem = new System.Windows.Forms.MenuItem();
            cutMenuItem.Shortcut = Shortcut.CtrlX;
            cutMenuItem.ShowShortcut = true;
            cutMenuItem.Click += new System.EventHandler(this.cutMenuItem_Click);
            System.Windows.Forms.MenuItem deleteMenuItem;
            deleteMenuItem = new System.Windows.Forms.MenuItem();
            deleteMenuItem.Shortcut = Shortcut.Del;
            deleteMenuItem.ShowShortcut = true;
            deleteMenuItem.Click += new System.EventHandler(this.deleteMenuItem_Click);

            System.Windows.Forms.MenuItem copyMenuItem;
            copyMenuItem = new System.Windows.Forms.MenuItem();
            copyMenuItem.Shortcut = Shortcut.CtrlC;
            copyMenuItem.ShowShortcut = true;
            copyMenuItem.Click += new System.EventHandler(this.copyMenuItem_Click);

            System.Windows.Forms.MenuItem pasteMenuItem;
            pasteMenuItem = new System.Windows.Forms.MenuItem();
            pasteMenuItem.Shortcut = Shortcut.CtrlV;
            pasteMenuItem.ShowShortcut = true;
            pasteMenuItem.Click += new System.EventHandler(this.pasteMenuItem_Click);

            contextMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] { cutMenuItem, deleteMenuItem, copyMenuItem, pasteMenuItem });
            contextMenu.Popup += new System.EventHandler(this.contextMenu_Popup);
            cutMenuItem.Index = 0;
            cutMenuItem.Text = "Cut";
            deleteMenuItem.Index = 1;
            deleteMenuItem.Text = "Delete";
            copyMenuItem.Index = 2;
            copyMenuItem.Text = "Copy";
            pasteMenuItem.Index = 3;
            pasteMenuItem.Text = "Paste";
        }
        public static int counter = 0;
        public void handleDrag(object sender, EventArgs e, object panel, Point NewLocation)
        {
            EIBFormDesigner.Controls.EIBPanel panelNew = null;
            Button buttonObject = (Button)sender;
            string text = buttonObject.Text;
            Control ctrl = null;
            EIBMenu menu = null;
            EIBMenuItem menuItem = null;
            EIBTreeNode treeNode = null;
            bool isusercontrol = true;
            if (form.currentBaseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
            {
                #region Handling New Control Adding
                if (text.Trim().Equals(FormDesignerConstants.PlaceHolderControl))
                {
                    ctrl = (EIBPlaceHolder)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.LabelControl))
                {
                    ctrl = (EIBLabel)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.FrameControl))
                {
                    ctrl = (EIBPanel)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.JasperControl))
                {
                    ctrl = (EIBJasper)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.RadioGroupControl))
                {
                    ctrl = (EIBRadioGroup)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.TextBoxControl))
                {
                    ctrl = (EIBTextBox)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.ButtonControl))
                {
                    ctrl = (EIBButton)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.DatePickerControl))
                {
                    ctrl = (EIBDatePicker)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.TimeControl))
                {
                    ctrl = (EIBTime)toolBox.getControlType(text.Trim());
                    ((EIBTime)ctrl).setTime("0:0:0");
                    ((EIBTime)ctrl).ShowTimeSplit();
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.BrowseControl))
                {
                    ctrl = (EIBBrowse)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.ImageBrowseControl))
                {
                    ctrl = (EIBImageBrowse)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.TreeControl))
                {
                    ctrl = (EIBTreeView)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.GridControl))
                {
                    ctrl = (EIBGrid)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.MultiTabControl))
                {
                    ctrl = (EIBTabControl)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    foreach (EIBTabPage page in ((EIBTabControl)ctrl).TabPages)
                    {
                        addPropertiesControl(page);
                    }
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.TabPage))
                {
                    ctrl = (EIBTabPage)toolBox.getControlType(text.Trim());
                    if (ctrl is EIBTabPage)
                    {
                        ctrl.Text = ctrl.Name;
                    }
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.PictureControl))
                {
                    ctrl = (EIBPicture)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.CheckboxControl))
                {
                    ctrl = (EIBCheckbox)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.RadioControl))
                {
                    ctrl = (EIBRadioButton)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.ComboControl))
                {
                    ctrl = (EIBCombobox)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.PagingControl))
                {
                    ctrl = (EIBPaging)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                /*if (text.Trim().Equals(FormDesignerConstants.MenuBarControl))
                {
                    ctrl = (EIBMenuBar)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }*/
                if (text.Trim().Equals(FormDesignerConstants.MenuBarControl))
                {
                    ctrl = (EIBVMenuBar)toolBox.getControlType(text.Trim());
                    DragDropHandler.RegisterControl(ctrl, true, true);
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.CalenderControl))
                {
                    ctrl = (EIBCalender)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.SchedularControl))
                {
                    ctrl = (EIBSchedular)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.AppletControl))
                {
                    ctrl = (EIBApplet)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.LatticeControl))
                {
                    ctrl = (EIBLattice)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.SearchControl))
                {
                    //Handle Search
                    ctrl = (EIBSearch)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.ListControl))
                {
                    //Handle Menu
                    ctrl = (EIBListbox)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }

                if (text.Trim().Equals(FormDesignerConstants.MenuControl))
                {
                    //Handle Menu
                    menu = (EIBMenu)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.MenuItemControl))
                {
                    //Handle Menu
                    menuItem = (EIBMenuItem)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.TreeNodeControl))
                {
                    treeNode = (EIBTreeNode)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.RowControl))
                {
                    ctrl = (EIBRow)toolBox.getControlType(text.Trim());
                    if (currentControl[0] is EIBGrid)
                    {
                        EIBGrid tablePanel = (EIBGrid)currentControl[0];
                        //tablePanel.SuspendLayout();
                        EIBRow rowTable = (EIBRow)ctrl;
                        rowTable.BackColor = Color.Coral;
                        //rowTable.HeightPercent = 0;
                        rowTable.HeightPixel = 30;
                        rowTable.HeightTyp = EIBRow.HeightMeasurement.Pixel;
                        tablePanel.Rows.Add(rowTable);
                        DragDropHandler.RegisterControl(ctrl, true, true);
                    }
                    if (currentControl[0] is EIBLattice)
                    {
                        EIBLattice tablePanel = (EIBLattice)currentControl[0];
                        //tablePanel.SuspendLayout();
                        EIBRow rowTable = (EIBRow)ctrl;
                        rowTable.BackColor = Color.Coral;
                        //rowTable.HeightPercent = 0;
                        rowTable.HeightPixel = 30;
                        rowTable.HeightTyp = EIBRow.HeightMeasurement.Pixel;
                        tablePanel.Rows.Add(rowTable);
                        DragDropHandler.RegisterControl(ctrl, true, true);
                    }
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.ColumnControl))
                {
                    ctrl = (EIBColumn)toolBox.getControlType(text.Trim());
                    if (currentControl[0] is EIBRow && ((EIBRow)currentControl[0]).Parent is EIBGrid)
                    {
                        EIBColumn columnTable = (EIBColumn)ctrl;
                        columnTable.BackColor = Color.Aqua;
                        columnTable.WidthPixel = 50;
                        columnTable.WidthType = WidthMeasurement.Pixel;
                        EIBRow rowTable = (EIBRow)currentControl[0];
                        rowTable.Columns.Add(columnTable);
                        DragDropHandler.RegisterControl(ctrl, true, true);
                    }
                    isusercontrol = false;
                    /*
                    if (currentControl is EIBGrid)
                    {
                        TableLayoutPanel tableLayoutPanel = (TableLayoutPanel)currentControl;
                        tableLayoutPanel.ColumnCount = tableLayoutPanel.ColumnCount + 1;
                        ColumnStyle colStyle =  new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100);                    
                        tableLayoutPanel.ColumnStyles.Add(colStyle);
                    
                    }
                     */
                }
                if (text.Trim().Equals(FormDesignerConstants.LineControl))
                {
                    ctrl = (EIBLine)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.ConnectorControl))
                {
                    ctrl = (EIBLine)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (isusercontrol &&
                        !text.Trim().Equals(FormDesignerConstants.NodeControl) &&
                        !text.Trim().Equals(FormDesignerConstants.NodeConnectorControl) &&
                        !text.Trim().Equals(FormDesignerConstants.TableControl) &&
                        !text.Trim().Equals(FormDesignerConstants.RelationControl))
                {
                    if (text != form.currentBaseWindow.UniqueID)
                    {
                        if (panel is EIBPlaceHolder)
                        {

                            if (!((EIBPlaceHolder)panel).CheckifAlreadyHave(text))
                            {
                                ctrl = (EIBItem)toolBox.getControlType("Item");
                                ((EIBItem)ctrl).Reference = text;
                                ((EIBItem)ctrl).Text = text;
                            }
                            else
                            {
                                MessageBox.Show("Already contains a usercontrol " + text);
                            }
                        }
                        else
                        {
                            MessageBox.Show("Draw a placeholder to have usercontrol");
                        }
                    }
                    else
                    {
                        MessageBox.Show("You can not drag same usercontrol on same form pattern.");
                    }
                }
                if (form.CurrentBaseWindow != null)
                {
                    form.CurrentBaseWindow.baseFrame.Focus();
                }
                #endregion
            }
            else if (form.currentBaseWindow.TypeOfWindow == FormDesignerConstants.WorkflowPattern)
            {

                isusercontrol = false;
                if (text.Trim().Equals(FormDesignerConstants.NodeControl))
                {
                    ctrl = (EIBNode)toolBox.getControlType(text.Trim());
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.NodeConnectorControl))
                {
                    //ctrl = (EIBRelation)toolBox.getControlType(text.Trim());
                    NodeRelationManager relationManager = new NodeRelationManager();
                    relationManager.currentBaseWindow = form.currentBaseWindow;
                    relationManager.loadWorkflowNodes(form.currentBaseWindow.baseFrame);
                    relationManager.ShowDialog(form);
                    isusercontrol = false;
                    return;
                }
            }
            else if (form.currentBaseWindow.TypeOfWindow == FormDesignerConstants.DataPattern)
            {
                if (text.Trim().Equals(FormDesignerConstants.TableControl))
                {
                    ctrl = (EIBTable)toolBox.getControlType(text.Trim());
                    ((EIBTable)ctrl).DatabaseDataSet = form.currentBaseWindow.DatabaseDataSet;
                    ((TableUserControl)ctrl).afterDragDrop();
                    isusercontrol = false;
                }
                if (text.Trim().Equals(FormDesignerConstants.RelationControl))
                {
                    //ctrl = (EIBRelation)toolBox.getControlType(text.Trim());
                    RelationshipManager relationManager = new RelationshipManager();
                    relationManager.DatabaseDataSet = form.currentBaseWindow.DatabaseDataSet;
                    relationManager.loadDatabaseTable(form.currentBaseWindow.baseFrame);
                    relationManager.ShowDialog(form);
                    isusercontrol = false;
                    return;
                }
            }
                    
                    //userpattern code.
            //isusercontrol = false;
            #region Associate Other Operation.
            if (!form.CurrentBaseWindow.baseFrame.isMouseClick)
            {
                form.currentBaseWindow.baseFrame.MouseClick += new MouseEventHandler(handleControlClick);
                form.CurrentBaseWindow.baseFrame.isMouseClick = true;
            }
            
            if (ctrl != null)
            {
                pickBox.Add((IEIBControl)ctrl, new PickBox());
                foreach (IEIBControl ctl in currentControl)
                {
                    if (ctl is TreeNode)
                    {
                        if (((TreeNode)ctl).TreeView != null)
                        {
                            if (pickBox.ContainsKey((IEIBControl)((TreeNode)ctl).TreeView))
                                pickBox[(IEIBControl)((TreeNode)ctl).TreeView].HideHandles();
                        }
                    }
                    else
                    {
                        if (pickBox.ContainsKey((IEIBControl)ctl))
                            pickBox[ctl].HideHandles();
                    }
                }
                
                //if (panel is EIBPlaceHolder)
                //{
                //    parentPanel = (EIBPlaceHolder)panel;
                //    if (ctrl is EIBMenuBar)
                //    {
                //        ((EIBMenuBar)ctrl).Location = new Point(0, 0);
                //        ((EIBMenuBar)ctrl).Height = 25;
                //        ((EIBMenuBar)ctrl).Width = parentPanel.Width;
                //        ((EIBMenuBar)ctrl).Dock = DockStyle.Top;
                //        ((EIBMenuBar)ctrl).Locked = true;
                //    }
                //    parentPanel.Controls.Add(ctrl);
                //}
                if (panel is EIBPanel)
                {
                    if (!(panel is EIBTabControl))
                    {
                        if (ctrl is TabPage)
                        {
                            if (EIBControlCollection.TabPagelist.ContainsKey(ctrl.Text.Trim()))
                            {
                                EIBControlCollection.TabPagelist.Remove(ctrl.Text.Trim());
                                return;
                            }
                        }
                    }
                    parentPanel = (EIBPanel)panel;
                    if (ctrl is EIBMenuBar)
                    {
                        ((EIBMenuBar)ctrl).Location = new Point(0, 0);
                        ((EIBMenuBar)ctrl).Height = 25;
                        ((EIBMenuBar)ctrl).Width = parentPanel.Width;
                        ((EIBMenuBar)ctrl).Dock = DockStyle.Top;
                        ((EIBMenuBar)ctrl).Locked = true;
                    }
                    parentPanel.Controls.Add(ctrl);
                }
                else if (panel is EIBTabControl)
                {
                    parentPanel = (EIBTabControl)panel;
                    EIBTabControl parentTabPanel = (EIBTabControl)panel;
                    if (ctrl is TabPage)
                    {
                        parentTabPanel.TabPages.Add((EIBTabPage)ctrl);
                    }
                    else
                    {
                        if (parentTabPanel.SelectedTab != null)
                        {
                            parentTabPanel.SelectedTab.Controls.Add(ctrl);
                        }
                    }

                }
                else if (panel is EIBColumn)
                {
                    EIBColumn column = (EIBColumn)panel;
                    try
                    {
                        column.Controls.Add(ctrl);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message[0].ToString());
                    }
                }
                else if (panel is EIBRow)
                {
                    EIBRow row = (EIBRow)panel;
                    if (row.Parent is EIBLattice)
                    {
                        try
                        {
                            row.Controls.Add(ctrl);
                        }
                        catch (Exception ex)
                        {
                            MessageBox.Show(ex.Message[0].ToString());
                        }
                    }
                }
                else if (panel is EIBRadioGroup)
                {
                    parentPanel = (EIBRadioGroup)panel;
                    if (ctrl is EIBRadioButton)
                    {
                        parentPanel.Controls.Add(ctrl);
                    }
                }
                else if (panel is EIBPlaceHolder)
                {
                    parentPanel = (EIBPlaceHolder)panel;
                    if (ctrl is EIBItem)
                    {
                        parentPanel.Controls.Add(ctrl);
                    }
                    else
                    {
                        MessageBox.Show("You can not drag controls in placeholders");
                        return;
                    }
                 }
                ctrl.Cursor = Cursors.HSplit;
                if (!(ctrl is EIBMenuBar || ctrl is EIBRow || ctrl is EIBColumn))
                {
                    ctrl.Left = NewLocation.X + 2;
                    ctrl.Top = NewLocation.Y + 2;
                }
                
                addPropertiesControl(ctrl);
                //Redo Undo Action
                form.History.Do(new AddControlMemento());
                form.Controlpool.Add((IEIBControl)ctrl,form.CurrentBaseWindow);
                /*******/
                List<IEIBControl> collectionControl = new List<IEIBControl>();
                collectionControl.Add((IEIBControl)ctrl);
                propertiesManager.displayProperties(form.propertyWindow,collectionControl);
                if (!(ctrl is EIBRow || ctrl is EIBColumn))
                {
                    pickBox[(IEIBControl)ctrl].SelectControl(ctrl, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
                }
            }
            #endregion
            #region Other Operation
            if (menu != null)
            {
                if (currentControl.Count == 1)
                {
                    if (currentControl[0] is EIBMenuBar)
                    {
                        currentControl.Clear();
                        currentControl.Add((IEIBControl)menu);
                        menu.Text = "Menu";
                        parentMenuBar = (EIBMenuBar)panel;
                        //parentMenuBar.CurrentToolStrip.Items.Add(menu);
                        if (parentMenuBar.CurrentToolStrip == null)
                        {
                            if(parentMenuBar.Controls.Count >0)
                            {
                                parentMenuBar.CurrentToolStrip = (ToolStrip)parentMenuBar.Controls[0];
                            }
                        }
                        parentMenuBar.CurrentToolStrip.Items.Add(menu);
                        parentMenuBar.CurrentMenu = menu;
                        menu.Click += new EventHandler(handleMenuClick);
                        propertiesManager.displayProperties(form.propertyWindow, currentControl);
                        formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);

                    }
                    else if (currentControl[0] is EIBVMenuBar)
                    {
                        currentControl.Clear();
                        currentControl.Add((IEIBControl)menu);
                        menu.Text = "Menu";
                        if (panel is EIBVMenuBar)
                        {
                            ((EIBVMenuBar)panel).Items.Add(menu);
                            ((EIBVMenuBar)panel).CurrentMenu = menu;
                            menu.Click += new EventHandler(handleMenuClick);
                            propertiesManager.displayProperties(form.propertyWindow, currentControl);
                            formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);
                        }
                    }
                    else if (currentControl[0] is EIBMenu)
                    {
                        EIBMenu myselectedmenu = (EIBMenu)currentControl[0];
                        currentControl.Clear();
                        currentControl.Add((IEIBControl)menu);
                        menu.Text = myselectedmenu.Text + "submenu";
                        if (panel is EIBMenuBar)
                        {
                            parentMenuBar = (EIBMenuBar)panel;
                            //parentMenuBar.CurrentToolStrip.Items.Add(menu);
                            parentMenuBar.CurrentMenu.DropDownItems.Add(menu);
                        }
                        else if (panel is EIBVMenuBar)
                        {
                            ((EIBVMenuBar)panel).CurrentMenu.DropDownItems.Add(menu);
                        }
                        menu.Click += new EventHandler(handleMenuClick);
                        propertiesManager.displayProperties(form.propertyWindow, currentControl);
                        formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);

                    }
                }
                
            }
            if (menuItem != null)
            {
                if (currentControl[0] is EIBMenuBar)
                {
                    /*currentControl.Clear();
                    currentControl.Add((IEIBControl)menu);
                    menu.Text = "Menu";
                    parentMenuBar = (EIBMenuBar)panel;
                    //parentMenuBar.CurrentToolStrip.Items.Add(menu);
                    if (parentMenuBar.CurrentToolStrip == null)
                    {
                        if (parentMenuBar.Controls.Count > 0)
                        {
                            parentMenuBar.CurrentToolStrip = (ToolStrip)parentMenuBar.Controls[0];
                        }
                    }
                    parentMenuBar.CurrentToolStrip.Items.Add(menu);
                    parentMenuBar.CurrentMenu = menu;
                    menu.Click += new EventHandler(handleMenuClick);
                    propertiesManager.displayProperties(form.propertyWindow, currentControl);
                    formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);
                     */

                }
                else if (currentControl[0] is EIBVMenuBar)
                {
                    currentControl.Clear();
                    currentControl.Add((IEIBControl)menuItem);
                    menuItem.Text = "MenuItem";
                    if (panel is EIBVMenuBar)
                    {
                        if (((EIBVMenuBar)panel).CurrentMenu != null)
                        {
                            ((EIBVMenuBar)panel).CurrentMenu.DropDownItems.Add(menuItem);
                            ((EIBVMenuBar)panel).CurrentMenu.CurrentMenuItem = menuItem;
                            menuItem.Click += new EventHandler(handleMenuItemClick);
                            propertiesManager.displayProperties(form.propertyWindow, currentControl);
                            formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);
                        }
                        else
                        {
                            MessageBox.Show("Please select a menu.");
                        }
                    }
                }
                else if (currentControl[0] is EIBMenu)
                {
                    EIBMenu myselectedmenu = (EIBMenu)currentControl[0];
                    currentControl.Clear();
                    currentControl.Add((IEIBControl)menuItem);
                    menuItem.Text = myselectedmenu.Text + "submenuitem";
                    if (panel is EIBVMenuBar)
                    {
                        ((EIBVMenuBar)panel).CurrentMenu.DropDownItems.Add(menuItem);
                    }
                    menuItem.Click += new EventHandler(handleMenuItemClick);
                    propertiesManager.displayProperties(form.propertyWindow, currentControl);
                    formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);

                }
                
            }
            if (treeNode != null && (currentControl[0] is EIBTreeView))
            {
                EIBTreeView treeView = (EIBTreeView)currentControl[0];
                treeView.Nodes.Add(treeNode);
                IEIBControl currentEIBControl = (IEIBControl)treeNode;
                form.currentBaseWindow.CurrentControl.Clear();
                form.currentBaseWindow.CurrentControl.Add(currentEIBControl);
                currentControl.Clear();
                currentControl.Add(treeNode) ;
                propertiesManager.displayProperties(form.propertyWindow, treeNode);
                formulaManager.displayFormulaProperties(form.formulaEditor, currentEIBControl);
            }
            if (treeNode != null && (currentControl[0] is EIBTreeNode))
            {
                EIBTreeNode treeNodeParent = (EIBTreeNode)currentControl[0];
                if (!treeNodeParent.Equals(treeNode))
                {
                    treeNodeParent.Nodes.Add(treeNode);
                }
                IEIBControl currentEIBControl = (IEIBControl)treeNode;
                form.currentBaseWindow.CurrentControl.Clear();
                form.currentBaseWindow.CurrentControl.Add(currentEIBControl) ;
                currentControl.Clear();
                currentControl.Add(treeNode);
                propertiesManager.displayProperties(form.propertyWindow, treeNode);
                formulaManager.displayFormulaProperties(form.formulaEditor, currentEIBControl);
            }
            LoadControls(form.currentBaseWindow.baseFrame);
            form.Invalidate();
            form.ResumeLayout(false);
            #endregion
        }

        public void LoadControls(object baseWindow)
        {
            try
            {
                if (form.propertyWindow.controlList.Items != null)
                {
                    form.propertyWindow.controlList.SelectedText = "";
                    form.propertyWindow.controlList.Items.Clear();

                }
                if (baseWindow is Control)
                {
                    foreach (Control control in ((Control)baseWindow).Controls)
                    {
                        string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
                        if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
                        {
                            if (!(control is EIBTabPage) && !(control is EIBTableConnector))
                            {
                                if (!form.propertyWindow.controlList.Items.Contains(control.Name))
                                {
                                    IEIBControl eibControl = (IEIBControl)control;
                                    if (!eibControl.ControlName.Trim().Equals(""))
                                    {
                                        form.propertyWindow.controlList.Items.Add(((Control)eibControl).Name);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch
            {

            }
        }

        public void addPropertiesControl(Control ctrl)
        {
            try
            {
                if (!pickBox.ContainsKey((IEIBControl)ctrl))
                    pickBox.Add((IEIBControl)ctrl, new PickBox());
                //if (!(ctrl is EIBMenuBar))
                //{
                    ctrl.MouseClick += new MouseEventHandler(handleControlClick);
                //}
                ctrl.DoubleClick += new EventHandler(handleControlDoubleClick);
                if (!(ctrl is EIBTabPage) && !(ctrl.Parent is EIBGrid) && !(ctrl.Parent is EIBLattice) &&
                    !(ctrl.Parent is EIBRow) && !(ctrl.Parent is EIBColumn) &&
                    !(ctrl is EIBColumn) && !(ctrl is EIBRow) && !(ctrl is EIBFormDesigner.Workflow.Controls.MarkControl))
                {
                    if (pickBox.ContainsKey((IEIBControl)ctrl))
                        pickBox[(IEIBControl)ctrl].WireControl(ctrl);
                }
                //currentControl = ctrl;
                ctrl.ContextMenu = contextMenu;
                ctrl.Focus();
                IEIBControl currentEIBControl = (IEIBControl)ctrl;
                if (form.currentBaseWindow.CurrentControl != null)
                {
                    foreach (IEIBControl ctl in currentControl)
                    {
                        if (ctl is TreeNode)
                        {
                            if (pickBox.ContainsKey((IEIBControl)((TreeNode)ctl).TreeView))
                                pickBox[(IEIBControl)((TreeNode)ctl).TreeView].HideHandles();
                        }
                        else
                        {
                            if (pickBox.ContainsKey((IEIBControl)ctl))
                                pickBox[ctl].HideHandles();
                        }
                    }
                    currentControl.Clear();
                    form.currentBaseWindow.CurrentControl.Clear();
                    currentControl.Add((IEIBControl)ctrl);
                    form.currentBaseWindow.CurrentControl.Add(currentEIBControl);
                }


                if (!(ctrl is EIBItem))
                {
                    //Code Commented by ajit.
                    //propertiesManager.displayProperties(form.propertyWindow, currentControl);
                    formulaManager.displayFormulaProperties(form.formulaEditor, currentEIBControl);
                }
                else
                {
                    propertiesManager.displayProperties(form.propertyWindow, ctrl.Parent);
                }
            }
            catch
            {
            }
        }
        public void handleMenuClick(object sender, EventArgs e)
        {
            EIBMenu menu = null;
            if (sender is EIBMenuBar)
            {
                EIBMenuBar menuBar = (EIBMenuBar)sender;
                parentMenuBar = menuBar;
                menu = menuBar.CurrentMenu;
            }
            else if (sender is EIBMenu)
            {
                menu = (EIBMenu)sender;
            }
            else if (sender is EIBVMenuBar)
            {
                if (menu != null && menu.Owner.Parent is EIBVMenuBar)
                {
                    ((EIBVMenuBar)menu.Owner.Parent).CurrentMenu = menu;
                }
            }
            if (parentMenuBar == null)
            {
                if (menu.Owner.Parent is EIBMenuBar)
                {
                    parentMenuBar = (EIBMenuBar)menu.Owner.Parent;
                    parentMenuBar.CurrentMenu = menu;
                }
                if (menu != null)
                {
                    if (menu.Owner is EIBVMenuBar)
                    {
                        ((EIBVMenuBar)menu.Owner).CurrentMenu = menu;
                    }
                    else
                    {
                        EIBVMenuBar parentHMenuBar = getOwner((EIBMenu)menu.OwnerItem);
                        parentHMenuBar.CurrentMenu = menu;
                        //MessageBox.Show(parentHMenuBar.CurrentMenu.Text);
                    }
                }
            }
            else
            {
                //parentMenuBar.CurrentMenu = menu;
                if (menu != null)
                {
                    if (menu.Owner is EIBVMenuBar)
                    {
                        ((EIBVMenuBar)menu.Owner).CurrentMenu = menu;
                    }
                    else
                    {
                        EIBVMenuBar parentHMenuBar = getOwner((EIBMenu)menu.OwnerItem);
                        if (parentHMenuBar == null)
                        {
                            parentMenuBar.CurrentMenu = menu;
                        }
                        else
                        {
                            parentHMenuBar.CurrentMenu = menu;
                        }
                        //MessageBox.Show(parentHMenuBar.CurrentMenu.Text);
                    }
                }
            }

            
            List<IEIBControl> propControl = new List<IEIBControl>();
            propControl.Add((IEIBControl)sender);
            propertiesManager.displayProperties(form.propertyWindow, propControl);
            formulaManager.displayFormulaProperties(form.formulaEditor, menu);
            if ((!(sender is EIBMenuBar)) && menu != null)
            {
                if (!PickBox.isctrlpressed)
                {
                    if (currentControl.Count >= 1)
                    {
                        foreach (IEIBControl ctl in currentControl)
                        {
                            if (ctl is TreeNode)
                            {
                                if (pickBox.ContainsKey((IEIBControl)((TreeNode)ctl).TreeView))
                                    pickBox[(IEIBControl)((TreeNode)ctl).TreeView].HideHandles();
                            }
                            else
                            {
                                if (pickBox.ContainsKey((IEIBControl)ctl))
                                    pickBox[ctl].HideHandles();
                            }
                        }
                        currentControl.Clear();
                        form.currentBaseWindow.CurrentControl.Clear();
                    }
                }
                //currentControl.Clear();
                currentControl.Add((IEIBControl)menu);
                //form.currentBaseWindow.CurrentControl.Clear();
                form.currentBaseWindow.CurrentControl.Add((IEIBControl)menu);
            }
            else if (parentMenuBar != null)
            {
                if (!PickBox.isctrlpressed)
                {
                    if (currentControl.Count >= 1)
                    {
                        foreach (IEIBControl ctl in currentControl)
                        {
                            if (ctl is TreeNode)
                            {
                                if (pickBox.ContainsKey((IEIBControl)((TreeNode)ctl).TreeView))
                                    pickBox[(IEIBControl)((TreeNode)ctl).TreeView].HideHandles();
                            }
                            else
                            {
                                if (pickBox.ContainsKey((IEIBControl)ctl))
                                    pickBox[ctl].HideHandles();
                            }
                        }
                        currentControl.Clear();
                        form.currentBaseWindow.CurrentControl.Clear();
                    }
                }
                //currentControl.Clear();
                currentControl.Add((IEIBControl)parentMenuBar);
                //form.currentBaseWindow.CurrentControl.Clear();
                form.currentBaseWindow.CurrentControl.Add((IEIBControl)parentMenuBar);
            }

            if (parentMenuBar != null && parentMenuBar.CurrentMenu != null && menuItemSelected)
            {
                if (parentMenuBar.CurrentMenu.CurrentMenuItem != null)
                {
                    propertiesManager.displayProperties(form.propertyWindow, parentMenuBar.CurrentMenu.CurrentMenuItem);
                    formulaManager.displayFormulaProperties(form.formulaEditor, parentMenuBar.CurrentMenu.CurrentMenuItem);
                }
            }
            else if (sender is EIBMenu)
            {
                if (menu.Owner is EIBVMenuBar && ((EIBVMenuBar)(menu.Owner)).CurrentMenu.CurrentMenuItem != null)
                {
                    propertiesManager.displayProperties(form.propertyWindow, ((EIBVMenuBar)menu.Owner).CurrentMenu.CurrentMenuItem);
                    formulaManager.displayFormulaProperties(form.formulaEditor, ((EIBVMenuBar)menu.Owner).CurrentMenu.CurrentMenuItem);
                }
            }


            menuItemSelected = false;
        }
        public EIBVMenuBar getOwner(EIBMenu menu)
        {
            if (menu != null)
            {
                if (menu.Owner is EIBVMenuBar)
                    return (EIBVMenuBar)menu.Owner;
                else if (menu.Owner.Parent is EIBMenuBar)
                {
                    return null;
                }
                else
                    return getOwner((EIBMenu)menu.OwnerItem);
            }
            else
            {
                return null;
            }
        }
        public EIBVMenuBar getOwnerForMenuItem(EIBMenuItem menuItem)
        {
            if (menuItem != null)
            {
                if(menuItem.OwnerItem is EIBMenu)
                {
                    return getOwner((EIBMenu)menuItem.OwnerItem);
                }
            }
            return null;
        }
        public void handleMenuItemClick(object sender, EventArgs e)
        {
            menuItemSelected = true;
            EIBMenuItem menuItem = (EIBMenuItem)sender;
            //((EIBVMenuBar)menu.Owner.Parent)
            EIBVMenuBar vMenuBar = getOwnerForMenuItem(menuItem);
            if (vMenuBar != null && vMenuBar.CurrentMenu != null)
            {
                vMenuBar.CurrentMenu.CurrentMenuItem = menuItem;
                List<IEIBControl> selectedmenuitem = new List<IEIBControl>();
                selectedmenuitem.Add((IEIBControl)sender);
                propertiesManager.displayProperties(form.propertyWindow, selectedmenuitem);
                formulaManager.displayFormulaProperties(form.formulaEditor, menuItem);
                currentControl.Clear();
                currentControl.Add((IEIBControl)menuItem);
                form.currentBaseWindow.CurrentControl.Clear();
                form.currentBaseWindow.CurrentControl.Add((IEIBControl)menuItem);
            }

        }

        public void handleControlDoubleClick(object sender, EventArgs e)
        {
            Control ctrl = (Control)sender;
            if (ctrl is EIBSearch)
            {
                EIBSearch search = (EIBSearch)ctrl;
                if (search.SearchDesign == null)
                {
                    search.SearchDesign = new SearchDesigner(ctrl.Parent);
                }
                search.SearchDesign.initSearchFrame(ctrl.Parent);
                search.SearchDesign.ShowDialog(form);
            }
        }
        private void MakeControlBaseWindowSelected(Control control)
        {
            if (control is EIBPanel)
            {
                EIBPanel basePanel = (EIBPanel)control;
                if (basePanel.Parent is BaseWindow)
                {

                    form.currentBaseWindow = (BaseWindow)basePanel.Parent;
                    FormulaEditorWindow.form.currentBaseWindow = form.currentBaseWindow;
                }
                else
                {
                    MakeControlBaseWindowSelected(control.Parent);
                }
            }
            else
            {
                MakeControlBaseWindowSelected(control.Parent);
            }

        }
        public void handleTabPageClick(object sender, MouseEventArgs e)
        {
        }
        public void handleControlClick(object sender, MouseEventArgs e)
        {
            try
            {
                if (e.Button == MouseButtons.Right)
                {
                    if (sender is Control && ((Control)sender) is EIBPanel && ((Control)sender).Parent == form.CurrentBaseWindow)
                    {
                        contextMenu.Show(((Control)sender), e.Location);
                    }
                    return;
                }
                Control ctrl = (Control)sender;
                //form.currentBaseWindow.baseFrame.SuspendLayout();
                //ctrl.SuspendLayout();
                #region SuspendLayout for control
                if (!(ctrl is BaseWindow))
                {
                    MakeControlBaseWindowSelected(ctrl);
                }
                if (ctrl is EIBTabControl)
                {
                    EIBTabControl tabControl = ((EIBTabControl)ctrl);
                    TabPage tabPage = tabControl.SelectedTab;
                    foreach (Control control in tabPage.Controls)
                    {
                        //control.Visible = true;
                        //enable all children
                        if (control.HasChildren)
                        {
                            foreach (Control childControl in control.Controls)
                            {
                                //childControl.Visible = true;
                            }
                        }

                    }
                }
                if (ctrl is BaseWindow)
                {
                    ctrl = ((BaseWindow)ctrl).baseFrame;
                }
                //change the current selected base window if User and clicked on
                // different basewindow
                if (ctrl is EIBPanel)
                {
                    EIBPanel basePanel = (EIBPanel)ctrl;
                    parentPanel = basePanel;
                    if (basePanel.Parent is BaseWindow)
                    {

                        form.currentBaseWindow = (BaseWindow)basePanel.Parent;
                        FormulaEditorWindow.form.currentBaseWindow = form.currentBaseWindow;
                    }
                    LoadControls(basePanel);
                }
                if (ctrl is EIBTabPage)
                {
                    LoadControls(ctrl);
                }
                if (!PickBox.isctrlpressed)
                {
                    if (currentControl.Count >= 1)
                    {
                        foreach (IEIBControl ctl in currentControl)
                        {
                            if (ctl is TreeNode)
                            {
                                if (pickBox.ContainsKey((IEIBControl)((TreeNode)ctl).TreeView))
                                    pickBox[(IEIBControl)((TreeNode)ctl).TreeView].HideHandles();
                            }
                            else
                            {
                                if (pickBox.ContainsKey((IEIBControl)ctl))
                                    pickBox[ctl].HideHandles();
                            }
                        }
                        currentControl.Clear();
                        form.currentBaseWindow.CurrentControl.Clear();
                    }
                }


                if (!currentControl.Contains((IEIBControl)ctrl))
                {
                    currentControl.Add((IEIBControl)ctrl);
                    form.currentBaseWindow.CurrentControl.Add((IEIBControl)ctrl);
                }

                //Handle TreeView Specific Logic
                if (ctrl is EIBTreeView)
                {
                    EIBTreeView treeView = (EIBTreeView)ctrl;
                    EIBTreeNode selectedNode = (EIBTreeNode)treeView.SelectedNode;
                    if (selectedNode != null)
                    {
                        if (!PickBox.isctrlpressed)
                        {
                            if (currentControl.Count >= 1)
                            {
                                foreach (IEIBControl ctl in currentControl)
                                {
                                    if (ctl is TreeNode)
                                    {
                                        if (pickBox.ContainsKey((IEIBControl)((TreeNode)ctl).TreeView))
                                            pickBox[(IEIBControl)((TreeNode)ctl).TreeView].HideHandles();
                                    }
                                    else
                                    {
                                        if (pickBox.ContainsKey((IEIBControl)ctl))
                                            pickBox[ctl].HideHandles();
                                    }
                                }
                                currentControl.Clear();
                                form.currentBaseWindow.CurrentControl.Clear();
                            }
                        }
                        currentControl.Add((IEIBControl)selectedNode);
                        form.currentBaseWindow.CurrentControl.Add((IEIBControl)selectedNode);
                    }
                }
                if (!(ctrl is EIBItem))
                {
                    propertiesManager.displayProperties(form.propertyWindow, currentControl);
                    ctrl.Focus();
                }
                if (!((ctrl.Parent is EIBLattice) || (ctrl.Parent is EIBGrid) || (ctrl.Parent is EIBRow)))
                {
                    if (PickBox.isLabelVisible)
                    {
                        if (pickBox.ContainsKey((IEIBControl)ctrl))
                            pickBox[(IEIBControl)ctrl].HideHandles();
                    }
                }
                if (ctrl is INodeControl)
                {
                    form.workEditor.formulaText.Enabled = true;
                    formulaManager.displayWorkFlowNodeProperties(form.workEditor, (INodeControl)ctrl);
                }
                else
                {
                    form.workEditor.formulaText.Text = "";
                    form.workEditor.formulaText.Enabled = false;
                }
                formulaManager.displayFormulaProperties(form.formulaEditor, currentControl[0]);

                //Disabling cut copy and paste for the workflow node controls and connectors.
                if (currentControl[0] is Control)
                {
                    if (((Control)currentControl[0]) is EIBFormDesigner.Workflow.Controls.MarkControl
                        || ((Control)currentControl[0]) is EIBFormDesigner.Workflow.Controls.EIBNode
                        || ((Control)currentControl[0]) is EIBFormDesigner.Database.TableUserControl)
                    {
                        ((Control)currentControl[0]).ContextMenu.MenuItems[0].Enabled = false;
                        ((Control)currentControl[0]).ContextMenu.MenuItems[2].Enabled = false;
                        ((Control)currentControl[0]).ContextMenu.MenuItems[3].Enabled = false;
                    }
                    else
                    {
                        if (((Control)currentControl[0]).ContextMenu != null)
                        {
                            ((Control)currentControl[0]).ContextMenu.MenuItems[0].Enabled = true;
                            ((Control)currentControl[0]).ContextMenu.MenuItems[2].Enabled = true;
                            ((Control)currentControl[0]).ContextMenu.MenuItems[3].Enabled = true;
                        }
                    }
                    if (currentControl.Count == 1)
                    {
                        Control DispControl = (Control)currentControl[0];
                        if (DispControl.GetType() == typeof(EIBTextBox) || DispControl.GetType() == typeof(EIBDatePicker)
                            || DispControl.GetType() == typeof(EIBTime) || DispControl.GetType() == typeof(EIBListbox)
                            || DispControl.GetType() == typeof(EIBCombobox))
                        {
                            form.displayNameExplorer.comboBox1.Enabled = true;
                            if (form.displayNameExplorer.MapList != null)
                            {
                                if (form.displayNameExplorer.MapList.ContainsKey(DispControl.Name))
                                {
                                    form.displayNameExplorer.comboBox1.SelectedItem = form.displayNameExplorer.MapList[DispControl.Name].Name
                                        + "(" + form.displayNameExplorer.MapList[DispControl.Name].Text + ")";
                                }
                                else
                                {
                                    form.displayNameExplorer.comboBox1.SelectedIndex = -1;
                                    form.displayNameExplorer.comboBox1.SelectedText = "";
                                }
                            }
                        }
                        else
                        {
                            form.displayNameExplorer.comboBox1.SelectedIndex = -1;
                            form.displayNameExplorer.comboBox1.SelectedText = "";
                            form.displayNameExplorer.comboBox1.Enabled = false;
                        }
                    }
                }
            }
            catch
            {

            }
            #endregion
            //ctrl.ResumeLayout();
            //ctrl.Refresh();
            //form.currentBaseWindow.baseFrame.ResumeLayout();
            //form.currentBaseWindow.baseFrame.Invalidate();

        }
        private void contextMenu_Popup(object sender, System.EventArgs e)
        {
        }

        public void TableControl_Layout(object sender, LayoutEventArgs e)
        {
            try
            {
                EIBGrid grid = (EIBGrid)sender;
                int count = 0;
                double percentMax = 0;
                double skalePercent = 1;
                int autoHeightColumnCount = 0;
                int fixedPixelHeight = 0;

                foreach (EIBRow row in grid.Rows)
                {
                    if (row.Visible)
                    {
                        count++;
                        if (row.HeightTyp == EIBRow.HeightMeasurement.Auto)
                            autoHeightColumnCount++;
                        else if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                            fixedPixelHeight += row.HeightPixel;
                        else
                            percentMax += row.HeightPercent;
                    }
                }
                if (count > 0)
                {
                    if (percentMax > 0)
                    {
                        if (autoHeightColumnCount == 0)
                            skalePercent = 100.0 / percentMax;
                    }
                    double percentAuto = (100.0 - percentMax) / autoHeightColumnCount;

                    int lastPos = 0;
                    int clientHeightWithoutFixed = Math.Max(0, grid.ClientSize.Height - fixedPixelHeight);
                    foreach (EIBRow row in grid.Rows)
                    {
                        if (row.Visible)
                        {
                            // fit in table
                            row.Width = grid.ClientSize.Width;
                            if (row.HeightTyp == EIBRow.HeightMeasurement.Auto)
                            {
                                row.Height = (int)(percentAuto / 100.0 * clientHeightWithoutFixed) - (count - 1) * grid.Space;
                            }
                            else
                            {
                                if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                                    row.Height = row.HeightPixel;
                                else
                                    row.Height = (int)(row.HeightPercent / 100.0 * skalePercent * clientHeightWithoutFixed) - (count - 1) * grid.Space;
                            }

                            // position in table
                            row.Location = new Point(0, lastPos);
                            if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                                lastPos += row.Height;
                            else
                                lastPos += row.Height + (count - 1) * grid.Space;
                        }
                    }
                }
            }
            catch
            {
            }

        }

        public void LatticeControl_Layout(object sender, LayoutEventArgs e)
        {
            try
            {
                EIBLattice lattice = (EIBLattice)sender;
                int count = 0;
                double percentMax = 0;
                double skalePercent = 1;
                int autoHeightColumnCount = 0;
                int fixedPixelHeight = 0;

                foreach (EIBRow row in lattice.Rows)
                {
                    if (row.Visible)
                    {
                        count++;
                        if (row.HeightTyp == EIBRow.HeightMeasurement.Auto)
                            autoHeightColumnCount++;
                        else if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                            fixedPixelHeight += row.HeightPixel;
                        else
                            percentMax += row.HeightPercent;
                    }
                }
                if (count > 0)
                {
                    if (percentMax > 0)
                    {
                        if (autoHeightColumnCount == 0)
                            skalePercent = 100.0 / percentMax;
                    }
                    double percentAuto = (100.0 - percentMax) / autoHeightColumnCount;

                    int lastPos = 0;
                    int clientHeightWithoutFixed = Math.Max(0, lattice.ClientSize.Height - fixedPixelHeight);
                    foreach (EIBRow row in lattice.Rows)
                    {
                        if (row.Visible)
                        {
                            // fit in table
                            row.Width = lattice.ClientSize.Width;
                            if (row.HeightTyp == EIBRow.HeightMeasurement.Auto)
                            {
                                row.Height = (int)(percentAuto / 100.0 * clientHeightWithoutFixed) - (count - 1) * lattice.Space;
                            }
                            else
                            {
                                if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                                    row.Height = row.HeightPixel;
                                else
                                    row.Height = (int)(row.HeightPercent / 100.0 * skalePercent * clientHeightWithoutFixed) - (count - 1) * lattice.Space;
                            }

                            // position in table
                            row.Location = new Point(0, lastPos);
                            if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                                lastPos += row.Height;
                            else
                                lastPos += row.Height + (count - 1) * lattice.Space;
                        }
                    }
                }
            }
            catch
            {
            }

        }

        public static void showProperty()
        {
            propertiesManager.displayProperties(form.propertyWindow, currentControl);
        }

        public void HandleCtrlDown(object sender, KeyEventArgs e)
        {
            //if (e.Shift)
            //{
            //    PickBox.isctrlpressed = true;
            //}
            if (e.Modifiers == Keys.Control)
            {
                if (e.KeyCode == Keys.V)
                {
                    pasteMenuItem_Click(null, null);
                }
            }
        }
        public void HandleKeyPress(object sender, KeyPressEventArgs e)
        {
            //if (e.KeyChar == (char)Keys.ShiftKey)
            //{
            //    PickBox.isctrlpressed = true;
            //}
        }
        public void HandleCtrlUp(object sender, KeyEventArgs e)
        {
            PickBox.isctrlpressed = e.Shift;
        }

        public void AlignAll(List<IEIBControl> control, Align format)
        {
            if (control.Count <= 1)
            {
                MessageBox.Show("Select more than two controls.");
                return;
            }
            if (format == Align.left)
                Alignment.AlignLeft(control);
            else if (format == Align.right)
                Alignment.AlignRight(control);
            else if (format == Align.center)
                Alignment.AlignCenter(control);
            else if (format == Align.top)
                Alignment.AlignTop(control);
            else if (format == Align.middle)
                Alignment.AlignMiddle(control);
            else if (format == Align.bottom)
                Alignment.AlignBottom(control);

        }
    }
}
