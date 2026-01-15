package com.example.TechMarket.Product;

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

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

        if(bindingResult.hasFieldErrors("title") || bindingResult.hasFieldErrors("description") || bindingResult.hasFieldErrors("price") || hasUploadError) {
            model.addAttribute("product", product);
            model.addAttribute("hasUploadError", hasUploadError);
            model.addAttribute("areImagesSelected", areImagesSelected);
            return "product/add";
        }

        product.setImages(imageList);
        product.setAvailable(true);
        productRepository.save(product);
        return "redirect:/";
    }
}