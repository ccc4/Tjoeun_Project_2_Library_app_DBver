//package dialog.letter;
//
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//import frame.Home;
//
//public class LetterSendDialog extends JDialog {
//	
////	private int value = 0; // 1: ����, 2: ����, 3: ����
//	private boolean value = false;
//	
//	Home frame;
//	
//	JLabel receiverLabel;
//	JTextField receiverField, titleField;
//	JTextArea contentsField;
//	JScrollPane contentsPanel;
////	JButton findMemBtn, replyBtn, relayBtn, DeleteBtn;
//	JButton findMemBtn, sendBtn;
//	
//	public LetterSendDialog(Home frame, String title) {
//		super(frame, title, true);
//		this.frame = frame;
//		
//		getContentPane().setLayout(null);
//		
//		add(receiverLabel = new JLabel("�޴��� : "));
////		receiverLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		receiverLabel.setBounds(10, 0, 50, 30);
//		add(receiverField = new JTextField());
//		receiverField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		receiverField.setBounds(60, 0, 120, 30);
//		add(findMemBtn = new JButton("�˻�"));
//		findMemBtn.setBounds(180, 0, 60, 30);
//		
//		
//		add(titleField = new JTextField());
//		titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		titleField.setBounds(10, 30, 230, 30);
//		
//		contentsField = new JTextArea();
//		add(contentsPanel = new JScrollPane(contentsField));
//		contentsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		contentsPanel.setBounds(10, 60, 230, 100);
//		
////		add(replyBtn = new JButton("����"));
////		replyBtn.setBounds(50, 165, 60, 30);
////		add(relayBtn = new JButton("����"));
////		relayBtn.setBounds(115, 165, 60, 30);
////		add(DeleteBtn = new JButton("����"));
////		DeleteBtn.setBounds(180, 165, 60, 30);
//		add(sendBtn = new JButton("����"));
//		sendBtn.setBounds(180, 165, 60, 30);
//		
//		setSize(250, 235);
////		setResizable(false);
//		setLocationRelativeTo(null);
//		
//		generateEvent();
//	}
//	
//	private void generateEvent() {
//		
//		findMemBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null, "�غ����Դϴ�.", "���� ������", JOptionPane.INFORMATION_MESSAGE);
//			}
//		});
//		
//		sendBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(getReceiverField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "���� ȸ���� �г����� �Է��ؾ��մϴ�.", "���� ������", JOptionPane.WARNING_MESSAGE);
//					receiverField.requestFocus();
//					return;
//				}
//				if(getTitleField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "������ �Է��ؾ��մϴ�.", "���� ������", JOptionPane.WARNING_MESSAGE);
//					titleField.requestFocus();
//					return;
//				}
//				if(getContentsField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "������ �Է��ؾ��մϴ�.", "���� ������", JOptionPane.WARNING_MESSAGE);
//					contentsField.requestFocus();
//					return;
//				}
//				
//				int check = JOptionPane.showConfirmDialog(null, "������ �����ðڽ��ϱ�?", "���� ������", JOptionPane.YES_NO_OPTION);
//				if(check != JOptionPane.YES_OPTION) return;
//				
//				
//				
//				value = true;
//				setVisible(false);
//			}
//		});
//	}
//
//	public boolean check() {
//		return this.value;
//	}
//
//	public String getReceiverField() {
//		return receiverField.getText().trim();
//	}
//
//	public void setReceiverField(JTextField receiverField) {
//		this.receiverField = receiverField;
//	}
//
//	public String getTitleField() {
//		return titleField.getText().trim();
//	}
//
//	public void setTitleField(JTextField titleField) {
//		this.titleField = titleField;
//	}
//
//	public String getContentsField() {
//		return contentsField.getText().trim();
//	}
//
//	public void setContentsField(JTextArea contentsField) {
//		this.contentsField = contentsField;
//	}
//	
//}
