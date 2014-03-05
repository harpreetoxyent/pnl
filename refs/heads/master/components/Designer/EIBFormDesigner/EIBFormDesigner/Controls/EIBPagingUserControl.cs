using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls
{
    public partial class EIBPagingUserControl : UserControl
    {
        public EIBPagingUserControl()
        {
            InitializeComponent();
        }

        private void pagingControlSelLabel_MouseClick(object sender, MouseEventArgs e)
        {
            this.OnMouseClick(e);
            
        }

        private void pagingControlSelLabel_MouseDown(object sender, MouseEventArgs e)
        {
            this.OnMouseDown(e);
        }

        private void pagingControlSelLabel_MouseMove(object sender, MouseEventArgs e)
        {
            this.OnMouseMove(e);
        }

        private void pagingControlSelLabel_MouseUp(object sender, MouseEventArgs e)
        {
            this.OnMouseUp(e);
        }
    }
}
