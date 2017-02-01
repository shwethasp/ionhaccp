package com.ionhaccp.utils;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.R.attr.data;
import static android.R.attr.key;
import static com.amazonaws.regions.ServiceAbbreviations.S3;

/**
 * Created by mehtermu on 30-01-2017.
 */

public class AWS_Class {
    private static final String CUSTOMER_SPECIFIC_ENDPOINT = "a9wpuqslfhsel.iot.us-east-1.amazonaws.com";
    private static final String COGNITO_POOL_ID = "us-west-2:21c3c095-d5d5-475b-a7b1-80a986c2951c";
    private static final Regions MY_REGION = Regions.US_WEST_2;

    public static CognitoCachingCredentialsProvider credentialsProvider;
    private String clientId;
    Context context;

    public AWS_Class() {

    }

    public AWS_Class(Context context) {
        this.context = context;
        clientId = UUID.randomUUID().

                toString();
        // Initialize the AWS Cognito credentials provider
        credentialsProvider = new

                CognitoCachingCredentialsProvider(
                context, // context
                COGNITO_POOL_ID, // Identity Pool ID
                MY_REGION // Region
        );

        Region region = Region.getRegion(MY_REGION);

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);

        TransferUtility transferUtility = new TransferUtility(s3, context);


        /*
        * For infromation on AWS S3 data follow the folloing link
        *
        * http://docs.aws.amazon.com/mobile/sdkforandroid/developerguide/s3transferutility.html
        * */

        /*
        * To upload a file
        * */

       /* TransferObserver observer = transferUtility.upload(
                MY_BUCKET,      //The bucket to upload to
                OBJECT_KEY,     //The key for the uploaded object
                MY_FILE         //The file where the data to upload exists
        );*/


        /*
        * Upload an Object to S3 with Metadata
        * */


      /*  ObjectMetadata myObjectMetadata = new ObjectMetadata();
            //create a map to store user metadata
          Map<String, String> userMetadata = new HashMap<String,String>();
          userMetadata.put(“myKey”,”myVal”);
            //call setUserMetadata on our ObjectMetadata object, passing it our map
          myObjectMetadata.setUserMetadata(userMetadata);*/

       /* TransferObserver observer = transferUtility.upload(
                MY_BUCKET,        *//* The bucket to upload to *//*
                OBJECT_KEY,       *//* The key for the uploaded object *//*
                MY_FILE,          *//* The file where the data to upload exists *//*
                myObjectMetadata  *//* The ObjectMetadata associated with the object*//*
        );*/


        /*
        * Download an Object from S3
        *
        * */

       /* TransferObserver observer = transferUtility.download(
                MY_BUCKET,     *//* The bucket to download from *//*
                OBJECT_KEY,    *//* The key for the object to download *//*
                MY_FILE        *//* The file to download the object to *//*
        );*/
    }




}
