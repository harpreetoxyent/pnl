using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Xml;
using System.Text;
using System.IO;
using System.Collections.Generic;
using EIBFormDesigner.XML;
using EIBXMLServices;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer.WebServiceClientExplorer;

namespace EIBFormDesigner.Designer.WebServiceExplorer
{
    public partial class WebServiceExplorer : ToolWindow
    {
        private FormDesigner designerForm = null;
        WebServiceClientExplorer.AddWebServiceClient addWebService;
        /*private Dictionary<string, EIBLabel> mapList = new Dictionary<string, EIBLabel>();

        internal Dictionary<string, EIBLabel> MapList
        {
            get {
                if (formPatternMapList.ContainsKey(designerForm.CurrentBaseWindow.Name))
                {
                    return formPatternMapList[designerForm.CurrentBaseWindow.Name];
                }
                else
                {
                    return null;
                }
            }
            //set { mapList = value; }
        }*/
        //internal Dictionary<string, Dictionary<string, EIBLabel>> formPatternMapList = new Dictionary<string, Dictionary<string, EIBLabel>>();
        //private Dictionary<string, EIBLabel> labelList = new Dictionary<string, EIBLabel>();
        

        public WebServiceExplorer(FormDesigner form)
        {
            designerForm = form;
            this.Text = "Web Service Explorer";
            this.TabText = "Web Service Explorer";
            InitializeComponent();
        }

        #region Designer Code
        /// <summary>
        /// Clean up any resources being used.
        /// </summary>

        private System.ComponentModel.IContainer components;
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(WebServiceExplorer));
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("WebServiceConsumers");
            this.WebServiceContextMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.addToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.webServiceClientToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.deleteContextMenuStrip = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.deleteToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.WebServiceContextMenu.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.deleteContextMenuStrip.SuspendLayout();
            this.SuspendLayout();
            // 
            // WebServiceContextMenu
            // 
            this.WebServiceContextMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.addToolStripMenuItem});
            this.WebServiceContextMenu.Name = "WebServiceContextMenu";
            this.WebServiceContextMenu.Size = new System.Drawing.Size(105, 26);
            // 
            // addToolStripMenuItem
            // 
            this.addToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.webServiceClientToolStripMenuItem});
            this.addToolStripMenuItem.Name = "addToolStripMenuItem";
            this.addToolStripMenuItem.Size = new System.Drawing.Size(104, 22);
            this.addToolStripMenuItem.Text = "Add";
            // 
            // webServiceClientToolStripMenuItem
            // 
            this.webServiceClientToolStripMenuItem.Name = "webServiceClientToolStripMenuItem";
            this.webServiceClientToolStripMenuItem.Size = new System.Drawing.Size(175, 22);
            this.webServiceClientToolStripMenuItem.Text = "Web Service Client";
            this.webServiceClientToolStripMenuItem.Click += new System.EventHandler(this.webServiceClientToolStripMenuItem_Click);
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
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.splitContainer1.IsSplitterFixed = true;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Margin = new System.Windows.Forms.Padding(2);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.treeView1);
            // 
            // splitContainer1.Panel2
            // 
            this.helpProvider1.SetHelpKeyword(this.splitContainer1.Panel2, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.splitContainer1.Panel2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.helpProvider1.SetShowHelp(this.splitContainer1.Panel2, true);
            this.splitContainer1.Size = new System.Drawing.Size(256, 396);
            this.splitContainer1.SplitterDistance = 351;
            this.splitContainer1.SplitterWidth = 3;
            this.splitContainer1.TabIndex = 1;
            // 
            // treeView1
            // 
            this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeView1.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.treeView1.ImageIndex = 5;
            this.treeView1.ImageList = this.imageList1;
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Name = "treeView1";
            treeNode1.ContextMenuStrip = this.WebServiceContextMenu;
            treeNode1.ImageIndex = 0;
            treeNode1.Name = "Node1";
            treeNode1.Text = "WebServiceConsumers";
            this.treeView1.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode1});
            this.treeView1.SelectedImageIndex = 0;
            this.treeView1.Size = new System.Drawing.Size(256, 351);
            this.treeView1.TabIndex = 0;
            this.treeView1.DoubleClick += new System.EventHandler(this.treeView1_DoubleClick);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // deleteContextMenuStrip
            // 
            this.deleteContextMenuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.deleteToolStripMenuItem});
            this.deleteContextMenuStrip.Name = "deleteContextMenuStrip";
            this.deleteContextMenuStrip.Size = new System.Drawing.Size(153, 48);
            // 
            // deleteToolStripMenuItem
            // 
            this.deleteToolStripMenuItem.Name = "deleteToolStripMenuItem";
            this.deleteToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.deleteToolStripMenuItem.Text = "Delete";
            this.deleteToolStripMenuItem.Click += new System.EventHandler(this.deleteToolStripMenuItem_Click);
            // 
            // WebServiceExplorer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.ClientSize = new System.Drawing.Size(256, 396);
            this.Controls.Add(this.splitContainer1);
            this.helpProvider1.SetHelpKeyword(this, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "WebServiceExplorer";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockRight;
            this.Text = "WebServiceExplorer";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.WebServiceContextMenu.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.deleteContextMenuStrip.ResumeLayout(false);
            this.ResumeLayout(false);

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
        #endregion

        #endregion

        private void Form1_Load(object sender, System.EventArgs e)
        {
            //this.LoadSampleData();
            initExplorer();

        }

        internal void initExplorer()
        {
            
        }

        public void serializeXMLMappings()
        {
            try
            {
                XmlDocument xmldoc = new XmlDocument();

                XmlElement xmlParentNode = xmldoc.CreateElement("WebServiceConsumers");
                foreach (KeyValuePair<string, WebServiceClientExplorer.WebServiceConsumer> webservKey in WebServiceClientExplorer.AddWebServiceClient.listWebServiceClient)
                {
                    webservKey.Value.Serialize(xmlParentNode);
                }
                string path = EIBXMLUtilities.folderName + @"\\webserviceconsumer";
                if (!Directory.Exists(path))
                {
                    Directory.CreateDirectory(path);
                }
                string fileName = path + "\\webserviceconsumer.xml";
                Stream xmlFile = new FileStream(fileName, FileMode.Create);
                xmldoc.AppendChild(xmlParentNode);
                xmldoc.Save(xmlFile);
                xmlFile.Close();
            }
            catch (Exception ex)
            {
                //MessageBox.Show("Map File get Corrupted during saving.");
            }

        }
        /// <summary>
        /// </summary>

        public void DeSerializeXMLMappings()
        {
            try
            {  
                this.initExplorer();
                treeView1.Nodes[0].Nodes.Clear();
                AddWebServiceClient.listWebServiceClient.Clear();
                AddWebServiceClient.listWebServiceName.Clear();
                XmlDocument xmldoc = new XmlDocument();
                string path = EIBXMLUtilities.folderName + @"\\webserviceconsumer";
                string fileName = path + "\\webserviceconsumer.xml";
                xmldoc.Load(fileName);
                foreach (XmlNode xmlNode in xmldoc.FirstChild.ChildNodes)
                {
                    WebServiceConsumer webservCons = new WebServiceConsumer();
                    webservCons.Deserialize(xmlNode);
                    AddWebServiceClient.listWebServiceClient.Add(webservCons.name, webservCons);
                    AddWebServiceClient.listWebServiceName.Add(webservCons.name, webservCons.name);
                    TreeNode webTreeNode = new TreeNode(webservCons.name);
                    webTreeNode.ContextMenuStrip = deleteContextMenuStrip;
                    treeView1.Nodes[0].Nodes.Add(webTreeNode);
                }
            }
            catch
            {

            }
        }

        private void webServiceClientToolStripMenuItem_Click(object sender, EventArgs e)
        {
            addWebService = new EIBFormDesigner.Designer.WebServiceClientExplorer.AddWebServiceClient();
            addWebService.resetValues("");
            DialogResult dResult = addWebService.ShowDialog();
            if (dResult == DialogResult.OK)
            {
                addWebService.newTreeNode.ContextMenuStrip = deleteContextMenuStrip;
                treeView1.Nodes[0].Nodes.Add(addWebService.newTreeNode);
                treeView1.ExpandAll();
                treeView1.SelectedNode = treeView1.Nodes[0];
            }
            else
            {
                return;
            }
        }

        private void treeView1_DoubleClick(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            try
            {
                TreeNode treeNode = treeView1.SelectedNode;
                if (treeNode != null)
                {
                    string name = treeNode.Text;
                    addWebService = new EIBFormDesigner.Designer.WebServiceClientExplorer.AddWebServiceClient();
                    if (addWebService.resetValues(name))
                    {
                        DialogResult dResult = addWebService.ShowDialog();
                        if (dResult == DialogResult.OK)
                        {
                            //Implemented inside addwebserviceclient method.
                        }
                        else
                        {
                            return;
                        }
                    }
                }
            }
            catch (Exception ex)
            {

            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void deleteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            TreeNode treeNode = treeView1.SelectedNode;
            if (treeNode != null)
            {
                string name = treeNode.Text;
                if (AddWebServiceClient.listWebServiceClient.ContainsKey(name))
                {
                    AddWebServiceClient.listWebServiceClient.Remove(name);
                    AddWebServiceClient.listWebServiceName.Remove(name);
                    treeNode.Remove();
                }
            }
        }
    }
 }