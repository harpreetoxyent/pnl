package com.oxymedical.component.renderer.data;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class JRHibernateDataSource implements JRDataSource {

    protected HashMap fieldsToIdxMap=new HashMap();

    protected Iterator iterator;

    protected Object currentValue;
    

    public JRHibernateDataSource(Collection list) {
        this.iterator = list.iterator();
    }

    public JRHibernateDataSource(Map list) {
      this.iterator = list.values().iterator();
  }

    private Object nestedFieldValue(Object object, String field) {
        Object value = null;
        if (field.indexOf("__")>-1) {
            try {
                Method nestedGetter =
                	PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(object,
                			field.substring(0,field.indexOf("__"))));
                Object nestedObject = nestedGetter.invoke(object, null);
               
value=nestedFieldValue(nestedObject,field.substring(field.indexOf("__")+2,field.length()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }           
        } else {
            try {
                Method getter =
PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(object,
field));
                value = getter.invoke(object, null);
               
if(Collection.class.isAssignableFrom(getter.getReturnType())) {
                    return new JRHibernateDataSource((Collection)value);
                }
                if(Map.class.isAssignableFrom(getter.getReturnType())) {
                    return new JRHibernateDataSource((Map)value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }           
        }
        return value;
    }
   
    public Object getCurrentValue() throws JRException {
        return currentValue;
    }

    public Object getFieldValue(JRField field) throws JRException {
        return nestedFieldValue(currentValue,field.getName());
    }
   
    public boolean next() throws JRException {
        currentValue = iterator.hasNext() ? iterator.next() : null;
        return currentValue != null;
    } 
}
