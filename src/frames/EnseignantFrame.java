package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.DynamicList;
import components.EnseignantList;
import components.EtudiantList;
import components.MainFrame;
import components.MatiereList;
import components.NotesList;
import espace_etudiant.Enseignant;
import gestion.Classe;
import gestion.Etudiant;
import model.Matiere;

public class EnseignantFrame extends MainFrame {
	
	String currentItem = "class";
	Enseignant ens = null;
	JPanel contentJPanel = new JPanel();
	ClassPage cp  = null;
	EnseignantFrame currentEnseignantFrame = null;
	
	public EnseignantFrame(Enseignant ens) {
		
		super(500,700,0,0);
		if(currentEnseignantFrame == null)
			currentEnseignantFrame = this;
		
		this.ens = ens;
		
		contentJPanel.setLayout(new BorderLayout());
		
		MainMenu mn = new MainMenu();
		setJMenuBar(mn);
		
		cp = new ClassPage();
		
		//Init the window to have it by default
		cp.setVisible(true);
		contentJPanel.add(cp);
		
		add(contentJPanel,BorderLayout.CENTER);
		setVisible(true);
	}
	
	class MainMenu extends JMenuBar{
		String currentItem = "class";

		public MainMenu() {
			// TODO Auto-generated constructor stub
			
			setPreferredSize(new Dimension(1000,30));
			
			JMenuItem logoutItem = new JMenuItem("LogOut");
			JMenuItem classItems = new JMenuItem("Classe");
			
			logoutItem.addActionListener(e-> {
				if(e.getSource()!=logoutItem)
					return;
				
				LoginUserFrame.openedLogin.setVisible(true);
				currentEnseignantFrame.setVisible(false);
				
				JOptionPane.showMessageDialog(this, "Disconnected");

				System.out.println("Log Out");
			});
			
			
			classItems.addActionListener(e-> {
				if(e.getSource()!=classItems)
					return;
				
				if(currentItem == "class")
					return;
				
				//mark the current item
				currentItem = "class";
				
				renderComp(cp,contentJPanel);
				System.out.println("Menu " + currentItem);

			});
				

			add(classItems);
			add(logoutItem);
		}
	}
	
	class ClassPage extends JPanel{
		
		JPanel classContent = new JPanel();
		String selectedClass = "";
		
		NotesList notesList = null;
		MatiereList matiereList = null;
		EtudiantList etudiantList = null;
		JTextField etudiantNumField = null;
		JComboBox<Classe> classesListBox = null;
		
		ClassPage(){
			super();
			
			setLayout(new BorderLayout());
			
			SecondayMenu sm = new SecondayMenu();
			add(sm,BorderLayout.SOUTH);
			
			classContent.setLayout(new BorderLayout());
			classContent.setPreferredSize(new Dimension(1000,350));
			
			//Set the class DropDown and the student number
			JPanel header = new JPanel();
			header.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			//Drop down
			classesListBox = new JComboBox<Classe>() ;
			classesListBox.setPreferredSize(new Dimension(150,50));
			
			for(Classe cl: LoginUserFrame.allclasses) {
				classesListBox.addItem(cl);
			}
			
			classesListBox.addItemListener(e->{
				if(e.getSource()!=classesListBox)
					return;
				
				changeContentBasedOnClass();

			});
			
			//student number
			etudiantNumField = new JTextField("Choisir un ID d'etudiant");
			etudiantNumField.setBorder(BorderFactory.createTitledBorder("ID Etudiant"));
			etudiantNumField.setPreferredSize(new Dimension(150,50));

			

			
			//add elements to parents
			header.add(classesListBox);
			header.add(etudiantNumField);
			add(header,BorderLayout.NORTH);	
			
			add(classContent,BorderLayout.CENTER);	
			
			changeContentBasedOnClass();


		}
		
		void changeContentBasedOnClass() {
			
			Classe cl = (Classe)classesListBox.getSelectedItem();
			if(selectedClass.equals(cl.toString()))
				return;
			
			selectedClass = cl.toString();
			etudiantList = new EtudiantList(cl,false,false,false);
			etudiantList.setVisible(true);
			
			currentItem = "Etudiant";
			
			renderComp(etudiantList, classContent);

		}
		
		class SecondayMenu extends JMenuBar{
			
			String currentItem = "Matieres";
			String etudiantID  = "";
			public SecondayMenu() {
				
				setPreferredSize(new Dimension(1000,30));
							
				JMenuItem matieres = new JMenuItem("Matieres");
				JMenuItem etudiants = new JMenuItem("Etudiant");
				JMenuItem notes = new JMenuItem("Notes");

				
				matieres.addActionListener(e-> {
					if(e.getSource()!=matieres)
						return;
					

					
					currentItem = "Matieres";
				
					
					renderComp(matiereList, classContent);
					//renderComp();
					System.out.println("Menu " + currentItem);
					
				});
				
				notes.addActionListener(e-> {
					if(e.getSource()!=notes)
						return;
					
					currentItem = "Notes";
					 
					
					 
				
					if(!etudiantID.equals(etudiantNumField.getText())) {
						etudiantID = etudiantNumField.getText();
						Classe cur = (Classe)classesListBox.getSelectedItem();
						notesList = null;
						//find etudiant with that id
						for(Etudiant etudiant : cur.listeEtudiant) {
							if(etudiant.getId().equals(etudiantID)) {
								notesList = new NotesList(etudiant, false, true, true);
								break;
							}
						}

					}
						if(notesList == null) {
							JOptionPane.showMessageDialog(this, "Invalid Id", "Choose a valid ID", JOptionPane.WARNING_MESSAGE);
						}else {
							renderComp(notesList, classContent);
						}

					

					//renderComp();
					System.out.println("Menu " + currentItem);

				});
				
				etudiants.addActionListener(e-> {
					if(e.getSource()!=etudiants)
						return;
					

					currentItem = "Etudiant";

					
					//mark the current item
					renderComp(etudiantList, classContent);
					//renderComp();
					System.out.println("Menu " + currentItem);

				});
				

				add(matieres);
				add(etudiants);
				add(notes);

				
			}
		}

	}
	
	void renderComp(Component c,JPanel parent) {
		parent.removeAll();
		c.setVisible(true);
		parent.add(c);
		parent.revalidate();
		parent.repaint();
	}
	
	
}
