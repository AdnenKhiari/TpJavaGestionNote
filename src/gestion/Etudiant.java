package gestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbmodels.DBUtils;
import espace_etudiant.Enseignant;
import espace_etudiant.Utilisateur;
import model.Matiere;
import model.Note;
import model.NoteMatiere;

public class Etudiant extends Utilisateur {
	private String id;
	private String idClasse;
	private String cin;
	private String name;
	private String prenom;
	private ArrayList<NoteMatiere> notesS1 = new ArrayList<NoteMatiere>();
	private ArrayList<NoteMatiere> notesS2 = new ArrayList<NoteMatiere>();

	public Etudiant(String id, String cin, String name, ArrayList<NoteMatiere> notesS1,
			ArrayList<NoteMatiere> notesS2) {
		super();
		this.id = id;
		this.cin = cin;
		this.name = name;
		this.notesS1 = notesS1;	
		this.notesS2 = notesS2;
	}

	public Etudiant(String id) {
		this.id= id;
	}
	public Etudiant(String id, String cin, String name) {
		super();
		this.id = id;
		this.cin = cin;
		this.name = name;
	}

	public Etudiant(String id, String cin, String name,String prenom,String idClasse, String login, String pwd) {
		super(login, pwd);
		this.id = id;
		this.cin = cin;
		this.name = name;
		this.prenom = prenom;
		this.idClasse = idClasse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<NoteMatiere> getNotesS1() {
		return notesS1;
	}

	protected void setNotesS1(ArrayList<NoteMatiere> notesS1) {
		this.notesS1 = notesS1;
	}

	public ArrayList<NoteMatiere> getNotesS2() {
		return notesS2;
	}

	protected void setNotesS2(ArrayList<NoteMatiere> notesS2) {
		this.notesS2 = notesS2;
	}

	@Override
	public String toString() {
		return "Etudiant [id=" + id + ", cin=" + cin + ", name=" + name + "idClasse" + idClasse +  ", notesS1=" + notesS1.toString()
				+ ", notesS2=" + notesS2.toString() + "]";
		
	}

	private double moyenneSemestre(ArrayList<NoteMatiere> notesSemestre) {
		double sum = 0.0;
		double totalCoef = 0.0;
		for (NoteMatiere notes : notesSemestre) {
			sum += notes.moyenne() * notes.getMatiere().getCoefMatiere();
			totalCoef += notes.getMatiere().getCoefMatiere();
		}
		return Math.round(sum / totalCoef);

	}

	public double moyenneS1() {
		return moyenneSemestre(notesS1);
	}

	public double moyenneS2() {
		return moyenneSemestre(notesS2);
	}

	public double moyenne() {
		return (moyenneS1() + moyenneS2()) / 2;
	}
	
	public void getNotesFromDb(int semestre) {
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("SELECT nm.idnote_matiere,nm.idMatiere, m.MatiereName, n.idNote, n.ds, n.tp, n.exam FROM NoteMatiere nm, Matiere m, Notes n, Etudiant e WHERE e.idEtudiant=nm.idEtudiant AND nm.idMatiere=m.idMatiere AND nm.idNote=n.idNote AND e.idEtudiant=? AND m.idSemestre=? ",new Object [] {id,semestre}); 
			if(prd != null) {
				
				if(semestre == 1)
					notesS1 = new ArrayList<NoteMatiere>();
				else 
					notesS2 = new ArrayList<NoteMatiere>();

				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					Note nt = new Note(rs.getInt(4),rs.getDouble(5),rs.getDouble(6),rs.getDouble(7));
					Matiere m = new Matiere( Integer.parseInt(rs.getString(2)),rs.getString(3));
					NoteMatiere nm = new NoteMatiere(Integer.parseInt(rs.getString(1)),m, nt);
					
					if(semestre == 1)
						notesS1.add(nm);
					else
						notesS2.add(nm);
				}
			}else {
				System.out.println("null");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Etudiant getEtudiantById(int etudid) {
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("SELECT e.idEtudiant,e.nom,e.prenom,e.cin,e.idClasse,u.login,u.pwd  FROM  Etudiant e,User u WHERE e.idEtudiant=? AND u.idRef=e.idEtudiant AND u.userType=1 ",new Object [] {etudid}); 

			if(prd != null) {
				System.out.println("moawjoud");
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					return new Etudiant(rs.getString(1),rs.getString(4),rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(7));
				}
			}else {
				System.out.println("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;

			}
		return null;
	}
	
	public static ArrayList<Etudiant> getAllEtudiantsInClasse(int idClasse){
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("SELECT e.idEtudiant,e.nom,e.prenom,e.cin,e.idClasse,u.login,u.pwd  FROM  Etudiant e,User u WHERE e.idClasse=? AND u.idRef=e.idEtudiant AND u.userType=1 ",new Object [] {idClasse}); 
			ArrayList<Etudiant> arr = new ArrayList<Etudiant>();
			if(prd != null) {
				System.out.println("moawjoud");
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					arr.add(new Etudiant(rs.getString(1),rs.getString(4),rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(7)));
				}
				System.out.println(arr);
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
			PreparedStatement prd = DBUtils.execute("INSERT INTO Etudiant VALUES (?,?,?,?,?)",new Object[] {id,name,prenom,cin,idClasse}); 
			PreparedStatement prdUser = DBUtils.execute("INSERT INTO User VALUES (null,?,?,?,1)",new Object[] {getLogin(),getMotDePasse(),id}); 

			if(prd != null) {
				int affected =  prd.executeUpdate();
				System.out.println(affected);
				if(affected == 1) {
					System.out.println("added etudiant succesfully");
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
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("DELETE FROM Etudiant WHERE idEtudiant = ?",new Object[] {id}); 
			if(prd != null) {
				int removed =  prd.executeUpdate();
				System.out.println(removed);
				if(removed == 1) {
					System.out.println("Removed Etudiant succesfully");
				}else {
					return false;
				}
			}else {
				System.out.println("null");
				return false;

			}
			
			PreparedStatement prdUser = DBUtils.execute("DELETE FROM User WHERE idRef = ? AND userType=1 ",new Object[] {id}); 
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

	public Boolean updateInDbTo(Etudiant newe) {
		try {
			// TODO Auto-generated method stub
			PreparedStatement prd = DBUtils.execute("UPDATE Etudiant SET idEtudiant=?,nom=?,prenom=?,cin=?,idClasse=? WHERE idEtudiant = ?",new Object[] {newe.id,newe.name,newe.prenom,newe.cin,newe.idClasse,id}); 
			PreparedStatement prdUser = DBUtils.execute("UPDATE User SET Login=?,Pwd=?,idRef=? WHERE idRef = ? AND userType=1",new Object[] {newe.getLogin(),newe.getMotDePasse(),newe.id,id}); 

			if(prd != null) {
				int updated =  prd.executeUpdate();
				System.out.println(updated);
				if(updated == 1) {
					System.out.println("updated Etudiant succesfully");
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
	

	public String getPrenom() {
		// TODO Auto-generated method stub
		return prenom;
	}

	public String getClasse() {
		// TODO Auto-generated method stub
		return idClasse;
	}


}
