package in.governance.samagra.apps.kafka.producer.utils;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import in.governance.samagra.apps.kafka.producer.model.YellowTrip;
import org.springframework.stereotype.Component;
import java.io.FileReader;
import java.util.List;
@Component
public class DataReader {
    public List yellowTriplist;
    public List ReadYellowTripCSVFile(String dataFile){
        try{
            CSVReader yellowTripCSVReader = new CSVReader(new FileReader(dataFile));
            CsvToBean csvToBean = new CsvToBeanBuilder(yellowTripCSVReader)
                    .withType(YellowTrip.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
            yellowTriplist = csvToBean.parse();
            yellowTripCSVReader.close();
        }
        catch(Exception FileNotFoundException){
            System.out.println("File is not available...");
        }

        return yellowTriplist;
    }
}
