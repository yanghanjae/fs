package yhj.fs.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yhj.fs.common.Pagenation;
import yhj.fs.common.PagingButtonInfo;
import yhj.fs.dto.CategoryDTO;
import yhj.fs.dto.ProductDTO;
import yhj.fs.service.CategoryService;
import yhj.fs.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService PRODUCT_SERVICE;
	private final CategoryService CATEGORY_SERVICE;
	
	public ProductController(ProductService PS, CategoryService CS) {
		this.PRODUCT_SERVICE = PS;
		this.CATEGORY_SERVICE = CS;
	}

	@GetMapping("")
	public String product(Model model, @RequestParam (defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String search, @RequestParam(required = false) Integer category) {
		
		
		Page<ProductDTO> products = PRODUCT_SERVICE.searchProductByName(page, search);
		
		if (category != null) {
			model.addAttribute("selectedCategory", category);
			products = PRODUCT_SERVICE.searchProduct(page, search, category);
		} 
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(products);
		
		model.addAttribute("products", products);
		model.addAttribute("paging", paging);
		model.addAttribute("category", CATEGORY_SERVICE.findAllCategory());
		
		model.addAttribute("search", search);
		
		return "product/product";
	}
	
	@ResponseBody
	@GetMapping("/{productNo}")
	public ProductDTO productDetail(@PathVariable int productNo) {
		
		return PRODUCT_SERVICE.findByProductNo(productNo).orElse(new ProductDTO());
	}
	
	@GetMapping("/add")
	public String ProductAdd (Model model) {
		
		model.addAttribute("category", CATEGORY_SERVICE.findAllCategory());
		
		return "product/addProduct";
	}
	
	@PostMapping("/add")
	public String addNewProduct (ProductDTO product) {
		
		CategoryDTO category = new CategoryDTO();
		category.setCategoryNo(product.getCategoryNo());
		product.setCategory(category);
		PRODUCT_SERVICE.addProduct(product);
		
		return "redirect:/product";
	}
	
	@PostMapping("/edit")
	public String EditProduct (ProductDTO product) {
		
		PRODUCT_SERVICE.updateProduct(product);
		
		return "redirect:/product";
	}
	
	@PostMapping("/delete")
	public String DeleteProduct (@RequestParam int productNo) {
		
		PRODUCT_SERVICE.deleteProduct(productNo);
		
		return "redirect:/product";
	}
	
	@GetMapping("/modal")
	public String productModal (Model model, @RequestParam (defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String search, @RequestParam(required = false) Integer category) {
		
		Page<ProductDTO> product = PRODUCT_SERVICE.searchProductByName(page, search);
		
		if(category != null) {
			model.addAttribute("selectedCategory", category);
			product = PRODUCT_SERVICE.searchProduct(page, search, category);
		}
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(product);
		
		model.addAttribute("products", product);
		model.addAttribute("paging", paging);
		model.addAttribute("category", CATEGORY_SERVICE.findAllCategory());
		model.addAttribute("search", search);
		
		return "product/productModal :: productModalContent";
	}
}
