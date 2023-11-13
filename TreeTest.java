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
    public void treeReader() { // Teste de leitura de arquivo com modelo Trabalho e verifica a folha da arvore com mais terras (Nenhum guerreiro nem conquista, nem perde terras)
        Tree tree = new Tree();

        String path = "C:/Users/thoma/OneDrive/Área de Trabalho/Alest/TrabalhoAlest2/lista.txt";
        Node raiz = null;

        try {
            FileReader caminhoArquivo = new FileReader(path);
            BufferedReader leituraArquivo = new BufferedReader(caminhoArquivo);

            String line = leituraArquivo.readLine();
            while (line != null) {
                if (tree.root == null) { // Criacao do root/raiz
                    String terras = line;

                    line = leituraArquivo.readLine();
                    String[] dadosLinha = line.split(" ");
                    String nome = dadosLinha[0];
                    raiz = new Node(nome, Integer.parseInt(terras), null);
                    tree.root = raiz;

                } else { // Se já tiver raiz, começa a criacao dos filhos

                    String[] dadosLinha = line.split(" "); // Leitura do arquivo e divide os dados de cada linha por espaço
                    String nomePai = dadosLinha[0];
                    String nomeFilho = dadosLinha[1];
                    int terrasFilho = Integer.parseInt(dadosLinha[2]);
                    Node filho = new Node(nomeFilho, terrasFilho, null);

                    if (nomePai.equals(raiz.name)) { // Filho do raiz
                        raiz.addChild(filho);
                    } else { // Filho de outro guerreiro
                        Node pai = tree.buscarGuerreiro(nomePai); // Busca o pai do guerreiro
                        if (pai != null) { // Se tiver pai, adiciona o filho, se nao, retorna que o pai nao foi encontrado
                        pai.addChild(new Node(nomeFilho, terrasFilho, null)); 
                        } else {
                            System.out.println("Pai nao encontrado");
                        }
                    }
                    line = leituraArquivo.readLine(); // Le a proxima linha
            }
        }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Excecao
        }

        System.out.println(tree.guerreiroMaisTerras());

        boolean expected = true;
        boolean actual = tree.guerreiroMaisTerras().getName().equals("Delscatorflex");

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
    public void buscarRootPorNome() {
        Tree tree = new Tree();
        Node guerreiro = new Node("Thomaz", 10, null);
        tree.root = guerreiro;

        Node expected = guerreiro;
        Node actual = tree.buscarGuerreiro("Thomaz");

        assertEquals(expected, actual);
    }

    @Test
    public void buscarGuerreiroFilhoPorNome() {
        Tree tree = new Tree();
        Node guerreiro = new Node("Thomaz", 10, null);
        tree.root = guerreiro;

        Node filho = new Node("Filho", 0, null);
        guerreiro.addChild(filho);

        Node expected = filho;
        Node actual = tree.buscarGuerreiro("Filho");

        assertEquals(expected, actual);
    }
}