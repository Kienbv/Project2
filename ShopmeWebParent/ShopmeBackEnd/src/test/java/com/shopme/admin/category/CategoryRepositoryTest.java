package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@DataJpaTest(showSql = false) //  showSql= false -> No want show sql on console log
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category("Gia vị");
        Category savedCategory = repo.save(category);

        Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateSubCategory() {
        Category parent = new Category(1);
        Category subCategory = new Category("Sản phẩm lợn", parent);
        Category savedCategory = repo.save(subCategory);

        Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateManySubCategories() {
        Category parent = new Category(4);
        Category nemThit = new Category("Gia vị lẩu", parent);
        Category khoaiChien = new Category("Gia vị tẩm ướp", parent);
        Category xucxich = new Category("Gia vị khác", parent);
        repo.saveAll(Arrays.asList(nemThit, khoaiChien, xucxich));

    }

    @Test
    public void testGetCategory() {
        Category category = repo.findById(1).get();
        System.out.println(category.getName());
        Set<Category> children = category.getChildren();
        for (Category subCategory : children
        ) {
            System.out.println(subCategory.getName());
        }
        Assertions.assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories() {
        Iterable<Category> categories = repo.findAll();

        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(subCategory.getName());
            printChildren(subCategory, newSubLevel);
        }
    }

    @Test
    public void testListRootCategories() {
        List<Category> rootCategories = repo.findRootCategories();
        rootCategories.forEach(category -> System.out.println(category.getName()));
    }

    @Test
    public void testFindByName() {
        String name = "Rau củ quả";
        Category category = repo.findByName(name);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getName()).isEqualTo(name);
    }


    @Test
    public void testFindByAlias() {
        String alias = "Rau củ quả";
        Category category = repo.findByAlias(alias);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getAlias()).isEqualTo(alias);
    }
}
