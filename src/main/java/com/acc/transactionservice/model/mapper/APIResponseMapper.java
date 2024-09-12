package com.acc.transactionservice.model.mapper;

import com.acc.transactionservice.model.APIResponse;

import java.util.List;

public interface APIResponseMapper<S,T>{
    T modelToDto(S source);
    List<T> modelsToDtos(List<S> sourceList);

    default APIResponse<T> mapToApiResponse(S source) {
        T target = modelToDto(source);
        return new APIResponse<>(target);
    }

    default APIResponse<List<T>> mapListToApiResponse(List<S> sourceList) {
        List<T> targetList = modelsToDtos(sourceList);
        return new APIResponse<>(targetList);
    }

    default APIResponse<T> mapErrorToApiResponse(List<String> error) {
        return new APIResponse<T>(error);
    }
}
