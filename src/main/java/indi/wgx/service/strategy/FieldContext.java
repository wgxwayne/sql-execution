package indi.wgx.service.strategy;

import javax.security.sasl.SaslClient;
import java.util.Scanner;

public class FieldContext {
    private final FieldStrategy fieldStrategy;

    public FieldContext(FieldStrategy fieldStrategy) {
        this.fieldStrategy = fieldStrategy;
    }
    public Double executeStrategy(String fieldName, String value){
        return fieldStrategy.calculateFieldScore(fieldName, value);
    }



//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String executionField = scanner.next();
//        String value = scanner.next();
//        FieldContext fieldContext;
//
//        if (executionField.equals("Extra")) {
//            fieldContext = new FieldContext(new CalculateExtraScore());
//        } else if (executionField.equals("type")){
//            fieldContext = new FieldContext(new CalculateTypeScore());
//        } else {
//            fieldContext = new FieldContext(new CalculateRowsScore());
//        }
//        fieldContext.executeStrategy(executionField, value);
//    }
}



