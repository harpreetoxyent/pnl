using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using EIBFormDesigner.Event;
using System.Collections;
using EIBXMLServices;
using System.IO;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.Build
{
    public class BuildException : Exception
    {
        string controlpath;
        public string ControlPath
        {
            set { controlpath = value; }
            get { return controlpath; }
        }
        public BuildException(string msg)
            :base(msg)
        {

        }
        public BuildException(string msg,string controlpath)
            : this(msg)
        {
            this.ControlPath = controlpath;
        }

    }
   
    class BuildTool
    {
        public static FormDesigner form = null;
        public BuildTool(FormDesigner form)
        {
            BuildTool.form = form;
            form.MakePublish(false);
        }
        public static List<string> listformpatternspath = new List<string>();
        public static Hashtable ControlCollection = new Hashtable();
        public static void BuildForm(string filename)
        {

            

            ControlCollection.Clear();
            DeserializeProjectFile(filename, null);
            //}
            //catch
            //{
            //    MessageBox.Show("There is a buld error in the formpattern");
            //}
        }
        public static void DeserializeProjectFile(string fileName, XmlNode parentXMLNode)
        {

            try
            {
                form.buildwindow.issuccessfull = true;
                XmlDocument doc = new XmlDocument();
                XmlNode currentXmlNode = null;
                if (parentXMLNode == null)
                {
                    doc.Load(fileName);
                    currentXmlNode = doc.FirstChild;
                }
                else
                {
                    currentXmlNode = parentXMLNode;
                }
                foreach (XmlNode xmlNode in currentXmlNode.ChildNodes)
                {
                    if (xmlNode.NodeType == XmlNodeType.Element)
                    {
                        if (xmlNode.Name == FormDesignerConstants.FormPattern)
                        {
                            //TreeNode newNode = new TreeNode();
                            //newNode.Text = xmlNode.Name;
                            //treeView.Nodes.Add(newNode);
                            HandleChildNodes(xmlNode, FormDesignerConstants.FormPattern);
                        }
                        //if (xmlNode.Name == FormDesignerConstants.DataPattern)
                        //{
                        //    //TreeNode newNode = new TreeNode();
                        //    //newNode.Text = xmlNode.Name;
                        //    //treeView.Nodes.Add(newNode);
                        //    HandleChildNodes(newNode, xmlNode, FormDesignerConstants.DataPattern);
                        //}
                        //if (xmlNode.Name == FormDesignerConstants.WorkflowPattern)
                        //{
                        //    //TreeNode newNode = new TreeNode();
                        //    //newNode.Text = xmlNode.Name;
                        //    //treeView.Nodes.Add(newNode);
                        //    HandleChildNodes(newNode, xmlNode, FormDesignerConstants.WorkflowPattern);
                        //}
                    }
                }
            }
            catch (FileNotFoundException)
            {
                form.buildwindow.issuccessfull = false;
                MessageBox.Show("Project Manifest.xml File not Found");
                
            }
            catch(Exception ex)
            {
                if (ex is BuildException)
                {
                    BuildException bex = (BuildException)ex;
                    form.buildwindow.issuccessfull = false;
                    form.buildwindow.ControlTree = bex.ControlPath;
                }
                else
                {
                    //MessageBox.Show("Don't build Now.");
                }
                //MessageBox.Show("There is a buld error in Control "+ bex.ControlPath);
            }
            finally
            {

                if (form.buildwindow.issuccessfull)
                {
                    form.MakePublish(true);
                    //MessageBox.Show("Build Successfull");
                    form.buildwindow.FormPatternName = "";
                    form.buildwindow.ControlName = "";
                    form.buildwindow.ControlTree = "";
                }
                // enabling redrawing of treeview after all nodes are added
                //treeView.EndUpdate();
            }
        }
        public static string path(XmlNode xmlNode)
        {
            if (xmlNode.ParentNode != null && xmlNode.ParentNode.NodeType!=XmlNodeType.Document)
            {
                path(xmlNode.ParentNode);
            }
            nodepath = nodepath + xmlNode.Attributes["id"].Value+".";
            return nodepath;
        }
        static string nodepath="";
        public static void DeserializeFormpatternFile(string fileName, XmlNode parentXMLNode)
        {

            //try
            //{

                // disabling re-drawing of treeview till all nodes are added
                //treeView.BeginUpdate();
                XmlDocument doc = new XmlDocument();
                XmlNode currentXmlNode = null;
                if (parentXMLNode == null)
                {
                    doc.Load(fileName);
                    currentXmlNode = doc.FirstChild;
                }
                else
                {
                    currentXmlNode = parentXMLNode;
                    //ControlCollection.Add(currentXmlNode.Attributes["id"].Value, currentXmlNode.Attributes["id"].Value);
                }
                foreach (XmlNode xmlNode in currentXmlNode.ChildNodes)
                {
                    if (xmlNode.NodeType == XmlNodeType.Element)
                    {
                        if (xmlNode.Attributes["id"] != null)
                        {

                            if (ControlCollection.Contains(xmlNode.Attributes["id"].Value))
                            {
                                nodepath = "";
                                form.buildwindow.ControlName = xmlNode.Attributes["id"].Value;
                                BuildException ex = new BuildException("Id Repeated for the control", path(xmlNode));
                                throw ex;
                            }
                            ControlCollection.Add(xmlNode.Attributes["id"].Value, xmlNode.Attributes["id"].Value);
                        }
                        if (xmlNode.HasChildNodes)
                        {
                            DeserializeFormpatternFile(null, xmlNode);
                        }
                        //if (xmlNode.Name == FormDesignerConstants.FormPattern)
                        //{
                        //    //TreeNode newNode = new TreeNode();
                        //    //newNode.Text = xmlNode.Name;
                        //    //treeView.Nodes.Add(newNode);
                        //    HandleChildNodes(xmlNode, FormDesignerConstants.FormPattern);
                        //}
                        //if (xmlNode.Name == FormDesignerConstants.DataPattern)
                        //{
                        //    //TreeNode newNode = new TreeNode();
                        //    //newNode.Text = xmlNode.Name;
                        //    //treeView.Nodes.Add(newNode);
                        //    HandleChildNodes(newNode, xmlNode, FormDesignerConstants.DataPattern);
                        //}
                        //if (xmlNode.Name == FormDesignerConstants.WorkflowPattern)
                        //{
                        //    //TreeNode newNode = new TreeNode();
                        //    //newNode.Text = xmlNode.Name;
                        //    //treeView.Nodes.Add(newNode);
                        //    HandleChildNodes(newNode, xmlNode, FormDesignerConstants.WorkflowPattern);
                        //}
                    }
                }
            //}
            //catch (FileNotFoundException)
            //{
            //    MessageBox.Show("Project Manifest.xml File not Found");
            //}
            //finally
            //{
            //    // enabling redrawing of treeview after all nodes are added
            //    //treeView.EndUpdate();
            //}
        }
        private static void HandleChildNodes(XmlNode xmlNode, string windowName)
        {
            foreach (XmlNode xmlChildNode in xmlNode.ChildNodes)
            {
                if (xmlChildNode.NodeType == XmlNodeType.Element)
                {
                    //TreeNode newChildNode = new TreeNode();
                    //newChildNode.Text = xmlChildNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value;
                    //newChildNode.ImageIndex = 6;
                    //treeNode.Nodes.Add(newChildNode);
                    //if (windowName.Equals(FormDesignerConstants.WorkflowPattern))
                    //{
                    //    FormDesignerUtilities.workWindowNames.Add(newChildNode.Text, newChildNode.Text);
                    //    BaseWindow.workCounter++;
                    //}
                    //else
                    if (windowName.Equals(FormDesignerConstants.FormPattern))
                    {
                        ControlCollection.Clear();
                        string path = EIBXMLUtilities.formFolderName + xmlChildNode.Attributes["id"].Value + ".xml";
                        //BuildTool.listformpatternspath.Add(path);
                        ControlCollection.Add(xmlChildNode.Attributes["id"].Value, xmlChildNode.Attributes["id"].Value);
                        form.buildwindow.FormPatternName = xmlChildNode.Attributes["id"].Value;
                        DeserializeFormpatternFile(path, null);
                    }
                    //else if (windowName.Equals(FormDesignerConstants.DataPattern))
                    //{
                    //    FormDesignerUtilities.dataWindowNames.Add(newChildNode.Text, newChildNode.Text);
                    //    BaseWindow.dataCounter++;
                    //}
                }
            }
        }

        //public static void BuildWindow()
    }
}
