package br.edu.unifio.ecommerce.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.ecommerce.entidades.Categoria;
import br.edu.unifio.ecommerce.entidades.Produto;

@SpringBootTest 
public class ProdutoRepositorioTests {
    @Autowired 
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired 
    private ProdutoRepositorio produtoRepositorio;

    @Test 
    public void deveSalvarUmProdutoNovo () {
        var produto = new Produto ();
        produto.setNome("Notebook Lenovo Legion 5i");
        produto.setDescricao("Processador I7, Armazenamento SSD 1TB, Memória 16GB");
        produto.setPreco(new BigDecimal("12570.30"));
        produto.setEstoque(Short.parseShort("10"));

        var categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        produto.setCategoria(categoria);

        produtoRepositorio.save(produto);

        assertNotNull(produto.getId());
        assertEquals(6, produto.getId());
    }

    @Test 
    public void deveBuscarUmProdutoPorId () {
        Produto produto = produtoRepositorio.findById (Integer.parseInt("3")).orElseThrow();
        
        assertNotNull(produto);
        assertEquals("Fone de Ouvido Bluetooth", produto.getNome());
    }

    @Test 
    public void deveBuscarTodosOsProdutos () {
        List<Produto> produtos = produtoRepositorio.findAll(Sort.by("nome"));

        assertEquals(7, produtos.size());
        assertEquals("Código Limpo", produtos.get(0).getNome());
        assertEquals("Fone de Ouvido Bluetooth", produtos.get(1).getNome());
    }

    @Test 
    public void deveExcluirUmProdutoPorId () {
        Produto produto = new Produto ();
        produto.setNome("Nome Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(new BigDecimal("1.00"));
        produto.setEstoque(Short.parseShort("1"));

        Categoria categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        produto.setCategoria(categoria);

        produtoRepositorio.save(produto);

        assertTrue(produtoRepositorio.existsById(produto.getId()));
        produtoRepositorio.deleteById(produto.getId());
        assertFalse(produtoRepositorio.existsById(produto.getId()));
    }

    @Test 
    public void deveAtualizarONomeDeUmProduto () {
        Produto produto = new Produto ();
        produto.setNome("Nome Teste");
        produto.setDescricao("Descrição Teste");
        produto.setPreco(new BigDecimal("1.00"));
        produto.setEstoque(Short.parseShort("1"));

        Categoria categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        produto.setCategoria(categoria);

        produtoRepositorio.save(produto);

        produto.setNome("Outro Nome Teste"); 
        produtoRepositorio.save(produto);  

        assertEquals("Outro Nome Teste", produtoRepositorio.findById(produto.getId()).orElseThrow().getNome()); 
    } 
}
