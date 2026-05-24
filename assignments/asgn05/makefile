PACKAGE = example
CLASS = App

.PHONY: all hadoop clean scala run showinput showoutput

all: scala

scala:
	git pull
	sbt package

run: scala
	spark-submit --class $(PACKAGE).$(CLASS) --master yarn ./target/scala-2.11/$(PACKAGE)_2.11-0.1.jar
	# spark-submit --class $(PACKAGE).$(CLASS) --master yarn ./target/scala-2.11/$(PACKAGE)_2.11-0.1.jar /user/jshin53/input /user/jshin53/output

showoutput:
	-hadoop fs -cat /user/jshin53/output/part-r-00000

showinput:
	-hadoop fs -cat /user/jshin53/input/store/store

copyHDFS:
	-hadoop fs -rm -r /user/jshin53/input/asgn05
	hadoop fs -mkdir /user/jshin53/input/asgn05
	hadoop fs -put ./local_inputs/q1_integers /user/jshin53/input/asgn05/q1_integers
	hadoop fs -put ./local_inputs/q2_employees /user/jshin53/input/asgn05/q2_employees
	hadoop fs -put ./local_inputs/q2_departments /user/jshin53/input/asgn05/q2_departments
	hadoop fs -put ./local_inputs/q3_students /user/jshin53/input/asgn05/q3_students

cleanHDFS:
	hadoop fs -rm -r /user/jshin53/input/asgn05

clean:
	rm -rf project target
