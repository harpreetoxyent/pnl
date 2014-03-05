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

namespace EIBFormDesigner.Designer
{
    public partial class FormulaEditorWindow : ToolWindow
    {
        internal static EIBFormDesigner.Designer.FormDesigner form = null;
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
        public FormulaEditorWindow(EIBFormDesigner.Designer.FormDesigner designer)
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
                this.formulaText.AppendText(menuItem.Text + "\n");
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
                if (currentBaseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                {
                    if (selectedNode != null)
                    {
                        this.formulaText.Enabled = true;
                        if (currentBaseWindow.CurrentControl != null && currentBaseWindow.CurrentControl.Count==1)
                        {
                            if (selectedNode.Name.Trim().Equals(FormDesignerConstants.DefaultValue))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].DefaultValue;
                            }
                            if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnClick))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnClickValue;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.Exiting))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].ExitingValue;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.Entering))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].EnteringValue;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnDoubleClick))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnDoubleClick;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnDrop))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnDrop;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnFocus))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnFocus;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnBlur))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnBlur;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventCreate))
                            {
                                 formulaText.Text = currentBaseWindow.CurrentControl[0].OnEventCreateValue;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventEdit))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnEventEditValue;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventUpdate))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnEventUpdateValue;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnChange))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnChange;
                            }
                            else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnOK))
                            {
                                formulaText.Text = currentBaseWindow.CurrentControl[0].OnOK;
                            }
                        }
                    }
                }
                else
                {
                    MessageBox.Show("Please select a Form Pattern before entering Formula");
                }

            }
                
            //MessageBox.Show(sender.ToString());
            //Display the basewindow current control's setting
        }

        private void saveFormula_Click(object sender, EventArgs e)
        {
            try
            {
                currentBaseWindow = form.currentBaseWindow;
                this.parserError.Text = "";
                if (errorWriter != null)
                {
                    errorWriter.Dispose();
                }
                errorWriter = new StringWriter();
                if (!formulaText.Text.Trim().Equals(""))
                {
                    //Parse the text to Validate the user input formula
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
                }
                if (currentBaseWindow != null && selectedNode != null && currentBaseWindow.CurrentControl != null)
                {
                    if (selectedNode.Name.Trim().Equals(FormDesignerConstants.DefaultValue))
                    {
                        currentBaseWindow.CurrentControl[0].DefaultValue = formulaText.Text;
                    }
                    if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnClick))
                    {
                        currentBaseWindow.CurrentControl[0].OnClickValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.Exiting))
                    {
                        currentBaseWindow.CurrentControl[0].ExitingValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.Entering))
                    {
                        currentBaseWindow.CurrentControl[0].EnteringValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnDoubleClick))
                    {
                        currentBaseWindow.CurrentControl[0].OnDoubleClick = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnDrop))
                    {
                        currentBaseWindow.CurrentControl[0].OnDrop = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnFocus))
                    {
                        currentBaseWindow.CurrentControl[0].OnFocus = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnBlur))
                    {
                        currentBaseWindow.CurrentControl[0].OnBlur = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventCreate))
                    {
                        currentBaseWindow.CurrentControl[0].OnEventCreateValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventEdit))
                    {
                        currentBaseWindow.CurrentControl[0].OnEventEditValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventUpdate))
                    {
                        currentBaseWindow.CurrentControl[0].OnEventUpdateValue = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnChange))
                    {
                        currentBaseWindow.CurrentControl[0].OnChange = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnOK))
                    {
                        currentBaseWindow.CurrentControl[0].OnOK = formulaText.Text;
                    }
                }
            }
            catch (Exception ex)
            {

            }


        }

        private void FormulaEditorWindow_Load(object sender, EventArgs e)
        {

        }
    }
}