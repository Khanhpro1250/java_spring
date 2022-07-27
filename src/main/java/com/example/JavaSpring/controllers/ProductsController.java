package com.example.JavaSpring.controllers;

import com.example.JavaSpring.models.Product;
import com.example.JavaSpring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.*;



@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductsController {
    @Autowired // singleton pattern
    private ProductRepository repository;
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    @GetMapping("")
    List<Object> getAll(){
       return Collections.singletonList(new ResponseEntity<Object>(repository.findAll(), HttpStatus.OK));
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Object create(
            @RequestParam Map<String,String> body,
            @RequestParam MultipartFile image
    ) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(image.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
        Product create_product = new Product();
        create_product.setName(body.get("name"));
        create_product.setPrice(Integer.parseInt(body.get("price")));
        create_product.setDescription(body.get("description"));
        create_product.setImage(image.getOriginalFilename());
        return new ResponseEntity<Object>( repository.save(create_product), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public Object update(
            @RequestParam Map<String,String> body,
            @PathVariable long id,
            @RequestParam MultipartFile image
    )throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(image.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
        Product find_product = repository.findById(id);
        if (find_product != null){
            find_product.setName(body.get("name"));
            find_product.setDescription(body.get("description"));
            find_product.setPrice(Integer.parseInt(body.get("price")));
            find_product.setImage(image.getOriginalFilename());
            return new ResponseEntity<Object>(repository.save(find_product), HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<Object>("Product not found !", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable long id){
        Product find_product = repository.findById(id);
        if (find_product != null){
            try{
                repository.delete(find_product);
                return  new ResponseEntity<Object>("Product was deleted!", HttpStatus.OK);
            }catch(Exception e) {
                return new ResponseEntity<Object>(e.toString(), HttpStatus.EXPECTATION_FAILED);
            }
        }
        else {
            return  new ResponseEntity<Object>("Product not found !", HttpStatus.NOT_FOUND);
        }
    }
}
