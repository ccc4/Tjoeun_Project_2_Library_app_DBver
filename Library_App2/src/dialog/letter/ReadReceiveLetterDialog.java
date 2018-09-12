package dialog.letter;

import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReadReceiveLetterDialog extends JDialog{
	
	JLabel senderLabel, sendDateLabel, titleLabel, contentsLabel;
	JTextField senderField, sendDateField, titleField;
	JTextArea contentsField;
	JScrollPane contentsPanel;
	JButton sendBtn;
	
	
	public ReadReceiveLetterDialog(LetterDialog owner, String title) {
		super(owner, title, true);
		
		this.setLayout(null);
		
		add(senderLabel = new JLabel("보낸이"));
		senderLabel.setBounds(10, 0, 50, 30);
		add(senderField = new JTextField());
		senderField.setBounds(70, 0, 340, 30);
		
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

		add(sendBtn = new JButton("답장"));
		sendBtn.setBounds(220, 270, 60, 30);
		add(sendBtn = new JButton("전달"));
		sendBtn.setBounds(285, 270, 60, 30);
		add(sendBtn = new JButton("삭제"));
		sendBtn.setBounds(350, 270, 60, 30);
		
		senderField.setEditable(false);
		sendDateField.setEditable(false);
		titleField.setEditable(false);
		contentsField.setEditable(false);
		
		setSize(430, 340);
//		setResizable(false);
		setLocationRelativeTo(null);
	}


	public JTextField getSenderField() {
		return senderField;
	}


	public void setSenderField(String string) {
		this.senderField.setText(string);
	}


	public JTextField getSendDateField() {
		return sendDateField;
	}


	public void setSendDateField(String string) {
		this.sendDateField.setText(string);
	}


	public JTextField getTitleField() {
		return titleField;
	}


	public void setTitleField(String string) {
		this.titleField.setText(string);
	}


	public JTextArea getContentsField() {
		return contentsField;
	}


	public void setContentsField(String string) {
		this.contentsField.setText(string);
	}
}
