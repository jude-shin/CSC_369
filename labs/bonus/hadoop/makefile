DRIVER_CLASS = MyDriver
JAR = bonus.jar
OUTPUTS = /user/jshin53/firstJoin /user/jshin53/secondJoin /user/jshin53/thirdJoin /user/jshin53/output

# Wildcard these .java files, and the .class files
SRCS := $(wildcard *.java)

.PHONY: all hadoop clean

all: hadoop

hadoop: $(SRCS)
	hadoop com.sun.tools.javac.Main $(SRCS)
	jar cvf $(JAR) *.class

# ("usage: <lineItemPath> <salePath> <productPath> <storePath> <firstJoin> <secondJoin> <thirdJoin> <output>");
run: $(JAR)
	# Remove the output and temp directories
	-hadoop fs -rm -r $(OUTPUTS)

	# Execute the commands
	hadoop jar $(JAR) $(DRIVER_CLASS) \
		/user/jshin53/input/lineItem\
		/user/jshin53/input/sale \
		/user/jshin53/input/product \
		/user/jshin53/input/store \
		$(OUTPUTS)

cleanall: clean
	rm -rf $(JAR)

clean:
	rm -rf *.class
