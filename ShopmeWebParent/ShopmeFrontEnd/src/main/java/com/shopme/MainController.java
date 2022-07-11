package com.shopme;

import com.shopme.category.CategoryWebServiceImp;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@EnableJpaRepositories(basePackages = "com.shopme.category")
@EntityScan("com.shopme.common.entity")
public class MainController {
	@Autowired
	private CategoryWebServiceImp categoryWebServiceImp;

	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Category> listCategories = categoryWebServiceImp.listNoChildrenCategories();
		model.addAttribute("listCategories", listCategories);

		return "index";
	}
}
