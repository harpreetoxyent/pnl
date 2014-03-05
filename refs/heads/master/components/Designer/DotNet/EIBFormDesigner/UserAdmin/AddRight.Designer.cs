namespace EIBFormDesigner.UserAdmin
{
    partial class AddRight
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
            this.rightsDesc = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.rightNameText = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.saveUserButton = new System.Windows.Forms.Button();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.rightsDesc);
            this.splitContainer1.Panel1.Controls.Add(this.label2);
            this.splitContainer1.Panel1.Controls.Add(this.rightNameText);
            this.splitContainer1.Panel1.Controls.Add(this.label1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.cancelButton);
            this.splitContainer1.Panel2.Controls.Add(this.saveUserButton);
            this.splitContainer1.Size = new System.Drawing.Size(387, 261);
            this.splitContainer1.SplitterDistance = 210;
            this.splitContainer1.TabIndex = 0;
            // 
            // rightsDesc
            // 
            this.helpProvider1.SetHelpKeyword(this.rightsDesc, "Rights");
            this.helpProvider1.SetHelpNavigator(this.rightsDesc, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.rightsDesc.Location = new System.Drawing.Point(158, 68);
            this.rightsDesc.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.rightsDesc.Multiline = true;
            this.rightsDesc.Name = "rightsDesc";
            this.rightsDesc.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.helpProvider1.SetShowHelp(this.rightsDesc, true);
            this.rightsDesc.Size = new System.Drawing.Size(191, 102);
            this.rightsDesc.TabIndex = 21;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label2, "Rights");
            this.helpProvider1.SetHelpNavigator(this.label2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label2.Location = new System.Drawing.Point(32, 68);
            this.label2.Name = "label2";
            this.helpProvider1.SetShowHelp(this.label2, true);
            this.label2.Size = new System.Drawing.Size(83, 17);
            this.label2.TabIndex = 20;
            this.label2.Text = "Description:";
            // 
            // rightNameText
            // 
            this.helpProvider1.SetHelpKeyword(this.rightNameText, "Rights");
            this.helpProvider1.SetHelpNavigator(this.rightNameText, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.rightNameText.Location = new System.Drawing.Point(158, 35);
            this.rightNameText.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.rightNameText.Name = "rightNameText";
            this.helpProvider1.SetShowHelp(this.rightNameText, true);
            this.rightNameText.Size = new System.Drawing.Size(191, 22);
            this.rightNameText.TabIndex = 19;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label1, "Rights");
            this.helpProvider1.SetHelpNavigator(this.label1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label1.Location = new System.Drawing.Point(32, 35);
            this.label1.Name = "label1";
            this.helpProvider1.SetShowHelp(this.label1, true);
            this.label1.Size = new System.Drawing.Size(86, 17);
            this.label1.TabIndex = 18;
            this.label1.Text = "Right Name:";
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.helpProvider1.SetHelpKeyword(this.cancelButton, "User");
            this.helpProvider1.SetHelpNavigator(this.cancelButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelButton.Location = new System.Drawing.Point(309, 8);
            this.cancelButton.Name = "cancelButton";
            this.helpProvider1.SetShowHelp(this.cancelButton, true);
            this.cancelButton.Size = new System.Drawing.Size(70, 32);
            this.cancelButton.TabIndex = 25;
            this.cancelButton.Text = "&Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // saveUserButton
            // 
            this.helpProvider1.SetHelpKeyword(this.saveUserButton, "User");
            this.helpProvider1.SetHelpNavigator(this.saveUserButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.saveUserButton.Location = new System.Drawing.Point(222, 8);
            this.saveUserButton.Name = "saveUserButton";
            this.helpProvider1.SetShowHelp(this.saveUserButton, true);
            this.saveUserButton.Size = new System.Drawing.Size(81, 32);
            this.saveUserButton.TabIndex = 24;
            this.saveUserButton.Text = "&Save";
            this.saveUserButton.UseVisualStyleBackColor = true;
            this.saveUserButton.Click += new System.EventHandler(this.saveUserButton_Click);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // AddRight
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 14F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(387, 261);
            this.Controls.Add(this.splitContainer1);
            this.Font = new System.Drawing.Font("Verdana", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.MaximizeBox = false;
            this.Name = "AddRight";
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "AddRight";
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.TextBox rightsDesc;
        private System.Windows.Forms.HelpProvider helpProvider1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox rightNameText;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Button saveUserButton;

    }
}