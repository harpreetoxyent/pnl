using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Designer;
using System.ComponentModel;
using EIBFormDesigner.Workflow.Common;

namespace EIBFormDesigner
{
    public interface IPropertiesManager
    {
        void displayProperties(PropertyWindow form, object controlObject);

    }
}
