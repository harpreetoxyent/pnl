using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using System.Xml.Schema;
using System.Collections;
using System.Xml;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Workflow.Designer.Wizards;
using WeifenLuo.WinFormsUI.Docking;
using EIBFormDesigner.Workflow.Designer.Forms;
using System.Drawing.Design;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Workflow.Common;

namespace EIBFormDesigner.Workflow.Controls
{
    public partial class NodeUserControl : UserControl, ITool, IEIBControl, INodeControl
    {

        private string dataPatternName;
        private string dataTableName;
        private string dataFieldName;
        internal List<EIBNodeConnectorBase> nodeConnectorList = new List<EIBNodeConnectorBase>();
        internal WorkflowNode workflowNode;
        private string nodeValue;
        private string onNodeEnterValue;
        private string onNodeExitClick;
        private string isMandatory;
        private string isForm;

        private List<DataMapping> dataMappings;

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

        public WorkflowNode WorkFlowNode
        {
            get
            {
                return workflowNode;
            }
            set
            {
                workflowNode = value;
            }
        }

        public string DataPatternName
        {
            get
            {
                return dataPatternName;
            }
            set
            {
                dataPatternName = value;
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

        public string DataTableName
        {
            get
            {
                return dataTableName;
            }
            set
            {
                dataTableName = value;
            }
        }
        public string DataFieldName
        {
            get
            {
                return dataFieldName;
            }
            set
            {
                dataFieldName = value;
            }
        }

        public string NodeValue
        {
            get
            {
                return nodeValue;
            }
            set
            {
                nodeValue = value;
            }
        }

        public string OnNodeEnterValue
        {
            get
            {
                return onNodeEnterValue;
            }
            set
            {
                onNodeEnterValue = value;
            }
        }

        public string OnNodeExitClick
        {
            get
            {
                return onNodeExitClick;
            }
            set
            {
                onNodeExitClick = value;
            }
        }

        //	Data types valid for XML file
        int width = 215;
        int height = 150;
        internal static int counter = 0;
        //	 Current item being edited (-1 = new item)
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
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
                controlName = value;
                workflowNode.WorkFlowNodeId = value;
                this.Name = value;
                this.nodeIdText.Text = value;

            }
        }
        //private string controlName;
        //[Browsable(true), DefaultValue(false), EditorBrowsable(EditorBrowsableState.Never), Description("Get the unique id of control"), Category("Appearance")]
        //public string ControlName
        //{
        //    get
        //    {
        //        return controlName;
        //    }
        //    set
        //    {
        //        if (this.controlName == null)// 
        //        {
        //            string retVal = Contr.validateUniqueId(value);
        //            if (retVal.Length > 0)
        //            {
        //                MessageBox.Show(retVal);
        //            }
        //            else
        //            {
        //                uniqueId = value;
        //            }

        //        }
        //        else if (this.uniqueId != value && ControlValidation.doesUniqueIdExist(value))
        //        {
        //            //string tmp = EIBFormDesigner.Program.currentForm.currentBaseWindow.UniqueID;
        //            MessageBox.Show("Control with same UniqueId already exist");
        //            return;
        //        }
        //        if (!(uniqueId == null))
        //        {
        //            string retVal = ControlValidation.validateUniqueId(value);
        //            if (retVal.Length > 0)
        //            {
        //                MessageBox.Show(retVal);
        //            }
        //            else
        //            {
        //                uniqueId = value;
        //            }
        //        }
        //    }
        //}
        public string ControlType
        {
            get
            {
                return "workflownode";
            }
        }		//	Data types valid for XML file


		//	Tooltip for buttons
		private ToolTip m_ToolTip = new ToolTip();

        public NodeUserControl()
		{
			InitializeComponent();
            workflowNode = new WorkflowNode();
            //workflowNode.WorkFlowNodeId = this.ControlName;
            label2.ContextMenuStrip = contextMenuStrip1;
            
		}

		private void FormCreateXMLDB_Load(object sender, EventArgs e)
		{
		}

        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Size = new Size(width, height);
            this.Name = "node" + counter;
            if (string.IsNullOrEmpty(this.controlName))
            {
                this.ControlName = this.Name;
            }
            this.Text = "node" + counter;
            this.nodeIdText.Text = this.WorkFlowNode.WorkFlowNodeId;
            this.nodeIdText.Enabled = false;
            counter++;
        }

        private void doneButon_Click(object sender, EventArgs e)
        {
            //WorkflowPropertyEditor wfEditor = new WorkflowPropertyEditor();
            //wfEditor.ShowDialog(this);
            NodeConfigurationWizard nodeConfigurationWizard = new NodeConfigurationWizard((EIBNode)this);
            nodeConfigurationWizard.ShowDialog(this);
        }

        private void doneButton_Click(object sender, EventArgs e)
        {
            if (this.nodeIdText.Text.Trim().Equals(""))
            {
                MessageBox.Show("Node Id can not be blank");
                return;
            }
            if (this.Parent.Parent != null)
            {
                if (!(((BaseWindow)this.Parent.Parent).WorkflowNodes.ContainsKey(this.nodeIdText.Text)))
                {
                    this.workflowNode.WorkFlowNodeId = this.nodeIdText.Text;
                    ((BaseWindow)this.Parent.Parent).WorkflowNodes.Add(this.nodeIdText.Text,this);
                }
            }
        }

        private void formPatternToolStripMenuItem_Click(object sender, EventArgs e)
        {
            WorkflowFormPattern workflowFormPattern = WorkflowFormPattern.CreateInstance(false,(EIBNode)this);
            workflowFormPattern.CheckListofFormpatterns();
            workflowFormPattern.button1.Visible = true;
            workflowFormPattern.button2.Visible = true;

            workflowFormPattern.ShowDialog(this);
        }

        private void dataPatternToolStripMenuItem_Click(object sender, EventArgs e)
        {
            WorkflowDataPattern workflowDataPattern = WorkflowDataPattern.CreateInstance(false,(EIBNode)this);
            workflowDataPattern.CheckListofDatapatterns();
            workflowDataPattern.button1.Visible = true;
            workflowDataPattern.button2.Visible = true;
            workflowDataPattern.ShowDialog(this);
        }

        private void userPatternToolStripMenuItem_Click(object sender, EventArgs e)
        {
            WorkflowUserPattern workflowUserPattern = WorkflowUserPattern.CreateInstance(false,(EIBNode)this);
            workflowUserPattern.CheckListofUserpatterns();
            workflowUserPattern.button3.Visible = true;
            workflowUserPattern.button4.Visible = true;
            workflowUserPattern.ShowDialog(this);
        }

	}
}




















