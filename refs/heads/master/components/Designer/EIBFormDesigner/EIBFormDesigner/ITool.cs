using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Tools;

namespace EIBFormDesigner
{
    public interface ITool
    {
         void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form);
                 
   }

}
