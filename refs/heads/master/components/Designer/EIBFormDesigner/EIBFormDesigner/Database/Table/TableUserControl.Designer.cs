namespace EIBFormDesigner.Database
{
	partial class TableUserControl
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
            this.panel4 = new System.Windows.Forms.Panel();
            this.lvDatabase = new System.Windows.Forms.ListView();
            this.colFieldName = new System.Windows.Forms.ColumnHeader();
            this.colFieldType = new System.Windows.Forms.ColumnHeader();
            this.colAutoIncrement = new System.Windows.Forms.ColumnHeader();
            this.colIsPrimary = new System.Windows.Forms.ColumnHeader();
            this.colSize = new System.Windows.Forms.ColumnHeader();
            this.colIsNotNull = new System.Windows.Forms.ColumnHeader();
            this.panel3 = new System.Windows.Forms.Panel();
            this.chkMToM = new System.Windows.Forms.CheckBox();
            this.chkNotNull = new System.Windows.Forms.CheckBox();
            this.numberDigits = new System.Windows.Forms.TextBox();
            this.chkPrimary = new System.Windows.Forms.CheckBox();
            this.doneButton = new System.Windows.Forms.Button();
            this.label8 = new System.Windows.Forms.Label();
            this.btnAddField = new System.Windows.Forms.Button();
            this.btnRemoveField = new System.Windows.Forms.Button();
            this.chkAutoIncrement = new System.Windows.Forms.CheckBox();
            this.label2 = new System.Windows.Forms.Label();
            this.cboFieldType = new System.Windows.Forms.ComboBox();
            this.txtFieldName = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.gbxDataBase = new System.Windows.Forms.GroupBox();
            this.tableName = new System.Windows.Forms.TextBox();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.button1 = new System.Windows.Forms.Button();
            this.panel4.SuspendLayout();
            this.panel3.SuspendLayout();
            this.gbxDataBase.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel4
            // 
            this.panel4.Controls.Add(this.lvDatabase);
            this.panel4.Controls.Add(this.panel3);
            this.panel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.panel4, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.panel4, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.panel4.Location = new System.Drawing.Point(3, 16);
            this.panel4.Name = "panel4";
            this.helpProvider1.SetShowHelp(this.panel4, true);
            this.panel4.Size = new System.Drawing.Size(263, 220);
            this.panel4.TabIndex = 2;
            // 
            // lvDatabase
            // 
            this.lvDatabase.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.colFieldName,
            this.colFieldType,
            this.colAutoIncrement,
            this.colIsPrimary,
            this.colSize,
            this.colIsNotNull});
            this.helpProvider1.SetHelpKeyword(this.lvDatabase, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.lvDatabase, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.lvDatabase.Location = new System.Drawing.Point(1, 8);
            this.lvDatabase.Name = "lvDatabase";
            this.helpProvider1.SetShowHelp(this.lvDatabase, true);
            this.lvDatabase.Size = new System.Drawing.Size(260, 86);
            this.lvDatabase.TabIndex = 3;
            this.lvDatabase.UseCompatibleStateImageBehavior = false;
            this.lvDatabase.View = System.Windows.Forms.View.Details;
            this.lvDatabase.SelectedIndexChanged += new System.EventHandler(this.lvDatabase_SelectedIndexChanged);
            // 
            // colFieldName
            // 
            this.colFieldName.Text = "Field Name";
            this.colFieldName.Width = 89;
            // 
            // colFieldType
            // 
            this.colFieldType.Text = "Field Type";
            this.colFieldType.Width = 82;
            // 
            // colAutoIncrement
            // 
            this.colAutoIncrement.Text = "Auto Increment";
            this.colAutoIncrement.Width = 103;
            // 
            // colIsPrimary
            // 
            this.colIsPrimary.Text = "Primary";
            // 
            // colSize
            // 
            this.colSize.Text = "Size";
            // 
            // colIsNotNull
            // 
            this.colIsNotNull.Text = "Not Null";
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.chkMToM);
            this.panel3.Controls.Add(this.chkNotNull);
            this.panel3.Controls.Add(this.numberDigits);
            this.panel3.Controls.Add(this.chkPrimary);
            this.panel3.Controls.Add(this.doneButton);
            this.panel3.Controls.Add(this.label8);
            this.panel3.Controls.Add(this.btnAddField);
            this.panel3.Controls.Add(this.btnRemoveField);
            this.panel3.Controls.Add(this.chkAutoIncrement);
            this.panel3.Controls.Add(this.label2);
            this.panel3.Controls.Add(this.cboFieldType);
            this.panel3.Controls.Add(this.txtFieldName);
            this.panel3.Controls.Add(this.label3);
            this.helpProvider1.SetHelpKeyword(this.panel3, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.panel3, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.panel3.Location = new System.Drawing.Point(3, 100);
            this.panel3.Name = "panel3";
            this.helpProvider1.SetShowHelp(this.panel3, true);
            this.panel3.Size = new System.Drawing.Size(260, 110);
            this.panel3.TabIndex = 1;
            // 
            // chkMToM
            // 
            this.chkMToM.AutoSize = true;
            this.chkMToM.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.helpProvider1.SetHelpKeyword(this.chkMToM, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.chkMToM, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.chkMToM.Location = new System.Drawing.Point(206, 55);
            this.chkMToM.Name = "chkMToM";
            this.helpProvider1.SetShowHelp(this.chkMToM, true);
            this.chkMToM.Size = new System.Drawing.Size(50, 17);
            this.chkMToM.TabIndex = 14;
            this.chkMToM.Text = "M2M";
            this.chkMToM.UseVisualStyleBackColor = true;
            this.chkMToM.CheckedChanged += new System.EventHandler(this.chkMToM_CheckedChanged);
            // 
            // chkNotNull
            // 
            this.chkNotNull.AutoSize = true;
            this.chkNotNull.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.helpProvider1.SetHelpKeyword(this.chkNotNull, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.chkNotNull, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.chkNotNull.Location = new System.Drawing.Point(194, 23);
            this.chkNotNull.Name = "chkNotNull";
            this.helpProvider1.SetShowHelp(this.chkNotNull, true);
            this.chkNotNull.Size = new System.Drawing.Size(64, 17);
            this.chkNotNull.TabIndex = 13;
            this.chkNotNull.Text = "Not Null";
            this.chkNotNull.UseVisualStyleBackColor = true;
            // 
            // numberDigits
            // 
            this.helpProvider1.SetHelpKeyword(this.numberDigits, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.numberDigits, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.numberDigits.Location = new System.Drawing.Point(166, 53);
            this.numberDigits.Margin = new System.Windows.Forms.Padding(2);
            this.numberDigits.Name = "numberDigits";
            this.helpProvider1.SetShowHelp(this.numberDigits, true);
            this.numberDigits.Size = new System.Drawing.Size(34, 20);
            this.numberDigits.TabIndex = 6;
            this.numberDigits.Visible = false;
            // 
            // chkPrimary
            // 
            this.chkPrimary.AutoSize = true;
            this.chkPrimary.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.helpProvider1.SetHelpKeyword(this.chkPrimary, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.chkPrimary, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.chkPrimary.Location = new System.Drawing.Point(93, 4);
            this.chkPrimary.Name = "chkPrimary";
            this.helpProvider1.SetShowHelp(this.chkPrimary, true);
            this.chkPrimary.Size = new System.Drawing.Size(60, 17);
            this.chkPrimary.TabIndex = 2;
            this.chkPrimary.Text = "Primary";
            this.chkPrimary.UseVisualStyleBackColor = true;
            this.chkPrimary.CheckedChanged += new System.EventHandler(this.chkPrimary_CheckedChanged);
            // 
            // doneButton
            // 
            this.helpProvider1.SetHelpKeyword(this.doneButton, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.doneButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.doneButton.Location = new System.Drawing.Point(153, 84);
            this.doneButton.Name = "doneButton";
            this.helpProvider1.SetShowHelp(this.doneButton, true);
            this.doneButton.Size = new System.Drawing.Size(70, 24);
            this.doneButton.TabIndex = 9;
            this.doneButton.Text = "&Done";
            this.doneButton.UseVisualStyleBackColor = true;
            this.doneButton.Click += new System.EventHandler(this.doneButon_Click);
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(76, 36);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(56, 13);
            this.label8.TabIndex = 12;
            this.label8.Text = "Field Type";
            // 
            // btnAddField
            // 
            this.helpProvider1.SetHelpKeyword(this.btnAddField, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.btnAddField, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.btnAddField.Location = new System.Drawing.Point(1, 84);
            this.btnAddField.Name = "btnAddField";
            this.helpProvider1.SetShowHelp(this.btnAddField, true);
            this.btnAddField.Size = new System.Drawing.Size(70, 24);
            this.btnAddField.TabIndex = 7;
            this.btnAddField.Text = "&Add Field";
            this.btnAddField.UseVisualStyleBackColor = true;
            this.btnAddField.Click += new System.EventHandler(this.btnAddField_Click);
            // 
            // btnRemoveField
            // 
            this.helpProvider1.SetHelpKeyword(this.btnRemoveField, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.btnRemoveField, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.btnRemoveField.Location = new System.Drawing.Point(77, 84);
            this.btnRemoveField.Name = "btnRemoveField";
            this.helpProvider1.SetShowHelp(this.btnRemoveField, true);
            this.btnRemoveField.Size = new System.Drawing.Size(70, 24);
            this.btnRemoveField.TabIndex = 8;
            this.btnRemoveField.Text = "&Remove Field";
            this.btnRemoveField.UseVisualStyleBackColor = true;
            this.btnRemoveField.Click += new System.EventHandler(this.btnRemoveField_Click);
            // 
            // chkAutoIncrement
            // 
            this.chkAutoIncrement.AutoSize = true;
            this.chkAutoIncrement.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.helpProvider1.SetHelpKeyword(this.chkAutoIncrement, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.chkAutoIncrement, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.chkAutoIncrement.Location = new System.Drawing.Point(160, 4);
            this.chkAutoIncrement.Name = "chkAutoIncrement";
            this.helpProvider1.SetShowHelp(this.chkAutoIncrement, true);
            this.chkAutoIncrement.Size = new System.Drawing.Size(98, 17);
            this.chkAutoIncrement.TabIndex = 3;
            this.chkAutoIncrement.Text = "Auto Increment";
            this.chkAutoIncrement.UseVisualStyleBackColor = true;
            this.chkAutoIncrement.CheckedChanged += new System.EventHandler(this.chkAutoIncrement_CheckedChanged);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label2, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.label2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label2.Location = new System.Drawing.Point(0, 4);
            this.label2.Name = "label2";
            this.helpProvider1.SetShowHelp(this.label2, true);
            this.label2.Size = new System.Drawing.Size(66, 13);
            this.label2.TabIndex = 7;
            this.label2.Text = "Current Field";
            // 
            // cboFieldType
            // 
            this.cboFieldType.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.cboFieldType, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.cboFieldType, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cboFieldType.Location = new System.Drawing.Point(76, 53);
            this.cboFieldType.Name = "cboFieldType";
            this.helpProvider1.SetShowHelp(this.cboFieldType, true);
            this.cboFieldType.Size = new System.Drawing.Size(83, 21);
            this.cboFieldType.TabIndex = 5;
            this.cboFieldType.SelectedIndexChanged += new System.EventHandler(this.cboFieldType_SelectedIndexChanged);
            // 
            // txtFieldName
            // 
            this.helpProvider1.SetHelpKeyword(this.txtFieldName, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.txtFieldName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.txtFieldName.Location = new System.Drawing.Point(1, 53);
            this.txtFieldName.Name = "txtFieldName";
            this.helpProvider1.SetShowHelp(this.txtFieldName, true);
            this.txtFieldName.Size = new System.Drawing.Size(71, 20);
            this.txtFieldName.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(-2, 36);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(60, 13);
            this.label3.TabIndex = 9;
            this.label3.Text = "Field Name";
            // 
            // gbxDataBase
            // 
            this.gbxDataBase.AutoSize = true;
            this.gbxDataBase.Controls.Add(this.tableName);
            this.gbxDataBase.Controls.Add(this.panel4);
            this.gbxDataBase.Location = new System.Drawing.Point(3, 12);
            this.gbxDataBase.Name = "gbxDataBase";
            this.gbxDataBase.Size = new System.Drawing.Size(269, 239);
            this.gbxDataBase.TabIndex = 14;
            this.gbxDataBase.TabStop = false;
            this.gbxDataBase.Text = "Table";
            // 
            // tableName
            // 
            this.tableName.Enabled = false;
            this.helpProvider1.SetHelpKeyword(this.tableName, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this.tableName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.tableName.Location = new System.Drawing.Point(40, 0);
            this.tableName.Margin = new System.Windows.Forms.Padding(2);
            this.tableName.Name = "tableName";
            this.helpProvider1.SetShowHelp(this.tableName, true);
            this.tableName.Size = new System.Drawing.Size(76, 20);
            this.tableName.TabIndex = 1;
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(124, -1);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(51, 23);
            this.button1.TabIndex = 15;
            this.button1.Text = "Edit";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // TableUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.Controls.Add(this.button1);
            this.Controls.Add(this.gbxDataBase);
            this.helpProvider1.SetHelpKeyword(this, "Creating the Table");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Name = "TableUserControl";
            this.helpProvider1.SetShowHelp(this, true);
            this.Size = new System.Drawing.Size(284, 258);
            this.Load += new System.EventHandler(this.FormCreateXMLDB_Load);
            this.panel4.ResumeLayout(false);
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            this.gbxDataBase.ResumeLayout(false);
            this.gbxDataBase.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

		}



		#endregion

        private System.Windows.Forms.Panel panel4;
        internal System.Windows.Forms.ListView lvDatabase;
        private System.Windows.Forms.ColumnHeader colFieldName;
        private System.Windows.Forms.ColumnHeader colFieldType;
        private System.Windows.Forms.ColumnHeader colAutoIncrement;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Button doneButton;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button btnAddField;
        private System.Windows.Forms.Button btnRemoveField;
        private System.Windows.Forms.CheckBox chkAutoIncrement;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cboFieldType;
        private System.Windows.Forms.TextBox txtFieldName;
        private System.Windows.Forms.Label label3;
        internal System.Windows.Forms.GroupBox gbxDataBase;
        internal System.Windows.Forms.TextBox tableName;
        private System.Windows.Forms.ColumnHeader colIsPrimary;
        private System.Windows.Forms.CheckBox chkPrimary;
        private System.Windows.Forms.TextBox numberDigits;
        private System.Windows.Forms.ColumnHeader colSize;
        private System.Windows.Forms.HelpProvider helpProvider1;
        private System.Windows.Forms.CheckBox chkNotNull;
        private System.Windows.Forms.ColumnHeader colIsNotNull;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.CheckBox chkMToM;

    }
}

