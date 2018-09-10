package dto;

import java.sql.Timestamp;

public class RtListDTO {
	
	private String title;
	private Timestamp rentalDate;
	
	public RtListDTO(String title, Timestamp rentalDate) {
		this.title = title;
		this.rentalDate = rentalDate;
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
