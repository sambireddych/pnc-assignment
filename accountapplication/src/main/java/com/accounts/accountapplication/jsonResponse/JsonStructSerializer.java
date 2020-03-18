package com.accounts.accountapplication.jsonResponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class JsonStructSerializer extends JsonSerializer<JsonStruct> {

    @Override
    public void serialize(JsonStruct arg0, JsonGenerator arg1,
                          SerializerProvider arg2) throws IOException{
        arg1.writeStartObject();
        JsonStatusEnum status = arg0.getStatus();

        if (status != null) {
            arg1.writeFieldName(JsonStruct.STATUS);
            arg1.writeString(status.toString());
        } else {
            throw new IOException(JsonStruct.STATUS + " field is mandatory");
        }

        if (status == JsonStatusEnum.SUCCESS) {
            if (arg0.getData() != null) {
                writeDataObject(arg1, arg0.getData(),JsonStruct.DATA);
            } else {
                throw new IOException(JsonStruct.DATA
                        + " field is mandatory when " + JsonStruct.STATUS
                        + " is " + JsonStatusEnum.SUCCESS);
            }
        }


        if (status == JsonStatusEnum.ERROR) {
                if (arg0.getCode() != null) {
                    arg1.writeFieldName(JsonStruct.CODE);
                    arg1.writeString(arg0.getCode());
                }
                if (arg0.getData() != null) {
                    writeDataObject(arg1, arg0.getData(), JsonStruct.DATA);
                }else{

                    writeDataObject(arg1,arg0.getErrors(),JsonStruct.ERROR);
                }
        }
        arg1.writeEndObject();
    }

    private void writeDataObject(JsonGenerator arg1, JsonData data, Object object) throws  IOException {
        arg1.writeFieldName(object.toString());

        arg1.writeStartObject();
        Map<String, Object> map = data.getKeysValuesMap();

        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            arg1.writeFieldName(entry.getKey());
            arg1.writeObject(entry.getValue());
        }
        arg1.writeEndObject();
    }

}