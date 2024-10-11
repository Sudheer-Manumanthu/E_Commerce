package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.dtos.SortParam;
import com.sudheer.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {
    Page<Product> searchProducts(String query, Integer pageNo, Integer pageSize, List<SortParam> sortParamList);
}
