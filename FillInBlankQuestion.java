package onlineTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FillInBlankQuestion extends Question { // Represents FillInBlank questions
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	String[] answer;
	// The String in the Hash map key is the student name
	// The String array is a reference to all of the students FillInBlankQuestions
	HashMap<String, String[]> studentAnswerKey;
	// Map represents all of the students and the answers they have

	public FillInBlankQuestion(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points); // gets these values from Question
		studentAnswerKey = new HashMap<String, String[]>(); // initializes map

		List<String> list = Arrays.asList(answer); // Turns the answer array into a list

		Collections.sort(list, String.CASE_INSENSITIVE_ORDER); // Sorts list alphabetically

		this.answer = new String[list.size()]; // Initializes the answer array field

		for (int i = 0; i < list.size(); i++) {
			this.answer[i] = list.get(i); // populates the answer array field w/h values in the sorted list
		}
	}

	public List<String> getAnswer() { // gets the correct answer for a question
		return Arrays.asList(this.answer); // returns the answers (array) as a list
	}

	public boolean equals(Question question) { // check for the equality of a question
		if (!(question instanceof FillInBlankQuestion)) {
			return false; // checks if the question is a FillInBlankQuestion
		}

		for (int i = 0; i < answer.length; i++) {
			if (!answer[i].equals(((FillInBlankQuestion) question).answer[i])) {
				return false; // If the answers to the questions aren't the same
			}
		}

		return this.questionNumber == question.questionNumber && this.text.equals(question.text)
				&& this.points == question.points; // checks if the question is the same
	}

	public String toString() { // returns the exact toString needed for a question
		String output = "";

		for (String a : answer) {
			output += a;
			if (!a.equals(answer[answer.length - 1])) {
				output += ", ";
			}

		}

		return "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: " + "["
				+ output + "]\n";
	}
}
