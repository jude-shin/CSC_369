#!/bin/bash

LOCAL_DIR="/home/jshin53/CSC_369/labs/lab01/app/output"

for name in customer lineItem product sale store
do
		echo "Erasing previous hdfs directory"	
    hadoop fs -rm -r /user/jshin53/input/$name
	
		echo "Creating hdfs directory"
		hadoop fs -mkdir /user/jshin53/input/$name

		echo "Putting local file in new directory"
		hadoop fs -put $LOCAL_DIR/$name /user/jshin53/input/$name/$name
done
