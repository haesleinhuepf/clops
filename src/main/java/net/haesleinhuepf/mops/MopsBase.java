package net.haesleinhuepf.mops;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.AbstractCLIJPlugin;
import net.haesleinhuepf.clij.macro.CLIJHandler;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.neighborhood.CenteredRectangleShape;
import net.imglib2.algorithm.neighborhood.DiamondShape;
import net.imglib2.algorithm.neighborhood.HyperSphereShape;
import net.imglib2.algorithm.neighborhood.Shape;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import org.scijava.Context;

public abstract class MopsBase extends AbstractCLIJPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor {

    static Context context = null;
    protected static OpService ops  = null;

    public MopsBase() {
        synchronized (this) {
            if (context == null) {
                context = new Context(OpService.class);
                ops = context.getService(OpService.class);
            }
        }
    }

    public abstract RandomAccessibleInterval run();

    boolean receivedSomeImageFromGPU = false;

    @Override
    public boolean executeCL() {
        receivedSomeImageFromGPU = false;
        RandomAccessibleInterval result = run();
        if (receivedSomeImageFromGPU) {
            CLIJHandler.getInstance().pushToGPU(IJ.getImage().getTitle());
        }
        return true;
    }

    public abstract String getParameters();

    @Override
    public String getParameterHelpText() {
        return getParameters().
                replace("BinaryImage ", "String ") .
                replace("IntegerNumber ", "Number ") .
                replace("Image ", "String ").
                replace("SphereShape ", "Number ").
                replace("RectangleShape ", "Number ").
                replace("CubeShape ", "Number ");
    }

    protected Img asImg(Object object) {

        ImagePlus input = null;
        if (CLIJHandler.getInstance().reportGPUMemory().contains("- " + object + "[")) {
            CLIJHandler.getInstance().pullFromGPU("" + object);
            input = IJ.getImage();
            if (input != null && input.getTitle() == object.toString()) {
                receivedSomeImageFromGPU = true;
            } else {
                input = null;
            }
        }

        if (input == null) {
            input = WindowManager.getImage("" + object);
        }
        Img val = ImageJFunctions.wrapReal(input);
        return val;
    }

    protected Img asBinaryImg(Object object) {

        ImagePlus input = null;
        if (CLIJHandler.getInstance().reportGPUMemory().contains("- " + object + "[")) {
            CLIJHandler.getInstance().pullFromGPU("" + object);
            input = IJ.getImage();
            if (input != null && input.getTitle() == object.toString()) {
                receivedSomeImageFromGPU = true;
            } else {
                input = null;
            }
        }

        if (input == null) {
            input = WindowManager.getImage("" + object);
        }
        CLIJ clij = CLIJ.getInstance();
        ClearCLBuffer buffer = clij.push(input);

        Img val = (Img)clij.pullBinaryRAI(buffer);
        buffer.close();
        return val;
    }

    protected Shape asCube(Object object) {
        int radius = asInteger(object);

        CenteredRectangleShape crs = new CenteredRectangleShape(new int[]{radius, radius, radius}, false);
        return crs;
    }

    protected Shape asRectangle(Object object) {
        int radius = asInteger(object);

        CenteredRectangleShape crs = new CenteredRectangleShape(new int[]{radius, radius}, false);
        return crs;
    }

    protected Shape asSphere(Object object) {
        long radius = asInteger(object);

        HyperSphereShape hss = new HyperSphereShape(radius);
        return hss;
    }
}
