package org.symbench.creopropertiesserver.creo;

import org.symbench.creopropertiesserver.utils.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.ptc.cipjava.*;

import com.ptc.pfc.pfcSolid.*;
import com.ptc.pfc.pfcBase.Point3D;

public class MassPropertiesAnalyzer extends BaseAnalyzer{

    private static final Logger logger = LoggerFactory.getLogger(MassPropertiesAnalyzer.class.getName());

    private static final String MASS = "mass";

    private static final String VOLUME = "volume";

    private static final String SURFACE_AREA = "surface_area";

    private static final String DENSITY = "density";

    private static final String CENTER_OF_MASS = "center_of_mass";

    private static final String COORD_SYS_INERTIA = "coord_sys_inertia";

    private static final String COORD_SYS_INERTIA_TENSOR = "coord_sys_inertia_tensor";

    private static final String CENTER_OF_GRAVITY_INERTIA_TENSOR = "ctr_grav_inertia_tensor";

    private static final String X_AXIS = "x_axis";

    private static final String Y_AXIS = "y_axis";

    private static final String Z_AXIS = "z_axis";

    private static final String X = "x";

    private static final String Y = "y";

    private static final String Z = "z";


    public Map<String, Object> getFileMassProperties(String modelPath) throws jxthrowable {
        Solid solid = (Solid) this.getModel(modelPath);
        return computeFileMassProperties(solid);
    }

    public Map<String, Object> getFileMassProperties() throws jxthrowable {
        Solid solid = (Solid) this.getModel(null);
        return computeFileMassProperties(solid);
    }

    private static Map<String, Object> computeFileMassProperties(Solid solid) throws jxthrowable {
        MassProperty property = solid.GetMassProperty(null);
        logger.info("Computing mass properties for " + solid.GetFullName());
        return getMassPropertiesData(property);
    }

    private static Map<String, Object> getMassPropertiesData(MassProperty property) throws jxthrowable{
        Map<String, Object> massPropertiesData = new HashMap<>();

        massPropertiesData.put(MASS, property.GetMass());
        massPropertiesData.put(VOLUME, property.GetVolume());
        massPropertiesData.put(DENSITY, property.GetDensity());
        massPropertiesData.put(SURFACE_AREA, property.GetSurfaceArea());

        Map<String, Object> centerOfMassData = getCenterOfMassData(property.GetGravityCenter());
        massPropertiesData.put(CENTER_OF_MASS, centerOfMassData);

        Map<String, Object> coordinateSystemInertiaData = getInertiaTensorData(
                property.GetCoordSysInertia()
        );
        massPropertiesData.put(COORD_SYS_INERTIA, coordinateSystemInertiaData);

        Map<String, Object> coordinateSystemInertiaTensorData = getInertiaTensorData(
                property.GetCoordSysInertiaTensor()
        );
        massPropertiesData.put(COORD_SYS_INERTIA_TENSOR, coordinateSystemInertiaTensorData);

        Map<String, Object> ctrGravityInertiaTensorData = getInertiaTensorData(
                property.GetCenterGravityInertiaTensor()
        );
        massPropertiesData.put(CENTER_OF_GRAVITY_INERTIA_TENSOR, ctrGravityInertiaTensorData);

        return massPropertiesData;
    }

    private static Map<String, Object> getCenterOfMassData(Point3D cg) throws jxthrowable {
        Map<String, Object> centerOfMassData = new HashMap<>();

        centerOfMassData.put(X, cg.get(0));
        centerOfMassData.put(Y, cg.get(1));
        centerOfMassData.put(Z, cg.get(2));

        return centerOfMassData;
    }

    private static Map<String, Object> getInertiaTensorData(Inertia inertia) throws jxthrowable {
        Map<String, Object> inertiaData = new HashMap<>();

        inertiaData.put(X_AXIS, getAxisData(0, inertia));
        inertiaData.put(Y_AXIS, getAxisData(1, inertia));
        inertiaData.put(Z_AXIS, getAxisData(2, inertia));

        return inertiaData;
    }

    private static Map<String, Object> getAxisData(int axis, Inertia inertia) throws jxthrowable {
        Map<String, Object> axisData = new HashMap<>();

        axisData.put(X, inertia.get(axis, 0));
        axisData.put(Y, inertia.get(axis, 1));
        axisData.put(Z, inertia.get(axis, 2));

        return axisData;
    }

    public static MassPropertiesAnalyzer getInstance() {
        return new MassPropertiesAnalyzer();
    }
}
