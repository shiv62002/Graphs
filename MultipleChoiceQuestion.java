package onlineTest;

import java.util.Arrays;
import java.util.HashMap;

public class MultipleChoiceQuestion extends Question { // Represents Mult choice questions
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] answer; // String array of answers
	// The String key represents Student names
	// The String array value represents Student answers
	HashMap<String, String[]> studentAnswerKey;

	public MultipleChoiceQuestion(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points); // gets the values from Question
		studentAnswerKey = new HashMap<String, String[]>(); // initializes map
		this.answer = new String[answer.length];

		for (int i = 0; i < answer.length; i++) {
			this.answer[i] = answer[i]; // Sets the answers of the field equal to the parameter
		}

		Arrays.sort(this.answer); // Sorts the answers
	}

	public String[] getAnswer() { // gets the correct answer for a question
		return answer;
	}

	public boolean equals(Question question) {
		if (!(question instanceof MultipleChoiceQuestion)
				|| this.answer.length != ((MultipleChoiceQuestion) question).answer.length) {
			return false; // Checks if the question is a mult choice question
		}

		for (int i = 0; i < this.answer.length; i++) {
			if (!(this.answer[i].equals(((MultipleChoiceQuestion) question).answer[i]))) {
				return false; // if the answers to he question aren't the same
			}
		}

		return this.questionNumber == question.questionNumber && this.text.equals(question.text)
				&& this.points == question.points; // checks if the question is the same
	}

	public String toString() { // gets the toString version for the question
		String output = "";

		for (String a : answer) {
			output += a;
			if (!a.equals(answer[answer.length - 1])) {
				output += ",";
			}
		}

		return "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: " + "["
				+ output + "]\n";
	}
}
