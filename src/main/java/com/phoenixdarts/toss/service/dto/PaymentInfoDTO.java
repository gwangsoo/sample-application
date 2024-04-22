package com.phoenixdarts.toss.service.dto;

import com.phoenixdarts.toss.domain.enumeration.PaymentMethodType;
import com.phoenixdarts.toss.domain.enumeration.PaymentStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.phoenixdarts.toss.domain.PaymentInfo} entity.
 */
@Schema(description = "결제 정보")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentInfoDTO implements Serializable {

    @NotNull
    @Size(max = 16)
    private String id;

    @Size(max = 50)
    private String orderNumber;

    private Instant paymentCompletedDate;

    @NotNull
    private PaymentStatusType status;

    private PaymentMethodType paymentMethodType;

    private Integer pgTID;

    @Min(value = 1)
    private Integer pgStatus;

    @Size(max = 255)
    private String pgDetail;

    @Size(max = 50)
    private String payer;

    @Size(max = 20)
    private String payerPhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getPaymentCompletedDate() {
        return paymentCompletedDate;
    }

    public void setPaymentCompletedDate(Instant paymentCompletedDate) {
        this.paymentCompletedDate = paymentCompletedDate;
    }

    public PaymentStatusType getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusType status) {
        this.status = status;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Integer getPgTID() {
        return pgTID;
    }

    public void setPgTID(Integer pgTID) {
        this.pgTID = pgTID;
    }

    public Integer getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(Integer pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getPgDetail() {
        return pgDetail;
    }

    public void setPgDetail(String pgDetail) {
        this.pgDetail = pgDetail;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerPhone() {
        return payerPhone;
    }

    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentInfoDTO)) {
            return false;
        }

        PaymentInfoDTO paymentInfoDTO = (PaymentInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentInfoDTO{" +
            "id='" + getId() + "'" +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", paymentCompletedDate='" + getPaymentCompletedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentMethodType='" + getPaymentMethodType() + "'" +
            ", pgTID=" + getPgTID() +
            ", pgStatus=" + getPgStatus() +
            ", pgDetail='" + getPgDetail() + "'" +
            ", payer='" + getPayer() + "'" +
            ", payerPhone='" + getPayerPhone() + "'" +
            "}";
    }
}
