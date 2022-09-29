package site.metacoding.firstapp.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void findById_test(){
        //given(받야아할것)
        Integer productId = 1;

        //when(테스트)
        Product productPS = productDao.findById(productId);

        //then(검증)
        assertEquals(expected: "딸기", productPS.getProductName());
    }
}
