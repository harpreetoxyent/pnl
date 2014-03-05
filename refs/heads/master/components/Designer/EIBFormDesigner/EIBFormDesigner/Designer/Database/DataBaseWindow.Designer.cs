using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Designer
{
    partial class DataBaseWindow
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
            this.baseFrame = new EIBFormDesigner.Controls.EIBPanel();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.SuspendLayout();
            // 
            // baseFrame
            // 
            this.baseFrame.ControlName = null;
            this.baseFrame.DefaultScreen = false;
            this.baseFrame.DefaultValue = null;
            this.baseFrame.Dock = System.Windows.Forms.DockStyle.Fill;
            this.baseFrame.EnteringValue = null;
            this.baseFrame.ExitingValue = null;
            this.helpProvider1.SetHelpKeyword(this.baseFrame, "DataBaseWindow");
            this.helpProvider1.SetHelpNavigator(this.baseFrame, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.baseFrame.Location = new System.Drawing.Point(0, 0);
            this.baseFrame.Margin = new System.Windows.Forms.Padding(2);
            this.baseFrame.Name = "baseFrame";
            this.baseFrame.OnClickValue = null;
            this.baseFrame.OnDoubleClick = null;
            this.baseFrame.ParentXmlNode = null;
            this.helpProvider1.SetShowHelp(this.baseFrame, true);
            this.baseFrame.Size = new System.Drawing.Size(334, 221);
            this.baseFrame.TabIndex = 0;
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // DataBaseWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(334, 221);
            this.Controls.Add(this.baseFrame);
            this.helpProvider1.SetHelpKeyword(this, "DataBaseWindow");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Name = "DataBaseWindow";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.Document;
            this.TabText = "Database Designer";
            this.Text = "Database Designer";
            this.ResumeLayout(false);

        }

        #endregion

        public EIBPanel baseFrame;
        private System.Windows.Forms.HelpProvider helpProvider1;

    }
}