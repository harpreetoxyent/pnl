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
using System.Drawing.Drawing2D;
using System.Xml;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Workflow.Node;
using System.Drawing.Design;

namespace EIBFormDesigner.Workflow.Controls
{
    public class EIBNodeConnector : EIBNodeConnectorBase
    {

    }
    public class Line
    {
        public EIBNode mark1;
        public EIBNode mark2;
        public MarkControl centerMark;
        public int Width;
        public bool isDeleted;
       

    }
    public class EIBNodeConnectorBase : Control, IEIBControl, INodeControl
    {
        internal bool isSelected = false;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        internal EIBNode mark1;
        internal EIBNode mark2;
        internal MarkControl centerMark;
        private string dataPatternName;
        private string dataTableName;
        private string dataFieldName;
        internal Line line;
        private string isMandatory;
        private string isForm;
        private string nodeValue;
        private string onNodeEnterValue;
        private string onNodeExitClick;
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

        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
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
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datatable"), Category("Data")]
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
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datafield"), Category("Data")]
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

        public EIBNode Mark1
        {
            get
            {
                return mark1;
            }
            set
            {
                mark1 = value;
            }
        }
        public MarkControl CenterMark
        {
            get
            {
                return centerMark;
            }
            set
            {
                centerMark = value;
            }
        }
        public EIBNode Mark2
        {
            get
            {
                return mark2;
            }
            set
            {
                mark2 = value;
            }
        }
        private int _X, _Y;
        private Container components = null;
        Bitmap bmpBack = null;
        public static EIBFormDesigner.Designer.FormDesigner form = null;
        public EIBPanel baseFrame;
        private System.Windows.Forms.ContextMenu linecmenu;
        internal static int counter = 0;
        private XmlNode parentXmlNode;
        public string ControlType
        {
            get
            {
                return "connector";
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
            }
        }
        public EIBNodeConnectorBase()
        {
            /*this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);*/
            this.linecmenu = new System.Windows.Forms.ContextMenu();
            this.centerMark = new MarkControl();
            this.centerMark.parentConnector = this;
                       
        }
        public void AssociateMarkUpandDown(EIBNode node)
        {
            node.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseUp);
            node.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseDown);
        }


        public void createLine()
        {
            try
            {
                //Adds red marks that are the beginning/end of the line
                //mark1.ContextMenu = linecmenu;
                //mark2.ContextMenu = linecmenu;
                //Line Struct contains the information for a single line
                line = new Line();
                line.mark1 = mark1;
                line.mark2 = mark2;
                mark1.nodeConnectorList.Add(this);
                mark2.nodeConnectorList.Add(this);
                line.centerMark = centerMark;
                line.Width = 2;
                line.isDeleted = false;
                int centerMarkX = (line.mark1.Center.X + line.mark2.Center.X) / 2;
                int centerMarkY = (line.mark1.Center.Y + line.mark2.Center.Y) / 2;
                line.centerMark.Location = new Point(centerMarkX - 4, centerMarkY - 4);
                baseFrame.Controls.Add(line.centerMark);
                //Events for moving marks
                if (!mark1.isMouseUp)
                {
                    mark1.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseUp);
                    mark1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseDown);
                }
                //mark1.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseMove);
                if (!mark2.isMouseUp)
                {
                    mark2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseUp);
                    mark2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseDown);
                }
                //mark2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseMove);
                centerMark.MouseClick += new MouseEventHandler(centerMark_MouseClick);
                centerMark.MouseDoubleClick += new MouseEventHandler(centerMark_MouseDoubleClick);
                //Adds Line object to an arraylist
                ((BaseWindow)this.baseFrame.Parent).Lines.Add(line);
                Redraw();
            }
            catch (System.Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        void centerMark_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            MarkControl markControl = (MarkControl)sender;
            EIBNodeConnectorBase selnodeConnector = (EIBNodeConnectorBase)markControl.parentConnector;
            Graphics g = null;
            g = Graphics.FromImage(baseFrame.BackgroundImage);
            g.Clear(baseFrame.BackColor);

            foreach (Line l in ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                EIBNodeConnectorBase nodeConnector = (EIBNodeConnectorBase)l.centerMark.parentConnector;
                int centerMarkX = (nodeConnector.mark1.Center.X + nodeConnector.mark2.Center.X) / 2;
                int centerMarkY = (nodeConnector.mark1.Center.Y + nodeConnector.mark2.Center.Y) / 2;
                if (!l.isDeleted)
                {
                    if (l.centerMark.parentConnector != selnodeConnector)
                    {
                        l.centerMark.parentConnector.isSelected = false;
                    }
                    else
                    {
                        l.centerMark.parentConnector.isSelected = true;
                    }
                    if (l.centerMark.parentConnector.isSelected)
                    {
                        Pen redPen = new Pen(Color.Red, (float)2);
                        g.DrawLine(redPen, nodeConnector.mark1.Center.X, nodeConnector.mark1.Center.Y, centerMarkX, centerMarkY);
                        g.DrawLine(redPen, centerMarkX, centerMarkY, nodeConnector.mark2.Center.X, nodeConnector.mark2.Center.Y);
                        NodeRelationManager nodeRelation = new NodeRelationManager();
                        nodeRelation.eibRelation1.firstTableName.Items.Add(nodeConnector.mark1.WorkFlowNode.WorkFlowNodeId);
                        nodeRelation.eibRelation1.secondTableName.Items.Add(nodeConnector.mark2.WorkFlowNode.WorkFlowNodeId);
                        nodeRelation.eibRelation1.firstTableName.SelectedIndex = 0;
                        nodeRelation.eibRelation1.secondTableName.SelectedIndex = 0;
                        nodeRelation.eibRelation1.relationName.Text = nodeConnector.ControlName;
                        nodeRelation.ShowDialog();
                    }
                    else
                    {
                        Pen royalBluePen = new Pen(Color.RoyalBlue, (float)2);
                        g.DrawLine(royalBluePen, nodeConnector.mark1.Center.X, nodeConnector.mark1.Center.Y, centerMarkX, centerMarkY);
                        g.DrawLine(royalBluePen, centerMarkX, centerMarkY, nodeConnector.mark2.Center.X, nodeConnector.mark2.Center.Y);
                    }
                }
            }
            baseFrame.Invalidate();
            baseFrame.Update();
            g.Dispose();
            
            /*EIBNodeConnectorBase nodeConnector = (EIBNodeConnectorBase)markControl.parentConnector;
            Graphics g = null;
            g = Graphics.FromImage(baseFrame.BackgroundImage);
            int centerMarkX = (nodeConnector.mark1.Center.X + nodeConnector.mark2.Center.X) / 2;
            int centerMarkY = (nodeConnector.mark1.Center.Y + nodeConnector.mark2.Center.Y) / 2;
            //
            foreach (Line l in ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (!l.isDeleted)
                {
                    if (!isSelected)
                    {
                        isSelected = true;
                        g.DrawLine(new Pen(Color.Red, (float)2), nodeConnector.mark1.Center.X, nodeConnector.mark1.Center.Y, centerMarkX, centerMarkY);
                        g.DrawLine(new Pen(Color.Red, (float)2), centerMarkX, centerMarkY, nodeConnector.mark2.Center.X, nodeConnector.mark2.Center.Y);
                        

                    }
                    else
                    {
                        isSelected = false;
                        g.DrawLine(new Pen(Color.RoyalBlue, (float)2), nodeConnector.mark1.Center.X, nodeConnector.mark1.Center.Y, centerMarkX, centerMarkY);
                        g.DrawLine(new Pen(Color.RoyalBlue, (float)2), centerMarkX, centerMarkY, nodeConnector.mark2.Center.X, nodeConnector.mark2.Center.Y);
                    }
                }
                baseFrame.Invalidate();
                baseFrame.Update();
            }
            g.Dispose();*/
        }

        void centerMark_MouseClick(object sender, MouseEventArgs e)
        {
            MarkControl markControl = (MarkControl)sender;
            EIBNodeConnectorBase selnodeConnector = (EIBNodeConnectorBase)markControl.parentConnector;
            Graphics g = null;
            g = Graphics.FromImage(baseFrame.BackgroundImage);
            g.Clear(baseFrame.BackColor);

            foreach (Line l in ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                EIBNodeConnectorBase nodeConnector = (EIBNodeConnectorBase)l.centerMark.parentConnector;
                int centerMarkX = (nodeConnector.mark1.Center.X + nodeConnector.mark2.Center.X) / 2;
                int centerMarkY = (nodeConnector.mark1.Center.Y + nodeConnector.mark2.Center.Y) / 2;
                if (!l.isDeleted)
                {
                    if (l.centerMark.parentConnector != selnodeConnector)
                    {
                        l.centerMark.parentConnector.isSelected = false;
                    }
                    else
                    {
                        l.centerMark.parentConnector.isSelected = true;
                    }
                    if (l.centerMark.parentConnector.isSelected)
                    {
                        Pen redPen = new Pen(Color.Red, (float)2);
                        g.DrawLine(redPen, nodeConnector.mark1.Center.X, nodeConnector.mark1.Center.Y, centerMarkX, centerMarkY);
                        g.DrawLine(redPen, centerMarkX, centerMarkY, nodeConnector.mark2.Center.X, nodeConnector.mark2.Center.Y);
                    }
                    else
                    {
                        Pen royalBluePen = new Pen(Color.RoyalBlue, (float)2);
                        g.DrawLine(royalBluePen , nodeConnector.mark1.Center.X, nodeConnector.mark1.Center.Y, centerMarkX, centerMarkY);
                        g.DrawLine(royalBluePen , centerMarkX, centerMarkY, nodeConnector.mark2.Center.X, nodeConnector.mark2.Center.Y);
                    }
                }
            }
            baseFrame.Invalidate();
            //baseFrame.Update();
            g.Dispose();
        }

        //Redraws all the lines and the background too
        public void Redraw()
        {
            bmpBack = new Bitmap(baseFrame.Width, baseFrame.Height);
            Graphics.FromImage(bmpBack).Clear(baseFrame.BackColor);
            if (bmpBack != null)
                baseFrame.BackgroundImage = (Bitmap)bmpBack.Clone();
            else
            {
                baseFrame.BackgroundImage = new Bitmap(baseFrame.BackgroundImage.Width, baseFrame.BackgroundImage.Height);
                Graphics.FromImage(baseFrame.BackgroundImage).Clear(Color.Transparent);
            }

            foreach (Line l in ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (!l.isDeleted)
                {
                    DrawLine(l);
                }
            }
            baseFrame.Invalidate();
            //baseFrame.Update();
        }
        private void Mark_MouseDown(object sender, MouseEventArgs e)
        {
            this.SuspendLayout();
            isSelected = true;
            _X = e.X;
            _Y = e.Y;
        }

        private void Mark_MouseMove(object sender, MouseEventArgs e)
        {
            if (isSelected)
            {
                EIBNode mc1 = (EIBNode)sender;
                Line l = getLineByMark(mc1);

                Point p = new Point(e.X - _X + mc1.Left, e.Y - _Y + mc1.Top);

                mc1.Location = p;

                Redraw(l, p);
            }
        }
        //Retrieves a Line object having a mark
        private Line getLineByMark(EIBNode m)
        {
            foreach (Line l in ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (l.mark1 == m || l.mark2 == m)
                    return l;
            }//Never happens :D
            throw new System.Exception("No line found");
        }

        private  void Mark_MouseUp(object sender, MouseEventArgs e)
        {
            isSelected = false;
            ResumeLayout();
            Redraw();
        }
        //Simply draws a line
        private void DrawLine(Line line)
        {
            Graphics g = null;
            g = Graphics.FromImage(baseFrame.BackgroundImage);
            int centerMarkX = (line.mark1.Center.X + line.mark2.Center.X) / 2;
            int centerMarkY = (line.mark1.Center.Y + line.mark2.Center.Y) / 2;
            line.centerMark.Location = new Point(centerMarkX - 4, centerMarkY - 4);
            g.DrawLine(new Pen(Color.RoyalBlue, (float)line.Width), line.mark1.Center.X, line.mark1.Center.Y, centerMarkX, centerMarkY);
            g.DrawLine(new Pen(Color.RoyalBlue, (float)line.Width), centerMarkX, centerMarkY, line.mark2.Center.X, line.mark2.Center.Y);
            g.Dispose();
        }

        public void EraseLine(Line line)
        {
            Graphics g = null;
            g = Graphics.FromImage(baseFrame.BackgroundImage);
            g.DrawLine(new Pen(baseFrame.BackColor, (float)line.Width), line.mark1.Center.X, line.mark1.Center.Y, line.mark2.Center.X, line.mark2.Center.Y);
            g.Dispose();
            centerMark.Dispose(); 
        }

        //Redraws all the lines and a part of the background
        private void Redraw(Line line, Point p)
        {
            Graphics.FromImage(baseFrame.BackgroundImage).DrawImage(bmpBack, 0, 0, baseFrame.BackgroundImage.Width,
                baseFrame.BackgroundImage.Height);
            foreach (Line l in ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (!l.isDeleted)
                {
                    DrawLine(l);
                }
            }
            //Region r = getRegionByLine(line, p);
            //Region r = baseFrame.Region;
            //baseFrame.Invalidate();
            //baseFrame.Update();
        }
        //Returns the region to update
        private Region getRegionByLine(Line l, Point p)
        {
            GraphicsPath gp = new GraphicsPath();
            gp.AddPolygon(new Point[] { l.mark1.Center, l.mark2.Center, p, l.mark1.Center });

            RectangleF rf = gp.GetBounds();
            gp.Dispose();

            rf.Inflate(100f, 100f);

            return new Region(rf);
        }
        public void InitiateSettings(EIBPanel baseFrame)
        {
            this.baseFrame = baseFrame;
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "nodeconnector" + counter.ToString();
                counter++;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            bmpBack = new Bitmap(baseFrame.Width, baseFrame.Height);
            Graphics.FromImage(bmpBack).Clear(baseFrame.BackColor);
            this.BackgroundImage = (Bitmap)bmpBack.Clone();
            this.Font = SystemFonts.DefaultFont;
            
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            //
        }
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }
        #region Component Designer generated code

        private void InitializeComponent()
        {
            this.Name = "LineControl";

        }
        #endregion
    }

}
