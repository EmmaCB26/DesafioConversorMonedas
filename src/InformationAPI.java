import com.google.gson.annotations.SerializedName;

public record InformationAPI(
        @SerializedName("conversion_rate") double conversionRate,
        @SerializedName("conversion_result") double conversionResult
) {
    public String conversionResultDecimales() {
        return String.format("%.2f", conversionResult);
    }

    public String conversionRateDecimales() {
        return String.format("%.2f", conversionRate);
    }
}
