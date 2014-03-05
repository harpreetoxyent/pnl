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
using System.Drawing.Design;

namespace EIBFormDesigner.Database.Table
{
    public class EIBTableConnector : EIBTableConnectorBase
    {

    }
    public class Line
    {
        public EIBTable mark1;
        public EIBTable mark2;
        public int Width;
        public bool isDeleted;
    }
    public class EIBTableConnectorBase : Control, IEIBControl
    {

        internal static int counter = 0;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        internal EIBTable mark1;
        internal EIBTable mark2;
        private string isMandatory;
        private string isForm;
        private string dataPatternName;
        private string dataTableName;
        private string dataFieldName;
        internal Line line ;
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

        public EIBTable Mark1
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
        public EIBTable Mark2
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
        private bool isSelected = false;
        private int _X, _Y;
        private Container components = null;
        Bitmap bmpBack = null;
        public static EIBFormDesigner.Designer.FormDesigner form = null;
        public Panel baseFrame;
        private System.Windows.Forms.ContextMenu linecmenu;
        private XmlNode parentXmlNode;
        public string ControlType
        {
            get
            {
                return "line";
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
        public EIBTableConnectorBase()
        {
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            this.linecmenu = new System.Windows.Forms.ContextMenu();
        }
        public void createLine()
        {
            try
            {
                //Adds red marks that are the beginning/end of the line
//                mark1.ContextMenu = linecmenu;

//                mark2.ContextMenu = linecmenu;

                //Line Struct contains the information for a single line
                line = new Line();
                line.mark1 = mark1;
                line.mark2 = mark2;
                line.Width = 2;
                line.isDeleted = false;

                mark1.tableConnectorList.Add(this);
                mark2.tableConnectorList.Add(this);

                //Events for moving marks
                mark1.Disposed += new System.EventHandler(mark1_Disposed);
                if (!mark1.isMouseUp)
                {
                    mark1.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseUp);
                    mark1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseDown);
                }
                //mark1.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseMove);

                if (!mark1.isMouseUp)
                {
                    mark2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseUp);
                    mark2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseDown);
                }
                //mark2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseMove);

                //Adds Line object to an arraylist
                ((BaseWindow)this.baseFrame.Parent).Lines.Add(line);
                Redraw();
            }
            catch (System.Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        public void AssociateMarkUpandDown(EIBTable table)
        {
            table.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseUp);
            table.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mark_MouseDown);
        }

        void mark1_Disposed(object sender, System.EventArgs e)
        {
            //MessageBox.Show("Deleted");
            //throw new System.Exception("The method or operation is not implemented.");
        }

        //Redraws all the lines and the background too
        public void Redraw()
        {
            bmpBack = new Bitmap(baseFrame.Width, baseFrame.Height);
            Graphics.FromImage(bmpBack).Clear(baseFrame.BackColor);

            if (bmpBack != null)
            {
                baseFrame.BackgroundImage = (Bitmap)bmpBack.Clone();
            }
            else
            {
                baseFrame.BackgroundImage = new Bitmap(baseFrame.BackgroundImage.Width, baseFrame.BackgroundImage.Height);
                Graphics.FromImage(baseFrame.BackgroundImage).Clear(Color.Transparent);
            }

            foreach (Line l in  ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (!l.isDeleted)
                {
                    DrawLine(l);
                }
            }
            //baseFrame.Refresh();
        }
        public void Redraw(float width)
        {
            baseFrame.SuspendLayout();
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
                    DrawLine(l, width);
                }
            }
            baseFrame.ResumeLayout(false);
            baseFrame.PerformLayout();

            baseFrame.Refresh();
        }

        //It redraws any table with out any table connector.
        /*public static void RedrawAny()
        {
            bmpBack = new Bitmap(baseFrame.Width, baseFrame.Height);
            Graphics.FromImage(bmpBack).Clear(baseFrame.BackColor);

            if (bmpBack != null)
            {
                baseFrame.BackgroundImage = (Bitmap)bmpBack.Clone();
            }
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
            //baseFrame.Refresh();
        }*/
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
                EIBTable mc1 = (EIBTable)sender;
                Line l = getLineByMark(mc1);

                Point p = new Point(e.X - _X + mc1.Left, e.Y - _Y + mc1.Top);

                mc1.Location = p;

                Redraw(l, p);
            }
        }
        //Retrieves a Line object having a mark
        private Line getLineByMark(EIBTable m)
        {
            foreach (Line l in  ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (l.mark1 == m || l.mark2 == m)
                    return l;
            }//Never happens :D
            throw new System.Exception("No line found");
        }

        public void Mark_MouseUp(object sender, MouseEventArgs e)
        {
            isSelected = false;
            ResumeLayout();
            Redraw();
        }
        //Simply draws a line
        private void DrawLine(Line line, float width)
        {
            Graphics g = null;

            g = Graphics.FromImage(baseFrame.BackgroundImage);
            g.DrawLine(new Pen(Color.White, width), line.mark1.Center.X, line.mark1.Center.Y, line.mark2.Center.X, line.mark2.Center.Y);
            g.Dispose();
        }

        //Simply draws a line
        private void DrawLine(Line line)
        {
            Graphics g = null;

            g = Graphics.FromImage(baseFrame.BackgroundImage);
            g.DrawLine(new Pen(Color.RoyalBlue, (float)line.Width), line.mark1.Center.X, line.mark1.Center.Y, line.mark2.Center.X, line.mark2.Center.Y);
            g.Dispose();
        }

        //Simply draws a line
        public void EraseLine(Line line)
        {
            Graphics g = null;

            g = Graphics.FromImage(baseFrame.BackgroundImage);
            g.DrawLine(new Pen(baseFrame.BackColor, (float)line.Width), line.mark1.Center.X, line.mark1.Center.Y, line.mark2.Center.X, line.mark2.Center.Y);
            g.Dispose();
        }

        //Redraws all the lines and a part of the background
        private void Redraw(Line line, Point p)
        {
            Graphics.FromImage(baseFrame.BackgroundImage).DrawImage(bmpBack, 0, 0, baseFrame.BackgroundImage.Width,
                baseFrame.BackgroundImage.Height);
            foreach (Line l in  ((BaseWindow)this.baseFrame.Parent).Lines)
            {
                if (!l.isDeleted)
                {
                    DrawLine(l);
                }
            }
            Region r = getRegionByLine(line, p);
            //Region r = baseFrame.Region;
            baseFrame.Invalidate(r);
            baseFrame.Update();
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
            bmpBack = new Bitmap(baseFrame.Width, baseFrame.Height);
            Graphics.FromImage(bmpBack).Clear(baseFrame.BackColor);
            this.BackgroundImage = (Bitmap)bmpBack.Clone();
            this.Name = "tableconnector" + counter;
            this.controlName = this.Name;
            counter++;
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
