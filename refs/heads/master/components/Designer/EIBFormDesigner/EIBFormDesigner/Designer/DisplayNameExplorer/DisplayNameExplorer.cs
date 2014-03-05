using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Xml;
using System.Text;
using System.IO;
using System.Collections.Generic;
using EIBFormDesigner.XML;
using EIBXMLServices;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Designer.DatabaseExlorer
{
    public partial class DisplayNameExplorer : ToolWindow
    {
        private FormDesigner designerForm = null;
        private Dictionary<string, EIBLabel> mapList = new Dictionary<string, EIBLabel>();

        internal Dictionary<string, EIBLabel> MapList
        {
            get {
                if (formPatternMapList.ContainsKey(designerForm.CurrentBaseWindow.Name))
                {
                    return formPatternMapList[designerForm.CurrentBaseWindow.Name];
                }
                else
                {
                    return null;
                }
            }
            //set { mapList = value; }
        }
        internal Dictionary<string, Dictionary<string, EIBLabel>> formPatternMapList = new Dictionary<string, Dictionary<string, EIBLabel>>();
        private Dictionary<string, EIBLabel> labelList = new Dictionary<string, EIBLabel>();
        private System.ComponentModel.IContainer components;

        public DisplayNameExplorer(FormDesigner form)
        {
            designerForm = form;
            //
            // Required for Windows Form Designer support
            //
            this.Text = "Display Name Explorer";
            this.TabText = "Display Name Explorer";
            InitializeComponent();
            //initFormContextMenu();
            //initDataContextMenu();
            //initWorkflowContextMenu();
            //
            // TODO: Add any constructor code after InitializeComponent call
            //
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DisplayNameExplorer));
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.button1 = new System.Windows.Forms.Button();
            this.closeButton = new System.Windows.Forms.Button();
            this.assignButton = new System.Windows.Forms.Button();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "");
            this.imageList1.Images.SetKeyName(1, "");
            this.imageList1.Images.SetKeyName(2, "");
            this.imageList1.Images.SetKeyName(3, "");
            this.imageList1.Images.SetKeyName(4, "");
            this.imageList1.Images.SetKeyName(5, "");
            this.imageList1.Images.SetKeyName(6, "");
            this.imageList1.Images.SetKeyName(7, "");
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.splitContainer1.IsSplitterFixed = true;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Margin = new System.Windows.Forms.Padding(2);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.comboBox1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.button1);
            this.splitContainer1.Panel2.Controls.Add(this.closeButton);
            this.splitContainer1.Panel2.Controls.Add(this.assignButton);
            this.helpProvider1.SetHelpKeyword(this.splitContainer1.Panel2, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.splitContainer1.Panel2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.helpProvider1.SetShowHelp(this.splitContainer1.Panel2, true);
            this.splitContainer1.Size = new System.Drawing.Size(219, 396);
            this.splitContainer1.SplitterDistance = 343;
            this.splitContainer1.SplitterWidth = 3;
            this.splitContainer1.TabIndex = 1;
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(3, 12);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(200, 21);
            this.comboBox1.TabIndex = 0;
            // 
            // button1
            // 
            this.helpProvider1.SetHelpKeyword(this.button1, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.button1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.button1.Location = new System.Drawing.Point(73, 12);
            this.button1.Margin = new System.Windows.Forms.Padding(2);
            this.button1.Name = "button1";
            this.helpProvider1.SetShowHelp(this.button1, true);
            this.button1.Size = new System.Drawing.Size(70, 24);
            this.button1.TabIndex = 2;
            this.button1.Text = "Remove";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // closeButton
            // 
            this.helpProvider1.SetHelpKeyword(this.closeButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.closeButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.closeButton.Location = new System.Drawing.Point(147, 12);
            this.closeButton.Margin = new System.Windows.Forms.Padding(2);
            this.closeButton.Name = "closeButton";
            this.helpProvider1.SetShowHelp(this.closeButton, true);
            this.closeButton.Size = new System.Drawing.Size(70, 24);
            this.closeButton.TabIndex = 1;
            this.closeButton.Text = "&Close";
            this.closeButton.UseVisualStyleBackColor = true;
            this.closeButton.Click += new System.EventHandler(this.closeButton_Click);
            // 
            // assignButton
            // 
            this.assignButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.helpProvider1.SetHelpKeyword(this.assignButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.assignButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.assignButton.Location = new System.Drawing.Point(2, 12);
            this.assignButton.Margin = new System.Windows.Forms.Padding(2);
            this.assignButton.Name = "assignButton";
            this.helpProvider1.SetShowHelp(this.assignButton, true);
            this.assignButton.Size = new System.Drawing.Size(70, 24);
            this.assignButton.TabIndex = 0;
            this.assignButton.Text = "&Assign";
            this.assignButton.UseVisualStyleBackColor = true;
            this.assignButton.Click += new System.EventHandler(this.assignButton_Click);
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // DisplayNameExplorer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.ClientSize = new System.Drawing.Size(219, 396);
            this.Controls.Add(this.splitContainer1);
            this.helpProvider1.SetHelpKeyword(this, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "DisplayNameExplorer";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockRight;
            this.Text = "DisplayNameExplorer";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        public int NodeLevel(TreeNode node)
        {

            int level = 0;
            while ((node = node.Parent) != null)
            {
                level++;
            }
            return level;

        } 
        #endregion

 
        private void Form1_Load(object sender, System.EventArgs e)
        {
            //this.LoadSampleData();
            initExplorer();

        }

        internal void initExplorer()
        {
            comboBox1.Items.Clear();
            labelList.Clear();
            if (designerForm.CurrentBaseWindow != null)
            {
                if (designerForm.CurrentBaseWindow.TypeOfWindow == FormDesignerConstants.FormPattern)
                {
                    comboBox1.Enabled = true;
                    pickAllLabels(designerForm.CurrentBaseWindow);
                }
                else
                {
                    comboBox1.Enabled = false;
                }
            }
            else
            {
                comboBox1.Enabled = false;
            }
        }

        internal void pickAllLabels(Control ctrl)
        {
            if (ctrl.GetType() == typeof(EIBLabel))
            {
                comboBox1.Items.Add(ctrl.Name+"("+ctrl.Text+")");
                labelList.Add(ctrl.Name, (EIBLabel)ctrl);
            }
            if (ctrl.Controls != null && ctrl.Controls.Count > 0)
            {
                foreach(Control ctl in ctrl.Controls)
                {
                    pickAllLabels(ctl);
                }
            }
        }

        private void closeButton_Click(object sender, EventArgs e)
        {
            this.Hide();
            //this.Close();
        }

        private void assignButton_Click(object sender, EventArgs e)
        {
            //Check if there is a selected base window to work on
            if (designerForm.CurrentBaseWindow != null)
            {
                //check if it is Form Pattern
                if (designerForm.CurrentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.FormPattern))
                {
                    //check if there is selected control on the form
                    if (designerForm.CurrentBaseWindow.CurrentControl != null && designerForm.currentBaseWindow.CurrentControl.Count==1)
                    {
                        Control ctrl = (Control)designerForm.CurrentBaseWindow.CurrentControl[0];
                        if (ctrl.GetType() == typeof(EIBTextBox) || ctrl.GetType() == typeof(EIBDatePicker)
                            || ctrl.GetType() == typeof(EIBTime) || ctrl.GetType() == typeof(EIBListbox)
                            || ctrl.GetType() == typeof(EIBCombobox))
                        {
                            if (!MapList.ContainsKey(ctrl.Name))
                            {
                                if (comboBox1.SelectedIndex >= 0)
                                {
                                    string selectedItemText = comboBox1.SelectedItem.ToString();
                                    selectedItemText = selectedItemText.Substring(0, selectedItemText.IndexOf('('));
                                    MapList.Add(ctrl.Name, labelList[selectedItemText]);
                                }
                            }
                            else
                            {
                                if (comboBox1.SelectedIndex >= 0)
                                {
                                    string selectedItemText = comboBox1.SelectedItem.ToString();
                                    selectedItemText = selectedItemText.Substring(0, selectedItemText.IndexOf('('));
                                    MapList[ctrl.Name] = labelList[selectedItemText];
                                }
                            }
                        }
                    }
                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (designerForm.CurrentBaseWindow != null)
            {
                //check if it is Form Pattern
                if (designerForm.CurrentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.FormPattern))
                {
                    //check if there is selected control on the form
                    if (designerForm.CurrentBaseWindow.CurrentControl != null && designerForm.currentBaseWindow.CurrentControl.Count == 1)
                    {
                        Control ctrl = (Control)designerForm.CurrentBaseWindow.CurrentControl[0];
                        if (ctrl.GetType() == typeof(EIBTextBox) || ctrl.GetType() == typeof(EIBDatePicker)
                            || ctrl.GetType() == typeof(EIBTime) || ctrl.GetType() == typeof(EIBListbox)
                            || ctrl.GetType() == typeof(EIBCombobox))
                        {
                            MapList.Remove(ctrl.Name);
                            comboBox1.SelectedIndex = -1;
                            comboBox1.SelectedText = "";
                        }
                    }
                }
            }
        }

        public void serializeXMLMappings()
        {
            try
            {
                XmlDocument xmldoc = new XmlDocument();
                XmlElement xmlMapFile = xmldoc.CreateElement("MapFile");
                foreach (string mapControlName in formPatternMapList.Keys)
                {
                    XmlElement xmlFormPattern = xmldoc.CreateElement("formpattern");
                    XmlAttribute formpatternNameAtt = xmldoc.CreateAttribute("name");
                    formpatternNameAtt.Value = mapControlName;
                    xmlFormPattern.Attributes.Append(formpatternNameAtt);
                    Dictionary<string, EIBLabel> mapDict = formPatternMapList[mapControlName];
                    foreach (string mapedControlName in mapDict.Keys)
                    {
                        XmlElement xmlField = xmldoc.CreateElement("field");
                        XmlAttribute fieldId = xmldoc.CreateAttribute("id");
                        fieldId.Value = mapedControlName;
                        xmlField.Attributes.Append(fieldId);

                        XmlAttribute fieldDisplayName = xmldoc.CreateAttribute("displayName");
                        fieldDisplayName.Value = mapDict[mapedControlName].Text;
                        xmlField.Attributes.Append(fieldDisplayName);

                        XmlAttribute fieldLabelId = xmldoc.CreateAttribute("labelid");
                        fieldLabelId.Value = mapDict[mapedControlName].Name;
                        xmlField.Attributes.Append(fieldLabelId);

                        xmlFormPattern.AppendChild(xmlField);
                    }
                    xmlMapFile.AppendChild(xmlFormPattern);
                }
                xmldoc.AppendChild(xmlMapFile);
                string fileName = EIBXMLUtilities.formFolderName + "\\MapFile.xml";
                Stream xmlFile = new FileStream(fileName, FileMode.Create);
                xmldoc.Save(xmlFile);
                xmlFile.Close();
                this.comboBox1.Enabled = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Map File get Corrupted during saving.");
            }
        }
        /// <summary>
  ///<MapFile>
  /*<formpattern name="formpattern0">
    <field id="listbox0" displayName="label0" labelid="label0" />
    <field id="listbox1" displayName="label3" labelid="label3" />
    <field id="combobox0" displayName="label3" labelid="label4" />
  </formpattern>
  <formpattern name="formpattern1">
    <field id="combobox1" displayName="label1" labelid="label1" />
    <field id="textbox0" displayName="label1" labelid="label2" />
  </formpattern>
</MapFile>*/
        /// </summary>

        public void DeSerializeXMLMappings()
        {
            try
            {
                this.initExplorer();
                XmlDocument xmldoc = new XmlDocument();
                string fileName = EIBXMLUtilities.formFolderName + "\\MapFile.xml";
                xmldoc.Load(fileName);
                foreach (XmlNode xmlNode in xmldoc.FirstChild.ChildNodes)
                {
                    if (!formPatternMapList.ContainsKey(xmlNode.Attributes[0].Value))
                    {
                        formPatternMapList.Add(xmlNode.Attributes[0].Value, new Dictionary<string, EIBLabel>());
                    }
                    Dictionary<string, EIBLabel> fieldMapList = formPatternMapList[xmlNode.Attributes[0].Value];
                    foreach (XmlNode xmlFieldNode in xmlNode.ChildNodes)
                    {
                        labelControl = null;
                        findControlWithId(xmlFieldNode.Attributes["labelid"].Value, FormDesigner.listFormBaseWindow[xmlNode.Attributes[0].Value]);
                        if (labelControl != null)
                        fieldMapList.Add(xmlFieldNode.Attributes["id"].Value, (EIBLabel)labelControl);
                    }
                }
                this.comboBox1.Enabled = false;
            }
            catch
            {

            }
        }
        public Control labelControl;
        public void findControlWithId(string id,Control ctrl)
        {
            if (id == ctrl.Name)
            {
                labelControl = ctrl;
                return;
            }
            else
            {
                foreach (Control ctl in ctrl.Controls)
                {
                    findControlWithId(id, ctl);
                }
            }
        }

  
    }
 }