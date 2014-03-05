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
using EIBFormDesigner.Database.Table;

namespace EIBFormDesigner.XML
{
    class DatabaseDeserializeServices
    {
        internal static IEIBControl currentControl = null;
        internal static IEIBControl currentTreeControl = null;

        //Deserialize individual base windows and there child control
        public static void DeserializeWindowXML(string fileName, Control baseFrame, XmlNode parentXMLNode)
        {
            DataSet dataset = new DataSet();
            int nextTop = 0, nextLeft = 0;
            int maxHeight = 0, maxWidth = 0;
            int ParentWidth;
            Dictionary<string, EIBTable> listTable = new Dictionary<string, EIBTable>();
            try
            {
                ParentWidth = baseFrame.Width;
                // disabling re-drawing of treeview till all nodes are added
                baseFrame.SuspendLayout();
                fileName = fileName + ".xml";
                string datasetName = System.IO.Path.GetFileNameWithoutExtension(fileName);
                List<string> M2MList = new List<string>();
                dataset = DatabaseXMLServices.ReadSchema(datasetName, fileName,M2MList);
                //dataset.ReadXml(fileName);
                baseFrame.Controls.Clear();
                ((BaseWindow)baseFrame.Parent).DatabaseDataSet = dataset;
                foreach (DataTable table in dataset.Tables)
                {
                    EIBTable.counter++;
                    EIBTable newNode = new EIBTable();
                    newNode.TableData = table;
                    newNode.Height = 160;
                    newNode.Width = 200;
                    newNode.DatabaseDataSet = dataset;
                    newNode.Name = table.TableName;
                    listTable.Add(newNode.Name, newNode);
                    newNode.ControlName = table.TableName;
                    newNode.tableName.Text = table.TableName;
                    if (M2MList.Contains(table.TableName))
                    {
                        newNode.M2M = true;
                    }
                    else
                    {
                        newNode.M2M = false;
                    }
                    newNode.Top = nextTop;
                    newNode.Left = nextLeft;
                    UpdateControlProperties(newNode);
                    newNode.AutoSize = false;
                    baseFrame.Controls.Add(newNode);
                    if (newNode.Height > maxHeight)
                    {
                        maxHeight = newNode.Height;
                    }
                    if (newNode.Width > maxWidth)
                    {
                        maxWidth = newNode.Width;
                    }
                    if ((nextLeft + newNode.Width + maxWidth) >= ParentWidth)
                    {
                        nextTop += maxHeight;
                        nextLeft = 0;
                    }
                    else
                    {
                        nextLeft += newNode.Width;
                    }
                    foreach (DataColumn column in table.Columns)
                    {
                        ListViewItem lvItem = new ListViewItem(column.ColumnName);
                        if (column.DataType.Name == typeof(string).Name)
                        {
                            int iCap;
                            bool isVarChar = Int32.TryParse(column.Caption,out iCap);
                            if (isVarChar)
                                lvItem.SubItems.Add(column.DataType.Name);
                            else
                                lvItem.SubItems.Add(Designer.Database.DatabaseConstants.LongTextType);
                        }
                        else
                        {
                            lvItem.SubItems.Add(column.DataType.Name);
                        }
                        string autoIncrement = null;
                        if (column.AutoIncrement)
                        {
                            autoIncrement = "Yes";
                        }
                        else
                        {
                            autoIncrement = "No";
                        }
                        lvItem.SubItems.Add(autoIncrement);
                        string uniqueKey = null;
                        if (column.Unique)
                        {
                            uniqueKey = "Yes";
                        }
                        else
                        {
                            uniqueKey = "No";
                        }
                        lvItem.SubItems.Add(uniqueKey);
                        int iCaption;
                        if (column.Caption != null && (!column.Caption.Trim().Equals("")) && Int32.TryParse(column.Caption,out iCaption))
                        {
                            lvItem.SubItems.Add(column.Caption);
                        }
                        else
                        {
                            lvItem.SubItems.Add("");
                        }
                        if (column.Unique)
                        {
                            lvItem.SubItems.Add("Not Null");
                        }
                        else
                        {
                            lvItem.SubItems.Add((column.AllowDBNull ? "Null" : "Not Null"));
                        }

                        newNode.lvDatabase.Items.Add(lvItem);
                    }
                }
                foreach (DataRelation relation in dataset.Relations)
                {
                    EIBTableConnector tableConnector = new EIBTableConnector();
                    tableConnector.InitiateSettings((EIBPanel)baseFrame);
                    tableConnector.Mark1 = (EIBTable)listTable[relation.ParentTable.TableName];
                    tableConnector.Mark2 = (EIBTable)listTable[relation.ChildTable.TableName];
                    tableConnector.createLine();
                    baseFrame.Controls.Add(tableConnector);
                }
            }
            catch (FileNotFoundException)
            {
                MessageBox.Show("Basewindow.xml not Found");
            }
            catch (XmlException)
            {
                MessageBox.Show("DataPattern xml is changed.");
            }
            finally
            {
                // enabling redrawing of treeview after all nodes are added
                baseFrame.ResumeLayout();
                baseFrame.Invalidate();
            }
        }
        private static void UpdateControlProperties(Control control)
        {
            if (!(control is BaseWindow) && !(control.Parent is BaseWindow))
            {
                FormDesigner.eventManager.addPropertiesControl(control);
            }
            string eibInterfaceName = (typeof(IEIBControl)).Name;
        }

 
    }
}
