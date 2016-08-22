package app.minervati.com.br.keepinmind.domain;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by victorminerva on 22/08/2016.
 */
public class Lembrete extends RealmObject {

    public static final String ID = "gastei.br.com.minervati.app.domain.RealmObject.ID";

    @PrimaryKey
    private Integer     id;
    private Integer     hora;
    private Integer     minuto;
    private String      tituloLembrete;
    private Date        dataLembrete;
    private Boolean     statusLembrete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getMinuto() {
        return minuto;
    }

    public void setMinuto(Integer minuto) {
        this.minuto = minuto;
    }

    public String getTituloLembrete() {
        return tituloLembrete;
    }

    public void setTituloLembrete(String tituloLembrete) {
        this.tituloLembrete = tituloLembrete;
    }

    public Date getDataLembrete() {
        return dataLembrete;
    }

    public void setDataLembrete(Date dataLembrete) {
        this.dataLembrete = dataLembrete;
    }

    public Boolean getStatusLembrete() {
        return statusLembrete;
    }

    public void setStatusLembrete(Boolean statusLembrete) {
        this.statusLembrete = statusLembrete;
    }
}
