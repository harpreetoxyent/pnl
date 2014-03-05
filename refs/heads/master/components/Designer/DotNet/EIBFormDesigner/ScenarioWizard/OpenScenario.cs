using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace EIBFormDesigner.ScenarioWizard
{
    public partial class OpenScenario : Form
    {
        public OpenScenario()
        {
            InitializeComponent();
            btnOpenFolder.Focus();
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            if (isDataValid())
            {
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }
        public bool isDataValid()
        {
            bool isDataValidValue = true;
            if (this.projectLocation.Text.Trim().Length <= 0)
            {
                errorLabel.Text = "Project Path need to be specified";
                isDataValidValue = false;
                return isDataValidValue;
            }
            else
            {
                isDataValidValue = System.IO.File.Exists(this.projectLocation.Text);
                if (!isDataValidValue)
                {
                    errorLabel.Text = "Project Path need to be valid path";
                }
            }
            return isDataValidValue;

        }

        private void btnOpenFolder_Click(object sender, System.EventArgs e)
        {
            this.folderBrowserDialog.RootFolder = System.Environment.SpecialFolder.MyComputer;
            this.folderBrowserDialog.ShowNewFolderButton = false;
            this.openFileDialog1.DefaultExt = "esp";
            this.openFileDialog1.Filter = "esp files (*.esp)|*.esp";
            this.folderBrowserDialog.Description = "Select the project manifest file.";
            openFileDialog1.InitialDirectory = folderBrowserDialog.SelectedPath;
            openFileDialog1.FileName = null;
            DialogResult result = this.openFileDialog1.ShowDialog();
            if (result == DialogResult.OK)
            {

                // retrieve the name of the selected folder
                string fileName = this.openFileDialog1.FileName;
                // print the folder name on a label
                this.projectLocation.Text = fileName;
                okButton.Focus();
            }

        }


    }
}