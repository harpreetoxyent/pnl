using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Data;
using System.Drawing;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Workflow.Controls;

namespace EIBFormDesigner.Workflow.Controls
{
    public class EIBNode : NodeUserControl
    {
        public bool isMouseUp = false;
        public EIBNode()
        {
            EIBNodeConnector nodeConnector = new EIBNodeConnector();
            nodeConnector.AssociateMarkUpandDown(this);
            isMouseUp = true;
            nodeConnector.baseFrame = UIEventManager.form.currentBaseWindow.baseFrame;
        }
        public Point Center
        {
            get { return new Point(Location.X + (this.Width / 2), Location.Y + (this.Height / 2)); }
        }
    }
}

