package com.example.sanji.aninterface;

import com.example.sanji.aninterface.manageCan.CANConfig;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public static List<DualWidget> parse(String content) throws XmlPullParserException, IOException {
        System.out.println(content.toString());
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(content));
        int eventType = xpp.getEventType();
        // Lists
        ArrayList<DualWidget> dualWidgetsList = new ArrayList<>();
        ArrayList<DataDisplayer> dataDisplayersList = new ArrayList<>();
        ArrayList<CANConfig> cansList = new ArrayList<>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
//                Start document
                System.out.println("Start document");
            } else if (eventType == XmlPullParser.START_TAG) {
                String tagName = xpp.getName();
                if (tagName.trim().equalsIgnoreCase("CAN")) {
                    int attributesCount = xpp.getAttributeCount();
                    CANConfig c = new CANConfig();
                    for (int i = 0; i < attributesCount; i++) {
                        c.updateByAttribute(xpp.getAttributeName(i).trim(), xpp.getAttributeValue(i).trim());
                    }
                    cansList.add(c);
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                // End tag
                String tagName = xpp.getName();
                if (tagName.trim().equalsIgnoreCase("DataDisplayer")) {
                    dataDisplayersList.add(new DataDisplayer(cansList));
                    cansList = new ArrayList<>();
                }
                if (tagName.trim().equalsIgnoreCase("DualVWidget") ||
                        tagName.trim().equalsIgnoreCase("DualHWidget")) {
                    dualWidgetsList.add(new DualWidget(dataDisplayersList, tagName.trim().equalsIgnoreCase("DualHWidget")));
                    dataDisplayersList = new ArrayList<>();
                }
//                System.out.println("End tag " + xpp.getName());
            }
            eventType = xpp.next();
        }
        // End document
        return dualWidgetsList;
    }
}
