import java.io.BufferedReader;
import java.io.FileReader;

public class CodigoMain extends Main {
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
        
    }
}
