package dialog.letter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
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
	Home frame;
	
	ArrayList<LetterDTO> receiveLetters = new ArrayList<>();
	ArrayList<LetterDTO> sendLetters = new ArrayList<>();
	
	JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
	ReceivePanel receivePanel;
	SentPanel sentPanel;
	SendPanel sendPanel;
	
	String[] receiveColumns = {"��ȣ", "����", "������", "������¥", "����"};
	DefaultTableModel receiveModel = new DefaultTableModel(receiveColumns, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JTable receiveTable = new JTable(receiveModel);
	JScrollPane receiveScrollPane = new JScrollPane(receiveTable);
	
	String[] sentColumns = {"��ȣ", "����", "�޴���", "������¥", "����"};
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
		this.frame = frame;
		
		pane.addTab("���� ����", receivePanel = new ReceivePanel());
		pane.addTab("���� ����", sentPanel = new SentPanel());
		pane.addTab("���� ����", sendPanel = new SendPanel());
		
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
			
			add(receiveScrollPane);
			
			receiveTable.addMouseListener(new ReadReceiveLetter());
			receiveTable.getColumn("��ȣ").setPreferredWidth(40);
			receiveTable.getColumn("����").setPreferredWidth(170);
			receiveTable.getColumn("������").setPreferredWidth(50);
			receiveTable.getColumn("����").setPreferredWidth(50);
			receiveTable.getTableHeader().setReorderingAllowed(false);
			receiveTable.getTableHeader().setResizingAllowed(false);
			
			refreshLetterList();
		}
	}
	class SentPanel extends JPanel{
		JButton sendBtn;
		JPanel btnPanel;
		public SentPanel() {
			
			add(sentScrollPane, BorderLayout.CENTER);
			
			sentTable.getColumn("��ȣ").setPreferredWidth(40);
			sentTable.getColumn("����").setPreferredWidth(170);
			sentTable.getColumn("�޴���").setPreferredWidth(50);
			sentTable.getColumn("����").setPreferredWidth(50);
			sentTable.getTableHeader().setReorderingAllowed(false);
			sentTable.getTableHeader().setResizingAllowed(false);
			
			refreshLetterList();
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
		
		receiveLetters = dao.getMyReceiveLetterList(conn, frame.getSession_idx());
		
		for(int i=0;i<receiveLetters.size();i++) {
			
			String[] letter = new String[5];
			letter[0] = String.valueOf(i+1);
			letter[1] = receiveLetters.get(i).getTitle();
			letter[2] = receiveLetters.get(i).getListNickname();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
			letter[3] = dateFormat.format(receiveLetters.get(i).getSendDate());
			
			if(receiveLetters.get(i).getReadDate() == null) {
				letter[4] = "������";
			} else {
				letter[4] = "����";
			}
			
			receiveModel.addRow(letter);
		}
		
		DB_Closer.close(conn);
	}
	
	public void getSentLetterList() {
		sentModel.setNumRows(0);
		
		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();
		
		sendLetters = dao.getMySendLetterList(conn, frame.getSession_idx());
		
		for(int i=0;i<sendLetters.size();i++) {
			
			String[] letter = new String[5];
			letter[0] = String.valueOf(i+1);
			letter[1] = sendLetters.get(i).getTitle();
			letter[2] = sendLetters.get(i).getListNickname();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
			letter[3] = dateFormat.format(sendLetters.get(i).getSendDate());
			
			if(sendLetters.get(i).getReadDate() == null) {
				letter[4] = "������";
			} else {
				letter[4] = "����";
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
			add(receiverLabel = new JLabel("�޴��� �г���"));
//			receiverLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			receiverLabel.setBounds(10, 0, 100, 30);
			add(receiverField = new JTextField());
			receiverField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			receiverField.setBounds(100, 0, 295, 30);
			add(findMemBtn = new JButton("�˻�"));
			findMemBtn.setBounds(400, 0, 60, 30);
			
			add(titleLabel = new JLabel("����"));
//			titleLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			titleLabel.setBounds(10, 40, 40, 30);
			add(titleField = new JTextField());
			titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			titleField.setBounds(100, 40, 360, 30);
			
			add(contentsLabel = new JLabel("����"));
			contentsLabel.setBounds(10, 70, 40, 30);
			contentsField = new JTextArea();
			add(contentsPanel = new JScrollPane(contentsField));
			contentsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			contentsPanel.setBounds(100, 70, 360, 170);

			add(sendBtn = new JButton("����"));
			sendBtn.setBounds(400, 250, 60, 30);
			
			generateEvent();
		}
		
		private void generateEvent() {
			
			findMemBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "�غ����Դϴ�.", "���� ������", JOptionPane.INFORMATION_MESSAGE);
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
					
					String receiverNickname = getReceiverField();
					String title = getTitleField();
					String contents = getContentsField();
					
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int receiverIdx = dao.getMemberIdx_FromNickname(conn, receiverNickname);
					
					if(receiverIdx == 0) {
						JOptionPane.showMessageDialog(null, "���� ����� ��Ȯ�� �������ּ���.", "���� ������", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int re = dao.lSend(conn, title, contents, frame.getSession_idx(), receiverIdx);
					if(re == 0) {
						JOptionPane.showMessageDialog(null, "���� ������ ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "���� ������", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "���� ������ ����", "���� ������", JOptionPane.INFORMATION_MESSAGE);
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

		public void setReceiverField(JTextField receiverField) {
			this.receiverField = receiverField;
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
				ReadReceiveLetterDialog readReceiveLetterDialog = new ReadReceiveLetterDialog(owner, "���� ����");
				
				int l_idx = Integer.parseInt((String) receiveTable.getModel().getValueAt(receiveTable.getSelectedRow(), 0)) -1;
				
				LetterDTO dto = receiveLetters.get(l_idx);
				
				readReceiveLetterDialog.setSenderField(dto.getListNickname());
				SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd / hh:mm:ss");
				readReceiveLetterDialog.setSendDateField(dateFormat.format(dto.getSendDate()));
				readReceiveLetterDialog.setTitleField(dto.getTitle());
				readReceiveLetterDialog.setContentsField(dto.getContents());
				
				if(dto.getReadDate() == null) {
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int re = dao.lReadLetter(conn, dto.getL_idx());
					if(re == 0) JOptionPane.showMessageDialog(null, "�����߻�", "���� �б�", JOptionPane.WARNING_MESSAGE);
					
					DB_Closer.close(conn);
					
					refreshLetterList();
				}
				readReceiveLetterDialog.setVisible(true);
			}
		}
	}
}
