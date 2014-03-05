using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Windows.Forms.Design;
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
    class EIBSchedular : EIBSchedularBase
    {

    }

    class dayeditor : UITypeEditor
    {
        dayeditor()
        {
        }

        public override UITypeEditorEditStyle  GetEditStyle(ITypeDescriptorContext context)
        {
             return UITypeEditorEditStyle.DropDown;
        }
        ListBox lb;
        string text = "Sunday";
        IWindowsFormsEditorService edSvc;
        public override object EditValue(ITypeDescriptorContext context, System.IServiceProvider provider, object value)
        {
            if (context != null && provider != null)
            {
                edSvc = (IWindowsFormsEditorService)provider.GetService(typeof(IWindowsFormsEditorService));
                if (edSvc != null)
                {
                    lb = new ListBox();
                    lb.Items.AddRange(new object[] {
                        "Sunday",
                        "Monday",
                        "Tuesday",
                        "Wednesday",
                        "Thursday",
                        "Friday",
                        "Saturday"});
                    lb.SelectedIndexChanged += new System.EventHandler(lb_SelectedIndexChanged);
                    edSvc.DropDownControl(lb);
                }
                return text;

            }
             
            return base.EditValue(context, provider, value);
        }

        void lb_SelectedIndexChanged(object sender, System.EventArgs e)
        {
            if (lb.SelectedIndex > -1 && edSvc != null)
            {
                text = lb.Text;
                edSvc.CloseDropDown();
            }
        }
        
    }

    class EIBSchedularBase : Button, ITool, IEIBControl, ICustomSizableControl
    {
        /*
        MonthCalendar monthCalender = null;
        TransparentPanel tpanel = null;
         */
        int width = 178;
        int height = 155;
        
        internal static int counter = 0;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;

        
        private string isMandatory;
        private string isForm;

        //internal static Hashtable datapickerNames = new Hashtable();

        private string firstDayOfWeek = "Sunday";
        private string mold = "default";
        private string timeZone;


        [Browsable(true), DefaultValue("Sunday"),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Content), 
        Description("Sets the first day of week for control"), Category("Appearance"),
        Editor(typeof(dayeditor), typeof(UITypeEditor))]
        public string FirstDayOfWeek
        {
            get
            {
                return firstDayOfWeek;
            }
            set
            {
                if(value == "Sunday" || 
                    value == "Monday" ||
                    value == "Tuesday" ||
                    value == "Wednesday" ||
                    value == "Thursday" ||
                    value == "Friday" ||
                    value == "Saturday")
                firstDayOfWeek = value;
            }
        }

        [Browsable(true), DefaultValue("default"),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
        Description("Sets the mold for control"), Category("Appearance")]
        public string Mold
        {
            get
            {
                return mold;
            }
            set
            {
                mold= value;
            }
        }

        [Browsable(true), DefaultValue(""),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
        Description("Sets the time zone for control"), Category("Appearance")]
        public string TimeZone
        {
            get
            {
                return timeZone;
            }
            set
            {
                timeZone = value;
            }
        }
        /*
        private Label label1;
        private ComboBox comboBox1;

        private void InitializeComponent()
        {
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"});
            this.comboBox1.Location = new System.Drawing.Point(115, 21);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(94, 21);
            this.comboBox1.TabIndex = 0;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(24, 24);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(85, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "FirstDayOfWeek";
            // 
            // EIBSchedular
            // 
            this._Height = 98;
            this._Width = 273;
            this.Controls.Add(this.label1);
            this.Controls.Add(this.comboBox1);
            //this.Name = "EIBSchedular";
            this.Size = new System.Drawing.Size(273, 98);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void comboBox1_SelectedIndexChanged(object sender, System.EventArgs e)
        {
            if (comboBox1.SelectedIndex > -1)
            {
                FirstDayOfWeek = comboBox1.Text;
            }
        }*/

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
        /*
       [DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
        TypeConverter(typeof(CustomFormatConverter)), Description("Sets the name of custom format"), Category("Data")]
         
        public string CustomFormatType
        {
            get
            {
                
                return customFormatType;
            }
            set
            {

            }
        }
       [DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
       TypeConverter(typeof(IsMandatory)), Description("Sets the mandatory field"), Category("Data")]
        */
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
        public bool getVisibility() 
        { 
            return visible;
        }
        
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
        
        private List<string> visibleTo =new List<string>(new string[] { "All"});
        
        [Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"),EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)),TypeConverter(typeof(VisibleToConverter))] 
        public List<string> VisibleTo{get { return visibleTo; } set { visibleTo = value; }} 
        private string onFocus; 
        public string OnFocus { get { return onFocus; } set { onFocus = value; } } 
        private string onBlur; public string OnBlur { get { return onBlur; } set { onBlur = value; } } 
        private string onDrop;
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
                return "schedular";
            }
        }
        
        public EIBSchedularBase()
        {
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "schedular" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            this.Size = new Size(width, height);
            this._Width = width;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Font = SystemFonts.DefaultFont;
            this.Text = this.Name;
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
            
            //this.SizeChanged += new EventHandler(Container_SizeChanged);
            //this.ControlAdded += new ControlEventHandler(Container_ControlAdded);
            //InitializeComponent();
        }

        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBSchedular>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            EIBControlCollection.Schedularlist.Add(this.Name, this.Name);
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
        /*
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
        }*/
        #endregion
    
    }
}