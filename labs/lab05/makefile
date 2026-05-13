PACKAGE = example
CLASS = App

.PHONY: all hadoop clean

all: scala

scala:
	sbt package

run:
	spark-submit --class $(PACKAGE).$(CLASS) --master yarn ./target/scala-2.11/$(PACKAGE)_2.11-0.1.jar \
		/user/jshin53/input/lineItem \
		/user/jshin53/input/sale \
		/user/jshin53/input/product \
		/user/jshin53/input/store

clean:
	rm -rf project target
