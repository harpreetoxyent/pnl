using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;

namespace EIBFormDesigner.Workflow.Node
{
    public class DataPatterns
    {
        List<DataPattern> datapatterns;
        public DataPatterns()
        {
            datapatterns = new List<DataPattern>();
        }
        public List<DataPattern> ListofDataPatterns
        {
            get { return datapatterns; }
            set { datapatterns = value; }
        }

        public static DataPatterns Desearilize(XmlNode node)
        {
            DataPatterns obj = null;
            if (node != null && node.Name == "DataPatterns")
            {
                obj = new DataPatterns();
                foreach (XmlNode cnode in node.ChildNodes)
                {
                    if (cnode.Name == "datapattern")
                    {
                        obj.ListofDataPatterns.Add(new DataPattern(cnode.Attributes["datapatternid"].Value));
                    }
                }
            }
            return obj;
        }
        public XmlNode Searialize(XmlDocument doc)
        {
            DataPatterns obj = this;
            XmlNode node = doc.CreateNode(XmlNodeType.Element, "DataPatterns", null);
            foreach (DataPattern fp in obj.ListofDataPatterns)
            {
                XmlNode cnode = doc.CreateNode(XmlNodeType.Element, "datapattern", null);
                XmlAttribute datapatternidatt = doc.CreateAttribute("datapatternid");
                datapatternidatt.Value = fp.Datapatternid;
                cnode.Attributes.Append(datapatternidatt);
                node.AppendChild(cnode);
            }
            return node;
        }

    }
    public class DataPattern
    {
        string datapatternid;
        public DataPattern(string datapatternid)
        {
            Datapatternid = datapatternid;
        }

        public string Datapatternid
        {
            get { return datapatternid; }
            set { datapatternid = value; }
        }

    }
}
