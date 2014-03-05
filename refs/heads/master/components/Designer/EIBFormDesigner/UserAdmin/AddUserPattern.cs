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
    public partial class AddUserPattern : Form
    {
        public AddUserPattern(bool isedit,string userpatternid)
        {
            InitializeComponent();
            Roles = new Dictionary<string, Role>();
            AssignRoles(UserAdminUtilities.UserAdminUtility.Roles);
            AssignUsers(UserAdminUtilities.UserAdminUtility.Users);
            AssignFormPatterns();
            this.isedit = isedit;
            if (isedit && userpatternid!=null)
            {
                RestoreUserPatternSettings(userpatternid);
                usereditpatternid = userpatternid;
            }
        }
        Dictionary<string, Role> Roles;

        bool isedit = false;
        string usereditpatternid;
        public void RestoreUserPatternSettings(string userpattternid)
        {
            UserPattern userpattern= UserAdminUtilities.UserAdminUtility.UserPatterns.GetUserPatternbyid(userpattternid);
            RolesCombo.SelectedItem = userpattern.Role.Roleid;
            defaultFormPatternCombo.SelectedItem = userpattern.DefaultFormPattern;
            RestoreRolesSettings(userpattern.Role.Rights);
            UserPatternIdText.Text = userpattern.Userpatternid;
            UserPatternIdText.Enabled = false;
            RestoreUsersSettings(userpattern.Users);
            textBox1.Text = userpattern.Comments;
        }

        public void RestoreRolesSettings(List<string> rights)
        {
            for (int index = 0; index < RightscheckedListBox.Items.Count; index++)
            {
                RightscheckedListBox.SetItemChecked(index, false);
            }
            for (int index = 0; index < RightscheckedListBox.Items.Count; index++)
            {
                foreach (string right in rights)
                {
                    if (RightscheckedListBox.Items[index].ToString() == right)
                    {
                        RightscheckedListBox.SetItemChecked(index, true);
                    }
                }
            }
        }

        public void RestoreUsersSettings(List<string> users)
        {
            for(int index=0;index< UserscheckedListBox.Items.Count; index++)
            {
                foreach(string user in users)
                {
                    if (UserscheckedListBox.Items[index].ToString() == user)
                    {
                        UserscheckedListBox.SetItemChecked(index, true);
                    }
                }
            }
        }

        public void AssignFormPatterns()
        {
            defaultFormPatternCombo.Items.Clear();
            defaultFormPatternCombo.Items.AddRange(AccessControlList.FormPatterns.ToArray());
        }

        public void AssignRoles(RolesCollection roles)
        {
            RolesCombo.Items.Clear();
            foreach (Role r in roles)
            {
                Roles.Add(r.Roleid, r);
                RolesCombo.Items.Add(r.Roleid);
            }
        }

        public void AssignUsers(UsersCollection users)
        {
            UserscheckedListBox.Items.Clear();
            foreach (User user in users)
            {
                UserscheckedListBox.Items.Add(user.Userid);
            }
        }

        private void saveOrgButton_Click(object sender, EventArgs e)
        {
            string userpatternid, comments,defaultformpattern;
            Role role=null;
            List<string> users = new List<string>() ;

            userpatternid = UserPatternIdText.Text;
            if (defaultFormPatternCombo.SelectedIndex > -1)
            {
                defaultformpattern = defaultFormPatternCombo.SelectedItem.ToString();
            }
            else
            {
                defaultformpattern = "";
            }
            if (RolesCombo.Items.Count > 0 && RolesCombo.SelectedItem!=null)
            {
                role = Roles[RolesCombo.SelectedItem.ToString()];
            }
            else
            {
                return;
            }
            comments = textBox1.Text;
            role.Rights.Clear();
            foreach (string right in RightscheckedListBox.CheckedItems)
            {
                role.Rights.Add(right);
            }
            foreach (string user in UserscheckedListBox.CheckedItems)
            {
                users.Add(user);
            }
            if (isedit)
            {
                if (UserAdminUtilities.UserAdminUtility.UserPatterns.userpatternname.Contains(userpatternid))
                {
                    if (!String.IsNullOrEmpty(userpatternid))
                    {
                        int index = UserAdminUtilities.UserAdminUtility.UserPatterns.GetUserPatternIndexbyid(usereditpatternid);
                        if (index >= 0) { }
                        UserAdminUtilities.UserAdminUtility.UserPatterns[index] = UserPattern.CreateUserPattern(userpatternid, role, comments, defaultformpattern, users);
                        this.Close();
                    }
                    else
                        MessageBox.Show("Userpattern id cannot be null or empty");
                }
            }
            else
            {
                if (!UserAdminUtilities.UserAdminUtility.UserPatterns.userpatternname.Contains(userpatternid))
                {
                    if (!String.IsNullOrEmpty(userpatternid))
                    {
                        UserAdminUtilities.UserAdminUtility.UserPatterns.Add(UserPattern.CreateUserPattern(userpatternid, role, comments, defaultformpattern, users));
                        this.Close();
                    }
                    else
                        MessageBox.Show("Userpattern id cannot be null or empty");
                }
                else
                    MessageBox.Show("Userpattern with same id exists");
            }
            //this.Close();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void RolesCombo_SelectedIndexChanged(object sender, EventArgs e)
        {
            RightscheckedListBox.Items.Clear();
            Role role = Roles[RolesCombo.SelectedItem.ToString()];
            foreach (Objects.Right right in UserAdminUtilities.UserAdminUtility.Rights)
            {
                bool ischeked = false;
                foreach (string rightid in role.Rights)
                {
                    if(right.Rightid==rightid)
                    {
                        ischeked=true;
                    }
                }
                RightscheckedListBox.Items.Add(right.Rightid,ischeked);
            }
        }

       
    }
}