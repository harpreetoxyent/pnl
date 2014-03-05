using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Event;
using EIBFormDesigner.Controls;

namespace EIBFormDesigner
{
    public enum Align
    {
        left,center,right,top,middle,bottom
    }

    public static class Alignment
    {

        public static bool CheckForTreeNode(List<IEIBControl> currentControl)
        {
            bool flag = false;
            foreach (IEIBControl ctrl in currentControl)
            {
                if (ctrl is EIBTreeNode)
                {
                    flag = true;
                }
            }
            return flag;
        }
        public static void AlignLeft(List<IEIBControl> currentControl)
        {

            if (!CheckForTreeNode(currentControl))
            {
                int left = ((Control)currentControl[0]).Left;
                foreach (Control ctrl in currentControl)
                {

                    ctrl.Left = left;

                }
            }
        }
        public static void AlignRight(List<IEIBControl> currentControl)
        {
            if (!CheckForTreeNode(currentControl))
            {
                int right1 = ((Control)currentControl[0]).Left + ((Control)currentControl[0]).Width;
                int i = 0;
                foreach (Control ctrl in currentControl)
                {
                    if (i > 0)
                    {
                        int right2 = ctrl.Left + ctrl.Width;
                        int distance = right1 - right2;
                        if (distance < 0)
                            ctrl.Left = ctrl.Left - Math.Abs(distance);
                        else
                        {
                            ctrl.Left = ctrl.Left + Math.Abs(distance);
                        }
                    }
                    i++;
                }
            }
        }
        public static void AlignCenter(List<IEIBControl> currentControl)
        {
            if (!CheckForTreeNode(currentControl))
            {
                int center1 = ((Control)currentControl[0]).Left + (((Control)currentControl[0]).Width / 2);
                int i = 0;
                foreach (Control ctrl in currentControl)
                {
                    if (i > 0)
                    {
                        int center2 = ctrl.Left + (ctrl.Width / 2);
                        int distance = center1 - center2;
                        if (distance < 0)
                            ctrl.Left = ctrl.Left - Math.Abs(distance);
                        else
                        {
                            ctrl.Left = ctrl.Left + Math.Abs(distance);
                        }
                    }
                    i++;
                }
            }
        }
        public static void AlignTop(List<IEIBControl> currentControl)
        {
            if (!CheckForTreeNode(currentControl))
            {
                int top = ((Control)currentControl[0]).Top;
                foreach (Control ctrl in currentControl)
                {

                    ctrl.Top = top;

                }
                
            }
        }
        public static void AlignMiddle(List<IEIBControl> currentControl)
        {
            if (!CheckForTreeNode(currentControl))
            {
                int center1 = ((Control)currentControl[0]).Top + (((Control)currentControl[0]).Height / 2);
                int i = 0;
                foreach (Control ctrl in currentControl)
                {
                    if (i > 0)
                    {
                        int center2 = ctrl.Top + (ctrl.Height / 2);
                        int distance = center1 - center2;
                        if (distance < 0)
                            ctrl.Top = ctrl.Top - Math.Abs(distance);
                        else
                        {
                            ctrl.Top = ctrl.Top + Math.Abs(distance);
                        }
                    }
                    i++;
                }
            }
        }
        public static void AlignBottom(List<IEIBControl> currentControl)
        {
            if (!CheckForTreeNode(currentControl))
            {
                int right1 = ((Control)currentControl[0]).Top + ((Control)currentControl[0]).Height;
                int i = 0;
                foreach (Control ctrl in currentControl)
                {
                    if (i > 0)
                    {
                        int right2 = ctrl.Top + ctrl.Height;
                        int distance = right1 - right2;
                        if (distance < 0)
                            ctrl.Top = ctrl.Top - Math.Abs(distance);
                        else
                        {
                            ctrl.Top = ctrl.Top + Math.Abs(distance);
                        }
                    }
                    i++;
                }
            }
        }
        //public static void CheckForMinLeftPosition(List<IEIBControl> currentControl)
        //{
        //    int xtremeleft = ((Control)currentControl[0]).Left;
        //    foreach (IEIBControl ctrl in currentControl)
        //    {
        //        Control control = (Control)ctrl;
        //        if (xtremeleft > control.Left)
        //        {
        //            xtremeleft = control.Left;
        //        }
        //    }
        //}
        //public static void CheckForMinRightPosition(List<IEIBControl> currentControl)
        //{
        //    int xtremeleft = ((Control)currentControl[0]).Left;
        //    foreach (IEIBControl ctrl in currentControl)
        //    {
        //        Control control = (Control)ctrl;
        //        Control.
        //        if (xtremeleft > control.Left)
        //        {
        //            xtremeleft = control.Left;
        //        }
        //    }
        //}
    }
}
