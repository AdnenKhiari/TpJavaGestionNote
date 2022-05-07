package espace_etudiant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dbmodels.DBUtils;
import gestion.Classe;
import gestion.Etudiant;

public class Enseignant extends Utilisateur {
	
	String name;
	String CIN;
	Integer idEnseignant;
	
	public Enseignant (String username,String pass,String name, String CIN,Integer id) {
		super();
		this.name = name;
		this.CIN = CIN;
		idEnseignant = id;
		setLogin(username);
		setMotDePasse(pass);
		
	}

	public String getNom() {
		// TODO Auto-generated method stub
		return name;
	}
	public String getCIN() {
		// TODO Auto-generated method stub
		return CIN;
	}
	
	public static ArrayList<Enseignant> getEns(){
		try {	
			PreparedStatement prd = DBUtils.execute("SELECT e.nom,e.cin,e.idEnseignant,u.Login,u.Pwd  FROM  Enseignant as e ,User as u WHERE u.idRef=e.idEnseignant AND u.userType=2 ",null); 
			ArrayList<Enseignant> arr = new ArrayList<Enseignant>();
			if(prd != null) {
				System.out.println("moawjoud");
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					arr.add(new Enseignant(rs.getString("u.Login"), rs.getString("u.Pwd"), rs.getString("e.nom"), rs.getString("e.cin"),rs.getInt("e.idEnseignant")));
				}
				return arr;
			}else {
				System.out.println("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
		return null;
	}

	public Boolean saveToDb()  {

		try {
			PreparedStatement prd = DBUtils.execute("INSERT INTO Enseignant VALUES (?,?,?)",new Object[] {idEnseignant,name,CIN}); 
			PreparedStatement prdUser = DBUtils.execute("INSERT INTO User VALUES (null,?,?,?,2)",new Object[] {getLogin(),getMotDePasse(),idEnseignant}); 

			if(prd != null) {
				int affected =  prd.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added enseignant succesfully");
				}else {
					return false;
				}
			}else {	
				System.out.println("null");
				return false;
			}
			

			if(prd != null) {
				int affected =  prdUser.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added User succesfully");
				}else {
					return false;

				}
			}else {		
				System.out.println("null");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();	
			return false;

		}
		return true;

	}

	public Boolean removeFromDb() {		
		try {
			PreparedStatement prd = DBUtils.execute("DELETE FROM Enseignant WHERE idEnseignant = ?",new Object[] {idEnseignant}); 
			if(prd != null) {
				int removed =  prd.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("Removed succesfully");
				}else {
					return false;

				}
			}else {
				System.out.println("null");
				return false;

			}
			
			PreparedStatement prdUser = DBUtils.execute("DELETE FROM User WHERE idRef = ? AND userType=2 ",new Object[] {idEnseignant}); 
			if(prd != null) {
				int removed =  prdUser.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("Removed User succesfully");
				}else {
					return false;

				}
			}else {
				System.out.println("null");
				return false;

			}
			
		} catch (Exception e) {
			e.printStackTrace();	
			return false;

		}
		return true;

	}

	public String getId() {
		return idEnseignant.toString();
	}

	public Boolean updateInDbTo(Enseignant updatedens) {
		try {
			PreparedStatement prd = DBUtils.execute("UPDATE Enseignant SET idEnseignant=?,nom=?,cin=? WHERE idEnseignant = ?",new Object[] {updatedens.idEnseignant,updatedens.name,updatedens.CIN,idEnseignant}); 
			PreparedStatement prdUser = DBUtils.execute("UPDATE User SET Login=?,Pwd=?,idRef=? WHERE idRef = ? AND userType=2",new Object[] {updatedens.getLogin(),updatedens.getMotDePasse(),updatedens.idEnseignant,idEnseignant}); 

			if(prd != null) {
				int updated =  prd.executeUpdate();
				System.out.println(updated);
				if(updated == 1) {
					System.out.println("updated succesfully");
				}else {
					return false;

				}
				
			}else {
				System.out.println("null");
				return false;

			}
			if(prdUser != null) {
				int updated =  prdUser.executeUpdate();
				System.out.println(updated);
				if(updated == 1) {
					System.out.println("updated user succesfully");
				}	else {
					return false;

				}
			}else {
				System.out.println("null");
				return false;

			}
		} catch (Exception e) {
			e.printStackTrace();	
			return false;

		}
		return true;

	}

	public static Enseignant getEnseignantById(int idRef) {
		try {
			PreparedStatement prd = DBUtils.execute("SELECT e.idEnseignant,e.nom,e.cin,u.Login,u.Pwd FROM  Enseignant e,User u WHERE idEnseignant=? AND idEnseignant=u.idRef AND u.usertype=2 ",new Object [] {idRef}); 
			if(prd != null) {
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					return new Enseignant(rs.getString(4),rs.getString(5),rs.getString(2),rs.getString(3),rs.getInt(1));
				}
			}else {
				System.out.println("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
			return null;
	}
}
