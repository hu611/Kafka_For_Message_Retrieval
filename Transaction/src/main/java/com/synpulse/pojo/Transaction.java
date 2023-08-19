package com.synpulse.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@ApiModel(description="Transaction Data Model")
@Data
@ToString
public class Transaction {
    @ApiModelProperty(value = "Unique Identifier", required = true)
    String uID;
    String amountWithCurrency;
    @ApiModelProperty(value = "Account IBAN", required = true)
    String AccountIBAN;
    @ApiModelProperty(value = "Transaction Date", required = true)
    String date;
    @ApiModelProperty(value = "Transaction Description", required = true)
    String description;
}
