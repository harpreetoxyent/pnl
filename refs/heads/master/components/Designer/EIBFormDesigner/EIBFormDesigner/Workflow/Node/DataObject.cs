using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;

namespace EIBFormDesigner.Workflow.Node
{
    public class DataObject
    {
        string status;

        public string Status
        {
            get { return status; }
            set { status = value; }
        }

        internal static DataObject Desearilize(System.Xml.XmlNode node)
        {
            DataObject obj = null;
            if (node != null && node.Name == "dataobject")
            {
                obj = new DataObject();
                foreach (XmlNode cnode in node.ChildNodes)
                {
                    if (cnode.Name == "status")
                    {
                        obj.Status = cnode.InnerText;
                    }
                }
            }
            return obj;
        }
    }
}
