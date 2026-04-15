Useful Commands:
  Remove the output directory:
    hadoop fs -rm -r /user/jshin53/output

  Run the program:
    make run

  See the output file:
    hadoop fs -cat /user/jshin53/output/part-r-00000

  Put files (into the input directory)
    hadoop fs -copyFromLocal localDataFile /user/jshin53/input
