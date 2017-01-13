package chatHarry;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * @author WorkStation-Hackrry
 *
 */
public class Chat {
	public static void main(String []z){
		marco m = new marco("Chat Room");
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class marco extends JFrame implements Runnable{
	/**
	 * la version serial es 28
	 */
	private static final long serialVersionUID = 28;
	private JButton jbSend,jbQuit;
	private JPanel lamBotones,jp_bl_PantallaChat;
	private JTextField jtfChatEscribo;
	private JTextPane jtpChatLeer;
	private JScrollPane jspChatLeer;
	private JComboBox<String> jcbAmigosOnline = new JComboBox<String>();
	private Thread thread_EnviaMensaje;
	private String nombre;
	//------------para los eventos------------------
	private String textoCapturado_jtfChEs, contText= "";
	private byte l=0;
	private Mensaje mensajeEnviar;
	//----------------------------------------------

	private JPanel jpNorth;
	
	public marco(String title){
		super(title);
		//pedimos un nombre
		nombre = JOptionPane.showInputDialog("Dime tu Nombre");
		
		setLayout(new BorderLayout());
		setBounds(500,150,450,300);
		setResizable(false);
		
		// subproceso para recibir los mensajes
		thread_EnviaMensaje = new Thread(this);
		//lo iniciamos inmediatamente 
		thread_EnviaMensaje.start();
		
		jpNorth = new JPanel(new GridLayout(1,2,5,5));
		
		// indicamos algunas ip (en las maquinas que vamos a usar)
		jcbAmigosOnline.addItem("192.168.1.33");
		jcbAmigosOnline.addItem("192.168.1.34");
		jcbAmigosOnline.addItem("192.168.1.35");
		jcbAmigosOnline.addItem("192.168.1.36");
		jcbAmigosOnline.addItem("192.168.1.37");
		
		//nombre que va a identificar a un usuario
		jpNorth.add(new JLabel(":> "+nombre));
		
		jpNorth.add(jcbAmigosOnline);
		
		add(jpNorth,BorderLayout.NORTH);
		addDisplay();
		addBoton();
		setVisible(true);
	}
	
	/**
	 * Metodo que tendra que ver con los botones 
	 */
	private void addBoton(){
		lamBotones = new JPanel(new GridLayout(2,1,10,10));
		
		jbSend = new JButton("Send");
		jbQuit = new JButton("Quit");
		
		lamBotones.add(jbSend);
		lamBotones.add(jbQuit);
		
		jbSend.addActionListener(new AccionBotonesTeclados());
		jbQuit.addActionListener(new AccionBotonesTeclados());
		
		this.getContentPane().add(lamBotones,BorderLayout.EAST);
	}
	
	/**Aqui simplemente nos encargamos de 
	 * las pantralla de los mensajes de chat
	 */
	private void addDisplay(){
		jp_bl_PantallaChat = new JPanel(new BorderLayout());
		
		jtfChatEscribo = new JTextField();
		jtpChatLeer = new JTextPane();
		jspChatLeer = new JScrollPane(jtpChatLeer);
		
		jtfChatEscribo.addKeyListener(new AccionBotonesTeclados());
		jtfChatEscribo.setBackground(Color.YELLOW);
		
		jtpChatLeer.setEditable(false);
		jspChatLeer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		jp_bl_PantallaChat.add(jspChatLeer,BorderLayout.CENTER);
		jp_bl_PantallaChat.add(jtfChatEscribo,BorderLayout.SOUTH);
	
		add(jp_bl_PantallaChat,BorderLayout.CENTER);
		
	}
	
	private boolean checkTextVacio(String texto){
		if(texto == null || texto.equalsIgnoreCase("")){
			return false;
		}else{
			return true;
		}
	}
	
	private void concatena(boolean txtvacio){
		if(l<1 && txtvacio){
			contText = contText + textoCapturado_jtfChEs;
			jtpChatLeer.setText(" <] "+ contText +"\n");
			l++;
		}else if(l >= 1 && txtvacio){
			contText = contText +"\n <] "+ textoCapturado_jtfChEs;
			jtpChatLeer.setText(" <] "+ contText +"\n "); 
		}
		
		jtpChatLeer.setFont(new Font(Font.SERIF,Font.ITALIC,14));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socketChat;
		ServerSocket serverSChat;
		try {
			// bucle infinito para que siempre este recibiendo los mensajes
			for(;;){
				// iniciamos un ServerSocket con el puerto indicado para su escucha
				serverSChat = new ServerSocket(2882);
				// acceptamos esa conexion y se la pasamos a un Socket
				socketChat = serverSChat.accept();
				//creamos un flujo de entrada y al constructor
				ObjectInputStream oosRecibo = new ObjectInputStream(socketChat.getInputStream());
				Mensaje mensajeDestinatario = (Mensaje) oosRecibo.readObject();
				
				//cerramos todos los distintos flujos
				oosRecibo.close();
				socketChat.close();
				serverSChat.close();
				
				System.out.println(mensajeDestinatario.getNombre()+" : "+mensajeDestinatario.getMensaje());
				
				textoCapturado_jtfChEs = mensajeDestinatario.getNombre()+" : "+mensajeDestinatario.getMensaje();
				concatena(checkTextVacio(textoCapturado_jtfChEs));

			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**Un solo metodo para que luego sea llamado en el evento 
	 * de los botones y en el de el teclado(enter)
	 */
	private void unoSolo(){
		textoCapturado_jtfChEs = jtfChatEscribo.getText();
		jtfChatEscribo.setText("");	
		concatena(checkTextVacio(textoCapturado_jtfChEs));
		
		mensajeEnviar = new Mensaje(nombre,textoCapturado_jtfChEs,jcbAmigosOnline.getSelectedItem().toString());
		try {
			//se crea la conexion con la direccion ip y puerto
			//esto es para conectarnos con el servidor
			Socket socketChat = new Socket("192.168.1.34",2828);
			
			ObjectOutputStream oosEnvio = new ObjectOutputStream(socketChat.getOutputStream());
			//escribimos el siguiente objeto "mensajeEnviar" (sin comilla)
			//para enviarlo por el flujo OOS que usaremos con la conexion
			//ip y puerto que hemos establecidos
			oosEnvio.writeObject(mensajeEnviar);
			
			//siempre cerrar los flujos que han abierto
			oosEnvio.close();
			socketChat.close();
			
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	
	
	private class AccionBotonesTeclados extends KeyAdapter implements ActionListener{

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == 10){
				
				unoSolo();
				
			}else if(e.getKeyCode() == 27){
				System.exit(0);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == jbSend){
				unoSolo();
				
			}else if(e.getActionCommand().equalsIgnoreCase(jbQuit.getText())){
				System.exit(0);
			}
		}
		
	}
	
}
      

class Mensaje implements Serializable{
	
	private static final long serialVersionUID = 28;
	private String nombre, destinatario, mensaje;

	public Mensaje(String nombre, String text, String selectedItem) {
		// TODO Auto-generated constructor stub
		this.nombre = nombre;
		mensaje = text;
		destinatario = selectedItem;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
            