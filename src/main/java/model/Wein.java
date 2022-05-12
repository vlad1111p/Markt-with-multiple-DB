package model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wein implements Produkt {

    public Wein(String bezeichnung, int qualitat, int preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.qualitat = qualitat;
    }

    public Wein(int qualitat, String bezeichnung, LocalDateTime verfallsdatum, double preis, boolean regal) {
        this.qualitat = qualitat;
        this.bezeichnung = bezeichnung;
        this.verfallsdatum = verfallsdatum;
        this.preis = preis;
        this.regal = regal;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    int qualitat;
    String bezeichnung;
    LocalDateTime verfallsdatum;
    double preis;
    boolean regal = false;


    @Override
    public String toString() {
        if (this.regal==true)
            return "Wein{" +
                    "id=" + id +
                    ", qualitat=" + qualitat +
                    ", bezeichnung='" + bezeichnung + '\'' +
                    ", verfallsdatum=" + verfallsdatum +
                    ", preis=" + preis +
                    '}';
        return "Wein{" +
                "id=" + id +
                ", qualitat=" + qualitat +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", verfallsdatum=" + verfallsdatum +
                ", preis=" + getTagespreis() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wein wein = (Wein) o;
        return qualitat == wein.qualitat && preis == wein.preis && id.equals(wein.id)
                && Objects.equals(bezeichnung, wein.bezeichnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qualitat, bezeichnung, preis);
    }

    @Override
    public double getTagespreis() {
        if (isRegal()) {
            return this.preis;
        }

        return this.preis + qualitat * 0.1;
    }

    public boolean isRegal() {
        return regal;
    }

    public void setRegal(boolean regal) {
        if (regal && this.regal != true) {
            this.preis = getTagespreis();
        }
        this.regal = regal;
    }
}
