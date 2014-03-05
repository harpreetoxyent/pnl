using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;

namespace EIBFormDesigner.Controls
{
    public class VisibleToConverter:TypeConverter
    {
        public override object ConvertTo(ITypeDescriptorContext context, System.Globalization.CultureInfo culture, object value, Type destType)
        {
            if (destType == typeof(string))
            {
                if (value is List<string>)
                {
                    return "Rights";
                }
                else if (value == null)
                {
                    return "All";
                }
            }
            return base.ConvertTo(context, culture, value, destType);
        }

    }
}
