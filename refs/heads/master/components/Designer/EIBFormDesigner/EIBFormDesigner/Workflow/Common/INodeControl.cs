using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.XML;
using System.Xml;
using System.ComponentModel;

namespace EIBFormDesigner.Workflow.Common
{
    public interface INodeControl
    {
        string NodeValue
        {
            get;
            set;
        }
        string OnNodeEnterValue
        {
            get;
            set;
        }
        string OnNodeExitClick
        {
            get;
            set;
        }
    }
}
