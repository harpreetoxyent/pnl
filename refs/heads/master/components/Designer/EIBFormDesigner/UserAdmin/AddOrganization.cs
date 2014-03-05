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
    public partial class AddOrganization : Form
    {
        bool isedit = false;
        string orgEditId;
        public AddOrganization(bool isedit,string orgid)
        {
            InitializeComponent();
            string fileName = "LookUpCountryValues.xml";
            AccessControlList.ReadLookupCountryValues(this.countryCombo, fileName);
            AccessControlList.ReadLookupCountryValues(this.regionCombo, fileName);
            this.isedit = isedit;
            if (isedit && orgid != null)
            {
                RestoreOrganizationSettings(orgid);
                orgEditId = orgid;
                orgName.Enabled = false;
            }
            else
            {
                orgName.Enabled = true;
            }
        }

        public void RestoreOrganizationSettings(string orgid)
        {
            Organization org =  UserAdminUtilities.UserAdminUtility.organizations.GetOrganizationbyid(orgid);
            orgName.Text = org.OrgName;
            statusCombo.SelectedItem = org.StatusId;
            countryCombo.SelectedItem = org.CountryId;
            regionCombo.SelectedItem = org.RegionId;
        }



        private void saveOrgButton_Click(object sender, EventArgs e)
        {
            string orgName, parentOrgName, companyId, countryId, regionId, statusId = "";
            try
            {
                orgName = this.orgName.Text;
                parentOrgName = UserAdminConstants.CompanyId;
                companyId = UserAdminConstants.CompanyId;
                if (this.countryCombo.SelectedItem != null)
                    countryId = this.countryCombo.SelectedItem.ToString();
                else
                    countryId = "";
                if (this.regionCombo.SelectedItem != null)
                    regionId = this.regionCombo.SelectedItem.ToString();
                else 
                    regionId = "";
                if (this.statusCombo.SelectedItem != null)
                    statusId = this.statusCombo.SelectedItem.ToString();
                else
                    statusId = "";
                if (isedit && orgEditId != null)
                {
                    if (UserAdminUtilities.UserAdminUtility.organizations.organizationname.Contains(orgName))
                    {
                        if (!String.IsNullOrEmpty(orgName))
                        {
                            int orgIndex = UserAdminUtilities.UserAdminUtility.organizations.GetOrganizationIndexbyid(orgEditId);
                            if (orgIndex >= 0)
                            {
                                UserAdminUtilities.UserAdminUtility.organizations[orgIndex] = Organization.CreateOrganization(orgName, parentOrgName, companyId, countryId, regionId, statusId);
                                this.Close();
                            }
                        }
                        else
                            MessageBox.Show("Organization name cannot be null or empty");
                    }
                }
                else
                {
                    if (!UserAdminUtilities.UserAdminUtility.organizations.organizationname.Contains(orgName))
                    {
                        if (!String.IsNullOrEmpty(orgName))
                        {
                            UserAdminUtilities.UserAdminUtility.organizations.Add(Organization.CreateOrganization(orgName, parentOrgName, companyId, countryId, regionId, statusId));
                            this.Close();
                        }
                        else
                            MessageBox.Show("Organization name cannot be null or empty");
                    }
                    else
                        MessageBox.Show("Organization with same name exists");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
            //this.Close();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void AddOrganization_Load(object sender, EventArgs e)
        {
            
        }

    }
}