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
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Event;
using EIBXMLServices;

namespace EIBFormDesigner.XML
{
    class DatabaseXMLServices
    {
        internal static DataTable CreateSchema(DataSet DataSetNew, string tableName, EIBTable eibTable)
        {
            // Create a Schema
            DataColumn DataColumnNew;
            string strName, strType;
            Type dataType;
            DataTable DataTableNew = new DataTable(tableName);
            foreach (ListViewItem lvItem in eibTable.lvDatabase.Items)
            {
                strName = lvItem.Text;
                strType = lvItem.SubItems[1].Text;
                //Append System. to make them .net types
                dataType = Type.GetType("System."+ strType);
                DataColumnNew = new DataColumn(strName, dataType);
                DataColumnNew.AutoIncrement = (lvItem.SubItems[2].Text == "Yes");
                DataTableNew.Columns.Add(DataColumnNew);
            }
            DataSetNew.Tables.Add(DataTableNew);
            return DataTableNew;
        }

        
        //Derserialize the data pattern files
        public static void DeserializeDataPatternFiles(TreeView treeView, string fileName, XmlNode parentXMLNode)
        {
            BaseWindow.dataCounter = 0;
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
                        if (xmlNode.Name == FormDesignerConstants.DataPattern)
                        {
                            
                            TreeNode newNode = new TreeNode();
                            newNode.Text = FormDesignerConstants.AvailableDataPattern;
                            treeView.Nodes.Add(newNode);
                            HandleChildNodes(newNode, xmlNode, FormDesignerConstants.DataPattern);
                        }
                    }
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Project Manifest.xml File not Found");
            }
            finally
            {
                // enabling redrawing of treeview after all nodes are added
                treeView.EndUpdate();
            }
        }
        private static void HandleChildNodes(TreeNode treeNode, XmlNode xmlNode, string windowName)
        {
            foreach (XmlNode xmlChildNode in xmlNode.ChildNodes)
            {
                if (xmlChildNode.NodeType == XmlNodeType.Element)
                {
                    BaseWindow.dataCounter++;
                    string dataPatternFileName = xmlChildNode.Attributes[XMLServicesConstants.XmlNodeNameAtt].Value;

                    TreeNode dataSetNode = new TreeNode();
                    dataSetNode.Text = dataPatternFileName;
                    treeNode.Nodes.Add(dataSetNode);

                    dataPatternFileName = EIBXMLUtilities.dataFolderName + "\\" + dataPatternFileName + ".xml"; ;
                    DataSet dataset = new DataSet();
                    try
                    {
                        dataset = DatabaseXMLServices.ReadSchema(dataSetNode.Text, dataPatternFileName, null);
                        //dataset.ReadXml(dataPatternFileName);
                        foreach (DataTable table in dataset.Tables)
                        {
                            TreeNode newNode = new TreeNode();
                            newNode.Text = table.TableName.ToString();
                            dataSetNode.Nodes.Add(newNode);
                            foreach (DataColumn column in table.Columns)
                            {
                                TreeNode newChildNode = new TreeNode();
                                newChildNode.Text = column.ColumnName + ": " + column.DataType.Name;
                                newChildNode.ImageIndex = 6;
                                newNode.Nodes.Add(newChildNode);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.ToString());
                    }
                }
            }
        }

        public static DataSet ReadSchema(string datasetName, string fileName,List<string> M2MList)
        {
            XmlDocument oXMLschema;
            XmlAttribute oAtt1;

            //load dataset schema into xml

            oXMLschema = new XmlDocument();
            oXMLschema.LoadXml(File.ReadAllText(fileName));

            XmlNamespaceManager nmsMgr =
                new XmlNamespaceManager(oXMLschema.NameTable);
            nmsMgr.AddNamespace("xs",
                "http://www.w3.org/2001/XMLSchema");

            string xpath_toEle_inDSschema = "/" + datasetName + "/xs:schema/xs:element/xs:complexType/xs:choice/xs:element";

            XmlNodeList oNList = oXMLschema.SelectNodes(
                           xpath_toEle_inDSschema, nmsMgr);
            foreach (XmlNode oN in oNList)
            {
                if (oN.Attributes["M2M"] != null)
                {
                    if (M2MList != null)
                    {
                        if (oN.Attributes["M2M"].Value == "Yes")
                        {
                            M2MList.Add(oN.Attributes["name"].Value);
                        }
                    }
                    oN.Attributes.Remove(oN.Attributes["M2M"]);
                }
            }
            string tempPath = System.IO.Path.GetTempPath();
            oXMLschema.Save(tempPath + "tempds.xml");
            DataSet ds = new DataSet();
            ds.ReadXmlSchema(tempPath + "tempds.xml");
            File.Delete(tempPath + "tempds.xml");
            return ds;
        }

        public static void Add_toSchema(string datasetName,string fileName,BaseWindow baseWindow)
        {
            XmlDocument oXMLschema;
            XmlAttribute oAtt1;
            //load dataset schema into xml
            oXMLschema = new XmlDocument();
            oXMLschema.LoadXml(File.ReadAllText(fileName));
            //modify the xml schema.
            XmlNamespaceManager nmsMgr =
                new XmlNamespaceManager(oXMLschema.NameTable);
            nmsMgr.AddNamespace("xs",
                "http://www.w3.org/2001/XMLSchema");

            string xpath_toEle_inDSschema = "/"+datasetName+"/xs:schema/xs:element/xs:complexType/xs:choice/xs:element";

            XmlNodeList oNList = oXMLschema.SelectNodes(
                           xpath_toEle_inDSschema, nmsMgr);

            foreach (XmlNode oN in oNList)
            {
                string name = oN.Attributes["name"].Value;
                int tableIndex = baseWindow.baseFrame.Controls.IndexOfKey(name);
                if (tableIndex >= 0)
                {
                    oAtt1 = oXMLschema.CreateAttribute("M2M");
                    if (((EIBTable)baseWindow.baseFrame.Controls[tableIndex]).M2M)
                    {
                        oAtt1.Value = "Yes";
                    }
                    else
                    {
                        oAtt1.Value = "No";
                    }
                    oN.Attributes.Append(oAtt1);
                }
            }
            oXMLschema.Save(fileName);
        }
    }
}
