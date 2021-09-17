import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JPanel p1;
	JTextField text;
	JButton button;
	static JPanel area;
	static JFrame f1 = new JFrame();
	 
	static Box vertical = Box.createVerticalBox();
	
	static ServerSocket skt;
	static Socket s;
	
	static DataInputStream din;
	static DataOutputStream dout;
	
	Boolean typing;
	
	public Server(){
		
		f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		p1 = new JPanel(); //Top panel that wiil show the photo, name, videocall button etc.
		p1.setLayout(null);
		p1.setBackground(new Color(255,140,0));
		p1.setBounds(0, 0, 450, 70);
		f1.add(p1);
		
		Image img = new ImageIcon(this.getClass().getResource("/3.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		//ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("3.png"));
		//Image img2 = img.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		
		ImageIcon img1 = new ImageIcon(img);
		JLabel label = new JLabel(img1); //Image of the go back arrow
		label.setBounds(5, 17, 30, 30);
		p1.add(label);

		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0); //If the user clicks the arrow then the frame closes
			}
		});

		Image img2 = new ImageIcon(this.getClass().getResource("/female-modified.png")).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		
		ImageIcon img3 = new ImageIcon(img2);
		JLabel label2 = new JLabel(img3); //Image of the user
		label2.setBounds(40, 5, 60, 60);
		p1.add(label2);
		
		Image img4 = new ImageIcon(this.getClass().getResource("/video.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		
		ImageIcon img5 = new ImageIcon(img4);
		JLabel label5 = new JLabel(img5); //Image of the video
		label5.setBounds(290, 23,30, 30);
		p1.add(label5);
		
		Image img6 = new ImageIcon(this.getClass().getResource("/phone.png")).getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);

		ImageIcon img7 = new ImageIcon(img6);
		JLabel label6 = new JLabel(img7); //Image of the phone
		label6.setBounds(350, 23, 35, 30);
		p1.add(label6);
		
		Image img8 = new ImageIcon(this.getClass().getResource("/3icon.png")).getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);

		ImageIcon img9 = new ImageIcon(img8);
		JLabel label7 = new JLabel(img9); //Image of the 3 dots
		label7.setBounds(410, 23, 13, 25);
		p1.add(label7);
		
		JLabel label3 = new JLabel("Name 1");
		label3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		label3.setForeground(Color.white); //Label for the name
		label3.setBounds(110, 17, 100, 20);
		p1.add(label3);
		
		JLabel label4 = new JLabel("Active Now");
		label4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		label4.setForeground(Color.white); //Label to show if the user is Active
		label4.setBounds(110, 36, 100, 20);
		p1.add(label4);
		
		JLabel serverlabel = new JLabel("'Server'");
		serverlabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
		serverlabel.setForeground(Color.white); //Label to show that this is the server side
		serverlabel.setBounds(205, 25, 100, 20);
		p1.add(serverlabel);
		
		Timer t = new Timer(1, new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(!typing){
					label4.setText("Active Now"); //If the user is not typing, then change the label to "Active Now"
				}
			}
		});
	       
		t.setInitialDelay(2000); //2 seconds delay so that "typing..." changes to "Active Now", after 2 seconds of no typing 
		
		area = new JPanel(); //Main panel that shows the messages
		area.setBounds(5, 75, 440, 570);
		area.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		
		JScrollPane pane = new JScrollPane(area);
		pane.setBounds(5, 75, 440, 570);
		pane.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		pane.getVerticalScrollBar().setUnitIncrement(14);
		f1.add(pane);
		
		text = new JTextField(); //Text fields to write the messages
		text.setBounds(5, 655, 320, 40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f1.add(text);
		
		text.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				//If the user presses a key while in the textField, change the label from "Active Now" to typing 
				label4.setText("typing...");
	               
				t.stop();
	               
				typing = true;
			}
	           
			public void keyReleased(KeyEvent ke){
				//When a key is released from the user while they are in the textField, start the timer
				typing = false;
	               
				if(!t.isRunning()){
					t.start();
				}
			}
		});
		
		button = new JButton("Send"); //Button to send the message
		button.setBounds(335, 655, 110, 40);
		button.setBackground(Color.green.darker());
		button.setForeground(Color.white);
		button.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		button.addActionListener(this);
		f1.add(button);
		
		f1.getContentPane().setBackground(Color.black);
		f1.setLayout(null);
		f1.setSize(450, 700);
		f1.setLocation(20, 200);
		f1.setUndecorated(true);
		f1.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		// TODO Auto-generated method stub
		try {
			String out = text.getText();
			if(!out.isEmpty()) {
				
				JPanel p2 = formatLabel(out);
				text.setText("");//Empty the text box
				
				area.setLayout(new BorderLayout());
				 
				JPanel right = new JPanel(new BorderLayout());
	            right.add(p2, BorderLayout.LINE_END);//So the messages appear at the right side of the screen
	            vertical.add(right);
	            vertical.add(Box.createVerticalStrut(15));//Add a small space between the messages
	            
	            area.add(vertical, BorderLayout.PAGE_START);
	            
	            f1.revalidate();

				dout.writeUTF(out);//Write the message at the dataOutputStream
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static JPanel formatLabel(String out){
		JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        //Make the label in which the messages will appear
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,10));
        
        //Add the time of when the message was send
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
		
		String msgin = "";
		try {
			skt = new ServerSocket(1000);//Give the socket a random number between 0 and 65535
			
			while(true){
				s = skt.accept();//Accept the connection with the client and make a new socket

				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				while(true){
					area.setLayout(new BorderLayout());   	
					
					msgin = din.readUTF();//msgin => the Client message
					
					JPanel p2 = formatLabel(msgin); //Make a new panel for the message
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START); //So the messages appear at the left side of the screen

                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15)); //Add a small space between the messages
                    area.add(vertical, BorderLayout.PAGE_START);
                    
    	            try {
    					Thread.sleep(1000);//Sleep for 1000 milliseconds => 1 second
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    	            //After a second passes, then show the message to the Client
                    f1.validate();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}