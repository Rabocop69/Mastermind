import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
public class Inicio {
	public static void main (String[] args) {
		new Menu();
	}
}

class MiPanel extends javax.swing.JPanel { 
	String imagen = ""; 

	public MiPanel(String imagen){ 
		this.imagen = imagen; 
		//this.setSize(500,500); 
} 

	public void paint(Graphics g){ 
		Dimension tamano = getSize(); 
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imatges/" + imagen)); 
		g.drawImage(imagenFondo.getImage(),0,0,tamano.width, tamano.height, null); 
		setOpaque(false); 
		super.paintComponent(g); 
	} 
}

class Menu{
	JFrame frame=new JFrame("MasterMind");
	BaseDades dades=new BaseDades();
	public Menu(){

		JButton botonova=new JButton("Nova partida");
		JButton botocar=new JButton("Carregar partida");
		
		frame.setSize(300,400);
		
		MiPanel pan=new MiPanel("MASTERMIND.jpg");
		Box box1 = Box.createHorizontalBox();
		box1.add(Box.createHorizontalStrut(15));
		box1.add(pan , BorderLayout.CENTER);
		box1.add(Box.createHorizontalStrut(15));
		
		Box box2 = Box.createHorizontalBox();
		box2.add(botonova);
		box2.add(Box.createHorizontalStrut(8));
		box2.add(botocar);
		
		JPanel pancentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pancentre.add(box2);
	
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(15));
		box.add(box1); 
		pan.repaint();
		box.add(Box.createVerticalStrut(20));	
		box.add(pancentre);

		JPanel panell=(JPanel)frame.getContentPane();
		panell.add(box);
		botonova.addActionListener(new B0ActListener());
		botocar.addActionListener(new B1ActListener());
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new BWinListener());
	}
	
	private class B0ActListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (dades.BDExists()) {
				new NovaPartida();
			} else  {
				dades.createBD();
				new NovaPartida();
			}
		}
	}
	
	private class B1ActListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (dades.BDExists()) {
				new CarregarPartides();
			} else  {
				dades.createBD();
				new CarregarPartides();
			}
		}
	}
	
	private class BWinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);
		}
	}
}

class CarregarPartides{
	JFrame frame=new JFrame("Carregar Partides");
	BaseDades dades=new BaseDades();
	ArrayList<Partida> partides=dades.llistarPartides();;
	JLabel lpartida=new JLabel("ID Partida: ");
	JTextField tpartida=new JTextField("",3);
	JButton botocarregar=new JButton("Carregar partida");
	JTextArea resultat=new JTextArea("");
	
	public CarregarPartides(){
		
		frame.setSize(400,500);
		
		JLabel titol=new JLabel("Partides Carregades");
		titol.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
		JPanel pantitol = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pantitol.add(titol);
		
		Box boxcercar = Box.createHorizontalBox();		
		boxcercar.add(Box.createHorizontalStrut(8));
		boxcercar.add(lpartida);
		boxcercar.add(Box.createHorizontalStrut(8));
		boxcercar.add(tpartida);
		boxcercar.add(Box.createHorizontalStrut(8));
		boxcercar.add(botocarregar);
		
		JPanel pancercar = new JPanel(new FlowLayout(FlowLayout.CENTER));	
		pancercar.add(boxcercar);
		
		resultat.setEditable(false);
		resultat.append("ID\tIntents\tAcabada\n");
		
		Box boxresult=Box.createHorizontalBox(); 
		boxresult.add(Box.createHorizontalStrut(15));
		boxresult.add(resultat);
		boxresult.add(Box.createHorizontalStrut(15));
		
        Iterator it= partides.iterator();
        String cadena;
        while(it.hasNext()){
			Partida tmp=(Partida)it.next();
			cadena =new String();
			cadena=tmp.getId()+"\t"+tmp.getComptador()+"\t";
						
			if(tmp.guanyar()) cadena=cadena+"Si\n";
			else cadena=cadena+"No\n";
			
			resultat.append(cadena);
		}
        	
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(5));
		box.add(pantitol);
		box.add(Box.createVerticalStrut(8));
		box.add(pancercar);
		box.add(Box.createVerticalStrut(8));
		box.add(boxresult);
		box.add(Box.createVerticalStrut(15));
		
		JPanel panell=(JPanel)frame.getContentPane();
		panell.add(box);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		botocarregar.addActionListener(new B1ActListener());
		frame.addWindowListener(new BWinListener());
	}
	
	private class B1ActListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String cadena=tpartida.getText();
			Partida tmp=dades.cercarPartida(Integer.parseInt(cadena));
			if(tmp.equals(null)){
				JOptionPane.showMessageDialog(null, "Aquesta partida no existeix");
			}else {
				JOptionPane.showMessageDialog(null, "Partida carregada correctament");
				frame.setVisible(false);
				new NovaPartida(tmp);
			}
		}
	}
	
	private class BWinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			frame.setVisible(false);
		}
	}
}

class NovaPartida{
	
	JFrame frame=new JFrame("MASTERMIND");
	JMenuBar barraMenu=new JMenuBar();;
	JMenuItem mitemCarregar;
    JMenu menuOp=new JMenu("Opcions");
    JCheckBoxMenuItem mitemFacil;
	JLabel labelTirada=new JLabel("Tirada: ");
	JTextField textTirada=new JTextField("",4);
	JButton jugar=new JButton("Jugar");
	JTextArea resultat=new JTextArea("");
	JLabel labelIntents=new JLabel("Intets fets: ");
	JTextField textIntents=new JTextField("",4);
	BaseDades dades=new BaseDades();
	Partida partidaActual;
	
	
	public NovaPartida(){
		partidaActual=new Partida();
		carregar();
	}
	
	public NovaPartida(Partida partida){
		partidaActual=partida;
		carregar();
		
		Iterator it= partidaActual.getTirades().iterator();
        String cadena="";
        while(it.hasNext()){
			Tirada tmp=(Tirada)it.next();
			
			cadena=cadena+tmp.toString();
			if(partidaActual.getDficultat()) 
				cadena=cadena+"\t"+tmp.ajuda();
			cadena=cadena+"\n";		
		}
		resultat.append(cadena);
		
	}
	
	private void carregar(){
		frame.setSize(400,360);
		frame.setJMenuBar(barraMenu);
		
		mitemCarregar= new JMenuItem("Canviar Partida");
		menuOp.add(mitemCarregar);
		
		mitemFacil= new JCheckBoxMenuItem("Facil");
		menuOp.add(mitemFacil);
		
		barraMenu.add(mitemFacil);
		
		JLabel titol=new JLabel("MasterMind");
		JPanel pantitol = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pantitol.add(titol);
		
		Box boxtirada=Box.createHorizontalBox(); 
		boxtirada.add(Box.createHorizontalStrut(9));
		boxtirada.add(labelTirada);
		boxtirada.add(Box.createHorizontalStrut(9));
		boxtirada.add(textTirada);
		boxtirada.add(Box.createHorizontalStrut(9));
		boxtirada.add(jugar);
		boxtirada.add(Box.createHorizontalStrut(5));
		
		JPanel pancentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pancentre.add(boxtirada);
		
		resultat.setEditable(false);
		resultat.append("Introduits\tBP\tMP\tPista\n");
		
		Box boxresult=Box.createHorizontalBox(); 
		boxresult.add(Box.createHorizontalStrut(15));
		boxresult.add(resultat);
		boxresult.add(Box.createHorizontalStrut(15));
		
		textIntents.setEditable(false);
		Box boxintents=Box.createHorizontalBox(); 
		
		boxintents.add(labelIntents);		
		boxintents.add(Box.createHorizontalStrut(9));
		boxintents.add(textIntents);
		boxintents.add(Box.createHorizontalStrut(9));
		
		JPanel panintents = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panintents.add(boxintents);
	
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(5));
		box.add(pantitol);
		box.add(pancentre);
		box.add(Box.createVerticalStrut(5));
		box.add(boxresult);
		box.add(Box.createVerticalStrut(5));
		box.add(panintents);
		box.add(Box.createVerticalStrut(15));

		JPanel panell=(JPanel)frame.getContentPane();
		panell.add(box);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
			
		jugar.addActionListener(new B1ActListener());
		mitemCarregar.addActionListener(new M1ActListener());
		frame.addWindowListener(new BWinListener());
	}
	
	private void guardar(){
		if(partidaActual.getId()==-1){
			partidaActual.setId(dades.nouId());
			dades.addPartida(partidaActual);
		}else{
			dades.updatePartida(partidaActual);
		}
	}
	

	private class M1ActListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				new CarregarPartides();
				frame.setVisible(false);
			}
	}

	
	private class B1ActListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String entrada=textTirada.getText();
			Tirada tiradatmp;
			if(textIntents.getText().equals(Partida.MAX_INTENTS+"")){
				JOptionPane.showMessageDialog(null,"Has arribat al nombre maxim d'intents",
					"Partida finalitzada!",JOptionPane.WARNING_MESSAGE);

			}else{
				if (entrada.length()!=5) {
					JOptionPane.showMessageDialog(null, "Esta tirada no es correcta");
				} else  {
					if(mitemFacil.getState() == true)
						partidaActual.setDificultat(true);
					 else
						partidaActual.setDificultat(false);
					
					tiradatmp=new Tirada(partidaActual.getAmagat(),
					BaseDades.stringToByteArray(entrada));
					partidaActual.novaTirada(tiradatmp,true);
					resultat.append(tiradatmp.toString());
					
					if(partidaActual.getDficultat()) 
						resultat.append("\t"+tiradatmp.ajuda());
						resultat.append("\n");
						textTirada.setText("");
						textIntents.setText(partidaActual.getComptador()+"");
						
					if(partidaActual.guanyar()){
						Icon icono = new ImageIcon(getClass().getResource("imatges/felicidades.gif"));
						JOptionPane.showMessageDialog(null,
						"","HAS GUANYAT!!",
						JOptionPane.DEFAULT_OPTION, icono);
					}
					
					guardar();
				}
			}
		}
	}
	
		
	private class BWinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			frame.setVisible(false);
			Icon icono = new ImageIcon(getClass().getResource("imatges/check.png"));
			JOptionPane.showMessageDialog(null,
				"S'ha guardat correctament la partida\n El ID d'aquesta partida es "+partidaActual.getId(),
				"Partida guardada",JOptionPane.DEFAULT_OPTION, icono);
		}
	}
}



