package com.synpulse.pojo;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
public class Transaction {
    String uID;
    String amountWithCurrency;
    String AccountIBAN;
    String date;
    String description;
}
