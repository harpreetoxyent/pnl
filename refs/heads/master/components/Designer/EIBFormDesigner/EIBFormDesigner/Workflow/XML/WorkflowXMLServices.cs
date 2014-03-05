using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
using System.Drawing;
using System.Xml;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Event;
using EIBXMLServices;
using EIBFormDesigner.Search;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.XML;
using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;

namespace EIBFormDesigner.Workflow.XML
{
    public class WorkflowXMLServices
    {
        private Hashtable controlProperties = null;
        internal Hashtable ControlProperties
        {
            get
            {
                return controlProperties;
            }
            set
            {
                controlProperties = value;
            }
        }
        //
        //
        //
        private WorkflowXMLServices()
        {
        }
        public static void appendElement(XmlNode xmlNode, String element, Hashtable properties, Hashtable eventProperties)
        {
            xmlNode.RemoveAll();
            if (properties != null)
            {
                foreach (string name in properties.Keys)
                {
                    XmlAttribute attr = xmlNode.OwnerDocument.CreateAttribute(name);
                    attr.InnerText = (string)properties[name];
                    xmlNode.Attributes.Append(attr);
                }
            }
            if (eventProperties != null)
            {
                foreach (string name in eventProperties.Keys)
                {
                    XmlNode eventNode = xmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventElt, null);
                    eventNode.InnerText = (string)eventProperties[name];
                    xmlNode.AppendChild(eventNode);
                    XmlAttribute attr = eventNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                    attr.InnerText = name;
                    eventNode.Attributes.Append(attr);
                }
            }
        }
        public static XmlNode writeElement(XmlNode parentXmlNode, String element, Hashtable properties, Hashtable eventProperties)
        {

            XmlNode xmlNode = parentXmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, element, null);
            parentXmlNode.AppendChild(xmlNode);
            if (properties != null)
            {
                foreach (string name in properties.Keys)
                {
                    XmlAttribute attr = parentXmlNode.OwnerDocument.CreateAttribute(name);
                    attr.InnerText = (string)properties[name];
                    xmlNode.Attributes.Append(attr);
                }
            }
            if (eventProperties != null)
            {
                foreach (string name in eventProperties.Keys)
                {
                    XmlNode eventNode = xmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventElt, null);
                    eventNode.InnerText = (string)eventProperties[name];
                    xmlNode.AppendChild(eventNode);
                    XmlAttribute attr = eventNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                    attr.InnerText = name;
                    eventNode.Attributes.Append(attr);
                }
            }
            return xmlNode;
        }
        public static XmlNode getRootNode(XmlDocument xmlDocument, String element, Hashtable properties)
        {

            XmlNode xmlNode = xmlDocument.CreateNode(XmlNodeType.Element, element, null);
            xmlDocument.AppendChild(xmlNode);
            if (properties != null)
            {
                foreach (string name in properties.Keys)
                {
                    XmlAttribute attr = xmlDocument.CreateAttribute(name);
                    attr.InnerText = (string)properties[name];
                    xmlNode.Attributes.Append(attr);
                }
            }
            return xmlNode;
        }
        public static Hashtable updateEvents(Control control, string elementName, string uniqueId)
        {
            Hashtable controlProperties = new Hashtable();
            string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
            {

                INodeControl eibControl = (INodeControl)control;
                if (eibControl.NodeValue != null && !eibControl.NodeValue.Trim().Equals(""))
                {

                    controlProperties.Add(FormDesignerConstants.OnNode, eibControl.NodeValue);
                }
                if (eibControl.OnNodeEnterValue != null && !eibControl.OnNodeEnterValue.Trim().Equals(""))
                {
                    controlProperties.Add(FormDesignerConstants.EnterNode, eibControl.OnNodeEnterValue);
                }
                if (eibControl.OnNodeExitClick != null && !eibControl.OnNodeExitClick.Trim().Equals(""))
                {
                    controlProperties.Add(FormDesignerConstants.ExitNode, eibControl.OnNodeExitClick);
                }
            }
            //EIBControl Settings
            return controlProperties;
        }

        public static Hashtable updateSettings(Control control, string elementName, string uniqueId)
        {
            //Add settings realted to control into hashtable which will later
            //be written in XML
            Hashtable controlProperties = new Hashtable();
            controlProperties.Add(XMLServicesConstants.XmlNodePositionAtt, "absolute");

            controlProperties.Add(XMLServicesConstants.XmlNodeNameAtt, elementName);
            controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, uniqueId);
            controlProperties.Add(XMLServicesConstants.XmlNodeWidthAtt, control.Width.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeHeightAtt, control.Height.ToString());
            string htmlColor = "";
            if (control.BackColor == Color.Transparent)
            {
                htmlColor = "Transparent";
            }
            else
            {
                htmlColor = string.Concat("#", (control.BackColor.ToArgb() & 0x00FFFFFF).ToString("X6"));
            }
            controlProperties.Add(XMLServicesConstants.XmlNodeBackColorAtt, htmlColor);
            controlProperties.Add(XMLServicesConstants.XmlNodeFontAtt, control.Font.Name);
            controlProperties.Add(XMLServicesConstants.XmlNodeFontSizeAtt, control.Font.Size.ToString());
            string foreColor = "";
            if (control.ForeColor == Color.Transparent)
            {
                foreColor = "Transparent";
            }
            else
            {
                foreColor = string.Concat("#", (control.ForeColor.ToArgb() & 0x00FFFFFF).ToString("X6"));
            }
            if (control.Padding != null)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodePaddingBottomAtt, control.Padding.Bottom.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodePaddingLeftAtt, control.Padding.Left.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodePaddingRightAtt, control.Padding.Right.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodePaddingTopAtt, control.Padding.Top.ToString());
            }

            if (control.Margin != null)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeMarginBottomAtt, control.Margin.Bottom.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeMarginLeftAtt, control.Margin.Left.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeMarginRightAtt, control.Margin.Right.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeMarginTopAtt, control.Margin.Top.ToString());
            }

            controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, control.Visible.ToString().ToLower());
            controlProperties.Add(XMLServicesConstants.XmlNodeForeColorAtt, foreColor);
            controlProperties.Add(XMLServicesConstants.XmlNodeNetForeColorAtt, control.ForeColor.ToKnownColor().ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeFontStyleAtt, control.Font.Style.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeXAtt, control.Location.X.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeYAtt, control.Location.Y.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeTextAtt, control.Text);
            controlProperties.Add(XMLServicesConstants.XmlNodeAlignAtt, control.Dock.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeNetBackColorAtt, control.BackColor.ToKnownColor().ToString());

            return controlProperties;
        }

        public static Hashtable updateMenuEvents(ToolStripItem control, string elementName, string uniqueId)
        {
            Hashtable controlProperties = new Hashtable();
            string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
            {

                IEIBControl eibControl = (IEIBControl)control;
                if (eibControl.OnClickValue != null && !eibControl.OnClickValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnClickElt, eibControl.OnClickValue);
                }
                if (eibControl.OnDoubleClick != null && !eibControl.OnDoubleClick.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnDoubleClickElt, eibControl.OnDoubleClick);
                }
                if (eibControl.EnteringValue != null && !eibControl.EnteringValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeEnteringElt, eibControl.EnteringValue);
                }
                if (eibControl.ExitingValue != null && !eibControl.ExitingValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeExitingElt, eibControl.ExitingValue);
                }
                if (eibControl.DefaultValue != null && !eibControl.DefaultValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeDefaultValueElt, eibControl.DefaultValue);
                }
            }
            //EIBControl Settings
            return controlProperties;
        }


        public static void saveXMLofNodeConnector(Control nodeControl)
        {
            if (nodeControl != null)
            {
                EIBNodeConnector nodeEIB = (EIBNodeConnector)nodeControl;
                EIBNode fromNode = nodeEIB.Mark1;
                EIBNode toNode = nodeEIB.Mark2;
                //Save workflow Node
                Hashtable controlProperties = new Hashtable();
                XmlNode xmlNode = XMLServices.writeElement(nodeEIB.ParentXmlNode, nodeEIB.ControlType, controlProperties, null, null);

                XmlAttribute connectorNameAtt = xmlNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                connectorNameAtt.Value = nodeEIB.ControlName;
                xmlNode.Attributes.Append(connectorNameAtt);

                //Save from node Pattern
                controlProperties.Clear();
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, fromNode.WorkFlowNode.WorkFlowNodeId);
                XMLServices.writeElement(xmlNode, "from", controlProperties, null, null);

                //Save from node Pattern
                controlProperties.Clear();
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, toNode.WorkFlowNode.WorkFlowNodeId);
                XMLServices.writeElement(xmlNode, "to", controlProperties, null, null);

            }
        }

        public static void saveXMLofNode(Control nodeControl,XmlNode xmlNode)
        {
            if (nodeControl != null)
            {
                EIBNode nodeEIB = (EIBNode)nodeControl;
                WorkflowNode workflowNode = nodeEIB.WorkFlowNode;
                //Save workflow Node
                Hashtable controlProperties = new Hashtable();
                //Save Form Pattern
                FormPatterns formpatterns = workflowNode.FormPatterns;
                //controlProperties.Clear();
                XmlNode xmlformpatternsnode= formpatterns.Searialize(xmlNode.OwnerDocument);
                xmlNode.AppendChild(xmlformpatternsnode);

                //Save Data Pattern
                DataPatterns dataPatterns = workflowNode.DataPatterns;
                //controlProperties.Clear();
                XmlNode xmldatapatternsnode = dataPatterns.Searialize(xmlNode.OwnerDocument);
                xmlNode.AppendChild(xmldatapatternsnode);

                //Save Data Pattern
                EIBFormDesigner.Workflow.Node.DataObject dataObject = workflowNode.DataObject;
                //controlProperties.Clear();
                XmlNode dataObjectNode = XMLServices.writeElement(xmlNode, "dataobject", controlProperties, null, null);

                //Save Data Info section
                //controlProperties.Clear();
                XMLServices.writeElement(dataObjectNode, "info", controlProperties, null, null);

                //Save Data data section 
                //controlProperties.Clear();
                XMLServices.writeElement(dataObjectNode, "data", controlProperties, null, null);

                //Save Data status section 
                //controlProperties.Clear();
                XmlNode xmlNodeStatus =  XMLServices.writeElement(dataObjectNode, "status", controlProperties, null, null);

                xmlNodeStatus.InnerText = dataObject.Status;
                //xmlNodeStatus.AppendChild(status);
                //Save User Pattern
                 UserPatterns userPattern = workflowNode.UserPatterns;
                //controlProperties.Clear();
                XmlNode xmluserpatternsnode = userPattern.Searialize(xmlNode.OwnerDocument);
                xmlNode.AppendChild(xmluserpatternsnode);


                //Save Data status section 
                //controlProperties.Clear();
                //XMLServices.writeElement(userPatternNode, "role", controlProperties, null);

            }
        }

        public static void saveXMLofChildren(Control panelControl)
        {
            foreach (Control control in panelControl.Controls)
            {
                //if (!(control is EIBFormDesigner.Workflow.Node.MarkControl))
                //{
                saveXMLofControl(control, null);
                //}
            }
        }

        private static void saveXMLofControl(Control control, XmlNode controlXMLNode)
        {
            //Give Handle to XML Node to new Frame Element so it can write its own child
            string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
            {
                if (control.Name != null)
                {
                    Hashtable controlProperties = WorkflowXMLServices.updateSettings(control, ((IEIBControl)control).ControlName, control.Name.ToLower());
                    Hashtable eventProperties = WorkflowXMLServices.updateEvents(control, control.Name.ToLower(), control.Name.ToLower());
                    XmlNode xmlNode = null;
                    if (!control.Name.Trim().Equals(""))
                    {
                        if (!(control is EIBFormDesigner.Workflow.Controls.MarkControl))
                        {
                            if (controlXMLNode == null)
                            {
                                if (control.GetType().Equals(typeof(EIBNode)))
                                {
                                    //controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBNode)control).WorkFlowNode.WorkFlowNodeId);
                                    controlProperties.Add("scenario", EIBXMLUtilities.projectName);
                                    controlProperties.Add("isstart", ((EIBNode)control).startNodeCheck.Checked.ToString());
                                    //XmlNode xmlNode = XMLServices.writeElement(nodeEIB.ParentXmlNode, nodeEIB.ControlType, controlProperties, null);
                                    xmlNode = XMLServices.writeElement(((IEIBControl)(control.Parent)).ParentXmlNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties, null);
                                    saveXMLofNode(control,xmlNode);

                                }
                                if (control.GetType().Equals(typeof(EIBNodeConnector)))
                                {
                                    ((IEIBControl)control).ParentXmlNode = ((IEIBControl)(control.Parent)).ParentXmlNode;
                                    saveXMLofNodeConnector(control);
                                }
                            }
                            else
                            {
                                xmlNode = XMLServices.writeElement(controlXMLNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties, null);

                            }
                        }
                        
                        

                    }
                }
            }
        }

    }

}
