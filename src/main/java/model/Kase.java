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
public class Kase implements Produkt {

    public Kase(String bezeichnung, int qualitat, int preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.qualitat = qualitat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    int qualitat;
    String bezeichnung;
    double preis;
    LocalDateTime verfallsdatum;

    public Kase(int qualitat, String bezeichnung, double preis, LocalDateTime verfallsdatum) {
        this.qualitat = qualitat;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.verfallsdatum = verfallsdatum;
    }

    @Override
    public String toString() {
        return "Kase{" +
                "id=" + id +
                ", qualitat=" + qualitat +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", preis=" + getTagespreis() +
                ", verfallsdatum=" + verfallsdatum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kase kase = (Kase) o;
        return qualitat == kase.qualitat && preis == kase.preis && id.equals(kase.id)
                && Objects.equals(bezeichnung, kase.bezeichnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qualitat, bezeichnung, preis);
    }

    @Override
    public double getTagespreis() {
        return this.preis+qualitat*0.1;
    }


}
