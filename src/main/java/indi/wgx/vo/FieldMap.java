package indi.wgx.vo;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 常量类
 */

@Component
public class FieldMap {

    // 字段内部之间的占比
    public  static HashMap<String, Double> singleFieldRatio = new HashMap<String, Double>() {
        {
            put("type", 0.4);
            put("rows", 0.4);
            put("Extra", 0.2);
        }
    };


    public  static HashMap<String, Integer> typeMap = new HashMap<String, Integer>(){
        {
            put("", 100);
            put("NULL", 100);
            put("ALL", 100);
            put("index", 90);
            put("range", 80);
            put("index_subquery", 70);
            put("unique_subquery", 60);
            put("index_merge", 50);
            put("ref_or_null", 40);
            put("fulltext", 30);
            put("ref", 20);
            put("eq_ref", 10);
            put("const", 0);
            put("system",0);
        }
    };

    public  static HashMap<String, Integer> extraMap = new HashMap<String, Integer>(){
        {
            put("null", 0);
            put("NULL", 0); // 好。算不是上是最好
            put("Using temporary", 50);
            put("Using filesort", 50);
//                put("Using index condition",
//                put("Range checked for each record",
//                put("Using where with pushed condition",
//                put("Using MRR",
//                put("Skip_open_table",
//                put("Open_frm_only",
//                put("Open_full_table",
//                put("Scanned",
//                put("Using index for group-by",
//                put("Start temporary",
//                put("End temporary",
//                put("FirstMatch",
//                put("Materialize",
//                put("Start materialize",
//                put("End materialize",
//                put("unique row not found",
//                put("Index dive skipped due to FORCE",
//                put("Impossible WHERE noticed after reading const tables",
            put("Using where", 0);
//                put("Using join buffer",
            put("Using index", 0);   // 最好
//                put("const row not found",
            put("Distinct", 0);
//                put("Full scan on NULL key",
//                put("Impossible HAVING",
            put("Impossible WHERE", 100);
//                put("LooseScan",
//                put("No matching min/max row",
//                put("no matching row in const table",
//                put("No matching rows after partition pruning",
//                put("No tables used",
//                put("Not exists",
//                put("Plan isn't ready yet",
//                put("Select tables optimized away",
//                put("Using intersect",
//                put("Using union",
//                put("Using sort_union",

        }
    };

    public  static HashMap<Integer, Double> rowsNumMap = new HashMap<Integer, Double>() {
        {
            // 每一行的系数，这里列举了8行的
            put(1, 1.0);
            put(2, 1/Math.pow(2, 1));
            put(3, 1/Math.pow(2, 2));
            put(4, 1/Math.pow(2, 3));
            put(5, 1/Math.pow(2, 4));
            put(6, 1/Math.pow(2, 5));
            put(7, 1/Math.pow(2, 6));
        }
    };

}
