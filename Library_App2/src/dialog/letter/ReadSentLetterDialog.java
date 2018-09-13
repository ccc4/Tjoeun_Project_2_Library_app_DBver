package dialog.letter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import frame.Home;

public class ReadSentLetterDialog extends JDialog{
	
	LetterDialog owner;
	private int l_idx;
	
	JLabel receiverLabel, sendDateLabel, titleLabel, contentsLabel;
	JTextField receiverField, sendDateField, titleField;
	JTextArea contentsField;
	JScrollPane contentsPanel;
	JButton DeleteBtn;
	
	
	public ReadSentLetterDialog(LetterDialog owner, String title) {
		super(owner, title, true);
		this.owner = owner;
		
		this.setLayout(null);
		
		add(receiverLabel = new JLabel("받는이"));
		receiverLabel.setBounds(10, 0, 50, 30);
		add(receiverField = new JTextField());
		receiverField.setBounds(70, 0, 340, 30);
		
		add(sendDateLabel = new JLabel("보낸날짜"));
		sendDateLabel.setBounds(10, 30, 50, 30);
		add(sendDateField = new JTextField());
		sendDateField.setBounds(70, 30, 340, 30);
		
		add(titleLabel = new JLabel("제목"));
		titleLabel.setBounds(10, 60, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(70, 60, 340, 30);
		
		add(contentsLabel = new JLabel("내용"));
		contentsLabel.setBounds(10, 90, 40, 30);
		contentsField = new JTextArea();
		add(contentsPanel = new JScrollPane(contentsField));
		contentsPanel.setBounds(70, 90, 340, 170);

		add(DeleteBtn = new JButton("삭제"));
		DeleteBtn.setBounds(350, 270, 60, 30);
		
		receiverField.setEditable(false);
		sendDateField.setEditable(false);
		titleField.setEditable(false);
		contentsField.setEditable(false);
		
		setSize(430, 340);
//		setResizable(false);
		setLocationRelativeTo(null);
		
		generateEvent();
	}


	private void generateEvent() {
		

		DeleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "편지를 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int re = dao.lDeleteSent(conn, getL_idx());
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "편지 삭제 실패\n계속 반복될 경우 관리자에게 문의하세요.", "삭제", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "편지 삭제 완료", "삭제", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					owner.refreshLetterList();
				}
			}
		});
	}


	public String getReceiverField() {
		return receiverField.getText().trim();
	}


	public void setReceiverField(String string) {
		this.receiverField.setText(string);
	}


	public JTextField getSendDateField() {
		return sendDateField;
	}


	public void setSendDateField(String string) {
		this.sendDateField.setText(string);
	}


	public String getTitleField() {
		return titleField.getText().trim();
	}


	public void setTitleField(String string) {
		this.titleField.setText(string);
	}


	public String getContentsField() {
		return contentsField.getText().trim();
	}


	public void setContentsField(String string) {
		this.contentsField.setText(string);
	}


	public int getL_idx() {
		return l_idx;
	}


	public void setL_idx(int l_idx) {
		this.l_idx = l_idx;
	}
	
}
