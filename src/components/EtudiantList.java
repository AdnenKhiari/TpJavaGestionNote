package components;

import java.util.ArrayList;

import gestion.Classe;
import gestion.Etudiant;

public class EtudiantList extends DynamicList {
	Classe cl = null;
	public EtudiantList(Classe cl,Boolean modPermission, Boolean delPermission,Boolean addPermission) {
		super(modPermission,delPermission,addPermission);
		this.cl = cl;
		RefreshTable();
	}

	

	@Override
	Boolean onDelete(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;

		for(ArrayList<String> row : arr) {
			Etudiant ens = new Etudiant(row.get(0));
			if(!ens.removeFromDb()) {
				good = false;
			}
		}	
		return good;
	}

	@Override
	Boolean onInsert(ArrayList<ArrayList<String>> arr) {
		Boolean good = true;
		// TODO Auto-generated method stub
		for(ArrayList<String> row : arr) {
			Etudiant ens = new Etudiant(row.get(0), row.get(1), row.get(2), row.get(3),cl.getId().toString(),row.get(5),row.get(6));
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
			ArrayList<String> olde = couple.get(0);
			ArrayList<String> newe = couple.get(1);

			Etudiant ens = new Etudiant(olde.get(0), olde.get(1), olde.get(2), olde.get(3),cl.getId().toString(),olde.get(5),olde.get(6));
			Etudiant updatede = new Etudiant(newe.get(0), newe.get(1), newe.get(2), newe.get(3),cl.getId().toString(),newe.get(5),newe.get(6));
			if(!ens.updateInDbTo(updatede)) {
				good = false;
			}
			
		}		
		return good;
	}


	@Override
	void RefreshTable() {
		String[] headers = {"ID","CIN","Nom ","Prenom","Id Classe","Login","Password"};
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); 
		cl.getListEtudiantFromDb();
		for(Etudiant e : cl.listeEtudiant) {
			ArrayList<String> mts = new ArrayList<String>();
			mts.add(e.getId());
			mts.add(e.getCin());
			mts.add(e.getName());
			mts.add(e.getPrenom());
			mts.add(e.getClasse());

			mts.add(e.getLogin());
			mts.add(e.getMotDePasse());

			data.add(mts);
			
		}
		UpdateData(headers,data);		
	}
}
