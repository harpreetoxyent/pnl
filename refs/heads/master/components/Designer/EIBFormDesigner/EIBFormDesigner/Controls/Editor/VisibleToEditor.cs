using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using System.Windows.Forms.Design;
using System.Drawing.Design;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls
{
    public class VisibleToEditor:UITypeEditor
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
                    VisibleToEditorForm modalEditor = new VisibleToEditorForm();
                    if (value != null)
                    {
                        modalEditor.VisibleToRights = (List<string>)value;
                    }
                    else
                    {
                        modalEditor.visibleToRights = new List<string>();
                    }
                    if (editorService.ShowDialog(modalEditor) == DialogResult.OK)
                    {
                        return modalEditor.VisibleToRights;
                    }
                }
            }
            return base.EditValue(context, provider, value);
        }
    }
}
