namespace EIBFormDesigner.Search
{
    partial class SearchUserControl
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.operatorComboBox = new System.Windows.Forms.ComboBox();
            this.condition = new System.Windows.Forms.Label();
            this.fieldValue = new System.Windows.Forms.TextBox();
            this.conditionList = new System.Windows.Forms.ComboBox();
            this.label4 = new System.Windows.Forms.Label();
            this.fieldList = new System.Windows.Forms.ComboBox();
            this.removeButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(-142, 37);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(75, 17);
            this.label1.TabIndex = 9;
            this.label1.Text = "FieldName";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(229, 7);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 17);
            this.label3.TabIndex = 17;
            this.label3.Text = "Operator";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(169, 9);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(44, 17);
            this.label2.TabIndex = 16;
            this.label2.Text = "Value";
            // 
            // operatorComboBox
            // 
            this.operatorComboBox.FormattingEnabled = true;
            this.operatorComboBox.Items.AddRange(new object[] {
            "AND",
            "OR"});
            this.operatorComboBox.Location = new System.Drawing.Point(229, 26);
            this.operatorComboBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.operatorComboBox.Name = "operatorComboBox";
            this.operatorComboBox.Size = new System.Drawing.Size(75, 21);
            this.operatorComboBox.TabIndex = 4;
            // 
            // condition
            // 
            this.condition.AutoSize = true;
            this.condition.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.condition.Location = new System.Drawing.Point(98, 9);
            this.condition.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.condition.Name = "condition";
            this.condition.Size = new System.Drawing.Size(67, 17);
            this.condition.TabIndex = 14;
            this.condition.Text = "Condition";
            // 
            // fieldValue
            // 
            this.fieldValue.Location = new System.Drawing.Point(169, 28);
            this.fieldValue.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.fieldValue.Name = "fieldValue";
            this.fieldValue.Size = new System.Drawing.Size(55, 20);
            this.fieldValue.TabIndex = 3;
            // 
            // conditionList
            // 
            this.conditionList.FormattingEnabled = true;
            this.conditionList.Items.AddRange(new object[] {
            "=",
            "!=",
            ">",
            "<",
            ">=",
            "<="});
            this.conditionList.Location = new System.Drawing.Point(98, 28);
            this.conditionList.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.conditionList.Name = "conditionList";
            this.conditionList.Size = new System.Drawing.Size(68, 21);
            this.conditionList.TabIndex = 2;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(2, 9);
            this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(75, 17);
            this.label4.TabIndex = 11;
            this.label4.Text = "FieldName";
            // 
            // fieldList
            // 
            this.fieldList.FormattingEnabled = true;
            this.fieldList.Location = new System.Drawing.Point(2, 28);
            this.fieldList.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.fieldList.Name = "fieldList";
            this.fieldList.Size = new System.Drawing.Size(84, 21);
            this.fieldList.TabIndex = 1;
            this.fieldList.SelectedIndexChanged += new System.EventHandler(this.fieldList_SelectedIndexChanged);
            // 
            // removeButton
            // 
            this.removeButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.removeButton.Location = new System.Drawing.Point(308, 26);
            this.removeButton.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.removeButton.Name = "removeButton";
            this.removeButton.Size = new System.Drawing.Size(70, 24);
            this.removeButton.TabIndex = 5;
            this.removeButton.Text = "&Remove";
            this.removeButton.UseVisualStyleBackColor = true;
            // 
            // SearchUserControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.removeButton);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.operatorComboBox);
            this.Controls.Add(this.condition);
            this.Controls.Add(this.fieldValue);
            this.Controls.Add(this.conditionList);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.fieldList);
            this.Controls.Add(this.label1);
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "SearchUserControl";
            this.Size = new System.Drawing.Size(396, 73);
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.SearchUserControl_KeyUp);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        internal System.Windows.Forms.ComboBox operatorComboBox;
        private System.Windows.Forms.Label condition;
        internal System.Windows.Forms.TextBox fieldValue;
        internal System.Windows.Forms.ComboBox conditionList;
        private System.Windows.Forms.Label label4;
        internal System.Windows.Forms.ComboBox fieldList;
        internal System.Windows.Forms.Button removeButton;
    }
}
