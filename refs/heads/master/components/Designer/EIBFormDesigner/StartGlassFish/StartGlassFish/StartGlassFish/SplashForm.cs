using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace StartGlassFish
{
    public partial class SplashForm : Form
    {
        public SplashForm()
        {
            InitializeComponent();
            CheckForIllegalCrossThreadCalls = false;
            //this.Opacity = 0.50;
        }

        private void SplashForm_Load(object sender, EventArgs e)
        {
            System.Threading.Thread thread1 = new System.Threading.Thread(new System.Threading.ThreadStart(Start));
            thread1.Start();
        }
        public void Start()
        {
            try
            {
                string startGlassfishPath = Environment.GetEnvironmentVariable("GLASSFISH_HOME");
                startGlassfishPath += @"\bin\startglassfish.bat";
                System.Diagnostics.Process proc = new System.Diagnostics.Process();
                proc.StartInfo = new System.Diagnostics.ProcessStartInfo();
                proc.StartInfo.FileName = startGlassfishPath;
                proc.StartInfo.CreateNoWindow = true;
                proc.Start();
                while (!proc.WaitForExit(30))
                {
                    Console.WriteLine("Waiting");
                }
                System.Threading.Thread.Sleep(1000);

            }
            catch (Exception ex)
            {
                MessageBox.Show("Sorry! Error occured during starting glassfish starting.");
            }
            finally
            {
                this.Close();
                this.Dispose(true);
            }
        }
        protected override void OnPaint(PaintEventArgs e)
        {
            Graphics g = e.Graphics;
            g.Clear(this.BackColor);
            
            base.OnPaint(e);
        }
    }
}