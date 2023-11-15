package com.ispl.ekalarogya.training.models;


import com.google.gson.annotations.SerializedName;

public class SanchPdfResposnse {
    @SerializedName("Status")
    String status;
    @SerializedName("Message")
    String message;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @SerializedName("mwr_report")
    MWRReportResponse mwr_report;

    public MWRReportResponse getMwr_report() {
        return mwr_report;
    }

    public class MWRReportResponse{
        @SerializedName("pdf_report")
        String pdf_report;

        public String getPdf_report() {
            return pdf_report;
        }
    }

}
