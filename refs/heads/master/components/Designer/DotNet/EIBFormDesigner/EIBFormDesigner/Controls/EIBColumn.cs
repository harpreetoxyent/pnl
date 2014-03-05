using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.Collections;
using System.Globalization;
using System.Reflection;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.Xml;
using System.ComponentModel;
using EIBFormDesigner.XML;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Controls.Grid.Collections;
using System.Drawing.Design;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls.Common;

namespace EIBFormDesigner.Controls
{
    /// <summary>
    /// A column in a row control. Does all the vertical spacing.
    /// </summary>
    [ToolboxItem(false)]
    public class EIBColumn : System.Windows.Forms.Panel, ITool, IEIBControl
    {
        internal static int counter = 0;
        int width = 50;
        int height = 30;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private string dataPatternName;
        private string isMandatory;
        private string isForm;
        private string dataTableName;
        private string dataFieldName;

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
        private List<DataMapping> dataMappings;

        [Browsable(true), Description("Set DataMappings"), Category("Data"),
        EditorAttribute(typeof(DataMappingEditor), typeof(UITypeEditor)),
        TypeConverter(typeof(DataMappingConverter))
        ]
        public List<DataMapping> DataMappings
        {
            get { return dataMappings; }
            set { dataMappings = value; }
        }

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

        bool sortable;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Sortable property of control"), Category("Appearance")]
        public bool Sortable
        {
            get
            {
                return sortable;
            }
            set
            {
                sortable = value;
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

        private Padding controlMargin = new Padding(0);
        [DisplayName("Margin"), Browsable(true), Category("Data"), Description("Set the Margin of a control.")]
        public Padding ControlMargin { get { return controlMargin; } set { controlMargin = value; } }

        [Browsable(false)]
        public new Padding Margin { get { return base.Margin; } set { base.Margin = value; } }
        string use;
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

        [Browsable(false)]
        public override DockStyle Dock
        {
            get
            {
                return base.Dock;
            }
            set
            {
                base.Dock = value;
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

        private bool visible = true; public bool getVisibility() { return visible; }
        [Browsable(false)]
        public new bool Visible
        {
            get { return visible; }
            set { visible = value; }
        }
        private List<string> visibleTo = new List<string>(new string[] { "All" });
        [Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"), EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)), TypeConverter(typeof(VisibleToConverter))]
        public List<string> VisibleTo { get { return visibleTo; } set { visibleTo = value; } } private string onFocus; public string OnFocus { get { return onFocus; } set { onFocus = value; } } private string onBlur; public string OnBlur { get { return onBlur; } set { onBlur = value; } } private string onDrop;
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
                if (!(controlName == null))
                {
                    if (value.Contains(" "))
                    {
                        MessageBox.Show("Please remove blanks from control name");
                    }
                    else if (value == "" || value == null)
                    {
                        MessageBox.Show("Control name can not be null");
                    }
                    else
                    {
                        controlName = value;
                        //this.Name = value;
                    }
                }
                else
                {
                    controlName = value;
                    //this.Name = value;
                }
            }
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Text = "column" + counter;
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "column" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            counter++;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Size = new Size(width, height);
        }
        public string ControlType
        {
            get
            {
                if (this.Parent.Parent is EIBLattice)
                    return "lcolumn";
                else
                    return "column";
            }
        }
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
        private string sortAscending;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the SortAscending property for the column."), Category("Appearance")]
        public string SortAscending
        {
            get
            {
                return sortAscending;
            }
            set
            {
                sortAscending = value;
            }
        }

        private string sortDescending;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the SortDescending property for the column."), Category("Appearance")]
        public string SortDescending
        {
            get
            {
                return sortDescending;
            }
            set
            {
                sortDescending = value;
            }
        }


        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.Container components = null;

        private int _space = 0;
        private int _widthPercent = 0;
        private int _widthPixel = 0;
        private string displayName = null;

        private WidthMeasurement _widthTyp = WidthMeasurement.Auto;

        /// <summary>
        /// Type of Width can be auto, percent or fixed amount of pixels
        /// </summary>


        #region Contructor

        public EIBColumn()
        {
            // This call is required by the Windows.Forms Form Designer.
            InitializeComponent();

            this.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom;
            this.Dock = DockStyle.None;
        }

        #endregion

        #region Public Properties

        [
        Category("Data"),
        Description("Horizontal space between columns")
        ]
        public int Space
        {
            get { return this._space; }
            set { this._space = value; }
        }

        [
        Category("Data"),
        Description("Set the width of a control in percent of the total width of the Row.")
        ]
        public int WidthPercent
        {
            get { return this._widthPercent; }
            set
            {
                if (this.Parent is EIBScrollableRow)
                {
                    ColumnCollection colcollection = Columns;
                    if (colcollection != null)
                    {
                        int oldPercent = this._widthPercent;
                        int maxPercent = 0;
                        foreach(EIBColumn col in colcollection)
                        {
                            if (col.WidthType == WidthMeasurement.Percent)
                                maxPercent += col.WidthPercent;
                        }
                        if (maxPercent - oldPercent + value > 100)
                        {
                            throw new ArgumentException("Width of all columns must not be > 100%");
                        }
                    }
                }
                this._widthPercent = value;
            }
        }

        [
        Category("Data"),
        Description("Set the Display Name of a control in Table.")
        ]
        public string DisplayName
        {
            get { return this.displayName; }
            set
            {
                this.displayName = value;
            }
        }


        [
        Category("Data"),
        Description("Set the width of a control in percent of the total width of the Row.")
        ]
        public int WidthPixel
        {
            get { return this._widthPixel; }
            set
            {
                this._widthPixel = value;
                if (this.Parent != null)
                {
                    if (this.Parent.Parent is EIBLattice)
                    {
                        RowCollection rowscollection = this.Rows;
                        int index = this.Columns.IndexOf(this);
                        foreach (EIBScrollableRow row in rowscollection)
                        {
                            ((EIBColumn)row.Columns[index])._widthPixel = value;
                            row.Columns[index].Size = new Size(value, row.Columns[index].Height);
                            row.Columns[index].Invalidate();
                        }
                    }
                    if (this.Parent.Parent is EIBGrid)
                    {
                        this.Size = new Size(value, this.Height);
                        this.Invalidate();
                    }
                }
                // this.Parent.Parent.Invalidate();
            }
        }
        [
        Category("Data"),
        Description("Set the width of a control to auto."),
        DefaultValue(WidthMeasurement.Auto)
        ]
        public WidthMeasurement WidthType
        {
            get { return this._widthTyp; }
            set { this._widthTyp = value; }
        }

        [
        Category("Data"),
        Description("Columns in this row"),
        EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
        ]
        public ColumnCollection Columns
        {
            get
            {
                if (this.Parent is EIBScrollableRow)
                    return (this.Parent as EIBScrollableRow).Columns;
                throw new ArgumentException("Column control is not placed in a row.");
            }
        }

        [
        Category("Data"),
        Description("Rows in this table"),
        EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
        ]
        public RowCollection Rows
        {
            get
            {
                if (this.Parent is EIBScrollableRow)
                {
                    if (this.Parent.Parent is EIBGrid)
                        return (this.Parent.Parent as EIBGrid).Rows;
                    else if (this.Parent.Parent is EIBLattice)
                        return (this.Parent.Parent as EIBLattice).Rows;
                }
                throw new ArgumentException("Column control is not placed in a row inside a table.");
            }
        }

        #endregion

        #region Disposing

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                    components.Dispose();
            }
            base.Dispose(disposing);
        }

        #endregion

        #region Component Designer generated code
        /// <summary>
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            components = new System.ComponentModel.Container();
        }
        #endregion
    }
}
