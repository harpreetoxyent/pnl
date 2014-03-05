using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Serialization;
using System.IO;
using System.Globalization;
using System.Security.Permissions;
using System.Threading;
using EIBFormDesigner.Designer;
using EIBFormDesigner.ScenarioWizard;
using EIBFormDesigner.CryptoGraphy;

namespace EIBFormDesigner
{
    [assembly: SecurityPermission(SecurityAction.RequestMinimum, ControlThread = true)]
    public static class Program
    {
        internal static FormDesigner currentForm = null;
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        /// 
        [STAThread]
        static void Main()
        {
            Thread.CurrentThread.CurrentCulture = new CultureInfo("en-US", false);
            Thread.CurrentThread.CurrentUICulture = new CultureInfo("en-US", false);
            Console.WriteLine("CurrentCulture is {0}.", CultureInfo.CurrentCulture.Name);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            //MessageBox.Show(Application.StartupPath);
            string omlfFileDir = Application.StartupPath;
            //string omlfFilePath = Application.StartupPath + @"\NolisFormDesigner.omlf";
            string ContentsOfFile = null;
            string dirConfig = null;
            bool isValidLicenseFile = false;
            //try
            //{
            //    //dirConfig = @"./"+"a";
            //    //FileInfo fi = new FileInfo(dirConfig);
            //    string [] omlfFiles = Directory.GetFiles(omlfFileDir, "*.omlf", SearchOption.TopDirectoryOnly);
            //    foreach (string omlfFilePath in omlfFiles)
            //    {
            //        try
            //        {
            //            ContentsOfFile = File.ReadAllText(omlfFilePath);
            //            isValidLicenseFile = ValidateLicenseFile(omlfFileDir, ContentsOfFile);
            //            if (isValidLicenseFile)
            //                break;
            //        }
            //        catch (Exception excep)
            //        {

            //        }
            //    }
            //    if (!isValidLicenseFile)
            //    {
            //        OpenFileDialog openFileDialog = new OpenFileDialog();
            //        openFileDialog.InitialDirectory = omlfFileDir;
            //        openFileDialog.DefaultExt = "*.omlf";
            //        openFileDialog.Filter = "(omlf Files)|*.omlf";
            //        DialogResult openDialogResult = openFileDialog.ShowDialog();
            //        if (openDialogResult == DialogResult.Cancel)
            //        {
            //            MessageBox.Show("Sorry. Please select the omlf(oxyent Medical Licence File) file.", "Form Designer", MessageBoxButtons.OK, MessageBoxIcon.Error);
            //            return;
            //        }
            //        else
            //        {
            //            ContentsOfFile = File.ReadAllText(openFileDialog.FileName);
            //        }
            //        isValidLicenseFile = ValidateLicenseFile(omlfFileDir, ContentsOfFile);
            //    }
            //}
            //catch (Exception ex)
            //{
            //    MessageBox.Show(ex.Message);
            //}
            isValidLicenseFile = true;
            if (isValidLicenseFile)
            {
                currentForm = new EIBFormDesigner.Designer.FormDesigner();
                //Uncomment if you want designer to cover the whole desktop Screen
                //ef.FormBorderStyle = FormBorderStyle.None;
                currentForm.WindowState = FormWindowState.Maximized;
                //SceanrioBuilder sc = new SceanrioBuilder();
                Application.Run(currentForm);
            }
        }

        private static bool ValidateLicenseFile(string omlfFileDir, string ContentsOfFile)
        {
            if (string.IsNullOrEmpty(ContentsOfFile))
            {
                //MessageBox.Show("Sorry. File Content is wrong.", "Form Designer", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
            else
            {
                Crypto.EncryptionAlgorithm = Crypto.Algorithm.RSA;
                Crypto.Key = "Oxyent Medical";
                Crypto.Encoding = Crypto.EncodingType.BASE_64;
                Crypto.Content = ContentsOfFile;
                /*FileInfo fprKey = new FileInfo(omlfFileDir + @"\config.xml");
                FileStream fprStream = fprKey.OpenRead();
                XmlDocument xmlDocument = new XmlDocument();
                xmlDocument.Load(fprStream);
                Crypto.KEY_PRIVATE = xmlDocument.OuterXml;*/
                Crypto.KEY_PRIVATE = "<RSAKeyValue><Modulus>sIb6+T+qxK6501zsh3AU1fv++ZKH4RNusnAJnlPOSkl6mrsM7TlCphGzVhFp4n6iLJaR5GkjXzd87e8+y69zBCjMag632gcvZcUsxzpekUSbmJMmVQmGOwMBr0DhN1V2Oexn8e9P8BW9uneJ9qY6LupaUCOwLLbv+7QBFie30Hs=</Modulus><Exponent>AQAB</Exponent><P>3B8/qhQh5TtjV8g1hPtZv69tus2uHv+2rJnzRi0Ax58vwL2Wo5GmQitrrmFSMCEsTvK20RLwXsLRBWMOjUyiaQ==</P><Q>zUy1GH27cMSX0sx+cY6egUWVb312h7Soao89x+eFzVGSntLUa9QmrmV6O6QukNHn5E7S7pFGaRVot96iUYb3Qw==</Q><DP>sTUW870xbHHbEBhFvcD8B60bEX/z5VDHpzxo9N3V+t7Uer4mP5hJ2ItMLR58I/MQ0J3lP3uXtzHvQ/7zAB3pwQ==</DP><DQ>FIWBaLRQw0Hzhy/Hx+Qgtf1VpbJd4czONPCilVPwTQjFZjje4PRw9WfRYxrTSMiirHCwpwZAioqIUDDh+tJJ7w==</DQ><InverseQ>nUKTT4p3GZfWOE9GNzGaseWApZB6p8kUjVF+qHbK7F1nNCy1yliWCg5sPOKMceGSfRXjQRUDVkM1Na1I7K/1Og==</InverseQ><D>HqWwmOlWyoLTPEDbx7/LvkLbx5SLBqaLB2uwH87I99i98nMbEWxph8925TBsw6fcnAUgGxmtzHAjvmNU8aPqQbq/kl3AEpjodIeWc3HJTS93EmV7+HCPorDVi0NiiiVChrjrCj2BtsxTdiuDnbxKlfbH1Fm2QeJaq5AdNoZrKKE=</D></RSAKeyValue>";
                Crypto.KEY_PUBLIC = File.ReadAllText(omlfFileDir + @"\" + Crypto.KEY_PUBLIC);
                if (Crypto.DecryptString())
                {
                    string xmlValue = Crypto.Content;
                    if (!string.IsNullOrEmpty(xmlValue))
                    {
                        XmlDocument xmlDoc = new XmlDocument();
                        xmlDoc.LoadXml(xmlValue);
                        XmlNode xmlNolisLicense = xmlDoc.FirstChild.NextSibling;
                        //MessageBox.Show(xmlNolisLicense.FirstChild.FirstChild.Value);
                        FormDesigner.isSuccessful = true;
                        //FormDesigner.company = xmlNolisLicense.FirstChild.FirstChild.Value;
                        return true;
                        //MessageBox.Show(xmlDoc.FirstChild.NextSibling.Name);
                    }
                    else
                    {
                        //MessageBox.Show("Sorry. Please select a valid license file.", "Form Designer", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return false;
                    }
                }
                else
                {
                    //MessageBox.Show("Sorry. Please select a valid license file.", "Form Designer", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;
                }
            }
        }
    }

}