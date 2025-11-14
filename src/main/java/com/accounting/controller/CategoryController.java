package com.accounting.controller;

import com.accounting.common.Result;
import com.accounting.dto.CategoryResponse;
import com.accounting.entity.Category;
import com.accounting.service.CategoryService;
import com.accounting.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("未授权");
        }
        token = token.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token无效");
        }
        return jwtUtil.getUserIdFromToken(token);
    }

    @PostMapping("/create")
    public Result<Category> createCategory(@RequestBody Category category, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            Category saved = categoryService.createCategory(userId, category);
            return Result.success("创建成功", saved);
        } catch (Exception e) {
            log.error("创建分类失败", e);
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Result<Category> updateCategory(@PathVariable("id") Long categoryId,
                                           @RequestBody Category category,
                                           HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            Category updated = categoryService.updateCategory(userId, categoryId, category);
            return Result.success("更新成功", updated);
        } catch (Exception e) {
            log.error("更新分类失败", e);
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteCategory(@PathVariable("id") Long categoryId, HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            boolean success = categoryService.deleteCategory(userId, categoryId);
            if (success) {
                return Result.success("删除成功");
            }
            return Result.error("删除失败");
        } catch (Exception e) {
            log.error("删除分类失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<CategoryResponse>> listCategories(@RequestParam(value = "type", required = false) Integer type,
                                                         HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            List<CategoryResponse> list = categoryService.listCategories(userId, type);
            if (CollectionUtils.isEmpty(list)) {
                return Result.success(Collections.emptyList());
            }
            return Result.success(list);
        } catch (Exception e) {
            log.error("获取分类列表失败", e);
            return Result.error(e.getMessage());
        }
    }
}
