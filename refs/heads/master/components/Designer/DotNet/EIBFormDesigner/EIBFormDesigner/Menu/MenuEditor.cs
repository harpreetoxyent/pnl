using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.Menu
{
    public partial class MenuEditor : Form
    {
        EIBMenuBar currentMenuBar;
        public MenuEditor(EIBMenuBar menuBar)
        {
            InitializeComponent();
            if (menuBar != null)
            {
                currentMenuBar = menuBar;
            }
            ResetMenuEditor(menuBar);
        }

        public void showButton(bool isEnabled)
        {
            //button1.Enabled = isEnabled;
            button2.Enabled = isEnabled;
            button3.Enabled = isEnabled;
            button4.Enabled = isEnabled;
            button5.Enabled = isEnabled;
            filteredPropertyGrid1.Enabled = isEnabled;
        }
        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            if (!isSelected)
            {
                treeView1.SelectedNode = null;
                showButton(false);
                //filteredPropertyGrid1.SelectedObject = null;
            }
            else
            {
                if (e.Node == null)
                {
                    showButton(false);
                    filteredPropertyGrid1.SelectedObject = null;
                }
                else
                {
                    filteredPropertyGrid1.SelectedObject = nodeMenus[e.Node.Name];
                    showButton(true);
                }
            }
        }
        Hashtable nodeMenus = new Hashtable();
        private void MenuEditor_Load(object sender, EventArgs e)
        {
            /*TreeNode root = new TreeNode("File");
            TreeNode c1 = new TreeNode("New");
            TreeNode c2 = new TreeNode("Open");
            TreeNode c3 = new TreeNode("Save");
            root.Nodes.Add(c1);
            root.Nodes.Add(c2);
            root.Nodes.Add(c3);
            treeView1.Nodes.Add(root);
            ToolStripMenuItem menuItem = new System.Windows.Forms.ToolStripMenuItem("File");
            menuItem.DropDownItems.Add(new ToolStripMenuItem("New"));
            menuItem.DropDownItems.Add(new ToolStripMenuItem("Open"));
            menuItem.DropDownItems.Add(new ToolStripMenuItem("Save"));
            
            nodeMenus.Add("File",menuItem);
            nodeMenus.Add("New", menuItem.DropDownItems[0]);
            nodeMenus.Add("Open", menuItem.DropDownItems[1]);
            nodeMenus.Add("Save", menuItem.DropDownItems[2]);*/

        }
        public void ResetMenuEditor(EIBMenuBar menuBar)
        {
            foreach(EIBMenu menuItem in ((ToolStrip)menuBar.Controls[0]).Items)
            {
                if (!string.IsNullOrEmpty(menuItem.Text))
                {
                    TreeNode tn = new TreeNode(menuItem.Text);
                    tn.Name = menuItem.Name;
                    treeView1.Nodes.Add(tn);
                    parseMenuItem(menuItem, tn);
                    nodeMenus.Add(tn.Name, menuItem);
                }
            }
        }
        public void parseMenuItem(EIBMenu menu,TreeNode parentTreeNode)
        {
            foreach (object menuItem in menu.DropDownItems)
            {
                if (menuItem.GetType() == typeof(EIBMenu))
                {
                    EIBMenu eibmenu = (EIBMenu)menuItem;
                    if (!string.IsNullOrEmpty(eibmenu.Text))
                    {
                        TreeNode tn = new TreeNode(eibmenu.Text);
                        tn.Name = eibmenu.Name;
                        parentTreeNode.Nodes.Add(tn);
                        parseMenuItem(eibmenu, tn);
                        nodeMenus.Add(tn.Name, eibmenu);
                    }
                }
                else
                {
                    EIBMenuItem eibMenuItem = (EIBMenuItem)menuItem;
                    if (!string.IsNullOrEmpty(eibMenuItem.Text))
                    {
                        TreeNode tn = new TreeNode(eibMenuItem.Text);
                        tn.Name = eibMenuItem.Name;
                        parentTreeNode.Nodes.Add(tn);
                        nodeMenus.Add(tn.Name, eibMenuItem);
                    }
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            TreeNode selectedNode = treeView1.SelectedNode;
            object menu = nodeMenus[selectedNode.Name];
            if (menu != null)
            {
                if (menu.GetType() == typeof(EIBMenu))
                {
                    EIBMenu eibMenu = (EIBMenu)menu;
                    EIBMenuItem ctrl = (EIBMenuItem)UIEventManager.getToolBox.getControlType(FormDesignerConstants.MenuItemControl);
                    ctrl.Text = "EIBMenuItem";
                    eibMenu.DropDownItems.Add(ctrl);
                    TreeNode tn = new TreeNode(ctrl.Text);
                    tn.Name = ctrl.Name;
                    selectedNode.Nodes.Add(tn);
                    nodeMenus.Add(tn.Name, ctrl);
                }
                else if (menu.GetType() == typeof(EIBMenuItem))
                {
                    MessageBox.Show("Select parent menu");
                }
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            TreeNode selectedNode = treeView1.SelectedNode;
            int index = selectedNode.Index;
            TreeNode parentNode = selectedNode.Parent;
            if (parentNode == null)
            {
                if (index <= 0)
                {
                    return;
                }
                selectedNode.Remove();
                treeView1.Nodes.Insert(index - 1, selectedNode);
                object menuObj = nodeMenus[selectedNode.Name];
                if (menuObj != null)
                {
                    if (menuObj.GetType() == typeof(EIBMenu))
                    {
                        EIBMenu eibMenu = (EIBMenu)menuObj;
                        int menuIndex = ((ToolStrip)(currentMenuBar.Controls[0])).Items.IndexOf(eibMenu);
                        ((ToolStrip)(currentMenuBar.Controls[0])).Items.Remove(eibMenu);
                        ((ToolStrip)(currentMenuBar.Controls[0])).Items.Insert(menuIndex - 1, eibMenu);
                    }
                    else if (menuObj.GetType() == typeof(EIBMenuItem))
                    {
                        /*EIBMenuItem eibMenu = (EIBMenuItem)menuObj;
                        EIBMenu parentEibMenu = (EIBMenu)nodeMenus[selectedNode.Parent.Name];
                        int menuIndex = parentEibMenu.DropDownItems.IndexOf(eibMenu);
                        parentEibMenu.DropDownItems.Remove(eibMenu);
                        parentEibMenu.DropDownItems.Insert(menuIndex - 1, eibMenu);*/
                        //MessageBox.Show("Select parent menu");
                    }
                }
            }
            else if (selectedNode.Index > 0)
            {
                selectedNode.Remove();
                parentNode.Nodes.Insert(index - 1, selectedNode);
                object menuObj = nodeMenus[selectedNode.Name];
                if (menuObj != null)
                {
                    if (menuObj.GetType() == typeof(EIBMenu))
                    {
                        EIBMenu eibMenu = (EIBMenu)menuObj;
                        EIBMenu parentEibMenu = (EIBMenu)nodeMenus[selectedNode.Parent.Name];
                        int menuIndex = parentEibMenu.DropDownItems.IndexOf(eibMenu);
                        parentEibMenu.DropDownItems.Remove(eibMenu);
                        parentEibMenu.DropDownItems.Insert(menuIndex - 1, eibMenu);
                    }
                    else if (menuObj.GetType() == typeof(EIBMenuItem))
                    {
                        EIBMenuItem eibMenu = (EIBMenuItem)menuObj;
                        EIBMenu parentEibMenu = (EIBMenu)nodeMenus[selectedNode.Parent.Name];
                        int menuIndex = parentEibMenu.DropDownItems.IndexOf(eibMenu);
                        parentEibMenu.DropDownItems.Remove(eibMenu);
                        parentEibMenu.DropDownItems.Insert(menuIndex - 1, eibMenu);
                        //MessageBox.Show("Select parent menu");
                    }
                }
            }
            
            
        }

        private void button4_Click(object sender, EventArgs e)
        {
            TreeNode selectedNode = treeView1.SelectedNode;
            int index = selectedNode.Index;
            TreeNode parentNode = selectedNode.Parent;
            if (parentNode == null)
            {
                if (index >= treeView1.Nodes.Count - 1 )
                {
                    return;
                }
                selectedNode.Remove();
                treeView1.Nodes.Insert(index + 1, selectedNode);
                object menuObj = nodeMenus[selectedNode.Name];
                if (menuObj != null)
                {
                    if (menuObj.GetType() == typeof(EIBMenu))
                    {
                        EIBMenu eibMenu = (EIBMenu)menuObj;
                        int menuIndex = ((ToolStrip)(currentMenuBar.Controls[0])).Items.IndexOf(eibMenu);
                        ((ToolStrip)(currentMenuBar.Controls[0])).Items.Remove(eibMenu);
                        ((ToolStrip)(currentMenuBar.Controls[0])).Items.Insert(menuIndex + 1, eibMenu);
                    }
                    else if (menuObj.GetType() == typeof(EIBMenuItem))
                    {
                        /*EIBMenuItem eibMenu = (EIBMenuItem)menuObj;
                        EIBMenu parentEibMenu = (EIBMenu)nodeMenus[selectedNode.Parent.Name];
                        int menuIndex = parentEibMenu.DropDownItems.IndexOf(eibMenu);
                        parentEibMenu.DropDownItems.Remove(eibMenu);
                        parentEibMenu.DropDownItems.Insert(menuIndex - 1, eibMenu);*/
                        //MessageBox.Show("Select parent menu");
                    }
                }
            }
            else if (selectedNode.Index < parentNode.Nodes.Count - 1)
            {
                selectedNode.Remove();
                parentNode.Nodes.Insert(index + 1, selectedNode);
                object menuObj = nodeMenus[selectedNode.Name];
                if (menuObj != null)
                {
                    if (menuObj.GetType() == typeof(EIBMenu))
                    {
                        EIBMenu eibMenu = (EIBMenu)menuObj;
                        EIBMenu parentEibMenu = (EIBMenu)nodeMenus[selectedNode.Parent.Name];
                        int menuIndex = parentEibMenu.DropDownItems.IndexOf(eibMenu);
                        parentEibMenu.DropDownItems.Remove(eibMenu);
                        parentEibMenu.DropDownItems.Insert(menuIndex + 1, eibMenu);
                    }
                    else if (menuObj.GetType() == typeof(EIBMenuItem))
                    {
                        EIBMenuItem eibMenu = (EIBMenuItem)menuObj;
                        EIBMenu parentEibMenu = (EIBMenu)nodeMenus[selectedNode.Parent.Name];
                        int menuIndex = parentEibMenu.DropDownItems.IndexOf(eibMenu);
                        parentEibMenu.DropDownItems.Remove(eibMenu);
                        parentEibMenu.DropDownItems.Insert(menuIndex + 1, eibMenu);
                        //MessageBox.Show("Select parent menu");
                    }
                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            TreeNode selectedNode = treeView1.SelectedNode;
            if (selectedNode == null)
            {
                EIBMenu ctrl = (EIBMenu)UIEventManager.getToolBox.getControlType(FormDesignerConstants.MenuControl);
                ctrl.Text = "Menu";
                TreeNode tn = new TreeNode(ctrl.Text);
                tn.Name = ctrl.Name;
                treeView1.Nodes.Add(tn);
                nodeMenus.Add(tn.Name, ctrl);
                ((ToolStrip)(currentMenuBar.Controls[0])).Items.Add(ctrl);
            }
            else
            {
                object menu = nodeMenus[selectedNode.Name];
                if (menu != null)
                {
                    if (menu.GetType() == typeof(EIBMenu))
                    {
                        EIBMenu eibMenu = (EIBMenu)menu;
                        EIBMenu ctrl = (EIBMenu)UIEventManager.getToolBox.getControlType(FormDesignerConstants.MenuControl);
                        ctrl.Text = "SubMenu";
                        eibMenu.DropDownItems.Add(ctrl);
                        TreeNode tn = new TreeNode(ctrl.Text);
                        tn.Name = ctrl.Name;
                        selectedNode.Nodes.Add(tn);
                        nodeMenus.Add(tn.Name, ctrl);
                    }
                    else if (menu.GetType() == typeof(EIBMenuItem))
                    {
                        MessageBox.Show("Select parent menu");
                    }
                }
            }
        }

        private void filteredPropertyGrid1_PropertyValueChanged(object s, PropertyValueChangedEventArgs e)
        {
            if (e.ChangedItem.Label == "Text")
            {
                treeView1.SelectedNode.Text = e.ChangedItem.Value.ToString();
            }
        }

        private void treeView1_Click(object sender, EventArgs e)
        {

        }
        bool isSelected = true;
        private void treeView1_MouseUp(object sender, MouseEventArgs e)
        {
            //Point pnt = treeView1.PointToClient(new Point(e.X,e.Y));
            TreeNode tn = treeView1.GetNodeAt(e.X,e.Y);
            if (tn == null)
            {
                isSelected = false;
                treeView1.SelectedNode = null;
                showButton(false);
                filteredPropertyGrid1.Enabled = false;
                //filteredPropertyGrid1.SelectedObject = null;
            }
            else
            {
                isSelected = true;
            }
        }

        //Delete button code for menu editor.
        private void button5_Click(object sender, EventArgs e)
        {
            TreeNode selectedNode = treeView1.SelectedNode;
            TreeNode parentNode = selectedNode.Parent;
            if (parentNode == null)
            {
                ((ToolStrip)(currentMenuBar.Controls[0])).Items.Remove((ToolStripItem)nodeMenus[selectedNode.Name]);
                removeAllChild(nodeMenus[selectedNode.Name]);
                treeView1.Nodes.Remove(selectedNode);
            }
            else
            {
                EIBMenu parentMenu = (EIBMenu)nodeMenus[parentNode.Name];
                parentMenu.DropDownItems.Remove((ToolStripItem)nodeMenus[selectedNode.Name]);
                removeAllChild(nodeMenus[selectedNode.Name]);
                parentNode.Nodes.Remove(selectedNode);
            }
            isSelected = false;
            treeView1.SelectedNode = null;
            showButton(false);
        }
        public void removeAllChild(object menu)
        {
            if (menu.GetType() == typeof(EIBMenuItem))
            {
                nodeMenus.Remove(((EIBMenuItem)menu).Name);
            }
            else
            {
                foreach (object ctrl in ((EIBMenu)menu).DropDownItems)
                {
                    if (ctrl.GetType() == typeof(EIBMenu))
                    {
                        EIBMenu eibMenu = (EIBMenu)ctrl;
                        if (eibMenu.HasDropDownItems)
                        {
                            removeAllChild(eibMenu);
                        }
                        nodeMenus.Remove(eibMenu.Name);
                    }
                    else if (ctrl.GetType() == typeof(EIBMenuItem))
                    {
                        nodeMenus.Remove(((EIBMenuItem)ctrl).Name);
                    }
                }
            }
        }

        
    }
}