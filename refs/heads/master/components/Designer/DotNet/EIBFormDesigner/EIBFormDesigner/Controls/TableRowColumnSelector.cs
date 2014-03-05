using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls
{
    public partial class TableRowColumnSelector : Form
    {
        public TableRowColumnSelector()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
        public void initTableRowColumn(int rowCount, int columnCount)
        {
            this.rowCombo.Items.Add(0);
            this.columnCombo.Items.Add(0);
            this.rowCombo.SelectedIndex = 0;
            this.columnCombo.SelectedIndex = 0;
            for (int i = 1; i < rowCount; i++)
            {
                this.rowCombo.Items.Add(i);
            }
            for (int i = 1; i < columnCount; i++)
            {
                this.columnCombo.Items.Add(i);
            }
        }
        private void okButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
