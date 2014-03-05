using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.UndoRedo
{
    class parentExpose
    {
        public BaseWindow baseFormPattern;
        public Control parent;
    }

    [Serializable]
    class ControlPool : IEnumerable<IEIBControl>
    {
        List<IEIBControl> eibControls = new List<IEIBControl>();
        public static Dictionary<IEIBControl, parentExpose> parentControls = new Dictionary<IEIBControl, parentExpose>();
        public List<IEIBControl> propControls = new List<IEIBControl>();

        public parentExpose FindControlParent(IEIBControl contorl)
        {
            if (parentControls.ContainsKey(contorl))
            {
                return parentControls[contorl];
            }
            else
            {
                return null;
            }
        }

        public void InvalidateAll()
        {
            foreach (IEIBControl control in eibControls)
            {
                if (control is Control)
                {
                    ((Control)control).Invalidate();
                }
            }
        }

        public void Clear()
        {
            eibControls.Clear();
            propControls.Clear();
            parentControls.Clear();
        }

        public IEIBControl this[int index]
        {
            get { return eibControls[index]; }
        }

        public void Add(IEIBControl eibControl, BaseWindow formPattern)
        {
            eibControls.Add(eibControl);
            if (!parentControls.ContainsKey(eibControl))
            {
                parentExpose pexp = new parentExpose();
                pexp.baseFormPattern = formPattern;
                pexp.parent = ((Control)eibControl).Parent;
                parentControls.Add(eibControl, pexp);
            }
        }
        public void AddWithParent(IEIBControl eibControl,IEIBControl parentControl, BaseWindow formPattern)
        {
            if (!eibControls.Contains(eibControl))
            {
                eibControls.Add(eibControl);
                if (!parentControls.ContainsKey(eibControl))
                {
                    parentExpose pexp = new parentExpose();
                    pexp.baseFormPattern = formPattern;
                    pexp.parent = (Control)parentControl;
                    parentControls.Add(eibControl, pexp);
                }
            }
        }

        public void Insert(int index, IEIBControl eibControl)
        {
            eibControls.Insert(index, eibControl);
            /*parentExpose pexp = new parentExpose();
            pexp.baseFormPattern = formPattern;
            pexp.parent = ((Control)eibControl).Parent;
            parentControls.Add(eibControl, pexp);*/
        }

        public void RemoveAt(int index)
        {
            if (index >=0 &&  eibControls.Count > index)
            {
                //parentControls.Remove(eibControls[index]);
                eibControls.RemoveAt(index);

            }
        }

        public void Remove(IEIBControl control)
        {
            if (eibControls.Contains(control))
            {
                eibControls.Remove(control);
            }
        }

        public int IndexOf(IEIBControl IEIBControl)
        {
            return eibControls.IndexOf(IEIBControl);
        }

        public int Count
        {
            get { return eibControls.Count; }
        }

        #region IEnumerable<IEIBControl> Members

        public IEnumerator<IEIBControl> GetEnumerator()
        {
            return eibControls.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        #endregion
    }
}
