using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;

namespace EIBFormDesigner.Controls
{
    public class DataMappingConverter:TypeConverter
    {
        public override object ConvertTo(ITypeDescriptorContext context, System.Globalization.CultureInfo culture, object value, Type destType)
        {
            if (destType == typeof(string))
            {
                if (value is List<DataMapping>)
                {
                    return "Data Mappings.";
                }
                else if (value == null)
                {
                    return "No Data Mappings.";
                }
            }
            return base.ConvertTo(context, culture, value, destType);
        }

    }
}
