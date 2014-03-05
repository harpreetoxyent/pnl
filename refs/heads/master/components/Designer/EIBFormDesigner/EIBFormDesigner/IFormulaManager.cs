using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Designer;
using System.ComponentModel;
using EIBFormDesigner.Workflow.Common;

namespace EIBFormDesigner
{
    public interface IFormulaManager
    {
        void displayFormulaProperties(FormulaEditorWindow form, IEIBControl controlObject);
        void displayWorkFlowNodeProperties(WorkflowEditorWindow form, INodeControl controlObject);
    }
}
