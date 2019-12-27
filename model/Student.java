package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String subject;
	private final ArrayList<Integer> mark = new ArrayList<Integer>();
	private ArrayList<String> work= new ArrayList<>();

	public Student() { }

	public Student(String subject) {
		String[] tmp = subject.split(" ");
		this.id = tmp[0];
		this.subject = tmp[1];
	}

	public String getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public ArrayList<Integer> getMark() {
		return mark;
	}

	public void setMark(String mark) {
		
		System.out.println(mark);
		
		if(mark != null) {
			String[] tmp = mark.split(" ");
			if (tmp[0].length() == 1) {
				
				int markInt = Integer.parseInt(tmp[0]);
				this.mark.add(markInt);
				
				if(tmp[2].equals("1")) 
					work.add(this.id + " " + this.subject + " " + tmp[1]);
				else
					work.add("null");
				
			}else {
				if(tmp[1].equals("1")) 
					work.add(this.id + " " + tmp[0]);
				else
					work.add("null");
			}
		}
	}
	
	public ArrayList<String> getWork(){
		return work;
	}
}