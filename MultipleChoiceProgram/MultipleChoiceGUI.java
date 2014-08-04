package MultipleChoiceProgram;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class MultipleChoiceGUI extends JFrame {
	
	private ArrayList<MultipleChoiceQuestion> questions;
	private JTextArea questionTextArea = new JTextArea();
	private JButton[] choiceButtons = new JButton[5];
	private ImagePanel imagePanel = new ImagePanel();

	private JButton nextButton = new JButton("Next");
	private JButton prevButton = new JButton("Prev");
	private JButton pageButton = new JButton("Go to page");
	private JButton fiftyFiftyButton = new JButton("50/50");

	private JCheckBox randomCB = new JCheckBox("Random?", false);
	private JCheckBox imageFeedbackCB = new JCheckBox("Image Feedback?", true);

	private int currentAnswerIndex;

	private Random r;

	private int currentQuestionIndex = 0;

	private Image wrongImg;

	public MultipleChoiceGUI(ArrayList<MultipleChoiceQuestion> questions) {
		//Assert that questions != null and questions.size > 0
		super("Multiple Choice");
		r = new Random();

		this.layoutGUI();
		this.registerListeners();

		this.questions = questions;
		this.loadQuestion(0);

		this.setVisible(true);
	}

	private void layoutGUI() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel southPanel = new JPanel();
		JPanel centerPanel = new JPanel(new BorderLayout());

		this.setLayout(new BorderLayout());

		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);

		//north
		JScrollPane scrollPane = new JScrollPane(this.questionTextArea);
		northPanel.add(scrollPane, BorderLayout.CENTER);
		//south
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(fiftyFiftyButton);
		southPanel.add(imageFeedbackCB);
		southPanel.add(randomCB);
		southPanel.add(pageButton);
		southPanel.add(prevButton);
		southPanel.add(nextButton);
		//center
		JPanel centerSouthPanel = new JPanel(new GridLayout(5, 1));
		for(int i = 0; i < choiceButtons.length; i++) {
			choiceButtons[i] = new JButton();
			centerSouthPanel.add(choiceButtons[i]);
		}
		centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);
		centerPanel.add(this.imagePanel, BorderLayout.CENTER);

		this.questionTextArea.setLineWrap(true);
		this.questionTextArea.setWrapStyleWord(true);

		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void registerListeners() {
		this.randomCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				MultipleChoiceGUI.this.randomCBAction();
			}
		});

		this.nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultipleChoiceGUI.this.nextButtonAction();
			}
		});

		this.prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultipleChoiceGUI.this.prevButtonAction();
			}
		});

		this.pageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultipleChoiceGUI.this.pageButtonAction();
			}
		});

		for(int i = 0; i < choiceButtons.length; i++) {
			final int index = i;
			choiceButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MultipleChoiceGUI.this.choiceButtonAction(index);
				}
			});
		}

		this.fiftyFiftyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultipleChoiceGUI.this.fiftyFiftyButtonAction();
			}
		});
	}

	private void loadNextQuestion() {
		boolean isRandom = this.randomCB.isSelected();

		int nextQuestion;
		if(isRandom) {
			nextQuestion = this.r.nextInt(questions.size());
		} else {
			if(currentQuestionIndex >= this.questions.size()) {
				JOptionPane.showMessageDialog(this, "You are done!");
				return;
			}
			nextQuestion = this.currentQuestionIndex + 1;
		}
		loadQuestion(nextQuestion);
	}

	private void loadPrevQuestion() {
		if(currentQuestionIndex >= this.questions.size()) {
			JOptionPane.showMessageDialog(this, "You're already on the first!");
			return;
		}
		int nextQuestion = this.currentQuestionIndex - 1;
		loadQuestion(nextQuestion);
	}

	private void loadQuestion(int questionIndex) {
		if(questionIndex < 0 || questionIndex >= questions.size()) {
			//TODO: Perhaps throw an exception?
			return;
		}
		this.currentQuestionIndex = questionIndex;

		MultipleChoiceQuestion question = questions.get(questionIndex);
		this.questionTextArea.setText((questionIndex + 1) + ") " + question.getQuestion());

		ArrayList<String> choices = question.getChoices();
		for(int i = 0; i < choiceButtons.length; i++) {
			if(i < choices.size()) {
				updateButtonChoice(choiceButtons[i], choices.get(i), i);
			} else {
				disableButtonChoice(choiceButtons[i]);
			}
		}
		this.currentAnswerIndex = question.getAnswerIndex();

		Image extImage = question.getExtImage();
		this.imagePanel.setImage(extImage);

		this.fiftyFiftyButton.setEnabled(true);
	}

	private void updateButtonChoice(JButton button, String choice, int index) {
		button.setText((char)(index + 65) + ") " + choice);
		button.setEnabled(true);
	}

	private void disableButtonChoice(JButton button) {
		button.setText("");
		button.setEnabled(false);
	}

	private void nextButtonAction() {
		this.loadNextQuestion();
	}

	private void prevButtonAction() {
		this.loadPrevQuestion();
	}

	private void pageButtonAction() {
		try {
			int questionNumber = Integer.parseInt(this.questionTextArea.getText().trim());
			this.loadQuestion(questionNumber - 1);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Fuck you!");
			return;
		}
	}

	private void randomCBAction() {
		this.prevButton.setEnabled(!randomCB.isSelected());
	}

	private void choiceButtonAction(int index) {
		boolean correctAnswer = index == this.currentAnswerIndex;
		boolean isImageFeedback = this.imageFeedbackCB.isSelected();
		boolean autoNextQuestion;
		String feedbackMessage;
		Image imageFeedback = null;

		if(correctAnswer) {
			feedbackMessage = "Correct!";
			autoNextQuestion = !isImageFeedback;
			if(isImageFeedback) {
				int num = this.r.nextInt(62);
				String path = "Images/correct/%d.gif";
				try {
					ImageIcon corgif;
					try {
						corgif = new ImageIcon(String.format(path, num));
					} catch(Exception e) {
						corgif = new ImageIcon(new URL("http://corgifs.herokuapp.com"));
					}
					imageFeedback = corgif.getImage();
				} catch(Exception e) {
					System.out.println("Unable to load corgif");
				}
			}
		} else {
			feedbackMessage = "Wrong! The correct answer is: " + (char)(this.currentAnswerIndex + 65);
			autoNextQuestion = false;
			if(isImageFeedback && this.wrongImg == null) {
				try {
					this.wrongImg = ImageIO.read(new File("Images/wrong.jpg"));
				} catch(Exception e) {
					System.out.println("Unable to load integrals");
				}
			}
			imageFeedback = this.wrongImg;
		}
		JOptionPane.showMessageDialog(this, feedbackMessage);
		if(autoNextQuestion) {
			this.loadNextQuestion();
			return;
		}
		if(isImageFeedback && imageFeedback != null) {
			this.imagePanel.setImage(imageFeedback);
		}
	}

	public void fiftyFiftyButtonAction() {
		int numChoices = this.questions.get(currentQuestionIndex).getChoices().size();
		int numToKillOff = numChoices / 2;
		int numKilledOff = 0;

		while(numKilledOff < numToKillOff) {
			int toKill = this.r.nextInt(numChoices);
			if(toKill != this.currentAnswerIndex && this.choiceButtons[toKill].isEnabled()) {
				this.choiceButtons[toKill].setEnabled(false);
				numKilledOff++;
			}
		}
		this.fiftyFiftyButton.setEnabled(false);
	}
}