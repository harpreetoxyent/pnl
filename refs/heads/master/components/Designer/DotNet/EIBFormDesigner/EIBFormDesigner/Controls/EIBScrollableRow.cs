using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using System.Xml;
using System.Collections.Generic;
using EIBFormDesigner.Controls.Grid.Collections;
using System.Drawing.Design;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls.Common;

namespace EIBFormDesigner.Controls
{
    public class EIBScrollableRow : Panel, ITool, IEIBControl, IContainerControl, ICustomSizableControl
    {
        internal static int counter = 0;
        int width = 50;
        int height = 30;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private Boolean defaultScreen = false;
        private string dataPatternName;
        private string isMandatory;
        private string isForm;
        private string dataTableName;
        private string dataFieldName;
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

        ContainerAlignment align;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Position of the Control"), Category("Appearance")]
        public ContainerAlignment Align
        {
            get
            {
                return align;
            }
            set
            {
                align = value;
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



        private Padding controlMargin = new Padding(0);
        [DisplayName("Margin"), Browsable(true), Category("Data"), Description("Set the Margin of a control.")]
        public Padding ControlMargin { get { return controlMargin; } set { controlMargin = value; } }
        [Browsable(false)]
        public new Padding Margin { get { return base.Margin; } set { base.Margin = value; } } string use;
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

        private bool visible = true;
        public bool getVisibility() { return visible; }
        [Browsable(true)]
        public new bool Visible
        {
            get
            {
                return base.Visible;
            }
            set
            {
                visible = value;
                base.Visible = value;
            }
        }

        private List<string> visibleTo = new List<string>(new string[] { "All" });
        [Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"), EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)), TypeConverter(typeof(VisibleToConverter))]
        public List<string> VisibleTo
        {
            get { return visibleTo; }
            set
            {
                if (this.Parent is BaseWindow)
                {
                    ((BaseWindow)this.Parent).VisibleTo = value;
                }
                visibleTo = value;
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

        /// <summary>
        /// this is uniqueid property as readonly.
        /// </summary>
        private string uniqueId;
        [Browsable(true), DefaultValue(false), EditorBrowsable(EditorBrowsableState.Never), Description("Get the unique id of control"), Category("Appearance")]
        public string UniqueId
        {
            get
            {
                return uniqueId;
            }
            set
            {
                if (this.uniqueId == null)// 
                {
                    string retVal = ControlValidation.validateUniqueId(value);
                    if (retVal.Length > 0)
                    {
                        MessageBox.Show(retVal);
                    }
                    else
                    {
                        uniqueId = value;
                    }

                }
                else if (this.uniqueId != value && ControlValidation.doesUniqueIdExist(value))
                {
                    //string tmp = EIBFormDesigner.Program.currentForm.currentBaseWindow.UniqueID;
                    MessageBox.Show("Control with same UniqueId already exist");
                    return;
                }
                if (!(uniqueId == null))
                {
                    string retVal = ControlValidation.validateUniqueId(value);
                    if (retVal.Length > 0)
                    {
                        MessageBox.Show(retVal);
                    }
                    else
                    {
                        uniqueId = value;
                    }
                }
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
                    }
                }
                else
                {
                    controlName = value;
                }
            }
        }
        public bool isMouseClick = false;

        public EIBScrollableRow()
        {

            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "row" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            this.Font = new Font(this.Font.FontFamily, 8f);
            this.AccessibleName = "row" + counter;
            this.Size = new Size(width, height);
            this.BorderStyle = BorderStyle.FixedSingle;
            this.AllowDrop = true;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Align = ContainerAlignment.None;
            this.BackColor = Color.BlueViolet;
            this.Layout += new LayoutEventHandler(RowControl_Layout);
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
            this.SizeChanged += new EventHandler(Container_SizeChanged);
            this.ControlAdded += new ControlEventHandler(Container_ControlAdded);


        }

        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBPanel>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            if (string.IsNullOrEmpty(this.UniqueId))
            {
                this.UniqueId = this.Name;
            }
            
            EIBControlCollection.Panellist.Add(this.Name, this.Name);
            counter = 0;

        }


        public string ControlType
        {
            get
            {
                return "row";
            }
        }

        private System.ComponentModel.Container components = null;

        private int _space = 0;
        private ColumnCollection columns = null;


        [
             Category("Data"),
             Description("Space Between Controls")
             ]
        public int Space
        {
            get { return this._space; }
            set
            {
                this._space = value;
                this.Invalidate(true);
            }
        }

        [
            Category("Data"),
            Description("Columns"),
            EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
            ]
        public ColumnCollection Columns
        {
            get
            {
                if (this.columns == null)
                    this.columns = new ColumnCollection(this);
                return this.columns;
            }
        }


        [
            Category("Data"),
            Description("Rows"),
            EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
            ]
        public RowCollection Rows
        {
            get
            {
                if (this.Parent is EIBGrid)
                    return (this.Parent as EIBGrid).Rows;
                else if (this.Parent is EIBLattice)
                    return (this.Parent as EIBLattice).Rows;
                throw new ArgumentException("Row Control is not placed in a table.");
            }
        }


        #region Disposing

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
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

        #region Adding Controls

        protected override void OnControlAdded(ControlEventArgs e)
        {
            if (this.Parent is EIBGrid && e.Control is EIBColumn)
                base.OnControlAdded(e);
            else if (this.Parent is EIBLattice)
                base.OnControlAdded(e);
            else
                throw new ArgumentException("can not add " + e.ToString() + " to this row.");
        }

        #endregion

        #region WidthType Changing functionality
        void Container_ControlAdded(object sender, ControlEventArgs e)
        {
            if (e.Control is ICustomSizableControl)
            {
                ICustomSizableControl iCsControl = (ICustomSizableControl)e.Control;
                if (iCsControl.WidthType == WidthMeasurement.Percent)
                {
                    int pWidth = this.Width;
                    int mWidth = (pWidth * iCsControl._Width) / 100;
                    iCsControl.ShouldWidhtChangeFireSizeChange = false;
                    e.Control.Width = mWidth;
                    iCsControl.ShouldWidhtChangeFireSizeChange = true;
                }
                if (e.Control is EIBTabControl)
                {
                    if (((EIBTabControl)e.Control).HeightType == HeightMeasurement.Percent)
                    {
                        int pHeight = this.Height;
                        int mHeight = (pHeight * ((EIBTabControl)e.Control)._Height) / 100;
                        ((EIBTabControl)e.Control).ShouldHeightChangeFireSizeChange = false;
                        e.Control.Height = mHeight;
                        ((EIBTabControl)e.Control).ShouldHeightChangeFireSizeChange = true;
                    }
                }
            }
        }

        void Container_SizeChanged(object sender, EventArgs e)
        {
            adjustChildSize();
        }

        public void adjustChildSize()
        {
            foreach (Control ctrl in this.Controls)
            {
                if (ctrl is ICustomSizableControl)
                {
                    ICustomSizableControl iCsControl = (ICustomSizableControl)ctrl;
                    if (iCsControl.WidthType == WidthMeasurement.Percent)
                    {
                        int pWidth = this.Width;
                        int mWidth = (pWidth * iCsControl._Width) / 100;
                        iCsControl.ShouldWidhtChangeFireSizeChange = false;
                        ctrl.Width = mWidth;
                        iCsControl.ShouldWidhtChangeFireSizeChange = true;
                    }
                }

                else if (ctrl is EIBColumn)
                { 
                    EIBColumn col = (EIBColumn)ctrl;
                    if (col.WidthType == WidthMeasurement.Pixel)
                    {
                        col.Width = col.WidthPixel;
                    }
                }

            }
        }

        private WidthMeasurement _widthType = WidthMeasurement.Auto;
        [
        Category("Data"),
        Description("Set the width of a control to auto."),
        DefaultValue(WidthMeasurement.Auto)
        ]
        public WidthMeasurement WidthType
        {
            get { return this._widthType; }
            set
            {
                if (this._widthType != value)
                {
                    if (this._widthType == WidthMeasurement.Percent && (value == WidthMeasurement.Auto || value == WidthMeasurement.Pixel))
                    {
                        this._widthType = value;
                        this._Width = base.Width;
                    }
                    else if ((this._widthType == WidthMeasurement.Auto || this._widthType == WidthMeasurement.Pixel) && value == WidthMeasurement.Percent)
                    {
                        this._widthType = value;
                        if (this.Parent != null)
                        {
                            int pWidth = this.Parent.Width;
                            int mWidth = (base.Width * 100) / pWidth;
                            this._width = mWidth;
                        }
                    }
                    else
                    {
                        this._widthType = value;
                        this.OnLayout(new LayoutEventArgs(this, "WidthType"));
                    }
                }
                else
                {
                    this._widthType = value;
                }

            }
        }
        [
        DisplayName("Height"),
        Browsable(true),
        Category("Data"),
        Description("Set the height of a control.")
        ]
        public int _Height
        {
            get { return this.Height; }
            set
            {
                this.Height = value;
            }
        }

        private int _width;
        [
        DisplayName("Width"),
        Browsable(true),
        Category("Data"),
        Description("Set the width of a control.")
        ]
        public int _Width
        {
            get { return this._width; }
            set
            {
                if (this._width != value)
                {
                    this._width = value;
                    this.OnLayout(new LayoutEventArgs(this, "_width"));
                }
            }
        }
        private bool shouldWidthChangeFireSizeChange = true;
        public bool ShouldWidhtChangeFireSizeChange
        {
            get
            {
                return shouldWidthChangeFireSizeChange;
            }
            set
            {
                shouldWidthChangeFireSizeChange = value;
            }
        }

        public void Control_Layout(object sender, LayoutEventArgs e)
        {
            if (e.AffectedProperty == "_width")
            {
                calculateWidth();
            }
            if (e.AffectedProperty == "WidthType")
            {
                calculateWidth();
            }
        }
        public void Control_SizeChanged(object sender, System.EventArgs e)
        {
            if (shouldWidthChangeFireSizeChange)
            {
                if (this.WidthType == WidthMeasurement.Percent)
                {
                    if (this.Parent != null)
                    {
                        int pWidth = this.Parent.Width;
                        int mWidth = (base.Width * 100) / pWidth;
                        this._width = mWidth;
                    }
                }
                else if (this.WidthType == WidthMeasurement.Auto || this.WidthType == WidthMeasurement.Pixel)
                {
                    this._width = base.Width;
                }
            }
        }
        public void calculateWidth()
        {
            if (this.WidthType == WidthMeasurement.Auto || this.WidthType == WidthMeasurement.Pixel)
            {
                base.Width = this._Width;
            }
            else
            {
                if (this.Parent != null)
                {
                    int wdth = this.Parent.Size.Width;
                    base.Width = (wdth * this._Width) / 100;
                }
            }
        }

        [Browsable(false)]
        public new Size Size
        {
            get
            {
                return base.Size;
            }
            set
            {
                base.Size = value;
            }
        }
        #endregion


        #region Layout Handler

        private void RowControl_Layout(object sender, LayoutEventArgs e)
        {
            try
            {

                int count = 0;
                double percentMax = 0;
                double skalePercent = 1;
                int autoWidthColumnCount = 0;
                int fixedPixelWidth = 0;
                foreach (EIBColumn col in this.Columns)
                {
                    if (col.Visible)
                    {
                        count++;
                        if (col.WidthType == WidthMeasurement.Percent)
                        {
                            percentMax += col.WidthPercent;
                        }
                        else if (col.WidthType == WidthMeasurement.Pixel)
                        {
                            fixedPixelWidth = col.WidthPixel;
                        }
                        else
                        {
                            autoWidthColumnCount++;
                        }
                    }
                }
                if (count > 0)
                {
                    if (percentMax > 0)
                    {
                        if (autoWidthColumnCount == 0)
                            skalePercent = 100.0 / percentMax;
                    }
                    double percentAuto = (100.0 - percentMax) / autoWidthColumnCount;

                    int lastPos = 0;
                    int clientWidthWithoutFixed = Math.Max(0, this.ClientSize.Width - fixedPixelWidth);
                    foreach (EIBColumn col in this.Columns)
                    {
                        if (col.Visible)
                        {
                            // fit in row
                            col.Height = this.ClientSize.Height;
                            if (col.WidthType == WidthMeasurement.Auto)
                            {
                                col.Width = (int)(percentAuto / 100.0 * clientWidthWithoutFixed) - (count - 1) * this.Space;
                            }
                            else if (col.WidthType == WidthMeasurement.Percent)
                            {
                                col.Width = (int)(col.WidthPercent / 100.0 * skalePercent * clientWidthWithoutFixed) - (count - 1) * this.Space;
                            }
                            else
                            {
                                col.Width = col.WidthPixel;
                            }

                            // position in row
                            col.Location = new Point(lastPos, 0);
                            if (col.WidthType == WidthMeasurement.Pixel)
                                lastPos += col.Width;
                            else
                                lastPos += col.Width + (count - 1) * this.Space;
                        }
                    }
                }
            }
            catch
            {
            }
        }
        #endregion
    }
}
