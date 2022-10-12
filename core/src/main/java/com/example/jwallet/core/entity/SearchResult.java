package com.example.jwallet.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResult<T> implements Serializable {
	private List<T> searchResult = new ArrayList<>();

	private Long totalSize;
	private Long offset;
	private Long limit;
}
