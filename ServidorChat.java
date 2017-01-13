package chatHarry;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author WorkStation-Hackrry
 *
 */
public class ServidorChat extends JFrame implements Runnable, ActionListener{

	private static final long serialVersionUID = 28;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServidorChat p = new ServidorChat("Servidor Chat");
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JTextArea textoDelChat = new JTextArea();
	private Thread hilito;
	private JScrollPane jsptextchat;
	private JTextField jtfPort;
	private JButton jbPort;
	private ServerSocket serverS_SC;
	
	public ServidorChat(String title){
		super(title);
		setBounds(200,300,360,200);
		hilito = new Thread(this);
		hilito.start();
		
		verPuerto();
		textoDelChat.setEditable(false);
		jsptextchat = new JScrollPane(textoDelChat);
		add(jsptextchat,BorderLayout.CENTER);
		setVisible(true);
	}
	private void verPuerto(){
		JPanel jpNorth = new JPanel();
		jtfPort = new JTextField(4);
		
		jtfPort.setEditable(false);
		
		jbPort = new JButton("Ver Puerto");
		
		jbPort.setEnabled(false);
		
		jbPort.addActionListener(this);
		
		jpNorth.add(jbPort);
		jpNorth.add(jtfPort); 
		
		add(jpNorth,BorderLayout.NORTH);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			

			Mensaje mensajeRecibido;
			
			while(true){
				serverS_SC = new ServerSocket(2828);
				Socket socket_SC = serverS_SC.accept();
				
				ObjectInputStream oisRecibiendo = new ObjectInputStream(socket_SC.getInputStream());

				mensajeRecibido = (Mensaje) oisRecibiendo.readObject();

				oisRecibiendo.close();
				socket_SC.close();
				serverS_SC.close();
				
				jbPort.setEnabled(true);
				System.out.println("Para: "+mensajeRecibido.getDestinatario());
				
				Socket socketReenvio = new Socket(mensajeRecibido.getDestinatario(),2882);
				
				ObjectOutputStream oosReenvio = new ObjectOutputStream(socketReenvio.getOutputStream());
				oosReenvio.writeObject(mensajeRecibido);
				oosReenvio.close();
				
				socketReenvio.close();
				
				String l = "("+socket_SC.getInetAddress().getHostAddress() + ")" +
				mensajeRecibido.getNombre()+" para "+socketReenvio.getInetAddress().getHostAddress() +
				" : "+mensajeRecibido.getMensaje();
				
				textoDelChat.setText( textoDelChat.getText()+"\n" +l);
				
				socket_SC.close();
				serverS_SC.close();
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		
		jtfPort.setText(""+serverS_SC.getLocalPort());
		
	}
	
	
	
}
