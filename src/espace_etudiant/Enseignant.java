package espace_etudiant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dbmodels.DBUtils;
import gestion.Classe;

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
			// TODO Auto-generated method stub
			
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

	public void saveToDb()  {

		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("INSERT INTO Enseignant VALUES (?,?,?)",new Object[] {idEnseignant,name,CIN}); 
			PreparedStatement prdUser = DBUtils.execute("INSERT INTO User VALUES (null,?,?,?,2)",new Object[] {getLogin(),getMotDePasse(),idEnseignant}); 

			if(prd != null) {
				int affected =  prd.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added enseignant succesfully");
				}
			}else {
				System.out.println("null");
			}
			

			if(prd != null) {
				int affected =  prdUser.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added User succesfully");
				}
			}else {
				System.out.println("null");
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}

	public void removeFromDb() {		
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("DELETE FROM Enseignant WHERE idEnseignant = ?",new Object[] {idEnseignant}); 
			if(prd != null) {
				int removed =  prd.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("Removed succesfully");
				}
			}else {
				System.out.println("null");
			}
			
			PreparedStatement prdUser = DBUtils.execute("DELETE FROM User WHERE idRef = ? AND userType=2 ",new Object[] {idEnseignant}); 
			if(prd != null) {
				int removed =  prdUser.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("Removed User succesfully");
				}
			}else {
				System.out.println("null");
			}
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}

	public String getId() {
		// TODO Auto-generated method stub
		return idEnseignant.toString();
	}

	public void updateInDbTo(Enseignant updatedens) {
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("UPDATE Enseignant SET idEnseignant=?,nom=?,cin=? WHERE idEnseignant = ?",new Object[] {updatedens.idEnseignant,updatedens.name,updatedens.CIN,idEnseignant}); 
			PreparedStatement prdUser = DBUtils.execute("UPDATE User SET Login=?,Pwd=?,idRef=? WHERE idRef = ? AND userType=2",new Object[] {updatedens.getLogin(),updatedens.getMotDePasse(),updatedens.idEnseignant,idEnseignant}); 

			if(prd != null) {
				int updated =  prd.executeUpdate();
				System.out.println(updated);
				if(updated == 1) {
					System.out.println("updated succesfully");
				}
				
			}else {
				System.out.println("null");
			}
			
			if(prdUser != null) {
				int updated =  prdUser.executeUpdate();
				System.out.println(updated);
				if(updated == 1) {
					System.out.println("updated user succesfully");
				}
				
			}else {
				System.out.println("null");
			}
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
}
