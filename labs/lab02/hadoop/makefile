SRC_DIR = src
OUT_DIR = out
DRIVER_CLASS = SalesDriver
JAR = lab02.jar

# Wildcard these .java files, and the .class files
SRCS := $(wildcard $(SRC_DIR)/*.java)
CLASSES := $(patsubst $(SRC_DIR)/%.java,$(OUT_DIR)/%.class,$(SRCS))

.PHONY: all hadoop clean

all: hadoop

hadoop: $(SRCS)
	mkdir -p $(OUT_DIR)
	hadoop com.sun.tools.javac.Main -d $(OUT_DIR) $(SRCS)
	jar cvf $(JAR) $(CLASSES)

run: $(JAR)
	hadoop jar $(JAR) $(DRIVER_CLASS) /user/jshin53/input/sale /user/jshin53/output

cleanall: clean
	rm -rf $(JAR)

clean:
	rm -rf $(OUT_DIR)
