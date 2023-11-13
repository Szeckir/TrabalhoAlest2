import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    
    static class Node { // Criacao da classe nodo (guerreiros)
        String name;
        int qtdTerras;
        ArrayList<Node> children;
        boolean alive;

        public String getName() {
            return name;
        }

        public int getQtdTerras() {
            return qtdTerras;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }

        public Node(String name, int qtdTerras, ArrayList<Main.Node> children) { // Construtor nodo
            this.name = name;
            this.qtdTerras = qtdTerras;
            this.children = new ArrayList<>();
            this.alive = true;
        }

        public void addChild(Node node) { // Adicionar um filho ao Guerreiro
            //node.children = null;
            children.add(node);
        }

        @Override
        public String toString() {
            boolean hasChildren = !children.isEmpty();
            
            return "Node:" +
                    " [name='" + name + '\'' +
                    "], [qtdTerras= " + qtdTerras + "], [children: " + hasChildren + "]";
        }

        public void guerreiroMorreu() { // Metodo que faz o guerreiro morrer e divide as terras entre os filhos
            alive = false;
            if (children != null) { // Verifica se o guerreiro possui filhos
                int terras = qtdTerras;
                int divisaoTerras = terras / children.size();

                for (int i = 0; i < children.size(); i++) { // Divide as terras entre os filhos
                    if (children.get(i).alive == false) {
                        System.out.println("Guerreiro " + children.get(i).name + " já está morto");
                    } else {
                        children.get(i).qtdTerras += divisaoTerras;
                    }
                }
                qtdTerras = 0;
            } else {
                System.out.println("Guerreiro não possui filhos"); // Se nao possui filhos, suas terras sao zeradas
                qtdTerras = 0;
            }
        }

        public void conquistaTerra(int terras) { // Metodo guerreiro conquistar terras
            qtdTerras += terras;
        }

        public void perdeTerra(int terras) { // Metodo guerreiro perder terras
            qtdTerras -= terras;
        }
    } 


    static class Tree { // Criacao da classe arvore
        Node root;

        

        public Tree() { // Construtor arvore
            this.root = null;
        }   

        public boolean isEmpty() { // Verifica se a arvore esta vazia
            return root == null;
        }

        public Node root() { // Retorna a raiz da arvore
            return root;
        }

        public Node guerreiroMaisTerras() { // Retorna o guerreiro com mais terras
            if (root == null) {
                return null;
            } 

            ArrayList<Node> ultimaGeracao = new ArrayList<>(); // Lista com os guerreiros da ultima geracao
            guerreirosUltimaGeracao(root, ultimaGeracao);

            Node guerreiroMaisTerras = ultimaGeracao.get(0);

            for(int i = 1; i < ultimaGeracao.size(); i++) { // Percorre a lista e verifica qual guerreiro possui mais terras
                if(ultimaGeracao.get(i).qtdTerras > guerreiroMaisTerras.qtdTerras) {
                    guerreiroMaisTerras = ultimaGeracao.get(i);
                }
            }
            return guerreiroMaisTerras;
        }

        public void guerreirosUltimaGeracao(Node node, ArrayList<Node> ultimaGeracao) { // Metodo que retorna os guerreiros sem filhos
            if(node.children.isEmpty()) { // Verifica se o guerreiro possui filhos
                ultimaGeracao.add(node); // Se for true (nao tem filhos), adiciona o guerreiro na lista
            } else { // Se for false (tem filhos), chama o metodo recursivamente para cada filho
                for(int i =0; i < node.children.size(); i++) {
                    guerreirosUltimaGeracao(node.children.get(i), ultimaGeracao);
                }
            }
        }

        public Node buscarGuerreiro(String nomeGuerreiro) { // Metodo que busca um guerreiro na arvore
            return buscaRecursiva(nomeGuerreiro, root);
        }


        public Node buscaRecursiva(String nome, Node node) { 
            if (node.children.isEmpty()) { // Verifica, se os filhos do raiz estiverem vazios, a unica possibilidade é o nome do raiz ser o procurado
                if (node.getName().equals(nome)) {
                    return node;
                } else {
                    return null;
                }
            }

            for (int i = 0; i < node.children.size(); i++) { // Percorre a arvore
                if (node.children.get(i).name.equals(nome)) { // Verifica se o nome do guerreiro eh igual ao nome passado como parametro, se for igual retorna
                    return node.children.get(i); 

                } else if (node.children.get(i).children.isEmpty() == false) {  // Se nao for igual, chama novamente o metodo e verifica os netos e assim por diante
                    Node filho = buscaRecursiva(nome, node.children.get(i)); // Cria um Nodo filho e chama o metodo recursivamente para cada filho
                    if (filho != null) { // Se o filho for diferente de null, retorna o filho
                        return filho;
                    }
                }
            }
            return null;
        }

        public boolean hasChildren(Node node) { // Verifica se um guerreiro tem filho
            return !node.children.isEmpty();
        }

        public void imprimirArvore() {
            imprimirArvoreRecursivo(root, 0);
        }
    
        private void imprimirArvoreRecursivo(Node node, int nivel) { // Metodo que imprime a arvore
            if (node == null) {
                return;
            }
    
            System.out.println(" ".repeat(nivel * 4) + "- " + node.name + " (Terras: " + node.qtdTerras + ")");
    
            for (Node filho : node.children) { // Percorre a arvore
                imprimirArvoreRecursivo(filho, nivel + 1); // Chama o metodo recursivamente para cada filho
            }
        }

        public Node getRoot() {
            return root;
        }
        
    }

    public static void main(String[] args) {
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
                    leituraArquivo.close();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Excecao
        }

        
        tree.imprimirArvore();
        System.out.println("Guerreiro com mais terras: " + tree.guerreiroMaisTerras());

        System.out.println(tree.buscarGuerreiro("Delscatorflex"));
    }
}