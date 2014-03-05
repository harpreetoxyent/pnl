using System;
using System.Configuration;
using System.Collections;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Windows.Forms;

namespace EIBFormDesigner.Event
{
    /// <summary>
    /// Allows draging and dropping of controls using custom DataFormats as
    /// described in the app.config file under EIBDesigner/DragDrop/DataFormats.
    /// This is a static sealed class
    /// </summary>
    public class LineEventHandler
    {
		public LineEventHandler()
		{
            Debug.WriteLine("Creating LineEventHandler");
		}

	}
}
