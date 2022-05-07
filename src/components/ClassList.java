package components;

import java.util.ArrayList;

import espace_etudiant.Enseignant;
import gestion.Classe;
import gestion.Etudiant;

public class ClassList extends DynamicList {

	public ClassList(Boolean modPermission, Boolean delPermission,Boolean addPermission) {
		super(modPermission,delPermission,addPermission);
		RefreshTable();
		
}
	
	@Override
	Boolean onDelete(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;

		for(ArrayList<String> row: arr) {
			Classe cl = new Classe(row.get(1),Integer.parseInt(row.get(0)));
			if(!cl.removeFromDb()) {
				good = false;
			}
		}
		return good;

	}

	@Override
	Boolean onInsert(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;

		for(ArrayList<String> row: arr) {
			Classe cl = new Classe(row.get(1),Integer.parseInt(row.get(0)));
			if(!cl.saveToDb()) {
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

			Classe cl = new Classe(oldens.get(1),Integer.parseInt(oldens.get(0)));
			Classe updatedcl = new Classe(newens.get(1),Integer.parseInt(newens.get(0)));
			if(!cl.updateInDbTo(updatedcl)) {
				good = false;
			}
			
		}
		return good;
	}

	@Override
	void RefreshTable() {
		String[] headers = {"IdClasse","Nom Classe"};
		
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); 
		
		for(Classe e : Classe.getAllClassesFromDb()) {
			
			ArrayList<String> mts = new ArrayList<String>();
			mts.add(e.getId().toString());
			mts.add(e.getNom());
			data.add(mts);
		}
		UpdateData(headers,data);		
	}

}
