package dialog.letter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import dto.LetterDTO;
import frame.Home;

public class LetterDialog extends JDialog {

	private LetterDialog owner = this;
	
	ArrayList<LetterDTO> receiveLetters = new ArrayList<>();
	ArrayList<LetterDTO> sentLetters = new ArrayList<>();
	
	JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
	ReceivePanel receivePanel;
	SentPanel sentPanel;
	SendPanel sendPanel;
	
	String[] receiveColumns = {"번호", "제목", "보낸이", "받은날짜", "상태"};
	DefaultTableModel receiveModel = new DefaultTableModel(receiveColumns, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JTable receiveTable = new JTable(receiveModel);
	JScrollPane receiveScrollPane = new JScrollPane(receiveTable);
	
	String[] sentColumns = {"번호", "제목", "받는이", "보낸날짜", "상태"};
	DefaultTableModel sentModel = new DefaultTableModel(sentColumns, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JTable sentTable = new JTable(sentModel);
	JScrollPane sentScrollPane = new JScrollPane(sentTable);
	
	public LetterDialog(Home frame, String title) {
		super(frame, title, true);
		
		pane.addTab("받은 편지", receivePanel = new ReceivePanel());
		pane.addTab("보낸 편지", sentPanel = new SentPanel());
		pane.addTab("편지 쓰기", sendPanel = new SendPanel());
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pane, BorderLayout.CENTER);
		
		this.setSize(480, 350);
//		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		refreshLetterList();
	}
	
	class ReceivePanel extends JPanel{
		JButton sendBtn;
		JPanel btnPanel;
		public ReceivePanel() {
			
			receiveScrollPane.setPreferredSize(new Dimension(460, 275));
			add(receiveScrollPane);
			
			receiveTable.addMouseListener(new ReadReceiveLetter());
			receiveTable.getColumn("번호").setPreferredWidth(40);
			receiveTable.getColumn("제목").setPreferredWidth(170);
			receiveTable.getColumn("보낸이").setPreferredWidth(50);
			receiveTable.getColumn("상태").setPreferredWidth(50);
			receiveTable.getTableHeader().setReorderingAllowed(false);
			receiveTable.getTableHeader().setResizingAllowed(false);
		}
	}
	class SentPanel extends JPanel{
		JButton sendBtn;
		JPanel btnPanel;
		public SentPanel() {
			
			sentScrollPane.setPreferredSize(new Dimension(460, 275));
			add(sentScrollPane);
			
			sentTable.addMouseListener(new ReadSentLetter());
			sentTable.getColumn("번호").setPreferredWidth(40);
			sentTable.getColumn("제목").setPreferredWidth(170);
			sentTable.getColumn("받는이").setPreferredWidth(50);
			sentTable.getColumn("상태").setPreferredWidth(50);
			sentTable.getTableHeader().setReorderingAllowed(false);
			sentTable.getTableHeader().setResizingAllowed(false);
		}
	}
	
	public void refreshLetterList() {
		getReceiveLetterList();
		getSentLetterList();
	}
	
	public void getReceiveLetterList() {
		receiveModel.setNumRows(0);
		
		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();
		
		receiveLetters = dao.getMyReceiveLetterList(conn, Home.getSession_idx());
		
		for(int i=0;i<receiveLetters.size();i++) {
			
			String[] letter = new String[5];
			letter[0] = String.valueOf(i+1);
			letter[1] = receiveLetters.get(i).getTitle();
			letter[2] = receiveLetters.get(i).getSenderNickname();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
			letter[3] = dateFormat.format(receiveLetters.get(i).getSendDate());
			
			if(receiveLetters.get(i).getReadDate() == null) {
				letter[4] = "안읽음";
			} else {
				letter[4] = "읽음";
			}
			
			receiveModel.addRow(letter);
		}
		
		DB_Closer.close(conn);
	}
	
	public void getSentLetterList() {
		sentModel.setNumRows(0);
		
		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();
		
		sentLetters = dao.getMySendLetterList(conn, Home.getSession_idx());
		
		for(int i=0;i<sentLetters.size();i++) {
			
			String[] letter = new String[5];
			letter[0] = String.valueOf(i+1);
			letter[1] = sentLetters.get(i).getTitle();
			letter[2] = sentLetters.get(i).getReceiverNickname();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
			letter[3] = dateFormat.format(sentLetters.get(i).getSendDate());
			
			if(sentLetters.get(i).getReadDate() == null) {
				letter[4] = "안읽음";
			} else {
				letter[4] = "읽음";
			}
			
			sentModel.addRow(letter);
		}
		
		DB_Closer.close(conn);
	}
	
	
	class SendPanel extends JPanel{
		
		JLabel receiverLabel, titleLabel, contentsLabel;
		JTextField receiverField, titleField;
		JTextArea contentsField;
		JScrollPane contentsPanel;
		JButton findMemBtn, sendBtn;
		
		public SendPanel() {
			
			this.setLayout(null);
			//480 350
			add(receiverLabel = new JLabel("받는이 닉네임"));
//			receiverLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			receiverLabel.setBounds(10, 0, 100, 30);
			add(receiverField = new JTextField());
			receiverField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			receiverField.setBounds(100, 0, 290, 30);
			add(findMemBtn = new JButton("검색"));
			findMemBtn.setBounds(395, 0, 60, 30);
			
			add(titleLabel = new JLabel("제목"));
//			titleLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			titleLabel.setBounds(10, 40, 40, 30);
			add(titleField = new JTextField());
			titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			titleField.setBounds(100, 40, 355, 30);
			
			add(contentsLabel = new JLabel("내용"));
			contentsLabel.setBounds(10, 70, 40, 30);
			contentsField = new JTextArea();
			add(contentsPanel = new JScrollPane(contentsField));
			contentsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			contentsPanel.setBounds(100, 70, 355, 170);

			add(sendBtn = new JButton("전송"));
			sendBtn.setBounds(395, 250, 60, 30);
			
			generateEvent();
		}
		
		private void generateEvent() {
			
			findMemBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LetterFindNicknameDialog letterFindNicknameDialog = new LetterFindNicknameDialog(owner, "닉네임 찾기", 1); // 1은 편지함에서 바로 보내는 것을 알기 위해
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
					
					String receiverNickname = getReceiverField();
					String title = getTitleField();
					String contents = getContentsField();
					
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int receiverIdx = dao.getMemberIdx_FromNickname(conn, receiverNickname);
					
					if(receiverIdx == 0) {
						JOptionPane.showMessageDialog(null, "보낼 대상을 정확히 기재해주세요.", "편지 보내기", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int re = dao.lSend(conn, title, contents, Home.getSession_idx(), receiverIdx);
					if(re == 0) {
						JOptionPane.showMessageDialog(null, "편지 보내기 실패\n계속 반복될 경우 관리자에게 문의하세요.", "편지 보내기", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "편지 보내기 성공", "편지 보내기", JOptionPane.INFORMATION_MESSAGE);
						receiverField.setText("");
						titleField.setText("");
						contentsField.setText("");
					}
					DB_Closer.close(conn);
					
					refreshLetterList();
				}
			});
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

		public void setTitleField(JTextField titleField) {
			this.titleField = titleField;
		}

		public String getContentsField() {
			return contentsField.getText().trim();
		}

		public void setContentsField(JTextArea contentsField) {
			this.contentsField = contentsField;
		}
		
	}
	
	class ReadReceiveLetter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				ReadReceiveLetterDialog readReceiveLetterDialog = new ReadReceiveLetterDialog(owner, "받은 편지");
				
				int l_idx = Integer.parseInt((String) receiveTable.getModel().getValueAt(receiveTable.getSelectedRow(), 0)) -1;
				
				LetterDTO dto = receiveLetters.get(l_idx);
				
				readReceiveLetterDialog.setL_idx(dto.getL_idx());
				readReceiveLetterDialog.setSenderField(dto.getSenderNickname());
				SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd / HH:mm:ss");
				readReceiveLetterDialog.setReceiveDateField(dateFormat.format(dto.getSendDate()));
				readReceiveLetterDialog.setTitleField(dto.getTitle());
				readReceiveLetterDialog.setContentsField(dto.getContents());
				
				if(dto.getReadDate() == null) {
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int re = dao.lRead(conn, dto.getL_idx());
					if(re == 0) JOptionPane.showMessageDialog(null, "문제발생", "편지 읽기", JOptionPane.WARNING_MESSAGE);
					
					DB_Closer.close(conn);
					
					refreshLetterList();
				}
				readReceiveLetterDialog.setVisible(true);
			}
		}
	}
	
	class ReadSentLetter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				ReadSentLetterDialog readSendLetterDialog = new ReadSentLetterDialog(owner, "보낸 편지");
				
				int l_idx = Integer.parseInt((String) sentTable.getModel().getValueAt(sentTable.getSelectedRow(), 0)) -1;
				
				LetterDTO dto = sentLetters.get(l_idx);
				
				readSendLetterDialog.setL_idx(dto.getL_idx());
				readSendLetterDialog.setReceiverField(dto.getReceiverNickname());
				SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd / hh:mm:ss");
				readSendLetterDialog.setSendDateField(dateFormat.format(dto.getSendDate()));
				readSendLetterDialog.setTitleField(dto.getTitle());
				readSendLetterDialog.setContentsField(dto.getContents());
				
				readSendLetterDialog.setVisible(true);
			}
		}
	}
}
