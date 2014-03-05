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
using user=EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.Workflow.Designer.Forms
{
    public partial class WorkflowUserPattern : DockContent
    {
        bool IsWizardstep=false;
        EIBNode node;
        protected WorkflowUserPattern()
        {
            InitializeComponent();
        }

        /// <summary>
        /// creates a WorkflowFormPattern object 
        /// </summary>
        /// <param name="iswizardstep">true if the WorkflowFormPattern is the wizardstep else false.</param>
        protected WorkflowUserPattern(bool iswizardstep)
            : this()
        {
            IsWizardstep = iswizardstep;
        }

        /// <summary>
        /// creates a WorkflowFormPattern object 
        /// </summary>
        /// <param name="iswizardstep">true if the WorkflowFormPattern is the wizardstep else false.</param>
        public static WorkflowUserPattern CreateInstance(bool iswizardstep,EIBNode node)
        {
            WorkflowUserPattern workflowuserpattern = new WorkflowUserPattern(iswizardstep);
            if (iswizardstep)
            {
                workflowuserpattern.DockAreas = DockAreas.Document;
                workflowuserpattern.CloseButton = false;
            }
            workflowuserpattern.node = node;
            return workflowuserpattern;
        }

        public void CheckListofUserpatterns()
        {
            checkedListBox1.Items.Clear();
            int i = 0;
            foreach (user.UserPattern userpattern in UserAdminUtilities.UserAdminUtility.UserPatterns)
            {
                bool ischecked = false;
                if (node.workflowNode.UserPatterns != null)
                {
                    foreach (UserPattern  up in node.WorkFlowNode.UserPatterns.ListofUserPatterns)
                    {
                        if (up.Userpatternid == userpattern.Userpatternid)
                        {
                            ischecked = true;
                        }
                    }
                }
                checkedListBox1.Items.Add(userpattern.Userpatternid, ischecked);
            }
        }

        public void button1_Click(object sender, EventArgs e)
        {
            // ((Button)sender).Enabled = false;
            node.WorkFlowNode.UserPatterns.ListofUserPatterns.Clear();
            foreach(object item in checkedListBox1.CheckedItems)
            {
                node.WorkFlowNode.UserPatterns.ListofUserPatterns.Add(new UserPattern(item.ToString()));
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {

            if (!IsWizardstep)
            {
                this.Close();
            }



            
        }

        private void WorkflowUserPattern_Load(object sender, EventArgs e)
        {
            //userpattern.UserPatternCollection userpatterns = new EIBFormDesigner.UserAdmin.Objects.UserPatternCollection();
            //List<string> rights=new List<string>();
            //rights.Add("create");
            //rights.Add("delete");
            //userpatterns.Add(userpattern.UserPattern.CreateUserPattern("up1",userpattern.Role.CreateRole("manager","sdfsdfsdf",rights),"sdfsdfsdf",new List<string>()));
            //userpatterns.Serialize();
        }
        bool isUncheck = false;
        private void checkedListBox1_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            button3.Enabled = true;
            if (isUncheck)
            {
                isUncheck = false;
                for (int index = 0; index < checkedListBox1.Items.Count; index++)
                {
                    checkedListBox1.SetItemChecked(index, false);
                }
            }
        }

        private void checkedListBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            isUncheck = true;
        }

      
    }
}