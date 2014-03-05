namespace EIBFormDesigner.UserAdmin
{
    partial class AddGroup
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
            this.label1 = new System.Windows.Forms.Label();
            this.groupNameText = new System.Windows.Forms.TextBox();
            this.saveGroup = new System.Windows.Forms.Button();
            this.cancelGroup = new System.Windows.Forms.Button();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(14, 14);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(93, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Group Name:";
            // 
            // groupNameText
            // 
            this.helpProvider1.SetHelpKeyword(this.groupNameText, "Groups");
            this.helpProvider1.SetHelpNavigator(this.groupNameText, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.groupNameText.Location = new System.Drawing.Point(109, 14);
            this.groupNameText.Margin = new System.Windows.Forms.Padding(2);
            this.groupNameText.Name = "groupNameText";
            this.helpProvider1.SetShowHelp(this.groupNameText, true);
            this.groupNameText.Size = new System.Drawing.Size(144, 20);
            this.groupNameText.TabIndex = 1;
            // 
            // saveGroup
            // 
            this.helpProvider1.SetHelpKeyword(this.saveGroup, "Groups");
            this.helpProvider1.SetHelpNavigator(this.saveGroup, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.saveGroup.Location = new System.Drawing.Point(109, 38);
            this.saveGroup.Margin = new System.Windows.Forms.Padding(2);
            this.saveGroup.Name = "saveGroup";
            this.helpProvider1.SetShowHelp(this.saveGroup, true);
            this.saveGroup.Size = new System.Drawing.Size(70, 24);
            this.saveGroup.TabIndex = 2;
            this.saveGroup.Text = "&Save";
            this.saveGroup.UseVisualStyleBackColor = true;
            this.saveGroup.Click += new System.EventHandler(this.saveRule_Click);
            // 
            // cancelGroup
            // 
            this.cancelGroup.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.helpProvider1.SetHelpKeyword(this.cancelGroup, "Groups");
            this.helpProvider1.SetHelpNavigator(this.cancelGroup, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelGroup.Location = new System.Drawing.Point(183, 38);
            this.cancelGroup.Margin = new System.Windows.Forms.Padding(2);
            this.cancelGroup.Name = "cancelGroup";
            this.helpProvider1.SetShowHelp(this.cancelGroup, true);
            this.cancelGroup.Size = new System.Drawing.Size(70, 24);
            this.cancelGroup.TabIndex = 3;
            this.cancelGroup.Text = "&Cancel";
            this.cancelGroup.UseVisualStyleBackColor = true;
            this.cancelGroup.Click += new System.EventHandler(this.cancelRule_Click);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // AddGroup
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.cancelGroup;
            this.ClientSize = new System.Drawing.Size(265, 72);
            this.Controls.Add(this.cancelGroup);
            this.Controls.Add(this.saveGroup);
            this.Controls.Add(this.groupNameText);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.helpProvider1.SetHelpKeyword(this, "Groups");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.Name = "AddGroup";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "AddGroup";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox groupNameText;
        private System.Windows.Forms.Button saveGroup;
        private System.Windows.Forms.Button cancelGroup;
        private System.Windows.Forms.HelpProvider helpProvider1;
    }
}