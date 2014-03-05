using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;

namespace EIBFormDesigner.UserAdmin
{
    public partial class AddGroup : Form
    {
        bool isedit = false;
        string groupEditId;
        public AddGroup(bool isedit, string groupid)
        {
            InitializeComponent();
            this.isedit = isedit;
            if (isedit && groupid != null)
            {
                Group group = UserAdminUtilities.UserAdminUtility.groups.GetGroupbyid(groupid);
                groupNameText.Text = group.GroupId;
                groupEditId = groupid; 
            }
        }

        private void saveRule_Click(object sender, EventArgs e)
        {
            if (isedit && groupEditId != null)
            {
                int groupIndex = UserAdminUtilities.UserAdminUtility.groups.GetGroupIndexbyid(groupEditId);
                if (groupIndex >= 0)
                {
                    if(!UserAdminUtilities.UserAdminUtility.groups.groupname.Contains(groupNameText.Text))
                    {
                        if(!String.IsNullOrEmpty(groupNameText.Text))
                        {
                            UserAdminUtilities.UserAdminUtility.groups[groupIndex] = Group.CreateGroup(groupNameText.Text, UserAdminConstants.CompanyId);
                            this.Close();
                        }
                        else
                            MessageBox.Show("Group name cannot be null or empty");
                    }
                    else
                        MessageBox.Show("Group with same name exists");
                }
            }
            else
            {
                if (!UserAdminUtilities.UserAdminUtility.groups.groupname.Contains(groupNameText.Text))
                {
                    if (!String.IsNullOrEmpty(groupNameText.Text))
                    {
                        UserAdminUtilities.UserAdminUtility.groups.Add(Group.CreateGroup(groupNameText.Text, UserAdminConstants.CompanyName));
                        this.Close();
                    }
                    else
                        MessageBox.Show("Group name cannot be null or empty");
                }
                else
                    MessageBox.Show("Group with same name exists");
            }
            //this.Close();
        }

        private void cancelRule_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}