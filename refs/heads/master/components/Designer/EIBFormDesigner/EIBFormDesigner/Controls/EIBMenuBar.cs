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
    /// <summary>
    /// This class is abandoned not for use just kept here for the reference
    /// </summary>
    public class EIBMenuBar : EIBMenuBarBase
    {
        public EIBMenuBar():base()
        {
        }
        public EIBMenuBar(bool flag):base(flag)
        {
        }

    }
    public class EIBMenuBarBase : ToolStripPanel, ITool, IEIBControl, IContainerControl
    {
        internal static int counter = 0;
        private XmlNode parentXmlNode;
        private ToolStrip toolStrip;
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
        private string orient;

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

        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content),TypeConverter(typeof(EIBFormDesigner.Property.IsMandatory)), Description("Sets the mandatory field"), Category("Data")]

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
        public ToolStrip CurrentToolStrip
        {
            get
            {
                return toolStrip;
            }
            set
            {
                toolStrip = value;
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
        public string Orient
        {
            get
            {
                orient = "horizontal";
                return orient;
            }
            set
            {
                orient = "horizontal";
            }
        }
        public string ControlType
        {
            get
            {
                return "menubar";
            }
        }
        public EIBMenuBarBase()
        {
           toolStrip  = new ToolStrip();
           this.toolStrip.BackColor = Color.FromKnownColor(KnownColor.GradientInactiveCaption);
           this.BackColor = Color.FromKnownColor(KnownColor.GradientInactiveCaption) ;
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "menubar" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            this.Join(toolStrip);
            this.Dock = DockStyle.Top;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Font = SystemFonts.DefaultFont;
            this.Align = ContainerAlignment.None;
        }
        public EIBMenuBarBase(bool flag)
        {
            //toolStrip = new ToolStrip();
            this.toolStrip.BackColor = Color.FromKnownColor(KnownColor.GradientInactiveCaption);
            this.BackColor = Color.FromKnownColor(KnownColor.GradientInactiveCaption);
            //this.BackColor = Color.Gainsboro;
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "menubar" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            //this.Join(toolStrip);
            this.Dock = DockStyle.Top;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Font = SystemFonts.DefaultFont;
            this.Align = ContainerAlignment.None;
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBMenuBar>(this.Name);
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            EIBControlCollection.MenuBarlist.Add(this.Name, this.Name);
            counter = 0;
        }

        public static void RefreshInList(IEIBControl control)
        {
            if (control is EIBMenuBar)
            {
                if (!EIBControlCollection.MenuBarlist.ContainsKey(((Control)control).Name))
                {
                    EIBControlCollection.MenuBarlist.Add(((Control)control).Name, ((Control)control).Name);
                }
                foreach (EIBMenu menu in ((ToolStrip)((EIBMenuBar)control).Controls[0]).Items)
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
    }
}
