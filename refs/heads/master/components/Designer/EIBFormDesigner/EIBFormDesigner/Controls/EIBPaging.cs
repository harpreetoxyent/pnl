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
using System.Drawing.Design;

namespace EIBFormDesigner.Controls
{
    class EIBPaging : EIBPagingBase
    {
        
    }
    class EIBPagingBase : EIBPagingUserControl, ITool, IEIBControl, ICustomSizableControl
    {
        internal static int counter = 0;
        int width = 75;
        int height = 30;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private string isMandatory;
        private string isForm;
        private string dataPatternName;
        private string dataTableName;
        private string dataFieldName;
        private int pageSize;
        private List<DataMapping> dataMappings;
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
        [Browsable(true), Description("Set DataMappings"),Category("Data"),
        EditorAttribute(typeof(DataMappingEditor), typeof(UITypeEditor)),
        TypeConverter(typeof(DataMappingConverter))
        ]
        public List<DataMapping> DataMappings
        {
            get { return dataMappings; }
            set { dataMappings = value; }
        }

        bool draggable;[Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Draggable property of control"), Category("Appearance")]
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

        bool droppable;[Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Draggable property of control"), Category("Appearance")]
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

        [Browsable(false)]
        public override Cursor Cursor
        {
            get
            {
                return base.Cursor;
            }
            set
            {
                base.Cursor = value;
            }
        }

        [Browsable(false)]
        public override Color BackColor
        {
            get
            {
                return base.BackColor;
            }
            set
            {
                base.BackColor = value;
            }
        }
        [Browsable(false)]
        public override Image BackgroundImage
        {
            get
            {
                return base.BackgroundImage;
            }
            set
            {
                base.BackgroundImage = value;
            }
        }

        [Browsable(false)]
        public override ImageLayout BackgroundImageLayout
        {
            get
            {
                return base.BackgroundImageLayout;
            }
            set
            {
                base.BackgroundImageLayout = value;
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

        private bool visible = true;public bool getVisibility() { return visible;}[Browsable(true)] public new bool Visible { get { return base.Visible; } set { visible = value; base.Visible = value; } }  private List<string> visibleTo =new List<string>(new string[] { "All"});[Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"),EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)),TypeConverter(typeof(VisibleToConverter))] public List<string> VisibleTo{get { return visibleTo; } set { visibleTo = value; }} private string onFocus; public string OnFocus { get { return onFocus; } set { onFocus = value; } } private string onBlur; public string OnBlur { get { return onBlur; } set { onBlur = value; } } private string onDrop;
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

        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the pagesize of the paging control."), Category("Data")]
        public int PageSize
        {
            get
            {
                return pageSize;
            }
            set
            {
                pageSize = value;
            }
        }

        /// <summary>
        /// this is uniqueid property as readonly.
        /// </summary>
        
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Get the unique id of control"), Category("Appearance")]
        public string UniqueId
        {
            get
            {
                return this.Name;
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
        public string ControlType
        {
            get
            {
                return "paging";
            }
        }
        public EIBPagingBase():base()
        {
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "paging" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            this.Size = new Size(base.Width, base.Height);
            width = base.Width;
            height = base.Height;
            this._Width = width;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Font = SystemFonts.DefaultFont;
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBPaging>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            EIBControlCollection.Paginglist.Add(this.Name, this.Name);
            counter = 0;
        }
        #region WidthType Changing functionality
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
                    if(this.Parent != null) { int pWidth = this.Parent.Width;
                    int mWidth = (base.Width * 100) / pWidth;
                    this._width = mWidth;}
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
    }
}
