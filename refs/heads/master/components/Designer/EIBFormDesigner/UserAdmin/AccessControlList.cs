using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Xml;
using System.Windows.Forms;
using System.IO;
using System.Reflection;
using EIBXMLServices;
using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;

namespace EIBFormDesigner.UserAdmin
{
    public partial class AccessControlList : Form
    {
        static List<string> formPatterns = new List<string>();

        public static List<string> FormPatterns
        {
            get { return formPatterns; }
            set { formPatterns = value; }
        }
        public AccessControlList()
        {
            UserAdminUtilities.UserAdminUtility.Users.Clear();
            UserAdminUtilities.UserAdminUtility.Roles.Clear();
            UserAdminUtilities.UserAdminUtility.Rights.Clear();
            UserAdminUtilities.UserAdminUtility.UserPatterns.Clear();
            UserAdminUtilities.UserAdminUtility.organizations.Clear();
            InitializeComponent();
        }
        
        private void addUserButton_Click(object sender, EventArgs e)
        {
            AddUserForm addUserForm = new AddUserForm();
            addUserForm.ShowDialog();
            showValues();
        }

        private void AccessControlList_Load(object sender, EventArgs e)
        {
            showValues();

        }
        public static void createRightXml()
        {
            UserAdminUtilities.UserAdminUtility.Rights.Clear();
            UserAdminUtilities.UserAdminUtility.Rights.Add(UserAdmin.Objects.Right.CreateRight("create", null, "create"));
            UserAdminUtilities.UserAdminUtility.Rights.Add(UserAdmin.Objects.Right.CreateRight("read", null, "read"));
            UserAdminUtilities.UserAdminUtility.Rights.Add(UserAdmin.Objects.Right.CreateRight("edit", null, "edit"));
            UserAdminUtilities.UserAdminUtility.Rights.Add(UserAdmin.Objects.Right.CreateRight("delete", null, "delete"));
            UserAdminUtilities.UserAdminUtility.Rights.Serialize();
        }
        public static void loadRightXml()
        {
            UserAdminUtilities.UserAdminUtility.Rights.Clear();
            UserAdminUtilities.UserAdminUtility.Rights = RightsCollection.Desearilize();
        }

        public void showValues()
        {
            try
            {
                UserListBox.Items.Clear();
                RightlistBox.Items.Clear();
                RoleslistBox.Items.Clear();
                UserPatternslistBox.Items.Clear();
                organizationListBox.Items.Clear();
                groupListBox.Items.Clear();
                foreach (User user in UserAdminUtilities.UserAdminUtility.Users)
                {
                    UserListBox.Items.Add(user.Userid);
                }
                foreach (Objects.Right right in UserAdminUtilities.UserAdminUtility.Rights)
                {
                    RightlistBox.Items.Add(right.Rightid);
                }
                foreach (Role role in UserAdminUtilities.UserAdminUtility.Roles)
                {
                    RoleslistBox.Items.Add(role.Roleid);
                }
                foreach (UserPattern userpattern in UserAdminUtilities.UserAdminUtility.UserPatterns)
                {
                    UserPatternslistBox.Items.Add(userpattern.Userpatternid);
                }
                foreach (Organization organization in UserAdminUtilities.UserAdminUtility.organizations)
                {
                    organizationListBox.Items.Add(organization.OrgName);
                }
                foreach (Group group in UserAdminUtilities.UserAdminUtility.groups)
                {
                    groupListBox.Items.Add(group.GroupId);
                }
            }
            catch
            {

            }
        }

        private void addRole_Click(object sender, EventArgs e)
        {
            AddRole roleForm = new AddRole();
            roleForm.ShowDialog();
            showValues();
        }

        private void addUserPattern_Click(object sender, EventArgs e)
        {
            AddUserPattern userpatternform = new AddUserPattern(false,null);
            userpatternform.ShowDialog();
            showValues();
        }

        private void deleteUser_Click(object sender, EventArgs e)
        {
            foreach (string userIdToDelete in UserListBox.SelectedItems)
            {
                User userToDelete = null;
                if (userIdToDelete != null && userIdToDelete != "")
                    foreach (User user in UserAdminUtilities.UserAdminUtility.Users)
                    {
                        if (user.Userid.ToString().Trim().Equals(userIdToDelete))
                        {
                            userToDelete = user;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.Users.Remove(userToDelete);
            }
            showValues();
        }

        

        private void deleteGroup_Click(object sender, EventArgs e)
        {
            foreach (string userPatternIdToDelete in UserPatternslistBox.SelectedItems)
            {
                UserPattern userPatternToDelete = null;
                if (userPatternIdToDelete != null && userPatternIdToDelete != "")
                    foreach (UserPattern userPattern in UserAdminUtilities.UserAdminUtility.UserPatterns)
                    {
                        if (userPattern.Userpatternid.ToString().Trim().Equals(userPatternIdToDelete))
                        {
                            userPatternToDelete = userPattern;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.UserPatterns.Remove(userPatternToDelete);
            }
            showValues();

            /*UserPattern userPatternToDelete = null;
            if (UserPatternslistBox.SelectedItem != null)
            {
                string userPatternIdToDelete = UserPatternslistBox.SelectedItem.ToString().Trim();
                int indexToRemove = UserPatternslistBox.SelectedIndex;
                if (userPatternIdToDelete != null && userPatternIdToDelete != "")
                    foreach (UserPattern userPattern in UserAdminUtilities.UserAdminUtility.UserPatterns)
                    {
                        if (userPattern.Userpatternid.ToString().Trim().Equals(userPatternIdToDelete))
                        {
                            userPatternToDelete = userPattern;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.UserPatterns.Remove(userPatternToDelete);
                if (indexToRemove >= 0)
                {
                    UserPatternslistBox.Items.RemoveAt(indexToRemove);
                }
            }*/
            //DialogResult dr = MessageBox.Show("Are you sure you want to delete this row ? ", "Confirm deleting", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            //if (dr == DialogResult.Yes) //if 
            //{
            //    //user clicked the "Delete" button
            //    DataTable groupTable = adminDataSet.Tables[UserAdminConstants.GroupTable];
            //    int rowSelected = this.iRowIndex;//get the index of the row you clicked
            //    if (groupTable.Rows.Count > rowSelected)
            //    {
            //        groupTable.Rows[rowSelected].Delete(); //delete the row
            //    }
            //}//if		  

        }

        private void deleteRole_Click(object sender, EventArgs e)
        {
            foreach (string roleIdToDelete in RoleslistBox.SelectedItems)
            {
                Role roleToDelete = null;
                if (roleIdToDelete != null && roleIdToDelete != "")
                    foreach (Role role in UserAdminUtilities.UserAdminUtility.Roles)
                    {
                        if (role.Roleid.ToString().Trim().Equals(roleIdToDelete))
                        {
                            roleToDelete = role;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.Roles.Remove(roleToDelete);
            }
            showValues();
        }

        private void deleteRight_Click(object sender, EventArgs e)
        {
            foreach (string rightIdToDelete in RightlistBox.SelectedItems)
            {
                Right rightToDelete = null;
                if (rightIdToDelete != null && rightIdToDelete != "")
                    foreach (Right right in UserAdminUtilities.UserAdminUtility.Rights)
                    {
                        if (right.Rightid.ToString().Trim().Equals(rightIdToDelete))
                        {
                            rightToDelete = right;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.Rights.Remove(rightToDelete);
            }
            showValues();
        }

        
        private void addRight_Click(object sender, EventArgs e)
        {
            AddRight rightForm = new AddRight(false,-1);
            rightForm.ShowDialog();
            showValues();
        }

        private void editToolStripMenuItem_Click(object sender, EventArgs e)
        {
            /*string userpatternid= UserPatternslistBox.SelectedItem.ToString();
            AddUserPattern userpattern = new AddUserPattern(true,userpatternid);
            userpattern.ShowDialog();*/
            //showValues();
        }

        private void toolStripMenuItem1_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Not Implemented.");
            //string roleid = RoleslistBox.SelectedItem.ToString();
            //AddRole roleForm = new AddRole();
            //roleForm.ShowDialog();
            //showValues();
        }

        private void toolStripMenuItem2_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Not Implemented.");
        }

        private void toolStripMenuItem3_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Not Implemented.");
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (UserListBox.SelectedIndex >= 0)
            {
                string userpatternid = UserListBox.SelectedItem.ToString();
                AddUserForm user = new AddUserForm(true, UserListBox.SelectedIndex);
                user.ShowDialog();
                showValues();
            }
            else
            {
                MessageBox.Show("Select a User.");
            }
            //showValues();
        }

        private void addOrganization_Click(object sender, EventArgs e)
        {
            AddOrganization addOrg = new AddOrganization(false,null);
            addOrg.ShowDialog();
            showValues();
        }

        private void editOrganization_Click(object sender, EventArgs e)
        {
            if (organizationListBox.SelectedIndex >= 0)
            {
                AddOrganization addOrg = new AddOrganization(true, organizationListBox.SelectedItem.ToString());
                addOrg.ShowDialog();
            }
        }

        private void deleteOrganization_Click(object sender, EventArgs e)
        {
            foreach (string orgIdToDelete in organizationListBox.SelectedItems)
            {
                Organization orgToDelete = null;
                if (orgIdToDelete != null && orgIdToDelete != "")
                    foreach (Organization org in UserAdminUtilities.UserAdminUtility.organizations)
                    {
                        if (org.OrgName.ToString().Trim().Equals(orgIdToDelete))
                        {
                            orgToDelete = org;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.organizations.Remove(orgToDelete);
            }
            showValues();
        }
        internal static void ReadLookupCountryValues(ComboBox comboToPopulate, string fileName)
        {
            Stream sInputStream = Assembly.GetExecutingAssembly().GetManifestResourceStream("EIBFormDesigner.UserAdmin." + fileName);

            XmlTextReader xtr = null;
            try
            {
                // Initialize the XmlTextReader variable with the name of the file
                xtr = new XmlTextReader(sInputStream);
                xtr.WhitespaceHandling = WhitespaceHandling.None;

                // Scan the XML file
                while (xtr.Read())
                {
                    // every time you find an element, find out what type it is
                    switch (xtr.NodeType)
                    {
                        case XmlNodeType.Text:
                            // If you find text, put it in the combo box' list
                            comboToPopulate.Items.Add(xtr.Value);
                            break;
                    }
                }
            }
            catch (Exception ex)
            {
                Console.Write(ex.Message[0]);
            }
            finally
            {
                // Close the XmlTextReader
                if (xtr != null)
                    xtr.Close();
            }

        }

        private void addGroupBtn_Click(object sender, EventArgs e)
        {
            AddGroup addGroupForm = new AddGroup(false,null);
            addGroupForm.ShowDialog();
            showValues();
        }

        private void editGroup_Click(object sender, EventArgs e)
        {
            if (groupListBox.SelectedIndex >= 0)
            {
                AddGroup addGroupForm = new AddGroup(true,groupListBox.SelectedItem.ToString());
                addGroupForm.ShowDialog();
                showValues();
            }
            else
            {
                MessageBox.Show("Select a Group.");
            }
        }

        private void deleteGroupBtn_Click(object sender, EventArgs e)
        {
            foreach (string grpIdToDelete in groupListBox.SelectedItems)
            {
                Group grpToDelete = null;
                if (grpIdToDelete != null && grpIdToDelete != "")
                    foreach (Group grp in UserAdminUtilities.UserAdminUtility.groups)
                    {
                        if (grp.GroupId.ToString().Trim().Equals(grpIdToDelete))
                        {
                            grpToDelete = grp;
                        }
                    }
                UserAdminUtilities.UserAdminUtility.groups.Remove(grpToDelete);
            }
            showValues();
        }

        private void editRoles_Click(object sender, EventArgs e)
        {
            if (RoleslistBox.SelectedIndex >= 0)
            {
                string roleid = RoleslistBox.SelectedItem.ToString();
                AddRole role = new AddRole(true, RoleslistBox.SelectedIndex);
                role.ShowDialog();
                showValues();
            }
            else
            {
                MessageBox.Show("Select a Role.");
            }
        }

        private void EditUserPattern_Click(object sender, EventArgs e)
        {
            if (UserPatternslistBox.SelectedIndex >= 0)
            {
                string userpatternid = UserPatternslistBox.SelectedItem.ToString();
                AddUserPattern userpattern = new AddUserPattern(true, userpatternid);
                userpattern.ShowDialog();
                showValues();
            }
            else
            {
                MessageBox.Show("Select a UserPattern", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void editRight_Click(object sender, EventArgs e)
        {
            if (RightlistBox.SelectedIndex >= 0)
            {
                string rightid = RightlistBox.SelectedItem.ToString();
                AddRight right = new AddRight(true, RightlistBox.SelectedIndex);
                right.ShowDialog();
                showValues();
            }
            else
            {
                MessageBox.Show("Select a Right", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }
    }
}