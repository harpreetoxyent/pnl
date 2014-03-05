namespace EIBFormDesigner.Workflow.Designer.Wizards
{
    partial class WorkflowPropertyEditor
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
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.cancelWorkflow = new System.Windows.Forms.Button();
            this.doneWorkflow = new System.Windows.Forms.Button();
            this.tabPage3 = new System.Windows.Forms.TabPage();
            this.roleName = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.roleListBox = new System.Windows.Forms.ListBox();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.label2 = new System.Windows.Forms.Label();
            this.dataIdText = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.statusCombo = new System.Windows.Forms.ComboBox();
            this.statusName = new System.Windows.Forms.TextBox();
            this.button5 = new System.Windows.Forms.Button();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.dataPatternName = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.button7 = new System.Windows.Forms.Button();
            this.button6 = new System.Windows.Forms.Button();
            this.dataPatternList = new System.Windows.Forms.ListBox();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.tabPage3.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.tabControl1.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.tabControl1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.cancelWorkflow);
            this.splitContainer1.Panel2.Controls.Add(this.doneWorkflow);
            this.splitContainer1.Size = new System.Drawing.Size(383, 260);
            this.splitContainer1.SplitterDistance = 200;
            this.splitContainer1.SplitterWidth = 3;
            this.splitContainer1.TabIndex = 1;
            // 
            // cancelWorkflow
            // 
            this.cancelWorkflow.Location = new System.Drawing.Point(231, 11);
            this.cancelWorkflow.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.cancelWorkflow.Name = "cancelWorkflow";
            this.cancelWorkflow.Size = new System.Drawing.Size(58, 27);
            this.cancelWorkflow.TabIndex = 1;
            this.cancelWorkflow.Text = "Cancel";
            this.cancelWorkflow.UseVisualStyleBackColor = true;
            // 
            // doneWorkflow
            // 
            this.doneWorkflow.Location = new System.Drawing.Point(160, 11);
            this.doneWorkflow.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.doneWorkflow.Name = "doneWorkflow";
            this.doneWorkflow.Size = new System.Drawing.Size(58, 27);
            this.doneWorkflow.TabIndex = 0;
            this.doneWorkflow.Text = "Done";
            this.doneWorkflow.UseVisualStyleBackColor = true;
            // 
            // tabPage3
            // 
            this.tabPage3.Controls.Add(this.roleListBox);
            this.tabPage3.Controls.Add(this.button4);
            this.tabPage3.Controls.Add(this.button3);
            this.tabPage3.Controls.Add(this.label1);
            this.tabPage3.Controls.Add(this.roleName);
            this.tabPage3.Location = new System.Drawing.Point(4, 26);
            this.tabPage3.Margin = new System.Windows.Forms.Padding(2);
            this.tabPage3.Name = "tabPage3";
            this.tabPage3.Padding = new System.Windows.Forms.Padding(2);
            this.tabPage3.Size = new System.Drawing.Size(375, 170);
            this.tabPage3.TabIndex = 2;
            this.tabPage3.Text = "UserPattern";
            this.tabPage3.UseVisualStyleBackColor = true;
            // 
            // roleName
            // 
            this.roleName.Location = new System.Drawing.Point(82, 15);
            this.roleName.Margin = new System.Windows.Forms.Padding(2);
            this.roleName.Name = "roleName";
            this.roleName.Size = new System.Drawing.Size(101, 23);
            this.roleName.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 15);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(78, 17);
            this.label1.TabIndex = 1;
            this.label1.Text = "Role Name";
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(7, 46);
            this.button3.Margin = new System.Windows.Forms.Padding(2);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(65, 21);
            this.button3.TabIndex = 2;
            this.button3.Text = "Add";
            this.button3.UseVisualStyleBackColor = true;
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(82, 46);
            this.button4.Margin = new System.Windows.Forms.Padding(2);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(59, 21);
            this.button4.TabIndex = 3;
            this.button4.Text = "Delete";
            this.button4.UseVisualStyleBackColor = true;
            // 
            // roleListBox
            // 
            this.roleListBox.FormattingEnabled = true;
            this.roleListBox.ItemHeight = 17;
            this.roleListBox.Location = new System.Drawing.Point(5, 73);
            this.roleListBox.Margin = new System.Windows.Forms.Padding(2);
            this.roleListBox.Name = "roleListBox";
            this.roleListBox.Size = new System.Drawing.Size(226, 72);
            this.roleListBox.TabIndex = 4;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.button5);
            this.tabPage2.Controls.Add(this.statusName);
            this.tabPage2.Controls.Add(this.dataIdText);
            this.tabPage2.Controls.Add(this.statusCombo);
            this.tabPage2.Controls.Add(this.label3);
            this.tabPage2.Controls.Add(this.label2);
            this.tabPage2.Location = new System.Drawing.Point(4, 26);
            this.tabPage2.Margin = new System.Windows.Forms.Padding(2);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(2);
            this.tabPage2.Size = new System.Drawing.Size(375, 170);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "DataObject";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(14, 11);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 17);
            this.label2.TabIndex = 0;
            this.label2.Text = "Data Id";
            // 
            // dataIdText
            // 
            this.dataIdText.Location = new System.Drawing.Point(82, 9);
            this.dataIdText.Margin = new System.Windows.Forms.Padding(2);
            this.dataIdText.Name = "dataIdText";
            this.dataIdText.Size = new System.Drawing.Size(85, 23);
            this.dataIdText.TabIndex = 1;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(14, 44);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(48, 17);
            this.label3.TabIndex = 2;
            this.label3.Text = "Status";
            // 
            // statusCombo
            // 
            this.statusCombo.FormattingEnabled = true;
            this.statusCombo.Location = new System.Drawing.Point(82, 39);
            this.statusCombo.Margin = new System.Windows.Forms.Padding(2);
            this.statusCombo.Name = "statusCombo";
            this.statusCombo.Size = new System.Drawing.Size(92, 25);
            this.statusCombo.TabIndex = 3;
            // 
            // statusName
            // 
            this.statusName.Location = new System.Drawing.Point(184, 39);
            this.statusName.Margin = new System.Windows.Forms.Padding(2);
            this.statusName.Name = "statusName";
            this.statusName.Size = new System.Drawing.Size(85, 23);
            this.statusName.TabIndex = 4;
            // 
            // button5
            // 
            this.button5.Location = new System.Drawing.Point(280, 39);
            this.button5.Margin = new System.Windows.Forms.Padding(2);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(64, 23);
            this.button5.TabIndex = 5;
            this.button5.Text = "Add";
            this.button5.UseVisualStyleBackColor = true;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.dataPatternList);
            this.tabPage1.Controls.Add(this.button6);
            this.tabPage1.Controls.Add(this.button7);
            this.tabPage1.Controls.Add(this.label4);
            this.tabPage1.Controls.Add(this.dataPatternName);
            this.tabPage1.Location = new System.Drawing.Point(4, 26);
            this.tabPage1.Margin = new System.Windows.Forms.Padding(2);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(2);
            this.tabPage1.Size = new System.Drawing.Size(375, 170);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "DataPattern";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // dataPatternName
            // 
            this.dataPatternName.Location = new System.Drawing.Point(126, 14);
            this.dataPatternName.Margin = new System.Windows.Forms.Padding(2);
            this.dataPatternName.Name = "dataPatternName";
            this.dataPatternName.Size = new System.Drawing.Size(101, 23);
            this.dataPatternName.TabIndex = 5;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(7, 14);
            this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(129, 17);
            this.label4.TabIndex = 6;
            this.label4.Text = "Data Pattern Name";
            // 
            // button7
            // 
            this.button7.Location = new System.Drawing.Point(10, 46);
            this.button7.Margin = new System.Windows.Forms.Padding(2);
            this.button7.Name = "button7";
            this.button7.Size = new System.Drawing.Size(65, 21);
            this.button7.TabIndex = 7;
            this.button7.Text = "Add";
            this.button7.UseVisualStyleBackColor = true;
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(85, 46);
            this.button6.Margin = new System.Windows.Forms.Padding(2);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(59, 21);
            this.button6.TabIndex = 8;
            this.button6.Text = "Delete";
            this.button6.UseVisualStyleBackColor = true;
            // 
            // dataPatternList
            // 
            this.dataPatternList.FormattingEnabled = true;
            this.dataPatternList.ItemHeight = 17;
            this.dataPatternList.Location = new System.Drawing.Point(10, 80);
            this.dataPatternList.Margin = new System.Windows.Forms.Padding(2);
            this.dataPatternList.Name = "dataPatternList";
            this.dataPatternList.Size = new System.Drawing.Size(232, 72);
            this.dataPatternList.TabIndex = 9;
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Controls.Add(this.tabPage3);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Margin = new System.Windows.Forms.Padding(2);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(383, 200);
            this.tabControl1.TabIndex = 0;
            // 
            // WorkflowPropertyEditor
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(383, 260);
            this.Controls.Add(this.splitContainer1);
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "WorkflowPropertyEditor";
            this.Text = "WorkflowPropertyEditor";
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.tabPage3.ResumeLayout(false);
            this.tabPage3.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            this.tabControl1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.Button cancelWorkflow;
        private System.Windows.Forms.Button doneWorkflow;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.ListBox dataPatternList;
        private System.Windows.Forms.Button button6;
        private System.Windows.Forms.Button button7;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox dataPatternName;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.Button button5;
        private System.Windows.Forms.TextBox statusName;
        private System.Windows.Forms.TextBox dataIdText;
        private System.Windows.Forms.ComboBox statusCombo;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TabPage tabPage3;
        private System.Windows.Forms.ListBox roleListBox;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox roleName;

    }
}