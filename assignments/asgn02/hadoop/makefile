DRIVER_CLASS = MyDriver
JAR = lab03.jar

# Wildcard these .java files, and the .class files
SRCS := $(wildcard *.java)

.PHONY: all hadoop clean

all: hadoop

hadoop: $(SRCS)
	hadoop com.sun.tools.javac.Main $(SRCS)
	jar cvf $(JAR) *.class

run: $(JAR)
	hadoop jar $(JAR) $(DRIVER_CLASS) /user/jshin53/input/sale /user/jshin53/output

cleanall: clean
	rm -rf $(JAR)

clean:
	rm -rf *.class
