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
using EIBFormDesigner.Controls.Common;
using System;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls.Common;


namespace EIBFormDesigner.Controls
{
    public class EIBVMenuBar : EIBVMenuBarBase
    {
        public EIBVMenuBar():base()
        {
        }
        public EIBVMenuBar(bool flag):base(flag)
        {
        }

    }
    public class EIBVMenuBarBase : MenuStrip, ITool, IEIBControl, IContainerControl, ICustomSizableControl
    {
        internal static int counter = 0;
        private XmlNode parentXmlNode;
        private EIBMenu menu;
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
        private MenuOrient orient;
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
        private Bitmap menuIcon;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
        Description("Sets the path for menuicon"), Category("Appearance")]
        public Bitmap MenuIcon
        {
            get
            {
                return menuIcon;
            }
            set
            {
                menuIcon = value;
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

        ContainerAlignment align;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Alignment of the Control"), Category("Appearance")]
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

        public EIBMenu CurrentMenu
        {
            get
            {
                return menu;
            }
            set
            {
                menu = value;
            }
        }
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

        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the Orientation of the Menu Control."), Category("Appearance")]
        public MenuOrient Orient
        {
            get
            {
                return orient;
            }
            set
            {
                if (value == MenuOrient.horizontal)
                {
                    this.LayoutStyle = ToolStripLayoutStyle.HorizontalStackWithOverflow;
                }
                else
                {
                    this.LayoutStyle = ToolStripLayoutStyle.VerticalStackWithOverflow;
                }
                orient = value;
            }
        }

        public string ControlType
        {
            get
            {
                return "hmenubar";
            }
        }
        public EIBVMenuBarBase()
        {
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "menubar" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            this.Dock = DockStyle.None;
            //this.Size = new Size(30, 206);
            this.Margin = new Padding(0, 0, 0, 0);
            this.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.VerticalStackWithOverflow;
            this.Font = SystemFonts.DefaultFont;
            this.AllowMerge = false;
            this.BackColor = Color.FromKnownColor(KnownColor.GradientInactiveCaption);
            this.Orient = MenuOrient.vertical;
            this.Align = ContainerAlignment.None;
            this.AutoSize = false;
            this._width = 30;
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
            //this.SizeChanged += new EventHandler(Container_SizeChanged);
            //this.ControlAdded += new ControlEventHandler(Container_ControlAdded);
        }
        public EIBVMenuBarBase(bool flag)
        {
            //toolStrip = new ToolStrip();
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "menubar" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            //this.Join(toolStrip);
            this.Dock = DockStyle.None;
            //this.Size = new Size(30, 206);
            
            this.Margin = new Padding(0, 0, 0, 0);
            this.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.VerticalStackWithOverflow;
            this.Font = SystemFonts.DefaultFont;
            this.AllowMerge = false;
            this.BackColor = Color.FromKnownColor(KnownColor.GradientInactiveCaption);
            this.Align = ContainerAlignment.None;
            this.AutoSize = false;
            this._width = 30;
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
            //this.SizeChanged += new System.EventHandler(Container_SizeChanged);
            //this.ControlAdded += new ControlEventHandler(Container_ControlAdded);
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBVMenuBar>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            if (string.IsNullOrEmpty(this.UniqueId))
            {
                this.UniqueId = this.Name;
            }
            EIBControlCollection.HMenuBarlist.Add(this.Name, this.Name);
            counter = 0;
        }

        public static void RefreshInList(IEIBControl control)
        {
            if (control is EIBVMenuBar)
            {
                if (!EIBControlCollection.MenuBarlist.ContainsKey(((Control)control).Name))
                {
                    EIBControlCollection.HMenuBarlist.Add(((Control)control).Name, ((Control)control).Name);
                }
                foreach (EIBMenu menu in ((EIBVMenuBar)control).Items)
                {
                    RefreshInList(menu);
                }
            }
            if (control is EIBMenu)
            {
                if (!EIBControlCollection.Menulist.ContainsKey(((ToolStripItem)control).Name))
                {
                    EIBControlCollection.Menulist.Add(((ToolStripItem)control).Name, ((ToolStripItem)control).Name);
                }
                foreach (IEIBControl menuControl in ((EIBMenu)control).DropDownItems)
                {
                    RefreshInList(menuControl);
                }
            }
            if (control is EIBMenuItem)
            {
                if (!EIBControlCollection.MenuItemlist.ContainsKey(((ToolStripItem)control).Name))
                {
                    EIBControlCollection.MenuItemlist.Add(((ToolStripItem)control).Name, ((ToolStripItem)control).Name);
                }
            }
        }
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
}
