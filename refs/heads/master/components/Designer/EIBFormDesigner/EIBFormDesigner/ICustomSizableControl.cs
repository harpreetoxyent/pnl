using System;
using System.Collections.Generic;
using System.Text;
using EIBFormDesigner.Controls;
using System.Windows.Forms;

namespace EIBFormDesigner
{
    public interface ICustomSizableControl
    {
        WidthMeasurement WidthType
        {
            get;
            set;
        }
        int _Height
        {
            get;
            set;
        }
        int _Width
        {
            get;
            set;
        }
        bool ShouldWidhtChangeFireSizeChange
        {
            get;
            set;
        }
        void Control_Layout(object sender, LayoutEventArgs e);
        void Control_SizeChanged(object sender, System.EventArgs e);
        void calculateWidth();
    }
}
