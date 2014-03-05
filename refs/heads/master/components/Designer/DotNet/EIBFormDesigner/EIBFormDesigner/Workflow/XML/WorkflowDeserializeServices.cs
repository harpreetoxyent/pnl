using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
using System.Drawing;
using System.Xml;
using System.Data;
using EIBFormDesigner.Search;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Event;
using EIBXMLServices;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.XML;

namespace EIBFormDesigner.Workflow.XML
{
    class WorkflowDeserializeServices
    {
        internal static IEIBControl currentControl = null;
        internal static IEIBControl currentTreeControl = null;
       
        //Deserialize individual base windows and there child control
        public static void DeserializeWindowXML(string fileName, Control baseFrame, XmlNode parentXMLNode)
        {
            DataSet dataset = new DataSet();
            XmlDocument doc = new XmlDocument();
            XmlNode currentXmlNode = null;
            Dictionary<string, EIBNode> listNode = new Dictionary<string, EIBNode>();
            try
            {

                // disabling re-drawing of treeview till all nodes are added
                baseFrame.SuspendLayout();
                fileName = fileName + ".xml";
                if (parentXMLNode == null)
                {
                    doc.Load(fileName);
                    currentXmlNode = doc.FirstChild;
                }
                else
                {
                    currentXmlNode = parentXMLNode;
                }
                baseFrame.Controls.Clear();
                if (currentXmlNode.Name == FormDesignerConstants.BaseWindow)
                {
                    // loading node attributes
                    UpdateControlProperties(baseFrame, currentXmlNode);
                }
                //Iterate all nodes

                foreach (XmlNode xmlNode in currentXmlNode.ChildNodes)
                {
                    if (xmlNode.NodeType == XmlNodeType.Element)
                    {
                        if (xmlNode.Name == FormDesignerConstants.NodeControl)
                        {
                            EIBNode.counter++;

                            EIBNode eibNode = new EIBNode();
                            UpdateControlProperties(eibNode, xmlNode);
                            eibNode.nodeIdText.Text = xmlNode.Attributes["id"].Value.ToString();
                            eibNode.startNodeCheck.Checked = (xmlNode.Attributes["isstart"].Value.ToLower().Equals("true"));
                           // baseFrame.Controls.Add(eibNode);
                            listNode.Add(eibNode.nodeIdText.Text, eibNode);
                            //EIBNode newNode = new EIBNode();
                           // UpdateControlProperties(newNode, xmlNode);
                           // newNode.nodeIdText.Text = xmlNode["workflownode"].Attributes["id"].Value.ToString();
                           // newNode.startNodeCheck.Checked = (xmlNode["workflownode"].Attributes["isstart"].Value.ToLower().Equals("true"));
                            //XmlNode xmlWorkFlowNode = getXMLNodeWithName(xmlNode,"workflownode")
                            //xmlNode[
                            eibNode.WorkFlowNode= Desearializeworkflownodexml(xmlNode);
                            eibNode.workflowNode.WorkFlowNodeId = eibNode.nodeIdText.Text;
                            eibNode.nodeIdText.Enabled = false;
                            ((BaseWindow)baseFrame.Parent).WorkflowNodes.Add(eibNode.workflowNode.WorkFlowNodeId, eibNode);
                            baseFrame.Controls.Add(eibNode);

                        }
                        if (xmlNode.Name == "connector")
                        {
                            try
                            {
                                //EIBNodeConnector.counter++;
                                EIBNodeConnector newNode = new EIBNodeConnector();
                                XmlNode xmlNodeconnector = xmlNode;
                                UpdateControlProperties(newNode, xmlNodeconnector);
                                XmlNode connectorNode = xmlNodeconnector;
                                //XmlNode fromNodeName = connectorNode["from"].Attributes["id"];
                                //XmlNode toNodeName = connectorNode["to"].Attributes["id"];
                                string fromNodeName = connectorNode["from"].Attributes["id"].Value;
                                string toNodeName = connectorNode["to"].Attributes["id"].Value;
                                EIBNode fromNode = listNode[fromNodeName];
                                EIBNode toNode = listNode[toNodeName];
                                Graphics g = null;
                                Bitmap bmpBack = new Bitmap(baseFrame.Width, baseFrame.Height);
                                Graphics.FromImage(bmpBack).Clear(baseFrame.BackColor);
                                baseFrame.BackgroundImage = (Bitmap)bmpBack.Clone();
                                g = Graphics.FromImage(baseFrame.BackgroundImage);
                                int centerMarkX = (fromNode.Center.X + toNode.Center.X) / 2;
                                int centerMarkY = (fromNode.Center.Y + toNode.Center.Y) / 2;
                                newNode.centerMark.Location = new Point(centerMarkX - 4, centerMarkY - 4);
                                newNode.InitiateSettings((EIBPanel)baseFrame);
                                newNode.Mark1 = fromNode;
                                newNode.Mark2 = toNode;
                                newNode.createLine();
                                baseFrame.Controls.Add(newNode.centerMark);
                                g.DrawLine(new Pen(Color.RoyalBlue, (float)2), fromNode.Center.X, fromNode.Center.Y, centerMarkX, centerMarkY);
                                g.DrawLine(new Pen(Color.RoyalBlue, (float)2), toNode.Center.X, toNode.Center.Y, centerMarkX, centerMarkY);
                                baseFrame.Controls.Add(newNode);
                            }
                            catch (Exception ex)
                            {
                                MessageBox.Show("Connector xml Modified.");
                            }
                        }
                     }
                }
                //Iterate all nodes connector
                /*foreach (XmlNode xmlNodeconnector in currentXmlNode.ChildNodes)
                {
                    if (xmlNodeconnector.NodeType == XmlNodeType.Element)
                    {
                            
                    }
                 }*/                
            }
            catch (FileNotFoundException)
            {
                MessageBox.Show("Basewindow.xml not Found");
            }
            finally
            {
                baseFrame.ResumeLayout();
                // enabling redrawing of treeview after all nodes are added
                baseFrame.Invalidate();
            }
        }

        public static WorkflowNode Desearializeworkflownodexml(XmlNode xmlnode)
        {
            WorkflowNode wnode = new WorkflowNode();
            if (xmlnode.Attributes["id"] != null)
            {
                string workflownodeid = xmlnode.Attributes["id"].Value;

                bool isstart = bool.Parse(xmlnode.Attributes["isstart"].Value);
                string scenario = xmlnode.Attributes["scenario"].Value;
            }
            foreach (XmlNode cnode in xmlnode.ChildNodes)
            {
                if (cnode.Name == "FormPatterns")
                {
                    wnode.FormPatterns = FormPatterns.Desearilize(cnode);
                }
                else if (cnode.Name == "DataPatterns")
                {
                    wnode.DataPatterns = DataPatterns.Desearilize(cnode);
                }
                else if (cnode.Name == "dataobject")
                {
                    wnode.DataObject = EIBFormDesigner.Workflow.Node.DataObject.Desearilize(cnode);
                }
                else if (cnode.Name == "UserPatterns")
                {
                    wnode.UserPatterns = UserPatterns.Desearilize(cnode);
                }
            }
            return wnode;
        }

        private static void UpdateControlProperties(Control control, XmlNode xmlNode)
        {
            int attributeCount = xmlNode.Attributes.Count;
            if (attributeCount > 0)
            {
                for (int counter = 0; counter < attributeCount; counter++)
                {
                    SetAttributeValue(control, xmlNode.Attributes[counter].Name, xmlNode.Attributes[counter].Value);
                    string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
                    if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
                    {
                        SetEIBAttributeValue((IEIBControl)control, xmlNode.Attributes[counter].Name, xmlNode.Attributes[counter].Value);
                    }
                }
            }
            if (!(control is BaseWindow) && !(control.Parent is BaseWindow))
            {
                if (control is EIBNodeConnector)
                {
                    FormDesigner.eventManager.addPropertiesControl(((EIBNodeConnector)control).centerMark);
                }
                else
                {
                    FormDesigner.eventManager.addPropertiesControl(control);
                }
            }
            string eibInterfaceName = (typeof(INodeControl)).Name;
            if (control.GetType().GetInterface(eibInterfaceName) != null)
            {
                SetEIBEventsValue((INodeControl)control, xmlNode);
            }
        }

        // This method deserialize the EIB events associated with the control from 
        // XML document. Look into all event nodes and check for associated event 
        // name
        private static void SetEIBEventsValue(INodeControl control, XmlNode xmlNode)
        {
            foreach (XmlNode childXmlNode in xmlNode.ChildNodes)
            {
                if (childXmlNode.NodeType == XmlNodeType.Element)
                {
                    if (childXmlNode.Name == XMLServicesConstants.XmlNodeEventsElt)
                    {
                        foreach (XmlNode childEventXmlNode in childXmlNode.ChildNodes)
                        {
                            int attributeCount = childEventXmlNode.Attributes.Count;
                            if (attributeCount > 0)
                            {
                                for (int counter = 0; counter < attributeCount; counter++)
                                {
                                    if (childEventXmlNode.Attributes[counter].Value == FormDesignerConstants.OnNode)
                                    {
                                        control.NodeValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == FormDesignerConstants.EnterNode)
                                    {
                                        control.OnNodeEnterValue = childEventXmlNode.InnerText;
                                    }
                                    if (childEventXmlNode.Attributes[counter].Value == FormDesignerConstants.ExitNode)
                                    {
                                        control.OnNodeExitClick = childEventXmlNode.InnerText;
                                    }
                                }
                            }
                        }
                    }
                    }
            }
        }


        private static void SetEIBAttributeValue(IEIBControl node, string propertyName, string value)
        {
            if (propertyName == XMLServicesConstants.XmlNodeNameAtt)
            {
                node.ControlName = value;
            }
        }
        private static void SetAttributeValue(Control control, string propertyName, string value)
        {
            if (propertyName == XMLServicesConstants.XmlNodeTextAtt)
            {
                control.Text = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeIdAtt)
            {
                control.Name = value;
            }
            if (propertyName == XMLServicesConstants.XmlNodeXAtt)
            {
                if (Int32.Parse(value) < 0)
                {
                    value = "0";
                }
                control.Location = new System.Drawing.Point(Int32.Parse(value), control.Location.Y);
            }
            if (propertyName == XMLServicesConstants.XmlNodeYAtt)
            {
                if (Int32.Parse(value) < 0)
                {
                    value = "0";
                }
                control.Location = new System.Drawing.Point(control.Location.X, Int32.Parse(value));
            }
            if (propertyName == XMLServicesConstants.XmlNodeWidthAtt)
            {
                control.Width = Int32.Parse(value);
                if (control is EIBColumn)
                {
                    EIBColumn column = (EIBColumn)control;
                    if (column.WidthType == WidthMeasurement.Pixel)
                    {
                        column.WidthPixel = control.Width;
                    }
                }

            }
            if (propertyName == XMLServicesConstants.XmlNodeHeightAtt)
            {
                control.Height = Int32.Parse(value);
                //if (control is EIBRow)
                //{
                //    EIBRow row = (EIBRow)control;
                //    if (row.HeightTyp == EIBRow.HeightMeasurement.Pixel)
                //    {
                //        row.HeightPixel = control.Height;
                //    }
                //}
            }
            if (propertyName == XMLServicesConstants.XmlNodeVisibleAtt)
            {
                if ((value.Equals(true.ToString().ToLower())))
                    control.Visible = true;
                else
                    control.Visible = false;
            }
            if (propertyName == XMLServicesConstants.XmlNodeFontSizeAtt)
            {
                control.Font = new Font(control.Font.FontFamily, float.Parse(value));
            }
            if (propertyName == XMLServicesConstants.XmlNodeFontStyleAtt)
            {
                control.Font = new Font(value, control.Font.Size);
            }
            if (propertyName == XMLServicesConstants.XmlNodeNetBackColorAtt)
            {
                control.BackColor = Color.FromName(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodeNetForeColorAtt)
            {
                control.ForeColor = Color.FromName(value);
            }
            if (propertyName == XMLServicesConstants.XmlNodePasswordAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    textBox.PasswordChar = value[0];
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeSourceAtt)
            {
                if (control is EIBPicture)
                {
                    EIBPicture picture = (EIBPicture)control;
                    Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
                    picture.Image = imgInFile;
                    imgInFile.Dispose();
                }
                if (control is EIBButton)
                {
                    EIBButton button = (EIBButton)control;
                    Image imgInFile = Image.FromFile(EIBXMLUtilities.folderName + "/" + value);
                    button.Image = imgInFile;
                    imgInFile.Dispose();
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeMultiLineAtt)
            {
                if (control is EIBTextBox)
                {
                    EIBTextBox textBox = (EIBTextBox)control;
                    textBox.Multiline = (value == true.ToString().ToLower());
                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeSizeTypeAtt)
            {
                //if (control is EIBRow)
                //{
                //    EIBRow row = (EIBRow)control;
                //    if (value.Trim().Equals("Pixel"))
                //    {
                //        row.HeightTyp = EIBRow.HeightMeasurement.Pixel;
                //        row.HeightPixel = row.Height;
                //    }
                //    if (value.Trim().Equals("Percent"))
                //    {
                //        row.HeightTyp = EIBRow.HeightMeasurement.Percent;
                //    }
                //    if (value.Trim().Equals("Auto"))
                //    {
                //        row.HeightTyp = EIBRow.HeightMeasurement.Auto;
                //    }
                //}
                if (control is EIBColumn)
                {
                    EIBColumn column = (EIBColumn)control;
                    if (value.Trim().Equals("Pixel"))
                    {
                        column.WidthType = WidthMeasurement.Pixel;
                        column.WidthPixel = column.Width;
                    }
                    if (value.Trim().Equals("Percent"))
                    {
                        column.WidthType = WidthMeasurement.Percent;
                    }
                    if (value.Trim().Equals("Auto"))
                    {
                        column.WidthType = WidthMeasurement.Auto;
                    }
                }

            }


            if (propertyName == XMLServicesConstants.XmlNodeDefaultAtt)
            {
                if (control is EIBPanel)
                {
                    EIBPanel panel = (EIBPanel)control;
                    panel.DefaultScreen = (value.Equals(true.ToString().ToLower()));

                }
            }
            if (propertyName == XMLServicesConstants.XmlNodeBorderAtt)
            {
                if (control is EIBPanel)
                {
                    EIBPanel panel = (EIBPanel)control;
                    if (value == System.Windows.Forms.BorderStyle.FixedSingle.ToString().ToLower())
                    {
                        panel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
                    }
                    if (value == System.Windows.Forms.BorderStyle.None.ToString())
                    {
                        panel.BorderStyle = System.Windows.Forms.BorderStyle.None;
                    }
                    if (value == System.Windows.Forms.BorderStyle.Fixed3D.ToString())
                    {
                        panel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
                    }
                }
                if (control is EIBLabel)
                {
                    EIBLabel label = (EIBLabel)control;
                    if (value == System.Windows.Forms.BorderStyle.FixedSingle.ToString().ToLower())
                    {
                        label.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
                    }
                    if (value == System.Windows.Forms.BorderStyle.None.ToString())
                    {
                        label.BorderStyle = System.Windows.Forms.BorderStyle.None;
                    }
                    if (value == System.Windows.Forms.BorderStyle.Fixed3D.ToString())
                    {
                        label.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
                    }
                }

            }
        }

    }
}

