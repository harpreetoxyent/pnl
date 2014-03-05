using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Data;
using System.Drawing;
using EIBFormDesigner.Database;
using EIBFormDesigner.Database.Table;
using System.Drawing.Design;

namespace EIBFormDesigner.Controls
{
    public class EIBTable : TableUserControl
    {
        public bool isMouseUp = false;
        public EIBTable()
        {
            EIBTableConnector tableConnector = new EIBTableConnector();
            tableConnector.AssociateMarkUpandDown(this);
            tableConnector.InitiateSettings(EIBFormDesigner.UIEventManager.form.currentBaseWindow.baseFrame);
            isMouseUp = true;
        }
        public Point Center
        {
            get { return new Point(Location.X + (this.Width/2), Location.Y + (this.Height/2)); }
        }

        private void InitializeComponent()
        {
            this.SuspendLayout();
            // 
            // EIBTable
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.Name = "EIBTable";
            this.Size = new System.Drawing.Size(286, 268);
            this.ResumeLayout(false);
            this.PerformLayout();
            //this.MouseUp+=new MouseEventHandler(this.table_MouseUp);
            //isMouseUp = true;

        }
        public void table_MouseUp(object sender, MouseEventArgs e)
        {
            
        }
    }   
}
