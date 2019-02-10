package com.example.sanji.aninterface.views;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Obj3DView extends RendererActivity {
    private Object3dContainer faceObject3D;
    private float earthradius= 6371000;
    private float rMat[] = new float[16];
    private float orientation[] = new float[3];
    public float rotationx;
    public float rotationy;
    public float rotationz;
    private float anglez;
    private boolean signal;
    float azimuth;
    private SensorManager sensorManager;
    private Sensor sensor;
    /**
     * Called when the activity is first created.
     */

    @Override
    public void initScene() {
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        Light myLight = new Light();
        myLight.position.setZ(150);
        scene.lights().add(myLight);
        IParser myParser = Parser.createParser(Parser.Type.OBJ, getResources(), "com.example.sanji.aninterface:raw/arrow_obj", true);
        myParser.parse();
        System.out.println("ajouter l'objet");
        faceObject3D = myParser.getParsedObject();
        faceObject3D.position().x = faceObject3D.position().y = faceObject3D.position().z = 0;
        //  Depending on the model you will need to change the scale
        faceObject3D.scale().x = faceObject3D.scale().y = faceObject3D.scale().z = 1.2f;
        faceObject3D.rotation().x += 50;
        faceObject3D.rotation().y += 50;
        faceObject3D.rotation().z += 50;
        float  x = earthradius * (float)(cos(46.0035479) * cos(-72.7311097));
        float  y = earthradius * (float)(cos(46.0035479) * sin(-72.7311097));
        float  z = earthradius * (float)(sin(46.0035479));
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
        scene.addChild(faceObject3D);


        signal = true;
    }
    public void setArrowRotation(double sourcelat, double sourcelong, double latitude, double longitude) {
        float  x = earthradius * (float)(cos(latitude) * cos(longitude));
        float  y = earthradius * (float)(cos(latitude) * sin(longitude));
        float  z = earthradius * (float)(sin(latitude));
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);

        float  xsource = earthradius * (float)(cos(sourcelat) * cos(sourcelong));
        float  ysource = earthradius * (float)(cos(sourcelat) * sin(sourcelong));
        float  zsource = earthradius * (float)(sin(sourcelat));

        float directionx = xsource -x;
        float directiony = ysource -y;
        float directionz = zsource -z;

        rotationx = (float)Math.sqrt(directionx*directionx + directiony*directiony + directionz*directionz);
        rotationy = (float)Math.atan(directiony/directionx);
        rotationz = (float) Math.acos(directionz/rotationx);

        System.out.println(anglez);

    }
    @Override
    public void updateScene() {

        signal = false;
        System.out.println(anglez);

    }
}

