package MultipleChoiceProgram;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class MultipleChoiceQuestion {

	private String question;
	private ArrayList<String> choices;
	private int answerIndex;
	private String extImagePath;

	public MultipleChoiceQuestion(
			String question,
			ArrayList<String> choices,
			int answerIndex
		) {
		this.question = question;
		this.choices = choices;
		this.answerIndex = answerIndex;
	}

	public String getQuestion() {
		return this.question;
	}

	public ArrayList<String> getChoices() {
		return this.choices;
	}

	public int getAnswerIndex() {
		return this.answerIndex;
	}

	public Image getExtImage() {
		Image extImage = null;
		try { // TODO: create a service to cache images
			extImage = ImageIO.read(new File(this.extImagePath));
		} catch (Exception e) {
		}
		return extImage;
	}

	public String toString() {
		return String.format("%s\n %d, %d", question, choices.size(), answerIndex);
	}
}