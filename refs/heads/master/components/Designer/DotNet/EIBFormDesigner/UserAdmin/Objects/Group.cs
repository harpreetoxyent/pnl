using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.Windows.Forms;
using EIBXMLServices;

namespace EIBFormDesigner.UserAdmin.Objects
{
    public class GroupCollection : IList<Group>
    {

        List<Group> Groups;
        public List<String> groupname;
        public GroupCollection()
        {
            Groups = new List<Group>();
            groupname = new List<string>();
        }

        public Group GetGroupbyid(string id)
        {
            Group GroupName = null;
            foreach (Group group in Groups)
            {
                if (group.GroupId == id)
                {
                    GroupName = group;
                    break;
                }
            }
            return GroupName;
        }

        public int GetGroupIndexbyid(string id)
        {
            int index = 0;
            foreach (Group group in Groups)
            {
                if (group.GroupId == id)
                {
                    break;
                }
                index++;
            }
            if (index < Groups.Count)
                return index;
            else
                return -1;
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode Groupnode = doc.CreateNode(XmlNodeType.Element, "Groups", null);

                foreach (Group Group in Groups)
                {
                    XmlNode node = Group.SearializeGroup(doc, false);
                    Groupnode.AppendChild(node);
                }

                doc.AppendChild(Groupnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\Group.xml");
            }
            catch
            {

            }
        }

        public static GroupCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\Group.xml");
                GroupCollection Groupcollection = new GroupCollection();
                XmlNode Groupnode = doc.ChildNodes[0];
                foreach (XmlNode groupnode in Groupnode.ChildNodes)
                {
                    Group Group = Group.DesearializeGroup(groupnode);
                    if (Group != null)
                    {
                        Groupcollection.Add(Group);
                    }
                }
                return Groupcollection;
            }
            catch
            {
                return new GroupCollection();
            }

        }

        #region GroupCollection interface property

        #region IList<Group> Members

        public int IndexOf(Group item)
        {
            return Groups.IndexOf(item);
        }

        public void Insert(int index, Group item)
        {
            groupname.Insert(index, item.GroupId);
            Groups.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            groupname.RemoveAt(index);
            Groups.RemoveAt(index);
        }

        public Group this[int index]
        {
            get
            {
                return Groups[index];
            }
            set
            {
                Groups[index] = value;
            }
        }

        #endregion

        #region ICollection<Group> Members

        public void Add(Group item)
        {
            groupname.Add(item.GroupId);
            Groups.Add(item);
        }

        public void Clear()
        {
            groupname.Clear();
            Groups.Clear();
        }

        public bool Contains(Group item)
        {
            return Groups.Contains(item);
        }

        public void CopyTo(Group[] array, int arrayIndex)
        {
            Groups.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return Groups.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(Group item)
        {
            if (groupname.Remove(item.GroupId))
                return Groups.Remove(item);
            else return false;
        }

        #endregion

        #region IEnumerable<Group> Members

        public IEnumerator<Group> GetEnumerator()
        {
            return Groups.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return Groups.GetEnumerator();
        }

        #endregion

        #endregion
    }
    public class Group
    {
        string groupId;

        public string GroupId
        {
            get { return groupId; }
            set { groupId = value; }
        }
        string companyId;

        public string CompanyId
        {
            get { return companyId; }
            set { companyId = value; }
        }

        private Group()
        {

        }
        public static Group CreateGroup(string groupId,string companyId)
        {
            Group group = new Group();
            group.groupId = groupId;
            group.companyId = companyId;
            return group;
        }
        /*
    <Group>
    <orgName>Deafult Org</orgName>
    <ParentOrgname>GIP</ParentOrgname>
    <companyId>GIP</companyId>
    <countryId>Germany</countryId>
    <regionId>Germany</regionId>
    <statusId>Active</statusId>
  </Group>*/
        public XmlNode SearializeGroup(XmlDocument document, bool isuserpattern)
        {
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "Group", null);

            XmlAttribute groupIdAtt = doc.CreateAttribute("GroupId");
            groupIdAtt.Value = this.GroupId;
            node.Attributes.Append(groupIdAtt);

            XmlAttribute companyIdAtt = doc.CreateAttribute("CompanyId");
            companyIdAtt.Value = this.CompanyId;
            node.Attributes.Append(companyIdAtt);

            return node;

        }

        public static Group DesearializeGroup(XmlNode node)
        {
            string groupId, companyId = "";
            try
            {

                if (node != null)
                {
                    groupId = node.Attributes["GroupId"].Value;
                    companyId = node.Attributes["CompanyId"].Value;
                    return Group.CreateGroup(groupId, companyId);
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
