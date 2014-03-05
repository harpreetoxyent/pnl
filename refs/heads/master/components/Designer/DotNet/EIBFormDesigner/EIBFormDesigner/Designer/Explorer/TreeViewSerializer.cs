using System;
using System.Xml;
using System.Windows.Forms;
using System.Text;
using System.IO;
using EIBFormDesigner.XML;
namespace EIBFormDesigner.Designer.Common
{
    /// <summary>
    /// Summary description for TreeViewSerializer.
    /// </summary>
    public class TreeViewSerializer
    {

        // Xml attributes for node e.g. <node text="Asia" tag="" imageindex="1"></node>
        private const string XmlNodeTagAtt = "tag";
        private const string XmlNodeImageIndexAtt = "imageindex";

        public TreeViewSerializer()
        {
            //
            // TODO: Add constructor logic here
            //

        }

        ////System.IO.StringWriter s;
        //public void SerializeTreeView(TreeView treeView, string fileName)
        //{
        //    XmlTextWriter textWriter = new XmlTextWriter(fileName, System.Text.Encoding.ASCII);
        //    // writing the xml declaration tag
        //    textWriter.WriteStartDocument();
        //    //textWriter.WriteRaw("\r\n");
        //    // writing the main tag that encloses all node tags
        //    textWriter.WriteStartElement("TreeView");

        //    // save the nodes, recursive method
        //    SaveNodes(treeView.Nodes, textWriter);

        //    textWriter.WriteEndElement();

        //    textWriter.Close();
        //}


        public void DeserializeTreeView(TreeView treeView, string fileName,XmlNode  parentXMLNode)
        {
            XmlTextReader reader = null;
            try
            {
                
                // disabling re-drawing of treeview till all nodes are added
                treeView.BeginUpdate();
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
                            TreeNode newNode = new TreeNode();
                            newNode.Text = xmlNode.Name;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode);
                        }
                        if (xmlNode.Name == FormDesignerConstants.DataPattern)
                        {
                            TreeNode newNode = new TreeNode();
                            newNode.Text = xmlNode.Name;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode);
                        }
                        if (xmlNode.Name == FormDesignerConstants.WorkflowPattern)
                        {
                            TreeNode newNode = new TreeNode();
                            newNode.Text = xmlNode.Name;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode);
                        }                        
                    }
                }
            }
            catch (FileNotFoundException )
            {
                MessageBox.Show("Project Manifest.xml File not Found");
            }
            finally
            {
                // enabling redrawing of treeview after all nodes are added
                treeView.EndUpdate();
            }
        }

        private static void HandleChildNodes(TreeNode treeNode, XmlNode xmlNode)
        {
            foreach (XmlNode xmlChildNode in xmlNode.ChildNodes)
            {
                if (xmlChildNode.NodeType == XmlNodeType.Element)
                {
                    TreeNode newChildNode = new TreeNode();
                    newChildNode.Text = xmlChildNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value;
                    newChildNode.ImageIndex = 6;
                    treeNode.Nodes.Add(newChildNode);
                }
            }
        }

        /// <summary>
        /// Used by Deserialize method for setting properties of TreeNode from xml node attributes
        /// </summary>
        /// <param name="node"></param>
        /// <param name="propertyName"></param>
        /// <param name="value"></param>
        private void SetAttributeValue(TreeNode node, string propertyName, string value)
        {
            if (propertyName == XMLServicesConstants.XmlNodeTextAtt)
            {
                node.Text = value;
            }
            else if (propertyName == XmlNodeImageIndexAtt)
            {
                node.ImageIndex = int.Parse(value);
            }
            else if (propertyName == XmlNodeTagAtt)
            {
                node.Tag = value;
            }
        }

        public void LoadXmlFileInTreeView(TreeView treeView, string fileName)
        {
            XmlTextReader reader = null;
            try
            {
                treeView.BeginUpdate();
                reader = new XmlTextReader(fileName);

                TreeNode n = new TreeNode(fileName);
                treeView.Nodes.Add(n);
                while (reader.Read())
                {
                    if (reader.NodeType == XmlNodeType.Element)
                    {
                        bool isEmptyElement = reader.IsEmptyElement;
                        StringBuilder text = new StringBuilder();
                        text.Append(reader.Name);
                        int attributeCount = reader.AttributeCount;
                        if (attributeCount > 0)
                        {
                            text.Append(" ( ");
                            for (int i = 0; i < attributeCount; i++)
                            {
                                if (i != 0) text.Append(", ");
                                reader.MoveToAttribute(i);
                                text.Append(reader.Name);
                                text.Append(" = ");
                                text.Append(reader.Value);
                            }
                            text.Append(" ) ");
                        }

                        if (isEmptyElement)
                        {
                            n.Nodes.Add(text.ToString());
                        }
                        else
                        {
                            n = n.Nodes.Add(text.ToString());
                        }
                    }
                    else if (reader.NodeType == XmlNodeType.EndElement)
                    {
                        n = n.Parent;
                    }
                    else if (reader.NodeType == XmlNodeType.XmlDeclaration)
                    {

                    }
                    else if (reader.NodeType == XmlNodeType.None)
                    {
                        return;
                    }
                    else if (reader.NodeType == XmlNodeType.Text)
                    {
                        n.Nodes.Add(reader.Value);
                    }

                }
            }
            finally
            {
                treeView.EndUpdate();
                reader.Close();
            }
        }
    }
}
