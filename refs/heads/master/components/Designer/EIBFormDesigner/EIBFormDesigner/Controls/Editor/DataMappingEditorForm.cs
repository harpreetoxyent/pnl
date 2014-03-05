using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls
{
    public partial class DataMappingEditorForm : Form
    {
        public DataMappingEditorForm()
        {
            InitializeComponent();
            BtnRemove.Enabled = false;
        }
        public List<DataMapping> dataMappings;

        public List<DataMapping> DataMappings
        {
            get { return dataMappings; }
            set
            {
                dataMappings = value;
                this.MappingList.Items.Clear();
                if (dataMappings != null)
                {
                    foreach (DataMapping mapping in dataMappings)
                    {
                        string mappingString = mapping.DataPatternName + ":" + mapping.DataTableName + ":" + mapping.DataFieldName;
                        MappingList.Items.Add(mappingString);
                    }
                }
            }
        }

        private void BtnRemove_Click(object sender, EventArgs e)
        {
            if (MappingList.SelectedIndex >= 0)
            {
                dataMappings.RemoveAt(MappingList.SelectedIndex);
                MappingList.Items.RemoveAt(MappingList.SelectedIndex);
                BtnRemove.Enabled = false;
            }
            propertyGrid1.SelectedObject = null;
        }

        private void MappingList_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (MappingList.SelectedIndex >= 0)
            {
                propertyGrid1.SelectedObject = DataMappings[MappingList.SelectedIndex];
                BtnRemove.Enabled = true;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            this.Close();
        }

        private void DataMappingEditorForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult = DialogResult.Cancel;
        }


    }
}