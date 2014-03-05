using EIBFormDesigner.Properties;

namespace EIBFormDesigner.Designer
{
    partial class PropertyWindow
    {
        public FilteredPropertyGrid propertyGrid;
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
            this.propertyGrid = new EIBFormDesigner.Properties.FilteredPropertyGrid();
            this.propertToolBox = new System.Windows.Forms.Panel();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.controlList = new System.Windows.Forms.ComboBox();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.propertToolBox.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // propertyGrid
            // 
            this.propertyGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.propertyGrid, "Property Editor Window");
            this.helpProvider1.SetHelpNavigator(this.propertyGrid, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.propertyGrid.Location = new System.Drawing.Point(0, 0);
            this.propertyGrid.Margin = new System.Windows.Forms.Padding(2);
            this.propertyGrid.Name = "propertyGrid";
            this.helpProvider1.SetShowHelp(this.propertyGrid, true);
            this.propertyGrid.Size = new System.Drawing.Size(219, 168);
            this.propertyGrid.TabIndex = 0;
            // 
            // propertToolBox
            // 
            this.propertToolBox.Controls.Add(this.splitContainer1);
            this.propertToolBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.propertToolBox.Location = new System.Drawing.Point(0, 0);
            this.propertToolBox.Margin = new System.Windows.Forms.Padding(2);
            this.propertToolBox.Name = "propertToolBox";
            this.propertToolBox.Size = new System.Drawing.Size(219, 207);
            this.propertToolBox.TabIndex = 0;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Margin = new System.Windows.Forms.Padding(2);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.controlList);
            this.helpProvider1.SetHelpKeyword(this.splitContainer1.Panel1, "Tool Window");
            this.helpProvider1.SetHelpNavigator(this.splitContainer1.Panel1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.helpProvider1.SetShowHelp(this.splitContainer1.Panel1, true);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.propertyGrid);
            this.splitContainer1.Size = new System.Drawing.Size(219, 207);
            this.splitContainer1.SplitterDistance = 36;
            this.splitContainer1.SplitterWidth = 3;
            this.splitContainer1.TabIndex = 1;
            // 
            // controlList
            // 
            this.controlList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.controlList.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.controlList.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.controlList, "Property Editor Window");
            this.helpProvider1.SetHelpNavigator(this.controlList, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.controlList.Location = new System.Drawing.Point(0, 0);
            this.controlList.Margin = new System.Windows.Forms.Padding(2);
            this.controlList.Name = "controlList";
            this.helpProvider1.SetShowHelp(this.controlList, true);
            this.controlList.Size = new System.Drawing.Size(219, 25);
            this.controlList.TabIndex = 0;
            this.controlList.SelectedIndexChanged += new System.EventHandler(this.controlList_SelectedIndexChanged);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // PropertyWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(219, 207);
            this.Controls.Add(this.propertToolBox);
            this.helpProvider1.SetHelpKeyword(this, "Property Editor Window");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "PropertyWindow";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockRight;
            this.Text = "PropertyWindow";
            this.propertToolBox.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel propertToolBox;
        private System.Windows.Forms.SplitContainer splitContainer1;
        internal System.Windows.Forms.ComboBox controlList;
        private System.Windows.Forms.HelpProvider helpProvider1;
    }
}