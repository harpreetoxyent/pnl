using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WeifenLuo.WinFormsUI.Docking;
using EIBFormDesigner.Workflow;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.Workflow.Designer.Wizards;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner;
using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.Workflow.Designer.Forms
{
    public partial class WorkflowDataPattern : DockContent
    {
        bool IsWizardStep = false;
        EIBNode node;

        public EIBNode Node
        {
            get { return node; }
            set { node = value; }
        }
        protected WorkflowDataPattern()
        {
            InitializeComponent();
        }

        /// <summary>
        /// creates a WorkflowFormPattern object 
        /// </summary>
        /// <param name="iswizardstep">true if the WorkflowFormPattern is the wizardstep else false.</param>
        protected WorkflowDataPattern(bool iswizardstep)
            : this()
        {
            IsWizardStep = iswizardstep;
        }

        /// <summary>
        /// creates a WorkflowFormPattern object 
        /// </summary>
        /// <param name="iswizardstep">true if the WorkflowFormPattern is the wizardstep else false.</param>
        public static WorkflowDataPattern CreateInstance(bool iswizardstep,EIBNode node)
        {
            WorkflowDataPattern workflowdatapattern = new WorkflowDataPattern(iswizardstep);
            if (iswizardstep)
            {
                workflowdatapattern.DockAreas = DockAreas.Document;
                workflowdatapattern.CloseButton = false;
            }
            workflowdatapattern.Node = node;
            return workflowdatapattern;
        }
        UsersCollection users;
        RolesCollection roles;
        RightsCollection rights;
        RoleRightMappingCollection rolerights;

        public void CheckListofDatapatterns()
        {
            checkedListBox1.Items.Clear();
            int i = 0;
            //foreach (string datapatternid in FormDesigner.listDataBaseWindow.Keys)
            foreach (string datapatternid in FormDesignerUtilities.dataWindowNames.Keys)
            {
                bool ischecked = false;
                if (node.workflowNode.DataPatterns != null)
                {
                    foreach (DataPattern dp in node.WorkFlowNode.DataPatterns.ListofDataPatterns)
                    {
                        if (datapatternid == dp.Datapatternid)
                        {
                            ischecked = true;
                        }
                    }
                }
                checkedListBox1.Items.Add(datapatternid, ischecked);
            }
        }

        public void button1_Click(object sender, EventArgs e)
        {
                //((Button)sender).Enabled = false;
            node.WorkFlowNode.DataPatterns.ListofDataPatterns.Clear();
            foreach (object item in checkedListBox1.CheckedItems)
            {
                node.WorkFlowNode.DataPatterns.ListofDataPatterns.Add(new DataPattern(item.ToString()));
            }
        }
        bool isUncheck = false;
        private void checkedListBox1_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            button1.Enabled = true;
            if (isUncheck)
            {
                isUncheck = false;
                for (int index = 0; index < checkedListBox1.Items.Count; index++)
                {
                    checkedListBox1.SetItemChecked(index, false);
                }
            }
           
        }

        private void button2_Click(object sender, EventArgs e)
        {

            if (!IsWizardStep)
            {
                this.Close();
            }
        }

        private void checkedListBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            isUncheck = true;
        }

        
    }
}
