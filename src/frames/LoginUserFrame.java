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
	
	public static ArrayList<Enseignant> listEnseignant = new ArrayList<Enseignant>();
	public static ArrayList<Classe> allclasses = new ArrayList<Classe>();
	public static LoginUserFrame openedLogin = null;

	public LoginUserFrame() {
		
		super(325,375,0, 0);
		
		if(openedLogin == null) {
			openedLogin = this;
		}
		
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
			
			//Here the user
			Utilisateur loggedUser = Utilisateur.getUser(loginString, passString);
			
			Boolean loggedBoolean = false;
		
			if(loggedUser != null) {
				succesLogin(loggedUser);
				loggedBoolean = true;
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
		
		//Todo add admin todb + test db
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