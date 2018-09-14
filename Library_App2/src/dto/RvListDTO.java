package dto;

import java.sql.Timestamp;

public class RvListDTO {

	private int b_idx;
	private String title;
	private Timestamp reserveDate;
	
	public RvListDTO(int b_idx, String title, Timestamp reserveDate) {
		this.b_idx = b_idx;
		this.title = title;
		this.reserveDate = reserveDate;
	}
	

	public int getB_idx() {
		return b_idx;
	}

	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(Timestamp reserveDate) {
		this.reserveDate = reserveDate;
	}
	
	
}
