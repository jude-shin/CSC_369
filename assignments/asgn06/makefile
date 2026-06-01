PACKAGE = example
CLASS = App

.PHONY: all hadoop clean scala run showinput showoutput

all: scala

scala:
	git pull
	sbt package

run: scala
	spark-submit --class $(PACKAGE).$(CLASS) --master yarn ./target/scala-2.11/$(PACKAGE)_2.11-0.1.jar

showoutput:
	-hadoop fs -cat /user/jshin53/output/part-r-00000

copyHDFS:
	-hadoop fs -rm -r /user/jshin53/input/asgn06
	hadoop fs -mkdir /user/jshin53/input/asgn06
	hadoop fs -mkdir /user/jshin53/input/asgn06/students
	hadoop fs -put ./local_inputs/students /user/jshin53/input/asgn06/students/students
	hadoop fs -mkdir /user/jshin53/input/asgn06/courses
	hadoop fs -put ./local_inputs/courses /user/jshin53/input/asgn06/courses/courses
	hadoop fs -mkdir /user/jshin53/input/asgn06/taken
	hadoop fs -put ./local_inputs/taken /user/jshin53/input/asgn06/taken/taken

cleanHDFS:
	hadoop fs -rm -r /user/jshin53/input/asgn06

clean:
	rm -rf project target
