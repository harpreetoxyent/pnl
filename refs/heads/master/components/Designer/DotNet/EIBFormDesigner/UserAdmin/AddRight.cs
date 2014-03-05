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
    public partial class AddRight : Form
    {
        public AddRight(bool isedit, int rightIndex)
        {
            InitializeComponent();
            this.isedit = isedit;
            if (isedit && rightIndex > -1)
            {
                right = UserAdminUtilities.UserAdminUtility.Rights[rightIndex];
                reset();
            }
        }
        bool isedit = false;
        Right right = null;

        public void reset()
        {
            if (isedit)
            {
                rightNameText.Text = right.Rightid;
                rightNameText.Enabled = false;
                rightsDesc.Text = right.Rightdescription;
            }
            else
            {
                rightNameText.Text = right.Rightid;
                rightsDesc.Text = right.Rightdescription;
            }
        }

        private void saveUserButton_Click(object sender, EventArgs e)
        {
            string rightid, rightdescription;
            rightid = rightNameText.Text;
            rightdescription = rightsDesc.Text;
            if (!isedit)
            {
                if (!UserAdminUtilities.UserAdminUtility.Rights.rightname.Contains(rightid))
                {
                    if (!String.IsNullOrEmpty(rightid))
                    {
                        UserAdminUtilities.UserAdminUtility.Rights.Add(Objects.Right.CreateRight(rightid, null, rightdescription));
                        this.Close();
                    }
                    else
                        MessageBox.Show("Right id can not be empty");
                }
                else
                    MessageBox.Show("Right with same id exists");
            }
            else
            {
                if (UserAdminUtilities.UserAdminUtility.Rights.rightname.Contains(rightid))
                {
                    if (!String.IsNullOrEmpty(rightid))
                    {
                        right.Rightid = rightid;
                        right.Rightdescription = rightdescription;
                        this.Close();
                    }
                    else
                        MessageBox.Show("Right id can not be empty");
                }
            }
            //this.Close();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}