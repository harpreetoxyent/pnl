using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.IO;
using System.Text.RegularExpressions;
using System.Xml;
using System.Diagnostics;
using System.Reflection;
using WeifenLuo.WinFormsUI.Docking;
using MySql.Data.MySqlClient;
using EIBFormDesigner.Event;
using EIBFormDesigner.Controls;
using EIBFormDesigner.XML;
using EIBFormDesigner.Designer.Database;
using EIBFormDesigner.ScenarioWizard;
using EIBFormDesigner.Designer.Common;
using EIBFormDesigner.Designer.DatabaseExlorer;
using EIBFormDesigner.UserAdmin;
using EIBXMLServices;
using EIBFormDesigner.Database.Table;


namespace EIBFormDesigner.Designer
{
    public partial class RenderWindow : System.Windows.Forms.Form
    {
        string projectNamewithoutESP = null;
        string glassfishHome = null;
        string extFolder = null;
        string autodeployFolder = null;
        string path = null;
        string liferayDirectory = null;
      
        public RenderWindow()
        {
            InitializeComponent();
            this.applicationName.Enabled = false;
            this.catalinaHome.Enabled = false;
            renderingOption.SelectedIndex = 0;
        }

        private void btnbaseapp_Click(object sender, EventArgs e)
        {
            this.folderBrowserDialog1.RootFolder = System.Environment.SpecialFolder.MyComputer;
            this.folderBrowserDialog1.ShowNewFolderButton = false;

            this.openFileDialog1.DefaultExt = "esp";
            this.openFileDialog1.Filter = "esp files (*.esp)|*.esp";
            this.folderBrowserDialog1.Description = "Select the project manifest file.";
            openFileDialog1.InitialDirectory = folderBrowserDialog1.SelectedPath;
            openFileDialog1.FileName = null;
            DialogResult result = this.openFileDialog1.ShowDialog();
            if (result == DialogResult.OK)
            {

                // retrieve the name of the selected folder
                string fileName = this.openFileDialog1.FileName;
                // print the folder name on a label
                this.baseApplicationPath.Text = fileName;
            }

            //this.folderBrowserDialog1.Description = "Select the project folder.";
            //DialogResult result = this.folderBrowserDialog1.ShowDialog();
            //if (result == DialogResult.OK)
            //{

            //    // retrieve the name of the selected folder
            //    string folderName = this.folderBrowserDialog1.SelectedPath;
            //    // print the folder name on a label
            //    this.baseApplicationPath.Text = folderName;
            //}
        }

        private void publishButton_Click(object sender, EventArgs e)
        {
            if (FormDesignerUtilities.visibleTrueWindow.Count < 1)
            {
                MessageBox.Show("Please set default screen to true", "Exit Project", MessageBoxButtons.OKCancel);
            }
            else
            {
            if (this.serverPath.Text == "")
                {
                    MessageBox.Show("Please specify server path");
                }
                else
                {
                    if (this.renderingOption.SelectedItem == null)
                    {
                        MessageBox.Show("Please specify rendering option");
                    }
                    else
                    {

                        if (!this.renderingOption.SelectedItem.ToString().Trim().Equals(""))
                        {
                            string urlpath = serverPath.Text.ToString().Trim();
                            string urlServer = urlpath + "applicationname=" + EIBXMLUtilities.projectName + "&sourcedir=" + projectNamewithoutESP + @"&serverDirectory=" + autodeployFolder + "&serverLibDirectory=" + extFolder + "&renderOption=" + this.renderingOption.SelectedItem.ToString();
                            System.Diagnostics.Process.Start(urlServer);
                            this.Close();

                        }
                        else
                        {
                            MessageBox.Show("Please specify rendering option");
                        }
                    }
                }
            }
        }
        

        private void applicationName_TextChanged(object sender, EventArgs e)
        {
            string projectNamewithoutESP = Directory.GetParent(EIBXMLUtilities.projectFilePath).FullName;

        }

        private void save()
        {
            if ((!EIBXMLUtilities.projectName.Equals("")) && EIBXMLUtilities.projectName != null)
            {
                XmlDocument doc = new XmlDocument();
                doc.LoadXml("<DeploySettings><properties>Deploy Settings</properties></DeploySettings>");
                /*
                 * renderingoption
                 * <applicationname></applicationname>
                 * glassfishhome
                 * serverpath
                 * databasetype
                 * username
                 * password
                 * serveraddress
                 */
                XmlElement projectTitleElem = doc.CreateElement("projectTitle");
                projectTitleElem.InnerText = this.txtProjectTitle.Text;
                doc.DocumentElement.AppendChild(projectTitleElem);

                XmlElement renderingoptionElem = doc.CreateElement("renderingoption");
                renderingoptionElem.InnerText = this.renderingOption.SelectedItem.ToString();
                doc.DocumentElement.AppendChild(renderingoptionElem);

                XmlElement applicationNameElem = doc.CreateElement("applicationname");
                applicationNameElem.InnerText = this.applicationName.Text;
                doc.DocumentElement.AppendChild(applicationNameElem);

                XmlElement glassFishElem = doc.CreateElement("glassfishhome");
                glassFishElem.InnerText = this.catalinaHome.Text;
                doc.DocumentElement.AppendChild(glassFishElem);

                XmlElement serverpathElem = doc.CreateElement("serverpath");
                serverpathElem.InnerText = this.serverPath.Text;
                doc.DocumentElement.AppendChild(serverpathElem);

                XmlElement databasetypeElem = doc.CreateElement("databasetype");
                // HSi make default database as mySQL
                String databaseTypStr = "MySQL";
                if (this.databaseType.SelectedItem != null)
                {
                    databaseTypStr = this.databaseType.SelectedItem.ToString();
                }
                databasetypeElem.InnerText = databaseTypStr;
                doc.DocumentElement.AppendChild(databasetypeElem);

                XmlElement usernameElem = doc.CreateElement("username");
                usernameElem.InnerText = this.userName.Text;
                doc.DocumentElement.AppendChild(usernameElem);

                XmlElement passwordElem = doc.CreateElement("password");
                passwordElem.InnerText = this.password.Text;
                doc.DocumentElement.AppendChild(passwordElem);

                XmlElement serveraddressElem = doc.CreateElement("serveraddress");
                serveraddressElem.InnerText = this.serverAddress.Text;
                doc.DocumentElement.AppendChild(serveraddressElem);

                XmlElement portNoElem = doc.CreateElement("portno");
                portNoElem.InnerText = this.txtPortNo.Text;
                doc.DocumentElement.AppendChild(portNoElem);

                XmlTextWriter writer = new XmlTextWriter(EIBXMLUtilities.dataFolderName + "\\" + "DeploySettings.xml", null);
                writer.Formatting = Formatting.Indented;
                doc.Save(writer);
                writer.Flush();
                writer.Close();
                //this.Close();
            }
        }

        private void deployButton_Click(object sender, EventArgs e)
        {
            if (FormDesignerUtilities.visibleTrueWindow.Count < 1)
            {
                MessageBox.Show("Please set default screen to true", "Exit Project", MessageBoxButtons.OKCancel);
            }
            else
            {
                if (this.serverPath.Text == "")
                {
                    MessageBox.Show("Please specify server path");
                }
                else
                {
                    if (this.renderingOption.SelectedItem == null)
                    {
                        MessageBox.Show("Please specify rendering option");
                    }
                    else
                    {
                        if (!this.renderingOption.SelectedItem.ToString().Trim().Equals(""))
                        {
                            try
                            {
                                save();
                                if ((!serverAddress.Text.Equals("")) && (DBSettings.connection == null || DBSettings.connection.State == ConnectionState.Closed))
                                {
                                    string connectionString = "server= " + serverAddress.Text + " ; user id= " + userName.Text + " ; password= " + password.Text + "; pooling=false;";
                                    DBSettings.connection = new MySqlConnection(connectionString);
                                    DBSettings.connection.Open();
                                }
                                /*MySqlCommand sqlCommand = new MySqlCommand(CommonResource.CreateSQL, DBSettings.connection);
                                int returnQueryExecute = sqlCommand.ExecuteNonQuery();
                                sqlCommand = new MySqlCommand(CommonResource.maintenance, DBSettings.connection);
                                returnQueryExecute = sqlCommand.ExecuteNonQuery();
                                sqlCommand = new MySqlCommand(CommonResource.billingtracking, DBSettings.connection);
                                returnQueryExecute = sqlCommand.ExecuteNonQuery();
                                sqlCommand = new MySqlCommand(CommonResource.auditlogging, DBSettings.connection);
                                returnQueryExecute = sqlCommand.ExecuteNonQuery();*/
                                String sourcedir = projectNamewithoutESP;
                                //Setting the baseapplication variables and creating deployExt directory
                                if (this.baseApplicationPath.Text != null && this.baseApplicationPath.Text.Length > 0)
                                {
                                    RenderUtil.extAppSourceDir = "";
                                    RenderUtil.extDeployTempSourceDir = "";
                                    RenderUtil.extDeploySourceDir = "";
                                    RenderUtil.baseAppSourceDir = "";
                                    RenderUtil.baseAppName = "";
                                    RenderUtil.extAppName = "";
                                    RenderUtil.extAppSourceDir = projectNamewithoutESP;
                                    RenderUtil.baseAppSourceDir = this.baseApplicationPath.Text;
                                    RenderUtil.baseAppSourceDir = Regex.Replace(RenderUtil.baseAppSourceDir, @"\\", "/");
                                    String[] temp = RenderUtil.baseAppSourceDir.Split('/');
                                    RenderUtil.baseAppName = temp[temp.Length-1].Split('.')[0];
                                    String extDeployTempSourceDirParent = RenderUtil.baseAppSourceDir.Substring(0, RenderUtil.baseAppSourceDir.IndexOf(temp[temp.Length-1]));
                                    RenderUtil.baseAppSourceDir = extDeployTempSourceDirParent;
                                    RenderUtil.extAppName = EIBXMLUtilities.projectName;
                                    RenderUtil.extDeployTempSourceDir = extDeployTempSourceDirParent + "/DeployExt";
                                    RenderUtil.extDeploySourceDir = RenderUtil.extAppSourceDir + "/DeployExt";
                                    sourcedir = RenderUtil.extDeploySourceDir;
                                    RenderUtil.CreateDeployExtDirectory();
                                }

                                string urlpath = serverPath.Text.ToString().Trim();
                                string urlServer = urlpath + "applicationname=" + EIBXMLUtilities.projectName + "&liferayDirectory=" + liferayDirectory + "&sourcedir=" + sourcedir + @"&serverDirectory=" + autodeployFolder + "&serverLibDirectory=" + extFolder + "&deploy=true&useradmin=true" + "&renderOption=" + this.renderingOption.SelectedItem.ToString() + "&TitleName=" + this.txtProjectTitle.Text;
                                System.Diagnostics.Process.Start(urlServer);
                                this.Close();
                            }
                            catch (Exception exp)
                            {
                                Console.WriteLine(exp.StackTrace);
                                MessageBox.Show("Deploy Not succesful");
                            }

                        }
                        else
                        {
                            MessageBox.Show("Please specify rendering option");
                        }
                    }
                }
            }
            }
            

        private void RenderWindow_Load(object sender, EventArgs e)
        {
            projectNamewithoutESP = Directory.GetParent(EIBXMLUtilities.projectFilePath).FullName;
            projectNamewithoutESP = Regex.Replace(projectNamewithoutESP, @"\\", "/");
            glassfishHome = Environment.GetEnvironmentVariable("GLASSFISH_HOME");
            extFolder = glassfishHome + "\\domains\\domain1\\lib\\ext";
            autodeployFolder = glassfishHome + "\\domains\\domain1\\autodeploy";
            extFolder = Regex.Replace(extFolder, @"\\", "/");
            autodeployFolder = Regex.Replace(autodeployFolder, @"\\", "/");
            path = "http://localhost:8080/HICFrameworkServlet/HICFrameworkServlet?";
            liferayDirectory = glassfishHome + "\\domains\\domain1\\applications\\j2ee-modules\\liferay-portal";
            this.applicationName.Text = projectNamewithoutESP;
            this.catalinaHome.Text = glassfishHome;
            this.serverPath.Text = path;

            if (File.Exists(EIBXMLUtilities.dataFolderName + "\\" + "DeploySettings.xml"))
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(EIBXMLUtilities.dataFolderName + "\\" + "DeploySettings.xml");
                foreach (XmlElement xmlChildNode in xmlDoc.FirstChild.NextSibling.ChildNodes)
                {
                    if (xmlChildNode.Name == "projecttitle")
                    {
                        this.txtProjectTitle.Text = xmlChildNode.InnerText;
                    }
                    else if (xmlChildNode.Name == "databasetype")
                    {
                        this.databaseType.SelectedItem = xmlChildNode.InnerText;
                    }
                    else if (xmlChildNode.Name == "username")
                    {
                        this.userName.Text = xmlChildNode.InnerText;
                    }
                    else if (xmlChildNode.Name == "password")
                    {
                        this.password.Text = xmlChildNode.InnerText;
                    }
                    else if (xmlChildNode.Name == "serveraddress")
                    {
                        this.serverAddress.Text = xmlChildNode.InnerText;
                    }
                    else if (xmlChildNode.Name == "portno")
                    {
                        this.txtPortNo.Text = xmlChildNode.InnerText;
                    }
                }
            }
        }

        private void databaseType_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (databaseType.SelectedItem.ToString() == "MySQL")
            {
                txtPortNo.Text = "3306";
            }
        }
    
      
      }
}