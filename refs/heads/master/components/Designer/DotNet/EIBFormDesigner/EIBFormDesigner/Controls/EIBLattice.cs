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
using System.Drawing.Design;
using System.ComponentModel;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer;
using System.ComponentModel.Design;
using EIBFormDesigner.Controls.Grid.Collections;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls.Common;

namespace EIBFormDesigner.Controls
{
    class LatticeListConverter : StringConverter
    {
        public override bool GetStandardValuesSupported(ITypeDescriptorContext context)
        {
            //true means show a combobox
            return true;
        }

        public override bool GetStandardValuesExclusive(ITypeDescriptorContext context)
        {
            //true will limit to list. false will show the list, 
            //but allow free-form entry
            return true;
        }

        public override System.ComponentModel.TypeConverter.StandardValuesCollection
               GetStandardValues(ITypeDescriptorContext context)
        {
            return new StandardValuesCollection(EIBLattice.searchList);
        }
    }

    /// <summary>
    /// A table control outlines all controls vertical.
    /// </summary>
    [ToolboxItem(true)]
    public class EIBLattice : System.Windows.Forms.Panel, ITool, IEIBControl, IContainerControl
    {
        internal static int counter = 0;
        EIBFormDesigner.Designer.FormDesigner designerForm = null;
        int width = 100;
        int height = 100;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private string dataPatternName;
        private string dataTableName;
        private string dataFieldName;
        private string isMandatory;
        private string isForm;
        static internal string[] searchList;
        private string selectedSearch = null;

        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of Search Field"), Category("Data"), TypeConverter(typeof(LatticeListConverter))]
        public string SearchSet
        {
            get
            {
                string searchString = "";
                if (selectedSearch != null)
                {
                    searchString = selectedSearch;
                }
                else
                {
                    searchList = UpdateListofSearches();
                    if (searchList != null)
                    {
                        if (searchList.Length > 0)
                        {
                            //Sort the list before displaying it
                            Array.Sort(searchList);
                            searchString = searchList[0];
                        }
                    }
                    else
                    {
                        searchString = "";
                    }
                }

                return selectedSearch;

            }
            set
            {
                selectedSearch = value;
            }
        }

        internal static string[] UpdateListofSearches()
        {
            Dictionary<int, Control> searchDicList = loadSearchList();
            if (searchDicList != null)
            {
                int _NumofRules = searchDicList.Count;
                searchList = new string[_NumofRules];
                for (int i = 0; i <= _NumofRules - 1; i++)
                {
                    searchList[i] = searchDicList[i].Name;
                }
            }
            return searchList;
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
        [Browsable(true)]
        public new bool Visible { get { return base.Visible; } set { visible = value; base.Visible = value; } }  private List<string> visibleTo = new List<string>(new string[] { "All" });
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

        public string paginal;
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of Paging Control."), Category("Data")]
        public string Paginal
        {
            get
            {
                return paginal;
            }
            set
            {
                Dictionary<string, Control> PagingDicList = loadPagingList();
                if (value == null || string.IsNullOrEmpty(value))
                {
                    paginal = "";
                }
                else if (PagingDicList.ContainsKey(value))
                {
                    paginal = value;
                }
                else
                {
                    MessageBox.Show("Paging Control with this name does not exist.");
                    paginal = "";
                }
            }
        }

        private static Dictionary<string, Control> loadPagingList()
        {
            Dictionary<string, Control> PagingDictionary = new Dictionary<string, Control>();
            int counter = 0;
            if (EIBFormDesigner.Program.currentForm != null)
            {
                BaseWindow baseWindow = EIBFormDesigner.Program.currentForm.currentBaseWindow;
                FindPagingControls(PagingDictionary, counter, baseWindow);
            }
            return PagingDictionary;
        }
        private static void FindPagingControls(Dictionary<string, Control> PagingDictionary, int counter, Control baseWindow)
        {
            foreach (Control control in baseWindow.Controls)
            {
                if ((control is EIBPaging) && !(control.Name.Trim().Equals("")))
                {
                    IEIBControl eibControl = (IEIBControl)control;
                    PagingDictionary.Add(control.Name, control);
                    counter++;
                }
                if (control is EIBPanel || control is EIBTabControl || control is EIBTabPage || control is EIBRadioGroup)
                {
                    FindPagingControls(PagingDictionary, counter, control);
                }
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
                return "lattice";
            }
        }
        private static Dictionary<int, Control> loadSearchList()
        {
            Dictionary<int, Control> searchDictionary = new Dictionary<int, Control>();
            int counter = 0;
            if (EIBFormDesigner.Program.currentForm != null)
            {
                BaseWindow baseWindow = EIBFormDesigner.Program.currentForm.currentBaseWindow;
                if (baseWindow != null)
                {
                    FindSearchControls(searchDictionary, counter, baseWindow);
                }
            }
            return searchDictionary;
        }

        private static void FindSearchControls(Dictionary<int, Control> searchDictionary, int counter, Control baseWindow)
        {
            foreach (Control control in baseWindow.Controls)
            {
                if ((control is EIBSearch) && !(control.Name.Trim().Equals("")))
                {
                    IEIBControl eibControl = (IEIBControl)control;
                    //this.searchList.Items.Add(eibControl.ControlName);
                    searchDictionary.Add(counter, control);
                    counter++;
                }
                if (control is EIBPanel || control is EIBTabControl || control is EIBTabPage || control is EIBRadioGroup)
                {
                    FindSearchControls(searchDictionary, counter, control);
                }
            }
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            designerForm = form;
            /*
            this.CellBorderStyle = TableLayoutPanelCellBorderStyle.Inset;
             */
            this.BackColor = Color.White;
            this.Size = new Size(width, height);
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "lattice" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            if (string.IsNullOrEmpty(this.UniqueId))
            {
                this.UniqueId = this.Name;
            }
            if (this.Text == null)
            {
                this.Text = this.Name;
            }
            this.Margin = new Padding(0, 0, 0, 0);
            this.Text = "lattice" + counter.ToString();
            this.Font = SystemFonts.DefaultFont;
            counter++;
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

        private bool multiSelect;
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Multiselect"), Category("Appearance")]
        public bool MultiSelect
        {
            get
            {
                return multiSelect;
            }
            set
            {

                multiSelect = value;
            }
        }
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.Container components = null;

        private int _space = 0;

        private RowCollection rows = null;

        #region constructor

        public EIBLattice()
        {
            // This call is required by the Windows.Forms Form Designer.
            InitializeComponent();
            this.Layout += new LayoutEventHandler(FormDesigner.eventManager.LatticeControl_Layout);

            this.Dock = DockStyle.None;
            this.Align = ContainerAlignment.None;
            this.BackColor = Color.White;
        }

        #endregion

        #region Public properties

        [
        Category("Data"),
        Description("Rows in this table"),
        EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
        ]
        public RowCollection Rows
        {
            get
            {
                if (this.rows == null)
                    this.rows = new RowCollection(this);
                return this.rows;
            }
        }

        [
        Category("Data"),
        Description("Space between each row")
        ]
        public int Space
        {
            get { return this._space; }
            set
            {
                this._space = value;
                this.Invalidate();
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

        #region Adding Controls

        protected override void OnControlAdded(ControlEventArgs e)
        {
            // prevent other controls from beeing added
            if (e.Control is EIBScrollableRow)
                base.OnControlAdded(e);
            else
                throw new ArgumentException("You cannot add a Control directly to a table. Please insert a row first.");
        }

        #endregion

        #region Layout Handler

        #endregion
    }
}
