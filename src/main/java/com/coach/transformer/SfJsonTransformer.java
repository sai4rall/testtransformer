package com.coach.transformer;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SimpleConfig;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.kafka.connect.transforms.util.Requirements.requireStruct;

public class SfJsonTransformer<R extends ConnectRecord<R>> implements Transformation<R> {
    private static final Logger log = LoggerFactory.getLogger(SfJsonTransformer.class);

    public static final String OVERVIEW_DOC = "Replace the record key with a new key formed from a subset of fields in the record value.";
    public static final String FIELDS_CONFIG = "select";
    public static final String JOLT_CONFIG = "joltPath";
    private static final String PURPOSE = "copying fields from value to key";
    private String selectField;
    private String joltPath;
    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(FIELDS_CONFIG, ConfigDef.Type.STRING, ConfigDef.NO_DEFAULT_VALUE, ConfigDef.Importance.HIGH,
                    "select Field names.")
            .define(JOLT_CONFIG,ConfigDef.Type.STRING, ConfigDef.NO_DEFAULT_VALUE, ConfigDef.Importance.HIGH,
                    "Jolt path Field names on the record value ");


    @Override
    public R apply(R record) {

        if (record.valueSchema() == null) {
           // return applySchemaless(record);
            return null;
        } else {
            return applyWithSchema(record);
        }

    }
    private R applyWithSchema(R record) {
        final Struct value = requireStruct(record.value(), PURPOSE);
        System.out.println("===>>>>>"+value+"===========");
        String v=  value.get(selectField).toString();
        Object transformedValue=transformJolt(v);
        return record.newRecord(record.topic(), record.kafkaPartition(),        record.keySchema(), record.key(), null, transformedValue, record.timestamp());
    }


    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public void close() {
    }
    Chainr chainr;

    @Override
    public void configure(Map<String, ?> configs) {
        final SimpleConfig config = new SimpleConfig(CONFIG_DEF, configs);
        joltPath = config.getString(JOLT_CONFIG);
        List<Object> specs = JsonUtils.filepathToList(joltPath);
        chainr = Chainr.fromSpec(specs);
        selectField = config.getString(FIELDS_CONFIG);
    }
    public Object transformJolt(String input){
        log.debug(input);
        JSONObject pl =   XML.toJSONObject(input);
        log.debug(pl.toString());
        Object inputJSON = JsonUtils.jsonToObject(pl.toString());
        Object transformedOutput = chainr.transform(inputJSON);
//        System.out.println(JsonUtils.toPrettyJsonString(transformedOutput));
        return transformedOutput;
    }
}

