#!/bin/bash

LOCAL_DIR="/home/jshin53/CSC_369/labs/lab01/app/output"

for name in customer lineItem product sale store
do
    # Erase the directory for simplicity
    hadoop fs -rm -r /user/jshin53/input/$name

    # Copy over the file
    hadoop fs -copyFromLocal $LOCAL_DIR/$name /user/jshin53/input/$name
done
