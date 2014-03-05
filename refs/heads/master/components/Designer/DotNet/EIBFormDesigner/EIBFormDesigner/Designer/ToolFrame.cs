using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Event;

namespace EIBFormDesigner.Designer
{
    public class ToolFrame:Panel
    {
        private System.Windows.Forms.Button labelControl;
        private System.Windows.Forms.Button textBoxControl;
        private System.Windows.Forms.Button buttonControl;
        private System.Windows.Forms.Button treeControl;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage designFields;
        private System.Windows.Forms.TabPage databaseFields;
        private System.Windows.Forms.Button connectorControl;
        private System.Windows.Forms.Button tableControl;
        private System.Windows.Forms.Button frameControl;
        private System.Windows.Forms.Button multiTabControl;

        public ToolFrame()
        {
        }
        public void Initialize()
        {
            this.tabControl = new System.Windows.Forms.TabControl();
            this.designFields = new System.Windows.Forms.TabPage();
            this.databaseFields = new System.Windows.Forms.TabPage();

            this.treeControl = new System.Windows.Forms.Button();
            this.buttonControl = new System.Windows.Forms.Button();
            this.textBoxControl = new System.Windows.Forms.Button();
            this.labelControl = new System.Windows.Forms.Button();
            this.connectorControl = new System.Windows.Forms.Button();
            this.tableControl = new System.Windows.Forms.Button();
            this.frameControl = new System.Windows.Forms.Button();
            this.multiTabControl = new System.Windows.Forms.Button();

            this.tabControl.SuspendLayout();
            this.designFields.SuspendLayout();
            this.databaseFields.SuspendLayout();

            // tabControl
            // 
            this.tabControl.Controls.Add(this.designFields);
            this.tabControl.Controls.Add(this.databaseFields);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Location = new System.Drawing.Point(0, 0);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(217, 526);
            this.tabControl.TabIndex = 0;
            // tableControl
            // 
            this.tableControl.AllowDrop = true;
            this.tableControl.Location = new System.Drawing.Point(6, 15);
            this.tableControl.Name = "tableControl";
            this.tableControl.Size = new System.Drawing.Size(94, 37);
            this.tableControl.TabIndex = 1;
            this.tableControl.Text = "Table";
            this.tableControl.UseVisualStyleBackColor = true;
            // 
            // frameControl
            // 
            this.frameControl.AllowDrop = true;
            this.frameControl.Location = new System.Drawing.Point(3, 3);
            this.frameControl.Name = "frameControl";
            this.frameControl.Size = new System.Drawing.Size(75, 37);
            this.frameControl.TabIndex = 0;
            this.frameControl.Text = "Frame";
            this.frameControl.UseVisualStyleBackColor = true;
            // 
            // 
            // treeControl
            // 
            this.treeControl.Location = new System.Drawing.Point(3, 114);
            this.treeControl.Name = "treeControl";
            this.treeControl.Size = new System.Drawing.Size(75, 37);
            this.treeControl.TabIndex = 4;
            this.treeControl.Text = "Tree";
            this.treeControl.UseVisualStyleBackColor = true;
            // 
            // buttonControl
            // 
            this.buttonControl.Location = new System.Drawing.Point(100, 60);
            this.buttonControl.Name = "buttonControl";
            this.buttonControl.Size = new System.Drawing.Size(75, 37);
            this.buttonControl.TabIndex = 3;
            this.buttonControl.Text = "Button";
            this.buttonControl.UseVisualStyleBackColor = true;
            // 
            // textBoxControl
            // 
            this.textBoxControl.Location = new System.Drawing.Point(3, 60);
            this.textBoxControl.Name = "textBoxControl";
            this.textBoxControl.Size = new System.Drawing.Size(75, 37);
            this.textBoxControl.TabIndex = 2;
            this.textBoxControl.Text = "TextBox";
            this.textBoxControl.UseVisualStyleBackColor = true;
            // 
            // labelControl
            // 
            this.labelControl.Location = new System.Drawing.Point(100, 6);
            this.labelControl.Name = "labelControl";
            this.labelControl.Size = new System.Drawing.Size(75, 37);
            this.labelControl.TabIndex = 1;
            this.labelControl.Text = "Label";
            this.labelControl.UseVisualStyleBackColor = true;
            // 
            // 
            // multiTabControl
            // 
            this.multiTabControl.Location = new System.Drawing.Point(100, 114);
            this.multiTabControl.Name = "multiTabControl";
            this.multiTabControl.Size = new System.Drawing.Size(75, 37);
            this.multiTabControl.TabIndex = 5;
            this.multiTabControl.Text = "MultiTab";
            this.multiTabControl.UseVisualStyleBackColor = true;
            // 
            // designFields
            // 
            this.designFields.Controls.Add(this.treeControl);
            this.designFields.Controls.Add(this.buttonControl);
            this.designFields.Controls.Add(this.textBoxControl);
            this.designFields.Controls.Add(this.labelControl);
            this.designFields.Controls.Add(this.frameControl);
            this.designFields.Controls.Add(this.multiTabControl);
            this.designFields.Location = new System.Drawing.Point(4, 25);
            this.designFields.Name = "designFields";
            this.designFields.Padding = new System.Windows.Forms.Padding(3);
            this.designFields.Size = new System.Drawing.Size(209, 497);
            this.designFields.TabIndex = 0;
            this.designFields.Text = "Design Fields";
            this.designFields.UseVisualStyleBackColor = true;

            // 
            // databaseFields
            // 
            this.databaseFields.Controls.Add(this.connectorControl);
            this.databaseFields.Controls.Add(this.tableControl);
            this.databaseFields.Location = new System.Drawing.Point(4, 25);
            this.databaseFields.Name = "databaseFields";
            this.databaseFields.Padding = new System.Windows.Forms.Padding(3);
            this.databaseFields.Size = new System.Drawing.Size(209, 497);
            this.databaseFields.TabIndex = 1;
            this.databaseFields.Text = "Database Fields";
            this.databaseFields.UseVisualStyleBackColor = true;
            // 
            // connectorControl
            // 
            this.connectorControl.AllowDrop = true;
            this.connectorControl.Location = new System.Drawing.Point(8, 58);
            this.connectorControl.Name = "connectorControl";
            this.connectorControl.Size = new System.Drawing.Size(92, 37);
            this.connectorControl.TabIndex = 2;
            this.connectorControl.Text = "Connector";
            this.connectorControl.UseVisualStyleBackColor = true;


            this.Controls.Add(this.tabControl);
            this.tabControl.ResumeLayout(false);
            this.designFields.ResumeLayout(false);
            this.databaseFields.ResumeLayout(false);
            this.PerformLayout();


            RegisterDragDrop();
        }
        public void RegisterDragDrop()
        {
            DragDropHandler.RegisterControl(labelControl, false, true);
            DragDropHandler.RegisterControl(frameControl, false, true);
            DragDropHandler.RegisterControl(textBoxControl, false, true);
            DragDropHandler.RegisterControl(buttonControl, false, true);
            DragDropHandler.RegisterControl(treeControl, false, true);
            DragDropHandler.RegisterControl(multiTabControl, false, true);
            DragDropHandler.RegisterControl(tableControl, false, true);
            DragDropHandler.RegisterControl(connectorControl, false, true);

        }

    }
}
