package onlineTest;

import java.io.Serializable;
import java.util.HashMap;

public class Exam implements Serializable { // Represents an entire exa,
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	int examId;
	String title;
	// The integer is the questionNumber
	// The value is the question
	HashMap<Integer, Question> questions;

	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
		questions = new HashMap<Integer, Question>(); // initializes the map
	}

	public boolean equals(Exam exam) {
		return exam.examId == this.examId && exam.title.equals(this.title);
	} // checks if an exam is the same based on if it has the same id and title

	public void addQuestion(Question question) {
		questions.put(question.questionNumber, question);
	} // adds a specific question to a specific question number value
}
