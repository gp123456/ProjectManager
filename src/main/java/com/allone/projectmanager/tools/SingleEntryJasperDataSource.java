package com.allone.projectmanager.tools;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class SingleEntryJasperDataSource implements JRDataSource {
	
	private boolean runOnce;
	
	public SingleEntryJasperDataSource() {
		this.runOnce = false;
	}

	@Override
	public Object getFieldValue(JRField fieldName) throws JRException {
		return "1";
	}

	@Override
	public boolean next() throws JRException {
		if (!runOnce) {
			runOnce = true;
			return true;
		}
		return false;
	}

}