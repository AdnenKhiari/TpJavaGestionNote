package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import components.MainFrame;
import espace_etudiant.Admin;
import espace_etudiant.Enseignant;
import espace_etudiant.Utilisateur;
import gestion.Classe;
import gestion.Etudiant;

public class LoginUserFrame extends MainFrame{
	
	private ArrayList<Utilisateur> users ;
	public static ArrayList<Enseignant> listEnseignant = new ArrayList<Enseignant>();
	public static ArrayList<Classe> allclasses = new ArrayList<Classe>();

	public static LoginUserFrame openedLogin = null;
	private ArrayList<Utilisateur> initUserList() {
		
		ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
		
		Classe mi2 = new Classe("Mi2",1);
		Classe mi3 = new Classe("Mi3",2);

		allclasses.add(mi2);
		allclasses.add(mi3);

		
		Enseignant prof1 = new Enseignant("Imene", "12345","Imene","147647915",1);
		Enseignant prof2 = new Enseignant("Khalil", "78910","Khalil Gharbi","97846245",2);

		listEnseignant.add(prof1);
		listEnseignant.add(prof2);
		
		//Classe mi3 = new Classe("Mi3");

		//ajouter les comptes des etudiants
		for(Etudiant ed : mi2.listeEtudiant) {
			users.add(ed);
		}

		for(Enseignant e : listEnseignant) {
			users.add(e);
		}

		users.add(new Admin("admin", "admin"));
		
		return users;
	}
	
	public LoginUserFrame() {
		super(325,375,0, 0);
		
		
		if(openedLogin == null) {
			openedLogin = this;
		}
		//init all users
		users = initUserList();
		
		
		JPanel containterJPanel= new JPanel();
		containterJPanel.setLayout(new BorderLayout(10,10));
		containterJPanel.setPreferredSize(new Dimension(325,325));


		JTextField userField = new JTextField();
		userField.setBorder(BorderFactory.createTitledBorder("Nom de user"));
		JPasswordField  userPassword = new JPasswordField();
		userPassword.setBorder(BorderFactory.createTitledBorder("Mote de passe"));

		JButton loginButton = new JButton("Login !");
		loginButton.addActionListener(e ->{
			String loginString = userField.getText();
			String passString =  String.valueOf(userPassword.getPassword());
			System.out.println(loginString);
			System.out.println(passString);
			Boolean loggedBoolean = false;
			for(Utilisateur us : users) {
				if(us.seConnecter(loginString, passString)) {
					succesLogin(us);
					loggedBoolean = true;
					break;
				}
			}
			if(!loggedBoolean) {
				JOptionPane.showMessageDialog(this, "Verifies les donnes ","Erreur de login", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		
		
		containterJPanel.setBorder(BorderFactory.createEmptyBorder(100,50,100,50));
		
		containterJPanel.add(userField,BorderLayout.NORTH);
		containterJPanel.add(userPassword,BorderLayout.CENTER);
		containterJPanel.add(loginButton,BorderLayout.SOUTH);

		
		add(containterJPanel,BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	void succesLogin(Utilisateur us) {
		
		Utilisateur.ConnectedUser = us;

		String typeuser = "";
		if(us instanceof Etudiant) {
			typeuser = "Etudiant";
			EtudiantFrame ff = new EtudiantFrame((Etudiant)us);
			ff.setVisible(true);
		}else if (us instanceof Enseignant) {
			typeuser = "Enseignant";
			EnseignantFrame ff = new EnseignantFrame((Enseignant)us);
			ff.setVisible(true);

		}else if (us instanceof Admin) {
			typeuser = "Admin";
			AdminFrame ff = new AdminFrame((Admin)us);
			ff.setVisible(true);
		}

		
		JOptionPane.showMessageDialog(null, "Vous etes entre comme un " + typeuser);
		setVisible(false);
	}
}