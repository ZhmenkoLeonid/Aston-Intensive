package com.zhmenko.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhmenko.user.model.request.BankAccountInsertRequest;
import com.zhmenko.user.model.request.BillingDetailsInsertRequest;
import com.zhmenko.user.model.request.CreditCardInsertRequest;

import java.io.Reader;

/**
 * 1Utility class for JSON operations.
 */
public final class JsonUtils {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                    .of(BillingDetailsInsertRequest.class, "billing_type").recognizeSubtypes()
                    .registerSubtype(CreditCardInsertRequest.class, "credit_card")
                    .registerSubtype(BankAccountInsertRequest.class, "bank_account"))
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    private JsonUtils() {
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to be converted to JSON string.
     * @return The JSON string representation of the given object.
     */
    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param <T>         The type of the object to be created from the JSON string.
     * @param reader      The Reader containing the JSON string.
     * @param objectClass The class of the object to be created.
     * @return The object created from the JSON string.
     * @throws com.google.gson.JsonParseException if the JSON string cannot be parsed into the specified class.
     */
    public static <T> T fromJsonString(Reader reader, Class<T> objectClass) {
        return gson.fromJson(reader, objectClass);
    }
}
