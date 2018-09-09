package dto;

import java.sql.Timestamp;

public class RvListDTO {

	private int m_idx;
	private int b_idx;
	private Timestamp reserveDate;
	private Timestamp rentalDate;
	private Timestamp calcelDate;
	
	public RvListDTO(int m_idx, int b_idx, Timestamp reserveDate, Timestamp rentalDate, Timestamp calcelDate) {
		this.m_idx = m_idx;
		this.b_idx = b_idx;
		this.reserveDate = reserveDate;
		this.rentalDate = rentalDate;
		this.calcelDate = calcelDate;
	}
	public RvListDTO(int m_idx, Timestamp reserveDate, Timestamp rentalDate, Timestamp calcelDate) {
		this.m_idx = m_idx;
		this.reserveDate = reserveDate;
		this.rentalDate = rentalDate;
		this.calcelDate = calcelDate;
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

	public Timestamp getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(Timestamp reserveDate) {
		this.reserveDate = reserveDate;
	}

	public Timestamp getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Timestamp rentalDate) {
		this.rentalDate = rentalDate;
	}

	public Timestamp getCalcelDate() {
		return calcelDate;
	}

	public void setCalcelDate(Timestamp calcelDate) {
		this.calcelDate = calcelDate;
	}
	
	
}
