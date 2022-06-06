IDE: IntelliJ IDEA
SDK: Java 11
Llibreries externes:
	-gson 2.8.5
Datassets:	(Situats a /data) 
	1: ConnectionMy.json (Document creat per nosaltres com a entorn de proves)
	2: RoomS.json  |   ConnectionS.json
	3: RoomM.json  |   ConnectionM.json
	4: RoomL.json  |   ConnectionL.json
	5: dataset_MapS.json   |   dataset_ObjectS.json
	6: HashMapS    |   HashMapM   |    HashMapL


Execució:
	1. Obrir el projecte amb l'IDE IntelliJ
	2. Hem de tenir en compte els Dataseets que hem seleccionat.
	3. Compilar i executar.
	4. Al executar es mostra un menú molt simple on podem escollir qualsevol de les funcionalitats implementades

Dijkstra:
	Per canviar de Dataseet s'ha de canviar manualment a les línies 76 i 77 de la classe Grafs_System.

RTree:
	Per l'arbre R-Tree hem implementat la funció d'eliminar juntament amb la cerca, quan busquis un objecte en l'arbre aquest s'eliminarà si és trobat.
