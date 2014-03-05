using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;


namespace EIBFormDesigner.ScenarioWizard
{
    public partial class LiferayWindow : Form
    {
        PictureBox pbox = null;
        string newPath = null;
        string activeDir = null;
        bool isCorrupt;
        ResourceUtilities resUtil = null;
        Resources.Resource resource = null;
        static List<Resources.Resource> res = null;
        public LiferayWindow()
        {
            InitializeComponent();
            res = new List<EIBFormDesigner.ScenarioWizard.Resources.Resource>();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void btnOpenImage_Click(object sender, EventArgs e)
        {
            try
            {
                string fileNameSource = "";
                OpenFileDialog open = new OpenFileDialog();
                pbox = new PictureBox();
                open.Filter = "Image Files(*.jpg; *.jpeg; *.gif; *.bmp)|*.jpg; *.jpeg; *.gif; *.bmp";
                if (open.ShowDialog() == DialogResult.OK)
                {
                    fileNameSource = open.FileName;
                    pbox.Image = Image.FromFile(fileNameSource);
                    imageLocation.Text = fileNameSource;
                }
            }
            catch (OutOfMemoryException)
            {
                MessageBox.Show("Image not supported, Please select another image");
                isCorrupt = true;
                //throw new ApplicationException("Failed loading image");
            }
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            string activeDir = EIBXMLServices.EIBXMLUtilities.folderName;
            resUtil = new ResourceUtilities();
            newPath = System.IO.Path.Combine(activeDir, "Resources");
            if (newPath != null)
            {
                System.IO.Directory.CreateDirectory(newPath);
                if (pbox != null)
                {
                    pbox.Image.Save(newPath + "//" + "LiferayLogo.gif", System.Drawing.Imaging.ImageFormat.Gif);
                }
            }
            resource = new EIBFormDesigner.ScenarioWizard.Resources.Resource();
            resource.ResourceInfo = new Dictionary<string, string>();
            resource.ResourcePath = newPath;
            resource.ResourceInfo.Add("Image", "LiferayLogo.gif");
            if (languageCbo.SelectedIndex > -1)
            {
                resource.ResourceInfo.Add("Language", languageCbo.Text.Trim().ToString());
            }
            resUtil.Serialize(resource);
            this.Close();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
