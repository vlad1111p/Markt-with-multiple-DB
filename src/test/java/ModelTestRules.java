import model.BilligesFleisch;
import model.Kase;
import model.Wein;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rules.BilligesFleischVerarbeitungsregeln;
import rules.KaseVerarbeitungsregeln;
import rules.WeinVerarbeitungsregeln;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTestRules {


    @Test
    public void testWein(){
        WeinVerarbeitungsregeln wvr=new WeinVerarbeitungsregeln();
        Wein wein = new Wein();
        wein.setQualitat(20);
        wein.setPreis(20);
        wein.setBezeichnung("none");
        wein.setRegal(false);
        wein.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertTrue(wvr.constraintCkeck(wein));
    }
    @Test
    public void testWeinTagP(){
        WeinVerarbeitungsregeln wvr=new WeinVerarbeitungsregeln();
        Wein wein = new Wein();
        wein.setQualitat(20);
        wein.setPreis(20);
        wein.setBezeichnung("none");
        wein.setRegal(false);
        wein.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertEquals(wein.getPreis()+0.1*wein.getQualitat(),wein.getTagespreis());
    }

    @Test
    public void testKase(){
        KaseVerarbeitungsregeln wvr=new KaseVerarbeitungsregeln();
        Kase kase = new Kase();
        kase.setQualitat(30);
        kase.setPreis(20);
        kase.setBezeichnung("none");

        kase.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertTrue(wvr.constraintCkeck(kase));
    }
    @Test
    public void testKaseExpired(){
        KaseVerarbeitungsregeln wvr=new KaseVerarbeitungsregeln();
        Kase kase = new Kase();
        kase.setQualitat(50);
        kase.setPreis(20);
        kase.setBezeichnung("none");
        kase.setVerfallsdatum(LocalDateTime.now().minusYears(10));
        assertFalse(wvr.constraintCkeck(kase));
    }

    @Test
    public void testKaseBadQuality(){
        KaseVerarbeitungsregeln wvr=new KaseVerarbeitungsregeln();
        Kase kase = new Kase();
        kase.setQualitat(10);
        kase.setPreis(20);
        kase.setBezeichnung("none");

        kase.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertFalse(wvr.constraintCkeck(kase));
    }
    @Test
    public void testFleisch(){
        BilligesFleischVerarbeitungsregeln wvr=new BilligesFleischVerarbeitungsregeln();
        BilligesFleisch Fleisch = new BilligesFleisch();
        Fleisch.setQualitat(50);
        Fleisch.setPreis(5);
        Fleisch.setBezeichnung("none");
        Fleisch.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertTrue(wvr.constraintCkeck(Fleisch));
    }
    @Test
    public void testFleischExpired(){
        BilligesFleischVerarbeitungsregeln wvr=new BilligesFleischVerarbeitungsregeln();
        BilligesFleisch Fleisch = new BilligesFleisch();
        Fleisch.setQualitat(50);
        Fleisch.setPreis(5);
        Fleisch.setBezeichnung("none");
        Fleisch.setVerfallsdatum(LocalDateTime.now().minusYears(10));
        assertFalse(wvr.constraintCkeck(Fleisch));
    }

    @Test
    public void testFleischIsWagyu(){
        BilligesFleischVerarbeitungsregeln wvr=new BilligesFleischVerarbeitungsregeln();
        BilligesFleisch Fleisch = new BilligesFleisch();
        Fleisch.setQualitat(50);
        Fleisch.setPreis(5);
        Fleisch.setBezeichnung("wagyu");
        Fleisch.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertFalse(wvr.constraintCkeck(Fleisch));
    }
    @Test
    public void testFleischTooExpensive(){
        BilligesFleischVerarbeitungsregeln wvr=new BilligesFleischVerarbeitungsregeln();
        BilligesFleisch Fleisch = new BilligesFleisch();
        Fleisch.setQualitat(50);
        Fleisch.setPreis(20);
        Fleisch.setBezeichnung("none");
        Fleisch.setVerfallsdatum(LocalDateTime.now().plusYears(10));
        assertFalse(wvr.constraintCkeck(Fleisch));
    }

}
