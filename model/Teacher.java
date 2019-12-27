package model;

import java.util.ArrayList;
import java.util.TreeMap;

public class Teacher {

	private TreeMap<String, Integer> mark = new TreeMap<>();
	private String student;
	private String id;
	private ArrayList<String> work = new ArrayList<>();

	public Teacher(String student) {

		this.id = student;
		String[] tmp = student.split("_");
		char[] tmpChar;
		StringBuilder stName = new StringBuilder();

		for (int j = 0; j < 2; j++) {
			tmpChar = tmp[j].toCharArray();
			tmpChar[0] = Character.toUpperCase(tmpChar[0]);
			stName.append(tmpChar);
			stName.append(" ");
		}

		this.student = stName.toString();
	}

	public int numberOfMark() {
		return mark.size();
	}

	public TreeMap<String, Integer> getMark() {
		return mark;
	}

	public void setMark(String mark) {
		
		String[] tmp = mark.split(" ");
		
		if (tmp[0].length() == 1) {
			
			int markInt = Integer.parseInt(tmp[0]);
			String data = tmp[1];
			this.mark.put(data, markInt);
			
			if (tmp[2].equals("1"))
				work.add(this.id + " " + tmp[1]);
			else
				work.add("null");
			
		} else {
			if (tmp[1].equals("1"))
				work.add(this.id + " " + tmp[0]);
			else
				work.add("null");
		}
	}

	public ArrayList<String> getWork() {
		return work;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}
}