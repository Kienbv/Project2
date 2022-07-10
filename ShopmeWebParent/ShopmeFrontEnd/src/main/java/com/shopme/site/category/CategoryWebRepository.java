package com.shopme.site.category;

import com.shopme.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryWebRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.enabled = true ORDER BY c.name ASC")
	public List<Category> findAllEnabled();

	@Query("SELECT c FROM Category c WHERE c.enabled = true AND c.alias = ?1")
	public Category findByAliasEnabled(String alias);
}
