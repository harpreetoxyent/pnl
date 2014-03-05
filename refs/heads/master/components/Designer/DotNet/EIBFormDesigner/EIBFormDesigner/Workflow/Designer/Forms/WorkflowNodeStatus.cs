using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WeifenLuo.WinFormsUI.Docking;
using EIBFormDesigner.Workflow.Controls;

namespace EIBFormDesigner.Workflow.Designer.Forms
{
    public partial class WorkflowNodeStatus : DockContent
    {
        bool IsWizardStep = false;
        EIBNode node;

        public EIBNode Node
        {
            get { return node; }
            set { node = value; }
        }
        public WorkflowNodeStatus()
        {
            InitializeComponent();
        }
        public WorkflowNodeStatus(bool iswizardstep)
            : this()
        {
            IsWizardStep = iswizardstep;
        }
        ///// <summary>
        ///// creates a WorkflowFormPattern object 
        ///// </summary>
        ///// <param name="iswizardstep">true if the WorkflowFormPattern is the wizardstep else false.</param>
        public static WorkflowNodeStatus CreateInstance(bool iswizardstep, EIBNode node)
        {
            WorkflowNodeStatus workflownodestatus = new WorkflowNodeStatus(iswizardstep);
            if (iswizardstep)
            {
                workflownodestatus.DockAreas = DockAreas.Document;
                workflownodestatus.CloseButton = false;
            }
            workflownodestatus.Node = node;

            return workflownodestatus;
        }

        public void button1_Click(object sender, EventArgs e)
        {
            //((Button)sender).Enabled = false;
            node.WorkFlowNode.DataObject.Status = textBox1.Text;
            
        }
        public void setNodeStatus(string status)
        {
            textBox1.Text = status;
        }
        //#region prev code
        ////UsersCollection users;
        ////RolesCollection roles;
        ////RightsCollection rights;
        ////RoleRightMappingCollection rolerights;
        ////private void button1_Click(object sender, EventArgs e)
        ////{
        ////    /*****User Creation *****/
        ////    /*
        ////    users= new UsersCollection();
        ////    List<string> r=new List<string>();
        ////    r.Add("ceo");
        ////    r.Add("emp");
        ////    users.Add(User.CreateUser("a", new PersonalInfo("b", "c", "d", "a@b.c", DateTime.Now, "male"), "123456", r)) ;
        ////    users.Serialize();
        ////     * 

        ////    roles = new RolesCollection();
        ////    roles.Add(Role.CreateRole("ceo","malik of the company."));
        ////    roles.Add(Role.CreateRole("manager","company ko chalane wala."));
        ////    roles.Add(Role.CreateRole("emp","company mein kam karne wale."));
        ////    roles.Serialize();



        ////    rights = new RightsCollection();
        ////    rights.Add(EIBFormDesigner.UserAdmin.Objects.Right.CreateRight("admin", "all in all"));
        ////    rights.Add(EIBFormDesigner.UserAdmin.Objects.Right.CreateRight("superuser", "have more power"));

        ////    rights.Serialize(); 

        ////     */

        ////    rolerights = new RoleRightMappingCollection();
        ////    rolerights.Add(RoleRightMapping.NewRoleRightMapping("up1","ceo","admin", "all in all"));
        ////    rolerights.Add(RoleRightMapping.NewRoleRightMapping("up2","manager", "superuser", "managers are superusers of the company."));

        ////    rolerights.Serialize();
        ////}

        ////private void button2_Click(object sender, EventArgs e)
        ////{
        ////     /*****User Desearialization *****/
        ////    /*
        ////    users= UsersCollection.Desearilize();
        ////    MessageBox.Show(users.Count.ToString());
        ////     * 
        ////    roles = RolesCollection.Desearilize();
        ////    MessageBox.Show(roles.Count.ToString());


        ////    rights = RightsCollection.Desearilize();
        ////    MessageBox.Show(rights.Count.ToString());
        ////     */

        ////    rolerights = RoleRightMappingCollection.Desearilize();
        ////    MessageBox.Show(rolerights.ToString());

        ////}

        //#endregion

        //public void CheckListofFormpatterns()
        //{
        //    checkedListBox1.Items.Clear();
        //    int i = 0;
        //    foreach (string formpatternid in FormDesigner.listFormBaseWindow.Keys)
        //    {
        //        bool ischecked = false;
        //        if (node.workflowNode.FormPatterns != null)
        //        {
        //            foreach (FormPattern fp in node.WorkFlowNode.FormPatterns.ListofFormPatterns)
        //            {
        //                if (formpatternid == fp.Formpatternid)
        //                {
        //                    ischecked = true;
        //                }
        //            }
        //        }
        //        checkedListBox1.Items.Add(formpatternid, ischecked);
        //    }
        //}

        //private void WorkflowFormPattern_Load(object sender, EventArgs e)
        //{

        //}

        ////private void button2_Click(object sender, EventArgs e)
        ////{
        ////    if (!IsWizardStep)
        ////    {
        ////        this.Close();
        ////    }
        ////}

        ////private void button1_Click(object sender, EventArgs e)
        ////{
        ////    ((Button)sender).Enabled = false;
        ////    node.WorkFlowNode.FormPatterns.ListofFormPatterns.Clear();
        ////    foreach (object item in checkedListBox1.CheckedItems)
        ////    {
        ////        node.WorkFlowNode.FormPatterns.ListofFormPatterns.Add(new FormPattern(item.ToString()));
        ////    }
        ////}

        ////private void checkedListBox1_ItemCheck(object sender, ItemCheckEventArgs e)
        ////{
        ////    button1.Enabled = true;
        ////}
    }
}