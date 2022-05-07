package gestion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dbmodels.DBUtils;
import espace_etudiant.Enseignant;
import model.Matiere;
import model.Note;
import model.NoteMatiere;

public class Classe {
	
	public ArrayList<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
	public ArrayList<Matiere> listMatiere1 = new ArrayList<Matiere>();
	public ArrayList<Matiere> listMatiere2 = new ArrayList<Matiere>();

	String nom;
	Integer idClasse;


	public Classe(String nom,Integer id){
		
		this.nom = nom;
		
		this.idClasse = id;

	}
	
	public Integer getId() {
		return idClasse;
	}

	
	@Override
    public String toString()
    {
         return nom;
    }
	
	void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		 return nom;
	}
	
	public void getListEtudiantFromDb() {
		
		listeEtudiant = Etudiant.getAllEtudiantsInClasse(idClasse);
	}
	

	public  static ArrayList<Classe> getAllClassesFromDb() {
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("SELECT idClasse,nomClasse FROM Classe ",null); 
			ArrayList<Classe> arr = new ArrayList<Classe>();
			if(prd != null) {
				System.out.println("moawjoud");
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					arr.add(new Classe(rs.getString("nomClasse"),Integer.parseInt(rs.getString("idClasse"))));
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
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("INSERT INTO Classe VALUES (null,?,1,2)",new Object[] {nom}); 

			if(prd != null) {
				int affected =  prd.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added classe succesfully");
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
	public Boolean removeFromDb()  {

		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("DELETE FROM Classe WHERE idClasse=?",new Object[] {idClasse}); 

			if(prd != null) {
				int removed =  prd.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("removed classe succesfully");
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
	public Boolean updateInDbTo(Classe newClasse)  {

		try {
			
			PreparedStatement prd = DBUtils.execute("UPDATE Classe SET nomClasse=? WHERE idClasse=? ",new Object[] {newClasse.nom,idClasse}); 

			if(prd != null) {
				int removed =  prd.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("updated classe succesfully");
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
}
