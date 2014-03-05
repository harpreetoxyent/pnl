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
    public class RightsCollection : IList<Right>
    {

        List<Right> rights;
        public List<String> rightname;
        public RightsCollection()
        {
            rightname = new List<string>();
            rights = new List<Right>();
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode rightsnode = doc.CreateNode(XmlNodeType.Element, "Rights", null);

                foreach (Right right in rights)
                {
                    XmlNode node = right.SearializeRight(doc);
                    rightsnode.AppendChild(node);
                }

                doc.AppendChild(rightsnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\rights.xml");
            }
            catch
            {
            }
        }

        public static RightsCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\rights.xml");
                RightsCollection rightcollection = new RightsCollection();
                XmlNode rightsnode = doc.ChildNodes[0];
                foreach (XmlNode rightnode in rightsnode.ChildNodes)
                {
                    Right right = Right.DesearializeRight(rightnode);
                    if (right != null)
                    {
                        rightcollection.Add(right);
                    }
                }
                return rightcollection;
            }
            catch
            {
                return new RightsCollection();
            }

        }

        #region RightsCollection interface property

        #region IList<Right> Members

        public int IndexOf(Right item)
        {
            return rights.IndexOf(item);
        }

        public void Insert(int index, Right item)
        {
            rightname.Insert(index, item.Rightid);
            rights.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            rightname.RemoveAt(index);
            rights.RemoveAt(index);
        }

        public Right this[int index]
        {
            get
            {
                return rights[index];
            }
            set
            {
                rights[index] = value;
            }
        }

        #endregion

        #region ICollection<Right> Members

        public void Add(Right item)
        {
            rightname.Add(item.Rightid);
            rights.Add(item);
        }

        public void Clear()
        {
            rightname.Clear();
            rights.Clear();
        }

        public bool Contains(Right item)
        {
            if (rightname.Contains(item.Rightid))
                return rights.Contains(item);
            else
                return false;
        }

        public void CopyTo(Right[] array, int arrayIndex)
        {
            rights.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return rights.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(Right item)
        {
            if(rightname.Remove(item.Rightid))
                return rights.Remove(item);
            else return false;
        }

        #endregion

        #region IEnumerable<Right> Members

        public IEnumerator<Right> GetEnumerator()
        {
            return rights.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return rights.GetEnumerator();
        }

        #endregion

        #endregion
    }




    public class Right
    {
        string rightid;
        string rightdescription;
        string companyId;

        public string CompanyId
        {
            get { return companyId; }
            set { companyId = value; }
        }
        #region Right Properties
        public string Rightid
        {
            get { return rightid; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    throw new UserException("Rightid cannot be null or empty.");
                }
                else
                {
                    rightid = value;
                }
            }
        }

        public string Rightdescription
        {
            get { return rightdescription; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    //throw new UserException("rightdescription cannot be null or empty.");
                }
                else
                {
                    rightdescription = value;
                }
            }
        }

        #endregion

        private Right()
        {

        }

        public static Right CreateRight(string rightid, string companyid, string rightdescription)
        {
            Right right = new Right();
            right.Rightid = rightid;
            right.Rightdescription = rightdescription;
            if (companyid != null)
                right.CompanyId = companyid;
            else
                right.CompanyId = UserAdminConstants.CompanyName;
            return right;
        }

        public XmlNode SearializeRight(XmlDocument document)
        {
            Right obj = this;
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "Right", null);

            XmlAttribute rightidatt = doc.CreateAttribute("rightid");
            rightidatt.Value = obj.Rightid;
            node.Attributes.Append(rightidatt);

            XmlAttribute companyIdAtt = doc.CreateAttribute("CompanyId");
            companyIdAtt.Value = this.CompanyId;
            node.Attributes.Append(companyIdAtt);

            XmlNode descnode = doc.CreateNode(XmlNodeType.Text, "Description", null);
            descnode.Value = obj.Rightdescription;
            node.AppendChild(descnode);

            return node;

        }

        public static Right DesearializeRight(XmlNode node)
        {
            string rightid, rightdescription,companyId;
            try
            {

                if (node != null)
                {
                    rightid = node.Attributes["rightid"].Value;
                    if (node.Attributes["CompanyId"] != null)
                    {
                        companyId = node.Attributes["CompanyId"].Value;
                    }
                    else
                    {
                        companyId = UserAdminConstants.CompanyName;
                    }
                    if (node.ChildNodes.Count > 0)
                    {
                        rightdescription = node.ChildNodes[0].Value;
                    }
                    else
                    {
                        rightdescription = "";
                    }
                    return Right.CreateRight(rightid, companyId, rightdescription);
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
