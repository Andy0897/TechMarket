package com.example.TehnicaBG.Product;

import com.example.TehnicaBG.Category.CategoryRepository;
import com.example.TehnicaBG.ImageEncoder;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {
    ProductService productService;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductController(ProductService productService, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String getShowProducts(Model model) {
        model.addAttribute("products", productRepository.findAvailableProducts());
        model.addAttribute("encoder", new ImageEncoder());
        return "product/show-all";
    }

    @GetMapping("/{productId}")
    public String getShowProduct(@PathVariable("productId") Long productId, Model model) {
        Product product = productRepository.findById(productId).get();
        model.addAttribute("product", product);
        model.addAttribute("encoder", new ImageEncoder());
        return product.isAvailable() ? "product/show-single" : "redirect:/products";
    }

    @GetMapping("/add")
    public String getAddProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("hasUploadError", false);
        model.addAttribute("areImagesSelected", true);
        return "product/add";
    }

    @PostMapping("/submit")
    public String getSubmitProduct(@Valid Product product, BindingResult bindingResult, @RequestParam("images") MultipartFile[] images, Model model) {
        return productService.submitAddProduct(product, bindingResult, images, model);
    }
}