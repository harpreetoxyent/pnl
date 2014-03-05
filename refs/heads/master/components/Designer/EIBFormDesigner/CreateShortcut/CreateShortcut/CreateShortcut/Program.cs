using System;
using System.Collections.Generic;
using System.Text;
using IWshRuntimeLibrary;
using IO = System.IO;

namespace CreateShortcut
{
    class Program
    {
        private static WshShellClass WshShell;
        static void Main(string[] args)
        {
            if (args.Length == 1)
            {
                if (args[0] == "/u")
                {
                    try
                    {
                        IO.File.Delete(Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\StartGlashFish.lnk");
                        IO.File.Delete(Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\StopGlashFish.lnk");
                        IO.File.Delete(Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\MySql.lnk");
                    }
                    catch(Exception ex)
                    {
                        
                    }
                }
            }
            else
            {
                WshShell = new WshShellClass();
                // Create the shortcut
                IWshRuntimeLibrary.IWshShortcut MyShortcut;
                // Choose the path for the shortcut
                string path = Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\StartGlashFish.lnk";
                MyShortcut = (IWshRuntimeLibrary.IWshShortcut)WshShell.CreateShortcut(path);
                // Where the shortcut should point to
                MyShortcut.TargetPath = @"c:\nolis\appdesigner\startglassfish.exe";
                // Description for the shortcut
                // Create the shortcut at the given path
                MyShortcut.Save();

                WshShell = new WshShellClass();
                // Create the shortcut

                // Choose the path for the shortcut
                path = Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\StopGlashFish.lnk";
                MyShortcut = (IWshRuntimeLibrary.IWshShortcut)WshShell.CreateShortcut(path);
                // Where the shortcut should point to
                MyShortcut.TargetPath = @"c:\nolis\appdesigner\stopglassfish.exe";
                // Description for the shortcut
                // Create the shortcut at the given path
                MyShortcut.Save();

                path = Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\MySql.lnk";
                MyShortcut = (IWshRuntimeLibrary.IWshShortcut)WshShell.CreateShortcut(path);
                // Where the shortcut should point to
                MyShortcut.TargetPath = @"c:\program files\MySql\MySql server 5.0\bin\MySql.exe";
                MyShortcut.Arguments = "mysql -h localhost -u root -p";
                // Description for the shortcut
                // Create the shortcut at the given path
                MyShortcut.Save();
            }
        }
    }
}
