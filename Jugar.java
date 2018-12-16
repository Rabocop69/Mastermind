import java.util.*;
import java.io.*;
public class Jugar {
	public static void menu(){
		System.out.println("MASTERMIND");
		System.out.println("\nCompleta amb 5 nombres del 0-9");
	}
	
	public static byte[] stringToByteArray(String cad){
		byte[] entrada=new byte[5];
		
		for(int i=0;i<5;i++){
			entrada[i]=(byte)(cad.charAt(i)-48);
		}
		return entrada;
	}
	
	public static String espai(int resta){
		String cad="";
		for(int i=0;i<29+resta;i++) cad=cad+'\n';
		return cad;
	}
	public static boolean validar(String cad){
		if(cad.length()>5) return false;
		for(int i=0;i<5;i++) if(cad.charAt(i)<48 || cad.charAt(i)>57) return false;
		return true;
	}
	
	public static void main (String[] args) {
		Partida partida=new Partida();
		byte guanyar=0;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		String cad="";
		byte[] control=partida.getAmagat();
		
		for(int i=0;i<control.length;i++){
				cad=cad+control[i];
		}
		
		
		System.out.println(cad);
		String numCad="";
		System.out.println("MASTERMIND");
		System.out.println("Vols jugar amb mode facil?");
		try{
			numCad=br.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(numCad.equals("No") || numCad.equals("no")){ 
			partida.setDficultat(false);
		}
			
		for(int k=0;partida.getVides()!=0 && guanyar!=5;k++){
			
			guanyar=0;menu();
			
			try{
				numCad=br.readLine();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			if(validar(numCad)){
				Tirada tiradaNova = new Tirada(partida.getAmagat(),stringToByteArray(numCad));
				partida.novaTirada(tiradaNova);
				guanyar=tiradaNova.getBp();
				
				if(guanyar!=5){
					partida.restarVida();
					System.out.println(partida.historial());	
					System.out.println("Tens "+partida.getVides()+" vides restants");
					System.out.println(espai(partida.getVides()));
				}
				
			}else{
				System.out.println("VALOR INTRODUIT INCORRECTE");
			}
		}

			
		if(guanyar==5){
			System.out.println("Has guanyat");
		}
		
		if(partida.getVides()==0){
			System.out.println("Has perdut");
		}
		
		
	}
}

