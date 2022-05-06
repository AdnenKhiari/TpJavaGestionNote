package frames;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import components.DynamicList;
import components.MainFrame;
import components.NotesList;
import gestion.Classe;
import gestion.Etudiant;
import model.Matiere;
import model.Note;
import model.NoteMatiere;
/*
 * TODO:
 * view classes : crud ( pour chaque classe on creer implicitement 2 semestres )
 * ajouter choix 1 ou 2 semestre dans les matieres
 * ajouter id enseignant  pour chaque matieres dansla liste de matieres par classe
 * option pour ajouter un etudiant a la classe par id seulement + pas de edit from la class 
 * ajouter chap semeestre et ens pour matieres dans une classe 
 * 
 * 
 */
public class InitFrame {
	public static void main(String[] args) {
		LoginUserFrame loginUserFrame = new LoginUserFrame();
		/*String[] headStrings = {"H1","H2","H3"};
		String[][] dtObjects = {
				{"Adnen","Lel","Aahaaaa"},
				{"Lel21","Lel22","lEL23"},
				{"Adnen","Lel","Aahaaaa"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
				{"Lel21","Lel22","lEL23"},
		};
		DynamicList ls = new DynamicList(headStrings, dtObjects, true,true,false);*/
		//ModifierNoteFrame loginUserFrame = new ModifierNoteFrame();
		
		

		
		//MainFrame ff = new EtudiantFrame(e,true,true,true);
		//ff.add(ls);

		loginUserFrame.setVisible(true);
	}
}
