package model;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import dbmodels.DBUtils;

public class Note {

	int id;
	private double exam;
	private double ds;
	private double tp;

	
	public Note(int id,double exam, double ds, double tp) {
		this.id =id;
		this.exam = exam;
		this.ds = ds;
		this.tp = tp;
	}

	public double getExam() {
		return exam;
	}
	public Integer getId() {
		return id;
	}

	public void setExam(double exam) {
		this.exam = exam;
	}

	public double getDs() {
		return ds;
	}
	
	public ArrayList<String> getNotesArray(){
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(Double.toString(ds));
		arr.add(Double.toString(tp));
		arr.add(Double.toString(exam));
		return arr;
	}

	public void setDs(double ds) {
		this.ds = ds;
	}

	public double getTp() {
		return tp;
	}

	public void setTp(double tp) {
		this.tp = tp;
	}

	@Override
	public String toString() {
		return "Note [exam=" + exam + ", ds=" + ds + ", tp=" + tp + "]";
	}



}
