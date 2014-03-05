namespace EIBFormDesigner.Controls
{
    partial class VisibleToEditorForm
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.VisibleToList = new System.Windows.Forms.CheckedListBox();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.VisibleToList);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(172, 274);
            this.panel1.TabIndex = 4;
            // 
            // VisibleToList
            // 
            this.VisibleToList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.VisibleToList.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.VisibleToList.FormattingEnabled = true;
            this.VisibleToList.HorizontalScrollbar = true;
            this.VisibleToList.Items.AddRange(new object[] {
            "All"});
            this.VisibleToList.Location = new System.Drawing.Point(0, 0);
            this.VisibleToList.Name = "VisibleToList";
            this.VisibleToList.Size = new System.Drawing.Size(172, 274);
            this.VisibleToList.TabIndex = 0;
            this.VisibleToList.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.VisibleToList_ItemCheck);
            // 
            // VisibleToEditorForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(172, 274);
            this.Controls.Add(this.panel1);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "VisibleToEditorForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "VisibleTo Editor";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.DataMappingEditorForm_FormClosing);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.CheckedListBox VisibleToList;
    }
}