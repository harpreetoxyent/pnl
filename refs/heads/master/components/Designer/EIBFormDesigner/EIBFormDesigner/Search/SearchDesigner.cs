using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Search
{
    public partial class SearchDesigner : Form
    {

        Control currentBaseWindow = null;
        List<String> fieldNameArray = new List<string>();
        TableLayoutPanel searchList = new TableLayoutPanel();
        int rowCounter;
        Dictionary<string, IEIBControl> controlDiction = new Dictionary<string, IEIBControl>();
        internal SearchUserControl searchControl = null;
        public SearchDesigner(Control parentFrame)
        {
            currentBaseWindow = parentFrame;
            InitializeComponent();
            searchList.Controls.Clear();
            searchList.AutoScroll = true;
            searchList.AutoSize = true;
            searchList.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 100));
            searchList.Size = new Size(this.panel1.Width - 10, this.panel1.Height-10);
            this.panel1.Controls.Add(searchList);
            rowCounter = this.searchList.RowCount;
            initSearchFrame(parentFrame);
        }

        public void initSearchFrame(Control parentControl)
        {
            foreach (Control control in parentControl.Controls)
            {
                if (!(control is EIBSearch) && !control.Name.Trim().Equals(""))
                {
                    /*IEIBControl eibControl = (IEIBControl)control;
                    if ((eibControl.DataTableName != null && !(eibControl.DataTableName.Trim().Equals(""))) || (eibControl.DataFieldName != null && !(eibControl.DataFieldName.Trim().Equals(""))))
                    {
                        if (!controlDiction.ContainsKey(control.Name))
                        {
                            controlDiction.Add(control.Name, eibControl);
                            fieldNameArray.Add(control.Name);
                        }
                    }*/
                    if (control is EIBPanel)
                    {
                        initSearchFrame(control);
                    }
                }
            }
        }

        internal void addButton_Click(object sender, EventArgs e)
        {
            if (this.searchList.Controls.Count > 0)
            {
                SearchUserControl searchCtrl = (SearchUserControl)this.searchList.Controls[this.searchList.Controls.Count - 1];
                if (searchCtrl.operatorComboBox.SelectedItem != null)
                {
                    searchControl = new SearchUserControl();
                    searchControl.controlDiction = controlDiction;
                    string[] arrayItems = fieldNameArray.ToArray();
                    searchControl.fieldList.Items.AddRange(arrayItems);
                    this.searchList.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20));
                    searchControl.removeButton.Click += new EventHandler(removeButton_Click);
                    this.searchList.Controls.Add(searchControl);
                    rowCounter++;
                }
                else
                {
                    MessageBox.Show("Operator needs to be selected before adding new condition");
                }
            }
            else
            {
                searchControl = new SearchUserControl();
                searchControl.controlDiction = controlDiction;
                string[] arrayItems = fieldNameArray.ToArray();
                searchControl.fieldList.Items.AddRange(arrayItems);
                this.searchList.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20));
                searchControl.removeButton.Click += new EventHandler(removeButton_Click);
                this.searchList.Controls.Add(searchControl);
                rowCounter++;
            }
        }

        void removeButton_Click(object sender, EventArgs e)
        {
            Button removeButton = (Button)sender;
            if (removeButton != null)
            {
                this.searchList.Controls.Remove(removeButton.Parent);
            }
        }

        private void saveBUtton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void SearchDesigner_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (FormDesignerConstants.EscapeKeyConstant))
            {
                this.Close();
            }
        }
    }
}