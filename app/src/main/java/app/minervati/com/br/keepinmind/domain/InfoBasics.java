package app.minervati.com.br.keepinmind.domain;

import java.util.Date;

/**
 * Created by victorminerva on 14/08/2016.
 */
public class InfoBasics {

    private Integer dia;
    private Integer mes;
    private Integer ano;
    private Date    dataInicio;
    private Integer qtdeDiasMenstru;
    private Integer duracaoCiclo;


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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
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
