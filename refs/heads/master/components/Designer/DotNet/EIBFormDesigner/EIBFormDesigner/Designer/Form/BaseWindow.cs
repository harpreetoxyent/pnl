using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using EIBFormDesigner.XML;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.Controls;
using System.Drawing.Design;

namespace EIBFormDesigner.Designer
{
    public partial class BaseWindow : ToolWindow, IEIBControl, IBaseWindow
    {
        public bool isKeyDown = false;
        internal static int formCounter = 0;
        internal static int dataCounter = 0;
        internal static int workCounter = 0;
        internal ArrayList Lines = new ArrayList();
        internal Dictionary<string, NodeUserControl> WorkflowNodes = new Dictionary<string, NodeUserControl>();

        private DataSet databaseDataSet;
        private bool isBaseWindowSaved = false;
        private XmlDocument xmlDoc = null;
        private Hashtable controlProperties = null;
        private XmlNode parentXmlNode;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private string controlName;
        private string isMandatory;
        private string isForm;
        private string typeOfWindow;
        private string dataPatternName;
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

        private Padding controlMargin = new Padding(0);[DisplayName("Margin"), Browsable(true), Category("Data"), Description("Set the Margin of a control.") ] public Padding ControlMargin { get { return controlMargin; } set { controlMargin = value; } } [Browsable(false)] public new Padding Margin { get { return base.Margin; } set { base.Margin = value; } } string use;
        [Browsable(true),DefaultValue(""), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the User defined class for control"), Category("Appearance")]
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

        public bool IsBaseWindowSaved
        {
            get
            {
                return isBaseWindowSaved;
            }
            set
            {
                isBaseWindowSaved = value;
            }
        }
        public string TypeOfWindow
        {
            get
            {
                return typeOfWindow;
            }
            set
            {
                typeOfWindow = value;
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

        private List<IEIBControl> currentControl = new List<IEIBControl>();
        public List<IEIBControl> CurrentControl
        {
            get
            {
                return currentControl;
            }
            set
            {
                currentControl = value;
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
        public Hashtable ControlProperties
        {
            get
            {
                return controlProperties;
            }
            set
            {
                controlProperties = value;
            }
        }
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
                    if (value == "" || value == null)
                    {
                        MessageBox.Show("Control Name cannot be blank.");
                    }
                    else if (value.Contains(" "))
                    {
                        MessageBox.Show("Please remove blanks from control name");
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
                return "basewindow";
            }
        }

        public static string elementName = "basewindow";
        private string uniqueId = null;
        internal String UniqueID
        {
            get
            {
                return uniqueId;
            }
            set
            {
                uniqueId = value;
            }
        }
        public XmlDocument XMLDocument
        {
            get
            {
                return xmlDoc;
            }
            set
            {
                xmlDoc = value;
            }
        }
        public void checkUniqueness(string typeWindow, string baseWindowName)
        {
            if (FormDesignerUtilities.formWindowNames.Contains(baseWindowName))
            {
                formCounter++;
                UniqueID = typeWindow.ToLower() + formCounter;
                checkUniqueness(typeWindow, UniqueID);
            }
            if (FormDesignerUtilities.workWindowNames.Contains(baseWindowName))
            {
                workCounter++;
                uniqueId = typeWindow.ToLower() + workCounter;
                checkUniqueness(typeOfWindow, uniqueId);
            }
        }
        public BaseWindow(string typeOfWindow, string windowName, bool isWindowSaved)
        {
            isBaseWindowSaved = isWindowSaved;
            InitializeComponent();
            if (typeOfWindow.Equals(FormDesignerConstants.FormPattern))
            {
                if (!isBaseWindowSaved && windowName == "")
                {
                    UniqueID = typeOfWindow.ToLower() + formCounter;
                    checkUniqueness(typeOfWindow, UniqueID);
                    //If new window then increment the counter
                    formCounter++;
                }
                //this.baseFrame.setStyle();
            }
            if (typeOfWindow.Equals(FormDesignerConstants.DataPattern))
            {
                UniqueID = typeOfWindow.ToLower() + dataCounter;
                if (!isBaseWindowSaved)
                {
                    dataCounter++;
                }
                //this.baseFrame.setStyle();
            }
            if (typeOfWindow.Equals(FormDesignerConstants.WorkflowPattern))
            {
                UniqueID = typeOfWindow.ToLower() + workCounter;
                checkUniqueness(typeOfWindow, uniqueId);
                if (!isBaseWindowSaved)
                {
                    workCounter++;
                }
            }
            if (windowName != null && windowName != "")
            {
                UniqueID = windowName;
            }
            this.Name = UniqueID;
            this.baseFrame.ControlName = UniqueID;
            databaseDataSet = new DataSet(this.baseFrame.ControlName);
            this.baseFrame.Text = this.controlName;
            this.baseFrame.BackColor = Color.White;
            this.Text = UniqueID;
            this.TabText = UniqueID;
            /*this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);*/

        }
        protected override void OnVisibleChanged(EventArgs e)
        {
            try
            {
                if (!this.baseFrame.Visible)
                {
                    this.baseFrame.Visible = true;
                    foreach (Control ctrl in this.baseFrame.Controls)
                    {
                        if (!ctrl.Visible)
                        {
                            ctrl.Visible = true;
                            this.makeVisible(ctrl);
                        }
                    }

                    return;
                }
            }
            catch
            {

            }
        }
        public void makeVisible(Control ctrlToMakeVisible)
        {
            try
            {
                if (ctrlToMakeVisible.Controls.Count > 0)
                {
                    foreach (Control ctrl in ctrlToMakeVisible.Controls)
                    {
                        ctrl.Visible = true;
                        if (ctrl.Controls.Count > 0)
                        {
                            this.makeVisible(ctrl);
                        }
                    }
                }
            }
            catch
            {
            }
        }
        public bool CheckVisibleToValue(Object value)
        {
            /*if (value is List<string>)
            {
                List<string> ListValue = (List<string>)value;
                if (ListValue.Contains("All"))
                {
                    return false;
                }
                return true;
            }
            return false;*/
            bool isVisibleTo = false;
            if (!((IEIBControl)this).VisibleTo.Contains("All"))
            {
                isVisibleTo = true;
            }
            else
            {
                foreach (object control in ((Control)this).Controls)
                {
                    if (control is IEIBControl)
                    {
                        isVisibleTo = false;
                        checkForChildChanges(control, ref isVisibleTo);
                    }
                }
            }
            return isVisibleTo;
        }
        public void checkForChildChanges(Object parentControl,ref bool isVisibleTo)
        {
            try
            {
                if (parentControl is IEIBControl)
                {
                    if (!((IEIBControl)parentControl).VisibleTo.Contains("All"))
                    {
                        isVisibleTo = true;
                        return;
                    }
                    if (parentControl is EIBTabControl)
                    {
                        /*if (!((EIBTabControl)parentControl).VisibleTo.Contains("All"))
                        {
                            isVisibleTo = true;
                        }*/
                        foreach (EIBTabPage tabPage in ((EIBTabControl)parentControl).TabPages)
                        {
                            if (!tabPage.VisibleTo.Contains("All"))
                            {
                                isVisibleTo = true;
                                break;
                            }
                            else
                            {
                                isVisibleTo = false;
                                checkForChildChanges(tabPage,ref isVisibleTo);
                                if (isVisibleTo) break;
                                /*foreach (object control in tabPage.Controls)
                                {
                                    if(control is IEIBControl)
                                    {
                                        isVisibleTo = checkForChildChanges(control);
                                    }
                                }*/
                            }
                        }
                    }
                    else if (parentControl is EIBVMenuBar)
                    {
                        /*if (!((EIBVMenuBar)parentControl).VisibleTo.Contains("All"))
                        {
                            isVisibleTo = true;
                        }*/

                        foreach (ToolStripMenuItem menu in ((EIBVMenuBar)parentControl).Items)
                        {
                            if (!((IEIBControl)menu).VisibleTo.Contains("All"))
                            {
                                isVisibleTo = true;
                                break;
                            }
                            else
                            {
                                isVisibleTo = false;
                                if (menu is EIBMenu)
                                {
                                    checkForChildChanges(menu, ref isVisibleTo);
                                    if (isVisibleTo) break;
                                }
                            }
                        }

                    }
                    else if (parentControl is EIBMenu)
                    {

                        foreach (ToolStripMenuItem menu in ((EIBMenu)parentControl).DropDownItems)
                        {
                            if (!((IEIBControl)menu).VisibleTo.Contains("All"))
                            {
                                isVisibleTo = true;
                                break;
                            }
                            else
                            {
                                isVisibleTo = false;
                                if (menu is EIBMenu)
                                {
                                    checkForChildChanges(menu, ref isVisibleTo);
                                    if (isVisibleTo) break;
                                }
                            }
                        }

                    }
                    else
                    {
                        foreach (object control in ((Control)parentControl).Controls)
                        {
                            if (control is IEIBControl)
                            {
                                isVisibleTo = false;
                                checkForChildChanges(control, ref isVisibleTo);
                                if (isVisibleTo) break;
                            }
                        }
                    }
                }
            }
            catch
            {

            }
            //return isVisibleTo;
        }
    }
 
}