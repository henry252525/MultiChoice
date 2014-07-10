GSON=google-gson-2.2.4/gson-2.2.4.jar
CFLAGS=-cp .:$(GSON)

all: Main.java
	javac $(CFLAGS) Main.java

convert: Converter.java
	javac $(CFLAGS) Converter.java

runconvert: Converter.class
	java $(CFLAGS) Converter

run: Main.class
	java $(CFLAGS) Main
