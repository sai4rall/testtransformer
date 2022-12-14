package com.coach.transformer;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;

public class TestMain {
    public static Chainr chainr;
    private static final int PRINT = 4;

    public static void main(String[] args) {
        List<Object> specs = JsonUtils.filepathToList("C:\\Users\\Admin\\Documents\\code\\testtransformer\\src\\main\\resources\\test_spec2.json");
        chainr = Chainr.fromSpec(specs);
        String xmldata="{\n" +
                "    \"string\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><Order CarrierServiceCode=\\\"\\\" Createuserid=\\\"TPR_INT_CREATEORDER\\\"    CustomerEMailID=\\\"npowell@bionix.com\\\"  DocumentType=\\\"0001\\\"    EnteredBy=\\\"MOBILE\\\" EnterpriseCode=\\\"COACH_US\\\"     EntryType=\\\"OUTLET_ECOMM\\\" OrderDate=\\\"2022-11-07T22:51:36+00:00\\\"    OrderHeaderKey=\\\"202211072257111183606032\\\" OrderNo=\\\"XUP11741380\\\"    OrderType=\\\"\\\" PriorityCode=\\\"N\\\" SCAC=\\\"\\\" SFEvent=\\\"\\\"    SellerOrganizationCode=\\\"COACH_US_ECOMM_OUTLET\\\"    SourcingClassification=\\\"DIGITAL_OUTLET\\\"     Status=\\\"Included In Shipment\\\">    <PriceInfo Currency=\\\"USD\\\" TotalAmount=\\\"149.77\\\"/>    <Extn ExtnCheckoutType=\\\"GUEST\\\" ExtnGiftFlag=\\\"N\\\" ExtnGiftWrap=\\\"N\\\"        ExtnIsPorter=\\\"N\\\" ExtnLocale=\\\"en_US\\\" ExtnShopRunner=\\\"N\\\" ExtnStorefrontPlatformVersion=\\\"18.10\\\"/>    <ChargeTransactionDetails TotalOpenAuthorizations=\\\"149.77\\\"        TotalOpenBookings=\\\"149.77\\\" TotalTransferredIn=\\\"0.00\\\" TotalTransferredOut=\\\"0.00\\\">        <ChargeTransactionDetail AuthorizationID=\\\"S33ZPBDRM9T73VB2\\\"            BookAmount=\\\"0.00\\\" ChargeType=\\\"AUTHORIZATION\\\"            CreditAmount=\\\"0.00\\\" DebitAmount=\\\"0.00\\\"            PaymentKey=\\\"202211072269571183606096\\\" RequestAmount=\\\"149.77\\\"            SettledAmount=\\\"0.00\\\" Status=\\\"CHECKED\\\" TransactionDate=\\\"2022-11-07T22:57:13+00:00\\\"/>    </ChargeTransactionDetails></Order>\"\n" +
                "  }";
        System.out.println(xmldata);
        String pl = (new JSONObject(xmldata)).getString("string");
        System.out.println("=================");
          JSONObject jsonObject = XML.toJSONObject(pl);

        System.out.println(jsonObject);
        System.out.println("***************");
        //  JSONObject jsonObject = XML.toJSONObject(pl);

//        String jsonPrettyPrintString = jsonObject.toString(PRINT);
//
//        transformJolt(xmldata);
    }
    public static String transformJolt(String input){
        Object inputJSON = JsonUtils.jsonToObject(input);
        Object transformedOutput = chainr.transform(inputJSON);
        System.out.println(JsonUtils.toPrettyJsonString(transformedOutput));
        return JsonUtils.toPrettyJsonString(transformedOutput);
    }
}
