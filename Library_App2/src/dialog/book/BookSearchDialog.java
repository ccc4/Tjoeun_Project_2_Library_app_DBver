package dialog.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

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

	private DropTarget dt;

	public BookSearchDialog(Home frame, String title) {
		super(frame, title, true);
		this.frame = frame;

		add(searchLabel = new JLabel("�˻�"));
		searchLabel.setBounds(10, 10, 40, 30);
		add(searchField = new JTextField());
		searchField.setBounds(55, 10, 200, 30);
		add(searchBtn = new JButton("�˻�"));
		searchBtn.setBounds(260, 10, 60, 30);
		
		add(titleLabel = new JLabel("����"));
		titleLabel.setBounds(10, 45, 40, 30);
		add(titleField = new JTextField());
		titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		titleField.setBounds(55, 45, 120, 30);
		titleField.setEditable(false);
		
		add(authorLabel = new JLabel("����"));
		authorLabel.setBounds(10, 80, 40, 30);
		add(authorField = new JTextField());
		authorField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		authorField.setBounds(55, 80, 120, 30);
		authorField.setEditable(false);
		
		add(publisherLabel = new JLabel("���ǻ�"));
		publisherLabel.setBounds(10, 115, 40, 30);
		add(publisherField = new JTextField());
		publisherField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		publisherField.setBounds(55, 115, 120, 30);
		publisherField.setEditable(false);
		
		add(stateLabel = new JLabel("���� ����", JLabel.CENTER));
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		stateLabel.setBounds(55, 205, 115, 30);
		stateLabel.setFont(new Font("GOTHIC", Font.BOLD, 15));
		
		add(imagePanel = new ImagePanel());
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		imagePanel.setBounds(180, 45, 140, 190);
		
		add(reserveBtn = new JButton("����"));
		reserveBtn.setBounds(195, 240, 60, 30);
		add(rentalBtn = new JButton("�뿩"));
		rentalBtn.setBounds(260, 240, 60, 30);
		
		
		
		
		getContentPane().setLayout(new BorderLayout());


		setSize(340, 310);
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
					
					titleField.setText("  " + dto.getTitle());
					authorField.setText("  " + dto.getAuthor());
					publisherField.setText("  " + dto.getPublisher());
					
					int checkReservation = dao.checkBookReservation(conn, b_idx);
//					int checkReservationMine = dao.checkBookReservationMine(conn, frame.getSession_idx(), b_idx);
//					�̰ɷ� ���⼭�� ����� �� ý�� �뿩�� �� �ְ� �����
					int checkRental = dao.checkBookRental(conn, b_idx);
					
					if(checkReservation == 1 & checkRental == 0) {
//						if
						stateLabel.setText("������");
						stateLabel.setForeground(Color.BLUE);
						rentalBtn.setEnabled(false);
						reserveBtn.setEnabled(false);
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
//		
//		this.addBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(getTitleField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "å ������ �Է����ּ���", "å ���", JOptionPane.WARNING_MESSAGE);
//					titleField.requestFocus();
//					return;
//				}
//				if(getAuthorField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���", "å ���", JOptionPane.WARNING_MESSAGE);
//					authorField.requestFocus();
//					return;
//				}
//				if(getPublisherField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "���ǻ縦 �Է����ּ���", "å ���", JOptionPane.WARNING_MESSAGE);
//					publisherField.requestFocus();
//					return;
//				}
//				if(targetImgFilePath.length() == 0) {
//					int re = JOptionPane.showConfirmDialog(null, "�̹����� �������� �ʾҽ��ϴ�.\n�״�� �����Ͻðڽ��ϱ�?", "å ���", JOptionPane.YES_NO_OPTION);
//					if(re != JOptionPane.YES_OPTION) return;
//				}
//				
//				value = true;
//				setVisible(false);
//			}
//		});


	public int check() {
		return this.value;
	}

	public JTextField getTitleField() {
		return titleField;
	}

	public void setTitleField(JTextField titleField) {
		this.titleField = titleField;
	}

}
