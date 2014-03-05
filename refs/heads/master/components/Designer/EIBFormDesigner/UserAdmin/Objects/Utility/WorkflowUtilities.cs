using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
using System.Drawing;
using System.Xml;
using System.Data;
using EIBXMLServices;
using EIBFormDesigner.UserAdmin.Objects;

namespace EIBFormDesigner.UserAdmin.Utility
{
    public class UserAdminUtilities
    {
        public UsersCollection Users;

        public RolesCollection Roles;
        public RightsCollection Rights;
        public UserPatternCollection UserPatterns;
        public OrganizationCollection organizations;
        public GroupCollection groups;
        static UserAdminUtilities _self = null;
        private UserAdminUtilities()
        {
            Users = new UsersCollection();
            Roles = new RolesCollection();
            Rights = new RightsCollection();
            UserPatterns = new UserPatternCollection();
            organizations = new OrganizationCollection();
            groups = new GroupCollection();
            Desearilize();
        }
        public static UserAdminUtilities UserAdminUtility
        {
            get
            {
                if (_self == null)
                {
                    _self = new UserAdminUtilities();
                }
                return _self;
            }
        }
        public static void Refresh()
        {
            _self = new UserAdminUtilities();
        }

        public void Searilize()
        {
            try
            {
                Users.Serialize();
                Rights.Serialize();
                Roles.Serialize();
                UserPatterns.Serialize();
                organizations.Serialize();
                groups.Serialize();
            }
            catch
            {

            }
            
        }

        public void Desearilize()
        {
            try
            {
                Users = UsersCollection.Desearilize();
                Rights = RightsCollection.Desearilize();
                Roles = RolesCollection.Desearilize();
                UserPatterns = UserPatternCollection.Desearilize();
                organizations = OrganizationCollection.Desearilize();
                groups = GroupCollection.Desearilize();
            }
            catch
            {

            }
        }

    }
}

