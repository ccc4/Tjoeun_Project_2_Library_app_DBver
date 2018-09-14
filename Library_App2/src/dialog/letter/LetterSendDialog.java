package dialog.letter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import frame.Home;

public class LetterSendDialog extends JDialog{
	
	LetterSendDialog owner = this;
	private boolean value = false;
	
	JLabel receiverLabel, titleLabel, contentsLabel;
	JTextField receiverField, titleField;
	JTextArea contentsField;
	JScrollPane contentsPanel;
	JButton findMemBtn, sendBtn;
	
	
	public LetterSendDialog(LetterDialog owner, String title) {
		super(owner, title, true);
		
		this.setLayout(null);
		
		add(receiverLabel = new JLabel("받는이 닉네임"));
		receiverLabel.setBounds(10, 0, 100, 30);
		add(receiverField = new JTextField());
		receiverField.setBounds(100, 0, 245, 30);
		add(findMemBtn = new JButton("검색"));
		findMemBtn.setBounds(350, 0, 60, 30);
		
		add(titleLabel = new JLabel("제목"));
		titleLabel.setBounds(10, 40, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(100, 40, 310, 30);
		
		add(contentsLabel = new JLabel("내용"));
		contentsLabel.setBounds(10, 70, 40, 30);
		contentsField = new JTextArea();
		add(contentsPanel = new JScrollPane(contentsField));
		contentsPanel.setBounds(100, 70, 310, 190);

		add(sendBtn = new JButton("전송"));
		sendBtn.setBounds(350, 270, 60, 30);
		
		setSize(430, 340);
//		setResizable(false);
		setLocationRelativeTo(null);
		
		generateEvent();
	}


	private void generateEvent() {
		
		findMemBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LetterFindNicknameDialog letterFindNicknameDialog = new LetterFindNicknameDialog(owner, "닉네임 찾기", 2); // 2는 다이알로그로 열릴 시를 체크하기 위해
				letterFindNicknameDialog.setVisible(true);
				
				if(!letterFindNicknameDialog.check()) return;
			}
		});
		
		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(getReceiverField().length() == 0) {
					JOptionPane.showMessageDialog(null, "받을 회원의 닉네임을 입력해야합니다.", "편지 보내기", JOptionPane.WARNING_MESSAGE);
					receiverField.requestFocus();
					return;
				}
				if(getTitleField().length() == 0) {
					JOptionPane.showMessageDialog(null, "제목을 입력해야합니다.", "편지 보내기", JOptionPane.WARNING_MESSAGE);
					titleField.requestFocus();
					return;
				}
				if(getContentsField().length() == 0) {
					JOptionPane.showMessageDialog(null, "내용을 입력해야합니다.", "편지 보내기", JOptionPane.WARNING_MESSAGE);
					contentsField.requestFocus();
					return;
				}
				
				int check = JOptionPane.showConfirmDialog(null, "편지를 보내시겠습니까?", "편지 보내기", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				value = true;
				setVisible(false);
			}
		});
	}

	public boolean check() {
		return this.value;
	}

	public String getReceiverField() {
		return receiverField.getText().trim();
	}


	public void setReceiverField(String str) {
		this.receiverField.setText(str);
	}


	public String getTitleField() {
		return titleField.getText().trim();
	}


	public void setTitleField(String str) {
		this.titleField.setText(str);
	}


	public String getContentsField() {
		return contentsField.getText().trim();
	}


	public void setContentsField(String str) {
		this.contentsField.setText(str);
	}

}
