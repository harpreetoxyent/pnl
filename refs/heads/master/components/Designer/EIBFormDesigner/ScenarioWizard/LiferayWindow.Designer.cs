namespace EIBFormDesigner.ScenarioWizard
{
    partial class LiferayWindow
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
                if (!isCorrupt)
                {
                    components.Dispose();
                }
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
            this.LabelImage = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.imageLocation = new System.Windows.Forms.TextBox();
            this.btnOpenImage = new System.Windows.Forms.Button();
            this.languageLabel = new System.Windows.Forms.Label();
            this.languageCbo = new System.Windows.Forms.ComboBox();
            this.SuspendLayout();
            // 
            // LabelImage
            // 
            this.LabelImage.AutoSize = true;
            this.LabelImage.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.LabelImage.Location = new System.Drawing.Point(12, 11);
            this.LabelImage.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.LabelImage.Name = "LabelImage";
            this.LabelImage.Size = new System.Drawing.Size(200, 17);
            this.LabelImage.TabIndex = 19;
            this.LabelImage.Text = "Please select the liferay image";
            // 
            // okButton
            // 
            this.okButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.okButton.Location = new System.Drawing.Point(16, 76);
            this.okButton.Margin = new System.Windows.Forms.Padding(2);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(70, 24);
            this.okButton.TabIndex = 17;
            this.okButton.Text = "&OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cancelButton.Location = new System.Drawing.Point(93, 76);
            this.cancelButton.Margin = new System.Windows.Forms.Padding(2);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(70, 24);
            this.cancelButton.TabIndex = 18;
            this.cancelButton.Text = "&Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // imageLocation
            // 
            this.imageLocation.Location = new System.Drawing.Point(216, 11);
            this.imageLocation.Margin = new System.Windows.Forms.Padding(2);
            this.imageLocation.Name = "imageLocation";
            this.imageLocation.ReadOnly = true;
            this.imageLocation.Size = new System.Drawing.Size(181, 20);
            this.imageLocation.TabIndex = 15;
            // 
            // btnOpenImage
            // 
            this.btnOpenImage.Location = new System.Drawing.Point(410, 8);
            this.btnOpenImage.Margin = new System.Windows.Forms.Padding(2);
            this.btnOpenImage.Name = "btnOpenImage";
            this.btnOpenImage.Size = new System.Drawing.Size(67, 24);
            this.btnOpenImage.TabIndex = 16;
            this.btnOpenImage.Text = "&Browse";
            this.btnOpenImage.Click += new System.EventHandler(this.btnOpenImage_Click);
            // 
            // languageLabel
            // 
            this.languageLabel.AutoSize = true;
            this.languageLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.languageLabel.Location = new System.Drawing.Point(13, 41);
            this.languageLabel.Name = "languageLabel";
            this.languageLabel.Size = new System.Drawing.Size(72, 17);
            this.languageLabel.TabIndex = 20;
            this.languageLabel.Text = "Language";
            // 
            // languageCbo
            // 
            this.languageCbo.FormattingEnabled = true;
            this.languageCbo.Items.AddRange(new object[] {
            "English(U.S.)",
            "Hindi"});
            this.languageCbo.Location = new System.Drawing.Point(216, 41);
            this.languageCbo.Name = "languageCbo";
            this.languageCbo.Size = new System.Drawing.Size(181, 21);
            this.languageCbo.TabIndex = 21;
            // 
            // LiferayWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(479, 117);
            this.Controls.Add(this.languageCbo);
            this.Controls.Add(this.languageLabel);
            this.Controls.Add(this.LabelImage);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.imageLocation);
            this.Controls.Add(this.btnOpenImage);
            this.Name = "LiferayWindow";
            this.Text = "LiferayWindow";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public System.Windows.Forms.Label LabelImage;
        public System.Windows.Forms.Button okButton;
        public System.Windows.Forms.Button cancelButton;
        public System.Windows.Forms.TextBox imageLocation;
        internal System.Windows.Forms.Button btnOpenImage;
        private System.Windows.Forms.Label languageLabel;
        private System.Windows.Forms.ComboBox languageCbo;

    }
}