namespace EIBFormDesigner.Designer
{
    partial class WorkflowEditorWindow
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
            System.Windows.Forms.TreeNode treeNode5 = new System.Windows.Forms.TreeNode("OnNode");
            System.Windows.Forms.TreeNode treeNode6 = new System.Windows.Forms.TreeNode("EnterNode");
            System.Windows.Forms.TreeNode treeNode7 = new System.Windows.Forms.TreeNode("ExitNode");
            System.Windows.Forms.TreeNode treeNode8 = new System.Windows.Forms.TreeNode("Design Element", new System.Windows.Forms.TreeNode[] {
            treeNode5,
            treeNode6,
            treeNode7});
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
            this.splitFormulaWindow.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.splitFormulaWindow.Name = "splitFormulaWindow";
            // 
            // splitFormulaWindow.Panel1
            // 
            this.splitFormulaWindow.Panel1.Controls.Add(this.tabMethod);
            // 
            // splitFormulaWindow.Panel2
            // 
            this.splitFormulaWindow.Panel2.Controls.Add(this.formulaTextPanel);
            this.splitFormulaWindow.Size = new System.Drawing.Size(843, 327);
            this.splitFormulaWindow.SplitterDistance = 280;
            this.splitFormulaWindow.TabIndex = 1;
            // 
            // tabMethod
            // 
            this.tabMethod.Controls.Add(this.Object);
            this.tabMethod.Controls.Add(this.reference);
            this.tabMethod.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabMethod.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.tabMethod, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.tabMethod, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.tabMethod.Location = new System.Drawing.Point(0, 0);
            this.tabMethod.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tabMethod.Name = "tabMethod";
            this.tabMethod.SelectedIndex = 0;
            this.helpProvider1.SetShowHelp(this.tabMethod, true);
            this.tabMethod.Size = new System.Drawing.Size(280, 327);
            this.tabMethod.TabIndex = 0;
            // 
            // Object
            // 
            this.Object.Controls.Add(this.designTree);
            this.Object.Location = new System.Drawing.Point(4, 29);
            this.Object.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Object.Name = "Object";
            this.Object.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Object.Size = new System.Drawing.Size(272, 294);
            this.Object.TabIndex = 0;
            this.Object.Text = "Object";
            this.Object.UseVisualStyleBackColor = true;
            // 
            // designTree
            // 
            this.designTree.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.designTree, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.designTree, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.designTree.HideSelection = false;
            this.designTree.LineColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.designTree.Location = new System.Drawing.Point(3, 2);
            this.designTree.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.designTree.Name = "designTree";
            treeNode5.Name = "OnNode";
            treeNode5.Text = "OnNode";
            treeNode6.Name = "EnterNode";
            treeNode6.Text = "EnterNode";
            treeNode7.Name = "ExitNode";
            treeNode7.Text = "ExitNode";
            treeNode8.Checked = true;
            treeNode8.Name = "DesignElement";
            treeNode8.Text = "Design Element";
            this.designTree.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode8});
            this.helpProvider1.SetShowHelp(this.designTree, true);
            this.designTree.ShowNodeToolTips = true;
            this.designTree.Size = new System.Drawing.Size(266, 290);
            this.designTree.TabIndex = 0;
            this.designTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.designTree_AfterSelect);
            // 
            // reference
            // 
            this.helpProvider1.SetHelpKeyword(this.reference, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.reference, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.reference.Location = new System.Drawing.Point(4, 29);
            this.reference.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.reference.Name = "reference";
            this.reference.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.helpProvider1.SetShowHelp(this.reference, true);
            this.reference.Size = new System.Drawing.Size(272, 294);
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
            this.helpProvider1.SetHelpKeyword(this.formulaTextPanel, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.formulaTextPanel, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.formulaTextPanel.Location = new System.Drawing.Point(0, 0);
            this.formulaTextPanel.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.formulaTextPanel.Name = "formulaTextPanel";
            this.formulaTextPanel.RowCount = 3;
            this.formulaTextPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.formulaTextPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.formulaTextPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.helpProvider1.SetShowHelp(this.formulaTextPanel, true);
            this.formulaTextPanel.Size = new System.Drawing.Size(559, 327);
            this.formulaTextPanel.TabIndex = 0;
            // 
            // formulaText
            // 
            this.formulaTextPanel.SetColumnSpan(this.formulaText, 3);
            this.formulaText.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.formulaText, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.formulaText, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.formulaText.Location = new System.Drawing.Point(3, 83);
            this.formulaText.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.formulaText.Name = "formulaText";
            this.helpProvider1.SetShowHelp(this.formulaText, true);
            this.formulaText.Size = new System.Drawing.Size(553, 159);
            this.formulaText.TabIndex = 0;
            this.formulaText.Text = "";
            // 
            // designElementLabel
            // 
            this.designElementLabel.AutoSize = true;
            this.designElementLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.designElementLabel, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.designElementLabel, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.designElementLabel.Location = new System.Drawing.Point(3, 0);
            this.designElementLabel.Name = "designElementLabel";
            this.helpProvider1.SetShowHelp(this.designElementLabel, true);
            this.designElementLabel.Size = new System.Drawing.Size(146, 81);
            this.designElementLabel.TabIndex = 1;
            this.designElementLabel.Text = "Method:";
            // 
            // formulaDomain
            // 
            this.formulaDomain.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.formulaDomain, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.formulaDomain, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.formulaDomain.Items.AddRange(new object[] {
            "Client",
            "EIB"});
            this.formulaDomain.Location = new System.Drawing.Point(155, 2);
            this.formulaDomain.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.formulaDomain.Name = "formulaDomain";
            this.helpProvider1.SetShowHelp(this.formulaDomain, true);
            this.formulaDomain.Size = new System.Drawing.Size(196, 28);
            this.formulaDomain.TabIndex = 2;
            // 
            // saveFormula
            // 
            this.saveFormula.Dock = System.Windows.Forms.DockStyle.Top;
            this.helpProvider1.SetHelpKeyword(this.saveFormula, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.saveFormula, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.saveFormula.Location = new System.Drawing.Point(459, 2);
            this.saveFormula.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.saveFormula.Name = "saveFormula";
            this.helpProvider1.SetShowHelp(this.saveFormula, true);
            this.saveFormula.Size = new System.Drawing.Size(97, 39);
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
            this.helpProvider1.SetHelpKeyword(this.parserError, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this.parserError, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.parserError.Location = new System.Drawing.Point(3, 244);
            this.parserError.Name = "parserError";
            this.helpProvider1.SetShowHelp(this.parserError, true);
            this.parserError.Size = new System.Drawing.Size(553, 83);
            this.parserError.TabIndex = 4;
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\formNew\\FormDesigner.chm";
            // 
            // WorkflowEditorWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(843, 327);
            this.Controls.Add(this.splitFormulaWindow);
            this.helpProvider1.SetHelpKeyword(this, "Design Elements");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(4, 2, 4, 2);
            this.Name = "WorkflowEditorWindow";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockBottom;
            this.TabText = "WorkflowWindow";
            this.Text = "WorkflowEditorWindow";
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