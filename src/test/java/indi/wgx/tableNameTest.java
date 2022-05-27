package indi.wgx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class tableNameTest {

    public static void main(String[] args) {

        List<String> tableNamesList = Stream.of("www", "ggg", "xxx").collect(Collectors.toList());

        String result = tableNamesList.stream().collect(Collectors.joining("|", "(", ")"));

        System.out.println(result);
    }
}
