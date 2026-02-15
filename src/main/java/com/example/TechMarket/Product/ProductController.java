package com.example.TechMarket.Product;

import com.example.TechMarket.Category.CategoryRepository;
import com.example.TechMarket.Condition.Condition;
import com.example.TechMarket.ImageEncoder;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public String getShowProducts(@RequestParam(required = false) Long categoryId,
                                  @RequestParam(required = false) Double minPrice,
                                  @RequestParam(required = false) Double maxPrice,
                                  @RequestParam(required = false) Condition condition,
                                  Model model) {

        List<Product> products = productRepository.findAvailableProducts()
                .stream()
                .filter(p -> categoryId == null ||
                        (p.getCategory() != null &&
                                p.getCategory().getId().equals(categoryId)))
                .filter(p -> minPrice == null ||
                        p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null ||
                        p.getPrice() <= maxPrice)
                .filter(p -> condition == null ||
                        p.getCondition() == condition)
                .toList();

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("encoder", new ImageEncoder());

        return "product/show-all";
    }


    @GetMapping("/{productId}")
    public String getShowProduct(@PathVariable("productId") Long productId, Model model) {
        Product product = productRepository.findById(productId).get();
        List<Product> productList = (List<Product>) productRepository.findAvailableProducts();
        productList.remove(product);
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", productList.size() > 3 ? productList.subList(0, 2) : productList);
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