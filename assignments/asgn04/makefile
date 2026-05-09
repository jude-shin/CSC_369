PACKAGE = example
CLASS = App

.PHONY: all hadoop clean

all: scala

scala:
	sbt package

run:
	spark-submit --class $(PACKAGE).$(CLASS) --master yarn ./target/scala-2.11/$(PACKAGE)_2.11-0.1.jar

clean:
	rm -rf project target
