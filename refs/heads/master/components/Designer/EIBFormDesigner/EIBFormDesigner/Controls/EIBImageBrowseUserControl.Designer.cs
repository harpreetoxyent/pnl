namespace EIBFormDesigner.Controls
{
    partial class EIBImageBrowseUserControl
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(EIBImageBrowseUserControl));
            this.pagingControlSelLabel = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.uploadBtn = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // pagingControlSelLabel
            // 
            this.pagingControlSelLabel.AutoSize = true;
            this.pagingControlSelLabel.BackColor = System.Drawing.SystemColors.ControlText;
            this.pagingControlSelLabel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pagingControlSelLabel.Location = new System.Drawing.Point(1, -7);
            this.pagingControlSelLabel.Name = "pagingControlSelLabel";
            this.pagingControlSelLabel.Size = new System.Drawing.Size(12, 15);
            this.pagingControlSelLabel.TabIndex = 5;
            this.pagingControlSelLabel.Text = " ";
            this.pagingControlSelLabel.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pagingControlSelLabel_MouseDown);
            this.pagingControlSelLabel.MouseMove += new System.Windows.Forms.MouseEventHandler(this.pagingControlSelLabel_MouseMove);
            this.pagingControlSelLabel.MouseClick += new System.Windows.Forms.MouseEventHandler(this.pagingControlSelLabel_MouseClick);
            this.pagingControlSelLabel.MouseUp += new System.Windows.Forms.MouseEventHandler(this.pagingControlSelLabel_MouseUp);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(4, 12);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(130, 151);
            this.pictureBox1.TabIndex = 6;
            this.pictureBox1.TabStop = false;
            // 
            // uploadBtn
            // 
            this.uploadBtn.Location = new System.Drawing.Point(140, 138);
            this.uploadBtn.Name = "uploadBtn";
            this.uploadBtn.Size = new System.Drawing.Size(75, 23);
            this.uploadBtn.TabIndex = 7;
            this.uploadBtn.Text = "Upload";
            this.uploadBtn.UseVisualStyleBackColor = true;
            // 
            // EIBImageBrowseUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.uploadBtn);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.pagingControlSelLabel);
            this.Size = new System.Drawing.Size(218, 168);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label pagingControlSelLabel;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button uploadBtn;
    }
}
