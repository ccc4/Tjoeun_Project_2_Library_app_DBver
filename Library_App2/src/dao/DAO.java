package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import db.util.*;
import dto.BookDTO;
import dto.MemberDTO;
import dto.RtListDTO;
import dto.RvListDTO;

public class DAO {
	private static DAO instance = new DAO();

	public static DAO getInstance() {
		return instance;
	}

	private DAO() {
		// 생성자 싱글턴
	}

	public int mJoin(Connection conn, String id, String pw, String nickname, String name, int age, String gender,
			String tel, String email_1, String email_2, String address) {

		int re = 0;

		PreparedStatement pstmt = null;

		try {
			String sql = "insert into member (m_id, m_pw, m_nickname, m_name, m_age, m_gender, m_tel, m_email_1, m_email_2, m_address) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nickname);
			pstmt.setString(4, name);
			pstmt.setInt(5, age);
			pstmt.setString(6, gender);
			pstmt.setString(7, tel);
			pstmt.setString(8, email_1);
			pstmt.setString(9, email_2);
			pstmt.setString(10, address);

			int check = pstmt.executeUpdate();

			if (check == 0)
				re = 0;
			else
				re = 1;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(pstmt);
		}

		return re;
	}

	public int mLogin(Connection conn, String id, String pw) {

		int re = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT m_pw FROM member where m_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("m_pw").equals(pw)) {
					re = 1; // 성공
				} else {
					re = 0;
				}
			} else {
				re = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}

		return re;
	}

	public MemberDTO getSession(Connection conn, String id) {
		MemberDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT m_idx, m_nickname FROM member WHERE m_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int idx = Integer.parseInt(rs.getString("m_idx"));
				String nickname = rs.getString("m_nickname");

				dto = new MemberDTO(idx, nickname);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}

		return dto;
	}

	public MemberDTO getMemberInfo(Connection conn, int session_idx) {
		MemberDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM member WHERE m_idx = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String id = rs.getString("m_id");
				String pw = rs.getString("m_pw");
				String nickname = rs.getString("m_nickname");
				String name = rs.getString("m_name");
				int age = Integer.parseInt(rs.getString("m_age"));
				String gender = rs.getString("m_gender");
				int tel = Integer.parseInt(rs.getString("m_tel"));
				String email_1 = rs.getString("m_email_1");
				String email_2 = rs.getString("m_email_2");
				String address = rs.getString("m_address");
				Timestamp joinDate = rs.getTimestamp("m_joinDate");

				dto = new MemberDTO(id, pw, nickname, name, age, gender, tel, email_1, email_2, address, joinDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}

		return dto;
	}

	public int mModify(Connection conn, int idx, String nickname, String name, int age, String gender, String tel,
			String email_1, String email_2, String address) {

		int re = 0;

		PreparedStatement pstmt = null;

		String sql = "UPDATE member SET m_nickname = ?, m_name = ?, m_age = ?, m_gender = ?, m_tel = ?, m_email_1 = ?, m_email_2 = ?, m_address = ? WHERE m_idx = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.setString(2, name);
			pstmt.setInt(3, age);
			pstmt.setString(4, gender);
			pstmt.setString(5, tel);
			pstmt.setString(6, email_1);
			pstmt.setString(7, email_2);
			pstmt.setString(8, address);
			pstmt.setInt(9, idx);

			int check = pstmt.executeUpdate();
			if (check == 0) {
				re = 0;
			} else {
				re = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(pstmt);
		}

		return re;
	}
	
	
	
	
	
	
	
	// 책 DAO
	
	public ArrayList<BookDTO> bookList(Connection conn) {
		
		ArrayList<BookDTO> dtos = new ArrayList<>();
		BookDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM book ORDER BY b_title ASC";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int idx = rs.getInt("b_idx");
				String title = rs.getString("b_title");
				String author = rs.getString("b_author");
				String publisher = rs.getString("b_publisher");
				
				dto = new BookDTO(idx, title, author, publisher);
				dtos.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}
		
		return dtos;
	}
	
	public int checkBookRental(Connection conn, int b_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM rental_list WHERE rt_b_idx = ? AND rt_returnDate IS NULL";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_idx);
			rs = pstmt.executeQuery();
			// 있으면 대여중
			if(rs.next()) {
				re = 1;
			} else {
				re = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}
		
		return re;
	}
	
	public int checkBookReservation(Connection conn, int b_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM reservation_list WHERE rv_b_idx = ? AND rv_rentalDate IS NULL AND rv_cancelDate IS NULL";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_idx);
			rs = pstmt.executeQuery();
			// 있으면 대여중
			if(rs.next()) {
				re = 1;
			} else {
				re = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}
		
		return re;
	}
	
//	public RtListDTO getRentalList(Connection conn, int b_idx) {
//		
//		RtListDTO dto = null;
//		
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		
//		String sql = "SELECT rt_m_idx, , rt_rentalDate, rt_returnDate FROM rental_list WHERE rt_b_idx = ?";
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, b_idx);
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				int m_idx = rs.getInt("rt_m_idx");
//				Timestamp rentalDate = rs.getTimestamp("rt_rentalDate");
//				Timestamp returnDate = rs.getTimestamp("rt_returnDate");
//				
//				dto = new RtListDTO(m_idx, rentalDate, returnDate);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DB_Closer.close(rs);
//			DB_Closer.close(pstmt);
//		}
//		
//		return dto;
//	}
//	
//	public RvListDTO getReservationList(Connection conn, int b_idx) {
//		
//		RvListDTO dto = null;
//		
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		
//		String sql = "SELECT rv_m_idx, ,rv_reserveDate, rv_rentalDate, rv_cancelDate FROM reservation_list WHERE rv_b_idx = ?";
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, b_idx);
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				int m_idx = rs.getInt("rv_m_idx");
//				Timestamp reserveDate = rs.getTimestamp("rv_reserveDate");
//				Timestamp rentalDate = rs.getTimestamp("rv_rentalDate");
//				Timestamp cancelDate = rs.getTimestamp("rv_cancelDate");
//				
//				dto = new RvListDTO(m_idx, reserveDate, rentalDate, cancelDate);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DB_Closer.close(rs);
//			DB_Closer.close(pstmt);
//		}
//		
//		return dto;
//	}
}
