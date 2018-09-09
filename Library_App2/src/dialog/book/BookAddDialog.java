package dialog.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import frame.ImagePanel;

public class BookAddDialog extends JDialog {
private boolean value = false;
	
	String targetImgFilePath = "";

	ImagePanel imagePanel = new ImagePanel();
	JPanel botPanel = new JPanel(new BorderLayout());

	JLabel titleLabel = new JLabel("Title");
	JLabel authorLabel = new JLabel("Author");
	JTextField titleField = new JTextField();
	JTextField authorField = new JTextField();
	JButton imgBtn = new JButton("Image");
	JButton addBtn = new JButton("Add");

	JPanel labelPanel = new JPanel(new GridLayout(2, 1));
	JPanel fieldPanel = new JPanel(new GridLayout(2, 1));
	JPanel writePanel = new JPanel(new BorderLayout());
	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	private DropTarget dt;

	public BookAddDialog(JFrame frame, String title) {
		super(frame, title, true);

		labelPanel.add(titleLabel);
		labelPanel.add(authorLabel);
		fieldPanel.add(titleField);
		fieldPanel.add(authorField);
		writePanel.add(labelPanel, BorderLayout.WEST);
		writePanel.add(fieldPanel, BorderLayout.CENTER);
		btnPanel.add(imgBtn);
		btnPanel.add(addBtn);
		
		botPanel.add(writePanel, BorderLayout.CENTER);
		botPanel.add(btnPanel, BorderLayout.SOUTH);

		getContentPane().setLayout(new BorderLayout());
		this.add(imagePanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);


		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.LIGHT_GRAY);

		setSize(300, 450);
//		setResizable(false);
		setLocationRelativeTo(null);

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
				JFileChooser chooser = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Only JPG", "JPG");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION)
					return;

				targetImgFilePath = chooser.getSelectedFile().getPath();
				imagePanel.setLocationImage(targetImgFilePath);
			}
		});
		
		this.addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getTitleField().length() == 0) {
					JOptionPane.showMessageDialog(null, "책 제목을 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					titleField.requestFocus();
					return;
				}
				if(getAuthorField().length() == 0) {
					JOptionPane.showMessageDialog(null, "저자를 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					authorField.requestFocus();
					return;
				}
				if(targetImgFilePath.length() == 0) {
					int re = JOptionPane.showConfirmDialog(null, "이미지를 선택하지 않았습니다.\n그대로 진행하시겠습니까?", "No Image", JOptionPane.YES_NO_OPTION);
					if(re != JOptionPane.YES_OPTION) return;
				}
				
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
}
