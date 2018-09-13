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

public class ReadReceiveLetterDialog extends JDialog{
	
	LetterDialog owner;
	private int l_idx;
	
	JLabel senderLabel, receiveDateLabel, titleLabel, contentsLabel;
	JTextField senderField, receiveDateField, titleField;
	JTextArea contentsField;
	JScrollPane contentsPanel;
	JButton replyBtn, relayBtn, DeleteBtn;
	
	
	public ReadReceiveLetterDialog(LetterDialog owner, String title) {
		super(owner, title, true);
		this.owner = owner;
		
		this.setLayout(null);
		
		add(senderLabel = new JLabel("������"));
		senderLabel.setBounds(10, 0, 50, 30);
		add(senderField = new JTextField());
		senderField.setBounds(70, 0, 340, 30);
		
		add(receiveDateLabel = new JLabel("������¥"));
		receiveDateLabel.setBounds(10, 30, 50, 30);
		add(receiveDateField = new JTextField());
		receiveDateField.setBounds(70, 30, 340, 30);
		
		add(titleLabel = new JLabel("����"));
		titleLabel.setBounds(10, 60, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(70, 60, 340, 30);
		
		add(contentsLabel = new JLabel("����"));
		contentsLabel.setBounds(10, 90, 40, 30);
		contentsField = new JTextArea();
		add(contentsPanel = new JScrollPane(contentsField));
		contentsPanel.setBounds(70, 90, 340, 170);

		add(replyBtn = new JButton("����"));
		replyBtn.setBounds(220, 270, 60, 30);
		add(relayBtn = new JButton("����"));
		relayBtn.setBounds(285, 270, 60, 30);
		add(DeleteBtn = new JButton("����"));
		DeleteBtn.setBounds(350, 270, 60, 30);
		
		senderField.setEditable(false);
		receiveDateField.setEditable(false);
		titleField.setEditable(false);
		contentsField.setEditable(false);
		
		setSize(430, 340);
//		setResizable(false);
		setLocationRelativeTo(null);
		
		generateEvent();
	}


	private void generateEvent() {
		
		replyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LetterSendDialog letterSendDialog = new LetterSendDialog(owner, "����");
				letterSendDialog.receiverField.setText(getSenderField());
				letterSendDialog.receiverField.setEditable(false);
				letterSendDialog.findMemBtn.setEnabled(false);
				setVisible(false);
				letterSendDialog.setVisible(true);
				
				if(!letterSendDialog.check()) return;
				
				String receiverNickname = letterSendDialog.getReceiverField();
				String title = letterSendDialog.getTitleField();
				String contents = letterSendDialog.getContentsField();
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int receiverIdx = dao.getMemberIdx_FromNickname(conn, receiverNickname);
				
				if(receiverIdx == 0) {
					JOptionPane.showMessageDialog(null, "���� ����� ��Ȯ�� �������ּ���.", "���� ������", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int re = dao.lSend(conn, title, contents, Home.getSession_idx(), receiverIdx);
				if(re == 0) {
					JOptionPane.showMessageDialog(null, "���� ������ ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "���� ������", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "���� ������ ����", "���� ������", JOptionPane.INFORMATION_MESSAGE);
				}
				DB_Closer.close(conn);
				
				owner.refreshLetterList();
			}
		});
		
		relayBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LetterSendDialog letterSendDialog = new LetterSendDialog(owner, "����");

				letterSendDialog.setTitleField(getTitleField());
				letterSendDialog.setContentsField(getContentsField());
				setVisible(false);
				letterSendDialog.setVisible(true);
				
				if(!letterSendDialog.check()) return;
				
				String receiverNickname = letterSendDialog.getReceiverField();
				String title = letterSendDialog.getTitleField();
				String contents = letterSendDialog.getContentsField();
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int receiverIdx = dao.getMemberIdx_FromNickname(conn, receiverNickname);
				
				if(receiverIdx == 0) {
					JOptionPane.showMessageDialog(null, "���� ����� ��Ȯ�� �������ּ���.", "���� ������", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int re = dao.lSend(conn, title, contents, Home.getSession_idx(), receiverIdx);
				if(re == 0) {
					JOptionPane.showMessageDialog(null, "���� ������ ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "���� ������", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "���� ������ ����", "���� ������", JOptionPane.INFORMATION_MESSAGE);
				}
				DB_Closer.close(conn);
				
				owner.refreshLetterList();
			}
		});

		DeleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int re = dao.lDeleteReceived(conn, getL_idx());
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "���� ���� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "���� ���� �Ϸ�", "����", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					owner.refreshLetterList();
				}
			}
		});
	}


	public String getSenderField() {
		return senderField.getText().trim();
	}


	public void setSenderField(String string) {
		this.senderField.setText(string);
	}


	public JTextField getReceiveDateField() {
		return receiveDateField;
	}


	public void setReceiveDateField(String string) {
		this.receiveDateField.setText(string);
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
