using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Designer
{
    partial class BaseWindow
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
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.baseFrame = new EIBFormDesigner.Controls.EIBPanel();
            this.SuspendLayout();
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // baseFrame
            // 
            this.baseFrame.AccessibleName = "panel0";
            this.baseFrame.AllowDrop = true;
            this.baseFrame.AutoScroll = true;
            this.baseFrame.AutoSize = true;
            this.baseFrame.BackColor = System.Drawing.SystemColors.Control;
            this.baseFrame.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.baseFrame.ControlName = null;
            this.baseFrame.DataMappings = null;
            this.baseFrame.DefaultScreen = false;
            this.baseFrame.DefaultValue = null;
            this.baseFrame.EnteringValue = null;
            this.baseFrame.ExitingValue = null;
            this.baseFrame.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F);
            this.helpProvider1.SetHelpKeyword(this.baseFrame, "Form Pattern");
            this.helpProvider1.SetHelpNavigator(this.baseFrame, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.baseFrame.Location = new System.Drawing.Point(8, 8);
            this.baseFrame.Margin = new System.Windows.Forms.Padding(0);
            this.baseFrame.Name = "baseFrame";
            this.baseFrame.OnClickValue = null;
            this.baseFrame.OnDoubleClick = null;
            this.baseFrame.Padding = new System.Windows.Forms.Padding(8);
            this.baseFrame.ParentXmlNode = null;
            this.helpProvider1.SetShowHelp(this.baseFrame, true);
            this.baseFrame.Size = new System.Drawing.Size(784, 730);
            this.baseFrame.TabIndex = 0;
            // 
            // BaseWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Gray;
            this.ClientSize = new System.Drawing.Size(800, 746);
            this.Controls.Add(this.baseFrame);
            this.helpProvider1.SetHelpKeyword(this, "Form Pattern");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "BaseWindow";
            this.Padding = new System.Windows.Forms.Padding(8);
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.Document;
            this.TabText = "BaseWindow";
            this.Text = "BaseWindow";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.HelpProvider helpProvider1;
        public EIBPanel baseFrame;

    }
}