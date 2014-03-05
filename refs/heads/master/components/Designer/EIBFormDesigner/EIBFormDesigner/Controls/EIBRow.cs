using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using System.Xml;
using System.Collections.Generic;
using EIBFormDesigner.Controls.Grid.Collections;
using System.Drawing.Design;

namespace EIBFormDesigner.Controls
{

    /// <summary>
	/// A Row in the Table Control.
	/// </summary>
	[ToolboxItem(false)]
    public class EIBRow : System.Windows.Forms.Panel, ITool, IEIBControl
	{
        internal static int counter = 0;
        int width = 50;
        int height = 30;
        private string defaultValue;
        private string onClickValue;
        private string onDoubleClick;
        private string enteringValue;
        private string exitingValue;
        private string isMandatory;
        private string isForm;
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
     TypeConverter(typeof(EIBFormDesigner.Property.IsForm)), Description("Sets the mandatory field"), Category("Data")]

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

        private bool visible = true; public bool getVisibility() { return visible; }
        [Browsable(false)]
        public new bool Visible { get { return visible; } set { visible = value; } }  private List<string> visibleTo = new List<string>(new string[] { "All" });
        [Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"),EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)),TypeConverter(typeof(VisibleToConverter))]
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
                   // this.Name = value;
                }
            }
        }
        public void InitiateSettings(EIBFormDesigner.Designer.FormDesigner form)
        {
            this.Text = "row" + counter;
            if (string.IsNullOrEmpty(this.Name))
            {
                this.Name = "row" + counter;
            }
            if (string.IsNullOrEmpty(this.ControlName))
            {
                this.ControlName = this.Name;
            }
            
            counter++;
            this.Margin = new Padding(0, 0, 0, 0);
            this.Size = new Size(width, height);
        }
        public string ControlType
        {
            get
            {
                if (this.Parent is EIBLattice)
                    return "lrow";
                else
                    return "row";
            }
        }

		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		private int _space = 0;
		private int _heightPercent = 0;
		private HeightMeasurement _heightTyp = HeightMeasurement.Auto;
		private int _heightPixel = 0;

		private ColumnCollection columns = null;

		/// <summary>
		/// Type of Height can be auto, percent or fixed amount of pixels
		/// </summary>
		public enum HeightMeasurement { Auto, Percent, Pixel };

		#region Constructor

        public EIBRow()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

			this.Layout +=new LayoutEventHandler(RowControl_Layout);

			this.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom;
			this.Dock = DockStyle.None;
		}

		#endregion

		#region Public properties

		[
        Category("Data"),
		Description("Space Between Controls")
		]
		public int Space
		{
			get { return this._space; }
			set 
			{
				this._space = value; 
				this.Invalidate(true);
			}
		}

		[
        Category("Data"),
		Description("Columns"),
		EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
		]
		public ColumnCollection Columns
		{
			get 
			{
				if (this.columns == null)
					this.columns = new ColumnCollection(this);
				return this.columns;
			}
		}

		[
        Category("Data"),
		Description("Set the height of a control in percent of the total hieght of the Table.")
		]
		public int HeightPercent
		{
			get { return this._heightPercent; }
			set 
			{ 
				RowCollection rowcollection = Rows;
				if (rowcollection != null)
				{
					int oldPercent = this.HeightPercent;
					int maxPercent = 0;
                    foreach (EIBRow row in rowcollection)
					{
						if (row.HeightTyp== HeightMeasurement.Percent)
							maxPercent += row.HeightPercent;
					}
					if (maxPercent - oldPercent + value > 100)
					{
						throw new ArgumentException("Height of all rows must not be > 100%");
					}
				}

				this._heightPercent = value; 
			}
		}
		
		[
        Category("Data"),
		Description("Set the height of a control to auto, percentage of the table or a fixed abount of pixels."),
		DefaultValue(HeightMeasurement.Auto)
		]
		public HeightMeasurement HeightTyp
		{
			get { return this._heightTyp; }
			set { this._heightTyp = value; }
		}

		[
		Category("Data"),
		Description("Set the height of a control to a fixed value of pixels.")
		]
		public int HeightPixel
		{
			get { return this._heightPixel; }
			set 
			{ 
				this._heightPixel = value;
                if (this.Parent != null)
                {
                    Panel grid = (Panel)this.Parent;
                    grid.Size = new Size(grid.Size.Width + 1, grid.Size.Height + 1);
                    grid.Size = new Size(grid.Size.Width - 1, grid.Size.Height - 1);
                    grid.Invalidate();
                }
			}
		}
		
		[
        Category("Data"),
		Description("Rows"),
		EditorAttribute(typeof(System.ComponentModel.Design.CollectionEditor), typeof(System.Drawing.Design.UITypeEditor))
		]
		public RowCollection Rows
		{
			get 
			{
				if (this.Parent is EIBGrid)
                    return (this.Parent as EIBGrid).Rows;
                else if (this.Parent is EIBLattice)
                    return (this.Parent as EIBLattice).Rows;
				throw new ArgumentException("Row Control is not placed in a table.");
			}
		}

		#endregion

		#region Disposing

		/// <summary> 
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
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
            if (this.Parent is EIBGrid && e.Control is EIBColumn)
				base.OnControlAdded (e);
            else if (this.Parent is EIBLattice)
                base.OnControlAdded(e);
            else
                throw new ArgumentException("can not add "+e.ToString()+" to this row.");
		}

		#endregion

		#region Layout Handler

		private void RowControl_Layout(object sender, LayoutEventArgs e)
		{
            try
            {

                int count = 0;
                double percentMax = 0;
                double skalePercent = 1;
                int autoWidthColumnCount = 0;
                int fixedPixelWidth = 0;
                foreach (EIBColumn col in this.Columns)
                {
                    if (col.Visible)
                    {
                        count++;
                        if (col.WidthType == WidthMeasurement.Percent)
                        {
                            percentMax += col.WidthPercent;
                        }
                        else if (col.WidthType == WidthMeasurement.Pixel)
                        {
                            fixedPixelWidth = col.WidthPixel;
                        }
                        else
                        {
                            autoWidthColumnCount++;
                        }
                    }
                }
                if (count > 0)
                {
                    if (percentMax > 0)
                    {
                        if (autoWidthColumnCount == 0)
                            skalePercent = 100.0 / percentMax;
                    }
                    double percentAuto = (100.0 - percentMax) / autoWidthColumnCount;

                    int lastPos = 0;
                    int clientWidthWithoutFixed = Math.Max(0, this.ClientSize.Width - fixedPixelWidth);
                    foreach (EIBColumn col in this.Columns)
                    {
                        if (col.Visible)
                        {
                            // fit in row
                            col.Height = this.ClientSize.Height;
                            if (col.WidthType == WidthMeasurement.Auto)
                            {
                                col.Width = (int)(percentAuto / 100.0 * clientWidthWithoutFixed) - (count - 1) * this.Space;
                            }
                            else if (col.WidthType == WidthMeasurement.Percent)
                            {
                                col.Width = (int)(col.WidthPercent / 100.0 * skalePercent * clientWidthWithoutFixed) - (count - 1) * this.Space;
                            }
                            else
                            {
                                col.Width = col.WidthPixel;
                            }

                            // position in row
                            col.Location = new Point(lastPos, 0);
                            if (col.WidthType == WidthMeasurement.Pixel)
                                lastPos += col.Width;
                            else
                                lastPos += col.Width + (count - 1) * this.Space;
                        }
                    }
                }
            }
            catch
            {
            }
		}
		#endregion
	}
}
