using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WeifenLuo.WinFormsUI.Docking;
using EIBFormDesigner.Event;

namespace EIBFormDesigner.Designer
{
    public partial class ToolBoxWindow : ToolWindow
    {
        public static FormDesigner form = null;
        public ToolBoxWindow()
        {
            InitializeComponent();
            RegisterDragDrop();
            this.designFields.VerticalScroll.Visible = true;
        }
        public void RegisterDragDrop()
        {
            DragDropHandler.RegisterControl(labelControl, false, true);
            DragDropHandler.RegisterControl(frameControl, false, true);
            DragDropHandler.RegisterControl(jasperControl, false, true);
            DragDropHandler.RegisterControl(textBoxControl, false, true);
            DragDropHandler.RegisterControl(buttonControl, false, true);
            DragDropHandler.RegisterControl(browseControl, false, true);
            DragDropHandler.RegisterControl(datePickerControl, false, true);
            DragDropHandler.RegisterControl(treeControl, false, true);
            DragDropHandler.RegisterControl(treeNodeControl, false, true);
            DragDropHandler.RegisterControl(multiTabControl, false, true);
            DragDropHandler.RegisterControl(tabPageControl, false, true);
            DragDropHandler.RegisterControl(tableControl, false, true);
            DragDropHandler.RegisterControl(connectorControl, false, true);
            DragDropHandler.RegisterControl(pictureControl, false, true);
            DragDropHandler.RegisterControl(checkboxControl, false, true);
            DragDropHandler.RegisterControl(radioControl, false, true);
            DragDropHandler.RegisterControl(comboControl, false, true);
            //DragDropHandler.RegisterControl(menuBarControl, false, true);
            DragDropHandler.RegisterControl(menuControl, false, true);
            DragDropHandler.RegisterControl(menuItemControl, false, true);
            DragDropHandler.RegisterControl(relationControl, false, true);
            DragDropHandler.RegisterControl(nodeControl, false, true);
            DragDropHandler.RegisterControl(nodeConnectorControl, false, true);
            // DragDropHandler.RegisterControl(searchControl, false, true);
            DragDropHandler.RegisterControl(listControl, false, true);
            DragDropHandler.RegisterControl(gridControl, false, true);
            DragDropHandler.RegisterControl(scrollablerowControl, false, true);
            DragDropHandler.RegisterControl(columnControl, false, true);
            DragDropHandler.RegisterControl(radioGroupControl, false, true);
            DragDropHandler.RegisterControl(PlaceHolderControl, false, true);
            DragDropHandler.RegisterControl(timeControl, false, true);
            DragDropHandler.RegisterControl(pagingControl, false, true);
            DragDropHandler.RegisterControl(vMenuBarControl, false, true);
            DragDropHandler.RegisterControl(calenderControl, false, true);
            DragDropHandler.RegisterControl(imageBrowseControl, false, true);
            DragDropHandler.RegisterControl(schedularControl, false, true);
            DragDropHandler.RegisterControl(appletControl, false, true);
            DragDropHandler.RegisterControl(latticeControl, false, true);
        }

        public ImageList imageList
        {
            get { return this.toobarImageList; }
        }

        public void LoadFormPatternAsUserControl(string UserControlName)
        {

        }

        private void pictureControl_Click(object sender, EventArgs e)
        {

        }

        private void labelControl_Click(object sender, EventArgs e)
        {

        }

        private void treeNodeControl_Click(object sender, EventArgs e)
        {

        }

        private void treeControl_Click(object sender, EventArgs e)
        {

        }

        private void scrollablerowControl_Click(object sender, EventArgs e)
        {

        }

        private void frameControl_Click(object sender, EventArgs e)
        {

        }

        private void radioControl_Click(object sender, EventArgs e)
        {

        }

        private void menuBarControl_Click(object sender, EventArgs e)
        {

        }

        private void menuControl_Click(object sender, EventArgs e)
        {

        }

        private void menuItemControl_Click(object sender, EventArgs e)
        {

        }

        private void buttonControl_Click(object sender, EventArgs e)
        {

        }

        private void searchControl_Click(object sender, EventArgs e)
        {

        }

        private void multiTabControl_Click(object sender, EventArgs e)
        {

        }

        private void hMenuBarControl_Click(object sender, EventArgs e)
        {

        }

        private void designFields_Click(object sender, EventArgs e)
        {

        }

        private void imageBrowseControl_Click(object sender, EventArgs e)
        {

        }

        private void schedularControl_Click(object sender, EventArgs e)
        {

        }


    }
}