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

public class BookDeleteDialog extends JDialog {
	
	private boolean value = false;
	
	int b_idx;

	JLabel titleLabel, authorLabel, publisherLabel, searchLabel, stateLabel;
	JTextField titleField, authorField, publisherField, searchField;
	ImagePanel imagePanel;
	JButton deleteBtn, searchBtn;

	public BookDeleteDialog(Home frame, String title) {
		super(frame, title, true);
		
		add(searchLabel = new JLabel("�˻�"));
		searchLabel.setBounds(10, 0, 40, 30);
		add(searchField = new JTextField());
		searchField.setBounds(55, 0, 200, 30);
		add(searchBtn = new JButton("�˻�"));
		searchBtn.setBounds(260, 0, 60, 30);

		add(titleLabel = new JLabel("����"));
		titleLabel.setBounds(10, 35, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(55, 35, 120, 30);
		titleField.setEditable(false);
		
		add(authorLabel = new JLabel("����"));
		authorLabel.setBounds(10, 65, 40, 30);
		add(authorField = new JTextField());
		authorField.setBounds(55, 65, 120, 30);
		authorField.setEditable(false);
		
		add(publisherLabel = new JLabel("���ǻ�"));
		publisherLabel.setBounds(10, 95, 40, 30);
		add(publisherField = new JTextField());
		publisherField.setBounds(55, 95, 120, 30);
		publisherField.setEditable(false);
		
		add(stateLabel = new JLabel("���� ����", JLabel.CENTER));
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		stateLabel.setBounds(55, 195, 115, 30);
		stateLabel.setFont(new Font("GOTHIC", Font.BOLD, 15));
		
		add(imagePanel = new ImagePanel());
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		imagePanel.setBounds(180, 35, 140, 190);
		
		add(deleteBtn = new JButton("����"));
		deleteBtn.setBounds(260, 230, 60, 30);
		
		
		
		
		getContentPane().setLayout(new BorderLayout());

		setSize(340, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
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
					
					setB_idx(dto.getIdx());
					titleField.setText(dto.getTitle());
					authorField.setText(dto.getAuthor());
					publisherField.setText(dto.getPublisher());
					
					int checkReservation = dao.checkBookReservation(conn, b_idx);
					int checkRental = dao.checkBookRental(conn, b_idx);
					
					if(checkReservation == 1 & checkRental == 0) {
						stateLabel.setText("������");
						stateLabel.setForeground(Color.BLUE);
						deleteBtn.setEnabled(false);
					} else if(checkReservation == 0 & checkRental == 1) {
						stateLabel.setText("�뿩��");
						stateLabel.setForeground(Color.RED);
						deleteBtn.setEnabled(false);
					} else if(checkReservation == 0 & checkRental == 0) {
						stateLabel.setText("�뿩����");
						stateLabel.setForeground(Color.BLACK);
						deleteBtn.setEnabled(true);
					}
					
					String bookImgName = dto.getImgName();
					if(bookImgName.trim().length() == 0) {
						imagePanel.setNoImage();
					} else {
						imagePanel.setSavedImage(bookImgName);
					} 
				}
				DB_Closer.close(conn);
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getTitleField().length() == 0) {
					JOptionPane.showMessageDialog(null, "å ������ �Է����ּ���", "å ����", JOptionPane.WARNING_MESSAGE);
					titleField.requestFocus();
					return;
				}
				if(getAuthorField().length() == 0) {
					JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���", "å ����", JOptionPane.WARNING_MESSAGE);
					authorField.requestFocus();
					return;
				}
				if(getPublisherField().length() == 0) {
					JOptionPane.showMessageDialog(null, "���ǻ縦 �Է����ּ���", "å ����", JOptionPane.WARNING_MESSAGE);
					publisherField.requestFocus();
					return;
				}
				int check = JOptionPane.showConfirmDialog(null, "���� �ش� å�� DB���� �����Ͻðڽ��ϱ�?\n������ �Ұ����մϴ�.", "å ����", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				value = true;
				setVisible(false);
			}
		});
	}

	public boolean check() {
		return this.value;
	}
	

	public int getB_idx() {
		return b_idx;
	}

	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}

	public String getTitleField() {
		return titleField.getText().trim();
	}

	public void setTitleField(JTextField titleField) {
		this.titleField = titleField;
	}

	public String getAuthorField() {
		return authorField.getText().trim();
	}

	public void setAuthorField(JTextField authorField) {
		this.authorField = authorField;
	}

	public String getPublisherField() {
		return publisherField.getText().trim();
	}

	public void setPublisherField(JTextField publisherField) {
		this.publisherField = publisherField;
	}
}
