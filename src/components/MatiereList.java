package components;

import java.util.ArrayList;

import espace_etudiant.Enseignant;
import gestion.Etudiant;
import model.Matiere;
import model.NoteMatiere;

public class MatiereList extends DynamicList {
	
	public MatiereList(Boolean modPermission, Boolean delPermission,Boolean addPermission) {
		super(modPermission,delPermission,addPermission);
		RefreshTable();
	}

	@Override
	void onDelete(ArrayList<ArrayList<String>> arr) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		for(ArrayList<String> row : arr) {
			Matiere e = new Matiere(Integer.parseInt(row.get(0)));
			e.removeFromDb();
		}
	}

	@Override
	void onInsert(ArrayList<ArrayList<String>> arr) {
		System.out.println("ya whayid ");
		// TODO Auto-generated method stub
		for(ArrayList<String> row : arr) {
			Integer idSem = row.get(2) == "Semestre 1" ? 1 : 2;
			Matiere e = new Matiere( Double.parseDouble(row.get(5)) ,Double.parseDouble(row.get(3)) ,Double.parseDouble(row.get(4)) ,Double.parseDouble(row.get(6)), row.get(1),Integer.parseInt(row.get(0)), Integer.parseInt(row.get(7)),idSem );
			System.out.println(e.toString());
			
			e.saveToDb();
		}
	}

	@Override
	void onModify(ArrayList<ArrayList<ArrayList<String>>> arr) {
		for(ArrayList<ArrayList<String>> couple : arr) {
			ArrayList<String> oldens = couple.get(0);
			ArrayList<String> newens = couple.get(1);

			Integer oldidSem = oldens.get(2) == "Semestre 1" ? 1 : 2;
			Integer newidSem = newens.get(2) == "Semestre 1" ? 1 : 2;

			Matiere olde = new Matiere( Double.parseDouble(oldens.get(5)) ,Double.parseDouble(oldens.get(3)) ,Double.parseDouble(oldens.get(4)) ,Double.parseDouble(oldens.get(6)), oldens.get(1),Integer.parseInt(oldens.get(0)), Integer.parseInt(oldens.get(7)),oldidSem );
			Matiere newe = new Matiere( Double.parseDouble(newens.get(5)) ,Double.parseDouble(newens.get(3)) ,Double.parseDouble(newens.get(4)) ,Double.parseDouble(newens.get(6)), newens.get(1),Integer.parseInt(newens.get(0)), Integer.parseInt(newens.get(7)),newidSem );
			olde.updateInDbTo(newe);
		}
	}

	@Override
	void RefreshTable() {
		String[] headers = {"Id Matiere","Nom Matiere","Semestre","CoefDs","CoefTp","CoefExamen","CoefMatiere","Id Enseignant"};
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); 

		for(Matiere nm : Matiere.getAllMatieresFromDb()) {
			
			ArrayList<String> mts = new ArrayList<String>();
			
			mts.add(Integer.toString(nm.getId()));
			mts.add((nm.getNomMatiere()));
			mts.add(nm.getSemestre());
			mts.add(Double.toString(nm.getCoefds()));
			mts.add(Double.toString(nm.getCoefTp()));
			mts.add(Double.toString(nm.getCoefExam()));
			mts.add(Double.toString(nm.getCoefMatiere()));
			mts.add(Integer.toString(nm.getIdEnseignant()));

			data.add(mts);
			
		}

		UpdateData(headers,data);		
	}
}
