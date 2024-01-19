package com.ys.infrastructure.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    int currentPage;
    int pageSize;
    int totalItems;
    int totalPages;
    int previousPage;
    Optional<Integer> nextPage;

    public static Pagination create(int currentPage, int pageSize, int totalItems) {
        long totalPages = (long) Math.ceil((double) totalItems / pageSize);
        int previousPage = currentPage > 1 ? currentPage - 1 : 0;
        Optional<Integer> nextPage = currentPage < totalPages ? Optional.of(currentPage + 1) : Optional.empty();

        return new Pagination(currentPage, pageSize, totalItems, (int) totalPages, previousPage, nextPage);
    }
}
