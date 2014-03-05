using System;
using System.Collections.Generic;
using System.Text;

namespace EIBFormDesigner
{

        /// <summary>
        /// Provides an interface for controls that are handled
        /// by the DragDropHandler.  Attempting to use the DragDropHandler
        /// on a control that does not inherit this interface 
        /// will generate an Exception.
        /// </summary>
        public interface IDragDropEnabled
        {
            /// <summary>
            /// Indicates the control is dropping
            /// </summary>
            /// <param name="_successfull">true: drop is successfull | false: drop canceled</param>
            void DropComplete(bool _successfull);

            /// <summary>
            /// Tells the control to store its Location (Left, Top)
            /// </summary>
            void StoreLocation();

            /// <summary>
            /// Gets / Sets if the control is being dragged
            /// </summary>
            bool IsDragging { get; set; }
        }
}
