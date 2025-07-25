
package yhj.fs.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yhj.fs.common.Pagenation;
import yhj.fs.common.PagingButtonInfo;
import yhj.fs.dto.StockDTO;
import yhj.fs.service.StockService;
import yhj.fs.service.StorageService;


@Controller
@RequestMapping("/stock")
public class StockController {

	private final StockService STOCK_SERVICE;
	private final StorageService STORAGE_SERVICE;
	
	public StockController(StockService stockS, StorageService storageS) {
		this.STOCK_SERVICE = stockS;
		this.STORAGE_SERVICE = storageS;
	}
	
	@GetMapping("")
	public String stock(Model model, @RequestParam (defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String search, @RequestParam(required = false) Integer storageNo) {
		
		Page<StockDTO> stock = STOCK_SERVICE.searchStockByName(page, search);
		
		if (storageNo != null) {
			model.addAttribute("selectedStorage", storageNo);
			stock = STOCK_SERVICE.searchStock(page, search, storageNo);
		}
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(stock);
		
		model.addAttribute("stock", stock);
		model.addAttribute("paging", paging);
		model.addAttribute("storage", STORAGE_SERVICE.findAllStorage());
		
		model.addAttribute("search", search);
		
		return "stock/stock";
	}
	
	@ResponseBody
	@GetMapping("/{stockNo}")
	public StockDTO stockDetail(@PathVariable int stockNo) {
		
		return STOCK_SERVICE.findByStockNo(stockNo).orElse(new StockDTO());
	}
	
	@GetMapping("/modal")
	public String productModal (Model model, @RequestParam (defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String search, @RequestParam(required = false) Integer storage) {
		
		Page<StockDTO> stock = STOCK_SERVICE.searchStockByName(page, search);
		
		if(storage != null) {
			model.addAttribute("selectedStorage", storage);
			stock = STOCK_SERVICE.searchStock(page, search, storage);
		}
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(stock);
		
		model.addAttribute("stock", stock);
		model.addAttribute("paging", paging);
		model.addAttribute("storage", STORAGE_SERVICE.findAllStorage());
		model.addAttribute("search", search);
		
		return "stock/stockModal :: stockModalContent";
	}
}
