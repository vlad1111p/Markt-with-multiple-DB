package rules;

import model.Produkt;

import java.time.LocalDateTime;

public class KaseVerarbeitungsregeln implements IVerarbeitungsregeln {
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
        return produkt.getQualitat()>29;
    }

    @Override
    public boolean checkVerfallsdatum(Produkt produkt) {
        return LocalDateTime.now().isBefore(produkt.getVerfallsdatum());

    }
}
