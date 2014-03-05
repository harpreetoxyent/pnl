using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using EIBFormDesigner.Designer;
//using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;

namespace EIBFormDesigner.Workflow.Node
{
    public class UserPatterns
    {

    List<UserPattern> userpatterns;
        public UserPatterns()
        {
            userpatterns = new List<UserPattern>();
        }
        public List<UserPattern> ListofUserPatterns
        {
            get { return userpatterns; }
            set { userpatterns = value; }
        }

        public static UserPatterns Desearilize(XmlNode node)
        {
            UserPatterns obj = null;
            if(node!=null && node.Name=="UserPatterns")
            {
                obj = new UserPatterns();
                foreach (XmlNode cnode in node.ChildNodes)
                {
                    if (cnode.Name == "userpattern")
                    {
                        obj.ListofUserPatterns.Add(new UserPattern(cnode.Attributes["userpatternid"].Value));
                    }
                }
            }
            return obj;
        }
        public XmlNode Searialize(XmlDocument doc)
        {
            UserPatterns obj = this;
            XmlNode node = doc.CreateNode(XmlNodeType.Element, "UserPatterns", null);
            foreach (UserPattern fp in obj.ListofUserPatterns)
            {
                XmlNode cnode= doc.CreateNode(XmlNodeType.Element, "userpattern", null);
                XmlAttribute userpatternidatt = doc.CreateAttribute("userpatternid");
                userpatternidatt.Value = fp.Userpatternid;
                cnode.Attributes.Append(userpatternidatt);
                node.AppendChild(cnode);
            }
            return node;
        }

    }
    public class UserPattern
    {
        string userpatternid;
        public UserPattern(string userpatternid)
        {
            Userpatternid = userpatternid;
        }

        public string Userpatternid
        {
            get { return userpatternid; }
            set { userpatternid = value; }
        }
        
    }
}
