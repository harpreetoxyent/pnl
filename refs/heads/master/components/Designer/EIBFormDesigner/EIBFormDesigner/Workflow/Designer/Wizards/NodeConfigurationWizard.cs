using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WeifenLuo.WinFormsUI.Docking;
using EIBFormDesigner.Workflow;
using EIBFormDesigner.Workflow.Common;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.Workflow.Designer.Wizards;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner;
using EIBFormDesigner.Workflow.Designer.Forms;

namespace EIBFormDesigner.Workflow.Designer.Wizards
{
    public partial class NodeConfigurationWizard : Form
    {
        List<DockContent> listofTabs = new List<DockContent>();
        public NodeConfigurationWizard(EIBNode node)
        {
            listofTabs = new List<DockContent>();

            InitializeComponent();
            InitializaTab(node);
            //this.dockPanel.Extender
        }
        public void InitializaTab(EIBNode node)
        {
            InitializeWorkflowFormPatternTab(node);
            InitializeWorkflowDataPatternTab(node);
            InitializeWorkflowUserPatternTab(node);
            InitializeWorkflowNodeStatusTab(node);
            listofTabs[0].Show(dockPanel, DockState.Document);
        }
        public void InitializeWorkflowFormPatternTab(EIBNode node)
        {
            WorkflowFormPattern workflowFormPattern = WorkflowFormPattern.CreateInstance(true,node);
            workflowFormPattern.CheckListofFormpatterns();
            workflowFormPattern.Show(dockPanel, DockState.Document);
            workflowFormPattern.TabText = "WorkflowFormPattern";
            workflowFormPattern.PanelPane.AllowDockDragAndDrop = false;
            workflowFormPattern.button1.Visible = false;
            workflowFormPattern.button2.Visible = false;
            listofTabs.Add(workflowFormPattern);
        }
        public void InitializeWorkflowDataPatternTab(EIBNode node)
        {
            WorkflowDataPattern workflowDataPattern = WorkflowDataPattern.CreateInstance(true,node);
            workflowDataPattern.CheckListofDatapatterns();
            workflowDataPattern.Show(dockPanel, DockState.Document);
            workflowDataPattern.TabText = "WorkflowDataPattern";
            workflowDataPattern.PanelPane.AllowDockDragAndDrop = false;
            workflowDataPattern.button1.Visible = false;
            workflowDataPattern.button2.Visible = false;
            listofTabs.Add(workflowDataPattern);  
        }
         public void InitializeWorkflowUserPatternTab(EIBNode node)
        {
            WorkflowUserPattern workflowuserpattern = WorkflowUserPattern.CreateInstance(true,node);
            workflowuserpattern.CheckListofUserpatterns();
            workflowuserpattern.Show(dockPanel, DockState.Document);
            workflowuserpattern.TabText = "WorkflowUserPattern";
            workflowuserpattern.PanelPane.AllowDockDragAndDrop = false;
            workflowuserpattern.button3.Visible = false;
            workflowuserpattern.button4.Visible = false;
            listofTabs.Add(workflowuserpattern);
        }
        public void InitializeWorkflowNodeStatusTab(EIBNode node)
        {
            WorkflowNodeStatus workflownodestatus = WorkflowNodeStatus.CreateInstance(true, node);
            workflownodestatus.setNodeStatus(node.WorkFlowNode.DataObject.Status);
            workflownodestatus.Show(dockPanel, DockState.Document);
            workflownodestatus.TabText = "WorkflowNodeStatus";
            workflownodestatus.PanelPane.AllowDockDragAndDrop = false;
            workflownodestatus.button1.Visible = false;
            workflownodestatus.button2.Visible = false;
            listofTabs.Add(workflownodestatus);
        }


        private void doneWorkflow_Click(object sender, EventArgs e)
        {
            //if (MessageBox.Show("Click on Apply to save Changes.", "Nolis App Designer", MessageBoxButtons.OKCancel)==DialogResult.Cancel)
            //{
            foreach(object tab in listofTabs)
            {
                if (tab.GetType() == typeof(WorkflowFormPattern))
                {
                    ((WorkflowFormPattern)tab).button1_Click(null, null);
                }
                if (tab.GetType() == typeof(WorkflowDataPattern))
                {
                    ((WorkflowDataPattern)tab).button1_Click(null, null);
                }
                if (tab.GetType() == typeof(WorkflowUserPattern))
                {
                    ((WorkflowUserPattern)tab).button1_Click(null, null);
                }
                if (tab.GetType() == typeof(WorkflowNodeStatus))
                {
                    ((WorkflowNodeStatus)tab).button1_Click(null, null);
                }
            }
                this.Close();
         //   }
        }

        private void cancelWorkflow_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}