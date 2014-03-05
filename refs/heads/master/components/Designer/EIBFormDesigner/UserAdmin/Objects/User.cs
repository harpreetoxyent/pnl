using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using EIBFormDesigner.UserAdmin.Objects.Exceptions;
using System.Windows.Forms;
using EIBXMLServices;
using System.Globalization;
using System.Text.RegularExpressions;

namespace EIBFormDesigner.UserAdmin.Objects
{
    public class UsersCollection:IList<User>
    {

        List<User> users;
        public List<String> username;
        public UsersCollection()
        {
            users = new List<User>();
            username = new List<String>();
        }

        public void Serialize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                XmlNode usersnode = doc.CreateNode(XmlNodeType.Element, "users", null);
                foreach (User usr in users)
                {
                    XmlNode node = usr.SearializeUser(doc);
                    usersnode.AppendChild(node);
                }

                doc.AppendChild(usersnode);

                doc.Save(EIBXMLUtilities.usersFolderName + "\\users.xml");
            }
            catch
            {

            }
        }

        public static UsersCollection Desearilize()
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(EIBXMLUtilities.usersFolderName + "\\users.xml");
                UsersCollection usercollection = new UsersCollection();
                XmlNode usersnode = doc.ChildNodes[0];
                foreach (XmlNode usernode in usersnode.ChildNodes)
                {
                    User usr = User.DesearializeUser(usernode);
                    if (usr != null)
                    {
                        usercollection.Add(usr);
                    }
                }
                return usercollection;
            }
            catch
            {
                return new UsersCollection();
            }
            finally
            {
                
            }

        }

        #region UsersCollection interface property

        #region IList<User> Members

        public int IndexOf(User item)
        {
            return users.IndexOf(item);
        }

        public void Insert(int index, User item)
        {
            username.Insert(index, item.Userid);
            users.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            username.RemoveAt(index);
            users.RemoveAt(index);
        }

        public User this[int index]
        {
            get
            {
                return users[index];
            }
            set
            {
                users[index] = value;
            }
        }

        #endregion

        #region ICollection<User> Members

        public void Add(User item)
        {
            username.Add(item.Userid);
            users.Add(item);
        }

        public void Clear()
        {
            username.Clear();
            users.Clear();
        }

        public bool Contains(User item)
        {
            return users.Contains(item);
        }

        public void CopyTo(User[] array, int arrayIndex)
        {
            users.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return users.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(User item)
        {
            if (username.Remove(item.Userid))
                return users.Remove(item);
            else return false;
        }

        #endregion

        #region IEnumerable<User> Members

        public IEnumerator<User> GetEnumerator()
        {
            return users.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return users.GetEnumerator();
        }

        #endregion

        #endregion
    }




    public class User
    {
        string userid;
        PersonalInfo personalinfo;
        string password;
        string orgName;

        public string OrgName
        {
            get { return orgName; }
            set { orgName = value; }
        }
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

        #region User Properties
        public string Userid
        {
            get { return userid; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    throw new UserException("Userid cannot be null or empty.");
                }
                else
                {
                    userid = value;
                }
            }
        }

        public PersonalInfo Personalinfo
        {
            get { return personalinfo; }
            set {
                if (value == null)
                {
                    throw new UserException("Personal info. missing.");
                }
                else
                {
                    personalinfo = value;
                }
            }
        }
        
        public string Password
        {
            get { return password; }
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    throw new UserException("Password cannot be null or empty.");
                }
                else
                {
                    password = value;
                }
            }
        }
        
        #endregion

        private User()
        {

        }

        public static User CreateUser(string userid,PersonalInfo personalinfo,string password,string orgName,string groupId)
        {
            User newuser= new User();
            newuser.Userid = userid;
            newuser.Password = password;
            newuser.Personalinfo = personalinfo;
            newuser.OrgName = orgName;
            newuser.GroupId = groupId;
            newuser.CompanyId = UserAdminConstants.CompanyName;
            return newuser;
        }

        public XmlNode SearializeUser(XmlDocument document)
        {
            User obj = this;
            XmlDocument doc = document;
            XmlNode node = null;
            node = doc.CreateNode(XmlNodeType.Element, "User", null);

            XmlAttribute useridatt= doc.CreateAttribute("userid");
            useridatt.Value = obj.Userid;
            node.Attributes.Append(useridatt);

            XmlAttribute companyIdAtt = doc.CreateAttribute("CompanyId");
            companyIdAtt.Value = this.CompanyId;
            node.Attributes.Append(companyIdAtt);

            XmlNode personalinfonode = doc.CreateNode(XmlNodeType.Element, "personalinfo", null);
            XmlAttribute fnameatt = doc.CreateAttribute("firstname");
            XmlAttribute mnameatt = doc.CreateAttribute("middlename");
            XmlAttribute lnameatt = doc.CreateAttribute("lastname");
            XmlAttribute emailidatt = doc.CreateAttribute("emailid");
            XmlAttribute birthdayatt = doc.CreateAttribute("birthday");
            XmlAttribute genderatt = doc.CreateAttribute("gender");
            XmlAttribute prefixatt = doc.CreateAttribute("prefix");
            XmlAttribute suffixatt = doc.CreateAttribute("suffix");
            XmlAttribute empnoatt = doc.CreateAttribute("empno");
            XmlAttribute categoryatt = doc.CreateAttribute("category");
            XmlAttribute isActiveatt = doc.CreateAttribute("active");
            XmlAttribute address1att = doc.CreateAttribute("address1");
            XmlAttribute address2att = doc.CreateAttribute("address2");
            XmlAttribute cityatt = doc.CreateAttribute("city");
            XmlAttribute stateatt = doc.CreateAttribute("state");
            XmlAttribute phoneNoatt = doc.CreateAttribute("phone");
            XmlAttribute zipCodeatt = doc.CreateAttribute("zipcode");
            XmlAttribute upinatt = doc.CreateAttribute("upin");
            XmlAttribute npinatt = doc.CreateAttribute("npin");


            fnameatt.Value = obj.Personalinfo.Firstname;
            lnameatt.Value = obj.Personalinfo.Lastname;
            mnameatt.Value = obj.Personalinfo.Middlename;
            emailidatt.Value = obj.Personalinfo.Emailid;
            birthdayatt.Value = obj.Personalinfo.Birthday.ToString();
            genderatt.Value = obj.Personalinfo.Gender;
            prefixatt.Value = obj.Personalinfo.Preffix;
            suffixatt.Value = obj.Personalinfo.Suffix;
            empnoatt.Value = obj.Personalinfo.EmployeeNo;
            categoryatt.Value = obj.Personalinfo.Category;
            isActiveatt.Value = obj.Personalinfo.IsActive.ToString();
            address1att.Value = obj.Personalinfo.Address1;
            address2att.Value = obj.Personalinfo.Address2;
            cityatt.Value = obj.Personalinfo.City;
            stateatt.Value = obj.Personalinfo.State;
            phoneNoatt.Value = obj.Personalinfo.PhoneNo.ToString();
            zipCodeatt.Value = obj.Personalinfo.Zipcode.ToString();
            upinatt.Value = obj.Personalinfo.Upin;
            npinatt.Value = obj.Personalinfo.Npin;


            personalinfonode.Attributes.Append(fnameatt);
            personalinfonode.Attributes.Append(mnameatt);
            personalinfonode.Attributes.Append(lnameatt);
            personalinfonode.Attributes.Append(emailidatt);
            personalinfonode.Attributes.Append(birthdayatt);
            personalinfonode.Attributes.Append(genderatt);
            personalinfonode.Attributes.Append(prefixatt);
            personalinfonode.Attributes.Append(suffixatt);
            personalinfonode.Attributes.Append(empnoatt);
            personalinfonode.Attributes.Append(categoryatt);
            personalinfonode.Attributes.Append(isActiveatt);
            personalinfonode.Attributes.Append(address1att);
            personalinfonode.Attributes.Append(address2att);
            personalinfonode.Attributes.Append(cityatt);
            personalinfonode.Attributes.Append(stateatt);
            personalinfonode.Attributes.Append(phoneNoatt);
            personalinfonode.Attributes.Append(zipCodeatt);
            personalinfonode.Attributes.Append(upinatt);
            personalinfonode.Attributes.Append(npinatt);


            node.AppendChild(personalinfonode);

            XmlAttribute passwordatt = doc.CreateAttribute("password");
            passwordatt.Value = obj.Password;
            node.Attributes.Append(passwordatt);

            XmlAttribute orgNameatt = doc.CreateAttribute("orgName");
            orgNameatt.Value = obj.OrgName;
            node.Attributes.Append(orgNameatt);

            XmlAttribute groupIdAtt = doc.CreateAttribute("groupId");
            groupIdAtt.Value = this.GroupId;
            node.Attributes.Append(groupIdAtt);

            doc.AppendChild(node);

            return node;

        }

        public static User DesearializeUser(XmlNode node)
        {
            string userid,password,orgname,groupid,companyId;
            PersonalInfo persionalinfo=null;
            try
            {

            if(node!=null)
            {
                userid= node.Attributes["userid"].Value;
                password=node.Attributes["password"].Value;
                orgname = node.Attributes["orgName"].Value;
                groupid = node.Attributes["groupId"].Value;
                if (node.Attributes["companyId"] != null)
                {
                    companyId = node.Attributes["CompanyId"].Value;
                }
                else
                {
                    companyId = UserAdminConstants.CompanyName;
                }
                foreach(XmlNode cnode in node.ChildNodes)
                {
#region personal info desearializers
                    if(cnode.Name=="personalinfo")
                    {
                        string fname = "", mname = "", lname = "", emailid = "", gender = "";
                        DateTime birthday = DateTime.Now;
                        string Preffix = "0";
                        string Suffix = "0";
                        string empno = "";
                        string Category = "0";
                        bool isActive = false;
                        string Address1 = "";
                        string Address2 = "";
                        string City = "";
                        string State = "";
                        int Zipcode = 0;
                        int phoneNo = 0;
                        string Upin = "";
                        string Npin = "";
                        try
                        {
                            fname = cnode.Attributes["firstname"].Value;
                            mname = cnode.Attributes["middlename"].Value;
                            lname = cnode.Attributes["lastname"].Value;
                            emailid = cnode.Attributes["emailid"].Value;
                            gender = cnode.Attributes["gender"].Value;
                            birthday = DateTime.Parse(cnode.Attributes["birthday"].Value);
                            Preffix = cnode.Attributes["prefix"].Value;
                            Suffix = cnode.Attributes["suffix"].Value;
                            empno = cnode.Attributes["empno"].Value;
                            Category = cnode.Attributes["category"].Value;
                            isActive = (cnode.Attributes["active"].Value == true.ToString());
                            Address1 = cnode.Attributes["address1"].Value;
                            Address2 = cnode.Attributes["address2"].Value;
                            City = cnode.Attributes["city"].Value;
                            State = cnode.Attributes["state"].Value;
                            phoneNo = Int32.Parse(cnode.Attributes["phone"].Value);
                            Zipcode = Int32.Parse(cnode.Attributes["zipcode"].Value);
                            Upin = cnode.Attributes["upin"].Value;
                            Npin = cnode.Attributes["npin"].Value;
                        }
                        catch
                        {

                        }
                        persionalinfo=new PersonalInfo();
                        persionalinfo.Firstname=fname;
                        persionalinfo.Lastname=lname;
                        persionalinfo.Middlename=mname;
                        persionalinfo.Emailid=emailid;
                        persionalinfo.Gender=gender;
                        persionalinfo.Birthday=birthday;
                        persionalinfo.Preffix = Preffix;
                        persionalinfo.Suffix = Suffix;
                        persionalinfo.EmployeeNo = empno;
                        persionalinfo.Category = Category;
                        persionalinfo.IsActive = isActive;
                        persionalinfo.Address1 = Address1;
                        persionalinfo.Address2 = Address2;
                        persionalinfo.City = City;
                        persionalinfo.State = State;
                        persionalinfo.Zipcode = Zipcode;
                        persionalinfo.PhoneNo = phoneNo;
                        persionalinfo.Upin = Upin;
                        persionalinfo.Npin = Npin;


                    }
#endregion
                                        
                }
                return User.CreateUser(userid, persionalinfo, password,orgname,groupid);
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

    public class PersonalInfo
    {
        string firstname;
        string middlename;
        string lastname;
        string emailid;
        DateTime birthday;
        string gender;
        string preffix;

        public string Preffix
        {
            get { return preffix; }
            set { preffix = value; }
        }
        string suffix;

        public string Suffix
        {
            get { return suffix; }
            set { suffix = value; }
        }
        string empno;

        public string EmployeeNo
        {
            get { return empno; }
            set { empno = value; }
        }
        string category;

        public string Category
        {
            get { return category; }
            set { category = value; }
        }
        bool isActive;

        public bool IsActive
        {
            get { return isActive; }
            set { isActive = value; }
        }
        string address1;

        public string Address1
        {
            get { return address1; }
            set { address1 = value; }
        }
        string address2;

        public string Address2
        {
            get { return address2; }
            set { address2 = value; }
        }
        string city;

        public string City
        {
            get { return city; }
            set { city = value; }
        }
        string state;

        public string State
        {
            get { return state; }
            set { state = value; }
        }
        int zipcode;

        public int Zipcode
        {
            get { return zipcode; }
            set { zipcode = value; }
        }
        int phoneNo;

        public int PhoneNo
        {
            get { return phoneNo; }
            set { phoneNo = value; }
        }
        string upin;

        public string Upin
        {
            get { return upin; }
            set { upin = value; }
        }
        string npin;

        public string Npin
        {
            get { return npin; }
            set { npin = value; }
        }

        public PersonalInfo()
        {

        }

        public PersonalInfo(string fname, string mname, string lname, string emailid, DateTime birthday, string gender,string Preffix, string Suffix,string empno,string Category,
        bool isActive,
        string Address1,
        string Address2,
        string City,
        string State,
        int Zipcode,
        int phoneNo,
        string Upin,
        string Npin)
        {
            this.Firstname = fname;
            this.Lastname = lname;
            this.Middlename = mname;
            this.Emailid = emailid;
            this.Birthday = birthday;
            this.Gender = gender;
            this.preffix = Preffix;
            this.suffix = Suffix;
            this.empno = empno;
            this.category = Category;
            this.isActive = isActive;
            this.address1 = Address1;
            this.address2 = Address2;
            this.city = City;
            this.state = State;
            this.zipcode = Zipcode;
            this.phoneNo = phoneNo;
            this.upin = Upin;
            this.npin = Npin;
        }
        #region Users Contact Information
        public string Firstname
        {
            get { return firstname; }
            set { firstname = value; }
        }
        
        public string Middlename
        {
            get { return middlename; }
            set { middlename = value; }
        }
       
        public string Lastname
        {
            get { return lastname; }
            set { lastname = value; }
        }
        

        public string Emailid
        {
            get { return emailid; }
            set
            {
                /*
                if (!IsEmail(value))
                {
                    throw new UserException("Email Id is missing or Invalid.");
                }*/
                emailid = value;
            }
        }
        
        public DateTime Birthday
        {
            get { return birthday; }
            set { birthday = value; }
        }
        
        public string Gender
        {
            get { return gender; }
            set { gender = value; }
        }
        #endregion
        public static bool IsEmail(string Email)
        {
            string strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" +
                @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" +
                @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
            Regex re = new Regex(strRegex);
            if (re.IsMatch(Email))
                return (true);
            else
                return (false);
        } 
    }

    namespace Exceptions
    {
        public class UserException : Exception
        {
            public UserException()
            {
            }
            public UserException(string message)
                : base(message)
            {

            }
        }
    }
}
