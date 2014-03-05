namespace EIBFormDesigner.Designer
{
    partial class FormulaEditorWindow
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
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("DefaultValue");
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("OnClick");
            System.Windows.Forms.TreeNode treeNode3 = new System.Windows.Forms.TreeNode("OnDoubleClick");
            System.Windows.Forms.TreeNode treeNode4 = new System.Windows.Forms.TreeNode("Entering");
            System.Windows.Forms.TreeNode treeNode5 = new System.Windows.Forms.TreeNode("Exiting");
            System.Windows.Forms.TreeNode treeNode6 = new System.Windows.Forms.TreeNode("OnDrop");
            System.Windows.Forms.TreeNode treeNode7 = new System.Windows.Forms.TreeNode("OnFocus");
            System.Windows.Forms.TreeNode treeNode8 = new System.Windows.Forms.TreeNode("OnBlur");

            System.Windows.Forms.TreeNode treeNode9 = new System.Windows.Forms.TreeNode("OnEventCreate");
            System.Windows.Forms.TreeNode treeNode10 = new System.Windows.Forms.TreeNode("OnEventEdit");
            System.Windows.Forms.TreeNode treeNode11 = new System.Windows.Forms.TreeNode("OnEventUpdate");
            System.Windows.Forms.TreeNode treeNode12 = new System.Windows.Forms.TreeNode("OnChange");
            System.Windows.Forms.TreeNode treeNode13 = new System.Windows.Forms.TreeNode("OnOK");
            System.Windows.Forms.TreeNode treeNode14 = new System.Windows.Forms.TreeNode("Design Element", new System.Windows.Forms.TreeNode[] {
            treeNode1,
            treeNode2,
            treeNode3,
            treeNode4,
            treeNode5,
            treeNode6,
            treeNode7,
            treeNode8,
            treeNode9,
            treeNode10,
            treeNode11,
            treeNode12,treeNode13});
            this.splitFormulaWindow = new System.Windows.Forms.SplitContainer();
            this.tabMethod = new System.Windows.Forms.TabControl();
            this.Object = new System.Windows.Forms.TabPage();
            this.designTree = new System.Windows.Forms.TreeView();
            this.reference = new System.Windows.Forms.TabPage();
            this.formulaTextPanel = new System.Windows.Forms.TableLayoutPanel();
            this.formulaText = new System.Windows.Forms.RichTextBox();
            this.designElementLabel = new System.Windows.Forms.Label();
            this.formulaDomain = new System.Windows.Forms.ComboBox();
            this.saveFormula = new System.Windows.Forms.Button();
            this.parserError = new System.Windows.Forms.Label();
            this.contextMenu = new System.Windows.Forms.ContextMenu();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.splitFormulaWindow.Panel1.SuspendLayout();
            this.splitFormulaWindow.Panel2.SuspendLayout();
            this.splitFormulaWindow.SuspendLayout();
            this.tabMethod.SuspendLayout();
            this.Object.SuspendLayout();
            this.formulaTextPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitFormulaWindow
            // 
            this.splitFormulaWindow.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitFormulaWindow.Location = new System.Drawing.Point(0, 0);
            this.splitFormulaWindow.Margin = new System.Windows.Forms.Padding(2);
            this.splitFormulaWindow.Name = "splitFormulaWindow";
            // 
            // splitFormulaWindow.Panel1
            // 
            this.splitFormulaWindow.Panel1.Controls.Add(this.tabMethod);
            // 
            // splitFormulaWindow.Panel2
            // 
            this.splitFormulaWindow.Panel2.Controls.Add(this.formulaTextPanel);
            this.splitFormulaWindow.Size = new System.Drawing.Size(632, 266);
            this.splitFormulaWindow.SplitterDistance = 210;
            this.splitFormulaWindow.SplitterWidth = 3;
            this.splitFormulaWindow.TabIndex = 1;
            // 
            // tabMethod
            // 
            this.tabMethod.Controls.Add(this.Object);
            this.tabMethod.Controls.Add(this.reference);
            this.tabMethod.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabMethod.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.tabMethod, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.tabMethod, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.tabMethod.Location = new System.Drawing.Point(0, 0);
            this.tabMethod.Margin = new System.Windows.Forms.Padding(2);
            this.tabMethod.Name = "tabMethod";
            this.tabMethod.SelectedIndex = 0;
            this.helpProvider1.SetShowHelp(this.tabMethod, true);
            this.tabMethod.Size = new System.Drawing.Size(210, 266);
            this.tabMethod.TabIndex = 0;
            // 
            // Object
            // 
            this.Object.Controls.Add(this.designTree);
            this.Object.Location = new System.Drawing.Point(4, 26);
            this.Object.Margin = new System.Windows.Forms.Padding(2);
            this.Object.Name = "Object";
            this.Object.Padding = new System.Windows.Forms.Padding(2);
            this.Object.Size = new System.Drawing.Size(202, 236);
            this.Object.TabIndex = 0;
            this.Object.Text = "Object";
            this.Object.UseVisualStyleBackColor = true;
            // 
            // designTree
            // 
            this.designTree.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.designTree, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.designTree, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.designTree.HideSelection = false;
            this.designTree.LineColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.designTree.Location = new System.Drawing.Point(2, 2);
            this.designTree.Margin = new System.Windows.Forms.Padding(2);
            this.designTree.Name = "designTree";
            treeNode1.Name = "DefaultValue";
            treeNode1.Text = "DefaultValue";
            treeNode2.Name = "OnClick";
            treeNode2.Text = "OnClick";
            treeNode3.Name = "OnDoubleClick";
            treeNode3.Text = "OnDoubleClick";
            treeNode4.Name = "Entering";
            treeNode4.Text = "Entering";
            treeNode5.Name = "Exiting";
            treeNode5.Text = "Exiting";
            treeNode6.Name = "OnDrop";
            treeNode6.Text = "OnDrop";
            treeNode7.Name = "OnFocus";
            treeNode7.Text = "OnFocus";
            treeNode8.Name = "OnBlur";
            treeNode8.Text = "OnBlur";

            treeNode9.Name = "OnEventCreate";
            treeNode9.Text = "OnEventCreate";

            treeNode10.Name = "OnEventEdit";
            treeNode10.Text = "OnEventEdit";
            
            treeNode11.Name = "OnEventUpdate";
            treeNode11.Text = "OnEventUpdate";

            treeNode12.Name = "OnChange";
            treeNode12.Text = "OnChange";

            treeNode13.Name = "OnOK";
            treeNode13.Text = "OnOK";


            treeNode14.Checked = true;
            treeNode14.Name = "DesignElement";
            treeNode14.Text = "Design Element";
            this.designTree.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode14});
            this.helpProvider1.SetShowHelp(this.designTree, true);
            this.designTree.ShowNodeToolTips = true;
            this.designTree.Size = new System.Drawing.Size(198, 232);
            this.designTree.TabIndex = 0;
            this.designTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.designTree_AfterSelect);
            // 
            // reference
            // 
            this.helpProvider1.SetHelpKeyword(this.reference, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.reference, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.reference.Location = new System.Drawing.Point(4, 26);
            this.reference.Margin = new System.Windows.Forms.Padding(2);
            this.reference.Name = "reference";
            this.reference.Padding = new System.Windows.Forms.Padding(2);
            this.helpProvider1.SetShowHelp(this.reference, true);
            this.reference.Size = new System.Drawing.Size(202, 236);
            this.reference.TabIndex = 1;
            this.reference.Text = "Reference";
            this.reference.UseVisualStyleBackColor = true;
            // 
            // formulaTextPanel
            // 
            this.formulaTextPanel.ColumnCount = 3;
            this.formulaTextPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 27.27273F));
            this.formulaTextPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 54.54546F));
            this.formulaTextPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 18.18182F));
            this.formulaTextPanel.Controls.Add(this.formulaText, 0, 1);
            this.formulaTextPanel.Controls.Add(this.designElementLabel, 0, 0);
            this.formulaTextPanel.Controls.Add(this.formulaDomain, 1, 0);
            this.formulaTextPanel.Controls.Add(this.saveFormula, 2, 0);
            this.formulaTextPanel.Controls.Add(this.parserError, 0, 2);
            this.formulaTextPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.formulaTextPanel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.formulaTextPanel, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.formulaTextPanel, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.formulaTextPanel.Location = new System.Drawing.Point(0, 0);
            this.formulaTextPanel.Margin = new System.Windows.Forms.Padding(2);
            this.formulaTextPanel.Name = "formulaTextPanel";
            this.formulaTextPanel.RowCount = 3;
            this.formulaTextPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.formulaTextPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.formulaTextPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.helpProvider1.SetShowHelp(this.formulaTextPanel, true);
            this.formulaTextPanel.Size = new System.Drawing.Size(419, 266);
            this.formulaTextPanel.TabIndex = 0;
            // 
            // formulaText
            // 
            this.formulaTextPanel.SetColumnSpan(this.formulaText, 3);
            this.formulaText.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.formulaText, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.formulaText, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.formulaText.Location = new System.Drawing.Point(2, 68);
            this.formulaText.Margin = new System.Windows.Forms.Padding(2);
            this.formulaText.Name = "formulaText";
            this.helpProvider1.SetShowHelp(this.formulaText, true);
            this.formulaText.Size = new System.Drawing.Size(415, 129);
            this.formulaText.TabIndex = 0;
            this.formulaText.Text = "";
            // 
            // designElementLabel
            // 
            this.designElementLabel.AutoSize = true;
            this.designElementLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.designElementLabel, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.designElementLabel, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.designElementLabel.Location = new System.Drawing.Point(2, 0);
            this.designElementLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.designElementLabel.Name = "designElementLabel";
            this.helpProvider1.SetShowHelp(this.designElementLabel, true);
            this.designElementLabel.Size = new System.Drawing.Size(110, 66);
            this.designElementLabel.TabIndex = 1;
            this.designElementLabel.Text = "Method:";
            // 
            // formulaDomain
            // 
            this.formulaDomain.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.formulaDomain, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.formulaDomain, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.formulaDomain.Items.AddRange(new object[] {
            "Client",
            "EIB"});
            this.formulaDomain.Location = new System.Drawing.Point(116, 2);
            this.formulaDomain.Margin = new System.Windows.Forms.Padding(2);
            this.formulaDomain.Name = "formulaDomain";
            this.helpProvider1.SetShowHelp(this.formulaDomain, true);
            this.formulaDomain.Size = new System.Drawing.Size(148, 25);
            this.formulaDomain.TabIndex = 2;
            // 
            // saveFormula
            // 
            this.saveFormula.Dock = System.Windows.Forms.DockStyle.Top;
            this.helpProvider1.SetHelpKeyword(this.saveFormula, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.saveFormula, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.saveFormula.Location = new System.Drawing.Point(344, 2);
            this.saveFormula.Margin = new System.Windows.Forms.Padding(2);
            this.saveFormula.Name = "saveFormula";
            this.helpProvider1.SetShowHelp(this.saveFormula, true);
            this.saveFormula.Size = new System.Drawing.Size(73, 32);
            this.saveFormula.TabIndex = 3;
            this.saveFormula.Text = "Save";
            this.saveFormula.UseVisualStyleBackColor = true;
            this.saveFormula.Click += new System.EventHandler(this.saveFormula_Click);
            // 
            // parserError
            // 
            this.parserError.AutoSize = true;
            this.formulaTextPanel.SetColumnSpan(this.parserError, 3);
            this.parserError.Dock = System.Windows.Forms.DockStyle.Fill;
            this.parserError.ForeColor = System.Drawing.Color.Red;
            this.helpProvider1.SetHelpKeyword(this.parserError, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this.parserError, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.parserError.Location = new System.Drawing.Point(2, 199);
            this.parserError.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.parserError.Name = "parserError";
            this.helpProvider1.SetShowHelp(this.parserError, true);
            this.parserError.Size = new System.Drawing.Size(415, 67);
            this.parserError.TabIndex = 4;
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // FormulaEditorWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(632, 266);
            this.Controls.Add(this.splitFormulaWindow);
            this.helpProvider1.SetHelpKeyword(this, "Formula Window");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Name = "FormulaEditorWindow";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockBottom;
            this.TabText = "FormulaWindow";
            this.Text = "FormulaEditorWindow";
            this.Load += new System.EventHandler(this.FormulaEditorWindow_Load);
            this.splitFormulaWindow.Panel1.ResumeLayout(false);
            this.splitFormulaWindow.Panel2.ResumeLayout(false);
            this.splitFormulaWindow.ResumeLayout(false);
            this.tabMethod.ResumeLayout(false);
            this.Object.ResumeLayout(false);
            this.formulaTextPanel.ResumeLayout(false);
            this.formulaTextPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        internal System.Windows.Forms.SplitContainer splitFormulaWindow;
        internal System.Windows.Forms.TabControl tabMethod;
        internal System.Windows.Forms.TabPage Object;
        internal System.Windows.Forms.TabPage reference;
        internal System.Windows.Forms.TreeView designTree;
        internal System.Windows.Forms.TableLayoutPanel formulaTextPanel;
        internal System.Windows.Forms.RichTextBox formulaText;
        internal System.Windows.Forms.Label designElementLabel;
        internal System.Windows.Forms.ComboBox formulaDomain;
        private System.Windows.Forms.Button saveFormula;
        private System.Windows.Forms.Label parserError;
        private System.Windows.Forms.ContextMenu contextMenu;
        private System.Windows.Forms.HelpProvider helpProvider1;

    }
}