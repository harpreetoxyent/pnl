using System;
using System.Configuration;
using System.Collections;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Windows.Forms;
using EIBFormDesigner.Designer;
namespace EIBFormDesigner.Event
{
    /// <summary>
    /// Allows draging and dropping of controls using custom DataFormats as
    /// described in the app.config file under EIBDesigner/DragDrop/DataFormats.
    /// This is a static sealed class
    /// </summary>
    public sealed class DragDropHandler
    {
        // The sectionGroup(s)/section(s) that contain DragDropHandler mappings
        private const string m_DFSection = "EIBDesigner/DragDrop/DataFormats";
        private const string m_DFPSection = "EIBDesigner/DragDrop/DataFormatParents";
        private static IUIEventManager eventManager = null;
        private static EIBFormDesigner.Designer.FormDesigner form = null;
        // Collections of DataFormats and DataFormatParents
        private static Hashtable m_htDataFormats = new Hashtable(5);
        private static Hashtable m_htDFParents = new Hashtable(2);

        private static bool m_Initialized = false;			// Are we initialized yet?
        private static bool m_IsSimpleDrag = false;			// Is this a simple drag?

        /// <summary>
        /// Initializes the DragDropHandler
        /// </summary>
        public static void Initialize(IUIEventManager eManager, EIBFormDesigner.Designer.FormDesigner designer)
        {
            eventManager = eManager;
            form = designer;
            if (m_Initialized)
                return;

            int idx = 0;

            // Collection of DataFormats from the app.config file
            NameValueCollection nvc =
                (NameValueCollection)ConfigurationSettings.GetConfig(m_DFSection);

            if (nvc == null)
                throw new Exception("Invalid section requested during Initialization.");

            // Store the collection in our internal collection
            for (idx = 0; idx < nvc.Count; idx++)
            {
                // Get the custom string for our new DataFormat
                // and generate a new DataFormat for it
                DataFormats.Format frmt = DataFormats.GetFormat(nvc[idx]);

                // Store the new DataFormat by object type
                m_htDataFormats.Add(nvc.GetKey(idx), frmt);
            }

            // Collection of DataFormatParents from the app.config file
            nvc = (NameValueCollection)ConfigurationSettings.GetConfig(m_DFPSection);

            // Store the collection in our internal collection
            for (idx = 0; idx < nvc.Count; idx++)
            {
                // Store the DataFormatParent mappins by parent object type
                // The value is a string[] of DataFormats that can be DragDropped
                // on the parent
                m_htDFParents.Add(nvc.GetKey(idx),
                    ((string)nvc[idx]).Split(new Char[] { ',' }));
            }

            m_Initialized = true;
        }

        /// <summary>
        /// Allows the user to register a control with the handler, thus populating
        /// the MouseDown and OnPaint delegates with extended handlers.
        /// (Not required for DragDropHandler usage)
        /// </summary>
        /// <param name="_ctrl">The Control to register with the handler</param>
        /// <param name="_parent">Is the control being registered a parent (AllowDrop true)</param>
        /// <param name="_simple">Is this a custom or simple(base) control? (ignored if _parent == true)</param>
        public static void RegisterControl(Control _ctrl, bool _parent, bool _simple)
        {
            // If this is a dragable control and not a parent...
            if (!_parent)
            {
                // If the control was registered as a non-simple control but
                // does not support IDragDropEnabled, throw an exception
                if (!_simple && !(_ctrl is IDragDropEnabled))
                    throw new ControlInterfaceException(string.Format("The control of type\n{0}\n is a simple control but was passed as a\n{1}",
                        _ctrl.GetType().ToString(),
                        "EIBDesigner.IDragDropEnabled"));

                // Try to get the format of the control
                GetDataFormat(_ctrl);
            }

            // Add the appropriate methods to the control
            if (!_parent)
            {
                if (!_simple)
                {
                    _ctrl.MouseDown += new MouseEventHandler(_ctrl_MouseDown);
                    _ctrl.Paint += new PaintEventHandler(_ctrl_Paint);
                }
                else
                {
                    _ctrl.MouseDown += new MouseEventHandler(_ctrlSimple_MouseDown);
                }
            }
            else
            {
                // If the parent mappings contain this parent, then
                // add the appropriate methods and set the AllowDrop property
                if (m_htDFParents.ContainsKey(_ctrl.GetType().ToString()))
                {
                    _ctrl.AllowDrop = true;
                    _ctrl.DragOver += new DragEventHandler(_ctrlParent_DragOver);
                    _ctrl.DragDrop += new DragEventHandler(_ctrlParent_DragDrop);
                }
                // Otherwise throw an exception
                else
                {
                    throw new DataFormatException(string.Format(
                        "Parent control\n{0}\nis not a valid mapped parent.",
                        _ctrl.GetType().ToString()));
                }
            }
        }

        /// <summary>
        /// Method assigned to Registered control's OnMouseDown event
        /// Fires the DoDragDrop event for the Registered control with
        /// DragDropEffects.Move set
        /// </summary>
        /// <param name="sender">the object to begin a DragDrop for</param>
        /// <param name="e">Contains the mouse information</param>
        private static void _ctrl_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
                ((Control)sender).DoDragDrop(
                    DragDropHandler.BeginDrag((Control)sender, true),
                    DragDropEffects.Move);
        }

        /// <summary>
        /// Method assigned to Registered control's OnMouseDown event (simple)
        /// Fires the DoDragDrop event for the Registered control with 
        /// DragDropEffects.Move set
        /// </summary>
        /// <param name="sender">the object to begin a DragDrop for</param>
        /// <param name="e">Contains the mouse information</param>
        private static void _ctrlSimple_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
                ((Control)sender).DoDragDrop(
                    DragDropHandler.BeginSimpleDrag((Control)sender),
                    DragDropEffects.Move);
        }

        /// <summary>
        /// Method assigned to Registered control's OnPaint event
        /// Draws a bounding box around the control if it is dragging
        /// </summary>
        /// <param name="sender">the object to paint</param>
        /// <param name="e">Contains the graphic information</param>
        private static void _ctrl_Paint(object sender, PaintEventArgs e)
        {
            Control ctrl = (Control)sender;

            // If we are dragging, then draw a bounding box around the control
            if (((IDragDropEnabled)ctrl).IsDragging)
            {
                Graphics grfx = e.Graphics;
                Pen pen = new Pen(Color.Black, 1);
                pen.DashStyle = DashStyle.Dot;

                grfx.DrawRectangle(pen, 0, 0, ctrl.Width - 1, ctrl.Height - 1);

                pen = null;
                grfx = null;
            }
        }

        /// <summary>
        /// Gets the control being DragDropped
        /// </summary>
        /// <param name="_ido">The IDataObject containg the control to get</param>
        /// <param name="_dropping">Are we dropping the control? 
        /// (false if we just want to check the control without ending the DragDrop)</param>
        /// <param name="_success">Was the DragDrop a success? 
        /// (ignored if _dropping is false)</param>
        /// <returns>The control being DragDropped</returns>
        public static Control GetControl(IDataObject _ido, bool _dropping, bool _success)
        {
            // If not initialized then throw an Exception
            if (!m_Initialized)
                throw new Exception(string.Format("{0} not initialized.",
                    "EIBDesigner.Event.DragDropHandler"), null);

            // Get the control using the custom DataFormat
            Control ctrl = null;
            string szDataFormat = DeriveUnknownFormat(_ido);
            if (szDataFormat != "")
            {
                ctrl = (Control)_ido.GetData(szDataFormat);

                // If dropping... alert the control unless this is a simple DragDrop
                if (!m_IsSimpleDrag)
                {
                    if (_dropping)
                    {
                        ((IDragDropEnabled)ctrl).IsDragging = false;
                        ((IDragDropEnabled)ctrl).DropComplete(_success);
                        ctrl.Invalidate();
                    }
                }
            }

            // Return the control being DragDropped
            return ctrl;
        }

        /// <summary>
        /// Handles the parent receiving a DragOver event
        /// </summary>
        /// <param name="sender">The parent receiving the event</param>
        /// <param name="e">Contains the data being dragged</param>
        private static void _ctrlParent_DragOver(object sender, DragEventArgs e)
        {
            // If this is a valid control for this parent, allow it to move
            // freely over, otherwise disallow DragDrop operations
            if (DragDropHandler.CanDropHere((Control)sender, e.Data))
            {
                // cthis... this as a Control
                Control cthis = (Control)sender;

                // Set the DragDropEffect and get the control we are dragging
                e.Effect = DragDropEffects.Move;
                Control ctrl = DragDropHandler.GetControl(e.Data, false, true);

                // If this isn't an IDragDropEnabled control don't worry about
                // showing it's position
                if (!(ctrl is IDragDropEnabled))
                {
                    return;
                }

                // If this control is not part of the current "parent"
                // remove it from it's original parent and place it in
                // the current parent control collection
                if (!cthis.Controls.Contains(ctrl))
                {
                    ctrl.Parent.Controls.Remove(ctrl);
                    ctrl.Parent = cthis;
                    cthis.Controls.Add(ctrl);
                }

                // Set the control being dragged to be positioned to where we 
                // are dragging
                Point NewLocation = cthis.PointToClient(new Point(e.X, e.Y));
                ctrl.Left = NewLocation.X + 2;
                ctrl.Top = NewLocation.Y + 2;
            }
            else
            {
                e.Effect = DragDropEffects.None;
            }
        }

        /// <summary>
        /// Determines if the control can be dropped on the parent
        /// </summary>
        /// <param name="_parent">The parent to test drop acceptance</param>
        /// <param name="_ido">The data being dropped</param>
        /// <returns></returns>
        internal static bool CanDropHere(Control _parent, IDataObject _ido)
        {
            string szDataFormat = "";	// The data format of the control being dragged
            string szFoundDF = "";		// The data format that was accepted

            szDataFormat = DeriveUnknownFormat(_ido);

            // Couldn't find the data in the mappings?... return false
            if (szDataFormat == "")
                return false;

            try
            {
                // Attempt to map the parent type to an allowed DataFormat list
                // if it is null or fails, throw an exception
                string[] aszAllowedDF =
                    (string[])m_htDFParents[_parent.GetType().ToString()];

                if (aszAllowedDF != null)
                {
                    // Check each string in the Allowed DataFormats list
                    // and if it matches the DataFormat from the IDataObject
                    // then store it and break out of the search
                    foreach (string _szDF in aszAllowedDF)
                    {
                        if (_szDF == szDataFormat)
                        {
                            szFoundDF = _szDF;
                            break;
                        }
                    }
                }
            }
            catch
            {
                throw new ControlInterfaceException(string.Format(
                    "The control:\n{0}\nis not a valid parent for any mapped IDragDropEnabled control.",
                    _parent.GetType().ToString()));
            }

            // If we found the DataFormat return true, this control can be dropped here
            if (szFoundDF == szDataFormat)
                return true;
            // DataFormat was not found so this parent can not host the IDataObject
            else
                return false;
        }

        /// <summary>
        /// Handles the parent receiving a DragDrop event
        /// </summary>
        /// <param name="sender">The parent receiving the event</param>
        /// <param name="e">Contains the data being dragged</param>
        private static void _ctrlParent_DragDrop(object sender, DragEventArgs e)
        {
            // Get the control being dropped
            Control ctrl = DragDropHandler.GetControl(e.Data, true, true);

            // Set the control being dropped to be positioned to where we 
            // did the drop
            Point NewLocation = ((Control)sender).PointToClient(new Point(e.X, e.Y));
            eventManager.handleDrag(ctrl, e, sender, NewLocation);
        }

        /// <summary>
        /// Returns the custom DataFormat for a given type (if any)
        /// </summary>
        /// <param name="_ctrl">The control to get the DataFormat for</param>
        /// <returns>Custom DataFormat</returns>
        public static string GetDataFormat(Control _ctrl)
        {
            // If not initialized then throw an Exception
            if (!m_Initialized)
                throw new Exception(string.Format("{0} not initialized.",
                    "EIBDesigner.DragDrop.DragDropHandler"), null);

            // Convert the Type to a string
            string szObjType = _ctrl.GetType().ToString();

            return GetDataFormat(szObjType);
        }

        /// <summary>
        /// Returns the custom DataFormat for a given type (if any)
        /// </summary>
        /// <param name="_ctrl">The type of the control to get the DataFormat for</param>
        /// <returns>Custom DataFormat</returns>
        public static string GetDataFormat(Type _type)
        {
            // If not initialized then throw an Exception
            if (!m_Initialized)
                throw new Exception(string.Format("{0} not initialized.",
                    "EIBDesigner.DragDrop.DragDropHandler"), null);

            // Convert the Type to a string
            string szObjType = _type.ToString();

            return GetDataFormat(szObjType);
        }

        /// <summary>
        /// Actually does the work getting the DataFormat
        /// </summary>
        /// <param name="_szType">The Type (as a string) of the control to get the DataFormat for</param>
        /// <returns>The custom DataFormat (or blank if not found)</returns>
        private static string GetDataFormat(string _szType)
        {
            string szRetVal = "";

            // If our custom configuration maps the specified type, return the 
            // DataFormat, otherwise return ""
            if (m_htDataFormats.ContainsKey(_szType))
            {
                // Get the Name of the DataFormat for the specified object Type
                szRetVal = ((DataFormats.Format)m_htDataFormats[_szType]).Name;
            }

            // If the custom DataFormat doesn't exist, throw an exception
            if (szRetVal == "")
                throw new DataFormatException(
                    string.Format("Unable to get DataFormat:\n\n an unmapped control type:\n{0}\nwas specified.",
                    _szType));

            return szRetVal;
        }

        private static string DeriveUnknownFormat(IDataObject _ido)
        {
            string szDataFormat = "";

            // Determine if the data is a valid type by scanning our 
            // custom mapped DataFormats and asserting them into
            // GetDataPresent until GetDataPresent returns true 
            // or we exhaust the list
            foreach (object _obj in m_htDataFormats.Values)
            {
                szDataFormat = ((DataFormats.Format)_obj).Name;
                if (_ido.GetDataPresent(szDataFormat))
                {
                    break;
                }
            }

            return szDataFormat;
        }
        
        /// <summary>
        /// Converts the control to a DataObject
        /// </summary>
        /// <param name="_ctrl">The control to convert</param>
        /// <param name="_simple_drag">Is this a simple DragDrop or not?</param>
        /// <returns>Control as a DataObject</returns>
        private static DataObject StoreControl(Control _ctrl, bool _simple_drag)
        {
            // This is a full DragDrop
            m_IsSimpleDrag = _simple_drag;

            // Get the custom DataFormat
            string szDF = GetDataFormat(_ctrl);

            // "Convert" the control to a DataObject using the custom DataFormat
            DataObject doControlToDrag = new DataObject(szDF, _ctrl);

            return doControlToDrag;
        }

        /// <summary>
        /// Returns the DataObject needed for a DoDragDrop, from a specified Control
        /// Used with controls that implement IDragDropEnabled (recommended usage)
        /// </summary>
        /// <param name="_ctrl">Control to get DataObject for</param>
        /// <param name="_invalidate">Invalidate the control or not? 
        /// (Set to true if you need to change the appearance of the control during a DragDrop)</param>
        /// <returns>Control converted to a DataObject ready for dragging</returns>
        public static DataObject BeginDrag(Control _ctrl, bool _invalidate)
        {
            // If not initialized then throw an Exception
            if (!m_Initialized)
                throw new Exception(string.Format("{0} not initialized.",
                    "EIBFormDesigner.Event.DragDropHandler"), null);

            // If this is not a valid control to be dragged, throw an exception
            if (!(_ctrl is IDragDropEnabled))
                throw new ControlInterfaceException(
                    string.Format("[{0}] only accepts controls that implement\n[{1}] for drag operations.\n\nUse {2} instead.",
                    "EIBFormDesigner.Event",
                    "IDragDropEnabled",
                    "BeginSimpleDrag"));

            DataObject doControlToDrag = StoreControl(_ctrl, false);

            // Inform the control that it has begun a drag operation
            ((IDragDropEnabled)_ctrl).StoreLocation();
            ((IDragDropEnabled)_ctrl).IsDragging = true;

            // Set the control up to be in the foreground and invalidate it
            // incase it needs redrawing
            if (_invalidate)
            {
                _ctrl.BringToFront();
                _ctrl.Invalidate();
            }

            // return the converted control as a DataObject
            return doControlToDrag;
        }

        /// <summary>
        /// Returns the DataObject needed for a DoDragDrop, from a specified Control
        /// Used for controls that do not support IDragDropEnabled 
        /// (NOTE: it is recommended your control implement IDragDropEnabled)
        /// </summary>
        /// <param name="_ctrl">Control to get DataObject for</param>
        /// <returns>Control converted to a DataObject ready for dragging</returns>
        public static DataObject BeginSimpleDrag(Control _ctrl)
        {
            // If not initialized then throw an Exception
            if (!m_Initialized)
                throw new Exception(string.Format("{0} not initialized.",
                    "EIBDesigner.Event.DragDropHandler"), null);

            return StoreControl(_ctrl, true);
        }
    }
}
