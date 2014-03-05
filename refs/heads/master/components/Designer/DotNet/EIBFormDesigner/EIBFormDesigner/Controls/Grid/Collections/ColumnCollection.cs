using System;
using System.Collections;
using EIBFormDesigner.Controls;
using System.Windows.Forms;

namespace EIBFormDesigner.Controls.Grid.Collections
{
    /// <summary>
    /// Summary description for RowsCollections.
    /// </summary>
    public class ColumnCollection : IList
    {
        private EIBScrollableRow parentRow;

        public ColumnCollection(EIBScrollableRow parent)
        {
            this.parentRow = parent;
        }

        #region IList Members

        public bool IsReadOnly
        {
            get
            {
                return parentRow.Controls.IsReadOnly;
            }

        }

        object IList.this[int index]
        {
            get
            {
                return this.parentRow.Controls[index];
            }
            set
            {
                throw new NotSupportedException("IList.this");
            }
        }

        public Control this[int index]
        {
            get
            {
                return parentRow.Controls[index] as Control;
            }
        }

        public void RemoveAt(int index)
        {
            parentRow.Controls.RemoveAt(index);
        }

        public void Insert(int index, object value)
        {
            throw new NotSupportedException("Insert");
        }

        public void Remove(object value)
        {
            this.parentRow.Controls.Remove(value as Control);
        }


        public bool Contains(object ctl)
        {
            return this.parentRow.Contains(ctl as System.Windows.Forms.Control);
        }

        public void Clear()
        {
            foreach (Control ctrl in this.parentRow.Controls)
                if (ctrl != null)
                    this.parentRow.Controls.Remove(ctrl);
        }

        public int IndexOf(object control)
        {
            return this.parentRow.Controls.IndexOf(control as System.Windows.Forms.Control);
        }

        int IList.Add(object value)
        {
            this.parentRow.Controls.Add(value as Control);
            return this.parentRow.Controls.IndexOf(value as Control);
        }

        public void Add(Control value)
        {
            this.parentRow.Controls.Add(value);
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
                return false;
            }
        }

        public int Count
        {
            get
            {
                return this.parentRow.Controls.Count;
            }
        }

        public void CopyTo(Array array, int index)
        {
            this.parentRow.Controls.CopyTo(array, index);
        }

        public object SyncRoot
        {
            get
            {
                return this.parentRow.Controls;
            }
        }

        #endregion

        #region IEnumerable Members

        public IEnumerator GetEnumerator()
        {
            return this.parentRow.Controls.GetEnumerator();
        }

        #endregion

    }

}
