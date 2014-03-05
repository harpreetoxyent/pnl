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

namespace EIBFormDesigner.XML
{
    public class EIBXMLServices
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
        public static String folderName = "";
        public static String projectName = "";
        public static String projectFilePath = "";
        public static String imageDirectory = "\\img\\";
        public static String formFolderName = "";
        public static String dataFolderName = "";
        public static String workflowFolderName = "";
        //
        //
        //
        private EIBXMLServices()
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

        public static Hashtable updateSettings(Control control, string elementName, string uniqueId)
        {
            //Add settings realted to control into hashtable which will later
            //be written in XML
            Hashtable controlProperties = new Hashtable();
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
            controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, control.Visible.ToString().ToLower());
            controlProperties.Add(XMLServicesConstants.XmlNodeForeColorAtt, foreColor);
            controlProperties.Add(XMLServicesConstants.XmlNodeFontStyleAtt, control.Font.Style.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeXAtt, control.Location.X.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeYAtt, control.Location.Y.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeTextAtt, control.Text);
            controlProperties.Add(XMLServicesConstants.XmlNodeAllignAtt, control.Dock.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeNetBackColorAtt, control.BackColor.ToKnownColor().ToString());

            //Attributes specific to COntrols
            if (control is EIBTextBox)
            {
                EIBTextBox textBoxEIB = (EIBTextBox)control;
                controlProperties.Add(XMLServicesConstants.XmlNodePasswordAtt, textBoxEIB.UseSystemPasswordChar.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeMultiLineAtt, textBoxEIB.Multiline.ToString().ToLower());

            }
            if (control is EIBPanel)
            {
                EIBPanel panelEIB = (EIBPanel)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, panelEIB.BorderStyle.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeDefaultAtt, panelEIB.DefaultScreen.ToString().ToLower());
            }
            if (control is EIBRadioButton)
            {
                EIBRadioButton radioButton = (EIBRadioButton)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeSelectedAtt, radioButton.Checked.ToString().ToLower());
            }
            if (control is EIBCheckbox)
            {
                EIBCheckbox checkbox = (EIBCheckbox)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeSelectedAtt, checkbox.Checked.ToString().ToLower());
            }
            if (control is EIBPicture)
            {
                EIBPicture picture = (EIBPicture)control;
                Image pictureImage  = picture.Image;


                if (pictureImage != null)
                {
                    string sourceImage = "\\img\\" + picture.ControlName + ".jpg";
                    String curDir = Directory.GetCurrentDirectory();
                    if (!Directory.Exists(EIBXMLServices.folderName + "\\img\\"))
                    {
                        Directory.CreateDirectory(EIBXMLServices.folderName + "\\img\\");
                    }
                    try
                    {
                        pictureImage.Save(EIBXMLServices.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                    }
                    catch (Exception)
                    {
                        //MessageBox.Show(ex.Message[0].ToString());
                        //do nothing
                    }
                    controlProperties.Add(XMLServicesConstants.XmlNodeSourceAtt, sourceImage);
                    Directory.SetCurrentDirectory(curDir);
                }
                
            }
            string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
            {
                IEIBControl eibControl = (IEIBControl)control;
                if (eibControl.DataPatternName != null && (!eibControl.DataPatternName.Trim().Equals("")))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeDataPatternAtt, eibControl.DataPatternName);
                }
                if (eibControl.DataTableName != null && (!eibControl.DataTableName.Trim().Equals("")))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeDataTableAtt, eibControl.DataTableName);
                }
                if (eibControl.DataFieldName != null && (!eibControl.DataFieldName.Trim().Equals("")))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeDataFieldAtt, eibControl.DataFieldName);
                }
               

            }
            return controlProperties;
        }

        public static Hashtable updateTreeNodeEvents(TreeNode control, string elementName, string uniqueId)
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

        public static Hashtable updateMenuSettings(ToolStripItem control, string elementName, string uniqueId)
        {
            Hashtable controlProperties = null;
            controlProperties = new Hashtable();
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
            controlProperties.Add(XMLServicesConstants.XmlNodeNetBackColorAtt, control.BackColor.ToKnownColor().ToString());
 
            controlProperties.Add("text", control.Text);
            return controlProperties;
        }

        public static Dictionary<string,string> readXMLConfiguration(string path, string filename)
        {
            Dictionary<string,string> configDictionary = new Dictionary<string,string>();
            filename = path + filename;
            FileInfo configFile = new FileInfo(filename);
            FileStream fileStream = configFile.OpenRead();
            XmlDocument xmlDocument = new XmlDocument();
            try
            {
                xmlDocument.Load(fileStream);
                if (xmlDocument.HasChildNodes)
                {
                    XmlNode oNode = xmlDocument.DocumentElement;
                    XmlNodeList oNodeList = oNode.ChildNodes;
                    for (int x = 0; x < oNodeList.Count; x++)
                    {

                        configDictionary.Add(oNodeList.Item(x).InnerText, oNodeList.Item(x).Value);
                    }
                }
            }
            catch (Exception ex)
            {
                Console.Write(ex.StackTrace);
            }
            return configDictionary;
        }
        public static void saveXMLofCombobox(Control comboBox)
        {
            EIBCombobox currentComboBox = (EIBCombobox)comboBox;
            int counter = 0;
            foreach (string itemValue in currentComboBox.Items)
            {

                Hashtable controlProperties = new Hashtable();
                if (counter == currentComboBox.SelectedIndex)
                {
                    controlProperties.Add("selected", true.ToString().ToLower());
                }
                controlProperties.Add("text", itemValue);
                controlProperties.Add("value", counter.ToString());
                XmlNode xmlNode = EIBXMLServices.writeElement(currentComboBox.ParentXmlNode, "option", controlProperties, null);
                counter++;
            }
        }


        public static void saveXMLofMenu(Control menuControl)
        {
            EIBMenuBar menuBar = (EIBMenuBar)menuControl;
            foreach (EIBMenu menu in menuBar.CurrentToolStrip.Items)
            {
                Hashtable controlProperties = EIBXMLServices.updateMenuSettings(menu, menu.Name, menu.Name);
                Hashtable eventProperties = EIBXMLServices.updateMenuEvents(menu, menu.Name.ToLower(), menu.Name.ToLower());

                XmlNode xmlNode = EIBXMLServices.writeElement(((IEIBControl)menuControl).ParentXmlNode, EIBControlList.GetRenderingElement(menu.GetType().Name), controlProperties, eventProperties);

                foreach (EIBMenuItem menuItem in menu.DropDownItems)
                {
                    Hashtable menuItemProperties = EIBXMLServices.updateMenuSettings(menuItem, menuItem.Name, menuItem.Name);
                    Hashtable menuEventProperties = EIBXMLServices.updateMenuEvents(menuItem, menuItem.Name.ToLower(), menuItem.Name.ToLower());

                    EIBXMLServices.writeElement(xmlNode, EIBControlList.GetRenderingElement(menuItem.GetType().Name), menuItemProperties, menuEventProperties);

                }
            }
        }
                                
        public static void saveXMLofTreeView(Control treeViewControl, TreeNode treeNode)
        {
            if (treeViewControl != null)
            {
                EIBTreeView treeView = (EIBTreeView)treeViewControl;
                foreach (EIBTreeNode treeNodeChild in treeView.Nodes)
                {
                    Hashtable controlProperties = new Hashtable();
                    controlProperties.Add("name", treeNodeChild.Name);
                    controlProperties.Add("text", treeNodeChild.Text);
                    if (treeNodeChild.Nodes.Count == 0)
                    {
                        controlProperties.Add("isleaf", true.ToString().ToLower());
                    }
                    Hashtable evntProperties = updateTreeNodeEvents(treeNodeChild, treeNodeChild.Name, treeNodeChild.Name);
                    XmlNode xmlNode = EIBXMLServices.writeElement(treeView.ParentXmlNode, EIBControlList.GetRenderingElement(treeNodeChild.GetType().Name), controlProperties, evntProperties);
                    treeNodeChild.ParentXmlNode = xmlNode;
                    saveXMLofTreeView(null, treeNodeChild);
                }
            }
            else if(treeNode !=null)
            {
                EIBTreeNode treeNodeParent = (EIBTreeNode)treeNode;
                foreach (EIBTreeNode treeNodeChild in treeNodeParent.Nodes)
                {
                    
                    Hashtable controlProperties = new Hashtable();
                    controlProperties.Add("name", treeNodeChild.Name);
                    controlProperties.Add("text", treeNodeChild.Text);
                    if (treeNodeChild.Nodes.Count == 0)
                    {
                        controlProperties.Add("isleaf", true.ToString().ToLower());
                    }
                    Hashtable evntProperties = updateTreeNodeEvents(treeNodeChild, treeNodeChild.Name, treeNodeChild.Name);
                    XmlNode xmlNode = EIBXMLServices.writeElement(treeNodeParent.ParentXmlNode, EIBControlList.GetRenderingElement(treeNodeChild.GetType().Name), controlProperties, evntProperties);
                    treeNodeChild.ParentXmlNode = xmlNode;
                    saveXMLofTreeView(null, treeNodeChild);
                }

            }
        }

        public static void saveXMLofTable(EIBGrid tableControl)
        {
            int rowCount = tableControl.RowCount;
            int columnCount = tableControl.ColumnCount;
            XmlNode xmlNode = null;
            for (int rowCounter = 0; rowCounter < rowCount; rowCounter++)
            {
                EIBRow row = new EIBRow();
                row.InitiateSettings(null);
                Hashtable controlProperties = new Hashtable();
                controlProperties.Add(XMLServicesConstants.XmlNodeHeightAtt, tableControl.RowStyles[rowCounter].Height.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeSizeTypeAtt, tableControl.RowStyles[rowCounter].SizeType.ToString());
                xmlNode = EIBXMLServices.writeElement(((IEIBControl)tableControl).ParentXmlNode, row.ControlType, controlProperties, null);
                
                row.ParentXmlNode = xmlNode;
                for (int colCounter = 0; colCounter < columnCount; colCounter++)
                {
                    XmlNode columnNode = null;
                    EIBColumn column = new EIBColumn();
                    column.InitiateSettings(null);
                    Hashtable columnProperties = new Hashtable();
                    columnProperties.Add(XMLServicesConstants.XmlNodeWidthAtt, tableControl.ColumnStyles[colCounter].Width.ToString());
                    columnProperties.Add(XMLServicesConstants.XmlNodeSizeTypeAtt, tableControl.ColumnStyles[colCounter].SizeType.ToString());
                    columnNode = EIBXMLServices.writeElement(((IEIBControl)row).ParentXmlNode, column.ControlType, columnProperties, null);
                    column.ParentXmlNode = columnNode;
                    Control control = tableControl.GetControlFromPosition(colCounter, rowCounter);
                    if (control != null)
                    {
                        saveXMLofControl(control, column.ParentXmlNode);
                    }
                }
            }
        }
        public static void saveXMLofChildren(Control panelControl)
        {
            //Scroll for all tabpages
            if (panelControl is EIBTabControl)
            {
                EIBTabControl eibTabCOntrol = (EIBTabControl)panelControl;
                foreach (EIBTabPage tabPage in eibTabCOntrol.TabPages)
                {
                    ((IEIBControl)tabPage).ParentXmlNode = eibTabCOntrol.ParentXmlNode;
                    Hashtable controlProperties = EIBXMLServices.updateSettings(tabPage, ((IEIBControl)tabPage).ControlName, tabPage.Name.ToLower());
                    Hashtable eventProperties = EIBXMLServices.updateEvents(tabPage, tabPage.Name.ToLower(), tabPage.Name.ToLower());
                    XmlNode xmlNode = null;
                    if (!tabPage.Name.Trim().Equals(""))
                    {
                        xmlNode = EIBXMLServices.writeElement((((IEIBControl)tabPage).ParentXmlNode), EIBControlList.GetRenderingElement(tabPage.GetType().Name), controlProperties, eventProperties);
                        ((IEIBControl)tabPage).ParentXmlNode = xmlNode;
                    }
                    // Save each Tab page as normal page
                    saveXMLofChildren(tabPage);
                }
            }
            foreach (Control control in panelControl.Controls)
            {
                saveXMLofControl(control, null);
            }
        }

        private static void saveXMLofControl( Control control, XmlNode controlXMLNode)
        {
            //Give Handle to XML Node to new Frame Element so it can write its own child
            string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
            {
                if (control.Name != null)
                {
                    Hashtable controlProperties = EIBXMLServices.updateSettings(control, ((IEIBControl)control).ControlName, control.Name.ToLower());
                    Hashtable eventProperties = EIBXMLServices.updateEvents(control, control.Name.ToLower(), control.Name.ToLower());
                    XmlNode xmlNode = null;
                    if (!control.Name.Trim().Equals(""))
                    {
                        if (!(control is EIBTabPage))
                        {
                            if (controlXMLNode == null)
                            {
                                xmlNode = EIBXMLServices.writeElement(((IEIBControl)(control.Parent)).ParentXmlNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties);
                            }
                            else
                            {
                                xmlNode = EIBXMLServices.writeElement(controlXMLNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties);

                            }
                        }
                        if (control.GetType().Equals(typeof(EIBPanel)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofChildren(control);
                        }
                        if (control.GetType().Equals(typeof(EIBTreeView)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofTreeView(control, null);
                        }
                        if (control.GetType().Equals(typeof(EIBTabControl)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofChildren(control);
                        }

                        if (control.GetType().Equals(typeof(EIBCombobox)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofCombobox(control);
                        }
                        if (control.GetType().Equals(typeof(EIBMenuBar)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofMenu(control);
                        }
                        if (control.GetType().Equals(typeof(EIBGrid)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofTable((EIBGrid)control);
                        }
                    }
                }
            }
        }
    
    }

}
