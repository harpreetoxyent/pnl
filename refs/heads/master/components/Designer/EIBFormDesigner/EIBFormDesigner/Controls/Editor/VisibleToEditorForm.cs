using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.UserAdmin.Utility;
using EIBFormDesigner.UserAdmin;
using EIBFormDesigner.UserAdmin.Objects;

namespace EIBFormDesigner.Controls
{
    public partial class VisibleToEditorForm : Form
    {
        public VisibleToEditorForm()
        {
            InitializeComponent();
            foreach (EIBFormDesigner.UserAdmin.Objects.Right right in UserAdminUtilities.UserAdminUtility.Rights)
            {
                this.VisibleToList.Items.Add(right.Rightid,false);
            }
        }
        public List<string> visibleToRights;

        public List<string> VisibleToRights
        {
            get { return visibleToRights; }
            set
            {
                visibleToRights = value;
                if (visibleToRights != null)
                {
                    if (visibleToRights.Count == 1 && visibleToRights[0] == "All")
                    {
                        this.VisibleToList.SetItemChecked(0,true);
                    }
                    else
                    {
                        foreach (string right in visibleToRights)
                        {
                            int index = this.VisibleToList.Items.IndexOf(right);
                            if (index >= 0)
                            {
                                this.VisibleToList.SetItemChecked(index,true);
                            }
                        }
                    }
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.OK;
            this.Close();
        }

        private void DataMappingEditorForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult = DialogResult.OK;
            this.visibleToRights.Clear();
            if (this.VisibleToList.GetItemChecked(0))
            {
                this.visibleToRights.Add("All");
            }
            else
            {
                foreach (string item in this.VisibleToList.CheckedItems)
                {
                    this.visibleToRights.Add(item);
                }
            }
        }
        bool isAll;
        private void VisibleToList_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            if (e.Index == 0)
            {
                for (int index = 1; index < this.VisibleToList.Items.Count; index++)
                {
                    if (e.NewValue == CheckState.Checked)
                    {
                        isAll = true;
                        this.VisibleToList.SetItemCheckState(index, CheckState.Checked);
                    }
                    else
                    {
                        isAll = false;
                        this.VisibleToList.SetItemCheckState(index, CheckState.Unchecked);
                    }
                }
            }
            else
            {
                if (isAll && e.NewValue == CheckState.Unchecked)
                {
                    e.NewValue = CheckState.Checked;
                }
            }
        }




    }
}