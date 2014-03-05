using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Collections;
using System.IO;

namespace EIBFormDesigner.Designer
{
    public class RenderUtil
    {
        public static String extAppSourceDir = "C:/Nolis/NolisApp/NOLISExt";
        public static String extDeployTempSourceDir = "C:/Nolis/NolisApp/DeployExt";
        public static String extDeploySourceDir = "C:/Nolis/NolisApp/NOLISExt/DeployExt";
        public static String baseAppSourceDir = "C:/Nolis/NolisApp/NOLISUI";
        public static String baseAppName = "NOLISUI";
        public static String extAppName = "NOLISExt";

        public static void CopyAll(DirectoryInfo source, DirectoryInfo target)
        {
            if (source.FullName.Contains(".svn")) return;

            // Check if the target directory exists, if not, create it.
            if (Directory.Exists(target.FullName) == false)
            {
                Directory.CreateDirectory(target.FullName);
            }

            // Copy each file into it’s new directory.
            foreach (FileInfo fi in source.GetFiles())
            {
                fi.CopyTo(Path.Combine(target.ToString(), fi.Name), true);
            }

            // Copy each subdirectory using recursion.
            foreach (DirectoryInfo diSourceSubDir in source.GetDirectories())
            {
                DirectoryInfo nextTargetSubDir =
                    target.CreateSubdirectory(diSourceSubDir.Name);
                CopyAll(diSourceSubDir, nextTargetSubDir);
            }
        }

        public static void copyFiles(DirectoryInfo source, DirectoryInfo target)
        {
            if (source.FullName.Contains(".svn")) return;

            // Check if the target directory exists, if not, create it.
            if (Directory.Exists(target.FullName) == false)
            {
                Directory.CreateDirectory(target.FullName);
            }

            if (Directory.Exists(source.FullName) == true)
            {
                // Copy each file into it’s new directory.
                foreach (FileInfo file in source.GetFiles())
                {
                    if (file.Extension.Equals(".esp"))
                    {
                        continue;
                    }
                    bool isFileExist = true;
                    foreach (FileInfo extFile in target.GetFiles())
                    {
                        if (file.Name.Equals(extFile.Name))
                        {
                            isFileExist = false;
                        }
                    }
                    if (isFileExist)
                    {
                        file.CopyTo(Path.Combine(target.ToString(), file.Name), false);
                    }
                }
            }
        }

        public static void CreateDeployExtDirectory()
        {

            // Cope new application data into DeployExt folder
            DirectoryInfo targetInfo = new DirectoryInfo(extDeploySourceDir);
            if (Directory.Exists(targetInfo.FullName) == true)
            {
                Directory.Delete(extDeploySourceDir, true);
            }
            DirectoryInfo sourceInfo = new DirectoryInfo(extAppSourceDir);
            DirectoryInfo tempTargetInfo = new DirectoryInfo(extDeployTempSourceDir);
            CopyAll(sourceInfo, tempTargetInfo);
            DirectoryInfo tempSourceInfo = new DirectoryInfo(extDeployTempSourceDir);
            CopyAll(tempSourceInfo, targetInfo);
            Directory.Delete(extDeployTempSourceDir, true);

            // Merge project file
            String nolisUIFile = baseAppSourceDir + "/" + baseAppName + ".esp";
            String nolisExtFile = extDeploySourceDir + "/" + extAppName + ".esp";
            mergeEspFile(nolisUIFile, nolisExtFile);

            //Code for users folder xml changes
            mergeUsersFolder(baseAppSourceDir, extDeploySourceDir);

            //Code to copy files from data source to data extension excluding existing files
            DirectoryInfo dataSourceInfo = new DirectoryInfo(baseAppSourceDir + "/data");
            DirectoryInfo dataTargetInfo = new DirectoryInfo(extDeploySourceDir + "/data");
            copyFiles(dataSourceInfo, dataTargetInfo);
            copySubDirsAndItsContents(dataSourceInfo, dataTargetInfo);

            //Code to copy files from forms source to forms extension excluding existing files
            //First check if MapFile.xml exists in new application. If exists than merge it with the source
            FileInfo mapFileInfo = new FileInfo(extDeploySourceDir + "/forms/MapFile.xml");
            if (mapFileInfo.Exists)
            {
                String mapFileFile = baseAppSourceDir + "/forms/MapFile.xml";
                String extMapFileFile = extDeploySourceDir + "/forms/MapFile.xml";
                mergeChildNodesOfRootElement(mapFileFile, extMapFileFile, "name");
            }
            DirectoryInfo formsSourceInfo = new DirectoryInfo(baseAppSourceDir + "/forms");
            DirectoryInfo formsTargetInfo = new DirectoryInfo(extDeploySourceDir + "/forms");
            copyFiles(formsSourceInfo, formsTargetInfo);
            copySubDirsAndItsContents(formsSourceInfo, formsTargetInfo);

            //Code to copy files from img source to img extension excluding existing files
            DirectoryInfo imgSourceInfo = new DirectoryInfo(baseAppSourceDir + "/img");
            DirectoryInfo imgTargetInfo = new DirectoryInfo(extDeploySourceDir + "/img");
            copyFiles(imgSourceInfo, imgTargetInfo);
            copySubDirsAndItsContents(imgSourceInfo, imgTargetInfo);

            //Code to copy files from resources source to resources extension excluding existing files
            DirectoryInfo resourcesSourceInfo = new DirectoryInfo(baseAppSourceDir + "/resources");
            DirectoryInfo resourcesTargetInfo = new DirectoryInfo(extDeploySourceDir + "/resources");
            copyFiles(resourcesSourceInfo, resourcesTargetInfo);
            copySubDirsAndItsContents(resourcesSourceInfo, resourcesTargetInfo);

            //Code to copy files from webserviceconsumer source to webserviceconsumer extension excluding existing files
            DirectoryInfo webserviceconsumerSourceInfo = new DirectoryInfo(baseAppSourceDir + "/webserviceconsumer");
            DirectoryInfo webserviceconsumerTargetInfo = new DirectoryInfo(extDeploySourceDir + "/webserviceconsumer");
            copyFiles(webserviceconsumerSourceInfo, webserviceconsumerTargetInfo);
            copySubDirsAndItsContents(webserviceconsumerSourceInfo, webserviceconsumerTargetInfo);

            //Code to copy files from workflow source to workflow extension excluding existing files
            DirectoryInfo workflowSourceInfo = new DirectoryInfo(baseAppSourceDir + "/workflow");
            DirectoryInfo workflowTargetInfo = new DirectoryInfo(extDeploySourceDir + "/workflow");
            copyFiles(workflowSourceInfo, workflowTargetInfo);
            copySubDirsAndItsContents(workflowSourceInfo, workflowTargetInfo);

            //Code to copy files from source to extension excluding existing files
            DirectoryInfo sourceDir = new DirectoryInfo(baseAppSourceDir);
            DirectoryInfo extensionSourceDir = new DirectoryInfo(extDeploySourceDir);
            copyFiles(sourceDir, extensionSourceDir);
        }

        public static void copySubDirsAndItsContents(DirectoryInfo sourceInfo, DirectoryInfo targetInfo)
        {
            if (Directory.Exists(sourceInfo.FullName) == true)
            {
                DirectoryInfo[] subDirs = sourceInfo.GetDirectories();
                foreach (DirectoryInfo subDir in subDirs)
                {
                    if (subDir.Name.Equals(".svn"))
                    {
                        Console.WriteLine("do not copy svn folder and its files------------------------------------------------");
                        continue;
                    }
                    DirectoryInfo extSubDir = new DirectoryInfo(targetInfo.FullName + "/" + subDir.Name);
                    copyFiles(subDir, extSubDir);
                }
            }
        }

        public static void mergeChildNodesOfRootElement(String fileName, String extFileName, String uniqueIdAttribute)
        {
            XmlDocument mapFileDoc = new XmlDocument();
            XmlDocument extMapFileDoc = new XmlDocument();
            mapFileDoc.Load(fileName);
            extMapFileDoc.Load(extFileName);
            XmlNode extMapFileNode = extMapFileDoc.FirstChild;
            XmlNode mapFileNode = mapFileDoc.FirstChild;
            mergeChildNodes(extMapFileDoc, extMapFileNode, mapFileNode, uniqueIdAttribute);
            XmlTextWriter textWriter = new XmlTextWriter(extFileName, null);
            extMapFileDoc.WriteContentTo(textWriter);
            textWriter.Flush();
            textWriter.Close();
        }

        public static void mergeUsersFolder(String nolisSourceDir, String extDeploySourceDir)
        {
            //Code for group xml merging
            String groupFile = nolisSourceDir + "/users/Group.xml";
            String extGroupFile = extDeploySourceDir + "/users/Group.xml";
            mergeChildNodesOfRootElement(groupFile, extGroupFile, "GroupId");

            //Code for Organization xml merging
            String orgFile = nolisSourceDir + "/users/Organization.xml";
            String extOrgFile = extDeploySourceDir + "/users/Organization.xml";
            mergeChildNodesOfRootElement(orgFile, extOrgFile, "OrgName");

            //Code for rights xml merging
            String rightsFile = nolisSourceDir + "/users/rights.xml";
            String extRightsFile = extDeploySourceDir + "/users/rights.xml";
            mergeChildNodesOfRootElement(rightsFile, extRightsFile, "rightid");

            //Code for roles xml merging
            String rolesFile = nolisSourceDir + "/users/roles.xml";
            String extRolesFile = extDeploySourceDir + "/users/roles.xml";
            mergeChildNodesOfRootElement(rolesFile, extRolesFile, "roleid");

            //Code for UserPatterns xml merging
            String upFile = nolisSourceDir + "/users/UserPatterns.xml";
            String extUpFile = extDeploySourceDir + "/users/UserPatterns.xml";
            mergeChildNodesOfRootElement(upFile, extUpFile, "userpatternid");

            //Code for users xml merging
            String usersFile = nolisSourceDir + "/users/users.xml";
            String extUsersFile = extDeploySourceDir + "/users/users.xml";
            mergeChildNodesOfRootElement(usersFile, extUsersFile, "userid");

            DirectoryInfo usersSourceInfo = new DirectoryInfo(nolisSourceDir + "/users");
            DirectoryInfo extUsersSourceInfo = new DirectoryInfo(extDeploySourceDir + "/users");
            copySubDirsAndItsContents(usersSourceInfo, extUsersSourceInfo);
        }

        public static void mergeEspFile(String nolisUIFile, String nolisExtFile)
        {
            XmlDocument nolisUIDoc = new XmlDocument();
            XmlDocument nolisExtDoc = new XmlDocument();
            nolisUIDoc.Load(nolisUIFile);
            nolisExtDoc.Load(nolisExtFile);
            XmlNode extRootNode = nolisExtDoc.FirstChild;
            XmlNode rootNode = nolisUIDoc.FirstChild;

            //Code for form pattern
            XmlNode extRootFormPatternNode = extRootNode.FirstChild;
            XmlNode rootFormPatternNode = rootNode.FirstChild;
            mergeChildNodes(nolisExtDoc, extRootFormPatternNode, rootFormPatternNode, "id");

            // Code for data pattern
            XmlNode extRootDataPatternNode = extRootFormPatternNode.NextSibling;
            XmlNode rootDataPatternNode = rootFormPatternNode.NextSibling;
            mergeChildNodes(nolisExtDoc, extRootDataPatternNode, rootDataPatternNode, "id");

            // Code for workflow pattern
            XmlNode extRootWorkflowPatternNode = extRootDataPatternNode.NextSibling;
            XmlNode rootWorkflowPatternNode = rootDataPatternNode.NextSibling;
            mergeChildNodes(nolisExtDoc, extRootWorkflowPatternNode, rootWorkflowPatternNode, "id");

            XmlTextWriter textWriter = new XmlTextWriter(nolisExtFile, null);
            nolisExtDoc.WriteContentTo(textWriter);
            textWriter.Flush();
            textWriter.Close();
        }

        public static void mergeChildNodes(XmlDocument nolisExtDoc, XmlNode extNode, XmlNode rootNode, String uniqueIdAttribute)
        {
            IList itemList = new ArrayList();
            foreach (XmlNode itemNode in rootNode.ChildNodes)
            {
                if (itemNode.NodeType == XmlNodeType.Element)
                {
                    XmlNodeReader reader = new XmlNodeReader(itemNode);
                    reader.Read();
                    String idAttributeValue = reader.GetAttribute(uniqueIdAttribute);
                    //Console.WriteLine("idAttributeValue = " + idAttributeValue); 
                    reader.Close();
                    Boolean isIdExist = false;
                    foreach (XmlNode extItemNode in extNode.ChildNodes)
                    {
                        if (extItemNode.NodeType == XmlNodeType.Element)
                        {
                            XmlNodeReader localReader = new XmlNodeReader(extItemNode);
                            localReader.Read();
                            String extIdAttributeValue = localReader.GetAttribute(uniqueIdAttribute);
                            Console.WriteLine("extIdAttributeValue = " + extIdAttributeValue);
                            localReader.Close();
                            if (extIdAttributeValue.Equals(idAttributeValue))
                            {
                                isIdExist = true;
                                break;
                            }
                        }
                    }
                    if (isIdExist)
                    {
                        continue;
                    }
                    else
                    {
                        itemList.Add(itemNode);
                    }
                }
            }
            Console.WriteLine("size of itemList  = " + itemList.Count);
            foreach (Object objXmlNode in itemList)
            {
                XmlNode newNode = nolisExtDoc.ImportNode((XmlNode)objXmlNode, true);
                extNode.AppendChild(newNode);
            }
        }
    }
}