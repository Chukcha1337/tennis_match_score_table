package com.chuckcha.entity;

import com.chuckcha.dto.MatchDto;

import java.util.List;

public record Page(List<MatchDto> matches, int pagesNumber) {
}
