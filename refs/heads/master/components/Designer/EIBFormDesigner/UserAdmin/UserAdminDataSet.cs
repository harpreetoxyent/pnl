using System;
using System.Collections.Generic;
using System.Text;
using System.Data;

namespace EIBFormDesigner.UserAdmin
{
    public class UserAdminDataSet : DataSet
    {
        DataTable userTable = new DataTable(UserAdminConstants.UserTable);
        DataTable companyTable = new DataTable(UserAdminConstants.CompanyTable);
        DataTable organizationTable = new DataTable(UserAdminConstants.OrgTable);
        DataTable groupsTable = new DataTable(UserAdminConstants.GroupTable);
        DataTable rolesTable = new DataTable(UserAdminConstants.RolesTable);
        DataTable rightsTable = new DataTable(UserAdminConstants.RightsTable);
        DataTable roleRightsTable = new DataTable(UserAdminConstants.RoleRightsTable);
        DataTable locationTable = new DataTable(UserAdminConstants.LocationTable);
        //DataTable contactTable = new DataTable(UserAdminConstants.ContactTable);

        public void initCompanyTable()
        {
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String));
            DataColumn portalURL = new DataColumn("portalURL", typeof(String));
            companyTable.Columns.Add(companyId);
            companyTable.Columns.Add(portalURL);
        }
        public void initContactTable()
        {
        }
        public void initUserTable()
        {
            DataColumn userId = new DataColumn(UserAdminConstants.UserId, typeof(String));
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String));
            DataColumn contactId = new DataColumn(UserAdminConstants.ContactId, typeof(String));
            DataColumn password = new DataColumn(UserAdminConstants.Password, typeof(String));
            DataColumn emailAddress = new DataColumn(UserAdminConstants.EmailAddress, typeof(String));
            DataColumn roleId = new DataColumn(UserAdminConstants.RoleId, typeof(String));
            DataColumn active = new DataColumn(UserAdminConstants.UserActive, typeof(Int16));
            DataColumn orgName = new DataColumn(UserAdminConstants.OrgName, typeof(String));
            DataColumn groupId = new DataColumn(UserAdminConstants.GroupId, typeof(String));
            DataColumn firstName = new DataColumn(UserAdminConstants.FirstName, typeof(String));
            DataColumn middleName = new DataColumn(UserAdminConstants.MiddleName, typeof(String));
            DataColumn lastName = new DataColumn(UserAdminConstants.LastName, typeof(String));
            DataColumn nickName = new DataColumn(UserAdminConstants.NickName, typeof(String));
            DataColumn birthDay = new DataColumn(UserAdminConstants.BirthDay, typeof(String));
            DataColumn gender = new DataColumn(UserAdminConstants.Gender, typeof(String));
            userTable.Columns.Add(userId);
            userTable.Columns.Add(companyId);
            userTable.Columns.Add(contactId);
            userTable.Columns.Add(password);
            userTable.Columns.Add(emailAddress);
            userTable.Columns.Add(active);
            userTable.Columns.Add(roleId);
            userTable.Columns.Add(groupId);
            userTable.Columns.Add(orgName);
            userTable.Columns.Add(firstName);
            userTable.Columns.Add(middleName);
            userTable.Columns.Add(lastName);
            userTable.Columns.Add(nickName);
            userTable.Columns.Add(birthDay);
            userTable.Columns.Add(gender);

        }
        public void initGroupTable()
        {
            DataColumn groupId = new DataColumn(UserAdminConstants.GroupId, typeof(String));
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String));
            groupsTable.Columns.Add(groupId);
            groupsTable.Columns.Add(companyId);

        }

        public void initRoleRightTable()
        {
            DataColumn roleId = new DataColumn(UserAdminConstants.RoleId, typeof(String));
            DataColumn rightId = new DataColumn(UserAdminConstants.RightsId, typeof(String));
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String));
            roleRightsTable.Columns.Add(rightId);
            roleRightsTable.Columns.Add(roleId);
            roleRightsTable.Columns.Add(companyId);

        }
        public void initRolesTable()
        {
            DataColumn roleId = new DataColumn(UserAdminConstants.RoleId, typeof(String));
            DataColumn mappedURL = new DataColumn(UserAdminConstants.MappedURL, typeof(String));
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String));
            rolesTable.Columns.Add(roleId);
            rolesTable.Columns.Add(mappedURL);
            rolesTable.Columns.Add(companyId);
        }
        public void initRightsTable()
        {
            DataColumn rightId = new DataColumn(UserAdminConstants.RightsId, typeof(String));
            DataColumn mappedURL = new DataColumn(UserAdminConstants.MappedURL, typeof(String));
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String));
            rightsTable.Columns.Add(rightId);
            rightsTable.Columns.Add(mappedURL);
            rightsTable.Columns.Add(companyId);
        }
        public void initOrgTable()
        {
            DataColumn name = new DataColumn(UserAdminConstants.OrgName, typeof(String));
            DataColumn orgCountryId = new DataColumn(UserAdminConstants.OrgCountryId, typeof(String));
            
            DataColumn companyId = new DataColumn(UserAdminConstants.CompanyId, typeof(String)); 
            DataColumn parentOrgName = new DataColumn(UserAdminConstants.ParentOrgName, typeof(String));           
            DataColumn orgRegionId = new DataColumn(UserAdminConstants.OrgRegionId, typeof(String));
            DataColumn orgStatusId = new DataColumn(UserAdminConstants.OrgStatusId, typeof(String));
            organizationTable.Columns.Add(name);
            organizationTable.Columns.Add(parentOrgName);
            organizationTable.Columns.Add(companyId);
            organizationTable.Columns.Add(orgCountryId);
            organizationTable.Columns.Add(orgRegionId);
            organizationTable.Columns.Add(orgStatusId);
        }
        public UserAdminDataSet():base("UserAdmin")
        {
            initCompanyTable();
            initUserTable();
            initGroupTable();
            initRolesTable();
            initRightsTable();
            initRoleRightTable();
            initContactTable();
            initOrgTable();
            this.Tables.Add(userTable);
            this.Tables.Add(companyTable);
            this.Tables.Add(organizationTable);
            this.Tables.Add(groupsTable);
            this.Tables.Add(rolesTable);
            this.Tables.Add(rightsTable);
            this.Tables.Add(locationTable);
            this.Tables.Add(roleRightsTable);
        }
    }
}
