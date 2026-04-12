The java program (run on my laptop) took only 55 milliseconds on average to run
completely, reading from the input file, and writing to the output file.

The hadoop program took an average of 15000 milliseconds to complete the job,
starting from when the job first started processing, till the time the reduce
step finished 100% completion. (I am not tracking from when the job was
submitted, but from when the logs said the map function started. 

I think this is because the overhead it takes to distribute, map, and reduce the
data is not worth it, since we are only processing 2000 lines of data. It would
be interesting to see what the threshold would be for this map/reduce solution 
to be worth it.
