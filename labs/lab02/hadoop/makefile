SRC_DIR = src
DRIVER_CLASS = SalesDriver
JAR = lab02.jar

# Wildcard these .java files, and the .class files
SRCS := $(wildcard $(SRC_DIR)/*.java)

.PHONY: all hadoop clean

all: hadoop

hadoop: $(SRCS)
	hadoop com.sun.tools.javac.Main -d *.class $(SRCS)
	jar cvf $(JAR) $(CLASSES)

run: $(JAR)
	hadoop jar $(JAR) $(DRIVER_CLASS) /user/jshin53/input/sale /user/jshin53/output

cleanall: clean
	rm -rf $(JAR)

clean:
	rm -rf *.class
