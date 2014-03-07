package com.pnl.component.nlp;

import java.io.IOException;

import com.oxymedical.core.commonData.IHICData;

public interface INLPComponent {
	public IHICData execute(IHICData hicData) throws IOException;
}
