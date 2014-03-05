using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using EIBXMLServices;
using EIBFormDesigner.UserAdmin.Objects.Exceptions;
using System.Windows.Forms;
using System.Collections;

namespace EIBFormDesigner.UserAdmin.Objects
{
    public class UserPatternCollection:IList<UserPattern>
    {
        List<UserPattern> userpatterns;
        public List<String> userpatternname;
        public UserPatternCollection()
        {
            userpatternname = new List<string>();
            userpatterns = new List<UserPattern>();
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode userpatternsnode = doc.CreateNode(XmlNodeType.Element, "UserPatterns", null);

                foreach (UserPattern userpattern in userpatterns)
                {
                    XmlNode node = userpattern.Searialize(doc);
                    userpatternsnode.AppendChild(node);
                }

                doc.AppendChild(userpatternsnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\UserPatterns.xml");
            }
            catch
            {

            }
        }

        public static UserPatternCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\UserPatterns.xml");
                UserPatternCollection userpatterncollection = new UserPatternCollection();
                XmlNode userpatternsnode = doc.ChildNodes[0];
                foreach (XmlNode userpatternnode in userpatternsnode.ChildNodes)
                {
                    UserPattern userpattern = UserPattern.Desearialize(userpatternnode);
                    if (userpattern != null)
                    {
                        userpatterncollection.Add(userpattern);
                    }
                }
                return userpatterncollection;
            }
            catch
            {
                return new UserPatternCollection();
            }

        }

        public UserPattern GetUserPatternbyid(string id)
        {
            UserPattern up = null;
            foreach (UserPattern userpattern in userpatterns)
            {
                if (userpattern.Userpatternid == id)
                {
                    up=userpattern;
                    break;
                }
            }
            return up;
        }

        public int GetUserPatternIndexbyid(string id)
        {
            int index = 0;
            foreach (UserPattern userpattern in userpatterns)
            {
                if (userpattern.Userpatternid == id)
                {
                    break;
                }
                index++;
            }
            if (index < userpatterns.Count)
                return index;
            else
                return -1;
        }
        #region UserPatternCollection interface property

        #region IList<UserPattern> Members

        public int IndexOf(UserPattern item)
        {
            return userpatterns.IndexOf(item);
        }

        public void Insert(int index, UserPattern item)
        {
            userpatternname.Insert(index, item.Userpatternid);
            userpatterns.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            userpatternname.RemoveAt(index);
            userpatterns.RemoveAt(index);
        }

        public UserPattern this[int index]
        {
            get
            {
                return userpatterns[index];
            }
            set
            {
                userpatterns[index] = value;
            }
        }

        #endregion

        #region ICollection<UserPattern> Members

        public void Add(UserPattern item)
        {
            userpatternname.Add(item.Userpatternid);
            userpatterns.Add(item);
        }

        public void Clear()
        {
            userpatternname.Clear();
            userpatterns.Clear();
        }

        public bool Contains(UserPattern item)
        {
            if(userpatternname.Contains(item.Userpatternid))
                return userpatterns.Contains(item);
            else return false;
        }

        public void CopyTo(UserPattern[] array, int arrayIndex)
        {
            userpatterns.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return userpatterns.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(UserPattern item)
        {
            if (userpatternname.Remove(item.Userpatternid))
                return userpatterns.Remove(item);
            else return false;
        }

        #endregion

        #region IEnumerable<UserPattern> Members

        public IEnumerator<UserPattern> GetEnumerator()
        {
            return userpatterns.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return userpatterns.GetEnumerator();
        }

        #endregion

        #endregion
    }

    public class UserPattern
    {
        string userpatternid;
        Role role;
        string comments;
        string companyId;
        string defaultFormPattern;

        public string DefaultFormPattern
        {
            get { return defaultFormPattern; }
            set { defaultFormPattern = value; }
        }

        public string CompanyId
        {
            get { return companyId; }
            set { companyId = value; }
        }
        List<string> users;

        #region UserPattern Properties

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

        public Role Role
        {
            get { return role; }
            set
            {
                if (value==null)
                {
                    //throw new UserException("Role Cannot be null");
                }
                else
                {
                    role = value;
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
                    comments = "";
                    //throw new UserException("Comments cannot be null or empty.");
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

        private UserPattern()
        {
        }

        public static UserPattern CreateUserPattern(string userpatternid, Role role, string comments, string defaultFormPattern, List<string> users)
        {
            UserPattern obj = new UserPattern();
            obj.Userpatternid = userpatternid;
            obj.Role = role;
            obj.Comments = comments;
            obj.Users = users;
            obj.CompanyId = UserAdminConstants.CompanyName;
            obj.defaultFormPattern = defaultFormPattern;
            return obj;
        }

        public XmlNode Searialize(XmlDocument document)
        {
            UserPattern obj = this;
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "UserPattern", null);

            XmlAttribute userpatternidatt = doc.CreateAttribute("userpatternid");
            userpatternidatt.Value = obj.Userpatternid;
            node.Attributes.Append(userpatternidatt);

            XmlAttribute companyIdAtt = doc.CreateAttribute("CompanyId");
            companyIdAtt.Value = this.CompanyId;
            node.Attributes.Append(companyIdAtt);

            XmlAttribute defaultformpatternAtt = doc.CreateAttribute("defaultformpattern");
            defaultformpatternAtt.Value = this.DefaultFormPattern;
            node.Attributes.Append(defaultformpatternAtt);

            XmlNode commentsnode = doc.CreateNode(XmlNodeType.Text, "Comments", null);
            commentsnode.Value = obj.Comments;
            node.AppendChild(commentsnode);
            
            node.AppendChild(role.SearializeRole(doc,true));

            XmlNode usersnode = doc.CreateNode(XmlNodeType.Element, "Users", null);
            foreach (string user in obj.Users)
            {
                XmlNode usernode = doc.CreateNode(XmlNodeType.Element, "User", null);
                XmlAttribute useridatt = doc.CreateAttribute("userid");
                useridatt.Value = user;
                usernode.Attributes.Append(useridatt);
                usersnode.AppendChild(usernode);
            }

            node.AppendChild(usersnode);

            return node;
        }

        public static UserPattern Desearialize(XmlNode node)
        {
            string userpatternid, companyId, comments = " ",defaultformpattern= "";
            List<string> users = null;
            Role role = null;
            try
            {

                if (node != null)
                {
                    userpatternid = node.Attributes["userpatternid"].Value;
                    if (node.Attributes["companyId"] != null)
                    {
                        companyId = node.Attributes["CompanyId"].Value;
                    }
                    else
                    {
                        companyId = UserAdminConstants.CompanyName;
                    }

                    if (node.Attributes["defaultformpattern"] != null)
                    {
                        defaultformpattern = node.Attributes["defaultformpattern"].Value;
                    }
                    else
                    {
                        defaultformpattern = "";
                    }

                    users = new List<string>();
                    foreach (XmlNode cnode in node.ChildNodes)
                    {
                        if (cnode.NodeType == XmlNodeType.Text)
                        {
                            comments = cnode.Value;
                        }
                        else if (cnode.NodeType == XmlNodeType.Element)
                        {
                            if (cnode.Name == "Role")
                            {
                                role = Role.DesearializeRole(cnode);
                            }
                            if (cnode.Name == "Users")
                            {
                                foreach (XmlNode cusernode in cnode.ChildNodes)
                                {
                                    users.Add(cusernode.Attributes["userid"].Value);
                                }
                            }
                        }
                    }
                    return UserPattern.CreateUserPattern(userpatternid, role, comments, defaultformpattern, users);
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
