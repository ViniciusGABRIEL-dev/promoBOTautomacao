package br.com.gabriel.garimpo.service;


import java.util.List;
import br.com.gabriel.garimpo.model.Product;
import java.math.BigDecimal;
import br.com.gabriel.garimpo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service //anotação, parte da lógica de negócio
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List <Product> listarTodos(){
        return productRepository.findAll();
    }

    public Product buscarPorId(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto NÃO encontrado " + id));
        
    }

    private static final BigDecimal NOTA_MINIMA = new BigDecimal("4.0");
    private static final int AVALIACOES_MINIMAS = 20;

    public Product cadastrar(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Produto não informado");
        }
        if(product.getRating() == null ||
                product.getRating().compareTo(NOTA_MINIMA) < 0){
                    throw new IllegalArgumentException("Nota do produto abaixo do mínimo");
                }    
        if(product.getReviewCount() == null || 
                 product.getRating().compareTo(NOTA_MINIMA) < 0){
                    throw new IllegalArgumentException("Quantidade de avaliações insuficiente");
                } 
        if (product.getReviewCount() == null ||
                product.getReviewCount() < AVALIACOES_MINIMAS) {
            throw new IllegalArgumentException("Quantidade de avaliações insuficiente");
        }      
        if (product.getMarketplace() == null ||
                product.getExternalId() == null ||
                product.getExternalId().isBlank()) {
            throw new IllegalArgumentException("Marketplace e ID externo são obrigatórios");
        }
        boolean existe = productRepository.existsByMarketplaceAndExternalId(
                product.getMarketplace(),
                product.getExternalId()
        );
        if (existe) {
            throw new IllegalArgumentException("Produto já cadastrado");
        }

        return productRepository.save(product);
    }
}
