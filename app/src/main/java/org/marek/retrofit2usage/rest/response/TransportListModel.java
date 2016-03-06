package org.marek.retrofit2usage.rest.response;

import java.util.List;

public class TransportListModel<T> extends TransportModel {
	private List<T> list;
    private int total;

	public TransportListModel() {
		super();
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(final int total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(final List<T> list) {
		this.list = list;
	}


	@Override
	public String toString() {
		return "TransportListModel [total=" + total + ", message=" + getMessage() + ", errorCode=" + getErrorCode() + ", list=" + list + ", success=" + isSuccess() + "]";
	}
}