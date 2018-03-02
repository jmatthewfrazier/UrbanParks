//package view;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Frame;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//
//import model.UrbanParksData;
//import model.UserRole;
//
//
///**
// * Created by dave on 2/13/18.
// */
//public class LoginGUIPanel extends JDialog{
//
//	private static JTextField userName;
//
//	private JLabel lbUserName;
//
//	private JButton btnLogin;
//
//	private JButton btnCancel;
//
//	private String userID;
//
//	private static boolean succeeded;
//
//	private UrbanParksData systemController;
//
//
//	public LoginGUIPanel(Frame parent, UrbanParksData theSystemController){
//		super(parent, "Login", true);
//
//		this.systemController = theSystemController;
//
//		JPanel panel = new JPanel(new GridBagLayout());
//		GridBagConstraints cs = new GridBagConstraints();
//
//		cs.fill = GridBagConstraints.HORIZONTAL;
//
//		lbUserName = new JLabel("User Name: ");
//		cs.gridx = 0;
//		cs.gridy = 0;
//		cs.gridwidth = 1;
//		panel.add(lbUserName, cs);
//
//		userName = new JTextField(20);
//		cs.gridx = 1;
//		cs.gridy = 0;
//		cs.gridwidth = 2;
//		panel.add(userName, cs);
//		panel.setBorder(new LineBorder(Color.GRAY));
//
//		btnLogin = new JButton("Login");
//
//		btnLogin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e){
//				if (systemController.checkUserName(userName.getText().trim())) {
//					userID = userName.getText().trim();
//					succeeded = true;
//				}
//				else{
//					JOptionPane.showMessageDialog(LoginGUIPanel.this,
//							"Invalid username",
//							"Login",
//							JOptionPane.ERROR_MESSAGE);
//					userName.setText("");
//					succeeded = false;
//				}
//			}
//		});
//
//		btnCancel = new JButton("Cancel");
//
//		btnCancel.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//				dispose();
//			}
//		});
//
//		JPanel jp = new JPanel();
//		jp.add(btnLogin);
//		jp.add(btnCancel);
//
//		getContentPane().add(panel, BorderLayout.CENTER);
//        getContentPane().add(jp, BorderLayout.PAGE_END);
//
//        pack();
//        setResizable(false);
//        setLocationRelativeTo(parent);
//	}
//
//
//	public static String getUsername() {
//        return userName.getText().trim();
//    }
//
//	public static boolean isSucceeded(){
//		return succeeded;
//	}
//
//    //end LoginGUIPanel class
//
//}
