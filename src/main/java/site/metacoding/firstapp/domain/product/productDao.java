package site.metacoding.firstapp.domain.product;

import java.util.List;

public interface productDao {
    public Product findById(Integer productId);
    public List<Product> findAll();
    public void insert(Product product);
    public void update(Integer producId, Product product);
    public void deleteById(Integer productId);
}
