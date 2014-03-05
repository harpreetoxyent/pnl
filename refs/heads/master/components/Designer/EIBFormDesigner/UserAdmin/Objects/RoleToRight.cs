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
    public class RoleRightMappingCollection : IList<RoleRightMapping>
    {

        List<RoleRightMapping> rolerights;
        public RoleRightMappingCollection()
        {
            rolerights = new List<RoleRightMapping>();
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode rolerightsnode = doc.CreateNode(XmlNodeType.Element, "RoleToRights", null);

                foreach (RoleRightMapping roleright in rolerights)
                {
                    XmlNode node = roleright.SearializeRoleRightMapping(doc);
                    rolerightsnode.AppendChild(node);
                }

                doc.AppendChild(rolerightsnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\RoleToRights.xml");
            }
            catch
            {

            }
        }

        public static RoleRightMappingCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\RoleToRights.xml");
                RoleRightMappingCollection rolerightmappingcollection = new RoleRightMappingCollection();
                XmlNode rolerightsnode = doc.ChildNodes[0];
                foreach (XmlNode rolerightnode in rolerightsnode.ChildNodes)
                {
                    RoleRightMapping rolerightmapping = RoleRightMapping.DesearializeRoleRightMapping(rolerightnode);
                    if (rolerightmapping != null)
                    {
                        rolerightmappingcollection.Add(rolerightmapping);
                    }
                }
                return rolerightmappingcollection;
            }
            catch
            {
                return new RoleRightMappingCollection();
            }

        }

        #region RoleRightMappingCollection interface property

        #region IList<RoleRightMapping> Members

        public int IndexOf(RoleRightMapping item)
        {
            return rolerights.IndexOf(item);
        }

        public void Insert(int index, RoleRightMapping item)
        {
            rolerights.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            rolerights.RemoveAt(index);
        }

        public RoleRightMapping this[int index]
        {
            get
            {
                return rolerights[index];
            }
            set
            {
                rolerights[index] = value;
            }
        }

        #endregion

        #region ICollection<RoleRightMapping> Members

        public void Add(RoleRightMapping item)
        {
            rolerights.Add(item);
        }

        public void Clear()
        {
            rolerights.Clear();
        }

        public bool Contains(RoleRightMapping item)
        {
            return rolerights.Contains(item);
        }

        public void CopyTo(RoleRightMapping[] array, int arrayIndex)
        {
            rolerights.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return rolerights.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(RoleRightMapping item)
        {
            return rolerights.Remove(item);
        }

        #endregion

        #region IEnumerable<RoleRightMapping> Members

        public IEnumerator<RoleRightMapping> GetEnumerator()
        {
            return rolerights.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return rolerights.GetEnumerator();
        }

        #endregion

        #endregion
    }




    public class RoleRightMapping
    {
        string userpatternid;
        string roleid;
        string rightid;
        string comments;
        List<string> users;
        #region RoleRightMapping Properties

        public string Userpatternid
        {
            get { return userpatternid; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("userpatternid cannot be null or empty.");
                }
                else
                {
                    userpatternid = value;
                }
            }
        }

        public string Roleid
        {
            get { return roleid; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("Rightid cannot be null or empty.");
                }
                else
                {
                    roleid = value;
                }
            }
        }

        public string Rightid
        {
            get { return rightid; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("Rightid cannot be null or empty.");
                }
                else
                {
                    rightid = value;
                }
            }
        }

        public string Comments
        {
            get { return comments; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("rightdescription cannot be null or empty.");
                }
                else
                {
                    comments = value;
                }
            }
        }

        public List<string> Users
        {
            get { return users; }
            set
            {
                users = value;
            }
        }

        #endregion

        private RoleRightMapping()
        {

        }

        public static RoleRightMapping NewRoleRightMapping(string userpatternid,string roleid,string rightid, string comments,List<string> users)
        {
            RoleRightMapping roleright = new RoleRightMapping();
            roleright.Userpatternid = userpatternid;
            roleright.Roleid = roleid;
            roleright.Rightid = rightid;
            roleright.comments = comments;
            roleright.Users = users;
            return roleright;
        }

        public XmlNode SearializeRoleRightMapping(XmlDocument document)
        {
            RoleRightMapping obj = this;
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "RoleToRight", null);

            XmlAttribute userpatternidatt = doc.CreateAttribute("userpatternid");
            userpatternidatt.Value = obj.Userpatternid;
            node.Attributes.Append(userpatternidatt);

            XmlAttribute roleidatt = doc.CreateAttribute("roleid");
            roleidatt.Value = obj.Roleid;
            node.Attributes.Append(roleidatt);

            XmlAttribute rightidatt = doc.CreateAttribute("rightid");
            rightidatt.Value = obj.Rightid;
            node.Attributes.Append(rightidatt);

            XmlNode commentsnode = doc.CreateNode(XmlNodeType.Text, "Comments", null);
            commentsnode.Value = obj.Comments;
            node.AppendChild(commentsnode);

            XmlNode usersnode = doc.CreateNode(XmlNodeType.Element, "Users", null);
            foreach (string user in obj.Users)
            {
                XmlNode usernode= doc.CreateNode(XmlNodeType.Element, "User", null);
                XmlAttribute useridatt= doc.CreateAttribute("userid");
                useridatt.Value = user;
                usernode.Attributes.Append(useridatt);
                usersnode.AppendChild(usernode);
            }

            node.AppendChild(usersnode);

            return node;

        }

        public static RoleRightMapping DesearializeRoleRightMapping(XmlNode node)
        {
            string userpatternid,roleid,rightid, comments=null;
            List<string> users=null;
            try
            {

                if (node != null)
                {
                    userpatternid = node.Attributes["userpatternid"].Value;
                    roleid = node.Attributes["roleid"].Value;
                    rightid = node.Attributes["rightid"].Value;
                    users=new List<string>();
                    foreach (XmlNode cnode in node.ChildNodes)
                    {
                        if (cnode.NodeType == XmlNodeType.Text)
                        {
                            comments = cnode.Value;
                        }
                        else if (cnode.NodeType == XmlNodeType.Element)
                        {
                            foreach (XmlNode cusernode in cnode.ChildNodes)
                            {
                                users.Add(cusernode.Attributes["userid"].Value);
                            }
                        }
                    }
                    return RoleRightMapping.NewRoleRightMapping(userpatternid,roleid,rightid, comments,users);
                }
                return null;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
                return null;
            }
        }
    }
}
