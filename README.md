# EsercizioTaboo

In this implementation the initial solution was chosen by ordering the jobs from the the one with the closest deadline first giving the solution [4, 1, 2, 3, 5, 6] with a value of 26.

The move is based on bubbling down the sequence the job which completes earliest in respect to his deadline.

The taboo in this case is the fact that the job which has been moved down cannot be moved again for n moves, where n is the taboo tenure, set to 4 in this implementation.

The implementation performs 300 iterations: every 30 iterations the solution is randomized to explore more of the solution space, diversifying the exploration.

The best solution found in most cases has a value between 19 and 23.
