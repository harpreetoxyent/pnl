namespace EIBFormDesigner.Workflow.Controls
{
    partial class NodeUserControl
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
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
		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            this.components = new System.ComponentModel.Container();
            this.editNodeButton = new System.Windows.Forms.Button();
            this.nodeIdText = new System.Windows.Forms.TextBox();
            this.NodeId = new System.Windows.Forms.Label();
            this.doneButton = new System.Windows.Forms.Button();
            this.startNodeCheck = new System.Windows.Forms.CheckBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.configureNodeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.formPatternToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.dataPatternToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.userPatternToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // editNodeButton
            // 
            this.editNodeButton.Location = new System.Drawing.Point(13, 84);
            this.editNodeButton.Name = "editNodeButton";
            this.editNodeButton.Size = new System.Drawing.Size(62, 26);
            this.editNodeButton.TabIndex = 6;
            this.editNodeButton.Text = "&Edit Node";
            this.editNodeButton.UseVisualStyleBackColor = true;
            this.editNodeButton.Click += new System.EventHandler(this.doneButon_Click);
            // 
            // nodeIdText
            // 
            this.nodeIdText.Location = new System.Drawing.Point(84, 26);
            this.nodeIdText.Margin = new System.Windows.Forms.Padding(2);
            this.nodeIdText.Name = "nodeIdText";
            this.nodeIdText.Size = new System.Drawing.Size(76, 20);
            this.nodeIdText.TabIndex = 1;
            // 
            // NodeId
            // 
            this.NodeId.AutoSize = true;
            this.NodeId.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.NodeId.Location = new System.Drawing.Point(10, 28);
            this.NodeId.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.NodeId.Name = "NodeId";
            this.NodeId.Size = new System.Drawing.Size(57, 17);
            this.NodeId.TabIndex = 7;
            this.NodeId.Text = "Node Id";
            // 
            // doneButton
            // 
            this.doneButton.Location = new System.Drawing.Point(90, 84);
            this.doneButton.Margin = new System.Windows.Forms.Padding(2);
            this.doneButton.Name = "doneButton";
            this.doneButton.Size = new System.Drawing.Size(56, 26);
            this.doneButton.TabIndex = 8;
            this.doneButton.Text = "Done";
            this.doneButton.UseVisualStyleBackColor = true;
            this.doneButton.Click += new System.EventHandler(this.doneButton_Click);
            // 
            // startNodeCheck
            // 
            this.startNodeCheck.AutoSize = true;
            this.startNodeCheck.Location = new System.Drawing.Point(84, 52);
            this.startNodeCheck.Margin = new System.Windows.Forms.Padding(2);
            this.startNodeCheck.Name = "startNodeCheck";
            this.startNodeCheck.Size = new System.Drawing.Size(15, 14);
            this.startNodeCheck.TabIndex = 9;
            this.startNodeCheck.UseVisualStyleBackColor = true;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(10, 52);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(72, 17);
            this.label1.TabIndex = 10;
            this.label1.Text = "StartNode";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.BackColor = System.Drawing.SystemColors.ControlText;
            this.label2.Dock = System.Windows.Forms.DockStyle.Top;
            this.label2.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.label2.Location = new System.Drawing.Point(0, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(13, 13);
            this.label2.TabIndex = 11;
            this.label2.Text = ">";
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.configureNodeToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(161, 26);
            // 
            // configureNodeToolStripMenuItem
            // 
            this.configureNodeToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.formPatternToolStripMenuItem,
            this.toolStripSeparator1,
            this.dataPatternToolStripMenuItem,
            this.toolStripSeparator2,
            this.userPatternToolStripMenuItem});
            this.configureNodeToolStripMenuItem.Name = "configureNodeToolStripMenuItem";
            this.configureNodeToolStripMenuItem.Size = new System.Drawing.Size(160, 22);
            this.configureNodeToolStripMenuItem.Text = "Con&figure Node";
            // 
            // formPatternToolStripMenuItem
            // 
            this.formPatternToolStripMenuItem.Name = "formPatternToolStripMenuItem";
            this.formPatternToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.formPatternToolStripMenuItem.Text = "Form Pattern";
            this.formPatternToolStripMenuItem.Click += new System.EventHandler(this.formPatternToolStripMenuItem_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(145, 6);
            // 
            // dataPatternToolStripMenuItem
            // 
            this.dataPatternToolStripMenuItem.Name = "dataPatternToolStripMenuItem";
            this.dataPatternToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.dataPatternToolStripMenuItem.Text = "Data Pattern";
            this.dataPatternToolStripMenuItem.Click += new System.EventHandler(this.dataPatternToolStripMenuItem_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(145, 6);
            // 
            // userPatternToolStripMenuItem
            // 
            this.userPatternToolStripMenuItem.Name = "userPatternToolStripMenuItem";
            this.userPatternToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.userPatternToolStripMenuItem.Text = "User Pattern";
            this.userPatternToolStripMenuItem.Click += new System.EventHandler(this.userPatternToolStripMenuItem_Click);
            // 
            // NodeUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.startNodeCheck);
            this.Controls.Add(this.doneButton);
            this.Controls.Add(this.NodeId);
            this.Controls.Add(this.nodeIdText);
            this.Controls.Add(this.editNodeButton);
            this.Name = "NodeUserControl";
            this.Size = new System.Drawing.Size(161, 119);
            this.Load += new System.EventHandler(this.FormCreateXMLDB_Load);
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

		}



		#endregion

        private System.Windows.Forms.Button editNodeButton;
        internal System.Windows.Forms.TextBox nodeIdText;
        private System.Windows.Forms.Label NodeId;
        private System.Windows.Forms.Button doneButton;
        internal System.Windows.Forms.CheckBox startNodeCheck;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem configureNodeToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem formPatternToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem dataPatternToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem userPatternToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;

    }
}

