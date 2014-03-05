using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Xml;
using System.Text;
using System.IO;
using EIBFormDesigner.XML;
using EIBXMLServices;
using EIBFormDesigner.Workflow.XML;
using EIBFormDesigner.Database;

namespace EIBFormDesigner.Designer.Common
{
    public partial class ProjectExplorer : ToolWindow
    {
        private FormDesigner designerForm = null;
        private System.Windows.Forms.TreeView treeView1;
        private System.ComponentModel.IContainer components;
        private System.Windows.Forms.ContextMenu formContextMenu;
        private System.Windows.Forms.ContextMenu formDeleteContextMenu;
        
        private System.Windows.Forms.ContextMenu databaseContextMenu;
        private System.Windows.Forms.ContextMenu dataDeleteContextMenu;

        private System.Windows.Forms.ContextMenu workflowContextMenu;
        private System.Windows.Forms.ContextMenu workDeleteContextMenu;

        

        public void initDataContextMenu()
        {
            databaseContextMenu = new System.Windows.Forms.ContextMenu();
            dataDeleteContextMenu = new System.Windows.Forms.ContextMenu();

            databaseContextMenu.Popup += new System.EventHandler(this.dataContextMenuItem_Click);
            dataDeleteContextMenu.Popup += new System.EventHandler(this.dataContextMenuItem_Click);

            int numberOfMethodsSupported = 1;

            MenuItem[] methodAray = new System.Windows.Forms.MenuItem[numberOfMethodsSupported];
            MenuItem[] methodDeleteAray = new System.Windows.Forms.MenuItem[1];
            
            MenuItem menuItem = new MenuItem();
            menuItem.Index = 0;
            menuItem.Text = "Add";
            methodAray[0] = menuItem;

            MenuItem dataMenuItem = new MenuItem();
            dataMenuItem.Click += new System.EventHandler(this.dataContextMenuItem_Click);
            dataMenuItem.Index = 0;
            dataMenuItem.Text = "Data Pattern";
            menuItem.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] { dataMenuItem });

            MenuItem menuItem1 = new MenuItem();
            menuItem1.Click += new System.EventHandler(this.dataContextMenuItem_Click);
            menuItem1.Index = 0;
            menuItem1.Text = "Delete";
            methodDeleteAray[0] = menuItem1;

            databaseContextMenu.MenuItems.AddRange(methodAray);
            dataDeleteContextMenu.MenuItems.AddRange(methodDeleteAray);

        }

        public void initFormContextMenu()
        {
            formContextMenu = new System.Windows.Forms.ContextMenu();
            formDeleteContextMenu = new System.Windows.Forms.ContextMenu();

            formContextMenu.Popup += new System.EventHandler(this.formContextMenuItem_Click);
            formDeleteContextMenu.Popup += new System.EventHandler(this.formContextMenuItem_Click);
            
            int numberOfMethodsSupported = 1;
            MenuItem[] methodAray = new System.Windows.Forms.MenuItem[numberOfMethodsSupported];
            MenuItem[] methodDeleteAray = new System.Windows.Forms.MenuItem[1];
            
            
            MenuItem menuItem = new MenuItem();
            menuItem.Index = 0;
            menuItem.Text = "Add";
            methodAray[0] = menuItem;
           
            MenuItem formMenuItem = new MenuItem();
            formMenuItem.Click += new System.EventHandler(this.formContextMenuItem_Click);
            formMenuItem.Index = 0;
            formMenuItem.Text = "Form Pattern";
            menuItem.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {formMenuItem});

            MenuItem menuItem1 = new MenuItem();
            menuItem1.Click += new System.EventHandler(this.formContextMenuItem_Click);
            menuItem1.Index = 0;
            menuItem1.Text = "Delete";
            methodDeleteAray[0] = menuItem1;

            formContextMenu.MenuItems.AddRange(methodAray);
            formDeleteContextMenu.MenuItems.AddRange(methodDeleteAray);
        }


        public void initWorkflowContextMenu()
        {
            workflowContextMenu = new System.Windows.Forms.ContextMenu();
            workDeleteContextMenu = new System.Windows.Forms.ContextMenu();

            workflowContextMenu.Popup += new System.EventHandler(this.workflowContextMenuItem_Click);
            workDeleteContextMenu.Popup += new System.EventHandler(this.workflowContextMenuItem_Click);

            int numberOfMethodsSupported = 1;
            MenuItem[] methodAray = new System.Windows.Forms.MenuItem[numberOfMethodsSupported];
            MenuItem[] methodDeleteAray = new System.Windows.Forms.MenuItem[1];

            MenuItem menuItem = new MenuItem();
            menuItem.Index = 0;
            menuItem.Text = "Add";
            methodAray[0] = menuItem;

            MenuItem workflowMenuItem = new MenuItem();
            workflowMenuItem.Click += new System.EventHandler(this.workflowContextMenuItem_Click);
            workflowMenuItem.Index = 0;
            workflowMenuItem.Text = "Workflow Pattern";
            menuItem.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] { workflowMenuItem });

            MenuItem menuItem1 = new MenuItem();
            menuItem1.Click += new System.EventHandler(this.workflowContextMenuItem_Click);
            menuItem1.Index = 0;
            menuItem1.Text = "Delete";
            methodDeleteAray[0] = menuItem1;

            workflowContextMenu.MenuItems.AddRange(methodAray);
            workDeleteContextMenu.MenuItems.AddRange(methodDeleteAray);
        }
       
        public void formContextMenuItem_Click(object sender, System.EventArgs e)
        {
            
            if (sender is MenuItem)
            {
                MenuItem menuItem = (MenuItem)sender;
                if (menuItem.Text.Trim().Equals(FormDesignerConstants.DeleteBaseWindow))
                {
                    DialogResult result = MessageBox.Show("Do you want to delete the current formpattern", "Exit Window", MessageBoxButtons.OKCancel);
                    if (result == DialogResult.OK)
                    {
                        TreeNode selecetdTreeNode = this.treeView1.SelectedNode;
                        if (selecetdTreeNode != null)
                        {
                            if (FormDesigner.listFormBaseWindow.ContainsKey(selecetdTreeNode.Text))
                            {
                                //FormDesigner.listFormBaseWindow[baseWindow.Name].Close();
                                BaseWindow formBaseWindow = FormDesigner.listFormBaseWindow[selecetdTreeNode.Text];

                                //Added by HKU to tell that user deleting a defaultscreen
                                if (formBaseWindow.baseFrame.DefaultScreen)
                                {
                                    MessageBox.Show("You are deleting a defaultscreen formpattern. Make another formpattern as defaultscreen");
                                }
                                //Adding by HKU done
                                if (FormDesigner.listBaseWindow.Contains(formBaseWindow))
                                {
                                    FormDesignerUtilities.visibleTrueWindow.Clear();
                                }
                                FormDesigner.listFormBaseWindow.Remove(selecetdTreeNode.Text);

                                if (FormDesigner.listBaseWindow.Contains(formBaseWindow))
                                {
                                    FormDesigner.listBaseWindow.Remove(formBaseWindow);
                                }
                                formBaseWindow.Close();
                                designerForm.displayNameExplorer.formPatternMapList.Remove(formBaseWindow.Name);
                            }
                            XmlDocument document = designerForm.applicationDoc;
                            XmlNode formXmlNode = document.FirstChild.SelectSingleNode(FormDesignerConstants.FormPattern);
                            foreach (XmlNode xmlNode in formXmlNode.ChildNodes)
                            {
                                if (xmlNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value.Trim().Equals(selecetdTreeNode.Text))
                                {
                                    formXmlNode.RemoveChild(xmlNode);
                                }
                            }
                            designerForm.saveToolStripMenuItem_Click(null, null);

                        }
                    }
                }
                else
                {
                    designerForm.initBaseWindow(FormDesignerConstants.FormDesigner, null, false);
                    designerForm.propertyEditorToolStripMenuItem_Click(null, null);
                    designerForm.formulaEditorToolStripMenuItem_Click(null, null);
                    designerForm.datapatternExplorerToolStripMenuItem_Click(null, null);
                    designerForm.toolBoxToolStripMenuItem_Click(null, null);
                    designerForm.toolBoxWindow.tabControl.SelectedIndex = 0;
                    designerForm.baseWindowDefaultToolClick(null, null);

                }
            }
        }
        public void dataContextMenuItem_Click(object sender, System.EventArgs e)
        {

            if (sender is MenuItem)
            {
                MenuItem menuItem = (MenuItem)sender;
                if (menuItem.Text.Trim().Equals(FormDesignerConstants.DeleteBaseWindow))
                {
                    DialogResult result = MessageBox.Show("Do you want to delete the current datapattern", "Exit Window", MessageBoxButtons.OKCancel);
                    if (result == DialogResult.OK)
                    {
                        TreeNode selecetdTreeNode = this.treeView1.SelectedNode;
                        if (selecetdTreeNode != null)
                        {
                            if (FormDesigner.listDataBaseWindow.ContainsKey(selecetdTreeNode.Text))
                            {
                                BaseWindow dataBaseWindow = FormDesigner.listDataBaseWindow[selecetdTreeNode.Text];
                                FormDesigner.listDataBaseWindow.Remove(selecetdTreeNode.Text);
                                if (FormDesigner.listBaseWindow.Contains(dataBaseWindow))
                                {
                                    FormDesigner.listBaseWindow.Remove(dataBaseWindow);
                                }
                                dataBaseWindow.Close();

                            }
                            XmlDocument document = designerForm.applicationDoc;
                            XmlNode formXmlNode = document.FirstChild.SelectSingleNode(FormDesignerConstants.DataPattern);
                            foreach (XmlNode xmlNode in formXmlNode.ChildNodes)
                            {
                                if (xmlNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value.Trim().Equals(selecetdTreeNode.Text))
                                {
                                    formXmlNode.RemoveChild(xmlNode);
                                }
                            }
                            designerForm.saveToolStripMenuItem_Click(null, null);
                        }
                    }
                }
                else
                {
                    designerForm.initBaseWindow(FormDesignerConstants.DatabaseDesigner, null, false);
                    designerForm.propertyEditorToolStripMenuItem_Click(null, null);
                    designerForm.datapatternExplorerToolStripMenuItem_Click(null, null);
                    //designerForm.projectExplorerToolStripMenuItem_Click(null, null);
                    designerForm.projectExplorerShowToolStrip();
                    designerForm.toolBoxToolStripMenuItem_Click(null, null);
                    designerForm.toolBoxWindow.tabControl.SelectedIndex = 1;
                    designerForm.baseWindowDefaultToolClick(null, null);
                }
            }
        }
        public void workflowContextMenuItem_Click(object sender, System.EventArgs e)
        {

            if (sender is MenuItem)
            {
                MenuItem menuItem = (MenuItem)sender;
                if (menuItem.Text.Trim().Equals(FormDesignerConstants.DeleteBaseWindow))
                {
                    DialogResult result = MessageBox.Show("Do you want to delete the current workflowpattern", "Exit Window", MessageBoxButtons.OKCancel);
                    if (result == DialogResult.OK)
                    {
                        TreeNode selecetdTreeNode = this.treeView1.SelectedNode;
                        if (selecetdTreeNode != null)
                        {
                            if (FormDesigner.listWorkBaseWindow.ContainsKey(selecetdTreeNode.Text))
                            {
                                BaseWindow workWindow = FormDesigner.listWorkBaseWindow[selecetdTreeNode.Text];
                                FormDesigner.listWorkBaseWindow.Remove(selecetdTreeNode.Text);
                                if (FormDesigner.listBaseWindow.Contains(workWindow))
                                {
                                    FormDesigner.listBaseWindow.Remove(workWindow);
                                }
                                workWindow.Close();

                            }
                            XmlDocument document = designerForm.applicationDoc;
                            XmlNode formXmlNode = document.FirstChild.SelectSingleNode(FormDesignerConstants.WorkflowPattern);
                            foreach (XmlNode xmlNode in formXmlNode.ChildNodes)
                            {
                                if (xmlNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value.Trim().Equals(selecetdTreeNode.Text))
                                {
                                    formXmlNode.RemoveChild(xmlNode);
                                }
                            }
                            designerForm.saveToolStripMenuItem_Click(null, null);
                        }
                    }
                }
                else
                {


                    designerForm.initBaseWindow(FormDesignerConstants.WorkflowDesigner, null, false);
                    designerForm.propertyEditorToolStripMenuItem_Click(null, null);
                    designerForm.datapatternExplorerToolStripMenuItem_Click(null, null);
                    designerForm.projectExplorerShowToolStrip();
                    designerForm.toolBoxToolStripMenuItem_Click(null, null);
                    designerForm.toolBoxWindow.tabControl.SelectedIndex = 2;
                    designerForm.baseWindowDefaultToolClick(null, null);
                }
            }
        }
        
        public ProjectExplorer(FormDesigner form)
        {
            designerForm = form;
            //
            // Required for Windows Form Designer support
            //

            InitializeComponent();
            this.Text = "Project Explorer";
            this.TabText = "Project Explorer";

            initFormContextMenu();
            initDataContextMenu();
            initWorkflowContextMenu();
            //
            // TODO: Add any constructor code after InitializeComponent call
            //
        }

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code
        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ProjectExplorer));
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // treeView1
            // 
            this.treeView1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.treeView1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.treeView1, "Project Explorer Window");
            this.helpProvider1.SetHelpNavigator(this.treeView1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.treeView1.ImageIndex = 5;
            this.treeView1.ImageList = this.imageList1;
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Margin = new System.Windows.Forms.Padding(2);
            this.treeView1.Name = "treeView1";
            this.treeView1.SelectedImageIndex = 1;
            this.helpProvider1.SetShowHelp(this.treeView1, true);
            this.treeView1.Size = new System.Drawing.Size(251, 353);
            this.treeView1.TabIndex = 0;
            this.treeView1.DoubleClick += new System.EventHandler(this.treeView1_DoubleClick);
            this.treeView1.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView1_AfterSelect);
            this.treeView1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.treeView1_MouseDown);
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "");
            this.imageList1.Images.SetKeyName(1, "");
            this.imageList1.Images.SetKeyName(2, "");
            this.imageList1.Images.SetKeyName(3, "");
            this.imageList1.Images.SetKeyName(4, "");
            this.imageList1.Images.SetKeyName(5, "");
            this.imageList1.Images.SetKeyName(6, "");
            this.imageList1.Images.SetKeyName(7, "");
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // ProjectExplorer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.ClientSize = new System.Drawing.Size(250, 395);
            this.Controls.Add(this.treeView1);
            this.helpProvider1.SetHelpKeyword(this, "Project Explorer Window");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Name = "ProjectExplorer";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockRight;
            this.Text = "Project Explorer";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);

        }

        void treeView1_MouseDown(object sender, MouseEventArgs e)
        {
            try
            {
                if (e.Button == MouseButtons.Right)
                {
                    treeView1.SelectedNode = treeView1.GetNodeAt(e.X, e.Y);
                    int nodeLevel = NodeLevel(treeView1.SelectedNode);
                    if (nodeLevel == 0)
                    {
                        if (treeView1.SelectedNode.Text.Trim().Equals(FormDesignerConstants.FormDesigner))
                        {
                            formContextMenu.Show(treeView1, new Point(e.X, e.Y));
                        }
                        if (treeView1.SelectedNode.Text.Trim().Equals(FormDesignerConstants.DatabaseDesigner))
                        {
                            databaseContextMenu.Show(treeView1, new Point(e.X, e.Y));
                        }
                        if (treeView1.SelectedNode.Text.Trim().Equals(FormDesignerConstants.WorkflowDesigner))
                        {
                            workflowContextMenu.Show(treeView1, new Point(e.X, e.Y));
                        }
                    }
                    if (nodeLevel == 1)
                    {
                        if (treeView1.SelectedNode.Parent.Text.Trim().Equals(FormDesignerConstants.FormPattern))
                        {
                            formDeleteContextMenu.Show(treeView1, new Point(e.X, e.Y));
                        }
                        if (treeView1.SelectedNode.Parent.Text.Trim().Equals(FormDesignerConstants.DataPattern))
                        {
                            dataDeleteContextMenu.Show(treeView1, new Point(e.X, e.Y));
                        }
                        if (treeView1.SelectedNode.Parent.Text.Trim().Equals(FormDesignerConstants.WorkflowPattern))
                        {
                            workDeleteContextMenu.Show(treeView1, new Point(e.X, e.Y));
                        }
                    }

                }
            }
            catch(Exception ex)
            {

            }
        }
        public int NodeLevel(TreeNode node)
        {

            int level = 0;
            while ((node = node.Parent) != null)
            {
                level++;
            }
            return level;

        } 

        void treeView1_DoubleClick(object sender, EventArgs e)
        {
            TreeView treeView = (TreeView)sender;
            if (treeView != null)
            {
                string fileName = null;
                TreeNode treeNode = treeView.SelectedNode;
                if (treeNode != null)
                {
                    if (treeNode.Parent != null)
                    {
                        if (treeNode.Parent.Text == FormDesignerConstants.FormPattern)
                        {
                            fileName = EIBXMLUtilities.formFolderName + "\\" + treeNode.Text;
                            if (FormDesigner.listFormBaseWindow.ContainsKey(treeNode.Text))
                            {
                                BaseWindow basewindow = FormDesigner.listFormBaseWindow[treeNode.Text];
                                designerForm.currentBaseWindow = basewindow;
                            }
                            else
                            {
                                //designerForm.initBaseWindow(FormDesignerConstants.FormPattern, treeNode.Text, true);
                                //EIBDeserializeServices.DeserializeWindowXML(fileName, designerForm.currentBaseWindow.baseFrame, null);
                            }
                        }
                        else if (treeNode.Parent.Text == FormDesignerConstants.DataPattern)
                        {
                            fileName = EIBXMLUtilities.dataFolderName + "\\" + treeNode.Text;
                            if (FormDesigner.listDataBaseWindow.ContainsKey(treeNode.Text))
                            {
                                BaseWindow basewindow = FormDesigner.listDataBaseWindow[treeNode.Text];
                                designerForm.currentBaseWindow = basewindow;
                            }
                            else
                            {
                                designerForm.initBaseWindow(FormDesignerConstants.DataPattern, treeNode.Text, true);
                                DatabaseDeserializeServices.DeserializeWindowXML(fileName, designerForm.currentBaseWindow.baseFrame, null);
                                designerForm.currentBaseWindow.baseFrame.Refresh();
                            }
                        }
                        else if (treeNode.Parent.Text == FormDesignerConstants.WorkflowPattern)
                        {
                            fileName = EIBXMLUtilities.workflowFolderName + "\\" + treeNode.Text;
                            if (FormDesigner.listWorkBaseWindow.ContainsKey(treeNode.Text))
                            {
                                BaseWindow basewindow = FormDesigner.listWorkBaseWindow[treeNode.Text];
                                designerForm.currentBaseWindow = basewindow;
                            }
                            else
                            {
                                designerForm.initBaseWindow(FormDesignerConstants.WorkflowPattern, treeNode.Text, true);
                                WorkflowDeserializeServices.DeserializeWindowXML(fileName, designerForm.currentBaseWindow.baseFrame, null);
                            }
                        }
                        designerForm.baseWindowDefaultToolClick(null, null);

                        try
                        {
                            //Code For Screen Refreshing of Extra Lines comming on the screen in DataPattern window and WorkFlowpatrern window.
                            if (treeNode.Parent.Text == FormDesignerConstants.DataPattern)
                            {
                                if (designerForm.currentBaseWindow.Lines.Count > 0)
                                {
                                    ((EIBFormDesigner.Database.Table.Line)designerForm.currentBaseWindow.Lines[0]).mark1.tableConnectorList[0].Redraw();
                                }
                            }
                            else if (treeNode.Parent.Text == FormDesignerConstants.WorkflowPattern)
                            {
                                if (designerForm.currentBaseWindow.Lines.Count > 0)
                                {
                                    ((EIBFormDesigner.Workflow.Controls.Line)designerForm.currentBaseWindow.Lines[0]).mark1.nodeConnectorList[0].Redraw();
                                }
                            }
                        }
                        catch
                        {

                        }

                    }
                }
            }
        }

        void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {

            TreeView treeView = (TreeView)sender;
            if (treeView != null)
            {
                TreeNode treeNode = treeView.SelectedNode;
                if (treeNode != null)
                {
                    if (treeNode.Text.Trim().Equals(FormDesignerConstants.FormPattern))
                    {
                        //Do Form Pattern
                    }
                    if (treeNode.Text.Trim().Equals(FormDesignerConstants.DataPattern))
                    {
                        //Do Data Pattern
                    }
                    if (treeNode.Text.Trim().Equals(FormDesignerConstants.WorkflowPattern))
                    {
                        //Do Workflow Pattern
                    }
                }
            }
        }
        public void changeProjectItemSelection(string patternName,string typeOfWindow)
        {
            int indexOfType = -1;
            if (!string.IsNullOrEmpty(typeOfWindow))
            {
                if (typeOfWindow.Equals(FormDesignerConstants.FormPattern))
                {
                    indexOfType = 0;
                    //Do Form Pattern
                }
                if (typeOfWindow.Equals(FormDesignerConstants.DataPattern))
                {
                    indexOfType = 1;
                    //Do Data Pattern
                }
                if (typeOfWindow.Equals(FormDesignerConstants.WorkflowPattern))
                {
                    indexOfType = 2;
                    //Do Workflow Pattern
                }
                if (indexOfType != -1)
                {
                    foreach (TreeNode tnode in treeView1.Nodes[indexOfType].Nodes)
                    {
                        if (tnode.Text == patternName)
                        {
                            treeView1.SelectedNode = tnode;
                            break;
                        }
                    }
                    //treeView1_DoubleClick(treeView1, null);
                }
            }
        }
        #endregion

 
        private void Form1_Load(object sender, System.EventArgs e)
        {
            //this.LoadSampleData();
            //initExplorer();

        }

        internal void initExplorer()
        {
            this.treeView1.Nodes.Clear();
            FormDesignerUtilities.ClearUserControl();
            EIBDeserializeServices.DeserializeProjectFile(this.treeView1, EIBXMLUtilities.projectFilePath, null);
            foreach (string baseWin in FormDesigner.listFormBaseWindow.Keys)
            {
                if (!designerForm.displayNameExplorer.formPatternMapList.ContainsKey(baseWin))
                {
                    designerForm.displayNameExplorer.formPatternMapList.Add(baseWin, new System.Collections.Generic.Dictionary<string, EIBFormDesigner.Controls.EIBLabel>());
                }
                if (!FormDesigner.listFormBaseWindow[baseWin].baseFrame.isMouseClick)
                {
                    FormDesigner.listFormBaseWindow[baseWin].baseFrame.MouseClick += new MouseEventHandler(FormDesigner.eventManager.handleControlClick);
                    FormDesigner.listFormBaseWindow[baseWin].baseFrame.isMouseClick = true;
                }
            }
            designerForm.displayNameExplorer.DeSerializeXMLMappings();
            foreach (string baseWin in FormDesigner.listDataBaseWindow.Keys)
            {

                if (!FormDesigner.listDataBaseWindow[baseWin].baseFrame.isMouseClick)
                {
                    FormDesigner.listDataBaseWindow[baseWin].baseFrame.MouseClick += new MouseEventHandler(FormDesigner.eventManager.handleControlClick);
                    FormDesigner.listDataBaseWindow[baseWin].baseFrame.isMouseClick = true;
                }
            }
            foreach (string baseWin in FormDesigner.listWorkBaseWindow.Keys)
            {
                if (!FormDesigner.listWorkBaseWindow[baseWin].baseFrame.isMouseClick)
                {
                    FormDesigner.listWorkBaseWindow[baseWin].baseFrame.MouseClick += new MouseEventHandler(FormDesigner.eventManager.handleControlClick);
                    FormDesigner.listWorkBaseWindow[baseWin].baseFrame.isMouseClick = true;
                }
            }
        }

        
    }
 }