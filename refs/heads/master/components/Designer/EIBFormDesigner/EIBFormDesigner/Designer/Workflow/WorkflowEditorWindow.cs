using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.IO;
using System.Resources;
using System.Collections; 
using EIBFormDesigner.Parse;
using EIBFormDesigner.Designer.Form;
using EIBFormDesigner.Workflow.Common;

namespace EIBFormDesigner.Designer
{
    public partial class WorkflowEditorWindow : ToolWindow
    {
        private static EIBFormDesigner.Designer.FormDesigner form = null;
        public static TextWriter errorWriter = null;

        private IBaseWindow currentBaseWindow;
        public IBaseWindow CurrentBaseWindow
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
        private TreeNode selectedNode = null;
        public TreeNode SelectedNode
        {
            get
            {
                return selectedNode;
            }
            set
            {
                selectedNode = value;
            }
        }
        public WorkflowEditorWindow(EIBFormDesigner.Designer.FormDesigner designer)
        {
            form = designer;
            InitializeComponent();
            this.formulaText.Enabled = false;
            initContextMenu();
            formulaText.KeyPress += new KeyPressEventHandler(this.formulaText_Click);
            formulaText.ContextMenu = contextMenu;
        }
        public void initContextMenu()
        {
            contextMenu.Popup += new System.EventHandler(this.contextMenuItem_Click);
            ResourceSet resourceSet = FormulaEditorResource.ResourceManager.GetResourceSet(Thread.CurrentThread.CurrentUICulture, true, true);
            IEnumerator  methodEnum = resourceSet.GetEnumerator();
            int methodCounter = 0;
            int numberOfMethodsSupported = 13;
            MenuItem[] methodAray = new System.Windows.Forms.MenuItem[numberOfMethodsSupported];
            while (methodEnum.MoveNext())
            {
                MenuItem menuItem = new MenuItem();
                menuItem.Click += new System.EventHandler(this.contextMenuItem_Click);
                menuItem.Index = methodCounter;
                DictionaryEntry  entry= (DictionaryEntry)methodEnum.Current;
                menuItem.Text = (string)entry.Value;
                methodAray[methodCounter] = menuItem;
                methodCounter++;
            }
            contextMenu.MenuItems.AddRange(methodAray);

        }
        public void contextMenuItem_Click(object sender, System.EventArgs e)
        {

            if (sender is MenuItem)
            {
                MenuItem menuItem = (MenuItem)sender;
                this.formulaText.Text = menuItem.Text;
            }
        }

        public void formulaText_Click(object sender, KeyPressEventArgs e)
        {
            //key code for @
             int atCharKey = 0x00000020;
            if (Control.ModifierKeys == Keys.Control)
            {
                if ((int)e.KeyChar == atCharKey)
                {
                    contextMenu.Show(formulaText, new Point(10, 10));
                }
            }
        }
        private void designTree_AfterSelect(object sender, TreeViewEventArgs e)
        {
            currentBaseWindow = form.currentBaseWindow;
            TreeView treeControl = (TreeView)sender;
            selectedNode = treeControl.SelectedNode;
            if(currentBaseWindow!= null)
            {
                if (currentBaseWindow.TypeOfWindow == FormDesignerConstants.WorkflowPattern)
                {
                    if (selectedNode != null)
                    {
                        if (currentBaseWindow.CurrentControl.Count > 0)
                        {
                            if (currentBaseWindow.CurrentControl[0] is INodeControl)
                            {
                                INodeControl currentNode = (INodeControl)currentBaseWindow.CurrentControl[0];

                                this.formulaText.Enabled = true;
                                if (currentBaseWindow.CurrentControl != null)
                                {
                                    if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnNode))
                                    {
                                        formulaText.Text = currentNode.NodeValue;
                                    }
                                    if (selectedNode.Name.Trim().Equals(FormDesignerConstants.EnterNode))
                                    {
                                        formulaText.Text = currentNode.OnNodeEnterValue;
                                    }
                                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.ExitNode))
                                    {
                                        formulaText.Text = currentNode.OnNodeExitClick;
                                    }
                                }
                            }
                            else
                            {
                                formulaText.Text = "";
                            }
                        }
                    }
                }
                else
                {
                    MessageBox.Show("Please select a Workflow Pattern before entering process rule");
                }

            }
                
            //MessageBox.Show(sender.ToString());
            //Display the basewindow current control's setting
        }

        private void saveFormula_Click(object sender, EventArgs e)
        {
            try
            {
                this.parserError.Text = "";
                if (errorWriter != null)
                {
                    errorWriter.Dispose();
                }
                errorWriter = new StringWriter();
                if (!formulaText.Text.Trim().Equals(""))
                {
                    Scanner scanner = new Scanner(formulaText.Text);
                    Parser parser = new Parser(scanner);
                    parser.errors.errorStream = errorWriter;
                    parser.Parse();
                    TextReader stringReader = new StringReader(errorWriter.ToString());
                    this.parserError.Text = stringReader.ReadToEnd();
                    Console.Write(parser.errors.count + " errors detected");
                    if (parser.errors.count > 0)
                    {
                        return;
                    }
                    //Parse the text to Validate the java api
                }
                if (currentBaseWindow != null && selectedNode != null && currentBaseWindow.CurrentControl != null)
                {
                    INodeControl eibControl = (INodeControl)currentBaseWindow.CurrentControl[0];
                    if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnNode))
                    {
                        eibControl.NodeValue = formulaText.Text;
                    }
                    if (selectedNode.Name.Trim().Equals(FormDesignerConstants.EnterNode))
                    {
                        eibControl.OnNodeEnterValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.ExitNode))
                    {
                        eibControl.OnNodeExitClick = formulaText.Text;
                    }
                }
            }
            catch
            {
            }


        }

        private void FormulaEditorWindow_Load(object sender, EventArgs e)
        {

        }
    }
}