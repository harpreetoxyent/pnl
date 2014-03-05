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
using System.Configuration;
using System.Collections.Specialized;
using System.Reflection;

namespace EIBFormDesigner.XML
{
    public class XMLServices
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
      
        private XMLServices()
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
        public static XmlNode writeElement(XmlNode parentXmlNode, String element, Hashtable properties, Hashtable eventProperties,Hashtable internalEventProperties)
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
                XmlNode eventXmlNode = xmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventsElt, null);
                xmlNode.AppendChild(eventXmlNode);
                foreach (string name in eventProperties.Keys)
                {
                    XmlNode eventNode = eventXmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventElt, null);
                    XmlNode eventNodeCData = eventXmlNode.OwnerDocument.CreateNode(XmlNodeType.CDATA, "", null);
                    eventNodeCData.InnerText = (string)eventProperties[name];
                    //eventNode.InnerText = (string)eventProperties[name];
                    eventNode.AppendChild(eventNodeCData);
                    eventXmlNode.AppendChild(eventNode);
                    XmlAttribute attr = eventNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                    attr.InnerText = name;
                    eventNode.Attributes.Append(attr);
                }

                if (internalEventProperties != null)
                {
                    foreach (string name in internalEventProperties.Keys)
                    {
                        XmlNode eventNode = eventXmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventElt, null);
                        eventNode.InnerText = (string)internalEventProperties[name];
                        eventXmlNode.AppendChild(eventNode);
                        XmlAttribute attr = eventNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                        attr.InnerText = name;
                        eventNode.Attributes.Append(attr);
                        XmlAttribute internalAttr = eventNode.OwnerDocument.CreateAttribute("internal");
                        internalAttr.InnerText = "true";
                        eventNode.Attributes.Append(internalAttr);

                    }
                }
            }
            return xmlNode;
        }

        public static void writeDataMappings(XmlNode parentNode, List<Hashtable> Properties)
        {
            if (Properties != null)
            {
                XmlNode xmlDataMappingsNode = parentNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.xmlDataMappings, null);
                parentNode.AppendChild(xmlDataMappingsNode);
                foreach (Hashtable mappingProperties in Properties)
                {
                    XmlNode xmlNode = xmlDataMappingsNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.xmlMappings, null);
                    xmlDataMappingsNode.AppendChild(xmlNode);
                    if (mappingProperties != null)
                    {
                        foreach (string name in mappingProperties.Keys)
                        {
                            XmlAttribute attr = xmlDataMappingsNode.OwnerDocument.CreateAttribute(name);
                            attr.InnerText = (string)mappingProperties[name];
                            xmlNode.Attributes.Append(attr);
                        }
                    }
                }
            }
        }

        public static XmlNode getRootNode(XmlDocument xmlDocument, String element, Hashtable properties,Hashtable eventProperties,Hashtable internalEventProperties)
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
            if (eventProperties != null)
            {
                XmlNode eventXmlNode = xmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventsElt, null);
                xmlNode.AppendChild(eventXmlNode);
                foreach (string name in eventProperties.Keys)
                {
                    XmlNode eventNode = eventXmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventElt, null);
                    XmlNode eventNodeCData = eventXmlNode.OwnerDocument.CreateNode(XmlNodeType.CDATA, "", null);
                    eventNodeCData.InnerText = (string)eventProperties[name];
                    //eventNode.InnerText = (string)eventProperties[name];
                    eventNode.AppendChild(eventNodeCData);
                    eventXmlNode.AppendChild(eventNode);
                    XmlAttribute attr = eventNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                    attr.InnerText = name;
                    eventNode.Attributes.Append(attr);
                }
                foreach (string name in internalEventProperties.Keys)
                {
                    XmlNode eventNode = eventXmlNode.OwnerDocument.CreateNode(XmlNodeType.Element, XMLServicesConstants.XmlNodeEventElt, null);
                    eventNode.InnerText = (string)internalEventProperties[name];
                    eventXmlNode.AppendChild(eventNode);
                    XmlAttribute attr = eventNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                    attr.InnerText = name;
                    eventNode.Attributes.Append(attr);
                    XmlAttribute internalAttr = eventNode.OwnerDocument.CreateAttribute("internal");
                    internalAttr.InnerText = "true";
                    eventNode.Attributes.Append(internalAttr);

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
                if (eibControl.OnDrop != null && !eibControl.OnDrop.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnDropElt, eibControl.OnDrop);
                }
                if (eibControl.OnFocus != null && !eibControl.OnFocus.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnFocusElt, eibControl.OnFocus);
                }
                if (eibControl.OnBlur != null && !eibControl.OnBlur.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnBlurElt, eibControl.OnBlur);
                }
                if (eibControl.OnEventCreateValue != null && !eibControl.OnEventCreateValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnEventCreateElt, eibControl.OnEventCreateValue);
                }
                if (eibControl.OnEventEditValue != null && !eibControl.OnEventEditValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnEventEditElt, eibControl.OnEventEditValue);
                }
                if (eibControl.OnEventUpdateValue != null && !eibControl.OnEventUpdateValue.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnEventUpdateElt, eibControl.OnEventUpdateValue);
                }
                if (eibControl.OnChange != null && !eibControl.OnChange.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnChangeElt, eibControl.OnChange);
                }
                if (eibControl.OnOK != null && !eibControl.OnOK.Trim().Equals(""))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeOnOKElt, eibControl.OnOK);
                }
                if (eibControl is EIBPanel)
                {
                    if (((EIBPanel)eibControl).GlobalScripts != null && !((EIBPanel)eibControl).GlobalScripts.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeGlobalScriptsElt, ((EIBPanel)eibControl).GlobalScripts);
                    }
                }
                if (eibControl is EIBCheckbox)
                {
                    if (((EIBCheckbox)eibControl).OnCheck != null && !((EIBCheckbox)eibControl).OnCheck.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnCheckElt, ((EIBCheckbox)eibControl).OnCheck);
                    }
                }
                if (eibControl is EIBTreeNode)
                {
                    if (((EIBTreeNode)eibControl).OnOpen != null && !((EIBTreeNode)eibControl).OnOpen.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnOpenElt, ((EIBTreeNode)eibControl).OnOpen);
                    }
                }
                if (eibControl is EIBTreeView)
                {
                    if (((EIBTreeView)eibControl).OnSelect != null && !((EIBTreeView)eibControl).OnSelect.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnSelectElt, ((EIBTreeView)eibControl).OnSelect);
                    }
                }
                if (eibControl is EIBGrid)
                {
                    if (((EIBGrid)eibControl).OnSelect != null && !((EIBGrid)eibControl).OnSelect.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnSelectElt, ((EIBGrid)eibControl).OnSelect);
                    }
                }
                if (eibControl is EIBCombobox)
                {
                    if (((EIBCombobox)eibControl).OnChanging != null && !((EIBCombobox)eibControl).OnChanging.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnChangingElt, ((EIBCombobox)eibControl).OnChanging);
                    }

                    if (((EIBCombobox)eibControl).OnOpen != null && !((EIBCombobox)eibControl).OnOpen.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnOpenElt, ((EIBCombobox)eibControl).OnOpen);
                    }
                }
                if (eibControl is EIBTextBox)
                {
                    if (((EIBTextBox)eibControl).OnChanging != null && !((EIBTextBox)eibControl).OnChanging.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnChangingElt, ((EIBTextBox)eibControl).OnChanging);
                    }
                }
            }
            //Code moved to the other method named updateInternalEvents.
            /*IEIBControl ControlDesc = (IEIBControl)control;
            string controlName = "";
            if (control is EIBPanel)
            {
                if (control.Parent is BaseWindow)
                {
                    controlName = control.Parent.GetType().FullName;
                    ControlDesc = (IEIBControl)control.Parent;
                }
                else
                {
                    controlName = control.GetType().FullName;
                }
            }
            else
            {
                controlName = control.GetType().FullName;
            }
            NameValueCollection controlNameValues = (NameValueCollection)ConfigurationSettings.GetConfig("Internal/Controls/Name");
            if (controlNameValues[controlName] != null)
            {
                NameValueCollection AttributeValues = (NameValueCollection)ConfigurationSettings.GetConfig("Internal/Controls/Attribute");
                string attributeKey = AttributeValues.GetKey(0);
                string attributeValue = AttributeValues[attributeKey];
                PropertyInfo property = ControlDesc.GetType().GetProperty(attributeKey);
                object propertyValue = property.GetGetMethod().Invoke(ControlDesc,null);

                MethodInfo methodAttributeValue = ControlDesc.GetType().GetMethod(attributeValue);
                object attributeResult = methodAttributeValue.Invoke(ControlDesc, new object[] { propertyValue});

                if ((bool)attributeResult)
                {
                    NameValueCollection EventNameValues = (NameValueCollection)ConfigurationSettings.GetConfig("Internal/Controls/EventName");
                    string EventNameKey = EventNameValues.GetKey(0);
                    string EventNameValue = EventNameValues[EventNameKey];
                    EventNameValue = EventNameValue + "(\"" + ControlDesc.ControlName + ")\"";
                    controlProperties.Add(EventNameKey, EventNameValue);
                }
                //MessageBox.Show(attributeResult.ToString());
            }*/
            //EIBControl Settings
            return controlProperties;
        }
        public static Hashtable updateInternalEvents(Control control, string elementName, string uniqueId)
        {
            Hashtable controlProperties = new Hashtable();
            IEIBControl ControlDesc = (IEIBControl)control;
            string controlName = "";
            string name = ControlDesc.ControlName;
            if (control is EIBPanel)
            {
                if (control.Parent is BaseWindow)
                {
                    controlName = control.Parent.GetType().FullName;
                    ControlDesc = (IEIBControl)control.Parent;
                }
                else
                {
                    controlName = control.GetType().FullName;
                }
            }
            else
            {
                controlName = control.GetType().FullName;
            }
            NameValueCollection controlNameValues = (NameValueCollection)ConfigurationSettings.GetConfig("Internal/Controls/Name");
            if (controlNameValues[controlName] != null)
            {
                NameValueCollection AttributeValues = (NameValueCollection)ConfigurationSettings.GetConfig("Internal/Controls/Attribute");
                string attributeKey = AttributeValues.GetKey(0);
                string attributeValue = AttributeValues[attributeKey];
                PropertyInfo property = ControlDesc.GetType().GetProperty(attributeKey);
                object propertyValue = property.GetGetMethod().Invoke(ControlDesc, null);

                MethodInfo methodAttributeValue = ControlDesc.GetType().GetMethod(attributeValue);
                object attributeResult = methodAttributeValue.Invoke(ControlDesc, new object[] { propertyValue });

                if ((bool)attributeResult)
                {
                    NameValueCollection EventNameValues = (NameValueCollection)ConfigurationSettings.GetConfig("Internal/Controls/EventName");
                    string EventNameKey = EventNameValues.GetKey(0);
                    string EventNameValue = EventNameValues[EventNameKey];
                    EventNameValue = EventNameValue + "(\"" + name + "\")";
                    controlProperties.Add(EventNameKey, EventNameValue);
                }
            }
            //EIBControl Settings
            return controlProperties;
        }

        public static List<Hashtable> updateDataMappings(Control control, string elementName, string uniqueId)
        {
            List<Hashtable> mappingProperties = new List<Hashtable>();
            string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
            if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
            {

                IEIBControl eibControl = (IEIBControl)control;
                if (eibControl.DataMappings != null)
                {
                    foreach (DataMapping dataMapping in eibControl.DataMappings)
                    {
                        Hashtable controlProperties = new Hashtable();
                        if (dataMapping.DataPatternName != null && (!dataMapping.DataPatternName.Trim().Equals("")))
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeDataPatternAtt, dataMapping.DataPatternName);
                        }
                        if (dataMapping.DataTableName != null && (!dataMapping.DataTableName.Trim().Equals("")))
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeDataTableAtt, dataMapping.DataTableName);
                        }
                        if (dataMapping.DataFieldName != null && (!dataMapping.DataFieldName.Trim().Equals("")))
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeDataFieldAtt, dataMapping.DataFieldName);
                        }
                        mappingProperties.Add(controlProperties);
                    }
                }
            }
            //EIBControl Settings
            return mappingProperties;
        }

        public static Hashtable updateSettings(Control control, string elementName, string uniqueId)
        {
            #region Updating Common Properties of Control -1
            //Add settings realted to control into hashtable which will later
            //be written in XML
            Hashtable controlProperties = new Hashtable();
            if(control is IEIBControl)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodePositionAtt, ((IEIBControl)control).Position.ToString().ToLower());
            }

            controlProperties.Add(XMLServicesConstants.XmlNodeNameAtt, elementName);
            if (control is EIBTextBox)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBTextBox)control).UniqueId);
            }
            else if (control is EIBButton)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBButton)control).UniqueId);
            }
            else if (control is EIBLabel)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBLabel)control).UniqueId);
            }
            else if (control is EIBCombobox)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBCombobox)control).UniqueId);
            }
            else if (control is EIBApplet)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBApplet)control).UniqueId);
            }
            else if (control is EIBBrowse)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBBrowse)control).UniqueId);
            }
            else if (control is EIBCalender)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBCalender)control).UniqueId);
            }
            else if (control is EIBCheckbox)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBCheckbox)control).UniqueId);
            }
            else if (control is EIBDatePicker)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBDatePicker)control).UniqueId);
            }
            else if (control is EIBGrid)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBGrid)control).UniqueId);
            }
            else if (control is EIBImageBrowse)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBImageBrowse)control).UniqueId);
            }
            else if (control is EIBJasper)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBJasper)control).UniqueId);
            }
            else if (control is EIBLattice)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBLattice)control).UniqueId);
            }
            else if (control is EIBListbox)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBListbox)control).UniqueId);
            }
            //else if (control is EIBMenu)
            //{
            //    controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBMenu)control).UniqueId);
            //}
            else if (control is EIBVMenuBar)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBVMenuBar)control).UniqueId);
            }
            else if (control is EIBPaging)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBPaging)control).UniqueId);
            }
            else if (control is EIBPicture)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBPicture)control).UniqueId);
            }
            else if (control is EIBPlaceHolder)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBPlaceHolder)control).UniqueId);
            }
            else if (control is EIBRadioButton)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBRadioButton)control).UniqueId);
            }
            else if (control is EIBRadioGroup)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBRadioGroup)control).UniqueId);
            }
            else if (control is EIBScrollableRow)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBScrollableRow)control).UniqueId);
            }
            else if (control is EIBSchedular)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBSchedular)control).UniqueId);
            }
            else if (control is EIBSearch)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBSearch)control).UniqueId);
            }
            else if (control is EIBTabControl)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBTabControl)control).UniqueId);
            }
            else if (control is EIBTabPage)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBTabPage)control).UniqueId);
            }
            else if (control is EIBTime)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBTime)control).UniqueId);
            }
            else if (control is EIBTreeView)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBTreeView)control).UniqueId);
            }

            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, uniqueId);
            }

            if (control is ICustomSizableControl)
            {
                ICustomSizableControl iCsControl = (ICustomSizableControl)control;
                string widthStr = iCsControl._Width.ToString();
                /*if (iCsControl.WidthType == WidthMeasurement.Percent)
                {
                    widthStr += "%";
                }
                else
                {
                    widthStr += "px";
                }*/
                controlProperties.Add(XMLServicesConstants.XmlNodeWidthAtt, widthStr);
                controlProperties.Add(XMLServicesConstants.XmlNodeHeightAtt, iCsControl._Height.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeWidthTypeAtt, iCsControl.WidthType.ToString());

                if (control is EIBTabControl)
                {
                    if (((EIBTabControl)control).HeightType != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeHeightTypeAtt, ((EIBTabControl)control).HeightType.ToString());
                    }
                }
            }
            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeWidthAtt, control.Width.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeHeightAtt, control.Height.ToString());
            }
            string htmlColor = "";
            if (control.BackColor == Color.Transparent)
            {
                htmlColor = "Transparent";
            }
            else
            {
                htmlColor = string.Concat("#", (control.BackColor.ToArgb() & 0x00FFFFFF).ToString("X6"));
            }
            if (control is EIBTabControl)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeBackColorAtt, "Transparent");
            }
            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeBackColorAtt, htmlColor);
            }
            controlProperties.Add(XMLServicesConstants.XmlNodeCursorAtt, control.Cursor.ToString());
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
                if (!(control is IEIBControl))
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginBottomAtt, control.Margin.Bottom.ToString());
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginLeftAtt, control.Margin.Left.ToString());
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginRightAtt, control.Margin.Right.ToString());
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginTopAtt, control.Margin.Top.ToString());
                }
                else
                {
                    IEIBControl control1 = (IEIBControl)control;
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginBottomAtt, control1.ControlMargin.Bottom.ToString());
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginLeftAtt, control1.ControlMargin.Left.ToString());
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginRightAtt, control1.ControlMargin.Right.ToString());
                    controlProperties.Add(XMLServicesConstants.XmlNodeMarginTopAtt, control1.ControlMargin.Top.ToString());
                }
            }
            if (!(control is EIBTabPage))
            {
                if (control is ICustomVisible)
                {
                    ICustomVisible ctrlVisible = (ICustomVisible)control;
                    if (!ctrlVisible.getVisibility())
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, ctrlVisible.getVisibility().ToString().ToLower());
                    }
                    else
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, ctrlVisible.getVisibility().ToString().ToLower());
                    }
                }
                else
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, control.Visible.ToString().ToLower());
                }
            }
            //if (control.Parent is EIBTabPage)
            //{
            //    //This is ahack against microsoft limitation on tabpages
            //    //redundant if else block will be reviewed in new .net release
            //    if (control.Parent.Visible.Equals(false))
            //    {
            //        if (!controlProperties.ContainsKey(XMLServicesConstants.XmlNodeVisibleAtt))
            //        controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, true.ToString().ToLower());
            //        /*  if (control.HasChildren)
            //           {
            //               foreach (Control childcontrol in control.Controls  )
            //               {
            //                   controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, true.ToString().ToLower());
            //               }

            //           }*/
            //    }
            //    else
            //    {
            //        if (!controlProperties.ContainsKey(XMLServicesConstants.XmlNodeVisibleAtt))
            //        controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, control.Visible.ToString().ToLower());
            //    }
            //}
            //else if (control.Parent != null)
            //{
            //    if (control.Parent.Parent is EIBTabPage)
            //    {
            //        if (control.Parent.Parent.Visible.Equals(false))
            //        {
            //            if (!controlProperties.ContainsKey(XMLServicesConstants.XmlNodeVisibleAtt))
            //            controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, true.ToString().ToLower());
            //        }
            //    }
            //}

            //// else
            //// {
            //if (!(control.Parent is EIBTabPage))
            //{
            //    if(!controlProperties.ContainsKey(XMLServicesConstants.XmlNodeVisibleAtt))
            //    controlProperties.Add(XMLServicesConstants.XmlNodeVisibleAtt, control.Visible.ToString().ToLower());
            //}
            ////  }
            #endregion
            #region Updating Common Properties of Control -2
            controlProperties.Add(XMLServicesConstants.XmlNodeForeColorAtt, foreColor);
            controlProperties.Add(XMLServicesConstants.XmlNodeEnableAtt, control.Enabled.ToString().ToLower());
            //controlProperties.Add(XMLServicesConstants.XmlNodeNetForeColorAtt, control.ForeColor.ToKnownColor().ToString());
            if (control.ForeColor.IsKnownColor)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeNetForeColorAtt, control.ForeColor.ToKnownColor().ToString());
            }
            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeNetForeColorAtt, foreColor);
            }
            controlProperties.Add(XMLServicesConstants.XmlNodeFontStyleAtt, control.Font.Style.ToString());
            if (control.Parent is BaseWindow)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeXAtt, "0");
                controlProperties.Add(XMLServicesConstants.XmlNodeYAtt, "0");
            }
            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeXAtt, control.Location.X.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeYAtt, control.Location.Y.ToString());
            }
            controlProperties.Add(XMLServicesConstants.XmlNodeTextAtt, control.Text);


            if (control is IContainerControl)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeAlignAtt, ((IContainerControl)control).Align.ToString().ToLower());
            }
            if (control is IEIBControl)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeUseAtt, ((IEIBControl)control).Use);
            }

            if (control.BackColor.IsKnownColor)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeNetBackColorAtt, control.BackColor.ToKnownColor().ToString());
            }
            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeNetBackColorAtt, htmlColor);
            }
            controlProperties.Add(XMLServicesConstants.XmlNodeTabIndexAtt, control.TabIndex.ToString());
            #endregion

            #region Saving Background Image Property
            string BackgroundImage = UpdateBackgroundImage(control, uniqueId);
            if (BackgroundImage != null)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeBackgroundImageAtt, BackgroundImage);
            }

            #endregion

            #region Saving Background Image Layout Property
            string backgroundimagelayout = control.BackgroundImageLayout.ToString().ToLower();
            if (!string.IsNullOrEmpty(backgroundimagelayout))
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeBackgroundImageLayoutAtt, backgroundimagelayout);
            }
            #endregion

            #region Saving Some Specific Property of Control

            #region Saving MenuIcon Property
            if (control is EIBMenuBar || control is EIBVMenuBar)
            {
                string MenuIcon = UpdateMenuIcon((IEIBControl)control, uniqueId);
                if (MenuIcon != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMenuIconAtt, MenuIcon);

                }
            }
            #endregion
            if (control is EIBLabel)
            {
                EIBLabel labelEIB = (EIBLabel)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, labelEIB.BorderStyle.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeTextAlignAtt, labelEIB.TextAlign.ToString().ToLower());
                if (labelEIB.RenderCursor.ToString() != null)
                { controlProperties.Add(XMLServicesConstants.XmlNodeRenderCursorAtt, labelEIB.RenderCursor.ToString().ToLower()); }

                if (labelEIB.Multiline.ToString() != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMultilineLabelAtt, labelEIB.Multiline.ToString().ToLower());
                }
                //controlProperties.Add(XMLServicesConstants.XmlNodeFontStyleAtt, labelEIB.Font.Style.ToString().ToLower());
            }
            if (control is EIBRadioGroup)
            {
                EIBRadioGroup labelEIB = (EIBRadioGroup)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, labelEIB.BorderStyle.ToString().ToLower());
            }

            if (control is EIBListbox)
            {
                EIBListbox listBoxEIB = (EIBListbox)control;
                if (listBoxEIB.SelectionMode != null)
                //controlProperties.Add(XMLServicesConstants.XmlNodeSelectionModeAtt, listBoxEIB.SelectionMode.ToString().ToLower());
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeCustomSelectionModeAtt, listBoxEIB.SelectionMode.ToString().ToLower());
                }
            }
            if (control is EIBDatePicker)
            {
                EIBDatePicker datePicker = (EIBDatePicker)control;
                if (datePicker.CustomFormatType != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeDateFormatAtt, datePicker.CustomFormatType.ToString());
                }
                //if (datePicker.format != null)
                //{
                //    controlProperties.Add(XMLServicesConstants.XmlNodenewDateFormatAtt, datePicker.format.ToString());
                //}

            }

            if (control is EIBTime)
            {
                EIBTime time = (EIBTime)control;
                if (time != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeTimeAtt, time.Time.ToString());
                }

            }

            if (control is EIBSchedular)
            {
                EIBSchedular schedular = (EIBSchedular)control;
                if (schedular != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeFirstDayOfWeekAtt, schedular.FirstDayOfWeek);
                    controlProperties.Add(XMLServicesConstants.XmlNodeMoldAtt, schedular.Mold);
                    controlProperties.Add(XMLServicesConstants.XmlNodeTimeZoneAtt, schedular.TimeZone);
                }

            }
            if (control is EIBApplet)
            {
                EIBApplet applet = (EIBApplet)control;
                if (applet != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeArchiveAtt, "applet/" + applet.Archive);
                    controlProperties.Add(XMLServicesConstants.XmlNodeCodeAtt, applet.Code);
                    controlProperties.Add(XMLServicesConstants.XmlNodeAppNameAtt, applet.AppName);
                }

            }
            ///Column
            if (control is EIBColumn)
            {
                EIBColumn column = (EIBColumn)control;
                if (column != null)
                {
                    if (column.Sortable)
                        controlProperties.Add(XMLServicesConstants.XmlNodeSortableAtt, "auto");
                }
                if (column.SortAscending != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeSortAscendingAtt, column.SortAscending);
                }
                if (column.SortDescending != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeSortDescendingAtt, column.SortDescending);
                }

            }


            ////listbox
            if (control is EIBListbox)
            {
                EIBListbox listbox = (EIBListbox)control;
                if (listbox != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMultiSelectAtt, listbox.MultiSelect.ToString().ToLower());
                }
                if (listbox.Checkmark.ToString() != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeCheckmarkAtt, listbox.Checkmark.ToString().ToLower());
                }

            }

            if (control is EIBCombobox)
            {
                EIBCombobox comboboxEIB = (EIBCombobox)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeReadOnlyAtt, comboboxEIB.ReadOnly.ToString().ToLower());
            }
            if (control is EIBPaging)
            {
                EIBPaging pagingEIB = (EIBPaging)control;
                controlProperties.Add(XMLServicesConstants.XmlNodePageSizeAtt, pagingEIB.PageSize.ToString());
            }

            if (control is EIBTextBox)
            {
                EIBTextBox textBoxEIB = (EIBTextBox)control;
                string password = textBoxEIB.PasswordChar.ToString().ToLower();
                bool isPassword = true;
                if (textBoxEIB.PasswordChar == '\0')
                {
                    password = "";
                    isPassword = false;
                }
                controlProperties.Add(XMLServicesConstants.XmlNodePasswordCharAtt, password);
                controlProperties.Add(XMLServicesConstants.XmlNodePasswordAtt, isPassword.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeMultiLineAtt, textBoxEIB.Multiline.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeReadOnlyAtt, textBoxEIB.ReadOnly.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeValueTypeAtt, textBoxEIB.ValueType);
                controlProperties.Add(XMLServicesConstants.XmlNodeMaxLengthAtt, textBoxEIB.MaxLength.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, textBoxEIB.BorderStyle.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeSearchFieldAtt, textBoxEIB.SearchField.ToString().ToLower());

                if (textBoxEIB.MinLength.ToString() != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMinLengthAtt, textBoxEIB.MinLength.ToString().ToLower()); 
                }
                if (textBoxEIB.Style != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeStyleAtt, textBoxEIB.Style);
                }
                if (textBoxEIB.BorderSize.ToString() != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeBorderSizeAtt, textBoxEIB.BorderSize.ToString());
                }
                if (textBoxEIB.BorderColor != null)
                {

                    string htmlColour = "";
                    if (textBoxEIB.BorderColor == Color.Transparent)
                    {
                        htmlColour = "Transparent";
                    }
                    else
                    {
                        htmlColour = string.Concat("#", (textBoxEIB.BorderColor.ToArgb() & 0x00FFFFFF).ToString("X6"));
                    }
                    controlProperties.Add(XMLServicesConstants.XmlNodeBorderColorAtt, htmlColour);
                }
                //if (textBoxEIB.Constraint != null)
                //{
                //    controlProperties.Add(XMLServicesConstants.XmlNodeConstraintAtt, textBoxEIB.Constraint.ToString().ToLower());
                //}
    

            }
            if (control is EIBVMenuBar)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeOrientAtt, ((EIBVMenuBar)control).Orient.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeAutoSizeAtt, ((EIBVMenuBar)control).AutoSize.ToString().ToLower());
            }
            //if (control is EIBMenuBar)
            //{
            //    controlProperties.Add(XMLServicesConstants.XmlNodeOrientAtt, ((EIBMenuBar)control).Orient);
            //}

            if (control is EIBPlaceHolder)
            {
                EIBPlaceHolder placeholderEIB = (EIBPlaceHolder)control;
                //Image pictureImage = panelEIB.BackgroundImage;
                //if (pictureImage != null)
                //{
                //    string sourceImage = "\\img\\" + panelEIB.ControlName + ".jpg";
                //    String curDir = Directory.GetCurrentDirectory();
                //    if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                //    {
                //        Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                //    }
                //    try
                //    {
                //        pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                //    }
                //    catch (Exception)
                //    {
                //        //MessageBox.Show(ex.Message[0].ToString());
                //        //do nothing
                //    }
                //    controlProperties.Add(XMLServicesConstants.XmlNodeBackgroundImageAtt, sourceImage);
                //    Directory.SetCurrentDirectory(curDir);
                //}
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, placeholderEIB.BorderStyle.ToString().ToLower());
                foreach (Control childControl in placeholderEIB.Controls)
                {
                    if (childControl is EIBItem)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeItemreferenceAtt, ((EIBItem)childControl).Text);
                        break;
                    }
                }
            }

            if (control is EIBPanel)
            {
                EIBPanel panelEIB = (EIBPanel)control;
                //Image pictureImage = panelEIB.BackgroundImage;
                //if (pictureImage != null)
                //{
                //    string sourceImage = "\\img\\" + panelEIB.ControlName + ".jpg";
                //    String curDir = Directory.GetCurrentDirectory();
                //    if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                //    {
                //        Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                //    }
                //    try
                //    {
                //        pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                //    }
                //    catch (Exception)
                //    {
                //        //MessageBox.Show(ex.Message[0].ToString());
                //        //do nothing
                //    }
                //    controlProperties.Add(XMLServicesConstants.XmlNodeBackgroundImageAtt, sourceImage);
                //    Directory.SetCurrentDirectory(curDir);
                //}
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, panelEIB.BorderStyle.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeDefaultAtt, panelEIB.DefaultScreen.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeBoxAlignmentAtt, panelEIB.BoxAlignment.ToString().ToLower());
                if (panelEIB.Parent is BaseWindow)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeTitleAtt, panelEIB.Title);
                    controlProperties.Add(XMLServicesConstants.XmlNodePopupAtt, panelEIB.Popup.ToString().ToLower());
                }


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
            if (control is EIBGrid)
            {

                EIBGrid gridControl = (EIBGrid)control;
                if (gridControl != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMultiSelectAtt, gridControl.MultiSelect.ToString().ToLower());
                    controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, gridControl.BorderStyle.ToString().ToLower());
                    if (gridControl.SearchSet != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeSearchAtt, gridControl.SearchSet.ToLower());
                    }
                    if (gridControl.Paginal != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodePaginalAtt, gridControl.Paginal);
                    }
                    if (gridControl.borderSize.ToString() != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeBorderSizeAtt, gridControl.BorderSize.ToString());
                    }
                    if (gridControl.BorderColor != null)
                    {

                        string htmlColour = "";
                        if (gridControl.BorderColor == Color.Transparent)
                        {
                            htmlColour = "Transparent";
                        }
                        else
                        {
                            htmlColour = string.Concat("#", (gridControl.BorderColor.ToArgb() & 0x00FFFFFF).ToString("X6"));
                        }
                        controlProperties.Add(XMLServicesConstants.XmlNodeBorderColorAtt, htmlColour);
                    }
                }
            }
            if (control is EIBLattice)
            {

                EIBLattice latticeControl = (EIBLattice)control;
                if (latticeControl != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeMultiSelectAtt, latticeControl.MultiSelect.ToString().ToLower());
                    controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, latticeControl.BorderStyle.ToString().ToLower());
                    if (latticeControl.SearchSet != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeSearchAtt, latticeControl.SearchSet.ToLower());
                    }
                    if (latticeControl.Paginal != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodePaginalAtt, latticeControl.Paginal);
                    }
                }
            }
            if (control is EIBScrollableRow)
            {
                EIBScrollableRow row = (EIBScrollableRow)control;
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderAtt, row.BorderStyle.ToString().ToLower());
            }
            if (control is EIBListbox)
            {
                EIBListbox listBoxControl = (EIBListbox)control;
                if (listBoxControl.Paginal != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodePaginalAtt, listBoxControl.Paginal);
                }
            }
            if (control is EIBButton)
            {
                EIBButton button = (EIBButton)control;
                if (button.UseDefaultColor.ToString() != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeUseDefaultColorAtt, button.UseDefaultColor.ToString().ToLower());
                }
                Image pictureImage = button.Image;
                if (pictureImage != null)
                {
                    string sourceImage = "\\img\\" + button.Name + ".jpg";
                    String curDir = Directory.GetCurrentDirectory();
                    if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                    {
                        Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                    }
                    try
                    {
                        pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                    }
                    catch (Exception)
                    {
                        //MessageBox.Show(ex.Message[0].ToString());
                        //do nothing
                    }
                    controlProperties.Add(XMLServicesConstants.XmlNodeSourceAtt, sourceImage);
                    Directory.SetCurrentDirectory(curDir);
                }


                controlProperties.Add(XMLServicesConstants.XmlNodeFlatStyleAtt, button.FlatStyle.ToString().ToLower());
                controlProperties.Add(XMLServicesConstants.XmlNodeBorderSizeAtt, button.FlatAppearance.BorderSize.ToString());
                controlProperties.Add(XMLServicesConstants.XmlNodeTextAlignAtt, button.TextAlign.ToString().ToLower());

                if (button.RenderCursor.ToString() != null)
                { controlProperties.Add(XMLServicesConstants.XmlNodeRenderCursorAtt, button.RenderCursor.ToString().ToLower()); }
                
				if (button.RenderTextWithImage.ToString() != null)
                { controlProperties.Add(XMLServicesConstants.XmlNodeRenderTextWithImgAtt, button.RenderTextWithImage.ToString().ToLower()); }
            }
            if (control is EIBPanel)
            {
                EIBPanel panel = (EIBPanel)control;
                String srcFilePath = panel.Source;
                if (srcFilePath != null && srcFilePath.Length > 0)
                {
                    if (!srcFilePath.StartsWith("\\content\\"))
                    {
                        if (!Directory.Exists(EIBXMLUtilities.folderName + "\\content\\"))
                        {
                            Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\content\\");
                        }
                        try
                        {
                            File.Copy(srcFilePath, EIBXMLUtilities.folderName + "\\content\\" + panel.ControlName + "_" + Path.GetFileName(srcFilePath));
                        }
                        catch (Exception ex)
                        {
                            MessageBox.Show("Error-Save: " + ex.Message + "\r\n" + "FineName: " + Path.GetFileName(srcFilePath) + "" +"already exists for another frame");
                        }

                        srcFilePath = "\\content\\" + panel.ControlName + "_" +Path.GetFileName(srcFilePath);
                    }
                    if (panel.Source != null)
                    { controlProperties.Add(XMLServicesConstants.XmlNodeSourceAtt, srcFilePath); }
                }

            }

                if (control is EIBPicture)
                {
                    EIBPicture picture = (EIBPicture)control;
                    Image pictureImage = picture.Image;
                    bool isChanged = true;

                    if (pictureImage != null)
                    {
                        try
                        {
                            //int[] aPropertyIdList = pictureImage.PropertyIdList;
                            if (pictureImage.PropertyIdList.Length == 0)
                            {
                                isChanged = false;
                            }
                        }
                        catch
                        {
                            isChanged = false;
                        }
                        string sourceImage = "\\img\\" + picture.ControlName + ".jpg";
                        String curDir = Directory.GetCurrentDirectory();
                        if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                        {
                            Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                        }
                        try
                        {
                            if (isChanged)
                            {
                                pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                            }
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

                if (control is EIBImageBrowse)
                {
                    EIBImageBrowse imageBrowse = (EIBImageBrowse)control;
                    Image pictureImage = imageBrowse.Image;
                    bool isChanged = true;
                    try
                    {
                        if (pictureImage != null)
                        {
                            int[] aPropertyIdList = pictureImage.PropertyIdList;
                        }
                    }
                    catch
                    {
                        isChanged = false;
                    }

                    if (pictureImage != null)
                    {
                        string sourceImage = "\\img\\" + imageBrowse.ControlName + ".jpg";
                        String curDir = Directory.GetCurrentDirectory();
                        if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                        {
                            Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                        }
                        try
                        {
                            if (isChanged)
                            {
                                pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                            }
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
            #endregion

                #region Saving VisibleTo Property
                if (control is IEIBControl)
                {
                    string visibleToValue = "";
                    foreach (string item in ((IEIBControl)control).VisibleTo)
                    {
                        visibleToValue += item + ",";
                    }
                    if (visibleToValue.Contains(","))
                    {
                        visibleToValue = visibleToValue.Remove(visibleToValue.Length - 1);
                    }
                    controlProperties.Add(XMLServicesConstants.XmlNodeVisibleToAtt, visibleToValue);
                }
                #endregion

                #region Saving some more Properties
                string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
                if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
                {
                    IEIBControl eibControl = (IEIBControl)control;
                    if (eibControl.IsMandatory != null && (!eibControl.IsMandatory.Trim().Equals("")))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeIsMandatoryAtt, eibControl.IsMandatory);
                    }
                    if (eibControl.IsForm != null && (!eibControl.IsForm.Trim().Equals("")))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeIsFormAtt, eibControl.IsForm);
                    }
                    if (eibControl.Draggable != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeDraggableAtt, eibControl.Draggable.ToString().ToLower());
                    }

                    if (eibControl.Droppable != null)
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeDroppableAtt, eibControl.Droppable.ToString().ToLower());
                    }

                    if (eibControl is EIBPanel)
                    {
                        if (((EIBPanel)eibControl).Style != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeStyleAtt, ((EIBPanel)eibControl).Style);
                        }
                        if (((EIBPanel)eibControl).RenderAsIFrame.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeRenderAsIFrameAtt, ((EIBPanel)eibControl).RenderAsIFrame.ToString().ToLower());
                        }

                        if (((EIBPanel)eibControl).AutoScroll.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeAutoScroll, ((EIBPanel)eibControl).AutoScroll.ToString().ToLower());
                        }

                    }
                    if (eibControl is EIBGrid)
                    {
                        if (((EIBGrid)eibControl).UseDefaultColor.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeUseDefaultColorAtt, ((EIBGrid)eibControl).UseDefaultColor.ToString().ToLower());
                        }

                        if (((EIBGrid)eibControl).Checkmark.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeCheckmarkAtt, ((EIBGrid)eibControl).Checkmark.ToString().ToLower());
                        }
                        if (((EIBGrid)eibControl).GridType.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeGridTypeAtt, ((EIBGrid)eibControl).GridType.ToString());
                        }
                        if (((EIBGrid)eibControl).AutoScroll.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeAutoScroll, ((EIBGrid)eibControl).AutoScroll.ToString());
                        }

                    }
                    if (eibControl is EIBCombobox)
                    {
                        if (((EIBCombobox)eibControl).Mold.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeRoundedMoldAtt, ((EIBCombobox)eibControl).Mold.ToString());
                        }
                    }
                    if (eibControl is EIBTextBox)
                    {
                        if (((EIBTextBox)eibControl).Mold.ToString() != null)
                        {
                            controlProperties.Add(XMLServicesConstants.XmlNodeRoundedMoldAtt, ((EIBTextBox)eibControl).Mold.ToString());
                        }
                    }
                
                
                #endregion
            }

            return controlProperties;
        }

        public static void visibility(Control ctrl)
        {

        }
        public static string UpdateBackgroundImage(Control control, string uniqueId)
        {
            Image pictureImage = null;
            if (control is EIBLabel)
            {
                pictureImage = ((EIBLabel)control).Image;
            }
            else
            {
                pictureImage = control.BackgroundImage;
            }
            bool isChanged = true;
            try
            {
                if (pictureImage != null)
                {
                    int[] aPropertyIdList = pictureImage.PropertyIdList;
                }
            }
            catch
            {
                isChanged = false;
            }

            if (pictureImage != null)
            {
                string sourceImage = "\\img\\" + ((IEIBControl)control).ControlName + ".jpg";
                String curDir = Directory.GetCurrentDirectory();
                if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                {
                    Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                }
                try
                {
                    if (isChanged)
                    {
                        pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                    }
                }
                catch (Exception)
                {
                    //MessageBox.Show(ex.Message[0].ToString());
                    //do nothing
                }
                return sourceImage;
            }
            /*Image pictureImage = control.BackgroundImage;
            if (pictureImage != null)
            {
                IEIBControl eibcontrol = (IEIBControl)control;
                string sourceImage = "\\img\\" + eibcontrol.ControlName + ".jpg";
                String curDir = Directory.GetCurrentDirectory();
                if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                {
                    Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                }
                try
                {
                    pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
                }
                catch (Exception)
                {
                    //MessageBox.Show(ex.Message[0].ToString());
                    //do nothing
                }
                Directory.SetCurrentDirectory(curDir);
                return sourceImage;
            }*/
            return null;

        }
        
        //public static string UpdateLabelImage(EIBLabel control, string uniqueId)
        //{
        //    Image pictureImage = ((EIBLabel)control).Image ;
        //    bool isChanged = true;
        //    try
        //    {
        //        if (pictureImage != null)
        //        {
        //            int[] aPropertyIdList = pictureImage.PropertyIdList;
        //        }
        //    }
        //    catch
        //    {
        //        isChanged = false;
        //    }

        //    if (pictureImage != null)
        //    {
        //        string sourceImage = "\\img\\" + ((EIBLabel)control).ControlName + ".jpg";
        //        String curDir = Directory.GetCurrentDirectory();
        //        if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
        //        {
        //            Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
        //        }
        //        try
        //        {
        //            if (isChanged)
        //            {
        //                pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, System.Drawing.Imaging.ImageFormat.Jpeg);
        //            }
        //        }
        //        catch (Exception)
        //        {
        //            //MessageBox.Show(ex.Message[0].ToString());
        //            //do nothing
        //        }
        //        return sourceImage;
        //    }
           
        //    return null;

        //}

        public static string UpdateMenuIcon(IEIBControl control, string uniqueId)
        {
            Image pictureImage = null;
            string sourceImage = null;
            if (control is EIBMenuBar)
            {
                pictureImage = ((EIBMenuBar)control).MenuIcon;
                sourceImage = "\\img\\" + ((EIBMenuBar)control).UniqueId + ".jpg";
            }
            else if (control is EIBVMenuBar)
            {
                pictureImage = ((EIBVMenuBar)control).MenuIcon;
                sourceImage = "\\img\\" + ((EIBVMenuBar)control).UniqueId + ".jpg";
            }
            else if (control is EIBMenu)
            {
                pictureImage = ((EIBMenu)control).MenuIcon;
                sourceImage = "\\img\\" + ((EIBMenu)control).UniqueId + ".jpg";
            }
            else if (control is EIBMenuItem)
            {
                pictureImage = ((EIBMenuItem)control).MenuIcon;
                sourceImage = "\\img\\" + ((EIBMenuItem)control).UniqueId + ".jpg";
            }

            bool isChanged = true;
            try
            {
                if (pictureImage != null)
                {
                    int[] aPropertyIdList = pictureImage.PropertyIdList;
                }
            }
            catch
            {
                isChanged = false;
            }

            if (sourceImage != null && pictureImage != null)
            {
                //string sourceImage = "\\img\\" + ((IEIBControl)control).ControlName + ".jpg";
                String curDir = Directory.GetCurrentDirectory();
                if (!Directory.Exists(EIBXMLUtilities.folderName + "\\img\\"))
                {
                    Directory.CreateDirectory(EIBXMLUtilities.folderName + "\\img\\");
                }
                try
                {
                    if (isChanged)
                    {
                        pictureImage.Save(EIBXMLUtilities.folderName + sourceImage, pictureImage.RawFormat);
                    }
                }
                catch (Exception)
                {
                }
                return sourceImage;
            }
            return null;

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
                if (eibControl is EIBPanel)
                {
                    if (((EIBPanel)eibControl).GlobalScripts != null && !((EIBPanel)eibControl).GlobalScripts.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeGlobalScriptsElt, ((EIBPanel)eibControl).GlobalScripts);
                    }
                }
                if (eibControl is EIBTreeNode)
                {
                    if (((EIBTreeNode)eibControl).OnOpen != null && !((EIBTreeNode)eibControl).OnOpen.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnOpenElt, ((EIBTreeNode)eibControl).OnOpen);
                    }
                }
                if (eibControl is EIBTreeView)
                {
                    if (((EIBTreeView)eibControl).OnSelect != null && !((EIBTreeView)eibControl).OnSelect.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnSelectElt, ((EIBTreeView)eibControl).OnSelect);
                    }
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
                if (eibControl is EIBPanel)
                {
                    if (((EIBPanel)eibControl).GlobalScripts != null && !((EIBPanel)eibControl).GlobalScripts.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeGlobalScriptsElt, ((EIBPanel)eibControl).GlobalScripts);
                    }
                }
                if (eibControl is EIBTreeNode)
                {
                    if (((EIBTreeNode)eibControl).OnOpen != null && !((EIBTreeNode)eibControl).OnOpen.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnOpenElt, ((EIBTreeNode)eibControl).OnOpen);
                    }
                }
                if (eibControl is EIBTreeView)
                {
                    if (((EIBTreeView)eibControl).OnSelect != null && !((EIBTreeView)eibControl).OnSelect.Trim().Equals(""))
                    {
                        controlProperties.Add(XMLServicesConstants.XmlNodeOnSelectElt, ((EIBTreeView)eibControl).OnSelect);
                    }
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
            controlProperties.Add(XMLServicesConstants.XmlNodeFontAtt, control.Font.Name);
            controlProperties.Add(XMLServicesConstants.XmlNodeFontSizeAtt, control.Font.Size.ToString());
            controlProperties.Add(XMLServicesConstants.XmlNodeFontStyleAtt, control.Font.Style.ToString());
            if (control is EIBMenu)
            {
                if (((EIBMenu)control).RenderAsMenuItem != null)
                {
                    controlProperties.Add(XMLServicesConstants.XmlNodeRenderAsMenuItemAtt, ((EIBMenu)control).RenderAsMenuItem.ToString().ToLower());
                }
            }
            //controlProperties.Add(XMLServicesConstants.XmlNodeAllignAtt,control.Alignment,ToolStripItemAlignment.
          //  controlProperties.Add(XMLServicesConstants.XmlNodeIdAtt, ((EIBVMenuBar)control).UniqueId);

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
            string foreColor;
            if (control.ForeColor == Color.Transparent)
            {
                foreColor = "Transparent";
            }
            else
            {
                foreColor = string.Concat("#", (control.ForeColor.ToArgb() & 0x00FFFFFF).ToString("X6"));
            }
            controlProperties.Add(XMLServicesConstants.XmlNodeForeColorAtt, foreColor);
            if (control.ForeColor.IsKnownColor)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeNetForeColorAtt, control.ForeColor.ToKnownColor().ToString());
            }
            else
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeNetForeColorAtt, foreColor);
            }
            controlProperties.Add("text", control.Text);
            controlProperties.Add(XMLServicesConstants.XmlNodeTextAlignAtt, control.TextAlign.ToString().ToLower());

            string MenuIcon = UpdateMenuIcon((IEIBControl)control, uniqueId);
            if (MenuIcon != null)
            {
                controlProperties.Add(XMLServicesConstants.XmlNodeMenuIconAtt, MenuIcon);
            }

            #region Saving VisibleTo Property
            if (control is IEIBControl)
            {
                string visibleToValue = "";
                foreach (string item in ((IEIBControl)control).VisibleTo)
                {
                    visibleToValue += item + ",";
                }
                if (visibleToValue.Contains(","))
                {
                    visibleToValue = visibleToValue.Remove(visibleToValue.Length - 1);
                }
                controlProperties.Add(XMLServicesConstants.XmlNodeVisibleToAtt, visibleToValue);
            }
            #endregion
            return controlProperties;
        }

        public static Dictionary<string, string> readXMLConfiguration(string path, string filename)
        {
            Dictionary<string, string> configDictionary = new Dictionary<string, string>();
            /* YS check if this is dead code
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
            }*/

            return configDictionary;
        }

        public static void saveXMLofDatePicker(Control datePickerControl)
        {
            EIBDatePicker currentDatePicker = (EIBDatePicker)datePickerControl;
        }
        public static void saveXMLofTimeControl(Control timeControl)
        {
            EIBTime currentTimeControl = (EIBTime)timeControl;
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
                controlProperties.Add(XMLServicesConstants.XmlNodeTextAtt, itemValue);
                controlProperties.Add(XMLServicesConstants.XmlNodeValueAtt, itemValue);
                controlProperties.Add(XMLServicesConstants.XmlNodeNameAtt, itemValue);
                XmlNode xmlNode = XMLServices.writeElement(currentComboBox.ParentXmlNode, "option", controlProperties, null, null);
                counter++;
            }
        }


        public static void saveXMLofMenu(Control menuControl)
        {
            if (menuControl is EIBMenuBar)
            {
                EIBMenuBar menuBar = (EIBMenuBar)menuControl;
                foreach (EIBMenu menu in menuBar.CurrentToolStrip.Items)
                {
                    Hashtable controlProperties = XMLServices.updateMenuSettings(menu, menu.Name, menu.Name);
                    Hashtable eventProperties = XMLServices.updateMenuEvents(menu, menu.Name.ToLower(), menu.Name.ToLower());

                    XmlNode xmlNode = XMLServices.writeElement(((IEIBControl)menuControl).ParentXmlNode, EIBControlList.GetRenderingElement(menu.GetType().Name), controlProperties, eventProperties, null);
                    saveXMLofSubMenus(menu, xmlNode);


                    //foreach (EIBMenuItem menuItem in menu.DropDownItems)
                    //{
                    //    Hashtable menuItemProperties = XMLServices.updateMenuSettings(menuItem, menuItem.Name, menuItem.Name);
                    //    Hashtable menuEventProperties = XMLServices.updateMenuEvents(menuItem, menuItem.Name.ToLower(), menuItem.Name.ToLower());

                    //    XMLServices.writeElement(xmlNode, EIBControlList.GetRenderingElement(menuItem.GetType().Name), menuItemProperties, menuEventProperties);

                    //}
                }
            }
            else
            {
                EIBVMenuBar menuBar = (EIBVMenuBar)menuControl;
                foreach (EIBMenu menu in menuBar.Items)
                {
                    Hashtable controlProperties = XMLServices.updateMenuSettings(menu, menu.ControlName, menu.UniqueId);
                    Hashtable eventProperties = XMLServices.updateMenuEvents(menu, menu.Name.ToLower(), menu.Name.ToLower());

                    XmlNode xmlNode = XMLServices.writeElement(((IEIBControl)menuControl).ParentXmlNode, EIBControlList.GetRenderingElement(menu.GetType().Name), controlProperties, eventProperties, null);
                    saveXMLofSubMenus(menu, xmlNode);
                }
            }
        }

        public static void saveXMLofSubMenus(EIBMenu menu, XmlNode xmlNode)
        {
            foreach (IEIBControl ctrlmenu in menu.DropDownItems)
            {
                if (ctrlmenu is EIBMenu)
                {
                    EIBMenu tmenu = (EIBMenu)ctrlmenu;
                    Hashtable menuItemProperties = XMLServices.updateMenuSettings(tmenu, tmenu.ControlName, tmenu.UniqueId);
                    Hashtable menuEventProperties = XMLServices.updateMenuEvents(tmenu, tmenu.Name.ToLower(), tmenu.Name.ToLower());

                    XmlNode pxmlNode = XMLServices.writeElement(xmlNode, EIBControlList.GetRenderingElement(tmenu.GetType().Name), menuItemProperties, menuEventProperties, null);
                    if (((ToolStripMenuItem)ctrlmenu).DropDownItems.Count > 0)
                    {
                        saveXMLofSubMenus((EIBMenu)ctrlmenu, pxmlNode);
                    }
                }
                else if (ctrlmenu is EIBMenuItem)
                {
                    EIBMenuItem tmenu = (EIBMenuItem)ctrlmenu;
                    Hashtable menuItemProperties = XMLServices.updateMenuSettings(tmenu, tmenu.ControlName, tmenu.UniqueId);
                    Hashtable menuEventProperties = XMLServices.updateMenuEvents(tmenu, tmenu.Name.ToLower(), tmenu.Name.ToLower());

                    XMLServices.writeElement(xmlNode, EIBControlList.GetRenderingElement(tmenu.GetType().Name), menuItemProperties, menuEventProperties, null);
                    //saveXMLofSubMenus((EIBMenu)ctrlmenu);
                }

            }
        }
        public static void saveXMLofList(Control listControl)
        {
            if (listControl != null)
            {
                EIBListbox listBoxEIB = (EIBListbox)listControl;
                int countList = listBoxEIB.Items.Count;
                for (int counterList = 0; counterList < countList; counterList++)
                {
                    Hashtable controlProperties = new Hashtable();
                    controlProperties.Add(XMLServicesConstants.XmlNodeListItemNameAtt, listBoxEIB.Items[counterList].ToString());
                    if (listBoxEIB.SelectionMode != null)
                    {
                        if (listBoxEIB.SelectionMode.ToString() == "None")
                        {
                            if (counterList == listBoxEIB.SelectedIndex)
                            {
                                controlProperties.Add("selected", true.ToString().ToLower());
                            }
                        }
                        else
                        {
                            if (listBoxEIB.GetSelected(counterList) == true)
                            {
                                controlProperties.Add("selected", true.ToString().ToLower());
                            }
                        }
                    }
                    else
                    {
                        if (counterList == listBoxEIB.SelectedIndex)
                        {
                            controlProperties.Add("selected", true.ToString().ToLower());
                        }
                    }

                    controlProperties.Add(XMLServicesConstants.XmlNodeNameAtt, listBoxEIB.Items[counterList].ToString());
                    XmlNode xmlNode = XMLServices.writeElement(listBoxEIB.ParentXmlNode, "listitem", controlProperties, null, null);
                }
            }
        }
        public static void saveXMLofTreeView(Control treeViewControl, TreeNode treeNode)
        {
            if (treeViewControl != null)
            {
                try
                {
                    EIBTreeView treeView = (EIBTreeView)treeViewControl;
                    foreach (EIBTreeNode treeNodeChild in treeView.Nodes)
                    {
                        Hashtable controlProperties = new Hashtable();
                        controlProperties.Add("name", treeNodeChild.Name);
                        controlProperties.Add("text", treeNodeChild.Text);
                        controlProperties.Add("id", treeNodeChild.UniqueId);
                        if (treeNodeChild.Nodes.Count == 0)
                        {
                            controlProperties.Add("isleaf", true.ToString().ToLower());
                        }
                        Hashtable evntProperties = updateTreeNodeEvents(treeNodeChild, treeNodeChild.Name, treeNodeChild.Name);
                        XmlNode xmlNode = XMLServices.writeElement(treeView.ParentXmlNode, EIBControlList.GetRenderingElement(treeNodeChild.GetType().Name), controlProperties, evntProperties, null);
                        treeNodeChild.ParentXmlNode = xmlNode;
                        saveXMLofTreeView(null, treeNodeChild);
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("To add nodes please drag and drop from toolBoxWindow ");
                }
            }
            else if (treeNode != null)
            {
                EIBTreeNode treeNodeParent = (EIBTreeNode)treeNode;
                foreach (EIBTreeNode treeNodeChild in treeNodeParent.Nodes)
                {

                    Hashtable controlProperties = new Hashtable();
                    controlProperties.Add("name", treeNodeChild.Name);
                    controlProperties.Add("text", treeNodeChild.Text);
                    controlProperties.Add("id", treeNodeChild.UniqueId);
                    if (treeNodeChild.Nodes.Count == 0)
                    {
                        controlProperties.Add("isleaf", true.ToString().ToLower());
                    }
                    Hashtable evntProperties = updateTreeNodeEvents(treeNodeChild, treeNodeChild.Name, treeNodeChild.Name);
                    XmlNode xmlNode = XMLServices.writeElement(treeNodeParent.ParentXmlNode, EIBControlList.GetRenderingElement(treeNodeChild.GetType().Name), controlProperties, evntProperties, null);
                    treeNodeChild.ParentXmlNode = xmlNode;
                    saveXMLofTreeView(null, treeNodeChild);
                }

            }
        }
        public static void saveXMLOfSearch(EIBSearch search)
        {
            XmlNode xmlNode = null;
            SearchDesigner searchDesigner = search.SearchDesign;
            Hashtable controlProperties = new Hashtable();
            controlProperties.Add(XMLServicesConstants.XmlNodeNameAtt, search.ControlName);
            xmlNode = XMLServices.writeElement(((IEIBControl)search).ParentXmlNode, search.ControlType, controlProperties, null, null);

            if (searchDesigner != null)
            {
                foreach (Control control in searchDesigner.Controls)
                {
                    if (control is SplitContainer)
                    {
                        foreach (Control childControl in control.Controls)
                        {
                            if (childControl.Controls[0] is Panel)
                            {
                                foreach (Control panelControl in childControl.Controls[0].Controls)
                                {
                                    if (panelControl is TableLayoutPanel)
                                    {
                                        foreach (Control tableControl in panelControl.Controls)
                                        {
                                            if (tableControl is SearchUserControl)
                                            {
                                                SearchUserControl searchControl = (SearchUserControl)tableControl;
                                                Hashtable queryProperties = new Hashtable();
                                                queryProperties.Add("FieldName", searchControl.fieldList.SelectedItem);
                                                queryProperties.Add("Condition", searchControl.conditionList.SelectedItem);
                                                queryProperties.Add("FieldValue", searchControl.fieldValue.Text);
                                                queryProperties.Add("Operator", searchControl.operatorComboBox.SelectedItem);
                                                if (searchControl.fieldList.SelectedItem != null && searchControl.fieldList.SelectedItem.ToString().Trim() != "")
                                                {
                                                    IEIBControl eibControl = searchControl.GetIEIBControl(searchControl.fieldList.SelectedItem.ToString());
                                                    /*queryProperties.Add("datafield", eibControl.DataFieldName);
                                                    queryProperties.Add("datatable", eibControl.DataTableName);
                                                    queryProperties.Add("datapattern", eibControl.DataPatternName);*/
                                                }
                                                XMLServices.writeElement(xmlNode, searchControl.ControlType, queryProperties, null, null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        public static void saveXMLofTable(EIBGrid tableControl)
        {

            int rowCount = tableControl.Rows.Count;
            XmlNode xmlNode = null;
            for (int rowCounter = 0; rowCounter < rowCount; rowCounter++)
            {
                EIBScrollableRow row = tableControl.Rows[rowCounter];
                if (row != null)
                {
                    int columnCount = row.Columns.Count;
                    Hashtable controlProperties = XMLServices.updateSettings(row, row.ControlName, row.Name.ToLower());
                    // controlProperties.Add(XMLServicesConstants.XmlNodeSizeTypeAtt, row.HeightTyp.ToString());
                    xmlNode = XMLServices.writeElement(((IEIBControl)tableControl).ParentXmlNode, row.ControlType, controlProperties, null, null);
                    row.ParentXmlNode = xmlNode;
                    for (int colCounter = 0; colCounter < columnCount; colCounter++)
                    {
                        XmlNode columnNode = null;
                        EIBColumn column = (EIBColumn)row.Columns[colCounter];
                        Hashtable columnProperties = XMLServices.updateSettings(column, column.ControlName, column.Name.ToLower());
                        columnProperties.Add(XMLServicesConstants.XmlNodeSizeTypeAtt, column.WidthType.ToString());
                        columnProperties[XMLServicesConstants.XmlNodeVisibleAtt] = true.ToString().ToLower();
                        columnNode = XMLServices.writeElement(((IEIBControl)row).ParentXmlNode, column.ControlType, columnProperties, null, null);
                        column.ParentXmlNode = columnNode;
                        foreach (Control control in column.Controls)
                        {
                            if (control != null)
                            {
                                saveXMLofControl(control, column.ParentXmlNode);
                            }
                        }
                    }
                }

            }
        }
        public static void saveXMLofLattice(EIBLattice latticeControl)
        {

            int rowCount = latticeControl.Rows.Count;
            XmlNode xmlNode = null;
            for (int rowCounter = 0; rowCounter < rowCount; rowCounter++)
            {
                EIBScrollableRow row = latticeControl.Rows[rowCounter];
                if (row != null)
                {
                    int columnCount = row.Columns.Count;
                    Hashtable controlProperties = XMLServices.updateSettings(row, row.ControlName, row.Name.ToLower());
                    //controlProperties.Add(XMLServicesConstants.XmlNodeSizeTypeAtt, row.HeightTyp.ToString());
                    xmlNode = XMLServices.writeElement(((IEIBControl)latticeControl).ParentXmlNode, row.ControlType, controlProperties, null, null);
                    row.ParentXmlNode = xmlNode;
                    for (int colCounter = 0; colCounter < columnCount; colCounter++)
                    {
                        //XmlNode columnNode = null;
                        Control control = row.Columns[colCounter];
                        saveXMLofControl(control, null);
                        //Hashtable columnProperties = XMLServices.updateSettings(column, ((IEIBControl)column).ControlName, column.Name.ToLower());
                        //columnProperties[XMLServicesConstants.XmlNodeVisibleAtt] = true.ToString().ToLower();
                        //columnNode = XMLServices.writeElement(((IEIBControl)row).ParentXmlNode, ((IEIBControl)column).ControlType, columnProperties, null, null);
                        //((IEIBControl)column).ParentXmlNode = columnNode;
                        //foreach (Control control in column.Controls)
                        //{
                        //    if (control != null)
                        //    {
                        //        saveXMLofControl(control, ((IEIBControl)column).ParentXmlNode);
                        //    }
                        //}
                    }
                }
            }
        }

        public static void saveXMLofChildren(Control panelControl)
        {
            //Scroll for all tabpages
            if (panelControl is EIBTabControl)
            {
                try
                {
                    EIBTabControl eibTabCOntrol = (EIBTabControl)panelControl;
                    foreach (EIBTabPage tabPage in eibTabCOntrol.TabPages)
                    {
                        ((IEIBControl)tabPage).ParentXmlNode = eibTabCOntrol.ParentXmlNode;
                        Hashtable controlProperties = XMLServices.updateSettings(tabPage, ((IEIBControl)tabPage).ControlName, tabPage.Name.ToLower());
                        Hashtable eventProperties = XMLServices.updateEvents(tabPage, tabPage.Name.ToLower(), tabPage.Name.ToLower());
                        Hashtable internalEventProperties = XMLServices.updateInternalEvents(tabPage, tabPage.Name.ToLower(), tabPage.Name.ToLower());
                        XmlNode xmlNode = null;
                        if (!tabPage.Name.Trim().Equals(""))
                        {
                            xmlNode = XMLServices.writeElement((((IEIBControl)tabPage).ParentXmlNode), EIBControlList.GetRenderingElement(tabPage.GetType().Name), controlProperties, eventProperties, internalEventProperties);
                            ((IEIBControl)tabPage).ParentXmlNode = xmlNode;
                        }
                        // Save each Tab page as normal page
                        saveXMLofChildren(tabPage);
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("To add tabs please drag and drop from toolBoxWindow " + Environment.NewLine + ex.ToString());
                }
            }
            foreach (Control control in panelControl.Controls)
            {
                saveXMLofControl(control, null);
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
                    Hashtable controlProperties = XMLServices.updateSettings(control, ((IEIBControl)control).ControlName, control.Name.ToLower());
                    Hashtable eventProperties = XMLServices.updateEvents(control, control.Name.ToLower(), control.Name.ToLower());
                    Hashtable internalEventProperties = XMLServices.updateInternalEvents(control, control.Name.ToLower(), control.Name.ToLower());
                    List<Hashtable> mappingProperties = XMLServices.updateDataMappings(control, ((IEIBControl)control).ControlName, control.Name.ToLower());
                    XmlNode xmlNode = null;
                    //Check for the control Existing in the lattice's column in the tab control's tabpage.
                    if ((control is IEIBControl) && controlXMLNode != null && controlXMLNode.Name == "column")
                    {
                        controlProperties[XMLServicesConstants.XmlNodeVisibleAtt] = true.ToString().ToLower();
                    }
                    if (!control.Name.Trim().Equals(""))
                    {
                        if (!(control is EIBTabPage))
                        {
                            if (controlXMLNode == null)
                            {
                                xmlNode = XMLServices.writeElement(((IEIBControl)(control.Parent)).ParentXmlNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties, internalEventProperties);
                                XMLServices.writeDataMappings(xmlNode, mappingProperties);
                            }
                            else
                            {
                                xmlNode = XMLServices.writeElement(controlXMLNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties, internalEventProperties);
                                XMLServices.writeDataMappings(xmlNode, mappingProperties);

                            }
                        }
                        if (control.GetType().Equals(typeof(EIBPlaceHolder)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            savexmlofPlaceHolder(control);
                        }
                        if (control.GetType().Equals(typeof(EIBPanel)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofChildren(control);
                        }
                        if (control.GetType().Equals(typeof(EIBRadioGroup)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofChildren(control);
                        }
                        if (control.GetType().Equals(typeof(EIBTreeView)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofTreeView(control, null);
                        }
                        if (control.GetType().Equals(typeof(EIBListbox)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofList(control);
                        }
                        if (control.GetType().Equals(typeof(EIBTabControl)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofChildren(control);
                        }
                        if (control.GetType().Equals(typeof(EIBDatePicker)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofDatePicker(control);
                        }
                        if (control.GetType().Equals(typeof(EIBTime)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofTimeControl(control);
                        }
                        if (control.GetType().Equals(typeof(EIBPaging)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
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
                        if (control.GetType().Equals(typeof(EIBVMenuBar)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofMenu(control);
                        }
                        if (control.GetType().Equals(typeof(EIBCalender)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                        }
                        if (control.GetType().Equals(typeof(EIBSchedular)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                        }
                        if (control.GetType().Equals(typeof(EIBApplet)))
                        {
                            ((EIBApplet)control).ParentXmlNode = xmlNode;
                        }
                        if (control.GetType().Equals(typeof(EIBGrid)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofTable((EIBGrid)control);
                        }
                        if (control.GetType().Equals(typeof(EIBLattice)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLofLattice((EIBLattice)control);
                        }
                        if (control.GetType().Equals(typeof(EIBSearch)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                            saveXMLOfSearch((EIBSearch)control);
                        }
                        if (control.GetType().Equals(typeof(EIBImageBrowse)))
                        {
                            ((IEIBControl)control).ParentXmlNode = xmlNode;
                        }

                    }

                }
            }
        }

        public static void savexmlofPlaceHolder(Control placeholdercontrol)
        {
            foreach (Control control in placeholdercontrol.Controls)
            {
                if (control.Name.Trim().Equals(""))
                {
                    Hashtable controlProperties = new Hashtable();
                    Hashtable eventProperties = new Hashtable();
                    if (control is EIBItem)
                    {
                        controlProperties.Add("reference", ((EIBItem)control).Text);
                        XMLServices.writeElement(((IEIBControl)placeholdercontrol).ParentXmlNode, EIBControlList.GetRenderingElement(control.GetType().Name), controlProperties, eventProperties, null);
                    }

                }
            }
        }

    }

}
