# N-Puzzle
_Solving the N-puzzle game using the A* search algorithm_

## To start the program
- Build the project  
`make`  


- Run the program with a puzzle from a file  
`make run FILE=[file_name]`  


- Run with randomly generated 3x3 puzzle  
`make puzzle`  


- Run with puzzle according to the specified parameters  
`make puzzle FLAGS='[-s] [-u] [-i]' SIZE='[size]`  
`size` — Size of the puzzle's side (3 by default)  
`-s` — Forces generation of a solvable puzzle  
`-u` — Forces generation of an unsolvable puzzle  
`-i` — Number of passes


- Delete all compiled and generated files  
`make clean`  


- Rebuild the project  
`make re`  


- Rebuild the project and run with randomly generated 3x3 puzzle (also possible to specify parameters as in `make puzzle`)  
`make re-puzzle`

## Heuristics
To choose the heuristic function, in the file `heuristic.properties` change the value of key `heuristic` to `manhattan`, `hamming` or `euclidean`  

:gb:
### Manhattan distance (Taxicab, City-Block)
### Hamming distance (Misplaced Tiles)
### Euclidean Distance 

:ru:  
### Манхэттенское расстояние (Manhattan distance, Taxicab, City-Block)
Эта эвристика определяет общее смещение всех плиток от их правильных позиций по вертикали и горизонтали (то есть количество ходов от текущей до целевой позиции для каждой плитки). Сумма этих расстояний для всех плиток дает оценку общего расстояния между текущим и целевым состоянием.
### Количество неправильных плиток (Hamming distance, Misplaced Tiles)
Подсчитывает количество плиток, находящихся не на своих местах в текущем состоянии пазла. Чем больше неправильных плиток, тем дальше текущее состояние от целевого.
### Евклидово расстояние (Euclidean Distance)
В этой эвристике используется евклидово расстояние между текущим положением плитки и ее целевым положением в двумерном пространстве. Эта эвристика может быть менее информативной для задачи N-пазл, чем «Манхэттенское расстояние».  
Евклидова эвристика для N-puzzle определяется как квадратный корень суммы квадратов разницы между координатами плиток в текущем и целевом состоянии. То есть:
h = sqrt((x1 - x2)^2 + (y1 - y2)^2)
  
## Some variations of the A* algorithm
:gb:  
__A* (A-star) - A* (А-звезда)__  
__B* (B-star) - B* (B-звезда)__  
__D* (Dynamic A*) - D* (Динамический A*)__  
__RTA* (Real-time A*) - RTA* (A* в реальном времени)__  
__Adaptive A* - Адаптивный A*__  
__Anytime Repairing A* - A* с постоянным улучшением__  
__Lifelong Planning A* - A* для долгосрочного планирования__  
__A* with limited memory - A* с ограниченной памятью__  
__Parallel A* - Параллельный A*__

:ru:  

https://theory.stanford.edu/~amitp/GameProgramming/Variations.html
https://github.com/mharriso/school21-checklists/blob/master/ng_algo_n-puzzle.pdf