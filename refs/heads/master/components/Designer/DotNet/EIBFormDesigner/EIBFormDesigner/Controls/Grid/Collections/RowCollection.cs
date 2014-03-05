using System;
using System.Collections;
using EIBFormDesigner.Controls;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls.Grid.Collections
{
    /// <summary>
    /// Summary description for RowsCollections.
    /// </summary>
    public class RowCollection : IList
    {
        private Panel parentTable;

        public RowCollection(Panel parent)
        {
            if (parent is EIBGrid || parent is EIBLattice)
                this.parentTable = parent;
        }
        #region IList Members

        public bool IsReadOnly
        {
            get
            {
                return parentTable.Controls.IsReadOnly;
            }

        }

        object IList.this[int index]
        {
            get
            {
                return this.parentTable.Controls[index];
            }
            set
            {
                throw new NotSupportedException("IList.this");
            }
        }

        public EIBScrollableRow this[int index]
        {
            get
            {
                return parentTable.Controls[index] as EIBScrollableRow;
            }
        }

        public void RemoveAt(int index)
        {
            parentTable.Controls.RemoveAt(index);
        }

        public void Insert(int index, object value)
        {
            throw new NotSupportedException("Insert");
        }

        public void Remove(object value)
        {
            this.parentTable.Controls.Remove(value as EIBScrollableRow);
        }


        public bool Contains(object ctl)
        {
            return this.parentTable.Contains(ctl as System.Windows.Forms.Control);
        }

        public void Clear()
        {
            foreach (EIBScrollableRow ctrl in this.parentTable.Controls)
                if (ctrl != null)
                    this.parentTable.Controls.Remove(ctrl);
        }

        public int IndexOf(object control)
        {
            return this.parentTable.Controls.IndexOf(control as System.Windows.Forms.Control);
        }

        int IList.Add(object value)
        {
            this.parentTable.Controls.Add(value as EIBScrollableRow);
            return this.parentTable.Controls.IndexOf(value as EIBScrollableRow);
        }

        public void Add(EIBScrollableRow value)
        {
            // TODO:  Add RowsCollections.Add implementation
            this.parentTable.Controls.Add(value);
        }

        public bool IsFixedSize
        {
            get
            {
                return false;
            }
        }


        #endregion

        #region ICollection Members

        public bool IsSynchronized
        {
            get
            {
                // TODO:  Add RowsCollections.IsSynchronized getter implementation
                return false;
            }
        }

        public int Count
        {
            get
            {
                return this.parentTable.Controls.Count;
            }
        }

        public void CopyTo(Array array, int index)
        {
            this.parentTable.Controls.CopyTo(array, index);
        }

        public object SyncRoot
        {
            get
            {
                // TODO:  Add RowsCollections.SyncRoot getter implementation
                return this.parentTable.Controls;
            }
        }

        #endregion

        #region IEnumerable Members

        public IEnumerator GetEnumerator()
        {
            // TODO:  Add RowsCollections.GetEnumerator implementation
            return this.parentTable.Controls.GetEnumerator();
        }

        #endregion

    }

}
