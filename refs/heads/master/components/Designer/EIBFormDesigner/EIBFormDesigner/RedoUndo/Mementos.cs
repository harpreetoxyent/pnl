using System;
using System.Collections.Generic;
using System.Text;
using GenericUndoRedo;
using System.Windows.Forms;
using System.Reflection;
using EIBFormDesigner;

namespace EIBFormDesigner.UndoRedo
{
    abstract class ControlMemento : IMemento<ControlPool>
    {
        #region IMemento<ControlPool> Members

        public abstract IMemento<ControlPool> Restore(ControlPool target);
        
        #endregion
    }
    
    class InsertControlMemento : ControlMemento
    {
        private int index;
        public InsertControlMemento(int index)
        {
            this.index = index;
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {
            IEIBControl removed = target[index];
            IMemento<ControlPool> inverse = new RemoveControlMemento(index, removed);
            target.RemoveAt(index);
            return inverse;
        }
    }
    
    class RemoveControlMemento : ControlMemento
    {
        IEIBControl removed;
        int index;
        public RemoveControlMemento(int index, IEIBControl removed)
        {
            this.index = index;
            this.removed = removed;
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {
            IMemento<ControlPool> inverse = new InsertControlMemento(index);
            target.Insert(index, removed);
            return inverse;
        }
    }

    class AddControlMemento : ControlMemento
    {
        public AddControlMemento()
        {
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {
            int index = target.Count - 1;
            IMemento<ControlPool> inverse = new RemoveControlMemento(index, target[index]);
            target.RemoveAt(target.Count - 1);
            return inverse;
        }
    }

    class DeleteControlMemento : ControlMemento
    {
        IEIBControl controlToDelete;
        EIBFormDesigner.Designer.BaseWindow baseWindow;
        IEIBControl parentControl;
        public DeleteControlMemento(IEIBControl control,EIBFormDesigner.Designer.BaseWindow basewin)
        {
            controlToDelete = control;
            parentControl = (IEIBControl)((Control)control).Parent;
            this.baseWindow = basewin;
        }
        public DeleteControlMemento(IEIBControl control,IEIBControl parent, EIBFormDesigner.Designer.BaseWindow basewin)
        {
            controlToDelete = control;
            parentControl = parent;
            this.baseWindow = basewin;
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {
            IMemento<ControlPool> inverse = new DontDeleteControlMemento(controlToDelete, parentControl, baseWindow);
            target.AddWithParent(controlToDelete,parentControl,baseWindow);
            return inverse;
        }
    }

    class DontDeleteControlMemento : ControlMemento
    {
        IEIBControl dCtrl;
        EIBFormDesigner.Designer.BaseWindow baseWindow;
        IEIBControl parentControl;
        public DontDeleteControlMemento(IEIBControl control, IEIBControl parent, EIBFormDesigner.Designer.BaseWindow basewin)
        {
            dCtrl = control;
            this.parentControl = parent;
            this.baseWindow = basewin;
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {

            int index = target.Count - 1;
            IMemento<ControlPool> inverse = new DeleteControlMemento(dCtrl, parentControl,baseWindow);
            target.RemoveAt(target.Count - 1);
            return inverse;
        }
    }

    class PropertyChangeMemento : ControlMemento
    {
        string propertyName;
        object propertyValue;
        object selObject;
        public PropertyChangeMemento(string propertyName, object oldValue, object selObject)
        {
            this.propertyName = propertyName;
            propertyValue = oldValue;
            this.selObject = selObject;
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {

            PropertyInfo p = getPropertyInfo();
            //System.Reflection.PropertyInfo p = ((Control)selObject).GetType().GetProperty(propertyName);
            object newpropval = getPropertyValue();//p.GetGetMethod().Invoke(selObject, null);
            setPropertyValue();//p.GetSetMethod().Invoke(selObject, new object[] { propertyValue });
            IMemento<ControlPool> inverse = new PropertyOldChangeMemento(propertyName, newpropval,selObject);
            UIEventManager.showProperty();
            return inverse;
        }
        public void setPropertyValue()
        {
            string[] slPropName = propertyName.Split(':');
            PropertyInfo p = null;
            p = selObject.GetType().GetProperty(slPropName[0]);
            PropertyInfo pp = p;
            object objVal = p.GetGetMethod().Invoke(selObject, null);
            if (slPropName.Length == 1)
            {
                objVal = propertyValue;
            }
            for (int i = 1; i < slPropName.Length; i++)
            {
                pp = pp.PropertyType.GetProperty(slPropName[i]);
                object childobjVal = pp.GetGetMethod().Invoke(objVal, null);
                pp.GetSetMethod().Invoke(objVal, new object[] { propertyValue});
            }
            object pVal = p.GetSetMethod().Invoke(selObject, new object[] { objVal });
        }
        public object getPropertyValue()
        {
            string[] slPropName = propertyName.Split(':');
            PropertyInfo p = null;
            p = selObject.GetType().GetProperty(slPropName[0]);
            object pVal = p.GetGetMethod().Invoke(selObject, null);
            if (p == null)
            {
                return p;
            }
            for (int i = 1; i < slPropName.Length; i++)
            {
                p = p.PropertyType.GetProperty(slPropName[i]);
                pVal = p.GetGetMethod().Invoke(pVal, null);
            }
            return pVal;
        }

        public PropertyInfo getPropertyInfo()
        {
            string[] slPropName = propertyName.Split(':');
            PropertyInfo p = null;
            p = selObject.GetType().GetProperty(slPropName[0]);
            if (p == null)
            {
                return p;
            }
            for(int i = 1 ;i<slPropName.Length;i++)
            {
                p = p.PropertyType.GetProperty(slPropName[i]);
            }
            return p;

        }
    }
    class PropertyOldChangeMemento : ControlMemento
    {
        string propertyName;
        object propertyValue;
        object selObject;
        public PropertyOldChangeMemento(string propertyName, object oldValue, object selObject)
        {
            this.propertyName = propertyName;
            propertyValue = oldValue;
            this.selObject = selObject;
        }

        public override IMemento<ControlPool> Restore(ControlPool target)
        {
            
            if (selObject is Control)
            {
                /*System.Reflection.PropertyInfo p = ((Control)selObject).GetType().GetProperty(propertyName);
                object newpropval = p.GetGetMethod().Invoke(selObject, null);
                p.GetSetMethod().Invoke(selObject, new object[] { propertyValue });*/
                PropertyInfo p = getPropertyInfo();
                object newpropval = getPropertyValue();
                setPropertyValue();
                IMemento<ControlPool> inverse = new PropertyChangeMemento(propertyName, newpropval,selObject);
                UIEventManager.showProperty();
                return inverse;
            }
            return null;
        }

        public void setPropertyValue()
        {
            string[] slPropName = propertyName.Split(':');
            PropertyInfo p = null;
            p = selObject.GetType().GetProperty(slPropName[0]);
            PropertyInfo pp = p;
            object objVal = p.GetGetMethod().Invoke(selObject, null);
            if (slPropName.Length == 1)
            {
                objVal = propertyValue;
            }
            for (int i = 1; i < slPropName.Length; i++)
            {
                pp = pp.PropertyType.GetProperty(slPropName[i]);
                object childobjVal = pp.GetGetMethod().Invoke(objVal, null);
                pp.GetSetMethod().Invoke(objVal, new object[] { propertyValue });
            }
            object pVal = p.GetSetMethod().Invoke(selObject, new object[] { objVal });
        }
        public object getPropertyValue()
        {
            string[] slPropName = propertyName.Split(':');
            PropertyInfo p = null;
            p = selObject.GetType().GetProperty(slPropName[0]);
            object pVal = p.GetGetMethod().Invoke(selObject, null);
            if (p == null)
            {
                return p;
            }
            for (int i = 1; i < slPropName.Length; i++)
            {
                p = p.PropertyType.GetProperty(slPropName[i]);
                pVal = p.GetGetMethod().Invoke(pVal, null);
            }
            return pVal;
        }

        public PropertyInfo getPropertyInfo()
        {
            string[] slPropName = propertyName.Split(':');
            PropertyInfo p = null;
            p = selObject.GetType().GetProperty(slPropName[0]);
            if (p == null)
            {
                return p;
            }
            for (int i = 1; i < slPropName.Length; i++)
            {
                p = p.PropertyType.GetProperty(slPropName[i]);
            }
            return p;

        }
    }
}
