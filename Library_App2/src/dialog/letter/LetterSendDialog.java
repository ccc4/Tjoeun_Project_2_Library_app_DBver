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
		
		add(receiverLabel = new JLabel("�޴��� �г���"));
		receiverLabel.setBounds(10, 0, 100, 30);
		add(receiverField = new JTextField());
		receiverField.setBounds(100, 0, 245, 30);
		add(findMemBtn = new JButton("�˻�"));
		findMemBtn.setBounds(350, 0, 60, 30);
		
		add(titleLabel = new JLabel("����"));
		titleLabel.setBounds(10, 40, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(100, 40, 310, 30);
		
		add(contentsLabel = new JLabel("����"));
		contentsLabel.setBounds(10, 70, 40, 30);
		contentsField = new JTextArea();
		add(contentsPanel = new JScrollPane(contentsField));
		contentsPanel.setBounds(100, 70, 310, 190);

		add(sendBtn = new JButton("����"));
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
				LetterFindNicknameDialog letterFindNicknameDialog = new LetterFindNicknameDialog(owner, "�г��� ã��", 2); // 2�� ���̾˷α׷� ���� �ø� üũ�ϱ� ����
				letterFindNicknameDialog.setVisible(true);
				
				if(!letterFindNicknameDialog.check()) return;
			}
		});
		
		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(getReceiverField().length() == 0) {
					JOptionPane.showMessageDialog(null, "���� ȸ���� �г����� �Է��ؾ��մϴ�.", "���� ������", JOptionPane.WARNING_MESSAGE);
					receiverField.requestFocus();
					return;
				}
				if(getTitleField().length() == 0) {
					JOptionPane.showMessageDialog(null, "������ �Է��ؾ��մϴ�.", "���� ������", JOptionPane.WARNING_MESSAGE);
					titleField.requestFocus();
					return;
				}
				if(getContentsField().length() == 0) {
					JOptionPane.showMessageDialog(null, "������ �Է��ؾ��մϴ�.", "���� ������", JOptionPane.WARNING_MESSAGE);
					contentsField.requestFocus();
					return;
				}
				
				int check = JOptionPane.showConfirmDialog(null, "������ �����ðڽ��ϱ�?", "���� ������", JOptionPane.YES_NO_OPTION);
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
