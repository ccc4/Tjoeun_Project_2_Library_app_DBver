package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import db.util.DB_Transaction;
import db.util.GenerateConnection;
import dialog.book.BookAddDialog;
import dialog.book.BookDeleteDialog;
import dialog.book.BookModiDialog;
import dialog.book.BookSearchDialog;
import dialog.letter.LetterDialog;
import dialog.member.LoginDialog;
import dialog.member.MemChangePw;
import dialog.member.MemCheckRemove;
import dialog.member.MemJoinDialog;
import dialog.member.MemModiDialog;
import dto.BookDTO;
import dto.MemberDTO;
import dto.RtListDTO;
import dto.RvListDTO;

@SuppressWarnings("serial")
public class Home extends JFrame {
	
	private Home owner = this;
	private static int session_idx = 0;

	private int selectedBookIdx;
	
	ArrayList<BookDTO> books;
	ArrayList<RtListDTO> rentalBooks;
	ArrayList<RvListDTO> reserveBooks;
	
	
	
	
	
	// memTop �α���
	JLabel memTopNorthLabel = new JLabel("�α��� �� �̿밡��");
	JPanel memTopCenterPanel = new JPanel(new GridLayout(2, 2));
	JButton memLoginBtn = new JButton("�α���");
	JButton memJoinBtn = new JButton("ȸ������");
	JButton memModiBtn = new JButton("��������");
	JButton memLogoutBtn = new JButton("�α׾ƿ�");

	// memMid �뿩
	String[] memRtListColumns = {" ", "å ����", "�뿩 ����" };
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
	String[] memRvListColumns = {" ", "å ����", "���� ����" };
	DefaultTableModel rvListModel = new DefaultTableModel(memRvListColumns, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JTable rvListTable = new JTable(rvListModel);
	JScrollPane memBotScrollPane = new JScrollPane(rvListTable);

	JPanel memBotSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JButton memBotRentalBtn = new JButton("�뿩");
	JButton memBotReserveCancelBtn = new JButton("�������");

	// bookPanel
	JPanel bookTopPanel = new JPanel(new GridLayout(1, 2));
	JPanel bookTopLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel bookTopRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JSplitPane bookMidPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JPanel bookBotPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	JButton bookSearchBtn = new JButton("å �˻�");
	JButton bookSearchBtn2 = new JButton("å �˻�2");
	JButton LetterBtn = new JButton("������");
	JButton bookRentalBtn = new JButton("�뿩");
	JButton bookReserveBtn = new JButton("����");

	// ���̺� �߰�

	String[] bookListColumns = {"��ȣ", "å ����", "����", "���ǻ�", "���� ����" };
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
	JMenuItem admin_Book_delete = new JMenuItem("å ����");
	JMenu menu_Admin_Member = new JMenu("ȸ�� ����");
	JMenuItem admin_Member_List = new JMenuItem("ȸ�� ���");
	JMenuItem admin_Member_Add = new JMenuItem("ȸ�� �߰�");
	JMenuItem admin_Member_Modify = new JMenuItem("ȸ�� ��������");
	JMenuItem admin_Member_Delete = new JMenuItem("ȸ�� ����");

	JMenu menu_Member = new JMenu("ȸ�� �޴�");
	JMenuItem member_ChangePw = new JMenuItem("��й�ȣ ����");
	JMenuItem member_DeleteId = new JMenuItem("ȸ��Ż��");

	// =======================================================================

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

		bookListTable.addMouseListener(new BookListSelectedRow());
		bookListTable.getColumn("��ȣ").setPreferredWidth(40);
		bookListTable.getColumn("å ����").setPreferredWidth(300);
		bookListTable.getColumn("����").setPreferredWidth(130);
		bookListTable.getColumn("���ǻ�").setPreferredWidth(130);
		bookListTable.getTableHeader().setReorderingAllowed(false);
		bookListTable.getTableHeader().setResizingAllowed(false);

		rtListTable.addMouseListener(new MRentalListSelectedRow());
		rtListTable.getColumn(" ").setPreferredWidth(10);
		rtListTable.getColumn("å ����").setPreferredWidth(100);
		rtListTable.getTableHeader().setReorderingAllowed(false);
		rtListTable.getTableHeader().setResizingAllowed(false);

		rvListTable.getColumn(" ").setPreferredWidth(10);
		rvListTable.getColumn("å ����").setPreferredWidth(100);
		rvListTable.addMouseListener(new MReservationListSelectedRow());
		rvListTable.getTableHeader().setReorderingAllowed(false);
		rvListTable.getTableHeader().setResizingAllowed(false);

		mainPanel.setResizeWeight(.99);
		mainPanel.setDividerLocation(800);
		mainPanel.setEnabled(false);

		logout();
//			menu_Admin.setVisible(false); �׽�Ʈ�Ҷ� ���α�
		generateEvent();

		this.setSize(1100, 450);
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

			bookTopLeftPanel.add(bookSearchBtn);
			bookTopLeftPanel.add(bookSearchBtn2);
			bookTopRightPanel.add(LetterBtn);
			bookTopPanel.add(bookTopLeftPanel);
			bookTopPanel.add(bookTopRightPanel);

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

			refreshTable();
		}
	}

	class BookListSelectedRow extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {

			controlBtn();
			Connection conn = GenerateConnection.getConnection();
			DAO dao = DAO.getInstance();

			String bookImgName = "";
//			selectedBookTitle = (String) bookListTable.getValueAt(bookListTable.getSelectedRow(), 0);
//			selectedBookIdx = dao.getBookIdx_FromTitle(conn, selectedBookTitle);
			int index = Integer.parseInt((String) bookListTable.getValueAt(bookListTable.getSelectedRow(), 0)) -1;
			selectedBookIdx = books.get(index).getIdx();

			BookDTO dto = dao.getBookInfo(conn, selectedBookIdx);

			bookImgName = dto.getImgName();
			if (bookImgName.trim().length() == 0 || bookImgName.equals("")) {
				bookImagePanel.setNoImage();
			} else {
				bookImagePanel.setSavedImage(bookImgName);
			}

			if (getSession_idx() != 0) {
				int checkReservation = dao.checkBookReservation(conn, selectedBookIdx);
				int checkReservationMine = dao.checkBookReservationMine(conn, getSession_idx(), selectedBookIdx);
				int checkRental = dao.checkBookRental(conn, selectedBookIdx);

				if (checkReservation == 1 & checkRental == 0) { // ������ ������ (���� �����ߴ��� Ȯ�� �ʿ�)
					if (checkReservationMine == 1) { // ���� �������� ���
						bookRentalBtn.setEnabled(true);
						bookReserveBtn.setEnabled(false);
					} else { // �ٸ������ ����
						bookRentalBtn.setEnabled(false);
						bookReserveBtn.setEnabled(false);
					}
				} else if (checkReservation == 0 & checkRental == 1) { // �뿩��
					bookRentalBtn.setEnabled(false);
					bookReserveBtn.setEnabled(false);
				} else if (checkReservation == 0 & checkRental == 0) { // �뿩 ���� ����
					bookRentalBtn.setEnabled(true);
					bookReserveBtn.setEnabled(true);
				}
			}
			
			
			if(e.getClickCount() == 2) {
				if(getSession_idx() == 0) return;
				
				controlBtn();
				BookSearchDialog bookSearchDialog = new BookSearchDialog(owner, "å ����");
				
				String targetTitle = (String) bookListTable.getValueAt(bookListTable.getSelectedRow(), 1);
//				String targetAuthor = (String) bookListTable.getValueAt(bookListTable.getSelectedRow(), 2);
				bookSearchDialog.setSearchField(targetTitle);
				bookSearchDialog.getSearchBtn().doClick();
				bookSearchDialog.setVisible(true);

				
				String title, author;
				if (bookSearchDialog.check() == 0) {
					return;
				} else if (bookSearchDialog.check() == 1) { // �뿩
					int check = JOptionPane.showConfirmDialog(null, "�� å�� �뿩�Ͻðڽ��ϱ�?", "å �뿩", JOptionPane.YES_NO_OPTION);
					if (check != JOptionPane.YES_OPTION) return;

					title = bookSearchDialog.getTitleField();
					author = bookSearchDialog.getAuthorField();
					
					int b_idx = dao.getBookIdx_FromTitleAuthor(conn, title, author);

					int checkReserve = dao.checkBookReservationMine(conn, getSession_idx(), b_idx);
					if (checkReserve == 1) {
						dao.bRental_AtReservation(conn, getSession_idx(), b_idx);
					}
					int re = dao.bRental(conn, getSession_idx(), b_idx);
					if (re == 0) {
						JOptionPane.showMessageDialog(null, "å �뿩 ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �뿩", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "å �뿩 �Ϸ�", "å �뿩", JOptionPane.INFORMATION_MESSAGE);
					}

				} else if (bookSearchDialog.check() == 2) { // ����
					int check = JOptionPane.showConfirmDialog(null, "�� å�� �����Ͻðڽ��ϱ�?", "å ����", JOptionPane.YES_NO_OPTION);
					if (check != JOptionPane.YES_OPTION) return;

					title = bookSearchDialog.getTitleField();
					author = bookSearchDialog.getAuthorField();
					
					int b_idx = dao.getBookIdx_FromTitleAuthor(conn, title, author);

					int re = dao.bReserve(conn, getSession_idx(), b_idx);
					if (re == 0) {
						JOptionPane.showMessageDialog(null, "å ���� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å ����", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "å ���� �Ϸ�", "å ����", JOptionPane.INFORMATION_MESSAGE);
					}

				}
				refreshTable();
			}
			DB_Closer.close(conn);
		}
	}

	class MRentalListSelectedRow extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			controlBtn();
			Connection conn = GenerateConnection.getConnection();
			DAO dao = DAO.getInstance();

//			selectedBookTitle = (String) rtListTable.getModel().getValueAt(rtListTable.getSelectedRow(), 0);
//			selectedBookIdx = dao.getBookIdx_FromTitle(conn, selectedBookTitle);
			int index = Integer.parseInt((String) rtListTable.getValueAt(rtListTable.getSelectedRow(), 0))-1;
			selectedBookIdx = rentalBooks.get(index).getB_idx();

			memMidReturnBtn.setEnabled(true);
			DB_Closer.close(conn);
		}
	}

	class MReservationListSelectedRow extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			controlBtn();
			Connection conn = GenerateConnection.getConnection();
			DAO dao = DAO.getInstance();

//			selectedBookTitle = (String) rvListTable.getModel().getValueAt(rvListTable.getSelectedRow(), 0);
//			selectedBookIdx = dao.getBookIdx_FromTitle(conn, selectedBookTitle);
			int index = Integer.parseInt((String) rvListTable.getValueAt(rvListTable.getSelectedRow(), 0))-1;
			selectedBookIdx = reserveBooks.get(index).getB_idx();

			memBotRentalBtn.setEnabled(true);
			memBotReserveCancelBtn.setEnabled(true);
			DB_Closer.close(conn);
		}
	}

	public void refreshTable() {
		getBookList();
		getMRentalList();
		getMReservationList();
	}

	public void getBookList() {
		bookListModel.setNumRows(0);

		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();

		books = dao.getBookList(conn);

		for (int i = 0; i < books.size(); i++) {

			String[] book = new String[5];
			book[0] = String.valueOf(i+1);
			book[1] = books.get(i).getTitle();
			book[2] = books.get(i).getAuthor();
			book[3] = books.get(i).getPublisher();

			int checkReservation = dao.checkBookReservation(conn, books.get(i).getIdx());
			int checkRental = dao.checkBookRental(conn, books.get(i).getIdx());

			if (checkReservation == 1 & checkRental == 0) {
				book[4] = "������";
			} else if (checkReservation == 0 & checkRental == 1) {
				book[4] = "�뿩��";
			} else if (checkReservation == 0 & checkRental == 0) {
				book[4] = "�뿩����";
			}

			bookListModel.addRow(book);
		}

		DB_Closer.close(conn);
	}

	public void getMRentalList() {
		rtListModel.setNumRows(0);

		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();

		rentalBooks = dao.getRentalList(conn, getSession_idx());

		for (int i = 0; i < rentalBooks.size(); i++) {

			String[] book = new String[3];
			book[0] = String.valueOf(i+1);
			book[1] = rentalBooks.get(i).getTitle();

//			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd"); // ���� �ڵ�
//			book[2] = dateFormat.format(rentalBooks.get(i).getRentalDate());
			
			
			Calendar cal = Calendar.getInstance();
			
			// ���� ��¥
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String today = sdf1.format(cal.getTime());
//			Timestamp now = Timestamp.valueOf(today);
//			System.out.println("today: " + now);
			
			// ��� ��¥
			Timestamp original = rentalBooks.get(i).getRentalDate();
//			cal.setTimeInMillis(original.getTime());
//			System.out.println("original: " + original);
			
			
			int checkDate = 0;
			// �� ���ϱ�
			try {
//				Date nowDate = sdf1.parse(today);
				Date nowDate = sdf1.parse(sdf1.format(cal.getTime())); // String ���� ��ȯ �� Date Ÿ������ ��ȯ
				Date targetDate = sdf1.parse(sdf1.format(original));
//				Date targetDate = sdf1.parse("2018-09-13 22:40:00"); // �׽�Ʈ��
				
				int sub = (int) (nowDate.getTime() - targetDate.getTime());
				checkDate = sub / (60 * 60 * 24 * 1000); // ��¥�� ��ȯ
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(checkDate < 3) {
				cal.setTimeInMillis(rentalBooks.get(i).getRentalDate().getTime());
				cal.add(Calendar.SECOND, 3 * (60 * 60 * 24));
				Timestamp later = new Timestamp(cal.getTime().getTime());
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd HH:mm");
				
				book[2] = sdf2.format(later) + " ����";
				
			} else {
				book[2] = (checkDate - 2) + " �� ��ü��";
				
				// ������ �޽����� �ش� ȸ������ ��ü �˸���
			}
			rtListModel.addRow(book);
		}
		DB_Closer.close(conn);
	}

	public void getMReservationList() {
		rvListModel.setNumRows(0);

		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();

		reserveBooks = dao.getReservationList(conn, getSession_idx());

		for (int i = 0; i < reserveBooks.size(); i++) {

			String[] book = new String[3];
			book[0] = String.valueOf(i+1);
			book[1] = reserveBooks.get(i).getTitle();

//			book[1] = dateFormat.format(books.get(i).getReserveDate()); // ���� �ڵ�
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTimeInMillis(reserveBooks.get(i).getReserveDate().getTime());
			cal.add(Calendar.SECOND, (60 * 60 * 24));
			Timestamp later = new Timestamp(cal.getTime().getTime());
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd HH:mm");
			book[2] = sdf2.format(later) + " ����";
			
			rvListModel.addRow(book);
		}
		DB_Closer.close(conn);
	}

	class MenubarClass extends JMenuBar {
		public MenubarClass() {

			menu_Option.add(admin_Login);

			menu_Admin_Book.add(admin_Book_List);
			menu_Admin_Book.addSeparator();
			menu_Admin_Book.add(admin_Book_Add);
			menu_Admin_Book.add(admin_Book_Modify);
			menu_Admin_Book.add(admin_Book_delete);

			menu_Admin_Member.add(admin_Member_List);
			menu_Admin_Member.addSeparator();
			menu_Admin_Member.add(admin_Member_Add);
			menu_Admin_Member.add(admin_Member_Modify);
			menu_Admin_Member.add(admin_Member_Delete);

			menu_Admin.add(admin_Logout);
			menu_Admin.add(menu_Admin_Book);
			menu_Admin.add(menu_Admin_Member);

			menu_Member.add(member_ChangePw);
			menu_Member.add(member_DeleteId);

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

				if (!memJoinDialog.check()) {
//					JOptionPane.showMessageDialog(null, "ȸ�������� ����Ͽ����ϴ�.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
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
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "ȸ������ ����", "ȸ������", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "ȸ������ ����!\n" + id + " �� ȯ���մϴ�.", "ȸ������",
							JOptionPane.INFORMATION_MESSAGE);
				}

				DB_Closer.close(conn);
			}
		});

		this.memLoginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginDialog memLoginDialog = new LoginDialog(owner, "�α���");
				memLoginDialog.setVisible(true);

				if (!memLoginDialog.check())
					return;

				String id = memLoginDialog.getIdField();
				String pw = memLoginDialog.getPwField();

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				int re = dao.mLogin(conn, id, pw);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "�α��� ����\n���̵�� ��й�ȣ�� Ȯ�����ּ���.", "�α���", JOptionPane.WARNING_MESSAGE);
				} else {
					MemberDTO dto = dao.getSession(conn, id);
					setSession_idx(dto.getIdx());
					memTopNorthLabel.setText(dto.getNickname() + " �� �������");
					loginSuccess();
					refreshTable();

					// �α����� �� �� ���� �޽����� �ִ� ��� �˾�
					int re2 = dao.checkLetter(conn, getSession_idx());
					if (re2 != 0) {
						int check = JOptionPane.showConfirmDialog(null, "�� ���� ������ " + re2 + " �� �ֽ��ϴ�.\nȮ���Ͻðڽ��ϱ�?", "���� �˸�", JOptionPane.YES_NO_OPTION);
						if (check != JOptionPane.YES_OPTION) {
							return;
						} else if (check == JOptionPane.YES_OPTION) {
							LetterDialog letterDialog = new LetterDialog(owner, "������");
							letterDialog.setVisible(true);
						}
					}
				}

				DB_Closer.close(conn);
			}
		});

		this.memModiBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MemModiDialog memModiDialog = new MemModiDialog(owner, "ȸ������ ����");
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				MemberDTO member = dao.getMemberInfo(conn, getSession_idx());
				
				memModiDialog.setIdField(member.getId());
				memModiDialog.setNicknameField(member.getNickname());
				memModiDialog.setNameField(member.getName());
				memModiDialog.setAgeField(member.getAge());
				
				
				if(member.getGender().equals("M")) {
					memModiDialog.getMaleBtn().setSelected(true);
				} else if(member.getGender().equals("F")) {
					memModiDialog.getFemaleBtn().setSelected(true);
				}
				
				memModiDialog.setTelField(member.getTel());
				memModiDialog.setEmailField_1(member.getEmail_1());
				
				if(member.getEmail_2().equals("naver.com")) {
					memModiDialog.getEmailField_2_List().setSelectedItem("naver.com");
				} else {
					memModiDialog.getEmailField_2_List().setSelectedItem("gmail.com");
				}
				
				memModiDialog.setAddressArea(member.getAddress());
				
				// ���̾˷αװ� ������
				
				memModiDialog.setVisible(true);

				if (!memModiDialog.check()) return;

				String nickname = memModiDialog.getNicknameField();
				String name = memModiDialog.getNameField();
				int age = Integer.parseInt(memModiDialog.getAgeField());
				String gender = memModiDialog.getGender();
				String tel = memModiDialog.getTelField();
				String email_1 = memModiDialog.getEmailField_1();
				String email_2 = memModiDialog.getEmailField_2();
				String address = memModiDialog.getAddressArea();


				int re = dao.mModify(conn, getSession_idx(), nickname, name, age, gender, tel, email_1, email_2, address);

				if (re == 0) {
					JOptionPane.showMessageDialog(null, "ȸ������ ���� ����", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
				} else {
					memTopNorthLabel.setText(nickname + " �� �������");
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

		this.member_ChangePw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MemChangePw memChangePw = new MemChangePw(owner, "��й�ȣ ����");
				memChangePw.setVisible(true);

				if (!memChangePw.check()) return;

				String beforePw = memChangePw.getBeforePwField();
				String afterPw = memChangePw.getAfterPwField();

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				MemberDTO dto = dao.getMemberInfo(conn, getSession_idx());
				int re1 = dao.mLogin(conn, dto.getId(), beforePw);
				if (re1 == 0) {
					JOptionPane.showMessageDialog(null, "���� ��й�ȣ�� Ȯ�����ּ���.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
				} else if (re1 != 0 && (beforePw.equals(afterPw))) {
					JOptionPane.showMessageDialog(null, "���� ��й�ȣ�� ���� ��й�ȣ�δ� ������ �� �����ϴ�.\n�ٸ� ��й�ȣ�� ��G���ּ���.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
				} else {
					int re2 = dao.mChangePw(conn, getSession_idx(), afterPw);
					if (re2 == 0) {
						JOptionPane.showMessageDialog(null, "��й�ȣ ���� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "��й�ȣ ���� �Ϸ�\n�ٽ� �α������ּ���.", "��й�ȣ ����", JOptionPane.INFORMATION_MESSAGE);
						logout();
					}
				}

				DB_Closer.close(conn);
			}
		});

		this.member_DeleteId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MemCheckRemove memCheckRemove = new MemCheckRemove(owner, "ȸ��Ż��");
				memCheckRemove.setVisible(true);

				if (!memCheckRemove.check()) return;

				String pw = memCheckRemove.getPwField();

				Connection conn = GenerateConnection.getConnection();
				DB_Transaction.setAutoCommit(conn, false);
				DAO dao = DAO.getInstance();

				MemberDTO dto = dao.getMemberInfo(conn, getSession_idx());

				if (!dto.getPw().equals(pw)) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� Ȯ�����ּ���.", "ȸ��Ż��", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int check = JOptionPane.showConfirmDialog(null, "����������ٰ� ȸ��Ż�� �����Ͻðڽ��ϱ�?\n������ �����ʹ� ���� �Ұ����մϴ�.", "ȸ��Ż��", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;

				int re1 = dao.mDeleteAddToList(conn, dto.getIdx(), dto.getId(), dto.getPw(), dto.getNickname(),
						dto.getName(), dto.getAge(), dto.getGender(), dto.getTel(), dto.getEmail_1(), dto.getEmail_2(),
						dto.getAddress(), dto.getJoinDate());
				if (re1 == 0) {
					JOptionPane.showMessageDialog(null, "ȸ�� Ż�� ����(Ʈ����� ����)\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "ȸ��Ż��", JOptionPane.WARNING_MESSAGE);
					DB_Transaction.rollback(conn); 
				} else {
					int re2 = dao.mDelete(conn, session_idx);
					if (re2 == 0) {
						JOptionPane.showMessageDialog(null, "ȸ�� Ż�� ����(Ʈ����� ����)\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "ȸ��Ż��", JOptionPane.WARNING_MESSAGE);
						DB_Transaction.rollback(conn);
						return;
					} else {
						JOptionPane.showMessageDialog(null, "ȸ�� Ż�� �Ϸ�", "ȸ��Ż��", JOptionPane.INFORMATION_MESSAGE);
						DB_Transaction.commit(conn);
						logout();
					}
				}
				DB_Closer.close(conn);
			}
		});

		this.admin_Login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginDialog memLoginDialog = new LoginDialog(owner, "������ �α���");
				memLoginDialog.setVisible(true);

				if (!memLoginDialog.check()) return;
				
				String id = memLoginDialog.getIdField();
				String pw = memLoginDialog.getPwField();

				if (!(id.equals("abcd") && pw.equals("1234"))) {
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

		this.admin_Book_Add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BookAddDialog bookAddDialog = new BookAddDialog(owner, "å ���");
				bookAddDialog.setVisible(true);

				if (!bookAddDialog.check()) return;

				String title = bookAddDialog.getTitleField();
				String author = bookAddDialog.getAuthorField();
				String publisher = bookAddDialog.getPublisherField();
				String imgName = "";
				String targetImgFilePath = bookAddDialog.getTargetImgFilePath();

				if (targetImgFilePath.trim().length() != 0) {
					imgName = title + targetImgFilePath.substring(targetImgFilePath.length() - 4);
				}

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				int re = dao.bAdd(conn, title, author, publisher, imgName, targetImgFilePath);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "å ��� ����", "å ���", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "å ��� �Ϸ�", "å ���", JOptionPane.INFORMATION_MESSAGE);
				}
				DB_Closer.close(conn);

				refreshTable();
			}
		});
		
		this.admin_Book_Modify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BookModiDialog bookModiDialog = new BookModiDialog(owner, "å ����");
				bookModiDialog.setVisible(true);

				if (!bookModiDialog.check())return;

				int b_idx = bookModiDialog.getB_idx();
				String title = bookModiDialog.getTitleField();
				String author = bookModiDialog.getAuthorField();
				String publisher = bookModiDialog.getPublisherField();
				String imgName = "";
				String targetImgFilePath = bookModiDialog.getTargetImgFilePath();

				if (targetImgFilePath.trim().length() != 0) {
					imgName = title + targetImgFilePath.substring(targetImgFilePath.length() - 4);
				}

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				int re = dao.bModify(conn, b_idx, title, author, publisher, imgName, targetImgFilePath);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "å ���� ����", "å ����", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "å ���� �Ϸ�", "å ����", JOptionPane.INFORMATION_MESSAGE);
				}
				DB_Closer.close(conn);

				refreshTable();
			}
		});
		
		this.admin_Book_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookDeleteDialog bookDeleteDialog = new BookDeleteDialog(owner, "å ����");
				bookDeleteDialog.setVisible(true);

				if (!bookDeleteDialog.check())return;
				
				int b_idx = bookDeleteDialog.getB_idx();
				
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int re = dao.bDelete(conn, b_idx);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "å ���� ����", "å ����", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "å ���� �Ϸ�", "å ����", JOptionPane.INFORMATION_MESSAGE);
				}
				DB_Closer.close(conn);
				
				refreshTable();
			}
		});

		this.bookRentalBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				int check = JOptionPane.showConfirmDialog(null, "�� å�� �뿩�Ͻðڽ��ϱ�?", "å �뿩", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION)return;

				Connection conn = GenerateConnection.getConnection();
				DB_Transaction.setAutoCommit(conn, false);
				DAO dao = DAO.getInstance();

				int checkReserve = dao.checkBookReservationMine(conn, getSession_idx(), selectedBookIdx);
				if (checkReserve == 1) {
					int check2 = dao.bRental_AtReservation(conn, getSession_idx(), selectedBookIdx);
					if (check2 == 0) {
						DB_Transaction.rollback(conn);
						JOptionPane.showMessageDialog(null, "å �뿩 ����-1 (������� ����))\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �뿩", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				int re = dao.bRental(conn, getSession_idx(), selectedBookIdx);
				if (re == 0) {
					DB_Transaction.rollback(conn);
					JOptionPane.showMessageDialog(null, "å �뿩 ����-2 (�뿩 ����)\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �뿩", JOptionPane.WARNING_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "å �뿩 �Ϸ�", "å �뿩", JOptionPane.INFORMATION_MESSAGE);

				DB_Transaction.commit(conn);
				DB_Closer.close(conn);

				bookRentalBtn.setEnabled(false);
				bookReserveBtn.setEnabled(false);
				refreshTable();
				// ȸ�� �뿩 ���� ��ϵ� ������Ʈ
			}
		});

		this.bookReserveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				int check = JOptionPane.showConfirmDialog(null, "�� å�� �����Ͻðڽ��ϱ�?", "å ����", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				int re = dao.bReserve(conn, getSession_idx(), selectedBookIdx);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "å ���� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å ����", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "å ���� �Ϸ�", "å ����", JOptionPane.INFORMATION_MESSAGE);
				}

				DB_Closer.close(conn);

				bookRentalBtn.setEnabled(false);
				bookReserveBtn.setEnabled(false);
				refreshTable();
			}
		});

		this.memMidReturnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				int check = JOptionPane.showConfirmDialog(null, "�� å�� �ݳ��Ͻðڽ��ϱ�?", "å �ݳ�", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				int re = dao.bReturn(conn, getSession_idx(), selectedBookIdx);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "å �ݳ� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �ݳ�", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "å �ݳ� �Ϸ�", "å �ݳ�", JOptionPane.INFORMATION_MESSAGE);
				}

				DB_Closer.close(conn);

				memMidReturnBtn.setEnabled(false);
				refreshTable();
			}
		});

		this.memBotRentalBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				int check = JOptionPane.showConfirmDialog(null, "�� å�� �뿩�Ͻðڽ��ϱ�?", "å �뿩", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;

				Connection conn = GenerateConnection.getConnection();
				DB_Transaction.setAutoCommit(conn, false);
				DAO dao = DAO.getInstance();

				int checkReserve = dao.checkBookReservationMine(conn, getSession_idx(), selectedBookIdx);
				if (checkReserve == 1) {
					int check2 = dao.bRental_AtReservation(conn, getSession_idx(), selectedBookIdx);
					if (check2 == 0) {
						DB_Transaction.rollback(conn);
						JOptionPane.showMessageDialog(null, "å �뿩 ����-1 (������� ����))\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �뿩", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				int re = dao.bRental(conn, getSession_idx(), selectedBookIdx);
				if (re == 0) {
					DB_Transaction.rollback(conn);
					JOptionPane.showMessageDialog(null, "å �뿩 ����-2 (�뿩 ����)\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �뿩", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					JOptionPane.showMessageDialog(null, "å �뿩 �Ϸ�", "å �뿩", JOptionPane.INFORMATION_MESSAGE);
				}
				DB_Transaction.commit(conn);
				DB_Closer.close(conn);

				bookRentalBtn.setEnabled(false);
				bookReserveBtn.setEnabled(false);
				refreshTable();
				// ȸ�� �뿩 ���� ��ϵ� ������Ʈ
			}
		});

		this.memBotReserveCancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				int check = JOptionPane.showConfirmDialog(null, "�� å�� ������ ����Ͻðڽ��ϱ�?", "å �������", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				int re = dao.bReservationCancel(conn, getSession_idx(), selectedBookIdx);
				if (re == 0) {
					JOptionPane.showMessageDialog(null, "å ������� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �������", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "å ������� �Ϸ�", "å �������", JOptionPane.INFORMATION_MESSAGE);
				}

				DB_Closer.close(conn);

				memBotRentalBtn.setEnabled(false);
				memBotReserveCancelBtn.setEnabled(false);
				refreshTable();
			}
		});

		this.bookSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				BookSearchDialog bookSearchDialog = new BookSearchDialog(owner, "å �˻�");
				bookSearchDialog.setVisible(true);

				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();

				String title, author;
				if (bookSearchDialog.check() == 0) {
					return;
				} else if (bookSearchDialog.check() == 1) { // �뿩
					int check = JOptionPane.showConfirmDialog(null, "�� å�� �뿩�Ͻðڽ��ϱ�?", "å �뿩", JOptionPane.YES_NO_OPTION);
					if (check != JOptionPane.YES_OPTION) return;

					title = bookSearchDialog.getTitleField();
					author = bookSearchDialog.getAuthorField();
					
					int b_idx = dao.getBookIdx_FromTitleAuthor(conn, title, author);

					int checkReserve = dao.checkBookReservationMine(conn, getSession_idx(), b_idx);
					if (checkReserve == 1) {
						dao.bRental_AtReservation(conn, getSession_idx(), b_idx);
					}
					int re = dao.bRental(conn, getSession_idx(), b_idx);
					if (re == 0) {
						JOptionPane.showMessageDialog(null, "å �뿩 ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å �뿩", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "å �뿩 �Ϸ�", "å �뿩", JOptionPane.INFORMATION_MESSAGE);
					}

				} else if (bookSearchDialog.check() == 2) { // ����
					int check = JOptionPane.showConfirmDialog(null, "�� å�� �����Ͻðڽ��ϱ�?", "å ����", JOptionPane.YES_NO_OPTION);
					if (check != JOptionPane.YES_OPTION) return;

					title = bookSearchDialog.getTitleField();
					author = bookSearchDialog.getAuthorField();
					
					int b_idx = dao.getBookIdx_FromTitleAuthor(conn, title, author);

					int re = dao.bReserve(conn, getSession_idx(), b_idx);
					if (re == 0) {
						JOptionPane.showMessageDialog(null, "å ���� ����\n��� �ݺ��� ��� �����ڿ��� �����ϼ���.", "å ����", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "å ���� �Ϸ�", "å ����", JOptionPane.INFORMATION_MESSAGE);
					}

				}
				refreshTable();

				DB_Closer.close(conn);
			}
		});

		this.bookSearchBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controlBtn();
				JOptionPane.showMessageDialog(null, "�غ����Դϴ�.", "å �˻� ���� 2", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		this.LetterBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LetterDialog letterDialog = new LetterDialog(owner, "������");
				letterDialog.setVisible(true);
			}
		});

	}

	public void controlBtn() {
		bookRentalBtn.setEnabled(false);
		bookReserveBtn.setEnabled(false);
		memMidReturnBtn.setEnabled(false);
		memBotRentalBtn.setEnabled(false);
		memBotReserveCancelBtn.setEnabled(false);
	}

	public void loginSuccess() {
		this.memLoginBtn.setEnabled(false);
		this.memJoinBtn.setEnabled(false);
		this.memModiBtn.setEnabled(true);
		this.memLogoutBtn.setEnabled(true);

		this.bookSearchBtn.setEnabled(true);
		this.bookSearchBtn2.setEnabled(true);
		this.LetterBtn.setEnabled(true);

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
		this.bookSearchBtn2.setEnabled(false);
		this.LetterBtn.setEnabled(false);
		this.bookRentalBtn.setEnabled(false);
		this.bookReserveBtn.setEnabled(false);

		this.menu_Member.setVisible(false);

		refreshTable();
	}

	public static int getSession_idx() {
		return session_idx;
	}

	public void setSession_idx(int session_idx) {
		this.session_idx = session_idx;
	}

	public static void main(String[] args) {
		new Home();
	}
}
