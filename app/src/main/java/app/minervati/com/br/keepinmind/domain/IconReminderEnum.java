package app.minervati.com.br.keepinmind.domain;

/**
 * Created by victorminerva on 16/08/2016.
 */
public enum  IconReminderEnum {

    DAY_START(1),
    DAY_LOW_RISK(2),
    DAY_MID_RISK(3),
    DAY_TPM(4),
    DAY_END(5);

    private Integer value;

    IconReminderEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
