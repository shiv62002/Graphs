package onlineTest;

import java.io.Serializable;
import java.util.HashMap;

public class Student implements Serializable { // Represents a student
	/**
	 * 
	 */
	static final long serialVersionUID = 1L;
	String name;
	int score;
	// HashMap Integer represents the exam id and the value is the specified exam
	HashMap<Integer, Exam> exams = new HashMap<Integer, Exam>();

	public Student(String name) {
		this.name = name; // constructor for a Student
		this.score = 0;
	}

	public String getName() { // gets the student's name
		return this.name;
	}

}
