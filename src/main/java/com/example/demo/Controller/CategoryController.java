package com.example.demo.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Entity.Category;
import com.example.demo.Service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	private final CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}

	// Danh sách + tìm kiếm + phân trang
	@GetMapping
	public String list(@RequestParam(value = "q", required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size, Model model) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
		Page<Category> pageData = service.search(keyword, pageable);

		model.addAttribute("pageData", pageData);
		model.addAttribute("q", keyword);
		model.addAttribute("title", "Danh mục sản phẩm");
		return "category/list";
	}

	// Form tạo mới
	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("title", "Thêm danh mục");
		return "category/form";
	}

	// Lưu tạo mới
	@PostMapping
	public String create(@Valid @ModelAttribute("category") Category category, BindingResult result,
			RedirectAttributes ra) {
		if (result.hasErrors()) {
			return "category/form";
		}
		service.save(category);
		ra.addFlashAttribute("success", "Đã thêm danh mục thành công!");
		return "redirect:/categories";
	}

	// Form sửa
	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		return service.findById(id).map(c -> {
			model.addAttribute("category", c);
			model.addAttribute("title", "Sửa danh mục");
			return "category/form";
		}).orElseGet(() -> {
			ra.addFlashAttribute("error", "Không tìm thấy danh mục!");
			return "redirect:/categories";
		});
	}

	// Cập nhật
	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid @ModelAttribute("category") Category category,
			BindingResult result, RedirectAttributes ra) {
		if (result.hasErrors()) {
			return "category/form";
		}
		category.setId(id);
		service.save(category);
		ra.addFlashAttribute("success", "Đã cập nhật danh mục!");
		return "redirect:/categories";
	}

	// Xóa
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
		service.deleteById(id);
		ra.addFlashAttribute("success", "Đã xóa danh mục!");
		return "redirect:/categories";
	}
}
