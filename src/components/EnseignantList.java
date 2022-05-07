package components;

import java.util.ArrayList;

import espace_etudiant.Enseignant;
import gestion.Etudiant;
import model.NoteMatiere;

public class EnseignantList extends DynamicList {
	public EnseignantList(Boolean modPermission, Boolean delPermission,Boolean addPermission) {
			super(modPermission,delPermission,addPermission);
			RefreshTable();
	}

	@Override
	Boolean onDelete(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;
		for(ArrayList<String> row : arr) {
			Enseignant ens = new Enseignant(row.get(3), row.get(4), row.get(1), row.get(2),Integer.parseInt(row.get(0)));
			if(!ens.removeFromDb()) {
				good = false;
			}
		}
		return good;
	}

	@Override
	Boolean onInsert(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;
		for(ArrayList<String> row : arr) {
			Enseignant ens = new Enseignant(row.get(3), row.get(4), row.get(1), row.get(2),Integer.parseInt(row.get(0)));
			if(!ens.saveToDb()) {
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

			Enseignant ens = new Enseignant(oldens.get(3), oldens.get(4), oldens.get(1), oldens.get(2),Integer.parseInt(oldens.get(0)));
			Enseignant updatedens = new Enseignant(newens.get(3), newens.get(4), newens.get(1), newens.get(2),Integer.parseInt(newens.get(0)));
			if(!ens.updateInDbTo(updatedens)) {
				good = false;
			}
		}
		return good;

	}

	@Override
	void RefreshTable() {
		String[] headers = {"IdEnseignant","Nom","CIN","Username","Password"};
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); 

			for(Enseignant e :Enseignant.getEns()) {
				ArrayList<String> dt = new ArrayList<String>();
				dt.add(e.getId());
				dt.add(e.getNom());
				dt.add(e.getCIN());
				dt.add(e.getLogin());
				dt.add(e.getMotDePasse());
				data.add(dt);
			}
		UpdateData(headers,data);		
	}
}
