# Motivation
I took a course in University where the exam consisted mostly of multiple choice questions. Somewhere along the term, I managed to acquire a question bank consisting of 1000+ questions. The question bank covered a large fraction of the content covered in this course and was therefore a very good study resource. Unfortunately, the format of this question bank was a collection of MS Word documents. This program was created to fix that.

# MultiChoice
MultiChoice is a Java swing program used to spit out multiple choice questions saved as JSON files. It contains two major components:

1. A parser that converts the original MS Word document to a JSON
2. A Java swing GUI used to display the questions

Compilation and reading of the JSON files used Google's GSON library.

## MS Word to JSON Parser
This is an unimportant but crucial part of the program. There was a lot of "hacking" done in this component since the parser required specifically for this one use case (see Motivation). No long-term engineering decisions were considered for this part of the project.

The MS Word document was very poorly formatted and only had a certain degree of consistently. To simplify the parsing process, the content of the MS Word document was copied and pasted into a text editor. This allowed me to avoid using any libraries to read from the MS Word document directly. This also means that any figures and tables that were included in the document was also lost.

Luckily, there were at most roughly ten figures and tables per document. As such, these were recovered manually by skimming through the document and saving them as images. In order to recover the correct references for the questions, I simply analyzed question text and added an appropriate reference each time I see the phrase "Figure x.x" or "Table x.x" where `x` is a number.

## Java Swing GUI
This is the main component of this project. For each question, the GUI displays

* Question Text
* Relevant Table or Figure
* Multiple Choice Answers
* Options including:
  * 50/50
  * Crazy Automatic Answer Mode
  * Image Feedback
  * Random

The *50/50 option* removes half the of the multiple choice answers (excluding the correct answer, of course). The *Crazy Automatic Answer Mode* grays out all the incorrect answer to allow the user to become familiar with only the correct answer (on the event that the question bank reflect exactly what's going to be on the exam). The *Image Feedback* option displays varying images as a feedback to the user depending on whether you answered the question correctly. Lastly, the *Random* option is used to randomize the order of the questions.
