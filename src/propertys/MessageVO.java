package propertys;

public class MessageVO<T> {

	private T rows;
	
	private int total;
	
	private String error;
	

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
