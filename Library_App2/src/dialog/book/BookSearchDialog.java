package dialog.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import dto.BookDTO;
import frame.Home;
import frame.ImagePanel;

public class BookSearchDialog extends JDialog {
	
	private int value = 0;
	
	JLabel searchLabel, titleLabel, authorLabel, publisherLabel, stateLabel;
	JTextField searchField, titleField, authorField, publisherField;
	
	ImagePanel imagePanel;
	JButton searchBtn, rentalBtn, reserveBtn;

	public BookSearchDialog(Home frame, String title) {
		super(frame, title, true);

		add(searchLabel = new JLabel("검색"));
		searchLabel.setBounds(10, 0, 40, 30);
		add(searchField = new JTextField());
		searchField.setBounds(55, 0, 200, 30);
		add(searchBtn = new JButton("검색"));
		searchBtn.setBounds(260, 0, 60, 30);
		
		add(titleLabel = new JLabel("제목"));
		titleLabel.setBounds(10, 35, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(55, 35, 120, 30);
		titleField.setEditable(false);
		
		add(authorLabel = new JLabel("저자"));
		authorLabel.setBounds(10, 65, 40, 30);
		add(authorField = new JTextField());
		authorField.setBounds(55, 65, 120, 30);
		authorField.setEditable(false);
		
		add(publisherLabel = new JLabel("출판사"));
		publisherLabel.setBounds(10, 95, 40, 30);
		add(publisherField = new JTextField());
		publisherField.setBounds(55, 95, 120, 30);
		publisherField.setEditable(false);
		
		add(stateLabel = new JLabel("현재 상태", JLabel.CENTER));
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		stateLabel.setBounds(55, 195, 115, 30);
		stateLabel.setFont(new Font("GOTHIC", Font.BOLD, 15));
		
		add(imagePanel = new ImagePanel());
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		imagePanel.setBounds(180, 35, 140, 190);
		
		add(rentalBtn = new JButton("대여"));
		rentalBtn.setBounds(195, 230, 60, 30);
		add(reserveBtn = new JButton("예약"));
		reserveBtn.setBounds(260, 230, 60, 30);
		
		
		
		
		getContentPane().setLayout(new BorderLayout());


		setSize(340, 300);
		setLocationRelativeTo(null);
		setResizable(false);

		generateEvents();
	}
		
	private void generateEvents() {
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				String selectedTitle = getSearchField();
				
				if(selectedTitle.length() == 0) {
					JOptionPane.showMessageDialog(null, "책 제목을 입력해야합니다.", "책 검색", JOptionPane.WARNING_MESSAGE);
					searchField.requestFocus();
					return;
				}
				
				int re = dao.checkBookExist(conn, selectedTitle);
				if(re == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 책 제목입니다.", "책 검색", JOptionPane.WARNING_MESSAGE);
				} else {
					int b_idx = dao.getBookIdx_FromTitle(conn, selectedTitle);
					BookDTO dto = dao.getBookInfo(conn, b_idx);
					
					titleField.setText(dto.getTitle());
					authorField.setText(dto.getAuthor());
					publisherField.setText(dto.getPublisher());
					
					int checkReservation = dao.checkBookReservation(conn, b_idx);
					int checkReservationMine = dao.checkBookReservationMine(conn, Home.getSession_idx(), b_idx);
//					이걸로 여기서도 예약된 내 첵을 대여할 수 있게 만들기
					int checkRental = dao.checkBookRental(conn, b_idx);
					
					if(checkReservation == 1 & checkRental == 0) {
						if(checkReservationMine == 1) {
							stateLabel.setText("예약중");
							stateLabel.setForeground(Color.BLUE);
							rentalBtn.setEnabled(true);
							reserveBtn.setEnabled(false);
						} else {
							stateLabel.setText("예약중");
							stateLabel.setForeground(Color.BLUE);
							rentalBtn.setEnabled(false);
							reserveBtn.setEnabled(false);
						}
					} else if(checkReservation == 0 & checkRental == 1) {
						stateLabel.setText("대여중");
						stateLabel.setForeground(Color.RED);
						rentalBtn.setEnabled(false);
						reserveBtn.setEnabled(false);
					} else if(checkReservation == 0 & checkRental == 0) {
						stateLabel.setText("대여가능");
						stateLabel.setForeground(Color.BLACK);
						rentalBtn.setEnabled(true);
						reserveBtn.setEnabled(true);
					}
					
					String bookImgName = dto.getImgName();
					if(bookImgName.trim().length() == 0 || bookImgName.equals("")) {
						imagePanel.setNoImage();
					} else {
						imagePanel.setSavedImage(bookImgName);
					} 
				}
				DB_Closer.close(conn);
			}
		});
		
		rentalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				value = 1;
				setVisible(false);
			}
		});
		
		reserveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				value = 2;
				setVisible(false);
			}
		});
	}

	public int check() {
		return this.value;
	}

	public String getTitleField() {
		return titleField.getText().trim();
	}

	public void setTitleField(JTextField titleField) {
		this.titleField = titleField;
	}

	public String getSearchField() {
		return searchField.getText().trim();
	}

	public void setSearchField(String str) {
		this.searchField.setText(str);
	}

	public JButton getSearchBtn() {
		return searchBtn;
	}

	public String getAuthorField() {
		return authorField.getText().trim();
	}

	public void setAuthorField(JTextField authorField) {
		this.authorField = authorField;
	}
	
	
}
