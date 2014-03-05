using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.IO;
using System.Text.RegularExpressions;
using System.Xml;
using System.Diagnostics;
using System.Reflection;
using EIBFormDesigner.Event;
using WeifenLuo.WinFormsUI.Docking;
using EIBFormDesigner.Controls;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer.Database;
using EIBFormDesigner.ScenarioWizard;
using EIBFormDesigner.Designer.Common;
using EIBFormDesigner.Designer.DatabaseExlorer;
using EIBFormDesigner.UserAdmin;
using EIBXMLServices;
using EIBFormDesigner.Database.Table;
using EIBFormDesigner.Build;
using EIBFormDesigner.Designer.Build;
using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;
using EIBFormDesigner.Menu;
using EIBFormDesigner.Designer.WebServiceExplorer;
using EIBFormDesigner.UndoRedo;
using GenericUndoRedo;


namespace EIBFormDesigner.Designer
{
    public partial class FormDesigner : System.Windows.Forms.Form
    {
        internal BuildWindow buildwindow = null;
        internal BuildTool buildtool;
        internal StartupHIC startupImage = null;
        internal FormDesignerUtilities formDesignerUtility = FormDesignerUtilities.GetInstance();
        internal AccessControlList accessControlList = new AccessControlList();
        internal BaseWindow currentBaseWindow;
        public ToolBoxWindow toolBoxWindow;
        public PropertyWindow propertyWindow;
        internal ProjectExplorer projectExplorer;
        internal DataPatternExlorer dataPatternExplorer;
        internal DisplayNameExplorer displayNameExplorer;
        internal WebServiceExplorer.WebServiceExplorer webServiceExplorer;
        public FormulaEditorWindow formulaEditor;
        public WorkflowEditorWindow workEditor;
        internal static IUIEventManager eventManager = null;
        internal XmlDocument applicationDoc = new XmlDocument();
        private string uniqueId = null;
        private Boolean flag = false;
        public int projectCOunter = 0;
        public static bool isSuccessful = false;
        public static string company = "";
        private XmlNode parentXmlNode = null;
        internal static Dictionary<string, BaseWindow> listFormBaseWindow = new Dictionary<string, BaseWindow>();
        internal static Dictionary<string, BaseWindow> listDataBaseWindow = new Dictionary<string, BaseWindow>();
        internal static Dictionary<string, BaseWindow> listWorkBaseWindow = new Dictionary<string, BaseWindow>();
        internal static List<BaseWindow> listBaseWindow = new List<BaseWindow>();
        internal static Dictionary<string, string> configDictionary = XMLServices.readXMLConfiguration("./", "config.xml");
        DBSettings dbSettings = new DBSettings();
        TableSettings tableSettings = null;
        public static UserAdminUtilities useradminutility;
        #region RedoUndo Implementation

        private ControlPool controlpool = new ControlPool();

        internal ControlPool Controlpool
        {
            get { return controlpool; }
            set { controlpool = value; }
        }
        private UndoRedoHistory<ControlPool> history;

        internal UndoRedoHistory<ControlPool> History
        {
            get { return history; }
            set { history = value; }
        }

        #endregion

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
        internal String UniqueID
        {
            get
            {
                return uniqueId;
            }
            set
            {
                uniqueId = value;
            }
        }
        public BaseWindow CurrentBaseWindow
        {
            get
            {
                return currentBaseWindow;
            }
            set
            {
                currentBaseWindow = value;
            }
        }
        public IUIEventManager getEventManagerInstance()
        {
            if (eventManager == null)
            {
                eventManager = new UIEventManager(this);
            }
            return eventManager;
        }
        

        public FormDesigner()
        {
            this.UniqueID = "application" + projectCOunter;
            propertyWindow = new PropertyWindow();
            projectExplorer = new ProjectExplorer(this);
            dataPatternExplorer = new DataPatternExlorer(this);
            displayNameExplorer = new DisplayNameExplorer(this);
            webServiceExplorer = new EIBFormDesigner.Designer.WebServiceExplorer.WebServiceExplorer(this);
            eventManager = getEventManagerInstance();
            DragDropHandler.Initialize(eventManager, this);
            this.Disposed += new EventHandler(FormDesigner_Disposed);
            InitializeComponent();
            ToolBoxWindow.form = this;
            toolBoxWindow = new ToolBoxWindow();
            startupImage = new StartupHIC();
            formulaEditor = new FormulaEditorWindow(this);
            workEditor = new WorkflowEditorWindow(this);
            buildtool = new EIBFormDesigner.Build.BuildTool(this);
            buildwindow = new BuildWindow(this);
            setFromPatternsForACL();
            //UserAdmin.UserAdminConstants.CompanyId = company;
            //UserAdmin.UserAdminConstants.CompanyName = company;
            history = new UndoRedoHistory<ControlPool>(controlpool);
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
        }


        #region This is the window from designer of general settings

        void FormDesigner_Disposed(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(EIBXMLUtilities.projectName))
            {
                DialogResult result = MessageBox.Show("Exit and save the application", "Warning -Close", MessageBoxButtons.YesNoCancel);
                if (result == DialogResult.Yes)
                {
                    if (!(flag == true))
                    {
                        this.saveToolStripMenuItem_Click(null, null);
                    }
                    flag = true;

                }
                else
                {
                    if (result == DialogResult.No)
                    {
                        flag = true;
                    }
                    else
                    {
                        flag = false;
                    }
                }
            }
            else
            {
                flag = true;
            }

        }

        void currentBaseWindow_Click(object sender, EventArgs e)
        {
            currentBaseWindow = (BaseWindow)sender;
            formulaEditor.CurrentBaseWindow = currentBaseWindow;
        }

        private void EIBFormDesigner_Load(object sender, EventArgs e)
        {

        }
        internal void baseWindowDefaultToolClick(object sender, EventArgs e)
        {
            currentBaseWindow.Show(dockPanel);
        }
        public void toolBoxToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolBoxWindow.Show(dockPanel);
        }

        public void propertyEditorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            propertyWindow.Show(dockPanel);
        }
        public void projectExplorerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (EIBXMLUtilities.projectFilePath != "")
            {
                projectExplorer.initExplorer();
                projectExplorer.Show(dockPanel);
            }
            else
            {
                MessageBox.Show("No Projects to Show");
            }
        }
        public void projectExplorerShowToolStrip()
        {
            projectExplorer.Show(dockPanel);
        }
        public void datapatternExplorerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            dataPatternExplorer.initExplorer();
            dataPatternExplorer.Show(dockPanel);
        }
        public void formulaEditorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            formulaEditor.Show(dockPanel);
        }

        private void toolBar_ButtonClick(object sender, ToolBarButtonClickEventArgs e)
        {

        }
        public BaseWindow initBaseWindow(string typeOfWindow, string windowName, bool isWindowSaved)
        {
            try
            {
                if (typeOfWindow == FormDesignerConstants.FormPattern)
                {
                    //Check for new Window
                    if (windowName == null)
                    {
                        windowName = "";
                        currentBaseWindow = new BaseWindow(typeOfWindow, windowName, isWindowSaved);

                        windowName = currentBaseWindow.baseFrame.ControlName;
                        listFormBaseWindow.Add(currentBaseWindow.baseFrame.ControlName, currentBaseWindow);
                        listBaseWindow.Add(currentBaseWindow);
                        DragDropHandler.RegisterControl(currentBaseWindow.baseFrame, true, true);

                    }
                    //Check if we already create this object so no need to create new one
                    else if (!listFormBaseWindow.ContainsKey(windowName))
                    {
                        //currentBaseWindow.baseFrame.DataBindings.Add("ControlName",FormDesignerUtilities.dataSetList["datapattern0"]);
                        currentBaseWindow = new BaseWindow(typeOfWindow, windowName, isWindowSaved);
                        listFormBaseWindow.Add(currentBaseWindow.baseFrame.ControlName, currentBaseWindow);
                        listBaseWindow.Add(currentBaseWindow);
                        DragDropHandler.RegisterControl(currentBaseWindow.baseFrame, true, true);
                    }
                    else
                    {
                        currentBaseWindow = listFormBaseWindow[windowName];
                    }

                }
                if (typeOfWindow == FormDesignerConstants.DataPattern)
                {
                    if (windowName == null)
                    {
                        windowName = "";
                    }

                    if (!listDataBaseWindow.ContainsKey(windowName))
                    {
                        currentBaseWindow = new BaseWindow(typeOfWindow, windowName, isWindowSaved);
                        listDataBaseWindow.Add(currentBaseWindow.baseFrame.ControlName, currentBaseWindow);
                        listBaseWindow.Add(currentBaseWindow);
                        DragDropHandler.RegisterControl(currentBaseWindow.baseFrame, true, true);
                    }
                    else
                    {
                        currentBaseWindow = listDataBaseWindow[windowName];
                    }

                }
                if (typeOfWindow == FormDesignerConstants.WorkflowPattern)
                {

                    if (windowName == null)
                    {
                        windowName = "";
                    }
                    if (!listWorkBaseWindow.ContainsKey(windowName))
                    {
                        currentBaseWindow = new BaseWindow(typeOfWindow, windowName, isWindowSaved);
                        listWorkBaseWindow.Add(currentBaseWindow.baseFrame.ControlName, currentBaseWindow);
                        listBaseWindow.Add(currentBaseWindow);
                        DragDropHandler.RegisterControl(currentBaseWindow.baseFrame, true, true);
                    }
                    else
                    {
                        currentBaseWindow = listWorkBaseWindow[windowName];
                    }

                }

                currentBaseWindow.TypeOfWindow = typeOfWindow;
                formulaEditor.CurrentBaseWindow = currentBaseWindow;
                currentBaseWindow.Click += new EventHandler(currentBaseWindow_Click);
                currentBaseWindow.KeyPreview = true;
                if (!currentBaseWindow.isKeyDown)
                {
                    currentBaseWindow.KeyDown += new KeyEventHandler(eventManager.HandleCtrlDown);
                    currentBaseWindow.KeyUp += new KeyEventHandler(eventManager.HandleCtrlUp);
                    currentBaseWindow.KeyPress += new KeyPressEventHandler(eventManager.HandleKeyPress);
                    currentBaseWindow.isKeyDown = true;
                }
                /*Register it only for one time*/
                //DragDropHandler.RegisterControl(currentBaseWindow.baseFrame, true, true);
                //baseWindowDefaultToolClick(null, null);
                currentBaseWindow.MouseClick += new MouseEventHandler(eventManager.handleControlClick);
                if (!currentBaseWindow.baseFrame.isMouseClick)
                {
                    currentBaseWindow.baseFrame.MouseClick += new MouseEventHandler(eventManager.handleControlClick);
                    currentBaseWindow.baseFrame.isMouseClick = true;
                }
                //currentBaseWindow.SizeChanged += new EventHandler(currentBaseWindow_SizeChanged);
                currentBaseWindow.baseFrame.Scroll += new ScrollEventHandler(baseFrame_Scroll);
                currentBaseWindow.baseFrame.MouseWheel += new MouseEventHandler(baseFrame_MouseWheel);
                UIEventManager.form.currentBaseWindow = currentBaseWindow;
                FormulaEditorWindow.form.currentBaseWindow = currentBaseWindow;
                //Added by HKU to set the first form pattern as default screen
                if (currentBaseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                {
                    if (currentBaseWindow != null)
                    {
                        if (listFormBaseWindow.Count == 1 && FormDesignerUtilities.visibleTrueWindow.Count < 1)
                        {
                            currentBaseWindow.baseFrame.DefaultScreen = true;
                        }
                    }
                }
                //Adding by HKU done
                return currentBaseWindow;
            }
            catch
            {
                return null;
            }
        }

        void baseFrame_MouseWheel(object sender, MouseEventArgs e)
        {
            currentBaseWindow.baseFrame.SuspendLayout();
            if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
            {
                foreach (Control control in currentBaseWindow.baseFrame.Controls)
                {
                    if (control is EIBTableConnector)
                    {
                        EIBTableConnector tableCOnnector = (EIBTableConnector)control;
                        tableCOnnector.Redraw();
                        break;
                    }
                }
            }
            else
            {
                if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.WorkflowPattern))
                {
                    foreach (Control control in currentBaseWindow.baseFrame.Controls)
                    {
                        if (control is Workflow.Controls.EIBNodeConnector)
                        {
                            Workflow.Controls.EIBNodeConnector eibNodeConnector = (Workflow.Controls.EIBNodeConnector)control;
                            eibNodeConnector.Redraw();
                            break;
                        }
                    }
                }
            }
            currentBaseWindow.baseFrame.ResumeLayout();
            //throw new Exception("The method or operation is not implemented.");
        }

        void baseFrame_Scroll(object sender, ScrollEventArgs e)
        {
            currentBaseWindow.baseFrame.SuspendLayout();
            if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
            {
                foreach (Control control in currentBaseWindow.baseFrame.Controls)
                {
                    if (control is EIBTableConnector)
                    {
                        EIBTableConnector tableCOnnector = (EIBTableConnector)control;
                        tableCOnnector.Redraw();
                        break;
                    }
                }
            }
            else
            {
                if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.WorkflowPattern))
                {
                    foreach (Control control in currentBaseWindow.baseFrame.Controls)
                    {
                        if (control is Workflow.Controls.EIBNodeConnector)
                        {
                            Workflow.Controls.EIBNodeConnector eibNodeConnector = (Workflow.Controls.EIBNodeConnector)control;
                            eibNodeConnector.Redraw();
                            break;
                        }
                    }
                }
            }
            currentBaseWindow.baseFrame.ResumeLayout();
        }

        void currentBaseWindow_SizeChanged(object sender, EventArgs e)
        {
            currentBaseWindow.SuspendLayout();
            //currentBaseWindow.baseFrame.BorderStyle = BorderStyle.FixedSingle;
            if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
            {
                foreach (Control control in currentBaseWindow.baseFrame.Controls)
                {
                    if (control is EIBTableConnector)
                    {
                        EIBTableConnector tableCOnnector = (EIBTableConnector)control;
                        tableCOnnector.Redraw();
                        break;
                    }
                }
            }
            currentBaseWindow.ResumeLayout();
            //currentBaseWindow.Invalidate();
        }

        private void projectToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                SceanrioBuilder scb = new SceanrioBuilder();
                if (scb.ShowDialog() == DialogResult.OK)
                {

                    DialogResult result = DialogResult.OK;
                    if (!EIBXMLUtilities.projectFilePath.Trim().Equals(""))
                    {
                        result = MessageBox.Show("Do you want to exit from Current Project", "Exit Project", MessageBoxButtons.OKCancel);
                    }
                    if (result == DialogResult.OK)
                    {
                        foreach (BaseWindow baseWindow in FormDesigner.listBaseWindow)
                        {
                            baseWindow.Close();
                        }
                        FormDesigner.listFormBaseWindow.Clear();
                        FormDesigner.listBaseWindow.Clear();
                        FormDesigner.listDataBaseWindow.Clear();
                        FormDesigner.listWorkBaseWindow.Clear();
                        FormDesignerUtilities.visibleTrueWindow.Clear();
                        EIBXMLUtilities.folderName = scb.projectLocation.Text;
                        EIBXMLUtilities.projectName = scb.projectNameTxtBox.Text;
                        EIBXMLUtilities.projectFilePath = EIBXMLUtilities.folderName + "/" + EIBXMLUtilities.projectName + FormDesignerConstants.ScenarioProjectFormat;
                        //////////EIBFormDesigner.UserAdmin.AccessControlList.adminDataSet.Clear();
                        NameFolderStructure();
                        applicationDoc = new XmlDocument();
                        formDesignerUtility.instantiateProjectSettings(EIBXMLUtilities.folderName, applicationDoc);
                        toolBoxToolStripMenuItem_Click(null, null);
                        datapatternExplorerToolStripMenuItem_Click(null, null);
                        propertyEditorToolStripMenuItem_Click(null, null);
                        projectExplorerToolStripMenuItem_Click(null, null);
                        // edited on 28 july by ajit
                        // users.xml not found.
                        UserAdminUtilities.UserAdminUtility.Rights.Clear();
                        //AccessControlList.createRightXml();
                        useradminutility = UserAdminUtilities.UserAdminUtility;
                        UserAdminUtilities.UserAdminUtility.Users.Clear();
                        UserAdminUtilities.UserAdminUtility.Roles.Clear();
                        UserAdminUtilities.UserAdminUtility.UserPatterns.Clear();
                        //UserAdminUtilities.Refresh();
                        this.toolBoxWindow.tabControl.SelectedIndex = 0;
                        this.Text = EIBXMLUtilities.projectName + " - Application Designer";
                        this.displayNameExplorer.formPatternMapList.Clear();
                        //propertyWindow.propertyGrid.PropertyValueChanged += new PropertyValueChangedEventHandler(propertyWindow.propertyGrid.FilteredPropertyGrid_PropertyValueChanged);
                        if (scb.checkBox1.Checked)
                        {
                            LiferayWindow lfr = new LiferayWindow();
                            lfr.ShowDialog();
                        }
                    }
                }
            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message);
            }
        }

        private static void NameFolderStructure()
        {
            EIBXMLUtilities.formFolderName = EIBXMLUtilities.folderName + FormDesignerConstants.FormFolder;
            EIBXMLUtilities.dataFolderName = EIBXMLUtilities.folderName + FormDesignerConstants.DataFolder;
            EIBXMLUtilities.workflowFolderName = EIBXMLUtilities.folderName + FormDesignerConstants.WorkflowFolder;
            EIBXMLUtilities.usersFolderName = EIBXMLUtilities.folderName + FormDesignerConstants.UsersFolder;
        }

        private void publishToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Call Rendering Component
            try
            {
                RenderWindow renderWindow = new RenderWindow();
                renderWindow.ShowDialog(this);
                /*string projectNamewithoutESP = Directory.GetParent(EIBXMLUtilities.projectFilePath).FullName;
                projectNamewithoutESP = Regex.Replace(projectNamewithoutESP, @"\\", "/");
                string catalineHome = Environment.GetEnvironmentVariable("CATALINA_HOME");
                string extFolder = catalineHome + "\\common\\lib\\ext";
                string webappsFolder = catalineHome + "\\webapps";
                extFolder = Regex.Replace(extFolder, @"\\", "/");
                webappsFolder = Regex.Replace(webappsFolder, @"\\", "/");
                string urlServer = "http://localhost:8080/c?applicationname=" + EIBXMLUtilities.projectName + "&sourcedir=" + projectNamewithoutESP + @"&serverDirectory=" + webappsFolder+ "&serverLibDirectory=" + extFolder;
                System.Diagnostics.Process.Start(urlServer);
                */
                /* 
                 TextWriter tw = new StreamWriter("c:\\Projects\\build.properties");

                 tw.WriteLine("src.dir = " + projectNamewithoutESP);
                 tw.WriteLine("app.name = " + EIBXMLUtilities.projectName);
                 tw.WriteLine("server.dir = " + @"C:/apache-tomcat-5.5.20/apache-tomcat-5.5.20/webapps");
                 tw.WriteLine("server.address = " + @"http://localhost:8080/");
                 tw.Close();

                 Assembly thisAssembly = Assembly.GetExecutingAssembly();
                 Stream batStream = thisAssembly.GetManifestResourceStream("EIBFormDesigner.Rendering.bat");
                 string FileName = Path.GetTempPath();
                 FileName = FileName + "\\Rendering.bat";
                 FileStream File_Stream = new FileStream(FileName, FileMode.OpenOrCreate, FileAccess.Write);
                 StreamWriter sw = new StreamWriter(File_Stream);
                 string batFileContent = batStream.ToString();
                 sw.Write(@" set WORK_HOME=c:\Projects
                             set LIFERAY_HOME=%WORK_HOME%\liferay\portal\ext 
                             cd %WORK_HOME% 
                             CALL ANT -buildfile %WORK_HOME%\publish.xml 
                             cls ");
                 sw.Close();
                 File_Stream.Close();
                 Process.Start(FileName);
                 */

            }
            catch (IOException ex)
            {
                Debug.Assert(false, ex.ToString());
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        #endregion


        internal void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (EIBXMLUtilities.folderName != null && !EIBXMLUtilities.folderName.Trim().Equals(""))
                {
                    BaseWindow prevBaseWindow = this.currentBaseWindow;
                    if (prevBaseWindow != null)
                    {
                        eventManager.handleControlClick(prevBaseWindow.baseFrame, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
                    }
                    formDesignerUtility.save(this, applicationDoc);
                    projectExplorer.initExplorer();
                    dataPatternExplorer.initExplorer();
                    PickBox.isctrlpressed = false;
                    this.currentBaseWindow.CurrentControl.Clear();
                    if (prevBaseWindow != null && !prevBaseWindow.IsDisposed)
                    {
                        if (prevBaseWindow.Visible)
                        {
                            this.currentBaseWindow = prevBaseWindow;
                            this.currentBaseWindow.Show(dockPanel);
                            this.currentBaseWindow.Select();
                        }
                    }
                    setFromPatternsForACL();
                }
            }
            catch
            {
            }
        }

        private void setFromPatternsForACL()
        {
            AccessControlList.FormPatterns.Clear();
            string[] formpatterns = new string[FormDesigner.listFormBaseWindow.Count];
            FormDesigner.listFormBaseWindow.Keys.CopyTo(formpatterns, 0);
            AccessControlList.FormPatterns.AddRange(formpatterns);
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void createDatabaseToolStripMenuItem_Click(object sender, EventArgs e)
        {
            dbSettings.CurrentBaseWindow = currentBaseWindow;
            if (currentBaseWindow != null)
            {
                if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
                {
                    dbSettings.errorLabel.Text = "";
                    dbSettings.ShowDialog(this);
                }
                else
                {
                    MessageBox.Show(FormDesignerConstants.WrongWindowMessage);
                }
                return;
            }
            else
            {
                MessageBox.Show("Please Select A Data Pattern Window");
            }
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {

                OpenScenario scb = new OpenScenario();
                if (scb.ShowDialog() == DialogResult.OK)
                {
                    DialogResult result = DialogResult.OK;
                    if (!EIBXMLUtilities.projectFilePath.Trim().Equals(""))
                    {
                        result = MessageBox.Show("Do you want to exit from Current Project", "Exit Project", MessageBoxButtons.OKCancel);

                    }
                    if (result == DialogResult.OK)
                    {
                        foreach (BaseWindow baseWindow in FormDesigner.listBaseWindow)
                        {
                            baseWindow.Close();
                        }
                        FormDesigner.listFormBaseWindow.Clear();
                        FormDesigner.listBaseWindow.Clear();
                        FormDesigner.listDataBaseWindow.Clear();
                        FormDesigner.listWorkBaseWindow.Clear();
                        FormDesignerUtilities.visibleTrueWindow.Clear();
                        EIBXMLUtilities.projectFilePath = scb.projectLocation.Text;
                        //Read Manifest File and Update Everything
                        DirectoryInfo directory = new DirectoryInfo(EIBXMLUtilities.projectFilePath);
                        string directoryPath = "";
                        while (directory.Parent != null)
                        {
                            directoryPath = "\\" + directory.Parent + directoryPath;
                            directory = directory.Parent;
                        }
                        EIBXMLUtilities.folderName = directory.Root.Name + directoryPath;
                        NameFolderStructure();
                        formDesignerUtility.openProjectSettings(applicationDoc);
                        displayNameExplorer.formPatternMapList.Clear();
                        toolBoxToolStripMenuItem_Click(null, null);
                        datapatternExplorerToolStripMenuItem_Click(null, null);
                        propertyEditorToolStripMenuItem_Click(null, null);
                        projectExplorerToolStripMenuItem_Click(null, null);
                        this.toolBoxWindow.tabControl.SelectedIndex = 0;
                        this.Text = EIBXMLUtilities.projectName + " - Application Designer";
                        this.buildToolStripMenuItem1.Enabled = true;
                        this.MakePublish(false);
                        //AccessControlList.createRightXml();
                        //AccessControlList.loadRightXml();
                        useradminutility = UserAdminUtilities.UserAdminUtility;
                        //UserAdminUtilities.Refresh();
                        UserAdminUtilities.UserAdminUtility.Users.Clear();
                        UserAdminUtilities.UserAdminUtility.Roles.Clear();
                        UserAdminUtilities.UserAdminUtility.UserPatterns.Clear();
                        useradminutility.Desearilize();
                        setFromPatternsForACL();
                        webServiceExplorer.DeSerializeXMLMappings();
                        this.currentBaseWindow = null;
                        if (scb.liferayChckBx.Checked)
                        {
                            LiferayWindow lfr = new LiferayWindow();
                            lfr.ShowDialog();
                        }
                    }
                }

            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message);
            }
        }

        private void accessControlListToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(EIBXMLUtilities.projectName))
            {
                accessControlList.ShowDialog();
            }
            else
            {
                MessageBox.Show("Please create or open a scenario.");
            }
        }

        private void deployToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Call Rendering Component
            try
            {
                /* string projectNamewithoutESP = Directory.GetParent(EIBXMLUtilities.projectFilePath).FullName;
                 projectNamewithoutESP = Regex.Replace(projectNamewithoutESP, @"\\", "/");
                 string catalineHome = Environment.GetEnvironmentVariable("CATALINA_HOME");
                 string extFolder = catalineHome + "\\common\\lib\\ext";
                 string webappsFolder = catalineHome + "\\webapps";
                 extFolder = Regex.Replace(extFolder, @"\\", "/");
                 webappsFolder = Regex.Replace(webappsFolder, @"\\", "/");
                 string urlServer = "http://localhost:8080/c?applicationname=" + EIBXMLUtilities.projectName + "&sourcedir=" + projectNamewithoutESP + @"&serverDirectory=" + webappsFolder + "&serverLibDirectory=" + extFolder + "&deploy=true&useradmin=true";
                 System.Diagnostics.Process.Start(urlServer);
                 */
                /* 
                 TextWriter tw = new StreamWriter("c:\\Projects\\build.properties");

                 tw.WriteLine("src.dir = " + projectNamewithoutESP);
                 tw.WriteLine("app.name = " + EIBXMLUtilities.projectName);
                 tw.WriteLine("server.dir = " + @"C:/apache-tomcat-5.5.20/apache-tomcat-5.5.20/webapps");
                 tw.WriteLine("server.address = " + @"http://localhost:8080/");
                 tw.Close();

                 Assembly thisAssembly = Assembly.GetExecutingAssembly();
                 Stream batStream = thisAssembly.GetManifestResourceStream("EIBFormDesigner.Rendering.bat");
                 string FileName = Path.GetTempPath();
                 FileName = FileName + "\\Rendering.bat";
                 FileStream File_Stream = new FileStream(FileName, FileMode.OpenOrCreate, FileAccess.Write);
                 StreamWriter sw = new StreamWriter(File_Stream);
                 string batFileContent = batStream.ToString();
                 sw.Write(@" set WORK_HOME=c:\Projects
                             set LIFERAY_HOME=%WORK_HOME%\liferay\portal\ext 
                             cd %WORK_HOME% 
                             CALL ANT -buildfile %WORK_HOME%\publish.xml 
                             cls ");
                 sw.Close();
                 File_Stream.Close();
                 Process.Start(FileName);
                 */

            }
            catch (IOException ex)
            {
                Debug.Assert(false, ex.ToString());
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void tablePropertiesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            tableSettings = new TableSettings();
            tableSettings.CurrentBaseWindow = currentBaseWindow;
            if (currentBaseWindow != null)
            {
                if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
                {
                    if (currentBaseWindow.CurrentControl != null && currentBaseWindow.CurrentControl.Count > 0)
                    {
                        if (currentBaseWindow.CurrentControl[0] is EIBTable)
                        {
                            tableSettings.ShowDialog(this);
                        }
                        else
                        {
                            MessageBox.Show("Please select a table to see the properties.");
                        }
                    }
                    else
                    {
                        MessageBox.Show("Please select a table to see the properties.");
                    }
                }
                else
                {
                    MessageBox.Show(FormDesignerConstants.WrongWindowMessage);
                }
                return;
            }
            else
            {
                MessageBox.Show("Please Select A Data Pattern Window");
            }
        }

        private void processEditorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            workEditor.Show(dockPanel);
        }

        private void aboutFormDesignerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start(@"C:\nolis\Appdesigner\" + "Application_Designer.chm");
        }

        private void FormDesigner_Load(object sender, EventArgs e)
        {
            startupImage.Show(dockPanel);
        }

        private void centerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (currentBaseWindow != null)
            {
                eventManager.AlignAll(currentBaseWindow.CurrentControl, Align.center);
                if (!Alignment.CheckForTreeNode(currentBaseWindow.CurrentControl))
                    hideAllPickbox();
            }
        }

        private void leftToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (currentBaseWindow != null)
            {
                eventManager.AlignAll(currentBaseWindow.CurrentControl, Align.left);
                if (!Alignment.CheckForTreeNode(currentBaseWindow.CurrentControl))
                    hideAllPickbox();
            }
            
        }

        private void rightsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (currentBaseWindow != null)
            {
                eventManager.AlignAll(currentBaseWindow.CurrentControl, Align.right);
                if (!Alignment.CheckForTreeNode(currentBaseWindow.CurrentControl))
                    hideAllPickbox();
            }
        }

        public void hideAllPickbox()
        {
            foreach (IEIBControl ctrl in currentBaseWindow.CurrentControl)
            {
                if (UIEventManager.pickBox.ContainsKey(ctrl))
                {
                    UIEventManager.pickBox[ctrl].Remove();
                }
            }
        }

        private void buildToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            if (EIBXMLUtilities.folderName != null && !EIBXMLUtilities.folderName.Trim().Equals(""))
            {
                BuildTool.BuildForm(EIBXMLUtilities.projectFilePath);
                buildwindow.Show(dockPanel);
                buildwindow.DockState = DockState.DockBottom;
            }
        }

        public void MakePublish(bool enabled)
        {
            publishToolStripMenuItem.Enabled = enabled;
        }

        private void menuEditorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (CurrentBaseWindow != null && currentBaseWindow.CurrentControl.Count == 1)
            {
                if (this.CurrentBaseWindow.CurrentControl[0].GetType() == typeof(EIBMenuBar))
                {
                    MenuEditor menuedit = new MenuEditor((EIBMenuBar)this.CurrentBaseWindow.CurrentControl[0]);
                    menuedit.ShowDialog();
                }
                else
                {
                    MessageBox.Show("Select menubar to open menu editor.");
                }
            }
            else
            {
                MessageBox.Show("Select single Control.");
            }
        }

        private void topsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (currentBaseWindow != null)
            {
                eventManager.AlignAll(currentBaseWindow.CurrentControl, Align.top);
                if (!Alignment.CheckForTreeNode(currentBaseWindow.CurrentControl))
                    hideAllPickbox();
            }
        }

        private void middlesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (currentBaseWindow != null)
            {
                eventManager.AlignAll(currentBaseWindow.CurrentControl, Align.middle);
                if (!Alignment.CheckForTreeNode(currentBaseWindow.CurrentControl))
                    hideAllPickbox();
            }
        }

        private void bottomsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            eventManager.AlignAll(currentBaseWindow.CurrentControl, Align.bottom);
            if (!Alignment.CheckForTreeNode(currentBaseWindow.CurrentControl))
                hideAllPickbox();
        }

        private void dockPanel_TabIndexChanged(object sender, EventArgs e)
        {

        }

        private void dockPanel_ActiveDocumentChanged(object sender, EventArgs e)
        {
            if (dockPanel.ActiveDocument != null && dockPanel.ActiveDocument.GetType() == typeof(BaseWindow))
            {
                projectExplorer.changeProjectItemSelection(((ToolWindow)dockPanel.ActiveDocument).Text, ((BaseWindow)dockPanel.ActiveDocument).TypeOfWindow);
                propertyWindow.propertyGrid.SelectedObject = ((BaseWindow)dockPanel.ActiveDocument).baseFrame;
                dbSettings.CurrentBaseWindow = (BaseWindow)dockPanel.ActiveDocument;
                currentBaseWindow = (BaseWindow)dockPanel.ActiveDocument;
                if (((BaseWindow)dockPanel.ActiveDocument).TypeOfWindow == FormDesignerConstants.FormPattern)
                {
                    displayNameExplorer.initExplorer();
                    displayNameExplorer.comboBox1.SelectedText = "";
                    UIEventManager.ContextMenu.MenuItems[0].Enabled = true;
                    UIEventManager.ContextMenu.MenuItems[2].Enabled = true;
                    UIEventManager.ContextMenu.MenuItems[3].Enabled = true;
                }
                else if (((BaseWindow)dockPanel.ActiveDocument).TypeOfWindow == FormDesignerConstants.DataPattern)
                {
                    UIEventManager.ContextMenu.MenuItems[0].Enabled = false;
                    UIEventManager.ContextMenu.MenuItems[2].Enabled = false;
                    UIEventManager.ContextMenu.MenuItems[3].Enabled = false;
                }
                else if (((BaseWindow)dockPanel.ActiveDocument).TypeOfWindow == FormDesignerConstants.WorkflowPattern)
                {
                    UIEventManager.ContextMenu.MenuItems[0].Enabled = false;
                    UIEventManager.ContextMenu.MenuItems[2].Enabled = false;
                    UIEventManager.ContextMenu.MenuItems[3].Enabled = false;
                }

            }
        }

        private void displayNameExplorerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            displayNameExplorer.initExplorer();
            displayNameExplorer.Show(dockPanel);
        }

        private void webServiceExplorerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(EIBXMLUtilities.projectName))
            {
                MessageBox.Show("Please open an Application.");
                return;
            }
            webServiceExplorer.initExplorer();
            webServiceExplorer.Show(dockPanel);
        }

        private void undoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (history.CanUndo)
                {
                    clearControls();
                    history.Undo();
                    RepaintAll();
                }
            }
            catch
            {

            }
        }

        private void clearControls()
        {
            foreach (IEIBControl control in Controlpool)
            {
                if(((Control)control).Parent != null)
                ((Control)control).Parent.Controls.Remove((Control)control);
            }
        }

        private void redoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                if (history.CanRedo)
                {
                    clearControls();
                    history.Redo();
                    RepaintAll();
                }
            }
            catch
            {

            }
        }
        public void RepaintAll()
        {
            
            //currentBaseWindow.baseFrame.Controls.Clear();
            Graphics g = Graphics.FromHwnd(currentBaseWindow.baseFrame.Handle);
            g.Clear(currentBaseWindow.baseFrame.BackColor);
            foreach (IEIBControl control in Controlpool)
            {
                if (((Control)control).Controls.Count > 0)
                {
                    if (!(control is UserControl) && !(control is EIBTabControl) && !(control is EIBMenuBar))
                    {
                        ((Control)control).Controls.Clear();
                    }
                }
                parentExpose parents = ControlPool.parentControls[control];
                if (control is IEIBControl && parents.parent != null)
                {
                    if (control is EIBTabControl)
                    {
                        foreach(EIBTabPage tabPage in ((EIBTabControl)control).TabPages)
                        {
                            if (!EIBControlCollection.TabPagelist.ContainsKey(tabPage.Name))
                            {
                                EIBControlCollection.TabPagelist.Add(tabPage.Name, tabPage.Name);
                            }
                        }
                        if (!EIBControlCollection.TabControllist.ContainsKey(((EIBTabControl)control).Name))
                        {
                            EIBControlCollection.TabControllist.Add(((EIBTabControl)control).Name, ((EIBTabControl)control).Name);
                        }
                    }
                    if (control is EIBTabPage)
                    {
                        if (!EIBControlCollection.TabPagelist.ContainsKey(((Control)control).Name))
                        {
                            EIBControlCollection.TabPagelist.Add(((Control)control).Name, ((Control)control).Name);
                        }
                    }
                    if (control is EIBMenuBar)
                    {
                        EIBMenuBar.RefreshInList(control);
                    }
                    parents.parent.Controls.Add((Control)control);
                }
                else
                {
                    currentBaseWindow.baseFrame.Controls.Add((Control)control);
                }
            }
            eventManager.handleControlClick(currentBaseWindow.baseFrame, new MouseEventArgs(MouseButtons.Left, 1, 0, 0, 0));
        }





    }
}