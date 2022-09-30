package site.metacoding.firstapp.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import site.metacoding.firstapp.config.MyBatisConfig;

@Import(MyBatisConfig.class) // MyBatisTest가 MyBatisConfig를 못읽음
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실DB사용
@MybatisTest
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void findById_test() {
        // given (받아야 될 것)
        Integer productId = 1;

        // when (테스트)
        Product productPS = productDao.findById(productId);

        // then (검증)
        assertEquals("바나나", productPS.getProductName());
    }// rollback

    @Test
    public void findAll_test() {
        // given

        // when
        List<Product> productListPS = productDao.findAll();

        // then
        assertEquals(2, productListPS.size());
    }// rollback

    // Junit은 메서드 실행직전에 트랜잭션이 걸리고, 메서드 실행이 끝나면 rollback됨
    // Mybatis는 ResultSet을 자바 entity로 변경해줄때, 빈생성자를 호출하고 setter가 없어도 값을
    // 매핑해준다(setter필요업음)
    @Test
    public void insert_test() {
        // given
        String productName = "수박";
        Integer productPrice = 1000;
        Integer prodcutQty = 100;
        Product product = new Product(productName, productPrice, prodcutQty);

        // when
        int result = productDao.insert(product);

        // then
        assertEquals(1, result);
    }// rollback된다

    @Test
    public void update_test() {
        // given(업데이트는 given데이터를 select해서 만들어야한다,영속화)
        Integer productId = 1;
        String productName = "수박";
        Integer productPrice = 1000;
        Integer prodcutQty = 100;

        Product product = new Product(productName, productPrice, prodcutQty);
        product.setProductId(productId);

        // verify
        Product productPS = productDao.findById(product.getProductId());
        assertTrue(productPS == null ? false : true);

        // when(영속화시킨걸 넣어야한다(productPS))
        // product [id=1, productName="수박", productPrice="1000", productQty="100"]
        // productPS [id=1, productName="바나나", productPrice="3000", productQty="98",
        // createdAt=2022-9-29]

        productPS.update(product);
        // productPS [id=1, productName="수박", productPrice="1000", productQty="100",
        // createdAt=2022-9-29]
        int result = productDao.update(productPS);

        // then
        assertEquals(1, result);
    }// rollback

    @Test
    public void deleteById_test() {
        // given
        Integer productId = 1;

        // verify
        Product productPS = productDao.findById(productId);
        assertTrue(productPS == null ? false : true);
        // when
        int result = productDao.deleteById(productId);
        // then
        assertEquals(1, result);
    }
}
