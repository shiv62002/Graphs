package onlineTest;

import java.io.Serializable;

public abstract class Question implements Serializable {
	// Abstract class for the outline 3 different types of questions
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String text; // Common fields among the questions
	double points;
	int questionNumber;

	public Question(int questionNumber, String text, double points) {
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
	}

	public abstract boolean equals(Question question); // Check if questions are equal

	public abstract String toString(); // A toSring for a question
}
