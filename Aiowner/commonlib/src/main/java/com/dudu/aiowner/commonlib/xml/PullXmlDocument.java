package com.dudu.aiowner.commonlib.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/15.
 */
public class PullXmlDocument {

    public XmlPullParser parserXml(InputStream inputStream) {
        XmlPullParser xmlParser = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xmlParser = factory.newPullParser();
            xmlParser.setInput(inputStream, "UTF-8");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return xmlParser;
    }
}
