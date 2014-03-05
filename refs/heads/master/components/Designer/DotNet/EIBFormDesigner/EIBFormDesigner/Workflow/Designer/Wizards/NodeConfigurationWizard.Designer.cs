using WeifenLuo.WinFormsUI.Docking;
using System.Windows.Forms;

namespace EIBFormDesigner.Workflow.Designer.Wizards
{
    partial class NodeConfigurationWizard
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
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
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("FormPatterns");
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("DataPatterns");
            System.Windows.Forms.TreeNode treeNode3 = new System.Windows.Forms.TreeNode("UserPatterns");
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.splitContainer2 = new System.Windows.Forms.SplitContainer();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.dockPanel = new WeifenLuo.WinFormsUI.Docking.DockPanel();
            this.cancelWorkflow = new System.Windows.Forms.Button();
            this.doneWorkflow = new System.Windows.Forms.Button();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.splitContainer2.Panel1.SuspendLayout();
            this.splitContainer2.Panel2.SuspendLayout();
            this.splitContainer2.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Margin = new System.Windows.Forms.Padding(2);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.splitContainer2);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.cancelWorkflow);
            this.splitContainer1.Panel2.Controls.Add(this.doneWorkflow);
            this.splitContainer1.Size = new System.Drawing.Size(802, 554);
            this.splitContainer1.SplitterDistance = 500;
            this.splitContainer1.SplitterWidth = 3;
            this.splitContainer1.TabIndex = 1;
            // 
            // splitContainer2
            // 
            this.splitContainer2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer2.Location = new System.Drawing.Point(0, 0);
            this.splitContainer2.Name = "splitContainer2";
            // 
            // splitContainer2.Panel1
            // 
            this.splitContainer2.Panel1.AutoScroll = true;
            this.splitContainer2.Panel1.Controls.Add(this.treeView1);
            // 
            // splitContainer2.Panel2
            // 
            this.splitContainer2.Panel2.Controls.Add(this.dockPanel);
            this.splitContainer2.Size = new System.Drawing.Size(802, 500);
            this.splitContainer2.SplitterDistance = 126;
            this.splitContainer2.TabIndex = 0;
            // 
            // treeView1
            // 
            this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeView1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Name = "treeView1";
            treeNode1.Name = "FormPatterns";
            treeNode1.Text = "FormPatterns";
            treeNode2.Name = "DataPatterns";
            treeNode2.Text = "DataPatterns";
            treeNode3.Name = "UserPatterns";
            treeNode3.Text = "UserPatterns";
            this.treeView1.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode1,
            treeNode2,
            treeNode3});
            this.treeView1.Size = new System.Drawing.Size(126, 500);
            this.treeView1.TabIndex = 0;
            // 
            // dockPanel
            // 
            this.dockPanel.ActiveAutoHideContent = null;
            this.dockPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dockPanel.DocumentStyle = WeifenLuo.WinFormsUI.Docking.DocumentStyle.DockingSdi;
            this.dockPanel.Font = new System.Drawing.Font("Tahoma", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
            this.dockPanel.Location = new System.Drawing.Point(0, 0);
            this.dockPanel.Name = "dockPanel";
            this.dockPanel.Size = new System.Drawing.Size(672, 500);
            this.dockPanel.TabIndex = 0;
            // 
            // cancelWorkflow
            // 
            this.cancelWorkflow.Font = new System.Drawing.Font("Verdana", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelWorkflow.Location = new System.Drawing.Point(711, 13);
            this.cancelWorkflow.Margin = new System.Windows.Forms.Padding(2);
            this.cancelWorkflow.Name = "cancelWorkflow";
            this.cancelWorkflow.Size = new System.Drawing.Size(74, 27);
            this.cancelWorkflow.TabIndex = 1;
            this.cancelWorkflow.Text = "Ca&ncel";
            this.cancelWorkflow.UseVisualStyleBackColor = true;
            this.cancelWorkflow.Click += new System.EventHandler(this.cancelWorkflow_Click);
            // 
            // doneWorkflow
            // 
            this.doneWorkflow.Font = new System.Drawing.Font("Verdana", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.doneWorkflow.Location = new System.Drawing.Point(633, 13);
            this.doneWorkflow.Margin = new System.Windows.Forms.Padding(2);
            this.doneWorkflow.Name = "doneWorkflow";
            this.doneWorkflow.Size = new System.Drawing.Size(74, 27);
            this.doneWorkflow.TabIndex = 0;
            this.doneWorkflow.Text = "&Ok";
            this.doneWorkflow.UseVisualStyleBackColor = true;
            this.doneWorkflow.Click += new System.EventHandler(this.doneWorkflow_Click);
            // 
            // NodeConfigurationWizard
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(802, 554);
            this.Controls.Add(this.splitContainer1);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "NodeConfigurationWizard";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "NodeConfigurationWizard";
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.splitContainer2.Panel1.ResumeLayout(false);
            this.splitContainer2.Panel2.ResumeLayout(false);
            this.splitContainer2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.Button cancelWorkflow;
        private System.Windows.Forms.Button doneWorkflow;
        private System.Windows.Forms.SplitContainer splitContainer2;
        private System.Windows.Forms.TreeView treeView1;
        internal DockPanel dockPanel;

    }
}