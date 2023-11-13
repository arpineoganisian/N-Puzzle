# N-Puzzle
_Solving the N-puzzle game using the A* search algorithm_

## To start the program
- Build the project  
`make`  


- Run the program with a puzzle from a file  
`make run FILE=[file_name]`  


- Run with randomly generated 3x3 puzzle (rebuilds the program)  
`make puzzle`  


- Run with puzzle according to the specified parameters (rebuilds the program)  
`make puzzle FLAGS='[-s] [-u] [-i]' SIZE='[size]`  
`size` — Size of the puzzle's side (3 by default)  
`-s` — Forces generation of a solvable puzzle  
`-u` — Forces generation of an unsolvable puzzle  
`-i` — Number of passes


- Delete all compiled and generated files  
`make clean`  


- Rebuild the project (without running it)  
`make re`

## Heuristics
To choose the heuristic function, in the file `heuristic.properties` change the value of key `heuristic` to:  
`manhattan`, `hamming` or `euclidean`  

### :gb: Manhattan distance (Taxicab, City-Block)
- Manhattan distance is a measure of the distance between two points in a grid-based
system (like a city grid) measured along the grid lines.
- It is named "Manhattan distance" because it reflects the distance a car would travel on a
grid of streets in Manhattan, where travel can only occur along north-south and east-west paths.
- The formula for Manhattan distance between two points $`(x_{1}, y_{1})`$ and $`(x_{2}, y_{2})`$ is given by: $`|x_{2} - x_{1}| + |y_{2} - y_{1}|`$    
### Hamming distance (Misplaced Tiles)
- It counts the number of positions at which the corresponding symbols (tiles) are different.
### Euclidean Distance 
- Euclidean distance is a measure of the straight-line distance between two points in Euclidean space.
- It is derived from the Pythagorean theorem and represents the length of the shortest path between two points.
- For two points $`(x_{1}, y_{1})`$ and $`(x_{2}, y_{2})`$ the Euclidean distance is given by: $`\sqrt{(x_{2} - x_{1})^2 + (y_{2} - y_{1})^2}`$

### :ru: Манхеттенское расстояние (Manhattan distance, Taxicab, City-Block)
- Манхеттенское расстояние представляет собой расстояние между двумя точками в системе на основе сетки.
- Оно названо "Манхеттенским расстоянием", потому что отражает расстояние, которое машина проезжает по сетке улиц в Манхэттене, где движение возможно только вдоль путей север-юг и запад-восток.
- Манхеттенского расстояния между двумя точками $`(x_{1}, y_{1})`$ и $`(x_{2}, y_{2})`$ вычесляется по формуле: $`|x_{2} - x_{1}| + |y_{2} - y_{1}|`$
### Количество неправильных плиток (Hamming distance, Misplaced Tiles)
- Подсчитывает количество плиток, находящихся не на своих местах в текущем состоянии пазла. Чем больше неправильных плиток, тем дальше текущее состояние от целевого.
### Евклидово расстояние (Euclidean Distance)
- Прямолинейное расстояния между двумя точками в евклидовом пространстве.
- Оно происходит из теоремы Пифагора и представляет собой длину кратчайшего пути между двумя точками.
- Для двух точек $`(x_{1}, y_{1})`$ и $`(x_{2}, y_{2})`$ Евклидово расстояние вычисляется по формуле: $`\sqrt{(x_{2} - x_{1})^2 + (y_{2} - y_{1})^2}`$

## A* algorithm

During the algorithm execution, the function $`f(v)=g(v)+h(v)`$ is computed for vertices
- $`g(v)`$ represents the minimal cost of the path to vertex $`v`$ from the starting vertex
- $`h(v)`$ is the heuristic estimation of the cost from vertex $`v`$ to the final goal
In essence, the function $`f(v)`$ denotes the length of the path to the goal, composed of the traversed distance $`g(v)`$ and the remaining distance $`h(v)`$. The smaller the value of $`f(v)`$, the earlier we will explore the vertex $`v`$, as it is assumed to reach the goal distance more efficiently. Vertices explored by the algorithm can be stored in a priority queue based on the value of $`f(v)`$. A* operates similarly to Dijkstra's algorithm, examining routes to the goal in order of their current optimality based on available information (heuristic function).

В процессе работы алгоритма для вершин рассчитывается функция $`f(v)=g(v)+h(v)`$ 
- $`g(v)`$ - наименьшая стоимость пути в $`v`$ из стартовой вершины, в нашем контексте - количество ходов (передвежений)
- $`h(v)`$ - эвристическое приближение стоимости пути от $`v`$ до конечной цели, в нашем контексте - 3 эвристические функции на выбор
Фактически, функция $`f(v)`$ — длина пути до цели, которая складывается из пройденного расстояния $`g(v)`$ и оставшегося расстояния $`h(v)`$. Исходя из этого, чем меньше значение $`f(v)`$, тем раньше мы откроем вершину $`v`$, так как через неё мы предположительно достигнем расстояние до цели быстрее всего. Открытые алгоритмом вершины можно хранить в очереди с приоритетом по значению $`f(v)`$. А* действует подобно алгоритму Дейкстры и просматривает среди всех маршрутов ведущих к цели сначала те, которые благодаря имеющейся информации (эвристическая функция) в данный момент являются наилучшими.
  
## Some variations of the A* algorithm
:gb:  
### Beam search
In the main A* loop, the OPEN set stores all the nodes that may need to be searched to find a path. The Beam Search is a variation of A* that places a limit on the size of the OPEN set. If the set becomes too large, the node with the worst chances of giving a good path is dropped. One drawback is that you have to keep your set sorted to do this, which limits the kinds of data structures you’d choose.

### Iterative deepening A* (IDA*)


:ru:  
### Beam search
В основном цикле A* структура данных OPEN хранит все узлы, которые, возможно, потребуется выполнить поиск, чтобы найти путь. Поиск луча - это вариация A*, которая ограничивает размер набора OPEN. Если набор становится слишком большим, узел с наихудшими шансами дать хороший путь отбрасывается. Одним из недостатков является то, что для этого вы должны отсортировать свой набор, что ограничивает типы структур данных, которые вы выберете.









__A* (A-star) - A* (А-звезда)__  
__B* (B-star) - B* (B-звезда)__  
__D* (Dynamic A*) - D* (Динамический A*)__  
__RTA* (Real-time A*) - RTA* (A* в реальном времени)__  
__Adaptive A* - Адаптивный A*__  
__Anytime Repairing A* - A* с постоянным улучшением__  
__Lifelong Planning A* - A* для долгосрочного планирования__  
__A* with limited memory - A* с ограниченной памятью__  
__Parallel A* - Параллельный A*__



https://theory.stanford.edu/~amitp/GameProgramming/Variations.html
https://github.com/mharriso/school21-checklists/blob/master/ng_algo_n-puzzle.pdf
https://neerc.ifmo.ru/wiki/index.php?title=Алгоритм_A*

