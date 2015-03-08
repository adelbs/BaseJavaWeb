package obi1.fi.common.to;

import java.util.ArrayList;
import java.util.List;

import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.MessageSourceUtil;

public final class DataTableTO {

	private List<String> header;
	private List<String> fields;
	private List<Object> values;
	
	private boolean paginate;
	private int maxItemsPage = 1;
	private int numTotalItems = 1;
	private int currPage = 1;
	
	public DataTableTO(String... fields) {
		String fieldName;
		
		this.fields = new ArrayList<String>();
		this.header = new ArrayList<String>();
		this.paginate = true;
		
		for (String field : fields) {
			if (field.indexOf(".") > -1) {
				fieldName = field.substring(field.lastIndexOf(".") + 1);
			}
			else {
				fieldName = field;
			}
			
			this.fields.add(field);
			
			try {
				this.header.add(MessageSourceUtil.getMessage(fieldName));
			}
			catch (Exception x) {
				this.header.add(fieldName);
			}
		}
	}
	
	public void setValues(List<Object> values) {
		try {
			//TODO colocar somente colunas que forem utilizadas
			this.values = values;
		}
		catch (Exception x) {
			throw new FiException(x);
		}
	}

	public List<Object> getValues() {
		return values;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public int getMaxItemsPage() {
		return maxItemsPage;
	}

	public void setMaxItemsPage(int maxItemsPage) {
		this.maxItemsPage = maxItemsPage;
	}

	public int getNumTotalItems() {
		return numTotalItems;
	}

	public void setNumTotalItems(int numTotalItems) {
		this.numTotalItems = numTotalItems;
	}

	public boolean isPaginate() {
		return paginate;
	}

	public void setPaginate(boolean paginate) {
		this.paginate = paginate;
	}
}
