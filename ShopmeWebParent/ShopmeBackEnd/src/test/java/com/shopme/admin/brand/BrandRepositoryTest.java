package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;

@DataJpaTest(showSql = false) //  showSql= false -> No want show sql on console log
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTest {
    @Autowired
    private BrandRepository repo;

    @Test
    public void testCreateBrand1() {
        Category tbFood = new Category(1);
        Brand brand = new Brand("TBFood");
        brand.getCategories().add(tbFood);

        Brand savedBrand = repo.save(brand);

        Assertions.assertThat(savedBrand).isNotNull();
        Assertions.assertThat(savedBrand.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateBrand2() {
        Category sanPhamLon = new Category(4);
        Category xucXich = new Category(7);
        Brand brand = new Brand("CP");
        brand.getCategories().addAll(Arrays.asList(sanPhamLon, xucXich));

        Brand savedBrand = repo.save(brand);

        Assertions.assertThat(savedBrand).isNotNull();
        Assertions.assertThat(savedBrand.getId()).isGreaterThan(0);

    }

    @Test
    public void testGetById() {
        Brand brand = repo.findById(1).get();

        Assertions.assertThat(brand.getName()).isEqualTo("TBFood");
    }

    @Test
    public void testFindAll() {
        Iterable<Brand> brands = repo.findAll();
        for (Brand brand : brands) {
            System.out.println(brand.toString());
        }

        Assertions.assertThat(brands).hasSizeGreaterThan(1);
    }
}
