using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using System.Windows.Forms.Design;
using System.Drawing.Design;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls
{
    public class DataMappingEditor:UITypeEditor
    {
        public override UITypeEditorEditStyle GetEditStyle(ITypeDescriptorContext context)
        {
            if (context != null)
            {
                return UITypeEditorEditStyle.Modal;
            }
            return base.GetEditStyle(context);
        }

        public override object EditValue(ITypeDescriptorContext context, IServiceProvider provider, object value)
        {
            if ((context != null) && (provider != null))
            {
                // Access the Property Browser's UI display service
                IWindowsFormsEditorService editorService =
                (IWindowsFormsEditorService)
                provider.GetService(typeof(IWindowsFormsEditorService));

                if (editorService != null)
                {
                    DataMappingEditorForm modalEditor = new DataMappingEditorForm();
                    modalEditor.DataMappings = (List<DataMapping>)value;
                    if (editorService.ShowDialog(modalEditor) == DialogResult.OK)
                    {
                        return modalEditor.DataMappings;
                    }
                }
            }
            return base.EditValue(context, provider, value);
        }
    }
}
