using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.IO;
using System.Data;
using System.Data.OleDb;
using System.Xml;
using MySql.Data.MySqlClient;
using EIBFormDesigner.XML;
using EIBXMLServices;

namespace EIBFormDesigner.Designer.Database
{
    public class DBSettings : System.Windows.Forms.Form
    {
        private IBaseWindow currentBaseWindow;
        public static MySqlConnection connection = null;
        private Panel panel1;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private TextBox serverAddress;
        private TextBox password;
        private TextBox userName;
        private ComboBox databaseType;
        private Button createDBbutton;
        private Button cancelButton;
        private DataSet currentDataSet;
        public Label errorLabel;
        private Button saveButton;
        private HelpProvider helpProvider1;
        private TextBox txtPortNo;
        private Label lblPortNo;
        private System.ComponentModel.IContainer components;

        public IBaseWindow CurrentBaseWindow
        {
            get
            {
                return currentBaseWindow;
            }
            set
            {
                currentBaseWindow = value;
            }
        }
        public DBSettings()
        {
            //
            // Required for Windows Form Designer support
            //
            InitializeComponent();

            //
            // TODO: Add any constructor code after InitializeComponent call
            //

        }

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code
        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.panel1 = new System.Windows.Forms.Panel();
            this.txtPortNo = new System.Windows.Forms.TextBox();
            this.lblPortNo = new System.Windows.Forms.Label();
            this.saveButton = new System.Windows.Forms.Button();
            this.errorLabel = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.createDBbutton = new System.Windows.Forms.Button();
            this.databaseType = new System.Windows.Forms.ComboBox();
            this.serverAddress = new System.Windows.Forms.TextBox();
            this.password = new System.Windows.Forms.TextBox();
            this.userName = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.AutoSize = true;
            this.panel1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.panel1.Controls.Add(this.txtPortNo);
            this.panel1.Controls.Add(this.lblPortNo);
            this.panel1.Controls.Add(this.saveButton);
            this.panel1.Controls.Add(this.errorLabel);
            this.panel1.Controls.Add(this.cancelButton);
            this.panel1.Controls.Add(this.createDBbutton);
            this.panel1.Controls.Add(this.databaseType);
            this.panel1.Controls.Add(this.serverAddress);
            this.panel1.Controls.Add(this.password);
            this.panel1.Controls.Add(this.userName);
            this.panel1.Controls.Add(this.label4);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.panel1, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.panel1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.panel1.Location = new System.Drawing.Point(6, 6);
            this.panel1.Name = "panel1";
            this.helpProvider1.SetShowHelp(this.panel1, true);
            this.panel1.Size = new System.Drawing.Size(455, 201);
            this.panel1.TabIndex = 0;
            // 
            // txtPortNo
            // 
            this.helpProvider1.SetHelpKeyword(this.txtPortNo, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.txtPortNo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.txtPortNo.Location = new System.Drawing.Point(169, 128);
            this.txtPortNo.Name = "txtPortNo";
            this.helpProvider1.SetShowHelp(this.txtPortNo, true);
            this.txtPortNo.Size = new System.Drawing.Size(153, 20);
            this.txtPortNo.TabIndex = 12;
            // 
            // lblPortNo
            // 
            this.lblPortNo.AutoSize = true;
            this.lblPortNo.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.lblPortNo, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.lblPortNo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.lblPortNo.Location = new System.Drawing.Point(6, 128);
            this.lblPortNo.Name = "lblPortNo";
            this.helpProvider1.SetShowHelp(this.lblPortNo, true);
            this.lblPortNo.Size = new System.Drawing.Size(63, 17);
            this.lblPortNo.TabIndex = 11;
            this.lblPortNo.Text = "Port No";
            // 
            // saveButton
            // 
            this.helpProvider1.SetHelpKeyword(this.saveButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.saveButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.saveButton.Location = new System.Drawing.Point(80, 165);
            this.saveButton.Name = "saveButton";
            this.helpProvider1.SetShowHelp(this.saveButton, true);
            this.saveButton.Size = new System.Drawing.Size(90, 24);
            this.saveButton.TabIndex = 5;
            this.saveButton.Text = "&Save Settings";
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.ForeColor = System.Drawing.Color.Red;
            this.errorLabel.Location = new System.Drawing.Point(16, 157);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(0, 13);
            this.errorLabel.TabIndex = 10;
            // 
            // cancelButton
            // 
            this.helpProvider1.SetHelpKeyword(this.cancelButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.cancelButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelButton.Location = new System.Drawing.Point(252, 165);
            this.cancelButton.Name = "cancelButton";
            this.helpProvider1.SetShowHelp(this.cancelButton, true);
            this.cancelButton.Size = new System.Drawing.Size(70, 24);
            this.cancelButton.TabIndex = 7;
            this.cancelButton.Text = "C&lose";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // createDBbutton
            // 
            this.helpProvider1.SetHelpKeyword(this.createDBbutton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.createDBbutton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.createDBbutton.Location = new System.Drawing.Point(176, 165);
            this.createDBbutton.Name = "createDBbutton";
            this.helpProvider1.SetShowHelp(this.createDBbutton, true);
            this.createDBbutton.Size = new System.Drawing.Size(70, 24);
            this.createDBbutton.TabIndex = 6;
            this.createDBbutton.Text = "&Create";
            this.createDBbutton.UseVisualStyleBackColor = true;
            this.createDBbutton.Click += new System.EventHandler(this.createDBbutton_Click);
            // 
            // databaseType
            // 
            this.databaseType.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.databaseType, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.databaseType, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.databaseType.Items.AddRange(new object[] {
            "MySQL"});
            this.databaseType.Location = new System.Drawing.Point(169, 5);
            this.databaseType.Name = "databaseType";
            this.helpProvider1.SetShowHelp(this.databaseType, true);
            this.databaseType.Size = new System.Drawing.Size(153, 21);
            this.databaseType.TabIndex = 1;
            this.databaseType.SelectedIndexChanged += new System.EventHandler(this.databaseType_SelectedIndexChanged);
            // 
            // serverAddress
            // 
            this.helpProvider1.SetHelpKeyword(this.serverAddress, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.serverAddress, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.serverAddress.Location = new System.Drawing.Point(169, 98);
            this.serverAddress.Name = "serverAddress";
            this.helpProvider1.SetShowHelp(this.serverAddress, true);
            this.serverAddress.Size = new System.Drawing.Size(153, 20);
            this.serverAddress.TabIndex = 4;
            // 
            // password
            // 
            this.helpProvider1.SetHelpKeyword(this.password, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.password, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.password.Location = new System.Drawing.Point(169, 67);
            this.password.Name = "password";
            this.helpProvider1.SetShowHelp(this.password, true);
            this.password.Size = new System.Drawing.Size(153, 20);
            this.password.TabIndex = 3;
            this.password.UseSystemPasswordChar = true;
            // 
            // userName
            // 
            this.helpProvider1.SetHelpKeyword(this.userName, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.userName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.userName.Location = new System.Drawing.Point(169, 38);
            this.userName.Name = "userName";
            this.helpProvider1.SetShowHelp(this.userName, true);
            this.userName.Size = new System.Drawing.Size(153, 20);
            this.userName.TabIndex = 2;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label4, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label4, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label4.Location = new System.Drawing.Point(6, 5);
            this.label4.Name = "label4";
            this.helpProvider1.SetShowHelp(this.label4, true);
            this.label4.Size = new System.Drawing.Size(118, 17);
            this.label4.TabIndex = 3;
            this.label4.Text = "Database Type";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label3, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label3, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label3.Location = new System.Drawing.Point(6, 98);
            this.label3.Name = "label3";
            this.helpProvider1.SetShowHelp(this.label3, true);
            this.label3.Size = new System.Drawing.Size(120, 17);
            this.label3.TabIndex = 2;
            this.label3.Text = "Server Address";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label2, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label2.Location = new System.Drawing.Point(6, 69);
            this.label2.Name = "label2";
            this.helpProvider1.SetShowHelp(this.label2, true);
            this.label2.Size = new System.Drawing.Size(77, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "Password";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label1, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label1.Location = new System.Drawing.Point(6, 38);
            this.label1.Name = "label1";
            this.helpProvider1.SetShowHelp(this.label1, true);
            this.label1.Size = new System.Drawing.Size(88, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "User Name";
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // DBSettings
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(467, 213);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.helpProvider1.SetHelpKeyword(this, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "DBSettings";
            this.Padding = new System.Windows.Forms.Padding(6);
            this.helpProvider1.SetShowHelp(this, true);
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Show;
            this.Text = "Database Settings";
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion

        private void createDBbutton_Click(object sender, EventArgs e)
        {
            errorLabel.Text = "";
            try
            {
                if (currentBaseWindow != null)
                {
                    saveButton_Click(null, null);
                    DialogResult result = MessageBox.Show("This action will also drop the database with the same name if exists", "Warning Message", MessageBoxButtons.OKCancel);
                    //Check for OK Button and if cancel dont proceed
                    if (result == DialogResult.OK)
                    {
                        currentDataSet = currentBaseWindow.DatabaseDataSet;
                        string connectionString = "server= " + serverAddress.Text + " ; user id= " + userName.Text + " ; password= " + password.Text + "; pooling=false;";
                        if (connection == null || connection.State != ConnectionState.Open)
                        {
                            connection = new MySqlConnection(connectionString);
                            connection.Open();
                        }
                        if (connection.State == System.Data.ConnectionState.Open && currentDataSet != null)
                        {
                            DatabaseScriptGenerator dbScriptGenerator = new DatabaseScriptGenerator();
                            dbScriptGenerator.CurrentDataSet = currentDataSet;
                            string sqlQueries = dbScriptGenerator.GenerateDatabaseScripts(currentBaseWindow);
                            if (sqlQueries == "")
                            {
                                return;
                            }
                            /* Testing code For Yash
                            */

                            TextWriter tw = new StreamWriter(EIBXMLUtilities.dataFolderName + @"\test.sql");
                            tw.Write(sqlQueries);
                            tw.Close();

                            //MessageBox.Show("Connection to MySQL opened through MySQL for .Net" + sqlQueries);
                            MySqlCommand sqlCommand = new MySqlCommand(sqlQueries, connection);
                            int returnQueryExecute = sqlCommand.ExecuteNonQuery();

                            //string[] query1 = sqlQueries.Split(new string[] { ";" }, StringSplitOptions.RemoveEmptyEntries);
                            //foreach (string q1 in query1)
                            //{
                                //MySqlCommand sqlCommand = new MySqlCommand(q1, connection);
                                //int returnQueryExecute = sqlCommand.ExecuteNonQuery();
                            //}
                        }


                    }
                }
            }
            catch (Exception ex)
            {
                errorLabel.Text = ex.Message;

            }
            finally
            {
                connection.Close();
            }
            if (errorLabel.Text.Trim().Equals(""))
            {
                errorLabel.ForeColor = Color.Black;
                errorLabel.Text = "Data Base Created.";
                //this.Close();
            }
            
            this.Show();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            errorLabel.ForeColor = Color.Red;
            this.Close();
        }

        private void databaseType_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (databaseType.SelectedItem.ToString() == "MySQL")
            {
                txtPortNo.Text = "3306";
            }
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            if ((!EIBXMLUtilities.projectName.Equals("")) && EIBXMLUtilities.projectName != null)
            {
                XmlDocument doc = new XmlDocument();
                doc.LoadXml("<dbsettings><properties>DB Settings</properties></dbsettings>");
                XmlElement serverElem = doc.CreateElement("servername");
                serverElem.InnerText = this.serverAddress.Text;
                doc.DocumentElement.AppendChild(serverElem);
                XmlElement userElem = doc.CreateElement("user");
                userElem.InnerText = this.userName.Text;
                doc.DocumentElement.AppendChild(userElem);
                XmlElement passwordElem = doc.CreateElement("password");
                passwordElem.InnerText = this.password.Text;
                doc.DocumentElement.AppendChild(passwordElem);
                XmlElement databaseType = doc.CreateElement("databasetype");
                databaseType.InnerText = this.databaseType.SelectedItem.ToString();
                doc.DocumentElement.AppendChild(databaseType);
                XmlElement portNo = doc.CreateElement("portno");
                portNo.InnerText = this.txtPortNo.Text;
                doc.DocumentElement.AppendChild(portNo);

                XmlTextWriter writer = new XmlTextWriter(EIBXMLUtilities.dataFolderName + "\\" + "datasettings.xml", null);
                writer.Formatting = Formatting.Indented;
                doc.Save(writer);
                writer.Flush();
                writer.Close();
                //this.Close();
            }
        }
    }
}
