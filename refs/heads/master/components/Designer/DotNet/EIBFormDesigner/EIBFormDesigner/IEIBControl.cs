using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.XML;
using System.Xml;
using System.ComponentModel;
using EIBFormDesigner.Controls;
using System.Drawing.Design;

namespace EIBFormDesigner
{
    public interface IEIBControl:ICustomVisible
    {
        XmlNode ParentXmlNode
        {
            get;

            set;
        }
        string ControlName
        {
            get;
            set;
        }

        //string UniqueId
        //{
        //    get;
        //    set;

        //}

        string ControlType
        {
            get;
        }
        string IsMandatory
        {
            get;
            set;
        }
        string IsForm
        {
            get;
            set;
        }
        string DefaultValue
        {
            get;
            set;
        }
        string OnClickValue
        {
            get;
            set;
        }
        string OnDoubleClick
        {
            get;
            set;
        }
        string EnteringValue
        {
            get;
            set;
        }
        string ExitingValue
        {
            get;
            set;
        }
        string OnDrop
        {
            get;
            set;
        }
        string OnFocus
        {
            get;
            set;
        }
        string OnBlur
        {
            get;
            set;
        }

        string OnEventCreateValue
        {
            get;
            set;
        }
        string OnEventEditValue
        {
            get;
            set;
        }
        string OnEventUpdateValue
        {
            get;
            set;
        }
        string OnChange
        {
            get;
            set;
        }
        string OnOK
        {
            get;
            set;
        }
       
       
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        List<DataMapping> DataMappings
        {
            get;
            set;
        }

        [Browsable(true), DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Draggable property of control"), Category("Appearance")]
        bool Draggable
        {
            get;
            set;
        }

        [Browsable(true),DesignerSerializationVisibility(DesignerSerializationVisibility.Content), Description("Sets the Droppable property of control"), Category("Appearance")]
        bool Droppable
        {
            get;
            set;
        }

        ControlPosition Position
        {
            get;
            set;
        }

        [DisplayName("Margin"), Browsable(true), Category("Data"), Description("Set the Margin of a control.")]
        Padding ControlMargin { get; set; }

        string Use
        {
            get;
            set;
        }

        [Browsable(true), Description("Set VisibleTo property for the control"), Category("Data"), EditorAttribute(typeof(VisibleToEditor), typeof(UITypeEditor)), TypeConverter(typeof(VisibleToConverter))]
        List<string> VisibleTo
        {
            get;
            set;
        }


        /*[Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        string DataPatternName
        {
            get;
            set;
        }
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        string DataTableName
        {
            get;
            set;
        }
        [Browsable(true), EditorBrowsable(EditorBrowsableState.Never), Description("Sets the name of datapattern"), Category("Data")]
        string DataFieldName
        {
            get;
            set;
        }*/

    }
}
