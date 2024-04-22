package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.EntryFeeSubType;
import com.phoenixdarts.toss.domain.enumeration.EntryFeeType;
import com.phoenixdarts.toss.domain.enumeration.PaymentMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.EntryFee} entity.
 */
@Schema(description = "참가비")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntryFeeDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @NotNull
    private EntryFeeType entryFeeType;

    private EntryFeeSubType entryFeeSubType;

    private PaymentMethodType paymentMethodType;

    @NotNull
    @Max(value = 1)
    private Integer scheduleNumber;

    private Integer fee;

    private CurrencyDTO currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntryFeeType getEntryFeeType() {
        return entryFeeType;
    }

    public void setEntryFeeType(EntryFeeType entryFeeType) {
        this.entryFeeType = entryFeeType;
    }

    public EntryFeeSubType getEntryFeeSubType() {
        return entryFeeSubType;
    }

    public void setEntryFeeSubType(EntryFeeSubType entryFeeSubType) {
        this.entryFeeSubType = entryFeeSubType;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Integer getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(Integer scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntryFeeDTO)) {
            return false;
        }

        EntryFeeDTO entryFeeDTO = (EntryFeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entryFeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntryFeeDTO{" +
            "id='" + getId() + "'" +
            ", entryFeeType='" + getEntryFeeType() + "'" +
            ", entryFeeSubType='" + getEntryFeeSubType() + "'" +
            ", paymentMethodType='" + getPaymentMethodType() + "'" +
            ", scheduleNumber=" + getScheduleNumber() +
            ", fee=" + getFee() +
            ", currency=" + getCurrency() +
            "}";
    }
}
