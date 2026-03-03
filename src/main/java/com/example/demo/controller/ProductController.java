package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

import java.util.List;
@Controller
@RequestMapping("/products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;
  @GetMapping
  public String listProducts(Model model) {
      List<Product> productsList = productService.getAllProducts();
      model.addAttribute("products", productsList);
      return "product/list";
  }
  @GetMapping("/add")
  public String showAddForm(Model model) {
      model.addAttribute("product", new Product());
      model.addAttribute("categories", categoryService.getAllCategories());
      return "product/add";
  }
  @PostMapping("/save")
  public String saveproduct(@ModelAttribute("product") Product product) {
      productService.saveProduct(product);
      return "redirect:/products";
  }
  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
  }
  @PostMapping("/update")
  public String updateProduct(@ModelAttribute("product") Product product) {
      productService.saveProduct(product); 
      return "redirect:/products";
 }
  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable("id") Integer id) {
      productService.deleteProduct(id);
      return "redirect:/products";
  }
}