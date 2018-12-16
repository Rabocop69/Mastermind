import java.util.*;
public class Tirada {
	protected byte id;
	protected byte[] entrada={0,0,0,0,0};
	protected byte[] control={0,0,0,0,0};
	protected byte bp=0;
	protected byte mp=0;
	
	public Tirada(byte[] amagats,byte[] nombres){
		for(int i=0;i<5;i++){
				entrada[i]=nombres[i];
		}
		comprobarPosicionats(amagats);
	}
	
	public Tirada(byte[] amagats,byte[] nombres,byte id1){
		id=id1;
		for(int i=0;i<5;i++){
				entrada[i]=nombres[i];
		}
		comprobarPosicionats(amagats);
	}
	
	public byte getId(){
		return id;
	}
	
	public byte[] getEntrada(){
		return entrada;
	}
	
	public byte[] getControl(){
		return control;
	}
	
	public byte getBp(){
		return bp;
	}
	
	public byte getMp(){
		return mp;
	}
	
	public void setId(byte id1){
		id=id1;
	}
	
	public void comprobarPosicionats(byte[] amagat){
		int i,l;
		boolean trobat=false;
		for(i=0;i<5;i++){
			if(amagat[i]==entrada[i]){
				control[i]=1;
				bp++;
			}
		}
				
		for(i=0;i<5;i++){
			if(control[i]!=1){
				for(l=0;l<5 && !trobat;l++){
					if(entrada[i]==amagat[l]){
						trobat=true;
						control[i]=2;
					}
				}
				trobat=false;
			}
		}
		
		for(i=0;i<5;i++) if(control[i]==2) mp++;
	}
	
	public boolean equals(Object obj){
		if(Arrays.equals(entrada,((Tirada)obj).getEntrada()) 
			&& Arrays.equals(control,((Tirada)obj).getControl())){
			return true;
		}
		return false;
	
	}
	
	public String ajuda(){
		String cad="";
		for(int i=0;i<control.length;i++){
				cad=cad+control[i];
		}
		return cad;
	}
	
	public String toString(){
		String cad="";
		for(int i=0;i<entrada.length;i++){
				cad=cad+entrada[i];
		}
		cad=cad+"\t"+bp+"\t"+mp;		
		return cad;
	}
	
}

