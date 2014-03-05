namespace EIBFormDesigner.Controls
{
    partial class TableRowColumnSelector
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.rowLabel = new System.Windows.Forms.Label();
            this.rowCombo = new System.Windows.Forms.ComboBox();
            this.columnCombo = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // rowLabel
            // 
            this.rowLabel.AutoSize = true;
            this.rowLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rowLabel.Location = new System.Drawing.Point(4, 11);
            this.rowLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.rowLabel.Name = "rowLabel";
            this.rowLabel.Size = new System.Drawing.Size(35, 17);
            this.rowLabel.TabIndex = 0;
            this.rowLabel.Text = "Row";
            // 
            // rowCombo
            // 
            this.rowCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.rowCombo, "RowColumn");
            this.helpProvider1.SetHelpNavigator(this.rowCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.rowCombo.Location = new System.Drawing.Point(40, 11);
            this.rowCombo.Margin = new System.Windows.Forms.Padding(2);
            this.rowCombo.Name = "rowCombo";
            this.helpProvider1.SetShowHelp(this.rowCombo, true);
            this.rowCombo.Size = new System.Drawing.Size(54, 21);
            this.rowCombo.TabIndex = 1;
            // 
            // columnCombo
            // 
            this.columnCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.columnCombo, "RowColumn");
            this.helpProvider1.SetHelpNavigator(this.columnCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.columnCombo.Location = new System.Drawing.Point(167, 11);
            this.columnCombo.Margin = new System.Windows.Forms.Padding(2);
            this.columnCombo.Name = "columnCombo";
            this.helpProvider1.SetShowHelp(this.columnCombo, true);
            this.columnCombo.Size = new System.Drawing.Size(54, 21);
            this.columnCombo.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(113, 11);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(55, 17);
            this.label1.TabIndex = 2;
            this.label1.Text = "Column";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // okButton
            // 
            this.okButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.okButton, "RowColumn");
            this.helpProvider1.SetHelpNavigator(this.okButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.okButton.Location = new System.Drawing.Point(77, 40);
            this.okButton.Margin = new System.Windows.Forms.Padding(2);
            this.okButton.Name = "okButton";
            this.helpProvider1.SetShowHelp(this.okButton, true);
            this.okButton.Size = new System.Drawing.Size(70, 24);
            this.okButton.TabIndex = 3;
            this.okButton.Text = "&OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // button1
            // 
            this.button1.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.button1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.button1, "RowColumn");
            this.helpProvider1.SetHelpNavigator(this.button1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.button1.Location = new System.Drawing.Point(151, 40);
            this.button1.Margin = new System.Windows.Forms.Padding(2);
            this.button1.Name = "button1";
            this.helpProvider1.SetShowHelp(this.button1, true);
            this.button1.Size = new System.Drawing.Size(70, 24);
            this.button1.TabIndex = 4;
            this.button1.Text = "&Cancel";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // TableRowColumnSelector
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.button1;
            this.ClientSize = new System.Drawing.Size(240, 75);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.columnCombo);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.rowCombo);
            this.Controls.Add(this.rowLabel);
            this.helpProvider1.SetHelpKeyword(this, "RowColumn");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.Name = "TableRowColumnSelector";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "Select Row & Column";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label rowLabel;
        internal System.Windows.Forms.ComboBox rowCombo;
        internal System.Windows.Forms.ComboBox columnCombo;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.HelpProvider helpProvider1;
    }
}
