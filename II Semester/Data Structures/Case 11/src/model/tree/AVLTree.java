package model.tree;

public class AVLTree<T extends Comparable<T>> {
	private AVLNode<T> raiz;
	private int cantidadNodos;
	
	/*
	 * CONSTRUCTORES
	 */
	public AVLTree() {
		raiz = null;
	}
	
	public AVLTree(T pValor) {
		raiz = new AVLNode<T>(pValor);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public AVLNode<T> getRaiz() {
		return raiz;
	}
	public void setRaiz(AVLNode<T> raiz) {
		this.raiz = raiz;
	}
	public int getCantidadNodos() {
		return cantidadNodos;
	}
	
	/*
	 * METODOS CREADOS
	 */
	
	// Busqueda
	public T buscar(T pValor) {
		// Caso 1 - Raiz nula
		if (raiz == null) return null;
		// Caso 2 - Buscar dentro de la raiz
		
		return buscar(raiz, pValor);
	}
	
	public T buscar(AVLNode<T> pRoot ,T pValor) {
		// Caso 1 - No existe
		if (pRoot == null) {
			return null;
		}
		
		// Caso 2 - pValor es igual al valor guardado en pRoot
		else if (iguales(pValor, pRoot.getValor())) {
			return pValor;
		}
		
		// Caso 3 - pValor es menor al valor guardado en pRoot
		else if (menor(pValor, pRoot.getValor())) {
			return buscar(pRoot.getHijoIzquierdo(), pValor);
		}
		
		// Caso 4 - pValor es mayor al valor guardado en pRoot
		else {
			return buscar(pRoot.getHijoDerecho(), pValor);
		}
	}

	public T buscarMax(AVLNode<T> pRoot) {

		while(pRoot.getHijoDerecho() != null) {
			pRoot = pRoot.getHijoDerecho();
		}
		
		return pRoot.getValor();
	}
	
	public T buscarMin(AVLNode<T> pRoot) {

		while(pRoot.getHijoDerecho() != null) {
			pRoot = pRoot.getHijoIzquierdo();
		}
		
		return pRoot.getValor();
	}
	
	// Insercion
	public boolean insertar(T value) {
	    if (value == null) return false;
	    if (buscar(value) == null) {
	      raiz = insertar(raiz, value);
	      cantidadNodos++;
	      return true;
	    }
	    return false;
	  }

	  // Inserts a value inside the AVL tree.
	  private AVLNode<T> insertar(AVLNode<T> pRoot, T value) {

	    // Base case.
	    if (pRoot == null) return new AVLNode<T>(value);

	    // Insert node in left subtree.
	    if (this.menor(pRoot.getValor(), value)) {
	      pRoot.setHijoIzquierdo(insertar(pRoot.getHijoIzquierdo(), value));

	      // Insert node in right subtree.
	    } else {
	      pRoot.setHijoDerecho(insertar(pRoot.getHijoDerecho(), value));
	    }

	    // Update balance factor and height values.
	    update(pRoot);

	    // Re-balance tree.
	    return balance(pRoot);
	  }

	  // Update a node's height and balance factor.
	  private void update(AVLNode<T> pRoot) {

	    int leftNodeHeight = (pRoot.getHijoIzquierdo() == null) ? -1 : pRoot.getHijoIzquierdo().getAltura();
	    int rightNodeHeight = (pRoot.getHijoDerecho() == null) ? -1 : pRoot.getHijoDerecho().getAltura();

	    // Update this node's height.
	    pRoot.setAltura(1 + Math.max(leftNodeHeight, rightNodeHeight));

	    // Update balance factor.
	    pRoot.setBalance(rightNodeHeight - leftNodeHeight);
	  }

	  // Re-balance a node if its balance factor is +2 or -2.
	  private AVLNode<T> balance(AVLNode<T> pRoot) {

	    // Left heavy subtree.
	    if (pRoot.getBalance() == -2) {

	      // Left-Left case.
	      if (pRoot.getHijoIzquierdo().getBalance() <= 0) {
	        return leftLeftCase(pRoot);

	        // Left-Right case.
	      } else {
	        return leftRightCase(pRoot);
	      }

	      // Right heavy subtree needs balancing.
	    } else if (pRoot.getBalance() == 2) {

	      // Right-Right case.
	      if (pRoot.getHijoDerecho().getBalance() >= 0) {
	        return rightRightCase(pRoot);

	        // Right-Left case.
	      } else {
	        return rightLeftCase(pRoot);
	      }
	    }

	    // Node either has a balance factor of 0, +1 or -1 which is fine.
	    return pRoot;
	  }

	  private AVLNode<T> leftLeftCase(AVLNode<T> node) {
	    return rightRotation(node);
	  }

	  private AVLNode<T> leftRightCase(AVLNode<T> node) {
	    node.setHijoIzquierdo(leftRotation(node.getHijoIzquierdo()));
	    return leftLeftCase(node);
	  }

	  private AVLNode<T> rightRightCase(AVLNode<T> node) {
	    return leftRotation(node);
	  }

	  private AVLNode<T> rightLeftCase(AVLNode<T> node) {
	    node.setHijoDerecho(rightRotation(node.getHijoDerecho()));
	    return rightRightCase(node);
	  }

	  private AVLNode<T> leftRotation(AVLNode<T> node) {
		  AVLNode<T> newParent = node.getHijoDerecho();
	    node.setHijoDerecho(newParent.getHijoIzquierdo());
	    newParent.setHijoIzquierdo(node);
	    update(node);
	    update(newParent);
	    return newParent;
	  }

	  private AVLNode<T> rightRotation(AVLNode<T> node) {
		  AVLNode<T> newParent = node.getHijoIzquierdo();
	    node.setHijoIzquierdo(newParent.getHijoDerecho());
	    newParent.setHijoDerecho(node);
	    update(node);
	    update(newParent);
	    return newParent;
	  }
	
	public AVLNode<T> remover(AVLNode<T> pRoot, T pValor) {
		// Caso 1 - No existe el nodo
		if (pRoot == null) {
			return null;
		}
		
		// Caso 2 - pValor menor al valor guardado en pRoot
		else if (menor(pValor, pRoot.getValor())) {
			pRoot.setHijoIzquierdo(remover(pRoot.getHijoIzquierdo(), pValor));
		}
		
		// Caso 3 - pValor mayor al valor guardado en pRoot
		else if (mayor(pValor, pRoot.getValor())) {
			pRoot.setHijoDerecho(remover(pRoot.getHijoDerecho(), pValor));
		}
		
		// Caso 4 - Se encontro el valor que se quiere remover
		else {
			
			// Caso 4.1 - No hay hijo derecho
			if (pRoot.getHijoDerecho() == null) {
				return pRoot.getHijoIzquierdo();
			}
			
			// Caso 4.2 - No hay hijo izquierdo
			else if (pRoot.getHijoIzquierdo() == null) {
				return pRoot.getHijoDerecho();
			}
			
			// Caso 4.3 - Tiene ambos hijos
			else {
				
				// Caso 4.3.1 Sub-Arbol izquierdo es mas alto
				if (pRoot.getHijoIzquierdo().getAltura() > pRoot.getHijoDerecho().getAltura()) {
					
					T nuevoValor = buscarMax(pRoot.getHijoIzquierdo());
					pRoot.setValor(nuevoValor);
					
					pRoot.setHijoIzquierdo(remover(pRoot.getHijoIzquierdo(), nuevoValor));
				} 
				// Caso 4.3.2 Sub-Arbol derecho mas o igual de alto
				else {
					
					T nuevoValor = buscarMin(pRoot.getHijoDerecho());
					pRoot.setValor(nuevoValor);
					
					pRoot.setHijoDerecho(remover(pRoot.getHijoDerecho(), nuevoValor));
					
				}
			}	
		}
		
		return balance(pRoot);
	}
	
	
	// Comparacion de valores
	public boolean menor(T pValor1, T pValor2) {
		return pValor1.compareTo(pValor2) < 0;
	}
	
	public boolean mayor(T pValor1, T pValor2) {
		return pValor1.compareTo(pValor2) > 0;
	}
	
	public boolean iguales(T pValor1, T pValor2) {
		return pValor1.compareTo(pValor2) == 0;
	}
}