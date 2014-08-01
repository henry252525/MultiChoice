package MultipleChoiceProgram;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class QuestionParser {
	
	public static ArrayList<MultipleChoiceQuestion> parseFile(File file) {
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			return null;
		}
		ArrayList<MultipleChoiceQuestion> questions = new ArrayList<MultipleChoiceQuestion>();
		while(scanner.hasNextLine()) {
			if(QuestionParser.hasNextQuestion(scanner)) {
				questions.add(QuestionParser.parseNextQuestion(scanner));
			} else {
				scanner.nextLine();
			}
		}
		scanner.close();
		return questions;
	}

	private static boolean hasNextQuestion(Scanner scanner) {
		Pattern pattern = Pattern.compile("\\d\\)\\s");
		return scanner.findInLine(pattern) != null;
	}

	private static MultipleChoiceQuestion parseNextQuestion(Scanner scanner) {
		String question = "";
		ArrayList<String> choices = new ArrayList<String>();
		int answerIndex;

		String nextLine;

		nextLine = scanner.nextLine();
		while(!nextLine.startsWith("A) ")) {
			question += nextLine + "\n";
			nextLine = scanner.nextLine();
		}

		while(!nextLine.startsWith("Answer:")) {
			String choice = nextLine.substring(3);
			choices.add(choice);
			nextLine = scanner.nextLine();
		}
		String answer = nextLine.substring(7).trim();
		answerIndex = QuestionParser.getIndexOfLetter(answer);

		MultipleChoiceQuestion questionObj = new MultipleChoiceQuestion(
				question.trim(),
				choices,
				answerIndex
			);
		
		String imgResource = scanner.nextLine();
		//figure is optional
		if(imgResource.startsWith("Figure: ")) {
			imgResource = imgResource.substring(8).trim();
			questionObj.extImagePath = "Figures/" + imgResource + ".png";
		}
		//table is optional
		else if(imgResource.startsWith("Table: ")) {
			imgResource = imgResource.substring(7).trim();
			questionObj.extImagePath = "Tables/" + imgResource + ".png";
		}
		//fact is optional
		else if(imgResource.startsWith("Fact: ")) {
			imgResource = imgResource.substring(6).trim();
			questionObj.extImagePath = "Facts/" + imgResource + ".png";
		}

		return questionObj;
	}

	private static int getIndexOfLetter(String answer) {
		char letter = answer.toUpperCase().charAt(0);
		return letter - 65;
	}
}