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
    public partial class AddRole : Form
    {
        public AddRole()
        {
            InitializeComponent();
            CreateListForCheckedListBox(UserAdminUtilities.UserAdminUtility.Rights);
        }

        public AddRole(bool isEditing, int usrIndex)
            : this()
        {
            if (usrIndex != -1)
            {
                isEdited = isEditing;
                Role editRole = UserAdminUtilities.UserAdminUtility.Roles[usrIndex];
                if (editRole != null)
                {
                    currentRoleIndex = usrIndex;
                    currentRole = editRole;
                }
                reset();
            }
        }
        int currentRoleIndex = -1;
        Role currentRole = null;
        public void reset()
        {
            if (isEdited)
            {
                RoleIdText.Text = currentRole.Roleid;
                RoleIdText.Enabled = false;
                RoleDesc.Text = currentRole.Roledescription;
                foreach(string right in currentRole.Rights)
                {
                    checkedListBox1.SetItemChecked(checkedListBox1.Items.IndexOf(right),true);
                }
            }
            else
            {
                RoleIdText.Text = "";
                RoleDesc.Text = "";
                foreach (string right in checkedListBox1.Items)
                {
                    checkedListBox1.SetItemChecked(checkedListBox1.Items.IndexOf(right), false);
                }
            }
        }
        bool isEdited = false;
        public void CreateListForCheckedListBox(IList<Right> Rights)
        {
            foreach (Right right in Rights)
            {
                checkedListBox1.Items.Add(right.Rightid, false);
            }
        }
        private void saveButton_Click(object sender, EventArgs e)
        {
            string roleid, roledescription;
            List<string> rights = new List<string>();
            roleid = RoleIdText.Text;
            roledescription = RoleDesc.Text;
            foreach (object o in checkedListBox1.CheckedItems)
            {
                rights.Add(o.ToString());
            }
            if (isEdited)
            {
                if (UserAdminUtilities.UserAdminUtility.Roles.rolename.Contains(roleid))
                {
                    if (!String.IsNullOrEmpty(roleid))
                    {
                        UserAdminUtilities.UserAdminUtility.Roles.RemoveAt(currentRoleIndex);
                        UserAdminUtilities.UserAdminUtility.Roles.Insert(currentRoleIndex, Role.CreateRole(roleid, roledescription, rights));
                        this.Close();
                    }
                    else
                        MessageBox.Show("Role id can not be empty");
                }
            }
            else
            {
                if (!UserAdminUtilities.UserAdminUtility.Roles.rolename.Contains(roleid))
                {
                    if (!String.IsNullOrEmpty(roleid))
                    {
                        UserAdminUtilities.UserAdminUtility.Roles.Add(Role.CreateRole(roleid, roledescription, rights));
                        this.Close();
                    }
                    else
                        MessageBox.Show("Right id can not be empty");
                }
                else
                    MessageBox.Show("Role with same id exists");
            }
            //this.Close();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();







        }

    }
}