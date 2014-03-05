using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Xml;
using EIBFormDesigner.Property;
using System.Drawing.Design;

namespace EIBFormDesigner.Controls
{
    class EIBTimeBase : UserControl, ITool, IEIBControl, ICustomSizableControl
    {
        #region Designer Code.
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
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
        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.numTime = new System.Windows.Forms.NumericUpDown();
            this.textBox1 = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.numTime)).BeginInit();
            this.SuspendLayout();
            // 
            // numTime
            // 
            this.numTime.Location = new System.Drawing.Point(37, 5);
            this.numTime.Maximum = new decimal(new int[] {
            1440,
            0,
            0,
            0});
            this.numTime.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            -2147483648});
            this.numTime.Name = "numTime";
            this.numTime.Size = new System.Drawing.Size(18, 20);
            this.numTime.TabIndex = 0;
            this.numTime.ValueChanged += new System.EventHandler(this.numTime_ValueChanged);
            // 
            // textBox1
            // 
            this.textBox1.HideSelection = false;
            this.textBox1.Location = new System.Drawing.Point(4, 5);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(34, 20);
            this.textBox1.TabIndex = 1;
            this.textBox1.Text = "00:00";
            this.textBox1.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.textBox1_MouseDoubleClick);
            this.textBox1.MouseClick += new System.Windows.Forms.MouseEventHandler(this.textBox1_MouseClick);
            // 
            // EIBTimeBase
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.numTime);
            this.Name = "time"+counter;
            this.Size = new System.Drawing.Size(59, 30);
            ((System.ComponentModel.ISupportInitialize)(this.numTime)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
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

        #endregion

        internal static int counter = 0;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private string customFormatType;
        private string isMandatory;
        private string isForm;
        private string dataPatternName;
        private string dataTableName;
        private string dataFieldName;
        private string time;
        internal static Hashtable timenames = new Hashtable();
        EIBFormDesigner.Designer.FormDesigner currentForm;

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

        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
       TypeConverter(typeof(IsMandatory)), Description("Sets the mandatory field"), Category("Data")]

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
        private NumericUpDown numTime;
        private TextBox textBox1;
    
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
                return "time";
            }
        }
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Get the unique id of control"), Category("Appearance")]
        public string Time
        {
            get
            {
                return this.time;
            }
        }
        public void setTime(string value)
        {
            this.time = value;
            string[] sepVal = value.Split(':');
            decimal minute = decimal.Parse(sepVal[1]);
            decimal hour = decimal.Parse(sepVal[0]) * 60;
            decimal totalTime = hour+minute;

            this.numTime.Value = totalTime;
        }
        void EIBTimeBase_Resize(object sender, System.EventArgs e)
        {
            this.SuspendLayout();
            //if ((this.Size.Width < this.dateTimePicker.Size.Width) || (this.Size.Height < this.dateTimePicker.Size.Height))
            //{
            //    dateTimePicker.Size = new Size(width, height);
            //    this.Size = new Size(this.dateTimePicker.Size.Width + 5, this.dateTimePicker.Size.Height + 5);
            //}
            this.ResumeLayout();
            this.Invalidate();
        }
        public void checkUniqueness(string timeName)
        {
            if (timenames.Contains(timeName))
            {
                counter++;
                this.Name = "time" + counter;
                checkUniqueness(this.Name);
            }
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBTime>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            EIBControlCollection.Timelist.Add(this.Name, this.Name);
            this.currentForm = form;
            counter = 0;
            //ShowTimeSplit();
            
        }

        public void ShowTimeSplit()
        {
           /* if (string.IsNullOrEmpty(time))
            {
                time = "0:0:0";
            }
            string[] splitTime = this.time.Split(':');
            if (splitTime.Length == 3)
            {
                this.Hour.Value = decimal.Parse(splitTime[0]);
                this.minute.Value = decimal.Parse(splitTime[1]);
                this.second.Value = decimal.Parse(splitTime[2]);
            }*/
        }
        public EIBTimeBase()
        {
            InitializeComponent();
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "time" + counter;
            }
           // checkUniqueness(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            //timenames.Add(this.Name, this.Name);
            this.Margin = new Padding(0, 0, 0, 0);
            this.Font = SystemFonts.DefaultFont;
            textBox1.Text = "00:00";
            textBox1.SelectionStart = 3;
            textBox1.SelectionLength = 2;
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
        }

        private void Hour_ValueChanged(object sender, EventArgs e)
        {
            ShowTime();
            //MessageBox.Show(this.time);
        }

        private void ShowTime()
        {
            this.time = textBox1.Text;
            EIBFormDesigner.Designer.FormDesigner.eventManager.handleControlClick(this, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
        }

        private void minute_ValueChanged(object sender, EventArgs e)
        {
            ShowTime();
        }

        private void second_ValueChanged(object sender, EventArgs e)
        {
            ShowTime();
        }

        private void numTime_ValueChanged(object sender, EventArgs e)
        {
            if (numTime.Value >= 1440)
            {
                numTime.Value = numTime.Value-1440;
            }
            if (numTime.Value == -1)
            {
                numTime.Value = 1439;
            }

            int s_start = textBox1.SelectionStart;
            int s_length = textBox1.SelectionLength;

            int hour = ((int)numTime.Value) / 60;
            int minute = ((int)numTime.Value) % 60;
            string hour_s = hour.ToString();
            string minute_s = minute.ToString();
            if (hour < 10)
            {
                hour_s = "0"+hour_s;
            }
            if (minute < 10)
            {
                minute_s = "0"+minute_s;
            }
            
            textBox1.Text = hour_s + ":" + minute_s;
            textBox1.SelectionStart = s_start;
            textBox1.SelectionLength = s_length;
            ShowTime();
        }

        private void textBox1_MouseClick(object sender, MouseEventArgs e)
        {
            int charIndex = textBox1.GetCharIndexFromPosition(e.Location);
            if (charIndex <= 2)
            {
                textBox1.SelectionStart = 0;
                textBox1.SelectionLength = 2;
            }
            else if(charIndex >2)
            {
                textBox1.SelectionStart = 3;
                textBox1.SelectionLength = 2;
            }
            int s_start = textBox1.SelectionStart;
            int s_length = textBox1.SelectionLength;
            if (s_start == 0 && s_length == 2)
            {
                numTime.Increment = 60;
            }
            else
            {
                numTime.Increment = 1;
            }
            ShowTime();
        }

        private void textBox1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            int charIndex = textBox1.GetCharIndexFromPosition(e.Location);
            if (charIndex <= 2)
            {
                textBox1.SelectionStart = 0;
                textBox1.SelectionLength = 2;
            }
            else if (charIndex > 2)
            {
                textBox1.SelectionStart = 3;
                textBox1.SelectionLength = 2;
            }
            int s_start = textBox1.SelectionStart;
            int s_length = textBox1.SelectionLength;
            if (s_start == 0 && s_length == 2)
            {
                numTime.Increment = 60;
            }
            else
            {
                numTime.Increment = 1;
            }
            ShowTime();
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
                        if(this.Parent != null) { int pWidth = this.Parent.Width;
                        int mWidth = (base.Width * 100) / pWidth;
                        this._width = mWidth;}
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
    }
    class EIBTime : EIBTimeBase
    {

    }
    
}
