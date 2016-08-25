package app.minervati.com.br.keepinmind.domain;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by victorminerva on 25/08/2016.
 */
public class Alarme extends RealmObject {

    public static final String ID = "gastei.br.com.minervati.app.domain.RealmObject.ID";

    @PrimaryKey
    private Long        id;
    private Date        data;
    private Integer     tipoLembrete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getTipoLembrete() {
        return tipoLembrete;
    }

    public void setTipoLembrete(Integer tipoLembrete) {
        this.tipoLembrete = tipoLembrete;
    }
}
