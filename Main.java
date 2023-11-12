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
    }

    public static void main(String[] args) {
       Tree tree = new Tree();

       Node guerreiro1 = new Node("Thomaz", 500, null);
       tree.root = guerreiro1;
        
       Node guerreiro2 = new Node("bettiol", 100, null);
       Node guerreiro3 = new Node("Samuca", 30, null);

       Node guerreiro4 = new Node("Lucas", 500, null);
       
       guerreiro1.addChild(guerreiro2);
       guerreiro1.addChild(guerreiro3);
       
       guerreiro2.addChild(guerreiro4);

        guerreiro2.conquistaTerra(150);

        //System.out.println(tree.isEmpty());

        guerreiro1.guerreiroMorreu();
      

        for(int i = 0; i < guerreiro1.children.size(); i++) {
            System.out.println(guerreiro1.children.get(i));
        }

        System.out.println("Guerreiro com mais terrs: " + tree.guerreiroMaisTerras());
       
    }
}