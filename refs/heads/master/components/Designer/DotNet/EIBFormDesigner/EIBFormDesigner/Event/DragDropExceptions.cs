using System;
using System.Collections.Generic;
using System.Text;

namespace EIBFormDesigner.Event
{
        /// <summary>
        /// Exception thrown when the control/type specified
        /// to a DragDropHandler method is invalid (unmapped in app.config)
        /// </summary>
        public class DataFormatException : Exception
        {
            public DataFormatException(string _szError) : base(_szError, null) { }
        }

        /// <summary>
        /// Exception thrown when the control/type specified
        /// to a DragDropHandler method does not implement IDragDropEnabled
        /// </summary>
        public class ControlInterfaceException : Exception
        {
            public ControlInterfaceException(string _szError) : base(_szError, null) { }
        }
}
