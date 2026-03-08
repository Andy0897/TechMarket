package com.example.TechMarket.Product;

import com.example.TechMarket.Category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public String submitAddProduct(Product product, BindingResult bindingResult, MultipartFile[] images, Model model) {
        List<byte[]> imageList = new ArrayList<>();
        boolean areImagesSelected = false;
        boolean hasUploadError = false;
        try {
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    imageList.add(file.getBytes());
                    areImagesSelected = true;
                }
            }
        } catch (Exception e) {
            hasUploadError = true;
        }

        if(bindingResult.hasFieldErrors("title") || bindingResult.hasFieldErrors("description") || bindingResult.hasFieldErrors("price") || hasUploadError || !areImagesSelected) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("hasUploadError", hasUploadError);
            model.addAttribute("areImagesSelected", areImagesSelected);
            return "product/add";
        }

        product.setImages(imageList);
        product.setAvailable(true);
        productRepository.save(product);
        return "redirect:/";
    }

    public String submitDeleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return "redirect:/products";
    }
}