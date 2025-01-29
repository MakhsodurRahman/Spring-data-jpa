package com.jpaproject.specification;

import com.jpaproject.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public interface ProductSpecification {

        // Filter by exact category name
        static Specification<Product> byCategoryName(Set<String> categoryName) {
            if (categoryName == null || categoryName.isEmpty()) return null;
            return (root, query, criteriaBuilder) -> root.get("category").get("name").in(categoryName);
        }

        // Filter by price range
        static Specification<Product> byPriceRange(Double start, Double end) {
            if (start == null || end == null || end < start) return null;
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), start, end);
        }

        // Filter by name containing substring
        static Specification<Product> nameLike(String name) {
            if (name == null || name.isBlank()) return null;
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
        }

        // Complex: Group by category and filter by minimum product count
        static Specification<Product> byCategoryWithMinProductCount(Long minCount) {
            if (minCount == null || minCount < 1) return null;
            return (root, query, criteriaBuilder) -> {
                query.groupBy(root.get("category"));
                return criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(root), minCount);
            };
        }

        // Complex: Join with Category and filter by category name prefix
        static Specification<Product> categoryNameStartsWith(String prefix) {
            if (prefix == null || prefix.isBlank()) return null;
            return (root, query, criteriaBuilder) -> {
                Join<Object, Object> categoryJoin = root.join("category");
                return criteriaBuilder.like(categoryJoin.get("name"), prefix + "%");
            };
        }

        // Complex: Subquery for products with a price above the average price
        static Specification<Product> priceAboveAverage() {
            return (root, query, criteriaBuilder) -> {
                Subquery<Double> subquery = query.subquery(Double.class);
                subquery.select(criteriaBuilder.avg(root.get("price"))).from(Product.class);
                return criteriaBuilder.greaterThan(root.get("price"), subquery);
            };
        }

        // Complex: Filter by products with no associated category (hanging entities)
        static Specification<Product> withoutCategory() {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("category"));
        }

        // Complex: Products in categories with a specific product count
        static Specification<Product> categoryWithProductCount(Long count) {
            if (count == null || count < 0) return null;
            return (root, query, criteriaBuilder) -> {
                query.groupBy(root.get("category"));
                return criteriaBuilder.equal(criteriaBuilder.count(root), count);
            };
        }

        // Complex: Filter by products whose category contains a product with a specific name
        static Specification<Product> categoryHasProductName(String productName) {
            if (productName == null || productName.isBlank()) return null;
            return (root, query, criteriaBuilder) -> {
                Join<Object, Object> categoryJoin = root.join("category");
                return criteriaBuilder.like(categoryJoin.join("products").get("name"), "%" + productName + "%");
            };
        }

        // Complex: Filter by products with a price in the top N percentile
        static Specification<Product> topPercentilePrice(Double percentile) {
            if (percentile == null || percentile <= 0 || percentile > 100) return null;
            return (root, query, criteriaBuilder) -> {
                Subquery<Double> subquery = query.subquery(Double.class);
                subquery.select(criteriaBuilder.literal(percentile)).from(Product.class);
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), subquery);
            };
        }

        // Complex: Join with Category and filter by products in a category with a specific ID
        static Specification<Product> byCategoryId(Long categoryId) {
            if (categoryId == null) return null;
            return (root, query, criteriaBuilder) -> {
                Join<Object, Object> categoryJoin = root.join("category");
                return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
            };
        }

        // Complex: Products where the name is unique
        static Specification<Product> uniqueProductName() {
            return (root, query, criteriaBuilder) -> {
                query.groupBy(root.get("name"));
                return criteriaBuilder.equal(criteriaBuilder.count(root.get("name")), 1);
            };
        }
}
