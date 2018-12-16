import java.util.*;
class Partida {
	public static final byte MAX_INTENTS=10;
	protected int id=-1;
	protected byte comptador=0;
	protected byte [] amagat=new byte[5];
	protected boolean facil=true;
	protected boolean acabat=false;
	protected ArrayList <Tirada> tirades=new ArrayList <Tirada>(5);
	
	public Partida(){omplirAmagatRandom();}
	
	public Partida(int id1,byte comptador1,byte[] amagat1,boolean facil1,ArrayList <Tirada> tirades1){
		id=id1;comptador=comptador1;amagat=amagat1;
		facil=facil1;tirades=tirades1;
	}
	
	public int getId(){
		return id;
	}
	
	public byte getComptador(){
		return comptador;
	}
	
	public byte[] getAmagat(){
		return amagat;
	}
	
	public boolean getDficultat(){
		return facil;
	}
	
	public boolean getAcabat(){
		return facil;
	}
	
	public Tirada getTirada(int num){
		return tirades.get(num);
	}
	
	public ArrayList<Tirada> getTirades(){
		return tirades;
	}
	
	public void setId(int identificador){
		id=identificador;
	}
	
	public void setAmagat(byte[] nouAmagat){
		amagat=nouAmagat;
	}
	
	public void setDificultat(boolean opcio){
		facil=opcio;
	}
	
	public byte newIdTirada(){
		Iterator it=tirades.iterator();
		int max=0;
		byte tmp=0;
		while(it.hasNext()){
			tmp=(byte)((Tirada)it.next()).getId();
			if(tmp>max) max=tmp;
		}
		max=max+1;
		return (byte)max;
		
	}
	
	public void novaTirada(Tirada novaTirada,boolean nova){
		if(nova) novaTirada.setId((byte)newIdTirada());
		tirades.add(novaTirada);
		comptador++;
	}
	
	public void omplirAmagatRandom(){
		Random rand = new Random();
		for(int i=0;i<amagat.length;i++){
				amagat[i]=(byte)(rand.nextDouble() * 10);
		}
	}
	
	public boolean guanyar(){
		boolean guanyat=false;
		Iterator it=tirades.iterator();
		while(it.hasNext()){
				Tirada tmptir=(Tirada)it.next();
				if(tmptir.getBp()==amagat.length)
					guanyat=true;
		}		
		acabat=guanyat;
		return guanyat;
	}

	
	public boolean tiradaRepetida(Tirada t1){
		return tirades.contains(t1);
	}
	
	
	public String historial(){
		String result=new String("");
		String aux=new String("");
		aux="Nombre \tBP\tMP\t";
		if(facil){
			aux=aux+"Control\n";
			for(int i=0;i<tirades.size();i++){
				aux=aux+tirades.get(i).toString()+'\t'+tirades.get(i).ajuda();
				aux=aux+"\n";
			}
		}else{
			aux=aux+'\n';
			for(int i=0;i<tirades.size();i++){
				aux=aux+tirades.get(i).toString();
				aux=aux+"\n";
			}
		
		}
		return aux;
	}
}


