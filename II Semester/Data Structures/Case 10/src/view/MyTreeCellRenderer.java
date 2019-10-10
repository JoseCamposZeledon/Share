package view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.arbolnario.NodoJTree;
import model.sensor.Sensor;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

	ArrayList<NodoJTree<Sensor>> sensores;
	
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {

    	super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

        String node = (String) ((NodoJTree) value).getUserObject();

        if (tieneExceso(value)) {
            setForeground(new Color(255, 0 ,0));
        }

        return this;
    }
    
    public void setSensores(ArrayList<NodoJTree<Sensor>> pSensores) {
    	sensores = pSensores;
    }
    
    public boolean tieneExceso(Object value) {
    	
    	NodoJTree<Sensor> nodo = (NodoJTree<Sensor>) value;
    	
    	if (sensores.contains(nodo)) {
    		return true;
    	} 
    	return false;
    }
}
