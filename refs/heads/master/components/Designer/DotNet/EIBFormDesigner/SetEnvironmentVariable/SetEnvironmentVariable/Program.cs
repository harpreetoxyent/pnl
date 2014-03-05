using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace SetEnvironmentVariable
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            if (args.Length >0 && args[0] == @"/u")
            {
                if (Environment.GetEnvironmentVariable("ANT_HOME") != null)
                {
                    Environment.SetEnvironmentVariable("ANT_HOME", null, EnvironmentVariableTarget.Machine);
                }
                if (Environment.GetEnvironmentVariable("JAVA_HOME") != null)
                {
                    Environment.SetEnvironmentVariable("JAVA_HOME", null, EnvironmentVariableTarget.Machine);
                }
                if (Environment.GetEnvironmentVariable("MODULE_LOC") != null)
                {
                    Environment.SetEnvironmentVariable("MODULE_LOC", null, EnvironmentVariableTarget.Machine);
                }
                if (Environment.GetEnvironmentVariable("GLASSFISH_HOME") != null)
                {
                    Environment.SetEnvironmentVariable("GLASSFISH_HOME", null, EnvironmentVariableTarget.Machine);
                }
                if (Environment.GetEnvironmentVariable("SERVER_ADDRESS") != null)
                {
                    Environment.SetEnvironmentVariable("SERVER_ADDRESS", null, EnvironmentVariableTarget.Machine);
                }
                string pathVariable = Environment.GetEnvironmentVariable("PATH",EnvironmentVariableTarget.Machine);
                if (!string.IsNullOrEmpty(pathVariable))
                {
                    pathVariable = pathVariable.Replace(@"C:\apache-ant-1.7.0\bin", "");
                    pathVariable = pathVariable.Replace(@"C:\Program Files\MySQL\MySQL Server 5.0\bin", "");
                    pathVariable = pathVariable.Replace(@"C:\Program Files\Java\jdk1.5.0_10\bin", "");
                    pathVariable = pathVariable.Replace(@"c:\glassfish\bin", "");
                    pathVariable = pathVariable.Replace(";;", ";");
                    pathVariable = pathVariable.Replace(";;", ";");
                    pathVariable = pathVariable.Replace(";;", ";");
                    pathVariable = pathVariable.Replace(";;", ";");

                    Environment.SetEnvironmentVariable("PATH", pathVariable, EnvironmentVariableTarget.Machine);
                }
                else
                {
                    MessageBox.Show("Path is Null");
                }
            }
            else
            {
                Environment.SetEnvironmentVariable("ANT_HOME", @"C:\apache-ant-1.7.0", EnvironmentVariableTarget.Machine);
                Environment.SetEnvironmentVariable("JAVA_HOME", @"C:\program files\java\jdk1.5.0_10", EnvironmentVariableTarget.Machine);
                Environment.SetEnvironmentVariable("MODULE_LOC", @"C:\Glassfish\domains\domain1\Module.xml", EnvironmentVariableTarget.Machine);
                Environment.SetEnvironmentVariable("GLASSFISH_HOME", @"C:\Glassfish", EnvironmentVariableTarget.Machine);
                Environment.SetEnvironmentVariable("SERVER_ADDRESS", @"127.0.0.1:8080", EnvironmentVariableTarget.Machine);
                Environment.SetEnvironmentVariable("PATH", Environment.GetEnvironmentVariable("PATH") + @";C:\apache-ant-1.7.0\bin;C:\Program Files\MySQL\MySQL Server 5.0\bin;C:\Program Files\Java\jdk1.5.0_10\bin;c:\glassfish\bin;", EnvironmentVariableTarget.Machine);
            }
        }
    }
}