using System;
using System.Collections.Generic;
using System.Text;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner
{
    public interface IContainerControl
    {
        ContainerAlignment Align
        {
            get;
            set;
        }
    }
}
