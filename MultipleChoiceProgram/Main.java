package MultipleChoiceProgram;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<MultipleChoiceQuestion> questions = new ArrayList<MultipleChoiceQuestion>();
		String[] questionFilenames = Main.getQuestionFilenames();
		
		try {
			Scanner scanner;
			Type listType = new TypeToken<ArrayList<MultipleChoiceQuestion>>() {
	              	      }.getType();
			for(int i = 0; i < questionFilenames.length; i++) {
				scanner = new Scanner(new File(questionFilenames[i]));
				String json = scanner.useDelimiter("\\Z").next();
				scanner.close();

				Gson gson = new Gson();
				ArrayList<MultipleChoiceQuestion> questionsFromFile = gson.fromJson(json, listType);
				questions.addAll(questionsFromFile);
			}
		} catch (Exception e) {
			//TODO: Handle file not found
			System.out.println(e);
			return;
		}

		System.out.printf("%d Questions Loaded!\n", questions.size());
		MultipleChoiceGUI prog = new MultipleChoiceGUI(questions);
	}

	public static String[] getQuestionFilenames() {
		String[] questionFilenames;
		try {
			Scanner scanner = new Scanner(new File("resources/questions.dat"));
			ArrayList<String> questions = new ArrayList<String>();
			while(scanner.hasNextLine()) {
				questions.add(scanner.nextLine().trim());
			}
			questionFilenames = questions.toArray(new String[questions.size()]);
		} catch(Exception e) {
			questionFilenames = new String[] {
				"resources/testcases.json"
			};
		}
		return questionFilenames;
	}
}