package ws;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Produto;
import repositorio.RepositorioProdutos;

/**
 * REST Web Service
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Path("produtos")
public class ProdutosWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProdutosWS
     */
    public ProdutosWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produto> getProdutos() {
        return RepositorioProdutos.getInstance().listar();
    }
    
    @GET
    @Path("/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto getProduto(@PathParam("codigo") int cod) {
        return RepositorioProdutos.getInstance().buscarPorCodigo(cod);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void adicionarProduto(Produto p, @Context final HttpServletResponse response) {
        RepositorioProdutos.getInstance().inserir(p);
        //Altera o codigo para 201 (Created)
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.flushBuffer();
        } catch(IOException e) {
            //erro 500
            throw new InternalServerErrorException();
        }
    }
    
    @PUT
    @Path("/{codigo}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void alterarProduto(@PathParam("codigo") int cod, Produto p) {
        Produto prod = RepositorioProdutos.getInstance().buscarPorCodigo(cod);
        prod.setNome(p.getNome());
        prod.setPreco(p.getPreco());
        RepositorioProdutos.getInstance().atualizar(prod);
    }
    
    @DELETE
    @Path("/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto removerProduto(@PathParam("codigo") int cod) {
        Produto prod = RepositorioProdutos.getInstance().buscarPorCodigo(cod);
        RepositorioProdutos.getInstance().excluir(prod);
        return prod;
    }
}
