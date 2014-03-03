package com.oxymedical.core.router;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

/**
 * Interface for HICRouter
 * @author hsi1
 *
 */
public interface IRouter
{
	public IHICData routeToComponent(IHICData hicData);
	public IHICData routeToComponentEx(IHICData hicData,String methodName);
}
