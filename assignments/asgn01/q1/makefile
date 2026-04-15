DRIVER_CLASS = MyDriver
JAR = asgn01.jar
QUESTION = q1

# Wildcard these .java files, and the .class files
SRCS := $(wildcard *.java)

.PHONY: all hadoop clean

all: hadoop

hadoop: $(SRCS)
	hadoop com.sun.tools.javac.Main $(SRCS)
	jar cvf $(JAR) *.class

run: $(JAR)
	hadoop jar $(JAR) $(DRIVER_CLASS) /user/jshin53/input/asgn01/$(QUESTION) /user/jshin53/output

cleanall: clean
	rm -rf $(JAR)

clean:
	rm -rf *.class
