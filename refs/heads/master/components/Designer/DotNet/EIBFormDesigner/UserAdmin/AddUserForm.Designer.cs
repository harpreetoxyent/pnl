namespace EIBFormDesigner.UserAdmin
{
    partial class AddUserForm
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
            this.components = new System.ComponentModel.Container();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.saveUserButton = new System.Windows.Forms.Button();
            this.password = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.userId = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.groupCombobox = new System.Windows.Forms.ComboBox();
            this.firstName = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.organizationCombobox = new System.Windows.Forms.ComboBox();
            this.label8 = new System.Windows.Forms.Label();
            this.emailAddress = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.lastName = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.middleName = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.genderCombo = new System.Windows.Forms.ComboBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.prefixCombo = new System.Windows.Forms.ComboBox();
            this.suffixCombo = new System.Windows.Forms.ComboBox();
            this.employeeno = new System.Windows.Forms.TextBox();
            this.categoryCombo = new System.Windows.Forms.ComboBox();
            this.address1 = new System.Windows.Forms.TextBox();
            this.address2 = new System.Windows.Forms.TextBox();
            this.cityCombo = new System.Windows.Forms.ComboBox();
            this.stateCombo = new System.Windows.Forms.ComboBox();
            this.label19 = new System.Windows.Forms.Label();
            this.zipCode = new System.Windows.Forms.TextBox();
            this.label20 = new System.Windows.Forms.Label();
            this.label21 = new System.Windows.Forms.Label();
            this.telephoneno = new System.Windows.Forms.TextBox();
            this.label22 = new System.Windows.Forms.Label();
            this.npin = new System.Windows.Forms.TextBox();
            this.upin = new System.Windows.Forms.TextBox();
            this.label23 = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.errorProvider1 = new System.Windows.Forms.ErrorProvider(this.components);
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.chkActive = new System.Windows.Forms.CheckBox();
            this.birthday = new System.Windows.Forms.DateTimePicker();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider1)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // saveUserButton
            // 
            this.helpProvider1.SetHelpKeyword(this.saveUserButton, "User");
            this.helpProvider1.SetHelpNavigator(this.saveUserButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.saveUserButton.Location = new System.Drawing.Point(345, 523);
            this.saveUserButton.Name = "saveUserButton";
            this.helpProvider1.SetShowHelp(this.saveUserButton, true);
            this.saveUserButton.Size = new System.Drawing.Size(81, 33);
            this.saveUserButton.TabIndex = 10;
            this.saveUserButton.Text = "&Save";
            this.saveUserButton.UseVisualStyleBackColor = true;
            this.saveUserButton.Click += new System.EventHandler(this.saveUserButton_Click);
            this.saveUserButton.Validating += new System.ComponentModel.CancelEventHandler(this.saveUserButton_Validating);
            // 
            // password
            // 
            this.helpProvider1.SetHelpKeyword(this.password, "User");
            this.helpProvider1.SetHelpNavigator(this.password, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.password.Location = new System.Drawing.Point(130, 43);
            this.password.Name = "password";
            this.helpProvider1.SetShowHelp(this.password, true);
            this.password.Size = new System.Drawing.Size(122, 23);
            this.password.TabIndex = 1;
            this.password.UseSystemPasswordChar = true;
            this.password.Validating += new System.ComponentModel.CancelEventHandler(this.password_Validating);
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label12, "User");
            this.helpProvider1.SetHelpNavigator(this.label12, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label12.Location = new System.Drawing.Point(22, 43);
            this.label12.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label12.Name = "label12";
            this.helpProvider1.SetShowHelp(this.label12, true);
            this.label12.Size = new System.Drawing.Size(69, 17);
            this.label12.TabIndex = 35;
            this.label12.Text = "Password";
            // 
            // userId
            // 
            this.helpProvider1.SetHelpKeyword(this.userId, "User");
            this.helpProvider1.SetHelpNavigator(this.userId, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.userId.Location = new System.Drawing.Point(130, 14);
            this.userId.Name = "userId";
            this.helpProvider1.SetShowHelp(this.userId, true);
            this.userId.Size = new System.Drawing.Size(122, 23);
            this.userId.TabIndex = 0;
            this.userId.Validating += new System.ComponentModel.CancelEventHandler(this.userId_Validating);
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label10, "User");
            this.helpProvider1.SetHelpNavigator(this.label10, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label10.Location = new System.Drawing.Point(22, 14);
            this.label10.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label10.Name = "label10";
            this.helpProvider1.SetShowHelp(this.label10, true);
            this.label10.Size = new System.Drawing.Size(49, 17);
            this.label10.TabIndex = 34;
            this.label10.Text = "UserId";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label2, "User");
            this.helpProvider1.SetHelpNavigator(this.label2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label2.Location = new System.Drawing.Point(14, 38);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.helpProvider1.SetShowHelp(this.label2, true);
            this.label2.Size = new System.Drawing.Size(76, 17);
            this.label2.TabIndex = 64;
            this.label2.Text = "First Name";
            // 
            // groupCombobox
            // 
            this.groupCombobox.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.groupCombobox, "User");
            this.helpProvider1.SetHelpNavigator(this.groupCombobox, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.groupCombobox.Location = new System.Drawing.Point(126, 341);
            this.groupCombobox.Name = "groupCombobox";
            this.helpProvider1.SetShowHelp(this.groupCombobox, true);
            this.groupCombobox.Size = new System.Drawing.Size(121, 25);
            this.groupCombobox.TabIndex = 20;
            // 
            // firstName
            // 
            this.helpProvider1.SetHelpKeyword(this.firstName, "User");
            this.helpProvider1.SetHelpNavigator(this.firstName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.firstName.Location = new System.Drawing.Point(126, 38);
            this.firstName.Name = "firstName";
            this.helpProvider1.SetShowHelp(this.firstName, true);
            this.firstName.Size = new System.Drawing.Size(121, 23);
            this.firstName.TabIndex = 0;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label6, "User");
            this.helpProvider1.SetHelpNavigator(this.label6, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label6.Location = new System.Drawing.Point(14, 341);
            this.label6.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label6.Name = "label6";
            this.helpProvider1.SetShowHelp(this.label6, true);
            this.label6.Size = new System.Drawing.Size(48, 17);
            this.label6.TabIndex = 71;
            this.label6.Text = "Group";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label1, "User");
            this.helpProvider1.SetHelpNavigator(this.label1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label1.Location = new System.Drawing.Point(14, 96);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.helpProvider1.SetShowHelp(this.label1, true);
            this.label1.Size = new System.Drawing.Size(76, 17);
            this.label1.TabIndex = 65;
            this.label1.Text = "Last Name";
            // 
            // organizationCombobox
            // 
            this.organizationCombobox.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.organizationCombobox, "User");
            this.helpProvider1.SetHelpNavigator(this.organizationCombobox, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.organizationCombobox.Location = new System.Drawing.Point(126, 310);
            this.organizationCombobox.Name = "organizationCombobox";
            this.helpProvider1.SetShowHelp(this.organizationCombobox, true);
            this.organizationCombobox.Size = new System.Drawing.Size(121, 25);
            this.organizationCombobox.TabIndex = 19;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label8, "User");
            this.helpProvider1.SetHelpNavigator(this.label8, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label8.Location = new System.Drawing.Point(14, 157);
            this.label8.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label8.Name = "label8";
            this.helpProvider1.SetShowHelp(this.label8, true);
            this.label8.Size = new System.Drawing.Size(60, 17);
            this.label8.TabIndex = 68;
            this.label8.Text = "Birthday";
            // 
            // emailAddress
            // 
            this.helpProvider1.SetHelpKeyword(this.emailAddress, "User");
            this.helpProvider1.SetHelpNavigator(this.emailAddress, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.emailAddress.Location = new System.Drawing.Point(126, 126);
            this.emailAddress.Name = "emailAddress";
            this.helpProvider1.SetShowHelp(this.emailAddress, true);
            this.emailAddress.Size = new System.Drawing.Size(121, 23);
            this.emailAddress.TabIndex = 6;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label5, "User");
            this.helpProvider1.SetHelpNavigator(this.label5, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label5.Location = new System.Drawing.Point(14, 310);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.helpProvider1.SetShowHelp(this.label5, true);
            this.label5.Size = new System.Drawing.Size(89, 17);
            this.label5.TabIndex = 70;
            this.label5.Text = "Organization";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label4, "User");
            this.helpProvider1.SetHelpNavigator(this.label4, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label4.Location = new System.Drawing.Point(14, 126);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.helpProvider1.SetShowHelp(this.label4, true);
            this.label4.Size = new System.Drawing.Size(98, 17);
            this.label4.TabIndex = 67;
            this.label4.Text = "Email Address";
            // 
            // lastName
            // 
            this.helpProvider1.SetHelpKeyword(this.lastName, "User");
            this.helpProvider1.SetHelpNavigator(this.lastName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.lastName.Location = new System.Drawing.Point(126, 96);
            this.lastName.Name = "lastName";
            this.helpProvider1.SetShowHelp(this.lastName, true);
            this.lastName.Size = new System.Drawing.Size(121, 23);
            this.lastName.TabIndex = 4;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label9, "User");
            this.helpProvider1.SetHelpNavigator(this.label9, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label9.Location = new System.Drawing.Point(14, 189);
            this.label9.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label9.Name = "label9";
            this.helpProvider1.SetShowHelp(this.label9, true);
            this.label9.Size = new System.Drawing.Size(56, 17);
            this.label9.TabIndex = 69;
            this.label9.Text = "Gender";
            // 
            // middleName
            // 
            this.helpProvider1.SetHelpKeyword(this.middleName, "User");
            this.helpProvider1.SetHelpNavigator(this.middleName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.middleName.Location = new System.Drawing.Point(126, 67);
            this.middleName.Name = "middleName";
            this.helpProvider1.SetShowHelp(this.middleName, true);
            this.middleName.Size = new System.Drawing.Size(121, 23);
            this.middleName.TabIndex = 2;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label3, "User");
            this.helpProvider1.SetHelpNavigator(this.label3, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label3.Location = new System.Drawing.Point(14, 67);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.helpProvider1.SetShowHelp(this.label3, true);
            this.label3.Size = new System.Drawing.Size(90, 17);
            this.label3.TabIndex = 66;
            this.label3.Text = "Middle Name";
            // 
            // genderCombo
            // 
            this.genderCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.genderCombo, "User");
            this.helpProvider1.SetHelpNavigator(this.genderCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.genderCombo.Items.AddRange(new object[] {
            "Male",
            "Female"});
            this.genderCombo.Location = new System.Drawing.Point(126, 189);
            this.genderCombo.Name = "genderCombo";
            this.helpProvider1.SetShowHelp(this.genderCombo, true);
            this.genderCombo.Size = new System.Drawing.Size(121, 25);
            this.genderCombo.TabIndex = 10;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label7, "User");
            this.helpProvider1.SetHelpNavigator(this.label7, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label7.Location = new System.Drawing.Point(290, 38);
            this.label7.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label7.Name = "label7";
            this.helpProvider1.SetShowHelp(this.label7, true);
            this.label7.Size = new System.Drawing.Size(43, 17);
            this.label7.TabIndex = 72;
            this.label7.Text = "Prefix";
            this.label7.Click += new System.EventHandler(this.label7_Click);
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label11, "User");
            this.helpProvider1.SetHelpNavigator(this.label11, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label11.Location = new System.Drawing.Point(290, 329);
            this.label11.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label11.Name = "label11";
            this.helpProvider1.SetShowHelp(this.label11, true);
            this.label11.Size = new System.Drawing.Size(31, 17);
            this.label11.TabIndex = 79;
            this.label11.Text = "City";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label13, "User");
            this.helpProvider1.SetHelpNavigator(this.label13, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label13.Location = new System.Drawing.Point(290, 96);
            this.label13.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label13.Name = "label13";
            this.helpProvider1.SetShowHelp(this.label13, true);
            this.label13.Size = new System.Drawing.Size(92, 17);
            this.label13.TabIndex = 73;
            this.label13.Text = "Employee No";
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label14, "User");
            this.helpProvider1.SetHelpNavigator(this.label14, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label14.Location = new System.Drawing.Point(290, 157);
            this.label14.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label14.Name = "label14";
            this.helpProvider1.SetShowHelp(this.label14, true);
            this.label14.Size = new System.Drawing.Size(46, 17);
            this.label14.TabIndex = 76;
            this.label14.Text = "Active";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label15, "User");
            this.helpProvider1.SetHelpNavigator(this.label15, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label15.Location = new System.Drawing.Point(290, 250);
            this.label15.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label15.Name = "label15";
            this.helpProvider1.SetShowHelp(this.label15, true);
            this.label15.Size = new System.Drawing.Size(68, 17);
            this.label15.TabIndex = 78;
            this.label15.Text = "Address2";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label16, "User");
            this.helpProvider1.SetHelpNavigator(this.label16, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label16.Location = new System.Drawing.Point(290, 126);
            this.label16.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label16.Name = "label16";
            this.helpProvider1.SetShowHelp(this.label16, true);
            this.label16.Size = new System.Drawing.Size(65, 17);
            this.label16.TabIndex = 75;
            this.label16.Text = "Category";
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label17, "User");
            this.helpProvider1.SetHelpNavigator(this.label17, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label17.Location = new System.Drawing.Point(290, 189);
            this.label17.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label17.Name = "label17";
            this.helpProvider1.SetShowHelp(this.label17, true);
            this.label17.Size = new System.Drawing.Size(68, 17);
            this.label17.TabIndex = 77;
            this.label17.Text = "Address1";
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label18, "User");
            this.helpProvider1.SetHelpNavigator(this.label18, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label18.Location = new System.Drawing.Point(290, 67);
            this.label18.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label18.Name = "label18";
            this.helpProvider1.SetShowHelp(this.label18, true);
            this.label18.Size = new System.Drawing.Size(42, 17);
            this.label18.TabIndex = 74;
            this.label18.Text = "Suffix";
            // 
            // prefixCombo
            // 
            this.prefixCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.prefixCombo, "User");
            this.helpProvider1.SetHelpNavigator(this.prefixCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.prefixCombo.Items.AddRange(new object[] {
            "Mr",
            "Ms"});
            this.prefixCombo.Location = new System.Drawing.Point(395, 38);
            this.prefixCombo.Name = "prefixCombo";
            this.helpProvider1.SetShowHelp(this.prefixCombo, true);
            this.prefixCombo.Size = new System.Drawing.Size(121, 25);
            this.prefixCombo.TabIndex = 1;
            // 
            // suffixCombo
            // 
            this.suffixCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.suffixCombo, "User");
            this.helpProvider1.SetHelpNavigator(this.suffixCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.suffixCombo.Items.AddRange(new object[] {
            "Dr",
            "Phd"});
            this.suffixCombo.Location = new System.Drawing.Point(395, 67);
            this.suffixCombo.Name = "suffixCombo";
            this.helpProvider1.SetShowHelp(this.suffixCombo, true);
            this.suffixCombo.Size = new System.Drawing.Size(121, 25);
            this.suffixCombo.TabIndex = 3;
            // 
            // employeeno
            // 
            this.helpProvider1.SetHelpKeyword(this.employeeno, "User");
            this.helpProvider1.SetHelpNavigator(this.employeeno, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.employeeno.Location = new System.Drawing.Point(395, 96);
            this.employeeno.Name = "employeeno";
            this.helpProvider1.SetShowHelp(this.employeeno, true);
            this.employeeno.Size = new System.Drawing.Size(121, 23);
            this.employeeno.TabIndex = 5;
            // 
            // categoryCombo
            // 
            this.categoryCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.categoryCombo, "User");
            this.helpProvider1.SetHelpNavigator(this.categoryCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.categoryCombo.Items.AddRange(new object[] {
            "UWMF Employees",
            "UWEmployees"});
            this.categoryCombo.Location = new System.Drawing.Point(395, 126);
            this.categoryCombo.Name = "categoryCombo";
            this.helpProvider1.SetShowHelp(this.categoryCombo, true);
            this.categoryCombo.Size = new System.Drawing.Size(121, 25);
            this.categoryCombo.TabIndex = 7;
            // 
            // address1
            // 
            this.helpProvider1.SetHelpKeyword(this.address1, "User");
            this.helpProvider1.SetHelpNavigator(this.address1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.address1.Location = new System.Drawing.Point(395, 191);
            this.address1.Multiline = true;
            this.address1.Name = "address1";
            this.address1.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.helpProvider1.SetShowHelp(this.address1, true);
            this.address1.Size = new System.Drawing.Size(121, 61);
            this.address1.TabIndex = 11;
            // 
            // address2
            // 
            this.helpProvider1.SetHelpKeyword(this.address2, "User");
            this.helpProvider1.SetHelpNavigator(this.address2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.address2.Location = new System.Drawing.Point(395, 259);
            this.address2.Multiline = true;
            this.address2.Name = "address2";
            this.address2.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.helpProvider1.SetShowHelp(this.address2, true);
            this.address2.Size = new System.Drawing.Size(121, 61);
            this.address2.TabIndex = 12;
            // 
            // cityCombo
            // 
            this.cityCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.cityCombo, "User");
            this.helpProvider1.SetHelpNavigator(this.cityCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cityCombo.Location = new System.Drawing.Point(395, 327);
            this.cityCombo.Name = "cityCombo";
            this.helpProvider1.SetShowHelp(this.cityCombo, true);
            this.cityCombo.Size = new System.Drawing.Size(121, 25);
            this.cityCombo.TabIndex = 16;
            // 
            // stateCombo
            // 
            this.stateCombo.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.stateCombo, "User");
            this.helpProvider1.SetHelpNavigator(this.stateCombo, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.stateCombo.Location = new System.Drawing.Point(395, 363);
            this.stateCombo.Name = "stateCombo";
            this.helpProvider1.SetShowHelp(this.stateCombo, true);
            this.stateCombo.Size = new System.Drawing.Size(121, 25);
            this.stateCombo.TabIndex = 17;
            // 
            // label19
            // 
            this.label19.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label19, "User");
            this.helpProvider1.SetHelpNavigator(this.label19, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label19.Location = new System.Drawing.Point(290, 365);
            this.label19.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label19.Name = "label19";
            this.helpProvider1.SetShowHelp(this.label19, true);
            this.label19.Size = new System.Drawing.Size(100, 17);
            this.label19.TabIndex = 88;
            this.label19.Text = "State/Province";
            // 
            // zipCode
            // 
            this.helpProvider1.SetHelpKeyword(this.zipCode, "User");
            this.helpProvider1.SetHelpNavigator(this.zipCode, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.zipCode.Location = new System.Drawing.Point(395, 400);
            this.zipCode.Name = "zipCode";
            this.helpProvider1.SetShowHelp(this.zipCode, true);
            this.zipCode.Size = new System.Drawing.Size(121, 23);
            this.zipCode.TabIndex = 18;
            // 
            // label20
            // 
            this.label20.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label20, "User");
            this.helpProvider1.SetHelpNavigator(this.label20, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label20.Location = new System.Drawing.Point(290, 402);
            this.label20.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label20.Name = "label20";
            this.helpProvider1.SetShowHelp(this.label20, true);
            this.label20.Size = new System.Drawing.Size(61, 17);
            this.label20.TabIndex = 90;
            this.label20.Text = "ZipCode";
            // 
            // label21
            // 
            this.label21.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label21, "User");
            this.helpProvider1.SetHelpNavigator(this.label21, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label21.Location = new System.Drawing.Point(14, 222);
            this.label21.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label21.Name = "label21";
            this.helpProvider1.SetShowHelp(this.label21, true);
            this.label21.Size = new System.Drawing.Size(98, 17);
            this.label21.TabIndex = 95;
            this.label21.Text = "Telephone No";
            // 
            // telephoneno
            // 
            this.helpProvider1.SetHelpKeyword(this.telephoneno, "User");
            this.helpProvider1.SetHelpNavigator(this.telephoneno, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.telephoneno.Location = new System.Drawing.Point(126, 222);
            this.telephoneno.Name = "telephoneno";
            this.helpProvider1.SetShowHelp(this.telephoneno, true);
            this.telephoneno.Size = new System.Drawing.Size(121, 23);
            this.telephoneno.TabIndex = 13;
            // 
            // label22
            // 
            this.label22.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label22, "User");
            this.helpProvider1.SetHelpNavigator(this.label22, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label22.Location = new System.Drawing.Point(14, 280);
            this.label22.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label22.Name = "label22";
            this.helpProvider1.SetShowHelp(this.label22, true);
            this.label22.Size = new System.Drawing.Size(40, 17);
            this.label22.TabIndex = 96;
            this.label22.Text = "NPIN";
            // 
            // npin
            // 
            this.helpProvider1.SetHelpKeyword(this.npin, "User");
            this.helpProvider1.SetHelpNavigator(this.npin, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.npin.Location = new System.Drawing.Point(126, 280);
            this.npin.Name = "npin";
            this.helpProvider1.SetShowHelp(this.npin, true);
            this.npin.Size = new System.Drawing.Size(121, 23);
            this.npin.TabIndex = 15;
            this.toolTip1.SetToolTip(this.npin, "National Provided identification Number");
            // 
            // upin
            // 
            this.helpProvider1.SetHelpKeyword(this.upin, "User");
            this.helpProvider1.SetHelpNavigator(this.upin, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.upin.Location = new System.Drawing.Point(126, 251);
            this.upin.Name = "upin";
            this.helpProvider1.SetShowHelp(this.upin, true);
            this.upin.Size = new System.Drawing.Size(121, 23);
            this.upin.TabIndex = 14;
            this.toolTip1.SetToolTip(this.upin, "Universal Provider identification Number");
            // 
            // label23
            // 
            this.label23.AutoSize = true;
            this.helpProvider1.SetHelpKeyword(this.label23, "User");
            this.helpProvider1.SetHelpNavigator(this.label23, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label23.Location = new System.Drawing.Point(14, 251);
            this.label23.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label23.Name = "label23";
            this.helpProvider1.SetShowHelp(this.label23, true);
            this.label23.Size = new System.Drawing.Size(40, 17);
            this.label23.TabIndex = 97;
            this.label23.Text = "UPIN";
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.helpProvider1.SetHelpKeyword(this.cancelButton, "User");
            this.helpProvider1.SetHelpNavigator(this.cancelButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelButton.Location = new System.Drawing.Point(451, 523);
            this.cancelButton.Name = "cancelButton";
            this.helpProvider1.SetShowHelp(this.cancelButton, true);
            this.cancelButton.Size = new System.Drawing.Size(70, 32);
            this.cancelButton.TabIndex = 36;
            this.cancelButton.Text = "&Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.MouseDown += new System.Windows.Forms.MouseEventHandler(this.cancelButton_MouseDown);
            // 
            // errorProvider1
            // 
            this.errorProvider1.ContainerControl = this;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label21);
            this.groupBox1.Controls.Add(this.telephoneno);
            this.groupBox1.Controls.Add(this.label22);
            this.groupBox1.Controls.Add(this.npin);
            this.groupBox1.Controls.Add(this.upin);
            this.groupBox1.Controls.Add(this.label23);
            this.groupBox1.Controls.Add(this.zipCode);
            this.groupBox1.Controls.Add(this.label20);
            this.groupBox1.Controls.Add(this.stateCombo);
            this.groupBox1.Controls.Add(this.label19);
            this.groupBox1.Controls.Add(this.cityCombo);
            this.groupBox1.Controls.Add(this.address2);
            this.groupBox1.Controls.Add(this.address1);
            this.groupBox1.Controls.Add(this.chkActive);
            this.groupBox1.Controls.Add(this.categoryCombo);
            this.groupBox1.Controls.Add(this.employeeno);
            this.groupBox1.Controls.Add(this.suffixCombo);
            this.groupBox1.Controls.Add(this.prefixCombo);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.label11);
            this.groupBox1.Controls.Add(this.label13);
            this.groupBox1.Controls.Add(this.label14);
            this.groupBox1.Controls.Add(this.label15);
            this.groupBox1.Controls.Add(this.label16);
            this.groupBox1.Controls.Add(this.label17);
            this.groupBox1.Controls.Add(this.label18);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.groupCombobox);
            this.groupBox1.Controls.Add(this.firstName);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.organizationCombobox);
            this.groupBox1.Controls.Add(this.label8);
            this.groupBox1.Controls.Add(this.emailAddress);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.lastName);
            this.groupBox1.Controls.Add(this.label9);
            this.groupBox1.Controls.Add(this.birthday);
            this.groupBox1.Controls.Add(this.middleName);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.genderCombo);
            this.groupBox1.Location = new System.Drawing.Point(5, 73);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(532, 444);
            this.groupBox1.TabIndex = 3;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Personal Information";
            // 
            // chkActive
            // 
            this.chkActive.AutoSize = true;
            this.chkActive.Location = new System.Drawing.Point(395, 160);
            this.chkActive.Name = "chkActive";
            this.chkActive.Size = new System.Drawing.Size(15, 14);
            this.chkActive.TabIndex = 9;
            this.chkActive.UseVisualStyleBackColor = true;
            // 
            // birthday
            // 
            this.birthday.Location = new System.Drawing.Point(126, 157);
            this.birthday.Name = "birthday";
            this.birthday.Size = new System.Drawing.Size(121, 23);
            this.birthday.TabIndex = 8;
            // 
            // AddUserForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 17F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(549, 568);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.saveUserButton);
            this.Controls.Add(this.password);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.userId);
            this.Controls.Add(this.label10);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.helpProvider1.SetHelpKeyword(this, "User");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.KeyPreview = true;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "AddUserForm";
            this.helpProvider1.SetShowHelp(this, true);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.Text = "Add User";
            this.Validating += new System.ComponentModel.CancelEventHandler(this.AddUserForm_Validating);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.AddUserForm_FormClosing);
            this.Load += new System.EventHandler(this.AddUserForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider1)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.HelpProvider helpProvider1;
        private System.Windows.Forms.ErrorProvider errorProvider1;
        private System.Windows.Forms.Button saveUserButton;
        private System.Windows.Forms.TextBox password;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox userId;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox groupCombobox;
        private System.Windows.Forms.TextBox firstName;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox organizationCombobox;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox emailAddress;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox lastName;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.DateTimePicker birthday;
        private System.Windows.Forms.TextBox middleName;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.ComboBox genderCombo;
        private System.Windows.Forms.ComboBox prefixCombo;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.ComboBox suffixCombo;
        private System.Windows.Forms.CheckBox chkActive;
        private System.Windows.Forms.ComboBox categoryCombo;
        private System.Windows.Forms.TextBox employeeno;
        private System.Windows.Forms.ComboBox stateCombo;
        private System.Windows.Forms.Label label19;
        private System.Windows.Forms.ComboBox cityCombo;
        private System.Windows.Forms.TextBox address2;
        private System.Windows.Forms.TextBox address1;
        private System.Windows.Forms.Label label21;
        private System.Windows.Forms.TextBox telephoneno;
        private System.Windows.Forms.Label label22;
        private System.Windows.Forms.TextBox npin;
        private System.Windows.Forms.TextBox upin;
        private System.Windows.Forms.Label label23;
        private System.Windows.Forms.TextBox zipCode;
        private System.Windows.Forms.Label label20;
        private System.Windows.Forms.ToolTip toolTip1;
        private System.Windows.Forms.Button cancelButton;
    }
}