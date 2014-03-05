using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Web.Services;
using Desc = System.Web.Services.Description;
using EIBFormDesigner;
using System.Xml.Schema;

namespace EIBFormDesigner.Designer.WebServiceClientExplorer
{
    public partial class AddWebServiceClient : System.Windows.Forms.Form
    {
        public AddWebServiceClient()
        {
            InitializeComponent();
            parameterDataSet = new DataSet();
            BindData();
        }
        public DataSet parameterDataSet;
        public TreeNode newTreeNode;
        public Dictionary<string, Dictionary<string, string>> parameterMappings = new Dictionary<string, Dictionary<string, string>>();
        public void prepareDataSet()
        {
            DataTable dtParam = new DataTable("ParamTable");
            dtParam.Columns.Add(new DataColumn("paramname", typeof(string)));
            dtParam.Columns.Add(new DataColumn("paramtype", typeof(string)));
            dtParam.Columns.Add(new DataColumn("paramvalue", typeof(string)));
            dtParam.Columns[0].DefaultValue = "";
            dtParam.Columns[1].DefaultValue = "";
            dtParam.Columns[2].DefaultValue = "";
            parameterDataSet.Tables.Add(dtParam);
        }

        private void BindData()
        {
            this.prepareDataSet();
            DataTable dtParam = parameterDataSet.Tables[0];
            DataTable dtType = this.prepareDataTableForType();

            DataGridTableStyle tableStyle = new DataGridTableStyle();
            tableStyle.MappingName = "ParamTable";
            DataGridComboBoxColumn ComboTextCol = new DataGridComboBoxColumn(this.parameterGrid, dtParam, "paramtype");
            for (int i = 0; i < dtParam.Columns.Count; ++i)
            {
                if (dtParam.Columns[i].ColumnName != "paramtype")
                {
                    DataGridColorTextBoxColumn TextCol = new DataGridColorTextBoxColumn(parameterGrid, dtParam, dtParam.Columns[i].ColumnName);
                    TextCol.MappingName = dtParam.Columns[i].ColumnName;
                    TextCol.HeaderText = dtParam.Columns[i].ColumnName;
                    TextCol.Width = 95;
                    tableStyle.GridColumnStyles.Add(TextCol);
                }
                else
                {
                    ComboTextCol.MappingName = "paramtype"; //must be from the lattice table...					
                    ComboTextCol.HeaderText = "Type";
                    ComboTextCol.Width = 150;
                    ComboTextCol.ColumnComboBox.DataSource = dtType;
                    ComboTextCol.ColumnComboBox.DisplayMember = "paramType"; //use for display value in combo
                    ComboTextCol.ColumnComboBox.ValueMember = "paramType"; // also use for value member in combo
                    tableStyle.PreferredRowHeight = ComboTextCol.ColumnComboBox.Height + 2;
                    tableStyle.GridColumnStyles.Add(ComboTextCol);
                }
            }
            parameterGrid.TableStyles.Clear();
            parameterGrid.TableStyles.Add(tableStyle);
            parameterGrid.DataSource = dtParam;
        }

        public DataTable prepareDataTableForType()
        {
            DataTable dtParamType = new DataTable("ParamTypeTable");
            dtParamType.Columns.Add(new DataColumn("paramType", typeof(string)));
            dtParamType.Rows.Add("Boolean");
            dtParamType.Rows.Add("Integer");
            dtParamType.Rows.Add("Char");
            dtParamType.Rows.Add("string");
            return dtParamType;
        }

        #region Code For DataGridCombobox Column in DataGrid

        public class DataGridColorTextBoxColumn : DataGridTextBoxColumn
        {
            private CurrencyManager _source;
            private int _rowNum;

            public static int _RowCount;
            private DataGrid dgForms;
            private DataTable xdt;
            private string _columnName;

            public DataGridColorTextBoxColumn(DataGrid dgForms, DataTable xdt, string columnName)
                : base()
            {
                _source = null;
                _RowCount = -1;
                _columnName = columnName;
                this.dgForms = dgForms;
                this.xdt = xdt;
            }

            // Paint
            protected override void Paint(Graphics g, Rectangle bounds,
                CurrencyManager source, int rowNum,
                Brush backBrush, Brush foreBrush, bool alignToRight)
            {
                string sex = String.Empty;
                if (xdt != null)
                {
                    if (xdt.Rows.Count > 0 && rowNum < xdt.Rows.Count)
                    {
                        if (xdt.Rows[rowNum] != null)
                        {
                            try
                            {
                                sex = (string)xdt.Rows[rowNum][_columnName];
                            }
                            catch
                            {
                                // swallow invalid cast exceptions (not good practice, but works)
                            }
                            if (sex != null)
                            {
                                if (sex == "Owner")
                                    backBrush = Brushes.Red;
                                else if (sex == "Sales Representative")
                                    backBrush = Brushes.Yellow;
                                else if (sex == "Accounting Manager")
                                    backBrush = Brushes.LightBlue;
                                else
                                    backBrush = Brushes.White;
                            }
                            base.Paint(g, bounds, source, rowNum, backBrush, foreBrush, alignToRight);
                        }
                    }

                }

                //	base.Paint(g, bounds, source, rowNum, backBrush, foreBrush, alignToRight);
            }






        }// end class

        // Step 1. Derive a custom column style from DataGridTextBoxColumn
        //	a) add a ComboBox member
        //  b) track when the combobox has focus in Enter and Leave events
        //  c) override Edit to allow the ComboBox to replace the TextBox
        //  d) override Commit to save the changed data
        public class DataGridComboBoxColumn : DataGridTextBoxColumn
        {
            public NoKeyUpCombo ColumnComboBox;
            private CurrencyManager _source;
            private int _rowNum;
            private bool _isEditing;
            public static int _RowCount;
            private DataGrid dgForms;
            private DataTable xdt;
            private string _columnName;

            public DataGridComboBoxColumn()
                : base()
            {
                _source = null;
                _isEditing = false;
                _RowCount = -1;

                ColumnComboBox = new NoKeyUpCombo();
                ColumnComboBox.DropDownStyle = ComboBoxStyle.DropDownList;

                ColumnComboBox.Leave += new EventHandler(LeaveComboBox);
                //		ColumnComboBox.Enter += new EventHandler(ComboMadeCurrent);
                ColumnComboBox.SelectionChangeCommitted += new EventHandler(ComboStartEditing);
            }

            public DataGridComboBoxColumn(DataGrid dgForms, DataTable xdt, string columnName)
                : base()
            {
                _source = null;
                _isEditing = false;
                _RowCount = -1;
                _columnName = columnName;
                ColumnComboBox = new NoKeyUpCombo();
                ColumnComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
                ColumnComboBox.Leave += new EventHandler(LeaveComboBox);
                //		ColumnComboBox.Enter += new EventHandler(ComboMadeCurrent);
                ColumnComboBox.SelectionChangeCommitted += new EventHandler(ComboStartEditing);
                this.dgForms = dgForms;
                this.xdt = xdt;
            }

            private void ComboStartEditing(object sender, EventArgs e)
            {
                _isEditing = true;
                base.ColumnStartedEditing((Control)sender);
            }

            private void HandleScroll(object sender, EventArgs e)
            {
                if (ColumnComboBox.Visible)
                    ColumnComboBox.Hide();

            }

            //		private void ComboMadeCurrent(object sender, EventArgs e)
            //		{
            //			_isEditing = true; 	
            //		}
            //		
            private void LeaveComboBox(object sender, EventArgs e)
            {
                if (_isEditing)
                {
                    SetColumnValueAtRow(_source, _rowNum, ColumnComboBox.Text);
                    _isEditing = false;
                    Invalidate();
                }
                ColumnComboBox.Hide();
                this.DataGridTableStyle.DataGrid.Scroll -= new EventHandler(HandleScroll);

            }

            protected override void Edit(CurrencyManager source, int rowNum, Rectangle bounds, bool readOnly, string instantText, bool cellIsVisible)
            {
                base.Edit(source, rowNum, bounds, readOnly, instantText, cellIsVisible);

                _rowNum = rowNum;
                _source = source;

                ColumnComboBox.Parent = this.TextBox.Parent;
                ColumnComboBox.Location = this.TextBox.Location;
                ColumnComboBox.Size = new Size(this.TextBox.Size.Width, ColumnComboBox.Size.Height);
                ColumnComboBox.SelectedIndex = ColumnComboBox.FindStringExact(this.TextBox.Text);
                ColumnComboBox.Text = this.TextBox.Text;
                this.TextBox.Visible = false;
                ColumnComboBox.Visible = true;
                this.DataGridTableStyle.DataGrid.Scroll += new EventHandler(HandleScroll);

                ColumnComboBox.BringToFront();
                ColumnComboBox.Focus();
            }

            // Paint
            protected override void Paint(Graphics g, Rectangle bounds,
                                          CurrencyManager source, int rowNum,
                                          Brush backBrush, Brush foreBrush, bool alignToRight)
            {
                string sex = String.Empty;
                if (xdt != null)
                {
                    if (xdt.Rows.Count > 0 && rowNum < xdt.Rows.Count)
                    {
                        if (xdt.Rows[rowNum] != null)
                        {
                            try
                            {
                                sex = (string)xdt.Rows[rowNum][_columnName];
                            }
                            catch
                            {
                                // swallow invalid cast exceptions (not good practice, but works)
                            }
                            if (sex != null)
                            {
                                if (sex == "Owner")
                                    backBrush = Brushes.Red;
                                else if (sex == "Sales Representative")
                                    backBrush = Brushes.Yellow;
                                else if (sex == "Accounting Manager")
                                    backBrush = Brushes.LightBlue;
                                else
                                    backBrush = Brushes.White;
                            }
                            base.Paint(g, bounds, source, rowNum, backBrush, foreBrush, alignToRight);
                        }
                    }

                }

                //	base.Paint(g, bounds, source, rowNum, backBrush, foreBrush, alignToRight);
            }

            protected override bool Commit(CurrencyManager dataSource, int rowNum)
            {
                if (_isEditing)
                {
                    _isEditing = false;
                    SetColumnValueAtRow(dataSource, rowNum, ColumnComboBox.Text);
                }
                return true;
            }

            protected override void ConcedeFocus()
            {
                //Debug.WriteLine("ConcedeFocus");
                base.ConcedeFocus();
            }

            protected override object GetColumnValueAtRow(CurrencyManager source, int rowNum)
            {
                object s = base.GetColumnValueAtRow(source, rowNum);
                DataView dv = (DataView)((DataTable)this.ColumnComboBox.DataSource).DefaultView;
                int rowCount = dv.Count;
                int i = 0;
                //if things are slow, you could order your dataview
                //& use binary search instead of this linear one
                while (i < rowCount)
                {
                    if (s.Equals(dv[i][this.ColumnComboBox.ValueMember]))
                        break;
                    ++i;
                }

                if (i < rowCount)
                    return dv[i][this.ColumnComboBox.DisplayMember];

                return DBNull.Value;
            }

            protected override void SetColumnValueAtRow(CurrencyManager source, int rowNum, object value)
            {
                object s = value;

                DataView dv = (DataView)((DataTable)this.ColumnComboBox.DataSource).DefaultView;
                int rowCount = dv.Count;
                int i = 0;

                //if things are slow, you could order your dataview
                //& use binary search instead of this linear one
                while (i < rowCount)
                {
                    if (s.Equals(dv[i][this.ColumnComboBox.DisplayMember]))
                        break;
                    ++i;
                }
                if (i < rowCount)
                    s = dv[i][this.ColumnComboBox.ValueMember];

                else
                    s = DBNull.Value;
                base.SetColumnValueAtRow(source, rowNum, s);
            }
        }

        public class NoKeyUpCombo : ComboBox
        {
            private const int WM_KEYUP = 0x101;

            protected override void WndProc(ref Message m)
            {
                if (m.Msg == WM_KEYUP)
                {
                    //ignore keyup to avoid problem with tabbing & dropdownlist;
                    return;
                }
                base.WndProc(ref m);
            }
        }
        #endregion
        static int counter = 0;
        public static Dictionary<string, WebServiceConsumer> listWebServiceClient = new Dictionary<string, WebServiceConsumer>();
        public static Dictionary<string, string> listWebServiceName = new Dictionary<string, string>();
        private void button1_Click(object sender, EventArgs e)
        {
            if (!validateInfos())
            {
                MessageBox.Show("Incomplete Information.");
                //this.DialogResult = DialogResult.Cancel;
                return;
            }
            else
            {
                WebServiceConsumer webCons = new WebServiceConsumer();
                if (comboNamespace.SelectedItem == null)
                {
                    webCons.name_space = comboNamespace.Text;
                }
                else
                {
                    webCons.name_space = comboNamespace.SelectedItem.ToString();
                }
                if (comboBox1.SelectedItem == null)
                {
                    webCons.wsdlurl = comboBox1.Text;
                }
                else
                {
                    webCons.wsdlurl = comboBox1.SelectedItem.ToString();
                }

                if (comboBox2.SelectedItem == null)
                {
                    webCons.methodname = comboBox2.Text;
                }
                else
                {
                    webCons.methodname = comboBox2.SelectedItem.ToString();
                }
                this.prepareArgumentList(webCons);
                if (button1.Text == "Add")
                {
                    webCons.name = "webserviceConsumer" + counter.ToString();
                    checkUniqueNess(webCons);
                    listWebServiceName.Add(webCons.name, webCons.name);
                    TreeNode treeNode1 = new TreeNode(webCons.name);
                    newTreeNode = treeNode1;
                    listWebServiceClient.Add(webCons.name, webCons);
                }
                else
                {
                    webCons.name = editName;
                    listWebServiceClient[editName] = webCons;
                }
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }

        private bool validateInfos()
        {
            if (string.IsNullOrEmpty(comboNamespace.Text))
            {
                return false;
            }
            if (string.IsNullOrEmpty(comboBox1.Text))
            {
                return false;
            }
            if (string.IsNullOrEmpty(comboBox2.Text))
            {
                return false;
            }
            return true;
        }

        private void prepareArgumentList(WebServiceConsumer webCons)
        {
            DataTable dtParam = parameterDataSet.Tables[0];
            foreach (DataRow dr in dtParam.Rows)
            {
                Argument arg1 = new Argument();
                foreach (DataColumn dc in dtParam.Columns)
                {
                    if (dc.ColumnName == "paramname")
                    {
                        arg1.argName = dr[dc].ToString();
                    }
                    else if (dc.ColumnName == "paramtype")
                    {
                        arg1.argType = dr[dc].ToString();
                    }
                    else if (dc.ColumnName == "paramvalue")
                    {
                        arg1.argValue = dr[dc].ToString();
                    }
                }
                webCons.argumentList.Add(arg1);
            }
        }
        private void prepareTableArgList(WebServiceConsumer webCons)
        {
            parameterDataSet.Tables.Clear();

        }
        public void checkUniqueNess(WebServiceConsumer webservCons)
        {
            string nameU = webservCons.name;
            if (listWebServiceName.ContainsKey(nameU))
            {
                counter++;
                nameU = "webserviceConsumer" + counter.ToString();
                webservCons.name = nameU;
                checkUniqueNess(webservCons);
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }


        string editName;
        internal bool resetValues(string name)
        {
            if (!listWebServiceClient.ContainsKey(name))
            {
                this.button1.Text = "Add";
                return false;
            }
            WebServiceConsumer webservCons = listWebServiceClient[name];

            this.comboNamespace.Items.Clear();
            this.comboBox1.Items.Clear();
            this.comboBox2.Items.Clear();
            this.comboBox1.Items.Add(webservCons.wsdlurl);
            this.comboBox1.SelectedIndex = 0;
            ReadServiceDescription();

            //this.comboNamespace.Items.Add(webservCons.name_space);
            this.comboNamespace.SelectedItem = webservCons.name_space;

            this.comboBox2.SelectedItem = webservCons.methodname;

            parameterDataSet.Tables.Clear();
            BindData();
            DataTable dtParam = parameterDataSet.Tables[0];
            foreach (Argument arg in webservCons.argumentList)
            {
                DataRow dr = dtParam.NewRow();
                dr["paramname"] = arg.argName;
                dr["paramtype"] = arg.argType;
                dr["paramvalue"] = arg.argValue;
                dtParam.Rows.Add(dr);
            }
            this.parameterGrid.DataSource = dtParam;
            ((System.Data.DataTable)this.parameterGrid.DataSource).DefaultView.AllowNew = false;
            this.button1.Text = "Save";
            editName = name;
            return true;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (editName == null)
            {
                parameterMappings = new Dictionary<string, Dictionary<string, string>>();
                comboBox2.Items.Clear();
                comboNamespace.Items.Clear();
                ReadServiceDescription();
                return;
            }
            if (listWebServiceClient[editName].wsdlurl != comboBox1.Text)
            {
                parameterMappings = new Dictionary<string, Dictionary<string, string>>();
                comboBox2.Items.Clear();
                comboNamespace.Items.Clear();
                ReadServiceDescription();
            }
        }
        void ReadServiceDescription()
        {

            try
            {
                this.Cursor = Cursors.WaitCursor;

                XmlTextReader reader = new XmlTextReader(comboBox1.Text);
                Desc.ServiceDescription service =
                    Desc.ServiceDescription.Read(reader);

                //WSDLParser.WSDLParser wsdl = new EIBFormDesigner.WSDLParser.WSDLParser(service);

                comboNamespace.Items.Add(service.TargetNamespace);
                foreach (Desc.PortType pt in service.PortTypes)
                {
                    //Console.WriteLine("PortType {0}", pt.Name);

                    foreach (Desc.Operation op in pt.Operations)
                    {
                        //Console.WriteLine("\tOperation: {0}", op.Name);
                        comboBox2.Items.Add(op.Name);
                        //service.Services[service.Name].Ports[pt.Name]
                        Desc.Types types = service.Types;
                        System.Xml.Schema.XmlSchema xmlSchema = types.Schemas[0];

                        //string typeName = service.Messages[op.Messages.Input.Message.Name].FindPartByName(op.Messages.Input.Message.Name).Type.Name;
                        System.Web.Services.Description.MessagePart msgPart = service.Messages[op.Messages.Input.Message.Name].Parts["parameters"];
                        string paramElementName = null;
                        if (msgPart != null)
                        {
                            paramElementName = msgPart.Element.Name;
                            parameterMappings.Add(paramElementName, new Dictionary<string, string>());
                        }
                        foreach (object obj in xmlSchema.Items)
                        {
                            System.Xml.Schema.XmlSchemaElement sElement = obj as System.Xml.Schema.XmlSchemaElement;
                            if (sElement != null && sElement.Name == paramElementName)
                            {
                                if (sElement.SchemaType != null && sElement.SchemaType.GetType() == typeof(System.Xml.Schema.XmlSchemaComplexType))
                                {
                                    System.Xml.Schema.XmlSchemaComplexType xComplexType = sElement.SchemaType as System.Xml.Schema.XmlSchemaComplexType;
                                    TraverseParticle(xComplexType.Particle, paramElementName);
                                }
                                break;
                            }
                            /*if (obj is System.Xml.Schema.XmlSchemaComplexType)
                            {
                                System.Xml.Schema.XmlSchemaComplexType xComplexType = obj as System.Xml.Schema.XmlSchemaComplexType;
                                if (xComplexType.Name == )
                                {
                                    MessageBox.Show(xComplexType.Name + " : " + typeName);
                                    TraverseParticle(xComplexType.ContentTypeParticle);

                                }
                            }*/

                        }
                    }
                }


                //wsdl.WSDLParser parser = new wsdl.WSDLParser(service);

                //this.tvwService.Nodes.Add(parser.ServiceNode);
                //this.cboURL.Items.Add(cboURL.Text);
                //http://soap.amazon.com/schemas2/AmazonWebServices.wsdl 
                //http://glkev.webs.innerhost.com/glkev_ws/weatherfetcher.asmx?wsdl
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message.ToString());
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }

        }

        /*void Start()
        {
            XmlSchemaComplexType complexType;
            foreach (XmlSchemaType type in xs.SchemaTypes.Values)
            {
                complexType = type as XmlSchemaComplexType;
                if (complexType != null)
                    TraverseParticle(complexType.ContentTypeParticle);
            }

            foreach (XmlSchemaElement el in xs.Elements.Values)
                TraverseParticle(el);
        }*/

        void TraverseParticle(XmlSchemaParticle particle, string paramElementName)
        {
            if (particle != null)
            {
                if (particle is XmlSchemaSequence)
                {
                    XmlSchemaSequence seqxml = particle as XmlSchemaSequence;
                    foreach (object obj in seqxml.Items)
                    {
                        if (obj is XmlSchemaElement)
                        {
                            XmlSchemaElement elem = obj as XmlSchemaElement;
                            string name = elem.Name;
                            if (elem.SchemaTypeName != null && !string.IsNullOrEmpty(elem.SchemaTypeName.Name))
                            {
                                parameterMappings[paramElementName].Add(name, elem.SchemaTypeName.Name);
                            }
                        }
                    }

                }
                if (particle is XmlSchemaElement)
                {
                    XmlSchemaElement elem = particle as XmlSchemaElement;

                    if (elem.RefName.IsEmpty)
                    {
                        XmlSchemaType type = (XmlSchemaType)elem.ElementSchemaType;
                        XmlSchemaComplexType complexType = type as XmlSchemaComplexType;
                        if (complexType != null && complexType.Name == null)
                            TraverseParticle(complexType.ContentTypeParticle, null);
                    }
                }
                else if (particle is XmlSchemaGroupBase)
                {
                    //xs:all, xs:choice, xs:sequence 
                    //XmlSchemaGroupBase baseParticle = particle as XmlSchemaGroupBase;
                    //foreach (XmlSchemaParticle subParticle in baseParticle.Items)
                    //TraverseParticle(subParticle,null);
                }
            }
        }

        //Operation Method selection changed.
        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            string operationName = comboBox2.SelectedItem.ToString();
            parameterDataSet.Tables.Clear();
            BindData();
            foreach (string key in parameterMappings[operationName].Keys)
            {
                string typeName = parameterMappings[operationName][key];
                string parameterName = key;
                DataTable dtParam = parameterDataSet.Tables[0];
                DataRow dr = dtParam.NewRow();
                dr["paramname"] = parameterName;
                dr["paramtype"] = typeName;
                dr["paramvalue"] = "";
                dtParam.Rows.Add(dr);
                this.parameterGrid.DataSource = dtParam;
            }
            ((System.Data.DataTable)this.parameterGrid.DataSource).DefaultView.AllowNew = false;
        }


    }
    public class WebServiceConsumer
    {
        public string name;
        public string name_space;
        public string wsdlurl;
        public string methodname;
        public List<Argument> argumentList;
        public WebServiceConsumer()
        {
            argumentList = new List<Argument>();
        }
        /*

         * <WebServiceConsumers>
 <WebServiceConsumer name="WebServiceConsumerOne" namespace="">
   <WSDLUrl>http://api.google.com/GoogleSearch.wsdl</WSDLUrl>
   <Operation name="doGetCachedPage">
  <argument name="key" type="String" value="2"/>
  <argument name="url" type="String" value="2"/>
   </Operation>
 </WebServiceConsumer>
 <WebServiceConsumer name="WebServiceConsumerOne" namespace="">
   <WSDLUrl>http://api.google.com/GoogleSearch.wsdl</WSDLUrl>
   <Operation name="doGetCachedPage">
  <argument name="key" type="String" value="2"/>
  <argument name="url" type="String" value="2"/>
   </Operation>
 </WebServiceConsumer>
</WebServiceConsumers>
        */
        internal void Serialize(XmlNode parentNode)
        {
            XmlDocument doc = parentNode.OwnerDocument;
            XmlElement xmlWebServiceConsumer = doc.CreateElement("WebServiceConsumer");

            XmlAttribute attrName = doc.CreateAttribute("name");
            attrName.Value = this.name;
            xmlWebServiceConsumer.Attributes.Append(attrName);

            XmlAttribute attrNameSpace = doc.CreateAttribute("namespace");
            attrNameSpace.Value = this.name_space;
            xmlWebServiceConsumer.Attributes.Append(attrNameSpace);

            XmlElement xmlWSDLUrl = doc.CreateElement("WSDLUrl");
            xmlWSDLUrl.InnerText = this.wsdlurl;
            xmlWebServiceConsumer.AppendChild(xmlWSDLUrl);

            XmlElement xmlOperation = doc.CreateElement("Operation");
            XmlAttribute attrOpName = doc.CreateAttribute("name");
            attrOpName.Value = this.methodname;
            xmlOperation.Attributes.Append(attrOpName);

            foreach (Argument arg in this.argumentList)
            {
                XmlElement xmlargument = doc.CreateElement("argument");
                XmlAttribute attrargName = doc.CreateAttribute("name");
                attrargName.Value = arg.argName;
                xmlargument.Attributes.Append(attrargName);
                XmlAttribute attrargType = doc.CreateAttribute("type");
                attrargType.Value = arg.argType;
                xmlargument.Attributes.Append(attrargType);
                XmlAttribute attrargvalue = doc.CreateAttribute("value");
                attrargvalue.Value = arg.argValue;
                xmlargument.Attributes.Append(attrargvalue);
                xmlOperation.AppendChild(xmlargument);
            }

            xmlWebServiceConsumer.AppendChild(xmlOperation);
            parentNode.AppendChild(xmlWebServiceConsumer);
        }
        /*
         * <WebServiceConsumer name="webserviceConsumer0">
    <WSDLUrl>s</WSDLUrl>
    <Operation name="s">
      <argument name="s" type="Integer" value="s" />
    </Operation>
  </WebServiceConsumer>
         */
        internal void Deserialize(XmlNode xmlNode)
        {
            try
            {
                this.name = xmlNode.Attributes["name"].Value;
                if (xmlNode.Attributes["namespace"] != null)
                {
                    this.name_space = xmlNode.Attributes["namespace"].Value;
                }
                else
                {
                    this.name_space = "";
                }
                foreach (XmlNode xmlChildNode in xmlNode.ChildNodes)
                {
                    if (xmlChildNode.Name == "WSDLUrl")
                    {
                        this.wsdlurl = xmlChildNode.InnerText;
                    }
                    if (xmlChildNode.Name == "Operation")
                    {
                        this.methodname = xmlChildNode.Attributes["name"].Value;
                        foreach (XmlNode xmlArgNode in xmlChildNode.ChildNodes)
                        {
                            Argument argument1 = new Argument();
                            argument1.argName = xmlArgNode.Attributes["name"].Value;
                            argument1.argType = xmlArgNode.Attributes["type"].Value;
                            argument1.argValue = xmlArgNode.Attributes["value"].Value;
                            this.argumentList.Add(argument1);
                        }
                    }
                }
            }
            catch
            {

            }
        }
    }
    public class Argument
    {
        public string argName;
        public string argType;
        public string argValue;
    }
}