
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
		String[] headers = {"Id Note","Id Matiere","Semestre","Nom Matiere","Ds","Tp","Examen"};
		data = new ArrayList<ArrayList<String>>(); 
		ed.getNotesFromDb(1);
		ed.getNotesFromDb(2);
	
		//	System.out.println("Yar cheking the s1");
			for(NoteMatiere nm : ed.getNotesS1()) {
				ArrayList<String> notes = new ArrayList<String>();
				notes.add(nm.getNotes().getId().toString());
				notes.add(nm.getMatiere().getId().toString());
				notes.add("Semestre 1");
				notes.add(nm.getMatiere().getNomMatiere());
				notes.add(Double.toString(nm.getNotes().getDs()));
				notes.add(Double.toString(nm.getNotes().getTp()));
				notes.add(Double.toString(nm.getNotes().getExam()));
				data.add( notes);
			}
			/*}else {*/
		//	System.out.println("Yar cheking the s2");

			for(NoteMatiere nm : ed.getNotesS2()) {
				ArrayList<String> notes =  new ArrayList<String>() ;
				notes.add(nm.getNotes().getId().toString());
				notes.add(nm.getMatiere().getId().toString());

				notes.add("Semestre 2");
				notes.add(nm.getMatiere().getNomMatiere());
				notes.add(Double.toString(nm.getNotes().getDs()));
				notes.add(Double.toString(nm.getNotes().getTp()));
				notes.add(Double.toString(nm.getNotes().getExam()));
				data.add( notes);
			}
		
		UpdateData(headers,data);
	}
	
	@Override
	void onDelete(ArrayList<ArrayList<String>> arr) {
		// TODO Auto-generated method stub
		System.out.println(arr.toString());
		
		
	}

	@Override
	void onInsert(ArrayList<ArrayList<String>> arr) {
		for(ArrayList<String> row : arr) {
			Note nt = new Note(Integer.parseInt(row.get(0)),Double.parseDouble(row.get(4))  , Double.parseDouble(row.get(5)), Double.parseDouble(row.get(6)));
			Matiere m = new Matiere(Integer.parseInt(row.get(1)));
			NoteMatiere nm = new NoteMatiere(m,nt);
			nm.saveToDb(ed);
		}
	}

	@Override
	void onModify(ArrayList<ArrayList<ArrayList<String>>> arr) {
		// TODO Auto-generated method stub
		System.out.println(arr.toString());

	}

	public void setSemestre(int idSem) {
		this.semestre = idSem;		
	}
}
