package com.dudu.aiowner.commonlib;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/14.
 */
public class ConfigReader {

    private boolean isTest;

    public void readDefaultConfig() {
        try {
            InputStream in = CommonLib.getInstance().getContext()
                    .getResources().openRawResource(R.raw.config);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlParser = factory.newPullParser();
            xmlParser.setInput(in, "UTF-8");
            String tagName = new String("");
            int eventType = xmlParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xmlParser.getName();
                } else if (eventType == XmlPullParser.TEXT) {
                    switch (tagName) {
                        case "is_test":
                            isTest = xmlParser.getText().equals("1");
                            break;
                    }
                } else {
                    tagName = new String("");
                }
                try {
                    eventType = xmlParser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public boolean isTest() {
        return isTest;
    }
}
