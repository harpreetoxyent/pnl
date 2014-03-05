using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls
{
    public partial class EIBImageBrowseUserControl : UserControl
    {
        public EIBImageBrowseUserControl()
        {
            InitializeComponent();
        }

        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the image for ImageBrowse Control."), Category("Appearance")]
        public Image Image
        {
            get
            {
                return this.pictureBox1.Image;
            }
            set
            {
                this.pictureBox1.Image = value;
            }
        }
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the Value for ImageBrowse Control."), Category("Appearance")]
        public override string Text
        {
            get
            {
                return this.uploadBtn.Text;
            }
            set
            {
                this.uploadBtn.Text = value;
            }
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
