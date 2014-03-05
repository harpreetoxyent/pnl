using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;

namespace EIBFormDesigner.Property
{
    class CustomFormatConverter : StringConverter 
    {
        public override bool GetStandardValuesSupported(
                           ITypeDescriptorContext context)
        {
            return true;
        }
        public override StandardValuesCollection
                     GetStandardValues(ITypeDescriptorContext context)
        {
            return new StandardValuesCollection(new string[]{
                                                     "yyyy-MM-dd", 
                                                     "MM-dd-yyyy", 
                                                     "dd-MM-yyyy",
                                                     "short",
                                                     "medium",
                                                     "long",
                                                     "long+medium"
             
            
            });
        }
        public override bool GetStandardValuesExclusive(
                           ITypeDescriptorContext context)
        {
            return false;
        }
    }
}
