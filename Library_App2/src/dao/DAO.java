package dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import db.util.*;
import dto.BookDTO;
import dto.MemberDTO;
import dto.RtListDTO;
import dto.RvListDTO;

public class DAO {
	
	private static final String BOOK_IMAGES_DIR = ".\\BookImages";
	
	
	private static DAO instance = new DAO();

	public static DAO getInstance() {
		return instance;
	}

	private DAO() {
		// 생성자 싱글턴
	}
	
	public int checkIdDuplication(Connection conn, String id) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM member WHERE m_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
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
	
	public int checkNicknameDuplication(Connection conn, String nickname) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM member WHERE m_nickname = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			
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

	public int mJoin(Connection conn, String id, String pw, String nickname, String name, int age, String gender,
			String tel, String email_1, String email_2, String address) {

		int re = 0;

		PreparedStatement pstmt = null;

		String sql = "insert into member (m_id, m_pw, m_nickname, m_name, m_age, m_gender, m_tel, m_email_1, m_email_2, m_address) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
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

		String sql = "SELECT m_pw FROM member where m_id = ?";
		
		try {
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
	
	public ArrayList<BookDTO> getBookList(Connection conn) {
		
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

	public int bAdd(Connection conn, String title, String author, String publisher, String imgName, String targetImgFilePath) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO book (b_title, b_author, b_publisher, b_imgName) VALUES (?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, publisher);
			pstmt.setString(4, imgName);
			
			int check = pstmt.executeUpdate();
			
			if(check == 0) {
				re = 0;
			} else {
				re = 1;
			}
			
			// 책 이미지 저장 (등록했으면)
			if(!imgName.equals("")) {
				File targetImgFile = new File(targetImgFilePath);
				byte[] targetImgFileContents = new byte[(int) targetImgFile.length()];
				
				File saveImgFileDir = new File(BOOK_IMAGES_DIR);
				if(!saveImgFileDir.exists()) saveImgFileDir.mkdirs();
				File saveFileName = new File(saveImgFileDir, imgName);
				
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(targetImgFile));
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFileName));
				in.read(targetImgFileContents);
				out.write(targetImgFileContents);
				out.flush();
				JOptionPane.showMessageDialog(null, "책 이미지 저장 완료", "책 등록", JOptionPane.INFORMATION_MESSAGE);
				
				out.close();
				in.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(pstmt);
		}
		
		return re;
	}
	
	public int getBookIdx_FromTitle(Connection conn, String title) {
		
		int idx = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT b_idx FROM book WHERE b_title = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				idx = rs.getInt("b_idx");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}
		
		return idx;
	}
	
	public BookDTO getBookInfo(Connection conn, int b_idx) {
		
		BookDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM book WHERE b_idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int idx = rs.getInt("b_idx");
				String title = rs.getString("b_title");
				String author = rs.getString("b_author");
				String publisher = rs.getString("b_publisher");
				String imgName = rs.getString("b_imgName");
				Timestamp addDate = rs.getTimestamp("b_addDate");
				
				dto = new BookDTO(idx, title, author, publisher, imgName, addDate);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(rs);
			DB_Closer.close(pstmt);
		}
		
		return dto;
	}
	
	public int checkBookReservationMine(Connection conn, int member_idx, int book_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM reservation_list WHERE rv_m_idx = ? AND rv_b_idx = ? AND rv_rentalDate IS NULL AND rv_cancelDate IS NULL";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			pstmt.setInt(2, book_idx);
			rs = pstmt.executeQuery();
			
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
	
	
	
	public void bRental_AtReservation(Connection conn, int member_idx, int book_idx) {
		
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE reservation_list SET rv_rentalDate = now() WHERE rv_m_idx = ? AND rv_b_idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			pstmt.setInt(2, book_idx);
			
			int check = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Closer.close(pstmt);
		}
	}

	public int bRental(Connection conn, int member_idx, int book_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO rental_list (rt_m_idx, rt_b_idx) VALUES (?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			pstmt.setInt(2, book_idx);
			
			int check = pstmt.executeUpdate();
			
			if(check == 0) {
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
	
	public int bReserve(Connection conn, int member_idx, int book_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO reservation_list (rv_m_idx, rv_b_idx) VALUES (?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			pstmt.setInt(2, book_idx);
			
			int check = pstmt.executeUpdate();
			
			if(check == 0) {
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
	
	public ArrayList<RtListDTO> getRentalList(Connection conn, int session_idx) {
		
		ArrayList<RtListDTO> dtos = new ArrayList<>();
		RtListDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM rental_list_view WHERE rt_m_idx = ? ORDER BY b_title ASC";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString("b_title");
				Timestamp rentalDate = rs.getTimestamp("rt_rentalDate");
				
				dto = new RtListDTO(title, rentalDate);
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
	
	public ArrayList<RvListDTO> getReservationList(Connection conn, int session_idx) {
		
		ArrayList<RvListDTO> dtos = new ArrayList<>();
		RvListDTO dto = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM reservation_list_view WHERE rv_m_idx = ? ORDER BY b_title ASC";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString("b_title");
				Timestamp reserveDate = rs.getTimestamp("rv_reserveDate");
				
				dto = new RvListDTO(title, reserveDate);
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
	
	public int bReturn(Connection conn, int session_idx, int b_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE rental_list SET rt_returnDate = now() WHERE rt_m_idx = ? AND rt_b_idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_idx);
			pstmt.setInt(2, b_idx);
			
			int check = pstmt.executeUpdate();
			
			if(check == 0) {
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
	
	public int bReservationCancel(Connection conn, int session_idx, int b_idx) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE reservation_list SET rv_cancelDate = now() WHERE rv_m_idx = ? AND rv_b_idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_idx);
			pstmt.setInt(2, b_idx);
			
			int check = pstmt.executeUpdate();
			
			if(check == 0) {
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
	
	public int checkBookExist(Connection conn, String title) {
		
		int re = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM book WHERE b_title = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			
			rs = pstmt.executeQuery();
			
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

	public int mChangePw(Connection conn, int session_idx, String afterPw) {

		int re = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE member SET m_pw = ? WHERE m_idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, afterPw);
			pstmt.setInt(2, session_idx);
			
			int check = pstmt.executeUpdate();
			
			if(check == 0) {
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
}
