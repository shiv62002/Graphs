package onlineTest;

import java.util.HashMap;

public class TrueFalseQuestion extends Question { // Represents True/false questions
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	boolean answer;
	HashMap<String, Boolean> studentAnswerKey;
	// Map with the Student names as keys, and their answers at the value

	public TrueFalseQuestion(int questionNumber, String text, double points, boolean answer) {
		super(questionNumber, text, points); // gets these values from Question class
		this.answer = answer;
		this.studentAnswerKey = new HashMap<String, Boolean>(); // Initializes HashMap
	}

	public boolean getAnswer() { // gets the correct answer for a question
		return answer;
	}

	public boolean equals(Question question) {
		if (!(question instanceof TrueFalseQuestion)) {
			return false; // checks if a question is TrueFlaseQuestion
		}

		return this.questionNumber == question.questionNumber && this.text.equals(question.text)
				&& this.points == question.points && this.answer == ((TrueFalseQuestion) question).answer;
	} // checks equality of two different questions based on fields

	public String toString() {
		return "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: "
				+ (answer ? "True" : "False") + "\n";
	}
}
