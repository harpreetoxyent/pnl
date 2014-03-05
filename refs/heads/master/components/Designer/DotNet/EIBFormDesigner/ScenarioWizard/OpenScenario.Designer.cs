namespace EIBFormDesigner.ScenarioWizard
{
    partial class OpenScenario
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
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.locationLabel = new System.Windows.Forms.Label();
            this.projectLocation = new System.Windows.Forms.TextBox();
            this.btnOpenFolder = new System.Windows.Forms.Button();
            this.folderBrowserDialog = new System.Windows.Forms.FolderBrowserDialog();
            this.errorLabel = new System.Windows.Forms.Label();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.label1 = new System.Windows.Forms.Label();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.liferayChckBx = new System.Windows.Forms.CheckBox();
            this.SuspendLayout();
            // 
            // okButton
            // 
            this.okButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.okButton, "Opening the FormPattern");
            this.helpProvider1.SetHelpNavigator(this.okButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.okButton.Location = new System.Drawing.Point(6, 60);
            this.okButton.Margin = new System.Windows.Forms.Padding(2);
            this.okButton.Name = "okButton";
            this.helpProvider1.SetShowHelp(this.okButton, true);
            this.okButton.Size = new System.Drawing.Size(70, 24);
            this.okButton.TabIndex = 3;
            this.okButton.Text = "&OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.cancelButton, "Opening the FormPattern");
            this.helpProvider1.SetHelpNavigator(this.cancelButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelButton.Location = new System.Drawing.Point(80, 60);
            this.cancelButton.Margin = new System.Windows.Forms.Padding(2);
            this.cancelButton.Name = "cancelButton";
            this.helpProvider1.SetShowHelp(this.cancelButton, true);
            this.cancelButton.Size = new System.Drawing.Size(70, 24);
            this.cancelButton.TabIndex = 4;
            this.cancelButton.Text = "&Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            // 
            // locationLabel
            // 
            this.locationLabel.AutoSize = true;
            this.locationLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.locationLabel.Location = new System.Drawing.Point(-132, 47);
            this.locationLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.locationLabel.Name = "locationLabel";
            this.locationLabel.Size = new System.Drawing.Size(66, 17);
            this.locationLabel.TabIndex = 10;
            this.locationLabel.Text = "Location:";
            // 
            // projectLocation
            // 
            this.helpProvider1.SetHelpKeyword(this.projectLocation, "Opening the FormPattern");
            this.helpProvider1.SetHelpNavigator(this.projectLocation, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.projectLocation.Location = new System.Drawing.Point(6, 36);
            this.projectLocation.Margin = new System.Windows.Forms.Padding(2);
            this.projectLocation.Name = "projectLocation";
            this.helpProvider1.SetShowHelp(this.projectLocation, true);
            this.projectLocation.Size = new System.Drawing.Size(181, 20);
            this.projectLocation.TabIndex = 1;
            // 
            // btnOpenFolder
            // 
            this.helpProvider1.SetHelpKeyword(this.btnOpenFolder, "Opening the FormPattern");
            this.helpProvider1.SetHelpNavigator(this.btnOpenFolder, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.btnOpenFolder.Location = new System.Drawing.Point(191, 36);
            this.btnOpenFolder.Margin = new System.Windows.Forms.Padding(2);
            this.btnOpenFolder.Name = "btnOpenFolder";
            this.helpProvider1.SetShowHelp(this.btnOpenFolder, true);
            this.btnOpenFolder.Size = new System.Drawing.Size(70, 24);
            this.btnOpenFolder.TabIndex = 2;
            this.btnOpenFolder.Text = "&Browse";
            this.btnOpenFolder.Click += new System.EventHandler(this.btnOpenFolder_Click);
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.errorLabel.ForeColor = System.Drawing.Color.Red;
            this.errorLabel.Location = new System.Drawing.Point(6, 86);
            this.errorLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(0, 17);
            this.errorLabel.TabIndex = 13;
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label1, "Locating");
            this.helpProvider1.SetHelpNavigator(this.label1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label1.Location = new System.Drawing.Point(6, 7);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.helpProvider1.SetShowHelp(this.label1, true);
            this.label1.Size = new System.Drawing.Size(196, 17);
            this.label1.TabIndex = 14;
            this.label1.Text = "Please select the scenario file";
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // liferayChckBx
            // 
            this.liferayChckBx.AutoSize = true;
            this.liferayChckBx.Location = new System.Drawing.Point(9, 88);
            this.liferayChckBx.Name = "liferayChckBx";
            this.liferayChckBx.Size = new System.Drawing.Size(111, 17);
            this.liferayChckBx.TabIndex = 15;
            this.liferayChckBx.Text = "Use Liferay Image";
            this.liferayChckBx.UseVisualStyleBackColor = true;
            // 
            // OpenScenario
            // 
            this.AcceptButton = this.okButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.cancelButton;
            this.ClientSize = new System.Drawing.Size(270, 115);
            this.Controls.Add(this.liferayChckBx);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.errorLabel);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.locationLabel);
            this.Controls.Add(this.projectLocation);
            this.Controls.Add(this.btnOpenFolder);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.helpProvider1.SetHelpKeyword(this, "Opening the FormPattern");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.Name = "OpenScenario";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "OpenScenario";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.Label locationLabel;
        public System.Windows.Forms.TextBox projectLocation;
        internal System.Windows.Forms.Button btnOpenFolder;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog;
        private System.Windows.Forms.Label errorLabel;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.HelpProvider helpProvider1;
        public System.Windows.Forms.CheckBox liferayChckBx;
    }
}