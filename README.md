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
The name comes from game tree searches, where you look some number of moves ahead. You can try to deepen the tree by looking ahead more moves. Once your answer doesn’t change or improve much, you assume that you have a pretty good answer, and it won’t improve. In IDA*, the “depth” is a cutoff for $`f(v)`$ values. When the $`f(v)`$ value is too large, the node won’t even be considered (i.e., it won’t be added to the OPEN set). The first time through you process very few nodes. Each subsequent pass, you increase the number of nodes you visit. If you find that the path improves, then you continue to increase the cutoff; otherwise, you can stop.

### Weighted A*

:ru:  
### Beam search
В основном цикле A* структура данных OPEN хранит все узлы, которые, возможно, потребуется просмотреть, чтобы найти путь. Поиск луча - это вариация A*, которая ограничивает размер набора OPEN. Если набор становится слишком большим, узел с наихудшими шансами дать хороший путь отбрасывается. Одним из недостатков является то, что для этого вы должны отсортировать свой набор, что ограничивает типы структур данных, которые вы выберете.  
Основные шаги:
1. Инициализация:  
- Начальное состояние помещается в множество OPEN (открытых состояний), которое представляет собой некоторое количество наилучших (наиболее перспективных) путей.
2. Поиск:  
- На каждом шаге алгоритма происходит расширение состояний в множестве
OPEN. Для каждого текущего состояния генерируются следующие состояния.
- Состояния оцениваются с использованием эвристической функции и функции стоимости пути, и выбираются наилучшие (наименее затратные) состояния.
3. Ограничение по лучам:  
- Множество OPEN ограничено фиксированным числом состояний, называемых лучами.
- Только наилучшие состояния добавляются в следующую итерацию, что позволяет алгоритму сосредотачиваться на наиболее перспективных вариантах.
4. Повторение:  
- Процесс поиска повторяется до тех пор, пока не будет достигнут конечный узел или выполнено другое критерии остановки.

### Iterative deepening A* (IDA*)
Название происходит от поиск в игровом дереве (Game Tree Search - это метод, применяемый в теории игр и искусственном интеллекте для анализа возможных ходов в игре), при котором вы просматриваете некоторое количество ходов вперед. Вы можете попытаться углубить дерево, рассматривая больше ходов вперед. Когда ваш ответ перестает изменяться или улучшаться значительно, можно считать, что у вас уже есть довольно хороший ответ, и дальнейшее углубление не даст существенных улучшений. В IDA* "глубина" - это пороговое значение $`f(v)`$. Когда значение $`f(v)`$ слишком велико, узел даже не рассматривается (то есть его не добавляют в множество OPEN). Первый проход обрабатывает очень небольшое количество узлов. На каждом последующем проходе увеличивается количество посещаемых узлов. Если вы обнаружите, что путь улучшается, вы продолжаете увеличивать отсечение; в противном случае вы можете остановиться. 
Работа алгоритма:
1. Установка начального предела глубины:
- Начинаем с установки начального предела глубины для поиска.
2. Применение А:*
- Запускаем А* с ограничением глубины на текущей итерации.
- A* использует эвристическую функцию для оценки стоимости пути от начальной точки до текущей точки, а также для оценки оставшейся стоимости до целевой тоЧки.
3. Расширение предела глубины:
- Если А* не находит путь к целевой точке на текущей итерации, увеличиваем предел глубины.
- Повторяем процесс с новым пределом глубины.
4. Повторение:
- Повторяем шаги 2-3 до тех пор, пока не будет найден путь от начальной точки к целевой точке.
5. Завершение:
- Как только путь найден, алгоритм завершается.
IDA* сочетает в себе преимущества алгоритма А* (поиск с использованием эвристической информации) с итеративным углублением, что позволяет эффективно находить оптимальные пути в графах с большим числом состояний.

### Weighted A*

## Sources:
- [Red Blob Games](https://www.redblobgames.com)
- [Алгоритм A*](https://neerc.ifmo.ru/wiki/index.php?title=Алгоритм_A*)
- [Variants of A*](https://theory.stanford.edu/~amitp/GameProgramming/Variations.html)

https://neerc.ifmo.ru/wiki/index.php?title=Алгоритм_A*  

https://www.redblobgames.com


