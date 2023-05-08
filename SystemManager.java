package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SystemManager implements Manager, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// The exam Map has examId keys, and Exam values
	HashMap<Integer, Exam> examMap = new HashMap<Integer, Exam>();
	// The students arrayList holds Student objects
	ArrayList<Student> students = new ArrayList<Student>();
	// The cutOffs hashMap houses number grades (keys), and letter (values)
	HashMap<Double, String> cutOffs;

	public boolean addExam(int examId, String title) {
		Exam exam = new Exam(examId, title); // Gets an exam

		if (examMap.containsValue(exam)) { // Check if the exam is already in the map
			return false;
		} else {
			examMap.put(examId, exam); // If the exam is not in the map insert it
			return true;
		}
	}

	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		Question trueFalseQuestion = new TrueFalseQuestion(questionNumber, text, points, answer);
		if (examMap.containsKey(examId)) { // Use the examID to check if the exam is in the map
			examMap.get(examId).addQuestion(trueFalseQuestion); // Add the question to the exam
		}
	}

	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		Question multipleChoiceQuestion = new MultipleChoiceQuestion(questionNumber, text, points, answer);
		if (examMap.containsKey(examId)) { // Add the question to the exam if the exam is not in the map
			examMap.get(examId).addQuestion(multipleChoiceQuestion);
		}
	}

	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		Question fillInTheBlank = new FillInBlankQuestion(questionNumber, text, points, answer);
		if (examMap.containsKey(examId)) { // Adds the question to the exam if exam is not in map
			examMap.get(examId).addQuestion(fillInTheBlank);
		}
	}

	public String getKey(int examId) {
		Exam answerKey = examMap.get(examId); // Gets an exam
		String output = "";

		for (Question question : answerKey.questions.values()) {
			output += question.toString(); // Gets Question answers for every value in questions HashMap
		}
		return output; // Return String with key of all the questions
	}

	public boolean addStudent(String name) {
		Student student = new Student(name); // Creates a new student

		for (Student studentName : this.students) {
			if (studentName.getName().equals(name)) {
				return false;
			}
		}
		students.add(student); // adds student to arrayList if they don't exist already

		return true;
	}

	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		Exam exam = examMap.get(examId); // gets the correct exam
		TrueFalseQuestion question = (TrueFalseQuestion) exam.questions.get(questionNumber);
		// makes a question variable equal to the question value in the questions
		// HashMap of Exam
		question.studentAnswerKey.put(studentName, answer);
		// puts the student answer in the HashMap of student answers in the
		// TrueFalseQuestion class
	}

	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Exam exam = examMap.get(examId); // gets the correct exam
		MultipleChoiceQuestion question = (MultipleChoiceQuestion) exam.questions.get(questionNumber);
		// Sets the question to the exact question value in the questions HashMap in
		// exam
		question.studentAnswerKey.put(studentName, answer);
		// puts the student answer in the HashMap of student answers in the
		// MultipleChoiceQuestion class
	}

	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Exam exam = examMap.get(examId); // gets the correct exam
		FillInBlankQuestion question = (FillInBlankQuestion) exam.questions.get(questionNumber);
		// makes a question variable equal to the question value in the questions
		// HashMap of Exam
		question.studentAnswerKey.put(studentName, answer);
		// puts the student answer in the HashMap of student answers in the
		// MultipleChoiceQuestion class
	}

	public double getExamScore(String studentName, int examId) {
		Exam exam = examMap.get(examId); // Gets the correct exam
		double score = 0; // Keeps track of the score for a student

		for (Integer questionNumber : exam.questions.keySet()) { // iterates through the questions HashMap in exam
			if (exam.questions.get(questionNumber) instanceof TrueFalseQuestion) {
				TrueFalseQuestion question = (TrueFalseQuestion) exam.questions.get(questionNumber);
				// sets the question value to the exact question value in HashMap of the Exam
				// class
				if (question.studentAnswerKey.get(studentName) == question.getAnswer()) {
					score += question.points; // adds points if the student answer is the correct answer
				}
			} else if (exam.questions.get(questionNumber) instanceof MultipleChoiceQuestion) {
				MultipleChoiceQuestion question = (MultipleChoiceQuestion) exam.questions.get(questionNumber);
				// sets the question value to the exact question value in HashMap of the Exam
				// class
				if (Arrays.deepEquals(question.studentAnswerKey.get(studentName), question.getAnswer())) {
					// checks if each element in the student answer key and student answer arrays is
					// the same
					score += question.points; // adds points if the student answer is the correct answer
				}
			} else if (exam.questions.get(questionNumber) instanceof FillInBlankQuestion) {
				double count = 0; // counts for partials credit
				FillInBlankQuestion question = (FillInBlankQuestion) exam.questions.get(questionNumber);
				// sets the question value to the exact question value in HashMap of the Exam
				// class
				List<String> correctAnswers = question.getAnswer(); // makes a list of correct Answers
				for (String studentAnswer : question.studentAnswerKey.get(studentName)) {
					if (correctAnswers.contains(studentAnswer)) {
						count++; // adds to the count for each element in the String array for
						// the student that is correct
					}
				}
				score += (count / correctAnswers.size()) * question.points;
				// Gets the score for a FillInBlankQuestion
			}
		}
		return score; // gets the final score
	}

	public String getGradingReport(String studentName, int examId) {
		String output = "";
		double studentScore = 0.0;

		Exam exam = examMap.get(examId); // Gets the correct exam
		// Males an arrayList of questionNumbers from the questions HashMap in the exam
		// class
		ArrayList<Integer> questionNumbers = new ArrayList<Integer>(exam.questions.keySet());
		Collections.sort(questionNumbers); // sorts the question numbers

		for (Integer questionNumber : questionNumbers) { // iterates through questionNumbers
			output += "Question #" + questionNumber + " "; // prints question numbers
			if (exam.questions.get(questionNumber) instanceof TrueFalseQuestion) {
				// checks if a questionNumber is a true/false question
				TrueFalseQuestion question = (TrueFalseQuestion) exam.questions.get(questionNumber);
				if (question.studentAnswerKey.get(studentName) == question.getAnswer()) {
					output += question.points; // adds points the String output
					studentScore += question.points; // adds points to the score
				} else {
					output += "0.0";
				}
				output += " points out of " + question.points + "\n";
			} else if (exam.questions.get(questionNumber) instanceof MultipleChoiceQuestion) {
				// checks if a questionNumber is a true/false question
				MultipleChoiceQuestion question = (MultipleChoiceQuestion) exam.questions.get(questionNumber);
				if (Arrays.deepEquals(question.studentAnswerKey.get(studentName), question.getAnswer())) {
					// checks if each element in the multiple choice question is correct
					output += question.points;
					studentScore += question.points;
				} else {
					output += "0.0";
				}
				output += " points out of " + question.points + "\n";
			} else if (exam.questions.get(questionNumber) instanceof FillInBlankQuestion) {
				double count = 0;
				FillInBlankQuestion question = (FillInBlankQuestion) exam.questions.get(questionNumber);
				// checks if a questionNumber is a fillInBlank question
				List<String> correctAnswers = question.getAnswer(); // makes String list of each correct answer for a
																	// question
				for (String studentAnswer : question.studentAnswerKey.get(studentName)) {
					// iterates through the student answers in the studentAnswerKey HashMap in the
					// FillInBlankQuestion Class
					if (correctAnswers.contains(studentAnswer)) {
						count++; // for each partially correct answer in a question, add to the count
					}
				}
				output += count / correctAnswers.size() * question.points + " points out of " + question.points + "\n";
				studentScore += count / correctAnswers.size() * question.points;
				// gets the correct score
			}

		}

		output += "Final Score: " + studentScore + " out of " + totalExamScore(examId);

		return output;
	}

	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.cutOffs = new HashMap<Double, String>(); // initializes the cutOffs HashMap

		for (int i = 0; i < cutoffs.length; i++) {
			this.cutOffs.put(cutoffs[i], letterGrades[i]); // puts the grades and cutOffs in the map
		}
	}

	public double getCourseNumericGrade(String studentName) {
		double totalExamAverage = 0.0;

		for (Integer examId : examMap.keySet()) {
			totalExamAverage += this.getExamScore(studentName, examId) / totalExamScore(examId);
		} // gets the ExamAvg by taking the exam score of a student and dividing by the
			// total exam score

		return totalExamAverage / examMap.keySet().size() * 100;
	}

	public String getCourseLetterGrade(String studentName) {
		double studentGrade = getCourseNumericGrade(studentName);
		// Create an ArrayList of numeric cutoffs (can't do maps, only sets)
		ArrayList<Double> cutoffs = new ArrayList<Double>(this.cutOffs.keySet());
		// Constructor of arrayList lets the keySetVallues (numeric cutoffs) be placed
		// in an ArrayList
		Collections.sort(cutoffs);
		// Want list to be highest to lowest instead of lowest to highest
		Collections.reverse(cutoffs);
		for (double cutoff : cutoffs) {
			// Want studentGrade to be higher than cutoffs, if it's not higher, get the
			// grade below the cutoff
			if (studentGrade >= cutoff) {
				return this.cutOffs.get(cutoff);
			}
		}
		// If the student grade is below all cutoffs
		return "F";
	}

	public String getCourseGrades() {
		String output = "";
		ArrayList<String> studentNames = new ArrayList<String>();

		for (Student student : students) {
			studentNames.add(student.getName()); // Adds students to arrayList
		}

		Collections.sort(studentNames, String.CASE_INSENSITIVE_ORDER); // Sorts students by name

		for (String names : studentNames) {
			output += names + " " + this.getCourseNumericGrade(names) + " " + this.getCourseLetterGrade(names) + "\n";
		} // Helps print Student names and grades

		return output;
	}

	public double getMaxScore(int examId) {
		double maxScore = 0;

		for (Student student : students) {
			double currScore = this.getExamScore(student.getName(), examId);

			if (currScore > maxScore) {
				maxScore = currScore; // Iterate through each student, if the current
				// score is higher than the max, make it the new max
			}
		}

		return maxScore; // returns the max score
	}

	public double getMinScore(int examId) {
		double minScore = Double.MAX_VALUE; // set the min to a large value

		for (Student student : students) {
			double currScore = this.getExamScore(student.getName(), examId);

			if (currScore < minScore) {
				minScore = currScore; // Iterate through each student, if the current
				// student has a score less than the min, make it the new min
			}
		}
		return minScore; // return the min
	}

	public double getAverageScore(int examId) {
		double totalScore = 0.0;

		for (Student student : students) {
			totalScore += this.getExamScore(student.getName(), examId);
		} // get the exam score of each student

		return totalScore / students.size(); // divide the score of each student by students

	}

	public void saveManager(Manager manager, String fileName) {
		File file = new File(fileName); // makes a new file
		try { // handles exception thrown
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Manager restoreManager(String fileName) {
		File file = new File(fileName); // makes a new file
		try { // handles exception thrown
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			Manager manager = (Manager) input.readObject();
			input.close();
			return manager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public double totalExamScore(int examId) {
		double totalScore = 0.0;
		for (Question question : examMap.get(examId).questions.values()) { // iterate through each question
			totalScore += question.points; // add the points of each question in the exam
		}
		return totalScore; // returns the total score
	}
}
