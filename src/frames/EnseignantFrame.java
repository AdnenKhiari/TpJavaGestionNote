package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.ClassList;
import components.DynamicList;
import components.EnseignantList;
import components.EtudiantList;
import components.MainFrame;
import components.MatiereList;
import components.NotesList;
import espace_etudiant.Admin;
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
	ClassList classList = null;

	public EnseignantFrame(Enseignant a) {
		
		super(700,650,0,0);
		if(currentEnseignantFrame == null)
			currentEnseignantFrame = this;
		
		ens = a;
		
		contentJPanel.setLayout(new BorderLayout());
		
		MainMenu mn = new MainMenu();
		setJMenuBar(mn);
		
		cp = new ClassPage();
		
		//Init the window to have it by default
		cp.setVisible(true);
		contentJPanel.add(cp);
		
		classList = new ClassList(false,false,false);
		classList.setVisible(true);

		
		add(contentJPanel,BorderLayout.CENTER);
		contentJPanel.setVisible(true);
		setVisible(true);
	}
	
	class MainMenu extends JMenuBar{
		String currentItem = "class";

		public MainMenu() {
			
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
		String currentItem = "Matieres";
		
		JPanel classContent = new JPanel();
		Classe selectedClass = null;
		Component selectedView = null;
		NotesList notesList = null;
		EtudiantList etudiantList = null;
		JTextField etudiantNumField = null;
		JComboBox<Classe> classesListBox = null;
		int i = 0;

		ClassPage(){
			
			super();
			
			setLayout(new BorderLayout());
			
			SecondayMenu sm = new SecondayMenu();
			add(sm,BorderLayout.SOUTH);
			
			classContent.setLayout(new BorderLayout());
			classContent.setPreferredSize(new Dimension(1000,350));
			
			//Set the class Dropdown and the student number
			JPanel header = new JPanel();
			header.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			
			//Drop down
			classesListBox = new JComboBox<Classe>() ;
			classesListBox.setPreferredSize(new Dimension(150,50));
			
			for(Classe cl: Classe.getAllClassesFromDb()) {
				classesListBox.addItem(cl);
			}
			
			classesListBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
						changeContentBasedOnClass();			
				}
			});
			
			//Student number
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
			
			//get the current class
			Classe cl = (Classe)classesListBox.getSelectedItem();
			cl.listeEtudiant = Etudiant.getAllEtudiantsInClasse(cl.getId());
			selectedClass = cl;
			
			//reset the etudiant list to use the current class
			etudiantList = new EtudiantList(cl,true,true,true);
			
			//reset the view to 

			etudiantList.setVisible(false);
			
			if(notesList != null)
				notesList.setVisible(false);
			
			if(selectedView == null) {
				currentItem="Etudiant";
				selectedView = etudiantList;
			}
			
			if(currentItem.equals("Etudiant")) {
				selectedView = etudiantList;
			}
			
			renderComp(selectedView, classContent);
		}
		
		class SecondayMenu extends JMenuBar{
			
			String currentItem = "Matieres";
			String etudiantID  = "";
			public SecondayMenu() {
				
				setPreferredSize(new Dimension(1000,30));
							
				JMenuItem etudiants = new JMenuItem("Etudiant");
				JMenuItem notes = new JMenuItem("Notes");

				notes.addActionListener(e-> {
					if(e.getSource()!=notes)
						return;					
					
					if(currentItem.equals( "Notes"))
						return;
					currentItem = "Notes";	 
				
					if(!etudiantID.equals(etudiantNumField.getText())) {
						
						etudiantID = etudiantNumField.getText();
						
						notesList = null;
						
						//find etudiant with that id
						for(Etudiant etudiant : selectedClass.listeEtudiant) {
							if(etudiant.getId().equals(etudiantID)) {
								notesList = new NotesList(etudiant, false, true, true);
								break;
							}
						}
					}
					
					if(notesList == null) {
						JOptionPane.showMessageDialog(this, "Invalid Id", "Choose a valid ID", JOptionPane.WARNING_MESSAGE);
					}else {
						selectedView = notesList;
						renderComp(selectedView, classContent);
					}

					//renderComp();
					System.out.println("Menu " + currentItem);

				});
				
				etudiants.addActionListener(e-> {
					if(e.getSource()!=etudiants)
						return;
					
					if(currentItem.equals( "Etudiant"))
						return;
					currentItem = "Etudiant";

					System.out.println("you are on Etuds");

					selectedView = etudiantList;

					//mark the current item
					renderComp(selectedView, classContent);
					System.out.println("Menu " + currentItem);

				});
				
				add(etudiants);
				add(notes);
			}
		}
	}
	
	void renderComp(Component c,JPanel parent) {
		c.setVisible(true);
		parent.removeAll();
		parent.add(c);
		parent.revalidate();
		parent.repaint();
	}
}


