namespace EIBFormDesigner.Designer
{
    partial class RenderWindow
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
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
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.databaseType = new System.Windows.Forms.ComboBox();
            this.serverAddress = new System.Windows.Forms.TextBox();
            this.password = new System.Windows.Forms.TextBox();
            this.userName = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.publishButton = new System.Windows.Forms.Button();
            this.renderingOption = new System.Windows.Forms.ComboBox();
            this.applicationName = new System.Windows.Forms.TextBox();
            this.deployButton = new System.Windows.Forms.Button();
            this.catalinaHome = new System.Windows.Forms.TextBox();
            this.serverPath = new System.Windows.Forms.TextBox();
            this.txtPortNo = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.txtProjectTitle = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.baseApplicationPath = new System.Windows.Forms.TextBox();
            this.btnbaseapp = new System.Windows.Forms.Button();
            this.btnGlfishDirec = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.folderBrowserDialog2 = new System.Windows.Forms.FolderBrowserDialog();
            this.openFileDialog2 = new System.Windows.Forms.OpenFileDialog();
            this.SuspendLayout();
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // databaseType
            // 
            this.databaseType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.databaseType.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.databaseType, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.databaseType, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.databaseType.Items.AddRange(new object[] {
            "MySQL"});
            this.databaseType.Location = new System.Drawing.Point(166, 177);
            this.databaseType.Margin = new System.Windows.Forms.Padding(2);
            this.databaseType.Name = "databaseType";
            this.helpProvider1.SetShowHelp(this.databaseType, true);
            this.databaseType.Size = new System.Drawing.Size(138, 21);
            this.databaseType.TabIndex = 13;
            this.databaseType.SelectedIndexChanged += new System.EventHandler(this.databaseType_SelectedIndexChanged);
            // 
            // serverAddress
            // 
            this.helpProvider1.SetHelpKeyword(this.serverAddress, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.serverAddress, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.serverAddress.Location = new System.Drawing.Point(166, 264);
            this.serverAddress.Margin = new System.Windows.Forms.Padding(2);
            this.serverAddress.Name = "serverAddress";
            this.helpProvider1.SetShowHelp(this.serverAddress, true);
            this.serverAddress.Size = new System.Drawing.Size(138, 20);
            this.serverAddress.TabIndex = 18;
            // 
            // password
            // 
            this.helpProvider1.SetHelpKeyword(this.password, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.password, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.password.Location = new System.Drawing.Point(166, 234);
            this.password.Margin = new System.Windows.Forms.Padding(2);
            this.password.Name = "password";
            this.helpProvider1.SetShowHelp(this.password, true);
            this.password.Size = new System.Drawing.Size(138, 20);
            this.password.TabIndex = 16;
            this.password.UseSystemPasswordChar = true;
            // 
            // userName
            // 
            this.helpProvider1.SetHelpKeyword(this.userName, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.userName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.userName.Location = new System.Drawing.Point(166, 208);
            this.userName.Margin = new System.Windows.Forms.Padding(2);
            this.userName.Name = "userName";
            this.helpProvider1.SetShowHelp(this.userName, true);
            this.userName.Size = new System.Drawing.Size(138, 20);
            this.userName.TabIndex = 15;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label5, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label5, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label5.Location = new System.Drawing.Point(12, 177);
            this.label5.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label5.Name = "label5";
            this.helpProvider1.SetShowHelp(this.label5, true);
            this.label5.Size = new System.Drawing.Size(118, 17);
            this.label5.TabIndex = 17;
            this.label5.Text = "Database Type";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label6, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label6, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label6.Location = new System.Drawing.Point(12, 264);
            this.label6.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label6.Name = "label6";
            this.helpProvider1.SetShowHelp(this.label6, true);
            this.label6.Size = new System.Drawing.Size(120, 17);
            this.label6.TabIndex = 14;
            this.label6.Text = "Server Address";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label7, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label7, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label7.Location = new System.Drawing.Point(12, 237);
            this.label7.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label7.Name = "label7";
            this.helpProvider1.SetShowHelp(this.label7, true);
            this.label7.Size = new System.Drawing.Size(77, 17);
            this.label7.TabIndex = 12;
            this.label7.Text = "Password";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label8, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label8, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label8.Location = new System.Drawing.Point(12, 208);
            this.label8.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label8.Name = "label8";
            this.helpProvider1.SetShowHelp(this.label8, true);
            this.label8.Size = new System.Drawing.Size(88, 17);
            this.label8.TabIndex = 11;
            this.label8.Text = "User Name";
            // 
            // publishButton
            // 
            this.helpProvider1.SetHelpKeyword(this.publishButton, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.publishButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.publishButton.Location = new System.Drawing.Point(225, 327);
            this.publishButton.Margin = new System.Windows.Forms.Padding(2);
            this.publishButton.Name = "publishButton";
            this.helpProvider1.SetShowHelp(this.publishButton, true);
            this.publishButton.Size = new System.Drawing.Size(78, 33);
            this.publishButton.TabIndex = 0;
            this.publishButton.Text = "Publish";
            this.publishButton.UseVisualStyleBackColor = true;
            this.publishButton.Visible = false;
            this.publishButton.Click += new System.EventHandler(this.publishButton_Click);
            // 
            // renderingOption
            // 
            this.renderingOption.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.renderingOption.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.renderingOption, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.renderingOption, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.renderingOption.Items.AddRange(new object[] {
            "zk"});
            this.renderingOption.Location = new System.Drawing.Point(166, 32);
            this.renderingOption.Margin = new System.Windows.Forms.Padding(2);
            this.renderingOption.Name = "renderingOption";
            this.helpProvider1.SetShowHelp(this.renderingOption, true);
            this.renderingOption.Size = new System.Drawing.Size(138, 21);
            this.renderingOption.TabIndex = 1;
            // 
            // applicationName
            // 
            this.helpProvider1.SetHelpKeyword(this.applicationName, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.applicationName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.applicationName.Location = new System.Drawing.Point(166, 62);
            this.applicationName.Margin = new System.Windows.Forms.Padding(2);
            this.applicationName.Name = "applicationName";
            this.helpProvider1.SetShowHelp(this.applicationName, true);
            this.applicationName.Size = new System.Drawing.Size(138, 20);
            this.applicationName.TabIndex = 3;
            this.applicationName.TextChanged += new System.EventHandler(this.applicationName_TextChanged);
            // 
            // deployButton
            // 
            this.helpProvider1.SetHelpKeyword(this.deployButton, "Deploy the Application");
            this.helpProvider1.SetHelpNavigator(this.deployButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.deployButton.Location = new System.Drawing.Point(117, 327);
            this.deployButton.Margin = new System.Windows.Forms.Padding(2);
            this.deployButton.Name = "deployButton";
            this.helpProvider1.SetShowHelp(this.deployButton, true);
            this.deployButton.Size = new System.Drawing.Size(85, 33);
            this.deployButton.TabIndex = 5;
            this.deployButton.Text = "Deploy";
            this.deployButton.UseVisualStyleBackColor = true;
            this.deployButton.Click += new System.EventHandler(this.deployButton_Click);
            // 
            // catalinaHome
            // 
            this.helpProvider1.SetHelpKeyword(this.catalinaHome, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.catalinaHome, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.catalinaHome.Location = new System.Drawing.Point(166, 116);
            this.catalinaHome.Margin = new System.Windows.Forms.Padding(2);
            this.catalinaHome.Name = "catalinaHome";
            this.helpProvider1.SetShowHelp(this.catalinaHome, true);
            this.catalinaHome.Size = new System.Drawing.Size(138, 20);
            this.catalinaHome.TabIndex = 6;
            // 
            // serverPath
            // 
            this.helpProvider1.SetHelpKeyword(this.serverPath, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.serverPath, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.serverPath.Location = new System.Drawing.Point(166, 144);
            this.serverPath.Margin = new System.Windows.Forms.Padding(2);
            this.serverPath.Name = "serverPath";
            this.helpProvider1.SetShowHelp(this.serverPath, true);
            this.serverPath.Size = new System.Drawing.Size(138, 20);
            this.serverPath.TabIndex = 8;
            // 
            // txtPortNo
            // 
            this.helpProvider1.SetHelpKeyword(this.txtPortNo, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.txtPortNo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.txtPortNo.Location = new System.Drawing.Point(166, 294);
            this.txtPortNo.Margin = new System.Windows.Forms.Padding(2);
            this.txtPortNo.Name = "txtPortNo";
            this.helpProvider1.SetShowHelp(this.txtPortNo, true);
            this.txtPortNo.Size = new System.Drawing.Size(138, 20);
            this.txtPortNo.TabIndex = 20;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label9, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label9, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label9.Location = new System.Drawing.Point(12, 294);
            this.label9.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label9.Name = "label9";
            this.helpProvider1.SetShowHelp(this.label9, true);
            this.label9.Size = new System.Drawing.Size(63, 17);
            this.label9.TabIndex = 19;
            this.label9.Text = "Port No";
            // 
            // txtProjectTitle
            // 
            this.helpProvider1.SetHelpKeyword(this.txtProjectTitle, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.txtProjectTitle, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.txtProjectTitle.Location = new System.Drawing.Point(166, 4);
            this.txtProjectTitle.Margin = new System.Windows.Forms.Padding(2);
            this.txtProjectTitle.Name = "txtProjectTitle";
            this.helpProvider1.SetShowHelp(this.txtProjectTitle, true);
            this.txtProjectTitle.Size = new System.Drawing.Size(138, 20);
            this.txtProjectTitle.TabIndex = 22;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label10, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.label10, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label10.Location = new System.Drawing.Point(10, 4);
            this.label10.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label10.Name = "label10";
            this.helpProvider1.SetShowHelp(this.label10, true);
            this.label10.Size = new System.Drawing.Size(96, 17);
            this.label10.TabIndex = 21;
            this.label10.Text = "Project Title";
            // 
            // baseApplicationPath
            // 
            this.helpProvider1.SetHelpKeyword(this.baseApplicationPath, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this.baseApplicationPath, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.baseApplicationPath.Location = new System.Drawing.Point(166, 89);
            this.baseApplicationPath.Margin = new System.Windows.Forms.Padding(2);
            this.baseApplicationPath.Name = "baseApplicationPath";
            this.helpProvider1.SetShowHelp(this.baseApplicationPath, true);
            this.baseApplicationPath.Size = new System.Drawing.Size(138, 20);
            this.baseApplicationPath.TabIndex = 24;
            // 
            // btnbaseapp
            // 
            this.helpProvider1.SetHelpKeyword(this.btnbaseapp, "Choose the Base  Application");
            this.helpProvider1.SetHelpNavigator(this.btnbaseapp, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.btnbaseapp.Location = new System.Drawing.Point(310, 86);
            this.btnbaseapp.Margin = new System.Windows.Forms.Padding(2);
            this.btnbaseapp.Name = "btnbaseapp";
            this.helpProvider1.SetShowHelp(this.btnbaseapp, true);
            this.btnbaseapp.Size = new System.Drawing.Size(70, 25);
            this.btnbaseapp.TabIndex = 25;
            this.btnbaseapp.Text = "&Browse";
            this.btnbaseapp.UseVisualStyleBackColor = true;
            this.btnbaseapp.Click += new System.EventHandler(this.btnbaseapp_Click);
            // 
            // btnGlfishDirec
            // 
            this.helpProvider1.SetHelpKeyword(this.btnGlfishDirec, "Choose the Glassfish Directory");
            this.helpProvider1.SetHelpNavigator(this.btnGlfishDirec, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.btnGlfishDirec.Location = new System.Drawing.Point(310, 113);
            this.btnGlfishDirec.Margin = new System.Windows.Forms.Padding(2);
            this.btnGlfishDirec.Name = "btnGlfishDirec";
            this.helpProvider1.SetShowHelp(this.btnGlfishDirec, true);
            this.btnGlfishDirec.Size = new System.Drawing.Size(70, 25);
            this.btnGlfishDirec.TabIndex = 25;
            this.btnGlfishDirec.Text = "&Browse";
            this.btnGlfishDirec.UseVisualStyleBackColor = true;
            this.btnGlfishDirec.Click += new System.EventHandler(this.btnGlfishDirec_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(10, 32);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(136, 17);
            this.label1.TabIndex = 2;
            this.label1.Text = "Rendering Option";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(10, 65);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(134, 17);
            this.label2.TabIndex = 4;
            this.label2.Text = "Application Name";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(10, 116);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(139, 17);
            this.label3.TabIndex = 7;
            this.label3.Text = "GLASSFISH Home";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(9, 144);
            this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(94, 17);
            this.label4.TabIndex = 9;
            this.label4.Text = "Server Path";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label11.Location = new System.Drawing.Point(10, 92);
            this.label11.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(129, 17);
            this.label11.TabIndex = 23;
            this.label11.Text = "Base Application";
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            // 
            // RenderWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(391, 392);
            this.Controls.Add(this.btnbaseapp);
            this.Controls.Add(this.btnGlfishDirec);
            this.Controls.Add(this.baseApplicationPath);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.txtProjectTitle);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.txtPortNo);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.databaseType);
            this.Controls.Add(this.serverAddress);
            this.Controls.Add(this.password);
            this.Controls.Add(this.userName);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.serverPath);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.catalinaHome);
            this.Controls.Add(this.deployButton);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.applicationName);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.renderingOption);
            this.Controls.Add(this.publishButton);
            this.helpProvider1.SetHelpKeyword(this, "How to do Rendering?");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.Name = "RenderWindow";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
            this.Text = "RenderWindow";
            this.Load += new System.EventHandler(this.RenderWindow_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.HelpProvider helpProvider1;
        private System.Windows.Forms.Button publishButton;
        private System.Windows.Forms.ComboBox renderingOption;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox applicationName;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button deployButton;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox catalinaHome;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox serverPath;
        private System.Windows.Forms.ComboBox databaseType;
        private System.Windows.Forms.TextBox serverAddress;
        private System.Windows.Forms.TextBox password;
        private System.Windows.Forms.TextBox userName;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox txtPortNo;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtProjectTitle;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.TextBox baseApplicationPath;
        private System.Windows.Forms.Button btnbaseapp;
        private System.Windows.Forms.Button btnGlfishDirec;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog2;
        private System.Windows.Forms.OpenFileDialog openFileDialog2;
    }
}