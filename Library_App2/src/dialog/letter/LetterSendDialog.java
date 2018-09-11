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
////	private int value = 0; // 1: 답장, 2: 전달, 3: 삭제
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
//		add(receiverLabel = new JLabel("받는이 : "));
////		receiverLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		receiverLabel.setBounds(10, 0, 50, 30);
//		add(receiverField = new JTextField());
//		receiverField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//		receiverField.setBounds(60, 0, 120, 30);
//		add(findMemBtn = new JButton("검색"));
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
////		add(replyBtn = new JButton("답장"));
////		replyBtn.setBounds(50, 165, 60, 30);
////		add(relayBtn = new JButton("전달"));
////		relayBtn.setBounds(115, 165, 60, 30);
////		add(DeleteBtn = new JButton("삭제"));
////		DeleteBtn.setBounds(180, 165, 60, 30);
//		add(sendBtn = new JButton("전송"));
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
//				JOptionPane.showMessageDialog(null, "준비중입니다.", "쪽지 보내기", JOptionPane.INFORMATION_MESSAGE);
//			}
//		});
//		
//		sendBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(getReceiverField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "받을 회원의 닉네임을 입력해야합니다.", "쪽지 보내기", JOptionPane.WARNING_MESSAGE);
//					receiverField.requestFocus();
//					return;
//				}
//				if(getTitleField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "제목을 입력해야합니다.", "쪽지 보내기", JOptionPane.WARNING_MESSAGE);
//					titleField.requestFocus();
//					return;
//				}
//				if(getContentsField().length() == 0) {
//					JOptionPane.showMessageDialog(null, "내용을 입력해야합니다.", "쪽지 보내기", JOptionPane.WARNING_MESSAGE);
//					contentsField.requestFocus();
//					return;
//				}
//				
//				int check = JOptionPane.showConfirmDialog(null, "쪽지를 보내시겠습니까?", "쪽지 보내기", JOptionPane.YES_NO_OPTION);
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
