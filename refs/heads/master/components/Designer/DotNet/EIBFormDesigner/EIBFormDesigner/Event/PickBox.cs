using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;
using System.Drawing;
using EIBFormDesigner.UndoRedo;
using EIBFormDesigner.Event;

namespace EIBFormDesigner.Event
{
    /// <summary>
    /// This class implements sizing and moving functions for
    ///	runtime editing of graphic controls
    /// </summary>
    public class PickBox
    {
        //////////////////////////////////////////////////////////////////
        // PRIVATE CONSTANTS AND VARIABLES
        //////////////////////////////////////////////////////////////////
        public static bool isLabelVisible = false;
        private const int BOX_SIZE = 8;
        private Color BOX_COLOR = Color.White;
        private static List<Control> m_control_list = new List<Control>();
        private static Dictionary<Control, Point> starts_list = new Dictionary<Control,Point>();
        private static Control m_control;
        private Label[] lbl = new Label[8];
        private int startl;
        private int startt;
        private int startw;
        private int starth;
        private int startx;
        private int starty;
        private static bool dragging;
        public static bool isctrlpressed;
        private Cursor[] arrArrow = new Cursor[] {Cursors.SizeNWSE, Cursors.SizeNS,
			Cursors.SizeNESW, Cursors.SizeWE, Cursors.SizeNWSE, Cursors.SizeNS,
			Cursors.SizeNESW, Cursors.SizeWE};
        private Cursor oldCursor;

        private const int MIN_SIZE = 20;

        private Dictionary<Control, Point> OldLocation = new Dictionary<Control,Point>();
        private Dictionary<Control, Point> delta = new Dictionary<Control, Point>();

        //private Point OldLocation;
        public bool isControlMDown = false;
        private static bool isCtrlKeyPressed = false;

        //
        // Constructor creates 8 sizing handles & wires mouse events
        // to each that implement sizing functions
        //
        public PickBox()
        {
            initializePickupBox();
        }

        private void initializePickupBox()
        {
            for (int i = 0; i < 8; i++)
            {
                lbl[i] = new Label();
                lbl[i].TabIndex = i;
                lbl[i].FlatStyle = 0;
                lbl[i].BorderStyle = BorderStyle.FixedSingle;
                lbl[i].BackColor = BOX_COLOR;
                lbl[i].Cursor = arrArrow[i];
                lbl[i].Text = "";
                lbl[i].BringToFront();
                lbl[i].MouseDown += new MouseEventHandler(this.lbl_MouseDown);
                lbl[i].MouseMove += new MouseEventHandler(this.lbl_MouseMove);
                lbl[i].MouseUp += new MouseEventHandler(this.lbl_MouseUp);
            }
        }
        //////////////////////////////////////////////////////////////////
        // PUBLIC METHODS
        //////////////////////////////////////////////////////////////////

        //
        // Wires a Click event handler to the passed Control
        // that attaches a pick box to the control when it is clicked
        //
        public void WireControl(Control ctl)
        {
            ctl.MouseClick += new MouseEventHandler(this.SelectControl);
        }


        /////////////////////////////////////////////////////////////////
        // PRIVATE METHODS
        /////////////////////////////////////////////////////////////////

        //
        // Attaches a pick box to the sender Control
        //
        internal void SelectControl(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
                return;

            if (m_control is Control)
            {
                m_control.Cursor = UIEventManager.pickBox[(IEIBControl)m_control].oldCursor;

                //Remove event any pre-existing event handlers appended by this class
                m_control.MouseDown -= new MouseEventHandler(this.ctl_MouseDown);
                m_control.MouseMove -= new MouseEventHandler(this.ctl_MouseMove);
                m_control.MouseUp -= new MouseEventHandler(this.ctl_MouseUp);
                m_control.KeyDown -= new KeyEventHandler(this.ctl_KeyDown);
                m_control.KeyUp -= new KeyEventHandler(this.ctl_KeyUp);
                m_control.CursorChanged -= new EventHandler(m_control_CursorChanged);
                m_control = null;
            }
            
            m_control = (Control)sender;
            m_control.SuspendLayout();
            //Add event handlers for moving the selected control around
            m_control.MouseDown += new MouseEventHandler(this.ctl_MouseDown);
            m_control.MouseMove += new MouseEventHandler(this.ctl_MouseMove);
            m_control.MouseUp += new MouseEventHandler(this.ctl_MouseUp);
            m_control.KeyDown += new KeyEventHandler(this.ctl_KeyDown);
            m_control.KeyUp += new KeyEventHandler(this.ctl_KeyUp);

            //Add sizing handles to Control's container (Form or PictureBox)
            for (int i = 0; i < 8; i++)
            {
                m_control.Parent.Controls.Add(lbl[i]);
                lbl[i].BringToFront();
            }

            //Position sizing handles around Control
            MoveHandles();

            //Display sizing handles
            ShowHandles();

            oldCursor = m_control.Cursor;
            m_control.Cursor = Cursors.SizeAll;
            m_control.CursorChanged += new EventHandler(m_control_CursorChanged);
            /*if (!((m_control is EIBFormDesigner.Controls.EIBPicture) || (m_control is EIBFormDesigner.Controls.EIBPanel)))
            {
                m_control.Cursor = Cursors.SizeAll;
            }*/
            m_control.ResumeLayout();
            //m_control.Invalidate();

        }

        void m_control_CursorChanged(object sender, EventArgs e)
        {
            oldCursor = ((Control)sender).Cursor;
        }

        public void Remove()
        {
            HideHandles();
            if(m_control!=null)
            m_control.Cursor = oldCursor;
        }

        private void ShowHandles()
        {
            try
            {
                isLabelVisible = true;
                if (m_control != null)
                {
                    for (int i = 0; i < 8; i++)
                    {
                        lbl[i].Visible = true;
                    }
                }
            }
            catch (ObjectDisposedException objectDisposedException)
            {
                //Control has been disposed  create new one
                initializePickupBox();
                ShowHandles();
            }
        }

        public void HideHandles()
        {
            for (int i = 0; i < 8; i++)
            {
                lbl[i].Visible = false;
            }
            isLabelVisible = false;
        }

        private void MoveHandles()
        {
            int sX = m_control.Left - BOX_SIZE;
            int sY = m_control.Top - BOX_SIZE;
            int sW = m_control.Width + BOX_SIZE;
            int sH = m_control.Height + BOX_SIZE;
            int hB = BOX_SIZE / 2;
            int[] arrPosX = new int[] {sX+hB, sX + sW / 2, sX + sW-hB, sX + sW-hB,
			sX + sW-hB, sX + sW / 2, sX+hB, sX+hB};
            int[] arrPosY = new int[] {sY+hB, sY+hB, sY+hB, sY + sH / 2, sY + sH-hB,
			sY + sH-hB, sY + sH-hB, sY + sH / 2};
            for (int i = 0; i < 8; i++)
                lbl[i].SetBounds(arrPosX[i], arrPosY[i], BOX_SIZE, BOX_SIZE);
        }

        /////////////////////////////////////////////////////////////////
        // MOUSE EVENTS THAT IMPLEMENT SIZING OF THE PICKED CONTROL
        /////////////////////////////////////////////////////////////////

        //
        // Store control position and size when mouse button pushed over
        // any sizing handle
        //
        private void lbl_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
                return;

            dragging = true;
            startl = m_control.Left;
            startt = m_control.Top;
            startw = m_control.Width;
            starth = m_control.Height;
            HideHandles();
        }

        //
        // Size the picked control in accordance with sizing handle being dragged
        //	0   1   2
        //  7       3
        //  6   5   4
        //
        private void lbl_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
                return;

            int l = m_control.Left;
            int w = m_control.Width;
            int t = m_control.Top;
            int h = m_control.Height;
            if (dragging)
            {
                switch (((Label)sender).TabIndex)
                {
                    case 0: // Dragging top-left sizing box
                        l = startl + e.X < startl + startw - MIN_SIZE ? startl + e.X : startl + startw - MIN_SIZE;
                        t = startt + e.Y < startt + starth - MIN_SIZE ? startt + e.Y : startt + starth - MIN_SIZE;
                        w = startl + startw - m_control.Left;
                        h = startt + starth - m_control.Top;
                        break;
                    case 1: // Dragging top-center sizing box
                        t = startt + e.Y < startt + starth - MIN_SIZE ? startt + e.Y : startt + starth - MIN_SIZE;
                        h = startt + starth - m_control.Top;
                        break;
                    case 2: // Dragging top-right sizing box
                        w = startw + e.X > MIN_SIZE ? startw + e.X : MIN_SIZE;
                        t = startt + e.Y < startt + starth - MIN_SIZE ? startt + e.Y : startt + starth - MIN_SIZE;
                        h = startt + starth - m_control.Top;
                        break;
                    case 3: // Dragging right-middle sizing box
                        w = startw + e.X > MIN_SIZE ? startw + e.X : MIN_SIZE;
                        break;
                    case 4: // Dragging right-bottom sizing box
                        w = startw + e.X > MIN_SIZE ? startw + e.X : MIN_SIZE;
                        h = starth + e.Y > MIN_SIZE ? starth + e.Y : MIN_SIZE;
                        break;
                    case 5: // Dragging center-bottom sizing box
                        h = starth + e.Y > MIN_SIZE ? starth + e.Y : MIN_SIZE;
                        break;
                    case 6: // Dragging left-bottom sizing box
                        l = startl + e.X < startl + startw - MIN_SIZE ? startl + e.X : startl + startw - MIN_SIZE;
                        w = startl + startw - m_control.Left;
                        h = starth + e.Y > MIN_SIZE ? starth + e.Y : MIN_SIZE;
                        break;
                    case 7: // Dragging left-middle sizing box
                        l = startl + e.X < startl + startw - MIN_SIZE ? startl + e.X : startl + startw - MIN_SIZE;
                        w = startl + startw - m_control.Left;
                        break;
                }
                l = (l < 0) ? 0 : l;
                t = (t < 0) ? 0 : t;
                m_control.SetBounds(l, t, w, h);
            }
        }

        //
        // Display sizing handles around picked control once sizing has completed
        //
        private void lbl_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
                return;

            dragging = false;
            MoveHandles();
            ShowHandles();

            string getProperty = "Bounds";
            if (!UIEventManager.form.Controlpool.propControls.Contains((IEIBControl)m_control))
            {
                UIEventManager.form.Controlpool.propControls.Add((IEIBControl)m_control);
            }
            UIEventManager.form.History.Do(new PropertyChangeMemento(getProperty, new Rectangle(startl,startt,startw,starth) , m_control));
        }

        /////////////////////////////////////////////////////////////////
        // MOUSE EVENTS THAT MOVE THE PICKED CONTROL AROUND THE FORM
        /////////////////////////////////////////////////////////////////

        //
        // Get mouse pointer starting position on mouse down and hide sizing handles
        //

        private void ctl_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode.Equals(Keys.ControlKey))
            {
                isCtrlKeyPressed = true;
                //MessageBox.Show(isCtrlKeyPressed.ToString());
            }
        }

        private void ctl_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode.Equals(Keys.ControlKey))
                isCtrlKeyPressed = false;
        }

        private void ctl_MouseDown(object sender, MouseEventArgs e)
        {
           if (e.Button == MouseButtons.Right)
                return;
            m_control = (Control)sender;

            HideHandles();
            if (!OldLocation.ContainsKey(m_control))
                OldLocation.Add(m_control, m_control.Location);
            else
                OldLocation[m_control] = m_control.Location;

            if (!starts_list.ContainsKey(m_control))
                starts_list.Add(m_control, new Point(e.X, e.Y));
            else
                starts_list[m_control] = new Point(e.X, e.Y);

            //foreach (Control ctrl in delta)
            //{
            //    delta[ctrl] = new Point()
            //}
            //if (!delta.ContainsKey(m_control))
            //    delta.Add(m_control, new Point(0, 0));
            //else
            //    starts_list[m_control] = new Point(e.X, e.Y);
            if (!isCtrlKeyPressed)
            {
                m_control_list.Clear();
            }
            
            if(!m_control_list.Contains(m_control))
            {
                m_control_list.Add(m_control);  
            }
            dragging = true;

            isControlMDown = true;
        }

        //
        // Reposition the dragged control
        //
 
        private void ctl_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
                return;

            bool move = true;
            //m_control = (Control)sender;
            if (dragging)
            {
                //Control parent = ((Control)sender).Parent;
                foreach (Control cntrl in m_control_list)
                {
                    if ((cntrl.Parent != null ))
                    {
                        m_control = (Control)cntrl;
                        if(starts_list.ContainsKey((Control)sender))
                        {
                            int delx = e.X - starts_list[(Control)sender].X;
                            int dely = e.Y - starts_list[(Control)sender].Y;
                            int l = m_control.Left + delx;
                            int t = m_control.Top + dely;
                            int w = m_control.Width;
                            int h = m_control.Height;
                            if (l < 0)
                                l = 0;
                            else if (l + w > m_control.Parent.Width)
                                l = m_control.Parent.Width - w;
                            if (t < 0)
                                t = 0;
                            else if (t + h > m_control.Parent.Height)
                                t = m_control.Parent.Height - h;

                            /*l = (l < 0) ? 0 : ((l + w > m_control.Parent.ClientRectangle.Width) ?
                              m_control.Parent.ClientRectangle.Width - w : l);
                            t = (t < 0) ? 0 : ((t + h > m_control.Parent.ClientRectangle.Height) ?
                            m_control.Parent.ClientRectangle.Height - h : t);*/
                            if (l < 0)
                                move = false;
                            else if (l > m_control.Parent.Width)
                                move = false;
                            if (t < 0)
                                move = false;
                            else if (t > m_control.Parent.Height)
                                move = false;
                            if(move)
                                m_control.Location = new Point(l, t);
                            //m_control.Left = l;
                            //m_control.Top = t;
                            MoveHandles();
                            //ShowHandles();
                        }
                    }
                }
            }
        }

        //
        // Display sizing handles around picked control once dragging has completed
        //
        private void ctl_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
                return;
            m_control = (Control)sender;
            m_control.Cursor = oldCursor;
            MoveHandles();
            ShowHandles();

            dragging = false;

            if (sender is TreeView)
            {

                if (((TreeView)sender).GetNodeAt(e.X, e.Y) == null)
                {
                    ((TreeView)sender).SelectedNode = null;
                    EIBFormDesigner.Designer.FormDesigner.eventManager.handleControlClick(sender, e);
                    SelectControl(sender, e);
                }
                else
                {
                    ((TreeView)sender).SelectedNode = ((TreeView)sender).GetNodeAt(e.X, e.Y);
                }
            }
            if (isControlMDown && m_control.Location != OldLocation[m_control])
            {
                string getProperty = "Location";
                if (!UIEventManager.form.Controlpool.propControls.Contains((IEIBControl)m_control))
                {
                    UIEventManager.form.Controlpool.propControls.Add((IEIBControl)m_control);
                }
                UIEventManager.form.History.Do(new PropertyChangeMemento(getProperty, OldLocation[m_control], m_control));
                isControlMDown = false;
            }


            
        }

    }

}
