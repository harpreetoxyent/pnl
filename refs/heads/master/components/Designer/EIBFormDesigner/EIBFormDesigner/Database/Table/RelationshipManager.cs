using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner.Database.Table
{
    public partial class RelationshipManager : Form
    {
        private EIBPanel panelControl;
        private DataRelation dataRelation = null;
        private DataSet databaseDataSet;
        public EIBPanel PanelControl
        {
            get
            {
                return panelControl;
            }
            set
            {
                panelControl = value;
            }
        }
        public DataSet DatabaseDataSet
        {
            get
            {
                return databaseDataSet;
            }
            set
            {
                databaseDataSet = value;
            }
        }

        public RelationshipManager()
        {
            InitializeComponent();
            this.eibRelation1.Done.Click += new System.EventHandler(this.Done_Click);
            this.eibRelation1.Cancel.Click += new System.EventHandler(this.Cancel_Click);
            this.eibRelation1.firstTableName.SelectedIndexChanged += new System.EventHandler(this.firstTableName_SelectedIndexChanged);
            this.eibRelation1.secondTableName.SelectedIndexChanged += new System.EventHandler(this.secondTableName_SelectedIndexChanged);


        }

        private void eibRelation1_Load(object sender, EventArgs e)
        {

        }
        private void Done_Click(object sender, EventArgs e)
        {
            this.eibRelation1.errorLabel.Text = "";
            if (this.eibRelation1.relationName.Text.Trim().Equals(""))
            {
                this.eibRelation1.errorLabel.Text = "RelationShip Name can not be empty";
                return;
            }
            if (this.eibRelation1.firstTableName.SelectedItem == null )
            {
                this.eibRelation1.errorLabel.Text = "Select First Table";
                return;
            }
            if (this.eibRelation1.firstTableFields.SelectedItem == null)
            {
                this.eibRelation1.errorLabel.Text = "Select First Table Fields";
                return;
            }
            if (this.eibRelation1.secondTableName.SelectedItem == null)
            {
                this.eibRelation1.errorLabel.Text = "Select Second Table";
                return;
            }
            if (this.eibRelation1.secondTableFields.SelectedItem == null)
            {
                this.eibRelation1.errorLabel.Text = "Select Second Table Fields";
                return;
            }

            Regex regex = new Regex("[a-zA-Z_][a-zA-Z0-9_]*");
            Match match = regex.Match(this.eibRelation1.relationName.Text);
            if (!match.Success)
            {
                MessageBox.Show("Wrong relationship name.");
                this.eibRelation1.relationName.SelectAll();
                this.eibRelation1.relationName.Focus();
                return;
            }

            try
            {
                dataRelation = new DataRelation(this.eibRelation1.relationName.Text,
                databaseDataSet.Tables[this.eibRelation1.firstTableName.SelectedItem.ToString()].Columns[this.eibRelation1.firstTableFields.SelectedItem.ToString()],
                databaseDataSet.Tables[this.eibRelation1.secondTableName.SelectedItem.ToString()].Columns[this.eibRelation1.secondTableFields.SelectedItem.ToString()]);
                if (dataRelation != null)
                {
                    databaseDataSet.Relations.Add(dataRelation);
                    //EIBLabel relationLabel = new EIBLabel();
                    //relationLabel.InitiateSettings(null);
                    //relationLabel.BorderStyle = BorderStyle.FixedSingle;
                    //relationLabel.Text = dataRelation.RelationName;
                    EIBTableConnector tableConnector = new EIBTableConnector();
                    tableConnector.InitiateSettings(panelControl);
                    tableConnector.Mark1 = (EIBTable)panelControl.Controls[this.eibRelation1.firstTableName.SelectedItem.ToString()];
                    tableConnector.Mark2 = (EIBTable)panelControl.Controls[this.eibRelation1.secondTableName.SelectedItem.ToString()];
                    tableConnector.createLine();
                    //panelControl.Controls.Add(relationLabel);
                    panelControl.Controls.Add(tableConnector);
                }
            }
            catch (Exception ex)
            {
                this.eibRelation1.errorLabel.Text = ex.Message;
            }
            if (this.eibRelation1.errorLabel.Text.Trim().Equals(""))
            {
                this.Close();
            }
        }

        public void loadDatabaseTable(EIBPanel panelControl)
        {
            this.panelControl = panelControl;
            foreach (Control control in panelControl.Controls)
            {
                //Give Handle to XML Node to new Frame Element so it can write its own child
                string eibCOntrolInterfaceName = (typeof(IEIBControl)).Name;
                if (control.GetType().GetInterface(eibCOntrolInterfaceName) != null)
                {
                    if (control is EIBTable)
                    {
                        EIBTable eibTable = (EIBTable)control;
                        DataTable dataTable = eibTable.TableData; 
                        if (dataTable != null)
                        {
                            this.eibRelation1.firstTableName.Items.Add(dataTable.TableName);
                            this.eibRelation1.secondTableName.Items.Add(dataTable.TableName);

                        }
                        else
                        {
                            dataTable = databaseDataSet.Tables[eibTable.tableName.Text];
                            if (dataTable != null)
                            {
                                this.eibRelation1.firstTableName.Items.Add(dataTable.TableName);
                                this.eibRelation1.secondTableName.Items.Add(dataTable.TableName);
                            }
                        }
                    }
                }
            }
        }
        private void Cancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        private void firstTableName_SelectedIndexChanged(object sender, EventArgs e)
        {
            ComboBox firstItem  = (ComboBox)sender;
            DataTable dataTable = this.databaseDataSet.Tables[firstItem.SelectedItem.ToString()];
            if (dataTable != null)
            {
                this.eibRelation1.firstTableFields.Items.Clear();
                this.eibRelation1.firstTableFields.Text = "";
                foreach (DataColumn column in dataTable.Columns)
                {
                    this.eibRelation1.firstTableFields.Items.Add(column.ColumnName);
                }
            }
        }
        private void secondTableName_SelectedIndexChanged(object sender, EventArgs e)
        {
            ComboBox secondItem = (ComboBox)sender;
            DataTable dataTable = this.databaseDataSet.Tables[secondItem.SelectedItem.ToString()];
            if (dataTable != null)
            {
                this.eibRelation1.secondTableFields.Items.Clear();
                this.eibRelation1.secondTableFields.Text = "";
                foreach (DataColumn column in dataTable.Columns)
                {
                    this.eibRelation1.secondTableFields.Items.Add(column.ColumnName);
                }
            }
        }
    }
}