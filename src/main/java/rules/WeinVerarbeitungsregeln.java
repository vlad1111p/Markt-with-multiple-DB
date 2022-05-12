package rules;

import model.Produkt;

public class WeinVerarbeitungsregeln implements IVerarbeitungsregeln {
    @Override
    public boolean constraintCkeck(Produkt produkt) {

        return checkBezeichnung(produkt)
                && checkPreis(produkt)
                && checkVerfallsdatum(produkt)
                && checkQualitat(produkt);
    }

    @Override
    public boolean checkBezeichnung(Produkt produkt) {
        return true;
    }

    @Override
    public boolean checkPreis(Produkt produkt) {
        return true;
    }

    @Override
    public boolean checkQualitat(Produkt produkt) {
        return produkt.getQualitat()>=0;
    }

    @Override
    public boolean checkVerfallsdatum(Produkt produkt) {
        return true;
    }
}
