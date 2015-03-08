package obi1.fi.business.to;

public final class NumeradoraTO {

	private String entityName;
	private Integer nextVal;
	private Integer maxBuffer;
	
	public NumeradoraTO(String entityName, Integer nextVal, Integer maxBuffer) {
		this.entityName = entityName;
		this.nextVal = nextVal;
		this.maxBuffer = maxBuffer;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public Integer getNextVal() {
		return nextVal;
	}
	
	public void setNextVal(Integer nextVal) {
		this.nextVal = nextVal;
	}

	public Integer getMaxBuffer() {
		return maxBuffer;
	}

	public void setMaxBuffer(Integer maxBuffer) {
		this.maxBuffer = maxBuffer;
	}
	
}
