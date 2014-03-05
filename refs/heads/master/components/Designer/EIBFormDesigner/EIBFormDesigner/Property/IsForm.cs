using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;

namespace EIBFormDesigner.Property
{
    class IsForm : StringConverter 
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
                                                     "yes", 
                                                     "no"});
        }
        public override bool GetStandardValuesExclusive(
                           ITypeDescriptorContext context)
        {
            return false;
        }
    }
}
