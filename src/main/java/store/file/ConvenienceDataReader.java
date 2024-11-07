package store.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConvenienceDataReader {

    private final String SEPARATOR = ",";

    public List<String[]> read(String fileName) {
        List<String[]> datas = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))
        ) {
            add(br, datas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }

    private void add(BufferedReader br, List<String[]> datas) throws IOException {
        String line;
        br.readLine();

        while ((line = br.readLine()) != null) {
            datas.add(line.split(SEPARATOR));
        }
    }
}
