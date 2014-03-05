using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace EIBFormDesigner.ScenarioWizard
{
	/// <summary>
	/// Summary description for TileListItem.
	/// </summary>
	public class TileListItem : System.Windows.Forms.UserControl
	{
        private System.Windows.Forms.PictureBox picBox;
		public new event MouseEventHandler MouseDown=null;
		public new event MouseEventHandler MouseUp=null;
		public new event EventHandler DoubleClick=null;
		public event EventHandler ItemSelected=null;
		private ToolTip itemTip;
        private Label lblTitle;
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public TileListItem()
		{
			// This call is required by the Windows.Forms Form Designer.
			InitializeComponent();

			// TODO: Add any initialization after the InitializeComponent call
			InitializeMyComponent();
			this.id=Guid.NewGuid().ToString();
		}

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

		#region Component Designer generated code
		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            this.picBox = new System.Windows.Forms.PictureBox();
            this.lblTitle = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.picBox)).BeginInit();
            this.SuspendLayout();
            // 
            // picBox
            // 
            this.picBox.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.picBox.BackColor = System.Drawing.Color.Transparent;
            this.picBox.Location = new System.Drawing.Point(12, 27);
            this.picBox.Name = "picBox";
            this.picBox.Size = new System.Drawing.Size(49, 32);
            this.picBox.TabIndex = 0;
            this.picBox.TabStop = false;
            this.picBox.DoubleClick += new System.EventHandler(this.TileListItem_DoubleClick);
            this.picBox.MouseDown += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseDown);
            this.picBox.MouseMove += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseMove);
            this.picBox.MouseUp += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseUp);
            this.picBox.MouseEnter += new System.EventHandler(this.TileListItem_MouseEnter);
            // 
            // lblTitle
            // 
            this.lblTitle.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.lblTitle.BackColor = System.Drawing.Color.Transparent;
            this.lblTitle.Location = new System.Drawing.Point(12, 77);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(106, 32);
            this.lblTitle.TabIndex = 1;
            this.lblTitle.DoubleClick += new System.EventHandler(this.TileListItem_DoubleClick);
            this.lblTitle.MouseDown += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseDown);
            this.lblTitle.MouseMove += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseMove);
            this.lblTitle.MouseEnter += new System.EventHandler(this.TileListItem_MouseEnter);
            this.lblTitle.MouseUp += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseUp);
            // 
            // TileListItem
            // 
            this.BackColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.Controls.Add(this.lblTitle);
            this.Controls.Add(this.picBox);
            this.Name = "TileListItem";
            this.Size = new System.Drawing.Size(145, 118);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseMove);
            this.MouseEnter += new System.EventHandler(this.TileListItem_MouseEnter);
            this.MouseLeave += new System.EventHandler(this.TileListItem_MouseLeave);
            ((System.ComponentModel.ISupportInitialize)(this.picBox)).EndInit();
            this.ResumeLayout(false);

		}
		#endregion
		private void InitializeMyComponent()
		{
			base.MouseUp += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseUp);
			base.DoubleClick += new System.EventHandler(this.TileListItem_DoubleClick);
			base.MouseDown += new System.Windows.Forms.MouseEventHandler(this.TileListItem_MouseDown);
			itemTip=new ToolTip();
			itemTip.Active=false;
		}
		private void TileListItem_MouseEnter(object sender, System.EventArgs e)
		{
			if (selected==false)
			{
				if (horverImage!=null)
				{
					this.BackgroundImage=horverImage;
				}
				else
					this.BackColor=horverColor;
			}
		}

		private void TileListItem_MouseLeave(object sender, System.EventArgs e)
		{
			if (selected==false)
			{
				if (normalImage!=null)
				{
					this.BackgroundImage=normalImage;
				}
				else
					this.BackColor=normalColor;
			}
		}
		private string id="";
		public string ID
		{
			get
			{
				return id;
			}
			set
			{
				id=value;
			}
		}
		private string type;
		public string Type
		{
			get
			{
				return type;
			}
			set
			{
				type=value;
				SetToolTipText();
			}
		}
		private bool selected=false;
		public bool Selected
		{
			get
			{
				return selected;
			}
			set
			{
				selected=value;
				if (this.selected==true)
				{
					if (selectionImage!=null)
					{
						this.BackgroundImage=selectionImage;
					}
					else
					{
						this.BackColor=selectionColor;
					}
					
					if (ItemSelected!=null)
					{
						ItemSelected(this,new EventArgs());
					}
				}
				else
				{
					if (normalImage!=null)
					{
						this.BackgroundImage=normalImage;
					}
					else
						this.BackColor=normalColor;
				}
			}
		}
		public System.Drawing.Image Image
		{
			get
			{
				return this.picBox.Image;
			}
			set
			{
				this.picBox.Image=value;
			}
		}
		private int imageIndex=0;
		public int ImageIndex
		{
			get
			{
				return imageIndex;
			}
			set
			{
				imageIndex=value;
                if(this.Parent!=null && this.Parent is ControlListView)
				{
					ControlListView listView=(ControlListView)this.Parent;
					this.Image = listView.ItemIconImageList.Images[imageIndex];
				}
			}
		}
		private DataRow attachmentDataRow=null;
		public DataRow AttachmentDataRow
		{
			get
			{
				return attachmentDataRow;
			}
			set
			{
				attachmentDataRow=value;
			}
		}
		public string Title
		{
			get
			{
				return lblTitle.Text;
			}
			set
			{
				lblTitle.Text=value;
				if(this.AttachmentDataRow!=null)
				{
					AttachmentDataRow["Title"]=value;
				}
				SetToolTipText();
			}
		}

		private void SetToolTipText()
		{
			string tooltipText="";
			tooltipText+="Title: " + this.Title + "\r\n";
			tooltipText+="Type: " + this.Type + "\r\n";
			itemTip.SetToolTip(this,tooltipText);
			itemTip.SetToolTip(this.lblTitle,tooltipText);
			itemTip.SetToolTip(this.picBox,tooltipText);
		}
		private System.Drawing.Image selectionImage=null;
		public System.Drawing.Image SelectionImage
		{
			get
			{
				return selectionImage;
			}
			set
			{
				selectionImage=value;
			}
		}
		private System.Drawing.Image horverImage=null;
		public System.Drawing.Image HorverImage
		{
			get
			{
				return horverImage;
			}
			set
			{
				horverImage=value;
			}
		}
		private System.Drawing.Image normalImage=null;
		public System.Drawing.Image NormalImage
		{
			get
			{
				return normalImage;
			}
			set
			{
				normalImage=value;
			}
		}
		private System.Drawing.Color selectionColor;
		public System.Drawing.Color SelectionColor
		{
			get
			{
				return selectionColor;
			}
			set
			{
				selectionColor=value;
			}
		}
		private System.Drawing.Color horverColor;
		public System.Drawing.Color HorverColor
		{
			get
			{
				return horverColor;
			}
			set
			{
				horverColor=value;
			}
		}
		private System.Drawing.Color normalColor;
		public System.Drawing.Color NormalColor
		{
			get
			{
				return normalColor;
			}
			set
			{
				normalColor=value;
			}
		}
		public System.Windows.Forms.PictureBox PictureBox
		{
			get
			{
				return picBox;
			}
		}
		public System.Windows.Forms.Label TitleLabel
		{
			get
			{
				return lblTitle;
			}
		}

		private bool showToolTips=false;
		public bool ShowToolTips
		{
			get
			{
				return showToolTips;
			}
			set
			{
				showToolTips=value;
				if (showToolTips==true)
				{
					itemTip.Active=true;
				}
				else
				{
					itemTip.Active=false;
				}
			}
		}

		private void TileListItem_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			if (selected==false)
			{
				if (horverImage!=null)
				{
					this.BackgroundImage=horverImage;
				}
				else
					this.BackColor=horverColor;
			}
		}

		private void TileListItem_MouseHover(object sender, System.EventArgs e)
		{
			if (selected==false)
			{
				if (horverImage!=null)
				{
					this.BackgroundImage=horverImage;
				}
				else
					this.BackColor=horverColor;
			}
		}


		private void TileListItem_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			if (Selected==false)
			{
				Selected=true;
			}
			if (MouseDown!=null)
			{
				MouseDown(sender,e);
			}
		}

		private void TileListItem_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			if (MouseUp!=null)
			{
				MouseUp(sender,e);
			}
		}

		private void TileListItem_DoubleClick(object sender, EventArgs e)
		{
			if (DoubleClick!=null)
			{
				DoubleClick(this,e);
			}
		}






	}
}
