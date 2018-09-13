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
import db.util.GenerateConnection;
import dto.BookDTO;
import frame.Home;
import frame.ImagePanel;

public class BookSearchDialog extends JDialog {
	
	Home frame;
	private int value = 0;
	
	JLabel searchLabel, titleLabel, authorLabel, publisherLabel, stateLabel;
	JTextField searchField, titleField, authorField, publisherField;
	
	ImagePanel imagePanel;
	JButton searchBtn, rentalBtn, reserveBtn;

	public BookSearchDialog(Home frame, String title) {
		super(frame, title, true);
		this.frame = frame;

		add(searchLabel = new JLabel("�˻�"));
		searchLabel.setBounds(10, 0, 40, 30);
		add(searchField = new JTextField());
		searchField.setBounds(55, 0, 200, 30);
		add(searchBtn = new JButton("�˻�"));
		searchBtn.setBounds(260, 0, 60, 30);
		
		add(titleLabel = new JLabel("����"));
		titleLabel.setBounds(10, 35, 40, 30);
		add(titleField = new JTextField());
		titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		titleField.setBounds(55, 35, 120, 30);
		titleField.setEditable(false);
		
		add(authorLabel = new JLabel("����"));
		authorLabel.setBounds(10, 70, 40, 30);
		add(authorField = new JTextField());
		authorField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		authorField.setBounds(55, 70, 120, 30);
		authorField.setEditable(false);
		
		add(publisherLabel = new JLabel("���ǻ�"));
		publisherLabel.setBounds(10, 105, 40, 30);
		add(publisherField = new JTextField());
		publisherField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		publisherField.setBounds(55, 105, 120, 30);
		publisherField.setEditable(false);
		
		add(stateLabel = new JLabel("���� ����", JLabel.CENTER));
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		stateLabel.setBounds(55, 195, 115, 30);
		stateLabel.setFont(new Font("GOTHIC", Font.BOLD, 15));
		
		add(imagePanel = new ImagePanel());
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		imagePanel.setBounds(180, 35, 140, 190);
		
		add(rentalBtn = new JButton("�뿩"));
		rentalBtn.setBounds(195, 230, 60, 30);
		add(reserveBtn = new JButton("����"));
		reserveBtn.setBounds(260, 230, 60, 30);
		
		
		
		
		getContentPane().setLayout(new BorderLayout());


		setSize(340, 295);
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
				
				String selectedTitle = searchField.getText().trim();
				
				if(selectedTitle.length() == 0) {
					JOptionPane.showMessageDialog(null, "å ������ �Է��ؾ��մϴ�.", "å �˻�", JOptionPane.WARNING_MESSAGE);
					searchField.requestFocus();
					return;
				}
				
				int re = dao.checkBookExist(conn, selectedTitle);
				if(re == 0) {
					JOptionPane.showMessageDialog(null, "�������� �ʴ� å �����Դϴ�.", "å �˻�", JOptionPane.WARNING_MESSAGE);
				} else {
					int b_idx = dao.getBookIdx_FromTitle(conn, selectedTitle);
					BookDTO dto = dao.getBookInfo(conn, b_idx);
					
					titleField.setText("  " + dto.getTitle());
					authorField.setText("  " + dto.getAuthor());
					publisherField.setText("  " + dto.getPublisher());
					
					int checkReservation = dao.checkBookReservation(conn, b_idx);
					int checkReservationMine = dao.checkBookReservationMine(conn, frame.getSession_idx(), b_idx);
//					�̰ɷ� ���⼭�� ����� �� ý�� �뿩�� �� �ְ� �����
					int checkRental = dao.checkBookRental(conn, b_idx);
					
					if(checkReservation == 1 & checkRental == 0) {
						if(checkReservationMine == 1) {
							stateLabel.setText("������");
							stateLabel.setForeground(Color.BLUE);
							rentalBtn.setEnabled(true);
							reserveBtn.setEnabled(false);
						} else {
							stateLabel.setText("������");
							stateLabel.setForeground(Color.BLUE);
							rentalBtn.setEnabled(false);
							reserveBtn.setEnabled(false);
						}
					} else if(checkReservation == 0 & checkRental == 1) {
						stateLabel.setText("�뿩��");
						stateLabel.setForeground(Color.RED);
						rentalBtn.setEnabled(false);
						reserveBtn.setEnabled(false);
					} else if(checkReservation == 0 & checkRental == 0) {
						stateLabel.setText("�뿩����");
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

	public JTextField getTitleField() {
		return titleField;
	}

	public void setTitleField(JTextField titleField) {
		this.titleField = titleField;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public void setSearchField(String str) {
		this.searchField.setText(str);
	}

	public JButton getSearchBtn() {
		return searchBtn;
	}
	
}
