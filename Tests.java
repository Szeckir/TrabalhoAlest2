public class Tests extends Main {
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

        System.out.println(tree.buscarGuerreiro("Lucas"));

    
    }
}
