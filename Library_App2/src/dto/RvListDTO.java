package dto;

import java.sql.Timestamp;

public class RvListDTO {

	private String title;
	private Timestamp reserveDate;
	
	public RvListDTO(String title, Timestamp reserveDate) {
		this.title = title;
		this.reserveDate = reserveDate;
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
