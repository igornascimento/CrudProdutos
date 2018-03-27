package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.Produto;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
public class RepositorioProdutos {

    private static RepositorioProdutos instancia;
    private List<Produto> listaProdutos;
    private int autoincrement;

    private RepositorioProdutos() {
        listaProdutos = new ArrayList<>();
        listaProdutos.add( new Produto(1, "Arroz", 2.50) );
        listaProdutos.add( new Produto(2, "Feijão", 5.00) );
        listaProdutos.add( new Produto(3, "Batata", 3.90) );
        autoincrement = 4;
    }
    
    public static synchronized RepositorioProdutos getInstance() {
        if (instancia == null) {
            instancia = new RepositorioProdutos();
        }
        return instancia;
    }
    
    public void inserir(Produto prod) {
        //se o codigo for 0, nao passar o codigo
        if (prod.getCodigo() == 0) {
            prod.setCodigo(autoincrement++);
        }
        listaProdutos.add(prod);
    }
    
    public List<Produto> listar() {
        return listaProdutos;
    }
    
    public Produto buscarPorCodigo(int cod) {
        for (Produto p : listaProdutos) {
            if (p.getCodigo() == cod) {
                return new Produto(p.getCodigo(), p.getNome(), p.getPreco());
            }
        }
        return null;
    }
    
    private int getIndice(int cod) {
        for (int i=0; i<listaProdutos.size(); i++) {
            if (listaProdutos.get(i).getCodigo() == cod) {
                return i;
            }
        }
        return -1;
    }
    
    public void atualizar(Produto prod) {
        listaProdutos.set(this.getIndice(prod.getCodigo()), prod);
    }
    
    public void excluir(Produto prod) {
        listaProdutos.remove(this.getIndice(prod.getCodigo()));
    }
    
}
