# N-Puzzle
_Solving the N-puzzle game using the A* search algorithm_

### To start the program

  
### Some variations of the A* algorithm  
__Weighted A*:__  
Consider a graph representing different locations on a map, and we are finding a path from point A to point B. If we need to consider different road conditions (e.g., traffic jams or poor road quality), we can use weighted A* to assign different weights to different edges of the graph based on these conditions.  
Допустим, у нас есть граф, представляющий различные местоположения на карте, и мы ищем путь от точки A до точки B. Если нам нужно учесть различные условия дорог (например, пробки или плохое качество дороги), мы можем использовать взвешенный A*, чтобы дать различный вес разным дугам графа в зависимости от этих условий.

__Adaptive A*:__  
Imagine a game where a character moves through a map to reach a goal. During the game, we can adapt the heuristic, considering changing obstacles or dynamic changes in the map to adjust the estimated cost to the goal based on the current conditions.  
Рассмотрим игру, в которой персонаж двигается по карте для достижения цели. В процессе игры мы можем изменять эвристику, например, учитывая изменяющиеся препятствия или динамические изменения на карте, чтобы подстроить оценку стоимости до цели в соответствии с текущими условиями.

__Anytime Repairing A*:__  
Picture a task of route planning for a robot in an unknown environment. At the initial stages of the search, we can quickly find an approximate solution so that the robot can start moving. Gradually, with additional information about the environment, we can refine the route to make it more optimal.  
Представьте задачу планирования маршрутов для робота в неизвестной среде. На начальном этапе поиска мы можем быстро найти приближенное решение для того, чтобы робот мог начать движение. Постепенно, при получении дополнительной информации о среде, мы можем уточнить маршрут, чтобы он стал более оптимальным.

__Real-time A*:__  
Think about autonomous driving. In this case, due to time constraints and the need for continuous response, we can use RTA* to make quick optimal decisions in real-time based on current road data and the surrounding environment.  
Рассмотрим автономное вождение. В этом случае, в силу ограничений времени и необходимости непрерывной реакции, мы можем использовать RTA* для быстрого принятия оптимальных решений в реальном времени на основе текущих данных о дороге и окружающей среде.

__Hierarchical A*:__  
Imagine a pathfinding task in a large and complex map. We can use hierarchical A* to divide the map into smaller parts and search for a path at a more generalized level to reduce the number of states to be considered at each step.  
Представим задачу поиска пути в большой и сложной карте. Мы можем использовать иерархический A*, чтобы разбить карту на более мелкие части и искать путь на более обобщенном уровне, чтобы уменьшить количество состояний, которые нужно рассматривать на каждом шаге.