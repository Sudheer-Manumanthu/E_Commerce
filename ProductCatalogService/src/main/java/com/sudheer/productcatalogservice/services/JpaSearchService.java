package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.dtos.SortParam;
import com.sudheer.productcatalogservice.dtos.SortType;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class JpaSearchService implements ISearchService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> searchProducts(String query, Integer pageNo, Integer pageSize, List<SortParam> sortParamList) {

        Sort sort = null;

        if(!sortParamList.isEmpty()) {
            if(sortParamList.get(0).getSortType().equals(SortType.DESC)){
                sort = Sort.by(sortParamList.get(0).getParameter()).descending();
            }
            else  sort = Sort.by(sortParamList.get(0).getParameter());

            for(int i = 1; i < sortParamList.size(); i++) {
                if(sortParamList.get(i).getSortType().equals(SortType.DESC)){
                    sort = sort.and(Sort.by(sortParamList.get(i).getParameter()).descending());
                }
                else  sort = sort.and(Sort.by(sortParamList.get(i).getParameter()));
            }

        }

        return productRepository.findByName(query, PageRequest.of(pageNo, pageSize, sort));
    }
}
