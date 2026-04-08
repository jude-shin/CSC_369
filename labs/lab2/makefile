# make runh ARGS="arg1 arg2 arg3"

SRC_DIR = src
OUT_DIR = out
MAIN_CLASS = Main
JAR = lab02.jar

# Wildcard these .java files, and the .class files
SRCS := $(wildcard $(SRC_DIR)/*.java)
CLASSES := $(patsubst $(SRC_DIR)/%.java,$(OUT_DIR)/%.class,$(SRCS))

.PHONY: all jar $(JAR) hadoop clean

all: jar hadoop

jar: $(JAR)

$(JAR): $(CLASSES)
	jar cvf $(JAR) -C $(OUT_DIR) .

$(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	mkdir -p $(OUT_DIR)
	javac -d $(OUT_DIR) $<

hadoop: $(SRCS)
	mkdir -p $(OUT_DIR)
	hadoop com.sun.tools.javac.Main -d $(OUT_DIR) $(SRCS)

runh: $(JAR)
	hadoop jar $(JAR) $(MAIN_CLASS) $(ARGS)

runj: $(JAR)
	jara -cp $(JAR)	$(MAIN_CLASS) $(ARGS)

cleanall: clean
	rm -rf $(JAR)

clean:
	rm -rf $(OUT_DIR)
