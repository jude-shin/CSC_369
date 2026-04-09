Goal: Calculate the total number of sales for each day

Use the "sale" data

Mapper's Goal: extract the sale data by date
    lines (containing things like the date)
    ->
    (line number, date)
    ->
    (date, null)
        (Text, NullWriteable)

Reducer's Goal: aggregate each value
    (d1, null)
    (d1, null)
    (d1, null)
    (d1, null)
    ->
    (date, 4)
