package com.example.sanji.aninterface.rest;

import android.os.AsyncTask;

import com.example.sanji.aninterface.ObjectToUpdateInterface;
import com.example.sanji.aninterface.RocketInfo;
import com.example.sanji.aninterface.Xml.VisitorNodeXml;
import com.example.sanji.aninterface.messageFormat.CANMessage;
import com.example.sanji.aninterface.messageFormat.LayoutMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by christian-polymorse on 3/2/18.
 */

public class Client extends AsyncTask<Void, Void, Void> implements ObjectToUpdateInterface /*Observer<CANMessage>*/{


    /***************************************************/
    //public attributes
    public String dstAddress, routes,appName,appId;
    public int dstPort;
    public List<String> TabName = new ArrayList<String>();
    public List<String> TabHName = new ArrayList<String>();
    public List<String> thirdTabName = new ArrayList<String>();
    public List<String> lastTabName = new ArrayList<String>();
    public List<List<String>> listTotalWidget = new ArrayList<>();
    public List<LayoutMessage> listJsonFirstTabD1 = new ArrayList<>();
    public List<LayoutMessage> listJsonFirstTabD2 = new ArrayList<>();
    public List<LayoutMessage> listJsonFirstTabWid2D1 = new ArrayList<>();
    public List<LayoutMessage> listJsonFirstTabWid2D2 = new ArrayList<>();
    public List<LayoutMessage> listJsonLastTabWid2D1 = new ArrayList<>();
    public List<LayoutMessage> listJsonLastTabWid2D2 = new ArrayList<>();
    public List<LayoutMessage> plotInfomation = new ArrayList<>();
    /******************************************************/
    //private attributes

    private VisitorNodeXml visit;
    private List<LayoutMessage> jsonList = new ArrayList<LayoutMessage>();
    private String Unit = "";


    public Client(String addr, int port, String route) {
        dstAddress = addr;
        dstPort = port;
        routes = route;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL myUrl = null;

        try {
            myUrl = new URL("http", this.dstAddress, this.dstPort, routes);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            InputStream stream = conn.getInputStream();
            myParser.setInput(stream, "UTF-8");

           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           DocumentBuilder db = dbf.newDocumentBuilder();
           Document doc = db.parse(new InputSource(myUrl.openStream()));
           Node n =  doc.getFirstChild();
           visit = new VisitorNodeXml(n,jsonList);
           visit.visitNode();
           listTotalWidget = visit.listTotalWidget;
           plotInfomation = visit.plotInfo;
           listJsonFirstTabD1 = visit.fork.get(0);
           listJsonFirstTabD2 = visit.fork.get(1);
           listJsonFirstTabWid2D1 = visit.fork.get(2);
           listJsonFirstTabWid2D2 = visit.fork.get(3);
           listJsonLastTabWid2D1 = visit.fork.get(5);
           listJsonLastTabWid2D2 = visit.fork.get(6);
           //getting name and id of the xml received
           NodeList Rocket = doc.getElementsByTagName("Rocket");
           for (int i=0; i<Rocket.getLength(); i++) {
               Node node = Rocket.item(i);
                  appName = ((Element) node).getAttribute("name");
                  appId = ((Element) node).getAttribute("id");
           }
           //System.out.println( XMLParser.parse(stream.toString()));
           /***********************************************/
           //button'information ignored

           /*********************************************************/

           //Allow to get information of different tab
           NodeList tabList = doc.getElementsByTagName("TabContainer");
           for (int i=0; i<tabList.getLength(); i++) {
               Node node = tabList.item(i);
               NodeList child = node.getChildNodes();

               for (int j=0; j<child.getLength(); j++){
                   Node childNode = child.item(j);
                   if(childNode.getNodeType()==Node.ELEMENT_NODE) {
                       String attributeValue = ((Element) childNode).getAttribute("name");
                       switch (i) {
                           case 0:
                               TabName.add(attributeValue);
                               break;
                           case 1:
                               TabHName.add(attributeValue);
                               break;
                           case 2:
                               thirdTabName.add(attributeValue);
                               break;
                           case 3:
                               lastTabName.add(attributeValue);
                               break;
                       }
                   }

               }
           }

       } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
           e.printStackTrace();
       } catch (SAXException e) {
           e.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       }
    return null;
}
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(null);
    }

public void VerifyAndUpdateElement(CANMessage obj,List<LayoutMessage> listValue){
    String tempo = "";
        for(LayoutMessage l : listValue){
            if (l.getId() != null && obj.getSid().matches(l.getId())   ){
                tempo = round(String.valueOf(obj.getData1()), l.getChiffresSign());
                l.setDisplay(Double.parseDouble(tempo));
            }

        }

}
    public static String round(String value, int chiffreSign) {
        if (chiffreSign != 0)
            return value.length() >= chiffreSign ? value.substring(0, chiffreSign) : applyPadding(value, chiffreSign);
        else
            return value;
    }
    public List<LayoutMessage> plotValue = new ArrayList<>();

    private List<LayoutMessage> plotGraph(CANMessage obj,List<LayoutMessage>  listXml) throws JSONException {

       // CANMessage arrayPlot =  new CANMessage();

        for(LayoutMessage p : listXml)
        if (p.getId() != null && obj.getSid().matches(p.getId())){
            p.setDisplay(obj.getData1());
            /*p.set(obj.getDestType());
            p.setCrc(obj.getCrc());
            p.setSrcType(obj.getSrcType());
            p.setData1(obj.getData1());
            p.setData2(obj.getData2());*/
        }
       // System.out.println(listXml);
        plotValue = listXml;
        return plotValue;
    }

    private static String applyPadding(String val, int len) {
        for (int i = 0; i < len - val.length(); i++) {
            val += '0';
        }
        return val;
    }

    /*@Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CANMessage can) {
        VerifyAndUpdateElement(can, listJsonFirstTabD1);
        VerifyAndUpdateElement(can, listJsonFirstTabD2);
        VerifyAndUpdateElement(can, listJsonFirstTabWid2D1);
        VerifyAndUpdateElement(can, listJsonFirstTabWid2D2);
        VerifyAndUpdateElement(can, listJsonLastTabWid2D1);
        try {
            plotGraph(can,plotInfomation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    /*@Override
    public void onNext(String s) {
        try {
            final JSONObject jsonObj = new JSONObject(s);
            // System.out.println(jsonObj);
            CANMessage can = new CANMessage();
            can.setCrc(jsonObj.getString("crc"));
            can.setSid(jsonObj.getString("sid"));
            can.setDestSerial(Integer.parseInt(jsonObj.getString("dest_serial")));
            can.setDestType(Integer.parseInt(jsonObj.getString("dest_type")));
            can.setSrcSerial(Integer.parseInt(jsonObj.getString("src_serial")));
            can.setSrcType(Integer.parseInt(jsonObj.getString("src_type")));
            can.setData1(Double.parseDouble(jsonObj.getString("data_1")));
            try{
                can.setData2(Float.valueOf(jsonObj.getString("data_2")));}
            catch(NumberFormatException ex) { }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

  /* @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }*/

    @Override
    public void update(String data) {
        try {
            final JSONObject jsonObj = new JSONObject(data);
            // System.out.println(jsonObj);
            CANMessage can = new CANMessage();
            can.setCrc(jsonObj.getString("crc"));
            can.setSid(jsonObj.getString("sid"));
            can.setDestSerial(Integer.parseInt(jsonObj.getString("dest_serial")));
            can.setDestType(Integer.parseInt(jsonObj.getString("dest_type")));
            can.setSrcSerial(Integer.parseInt(jsonObj.getString("src_serial")));
            can.setSrcType(Integer.parseInt(jsonObj.getString("src_type")));
            can.setData1(Double.parseDouble(jsonObj.getString("data_1")));
            try{
                can.setData2(Float.valueOf(jsonObj.getString("data_2")));}
            catch(NumberFormatException ex) { }
            VerifyAndUpdateElement(can, listJsonFirstTabD1);
            VerifyAndUpdateElement(can, listJsonFirstTabD2);
            VerifyAndUpdateElement(can, listJsonFirstTabWid2D1);
            VerifyAndUpdateElement(can, listJsonFirstTabWid2D2);
            VerifyAndUpdateElement(can, listJsonLastTabWid2D1);
            plotGraph(can,plotInfomation);
            if(can.getSid().matches("GPS1_LONGITUDE"))
                RocketInfo.getInstance().setLon(can.getData1());
            if(can.getSid().matches("GPS1_LATITUDE"))
                RocketInfo.getInstance().setLat(can.getData1());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
