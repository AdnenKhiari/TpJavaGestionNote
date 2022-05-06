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

public class AdminFrame extends MainFrame {
	
	String currentItem = "class";
	Admin admin = null;
	JPanel contentJPanel = new JPanel();
	ClassPage cp  = null;
	EnseignantList el = null;
	AdminFrame currentAdminFrame = null;
	ClassList classList = null;
	MatiereList matiereList = null;

	public AdminFrame(Admin a) {
		
		super(500,700,0,0);
		if(currentAdminFrame == null)
			currentAdminFrame = this;
		
		
		admin = a;
		
		contentJPanel.setLayout(new BorderLayout());
		
		MainMenu mn = new MainMenu();
		setJMenuBar(mn);
		
		cp = new ClassPage();
		
		//Init the window to have it by default
		cp.setVisible(true);
		contentJPanel.add(cp);
		
		
		el = new EnseignantList(true,true,true);
		el.setVisible(true);
		
		matiereList = new MatiereList(true, true, true);
		matiereList.setVisible(true);
		
		classList = new ClassList(true,true,true);
		classList.setVisible(true);

		
		add(contentJPanel,BorderLayout.CENTER);
		contentJPanel.setVisible(true);
		setVisible(true);
	}
	
	class MainMenu extends JMenuBar{
		String currentItem = "class";

		public MainMenu() {
			// TODO Auto-generated constructor stub
			
			setPreferredSize(new Dimension(1000,30));
			
			JMenuItem logoutItem = new JMenuItem("LogOut");
			JMenuItem classItems = new JMenuItem("Classe");
			JMenuItem allclassesItems = new JMenuItem("All classes");
			JMenuItem enseignantItems = new JMenuItem("Enseignant");
			JMenuItem matieres = new JMenuItem("Matieres");

			logoutItem.addActionListener(e-> {
				if(e.getSource()!=logoutItem)
					return;
				
				LoginUserFrame.openedLogin.setVisible(true);
				currentAdminFrame.setVisible(false);
				
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
			
			enseignantItems.addActionListener(e-> {
				if(e.getSource()!=enseignantItems)
					return;
				
				if(currentItem == "el")
					return;
				
				//mark the current item
				currentItem = "el";
				
				renderComp(el,contentJPanel);
				System.out.println("Menu " + currentItem);

			});
			
			allclassesItems.addActionListener(e-> {
				if(e.getSource()!=allclassesItems)
					return;
				
				if(currentItem == "al")
					return;
				
				//mark the current item
				currentItem = "al";
				
				renderComp(classList,contentJPanel);
				System.out.println("Menu " + currentItem);

			});
			matieres.addActionListener(e-> {
				if(e.getSource()!=matieres)
					return;
				
				if(currentItem.equals("Matieres"))
					return;
				
				System.out.println("you are on matts");

				currentItem = "Matieres";
			
				renderComp(matiereList, contentJPanel);
				
			});
			

			add(classItems);
			add(allclassesItems);
			add(enseignantItems);
			add(matieres);
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
			
			//set the class Dropdown and the student number
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
						/*System.out.println("XDD");
						System.out.println(i++);*/
						changeContentBasedOnClass();			
				}
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
			
			//get the current class
			Classe cl = (Classe)classesListBox.getSelectedItem();
			cl.listeEtudiant = Etudiant.getAllEtudiantsInClasse(cl.getId());
			selectedClass = cl;
			
			//reset the etudiant list to use the current class
			etudiantList = new EtudiantList(cl,true,true,true);
			
			//reset the view to the current one 

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
								notesList = new NotesList(etudiant, true, true, true);
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
					//renderComp();
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
