import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

public class TreeTest extends Main {

    @Test
    public void novaArvoreEstaVazia() { // Teste árvore vazia
         Tree tree = new Tree();

            boolean esperado = true;
            boolean atual = tree.isEmpty();

        assertEquals(esperado, atual);
    }

    @Test
    public void treeCreation() { // Teste de criacao da arvore com filhos e busca
        Tree tree = new Tree();

       Node guerreiro1 = new Node("Thomaz", 500, null);
       Node guerreiro2 = new Node("Samuel", 300, null);
       Node guerreiro3 = new Node("Julia", 100, null);

       tree.root = guerreiro1;

       guerreiro1.addChild(guerreiro2);
       guerreiro2.addChild(guerreiro3);

       guerreiro3.conquistaTerra(250);

       guerreiro1.guerreiroMorreu();

       Node esperado = tree.guerreiroMaisTerras();

       assertEquals(esperado, guerreiro3);
    }

    @Test
    public void guerreiroTemFilho() { // Verifica se teste de guerreiro tem filho está funcionando
        Tree tree = new Tree();

        Node guerreiro = new Node("Thomaz", 200, null);
        boolean expected = tree.hasChildren(guerreiro);
        boolean actual = false;

        assertEquals(expected, actual);
    }

    @Test
    public void guerreiroConquistarTerras() { // Verifica se guerreiro está conquistando terras
        Tree tree = new Tree();

        Node guerreiro = new Node("Thomaz", 100, null);
        tree.root = guerreiro;

        guerreiro.conquistaTerra(200);

        int actual = guerreiro.qtdTerras;
        int expected = 300;

        assertEquals(expected, actual);
    }

    @Test
    public void divisaoTerrasRoot() { // Verifica a divisao correta de terras.
        Tree tree = new Tree();
        Node root = new Node("Raiz", 1000, null);

        root.addChild(new Node("Filho 1", 0, null));
        root.addChild(new Node("Filho 2", 0, null));
        root.addChild(new Node("Filho 3", 0, null));
        root.addChild(new Node("Filho 4", 0, null));

        root.guerreiroMorreu();

        boolean expected = true;
        boolean actual = false;
        
        for(int i = 0; i < root.children.size(); i++) {
            if (root.children.get(i).qtdTerras == 250) {
                actual = true;
            } else {
                actual = false;
                break;
            }
        }

        assertEquals(expected, actual);
    }

    @Test
    public void buscarRootPorNome() { // Testa a busca pelo nome do root
        Tree tree = new Tree();
        Node guerreiro = new Node("Thomaz", 10, null);
        tree.root = guerreiro;

        Node expected = guerreiro;
        Node actual = tree.buscarGuerreiro("Thomaz");

        assertEquals(expected, actual);
    }

    @Test
    public void buscarGuerreiroFilhoPorNome() { // Testa a busca pelo nome de um guerreiro filho
        Tree tree = new Tree();
        Node guerreiro = new Node("Thomaz", 10, null);
        tree.root = guerreiro;

        Node filho = new Node("Filho", 0, null);
        guerreiro.addChild(filho);

        Node expected = filho;
        Node actual = tree.buscarGuerreiro("Filho");

        assertEquals(expected, actual);
    }

    @Test
    public void buscarQuantidadeTerrasFilho() { // Testa a busca pela quantidade de terras de um guerreiro filho
        Tree tree = new Tree();
        Node guerreiro = new Node("Thomaz", 10, null);
        tree.root = guerreiro;

        Node filho = new Node("Filho", 0, null);
        guerreiro.addChild(filho);

        int expected = 0;
        int actual = tree.buscarGuerreiro("Filho").qtdTerras;

        assertEquals(expected, actual);
    }

    @Test
    public void buscarGuerreiroNaoExistente() { // Testa a busca por um guerreiro que nao existe
        Tree tree = new Tree();
        Node guerreiro = new Node("Thomaz", 10, null);
        tree.root = guerreiro;

        Node expected = null;
        Node actual = tree.buscarGuerreiro("Filho");

        assertEquals(expected, actual);
    }

    // Crie mais alguns exemplos de testes
    @Test
    public void buscarGuerreiroComMaisTerras() { // Testa a busca pelo guerreiro com mais terras
        Tree tree = new Tree();

        Node guerreiro1 = new Node("Thomaz", 500, null);
        Node guerreiro2 = new Node("Samuel", 300, null);
        Node guerreiro3 = new Node("Julia", 100, null);

        tree.root = guerreiro1;

        guerreiro1.addChild(guerreiro2);
        guerreiro2.addChild(guerreiro3);

        guerreiro1.conquistaTerra(100);
        guerreiro1.perdeTerra(200);
        guerreiro1.guerreiroMorreu();

        Node expected = guerreiro3;
        Node actual = tree.guerreiroMaisTerras();

        assertEquals(expected, actual);
    }

}