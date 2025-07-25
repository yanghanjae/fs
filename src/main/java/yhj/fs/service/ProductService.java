package yhj.fs.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yhj.fs.dto.ProductDTO;
import yhj.fs.entity.Category;
import yhj.fs.entity.Product;
import yhj.fs.repository.CategoryRepository;
import yhj.fs.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository PRODUCT_REPOSITORY;
	private final CategoryRepository CATEGORY_REPOSITORY;
	private final ModelMapper modelMapper;
	
	public ProductService(ProductRepository PR, CategoryRepository CR, ModelMapper MM) {
		this.PRODUCT_REPOSITORY = PR;
		this.CATEGORY_REPOSITORY = CR;
		this.modelMapper = MM;
	}
	
	// 카테고리 미지정시
	public Page<ProductDTO> searchProductByName (int page, String search) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productNo").descending());
		
		return PRODUCT_REPOSITORY.searchByName(search, pageable).map(i -> modelMapper.map(i, ProductDTO.class));
	}

	
	// 카테고리 지정시
	public Page<ProductDTO> searchProduct(int page, String search, Integer category) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productNo").descending());

		return PRODUCT_REPOSITORY.search(search, category, pageable).map(i -> modelMapper.map(i, ProductDTO.class));
	}

	
	// 특정 상품 찾기
	public Optional<ProductDTO> findByProductNo(int productNo) {
		
		return PRODUCT_REPOSITORY.findById(productNo).map(i -> modelMapper.map(i, ProductDTO.class));
	}
	
	@Transactional
	public void addProduct (ProductDTO product) {
		
		PRODUCT_REPOSITORY.save(modelMapper.map(product, Product.class));
	}
	
	@Transactional
	public void updateProduct (ProductDTO product) {
		
		Product foundProduct = PRODUCT_REPOSITORY.findById(product.getProductNo())
				.orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다: " + product.getProductNo()));
		
		foundProduct.setProductName(product.getProductName());
		foundProduct.setProductImgUrl(product.getProductImgUrl());
		
		Category category = CATEGORY_REPOSITORY.findById(product.getCategoryNo())
				.orElseThrow(() -> new IllegalArgumentException("해당 분류가 존재하지 않습니다: " + product.getProductNo()));
		foundProduct.setCategory(category);
		
		foundProduct.setProductPrice(product.getProductPrice());
		foundProduct.setProductUnit(product.getProductUnit());
				
	}
	
	@Transactional
	public void deleteProduct (int productNo) {
		
		PRODUCT_REPOSITORY.deleteById(productNo);
	}
}
