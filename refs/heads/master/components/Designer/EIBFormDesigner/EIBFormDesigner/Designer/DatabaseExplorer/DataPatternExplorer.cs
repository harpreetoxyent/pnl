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

namespace EIBFormDesigner.Designer.DatabaseExlorer
{
    public partial class DataPatternExlorer : ToolWindow
    {
        private FormDesigner designerForm = null;
        private System.Windows.Forms.TreeView treeView1;
        private System.ComponentModel.IContainer components;

        public DataPatternExlorer(FormDesigner form)
        {
            designerForm = form;
            //
            // Required for Windows Form Designer support
            //
            this.Text = "Data Pattern Explorer";
            this.TabText = "Data Pattern Explorer";
            InitializeComponent();
            //initFormContextMenu();
            //initDataContextMenu();
            //initWorkflowContextMenu();
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DataPatternExlorer));
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.closeButton = new System.Windows.Forms.Button();
            this.assignButton = new System.Windows.Forms.Button();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // treeView1
            // 
            this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeView1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.treeView1, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.treeView1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.treeView1.ImageIndex = 5;
            this.treeView1.ImageList = this.imageList1;
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Margin = new System.Windows.Forms.Padding(2);
            this.treeView1.Name = "treeView1";
            this.treeView1.SelectedImageIndex = 1;
            this.helpProvider1.SetShowHelp(this.treeView1, true);
            this.treeView1.Size = new System.Drawing.Size(330, 337);
            this.treeView1.TabIndex = 0;
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
            this.splitContainer1.Panel2.Controls.Add(this.closeButton);
            this.splitContainer1.Panel2.Controls.Add(this.assignButton);
            this.helpProvider1.SetHelpKeyword(this.splitContainer1.Panel2, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.splitContainer1.Panel2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.helpProvider1.SetShowHelp(this.splitContainer1.Panel2, true);
            this.splitContainer1.Size = new System.Drawing.Size(330, 395);
            this.splitContainer1.SplitterDistance = 337;
            this.splitContainer1.SplitterWidth = 3;
            this.splitContainer1.TabIndex = 1;
            // 
            // closeButton
            // 
            this.helpProvider1.SetHelpKeyword(this.closeButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.closeButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.closeButton.Location = new System.Drawing.Point(83, 12);
            this.closeButton.Margin = new System.Windows.Forms.Padding(2);
            this.closeButton.Name = "closeButton";
            this.helpProvider1.SetShowHelp(this.closeButton, true);
            this.closeButton.Size = new System.Drawing.Size(70, 24);
            this.closeButton.TabIndex = 1;
            this.closeButton.Text = "&Close";
            this.closeButton.UseVisualStyleBackColor = true;
            this.closeButton.Click += new System.EventHandler(this.closeButton_Click);
            // 
            // assignButton
            // 
            this.assignButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.helpProvider1.SetHelpKeyword(this.assignButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.assignButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.assignButton.Location = new System.Drawing.Point(9, 12);
            this.assignButton.Margin = new System.Windows.Forms.Padding(2);
            this.assignButton.Name = "assignButton";
            this.helpProvider1.SetShowHelp(this.assignButton, true);
            this.assignButton.Size = new System.Drawing.Size(70, 24);
            this.assignButton.TabIndex = 0;
            this.assignButton.Text = "&Assign";
            this.assignButton.UseVisualStyleBackColor = true;
            this.assignButton.Click += new System.EventHandler(this.assignButton_Click);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // DataPatternExlorer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.ClientSize = new System.Drawing.Size(330, 395);
            this.Controls.Add(this.splitContainer1);
            this.helpProvider1.SetHelpKeyword(this, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "DataPatternExlorer";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockRight;
            this.Text = "DataPattern Explorer";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
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

 
        private void Form1_Load(object sender, System.EventArgs e)
        {
            //this.LoadSampleData();
            initExplorer();

        }

        internal void initExplorer()
        {
            this.treeView1.Nodes.Clear();
            DatabaseXMLServices.DeserializeDataPatternFiles(this.treeView1, EIBXMLUtilities.projectFilePath, null);
        }

        private void closeButton_Click(object sender, EventArgs e)
        {
            this.Hide();
            //this.Close();
        }

        private void assignButton_Click(object sender, EventArgs e)
        {
            //Check if there is a selected base window to work on
            if (designerForm.CurrentBaseWindow != null)
            {
                //check if it is Form Pattern
                if (designerForm.CurrentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.FormPattern))
                {
                    //check if there is selected control on the form
                    if (designerForm.CurrentBaseWindow.CurrentControl != null && designerForm.currentBaseWindow.CurrentControl.Count==1)
                    {
                        //Assign to this control the selected data node
                        TreeNode treeNode = this.treeView1.SelectedNode;
                        int nodeLevel = NodeLevel(treeNode);
                        if (! (nodeLevel == 0))
                        {
                            if (nodeLevel == 1)
                            {
                                MessageBox.Show("Incorrect Assignment","Warning -Assignment"); 
                                //designerForm.CurrentBaseWindow.CurrentControl.DataPatternName = treeNode.Text;
                            }
                            else if (nodeLevel == 2)
                            {
                                /*designerForm.CurrentBaseWindow.CurrentControl[0].DataTableName = treeNode.Text;
                                designerForm.CurrentBaseWindow.CurrentControl[0].DataPatternName = treeNode.Parent.Text;*/
                            }
                            else if (nodeLevel == 3)
                            {
                                /*designerForm.CurrentBaseWindow.CurrentControl[0].DataFieldName = treeNode.Text;
                                designerForm.CurrentBaseWindow.CurrentControl[0].DataTableName = treeNode.Parent.Text;
                                designerForm.CurrentBaseWindow.CurrentControl[0].DataPatternName = treeNode.Parent.Parent.Text;*/
                                if (designerForm.currentBaseWindow.CurrentControl[0].DataMappings == null)
                                {
                                    designerForm.currentBaseWindow.CurrentControl[0].DataMappings = new System.Collections.Generic.List<DataMapping>();
                                }
                                DataMapping dataMapping = new DataMapping(treeNode.Parent.Parent.Text, treeNode.Parent.Text, treeNode.Text);
                                designerForm.currentBaseWindow.CurrentControl[0].DataMappings.Add(dataMapping);
                            }
                        }
                    }
                }
            }
        }

  
    }
 }