using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.IO;

namespace EIBFormDesigner.ScenarioWizard
{
    public partial class SceanrioBuilder : Form
    {
        internal System.Windows.Forms.Button btnOpenFolder;
		private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog;
		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		private System.ComponentModel.Container components = null;

        public SceanrioBuilder()
		{
			//
			// Erforderlich für die Windows Form-Designerunterstützung
			//
			InitializeComponent();
            this.controlListView1.BackColor = System.Drawing.Color.White;
            this.controlListView1.SelectedIndexChanged += new SelectedIndexChangedEventHandler(this.controlListView1_SelectedIndexChanged);
            TileListItem item = new TileListItem();

            this.controlListView1.Add(item);
			//
			// TODO: Fügen Sie den Konstruktorcode nach dem Aufruf von InitializeComponent hinzu
			//
		}
        private void controlListView1_SelectedIndexChanged(object sender, TileListItem item)
        {
            if (this.controlListView1.SelectedItem != null)
            {
                //do something
            }
        }
        private void controlListView1_DoubleClick(object sender, System.EventArgs e)
        {
            if (sender is TileListItem)
            {
                TileListItem item = (TileListItem)sender;
            }
        }

		/// <summary>
		/// Die verwendeten Ressourcen bereinigen.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Vom Windows Form-Designer generierter Code
		/// <summary>
		/// Erforderliche Methode für die Designerunterstützung. 
		/// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
		/// </summary>
		private void InitializeComponent()
		{
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("General");
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("Search");
            System.Windows.Forms.TreeNode treeNode3 = new System.Windows.Forms.TreeNode("Performance");
            this.btnOpenFolder = new System.Windows.Forms.Button();
            this.folderBrowserDialog = new System.Windows.Forms.FolderBrowserDialog();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.panel1 = new System.Windows.Forms.Panel();
            this.scenarioTypesView = new System.Windows.Forms.TreeView();
            this.label1 = new System.Windows.Forms.Label();
            this.checkBox1 = new System.Windows.Forms.CheckBox();
            this.errorLabel = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.projectNameTxtBox = new System.Windows.Forms.TextBox();
            this.nameLabel = new System.Windows.Forms.Label();
            this.locationLabel = new System.Windows.Forms.Label();
            this.projectLocation = new System.Windows.Forms.TextBox();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.controlListView1 = new EIBFormDesigner.ScenarioWizard.ControlListView();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnOpenFolder
            // 
            this.helpProvider1.SetHelpKeyword(this.btnOpenFolder, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.btnOpenFolder, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.btnOpenFolder.Location = new System.Drawing.Point(380, 46);
            this.btnOpenFolder.Name = "btnOpenFolder";
            this.helpProvider1.SetShowHelp(this.btnOpenFolder, true);
            this.btnOpenFolder.Size = new System.Drawing.Size(70, 24);
            this.btnOpenFolder.TabIndex = 0;
            this.btnOpenFolder.Text = "Browse";
            this.btnOpenFolder.Click += new System.EventHandler(this.btnOpenFolder_Click);
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.panel1);
            this.splitContainer1.Panel1.Controls.Add(this.label1);
            this.splitContainer1.Panel1.Controls.Add(this.controlListView1);
            this.helpProvider1.SetHelpKeyword(this.splitContainer1.Panel1, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.splitContainer1.Panel1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.helpProvider1.SetShowHelp(this.splitContainer1.Panel1, true);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.checkBox1);
            this.splitContainer1.Panel2.Controls.Add(this.errorLabel);
            this.splitContainer1.Panel2.Controls.Add(this.okButton);
            this.splitContainer1.Panel2.Controls.Add(this.cancelButton);
            this.splitContainer1.Panel2.Controls.Add(this.projectNameTxtBox);
            this.splitContainer1.Panel2.Controls.Add(this.nameLabel);
            this.splitContainer1.Panel2.Controls.Add(this.locationLabel);
            this.splitContainer1.Panel2.Controls.Add(this.projectLocation);
            this.splitContainer1.Panel2.Controls.Add(this.btnOpenFolder);
            this.helpProvider1.SetHelpKeyword(this.splitContainer1.Panel2, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.splitContainer1.Panel2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.helpProvider1.SetShowHelp(this.splitContainer1.Panel2, true);
            this.splitContainer1.Size = new System.Drawing.Size(664, 310);
            this.splitContainer1.SplitterDistance = 176;
            this.splitContainer1.TabIndex = 3;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.scenarioTypesView);
            this.panel1.Location = new System.Drawing.Point(3, 55);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(157, 100);
            this.panel1.TabIndex = 5;
            // 
            // scenarioTypesView
            // 
            this.scenarioTypesView.Dock = System.Windows.Forms.DockStyle.Top;
            this.scenarioTypesView.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.scenarioTypesView, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.scenarioTypesView, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.scenarioTypesView.Location = new System.Drawing.Point(0, 0);
            this.scenarioTypesView.Name = "scenarioTypesView";
            treeNode1.Name = "General";
            treeNode1.Text = "General";
            treeNode2.Name = "Search";
            treeNode2.Text = "Search";
            treeNode3.Name = "Performance";
            treeNode3.Text = "Performance";
            this.scenarioTypesView.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode1,
            treeNode2,
            treeNode3});
            this.helpProvider1.SetShowHelp(this.scenarioTypesView, true);
            this.scenarioTypesView.Size = new System.Drawing.Size(157, 100);
            this.scenarioTypesView.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label1, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.label1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label1.Location = new System.Drawing.Point(161, 21);
            this.label1.Name = "label1";
            this.helpProvider1.SetShowHelp(this.label1, true);
            this.label1.Size = new System.Drawing.Size(74, 16);
            this.label1.TabIndex = 3;
            this.label1.Text = "Templates";
            // 
            // checkBox1
            // 
            this.checkBox1.AutoSize = true;
            this.checkBox1.Location = new System.Drawing.Point(17, 101);
            this.checkBox1.Name = "checkBox1";
            this.checkBox1.Size = new System.Drawing.Size(111, 17);
            this.checkBox1.TabIndex = 9;
            this.checkBox1.Text = "Use Liferay Image";
            this.checkBox1.UseVisualStyleBackColor = true;
            this.checkBox1.CheckedChanged += new System.EventHandler(this.checkBox1_CheckedChanged);
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.errorLabel.ForeColor = System.Drawing.Color.Red;
            this.errorLabel.Location = new System.Drawing.Point(11, 104);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(0, 17);
            this.errorLabel.TabIndex = 8;
            // 
            // okButton
            // 
            this.okButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.okButton, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.okButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.okButton.Location = new System.Drawing.Point(82, 73);
            this.okButton.Name = "okButton";
            this.helpProvider1.SetShowHelp(this.okButton, true);
            this.okButton.Size = new System.Drawing.Size(70, 24);
            this.okButton.TabIndex = 7;
            this.okButton.Text = "&OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.cancelButton, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.cancelButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelButton.Location = new System.Drawing.Point(158, 73);
            this.cancelButton.Name = "cancelButton";
            this.helpProvider1.SetShowHelp(this.cancelButton, true);
            this.cancelButton.Size = new System.Drawing.Size(70, 24);
            this.cancelButton.TabIndex = 6;
            this.cancelButton.Text = "&Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // projectNameTxtBox
            // 
            this.helpProvider1.SetHelpKeyword(this.projectNameTxtBox, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.projectNameTxtBox, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.projectNameTxtBox.Location = new System.Drawing.Point(82, 16);
            this.projectNameTxtBox.Name = "projectNameTxtBox";
            this.helpProvider1.SetShowHelp(this.projectNameTxtBox, true);
            this.projectNameTxtBox.Size = new System.Drawing.Size(285, 20);
            this.projectNameTxtBox.TabIndex = 3;
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nameLabel.Location = new System.Drawing.Point(11, 16);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(49, 17);
            this.nameLabel.TabIndex = 3;
            this.nameLabel.Text = "Name:";
            // 
            // locationLabel
            // 
            this.locationLabel.AutoSize = true;
            this.locationLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.locationLabel.Location = new System.Drawing.Point(11, 49);
            this.locationLabel.Name = "locationLabel";
            this.locationLabel.Size = new System.Drawing.Size(66, 17);
            this.locationLabel.TabIndex = 2;
            this.locationLabel.Text = "Location:";
            // 
            // projectLocation
            // 
            this.helpProvider1.SetHelpKeyword(this.projectLocation, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.projectLocation, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.projectLocation.Location = new System.Drawing.Point(82, 49);
            this.projectLocation.Name = "projectLocation";
            this.helpProvider1.SetShowHelp(this.projectLocation, true);
            this.projectLocation.Size = new System.Drawing.Size(285, 20);
            this.projectLocation.TabIndex = 4;
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // controlListView1
            // 
            this.controlListView1.AutoAdjustHeight = false;
            this.controlListView1.AutoResize = false;
            this.controlListView1.AutoScroll = true;
            this.controlListView1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(230)))), ((int)(((byte)(230)))));
            this.controlListView1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.helpProvider1.SetHelpKeyword(this.controlListView1, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this.controlListView1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.controlListView1.HorizontalSpacing = 5;
            this.controlListView1.ItemHeight = 72;
            this.controlListView1.ItemHorverColor = System.Drawing.Color.Snow;
            this.controlListView1.ItemNormalColor = System.Drawing.Color.White;
            this.controlListView1.ItemSelectionColor = System.Drawing.Color.WhiteSmoke;
            this.controlListView1.ItemWidth = 250;
            this.controlListView1.Location = new System.Drawing.Point(165, 55);
            this.controlListView1.Name = "controlListView1";
            this.controlListView1.NormalColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(230)))), ((int)(((byte)(230)))));
            this.controlListView1.Selected = false;
            this.controlListView1.SelectedColor = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(216)))), ((int)(((byte)(216)))));
            this.helpProvider1.SetShowHelp(this.controlListView1, true);
            this.controlListView1.Size = new System.Drawing.Size(386, 89);
            this.controlListView1.TabIndex = 2;
            this.controlListView1.VerticleSpacing = 5;
            // 
            // SceanrioBuilder
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(664, 310);
            this.Controls.Add(this.splitContainer1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.helpProvider1.SetHelpKeyword(this, "Creating a new Scenario");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "SceanrioBuilder";
            this.helpProvider1.SetShowHelp(this, true);
            this.Text = "Scenario Builder";
            this.Load += new System.EventHandler(this.SceanrioBuilder_Load);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.Panel2.PerformLayout();
            this.splitContainer1.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// Der Haupteinstiegspunkt für die Anwendung.
		/// </summary>
		[STAThread]
		static void Main() 
		{
            Application.Run(new SceanrioBuilder());
		}

		private void listBox1_SelectedIndexChanged(object sender, System.EventArgs e)
		{
		
		}

		private void btnOpenFolder_Click(object sender, System.EventArgs e)
		{			
			this.folderBrowserDialog.RootFolder=System.Environment.SpecialFolder.MyComputer;
			this.folderBrowserDialog.ShowNewFolderButton=true;
			DialogResult result=this.folderBrowserDialog.ShowDialog();
			if (result==DialogResult.OK)
			{

				// retrieve the name of the selected folder
				string foldername=this.folderBrowserDialog.SelectedPath;
				
				// print the folder name on a label
				this.projectLocation.Text=foldername;
                /*
				// iterate over all files in the selected folder and add them to 
				// the listbox.
				foreach (string filename in Directory.GetFiles(foldername))
					this.listBox1.Items.Add(filename);
                 */
			}

		}


        private void SceanrioBuilder_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {

        }

        private void okButton_Click(object sender, EventArgs e)
        {
            if (isDataValid())
            {
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }
        public bool isDataValid()
        {
            bool isDataValidValue = true;
            if (this.projectNameTxtBox.Text.Trim().Length <= 0)
            {
                errorLabel.Text = "Project Name can not be null";
                isDataValidValue = false;
                return isDataValidValue;
            }
            if (this.projectLocation.Text.Trim().Length <= 0)
            {
                errorLabel.Text = "Project Path need to be specified";
                isDataValidValue = false;
                return isDataValidValue;
            }
            try
            {
                string directoryName = System.IO.Path.GetDirectoryName(this.projectLocation.Text);
                bool exists = System.IO.Directory.Exists(directoryName);
                if (directoryName.Trim().Equals("") || (!exists))
                {
                    throw new Exception("Invalid path value");
                }
            }
            catch(Exception)
            {
                errorLabel.Text = "Invalid path value";
                isDataValidValue = false;
            }
            return isDataValidValue;

        }
        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            
        }




    }
}