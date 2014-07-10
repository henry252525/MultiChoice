import java.util.*;
import java.io.*;
import com.google.gson.*;

public class Converter {
	
	public static void main(String[] args) {
		ArrayList<MultipleChoiceQuestion> questions = new ArrayList<MultipleChoiceQuestion>();
		//questions.addAll(QuestionParser.parseFile(new File("TestCases.dat")));
		//questions.addAll(QuestionParser.parseFile(new File("Ch24.dat")));
		//questions.addAll(QuestionParser.parseFile(new File("Ch25.dat")));
		//questions.addAll(QuestionParser.parseFile(new File("Ch26.dat")));
		questions.addAll(QuestionParser.parseFile(new File("Ch27.dat")));

		System.out.printf("%d Questions Loaded!\n", questions.size());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(questions);
		System.out.println(json);
	}

}