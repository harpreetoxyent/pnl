using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Event;

namespace EIBFormDesigner.Properties
{
    public class PropertiesManager : IPropertiesManager
    {
        public PropertiesManager()
        {
  
        }
        public void displayProperties(PropertyWindow form, object controlObject)
        {
            try
            {
                List<IEIBControl> currentcontrol = (List<IEIBControl>)controlObject;
                if (currentcontrol.Count == 1)
                    form.propertyGrid.SelectedObject = currentcontrol[0];
                else
                    form.propertyGrid.SelectedObjects = (object[])currentcontrol.ToArray();
                form.propertyGrid.Refresh();
            }
            catch (Exception exp)
            {
                Console.Write(exp.StackTrace);
            }
        }
    }
}
