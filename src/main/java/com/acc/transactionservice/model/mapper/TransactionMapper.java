package com.acc.transactionservice.model.mapper;

import com.acc.transactionservice.entity.Transaction;
import com.acc.transactionservice.model.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends APIResponseMapper<Transaction, TransactionDto> {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Override
    TransactionDto modelToDto(Transaction transaction);

    List<TransactionDto> modelsToDtos(List<Transaction> transactions);

    Transaction dtoToEntity(TransactionDto transactionDto);
}
