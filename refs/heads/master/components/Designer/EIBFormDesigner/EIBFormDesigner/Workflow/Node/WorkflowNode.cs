using System;
using System.Collections.Generic;
using System.Text;
using EIBFormDesigner.Designer;
using EIBFormDesigner.UserAdmin.Utility;

namespace EIBFormDesigner.Workflow.Node
{
    public class WorkflowNode
    {
        string workflowId;
        DataPatterns datapatterns;
        DataObject dataObject;
        UserPatterns userPatterns;
        FormPatterns formpatterns;
        public WorkflowNode()
        {
            FormPatterns = new FormPatterns();
            DataPatterns = new DataPatterns();
            UserPatterns = new UserPatterns();
            dataObject = new DataObject();
        }
        public FormPatterns FormPatterns
        {
            get { return formpatterns; }
            set { formpatterns = value; }
        }
        public string WorkFlowNodeId
        {
            get
            {
                return workflowId;
            }
            set
            {
                workflowId = value;
            }
        }

        public DataPatterns DataPatterns
        {
            get
            {
                return datapatterns;
            }
            set
            {
                datapatterns = value;
            }
        }
        public DataObject DataObject
        {
            get
            {
                return dataObject;
            }
            set
            {
                dataObject = value;
            }
        }
        public UserPatterns UserPatterns
        {
            get
            {
                return userPatterns;
            }
            set
            {
                userPatterns = value;
            }
        }

    }
}
