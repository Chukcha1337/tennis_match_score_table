package com.chuckcha.entity;

import com.chuckcha.dto.MatchDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class Page {

    private final List<MatchDto> matches;
    private final int pagesNumber;

}
