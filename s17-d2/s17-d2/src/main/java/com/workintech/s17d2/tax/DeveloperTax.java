package com.workintech.s17d2.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{
    @Override
    public double getSimpleTaxRate() {
        return 0.3;
    }

    @Override
    public double getMiddleTaxRate() {
        return 0.5;
    }

    @Override
    public double getUpperTaxRate() {
        return 0.8;
    }
}
