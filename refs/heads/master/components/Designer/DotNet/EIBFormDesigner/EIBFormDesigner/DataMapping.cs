using System;
using System.Collections.Generic;
using System.Text;

namespace EIBFormDesigner
{
    [Serializable()]
    public class DataMapping
    {
        public DataMapping(string dataPatternName, string dataTableName, string dataFieldName)
        {
            this.dataPatternName = dataPatternName;
            this.dataTableName = dataTableName;
            this.dataFieldName = dataFieldName;
        }
        string dataPatternName;
        //[Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        public string DataPatternName
        {
            get
            {
                return dataPatternName;
            }
        }
        //[Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        string dataTableName;
        public string DataTableName
        {
            get { return dataTableName; }
        }
        //[Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        string dataFieldName;
        public string DataFieldName
        {
            get { return dataFieldName; }
        }
    }
}
