using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;

namespace EIBFormDesigner.ScenarioWizard
{
    class ResourceUtilities
    {
        
        public void Serialize(Resources.Resource res)
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode resourceNode = doc.CreateNode(XmlNodeType.Element, "resources", null);
                XmlNode resourceChildNode = doc.CreateNode(XmlNodeType.Element, "resourceInfo", null);
                foreach (var variable in res.ResourceInfo)
                {
                    XmlAttribute attr = resourceChildNode.OwnerDocument.CreateAttribute(variable.Key);
                    attr.InnerText = variable.Value;
                    resourceChildNode.Attributes.Append(attr);
                    resourceNode.AppendChild(resourceChildNode);
                }
                doc.AppendChild(resourceNode);
                doc.Save(res.ResourcePath + "\\resourcess.xml");
            }
            catch
            {

            }
        }
    }
}
