package dto;

import java.sql.Timestamp;

public class RtListDTO {
	
	private int b_idx;
	private String title;
	private Timestamp rentalDate;
	
	public RtListDTO(int b_idx, String title, Timestamp rentalDate) {
		this.b_idx = b_idx;
		this.title = title;
		this.rentalDate = rentalDate;
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

	public Timestamp getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Timestamp rentalDate) {
		this.rentalDate = rentalDate;
	}
}
