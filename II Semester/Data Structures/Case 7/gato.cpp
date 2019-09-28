#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
#include <ctime>

#define fromIndex(i, j) (i*3 + j)

using namespace std;


// Encuentra las combinaciones de tamaño K de 0 a N usando bitmasks.
// Estos resultados se usaran para simular los indices de un array.
vector<vector<int>> comb(int N, int K) {
    vector<vector<int>> resultado;
    string bitmask(K, 1);
    bitmask.resize(N, 0);
    do {
        vector<int> temp;
        for (int i = 0; i < N; ++i) {
            if (bitmask[i]) {
                temp.push_back(i);
            }
        }
        resultado.push_back(temp);
    } while (prev_permutation(bitmask.begin(), bitmask.end()));
    return resultado;
}


int gane(char *juego[3][3]) {
    // Usando un cuadrado magico (cualquier linea sumada da como resultado 15) se encuentra si alguien ganó.
    // Teniendo un cuadrado magico para cada N y haciendo pequeños cambios al código se puede tener
    // una manera generalizada de revisar quien gana para cualquier gato de tamaño N.
    int magicSquare[3][3] = {{4,9,2},{3,5,7},{8,1,6}};
    vector<int> cruz, circulo = {};
    // Recorre la matriz de juego y llena las matrices cruz y circulo con el valor de
    // magicSquare correspondiende al índice donde se encuentra el caracter.
    for (int i=0; i<3; i++) {
        for (int j=0; j<3; j++) {
            const char *current = juego[i][j];
            if (current == "X") {
                cruz.push_back(magicSquare[i][j]);
            } else if (current == "O") {
                circulo.push_back(magicSquare[i][j]);
            }
        }
    }
    bool ganeCruz = false, ganeCirculo = false;
    vector<vector<int>> cruzCombi = comb(cruz.size(), 3);
    vector<vector<int>> circuloCombi = comb(circulo.size(), 3);
    int suma = 0;
    // Si alguna de las combinaciones de las cruces da 15 entonces cruzGane es true.
    for (unsigned int i = 0; i < cruzCombi.size(); i++) {
        suma = 0;
        for (unsigned int j = 0; j < cruzCombi[i].size(); j++) {
            suma += cruz[cruzCombi.at(i).at(j)];
        }
        if (suma == 15) {
            ganeCruz = true;
        }
    }
    // Si alguna de las combinaciones de las circulos da 15 entonces circuloGane es true.
    for (unsigned int i = 0; i < circuloCombi.size(); i++) {
        suma = 0;
        for (unsigned int j = 0; j < circuloCombi[i].size(); j++) {
            suma += circulo[circuloCombi.at(i).at(j)];
        }
        if (suma == 15) {
            ganeCirculo = true;
        };
    }
    // Si ambos han ganado, se reconoce como un error y retorna 3.
    if (ganeCruz && !ganeCirculo) {
        return 1;
    } else if (!ganeCruz && ganeCirculo) {
        return 2;
    } else if (ganeCruz && ganeCirculo) {
        return 3;
    } else {
        return 0;
    }
}


// Imprime el gato
void imprimir(char* juego[3][3]) {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (juego[i][j]) {
                cout << " " << juego[i][j];
            } else {
                cout << "  ";
            }
            if (j < 2) {
                cout << " |";
            }
        }
        if (i < 2) {
            cout << "\n" << "-----------" << endl;
        }
    }
    cout << endl;
}


int jugada(char* juego[3][3], bool turnoCruz) {
    vector<int> vacios = {};
    char* charAmigo = (char*)"X";
    char* charEnemigo = (char*)"O";
    char* temp[3][3] = {};
    char* esquinas[4] = {};
    if (!turnoCruz) {
        swap(charAmigo, charEnemigo);
    }
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (!juego[i][j]) {
                // Rellena lista de espacios vacios
                vacios.push_back(fromIndex(i,j));
            } else {
                // Rellena array de esquinas
                switch (fromIndex(i,j)) {
                    case 0:
                        esquinas[0] = juego[i][j];
                        break;
                    case 2:
                        esquinas[1] = juego[i][j];
                        break;
                    case 6:
                        esquinas[2] = juego[i][j];
                        break;
                    case 8:
                        esquinas[3] = juego[i][j];
                        break;
                    default:
                        break;
                }
            }
            // Clona matriz para usar posteriormente
            temp[i][j] = juego[i][j];
        }
    }
    int a, b;
    // Encuentra si hay alguna jugada ganadora
    for (auto i: vacios) {
        a = i/3;
        b = i%3;
        temp[a][b] = charAmigo;
        if (gane(temp)) {
            return fromIndex(a,b);
        }
        temp[a][b] = nullptr;
    }
    // Encuentra si hay que defenderse de una jugada perdedora
    for (auto i: vacios) {
        a = i/3;
        b = i%3;
        temp[a][b] = charEnemigo;
        if (gane(temp)) {
            return fromIndex(a,b);
        }
        temp[a][b] = nullptr;
    }
    // De lo contrario busca ganar esquinas opuestas (tienen valor estratégico)
    // posteriormente solo esquinas y sino rellena cualquier lugar
    int inversos[4] = {3,2,1,0};
    int esquinaPos[4] = {0,2,6,8};
    int opuestoEnemigo = 0;
    vector<int> esquinasDisponibles = {};
    // Esquina opuesta aliada
    for (int i=0; i<4; i++) {
        if (esquinas[i] == charAmigo && !esquinas[inversos[i]]) {
            return esquinaPos[i];
        } else if (esquinas[i] == charEnemigo && !esquinas[inversos[i]]) {
            opuestoEnemigo = esquinaPos[inversos[i]];
        } else if (!esquinas[i]) {
            esquinasDisponibles.push_back(esquinaPos[i]);
        }
    }
    // Esquina opuesta enemiga
    if (opuestoEnemigo) {
        return opuestoEnemigo;
    // Cualquier esquina disponible
    } else if (!esquinasDisponibles.empty()) {
        return esquinasDisponibles.at(rand() % esquinasDisponibles.size());
    // Random
    } else {
        return vacios.at(rand() % vacios.size());
    }
}


void jugar(char* juego[3][3], char* symbol) {
    if (gane(juego)) cout << "Este juego ya ha terminado." << endl;
    bool turnoCruz = false;
    if ((char *) "X" == (char *) symbol) turnoCruz = true;
    int num = jugada(juego, turnoCruz);
    int a = num/3;
    int b = num%3;
    juego[a][b] = symbol;
    imprimir(juego);
    cout << "\n" << endl;
}


int main() {
    srand(time(0));
    char *juego[3][3] = {{},{},{}};
    jugar(juego, (char*)"X");
    jugar(juego, (char*)"O");
    jugar(juego, (char*)"X");
    jugar(juego, (char*)"O");
    jugar(juego, (char*)"X");
}