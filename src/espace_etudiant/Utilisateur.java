package espace_etudiant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbmodels.DBUtils;
import gestion.Etudiant;

public class Utilisateur {
	private String login;
	private String motDePasse;
	
	public static Utilisateur ConnectedUser;
	
	public Utilisateur() {
		
	}

	public String getLogin() {
		return login;
		
	}

	public static Utilisateur getUser(String login,String mdp)  {
		
		String oldmp = mdp;
		mdp = cryptPass(mdp);
		try {
			PreparedStatement prd = DBUtils.execute("SELECT idRef,usertype  FROM  User WHERE Login=? AND Pwd =?",new Object [] {login,mdp}); 
			if(prd != null) {
				ResultSet rs  =  prd.executeQuery();
				while(rs.next()) {
					int idRef = rs.getInt(1);
					int usertype = rs.getInt(2);
					if(usertype == 1) {
						return Etudiant.getEtudiantById(idRef);
					}else if(usertype == 2) {
						return Enseignant.getEnseignantById(idRef);
					}else if(usertype == 3) {
						return new Admin(login,oldmp);
					}
				}
			}else {
				System.out.println("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = cryptPass(motDePasse);
	}

	public Utilisateur(String login, String motDePasse) {
		this.login = login;
		this.motDePasse = cryptPass(motDePasse);
	}
	
	public Boolean seConnecter(String login, String motDePasse) {
		return (login.equals(this.login) && cryptPass(motDePasse).equals(this.motDePasse));
	}

	private static String cryptPass(String motDePasse) {
		String Crypted = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(motDePasse.getBytes());

			byte byteData[] = md.digest();

			// convertir le tableau de bits en format hexadécimal
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			Crypted = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return Crypted;

	}
}
