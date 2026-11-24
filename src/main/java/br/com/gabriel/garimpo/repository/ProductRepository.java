package br.com.gabriel.garimpo.repository;

import br.com.gabriel.garimpo.model.Marketplace;
import br.com.gabriel.garimpo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByMarketplaceAndExternalId(
        Marketplace marketplace,
        String externalId
    );
    
    
}
