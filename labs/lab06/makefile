PACKAGE = example
CLASS = App

.PHONY: all hadoop clean scala run showinput showoutput

all: scala

scala:
	git pull
	sbt package

run: scala
	spark-submit --class $(PACKAGE).$(CLASS) --master yarn ./target/scala-2.11/$(PACKAGE)_2.11-0.1.jar /user/jshin53/input /user/jshin53/output

showoutput:
	-hadoop fs -cat /user/jshin53/output/part-r-00000

showinput:
	-hadoop fs -cat /user/jshin53/input/store/store

clean:
	rm -rf project target
