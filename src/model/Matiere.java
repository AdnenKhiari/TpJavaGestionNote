package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbmodels.DBUtils;
import espace_etudiant.Enseignant;
import gestion.Classe;

public class Matiere {

	private int id;
	private int IdSemestre;
	private int idEnseignant;
	private String nomMatiere;
	private double coefExam;
	private double coefDs;
	private double coefTp;
	private double coefMatiere;

	public Matiere(double coefExam, double coefds, double coefTp, double coefMatiere, String nomMatiere,Integer id,Integer idEnseignant,Integer idSem) {
		this.IdSemestre=idSem;
		this.idEnseignant = idEnseignant;
		this.id = id;
		this.coefExam = coefExam;
		this.coefDs = coefds;
		this.coefTp = coefTp;
		this.coefMatiere = coefMatiere;
		this.nomMatiere = nomMatiere;
	}

	public Matiere(int id) {
		this.id=id;
	}
	
	public Matiere(String nmname) {
		this.nomMatiere=nmname;
	}
	
	public Matiere(int id,String nmname) {
		this.nomMatiere=nmname;
		this.id=id;
	}

	public double getCoefExam() {
		return coefExam;
	}

	public void setCoefExam(double coefExam) {
		this.coefExam = coefExam;
	}

	public double getCoefds() {
		return coefDs;
	}

	public void setCoefds(double coefds) {
		this.coefDs = coefds;
	}

	public double getCoefTp() {
		return coefTp;
	}

	public void setCoefTp(double coefTp) {
		this.coefTp = coefTp;
	}

	public double getCoefMatiere() {
		return coefMatiere;
	}

	public void setCoefMatiere(double coefMatiere) {
		this.coefMatiere = coefMatiere;
	}

	public String getNomMatiere() {
		return nomMatiere;
	}

	public void setNomMatiere(String nomMatiere) {
		this.nomMatiere = nomMatiere;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCoefDs() {
		return coefDs;
	}

	public void setCoefDs(double coefDs) {
		this.coefDs = coefDs;
	}

	@Override
	public String toString() {
		return "Matiere id = "+Integer.toString(id)+"[coefExam=" + coefExam + ", coefDs=" + coefDs + ", coefTp=" + coefTp + ", coefMatiere="
				+ coefMatiere + ", nomMatiere=" + nomMatiere + "]";
	}

	public static ArrayList<Matiere> getAllMatieresFromDb() {
		try {
			PreparedStatement prd = DBUtils.execute("SELECT coefExam,coefDS,coefTP,coefMatiere,MatiereName,idMatiere,idEnseignant,IdSemestre FROM  Matiere",null); 
			ArrayList<Matiere> arr = new ArrayList<Matiere>();
			if(prd != null) {
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					arr.add(new Matiere(rs.getDouble(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4), rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)));
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
			PreparedStatement prd = DBUtils.execute("INSERT INTO Matiere VALUES (null,?,?,?,?,?,?,?)",new Object[] {nomMatiere,coefDs,coefExam,coefTp,coefMatiere,IdSemestre,idEnseignant}); 

			if(prd != null) {
				int affected =  prd.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added Matiere succesfully");
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
			PreparedStatement prd = DBUtils.execute("DELETE FROM Matiere WHERE idMatiere = ?",new Object[] {id}); 
			if(prd != null) {
				int removed =  prd.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("Removed Matiere succesfully");
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
	
	public String getSemestre() {
		if(IdSemestre == 1)
			return "Semestre 1";
		else
			return "Semestre 2";
	}

	public int getIdEnseignant() {
		return idEnseignant;
	}

	public Boolean updateInDbTo(Matiere newe) {
		try {
			PreparedStatement prd = DBUtils.execute("UPDATE Matiere SET idMatiere=?,MatiereName=?,coefDS=?,coefExam=?,coefTP=?,coefMatiere=?,idSemestre=?,idEnseignant=? WHERE idMatiere = ?",new Object[] {newe.id,newe.nomMatiere,newe.coefDs,newe.coefExam,newe.coefTp,newe.coefMatiere,newe.IdSemestre,newe.idEnseignant,id}); 

			if(prd != null) {
				int updated =  prd.executeUpdate();
				System.out.println(updated);
				if(updated == 1) {
					System.out.println("updated Matiere succesfully");
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
