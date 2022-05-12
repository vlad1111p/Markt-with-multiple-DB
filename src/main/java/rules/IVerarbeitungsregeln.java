package rules;

import model.Produkt;

public interface IVerarbeitungsregeln {

    boolean constraintCkeck(Produkt produkt);

    boolean checkBezeichnung(Produkt produkt);

    boolean checkPreis(Produkt produkt);

    boolean checkQualitat(Produkt produkt);

    boolean checkVerfallsdatum(Produkt produkt);



}
