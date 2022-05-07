package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dbmodels.DBUtils;
import gestion.Etudiant;

public class NoteMatiere {
	Integer id;
	private Matiere matiere;
	private Note notes;

	public NoteMatiere(Integer id,Matiere matiere, Note notes) {
		this.id = id;
		this.matiere = matiere;
		this.notes = notes;
	}
	public Integer getId()
	{
		return id;
	}
	public NoteMatiere(Integer id) {
		this.id = id;
	}
	public NoteMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public double moyenne() {
		return (notes.getExam() * matiere.getCoefExam() + notes.getTp() * matiere.getCoefTp()
				+ notes.getDs() * matiere.getCoefds());
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}

	/*@Override
	public String toString() {
		return "NoteMatiere [id = "+id.toString()+"  matiere=" + matiere.toString() + ", notes=" + notes.toString() + "]";
	}
	*/
	
	@Override
	public String toString() {
		return matiere.getNomMatiere();
	}

	public Boolean saveToDb(Etudiant ed) {
		
		try {
			int noteId = -1;
			// get note Id
			PreparedStatement prd = DBUtils.execute("INSERT INTO Notes VALUES (null,?,?,?)",new Object[] {notes.getExam(),notes.getDs(),notes.getTp()}); 

			if(prd != null) {
				Boolean success = prd.executeUpdate() == 1;
				if(!success) {
					System.out.println("Error while isnerting notes");
					return false;
				}
				ResultSet affected =  prd.getGeneratedKeys();
				if(affected.next()) {
					System.out.println("Inserted succefully ");
					noteId =  affected.getInt(1);
					System.out.println(noteId);
				}

			}else {
				System.out.println("null");
				return false;
			}
			
		   prd = DBUtils.execute("INSERT INTO NoteMatiere VALUES (null,?,?,?)",new Object[] {noteId,matiere.getId(),ed.getId()}); 
			if(prd != null) {
				Boolean success = prd.executeUpdate() == 1;
				if(!success) {
					System.out.println("Error while isnerting notes");
					return false;
				}else {
					System.out.println("Success Inserting note mateire");
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
			PreparedStatement prd = DBUtils.execute("DELETE FROM NoteMatiere WHERE idnote_Matiere = ?",new Object[] {id}); 
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
			
			prd = DBUtils.execute("DELETE FROM Notes WHERE idNote = ?",new Object[] {notes.getId()}); 
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
	public Boolean updateInDbTo(NoteMatiere nm1) {
		try {
			PreparedStatement prd = DBUtils.execute("UPDATE Notes SET ds=?,tp=?,exam=? WHERE idNote=? ",new Object[] {nm1.getNotes().getDs(),nm1.getNotes().getTp(),nm1.getNotes().getExam(),notes.getId()}); 
			System.out.println("MY Notes are  "+nm1.getNotes());
			if(prd != null) {
				
				Boolean success = prd.executeUpdate() == 1;
				if(!success) {
					System.out.println("Error while modifiying notes");
					return false;
				}else {
					System.out.println("Succefully executed the statement");
				}

			}else {
				System.out.println("Error while modifiying notes because of null prd");

				System.out.println("null");
				return false;
			}
			
		    prd = DBUtils.execute("UPDATE NoteMatiere SET idMatiere=?  WHERE idnote_Matiere=?",new Object[] {nm1.getMatiere().getId(),id}); 
			if(prd != null) {
				Boolean success = prd.executeUpdate() == 1;
				if(!success) {
					System.out.println("Error while modifiying notes ez");
					return false;
				}else {
					System.out.println("Succefully executed the statement");
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
