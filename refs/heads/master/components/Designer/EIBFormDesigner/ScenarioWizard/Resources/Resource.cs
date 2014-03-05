using System;
using System.Collections.Generic;
using System.Text;

namespace EIBFormDesigner.ScenarioWizard.Resources
{
    public class Resource
    {
        string resourcePath;
        public string ResourcePath
        {
            get
            {
                return resourcePath;
            }
            set
            {
                resourcePath = value;
            }
        }

        Dictionary<string , string> resourceInfo;
        public Dictionary<string, string> ResourceInfo
        {
            get
            {
                return resourceInfo;
            }
            set
            {
                resourceInfo = value;
            }
        }
    }
}
