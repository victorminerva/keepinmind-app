package app.minervati.com.br.keepinmind.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by victorminerva on 14/08/2016.
 */
public class InfoBasics extends RealmObject {

    public static final String ID = "gastei.br.com.minervati.app.domain.RealmObject.ID";

    @PrimaryKey
    private Long    id;
    private Integer dia;
    private Integer mes;
    private Integer ano;
    private Integer qtdeDiasMenstru;
    private Integer duracaoCiclo;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getDia() {
        return dia;
    }
    public void setDia(Integer dia) {
        this.dia = dia;
    }
    public Integer getMes() {
        return mes;
    }
    public void setMes(Integer mes) {
        this.mes = mes;
    }
    public Integer getAno() {
        return ano;
    }
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    public Integer getQtdeDiasMenstru() {
        return qtdeDiasMenstru;
    }
    public void setQtdeDiasMenstru(Integer qtdeDiasMenstru) {
        this.qtdeDiasMenstru = qtdeDiasMenstru;
    }
    public Integer getDuracaoCiclo() {
        return duracaoCiclo;
    }
    public void setDuracaoCiclo(Integer duracaoCiclo) {
        this.duracaoCiclo = duracaoCiclo;
    }
}
