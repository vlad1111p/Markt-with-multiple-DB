package model;


import java.time.LocalDateTime;


public interface Produkt {
    String getBezeichnung();

    void setBezeichnung(String value);

    double getPreis();

    void setPreis(double value);

    void setId(Long value);

    Long getId();

    void setQualitat(int value);

    int getQualitat();

    boolean equals(Object o);

    int hashCode();

    LocalDateTime getVerfallsdatum();

    void setVerfallsdatum(LocalDateTime date);

    double getTagespreis( );



}
