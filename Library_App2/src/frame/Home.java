package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import dialog.book.BookAddDialog;
import dialog.member.LoginDialog;
import dialog.member.MemJoinDialog;
import dialog.member.MemModiDialog;
import dto.BookDTO;
import dto.MemberDTO;

public class Home extends JFrame {
	// memTop �α���
		JLabel memTopNorthLabel = new JLabel("�α��� �� �̿밡��");
		JPanel memTopCenterPanel = new JPanel(new GridLayout(2, 2));
		JButton memLoginBtn = new JButton("�α���");
		JButton memJoinBtn = new JButton("ȸ������");
		JButton memModiBtn = new JButton("��������");
		JButton memLogoutBtn = new JButton("�α׾ƿ�");
		
		// memMid �뿩
		String[] memRtListColumns = {"å ����", "�뿩 ��¥"};
		DefaultTableModel rtListModel = new DefaultTableModel(memRtListColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable rtListTable = new JTable(rtListModel);
		JScrollPane memMidScrollPane = new JScrollPane(rtListTable);
		
		JPanel memMidSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton memMidReturnBtn = new JButton("�ݳ�");
		
		// memBot ����
		String[] memRvListColumns = {"å ����", "���� ��¥"};
		DefaultTableModel rvListColumns = new DefaultTableModel(memRvListColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable rvListTable = new JTable(rvListColumns);
		JScrollPane memBotScrollPane = new JScrollPane(rvListTable);
		
		JPanel memBotSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton memBotRentalBtn = new JButton("�뿩");
		JButton memBotReserveCancelBtn = new JButton("�������");
		
		
		// bookPanel
		JPanel bookTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JSplitPane bookMidPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JPanel bookBotPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton bookSearchBtn = new JButton("å �˻�");
		JButton bookRentalBtn = new JButton("�뿩");
		JButton bookReserveBtn = new JButton("����");
		
		// ���̺� �߰�
		
		String[] bookListColumns = {"å ����", "����", "���ǻ�", "���� ����"};
		DefaultTableModel bookListModel = new DefaultTableModel(bookListColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable bookListTable = new JTable(bookListModel);
		JScrollPane bookListPanel = new JScrollPane(bookListTable);
		ImagePanel bookImagePanel = new ImagePanel();
		
		
		// main
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JPanel bookPanel = new BookPanelClass();
		JPanel memPanel = new JPanel(new BorderLayout());
		JPanel memTopPanel = new MemTopPanelClass();
		JPanel memMidBotPanel = new JPanel(new GridLayout(2, 1));
		JPanel memMidPanel = new MemMidPanelClass();
		JPanel memBotPanel = new MemBotPanelClass();
		
		
		// ====================================================================
		// menuBar
		
		JMenu menu_Option = new JMenu("�ɼ�");
		JMenuItem admin_Login = new JMenuItem("������ �α���");
		
		JMenu menu_Admin = new JMenu("������ �޴�");
		JMenuItem admin_Logout = new JMenuItem("������ �α׾ƿ�");
		JMenu menu_Admin_Book = new JMenu("å ����");
		JMenuItem admin_Book_List = new JMenuItem("å ���");
		JMenuItem admin_Book_Add = new JMenuItem("å �߰�");
		JMenuItem admin_Book_Modify = new JMenuItem("å ��������");
		JMenuItem admin_Book_Remove = new JMenuItem("å ����");
		JMenu menu_Admin_Member = new JMenu("ȸ�� ����");
		JMenuItem admin_Member_List = new JMenuItem("ȸ�� ���");
		JMenuItem admin_Member_Add = new JMenuItem("ȸ�� �߰�");
		JMenuItem admin_Member_Modify = new JMenuItem("ȸ�� ��������");
		JMenuItem admin_Member_Remove = new JMenuItem("ȸ�� ����");
		
		JMenu menu_Member = new JMenu("ȸ�� �޴�");
		JMenuItem member_ChangePW = new JMenuItem("��й�ȣ ����");
		JMenuItem member_RemoveID = new JMenuItem("ȸ��Ż��");
		
		
		
		
		// =======================================================================
		
		private Home owner = this;
		private int session_idx = 0;
		
		
		
		public Home() {
			super("Library_App");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			memMidBotPanel.add(memMidPanel);
			memMidBotPanel.add(memBotPanel);
			
			memPanel.add(memTopPanel, BorderLayout.NORTH);
			memPanel.add(memMidBotPanel, BorderLayout.CENTER);
			
			mainPanel.add(bookPanel);
			mainPanel.add(memPanel);
			
			this.add(mainPanel);
			this.setJMenuBar(new MenubarClass());
			
			memTopNorthLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
//			bookListTable.addMouseListener(l); Ŭ�� �̺�Ʈ
			bookListTable.getColumn("å ����").setPreferredWidth(300);
			bookListTable.getColumn("����").setPreferredWidth(130);
			bookListTable.getColumn("���ǻ�").setPreferredWidth(130);
			bookListTable.getTableHeader().setReorderingAllowed(false);
			bookListTable.getTableHeader().setResizingAllowed(false);
			
			rtListTable.getTableHeader().setReorderingAllowed(false);
			rtListTable.getTableHeader().setResizingAllowed(false);
			
			rvListTable.getTableHeader().setReorderingAllowed(false);
			rvListTable.getTableHeader().setResizingAllowed(false);
			
			mainPanel.setResizeWeight(.99);
			mainPanel.setDividerLocation(800);
			mainPanel.setEnabled(false);
			
			logout();
			menu_Admin.setVisible(false);
			generateEvent();
			
			
			
			
			
			
			this.setSize(1000, 450);
			this.setLocationRelativeTo(null);
//			this.setResizable(false);
			this.setVisible(true);
		}

		
		class MemTopPanelClass extends JPanel {
			
			public MemTopPanelClass() {
				
				memTopCenterPanel.add(memLoginBtn);
				memTopCenterPanel.add(memJoinBtn);
				memTopCenterPanel.add(memModiBtn);
				memTopCenterPanel.add(memLogoutBtn);
				
				this.setLayout(new BorderLayout());
				this.add(memTopNorthLabel, BorderLayout.NORTH);
				this.add(memTopCenterPanel, BorderLayout.CENTER);
			}
		}
		
		class MemMidPanelClass extends JPanel {
			public MemMidPanelClass() {
				
				memMidSouthPanel.add(memMidReturnBtn);
				
				this.setBorder(BorderFactory.createTitledBorder("�뿩 ����"));
				this.setLayout(new BorderLayout());
				this.add(memMidScrollPane, BorderLayout.CENTER);
				this.add(memMidSouthPanel, BorderLayout.SOUTH);
			}
		}
		
		class MemBotPanelClass extends JPanel {
			public MemBotPanelClass() {
				
				memBotSouthPanel.add(memBotRentalBtn);
				memBotSouthPanel.add(memBotReserveCancelBtn);
				
				this.setBorder(BorderFactory.createTitledBorder("���� ����"));
				this.setLayout(new BorderLayout());
				this.add(memBotScrollPane, BorderLayout.CENTER);
				this.add(memBotSouthPanel, BorderLayout.SOUTH);
			}
		}
		
		class BookPanelClass extends JPanel {
			public BookPanelClass() {
				
				bookTopPanel.add(bookSearchBtn);
				
				bookMidPanel.setResizeWeight(.7);
				bookMidPanel.setDividerLocation(570);
				bookMidPanel.setEnabled(false);
				bookMidPanel.add(bookListPanel);
				bookMidPanel.add(bookImagePanel);
				
				bookBotPanel.add(bookRentalBtn);
				bookBotPanel.add(bookReserveBtn);
				
				
				this.setLayout(new BorderLayout());
				this.add(bookTopPanel, BorderLayout.NORTH);
				this.add(bookMidPanel, BorderLayout.CENTER);
				this.add(bookBotPanel, BorderLayout.SOUTH);
				
				getBookList();
			}
		}
		
		public void getBookList() {
			bookListModel.setRowCount(0);
			
			Connection conn = GenerateConnection.getConnection();
			DAO dao = DAO.getInstance();
			
			ArrayList<BookDTO> books = dao.bookList(conn);
			
			for(int i=0;i<books.size();i++) {
				
				String[] book = new String[4];
				book[0] = books.get(i).getTitle();
				book[1] = books.get(i).getAuthor();
				book[2] = books.get(i).getPublisher();
				
				int checkReservation = dao.checkBookReservation(conn, books.get(i).getIdx());
				int checkRental = dao.checkBookRental(conn, books.get(i).getIdx());
				
				if(checkReservation == 1 & checkRental == 0) {
					book[3] = "������"; 
				} else if(checkReservation == 0 & checkRental == 1) {
					book[3] = "�뿩��"; 
				} else if(checkReservation == 0 & checkRental == 0) {
					book[3] = "�뿩����"; 
				}
				
				bookListModel.addRow(book);
			}
			
		}
		
		class MenubarClass extends JMenuBar {
			public MenubarClass() {
				
				menu_Option.add(admin_Login);
				
				menu_Admin_Book.add(admin_Book_List);
				menu_Admin_Book.addSeparator();
				menu_Admin_Book.add(admin_Book_Add);
				menu_Admin_Book.add(admin_Book_Modify);
				menu_Admin_Book.add(admin_Book_Remove);
				
				menu_Admin_Member.add(admin_Member_List);
				menu_Admin_Member.addSeparator();
				menu_Admin_Member.add(admin_Member_Add);
				menu_Admin_Member.add(admin_Member_Modify);
				menu_Admin_Member.add(admin_Member_Remove);
				
				menu_Admin.add(admin_Logout);
				menu_Admin.add(menu_Admin_Book);
				menu_Admin.add(menu_Admin_Member);
				
				menu_Member.add(member_ChangePW);
				menu_Member.add(member_RemoveID);
				
				this.add(menu_Option);
				this.add(menu_Admin);
				this.add(menu_Member);
			}
		}
		
		
		private void generateEvent() {
			this.memJoinBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MemJoinDialog memJoinDialog = new MemJoinDialog(owner, "ȸ������");
					memJoinDialog.setVisible(true);
					
					if(!memJoinDialog.check()) {
//						JOptionPane.showMessageDialog(null, "ȸ�������� ����Ͽ����ϴ�.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					String id = memJoinDialog.getIdField();
					String pw = memJoinDialog.getPwField();
					String nickname = memJoinDialog.getNicknameField();
					String name = memJoinDialog.getNameField();
					int age = Integer.parseInt(memJoinDialog.getAgeField());
					String gender = memJoinDialog.getGender();
					String tel = memJoinDialog.getTelField();
					String email_1 = memJoinDialog.getEmailField_1();
					String email_2 = memJoinDialog.getEmailField_2();
					String address = memJoinDialog.getAddressArea();
					
					
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int re = dao.mJoin(conn, id, pw, nickname, name, age, gender, tel, email_1, email_2, address);
					if(re == 0) {
						JOptionPane.showMessageDialog(null, "ȸ������ ����", "ȸ������", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "ȸ������ ����!\n" + id + " �� ȯ���մϴ�.", "ȸ������", JOptionPane.INFORMATION_MESSAGE);
					}
					
					DB_Closer.close(conn);
				}
			});
			
			this.memLoginBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					LoginDialog memLoginDialog = new LoginDialog(owner, "�α���");
					memLoginDialog.setVisible(true);
					
					if(!memLoginDialog.check()) {
//						JOptionPane.showMessageDialog(null, "�α��Τ������� ����Ͽ����ϴ�.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String id = memLoginDialog.getIdField();
					String pw = memLoginDialog.getPwField();
					
//					if(!(id.equals("abcd") && pw.equals("1234"))) {
//						JOptionPane.showMessageDialog(null, "�߸��� ����", "WRONG", JOptionPane.WARNING_MESSAGE);
//						return;
//					}
//					setSession_id(id);
//					loginSuccess();
//					memTopNorthLabel.setText("Welcome " + id + " !!");
					
					
//					�α��� ���
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int re = dao.mLogin(conn, id, pw);
					if(re == 0) {
						JOptionPane.showMessageDialog(null, "�α��� ����\n���̵�� ��й�ȣ�� Ȯ�����ּ���.", "�α���", JOptionPane.WARNING_MESSAGE);
					} else {
						MemberDTO dto = dao.getSession(conn, id);
						setSession_idx(dto.getIdx());
						memTopNorthLabel.setText(dto.getNickname() + " �� �ݰ����ϴ�");
						loginSuccess();
//						JOptionPane.showMessageDialog(null, "�α��� ����!\n" + id + " �� �������.", "�α���", JOptionPane.INFORMATION_MESSAGE);
						
//						�� ���� �޽��� ���� �� �˾�
//						if() {
//							JOptionPane.showMessageDialog(null, "adf ����!\n" + id + " �� �������.", "SUCCESS!", JOptionPane.INFORMATION_MESSAGE);
//							
//						}
						
						
					}
					
					DB_Closer.close(conn);
				}
			});

			this.memModiBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					MemModiDialog memModiDialog = new MemModiDialog(owner, "ȸ������ ����", getSession_idx());
					memModiDialog.setVisible(true);
					
					
					if(!memModiDialog.check()) {
//						JOptionPane.showMessageDialog(null, "���������� ����Ͽ����ϴ�.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					String nickname = memModiDialog.getNicknameField();
					String name = memModiDialog.getNameField();
					int age = Integer.parseInt(memModiDialog.getAgeField());
					String gender = memModiDialog.getGender();
					String tel = memModiDialog.getTelField();
					String email_1 = memModiDialog.getEmailField_1();
					String email_2 = memModiDialog.getEmailField_2();
					String address = memModiDialog.getAddressArea();
					
					
					Connection conn = GenerateConnection.getConnection();
					DAO dao = DAO.getInstance();
					
					int re = dao.mModify(conn, getSession_idx(), nickname, name, age, gender, tel, email_1, email_2, address);
					
					if(re == 0) {
						JOptionPane.showMessageDialog(null, "ȸ������ ���� ����", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "ȸ������ ���� ����", "ȸ������ ����", JOptionPane.INFORMATION_MESSAGE);
					}
					
					DB_Closer.close(conn);
				}
			});

			this.memLogoutBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					logout();
				}
			});
			
			this.admin_Login.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginDialog memLoginDialog = new LoginDialog(owner, "������ �α���");
					memLoginDialog.setVisible(true);
					
					if(!memLoginDialog.check()) {
						return;
					}
					
					String id = memLoginDialog.getIdField();
					String pw = memLoginDialog.getPwField();
					
					if(!(id.equals("abcd") && pw.equals("1234"))) {
						JOptionPane.showMessageDialog(null, "�߸��� ����", "������ �α���", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					menu_Admin.setVisible(true);
				}
			});
			
			this.admin_Logout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					menu_Admin.setVisible(false);
				}
			});
			
//			this.admin_Book_Add.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					BookAddDialog bookAddDialog = new BookAddDialog(owner, "Book Add");
//					bookAddDialog.setVisible(true);
//					
//					String title = bookAddDialog.getTitleField();
//					String author = bookAddDialog.getAuthorField();
//					String imagePath = bookAddDialog.getTargetImgFilePath();
//					
//					
//					
//				}
//			});
		}
		
		
		public void loginSuccess() {
			this.memLoginBtn.setEnabled(false);
			this.memJoinBtn.setEnabled(false);
			this.memModiBtn.setEnabled(true);
			this.memLogoutBtn.setEnabled(true);
			
//			this.memMidReturnBtn.setEnabled(true);
//			this.memBotRentalBtn.setEnabled(true);
//			this.memBotReserveCancelBtn.setEnabled(true);
			
			this.bookSearchBtn.setEnabled(true);
//			this.bookRentalBtn.setEnabled(true);
//			this.bookReserveBtn.setEnabled(true);
			
			this.menu_Member.setVisible(true);
		}
		public void logout() {
			this.setSession_idx(0);
			this.memTopNorthLabel.setText("�α��� �� �̿밡��");
			
			this.memLoginBtn.setEnabled(true);
			this.memJoinBtn.setEnabled(true);
			this.memModiBtn.setEnabled(false);
			this.memLogoutBtn.setEnabled(false);
			
			this.memMidReturnBtn.setEnabled(false);
			this.memBotRentalBtn.setEnabled(false);
			this.memBotReserveCancelBtn.setEnabled(false);
			
			this.bookSearchBtn.setEnabled(false);
			this.bookRentalBtn.setEnabled(false);
			this.bookReserveBtn.setEnabled(false);
			
			this.menu_Member.setVisible(false);
		}
		
		
		public int getSession_idx() {
			return session_idx;
		}

		public void setSession_idx(int session_idx) {
			this.session_idx = session_idx;
		}

		public static void main(String[] args) {
			new Home();
		}
}
