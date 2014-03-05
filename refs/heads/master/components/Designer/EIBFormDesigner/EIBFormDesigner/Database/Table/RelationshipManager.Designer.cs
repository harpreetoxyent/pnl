namespace EIBFormDesigner.Database.Table
{
    partial class RelationshipManager
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
            this.eibRelation1 = new EIBFormDesigner.Controls.EIBRelation();
            this.SuspendLayout();
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // eibRelation1
            // 
            this.eibRelation1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.eibRelation1.ControlName = null;
            this.eibRelation1.DataFieldName = null;
            this.eibRelation1.DataPatternName = null;
            this.eibRelation1.DataTableName = null;
            this.eibRelation1.DefaultValue = null;
            this.eibRelation1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.eibRelation1.EnteringValue = null;
            this.eibRelation1.ExitingValue = null;
            this.helpProvider1.SetHelpKeyword(this.eibRelation1, "RelationShipManager");
            this.helpProvider1.SetHelpNavigator(this.eibRelation1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.eibRelation1.Location = new System.Drawing.Point(0, 0);
            this.eibRelation1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.eibRelation1.Name = "eibRelation1";
            this.eibRelation1.OnClickValue = null;
            this.eibRelation1.OnDoubleClick = null;
            this.eibRelation1.ParentXmlNode = null;
            this.helpProvider1.SetShowHelp(this.eibRelation1, true);
            this.eibRelation1.Size = new System.Drawing.Size(463, 262);
            this.eibRelation1.TabIndex = 0;
            this.eibRelation1.Load += new System.EventHandler(this.eibRelation1_Load);
            // 
            // RelationshipManager
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(463, 262);
            this.Controls.Add(this.eibRelation1);
            this.helpProvider1.SetHelpKeyword(this, "RelationShipManager");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.Name = "RelationshipManager";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "RelationshipManager";
            this.ResumeLayout(false);

        }

        #endregion

        private EIBFormDesigner.Controls.EIBRelation eibRelation1;
        private System.Windows.Forms.HelpProvider helpProvider1;

    }
}