using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.Collections;
using System.Globalization;
using System.Reflection;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.ComponentModel;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using EIBFormDesigner.Controls;
using EIBFormDesigner.Workflow.Node;
using EIBFormDesigner.Workflow.Controls;
using EIBFormDesigner.Event;

namespace EIBFormDesigner.Tools
{
    public class ToolBox : IToolBox
    {
        private static EIBFormDesigner.Designer.FormDesigner eibFormDesigner = null;
        private Dictionary<string, Type> listTools = new Dictionary<string, Type>();
        public ToolBox(EIBFormDesigner.Designer.FormDesigner formDesigner)
        {
            eibFormDesigner = formDesigner;
            initTools();
        }
        private void initTools()
        {
            listTools.Add(FormDesignerConstants.LabelControl, typeof(EIBLabel));
            listTools.Add(FormDesignerConstants.ButtonControl, typeof(EIBButton));
            listTools.Add(FormDesignerConstants.BrowseControl, typeof(EIBBrowse));
            listTools.Add(FormDesignerConstants.DatePickerControl, typeof(EIBDatePicker));
            listTools.Add(FormDesignerConstants.TimeControl, typeof(EIBTime));
            listTools.Add(FormDesignerConstants.FrameControl, typeof(EIBPanel));
            listTools.Add(FormDesignerConstants.JasperControl, typeof(EIBJasper));
            listTools.Add(FormDesignerConstants.RadioGroupControl, typeof(EIBRadioGroup));
            listTools.Add(FormDesignerConstants.TextBoxControl, typeof(EIBTextBox));
            listTools.Add(FormDesignerConstants.TreeControl, typeof(EIBTreeView));
            listTools.Add(FormDesignerConstants.TreeNodeControl, typeof(EIBTreeNode));
            listTools.Add(FormDesignerConstants.MultiTabControl, typeof(EIBTabControl));
            listTools.Add(FormDesignerConstants.TabPage, typeof(EIBTabPage));
            listTools.Add(FormDesignerConstants.TableControl, typeof(EIBTable));
            listTools.Add(FormDesignerConstants.RelationControl, typeof(EIBRelation));
            listTools.Add(FormDesignerConstants.NodeControl, typeof(EIBNode));
            listTools.Add(FormDesignerConstants.NodeConnectorControl, typeof(EIBNodeConnector));
            listTools.Add(FormDesignerConstants.LineControl, typeof(EIBLine));
            listTools.Add(FormDesignerConstants.ConnectorControl, typeof(EIBLine));
            listTools.Add(FormDesignerConstants.PictureControl, typeof(EIBPicture));
            listTools.Add(FormDesignerConstants.CheckboxControl, typeof(EIBCheckbox));
            listTools.Add(FormDesignerConstants.RadioControl, typeof(EIBRadioButton));
            listTools.Add(FormDesignerConstants.ComboControl, typeof(EIBCombobox));
            //listTools.Add(FormDesignerConstants.SearchControl, typeof(EIBSearch));
            listTools.Add(FormDesignerConstants.ListControl, typeof(EIBListbox));
            listTools.Add(FormDesignerConstants.MenuBarControl, typeof(EIBVMenuBar));
            listTools.Add(FormDesignerConstants.MenuControl, typeof(EIBMenu));
            listTools.Add(FormDesignerConstants.MenuItemControl, typeof(EIBMenuItem));
            listTools.Add(FormDesignerConstants.GridControl, typeof(EIBGrid));
            listTools.Add(FormDesignerConstants.ScrollableRowControl, typeof(EIBScrollableRow));
            listTools.Add(FormDesignerConstants.ColumnControl, typeof(EIBColumn));
            listTools.Add(FormDesignerConstants.PlaceHolderControl, typeof(EIBPlaceHolder));
            listTools.Add(FormDesignerConstants.UserControl, typeof(EIBItem));
            listTools.Add(FormDesignerConstants.PagingControl, typeof(EIBPaging));
            listTools.Add(FormDesignerConstants.VMenuBarControl, typeof(EIBVMenuBar));
            listTools.Add(FormDesignerConstants.CalenderControl, typeof(EIBCalender));
            listTools.Add(FormDesignerConstants.SchedularControl, typeof(EIBSchedular));
            listTools.Add(FormDesignerConstants.AppletControl, typeof(EIBApplet));
            listTools.Add(FormDesignerConstants.LatticeControl, typeof(EIBLattice));
            listTools.Add(FormDesignerConstants.ImageBrowseControl, typeof(EIBImageBrowse));

        }
        public ITool getControlType(string controlId)
        {
            Type controlType = null;
            if (listTools.ContainsKey(controlId))
            {
                controlType = listTools[controlId];
            }
            ITool control = (ITool)FormatterServices.GetSafeUninitializedObject(controlType);
            ConstructorInfo constructor = controlType.GetConstructor(Type.EmptyTypes);
            MethodBase method = (MethodBase)constructor;
            method.Invoke(control, null);
            control.InitiateSettings(eibFormDesigner);
            if (control.GetType().GetInterface(typeof(IEIBControl).Name) != null)
            {
                PropertyInfo propertyInfo = control.GetType().GetProperty("Name");
                if (propertyInfo != null)
                {
                    string name = (string)propertyInfo.GetGetMethod().Invoke(control, null);
                    ((IEIBControl)control).ControlName = name;
                }
                
            }
            return control;
        }
        public static Control CloneCtrl(Control ctrl)
        {

            CBFormCtrl cbCtrl = new CBFormCtrl(ctrl);
            Control newCtrl = (Control)CreateControl(cbCtrl.CtrlName, cbCtrl.PartialName);

            SetControlProperties(newCtrl, cbCtrl.PropertyList);

            return newCtrl;
        }
        public static void SetControlProperties(object ctrl, Hashtable propertyList)
        {
            PropertyDescriptorCollection properties = TypeDescriptor.GetProperties(ctrl);

            foreach (PropertyDescriptor myProperty in properties)
            {
                if (propertyList.Contains(myProperty.Name))
                {
                    Object obj = propertyList[myProperty.Name];
                    try
                    {
                        myProperty.SetValue(ctrl, obj);
                    }
                    catch (Exception ex)
                    {
                        //do nothing, just continue
                        System.Diagnostics.Trace.WriteLine(ex.Message);
                    }

                }

            }

        }

        public static void CopyCtrl2ClipBoard(Control ctrl)
        {
            CBFormCtrl cbCtrl = new CBFormCtrl(ctrl);
            IDataObject ido = new System.Windows.Forms.DataObject();

            //ido.SetData(CBFormCtrl.Format.Name, true, cbCtrl);
            //ido = Clipboard.GetDataObject();
            ido.SetData(CBFormCtrl.Format.Name, false, cbCtrl);
            Clipboard.SetDataObject(ido, false);
        }

        public static Control GetCtrlFromClipBoard()
        {
            Control ctrl;

            IDataObject ido = Clipboard.GetDataObject();
            if (ido.GetDataPresent(CBFormCtrl.Format.Name))
            {
                CBFormCtrl cbCtrl = ido.GetData(CBFormCtrl.Format.Name) as CBFormCtrl;
                ctrl = (Control)CreateControl(cbCtrl.CtrlName, cbCtrl.PartialName);
                SetControlProperties(ctrl, cbCtrl.PropertyList);
                cbCtrl.retreiveAllChildControl(ctrl);
                return ctrl;
            }
            return null;
        }
        public static object CreateControl(string ctrlName, string partialName)
        {
            try
            {
                Control ctrl = null;
                //ToolStripMenuItem menu;
                //ToolStripButton menuItem;
                switch (ctrlName)
                {
                    case "EIBButton":
                        ctrl = new EIBButton();
                        break;
                    case "EIBApplet":
                        ctrl = new EIBApplet();
                        break;
                    case "EIBLattice":
                        ctrl = new EIBLattice();
                        break;
                    case "EIBSchedular":
                        ctrl = new EIBSchedular();
                        break;
                    case "EIBDatePicker":
                        ctrl = new EIBDatePicker();
                        break;
                    case "EIBTime":
                        ctrl = new EIBTime();
                        break;
                    case "EIBCheckbox":
                        ctrl = new EIBCheckbox();
                        break;
                    case "EIBCombobox":
                        ctrl = new EIBCombobox();
                        break;
                    case "EIBLabel":
                        ctrl = new EIBLabel();
                        break;
                    case "EIBLine":
                        ctrl = new EIBLine();
                        break;
                    case "EIBPanel":
                        ctrl = new EIBPanel();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBJasper":
                        ctrl = new EIBJasper();
                        break;
                    case "EIBRadioGroup":
                        ctrl = new EIBRadioGroup();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBPicture":
                        ctrl = new EIBPicture();
                        break;
                    case "EIBRadioButton":
                        ctrl = new EIBRadioButton();
                        break;
                    case "EIBTabControl":
                        ctrl = new EIBTabControl();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBTabPage":
                        ctrl = new EIBTabPage();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBSearch":
                        ctrl = new EIBSearch();
                        break;
                    case "EIBListbox":
                        ctrl = new EIBListbox();
                        break;
                    case "EIBGrid":
                        ctrl = new EIBGrid();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBTable":
                        ctrl = new EIBTable();
                        break;
                    case "EIBRelation":
                        ctrl = new EIBRelation();
                        break;
                    case "EIBTextBox":
                        ctrl = new EIBTextBox();
                        break;
                    case "EIBTreeView":
                        ctrl = new EIBTreeView();
                        break;
                    case "EIBMenuBar":
                        ctrl = new EIBMenuBar(false);
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBNode":
                        ctrl = new EIBNode();
                        break;
                    case "EIBNodeRelation":
                        ctrl = new EIBNodeRelation();
                        break;
                    case "EIBColumn":
                        ctrl = new EIBColumn();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBScrollableRow":
                        ctrl = new EIBScrollableRow();
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBBrowse":
                        ctrl = new EIBBrowse();
                        break;
                    case "EIBVMenuBar":
                        ctrl = new EIBVMenuBar(false);
                        DragDropHandler.RegisterControl(ctrl, true, true);
                        break;
                    case "EIBMenu":
                        return new EIBMenu();
                    case "EIBMenuItem":
                        return new EIBMenuItem();
                    /*
                                        case "EIBTreeNode":
                                            ctrl = new EIBTreeNode();
                                            break;
                    */
                    default:
                        Assembly controlAsm = Assembly.LoadWithPartialName(partialName);
                        Type controlType = controlAsm.GetType(partialName + "." + ctrlName);
                        ctrl = (Control)Activator.CreateInstance(controlType);
                        break;

                }

                return ctrl;

            }
            catch (Exception ex)
            {
                System.Diagnostics.Trace.WriteLine("create control failed:" + ex.Message);
                return new Control();
            }
        }


    }
    #region Clipboard Support
    /// <summary>
    /// Summary description for CBFormCtrl.
    /// </summary>
    [Serializable()]
    public class CBFormCtrl//clipboard form control
    {
        private static DataFormats.Format format;
        private string ctrlName;
        private string partialName;
        private Hashtable propertyList = new Hashtable();
        private List<CBFormCtrl> CBControls = new List<CBFormCtrl>();

        static CBFormCtrl()
        {
            // Registers a new data format with the windows Clipboard
            format = DataFormats.GetFormat(typeof(CBFormCtrl).FullName);
        }

        public static DataFormats.Format Format
        {
            get
            {
                return format;
            }
        }
        public string CtrlName
        {
            get
            {
                return ctrlName;
            }
            set
            {
                ctrlName = value;
            }
        }

        public string PartialName
        {
            get
            {
                return partialName;
            }
            set
            {
                partialName = value;
            }
        }

        public Hashtable PropertyList
        {
            get
            {
                return propertyList;
            }

        }


        public CBFormCtrl()
        {

        }

        public CBFormCtrl(object ctrl)
        {
            CreateCBFormCtrl(ctrl);

        }

        private void CreateCBFormCtrl(object ctrl)
        {
            CtrlName = ctrl.GetType().Name;
            PartialName = ctrl.GetType().Namespace;
            if (ctrl is EIBMenuBar)
            {
                PropertyDescriptorCollection properties = TypeDescriptor.GetProperties(ctrl);//, new Attribute[] { new SerializableAttribute(),new BrowsableAttribute(true)});

                foreach (PropertyDescriptor myProperty in properties)
                {
                    try
                    {
                        if (myProperty.PropertyType.IsSerializable && myProperty.IsBrowsable)
                            propertyList.Add(myProperty.Name, myProperty.GetValue(ctrl));
                        else if (myProperty.Name == "Controls")
                        {
                            //propertyList.Add(myProperty.Name,myProperty.GetValue(ctrl));
                        }
                    }
                    catch (Exception ex)
                    {
                        System.Diagnostics.Trace.WriteLine(ex.Message);
                        //do nothing, just continue
                    }

                }
            }
            else
            {
                PropertyDescriptorCollection properties = TypeDescriptor.GetProperties(ctrl);

                foreach (PropertyDescriptor myProperty in properties)
                {
                    try
                    {
                        if (myProperty.PropertyType.IsSerializable)
                            propertyList.Add(myProperty.Name, myProperty.GetValue(ctrl));
                        else if (myProperty.Name == "Controls")
                        {
                            //propertyList.Add(myProperty.Name,myProperty.GetValue(ctrl));
                        }
                        else if (myProperty.Name == "Nodes")
                        {
                            
                        }
                    }
                    catch (Exception ex)
                    {
                        System.Diagnostics.Trace.WriteLine(ex.Message);
                        //do nothing, just continue
                    }

                }
            }
            if (ctrl is ToolStrip)
            {
                ToolStrip toolStrip = (ToolStrip)ctrl;
                foreach (ToolStripItem ctl in toolStrip.Items)
                {
                    if (ctl is EIBMenu)
                    {
                        CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                        this.CBControls.Add(cbCtrl);
                    }
                    else if (ctl is EIBMenuItem)
                    {
                        CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                        this.CBControls.Add(cbCtrl);
                    }
                }
            }
            else if (ctrl is EIBMenu)
            {
                EIBMenu menu = (EIBMenu)ctrl;
                foreach (ToolStripItem ctl in menu.DropDownItems)
                {
                    if (ctl is EIBMenu)
                    {
                        CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                        this.CBControls.Add(cbCtrl);
                    }
                    else if (ctl is EIBMenuItem)
                    {
                        CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                        this.CBControls.Add(cbCtrl);
                    }
                }
            }
            else if (!(ctrl is EIBMenuItem))
            {
                if (ctrl is EIBTreeView)
                {
                    foreach (EIBTreeNode ctl in ((EIBTreeView)ctrl).Nodes)
                    {
                        CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                        this.CBControls.Add(cbCtrl);
                    }
                }
                else if (ctrl is EIBTreeNode)
                {
                    foreach (EIBTreeNode ctl in ((EIBTreeNode)ctrl).Nodes)
                    {
                        CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                        this.CBControls.Add(cbCtrl);
                    }
                }
                else
                {
                    foreach (Control ctl in ((Control)ctrl).Controls)
                    {
                        if (ctl is IEIBControl)
                        {
                            CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                            this.CBControls.Add(cbCtrl);
                        }
                        else if (ctl is ToolStrip)
                        {
                            CBFormCtrl cbCtrl = new CBFormCtrl(ctl);
                            this.CBControls.Add(cbCtrl);
                        }
                    }
                }
            }
        }

        internal void retreiveAllChildControl(object parent)
        {
            foreach (CBFormCtrl ctl in this.CBControls)
            {
                if (ctl.CtrlName == "ToolStrip" && ctl.PartialName == "System.Windows.Forms")
                {
                    ToolStrip toolStrip = new ToolStrip();
                    ctl.retreiveAllChildControl(toolStrip);
                    ((EIBMenuBar)parent).Join(toolStrip);
                }
                else if (ctl.ctrlName == "EIBMenu")
                {
                    EIBMenu ctrl = (EIBMenu)ToolBox.CreateControl(ctl.CtrlName, ctl.PartialName);
                    ToolBox.SetControlProperties(ctrl, ctl.PropertyList);
                    ctl.retreiveAllChildControl(ctrl);
                    if (parent is ToolStrip)
                    {
                        ((ToolStrip)parent).Items.Add(ctrl);
                    }
                    else
                    {
                        ((EIBMenu)parent).DropDownItems.Add(ctrl);
                    }
                    
                }
                else if (ctl.ctrlName == "EIBMenuItem")
                {
                    EIBMenuItem ctrl = (EIBMenuItem)ToolBox.CreateControl(ctl.CtrlName, ctl.PartialName);
                    ToolBox.SetControlProperties(ctrl, ctl.PropertyList);
                    //ctl.retreiveAllChildControl(ctrl);
                    if (parent is ToolStrip)
                    {
                        ((ToolStrip)parent).Items.Add(ctrl);
                    }
                    else
                    {
                        ((EIBMenu)parent).DropDownItems.Add(ctrl);
                    }

                }
                else
                {
                    Control ctrl = (Control)ToolBox.CreateControl(ctl.CtrlName, ctl.PartialName);
                    ToolBox.SetControlProperties(ctrl, ctl.PropertyList);
                    ctl.retreiveAllChildControl(ctrl);
                    ((Control)parent).Controls.Add(ctrl);
                }
                
            }
        }
    }
    #endregion
}
