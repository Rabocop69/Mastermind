import java.sql.*;
import java.util.*;
public class BaseDades {
	private String url="jdbc:mysql://localhost:3306/";
	private	String user="mastermind";
	private	String pwd="Dequa16.";
	private Connection con=null;
	private	Statement st= null;
	
	public static void mostraSQLException(SQLException ex) {
		ex.printStackTrace(System.err);
		System.err.println("SQLState:"+ex.getSQLState());
		System.err.println("Error Code:"+ex.getErrorCode());
		System.err.println("Message:"+ex.getMessage());
		Throwable t=ex.getCause();
		while(t!=null) {
			System.out.println("Cause:"+t);
			t=t.getCause();
		}
	}
	
	public static byte[] stringToByteArray(String cad){
		byte[] entrada=new byte[5];
		
		for(int i=0;i<5;i++){
			entrada[i]=(byte)(cad.charAt(i)-48);
		}
		return entrada;
	}
	
	public static String byteArrayToString(byte[] array){
		String cad="";
		
		for(int i=0;i<array.length;i++){
			cad=cad+array[i];
		}
		return cad;
	}
	
	public boolean BDExists() {
		String cad0="use mastermind";	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,"root",pwd);
			st=con.createStatement();
			st.executeUpdate(cad0);
			
		} catch (ClassNotFoundException e) {
			return false;
		} catch(SQLException e) {
			return false;
		}
		return true;
	}
	
	public void createBD(){
		String cad1="drop database if exists mastermind";
		String cad2="create database mastermind";
		String cad3="use mastermind";
		String cad4="create table partida ( id integer not null primary key,comptador integer,amagat char(5),acabat bit)";
		String cad5="create table tirada ( id integer not null,id_partida integer not null,nombres char(5),control char(5),bp integer,mp integer)";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,"root",pwd);
			st= con.createStatement();
			st.executeUpdate(cad1);
			st.executeUpdate(cad2);
			st.executeUpdate(cad3);
			st.executeUpdate(cad4);
			st.executeUpdate(cad5);
			addUser("mastermind",pwd);
			grant("all","mastermind");

			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
	}
	
	public void grant(String permis, String usuari) {
		String cad0="use mastermind";
		String permiso="grant "+permis+" privileges on mastermind.* to '"+usuari+"'@'localhost'";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,"root",pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			st.executeUpdate(permiso);

			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
	}
	
	public void addUser(String usuari, String passw){
		String cad0="create user '"+usuari+"'@'localhost' identified by '"+passw+"'";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,"root",pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
	}
	
	public int nouId() {
		String cad0="use mastermind";
		String select="select max(id) from partida";
		ResultSet rs;
		int id=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			rs=st.executeQuery(select);
			if(rs.next()){
				id=((int)(rs.getInt("max(id)")))+1;
			}
			
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			if(!e.getMessage().equals("Column 'id' not found"))
				mostraSQLException(e);
		}
		return id;
	}
	
	public void addPartida(Partida p1) {
		String nombres=byteArrayToString(p1.getAmagat());
		String cad0="use mastermind";
		String insertar="insert into partida values ("+p1.getId()+","+
			p1.getComptador()+",'"+byteArrayToString(p1.getAmagat())+"',"+p1.getAcabat()+")";
		ArrayList <Tirada> tirades=p1.getTirades();
		Iterator it=tirades.iterator();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			st.executeUpdate(insertar);
			
			while(it.hasNext()){
				addTirada((Tirada)it.next(),p1.getId());
			}
			
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
	}
	
	public void addTirada(Tirada t1, int idp){
		String nombres=byteArrayToString(t1.getEntrada());
		String control=byteArrayToString(t1.getControl());
		String cad0="use mastermind";
		String insertar="insert into tirada values ("+t1.getId()+","+idp+",'"+nombres+"','"+
			control+"',"+t1.getBp()+","+t1.getMp()+")";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			st.executeUpdate(insertar);
			
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
	} 
	
	public ArrayList<Partida> llistarPartides(){
		String cad0="use mastermind";
		String cad1="select * from partida";
		ResultSet rs;
		ArrayList<Partida> partides=new ArrayList<Partida>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			rs=st.executeQuery(cad1);
			while(rs.next()){
				int id=(int)(rs.getInt("id"));
				int compt=(int)(rs.getInt("comptador"));
				String amagat=(String)(rs.getString("amagat"));
				boolean acabat=(boolean)(rs.getBoolean("acabat"));
				partides.add(new Partida(id,(byte)compt,stringToByteArray(amagat),
					acabat,cercarTirades(id,amagat)));
			}
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
		return partides;
	}
	
	public ArrayList<Tirada> cercarTirades(int idPartida,String nombresp){
		String cad0="use mastermind";
		String cad1="select * from tirada where id_partida="+idPartida;
		ResultSet rs;
		ArrayList<Tirada> tirades=new ArrayList<Tirada>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			rs=st.executeQuery(cad1);
			while(rs.next()){
				String entrada=(String)(rs.getString("nombres"));
				int id=(int)(rs.getInt("id"));
				tirades.add(new Tirada(stringToByteArray(nombresp),
					stringToByteArray(entrada),(byte)id));
			}
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
		return tirades;
	}
	
	public Partida cercarPartida(int id){
		String cad0="use mastermind";
		String cad1="select * from partida where id="+id;
		ResultSet rs;
		Partida partida=new Partida();
		ArrayList<Tirada> tirades;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			rs=st.executeQuery(cad1);
			if(rs.next()){
				int compt=(int)(rs.getInt("comptador"));
				String amagat=(String)(rs.getString("amagat"));
				boolean acabat=(boolean)(rs.getBoolean("acabat"));		
				tirades=cercarTirades(id,amagat);
				partida=new Partida(id,(byte)compt,stringToByteArray(amagat),
					acabat,tirades);
			}
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			return null;
		}
		return partida;
	}
	
	public void updatePartida(Partida p1) {
		String cad0="use mastermind";
		String acutalitzar= "update partida set comptador="+p1.getComptador()+
			",amagat='"+byteArrayToString(p1.getAmagat())+"',acabat="+p1.getAcabat()+" where id="+p1.getId();
		ArrayList<Tirada> tiradesp=p1.getTirades();
		ArrayList<Tirada> tiradesbd=cercarTirades(p1.getId(),byteArrayToString(p1.getAmagat()));
		Iterator it=tiradesp.iterator();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,user,pwd);
			st= con.createStatement();
			st.executeUpdate(cad0);
			st.executeUpdate(acutalitzar);
			
			while(it.hasNext()){
				Tirada tmptir=(Tirada)it.next();
				if(!tiradesbd.contains(tmptir)) addTirada(tmptir,p1.getId());
			}
			
			if(st!=null) st.close(); if(con!=null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			mostraSQLException(e);
		}
	}
	
}

