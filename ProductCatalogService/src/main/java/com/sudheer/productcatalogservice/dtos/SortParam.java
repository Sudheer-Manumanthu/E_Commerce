package com.sudheer.productcatalogservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {
    String parameter;
    SortType sortType;
}
