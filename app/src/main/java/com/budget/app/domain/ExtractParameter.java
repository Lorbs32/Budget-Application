package com.budget.app.domain;

public class ExtractParameter
{
    public static String getParameterValue(String url, String parameterName) {
        // Find the parameter in the URL
        String paramString = parameterName + "=";
        int paramIndex = url.indexOf(paramString);

        // If the parameter is not found, return null
        if (paramIndex == -1) {
            return null;
        }

        // Extract the value of the parameter
        int valueIndex = paramIndex + paramString.length();
        int endIndex = url.indexOf("&", valueIndex);

        if (endIndex == -1) {
            endIndex = url.length();
        }

        return url.substring(valueIndex, endIndex);
    }
}
