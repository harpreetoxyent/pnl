using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.IO;

namespace EIBFormDesigner.Designer
{
    public class FolderSelect : System.Windows.Forms.Form
    {
        private static string driveLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private DirectoryInfo folder;

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button cancelBtn;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Button selectBtn;
        private System.Windows.Forms.ImageList imageList1;
        private SplitContainer splitContainer1;
        private TreeView treeView1;
        internal CheckedListBox projectTypeList;
        private Label errorLabel;
        private Label projectLabel;
        internal TextBox projectName;
        private Label label2;
        private System.ComponentModel.IContainer components;

        public FolderSelect()
        {
            //
            // Required for Windows Form Designer support
            //
            InitializeComponent();

            //
            // TODO: Add any constructor code after InitializeComponent call
            //

            // initialize the treeView
            fillTree();
        }

        /// <summary> method fillTree
        /// <para>This method is used to initially fill the treeView control with a list
        /// of available drives from which you can search for the desired folder.</para>
        /// </summary>
        private void fillTree()
        {
            DirectoryInfo directory;
            string sCurPath = "";

            // clear out the old values
            treeView1.Nodes.Clear();

            // loop through the drive letters and find the available drives.
            foreach (char c in driveLetters)
            {
                sCurPath = c + ":\\";
                try
                {
                    // get the directory informaiton for this path.
                    directory = new DirectoryInfo(sCurPath);

                    // if the retrieved directory information points to a valid
                    // directory or drive in this case, add it to the root of the 
                    // treeView.
                    if (directory.Exists == true)
                    {
                        TreeNode newNode = new TreeNode(directory.FullName);
                        treeView1.Nodes.Add(newNode);	// add the new node to the root level.
                        getSubDirs(newNode);			// scan for any sub folders on this drive.
                    }
                }
                catch (Exception doh)
                {
                    Console.WriteLine(doh.Message);
                }
            }
        }

        /// <summary> method getSubDirs
        /// <para>this function will scan the specified parent for any subfolders 
        /// if they exist.  To minimize the memory usage, we only scan a single 
        /// folder level down from the existing, and only if it is needed.</para>
        /// <param name="parent">the parent folder in which to search for sub-folders.</param>
        /// </summary>
        private void getSubDirs(TreeNode parent)
        {
            DirectoryInfo directory;
            try
            {
                // if we have not scanned this folder before
                if (parent.Nodes.Count == 0)
                {
                    directory = new DirectoryInfo(parent.FullPath);
                    foreach (DirectoryInfo dir in directory.GetDirectories())
                    {
                        TreeNode newNode = new TreeNode(dir.Name);
                        parent.Nodes.Add(newNode);
                    }
                }

                // now that we have the children of the parent, see if they
                // have any child members that need to be scanned.  Scanning 
                // the first level of sub folders insures that you properly 
                // see the '+' or '-' expanding controls on each node that represents
                // a sub folder with it's own children.
                foreach (TreeNode node in parent.Nodes)
                {
                    // if we have not scanned this node before.
                    if (node.Nodes.Count == 0)
                    {
                        // get the folder information for the specified path.
                        directory = new DirectoryInfo(node.FullPath);

                        // check this folder for any possible sub-folders
                        foreach (DirectoryInfo dir in directory.GetDirectories())
                        {
                            // make a new TreeNode and add it to the treeView.
                            TreeNode newNode = new TreeNode(dir.Name);
                            node.Nodes.Add(newNode);
                        }
                    }
                }
            }
            catch (Exception doh)
            {
                Console.WriteLine(doh.Message);
            }
        }

        /// <summary> method fixPath
        /// <para>For some reason, the treeView will only work with paths constructed like the following example.
        /// "c:\\Program Files\Microsoft\...".  What this method does is strip the leading "\\" next to the drive
        /// letter.</para>
        /// <param name="node">the folder that needs it's path fixed for display.</param>
        /// <returns>The correctly formatted full path to the selected folder.</returns>
        /// </summary>
        private string fixPath(TreeNode node)
        {
            string sRet = "";
            try
            {
                sRet = node.FullPath;
                int index = sRet.IndexOf("\\\\");
                if (index > 1)
                {
                    sRet = node.FullPath.Remove(index, 1);
                }
            }
            catch (Exception doh)
            {
                Console.WriteLine(doh.Message);
            }
            return sRet;
        }
        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
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

        #region Windows Form Designer generated code
        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("Node2");
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("Node1", new System.Windows.Forms.TreeNode[] {
            treeNode1});
            System.Windows.Forms.TreeNode treeNode3 = new System.Windows.Forms.TreeNode("Node0", new System.Windows.Forms.TreeNode[] {
            treeNode2});
            this.panel1 = new System.Windows.Forms.Panel();
            this.projectLabel = new System.Windows.Forms.Label();
            this.projectName = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.panel2 = new System.Windows.Forms.Panel();
            this.errorLabel = new System.Windows.Forms.Label();
            this.cancelBtn = new System.Windows.Forms.Button();
            this.selectBtn = new System.Windows.Forms.Button();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.label2 = new System.Windows.Forms.Label();
            this.projectTypeList = new System.Windows.Forms.CheckedListBox();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.projectLabel);
            this.panel1.Controls.Add(this.projectName);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.textBox1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(6, 6);
            this.panel1.Name = "panel1";
            this.panel1.Padding = new System.Windows.Forms.Padding(10);
            this.panel1.Size = new System.Drawing.Size(835, 83);
            this.panel1.TabIndex = 0;
            // 
            // projectLabel
            // 
            this.projectLabel.Location = new System.Drawing.Point(13, 13);
            this.projectLabel.Name = "projectLabel";
            this.projectLabel.Size = new System.Drawing.Size(104, 26);
            this.projectLabel.TabIndex = 3;
            this.projectLabel.Text = "Project Name";
            // 
            // projectName
            // 
            this.projectName.Location = new System.Drawing.Point(123, 13);
            this.projectName.Name = "projectName";
            this.projectName.Size = new System.Drawing.Size(425, 22);
            this.projectName.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(13, 47);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(104, 26);
            this.label1.TabIndex = 1;
            this.label1.Text = "Full Path";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(123, 47);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(425, 22);
            this.textBox1.TabIndex = 0;
            // 
            // panel2
            // 
            this.panel2.AutoScroll = true;
            this.panel2.Controls.Add(this.errorLabel);
            this.panel2.Controls.Add(this.cancelBtn);
            this.panel2.Controls.Add(this.selectBtn);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(6, 268);
            this.panel2.Name = "panel2";
            this.panel2.Padding = new System.Windows.Forms.Padding(10);
            this.panel2.Size = new System.Drawing.Size(835, 55);
            this.panel2.TabIndex = 1;
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.errorLabel.ForeColor = System.Drawing.Color.Red;
            this.errorLabel.Location = new System.Drawing.Point(14, 14);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(0, 17);
            this.errorLabel.TabIndex = 4;
            // 
            // cancelBtn
            // 
            this.cancelBtn.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.cancelBtn.Dock = System.Windows.Forms.DockStyle.Right;
            this.cancelBtn.Location = new System.Drawing.Point(735, 10);
            this.cancelBtn.Name = "cancelBtn";
            this.cancelBtn.Size = new System.Drawing.Size(90, 35);
            this.cancelBtn.TabIndex = 3;
            this.cancelBtn.Text = "Cancel";
            this.cancelBtn.Click += new System.EventHandler(this.cancelBtn_Click);
            // 
            // selectBtn
            // 
            this.selectBtn.Location = new System.Drawing.Point(624, 10);
            this.selectBtn.Name = "selectBtn";
            this.selectBtn.Size = new System.Drawing.Size(90, 35);
            this.selectBtn.TabIndex = 2;
            this.selectBtn.Text = "Select";
            this.selectBtn.Click += new System.EventHandler(this.selectBtn_Click);
            // 
            // imageList1
            // 
            this.imageList1.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
            this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(6, 89);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.label2);
            this.splitContainer1.Panel1.Controls.Add(this.projectTypeList);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.treeView1);
            this.splitContainer1.Size = new System.Drawing.Size(835, 179);
            this.splitContainer1.SplitterDistance = 278;
            this.splitContainer1.TabIndex = 1;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Dock = System.Windows.Forms.DockStyle.Top;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(0, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(116, 24);
            this.label2.TabIndex = 1;
            this.label2.Text = "Project Type";
            // 
            // projectTypeList
            // 
            this.projectTypeList.BackColor = System.Drawing.SystemColors.MenuBar;
            this.projectTypeList.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.projectTypeList.CheckOnClick = true;
            this.projectTypeList.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.projectTypeList.FormattingEnabled = true;
            this.projectTypeList.Items.AddRange(new object[] {
            "Form Designer",
            "Database Designer",
            "Workflow Designer"});
            this.projectTypeList.Location = new System.Drawing.Point(4, 27);
            this.projectTypeList.Name = "projectTypeList";
            this.projectTypeList.Size = new System.Drawing.Size(271, 132);
            this.projectTypeList.TabIndex = 0;
            this.projectTypeList.ThreeDCheckBoxes = true;
            // 
            // treeView1
            // 
            this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeView1.ImageIndex = 0;
            this.treeView1.ImageList = this.imageList1;
            this.treeView1.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Name = "treeView1";
            treeNode1.Name = "";
            treeNode1.Text = "Node2";
            treeNode2.Name = "";
            treeNode2.Text = "Node1";
            treeNode3.Name = "";
            treeNode3.Text = "Node0";
            this.treeView1.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode3});
            this.treeView1.SelectedImageIndex = 0;
            this.treeView1.Size = new System.Drawing.Size(553, 179);
            this.treeView1.TabIndex = 0;
            this.treeView1.BeforeExpand += new System.Windows.Forms.TreeViewCancelEventHandler(this.treeView1_BeforeExpand);
            this.treeView1.BeforeSelect += new System.Windows.Forms.TreeViewCancelEventHandler(this.treeView1_BeforeSelect);
            // 
            // FolderSelect
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 15);
            this.ClientSize = new System.Drawing.Size(847, 329);
            this.Controls.Add(this.splitContainer1);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(276, 346);
            this.Name = "FolderSelect";
            this.Padding = new System.Windows.Forms.Padding(6);
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Show;
            this.Text = "Select Project";
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);

        }
        #endregion

        /// <summary> method treeView1_BeforeSelect
        /// <para>Before we select a tree node we want to make sure that we scan the soon to be selected
        /// tree node for any sub-folders.  this insures proper tree construction on the fly.</para>
        /// <param name="sender">The object that invoked this event</param>
        /// <param name="e">The TreeViewCancelEventArgs event arguments.</param>
        /// <see cref="System.Windows.Forms.TreeViewCancelEventArgs"/>
        /// <see cref="System.Windows.Forms.TreeView"/>
        /// </summary>
        private void treeView1_BeforeSelect(object sender, System.Windows.Forms.TreeViewCancelEventArgs e)
        {
            getSubDirs(e.Node);					// get the sub-folders for the selected node.
            textBox1.Text = fixPath(e.Node);	// update the user selection text box.
            folder = new DirectoryInfo(e.Node.FullPath);	// get it's Directory info.
        }

        /// <summary> method treeView1_BeforeExpand
        /// <para>Before we expand a tree node we want to make sure that we scan the soon to be expanded
        /// tree node for any sub-folders.  this insures proper tree construction on the fly.</para>
        /// <param name="sender">The object that invoked this event</param>
        /// <param name="e">The TreeViewCancelEventArgs event arguments.</param>
        /// <see cref="System.Windows.Forms.TreeViewCancelEventArgs"/>
        /// <see cref="System.Windows.Forms.TreeView"/>
        /// </summary>
        private void treeView1_BeforeExpand(object sender, System.Windows.Forms.TreeViewCancelEventArgs e)
        {
            getSubDirs(e.Node);					// get the sub-folders for the selected node.
            textBox1.Text = fixPath(e.Node);	// update the user selection text box.
            folder = new DirectoryInfo(e.Node.FullPath);	// get it's Directory info.
        }

        /// <summary> method cancelBtn_Click
        /// <para>This method cancels the folder browsing.</para>
        /// </summary>
        private void cancelBtn_Click(object sender, System.EventArgs e)
        {
            folder = null;
            this.Close();
        }

        public bool isDataValid()
        {
            bool isDataValidValue = true;
            if (projectTypeList.CheckedItems.Count > 1)
            {
                errorLabel.Text = "Selection exceeds more than one kind of Project";
                isDataValidValue = false;
                return isDataValidValue;
            }
            if (projectTypeList.CheckedItems.Count < 1)
            {
                errorLabel.Text = "Please select kind of Project to Continue";
                isDataValidValue = false;
                return isDataValidValue;
            }
            if (projectName.Text.Trim().Length <= 0)
            {
                errorLabel.Text = "Project Name can not be null";
                isDataValidValue = false;
                return isDataValidValue;
            }
            if (textBox1.Text.Trim().Length <= 0)
            {
                errorLabel.Text = "Project Path need to be specified";
                isDataValidValue = false;
                return isDataValidValue;
            }
            return isDataValidValue;

        }
        /// <summary> method selectBtn_Click
        /// <para>This method accepts which ever folder is selected and closes this application 
        /// with a DialogResult.OK result if you invoke this form though Form.ShowDialog().  
        /// In this example this method simply looks up the selected folder, and presents the 
        /// user with a message box displaying the name and path of the selected folder.
        /// </para>
        /// </summary>
        private void selectBtn_Click(object sender, System.EventArgs e)
        {
            if (isDataValid())
            {
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }

        /// <summary> method name
        /// <para>A method to retrieve the short name for the selected folder.</para>
        /// <returns>The folder name for the selected folder.</returns>
        /// </summary>
        public string name
        {
            get { return ((folder != null && folder.Exists)) ? folder.Name : null; }
        }

        /// <summary> method fullPath
        /// <para>Retrieve the full path for the selected folder.</para>
        /// 
        /// <returns>The correctly formatted full path to the selected folder.</returns>
        /// <seealso cref="FolderSelect.fixPath"/>
        /// </summary>
        public string fullPath
        {
            get { return ((folder != null && folder.Exists && treeView1.SelectedNode != null)) ? fixPath(treeView1.SelectedNode) : null; }
        }

        /// <summary> method info
        /// <para>Retrieve the full DirectoryInfo object associated with the selected folder.  Note
        /// that this will not have the corrected full path string.</para>
        /// <returns>The full DirectoryInfo object associated with the selected folder.</returns>
        /// <see cref="System.IO.DirectoryInfo"/>
        /// </summary>
        public DirectoryInfo info
        {
            get { return ((folder != null && folder.Exists)) ? folder : null; }
        }

    }
}
