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
using EIBFormDesigner.Controls;
using EIBFormDesigner.Database.Table;

namespace EIBFormDesigner.Designer.Database
{
    public class TableSettings : System.Windows.Forms.Form
    {
        private IBaseWindow currentBaseWindow;
        private Panel panel1;
        private Label label4;
        private ComboBox foreignKeyList;
        private Button deletebutton;
        private Button cancelButton;
        private DataSet currentDataSet;
        private Label errorLabel;
        private HelpProvider helpProvider1;
        private ComboBox uniqueKeyList;
        private Label label1;
        private ComboBox childRelations;
        private Label label2;
        private ComboBox parentRelations;
        private Label label3;
        private Label label5;
        private TextBox tableName;
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
        public TableSettings()
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
            this.label5 = new System.Windows.Forms.Label();
            this.tableName = new System.Windows.Forms.TextBox();
            this.childRelations = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.parentRelations = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.uniqueKeyList = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.errorLabel = new System.Windows.Forms.Label();
            this.cancelButton = new System.Windows.Forms.Button();
            this.deletebutton = new System.Windows.Forms.Button();
            this.foreignKeyList = new System.Windows.Forms.ComboBox();
            this.label4 = new System.Windows.Forms.Label();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.AutoSize = true;
            this.panel1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.panel1.Controls.Add(this.label5);
            this.panel1.Controls.Add(this.tableName);
            this.panel1.Controls.Add(this.childRelations);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.parentRelations);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.uniqueKeyList);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.errorLabel);
            this.panel1.Controls.Add(this.cancelButton);
            this.panel1.Controls.Add(this.deletebutton);
            this.panel1.Controls.Add(this.foreignKeyList);
            this.panel1.Controls.Add(this.label4);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.helpProvider1.SetHelpKeyword(this.panel1, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.panel1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.panel1.Location = new System.Drawing.Point(6, 6);
            this.panel1.Name = "panel1";
            this.helpProvider1.SetShowHelp(this.panel1, true);
            this.panel1.Size = new System.Drawing.Size(354, 202);
            this.panel1.TabIndex = 0;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label5, "DataBinding");
            this.helpProvider1.SetHelpNavigator(this.label5, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label5.Location = new System.Drawing.Point(7, 14);
            this.label5.Name = "label5";
            this.helpProvider1.SetShowHelp(this.label5, true);
            this.label5.Size = new System.Drawing.Size(95, 17);
            this.label5.TabIndex = 18;
            this.label5.Text = "Table Name";
            // 
            // tableName
            // 
            this.helpProvider1.SetHelpKeyword(this.tableName, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.tableName, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.tableName.Location = new System.Drawing.Point(191, 11);
            this.tableName.Margin = new System.Windows.Forms.Padding(2);
            this.tableName.Name = "tableName";
            this.helpProvider1.SetShowHelp(this.tableName, true);
            this.tableName.Size = new System.Drawing.Size(153, 20);
            this.tableName.TabIndex = 17;
            // 
            // childRelations
            // 
            this.childRelations.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.childRelations, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.childRelations, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.childRelations.Location = new System.Drawing.Point(191, 122);
            this.childRelations.Name = "childRelations";
            this.helpProvider1.SetShowHelp(this.childRelations, true);
            this.childRelations.Size = new System.Drawing.Size(153, 21);
            this.childRelations.TabIndex = 15;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label2, "DataBinding");
            this.helpProvider1.SetHelpNavigator(this.label2, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label2.Location = new System.Drawing.Point(6, 122);
            this.label2.Name = "label2";
            this.helpProvider1.SetShowHelp(this.label2, true);
            this.label2.Size = new System.Drawing.Size(147, 17);
            this.label2.TabIndex = 16;
            this.label2.Text = "Child Relationships";
            // 
            // parentRelations
            // 
            this.parentRelations.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.parentRelations, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.parentRelations, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.parentRelations.Location = new System.Drawing.Point(191, 96);
            this.parentRelations.Name = "parentRelations";
            this.helpProvider1.SetShowHelp(this.parentRelations, true);
            this.parentRelations.Size = new System.Drawing.Size(153, 21);
            this.parentRelations.TabIndex = 13;
            this.parentRelations.SelectedIndexChanged += new System.EventHandler(this.comboBox2_SelectedIndexChanged);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label3, "DataBinding");
            this.helpProvider1.SetHelpNavigator(this.label3, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label3.Location = new System.Drawing.Point(6, 96);
            this.label3.Name = "label3";
            this.helpProvider1.SetShowHelp(this.label3, true);
            this.label3.Size = new System.Drawing.Size(159, 17);
            this.label3.TabIndex = 14;
            this.label3.Text = "Parent Relationships";
            // 
            // uniqueKeyList
            // 
            this.uniqueKeyList.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.uniqueKeyList, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.uniqueKeyList, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.uniqueKeyList.Location = new System.Drawing.Point(191, 66);
            this.uniqueKeyList.Name = "uniqueKeyList";
            this.helpProvider1.SetShowHelp(this.uniqueKeyList, true);
            this.uniqueKeyList.Size = new System.Drawing.Size(153, 21);
            this.uniqueKeyList.TabIndex = 11;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label1, "DataBinding");
            this.helpProvider1.SetHelpNavigator(this.label1, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label1.Location = new System.Drawing.Point(6, 66);
            this.label1.Name = "label1";
            this.helpProvider1.SetShowHelp(this.label1, true);
            this.label1.Size = new System.Drawing.Size(178, 17);
            this.label1.TabIndex = 12;
            this.label1.Text = "Unique Key Constraints";
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.ForeColor = System.Drawing.Color.Red;
            this.errorLabel.Location = new System.Drawing.Point(7, 174);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(0, 13);
            this.errorLabel.TabIndex = 10;
            // 
            // cancelButton
            // 
            this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.helpProvider1.SetHelpKeyword(this.cancelButton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.cancelButton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.cancelButton.Location = new System.Drawing.Point(267, 150);
            this.cancelButton.Name = "cancelButton";
            this.helpProvider1.SetShowHelp(this.cancelButton, true);
            this.cancelButton.Size = new System.Drawing.Size(70, 24);
            this.cancelButton.TabIndex = 7;
            this.cancelButton.Text = "C&lose";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // deletebutton
            // 
            this.helpProvider1.SetHelpKeyword(this.deletebutton, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.deletebutton, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.deletebutton.Location = new System.Drawing.Point(191, 150);
            this.deletebutton.Name = "deletebutton";
            this.helpProvider1.SetShowHelp(this.deletebutton, true);
            this.deletebutton.Size = new System.Drawing.Size(70, 24);
            this.deletebutton.TabIndex = 6;
            this.deletebutton.Text = "&Delete";
            this.deletebutton.UseVisualStyleBackColor = true;
            this.deletebutton.Click += new System.EventHandler(this.deletebutton_Click);
            // 
            // foreignKeyList
            // 
            this.foreignKeyList.FormattingEnabled = true;
            this.helpProvider1.SetHelpKeyword(this.foreignKeyList, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this.foreignKeyList, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.foreignKeyList.Location = new System.Drawing.Point(191, 39);
            this.foreignKeyList.Name = "foreignKeyList";
            this.helpProvider1.SetShowHelp(this.foreignKeyList, true);
            this.foreignKeyList.Size = new System.Drawing.Size(153, 21);
            this.foreignKeyList.TabIndex = 1;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.helpProvider1.SetHelpKeyword(this.label4, "DataBinding");
            this.helpProvider1.SetHelpNavigator(this.label4, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.label4.Location = new System.Drawing.Point(6, 39);
            this.label4.Name = "label4";
            this.helpProvider1.SetShowHelp(this.label4, true);
            this.label4.Size = new System.Drawing.Size(182, 17);
            this.label4.TabIndex = 3;
            this.label4.Text = "Foreign Key Constraints";
            // 
            // helpProvider1
            // 
            this.helpProvider1.HelpNamespace = "C:\\AD_help\\Application_Designer.chm";
            // 
            // TableSettings
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.CancelButton = this.cancelButton;
            this.ClientSize = new System.Drawing.Size(366, 214);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.helpProvider1.SetHelpKeyword(this, "Data Binding");
            this.helpProvider1.SetHelpNavigator(this, System.Windows.Forms.HelpNavigator.KeywordIndex);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "TableSettings";
            this.Padding = new System.Windows.Forms.Padding(6);
            this.helpProvider1.SetShowHelp(this, true);
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Show;
            this.Text = "Database Settings";
            this.Load += new System.EventHandler(this.TableSettings_Load);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void TableSettings_Load(object sender, EventArgs e)
        {
            try
            {
                if (currentBaseWindow != null)
                {
                    if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
                    {
                        if (currentBaseWindow.CurrentControl != null && currentBaseWindow.CurrentControl.Count==1)
                        {
                            if (currentBaseWindow.CurrentControl[0] is EIBTable)
                            {
                                childRelations.Items.Clear();
                                parentRelations.Items.Clear();
                                uniqueKeyList.Items.Clear();
                                foreignKeyList.Items.Clear();
                                childRelations.Text = "";
                                parentRelations.Text = "";
                                uniqueKeyList.Text = "";
                                foreignKeyList.Text = "";
                                currentDataSet = currentBaseWindow.DatabaseDataSet;
                                EIBTable eibTable = (EIBTable)currentBaseWindow.CurrentControl[0];
                                DataTable currentTable = currentDataSet.Tables[eibTable.tableName.Text];
                                ConstraintCollection consRelation = currentTable.Constraints;
                                foreach (Constraint constraint in consRelation)
                                {
                                    //remove constraint
                                    if (constraint is ForeignKeyConstraint)
                                    {
                                        ForeignKeyConstraint uniqueCS = (ForeignKeyConstraint)constraint;
                                        foreignKeyList.Items.Add(uniqueCS.ConstraintName);
                                    }
                                }
                                consRelation = currentTable.Constraints;
                                foreach (Constraint constraint in consRelation)
                                {
                                    //remove constraint
                                    if (constraint is UniqueConstraint)
                                    {
                                        UniqueConstraint uniqueCS = (UniqueConstraint)constraint;
                                        // Get the Columns as an array.
                                        uniqueKeyList.Items.Add(uniqueCS.ConstraintName);

                                    }
                                }
                                DataRelationCollection relationCOllection = null;
                                relationCOllection = currentTable.ParentRelations;
                                foreach (DataRelation relation in relationCOllection)
                                {
                                    parentRelations.Items.Add(relation.RelationName);
                                }
                                relationCOllection = currentTable.ChildRelations;
                                foreach (DataRelation relation in relationCOllection)
                                {
                                    childRelations.Items.Add(relation.RelationName);
                                }
                                this.tableName.Text = currentTable.TableName;
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                errorLabel.Text = ex.Message;
            }

        }

        private void deletebutton_Click(object sender, EventArgs e)
        {
            bool isAnySelected = false;
            try
            {
                errorLabel.Text = "";
                if (currentBaseWindow != null)
                {
                    if (currentBaseWindow.TypeOfWindow.Equals(FormDesignerConstants.DataPattern))
                    {
                        if (currentBaseWindow.CurrentControl != null)
                        {
                            if (currentBaseWindow.CurrentControl[0] is EIBTable && currentBaseWindow.CurrentControl.Count == 1)
                            {
                                currentDataSet = currentBaseWindow.DatabaseDataSet;
                                EIBTable eibTable = (EIBTable)currentBaseWindow.CurrentControl[0];
                                DataTable currentTable = currentDataSet.Tables[eibTable.tableName.Text];
                                if (foreignKeyList.SelectedItem != null)
                                {
                                    isAnySelected = true;
                                    if (!foreignKeyList.SelectedItem.ToString().Trim().Equals(""))
                                    {
                                        string PkeyTable = ((ForeignKeyConstraint)currentTable.Constraints[foreignKeyList.SelectedItem.ToString()]).RelatedTable.TableName;
                                        //currentTable.Constraints[foreignKeyList.SelectedItem.ToString()].Table
                                        currentTable.Constraints.Remove(foreignKeyList.SelectedItem.ToString().Trim());
                                        currentTable.ParentRelations.Remove(foreignKeyList.SelectedItem.ToString());
                                        if (parentRelations.SelectedItem != null)
                                        {
                                            parentRelations.SelectedIndex = 0;
                                        }
                                        EIBTableConnectorBase connectorDel = null;
                                        foreach (EIBTableConnectorBase connectorBase in eibTable.tableConnectorList)
                                        {
                                            Control connectorControl = ((BaseWindow)currentBaseWindow).baseFrame.Controls[connectorBase.ControlName];
                                            EIBTableConnector tableConnector = (EIBTableConnector)connectorControl;
                                            if (tableConnector != null && connectorBase.Mark1.tableName.Text == PkeyTable && connectorBase.Mark2.tableName.Text == currentTable.TableName)
                                            {
                                                connectorDel = connectorBase;
                                                //tableConnector.Redraw(-1   );
                                                EIBFormDesigner.Database.Table.Line line = tableConnector.line;
                                                line.isDeleted = true;
                                                if (((BaseWindow)currentBaseWindow).Lines.Contains(line))
                                                {
                                                    ((BaseWindow)currentBaseWindow).Lines.Remove(line);

                                                }
                                                tableConnector.EraseLine(line);

                                                ((BaseWindow)currentBaseWindow).baseFrame.Controls.Remove(connectorControl);
                                            }
                                        }
                                        if (connectorDel != null)
                                        {
                                            eibTable.tableConnectorList.Remove(connectorDel);
                                            connectorDel.mark1.tableConnectorList.Remove(connectorDel);
                                        }
                                    }
                                }
                                if (uniqueKeyList.SelectedItem != null)
                                {
                                    isAnySelected = true;
                                    if (!uniqueKeyList.SelectedItem.ToString().Trim().Equals(""))
                                    {
                                        currentTable.Constraints.Remove(uniqueKeyList.SelectedItem.ToString().Trim());
                                    }
                                }
                                if (parentRelations.SelectedItem != null && isAnySelected == false)
                                {
                                    isAnySelected = true;
                                    if (!parentRelations.SelectedItem.ToString().Trim().Equals(""))
                                    {
                                        if (currentTable.ParentRelations.Contains(parentRelations.SelectedItem.ToString().Trim()))
                                        {
                                            string PkeyTable = currentTable.ParentRelations[parentRelations.SelectedItem.ToString()].ParentTable.TableName;
                                            currentTable.ParentRelations.Remove(parentRelations.SelectedItem.ToString().Trim());
                                            if (currentTable.Constraints.Contains(parentRelations.SelectedItem.ToString()))
                                            {
                                                currentTable.Constraints.Remove(parentRelations.SelectedItem.ToString());
                                            }
                                            EIBTableConnectorBase connectorDel = null;
                                            foreach (EIBTableConnectorBase connectorBase in eibTable.tableConnectorList)
                                            {
                                                Control connectorControl = ((BaseWindow)currentBaseWindow).baseFrame.Controls[connectorBase.ControlName];
                                                EIBTableConnector tableConnector = (EIBTableConnector)connectorControl;
                                                if (tableConnector != null && connectorBase.Mark1.tableName.Text == PkeyTable && connectorBase.Mark2.tableName.Text == currentTable.TableName)
                                                {
                                                    //tableConnector.Redraw(-1   );
                                                    connectorDel = connectorBase;
                                                    EIBFormDesigner.Database.Table.Line line = tableConnector.line;
                                                    line.isDeleted = true;
                                                    if (((BaseWindow)currentBaseWindow).Lines.Contains(line))
                                                    {
                                                        ((BaseWindow)currentBaseWindow).Lines.Remove(line);

                                                    }
                                                    tableConnector.EraseLine(line);
                                                    ((BaseWindow)currentBaseWindow).baseFrame.Controls.Remove(connectorControl);
                                                }
                                            }
                                            if (connectorDel != null)
                                            {
                                                eibTable.tableConnectorList.Remove(connectorDel);
                                                connectorDel.mark1.tableConnectorList.Remove(connectorDel);
                                            }
                                        }
                                    }
                                }
                                if (childRelations.SelectedItem != null)
                                {
                                    isAnySelected = true;
                                    if (!childRelations.SelectedItem.ToString().Trim().Equals(""))
                                    {
                                        string CKeyTableName = currentTable.ChildRelations[childRelations.SelectedItem.ToString()].ChildTable.TableName;
                                        currentTable.ChildRelations[childRelations.SelectedItem.ToString()].ChildTable.Constraints.Remove(childRelations.SelectedItem.ToString());
                                        currentTable.ChildRelations.Remove(childRelations.SelectedItem.ToString().Trim());
                                        EIBTableConnectorBase connectorDel = null;
                                        foreach (EIBTableConnectorBase connectorBase in eibTable.tableConnectorList)
                                        {
                                            Control connectorControl = ((BaseWindow)currentBaseWindow).baseFrame.Controls[connectorBase.ControlName];
                                            EIBTableConnector tableConnector = (EIBTableConnector)connectorControl;
                                            if (tableConnector != null && CKeyTableName == connectorBase.Mark2.tableName.Text && currentTable.TableName == connectorBase.Mark1.tableName.Text)
                                            {
                                                //tableConnector.Redraw(-1   );
                                                connectorDel = connectorBase;
                                                EIBFormDesigner.Database.Table.Line line = tableConnector.line;
                                                line.isDeleted = true;
                                                if (((BaseWindow)currentBaseWindow).Lines.Contains(line))
                                                {
                                                    ((BaseWindow)currentBaseWindow).Lines.Remove(line);

                                                }
                                                tableConnector.EraseLine(line);
                                                ((BaseWindow)currentBaseWindow).baseFrame.Controls.Remove(connectorControl);
                                            }
                                            
                                        }
                                        if (connectorDel != null)
                                        {
                                            eibTable.tableConnectorList.Remove(connectorDel);
                                            connectorDel.mark2.tableConnectorList.Remove(connectorDel);

                                        }
                                    }
                                }

                            }
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
                TableSettings_Load(null, null);
                if (!isAnySelected)
                {
                    errorLabel.Text += "\n" + "Please select a Constraint or relation to delete.";
                }
                //this.Close();
            }
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}
