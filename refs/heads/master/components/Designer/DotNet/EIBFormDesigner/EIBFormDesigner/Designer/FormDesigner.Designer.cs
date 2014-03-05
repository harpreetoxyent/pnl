using EIBFormDesigner.XML;
namespace EIBFormDesigner.Designer
{
    partial class FormDesigner
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
            /*generatedFileName = EIBXMLServices.folderName + "\\"+ ElementName + fileCounter + extension;
            if (xmlDoc != null)
            {
                xmlDoc.Save(generatedFileName);
                foreach (BaseWindow baseWindow in listBaseWindow)
                {
                    generatedFileName = EIBXMLServices.folderName + "\\" + baseWindow.UniqueID + extension;
                    baseWindow.XMLDocument.Save(generatedFileName);
                }
            }*/
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            FormDesigner_Disposed(this, null);
            if (flag == true)
            {
                base.Dispose(disposing);
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormDesigner));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.fIleToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.projectToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.scenarioToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.accessControlListToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.editToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.undoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.redoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.alignToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.leftToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.centerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.rightsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
            this.topsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.middlesToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.bottomsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.buildtoolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.buildToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.toolsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolBoxToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.formulaEditorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.propertyEditorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.projectExplorerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.databaseToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.createDatabaseToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tablePropertiesToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.datapatternExplorerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.workflowToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.processEditorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuEditorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.displayNameExplorerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.webServiceExplorerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.renderToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.publishToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutFormDesignerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.Line = new System.Windows.Forms.Button();
            this.imageList = new System.Windows.Forms.ImageList(this.components);
            this.dockPanel = new WeifenLuo.WinFormsUI.Docking.DockPanel();
            this.hlpPrvMain = new System.Windows.Forms.HelpProvider();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.hlpPrvMain.SetHelpKeyword(this.menuStrip1, "Getting Started");
            this.hlpPrvMain.SetHelpNavigator(this.menuStrip1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fIleToolStripMenuItem,
            this.editToolStripMenuItem,
            this.toolStripMenuItem1,
            this.buildtoolStripMenuItem,
            this.toolsToolStripMenuItem,
            this.renderToolStripMenuItem,
            this.helpToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Padding = new System.Windows.Forms.Padding(4, 2, 0, 2);
            this.hlpPrvMain.SetShowHelp(this.menuStrip1, true);
            this.menuStrip1.Size = new System.Drawing.Size(771, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // fIleToolStripMenuItem
            // 
            this.fIleToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newToolStripMenuItem,
            this.openToolStripMenuItem,
            this.saveToolStripMenuItem,
            this.scenarioToolStripMenuItem,
            this.exitToolStripMenuItem});
            this.fIleToolStripMenuItem.Name = "fIleToolStripMenuItem";
            this.fIleToolStripMenuItem.ShortcutKeyDisplayString = "";
            this.fIleToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Alt | System.Windows.Forms.Keys.F)));
            this.fIleToolStripMenuItem.Size = new System.Drawing.Size(35, 20);
            this.fIleToolStripMenuItem.Text = "&File";
            // 
            // newToolStripMenuItem
            // 
            this.newToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.projectToolStripMenuItem});
            this.newToolStripMenuItem.Name = "newToolStripMenuItem";
            this.newToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
            this.newToolStripMenuItem.Text = "&New";
            // 
            // projectToolStripMenuItem
            // 
            this.projectToolStripMenuItem.Name = "projectToolStripMenuItem";
            this.projectToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.N)));
            this.projectToolStripMenuItem.Size = new System.Drawing.Size(165, 22);
            this.projectToolStripMenuItem.Text = "Scenario";
            this.projectToolStripMenuItem.Click += new System.EventHandler(this.projectToolStripMenuItem_Click);
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.O)));
            this.openToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
            this.openToolStripMenuItem.Text = "&Open";
            this.openToolStripMenuItem.Click += new System.EventHandler(this.openToolStripMenuItem_Click);
            // 
            // saveToolStripMenuItem
            // 
            this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
            this.saveToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.S)));
            this.saveToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
            this.saveToolStripMenuItem.Text = "&Save";
            this.saveToolStripMenuItem.Click += new System.EventHandler(this.saveToolStripMenuItem_Click);
            // 
            // scenarioToolStripMenuItem
            // 
            this.scenarioToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.accessControlListToolStripMenuItem});
            this.scenarioToolStripMenuItem.Name = "scenarioToolStripMenuItem";
            this.scenarioToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Alt)
                        | System.Windows.Forms.Keys.A)));
            this.scenarioToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
            this.scenarioToolStripMenuItem.Text = "S&cenario";
            // 
            // accessControlListToolStripMenuItem
            // 
            this.accessControlListToolStripMenuItem.Name = "accessControlListToolStripMenuItem";
            this.accessControlListToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Shift)
                        | System.Windows.Forms.Keys.A)));
            this.accessControlListToolStripMenuItem.Size = new System.Drawing.Size(244, 22);
            this.accessControlListToolStripMenuItem.Text = "Access Control List";
            this.accessControlListToolStripMenuItem.Click += new System.EventHandler(this.accessControlListToolStripMenuItem_Click);
            // 
            // exitToolStripMenuItem
            // 
            this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
            this.exitToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Alt | System.Windows.Forms.Keys.F4)));
            this.exitToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
            this.exitToolStripMenuItem.Text = "&Exit";
            this.exitToolStripMenuItem.Click += new System.EventHandler(this.exitToolStripMenuItem_Click);
            // 
            // editToolStripMenuItem
            // 
            this.editToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.undoToolStripMenuItem,
            this.redoToolStripMenuItem});
            this.editToolStripMenuItem.Name = "editToolStripMenuItem";
            this.editToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.editToolStripMenuItem.Text = "&Edit";
            // 
            // undoToolStripMenuItem
            // 
            this.undoToolStripMenuItem.Name = "undoToolStripMenuItem";
            this.undoToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Z)));
            this.undoToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.undoToolStripMenuItem.Text = "Undo";
            this.undoToolStripMenuItem.Click += new System.EventHandler(this.undoToolStripMenuItem_Click);
            // 
            // redoToolStripMenuItem
            // 
            this.redoToolStripMenuItem.Name = "redoToolStripMenuItem";
            this.redoToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Y)));
            this.redoToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.redoToolStripMenuItem.Text = "Redo";
            this.redoToolStripMenuItem.Click += new System.EventHandler(this.redoToolStripMenuItem_Click);
            // 
            // toolStripMenuItem1
            // 
            this.toolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.alignToolStripMenuItem});
            this.toolStripMenuItem1.Name = "toolStripMenuItem1";
            this.toolStripMenuItem1.Size = new System.Drawing.Size(53, 20);
            this.toolStripMenuItem1.Text = "Fo&rmat";
            // 
            // alignToolStripMenuItem
            // 
            this.alignToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.leftToolStripMenuItem,
            this.centerToolStripMenuItem,
            this.rightsToolStripMenuItem,
            this.toolStripMenuItem2,
            this.topsToolStripMenuItem,
            this.middlesToolStripMenuItem,
            this.bottomsToolStripMenuItem});
            this.alignToolStripMenuItem.Name = "alignToolStripMenuItem";
            this.alignToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.alignToolStripMenuItem.Text = "Align";
            // 
            // leftToolStripMenuItem
            // 
            this.leftToolStripMenuItem.Name = "leftToolStripMenuItem";
            this.leftToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.leftToolStripMenuItem.Text = "Lefts";
            this.leftToolStripMenuItem.Click += new System.EventHandler(this.leftToolStripMenuItem_Click);
            // 
            // centerToolStripMenuItem
            // 
            this.centerToolStripMenuItem.Name = "centerToolStripMenuItem";
            this.centerToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.centerToolStripMenuItem.Text = "Centers";
            this.centerToolStripMenuItem.Click += new System.EventHandler(this.centerToolStripMenuItem_Click);
            // 
            // rightsToolStripMenuItem
            // 
            this.rightsToolStripMenuItem.Name = "rightsToolStripMenuItem";
            this.rightsToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.rightsToolStripMenuItem.Text = "Rights";
            this.rightsToolStripMenuItem.Click += new System.EventHandler(this.rightsToolStripMenuItem_Click);
            // 
            // toolStripMenuItem2
            // 
            this.toolStripMenuItem2.Name = "toolStripMenuItem2";
            this.toolStripMenuItem2.Size = new System.Drawing.Size(149, 6);
            // 
            // topsToolStripMenuItem
            // 
            this.topsToolStripMenuItem.Name = "topsToolStripMenuItem";
            this.topsToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.topsToolStripMenuItem.Text = "Tops";
            this.topsToolStripMenuItem.Click += new System.EventHandler(this.topsToolStripMenuItem_Click);
            // 
            // middlesToolStripMenuItem
            // 
            this.middlesToolStripMenuItem.Name = "middlesToolStripMenuItem";
            this.middlesToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.middlesToolStripMenuItem.Text = "Middles";
            this.middlesToolStripMenuItem.Click += new System.EventHandler(this.middlesToolStripMenuItem_Click);
            // 
            // bottomsToolStripMenuItem
            // 
            this.bottomsToolStripMenuItem.Name = "bottomsToolStripMenuItem";
            this.bottomsToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.bottomsToolStripMenuItem.Text = "Bottoms";
            this.bottomsToolStripMenuItem.Click += new System.EventHandler(this.bottomsToolStripMenuItem_Click);
            // 
            // buildtoolStripMenuItem
            // 
            this.buildtoolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.buildToolStripMenuItem1});
            this.buildtoolStripMenuItem.Name = "buildtoolStripMenuItem";
            this.buildtoolStripMenuItem.Size = new System.Drawing.Size(41, 20);
            this.buildtoolStripMenuItem.Text = "&Build";
            // 
            // buildToolStripMenuItem1
            // 
            this.buildToolStripMenuItem1.Name = "buildToolStripMenuItem1";
            this.buildToolStripMenuItem1.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Shift)
                        | System.Windows.Forms.Keys.B)));
            this.buildToolStripMenuItem1.Size = new System.Drawing.Size(175, 22);
            this.buildToolStripMenuItem1.Text = "Build";
            this.buildToolStripMenuItem1.Click += new System.EventHandler(this.buildToolStripMenuItem1_Click);
            // 
            // toolsToolStripMenuItem
            // 
            this.toolsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolBoxToolStripMenuItem,
            this.formulaEditorToolStripMenuItem,
            this.propertyEditorToolStripMenuItem,
            this.projectExplorerToolStripMenuItem,
            this.databaseToolStripMenuItem,
            this.datapatternExplorerToolStripMenuItem,
            this.workflowToolStripMenuItem,
            this.menuEditorToolStripMenuItem,
            this.displayNameExplorerToolStripMenuItem,
            this.webServiceExplorerToolStripMenuItem});
            this.toolsToolStripMenuItem.Name = "toolsToolStripMenuItem";
            this.toolsToolStripMenuItem.Size = new System.Drawing.Size(44, 20);
            this.toolsToolStripMenuItem.Text = "&Tools";
            // 
            // toolBoxToolStripMenuItem
            // 
            this.toolBoxToolStripMenuItem.Name = "toolBoxToolStripMenuItem";
            this.toolBoxToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.T)));
            this.toolBoxToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.toolBoxToolStripMenuItem.Text = "T&oolBox";
            this.toolBoxToolStripMenuItem.Click += new System.EventHandler(this.toolBoxToolStripMenuItem_Click);
            // 
            // formulaEditorToolStripMenuItem
            // 
            this.formulaEditorToolStripMenuItem.Name = "formulaEditorToolStripMenuItem";
            this.formulaEditorToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.F)));
            this.formulaEditorToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.formulaEditorToolStripMenuItem.Text = "For&mulaEditor";
            this.formulaEditorToolStripMenuItem.Click += new System.EventHandler(this.formulaEditorToolStripMenuItem_Click);
            // 
            // propertyEditorToolStripMenuItem
            // 
            this.propertyEditorToolStripMenuItem.Name = "propertyEditorToolStripMenuItem";
            this.propertyEditorToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.P)));
            this.propertyEditorToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.propertyEditorToolStripMenuItem.Text = "Property&Editor";
            this.propertyEditorToolStripMenuItem.Click += new System.EventHandler(this.propertyEditorToolStripMenuItem_Click);
            // 
            // projectExplorerToolStripMenuItem
            // 
            this.projectExplorerToolStripMenuItem.Name = "projectExplorerToolStripMenuItem";
            this.projectExplorerToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.E)));
            this.projectExplorerToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.projectExplorerToolStripMenuItem.Text = "Project Exp&lorer";
            this.projectExplorerToolStripMenuItem.Click += new System.EventHandler(this.projectExplorerToolStripMenuItem_Click);
            // 
            // databaseToolStripMenuItem
            // 
            this.databaseToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.createDatabaseToolStripMenuItem,
            this.tablePropertiesToolStripMenuItem});
            this.databaseToolStripMenuItem.Name = "databaseToolStripMenuItem";
            this.databaseToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.databaseToolStripMenuItem.Text = "&Database";
            // 
            // createDatabaseToolStripMenuItem
            // 
            this.createDatabaseToolStripMenuItem.Name = "createDatabaseToolStripMenuItem";
            this.createDatabaseToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.D)));
            this.createDatabaseToolStripMenuItem.Size = new System.Drawing.Size(222, 22);
            this.createDatabaseToolStripMenuItem.Text = "Create &Database";
            this.createDatabaseToolStripMenuItem.Click += new System.EventHandler(this.createDatabaseToolStripMenuItem_Click);
            // 
            // tablePropertiesToolStripMenuItem
            // 
            this.tablePropertiesToolStripMenuItem.Name = "tablePropertiesToolStripMenuItem";
            this.tablePropertiesToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Alt)
                        | System.Windows.Forms.Keys.P)));
            this.tablePropertiesToolStripMenuItem.Size = new System.Drawing.Size(222, 22);
            this.tablePropertiesToolStripMenuItem.Text = "Table Properties";
            this.tablePropertiesToolStripMenuItem.Click += new System.EventHandler(this.tablePropertiesToolStripMenuItem_Click);
            // 
            // datapatternExplorerToolStripMenuItem
            // 
            this.datapatternExplorerToolStripMenuItem.Name = "datapatternExplorerToolStripMenuItem";
            this.datapatternExplorerToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Alt)
                        | System.Windows.Forms.Keys.E)));
            this.datapatternExplorerToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.datapatternExplorerToolStripMenuItem.Text = "Datapattern Explor&er";
            this.datapatternExplorerToolStripMenuItem.Click += new System.EventHandler(this.datapatternExplorerToolStripMenuItem_Click);
            // 
            // workflowToolStripMenuItem
            // 
            this.workflowToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.processEditorToolStripMenuItem});
            this.workflowToolStripMenuItem.Name = "workflowToolStripMenuItem";
            this.workflowToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.workflowToolStripMenuItem.Text = "Workflow";
            // 
            // processEditorToolStripMenuItem
            // 
            this.processEditorToolStripMenuItem.Name = "processEditorToolStripMenuItem";
            this.processEditorToolStripMenuItem.Size = new System.Drawing.Size(150, 22);
            this.processEditorToolStripMenuItem.Text = "ProcessEditor";
            this.processEditorToolStripMenuItem.Click += new System.EventHandler(this.processEditorToolStripMenuItem_Click);
            // 
            // menuEditorToolStripMenuItem
            // 
            this.menuEditorToolStripMenuItem.Name = "menuEditorToolStripMenuItem";
            this.menuEditorToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Alt)
                        | System.Windows.Forms.Keys.M)));
            this.menuEditorToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.menuEditorToolStripMenuItem.Text = "Me&nu Editor";
            this.menuEditorToolStripMenuItem.Click += new System.EventHandler(this.menuEditorToolStripMenuItem_Click);
            // 
            // displayNameExplorerToolStripMenuItem
            // 
            this.displayNameExplorerToolStripMenuItem.Name = "displayNameExplorerToolStripMenuItem";
            this.displayNameExplorerToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.displayNameExplorerToolStripMenuItem.Text = "DisplayNameExplorer";
            this.displayNameExplorerToolStripMenuItem.Click += new System.EventHandler(this.displayNameExplorerToolStripMenuItem_Click);
            // 
            // webServiceExplorerToolStripMenuItem
            // 
            this.webServiceExplorerToolStripMenuItem.Name = "webServiceExplorerToolStripMenuItem";
            this.webServiceExplorerToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Alt)
                        | System.Windows.Forms.Keys.W)));
            this.webServiceExplorerToolStripMenuItem.Size = new System.Drawing.Size(246, 22);
            this.webServiceExplorerToolStripMenuItem.Text = "WebServiceExplorer";
            this.webServiceExplorerToolStripMenuItem.Click += new System.EventHandler(this.webServiceExplorerToolStripMenuItem_Click);
            // 
            // renderToolStripMenuItem
            // 
            this.renderToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.publishToolStripMenuItem});
            this.renderToolStripMenuItem.Name = "renderToolStripMenuItem";
            this.renderToolStripMenuItem.Size = new System.Drawing.Size(54, 20);
            this.renderToolStripMenuItem.Text = "&Render";
            // 
            // publishToolStripMenuItem
            // 
            this.publishToolStripMenuItem.Name = "publishToolStripMenuItem";
            this.publishToolStripMenuItem.Size = new System.Drawing.Size(118, 22);
            this.publishToolStripMenuItem.Text = "Publis&h";
            this.publishToolStripMenuItem.Click += new System.EventHandler(this.publishToolStripMenuItem_Click);
            // 
            // helpToolStripMenuItem
            // 
            this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.aboutFormDesignerToolStripMenuItem});
            this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
            this.helpToolStripMenuItem.Size = new System.Drawing.Size(40, 20);
            this.helpToolStripMenuItem.Text = "&Help";
            // 
            // aboutFormDesignerToolStripMenuItem
            // 
            this.aboutFormDesignerToolStripMenuItem.Name = "aboutFormDesignerToolStripMenuItem";
            this.aboutFormDesignerToolStripMenuItem.Size = new System.Drawing.Size(214, 22);
            this.aboutFormDesignerToolStripMenuItem.Text = "About Application Design&er";
            this.aboutFormDesignerToolStripMenuItem.Click += new System.EventHandler(this.aboutFormDesignerToolStripMenuItem_Click);
            // 
            // Line
            // 
            this.Line.Location = new System.Drawing.Point(3, 168);
            this.Line.Name = "Line";
            this.Line.Size = new System.Drawing.Size(75, 37);
            this.Line.TabIndex = 6;
            this.Line.Text = "Line";
            this.Line.UseVisualStyleBackColor = true;
            // 
            // imageList
            // 
            this.imageList.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
            this.imageList.ImageSize = new System.Drawing.Size(16, 16);
            this.imageList.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // dockPanel
            // 
            this.dockPanel.ActiveAutoHideContent = null;
            this.dockPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dockPanel.DockBottomPortion = 250;
            this.dockPanel.DockLeftPortion = 250;
            this.dockPanel.DockRightPortion = 200;
            this.dockPanel.DockTopPortion = 150;
            this.dockPanel.Font = new System.Drawing.Font("Tahoma", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World, ((byte)(0)));
            this.hlpPrvMain.SetHelpKeyword(this.dockPanel, "Getting Started");
            this.hlpPrvMain.SetHelpNavigator(this.dockPanel, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.dockPanel.Location = new System.Drawing.Point(0, 24);
            this.dockPanel.Margin = new System.Windows.Forms.Padding(2);
            this.dockPanel.Name = "dockPanel";
            this.dockPanel.RightToLeftLayout = true;
            this.hlpPrvMain.SetShowHelp(this.dockPanel, true);
            this.dockPanel.Size = new System.Drawing.Size(771, 573);
            this.dockPanel.TabIndex = 0;
            this.dockPanel.TabIndexChanged += new System.EventHandler(this.dockPanel_TabIndexChanged);
            this.dockPanel.ActiveDocumentChanged += new System.EventHandler(this.dockPanel_ActiveDocumentChanged);
            // 
            // hlpPrvMain
            // 
            this.hlpPrvMain.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // FormDesigner
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.ClientSize = new System.Drawing.Size(771, 597);
            this.Controls.Add(this.dockPanel);
            this.Controls.Add(this.menuStrip1);
            this.HelpButton = true;
            this.hlpPrvMain.SetHelpKeyword(this, "Getting Started");
            this.hlpPrvMain.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.IsMdiContainer = true;
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "FormDesigner";
            this.hlpPrvMain.SetShowHelp(this, true);
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "NOLIS Application Designer";
            this.Load += new System.EventHandler(this.FormDesigner_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem fIleToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem editToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem helpToolStripMenuItem;

        private System.Windows.Forms.Button Line;
        private System.Windows.Forms.ToolStripMenuItem toolsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem toolBoxToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem formulaEditorToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem propertyEditorToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem renderToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem publishToolStripMenuItem;

        private System.Windows.Forms.ImageList imageList;
        private System.Windows.Forms.ToolStripMenuItem newToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem projectToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutFormDesignerToolStripMenuItem;
        private WeifenLuo.WinFormsUI.Docking.DockPanel dockPanel;
        private System.Windows.Forms.ToolStripMenuItem databaseToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem createDatabaseToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem projectExplorerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem datapatternExplorerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem scenarioToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem accessControlListToolStripMenuItem;
        private System.Windows.Forms.HelpProvider hlpPrvMain;
        private System.Windows.Forms.ToolStripMenuItem tablePropertiesToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem workflowToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem processEditorToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem toolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem alignToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem leftToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem centerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem rightsToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem2;
        private System.Windows.Forms.ToolStripMenuItem topsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem middlesToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem bottomsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem buildtoolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem buildToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem menuEditorToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem displayNameExplorerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem webServiceExplorerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem undoToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem redoToolStripMenuItem;
    }
}
