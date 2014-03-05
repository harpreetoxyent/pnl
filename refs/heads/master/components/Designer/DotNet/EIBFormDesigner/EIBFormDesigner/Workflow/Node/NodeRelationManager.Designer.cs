using EIBFormDesigner.Workflow.Controls;

namespace EIBFormDesigner.Workflow.Node
{
    partial class NodeRelationManager
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
            this.eibRelation1 = new EIBNodeRelation();
            this.SuspendLayout();
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
            this.eibRelation1.Location = new System.Drawing.Point(0, 0);
            this.eibRelation1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.eibRelation1.Name = "eibRelation1";
            this.eibRelation1.OnClickValue = null;
            this.eibRelation1.OnDoubleClick = null;
            this.eibRelation1.ParentXmlNode = null;
            this.eibRelation1.Size = new System.Drawing.Size(617, 282);
            this.eibRelation1.TabIndex = 0;
            this.eibRelation1.Load += new System.EventHandler(this.eibRelation1_Load);
            // 
            // NodeRelationManager
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(617, 282);
            this.Controls.Add(this.eibRelation1);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.MaximizeBox = false;
            this.Name = "NodeRelationManager";
            this.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "NodeRelationManager";
            this.Load += new System.EventHandler(this.NodeRelationManager_Load);
            this.ResumeLayout(false);

        }

        #endregion

        internal EIBNodeRelation eibRelation1;

    }
}