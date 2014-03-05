using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

namespace EIBFormDesigner.Search
{
    public partial class SearchUserControl : UserControl
    {
        internal string selectedFieldList = null;
        internal string selectedConditionList = null;
        internal string selectedFieldValue = null;
        internal string selectedOperatorComboBox = null;

        internal Dictionary<string, IEIBControl> controlDiction = new Dictionary<string, IEIBControl>();

        public SearchUserControl()
        {
            InitializeComponent();
        }
        public string ControlType
        {
            get
            {
                return "Query";
            }
        }
        public IEIBControl GetIEIBControl(string controlName)
        {
            return controlDiction[controlName];
        }

        private void fieldList_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.fieldList.SelectedItem != null)
            {
                this.fieldValue.Text = "@" + this.fieldList.SelectedItem;
            }
        }

        private void SearchUserControl_KeyUp(object sender, KeyEventArgs e)
        {
            
        }
    }
}
