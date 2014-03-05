using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using EIBFormDesigner;
using GenericUndoRedo;
using EIBFormDesigner.UndoRedo;
using EIBFormDesigner.Event;

namespace EIBFormDesigner.Properties
{
    public class FilteredPropertyGrid : PropertyGrid
    {
        Attribute[] attributes = new Attribute[4];
        List<PropertyDescriptor> m_PropertyDescriptors = new List<PropertyDescriptor>();
        List<string > m_propertyName = new List<string>();
        public static Dictionary<Type, List<string>> InvalidProps = new Dictionary<Type, List<string>>();
        private ObjectWrapper m_Wrapper = null;
        private AttributeCollection m_BrowsableAttributes = null;
		public new AttributeCollection BrowsableAttributes 
        {
			get { return m_BrowsableAttributes; }
            set
            {
                if (m_BrowsableAttributes != value)
                {
                    m_BrowsableAttributes = value;
                    RefreshProperties();
                }
            }
		}
        public new object SelectedObject
        {
            get { return m_Wrapper != null ? ((ObjectWrapper)base.SelectedObject).SelectedObject : null; }
            set
            {
                // Set the new object to the wrapper and create one if necessary.
                if (m_Wrapper == null)
                {
                    m_Wrapper = new ObjectWrapper(value);
                    RefreshProperties();
                }
                else if (m_Wrapper.SelectedObject != value)
                {
                    bool needrefresh = value.GetType() != m_Wrapper.SelectedObject.GetType();
                    m_Wrapper.SelectedObject = value;
                    if (needrefresh) RefreshProperties();
                }
                
                // Set the list of properties to the wrapper.
                m_Wrapper.PropertyDescriptors = m_PropertyDescriptors;
                // Link the wrapper to the parent PropertyGrid.
                base.SelectedObject = m_Wrapper;
            }
        }
        /// <summary>Called when the browsable properties have changed.</summary>
        private void OnBrowsablePropertiesChanged()
        {
            if (m_Wrapper == null) return;
        }
        public FilteredPropertyGrid()
        {
            base.SelectedObject = m_Wrapper;
            attributes[0] = new CategoryAttribute("Layout");
            attributes[1] = new CategoryAttribute("Data");
            attributes[2] = new CategoryAttribute("Appearance");
            attributes[3] = new CategoryAttribute("Behavior");
            this.BrowsableAttributes = new
               AttributeCollection(attributes);
            this.PropertyValueChanged += new PropertyValueChangedEventHandler(FilteredPropertyGrid_PropertyValueChanged);

        }

        public void FilteredPropertyGrid_PropertyValueChanged(object s, PropertyValueChangedEventArgs e)
        {
            try
            {
                string getProperty = getPropertyChanged(e);

                if (!UIEventManager.form.Controlpool.propControls.Contains((IEIBControl)this.SelectedObject))
                {
                    UIEventManager.form.Controlpool.propControls.Add((IEIBControl)this.SelectedObject);
                }
                UIEventManager.form.History.Do(new PropertyChangeMemento(getProperty, e.OldValue, this.SelectedObject));
            }
            catch
            {
            }
        }

        private string getPropertyChanged(PropertyValueChangedEventArgs e)
        {
            string retProperty = "";
            GridItem g = e.ChangedItem;
            while (true)
            {
                retProperty = g.Label + retProperty;
                if (g.Parent.Label == "Appearance" || g.Parent.Label == "Data" || g.Parent.Label == "Layout" || g.Parent.Label == "Behavior")
                {
                    break;
                }
                else
                {
                    retProperty=":"+retProperty;
                    g = g.Parent;
                }
            }
            return retProperty;
        }
        public void RefreshProperties()
        {
            if (m_Wrapper == null) return;
            // Clear the list of properties to be displayed.
            m_PropertyDescriptors.Clear();
            if (m_BrowsableAttributes != null && m_BrowsableAttributes.Count > 0)
            {
                // Add to the list the attributes that need to be displayed.
                foreach (Attribute attribute in m_BrowsableAttributes)
                {
                    ShowAttribute(attribute);
                }
            }
        }
        private void ShowAttribute(Attribute attribute)
        {
            if (m_Wrapper != null)
            {
                PropertyDescriptorCollection filteredoriginalpropertydescriptors =
                    TypeDescriptor.GetProperties(m_Wrapper.SelectedObject, new Attribute[] { attribute });
                if (filteredoriginalpropertydescriptors == null || filteredoriginalpropertydescriptors.Count == 0)
                {
                }
                else
                {
                    foreach (PropertyDescriptor propertydescriptor in filteredoriginalpropertydescriptors)
                    {
                        if (checkProperty(m_Wrapper.SelectedObject.GetType(), propertydescriptor.DisplayName))
                        {
                            ShowProperty(propertydescriptor);
                        }
                    }
                }
            }
        }
        private void ShowProperty(PropertyDescriptor property)
        {
            if (!m_PropertyDescriptors.Contains(property))
                m_PropertyDescriptors.Add(property);
        }
        private bool checkProperty(Type objectType, string propertyName)
        {
            if (InvalidProps.ContainsKey(objectType))
            {
                List<string> listProp = InvalidProps[objectType];
                if (listProp.Contains(propertyName))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            return true;
        }
    }
}
