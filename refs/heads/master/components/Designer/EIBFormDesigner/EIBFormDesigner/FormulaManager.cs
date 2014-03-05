using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Reflection;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Workflow.Common;

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
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnDrop))
                    {
                        form.formulaText.Text = controlObject.OnDrop;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnFocus))
                    {
                        form.formulaText.Text = controlObject.OnFocus;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnBlur))
                    {
                        form.formulaText.Text = controlObject.OnBlur;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventCreate))
                    {
                        form.formulaText.Text = controlObject.OnEventCreateValue;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventEdit))
                    {
                        form.formulaText.Text = controlObject.OnEventEditValue;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnEventUpdate))
                    {
                        form.formulaText.Text = controlObject.OnEventUpdateValue;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnChange))
                    {
                        form.formulaText.Text = controlObject.OnChange;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnOK))
                    {
                        form.formulaText.Text = controlObject.OnOK;
                    }
                }
            }
            catch (Exception exp)
            {
                Console.Write(exp.StackTrace);
            }
        }
        public void displayWorkFlowNodeProperties(WorkflowEditorWindow form, INodeControl controlObject)
        {
            try
            {
                INodeControl currentNode = controlObject;
                if (form.SelectedNode != null)
                {
                    if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.OnNode))
                    {
                        form.formulaText.Text = currentNode.NodeValue;
                    }
                    if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.EnterNode))
                    {
                        form.formulaText.Text = currentNode.OnNodeEnterValue;
                    }
                    else if (form.SelectedNode.Name.Trim().Equals(FormDesignerConstants.ExitNode))
                    {
                        form.formulaText.Text = currentNode.OnNodeExitClick;
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
