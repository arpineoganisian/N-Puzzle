# N-Puzzle
_Solving the N-puzzle game using the A* search algorithm_

## To start the program
- Build the project  
`
make
`  


- Run the program with a puzzle from a file  
`
make run FILE=[file_name]
`  


- Run with randomly generated 3x3 puzzle  
`
make puzzle
`  


- Run with generated puzzle  
`
make puzzle [-s] [-u] [-i ITERATIONS] size
`  
positional arguments:  
size — Size of the puzzle's side  
optional arguments:  
-s — Forces generation of a solvable puzzle  
-u — Forces generation of an unsolvable puzzle  
-i ITERATIONS — Number of passes


- Delete all compiled and generated files  
`
make clean
`  


- Rebuild the project  
`
make re
`  


- Rebuild the project and run with generated puzzle  
`
make re-puzzle
`

## Heuristic functions
To choose the heuristic function, in the file `heuristic.properties` change the value of key `heuristic` to `manhattan`, `hamming` or `euclidean`  

:us:  

:ru:  
Эвристика «Количество неправильных пазлов» (Misplaced Tiles, Hamming): Эта эвристика подсчитывает количество плиток, находящихся не на своих местах в текущем состоянии пазла. Чем больше неправильных плиток, тем дальше текущее состояние от целевого.

Эвристика «Манхэттенское расстояние» (Manhattan Distance, Taxicab, City-Block): Эта эвристика определяет общее смещение всех плиток от их правильных позиций по вертикали и горизонтали. Сумма этих расстояний для всех плиток дает оценку общего расстояния между текущим и целевым состоянием.

Эвристика «Евклидово расстояние» (Euclidean Distance): В этой эвристике используется евклидово расстояние между текущим положением плитки и ее целевым положением в двумерном пространстве. Эта эвристика может быть менее информативной для задачи N-пазл, чем «Манхэттенское расстояние».  
Евклидова эвристика (иногда называемая евклидовым расстоянием) - это метод оценки эвристического расстояния в алгоритме поиска пути, таком как A*. Это измеряет прямое расстояние между текущей позицией и целевой позицией в пространстве состояний.
В контексте задачи N-пазлов (N-puzzle), Евклидова эвристика измеряет евклидово расстояние между текущим расположением плиток и целевым расположением плиток в пазле. Формально, для N-пазлов, Евклидова эвристика определяется как квадратный корень суммы квадратов разницы между координатами плиток в текущем и целевом состоянии.
Если (x1, y1) - текущие координаты плитки, и (x2 , y2) - целевые координаты плитки, то Евклидова эвристика для каждой плитки может быть рассчитана следующим образом:
h = sqrt((x1 - x2)^2 + (y1 - y2)^2)
  
## Some variations of the A* algorithm
:us:  
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