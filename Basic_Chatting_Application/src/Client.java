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

public class Client extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JPanel p1;
	JTextField text;
	JButton button;
	static JPanel area;
    static JFrame f1 = new JFrame();
    
    static Box vertical = Box.createVerticalBox();

	static Socket s;
	
	static DataInputStream din;
	static DataOutputStream dout;
	
	Boolean typing;
	
	public Client(){
		
		f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.blue);
		p1.setBounds(0, 0, 450, 70);
		f1.add(p1);
		
		Image img = new ImageIcon(this.getClass().getResource("/3.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		
		ImageIcon img1 = new ImageIcon(img);
		JLabel label = new JLabel(img1);
		label.setBounds(5, 17, 30, 30);
		p1.add(label);

		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});

		Image img2 = new ImageIcon(this.getClass().getResource("/male-modified.png")).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		
		ImageIcon img3 = new ImageIcon(img2);
		JLabel label2 = new JLabel(img3);
		label2.setBounds(40, 5, 60, 60);
		p1.add(label2);
		
		Image img4 = new ImageIcon(this.getClass().getResource("/video.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		
		ImageIcon img5 = new ImageIcon(img4);
		JLabel label5 = new JLabel(img5);
		label5.setBounds(290, 23,30, 30);
		p1.add(label5);
		
		Image img6 = new ImageIcon(this.getClass().getResource("/phone.png")).getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);

		ImageIcon img7 = new ImageIcon(img6);
		JLabel label6 = new JLabel(img7);
		label6.setBounds(350, 23, 35, 30);
		p1.add(label6);
		
		Image img8 = new ImageIcon(this.getClass().getResource("/3icon.png")).getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);

		ImageIcon img9 = new ImageIcon(img8);
		JLabel label7 = new JLabel(img9);
		label7.setBounds(410, 23, 13, 25);
		p1.add(label7);
		
		JLabel label3 = new JLabel("Name 2");
		label3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		label3.setForeground(Color.white);
		label3.setBounds(110, 17, 100, 20);
		p1.add(label3);
		
		JLabel label4 = new JLabel("Active Now");
		label4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		label4.setForeground(Color.white);
		label4.setBounds(110, 36, 100, 20);
		p1.add(label4);
		
		Timer t = new Timer(1, new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(!typing){
					label4.setText("Active Now");
				}
			}
	    });
	       
	    t.setInitialDelay(2000);
		
		area = new JPanel();
		area.setBounds(5, 75, 440, 570);
		area.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		
		JScrollPane pane = new JScrollPane(area);
		pane.setBounds(5, 75, 440, 570);
		pane.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		pane.getVerticalScrollBar().setUnitIncrement(14);
		f1.add(pane);
		
		text = new JTextField();
		text.setBounds(5, 655, 320, 40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f1.add(text);
		
		text.addKeyListener(new KeyAdapter(){
	        public void keyPressed(KeyEvent ke){
	        	label4.setText("typing...");
		               
		        t.stop();
		        typing = true;
	        }
		           
	        public void keyReleased(KeyEvent ke){
	        	typing = false;
		               
	        	if(!t.isRunning()){
	        		t.start();
	        	}
	        }
		});
		
		button = new JButton("Send");
		button.setBounds(335, 655, 110, 40);
		button.setBackground(Color.green.darker());
		button.setForeground(Color.white);
		button.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		button.addActionListener(this);
		f1.add(button);
		
		f1.getContentPane().setBackground(Color.black);
		f1.setLayout(null);
		f1.setSize(450, 700);
		f1.setLocation(1000, 200);
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
				text.setText("");
				
				area.setLayout(new BorderLayout());
				 
				JPanel right = new JPanel(new BorderLayout());
	            right.add(p2, BorderLayout.LINE_END);
	            vertical.add(right);
	            vertical.add(Box.createVerticalStrut(15));
	            
	            area.add(vertical, BorderLayout.PAGE_START);
	            f1.revalidate();
				
				dout.writeUTF(out);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JPanel formatLabel(String out){
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		        
		JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
		l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l1.setBackground(new Color(37, 211, 102));
		l1.setOpaque(true);
		l1.setBorder(new EmptyBorder(15,15,15,50));
		        
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
		new Client();
		
		try {
			s = new Socket("127.0.0.1", 1000); //Make a new connection with the server
			
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			String msgin = "";
			
			while(true){
				area.setLayout(new BorderLayout());
				
				msgin = din.readUTF();
				
				JPanel p2 = formatLabel(msgin);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                area.add(vertical, BorderLayout.PAGE_START);
                
	            try {
					Thread.sleep(1000);//Sleep for 1000 milliseconds => 1 second
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                //After a second passes, then show the message to the Server
                f1.validate();    
			}
		}catch(Exception e) {
			System.out.println("There is no Server to connect to");
			System.out.println("Please run the Server class");
			e.printStackTrace();
		}
	}
}