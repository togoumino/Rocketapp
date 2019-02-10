package com.example.sanji.aninterface.Xml;

import com.example.sanji.aninterface.messageFormat.LayoutMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class VisitorNodeXml {
    private Node node;
    public List<List<LayoutMessage>> fork = new ArrayList<List<LayoutMessage>>();
    List<LayoutMessage> jsonList = new ArrayList<LayoutMessage>();
    public List<List<String>> listTotalWidget = new ArrayList<>();
    private List<String> listWidgetName = new ArrayList<>();
    public List<LayoutMessage>plotInfo = new ArrayList<>();

    public VisitorNodeXml(Node n, List<LayoutMessage> jList){
        node =n;
        jsonList = jList;
    }
    public void visitNode( ) throws JSONException {
        NodeList nl = node.getChildNodes();
        Node an,an2,an3,an4,an5;
        for (int i=0; i < nl.getLength(); i++) {
            an = nl.item(i);
            if(an.getNodeType()==Node.ELEMENT_NODE) {
                NodeList nl2 = an.getChildNodes();
                for (int p=0; p < nl2.getLength(); p++){
                    an2 = nl2.item(p);
                    if(an2.getNodeType()==Node.ELEMENT_NODE) {
                        NodeList nl3 = an2.getChildNodes();
                        for (int a=0; a < nl3.getLength(); a++){
                            an3 = nl3.item(a);
                            if(an3.getNodeType()==Node.ELEMENT_NODE) {
                                NodeList nl4 = an3.getChildNodes();
                                for (int s=0; s < nl4.getLength(); s++){
                                    an4 = nl4.item(s);
                                    if(an4.getNodeType()==Node.ELEMENT_NODE) {
                                        listWidgetName.add(an4.getAttributes().item(0).getNodeValue());
                                        NodeList nl5 = an4.getChildNodes();
                                        for (int it = 0; it < nl5.getLength(); it++){
                                            an5 = nl5.item(it);
                                            if(an5.getNodeType()==Node.ELEMENT_NODE) {
                                                // if(nl5.item(it).getNodeName().matches("Plot"))
                                                NodeList nl6 = an5.getChildNodes();
                                                for (int at = 0; at < nl6.getLength(); at++){
                                                    NamedNodeMap nodePloInf = nl6.item(at).getAttributes();
                                                    if (nl6.item(at).getNodeType() == Node.ELEMENT_NODE) {
                                                        LayoutMessage arrayPlot = new LayoutMessage();
                                                        JSONObject itemPlot = new JSONObject();

                                                        for (int ah = 0; ah < nodePloInf.getLength(); ah++){
                                                            if (nl6.item(at).getNodeName().matches("CAN")){
                                                                Node tempInf = nodePloInf.item(ah);
                                                                itemPlot.put(tempInf.getNodeName().toString(), tempInf.getNodeValue().toString());
                                                                if(itemPlot.has("name"))
                                                                    arrayPlot.setName(itemPlot.getString("name"));
                                                                if(itemPlot.has("id"))
                                                                   // System.out.println(itemPlot.getString("id"));
                                                                    arrayPlot.setId(itemPlot.getString("id"));
                                                                if(itemPlot.has("maxAcceptable")){arrayPlot.setMaxAcceptable(Double.parseDouble(itemPlot.getString("maxAcceptable"))); }
                                                                if(itemPlot.has("minAcceptable"))
                                                                    arrayPlot.setMinAcceptable(Double.parseDouble(itemPlot.getString("minAcceptable")));
                                                                if(itemPlot.has("chiffresSign"))
                                                                    arrayPlot.setChiffresSign(Integer.parseInt(itemPlot.getString("chiffresSign")));
                                                                else
                                                                    arrayPlot.setChiffresSign(6);
                                                                if(itemPlot.has("serialNb"))
                                                                    arrayPlot.setSerialNb(Integer.parseInt(itemPlot.getString("serialNb")));
                                                                try{
                                                                    String[] data = new String[2];
                                                                    if(itemPlot.has("display")){
                                                                        data = itemPlot.getString("display").split(" ");
                                                                        arrayPlot.setDisplay(Double.parseDouble(data[0]));}

                                                                }catch (NumberFormatException e){

                                                                }
                                                                if(itemPlot.has("specificSource"))
                                                                    arrayPlot.setSpecificSource(itemPlot.getString("specificSource"));


                                                            }
                                                            if(arrayPlot.getId() != null){
                                                                plotInfo.add(arrayPlot);
                                                            }

                                                        }

                                                        NodeList nl7 = nl6.item(at).getChildNodes();
                                                        for (int o = 0; o < nl7.getLength(); o++) {
                                                            NamedNodeMap nodeMap = nl7.item(o).getAttributes();
                                                            if (nl7.item(o).getNodeType() == Node.ELEMENT_NODE) {
                                                                LayoutMessage array = new LayoutMessage();
                                                                JSONObject item = new JSONObject();
                                                                for (int ih = 0; ih < nodeMap.getLength(); ih++) {
                                                                    Node tempNode = nodeMap.item(ih);
                                                                    if (nl7.item(o).getNodeName().matches("CAN")) {
                                                                        item.put(tempNode.getNodeName().toString(), tempNode.getNodeValue().toString());
                                                                        if(item.has("name"))
                                                                            array.setName(item.getString("name"));
                                                                        if(item.has("id"))
                                                                            array.setId(item.getString("id"));
                                                                        if(item.has("maxAcceptable")){array.setMaxAcceptable(Double.parseDouble(item.getString("maxAcceptable"))); }
                                                                        if(item.has("minAcceptable"))
                                                                            array.setMinAcceptable(Double.parseDouble(item.getString("minAcceptable")));
                                                                        if(item.has("chiffresSign"))
                                                                            array.setChiffresSign(Integer.parseInt(item.getString("chiffresSign"))+2);
                                                                        else
                                                                            array.setChiffresSign(6);
                                                                        if(item.has("serialNb"))
                                                                            array.setSerialNb(Integer.parseInt(item.getString("serialNb")));
                                                                       try{
                                                                            String[] data = new String[2];
                                                                            if(item.has("display")){
                                                                                data = item.getString("display").split(" ");
                                                                            array.setDisplay(Double.parseDouble(data[0]));}

                                                                        }catch (NumberFormatException e){

                                                                        }
                                                                        if(item.has("specificSource"))
                                                                            array.setSpecificSource(item.getString("specificSource"));
                                                                    }
                                                                }
                                                                    jsonList.add(array);
                                                            }
                                                        }
                                                        if (jsonList.size() != 0)
                                                            fork.add(jsonList);
                                                        jsonList = new ArrayList<LayoutMessage>();
                                                    }
                                            }
                                            }
                                        }
                                    }

                                }

                                listTotalWidget.add(listWidgetName);
                                listWidgetName = new ArrayList<>();
                            }
                        }
                    }
                }
            }

        }
    }
}
