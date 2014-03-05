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
using EIBFormDesigner.Controls;

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
            //TreeView treeControl = (TreeView)sender;
            //selectedNode = treeControl.SelectedNode;
            
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
            if (currentBaseWindow != null)
            {
                IEIBControl control = form.currentBaseWindow.CurrentControl[0];
                TreeView treeControl = (TreeView)sender;
                selectedNode = treeControl.SelectedNode;

                if (currentBaseWindow != null)
                {
                    if (currentBaseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                    {
                        if (selectedNode != null)
                        {
                            this.formulaText.Enabled = true;
                            if (currentBaseWindow.CurrentControl != null && currentBaseWindow.CurrentControl.Count == 1)
                            {
                                if (selectedNode.Name.Trim().Equals(FormDesignerConstants.DefaultValue))
                                {
                                    if (currentBaseWindow.CurrentControl[0].DefaultValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].DefaultValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].DefaultValue;
                                }
                                if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnClick))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnClickValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnClickValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnClickValue;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.Exiting))
                                {
                                    if (currentBaseWindow.CurrentControl[0].ExitingValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].ExitingValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].ExitingValue;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.Entering))
                                {
                                    if (currentBaseWindow.CurrentControl[0].EnteringValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].EnteringValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].EnteringValue;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnDoubleClick))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnDoubleClick == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnDoubleClick = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnDoubleClick;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnDrop))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnDrop == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnDrop = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnDrop;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnFocus))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnFocus == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnFocus = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnFocus;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnBlur))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnBlur == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnBlur = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnBlur;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventCreate))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnEventCreateValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnEventCreateValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnEventCreateValue;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventEdit))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnEventEditValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnEventEditValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnEventEditValue;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventUpdate))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnEventUpdateValue == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnEventUpdateValue = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnEventUpdateValue;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnChange))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnChange == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnChange = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnChange;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnOK))
                                {
                                    if (currentBaseWindow.CurrentControl[0].OnOK == null)
                                    {
                                        currentBaseWindow.CurrentControl[0].OnOK = "";
                                    }
                                    formulaText.Text = currentBaseWindow.CurrentControl[0].OnOK;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.GlobalScripts))
                                {
                                    if (((EIBPanel)control).GlobalScripts == null)
                                    {
                                        ((EIBPanel)control).GlobalScripts = "";
                                    }
                                    formulaText.Text = ((EIBPanel)control).GlobalScripts;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnCheck))
                                {
                                    if (((EIBCheckbox)control).OnCheck == null)
                                    {
                                        ((EIBCheckbox)control).OnCheck = "";
                                    }
                                    formulaText.Text = ((EIBCheckbox)control).OnCheck;
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnOpen))
                                {
                                    if (control is EIBTreeNode)
                                    {
                                        if (((EIBTreeNode)control).OnOpen == null)
                                        {
                                            ((EIBTreeNode)control).OnOpen = "";
                                        }
                                        formulaText.Text = ((EIBTreeNode)control).OnOpen;
                                    }
                                    else if (control is EIBCombobox)
                                    {
                                        if (((EIBCombobox)control).OnOpen == null)
                                        {
                                            ((EIBCombobox)control).OnOpen = "";
                                        }
                                        formulaText.Text = ((EIBCombobox)control).OnOpen;
                                    }
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnSelect))
                                {
                                    if (control is EIBTreeView)
                                    {
                                        if (((EIBTreeView)control).OnSelect == null)
                                        {
                                            ((EIBTreeView)control).OnSelect = "";
                                        }
                                        formulaText.Text = ((EIBTreeView)control).OnSelect;
                                    }
                                    else if (control is EIBGrid)
                                    {
                                        if (((EIBGrid)control).OnSelect == null)
                                        {
                                            ((EIBGrid)control).OnSelect = "";
                                        }
                                        formulaText.Text = ((EIBGrid)control).OnSelect;
                                    }
                                }
                                else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnChanging))
                                {
                                    if (control is EIBCombobox)
                                    {
                                        if (((EIBCombobox)control).OnChanging == null)
                                        {
                                            ((EIBCombobox)control).OnChanging = "";
                                        }
                                        formulaText.Text = ((EIBCombobox)control).OnChanging;
                                    }
                                    else if (control is EIBTextBox)
                                    {
                                        if (((EIBTextBox)control).OnChanging == null)
                                        {
                                            ((EIBTextBox)control).OnChanging = "";
                                        }
                                        formulaText.Text = ((EIBTextBox)control).OnChanging;
                                    }
                                }
                                
                            }
                        }
                        else
                        {
                            formulaText.Text = "";
                            MessageBox.Show("Please select a Form Pattern before entering Formula.");
                        }

                    }
                }
                else
                {
                    MessageBox.Show("Please select a Form Pattern before entering Formula.");
                }

                //MessageBox.Show(sender.ToString());
                //Display the basewindow current control's setting
            }
        }

        private void saveFormula_Click(object sender, EventArgs e)
        {
            try
            {
                currentBaseWindow = form.currentBaseWindow;
                IEIBControl control = form.currentBaseWindow.CurrentControl[0];
                //this.parserError.Text = "";
                if (errorWriter != null)
                {
                    errorWriter.Dispose();
                }
                errorWriter = new StringWriter();
                //if (!formulaText.Text.Trim().Equals("") && !(selectedNode.Name.Trim().Equals(FormDesignerConstants.GlobalScripts)))
                //{
                //    //Parse the text to Validate the user input formula
                //    Scanner scanner = new Scanner(formulaText.Text);
                //    Parser parser = new Parser(scanner);
                //    parser.errors.errorStream = errorWriter;
                //    parser.Parse();
                //    TextReader stringReader = new StringReader(errorWriter.ToString());
                //    this.parserError.Text = stringReader.ReadToEnd();
                //    Console.Write(parser.errors.count + " errors detected");
                //    if (parser.errors.count > 0)
                //    {
                //        return;
                //    }
                //}
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
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.GlobalScripts))
                    {
                        ((EIBPanel)control).GlobalScripts = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnCheck))
                    {
                        ((EIBCheckbox)control).OnCheck = formulaText.Text;
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnOpen))
                    {
                        if (control is EIBTreeNode)
                        {
                            ((EIBTreeNode)control).OnOpen = formulaText.Text;
                        }
                        else if (control is EIBCombobox)
                        {
                            ((EIBCombobox)control).OnOpen = formulaText.Text;
                        }
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnSelect))
                    {  
                        if(control is EIBTreeView)
                        {
                            ((EIBTreeView)control).OnSelect = formulaText.Text;
                        }
                        else if (control is EIBGrid)
                        {
                        ((EIBGrid)control).OnSelect = formulaText.Text;
                        }
                    }
                    else if (selectedNode.Name.Trim().Equals(FormDesignerConstants.OnChanging))
                    {
                        if (control is EIBTextBox)
                        {
                            ((EIBTextBox)control).OnChanging = formulaText.Text;
                        }
                        else if (control is EIBCombobox)
                        {
                            ((EIBCombobox)control).OnChanging = formulaText.Text;
                        }
                    }
                    
                }
            }
            catch (Exception ex)
            {
                String errStr = "Error saving event: " + ex.Message + "\r\n";
                MessageBox.Show(errStr);
            }


        }

        private void FormulaEditorWindow_Load(object sender, EventArgs e)
        {
            formulaText.Text = "";
        }

    }
}