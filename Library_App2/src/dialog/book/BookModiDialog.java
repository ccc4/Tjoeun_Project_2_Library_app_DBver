package dialog.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import frame.Home;
import frame.ImagePanel;

public class BookModiDialog extends JDialog {
	
	private boolean value = false;
	String targetImgFilePath = "";

	JLabel titleLabel, authorLabel, publisherLabel, searchLabel;
	JTextField titleField, authorField, publisherField, searchField;
	ImagePanel imagePanel;
	JButton imgBtn, modifyBtn, searchBtn;

	private DropTarget dt;

	public BookModiDialog(Home frame, String title) {
		super(frame, title, true);
		
		add(searchLabel = new JLabel("검색"));
		searchLabel.setBounds(10, 10, 40, 30);
		add(searchField = new JTextField());
		searchField.setBounds(55, 10, 200, 30);
		add(searchBtn = new JButton("검색"));
		searchBtn.setBounds(260, 10, 60, 30);

		add(titleLabel = new JLabel("제목"));
		titleLabel.setBounds(10, 45, 40, 30);
		add(titleField = new JTextField());
		titleField.setBounds(55, 45, 120, 30);
		
		add(authorLabel = new JLabel("저자"));
		authorLabel.setBounds(10, 80, 40, 30);
		add(authorField = new JTextField());
		authorField.setBounds(55, 80, 120, 30);
		
		add(publisherLabel = new JLabel("출판사"));
		publisherLabel.setBounds(10, 115, 40, 30);
		add(publisherField = new JTextField());
		publisherField.setBounds(55, 115, 120, 30);
		
		add(imgBtn = new JButton("이미지 추가"));
		imgBtn.setBounds(55, 150, 115, 30);
		
		add(imagePanel = new ImagePanel());
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		imagePanel.setBounds(180, 45, 140, 190);
		
		add(modifyBtn = new JButton("수정"));
		modifyBtn.setBounds(260, 240, 60, 30);
		
		
		
		
		getContentPane().setLayout(new BorderLayout());

		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.LIGHT_GRAY);

		setSize(345, 315);
		setResizable(false);
		setLocationRelativeTo(null);
		
		generateEvents();
	}
	
	private void generateEvents() {
		
		dt = new DropTarget(imagePanel, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {

			@Override
			public void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(dtde.getDropAction());
				Transferable tf = dtde.getTransferable();
				try {
					List list = (List) tf.getTransferData(DataFlavor.javaFileListFlavor);
					File imageFile = (File) list.get(0);
					targetImgFilePath = imageFile.getAbsolutePath();
					imagePanel.setLocationImage(targetImgFilePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, true, null);

		this.imgBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("C:\\Users\\402-27\\git\\Tjoeun_Project_1_Library_app\\Library_App\\testImages");
//				JFileChooser chooser = new JFileChooser("C:\\Users\\402-27\\Desktop");
//				JFileChooser chooser = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Only JPG", "JPG");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION)
					return;

				targetImgFilePath = chooser.getSelectedFile().getPath();
				imagePanel.setLocationImage(targetImgFilePath);
			}
		});
		
		this.modifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getTitleField().length() == 0) {
					JOptionPane.showMessageDialog(null, "책 제목을 입력해주세요", "책 등록", JOptionPane.WARNING_MESSAGE);
					titleField.requestFocus();
					return;
				}
				if(getAuthorField().length() == 0) {
					JOptionPane.showMessageDialog(null, "저자를 입력해주세요", "책 등록", JOptionPane.WARNING_MESSAGE);
					authorField.requestFocus();
					return;
				}
				if(getPublisherField().length() == 0) {
					JOptionPane.showMessageDialog(null, "출판사를 입력해주세요", "책 등록", JOptionPane.WARNING_MESSAGE);
					publisherField.requestFocus();
					return;
				}
				if(targetImgFilePath.length() == 0) {
					int re = JOptionPane.showConfirmDialog(null, "이미지를 선택하지 않았습니다.\n그대로 진행하시겠습니까?", "책 등록", JOptionPane.YES_NO_OPTION);
					if(re != JOptionPane.YES_OPTION) return;
				}
				int check = JOptionPane.showConfirmDialog(null, "이대로 수정을 완료하시겠습니까?", "책 수정", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				value = true;
				setVisible(false);
			}
		});
	}

	public boolean check() {
		return this.value;
	}

	public String getTargetImgFilePath() {
		return targetImgFilePath;
	}

	public void setTargetImgFilePath(String targetImgFilePath) {
		this.targetImgFilePath = targetImgFilePath;
	}

	public String getTitleField() {
		return titleField.getText().trim();
	}

	public void setTitleField(JTextField titleField) {
		this.titleField = titleField;
	}

	public String getAuthorField() {
		return authorField.getText().trim();
	}

	public void setAuthorField(JTextField authorField) {
		this.authorField = authorField;
	}

	public String getPublisherField() {
		return publisherField.getText().trim();
	}

	public void setPublisherField(JTextField publisherField) {
		this.publisherField = publisherField;
	}
}
