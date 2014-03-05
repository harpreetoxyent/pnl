using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.UserAdmin.Objects;
using EIBFormDesigner.UserAdmin.Utility;
using System.Text.RegularExpressions;

namespace EIBFormDesigner.UserAdmin
{
    public partial class AddUserForm : Form
    {
        public AddUserForm()
        {
            InitializeComponent();
            string fileName = "LookUpCountryValues.xml";
            AccessControlList.ReadLookupCountryValues(this.cityCombo, fileName);
            AccessControlList.ReadLookupCountryValues(this.stateCombo, fileName);
            this.birthday.MaxDate = DateTime.Now;
            foreach (Organization organization in UserAdminUtilities.UserAdminUtility.organizations)
            {
                organizationCombobox.Items.Add(organization.OrgName);
            }
            foreach (UserAdmin.Objects.Group group in UserAdminUtilities.UserAdminUtility.groups)
            {
                groupCombobox.Items.Add(group.GroupId);
            }
            reset();
        }
        public AddUserForm(bool isEditing,int usrIndex):this()
        {
            if (usrIndex != -1)
            {
                isEdited = isEditing;
                User editUser = UserAdminUtilities.UserAdminUtility.Users[usrIndex];
                if (editUser != null)
                {
                    currentUserIndex = usrIndex;
                    currentUser = editUser;
                }
                reset();
            }
        }
        int currentUserIndex = -1;
        User currentUser = null;
        public void reset()
        {
            if (isEdited)
            {
                firstName.Text = currentUser.Personalinfo.Firstname;
                middleName.Text = currentUser.Personalinfo.Middlename;
                lastName.Text = currentUser.Personalinfo.Lastname;
                emailAddress.Text = currentUser.Personalinfo.Emailid;
                genderCombo.SelectedItem = currentUser.Personalinfo.Gender;
                prefixCombo.SelectedIndex= Int32.Parse(currentUser.Personalinfo.Preffix);
                suffixCombo.SelectedIndex = Int32.Parse(currentUser.Personalinfo.Suffix);
                employeeno.Text = currentUser.Personalinfo.EmployeeNo;
                categoryCombo.SelectedIndex = Int32.Parse(currentUser.Personalinfo.Category);
                chkActive.Checked = currentUser.Personalinfo.IsActive;
                address1.Text = currentUser.Personalinfo.Address1;
                address2.Text = currentUser.Personalinfo.Address2;
                cityCombo.SelectedItem = currentUser.Personalinfo.City;
                stateCombo.SelectedItem = currentUser.Personalinfo.State;
                telephoneno.Text = currentUser.Personalinfo.PhoneNo.ToString();
                zipCode.Text = currentUser.Personalinfo.Zipcode.ToString();
                upin.Text = currentUser.Personalinfo.Upin;
                npin.Text = currentUser.Personalinfo.Npin;

                userId.Text = currentUser.Userid;
                userId.Enabled = false;
                password.Text = currentUser.Password;
                organizationCombobox.SelectedItem = currentUser.OrgName;
                groupCombobox.SelectedItem = currentUser.GroupId;
            }
            else
            {
                firstName.Text = "";
                middleName.Text = "";
                lastName.Text = "";
                emailAddress.Text = "";
                genderCombo.SelectedItem = "";
                userId.Text = "";
                password.Text = "";
                organizationCombobox.SelectedIndex = -1;
                groupCombobox.SelectedIndex = -1;
                prefixCombo.SelectedIndex = -1;
                suffixCombo.SelectedIndex = -1;
                employeeno.Text = "";
                categoryCombo.SelectedIndex = -1;
                chkActive.Checked = false;
                address1.Text = "";
                address2.Text = "";
                cityCombo.SelectedIndex = -1;
                stateCombo.SelectedIndex = -1;
                telephoneno.Text = "";
                zipCode.Text = "";
                upin.Text = "";
                npin.Text = "";
            }
        }
        bool isEdited = false;
        private void saveUserButton_Click(object sender, EventArgs e)
        {
            try
            {
                userId.Focus();
                if (!this.Validate())
                {
                    return;
                }
                password.Focus();
                if (!this.Validate())
                {
                    return;
                }

                string fname, mname, lname, emailid, gender = "Male";
                DateTime Birthday;
                string Preffix = "";
                string Suffix = "";
                string empno;
                string Category = "";
                bool isActive;
                string Address1;
                string Address2;
                string City = "";
                string State = "";
                int Zipcode = '\0';
                int phoneNo = '\0';
                string Upin;
                string Npin;
                /*
                firstName.Focus();
                if (!this.Validate())
                {
                    return;
                }
                lastName.Focus();
                if (!this.Validate())
                {
                    return;
                }
                emailAddress.Focus();
                if (!this.Validate())
                {
                    return;
                }
                */
                //if(!string.IsNullOrEmpty(firstName.Text))
                    fname = firstName.Text;
                //if (!string.IsNullOrEmpty(middleName.Text)) 
                    mname = middleName.Text;
                //if (!string.IsNullOrEmpty(lastName.Text)) 
                    lname = lastName.Text;
                if (!string.IsNullOrEmpty(emailAddress.Text))
                    if (IsEmail(emailAddress.Text))
                        emailid = emailAddress.Text;
                    else
                    {
                        emailid = "";
                        MessageBox.Show("Invalid email address");
                    }
                else
                    emailid = "";
                if(prefixCombo.SelectedIndex >= 0)
                {
                    Preffix = prefixCombo.SelectedIndex.ToString();
                }
                if (suffixCombo.SelectedIndex >= 0)
                {
                    Suffix = suffixCombo.SelectedIndex.ToString();
                }
                empno = employeeno.Text;
                if (categoryCombo.SelectedIndex >= 0)
                {
                    Category = categoryCombo.SelectedIndex.ToString();
                }
                isActive = chkActive.Checked;
                Address1 = address1.Text;
                Address2 = address2.Text;
                if (cityCombo.SelectedIndex >= 0)
                {
                    City = cityCombo.SelectedItem.ToString();
                }
                if (stateCombo.SelectedIndex >= 0)
                {
                    State = stateCombo.SelectedItem.ToString();
                }
                if (!string.IsNullOrEmpty(telephoneno.Text) && (!Int32.TryParse(telephoneno.Text, out phoneNo)))
                {
                    MessageBox.Show("Phone No should be integer.");
                    return;
                }

                if (!string.IsNullOrEmpty(zipCode.Text) && !Int32.TryParse(zipCode.Text, out Zipcode))
                {
                    MessageBox.Show("Zipcode should be integer.");
                    return;
                }

                Npin = npin.Text;
                Upin = upin.Text;
                
                if (genderCombo.SelectedIndex >= 0)
                {
                    gender = genderCombo.SelectedItem.ToString();
                }
                Birthday = birthday.Value;
                PersonalInfo pi = new PersonalInfo(fname, mname, lname, emailid, birthday.Value, gender, Preffix, Suffix, empno, Category, isActive, Address1, Address2, City, State, Zipcode, phoneNo, Upin, Npin);
                string orgName = "",groupid = "";
                if (organizationCombobox.SelectedIndex >= 0)
                {
                    orgName = organizationCombobox.SelectedItem.ToString();
                }
                else
                {
                    MessageBox.Show("Select Organization.");
                    return;
                }
                if(groupCombobox.SelectedIndex >=0)
                {
                    groupid = groupCombobox.SelectedItem.ToString();
                }
                else
                {
                    MessageBox.Show("Select Group.");
                    return;
                }
                if (isEdited)
                {
                    //Checking if a user with same name exists
                    if (UserAdminUtilities.UserAdminUtility.Users.username.Contains(userId.Text))
                    {
                        UserAdminUtilities.UserAdminUtility.Users.Remove(currentUser);
                        UserAdminUtilities.UserAdminUtility.Users.Insert(currentUserIndex, User.CreateUser(userId.Text, pi, password.Text, orgName, groupid));
                        isSave = true;
                        this.Close();
                    }
                }
                else
                {
                    //Checking if a user with same name exists
                    if (!UserAdminUtilities.UserAdminUtility.Users.username.Contains(userId.Text))
                    {
                        UserAdminUtilities.UserAdminUtility.Users.Add(User.CreateUser(userId.Text, pi, password.Text, orgName, groupid));
                        isSave = true;
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show("User with the same name already exists");
                    }
                }
                //isSave = true;
                //this.Close();                
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        bool valid = false;
        bool isSave = false;
        private void cancelButton_Click(object sender, EventArgs e)
        {
            //this.Close();
            //this.OnFormClosing(new FormClosingEventArgs(CloseReason.UserClosing, false));
        }

        public static bool IsEmail(string Email)
        {
            string strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" +
                @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" +
                @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
            Regex re = new Regex(strRegex);
            if (re.IsMatch(Email))
                return (true);
            else
                return (false);
        } 

        private void AddUserForm_Load(object sender, EventArgs e)
        {
            genderCombo.SelectedIndex = 0;
        }

        private void userId_Validating(object sender, CancelEventArgs e)
        {
            if (string.IsNullOrEmpty(userId.Text))
            {
                errorProvider1.SetError(userId, "User Id is Null or Empty.");
                e.Cancel = true;
            }
            else
            {
                errorProvider1.SetError(userId, string.Empty);
            }
        }

        private void password_Validating(object sender, CancelEventArgs e)
        {
            if (string.IsNullOrEmpty(password.Text))
            {
                errorProvider1.SetError(password, "Password is Null or Empty.");
                e.Cancel = true;
            }
            else
            {
                errorProvider1.SetError(password, string.Empty);
            }
        }

        private void firstName_Validating(object sender, CancelEventArgs e)
        {
            if (string.IsNullOrEmpty(firstName.Text))
            {
                errorProvider1.SetError(firstName, "firstName is Null or Empty.");
                e.Cancel = true;
            }
            else
            {
                errorProvider1.SetError(firstName, string.Empty);
            }
        }

        private void lastName_Validating(object sender, CancelEventArgs e)
        {
            if (string.IsNullOrEmpty(lastName.Text))
            {
                errorProvider1.SetError(lastName, "lastName is Null or Empty.");
                e.Cancel = true;
            }
            else
            {
                errorProvider1.SetError(lastName, string.Empty);
            }
        }

        private void emailAddress_Validating(object sender, CancelEventArgs e)
        {
            if (string.IsNullOrEmpty(emailAddress.Text))
            {
                errorProvider1.SetError(emailAddress, "emailAddress is Null or Empty.");
                e.Cancel = true;
            }
            else
            {
                if (!IsEmail(emailAddress.Text))
                {
                    errorProvider1.SetError(emailAddress, "emailAddress is Invalid.");
                    e.Cancel = true;
                }
                else
                {
                    errorProvider1.SetError(firstName, string.Empty);
                }
            }
        }

        private void AddUserForm_Validating(object sender, CancelEventArgs e)
        {

        }

        private void cancelButton_MouseClick(object sender, MouseEventArgs e)
        {

        }

        private void AddUserForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (isSave)
            {
                return;
            }
            if (MessageBox.Show("Do you want to close the window?", "Alert", MessageBoxButtons.YesNo) == DialogResult.No)
            {
                e.Cancel = true;
            }
        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void saveUserButton_Validating(object sender, CancelEventArgs e)
        {

        }

        private void cancelButton_MouseDown(object sender, MouseEventArgs e)
        {
            this.Close();
        }
    }
}