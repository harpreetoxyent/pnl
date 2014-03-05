using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;

namespace EIBFormDesigner.Workflow.Node
{
    public class FormPatterns
    {
        List<FormPattern> formpatterns;
        public FormPatterns()
        {
            formpatterns = new List<FormPattern>();
        }
        public List<FormPattern> ListofFormPatterns
        {
            get { return formpatterns; }
            set { formpatterns = value; }
        }

        public static FormPatterns Desearilize(XmlNode node)
        {
            FormPatterns obj = null;
            if(node!=null && node.Name=="FormPatterns")
            {
                obj = new FormPatterns();
                foreach (XmlNode cnode in node.ChildNodes)
                {
                    if (cnode.Name == "formpattern")
                    {
                        obj.ListofFormPatterns.Add(new FormPattern(cnode.Attributes["formpatternid"].Value));
                    }
                }
            }
            return obj;
        }
        public XmlNode Searialize(XmlDocument doc)
        {
            FormPatterns obj = this;
            XmlNode node = doc.CreateNode(XmlNodeType.Element, "FormPatterns", null);
            foreach (FormPattern fp in obj.ListofFormPatterns)
            {
                XmlNode cnode= doc.CreateNode(XmlNodeType.Element, "formpattern", null);
                XmlAttribute formpatternidatt = doc.CreateAttribute("formpatternid");
                formpatternidatt.Value = fp.Formpatternid;
                cnode.Attributes.Append(formpatternidatt);
                node.AppendChild(cnode);
            }
            return node;
        }

    }
    public class FormPattern
    {
        string formpatternid;
        public FormPattern(string formpatternid)
        {
            Formpatternid = formpatternid;
        }

        public string Formpatternid
        {
            get { return formpatternid; }
            set { formpatternid = value; }
        }
        
    }
}
