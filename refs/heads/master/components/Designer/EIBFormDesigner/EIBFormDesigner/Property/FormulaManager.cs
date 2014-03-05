using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.Properties
{
    public class FormulaManager : IFormulaManager
    {

        public FormulaManager()
        {
        }
        public void displayFormulaProperties(FormulaEditorWindow form, IEIBControl controlObject)
        {
            try
            {
                if (form.SelectedNode != null)
                {
                    if(form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.DefaultValue))
                    {
                        form.formulaText.Text = controlObject.DefaultValue;
                    }
                    if(form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnClick))
                    {
                        form.formulaText.Text = controlObject.OnClickValue;
                    }
                    else if(form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.Exiting))
                    {
                        form.formulaText.Text = controlObject.ExitingValue;
                    }
                    else if(form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.Entering))
                    {
                        form.formulaText.Text = controlObject.EnteringValue;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnDoubleClick))
                    {
                        form.formulaText.Text = controlObject.OnDoubleClick;
                    }
                }
            }
            catch (Exception exp)
            {
                Console.Write(exp.StackTrace);
            }
        }
    }
}
