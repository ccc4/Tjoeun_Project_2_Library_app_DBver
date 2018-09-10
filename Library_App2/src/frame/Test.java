package frame;

import java.sql.Connection;
import java.util.ArrayList;

import db.util.GenerateConnection;
import dto.BookDTO;
import dao.DAO;

public class Test {
	public static void main(String[] args) {
		DAO dao = DAO.getInstance();
		Connection conn = GenerateConnection.getConnection();
		
		
		ArrayList<BookDTO> books = dao.bookList(conn);
		
//		for(int i=0;i<books.size();i++) {
//			System.out.println(books.get(i).getIdx());
//		}
		
//		int re = dao.checkBookRental(conn, 3);
//		System.out.println(re);
		int re = dao.checkBookReservation(conn, 3);
		System.out.println(re);
		
	}
}
