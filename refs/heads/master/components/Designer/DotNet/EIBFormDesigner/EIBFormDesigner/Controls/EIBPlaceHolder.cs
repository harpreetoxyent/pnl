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
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls.Common;


namespace EIBFormDesigner.Controls
{
    public class EIBPlaceHolder : EIBPlaceHolderBase
    {

    }
    public class EIBPlaceHolderBase : Panel, ITool, IEIBControl
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
        internal static Hashtable placeholderNames = new Hashtable();
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
                                else
                                {
                                    controlName = value;
                                }
                                baseWindow.IsBaseWindowSaved = true;
                                return;
                            }
                        }
                        else
                        {
                            if (this.controlName == value || this.controlName == null)
                            {
                                if (value.Contains(" "))
                                {
                                    MessageBox.Show("Please remove blanks from control name");
                                }
                                else
                                {
                                    controlName = value;
                                    return;
                                }
                            }
                            else if (FormDesignerUtilities.formWindowNames.Contains(value))
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
                    else
                    {
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
                    controlName = value;
                    //this.Name = value;
                }
            }
        }
        public EIBPlaceHolderBase()
        {
            this.Size = new Size(width, height);
            this.BorderStyle = BorderStyle.FixedSingle;
        }

        public void checkUniqueness(string placeholderName)
        {
            if (placeholderNames.Contains(placeholderName))
            {
                counter++;
                this.Name = "P" + counter;
                checkUniqueness(this.Name);
            }
        }

        public bool CheckifAlreadyHave(string text)
        {
            bool flag = false;
            foreach (Control ctrl in this.Controls)
            {
                if (ctrl.Text == text)
                {
                    flag = true;
                    break;
                }
            }
            return flag;
        }

        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Name = "placeholder" + counter;
            this.Name = EIBControlCollection.CheckControlForUniqueness<EIBPlaceHolder>(this.Name);
            if (this.ControlName == null)
            {
                this.ControlName = this.Name;
            }
            EIBControlCollection.PlaceHolderlist.Add(this.Name, this.Name);
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "placeholder" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            if (string.IsNullOrEmpty(this.UniqueId))
            {
                this.UniqueId = this.Name;
            }
            this.Font = new Font(this.Font.FontFamily, 8f);
            this.AccessibleName = "placeholder" + counter;
            this.Margin = new Padding(0, 0, 0, 0);
            this.AllowDrop = true;
            counter = 0;
            
        }
        public string ControlType
        {
            get
            {
                return "PlaceHolder";
            }
        }

    }
}
