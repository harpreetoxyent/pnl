using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Designer;
using EIBFormDesigner.Workflow.Controls;

namespace EIBFormDesigner.Workflow.Node
{
    public partial class NodeRelationManager : Form
    {
        private EIBPanel panelControl;
        public BaseWindow currentBaseWindow;
        public EIBPanel PanelControl
        {
            get
            {
                return panelControl;
            }
            set
            {
                panelControl = value;
            }
        }

        public NodeRelationManager()
        {
            InitializeComponent();
            this.eibRelation1.Done.Click += new System.EventHandler(this.Done_Click);
            this.eibRelation1.Cancel.Click += new System.EventHandler(this.Cancel_Click);
        }

        private void eibRelation1_Load(object sender, EventArgs e)
        {

        }
        private void Done_Click(object sender, EventArgs e)
        {
            if (this.eibRelation1.relationName.Text.Trim().Equals(""))
            {
                this.eibRelation1.errorLabel.Text = "RelationShip Name can not be empty";
                return;
            }
            if (this.eibRelation1.firstTableName.SelectedItem == null )
            {
                this.eibRelation1.errorLabel.Text = "Select First Node";
                return;
            }
            if (this.eibRelation1.secondTableName.SelectedItem == null)
            {
                this.eibRelation1.errorLabel.Text = "Select Second Node";
                return;
            }

            try
            {
                EIBNodeConnector nodeConnector = new EIBNodeConnector();
                nodeConnector.Name = this.eibRelation1.relationName.Text;
                nodeConnector.InitiateSettings(panelControl);
                nodeConnector.Mark1 = (EIBNode)currentBaseWindow.WorkflowNodes[this.eibRelation1.firstTableName.SelectedItem.ToString()];
                nodeConnector.Mark2 = (EIBNode)currentBaseWindow.WorkflowNodes[this.eibRelation1.secondTableName.SelectedItem.ToString()];
                nodeConnector.createLine();
                panelControl.Controls.Add(nodeConnector);
                FormDesigner.eventManager.addPropertiesControl(nodeConnector.CenterMark);

            }
            catch (Exception ex)
            {
                this.eibRelation1.errorLabel.Text = ex.Message;
            }
            this.Close();
        }

        private void Cancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void NodeRelationManager_Load(object sender, EventArgs e)
        {

        }
        public void loadWorkflowNodes(EIBPanel panelControl)
        {
            this.panelControl = panelControl;
            foreach (NodeUserControl control in currentBaseWindow.WorkflowNodes.Values)
            {
                this.eibRelation1.firstTableName.Items.Add(control.WorkFlowNode.WorkFlowNodeId);
                this.eibRelation1.secondTableName.Items.Add(control.WorkFlowNode.WorkFlowNodeId);
            }

        }
    }
}