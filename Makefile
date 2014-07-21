PKG=MultipleChoiceProgram
LIB=libraries
GSON=$(LIB)/google-gson-2.2.4/gson-2.2.4.jar
CFLAGS=-cp .:$(GSON)

all: $(PKG)/Main.java
	javac $(CFLAGS) MultipleChoiceProgram/Main.java
	jar cfm MultipleChoiceProgram.jar metadata/Manifest.txt $(PKG)/*.class

convert: $(PKG)/Converter.java
	javac $(CFLAGS) $(PKG)/Converter.java

runconvert: $(PKG)/Converter.class
	java $(CFLAGS) Converter

run: MultipleChoiceProgram.jar
	java -jar MultipleChoiceProgram.jar

clean:
	$(RM) $(PKG)/*.class
