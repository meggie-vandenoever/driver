package com.example.driver;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
// https://ali-ulvi.github.io/soapJava.html
public class App {
    public static void main(String[] args) {
        String soapEndpointUrl = "http://www.dneonline.com/calculator.asmx";
        String soapAction = "http://tempuri.org/Add";

        String soapBody =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <tem:Add>\n" +
            "         <tem:intA>1</tem:intA>\n" +
            "         <tem:intB>2</tem:intB>\n" +
            "      </tem:Add>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(soapEndpointUrl);
            httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
            httpPost.setHeader("SOAPAction", soapAction);

            StringEntity requestEntity = new StringEntity(soapBody, "UTF-8");
            httpPost.setEntity(requestEntity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String resp = EntityUtils.toString(responseEntity);
                    System.out.println("Response:");
                    System.out.println(resp);
                    // Optioneel: parse het resultaat met een XML parser
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
