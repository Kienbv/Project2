package com.shopme.admin.product;


import com.shopme.admin.brand.BrandRepository;
import com.shopme.admin.category.CategoryRepository;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private BrandRepository brandRepo;

	@Test
	public void testCreateProduct() {
		Brand brand = brandRepo.findById(1).get();
		Category category = categoryRepo.findById(9).get();

		Product product = new Product();
		product.setName("Cá viên La Cusina");
		product.setAlias("CV LaCu");
		product.setShortDescription("Short description for Cá viên La Cusina");
		product.setFullDescription("Full description for Cá viên La Cusina");

		product.setBrand(brand);
		product.setCategory(category);

		product.setPrice(32000);
		product.setCost(32000);
		product.setEnabled(true);
		product.setInStock(true);

		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());

		Product savedProduct = productRepo.save(product);

		Assertions.assertThat(savedProduct).isNotNull();
		Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllProducts() {
		Iterable<Product> iterableProducts = productRepo.findAll();

		iterableProducts.forEach(System.out::println);
	}

}
