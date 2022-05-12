package rules;

import model.Produkt;

import java.time.LocalDateTime;

public class BilligesFleischVerarbeitungsregeln implements IVerarbeitungsregeln {
    @Override
    public boolean constraintCkeck(Produkt produkt) {


        return checkBezeichnung(produkt)
                && checkPreis(produkt)
                && checkVerfallsdatum(produkt)
                && checkQualitat(produkt);
    }

    @Override
    public boolean checkBezeichnung(Produkt produkt) {
        return !produkt.getBezeichnung().contains("wagyu");
    }

    @Override
    public boolean checkPreis(Produkt produkt) {
        return produkt.getPreis()<10;
    }

    @Override
    public boolean checkQualitat(Produkt produkt) {
        return produkt.getQualitat()>20;
    }

    @Override
    public boolean checkVerfallsdatum(Produkt produkt) {
        return LocalDateTime.now().isBefore(produkt.getVerfallsdatum());

    }
}
