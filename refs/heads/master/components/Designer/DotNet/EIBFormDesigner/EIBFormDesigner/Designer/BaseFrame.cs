using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.IO;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;

namespace EIBFormDesigner.Designer
{
    public class BaseFrame:Panel
    {
        public static ArrayList Lines = new ArrayList();

        public BaseFrame()
        {
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);		

        }
   }
}
