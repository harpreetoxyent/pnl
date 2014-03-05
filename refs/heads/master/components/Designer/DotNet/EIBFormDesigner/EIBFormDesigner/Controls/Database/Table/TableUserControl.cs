using System;
using System.Data;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using System.Xml.Schema;
using System.Collections;
using System.Xml;
using System.Reflection;
using System.Text.RegularExpressions;
using System.Drawing.Design;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer.Database;
using EIBFormDesigner.Database.Table;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Database
{
    public partial class TableUserControl : UserControl, ITool, IEIBControl
    {
        private string dataPatternName;
        private string dataTableName;
        private string isMandatory;
        private string isForm;
        private bool m2m;

        /// <summary>
        /// /
        /// 
        /// </summary>
        private string onEventCreateValue;
        private string onEventEditValue;
        private string onEventUpdateValue;
        public string OnEventCreateValue
        {
            get
            {
                return onEventCreateValue;
            }
            set
            {
                onEventCreateValue = value;
            }
        }

        private string onChangeValue;
        public string OnChange
        {
            get
            {
                return onChangeValue;
            }
            set
            {
                onChangeValue = value;
            }
        }
        private string onOKValue;
        public string OnOK
        {
            get
            {
                return onOKValue;
            }
            set
            {
                onOKValue = value;
            }
        }
        public string OnEventEditValue
        {
            get
            {
                return onEventEditValue;
            }
            set
            {
                onEventEditValue = value;
            }
        }
        public string OnEventUpdateValue
        {
            get
            {
                return onEventUpdateValue;
            }
            set
            {
                onEventUpdateValue = value;
            }
        }

        public bool M2M
        {
            get
            {
                return m2m;
            }
            set
            {
                chkMToM.Checked = value;
                m2m = value;
            }
        }

        internal List<EIBTableConnectorBase> tableConnectorList = new List<EIBTableConnectorBase>();

        private List<DataMapping> dataMappings;

        
        public List<DataMapping> DataMappings
        {
            get { return dataMappings; }
            set { dataMappings = value; }
        }

        private bool visible = true;public bool getVisibility() { return visible;}[Browsable(true)] public new bool Visible { get { return base.Visible; } set { visible = value; base.Visible = value; } }  private List<string> visibleTo =new List<string>(new string[] { "All" });
        [Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"), EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)), TypeConverter(typeof(VisibleToConverter))]
        public List<string> VisibleTo { get { return visibleTo; } set { visibleTo = value; } }

        bool draggable;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Draggable property of control"), Category("Appearance")]
        public bool Draggable
        {
            get
            {
                return draggable;
            }
            set
            {
                draggable = value;
            }
        }

        bool droppable;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Draggable property of control"), Category("Appearance")]
        public bool Droppable
        {
            get
            {
                return droppable;
            }
            set
            {
                droppable = value;
            }
        }

        ControlPosition position;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Position of the Control"), Category("Appearance")]
        public ControlPosition Position
        {
            get
            {
                return position;
            }
            set
            {
                position = value;
            }
        }

        private Padding controlMargin = new Padding(0);[DisplayName("Margin"), Browsable(true), Category("Data"), Description("Set the Margin of a control.") ] public Padding ControlMargin { get { return controlMargin; } set { controlMargin = value; } } [Browsable(false)] public new Padding Margin { get { return base.Margin; } set { base.Margin = value; } } string use;
        [Browsable(true), DefaultValue(""), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the User defined class for control"), Category("Appearance")]
        public string Use
        {
            get
            {
                return use;
            }
            set
            {
                use = value;
            }
        }

        private string dataFieldName;
        
        public string DataPatternName
        {
            get
            {
                return dataPatternName;
            }
            set
            {
                dataPatternName = value;
            }
        }
        
        public string DataTableName
        {
            get
            {
                return dataTableName;
            }
            set
            {
                dataTableName = value;
            }
        }

        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
      TypeConverter(typeof(EIBFormDesigner.Property.IsMandatory)), Description("Sets the mandatory field"), Category("Data")]

        public string IsMandatory
        {
            get
            {

                return isMandatory;
            }
            set
            {

                isMandatory = value;

            }
        }

        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
  TypeConverter(typeof(EIBFormDesigner.Property.IsForm)), Description("Sets the Form Status"), Category("Data")]

        public string IsForm
        {
            get
            {

                return isForm;
            }
            set
            {

                isForm = value;

            }
        }

        public string DataFieldName
        {
            get
            {
                return dataFieldName;
            }
            set
            {
                dataFieldName = value;
            }
        }

        private DataSet databaseDataSet;
        public DataSet DatabaseDataSet
        {
            get
            {
                return databaseDataSet;
            }
            set
            {
                databaseDataSet = value;
            }
        }

        private DataTable dataTable;
        public DataTable TableData
        {
            get
            {
                return dataTable;
            }
            set
            {
                dataTable = value;
            }
        }

        //	Data types valid for XML file
        int width = 360;
        int height = 300;
        int reducedWidth = 200;
        int reducedHeight = 160;
        internal static int counter = 0;
        //	 Current item being edited (-1 = new item)
        private int m_intEditItem = -1;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        public string DefaultValue
        {
            get
            {
                return defaultValue;
            }
            set
            {
                defaultValue = value;
            }
        }
        public string OnClickValue
        {
            get
            {
                return onClickValue;
            }
            set
            {
                onClickValue = value;
            }
        }

        new public string OnDoubleClick
        {
            get
            {
                return onDoubleClick;
            }
            set
            {
                onDoubleClick = value;
            }
        }

        public string EnteringValue
        {
            get
            {
                return enteringValue;
            }
            set
            {
                enteringValue = value;
            }
        }

        public string ExitingValue
        {
            get
            {
                return exitingValue;
            }
            set
            {
                exitingValue = value;
            }
        }

        private string onFocus; public string OnFocus { get { return onFocus; } set { onFocus = value; } } private string onBlur; public string OnBlur { get { return onBlur; } set { onBlur = value; } } private string onDrop;
        public string OnDrop
        {
            get
            {
                return onDrop;
            }
            set
            {
                onDrop = value;
            }
        }

        private XmlNode parentXmlNode;
        public XmlNode ParentXmlNode
        {
            get
            {
                return parentXmlNode;
            }
            set
            {
                parentXmlNode = value;
            }
        }
        private string controlName;
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of control"), Category("Appearance")]
        public string ControlName
        {
            get
            {
                return controlName;
            }
            set
            {
                controlName = value;
            }
        }
        public string ControlType
        {
            get
            {
                return "table";
            }
        }		//	Data types valid for XML file
        private string[] DataTypes =
		{
			"Boolean",
			"Byte",
			DatabaseConstants.CharType,
			"String",
			"Decimal",
			"Double",
			"Int16",
			"Int32",
			"SByte",
			"DateTime",
			"Single",
			"TimeSpan",
			"UInt16",
			"UInt32",
            "LongText",
            "TimeStamp"
        };


        //	Tooltip for buttons
        private ToolTip m_ToolTip = new ToolTip();

        public TableUserControl()
        {
            InitializeComponent();
            StyleHelper.DisableFlicker(lvDatabase);
            this.Size = new Size(width, height);
            gbxDataBase.Size = new Size(340, 280);
            this.panel3.Visible = false;
            m2m = false;
            //setStyle();
        }
        public void setStyle()
        {
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            this.UpdateStyles();
        }
        public void afterDragDrop()
        {
            this.panel3.Visible = true;
            this.button1.Enabled = false;
            this.tableName.Enabled = true;
        }
        private void FormCreateXMLDB_Load(object sender, EventArgs e)
        {
            FormCreateXMLDBInitialise();
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = "table" + counter;
            this.Text = "table" + counter;
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.controlName = this.Name;
            }
            counter++;
        }

        private void FormCreateXMLDBInitialise()
        {
            //	Put data types in combo
            foreach (string strType in DataTypes)
                cboFieldType.Items.Add(strType);

            //	ToolTips
            m_ToolTip.SetToolTip(btnAddField, "Add or Update Field in List View");
            m_ToolTip.SetToolTip(btnRemoveField, "Remove Selected Field from List View");
        }

        private void btnAddField_Click(object sender, EventArgs e)
        {
            ListViewItem lvItem;

            /*if (txtFieldName.Text == "" || cboFieldType.Text == "")
            {
                ShowMessage("Field Name and Field Type Required", true);
                return;
            }*/


            if (txtFieldName.Text == "" && cboFieldType.Text == "")
            {
                MessageBox.Show("Field Name and Field Type Required");
                return;
            }
            if (txtFieldName.Text == "" )
            {
                MessageBox.Show("Field Name Required");
                return;
            }
            if (cboFieldType.Text == "")
            {
                MessageBox.Show("Field Type Required");
                return;
            }
            Regex reg = new Regex("[a-zA-Z0-9_]+", RegexOptions.None);
            Match mat = reg.Match(txtFieldName.Text);
            if (mat.Value.Length != txtFieldName.Text.Length)
            {
                MessageBox.Show("Column Name is not valid.");
                txtFieldName.Select(0, txtFieldName.Text.Length);
                txtFieldName.Focus();
                return;
            }


            if (m_intEditItem != -1)
            {
                lvItem = lvDatabase.Items[m_intEditItem];
                //if (lvItem.SubItems[3].Text == "Yes")
                //{
                //    MessageBox.Show("Cannot Update primary key constraint");
                //}
                //else
                //{
                    
                    lvItem.Text = txtFieldName.Text;
                    lvItem.SubItems[1].Text = cboFieldType.Text;
                    if (chkAutoIncrement.Enabled && chkAutoIncrement.Checked)
                        lvItem.SubItems[2].Text = "Yes";
                    else
                        lvItem.SubItems[2].Text = "No";
                    if (chkPrimary.Enabled && chkPrimary.Checked)
                        lvItem.SubItems[3].Text = "Yes";
                    else
                        lvItem.SubItems[3].Text = "No";
                    if (numberDigits.Visible)
                    {
                        if (!numberDigits.Text.Trim().Equals(""))
                        {
                            try
                            {
                                int numberOfDigits = Int32.Parse(numberDigits.Text.Trim());
                                lvItem.SubItems[4].Text = numberOfDigits.ToString();
                            }
                            catch (Exception ex)
                            {
                                MessageBox.Show("Format Exception in char Size");
                                return;
                            }
                        }
                    }
                    if (chkPrimary.Checked)
                    {
                        lvItem.SubItems[5].Text = "Not Null";
                    }
                    else
                    {
                        lvItem.SubItems[5].Text = (chkNotNull.Checked ? "Not Null" : "Null");
                    }

                //}
            }
            else
            {
                lvItem = new ListViewItem(txtFieldName.Text);
                lvItem.SubItems.Add(cboFieldType.Text);
                if (chkAutoIncrement.Enabled && chkAutoIncrement.Checked)
                    lvItem.SubItems.Add("Yes");
                else
                    lvItem.SubItems.Add("No");
                if (chkPrimary.Enabled && chkPrimary.Checked)
                    lvItem.SubItems.Add("Yes");
                else
                    lvItem.SubItems.Add("No");
                if (numberDigits.Visible)
                {
                    if (!numberDigits.Text.Trim().Equals(""))
                    {
                        try
                        {
                            int numberOfDigits = Int32.Parse(numberDigits.Text.Trim());
                            lvItem.SubItems.Add(numberOfDigits.ToString());
                        }
                        catch (Exception ex)
                        {
                            MessageBox.Show("Format Exception in char Size");
                            return;
                        }
                    }
                }
                else
                {
                    lvItem.SubItems.Add("");
                }
                if (chkNotNull.Enabled)
                    if (chkNotNull.Checked)
                        lvItem.SubItems.Add("Not Null");
                    else
                        lvItem.SubItems.Add("Null");
                else
                    lvItem.SubItems.Add("Not Null");
                lvDatabase.Items.Add(lvItem);
            }

                ClearCurrentField();
            

        }


        private void btnRemoveField_Click(object sender, EventArgs e)
        {
            if (lvDatabase.SelectedItems.Count != 1)
            {
                ShowMessage("No Field Selected To Remove", true);
                return;
            }

            ListViewItem lvItem = lvDatabase.SelectedItems[0];
            if (!(lvItem.SubItems[3].Text == "Yes"))
            {
                lvDatabase.Items.Remove(lvItem);
                ClearCurrentField();
            }
            else
            {
                MessageBox.Show("cannot remove primary key constraint ");
            }
        }

        private void cboFieldType_SelectedIndexChanged(object sender, EventArgs e)
        {
            chkPrimary.Enabled = true;
            numberDigits.Visible = false;
            switch (cboFieldType.Text)
            {
                case "Int16":
                case "Int32":
                case "Int64":
                case "UInt16":
                case "UInt32":
                case "SByte":
                case "UInt64":
                    chkAutoIncrement.Enabled = true;
                    break;
                case "Char":
                    numberDigits.Visible = true;
                    numberDigits.Text = "1";
                    break;
                case "String":
                    numberDigits.Visible = true;
                    numberDigits.Text = "50";
                    break;
                case "LongText":
                    chkPrimary.Enabled = false;
                    chkAutoIncrement.Enabled = false;
                    break;
                default:
                    chkAutoIncrement.Enabled = false;
                    break;
            }
        }

        private void ClearCurrentField()
        {
            txtFieldName.Text = "";
            cboFieldType.SelectedIndex = -1;
            chkAutoIncrement.Checked = false;
            chkAutoIncrement.Enabled = false;
            chkPrimary.Checked = false;
            chkPrimary.Enabled = true;
            chkNotNull.Enabled = true;
            chkNotNull.Checked = false;
            m_intEditItem = -1;
            btnAddField.Text = "Add Field";
        }


        private string GetTypeFromLV(string strName)
        {
            string strType = "";

            foreach (ListViewItem lvItem in lvDatabase.Items)
            {
                if (lvItem.Text == strName)
                {
                    strType = lvItem.SubItems[1].Text;
                    break;
                }
            }
            return strType;
        }

        private void lvDatabase_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (lvDatabase.SelectedItems.Count != 1)
                return;
                ListViewItem lvItem = lvDatabase.SelectedItems[0];
                m_intEditItem = lvItem.Index;
                txtFieldName.Text = lvItem.Text;
                cboFieldType.Text = lvItem.SubItems[1].Text;
                if (cboFieldType.Text == DatabaseConstants.LongTextType)
                {
                    chkPrimary.Enabled = false;
                    chkAutoIncrement.Enabled = false;
                }
                else
                {
                    chkPrimary.Enabled = true;
                }
                chkAutoIncrement.Checked = (lvItem.SubItems[2].Text == "Yes");
                chkPrimary.Checked = (lvItem.SubItems[3].Text == "Yes");
                chkNotNull.Enabled = !chkPrimary.Checked;
                if (numberDigits.Visible)
                {
                    if (lvItem.SubItems[4] != null)
                    {
                        numberDigits.Text = lvItem.SubItems[4].Text;
                    }
                }
                chkNotNull.Checked = (lvItem.SubItems[5].Text == "Not Null");
                btnAddField.Text = "Update Field";


        }


        [DllImport("User32")]
        public static extern bool MessageBeep(int SoundType);

        private void ShowMessage(string strMes, bool blnBeep)
        {
            if (blnBeep)
                MessageBeep(-1);
        }

        private string TypePrefix(string strType)
        {
            string strPrefix = strType.Replace("System.", "Convert.To");
            return strPrefix + "(";
        }

        private void doneButon_Click(object sender, EventArgs e)
        {
            DataTable DataTableNew = null;
            try
            {
                if (tableName.Text.Trim().Equals(""))
                {
                    this.gbxDataBase.ForeColor = Color.Red;
                    return;
                }
                else
                {
                    Regex reg = new Regex("[a-zA-Z0-9]+", RegexOptions.None);
                    Match mat = reg.Match(tableName.Text);
                    if (mat.Value.Length != tableName.Text.Length)
                    {
                        MessageBox.Show("Table Name is not valid.");
                        tableName.Select(0, tableName.Text.Length);
                        tableName.Focus();
                        return;
                    }
                    if (tableName_Leave(null, null))
                    {
                        return;
                    }
                    bool isNewTable = false;
                    DataColumn DataColumnNew;
                    string strName, strType;
                    Type dataType;
                    List<DataColumn> primaryListArray = new List<DataColumn>();
                    if (this.dataTable != null)
                    {
                        dataTable.TableName = tableName.Text;
                        DataTableNew = this.DatabaseDataSet.Tables[tableName.Text];
                    }
                    else if (this.databaseDataSet != null && this.databaseDataSet.Tables[tableName.Text] == null)
                    {
                        DataTableNew = new DataTable(tableName.Text);
                        isNewTable = true;
                    }
                    else
                    {
                        DataTableNew = this.databaseDataSet.Tables[tableName.Text];
                    }
                    this.gbxDataBase.ForeColor = SystemPens.ControlText.Color;
                    foreach (ListViewItem lvItem in lvDatabase.Items)
                    {
                        strName = lvItem.Text;
                        strType = lvItem.SubItems[1].Text;
                        //Append System. to make them .net types
                        if (strType.ToLower().Equals(DatabaseConstants.LongTextType.ToLower()))
                        {
                            strType = "String";
                        }
                        if (strType.Equals("TimeStamp"))
                        {
                            //strType = "DateTimeOffset";
                            dataType = Type.GetType("EIBFormDesigner.Designer.Database." + strType);
                        }
                        else
                        {
                            dataType = Type.GetType("System." + strType);
                        }

                        if (DataTableNew.Columns[strName] == null)
                        {
                            DataColumnNew = new DataColumn(strName, dataType);
                            if (dataType.Name.Equals(DatabaseConstants.CharType))
                            {
                                if (!(lvItem.SubItems[4].Text.Trim().Equals("")))
                                {
                                    string charDigit = lvItem.SubItems[4].Text.Trim();
                                    DataColumnNew.Caption = charDigit;
                                }
                            }
                            if (dataType.Name.Equals("String"))
                            {
                                if (!(lvItem.SubItems[4].Text.Trim().Equals("")))
                                {
                                    string charDigit = lvItem.SubItems[4].Text.Trim();
                                    DataColumnNew.Caption = charDigit;
                                }
                            }
                            DataColumnNew.AutoIncrement = (lvItem.SubItems[2].Text == "Yes");
                            DataColumnNew.Unique = (lvItem.SubItems[3].Text == "Yes");
                            DataTableNew.Columns.Add(DataColumnNew);
                        }
                        else
                        {
                            DataColumnNew = DataTableNew.Columns[strName];
                            if (dataType.Name.Equals(DatabaseConstants.CharType))
                            {
                                if (!(lvItem.SubItems[4].Text.Trim().Equals("")))
                                {
                                    string charDigit = lvItem.SubItems[4].Text.Trim();
                                    DataColumnNew.Caption = charDigit;
                                }
                            }
                            DataColumnNew.DataType = dataType;
                            DataColumnNew.AutoIncrement = (lvItem.SubItems[2].Text == "Yes");
                        }
                        if (lvItem.SubItems[3].Text == "Yes")
                        {
                            DataColumnNew.Unique = true;
                            primaryListArray.Add(DataColumnNew);
                        }
                        if (lvItem.SubItems[3].Text == "No")
                        {
                            //Column was previously defined primary and now being
                            //edited otherwise
                            bool deleteConstraint = false;
                            string constraintName = "";
                            if (DataColumnNew.Unique == true)
                            {
                                ConstraintCollection consRelation = DataColumnNew.Table.Constraints;
                                foreach (Constraint constraint in consRelation)
                                {
                                    //remove constraint
                                    if (constraint is ForeignKeyConstraint)
                                    {
                                        ForeignKeyConstraint uniqueCS = (ForeignKeyConstraint)constraint;
                                        // Get the Columns as an array.
                                        DataColumn[] colArray;
                                        colArray = uniqueCS.Columns;
                                        // Print each column's name.
                                        for (int i = 0; i < colArray.Length; i++)
                                        {
                                            if (colArray[i].ColumnName.Equals(DataColumnNew.ColumnName))
                                            {
                                                //remove Constraint
                                                deleteConstraint = true;
                                                constraintName = constraint.ConstraintName;
                                            }
                                        }
                                    }
                                }

                            }
                            if (deleteConstraint)
                            {
                                DataColumnNew.Table.Constraints.Remove(constraintName);
                            }
                            deleteConstraint = false;
                            if (DataColumnNew.Unique == true)
                            {
                                ConstraintCollection consRelation = DataColumnNew.Table.Constraints;
                                foreach (Constraint constraint in consRelation)
                                {
                                    //remove constraint
                                    if (constraint is UniqueConstraint)
                                    {
                                        UniqueConstraint uniqueCS = (UniqueConstraint)constraint;
                                        // Get the Columns as an array.
                                        DataColumn[] colArray;
                                        colArray = uniqueCS.Columns;
                                        // Print each column's name.
                                        bool isBreak = false;
                                        for (int i = 0; i < colArray.Length; i++)
                                        {
                                            if (colArray[i].ColumnName.Equals(DataColumnNew.ColumnName))
                                            {
                                                //remove Constraint
                                                deleteConstraint = true;
                                                constraintName = constraint.ConstraintName;
                                                isBreak = true;
                                                break;
                                            }
                                        }
                                        if (isBreak)
                                            break;
                                    }
                                }

                            }
                            if (deleteConstraint)
                            {
                                bool isInPK = false;
                                List<DataColumn> dcc = new List<DataColumn>();
                                foreach(DataColumn dc in DataColumnNew.Table.PrimaryKey)
                                {

                                    if (dc.ColumnName == DataColumnNew.ColumnName)
                                    {
                                        isInPK = true;
                                        //break;
                                    }
                                    else
                                    {
                                        dcc.Add(dc);
                                    }
                                }
                                if(isInPK)
                                {
                                    DataColumnNew.Table.PrimaryKey = (DataColumn[])dcc.ToArray();
                                }
                                DataColumnNew.Table.Constraints.Remove(constraintName);
                            }
                            //Constraints have been already deleted so delete the column
                            DataColumnNew.Unique = false;
                            if (primaryListArray.Contains(DataColumnNew))
                            {
                                primaryListArray.Remove(DataColumnNew);
                            }
                            
                        }
                        if(lvItem.SubItems[5].Text == "Not Null")
                        {
                            if (!(lvItem.SubItems[3].Text == "Yes"))
                            {
                                DataColumnNew.AllowDBNull = false;
                            }
                        }
                        if (lvItem.SubItems[5].Text == "Null")
                        {
                            DataColumnNew.AllowDBNull = true;
                        }
                    }
                    DataTableNew.PrimaryKey = primaryListArray.ToArray();
                    this.dataTable = DataTableNew;
                    if (isNewTable)
                    {
                        this.databaseDataSet.Tables.Add(DataTableNew);
                    }
                    this.Name = tableName.Text;
                    //resize to smaller size
                    this.panel3.Visible = false;
                    this.Size = new Size(reducedWidth, reducedHeight);
                    gbxDataBase.Size = new Size(269, 239); 
                }
                List<string> columnNamesToRemove = new List<string>();
                foreach (DataColumn column in DataTableNew.Columns)
                {
                    //ListViewItem lvItem in lvDatabase.Items
                    string strName = column.ColumnName;
                    bool isPresent = false;
                    foreach (ListViewItem lvItem in lvDatabase.Items)
                    {
                        if (strName == lvItem.Text)
                        {
                            isPresent = true;
                        }
                    }
                    if (!isPresent)
                    {
                        columnNamesToRemove.Add(strName);
                    }
                }
                foreach (string strName in columnNamesToRemove)
                {
                    DataColumn deletedColumn = DataTableNew.Columns[strName];
                    DataTableNew.Columns.Remove(deletedColumn);

                }
            }
            catch (Exception exp)
            {
                MessageBox.Show(exp.Message.ToString());
            }
            button1.Enabled = true;
            this.tableName.Enabled = false;
            if (editTableNameList.ContainsKey(this.Name))
            {
                editTableNameList.Remove(this.Name);
            }
            this.controlName = this.tableName.Text;
            //this.GetType().GetEvent("MouseClick").GetRaiseMethod(true)
            //this.RaiseMouseEvent("MouseClick", new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
            EIBFormDesigner.Designer.FormDesigner.eventManager.handleControlClick(this, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
            EIBFormDesigner.UIEventManager.pickBox[(IEIBControl)this].SelectControl(this, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
            
        }

        void TableUserControl_DoubleClick(object sender, System.EventArgs e)
        {
            if (this.Size.Height <= reducedHeight)
            {
                this.panel3.Visible = true;
                this.Size = new Size(width, height);
            }
            else if (this.Size.Height >= height)
            {
                this.panel3.Visible = false;
                this.Size = new Size(reducedWidth, reducedHeight);
                gbxDataBase.Size = new Size(269, 239); 
            }
        }

        private void chkPrimary_CheckedChanged(object sender, EventArgs e)
        {
            chkNotNull.Checked = false;
            chkNotNull.Enabled = !chkPrimary.Checked;
            if(cboFieldType.SelectedItem != null)
            {
                string selectedType = cboFieldType.SelectedItem.ToString();
                if (selectedType == "Int16" || selectedType == "Int32" || selectedType == "UInt16" || selectedType == "SByte" || selectedType == "UInt32" || selectedType == "SByte" || selectedType == "Byte")
                {
                    if (chkPrimary.Checked)
                    {
                        chkAutoIncrement.Checked = true;
                    }
                    else
                    {
                        chkAutoIncrement.Checked = false;
                    }
                }
            }
           
        }
        public static Dictionary<string,string> editTableNameList = new Dictionary<string,string>();
        private void button1_Click(object sender, EventArgs e)
        {
            if (!editTableNameList.ContainsKey(this.Name))
            {
                editTableNameList.Add(this.Name,this.tableName.Text);
            }
            TableUserControl_DoubleClick(null, null);
            ((Button)sender).Enabled = false;
            tableName.Enabled = true;
            gbxDataBase.Size = new Size(340, 280);
            EIBFormDesigner.Designer.FormDesigner.eventManager.handleControlClick(this, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
            EIBFormDesigner.UIEventManager.pickBox[(IEIBControl)this].SelectControl(this, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
        }

        private bool tableName_Leave(object sender, EventArgs e)
        {
            bool flag = false;
            if (this.tableName.Enabled)
            {
                if (string.IsNullOrEmpty(this.tableName.Text) || this.tableName.Text.Contains(" "))
                {
                    MessageBox.Show("Table name must not be blank or does not contain blank space.");
                    this.tableName.SelectionStart = 0;
                    this.tableName.SelectionLength = this.tableName.Text.Length;
                    this.tableName.Focus();
                    return true;
                }
                if (this.tableName.Enabled == true && !editTableNameList.ContainsKey(this.Name) && this.databaseDataSet.Tables[tableName.Text] != null)
                {
                    MessageBox.Show("Table with this name already exist.");
                    this.tableName.SelectionStart = 0;
                    this.tableName.SelectionLength = this.tableName.Text.Length;
                    this.tableName.Focus();
                    flag = true;
                }
                if (editTableNameList.ContainsKey(this.Name) && this.databaseDataSet.Tables[tableName.Text] != null && this.Name != this.tableName.Text)
                {
                    MessageBox.Show("Table with this name already exist.");
                    this.tableName.SelectionStart = 0;
                    this.tableName.SelectionLength = this.tableName.Text.Length;
                    this.tableName.Focus();
                    flag = true;
                }
               // else if(editTableNameList.ContainsKey(this.Name) && 
                //{

                //}
            }
            return flag;
        }

        private void chkAutoIncrement_CheckedChanged(object sender, EventArgs e)
        {
            if (cboFieldType.SelectedItem != null)
            {
                string selectedType = cboFieldType.SelectedItem.ToString();
                if (selectedType == "Int16" || selectedType == "Int32" || selectedType == "UInt16" || selectedType == "SByte" || selectedType == "UInt32" || selectedType == "SByte" || selectedType == "Byte")
                {
                    if (chkPrimary.Checked)
                    {
                        bool isAutoIncrement = chkAutoIncrement.Checked;
                        if (!isAutoIncrement)
                        {
                            DialogResult result = MessageBox.Show("Do you want to uncheck it.","Nolis App Designer",MessageBoxButtons.YesNo);
                            if (result == DialogResult.No)
                            {
                                chkAutoIncrement.Checked = true;
                            }
                        }
                    }
                }
            }
        }

        private void chkMToM_CheckedChanged(object sender, EventArgs e)
        {
            m2m = chkMToM.Checked;
        }
    }

    public class StyleHelper
    {
        public static void DisableFlicker(System.Windows.Forms.Control ctrl)
        {
            MethodInfo method = ctrl.GetType().GetMethod("SetStyle", BindingFlags.Instance | BindingFlags.NonPublic);
            if (method != null)
            {
                method.Invoke(ctrl, new object[] { ControlStyles.OptimizedDoubleBuffer | ControlStyles.AllPaintingInWmPaint, true });
            }
        }
    }

}




















