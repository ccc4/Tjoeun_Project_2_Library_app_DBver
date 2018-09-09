package dto;

import java.sql.Timestamp;

public class RtListDTO {
	
	private int m_idx;
	private int b_idx;
	private Timestamp rentalDate;
	private Timestamp returnDate;
	
	public RtListDTO(int m_idx, int b_idx, Timestamp rentalDate, Timestamp returnDate) {
		this.m_idx = m_idx;
		this.b_idx = b_idx;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
	}
	public RtListDTO(int m_idx, Timestamp rentalDate, Timestamp returnDate) {
		this.m_idx = m_idx;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
	}

	public int getM_idx() {
		return m_idx;
	}

	public void setM_idx(int m_idx) {
		this.m_idx = m_idx;
	}

	public int getB_idx() {
		return b_idx;
	}

	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}

	public Timestamp getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Timestamp rentalDate) {
		this.rentalDate = rentalDate;
	}

	public Timestamp getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}
	
	
}
