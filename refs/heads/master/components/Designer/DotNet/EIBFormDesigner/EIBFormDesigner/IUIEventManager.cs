using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner
{
    public interface IUIEventManager
    {
        void handleDrag(object sender, EventArgs e, object form, Point point);
        void handleControlClick(object sender, MouseEventArgs e);
        void addPropertiesControl(Control ctrl);
        void handleMenuClick(object sender, EventArgs e);
        void handleMenuItemClick(object sender, EventArgs e);
        void TableControl_Layout(object sender, LayoutEventArgs e);
        void LatticeControl_Layout(object sender, LayoutEventArgs e);
        void HandleCtrlDown(object sender, KeyEventArgs e);
        void HandleCtrlUp(object sender, KeyEventArgs e);
        void HandleKeyPress(object sender, KeyPressEventArgs e);
        void AlignAll(List<IEIBControl> control,Align format);
    }
}
