package dialog.letter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DAO;

import db.util.GenerateConnection;

public class LetterFindNicknameDialog extends JDialog{
	
	private boolean value = false;
	
	JList<String> nicknameList;
	JScrollPane nicknamePane;
	JButton selectBtn;
	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	public LetterFindNicknameDialog(JDialog owner, String title) {
		super(owner, title, true);
		
		Connection conn = GenerateConnection.getConnection();
		DAO dao = DAO.getInstance();
		
		Vector<String> nicknames = dao.mGetNicknames(conn);
		nicknameList = new JList<>(nicknames);
		nicknamePane = new JScrollPane(nicknameList);
		
		btnPanel.add(selectBtn = new JButton("º±≈√"));
		
		getContentPane().setLayout(new BorderLayout());
		add(nicknamePane, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
		
		nicknameList.addMouseListener(new SelectNickname());
		
		setSize(30, 200);
		setLocationRelativeTo(null);
		
		generateEvent();
	}
	
	private void generateEvent() {
		selectBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	class SelectNickname extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				
			}
		}
	}
	
	

	public boolean check() {
		return this.value;
	}
}
