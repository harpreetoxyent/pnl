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
using System.ComponentModel;
using System.Data;
using System.Xml;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer;
using System.Drawing.Design;

using System.Web;
using System.Web.UI;
using System.Web.UI.Design;



namespace EIBFormDesigner.Controls
{
    public enum BoxAlignment
    {
        None,
        Horizontal,
        Vertical
    }
    public class EIBPanel : EIBPanelBase
    {

    }
    public class EIBPanelBase : Panel, ITool, IEIBControl, IContainerControl, ICustomSizableControl
    {

        internal static int counter = 0;
        int width = 200;
        int height = 200;
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
        private string globalScriptsValue;
        public string GlobalScripts
        {
            get
            {
                return globalScriptsValue;
            }
            set
            {
                globalScriptsValue = value;
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

        private string style;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the style property for the frame."), Category("Appearance")]
        public string Style
        {
            get
            {
                return style;
            }
            set
            {
                style = value;
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

        BoxAlignment boxAlignment;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the BoxAlignment property of control"), Category("Appearance")]
        public BoxAlignment BoxAlignment
        {
            get
            {
                return boxAlignment;
            }
            set
            {
                boxAlignment = value;
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

        bool renderAsIFrame = false;
        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Renders the Frame as a IFrame in Zul."), Category("Appearance")]
        public bool RenderAsIFrame
        {
            get
            {
                return renderAsIFrame;
            }
            set
            {
                renderAsIFrame = value;
            }
        }

        private string source;
        [Category("Appearance"), Browsable(true), EditorAttribute(typeof(System.Windows.Forms.Design.FileNameEditor), typeof(System.Drawing.Design.UITypeEditor))]  
        public string Source
        {
            get { return source; }
            set { source = value; }
        } 
        

        string title;
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Set the title of basewindow."), Category("Appearance")]
        public string Title
        {
            get
            {
                return title;
            }
            set
            {
                if (this.Parent is BaseWindow)
                {
                    //Check if some other window is already visible name already Exists
                    BaseWindow baseWindow = (BaseWindow)this.Parent;
                    if (baseWindow != null)
                    {
                        if (baseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                        {
                            if (value != null)
                            {
                                title = value;
                            }
                        }
                    }
                }
            }
        }

        bool popup;
        [Browsable(true),DefaultValue(false), EditorBrowsable(EditorBrowsableState.Never), Description("Set the title of basewindow."), Category("Appearance")]
        public bool Popup
        {
            get
            {
                return popup;
            }
            set
            {
                if (this.Parent is BaseWindow)
                {
                    //Check if some other window is already visible name already Exists
                    BaseWindow baseWindow = (BaseWindow)this.Parent;
                    if (baseWindow != null)
                    {
                        if (baseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                        {
                            popup = value;
                        }
                    }
                }
            }
        }

        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Is Basewindow default"), Category("Appearance")]
        public Boolean DefaultScreen
        {
            get
            {
                return defaultScreen;
            }
            set
            {
                if (this.Parent is BaseWindow)
                {
                    //Check if some other window is already visible name already Exists
                    BaseWindow baseWindow = (BaseWindow)this.Parent;
                    if (baseWindow != null)
                    {
                        if (baseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                        {

                            if (FormDesignerUtilities.visibleTrueWindow.Count > 0)
                            {
                                if (value)
                                {
                                    if (! (FormDesignerUtilities.visibleTrueWindow[0] == this.Parent))
                                    {
                                        MessageBox.Show(((BaseWindow)FormDesignerUtilities.visibleTrueWindow[0]).baseFrame.ControlName + " is already set to be visible");
                                        return;
                                    }
                                }
                                else
                                {
                                    defaultScreen = value;
                                    if (FormDesignerUtilities.visibleTrueWindow.Contains(baseWindow))
                                    {
                                        FormDesignerUtilities.visibleTrueWindow.Remove(baseWindow);
                                    }
                                }
                            }
                            else
                            {
                                defaultScreen = value;
                                baseWindow.IsBaseWindowSaved = true;
                                if (defaultScreen)
                                {
                                    FormDesignerUtilities.visibleTrueWindow.Add(baseWindow);
                                }
                                return;
                            }
                        }
                    }
                }
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
        public bool getVisibility() { return visible;}
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
                if (this.Parent is BaseWindow)
                {
                    //Check is name already Exists
                    BaseWindow baseWindow = (BaseWindow)this.Parent;
                    if (baseWindow != null)
                    {
                        if ((!baseWindow.IsBaseWindowSaved) && baseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                        {
                            if (FormDesignerUtilities.formWindowNames.Contains(value))
                            {
                                MessageBox.Show("Form with same name already exist");
                                return;
                            }
                            else
                            {
                                if (value.Contains(" "))
                                {
                                    MessageBox.Show("Please remove blanks from control name");
                                }
                                else if (value == "")
                                {
                                    MessageBox.Show("Control name cannot be null");
                                }
                                else if (value.Contains("-") || value.Contains("_"))
                                {
                                    MessageBox.Show("Control name mustnot contain UnderScore(_) and hyphen(-).");
                                }
                                else
                                {
                                    //Now user wants to change control name, so keep the old
                                    //control name in a reference which we will use to delete files
                                    if (controlName != null && !FormDesignerUtilities.oldFilesToBeDeleted.Contains(controlName))
                                        FormDesignerUtilities.oldFilesToBeDeleted.Add(controlName);
                                    controlName = value;
                                }
                                baseWindow.IsBaseWindowSaved = true;
                                return;
                            }
                        }
                        else
                        {
                            if (this.controlName == null)// 
                            {
                                if (value.Contains(" "))
                                {
                                    MessageBox.Show("Please remove blanks from control name");
                                }
                                else if (value == "")
                                {
                                    MessageBox.Show("Control name cannot be null");
                                }
                                else if (value.Contains("-") || value.Contains("_"))
                                {
                                    MessageBox.Show("Control name mustnot contain UnderScore(_) and hyphen(-).");
                                }
                                else
                                {
                                    //Now user wants to change control name, so keep the old
                                    //control name in a reference which we will use to delete files
                                    if (controlName != null && !FormDesignerUtilities.oldFilesToBeDeleted.Contains(controlName))
                                        FormDesignerUtilities.oldFilesToBeDeleted.Add(controlName);
                                    controlName = value;
                                    return;
                                }
                            }
                            else if (this.controlName != value && FormDesignerUtilities.formWindowNames.Contains(value.ToLower()))
                            {
                                MessageBox.Show("Form with same name already exist");
                                return;
                            }
                        }
                    }
                }

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

                    else if (value.Contains("-") || value.Contains("_"))
                    {
                        MessageBox.Show("Control name mustnot contain UnderScore(_) and hyphen(-).");
                    }
                    else
                    {
                        //Now user wants to change control name, so keep the old
                        //control name in a reference which we will use to delete files
                        if (controlName != null && !FormDesignerUtilities.oldFilesToBeDeleted.Contains(controlName))
                            FormDesignerUtilities.oldFilesToBeDeleted.Add(controlName);
                        controlName = value;
                        //this.Name = value;
                        if (this.Parent is BaseWindow)
                        {
                            //Check is name already Exists
                            BaseWindow baseWindow = (BaseWindow)this.Parent;
                            baseWindow.DatabaseDataSet.DataSetName = controlName;
                        }
                    }
                }
                else
                {
                    //Now user wants to change control name, so keep the old
                    //control name in a reference which we will use to delete files
                    if (controlName != null && !FormDesignerUtilities.oldFilesToBeDeleted.Contains(controlName))
                        FormDesignerUtilities.oldFilesToBeDeleted.Add(controlName);
                    controlName = value;
                    //this.Name = value;
                }
            }
        }
        public bool isMouseClick = false;

        public EIBPanelBase()
        {

            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "frame" + counter;
            }
            this.Font = new Font(this.Font.FontFamily, 8f);
            this.AccessibleName = "frame" + counter;
            this.Size = new Size(width, height);
            this.BorderStyle = BorderStyle.FixedSingle;
            this.AllowDrop = true;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Align = ContainerAlignment.None;
            this.BackColor = Color.White;
            this.Layout += new LayoutEventHandler(Control_Layout);
            this.SizeChanged += new System.EventHandler(Control_SizeChanged);
            this.SizeChanged += new EventHandler(Container_SizeChanged);
            this.ControlAdded += new ControlEventHandler(Container_ControlAdded);
           
        }

        public void setStyle()
        {
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            this.UpdateStyles();
        }
        /*protected override void OnPaint(PaintEventArgs e)
        {
            //Graphics dc = e.Graphics;
            //dc.Clear(this.BackColor);
            //if (this.BackgroundImage != null)
           // {
            //    dc.DrawImage(this.BackgroundImage, 0, 0);
           // }
            base.OnPaint(e);
        }
        protected override void OnScroll(ScrollEventArgs se)
        {
            base.OnScroll(se);
            //this.Refresh();
        }
        protected override void OnClientSizeChanged(EventArgs e)
        {
            base.OnClientSizeChanged(e);
        }*/
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBPanel>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            EIBControlCollection.Panellist.Add(this.Name, this.Name);
            setStyle();
            counter = 0;

        }
        public string ControlType
        {
            get
            {
                return "panel";
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
                        if (this.Parent != null) { int pWidth = this.Parent.Width;
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

    }
}
