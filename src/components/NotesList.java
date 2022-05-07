
package components;

import java.util.ArrayList;
import java.util.Arrays;

import espace_etudiant.Enseignant;
import gestion.Etudiant;
import model.Matiere;
import model.Note;
import model.NoteMatiere;

public class NotesList extends DynamicList {

	ArrayList<ArrayList<String>> data = null;
	Etudiant ed = null;
	Integer semestre = 1; 
	
	public NotesList(Etudiant ed,Boolean modPermission, Boolean delPermission,Boolean addPermission) {
		super(modPermission,delPermission,addPermission);
		this.ed = ed;
		RefreshTable();
	}

	@Override
	public void RefreshTable() {
		String[] headers = {"Id Note Matiere","Id Matiere","Id Note","Semestre","Nom Matiere","Examen","Ds","Tp"};
		data = new ArrayList<ArrayList<String>>(); 
		ed.getNotesFromDb(1);
		ed.getNotesFromDb(2);
	
		//	System.out.println("Yar cheking the s1");
			for(NoteMatiere nm : ed.getNotesS1()) {
				ArrayList<String> notes = new ArrayList<String>();
				notes.add(nm.getId().toString());
				notes.add(nm.getMatiere().getId().toString());
				notes.add(nm.getNotes().getId().toString());
				notes.add("Semestre 1");
				notes.add(nm.getMatiere().getNomMatiere());
				notes.add(Double.toString(nm.getNotes().getExam()));
				notes.add(Double.toString(nm.getNotes().getDs()));
				notes.add(Double.toString(nm.getNotes().getTp()));
				data.add( notes);
			}

			for(NoteMatiere nm : ed.getNotesS2()) {
				ArrayList<String> notes =  new ArrayList<String>() ;
				notes.add(nm.getId().toString());
				notes.add(nm.getMatiere().getId().toString());
				notes.add(nm.getNotes().getId().toString());
				notes.add("Semestre 2");
				notes.add(nm.getMatiere().getNomMatiere());
				notes.add(Double.toString(nm.getNotes().getExam()));
				notes.add(Double.toString(nm.getNotes().getDs()));
				notes.add(Double.toString(nm.getNotes().getTp()));
				data.add( notes);
			}
		
		UpdateData(headers,data);
	}
	
	@Override
	Boolean onDelete(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;
		// TODO Auto-generated method stub
		for(ArrayList<String> row : arr) {
			Note nt = new Note(Integer.parseInt(row.get(2)),Double.parseDouble(row.get(5))  , Double.parseDouble(row.get(6)), Double.parseDouble(row.get(7)));
			Matiere m = new Matiere(Integer.parseInt(row.get(1)));
			NoteMatiere nm = new NoteMatiere(Integer.parseInt(row.get(0)),m,nt);
			if(!nm.removeFromDb()) {
				good = false;
			}
		}		
		return good;
		
	}

	@Override
	Boolean onInsert(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;

		for(ArrayList<String> row : arr) {
			Note nt = new Note(Integer.parseInt(row.get(2)),Double.parseDouble(row.get(5))  , Double.parseDouble(row.get(6)), Double.parseDouble(row.get(7)));
			Matiere m = new Matiere(Integer.parseInt(row.get(1)));
			NoteMatiere nm = new NoteMatiere(Integer.parseInt(row.get(0)),m,nt);
			if(!nm.saveToDb(ed)) {
				good = false;
			}
		}
		return good;

	}

	@Override
	Boolean onModify(ArrayList<ArrayList<ArrayList<String>>> arr) {
		Boolean good = true;

		for(ArrayList<ArrayList<String>> couple : arr) {
			ArrayList<String> oldens = couple.get(0);
			ArrayList<String> newens = couple.get(1);

			Note nt1 = new Note(Integer.parseInt(oldens.get(2)),Double.parseDouble(oldens.get(5))  , Double.parseDouble(oldens.get(6)), Double.parseDouble(oldens.get(7)));
			Matiere m1 = new Matiere(Integer.parseInt(oldens.get(1)));
			NoteMatiere nm1 = new NoteMatiere(Integer.parseInt(oldens.get(0)),m1,nt1);
			

			Note nt2 = new Note(Integer.parseInt(newens.get(2)),Double.parseDouble(newens.get(5))  , Double.parseDouble(newens.get(6)), Double.parseDouble(newens.get(7)));
			Matiere m2 = new Matiere(Integer.parseInt(newens.get(1)));
			NoteMatiere nm2 = new NoteMatiere(Integer.parseInt(newens.get(0)),m2,nt2);			

			if(!nm1.updateInDbTo(nm2)) {
				good = false;
			}
		}
		return good;

	}

	public void setSemestre(int idSem) {
		this.semestre = idSem;		
	}
}
