package javaimpls.two.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderType {
    BUY("BUY"), SELL("SELL");
    @Getter private final String value;
}
