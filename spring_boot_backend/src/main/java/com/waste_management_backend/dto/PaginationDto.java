package com.waste_management_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Data
@AllArgsConstructor
public class PaginationDto {

    private int pageNumber;
    private int pageSize;

    public Pageable getPageable(){
        return PageRequest.of(this.pageNumber,this.pageSize, Sort.by(Sort.Direction.DESC,"createdAt"));
    }

}
