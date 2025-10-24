package com.kbs.jpademo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page; // 현재페이지
    private int size; // 블록사이즈
    private int total; // 전체 레코드 수
    private int start; // 블록의 시작페이지
    private int end; // 블록의 마지막 페이지
    private boolean next; // 다음 페이지 여부
    private boolean prev; // 이전 페이지 여부
    private List<E> dtoList; // 페이징 할 dtoList

    @Builder(builderMethodName = "withALl")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;
        this.end = (int)(Math.ceil(this.page/(double)size))*size;
        this.start = this.end-(size-1);
        int last = (int)(Math.ceil(total/(double)size));
        this.end = end>last?last:end;
        this.prev = this.start > 1;
        this.next = this.end < last;
    }
}
