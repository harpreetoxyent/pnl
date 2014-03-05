using System;
using System.Collections.Generic;
using System.Text;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner
{
    public interface ICustomVisible
    {
        bool getVisibility();
        bool Visible
        {
            get;
            set;
        }
    }
}
