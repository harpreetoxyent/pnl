using System;
using System.Collections.Generic;
using System.Text;
using  System.Text.RegularExpressions;
using EIBFormDesigner.Designer;


namespace EIBFormDesigner.Controls.Common
{
    public class ControlValidation
    {
        public static string validateUniqueId(string uniqueId)
        {
            string retVal = "";

            if (uniqueId == "")
            {
                retVal = "UniqueId cannot be null";
            }
            else if (uniqueId.Contains("-"))
            {
                retVal = "UniqueId mustnot contain hyphen(-).";
            }
            else if (uniqueId == "" || uniqueId == null)
            {
                retVal = "UniqueId can not be null";
            }
            else if (uniqueId.Contains(" "))
            {
               retVal = "Please remove blanks from UniqueId";
            }
            else if (!(uniqueId == null))
            {
                Regex r = new Regex("^[a-zA-Z0-9_]*$");
                if (r.IsMatch(uniqueId))
                {
                    retVal = "";
                }
                else
                {
                    retVal = "Only alphanumeric characters may be used";
                }
            }  
       



            return retVal;
        }

        public static bool doesUniqueIdExist(string uniqueId)
        {
            string uidPrefix = EIBFormDesigner.Program.currentForm.currentBaseWindow.UniqueID.ToLower() + ".";
            return FormDesignerUtilities.formWindowNames.Contains(uidPrefix + uniqueId.ToLower()); ;
        }

        public static void addUniqueIdToList(string uniqueId)
        {
            string uidPrefix = EIBFormDesigner.Program.currentForm.currentBaseWindow.UniqueID.ToLower() + ".";
            FormDesignerUtilities.formWindowNames.Add(uidPrefix + uniqueId, uniqueId.ToLower());
        }

    }
}
