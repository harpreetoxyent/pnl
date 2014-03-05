using System;
using System.Collections.Generic;
using System.Text;

namespace EIBFormDesigner.Tools
{
    public interface IToolBox
    {
        ITool getControlType(string controlId);
    }
}
