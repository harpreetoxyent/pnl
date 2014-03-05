using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.Collections;
using System.Data;
using System.ComponentModel;

namespace EIBFormDesigner
{
    public interface IBaseWindow
    {
        string DefaultValue
        {
            get;
            set;
        }
        string OnClickValue
        {
            get;
            set;
        }

        string OnDoubleClick
        {
            get;
            set;
        }

        string EnteringValue
        {
            get;
            set;
        }

        string ExitingValue
        {
            get;
            set;
        }
        
        List<IEIBControl> CurrentControl
        {
            get;
            set;
        }
        XmlNode ParentXmlNode
        {
            get;
            set;
        }
        Hashtable ControlProperties
        {
            get;
            set;
        }
        DataSet DatabaseDataSet
        {
            get;
            set;
        }
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of control"), Category("Appearance")]
        string ControlName
        {
            get;
            set;
        }
        string ControlType
        {
            get;
        }
        string TypeOfWindow
        {
            get;
        }
    }
}
