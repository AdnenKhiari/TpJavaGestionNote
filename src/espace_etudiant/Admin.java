package espace_etudiant;

public class Admin extends Utilisateur {
	public Admin(String username,String pass) {
		super();
		setLogin(username);
		setMotDePasse(pass);
	}
}
