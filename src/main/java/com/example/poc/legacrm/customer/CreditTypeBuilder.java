package com.example.poc.legacrm.customer;

public class CreditTypeBuilder {

    private String creditFirm;

    private String creditTypeCd;

    public CreditType createCreditType() {
        return new CreditType(creditFirm, creditTypeCd);
    }

    public CreditTypeBuilder setCreditFirm(String creditFirm) {
        this.creditFirm = creditFirm;
        return this;
    }

    public CreditTypeBuilder setCreditTypeCd(String creditTypeCd) {
        this.creditTypeCd = creditTypeCd;
        return this;
    }
}