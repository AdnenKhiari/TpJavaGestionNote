package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dbmodels.DBUtils;
import gestion.Etudiant;

public class NoteMatiere {
	private Matiere matiere;
	private Note notes;

	public NoteMatiere(Matiere matiere, Note notes) {
		this.matiere = matiere;
		this.notes = notes;
		
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

	@Override
	public String toString() {
		return "NoteMatiere [matiere=" + matiere.toString() + ", notes=" + notes.toString() + "]";
	}

	public static void main(String[] args) {

	}

	public void saveToDb(Etudiant ed) {
		
		try {
			int noteId = -1;
			// get note Id
			PreparedStatement prd = DBUtils.execute("INSERT INTO Notes VALUES (null,?,?,?)",new Object[] {notes.getExam(),notes.getDs(),notes.getTp()}); 

			if(prd != null) {
				Boolean success = prd.executeUpdate() == 1;
				if(!success) {
					System.out.println("Error while isnerting notes");
					return;
				}
				ResultSet affected =  prd.getGeneratedKeys();
				if(affected.next()) {
					System.out.println("Inserted succefully ");
					noteId =  affected.getInt(1);
					System.out.println(noteId);
				}

			}else {
				System.out.println("null");
			}
			
		   prd = DBUtils.execute("INSERT INTO NoteMatiere VALUES (null,?,?,?)",new Object[] {noteId,matiere.getId(),ed.getId()}); 
			if(prd != null) {
				Boolean success = prd.executeUpdate() == 1;
				if(!success) {
					System.out.println("Error while isnerting notes");
					return;
				}else {
					System.out.println("Success Inserting note mateire");
				}

			}else {
				System.out.println("null");
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
	}

}
