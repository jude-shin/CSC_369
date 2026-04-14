Goal: Calculate the total number of sales for each day

Use the "sale" data

Mapper's Goal: extract the sale data by date
    lines (containing things like the date)
    ->
    (line number, date)
    
    a date and it's frequency (for combiner compatibility)
    ->
    (date, int)
        (Text, IntWritable)

Reducer's Goal: aggregate each value
    (d1, 1)
    (d1, 1)
    (d1, 1)
    (d1, 1)
    ->
    (date, 4)
