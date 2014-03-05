using System;
using System.Collections.Generic;
using System.Text;

namespace EIBFormDesigner.Controls
{
    public enum ControlPosition
    {
        Absolute,
        Relative
    }

    public enum ContainerAlignment
    {
        None,
        Left,
        Center,
        Right
    }
    public enum WidthMeasurement { Auto, Percent, Pixel };


    public enum HeightMeasurement { Auto, Percent, Pixel };
    public enum MoldType {Default, Rounded };
    public enum GridType { ListBox, Grid };
    
}
