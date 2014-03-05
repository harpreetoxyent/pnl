using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.Windows.Forms;
using EIBXMLServices;

namespace EIBFormDesigner.UserAdmin.Objects
{
    public class OrganizationCollection : IList<Organization>
    {

        List<Organization> Organizations;
        public List<String> organizationname;
        public OrganizationCollection()
        {
            organizationname = new List<string>();
            Organizations = new List<Organization>();
        }

        public Organization GetOrganizationbyid(string id)
        {
            Organization orgName = null;
            foreach (Organization Org in Organizations)
            {
                if (Org.OrgName == id)
                {
                    orgName = Org;
                    break;
                }
            }
            return orgName;
        }

        public int GetOrganizationIndexbyid(string id)
        {
            int index = 0;
            foreach (Organization Org in Organizations)
            {
                if (Org.OrgName == id)
                {
                    break;
                }
                index++;
            }
            if (index < Organizations.Count)
                return index;
            else
                return -1;
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode Organizationnode = doc.CreateNode(XmlNodeType.Element, "Organizations", null);

                foreach (Organization organization in Organizations)
                {
                    XmlNode node = organization.SearializeOrganization(doc, false);
                    Organizationnode.AppendChild(node);
                }

                doc.AppendChild(Organizationnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\Organization.xml");
            }
            catch
            {

            }
        }

        public static OrganizationCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\Organization.xml");
                OrganizationCollection Organizationcollection = new OrganizationCollection();
                XmlNode Organizationnode = doc.ChildNodes[0];
                foreach (XmlNode organizationnode in Organizationnode.ChildNodes)
                {
                    Organization organization = Organization.DesearializeOrganization(organizationnode);
                    if (organization != null)
                    {
                        Organizationcollection.Add(organization);
                    }
                }
                return Organizationcollection;
            }
            catch
            {
                return new OrganizationCollection();
            }

        }

        #region OrganizationCollection interface property

        #region IList<Organization> Members

        public int IndexOf(Organization item)
        {
            return Organizations.IndexOf(item);
        }

        public void Insert(int index, Organization item)
        {
            organizationname.Insert(index, item.OrgName);
            Organizations.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            organizationname.RemoveAt(index);
            Organizations.RemoveAt(index);
        }

        public Organization this[int index]
        {
            get
            {
                return Organizations[index];
            }
            set
            {
                Organizations[index] = value;
            }
        }

        #endregion

        #region ICollection<Organization> Members

        public void Add(Organization item)
        {
            organizationname.Add(item.OrgName);
            Organizations.Add(item);
        }

        public void Clear()
        {
            organizationname.Clear();
            Organizations.Clear();
        }

        public bool Contains(Organization item)
        {
            if(organizationname.Contains(item.OrgName))
                return Organizations.Contains(item);
            else return false;
        }

        public void CopyTo(Organization[] array, int arrayIndex)
        {
            Organizations.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return Organizations.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(Organization item)
        {
            if (organizationname.Remove(item.OrgName))
                return Organizations.Remove(item);
            else return false;
        }

        #endregion

        #region IEnumerable<Organization> Members

        public IEnumerator<Organization> GetEnumerator()
        {
            return Organizations.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return Organizations.GetEnumerator();
        }

        #endregion

        #endregion
    }
    public class Organization
    {
        string orgName;

        public string OrgName
        {
            get { return orgName; }
            set { orgName = value; }
        }
        string parentOrgName;

        public string ParentOrgName
        {
            get { return parentOrgName; }
            set { parentOrgName = value; }
        }
        string companyId;

        public string CompanyId
        {
            get { return companyId; }
            set { companyId = value; }
        }
        string countryId;

        public string CountryId
        {
            get { return countryId; }
            set { countryId = value; }
        }
        string regionId;

        public string RegionId
        {
            get { return regionId; }
            set { regionId = value; }
        }
        string statusId;

        public string StatusId
        {
            get { return statusId; }
            set { statusId = value; }
        }

        private Organization()
        {

        }
        public static Organization CreateOrganization(string orgName, string parentOrgName, string companyId, string countryId, string regionId, string statusId)
        {
            Organization org = new Organization();
            org.orgName = orgName;
            org.parentOrgName = parentOrgName;
            org.companyId = companyId;
            org.countryId = countryId;
            org.regionId = regionId;
            org.statusId = statusId;
            return org;
        }
        /*
    <Organization>
    <orgName>Deafult Org</orgName>
    <ParentOrgname>GIP</ParentOrgname>
    <companyId>GIP</companyId>
    <countryId>Germany</countryId>
    <regionId>Germany</regionId>
    <statusId>Active</statusId>
  </Organization>*/
        public XmlNode SearializeOrganization(XmlDocument document, bool isuserpattern)
        {
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "Organization", null);

            XmlAttribute orgNameAtt = doc.CreateAttribute("OrgName");
            orgNameAtt.Value = this.OrgName;
            node.Attributes.Append(orgNameAtt);

            XmlAttribute parentOrgNameAtt = doc.CreateAttribute("ParentOrgName");
            parentOrgNameAtt.Value = this.ParentOrgName;
            node.Attributes.Append(parentOrgNameAtt);

            XmlAttribute companyIdAtt = doc.CreateAttribute("CompanyId");
            companyIdAtt.Value = this.CompanyId;
            node.Attributes.Append(companyIdAtt);

            XmlAttribute countryIdAtt = doc.CreateAttribute("CountryId");
            countryIdAtt.Value = this.CountryId;
            node.Attributes.Append(countryIdAtt);

            XmlAttribute regionIdAtt = doc.CreateAttribute("RegionId");
            regionIdAtt.Value = this.RegionId;
            node.Attributes.Append(regionIdAtt);

            XmlAttribute statusIdAtt = doc.CreateAttribute("StatusId");
            statusIdAtt.Value = this.StatusId;
            node.Attributes.Append(statusIdAtt);

            return node;

        }

        public static Organization DesearializeOrganization(XmlNode node)
        {
            string orgName, parentOrgName, companyId, countryId, regionId, statusId = "";
            try
            {

                if (node != null)
                {
                    orgName = node.Attributes["OrgName"].Value;
                    parentOrgName = node.Attributes["ParentOrgName"].Value;
                    companyId = node.Attributes["CompanyId"].Value;
                    countryId = node.Attributes["CountryId"].Value;
                    regionId = node.Attributes["RegionId"].Value;
                    statusId = node.Attributes["StatusId"].Value;
                    return Organization.CreateOrganization(orgName, parentOrgName, companyId, countryId, regionId, statusId);
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
