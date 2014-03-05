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
using EIBFormDesigner.Controls;

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
                form.designTree.Nodes[0].Nodes.RemoveByKey("OnCheck");
                form.designTree.Nodes[0].Nodes.RemoveByKey("GlobalScripts");
                form.designTree.Nodes[0].Nodes.RemoveByKey("OnOpen");
                form.designTree.Nodes[0].Nodes.RemoveByKey("OnSelect");
                form.designTree.Nodes[0].Nodes.RemoveByKey("OnChanging");
                if (controlObject is EIBCheckbox)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnCheck"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onCheck = new System.Windows.Forms.TreeNode("OnCheck");
                        treeNode_onCheck.Name = "OnCheck";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onCheck);
                    }
                }
                if (controlObject is EIBPanel)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("GlobalScripts"))
                    {
                        System.Windows.Forms.TreeNode treeNode_globalScripts = new System.Windows.Forms.TreeNode("GlobalScripts");
                        treeNode_globalScripts.Name = "GlobalScripts";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_globalScripts);
                    }
                }
                if (controlObject is EIBTreeNode)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnOpen"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onOpen = new System.Windows.Forms.TreeNode("OnOpen");
                        treeNode_onOpen.Name = "OnOpen";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onOpen);
                    }
                }
                if (controlObject is EIBTreeView)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnSelect"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onSelect = new System.Windows.Forms.TreeNode("OnSelect");
                        treeNode_onSelect.Name = "OnSelect";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onSelect);
                    }
                }
                if (controlObject is EIBGrid)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnSelect"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onSelect = new System.Windows.Forms.TreeNode("OnSelect");
                        treeNode_onSelect.Name = "OnSelect";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onSelect);
                    }
                }
                if (controlObject is EIBCombobox)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnChanging"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onChanging = new System.Windows.Forms.TreeNode("OnChanging");
                        treeNode_onChanging.Name = "OnChanging";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onChanging);
                    }
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnOpen"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onOpen = new System.Windows.Forms.TreeNode("OnOpen");
                        treeNode_onOpen.Name = "OnOpen";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onOpen);
                    }

                }
                if (controlObject is EIBTextBox)
                {
                    if (!form.designTree.Nodes[0].Nodes.ContainsKey("OnChanging"))
                    {
                        System.Windows.Forms.TreeNode treeNode_onChanging = new System.Windows.Forms.TreeNode("OnChanging");
                        treeNode_onChanging.Name = "OnChanging";
                        form.designTree.Nodes[0].Nodes.Add(treeNode_onChanging);
                    }
                }
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
