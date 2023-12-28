# CS-LS
Este proyecto se centra en la implementación de varios algoritmos en lenguaje de programación. A continuación, se describen brevemente las funcionalidades y componentes clave de cada uno de los algoritmos implementados:
## Dijkstra
- Este algoritmo se utiliza para encontrar el camino más corto entre dos nodos en un grafo ponderado.
- Se ha implementado basándose en pseudocódigo y estructuras de datos propias.
- Se importa información de dos conjuntos de datos en formato JSON, procesándolos para representar el grafo.
- El algoritmo calcula rutas de manera eficiente, considerando la probabilidad de encontrar enemigos en el camino.
- El resultado del algoritmo proporciona el camino más eficiente y su probabilidad acumulada.
## B-Tree
- Se ha implementado un árbol binario de búsqueda (BST) para gestionar precios de objetos.
- La elección de un BST se basa en la eficiencia, dado un conjunto de datos pequeño.
- Cada objeto se representa como un nodo en el árbol, y se realiza una inserción única, seguida de extracciones constantes.
- El árbol se mantiene balanceado automáticamente para una búsqueda eficiente.
- La implementación incluye dos clases: Object y Node, que manejan objetos y nodos del árbol, respectivamente.
## R-Tree
- Este algoritmo se utiliza para organizar objetos en múltiples dimensiones (2D o 3D).
- La implementación incluye dos clases: BoundingBox y Position.
- BoundingBox actúa como un nodo que contiene otros BoundingBox o Position.
- Position almacena información sobre objetos y permite el cálculo de distancias entre ellos.
- La inserción de objetos se realiza de manera eficiente en función del espacio disponible en las casillas del árbol.
- Se implementa una función de búsqueda para encontrar objetos y una función de eliminación.
## HashMap
- Se ha implementado una tabla hash abierta para gestionar datos de jugadores.
- La elección de un enfoque de tabla hash abierta se basa en el alto número de elementos a manejar.
- Los objetos Player se almacenan en una lista y se asignan a casillas de la tabla hash.
- La implementación incluye una función de búsqueda y una función de inserción eficiente.  

##
Cada uno de estos algoritmos se ha implementado en función de sus requisitos y características específicos, y se han proporcionado tiempos de ejecución aproximados para entender su eficiencia.  
###
@authors: Bernat Segura - Oscar Pellice - Oscar Julian - Sergio Garcia  
@date: 23 de Maig 2020  
@IDE: IntelliJ IDEA  
@SDK: Java 11  
### Librerías externas:
- gson 2.8.5
### JSON:
ConnectionMy.json | ConnectionS.json | ConnectionM.json | ConnectionL.json  
dataset_MapS.json | dataset_ObjectS.json  
RoomS.json | RoomM.json | RoomL.json
HashMapS | HashMapM | HashMapL  

### Execution:
1. Obrir el projecte amb l'IDE IntelliJ
2. Hem de tenir en compte els Dataseets que hem seleccionat.
3. Compilar i executar.
4. Al executar es mostra un menú molt simple on podem escollir qualsevol de les funcionalitats implementades
