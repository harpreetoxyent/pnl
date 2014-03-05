using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using EIBFormDesigner.UserAdmin.Objects.Exceptions;
using System.Windows.Forms;
using EIBXMLServices;
using System.Globalization;

namespace EIBFormDesigner.UserAdmin.Objects
{
    public class RolesCollection:IList<Role>
    {

        List<Role> roles;
        public List<String> rolename;
        public RolesCollection()
        {
            rolename = new List<string>();
            roles = new List<Role>();
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode rolesnode = doc.CreateNode(XmlNodeType.Element, "Roles", null);

                foreach (Role role in roles)
                {
                    XmlNode node = role.SearializeRole(doc, false);
                    rolesnode.AppendChild(node);
                }

                doc.AppendChild(rolesnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\roles.xml");
            }
            catch
            {

            }
        }

        public static RolesCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\roles.xml");
                RolesCollection rolecollection = new RolesCollection();
                XmlNode rolesnode = doc.ChildNodes[0];
                foreach (XmlNode rolenode in rolesnode.ChildNodes)
                {
                    Role role = Role.DesearializeRole(rolenode);
                    if (role != null)
                    {
                        rolecollection.Add(role);
                    }
                }
                return rolecollection;
            }
            catch
            {
                return new RolesCollection();
            }

        }

        #region RolesCollection interface property

        #region IList<Role> Members

        public int IndexOf(Role item)
        {
            return roles.IndexOf(item);
        }

        public void Insert(int index, Role item)
        {
            rolename.Insert(index, item.Roleid);
            roles.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            rolename.RemoveAt(index);
            roles.RemoveAt(index);
        }

        public Role this[int index]
        {
            get
            {
                return roles[index];
            }
            set
            {
                roles[index] = value;
            }
        }

        #endregion

        #region ICollection<Role> Members

        public void Add(Role item)
        {
            rolename.Add(item.Roleid);
            roles.Add(item);
        }

        public void Clear()
        {
            rolename.Clear();
            roles.Clear();
        }

        public bool Contains(Role item)
        {
            if(rolename.Contains(item.Roleid))
                return roles.Contains(item);
            else
                return false;
        }

        public void CopyTo(Role[] array, int arrayIndex)
        {
            roles.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return roles.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(Role item)
        {
            if (rolename.Remove(item.Roleid))
                return roles.Remove(item);
            else
                return false;
        }

        #endregion

        #region IEnumerable<Role> Members

        public IEnumerator<Role> GetEnumerator()
        {
            return roles.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return roles.GetEnumerator();
        }

        #endregion

        #endregion
    }




    public class Role
    {
        string roleid;
        string roledescription;
        List<string> rights;
        string companyId;

        public string CompanyId
        {
            get { return companyId; }
            set { companyId = value; }
        }

        #region Role Properties
        public List<string> Rights
        {
            get { return rights; }
            set { rights = value; }
        }
        
        public string Roleid
        {
            get { return roleid; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("Roleid cannot be null or empty.");
                }
                else
                {
                    roleid = value;
                }
            }
        }
        
        public string Roledescription
        {
            get { return roledescription; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("roledescription cannot be null or empty.");
                }
                else
                {
                    roledescription = value;
                }
            }
        }
        
        #endregion

        private Role()
        {

        }

        public static Role CreateRole(string roleid,string roledescription,List<string> rights)
        {
            Role role = new Role();
            role.Roleid = roleid;
            role.Roledescription = roledescription;
            role.Rights = rights;
            role.CompanyId = UserAdminConstants.CompanyName;
            return role;
        }

        public XmlNode SearializeRole(XmlDocument document,bool isuserpattern)
        {
            Role obj = this;
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "Role", null);

            XmlAttribute roleidatt= doc.CreateAttribute("roleid");
            roleidatt.Value = obj.Roleid;
            node.Attributes.Append(roleidatt);

            XmlAttribute companyIdAtt = doc.CreateAttribute("CompanyId");
            companyIdAtt.Value = this.CompanyId;
            node.Attributes.Append(companyIdAtt);

            if (!isuserpattern)
            {
                XmlNode descnode = doc.CreateNode(XmlNodeType.Text, "Description", null);
                descnode.Value = obj.Roledescription;
                node.AppendChild(descnode);
            }

            XmlNode rightsnode = doc.CreateNode(XmlNodeType.Element, "Rights", null);
            foreach (string rightid in obj.Rights)
            {
                XmlNode rightnode= doc.CreateNode(XmlNodeType.Element, "Right", null);
                XmlAttribute rightidatt = doc.CreateAttribute("rightid");
                rightidatt.Value = rightid;
                rightnode.Attributes.Append(rightidatt);
                rightsnode.AppendChild(rightnode);
            }

            node.AppendChild(rightsnode);

            return node;

        }

        public static Role DesearializeRole(XmlNode node)
        {
            string roleid,roledescription=" ",companyId;
            List<string> rights=null;
            try
            {

            if(node!=null)
            {
                roleid= node.Attributes["roleid"].Value;
                if (node.Attributes["companyId"] != null)
                {
                    companyId = node.Attributes["CompanyId"].Value;
                }
                else
                {
                    companyId = UserAdminConstants.CompanyName;
                }
                
                foreach (XmlNode cnode in node.ChildNodes)
                {
                    if (cnode.NodeType == XmlNodeType.Text)
                    {
                        roledescription = cnode.Value;
                    }
                    if (cnode.NodeType == XmlNodeType.Element)
                    {
                        rights = new List<string>();
                        foreach (XmlNode rightnode in cnode.ChildNodes)
                        {
                            rights.Add(rightnode.Attributes["rightid"].Value);
                        }
                    }
                }
                return Role.CreateRole(roleid, roledescription,rights);
            }
            return null;
            }
            catch(Exception ex)
            {
               MessageBox.Show(ex.ToString());
                return null;
            }
        }
    }
}
