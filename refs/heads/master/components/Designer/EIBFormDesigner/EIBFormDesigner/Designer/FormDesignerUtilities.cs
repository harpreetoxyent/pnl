using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Data;
using System.Collections;
using System.Xml;
using System.Windows.Forms;
using EIBFormDesigner.XML;
using EIBFormDesigner.UserAdmin;
using EIBXMLServices;
using EIBFormDesigner.Designer.Common;
using System.Drawing;
using EIBFormDesigner.Event;
using EIBFormDesigner.Workflow.XML;

namespace EIBFormDesigner.Designer
{
    class FormDesignerUtilities
    {
        static FormDesignerUtilities formUtil = null;
        public static List<String> oldFilesToBeDeleted = new List<String>();
        private FormDesignerUtilities()
        {
        }

        #region UserControl Designer specific data member positioning.

        static int x = 2;
        static int y = 2;

        #endregion
        //There should be only one window whose visible
        //should be set to true
        internal static List<BaseWindow> visibleTrueWindow = new List<BaseWindow>();
        //Hashtable to make sure basewindow names are unique
        //across application
        internal static Hashtable formWindowNames = new Hashtable();
        internal static Hashtable dataWindowNames = new Hashtable();
        internal static Hashtable workWindowNames = new Hashtable();

        public static FormDesignerUtilities GetInstance()
        {
            if (formUtil == null)
            {
                formUtil =  new FormDesignerUtilities();
                return formUtil;
            }
            else 
                 return formUtil;
        }
        static XmlNode parentXmlNode = null;
        public void openProjectSettings(XmlDocument applicationDoc)
        {
            applicationDoc.Load(EIBXMLUtilities.projectFilePath);
            parentXmlNode = applicationDoc.FirstChild;

            XmlAttribute attr = null;
            attr = parentXmlNode.Attributes[XMLServicesConstants.XmlNodeNameAtt];
            if (attr == null && attr.InnerText.Trim().Equals(""))
            {
                MessageBox.Show("ESP File is missing project name");                
                return;
            }
            EIBXMLUtilities.projectName = attr.InnerText;

        }
        public void instantiateProjectSettings(string folderName, XmlDocument applicationDoc)
        {
            //Create Manifest XML Layout
            parentXmlNode = XMLServices.getRootNode(applicationDoc, FormDesignerConstants.ApplicationElementName, null,null, null);
            XmlNode formXmlNode = applicationDoc.CreateNode(XmlNodeType.Element, FormDesignerConstants.FormDesigner, null);
            parentXmlNode.AppendChild(formXmlNode);
            XmlNode dataXmlNode = applicationDoc.CreateNode(XmlNodeType.Element, FormDesignerConstants.DatabaseDesigner, null);
            parentXmlNode.AppendChild(dataXmlNode);
            XmlNode workXmlNode = applicationDoc.CreateNode(XmlNodeType.Element, FormDesignerConstants.WorkflowDesigner, null);
            parentXmlNode.AppendChild(workXmlNode);

            if (!Directory.Exists(EIBXMLUtilities.formFolderName))
            {
                Directory.CreateDirectory(EIBXMLUtilities.formFolderName);
            }
            if (!Directory.Exists(EIBXMLUtilities.dataFolderName))
            {
                Directory.CreateDirectory(EIBXMLUtilities.dataFolderName);
            }
            if (!Directory.Exists(EIBXMLUtilities.workflowFolderName))
            {
                Directory.CreateDirectory(EIBXMLUtilities.workflowFolderName);
            }
            if (!Directory.Exists(EIBXMLUtilities.usersFolderName))
            {
                Directory.CreateDirectory(EIBXMLUtilities.usersFolderName);
            }
            Stream applicationXmlFile = new FileStream(EIBXMLUtilities.projectFilePath, FileMode.Create);
            applicationDoc.Save(applicationXmlFile);
            applicationXmlFile.Close();

        }

        public void save(FormDesigner form, XmlDocument applicationDoc)
        {
            String generatedFileName = null;
            String extension = ".xml";
            string uniqueId = null;
            Hashtable controlProperties = XMLServices.updateSettings(form, EIBXMLUtilities.projectName, uniqueId);
            //add application name in manifest file
            XmlAttribute attr = null;
            attr = parentXmlNode.Attributes[XMLServicesConstants.XmlNodeNameAtt];
            if (attr == null)
            {
                attr = parentXmlNode.OwnerDocument.CreateAttribute(XMLServicesConstants.XmlNodeNameAtt);
                attr.InnerText = EIBXMLUtilities.projectName;
                parentXmlNode.Attributes.Append(attr);
            }
            XmlNode formXmlNode = parentXmlNode.SelectSingleNode(FormDesignerConstants.FormPattern);
            XmlNode dataXmlNode = parentXmlNode.SelectSingleNode(FormDesignerConstants.DataPattern);
            XmlNode workXmlNode = parentXmlNode.SelectSingleNode(FormDesignerConstants.WorkflowPattern);
            //formXmlNode.RemoveAll();
            //dataXmlNode.RemoveAll();
            //workXmlNode.RemoveAll();
            
            //delete old files for which name has been changed
            foreach (String oldFile in FormDesignerUtilities.oldFilesToBeDeleted)
            {
                File.Delete(EIBXMLUtilities.formFolderName + "\\" + oldFile + extension);
            }
            //clear the list for next session
            FormDesignerUtilities.oldFilesToBeDeleted.Clear();

            if (applicationDoc != null)
            {
                foreach (BaseWindow baseWindow in FormDesigner.listBaseWindow)
                {
                    //save basewindow only if it is visible
                    //if (baseWindow.Visible == true)
                    //{
                        #region Start If Condition
                        //write each basewindow file
                        if (baseWindow.baseFrame.ControlName == EIBXMLUtilities.projectName)
                        {
                            MessageBox.Show("Base Window can not have same name as of Application");
                            return;
                        }
                        baseWindow.TabText = baseWindow.baseFrame.ControlName;
                        baseWindow.Text = baseWindow.baseFrame.ControlName;
                        baseWindow.UniqueID = baseWindow.baseFrame.ControlName;
                        baseWindow.XMLDocument = new XmlDocument();
                        //base window settings are of the base frame
                        baseWindow.ControlProperties = XMLServices.updateSettings(baseWindow.baseFrame, baseWindow.Text, baseWindow.UniqueID);
                        Hashtable eventProperties = XMLServices.updateEvents(baseWindow.baseFrame, baseWindow.Text, baseWindow.UniqueID);
                        Hashtable internalEventProperties = XMLServices.updateInternalEvents(baseWindow.baseFrame, baseWindow.Text, baseWindow.UniqueID);
                        baseWindow.ParentXmlNode = XMLServices.getRootNode(baseWindow.XMLDocument, BaseWindow.elementName, baseWindow.ControlProperties,eventProperties,internalEventProperties);
                        baseWindow.baseFrame.ParentXmlNode = baseWindow.ParentXmlNode;
                        if (baseWindow.TypeOfWindow.Equals(FormDesignerConstants.FormPattern))
                        {
                            generatedFileName = EIBXMLUtilities.formFolderName + "\\" + baseWindow.UniqueID + extension;
                                
                            //Save Form Designer Design Elements
                            XMLServices.saveXMLofChildren(baseWindow.baseFrame);
                            //Save the generated Files in the chosen project folder
                            Stream xmlFile = new FileStream(generatedFileName, FileMode.Create);
                            baseWindow.XMLDocument.Save(xmlFile);
                            xmlFile.Close();
                            //write base window into application file
                            XmlNode xmlFormNode = SelectSingleNode(formXmlNode,baseWindow.baseFrame.ControlName);
                            //If changing name of old window then remove the old basewindow

                            if (baseWindow.Name != baseWindow.baseFrame.ControlName)
                            {
                                if (FormDesigner.listFormBaseWindow.ContainsKey(baseWindow.Name))
                                {
                                    //FormDesigner.listFormBaseWindow[baseWindow.Name].Close();
                                    xmlFormNode = SelectSingleNode(formXmlNode, baseWindow.Name);
                                    FormDesigner.listFormBaseWindow.Remove(baseWindow.Name);
                                    //readding the new base window with new name as key
                                    FormDesigner.listFormBaseWindow.Add(baseWindow.baseFrame.ControlName, baseWindow);

                                }
                            }
                            baseWindow.Name = baseWindow.baseFrame.ControlName;
                            if (xmlFormNode == null)
                            {
                                XMLServices.writeElement(formXmlNode, FormDesignerConstants.FormPattern.ToLower(), baseWindow.ControlProperties, null, null);
                            }
                            else
                            {
                                XMLServices.appendElement(xmlFormNode, FormDesignerConstants.FormPattern.ToLower(), baseWindow.ControlProperties, null);

                                if (FormDesigner.listFormBaseWindow.ContainsKey(baseWindow.Name))
                                {
                                    FormDesigner.listFormBaseWindow[baseWindow.Name] = baseWindow;
                                }
                                else
                                {
                                    FormDesigner.listFormBaseWindow.Add(baseWindow.Name,baseWindow);
                                }
                            }




                            //For each saved form pattern
                            // If it is not Added in User Control Tab then 
                            // buttonA = add  a button with defualt image, text of button will be name of formpattern
                            //ButtonA will be registered as base control and registered in dragdrop handler
                            //on drag either deserialize from the file and recreate the control or get the control object from application
                            /// and add at the specified location

                        }
                        else if (baseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
                        {
                            generatedFileName = EIBXMLUtilities.dataFolderName + "\\" + baseWindow.UniqueID + extension;
                            //Save Database and its relationships
                            DataSet DataSetNew = baseWindow.DatabaseDataSet;
                            // Write the schema and data to an XML file.
                            // Use WriteXml to write the document.
                            DataSetNew.WriteXml(generatedFileName, XmlWriteMode.WriteSchema);
                            // Dispose of the original DataSet.
                            DataSetNew.Dispose();

                            //Edit xml Schema file to add attibute M2M(Many to Many).
                            DatabaseXMLServices.Add_toSchema(DataSetNew.DataSetName, generatedFileName, baseWindow);

                            //write base window into application file
                            XmlNode xmlFormNode = SelectSingleNode(dataXmlNode, baseWindow.UniqueID);
                            if (baseWindow.Name != baseWindow.baseFrame.ControlName)
                            {
                                if (FormDesigner.listDataBaseWindow.ContainsKey(baseWindow.Name))
                                {
                                    //FormDesigner.listFormBaseWindow[baseWindow.Name].Close();
                                    xmlFormNode = SelectSingleNode(dataXmlNode, baseWindow.Name);
                                    FormDesigner.listDataBaseWindow.Remove(baseWindow.Name);
                                    //readding the new base window with new name as key
                                    //FormDesigner.listDataBaseWindow.Add(baseWindow.baseFrame.ControlName, baseWindow);
                                }
                            }
                            baseWindow.Name = baseWindow.baseFrame.ControlName;
                            if (xmlFormNode == null)
                            {
                                XMLServices.writeElement(dataXmlNode, FormDesignerConstants.DataPattern.ToLower(), baseWindow.ControlProperties, null, null);
                            }
                            else
                            {
                                XMLServices.appendElement(xmlFormNode, FormDesignerConstants.DataPattern.ToLower(), baseWindow.ControlProperties, null);
                                if (FormDesigner.listDataBaseWindow.ContainsKey(baseWindow.Name))
                                {
                                    FormDesigner.listDataBaseWindow[baseWindow.Name] = baseWindow;
                                }
                                else
                                {
                                    FormDesigner.listDataBaseWindow.Add(baseWindow.Name, baseWindow);
                                }
                            }
                        }
                        else if (baseWindow.TypeOfWindow.Equals(FormDesignerConstants.WorkflowPattern))
                        {
                            generatedFileName = EIBXMLUtilities.workflowFolderName + "\\" + baseWindow.UniqueID + extension;
                            //Save Workflow and its BPEL
                            //Save Form Designer Design Elements
                            WorkflowXMLServices.saveXMLofChildren(baseWindow.baseFrame);
                            //Save the generated Files in the chosen project folder
                            Stream xmlFile = new FileStream(generatedFileName, FileMode.Create);
                            baseWindow.XMLDocument.Save(xmlFile);
                            xmlFile.Close();
                            //write base window into application file
                            XmlNode xmlFormNode = SelectSingleNode(workXmlNode, baseWindow.baseFrame.ControlName);
                            //If changing name of old window then remove the old basewindow
                            if (baseWindow.Name != baseWindow.baseFrame.ControlName)
                            {
                                if (FormDesigner.listWorkBaseWindow.ContainsKey(baseWindow.Name))
                                {
                                    //FormDesigner.listFormBaseWindow[baseWindow.Name].Close();
                                    xmlFormNode = SelectSingleNode(workXmlNode, baseWindow.Name);
                                    FormDesigner.listWorkBaseWindow.Remove(baseWindow.Name);
                                }
                            }
                            baseWindow.Name = baseWindow.baseFrame.ControlName;
                            if (xmlFormNode == null)
                            {
                                XMLServices.writeElement(workXmlNode, FormDesignerConstants.WorkflowPattern.ToLower(), baseWindow.ControlProperties, null, null);
                            }
                            else
                            {
                                XMLServices.appendElement(xmlFormNode, FormDesignerConstants.WorkflowPattern.ToLower(), baseWindow.ControlProperties, null);

                                if (FormDesigner.listWorkBaseWindow.ContainsKey(baseWindow.Name))
                                {
                                    FormDesigner.listWorkBaseWindow[baseWindow.Name] = baseWindow;
                                }
                                else
                                {
                                    FormDesigner.listWorkBaseWindow.Add(baseWindow.Name, baseWindow);
                                }
                            }

                        }
                        //Mark the base window as saved
                        baseWindow.IsBaseWindowSaved = true;
                        #endregion
                    //}
                }
                if (FormDesigner.useradminutility != null)
                {
                    FormDesigner.useradminutility.Searilize();
                }
                form.displayNameExplorer.serializeXMLMappings();
                form.displayNameExplorer.comboBox1.Enabled = false;
                form.webServiceExplorer.serializeXMLMappings();
                //generatedFileName = EIBXMLUtilities.usersFolderName + "\\" + "users.xml";

                //if (AccessControlList.adminDataSet != null)
                //{
                //    AccessControlList.adminDataSet.WriteXml(generatedFileName, XmlWriteMode.IgnoreSchema);
                //}
                Stream applicationXmlFile = new FileStream(EIBXMLUtilities.projectFilePath, FileMode.Create);
                applicationDoc.Save(applicationXmlFile);
                applicationXmlFile.Close();
            }
        }
        public static XmlNode SelectSingleNode(XmlNode xmlNode, string uniqueId)
        {
            foreach (XmlNode childXmlNode in xmlNode.ChildNodes)
            {
                XmlAttribute xmlAttribute = childXmlNode.Attributes[XMLServicesConstants.XmlNodeNameAtt];
                if (xmlAttribute.Value.Equals(uniqueId))
                {
                    return childXmlNode;
                }
            }
            return null;
        }

        public static void ClearUserControl()
        {
            x = 2;
            y = 2;
            ToolBoxWindow.form.toolBoxWindow.userControls.Controls.Clear();
        }

        public static void LoadUserControl(string UserControlName)
        {
            Button btnUserControl = new Button();
            btnUserControl.FlatAppearance.BorderSize = 0;
            btnUserControl.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            btnUserControl.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            btnUserControl.ForeColor = System.Drawing.SystemColors.ControlText;
            //this.helpProvider1.SetHelpKeyword(this.buttonControl, "Button");
            //this.helpProvider1.SetHelpNavigator(this.buttonControl, System.Windows.Forms.HelpNavigator.KeywordIndex);
            btnUserControl.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            btnUserControl.ImageIndex = 28;
            btnUserControl.ImageList = ToolBoxWindow.form.toolBoxWindow.imageList;
            btnUserControl.Location = new System.Drawing.Point(x, y);
            y += 40;
            btnUserControl.Margin = new System.Windows.Forms.Padding(2);
            btnUserControl.Name = UserControlName;
            //this.helpProvider1.SetShowHelp(btnUserControl, true);
            btnUserControl.Size = new System.Drawing.Size(144, 33);
            btnUserControl.TabIndex = 1;
            btnUserControl.Text = UserControlName;
            btnUserControl.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            btnUserControl.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            btnUserControl.UseVisualStyleBackColor = true;
            ToolBoxWindow.form.toolBoxWindow.userControls.Controls.Add(btnUserControl);
            DragDropHandler.RegisterControl(btnUserControl, false, true);
        }
    }
}
