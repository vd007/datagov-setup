package in.governance.samagra.apps.kafka.producer.model;
import java.time.LocalDate;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
@Setter
@Getter
public class YellowTrip {
    @CsvBindByName(column="VendorID")
    public int vendorId;
    @CsvBindByName(column="tpep_pickup_datetime")
    public String tpepPickupDatetime;
    @CsvBindByName(column="tpep_dropoff_datetime")
    public String tpepDropoffDatetime;
    @CsvBindByName(column="passenger_count")
    public int passengerCount;
    @CsvBindByName(column="trip_distance")
    public double tripDistance;
    @CsvBindByName(column="RatecodeID")
    public int ratecodeID;
    @CsvBindByName(column="store_and_fwd_flag")
    public String storeAndFWDFlag;
    @CsvBindByName(column="PULocationID")
    public int pULocationID;
    @CsvBindByName(column="DOLocationID")
    public int dOLocationID;
    @CsvBindByName(column="payment_type")
    public int paymentType;
    @CsvBindByName(column="fare_amount")
    public double fareAmount;
    @CsvBindByName(column="extra")
    public double extraAmount;
    @CsvBindByName(column="mta_tax")
    public double mtaTax;
    @CsvBindByName(column="tip_amount")
    public double tipAmount;
    @CsvBindByName(column="tolls_amount")
    public double tollsAmount;
    @CsvBindByName(column="improvement_surcharge")
    public double improvementSurcharge;
    @CsvBindByName(column="total_amount")
    public double totalAmount;
    @CsvBindByName(column="congestion_surcharge")
    public double congestionSurcharge;
}
