import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    
    static class Node {
        String name;
        int qtdTerras;
        ArrayList<Node> children;
        boolean alive;

        public Node(String name, int qtdTerras, ArrayList<Main.Node> children) {
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

        public void guerreiroMorreu() {
            alive = false;
            if (children != null) {
                int terras = qtdTerras;
                int divisaoTerras = terras / children.size();

                for (int i = 0; i < children.size(); i++) {
                    if (children.get(i).alive == false) {
                        System.out.println("Guerreiro " + children.get(i).name + " já está morto");
                    } else {
                        children.get(i).qtdTerras += divisaoTerras;
                    }
                }
                qtdTerras = 0;
            } else {
                System.out.println("Guerreiro não possui filhos");
                qtdTerras = 0;
            }
        }

        public void conquistaTerra(int terras) {
            qtdTerras += terras;
        }

        public void perdeTerra(int terras) {
            qtdTerras -= terras;
        }
    } 


    static class Tree {
        Node root;

        public Tree() {
            this.root = null;
        }   

        public boolean isEmpty() {
            return root == null;
        }

        public Node root() {
            return root;
        }

        public Node guerreiroMaisTerras() {
            if (root == null) {
                return null;
            } 

            ArrayList<Node> ultimaGeracao = new ArrayList<>();
            guerreirosUltimaGeracao(root, ultimaGeracao);

            Node guerreiroMaisTerras = ultimaGeracao.get(0);

            for(int i = 1; i < ultimaGeracao.size(); i++) {
                if(ultimaGeracao.get(i).qtdTerras > guerreiroMaisTerras.qtdTerras) {
                    guerreiroMaisTerras = ultimaGeracao.get(i);
                }
            }
            return guerreiroMaisTerras;
        }

        public void guerreirosUltimaGeracao(Node node, ArrayList<Node> ultimaGeracao) {
            if(node.children.isEmpty()) {
                ultimaGeracao.add(node);
            } else {
                for(int i =0; i < node.children.size(); i++) {
                    guerreirosUltimaGeracao(node.children.get(i), ultimaGeracao);
                }
            }
        }

        public Node buscarGuerreiro(String nomeGuerreiro) {
            return buscaRecursiva(nomeGuerreiro, root);
        }

        public Node buscaRecursiva(String nome, Node node) {
            if (node == null) {
                return null;
            }

            if (node.name == nome) {
                return node;
            }

            for (int i = 0; i < node.children.size(); i++) {
                if (node.children.get(i).name == nome) {
                    return node.children.get(i);
                } 
            }            
            return null;
        }

        public boolean hasChildren(Node node) {
            return !node.children.isEmpty();
        }


        public void imprimirArvore() {
            imprimirArvoreRecursivo(root, 0);
        }
    
        private void imprimirArvoreRecursivo(Node node, int nivel) {
            if (node == null) {
                return;
            }
    
            // Imprime espaços para indentação
            for (int i = 0; i < nivel * 4; i++) {
                System.out.print(" ");
            }
    
            // Imprimir o guerreiro atual
            System.out.println("- " + node.name + " (Terras: " + node.qtdTerras + ")" + hasChildren(node));
    
            // Imprimir os filhos deste guerreiro
            for (int i = 0; i < node.children.size(); i++) {
                imprimirArvoreRecursivo(node.children.get(i), nivel + 1);
            }
        }
        
    }

    public static void main(String[] args) {
       Tree tree = new Tree();

        String path = "/Users/thomazszeckir/Desktop/Trabalho2Alest/lista.txt";
        Node raiz = null;

        try {
            FileReader caminhoArquivo = new FileReader(path);
            BufferedReader leituraArquivo = new BufferedReader(caminhoArquivo);

            String line = leituraArquivo.readLine();
            while (line != null) {
                if (tree.root == null) {
                    String terras = line;

                    line = leituraArquivo.readLine();
                    String[] dadosLinha = line.split(" ");
                    String nome = dadosLinha[0];
                    raiz = new Node(nome, Integer.parseInt(terras), null);
                    tree.root = raiz;
                }

                    String[] dadosLinha = line.split(" ");
                    String nomePai = dadosLinha[0];
                    String nomeFilho = dadosLinha[1];
                    int terrasFilho = Integer.parseInt(dadosLinha[2]);
                    Node filho = new Node(nomeFilho, terrasFilho, null);

                    if (nomePai.equals(raiz.name)) {
                        raiz.addChild(filho);
                    } else {
                        Node pai = tree.buscarGuerreiro(nomePai);
                        if (pai != null) {
                        pai.addChild(new Node(nomeFilho, terrasFilho, null)); 
                        } else {
                        }
                    }
                    line = leituraArquivo.readLine();
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        
        /* 
        System.out.println(tree.root);
        for (Node filho : tree.root.children) {
            System.out.println("Filho: " + filho.name + ", Terras: " + filho.qtdTerras);
        }  */      

        tree.imprimirArvore();
        
    }
}