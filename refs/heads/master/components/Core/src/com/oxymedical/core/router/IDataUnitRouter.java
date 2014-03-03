/**
 * 
 */
package com.oxymedical.core.router;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

/**
 * @author vka
 *
 */
public interface IDataUnitRouter
{
	public IHICData routeToModeler(IDataUnit dataUnit);
}
