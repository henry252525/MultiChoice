import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<MultipleChoiceQuestion> questions = new ArrayList<MultipleChoiceQuestion>();
		if(args.length > 0) {
			try {
				for(int i = 0; i < args.length; i++) {
					int chapter = Integer.parseInt(args[i]);
					questions.addAll(QuestionParser.parseFile(
							new File("Ch" + chapter + ".dat"))
						);
				}
			} catch(Exception e) {
				System.out.println(
						"Invalid arguments: Arguments should be a list of chapters. " +
						"Right now, only chapters 24, 25, and 26 are supported."
					);
				System.exit(0);
			}
		} else {
			try {
				Scanner scanner = new Scanner(new File("26"));
				String json = scanner.useDelimiter("\\Z").next();
				scanner.close();
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<MultipleChoiceQuestion>>() {
		              	      }.getType();
				ArrayList<MultipleChoiceQuestion> q26 = gson.fromJson(json, listType);
				questions.addAll(q26);
			} catch (Exception e) {
				//TODO: Handle file not found
				return;
			}
		}

		System.out.printf("%d Questions Loaded!\n", questions.size());

		MultipleChoiceGUI prog = new MultipleChoiceGUI(questions);
	}

}